package omni.codegen;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
class MacroDefinition{
  final String[] parameters;
  final List<String> source;
  private int numUses;
  MacroDefinition(ListIterator<String> itr,String...parameters) throws Exception{
    this.parameters=parameters;
    source=new ArrayList<>();
    while(itr.hasNext()){
      final String line=itr.next();
      final String trimmed=line.trim();
      final var tagMatcher=TemplateProcessor.TAG_PATTERN.matcher(trimmed);
      if(tagMatcher.find()){
        final String tag=tagMatcher.group();
        switch(tag){
        case "#ENDDEF":
          return;
        case "#MACRODEF":
          throw new Exception("Macro definition found inside macro definition on line "+itr.nextIndex());
        default:
        }
      }
      source.add(line);
    }
    throw new Exception("Incomplete macro at line "+itr.nextIndex());
  }
  public int getNumUses(){
    return numUses;
  }
  public void incrementUses(){
    ++numUses;
  }
  public void resetNumUses(){
    numUses=0;
  }
  void resolveMacro(ListIterator<String> output,String...parameters) throws Exception{
    ++numUses;
    for(String sourceLine:source){
      for(int i=0;i!=parameters.length;++i){
        sourceLine=sourceLine.replaceAll(this.parameters[i],parameters[i]);
      }
      output.add(sourceLine);
    }
  }
}
