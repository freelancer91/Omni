package omni.codegen;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;
class TemplateProcessor{
    private static final Pattern TAG_PATTERN=Pattern.compile("^#(\\w+)");
    private static final String DELIMITER_PATTERN="(?<!\\\\)"+Pattern.quote(",");
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
    TemplateProcessor(List<String> lines) throws Exception{
        this.lines=lines;
        typeDefs=new HashMap<>();
        macroDefs=new HashMap<>();
        sourceLines=new HashMap<>();
        extractDefs();
        var defPairItr=typeDefs.entrySet().iterator();
        if(defPairItr.hasNext()){
            validate=true;
            var pair=defPairItr.next();
            String defName=pair.getKey();
            sourceLines.put(defName,getSource(defName,pair.getValue()));
            validate=false;
            while(defPairItr.hasNext()){
                pair=defPairItr.next();
                defName=pair.getKey();
                sourceLines.put(defName,getSource(defName,pair.getValue()));
            }
        }
    }
    private List<String> getSource(String typeDefName,TypeDefinition typeDef) throws Exception{
        var output=new ArrayList<String>();
        depth=0;
        activeDepth=0;
        actualIndex=0;
        skipToEndIf=false;
        macroDepth=0;
        final var itr=lines.listIterator();
        while(itr.hasNext()){
            String line=itr.next();
            ++actualIndex;
            try{
                processSourceLine(typeDefName,typeDef,output,itr,line);
            }catch(Exception e){
                System.err.println("Exception caught and rethrown at line "+actualIndex+" for TypeDef "+typeDefName);
                throw e;
            }
        }
        if(validate){
            if(depth!=0){
                throw new Exception("Mismatched IF/ELSE structure : depth = "+depth);
            }
            if(macroDepth!=0){
                throw new Exception("macroDepth = "+macroDepth+". Should be 0.");
            }
        }
        return output;
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
                        if(definitionName
                                .isBlank()){ throw new Exception("No definition name found for definition at line "+itr.nextIndex()); }
                        typeDefs.put(definitionName,new TypeDefinition(itr));
                        break;
                    case "#MACRODEF":
                        final int paramBegin=trimmedLine.indexOf('(',tagEnd);
                        final int paramEnd=trimmedLine.lastIndexOf(')');
                        if(paramBegin<0||paramEnd<0){ throw new Exception(
                                "No parameter clause found for macro definition at line "+itr.nextIndex()); }
                        final String macroName=trimmedLine.substring(tagEnd,paramBegin).trim();
                        if(macroName
                                .isBlank()){ throw new Exception("No macro name found for macro definition at line "+itr.nextIndex()); }
                        macroDefs.put(macroName,new MacroDefinition(itr,
                                extractAndTrimArgs(trimmedLine.substring(paramBegin+1,paramEnd))));
                    default:
                    }
                }
            }
        }
    }

    public Map<String,MacroDefinition> getMacroDefs(){
        return this.macroDefs;
    }
    public List<String> getRawTemplateLines(){
        return this.lines;
    }
    public Map<String,TypeDefinition> getTypeDefs(){
        return this.typeDefs;
    }
    public Map<String,List<String>> getSources(){
        return this.sourceLines;
    }
    private void skipDefinition(ListIterator<String> itr) throws Exception{
        while(itr.hasNext()){
            String line=itr.next().trim();
            if(macroDepth==0){
                ++actualIndex;
            }
            if(line.startsWith("#ENDDEF")){
                return;
            }
        }
        throw new EOFException("Unexpected end to file at index "+actualIndex);
    }
    private static boolean matchTypeDef(String typeDefName,String[] args){
        int i=0;
        int numArgs=args.length;
        do{
            if(typeDefName.equals(args[i])){
                return true;
            }
        }while(++i!=numArgs);
        return false;
    }
    private static void trimArgs(String[] args){
        for(int i=0,bound=args.length;i!=bound;++i){
            args[i]=args[i].trim();
        }
    }
    private static String[] extractAndTrimArgs(String input){
        String[] args=input.split(DELIMITER_PATTERN);
        trimArgs(args);
        return args;
    }
    //    private static String[] extractArguments(String input){
    //        String[] args=input.split(DELIMITER_PATTERN);
    //
    //        return args;
    //    }
    private void validateControlStatementArgs(String[] args) throws Exception{
        int numArgs;
        if((numArgs=args.length)==0){
            throw new Exception("No arguments found for control statement on line "+actualIndex);
        }
        int i=0;
        do{
            String arg=args[i];
            if(!typeDefs.containsKey(arg)){
                throw new Exception("Unknown type definition mentioned on line "+actualIndex);
            }
        }while(++i!=numArgs);
    }
    private void processIfNot(String typeDefName,String lineTail) throws Exception{
        if(activeDepth==depth){
            String[] args=extractAndTrimArgs(lineTail);
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
    private void processIf(String typeDefName,String lineTail) throws Exception{
        if(activeDepth==depth){
            String[] args=extractAndTrimArgs(lineTail);
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
    private void processElseIf(String typeDefName,String lineTail) throws Exception{
        switch(depth-activeDepth){
        case 1:
            if(!skipToEndIf){
                String[] args=extractAndTrimArgs(lineTail);
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
                String[] args=extractAndTrimArgs(lineTail);
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
    private void processElse(){
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
        }
    }
    private void processEndIf(){
        switch(depth-activeDepth){
        case 0:
            --activeDepth;
        case 1:
            skipToEndIf=false;
        default:
        }
        --depth;
    }
    private void processMacro(String lineTail,ListIterator<String> itr,String typeDefName,TypeDefinition typeDef,
            List<String> output) throws Exception{
        final int paramBegin=lineTail.indexOf('(');
        final int paramEnd=lineTail.lastIndexOf(')');
        if(validate){
            if(paramBegin<0||paramEnd<0){
                throw new Exception("No parameter clause found for macro reference on line "+itr.nextIndex());
            }
        }
        final String macroName=lineTail.substring(0,paramBegin).trim();
        if(validate){
            if(macroName.isBlank()){
                throw new Exception("No macro name found for macro reference on line "+actualIndex);
            }
        }
        final MacroDefinition macro=macroDefs.get(macroName);
        if(validate){
            if(macro==null){
                System.out.println(macroDefs);
                throw new Exception("Undefined macro reference found on line "+actualIndex);
            }
        }
        String[] parameters=lineTail.substring(paramBegin+1,paramEnd).split(DELIMITER_PATTERN);
        if(validate){
            if(parameters.length!=macro.parameters.length){
                throw new Exception("Incorrect number of parameters for macro reference on line "+actualIndex);
            }
        }
        for(String sourceLine:macro.source){
            for(int i=0;i!=parameters.length;++i){
                sourceLine=sourceLine.replaceAll(macro.parameters[i],parameters[i]);
            }
            processSourceLine(typeDefName,typeDef,output,itr,sourceLine);
        }
    }
    private void processSourceLine(String typeDefName,TypeDefinition typeDef,List<String> output,
            final ListIterator<String> itr,String line) throws Exception{
        if(!line.isBlank()){
            String trimmed=line.trim();
            final var tagMatcher=TAG_PATTERN.matcher(trimmed);
            if(tagMatcher.find()){
                final String tag=tagMatcher.group();
                switch(tag){
                case "#TYPEDEF":
                case "#MACRODEF":
                    skipDefinition(itr);
                    break;
                case "#MACRO":
                    ++macroDepth;
                    processMacro(trimmed.substring(tagMatcher.end()).trim(),itr,typeDefName,typeDef,output);
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
                            throw new Exception("Unexpected content on line "+actualIndex);
                        }
                    }
                    processElse();
                    break;
                case "#ENDIF":
                    if(validate){
                        if(!trimmed.substring(tagMatcher.end()).trim().isEmpty()){
                            throw new Exception("Unexpected content on line "+actualIndex);
                        }
                    }
                    processEndIf();
                    break;
                default:
                    throw new Exception("Unexpected tag "+tag+" on line "+actualIndex);
                }
            }else{
                if(activeDepth==depth){
                    line=typeDef.parseLine(line);
                    output.add(line);
                }
            }
        }
    }

}
