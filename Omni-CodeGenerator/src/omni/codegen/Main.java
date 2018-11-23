package omni.codegen;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main{
  private static final Pattern FQT_PATTERN
      =Pattern.compile("([\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*");
  private static final Map<Path,Path> TEMPLATE_AND_SOURCE_FOLDERS=new HashMap<>();
  static{
    if(true){
      TEMPLATE_AND_SOURCE_FOLDERS.put(Paths.get("C:\\Users\\Thomas\\git\\repository\\Omni-Function\\templates"),
          Paths.get("C:\\Users\\Thomas\\git\\repository\\Omni-Function\\src-generated"));
      TEMPLATE_AND_SOURCE_FOLDERS.put(Paths.get("C:\\Users\\Thomas\\git\\repository\\Omni-Impl\\templates"),
          Paths.get("C:\\Users\\Thomas\\git\\repository\\Omni-Impl\\src-generated"));
    }else{
      TEMPLATE_AND_SOURCE_FOLDERS.put(Paths.get("D:\\eclipse\\workspace\\photonR\\Omni-Function\\templates"),
          Paths.get("D:\\eclipse\\workspace\\photonR\\Omni-Function\\src-generated"));
      TEMPLATE_AND_SOURCE_FOLDERS.put(Paths.get("D:\\eclipse\\workspace\\photonR\\Omni-Impl\\templates"),
          Paths.get("D:\\eclipse\\workspace\\photonR\\Omni-Impl\\src-generated"));
    }
  }
  public static void main(String[] args) throws Exception{
    TEMPLATE_AND_SOURCE_FOLDERS.forEach(Main::processTemplates);
  }
  private static void processTemplates(Path templateFolder,Path outputFolder){
    try{
      Files.walkFileTree(templateFolder,new SimpleFileVisitor<>(){
        public FileVisitResult visitFile(Path file,BasicFileAttributes attributes){
          final String fileName=file.getFileName().toString();
          if(fileName.endsWith(".template")){
            try{
              final String fqt=fileName.substring(0,fileName.lastIndexOf(".template"));
              final var fqtMatcher=FQT_PATTERN.matcher(fqt);
              if(!fqtMatcher.matches()){
                System.err.println("The fully-qualified-type \""+fqt+"\" is not valid");
              }else{
                System.out.println(fqt);
                final TemplateProcessor processor=new TemplateProcessor(Files.readAllLines(file));
                final Map<String,List<String>> sources=processor.getSources();
                final Map<String,TypeDefinition> typeDefs=processor.getTypeDefs();
                typeDefs.forEach((typeDefName,typeDef)->{
                  final List<String> sourceCode=sources.get(typeDefName);
                  final var sourceFilePath=outputFolder.resolve(
                      typeDef.parseLine(fqt).replaceAll("\\.",Matcher.quoteReplacement(File.separator))+".java");
                  if(true){
                    try{
                      Files.createDirectories(sourceFilePath.getParent());
                      Files.write(sourceFilePath,sourceCode);
                    }catch(final IOException e){
                      e.printStackTrace();
                    }
                  }else{
                    System.out.println("writing type "+typeDefName+" to "+sourceFilePath);
                    // for(final String line:sourceCode){
                    // System.out.println(" "+line);
                    // }
                  }
                  for(final var defVar:typeDef.definitionVars){
                    if(defVar.getNumUses()==0){
                      System.out.println("WARNING: for Type Definition "+typeDefName+" "+defVar);
                    }
                  }
                });
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
}
