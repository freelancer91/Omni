package omni.codegen;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class TemplateProcessor{
  private final List<String> lines;
  private final Map<String,TypeDefinition> typeDefs;
  private final Map<String,MacroDefinition> macroDefs;
  private final Map<String,List<String>> sourceLines;
  private int depth;
  private int activeDepth;
  private int actualIndex;
  private boolean skipToEndIf;
  private boolean validate;
  private int macroDepth;
  private int indent;
  public boolean trouble;
  static final Pattern TAG_PATTERN=Pattern.compile("^#(\\w+)");
  private static final String DELIMITER_PATTERN="(?<!\\\\)"+Pattern.quote(",");
  private static String[] extractAndTrimArgs(String input){
    final String[] args=input.split(DELIMITER_PATTERN);
    trimArgs(args);
    return args;
  }
  private static boolean matchTypeDef(String typeDefName,String[] args){
    int i=0;
    final int numArgs=args.length;
    do{
      if(typeDefName.equals(args[i])){ return true; }
    }while(++i!=numArgs);
    return false;
  }
  private static boolean postValidateSources(String defName,List<String> source){
    boolean trouble=false;
    int lineIndex=1;
    for(final String line:source){
      if(line.indexOf('#')>=0||line.indexOf('$')>=0){
        System.out
            .println("WARNING: output line "+lineIndex+" for definition "+defName+" may contain illegal characters");
        trouble=true;
      }
      ++lineIndex;
    }
    return trouble;
  }
  private static void trimArgs(String[] args){
    for(int i=0,bound=args.length;i!=bound;++i){
      args[i]=args[i].trim();
    }
  }
  TemplateProcessor(List<String> lines) throws Exception{
    this.lines=lines;
    typeDefs=new HashMap<>();
    macroDefs=new HashMap<>();
    sourceLines=new HashMap<>();
    extractDefs();
    final var defPairItr=typeDefs.entrySet().iterator();
    if(defPairItr.hasNext()){
      validate=true;
      var pair=defPairItr.next();
      String defName=pair.getKey();
      var source=getSource(defName,pair.getValue());
      trouble|=postValidateSources(defName,source);
      sourceLines.put(defName,source);
      validate=false;
      while(defPairItr.hasNext()){
        pair=defPairItr.next();
        defName=pair.getKey();
        source=getSource(defName,pair.getValue());
        trouble|=postValidateSources(defName,source);
        sourceLines.put(defName,source);
      }
    }
  }
  public Map<String,MacroDefinition> getMacroDefs(){
    return macroDefs;
  }
  public List<String> getRawTemplateLines(){
    return lines;
  }
  public Map<String,List<String>> getSources(){
    return sourceLines;
  }
  public Map<String,TypeDefinition> getTypeDefs(){
    return typeDefs;
  }
  private void extractDefs() throws Exception{
    // TODO allow for type definitions to be defined one variable at a time
    final var itr=lines.listIterator();
    while(itr.hasNext()){
      final String line=itr.next();
      if(!line.isBlank()){
        final String trimmedLine=line.trim();
        final var tagMatcher=TAG_PATTERN.matcher(trimmedLine);
        if(tagMatcher.find()){
          final String tag=tagMatcher.group();
          final int tagEnd=tagMatcher.end();
          switch(tag){
          case "#TYPEDEF":
            final String definitionName=trimmedLine.substring(tagEnd).trim();
            if(definitionName.isBlank()){
              trouble=true;
              throw new Exception("No definition name found for definition at line "+itr.nextIndex());
            }
            typeDefs.put(definitionName,TypeDefinition.valueOf(definitionName));
            break;
          case "#MACRODEF":
            final int paramBegin=trimmedLine.indexOf('(',tagEnd);
            final int paramEnd=trimmedLine.lastIndexOf(')');
            if(paramBegin<0||paramEnd<0){
              trouble=true;
              throw new Exception("No parameter clause found for macro definition at line "+itr.nextIndex());
            }
            final String macroName=trimmedLine.substring(tagEnd,paramBegin).trim();
            if(macroName.isBlank()){
              trouble=true;
              throw new Exception("No macro name found for macro definition at line "+itr.nextIndex());
            }
            final String paramStr=trimmedLine.substring(paramBegin+1,paramEnd);
            String[] params;
            if(paramStr.isEmpty()){
              params=new String[]{};
            }else{
              params=extractAndTrimArgs(paramStr);
            }
            macroDefs.put(macroName,new MacroDefinition(itr,params));
          default:
          }
        }
      }
    }
  }
  private List<String> getSource(String typeDefName,TypeDefinition typeDef) throws Exception{
    final var output=new ArrayList<String>();
    depth=0;
    activeDepth=0;
    actualIndex=0;
    skipToEndIf=false;
    macroDepth=0;
    final var itr=lines.listIterator();
    while(itr.hasNext()){
      final String line=itr.next();
      ++actualIndex;
      try{
        processSourceLine(typeDefName,typeDef,output,itr,line);
      }catch(final Exception e){
        trouble=true;
        System.err.println("Exception caught and rethrown at line "+actualIndex+" for TypeDef "+typeDefName);
        throw e;
      }
    }
    if(validate){
      macroDefs.forEach((macroName,macro)->{
        final int numUses=macro.getNumUses();
        if(numUses<2){
          trouble=true;
          System.out.println("WARNING: Macro "+macroName+" num uses = "+numUses);
        }
      });
      if(depth!=0){
        trouble=true;
        throw new Exception("Mismatched IF/ELSE structure : depth = "+depth);
      }
      if(macroDepth!=0){
        trouble=true;
        throw new Exception("macroDepth = "+macroDepth+". Should be 0.");
      }
    }
    return output;
  }
  private void processElse() throws Exception{
    switch(depth-activeDepth){
    case 1:
      if(!skipToEndIf){
        ++activeDepth;
      }
      return;
    case 0:
      skipToEndIf=true;
      --activeDepth;
    default:
      if(activeDepth<0){
        trouble=true;
        throw new Exception("Mismatched IF/ELSE structure detected on line "+actualIndex);
      }
    }
  }
  private void processElseIf(String typeDefName,String lineTail) throws Exception{
    switch(depth-activeDepth){
    case 1:
      if(!skipToEndIf){
        final String[] args=extractAndTrimArgs(lineTail);
        if(validate){
          validateControlStatementArgs(args);
        }
        if(matchTypeDef(typeDefName,args)){
          ++activeDepth;
        }
      }
      return;
    case 0:
      skipToEndIf=true;
      --activeDepth;
    default:
      if(validate){
        validateControlStatementArgs(extractAndTrimArgs(lineTail));
      }
    }
  }
  private void processElseIfNot(String typeDefName,String lineTail) throws Exception{
    switch(depth-activeDepth){
    case 1:
      if(!skipToEndIf){
        final String[] args=extractAndTrimArgs(lineTail);
        if(validate){
          validateControlStatementArgs(args);
        }
        if(!matchTypeDef(typeDefName,args)){
          ++activeDepth;
        }
      }
      return;
    case 0:
      skipToEndIf=true;
      --activeDepth;
    default:
      if(validate){
        validateControlStatementArgs(extractAndTrimArgs(lineTail));
      }
    }
  }
  private void processEndIf() throws Exception{
    switch(depth-activeDepth){
    case 0:
      --activeDepth;
    case 1:
      skipToEndIf=false;
    default:
    }
    --depth;
    if(activeDepth<0||depth<0){
      trouble=true;
      throw new Exception("Mismatched IF/ELSE structure detected on line "+actualIndex);
    }
  }
  private void processIf(String typeDefName,String lineTail) throws Exception{
    if(activeDepth==depth){
      final String[] args=extractAndTrimArgs(lineTail);
      if(validate){
        validateControlStatementArgs(args);
      }
      if(matchTypeDef(typeDefName,args)){
        ++activeDepth;
      }
    }else if(validate){
      validateControlStatementArgs(extractAndTrimArgs(lineTail));
    }
    ++depth;
  }
  private void processIfNot(String typeDefName,String lineTail) throws Exception{
    if(activeDepth==depth){
      final String[] args=extractAndTrimArgs(lineTail);
      if(validate){
        validateControlStatementArgs(args);
      }
      if(!matchTypeDef(typeDefName,args)){
        ++activeDepth;
      }
    }else if(validate){
      validateControlStatementArgs(extractAndTrimArgs(lineTail));
    }
    ++depth;
  }
  private void processMacro(String lineTail,ListIterator<String> itr,String typeDefName,TypeDefinition typeDef,
      List<String> output) throws Exception{
    final int paramBegin=lineTail.indexOf('(');
    final int paramEnd=lineTail.lastIndexOf(')');
    if(validate){
      if(paramBegin<0||paramEnd<0){
        trouble=true;
        throw new Exception("No parameter clause found for macro reference on line "+itr.nextIndex());
      }
    }
    final String macroName=lineTail.substring(0,paramBegin).trim();
    if(validate){
      if(macroName.isBlank()){
        trouble=true;
        throw new Exception("No macro name found for macro reference on line "+actualIndex);
      }
    }
    final MacroDefinition macro=macroDefs.get(macroName);
    if(validate){
      if(macro==null){
        System.out.println(macroDefs);
        trouble=true;
        throw new Exception("Undefined macro reference \""+macroName+"\" found on line "+actualIndex);
      }
    }
    final String paramStr=lineTail.substring(paramBegin+1,paramEnd);
    String[] parameters;
    if(paramStr.isEmpty()){
      parameters=new String[]{};
    }else{
      parameters=paramStr.split(DELIMITER_PATTERN,-1);
    }
    for(int i=0;i<parameters.length;++i){
      parameters[i]=parameters[i].replaceAll("\\$",Matcher.quoteReplacement("\\$"));
    }
    if(validate){
      macro.incrementUses();
      if(parameters.length!=macro.parameters.length){
        System.err.println("parameters.length = "+parameters.length);
        System.err.println("paramStr = ("+paramStr+")");
        for(int i=0;i!=parameters.length;++i){
          System.err.println("  param "+i+" = \""+parameters[i]+"\"");
        }
        trouble=true;
        throw new Exception("Incorrect number of parameters for macro reference "+macroName+" on line "+actualIndex);
      }
    }
    for(String sourceLine:macro.source){
      for(int i=0;i!=parameters.length;++i){
        sourceLine=sourceLine.replaceAll(macro.parameters[i],parameters[i]);
      }
      sourceLine=" ".repeat(indent)+sourceLine;
      processSourceLine(typeDefName,typeDef,output,itr,sourceLine);
    }
  }
  private void processSourceLine(String typeDefName,TypeDefinition typeDef,List<String> output,
      final ListIterator<String> itr,String line) throws Exception{
    if(!line.isBlank()){
      final String trimmed=line.trim();
      final var tagMatcher=TAG_PATTERN.matcher(trimmed);
      if(tagMatcher.find()){
        final String tag=tagMatcher.group();
        switch(tag){
        case "#TYPEDEF":
          break;
        case "#MACRODEF":
          skipDefinition(itr);
          break;
        case "#MACRO":
          ++macroDepth;
          final int lineIndent=line.indexOf("#MACRO");
          if(macroDepth==1){
            indent+=lineIndent;
          }
          processMacro(trimmed.substring(tagMatcher.end()).trim(),itr,typeDefName,typeDef,output);
          if(macroDepth==1){
            indent-=lineIndent;
          }
          --macroDepth;
          break;
        case "#IF":
          processIf(typeDefName,trimmed.substring(tagMatcher.end()).trim());
          break;
        case "#IFNOT":
          processIfNot(typeDefName,trimmed.substring(tagMatcher.end()).trim());
          break;
        case "#ELSEIF":
          processElseIf(typeDefName,trimmed.substring(tagMatcher.end()).trim());
          break;
        case "#ELSEIFNOT":
          processElseIfNot(typeDefName,trimmed.substring(tagMatcher.end()).trim());
          break;
        case "#ELSE":
          if(validate){
            if(!trimmed.substring(tagMatcher.end()).trim().isEmpty()){
              trouble=true;
              throw new Exception("Unexpected content on line "+actualIndex);
            }
          }
          processElse();
          break;
        case "#ENDIF":
          if(validate){
            if(!trimmed.substring(tagMatcher.end()).trim().isEmpty()){
              trouble=true;
              throw new Exception("Unexpected content on line "+actualIndex);
            }
          }
          processEndIf();
          break;
        default:
          trouble=true;
          throw new Exception("Unexpected tag "+tag+" on line "+actualIndex);
        }
      }else{
        if(activeDepth==depth){
          line=typeDef.parseLine(line);
          // if(macroDepth==0){
          // if(line.indexOf('$')>=0||line.indexOf('#')>=0){
          // System.err.println("WARNING: on "+typeDefName+" -> possible illegal character on line "+actualIndex);
          // }
          // }
          output.add(line);
        }
      }
    }
  }
  private void skipDefinition(ListIterator<String> itr) throws Exception{
    while(itr.hasNext()){
      final String line=itr.next().trim();
      if(macroDepth==0){
        ++actualIndex;
      }
      if(line.startsWith("#ENDDEF")){ return; }
    }
    trouble=true;
    throw new EOFException("Unexpected end to file at index "+actualIndex);
  }
  private void validateControlStatementArgs(String[] args) throws Exception{
    if(activeDepth<0||depth<0){
      trouble=true;
      throw new Exception("Mismatched IF/ELSE structure detected on line "+actualIndex);
    }
    int numArgs;
    if((numArgs=args.length)==0){
      trouble=true;
      throw new Exception("No arguments found for control statement on line "+actualIndex);
    }
    int i=0;
    do{
      final String arg=args[i];
      if(!typeDefs.containsKey(arg)){
        trouble=true;
        throw new Exception("Unknown type definition "+arg+" mentioned on line "+actualIndex);
      }
    }while(++i!=numArgs);
  }
}
