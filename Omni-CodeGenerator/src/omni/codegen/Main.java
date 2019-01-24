package omni.codegen;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main{
    private static final Pattern FQT_PATTERN
    =Pattern.compile("([\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*");
    private static final Map<Path,Path> TEMPLATE_AND_SOURCE_FOLDERS=new HashMap<>();
    private static final String WORKING_SET_ROOT_KEY="OMNI_WORKING_SET_ROOT";
    static{
        String workingSetRoot=System.getenv(WORKING_SET_ROOT_KEY);
        System.out.println(WORKING_SET_ROOT_KEY+" = "+workingSetRoot);
        final String[] projectNames=new String[]{"Omni-Function","Omni-Impl","Omni-Util"};
        for(final String projectName:projectNames){
            TEMPLATE_AND_SOURCE_FOLDERS.put(Paths.get(workingSetRoot,projectName,"templates"),
                    Paths.get(workingSetRoot,projectName,"src-generated"));
            TEMPLATE_AND_SOURCE_FOLDERS.put(Paths.get(workingSetRoot,projectName,"templates-test"),
                    Paths.get(workingSetRoot,projectName,"test-generated"));
        }
    }
    public static void main(String[] args) throws Exception{
        TEMPLATE_AND_SOURCE_FOLDERS.forEach(Main::processTemplates);
    }
    private static void processTemplates(Path templateFolder,Path outputFolder){
        try{
            Files.createDirectories(templateFolder);
            Files.walkFileTree(templateFolder,new SimpleFileVisitor<>(){
                @SuppressWarnings("unused") public FileVisitResult visitFile(Path file,BasicFileAttributes attributes)
                        throws IOException{
                    final String fileName=file.getFileName().toString();
                    if(fileName.endsWith(".template")){
                        final Path semaphoreFile=file.resolveSibling(fileName+".semaphore");
                        if(Files.exists(semaphoreFile)){
                            final FileTime lastModified=Files.getLastModifiedTime(file);
                            final List<String> semaphoreLines=Files.readAllLines(semaphoreFile);
                            if(semaphoreLines.size()>0){
                                final String dateInFile=semaphoreLines.get(0);
                                long lDateInFile;
                                try{
                                    lDateInFile=Long.valueOf(dateInFile.trim());
                                    final long lLastModified=lastModified.toMillis();
                                    if(lDateInFile>=lLastModified){
                                        System.out.println("UNCHANGED: Template file "+fileName);
                                        return FileVisitResult.CONTINUE;
                                    }
                                }catch(final Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                        System.out.println("MODIFIED : Template file "+fileName);
                        try{
                            final String fqt=fileName.substring(0,fileName.lastIndexOf(".template"));
                            final var fqtMatcher=FQT_PATTERN.matcher(fqt);
                            if(!fqtMatcher.matches()){
                                System.err.println("The fully-qualified-type \""+fqt+"\" is not valid");
                            }else{
                                final TemplateProcessor2 processor=new TemplateProcessor2(file);
                                final Map<TypeDefinition,List<String>> sources=processor.sources;
                                final Map<TypeDefinition,Integer> typeDefs=processor.typeDefs;
                                typeDefs.forEach((typeDef,lineNumber)->{
                                    final List<String> sourceCode=sources.get(typeDef);
                                    final var sourceFilePath=outputFolder.resolve(
                                            typeDef.parseLine(fqt).replaceAll("\\.",Matcher.quoteReplacement(File.separator))+".java");
                                    if(true){
                                        try{
                                            Files.createDirectories(sourceFilePath.getParent());
                                            if(Files.exists(sourceFilePath)){
                                                final List<String> existingCode=Files.readAllLines(sourceFilePath);
                                                int sourceCodeSize;
                                                if((sourceCodeSize=existingCode.size())==sourceCode.size()){
                                                    int index=0;
                                                    for(;;){
                                                        if(index==sourceCodeSize){
                                                            System.out.println(
                                                                    "UNCHANGED: Source code file "+sourceFilePath);
                                                            return;
                                                        }
                                                        final String existingLine=existingCode.get(index);
                                                        final String newLine=sourceCode.get(index);
                                                        if(!existingLine.equals(newLine)){
                                                            break;
                                                        }
                                                        ++index;
                                                    }
                                                }
                                            }
                                            System.out.println("MODIFIED : Source code file "+sourceFilePath);
                                            Files.write(sourceFilePath,sourceCode);
                                        }catch(final IOException e){
                                            e.printStackTrace();
                                        }
                                    }else{
                                        System.out.println("writing type "+typeDef.name()+" to "+sourceFilePath);
                                    }
                                });
                                if(!processor.trouble){
                                    writeSemaphoreFile(Files.getLastModifiedTime(file).toMillis(),file);
                                }
                            }
                        }catch(final Exception e){
                            e.printStackTrace();
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }catch(final IOException e){
            e.printStackTrace();
        }
    }
    private static void writeSemaphoreFile(long currentTime,Path templateFile) throws IOException{
        final String currentTimeStr=Long.toString(currentTime);
        final Path semaphoreFile=templateFile.resolveSibling(templateFile.getFileName().toString()+".semaphore");
        Files.write(semaphoreFile,List.of(currentTimeStr));
    }
}
