package omni.codegen;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
class TemplateFile{
  private final Set<Definition> definitions;
  private final String fqtString;
  private final String[] sourceCodeLines;
  private void validateFields() throws InvalidTemplateFileException{
    if(definitions
        .isEmpty()){ throw new InvalidTemplateFileException("No definitions were found in the definition file"); }
    if(fqtString.isEmpty()){ throw new InvalidTemplateFileException(
        "No fully-qualified type string was found in the definition file"); }
    if(sourceCodeLines.length==0){ throw new InvalidTemplateFileException(
        "No source code was found in the definition file"); }
    final var itr=definitions.iterator();
    final var firstDef=itr.next();
    final var defKeySet=firstDef.getMap().keySet();
    for(var currDef=firstDef;;){
      if(currDef.getName().isEmpty()){ throw new InvalidTemplateFileException("The definition name was empty"); }
      if(!itr.hasNext()){
        break;
      }
      currDef=itr.next();
      if(!currDef.getMap().keySet().equals(defKeySet)){ throw new InvalidTemplateFileException(
          "The definition set for definition "+currDef.getName()+" was mismatched against the other definition sets"); }
    }
  }
  private TemplateFile(Set<Definition> definitions,String fqtString,String[] sourceCodeLines)
      throws InvalidTemplateFileException{
    this.definitions=Objects.requireNonNull(definitions);
    this.fqtString=Objects.requireNonNull(fqtString);
    this.sourceCodeLines=Objects.requireNonNull(sourceCodeLines);
    validateFields();
  }
  static TemplateFile getTemplateFile(InputStream source) throws IOException,InvalidTemplateFileException{
    final var definitions=new HashSet<Definition>();
    String fqtString=null;
    final var sourceCodeLines=new ArrayList<String>();
    final var reader=new BufferedReader(new InputStreamReader(source));
    for(;;){
      var line=reader.readLine();
      if(line==null){
        break;
      }
      line=line.trim();
      if(!line.isEmpty()){
        final int spaceIndex=line.indexOf(' ');
        if(spaceIndex<0){
          if(!line.equals("#SOURCE")){ throw new InvalidTemplateFileException("The line "+line+" was unexpected."); }
          processSourceCodeBlock(sourceCodeLines,reader);
        }else{
          final var prefix=line.substring(0,spaceIndex);
          switch(prefix){
          case "#define":{
            processDefinitionBlock(definitions,reader,line,spaceIndex);
            break;
          }
          case "#FQT":{
            fqtString=line.substring(spaceIndex+1).trim();
            break;
          }
          default:{
            throw new InvalidTemplateFileException("The prefix "+prefix+" is unknown");
          }
          }
        }
      }
    }
    if(definitions.isEmpty()||fqtString==null||sourceCodeLines.isEmpty()){ return null; }
    return new TemplateFile(definitions,fqtString,sourceCodeLines.toArray(new String[sourceCodeLines.size()]));
  }
  private static void processDefinitionBlock(HashSet<Definition> definitions,BufferedReader reader,String line,
      int spaceIndex) throws IOException,EOFException,InvalidTemplateFileException{
    final var definitionName=line.substring(spaceIndex+1).trim();
    final var definitionPairs=new HashMap<String,String>();
    for(;;){
      line=reader.readLine();
      if(line==null){ throw new EOFException(); }
      line=line.trim();
      if(line.equals("#END")){
        break;
      }
      final int delimiterIndex=line.indexOf('=');
      if(delimiterIndex<0){ throw new InvalidTemplateFileException("Expected value for definition "+line); }
      final var key=line.substring(0,delimiterIndex).trim();
      final var value=line.substring(delimiterIndex+1).trim();
      if(key.isEmpty()
          ||value.isEmpty()){ throw new InvalidTemplateFileException("The key or value was empty on the line "+line); }
      definitionPairs.put(key,value);
    }
    definitions.add(new Definition(definitionName,definitionPairs));
  }
  private static void processSourceCodeBlock(ArrayList<String> sourceCodeLines,BufferedReader reader)
      throws IOException,EOFException{
    for(;;){
      final String line=reader.readLine();
      if(line==null){ throw new EOFException(); }
      if(line.equals("#END")){
        break;
      }
      sourceCodeLines.add(line);
    }
  }
  static TemplateFile getTemplateFile(Path source) throws IOException,InvalidTemplateFileException{
    return getTemplateFile(Files.newInputStream(source));
  }
  public void createFiles(String srcFolder) throws IOException{
    for(final Definition def:definitions){
      final var outputLines=new ArrayList<String>(sourceCodeLines.length);
      for(final var sourceLine:sourceCodeLines){
        final var outputLine=def.parseLine(sourceLine);
        outputLines.add(outputLine);
      }
      final var outputPath=def.resolvePath(srcFolder,fqtString);
      Files.createDirectories(outputPath.getParent());
      Files.write(outputPath,outputLines);
    }
  }
}
