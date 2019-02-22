package omni.codegen;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TemplateProcessor2{
    private static final Pattern TAG_PATTERN=Pattern.compile("^#(\\w+)");
    private static final String DELIMITER_PATTERN="(?<!\\\\)"+Pattern.quote(",");
    boolean trouble;
    private final String[] lines;
    private int currIndex;
    final Map<TypeDefinition,List<String>> sources;
    final Map<TypeDefinition,Integer> typeDefs;
    private final Map<MacroSignature,MacroDefinition2> macroDefs;
    private void extractTypeDef(String trimmedLine,int tagEnd) throws Exception{
        final String defName=trimmedLine.substring(tagEnd).trim();
        if(defName.isBlank()){
            trouble=true;
            throw new Exception("No definition name found for definition at line "+currIndex);
        }
        try{
            final Integer existingIndex=typeDefs.put(TypeDefinition.valueOf(defName),currIndex);
            if(existingIndex!=null){
                trouble=true;
                throw new Exception("The typedef "+defName+" found on line "+currIndex
                        +" is a duplicate of the definition found on line "+existingIndex);
            }
        }catch(final IllegalArgumentException e){
            trouble=true;
            throw new Exception("Unknown type definition "+defName+" found on line "+currIndex);
        }
    }
    private static void trimArgs(String[] args){
        for(int i=0,bound=args.length;i!=bound;++i){
            args[i]=args[i].trim();
        }
    }
    private static String[] extractAndTrimArgs(String input){
        final String[] args=input.split(DELIMITER_PATTERN);
        trimArgs(args);
        return args;
    }
    private void extractMacroDef(String trimmedLine,int tagEnd) throws Exception{
        final int firstLine=++currIndex;
        final int paramBegin=trimmedLine.indexOf('(',tagEnd);
        final int paramEnd=trimmedLine.lastIndexOf(')');
        if(paramBegin<0||paramEnd<0){
            trouble=true;
            throw new Exception("No parameter clause found for macro definition at line "+firstLine);
        }
        final String paramStr=trimmedLine.substring(paramBegin+1,paramEnd);
        String[] params;
        if(paramStr.isEmpty()){
            params=new String[]{};
        }else{
            params=extractAndTrimArgs(paramStr);
        }
        String[] switches;
        int switchBegin=trimmedLine.indexOf('<',tagEnd);
        if(switchBegin>=0){
            final int switchEnd=trimmedLine.lastIndexOf('>',paramBegin);
            final String switchStr=trimmedLine.substring(switchBegin+1,switchEnd);
            if(switchStr.isEmpty()){
                switches=new String[]{};
            }else{
                switches=extractAndTrimArgs(switchStr);
            }
        }else{
            switchBegin=paramBegin;
            switches=new String[]{};
        }
        final String macroName=trimmedLine.substring(tagEnd,switchBegin).trim();
        if(macroName.isBlank()){
            trouble=true;
            throw new Exception("No macro name found for macro definition at line "+firstLine);
        }
        final var lines=this.lines;
        for(int i=firstLine,bound=lines.length;;++i){
            if(i==bound){
                trouble=true;
                throw new Exception("Incomplete macro "+macroName+" at line "+firstLine);
            }
            trimmedLine=lines[i].trim();
            final var tagMatcher=TAG_PATTERN.matcher(trimmedLine);
            if(tagMatcher.find()){
                final String tag=tagMatcher.group();
                switch(tag){
                case "#ENDDEF":
                    final MacroSignature macroSig=new MacroSignature(macroName,switches.length,params.length);
                    final MacroDefinition2 macroDef=new MacroDefinition2(firstLine,switches,params);
                    final MacroDefinition2 existingDef=macroDefs.put(macroSig,macroDef);
                    if(existingDef!=null){
                        trouble=true;
                        throw new Exception("Macro definition "+macroName+" found at line "+firstLine
                                +" is a duplicate of another macro definition found at line "+existingDef.lineNumber);
                    }
                    currIndex=i;
                    return;
                case "#MACRODEF":
                    trouble=true;
                    throw new Exception(
                            "Macro definition found at line "+i+" is inside another macro definition which began at line "+firstLine);
                default:
                }
            }
        }
    }
    private void extractDefinitions() throws Exception{
        String[] lines;
        currIndex=0;
        for(final int bound=(lines=this.lines).length;currIndex!=bound;++currIndex){
            String line=lines[currIndex];
            if(!line.isBlank()){
                line=line.trim();
                final var tagMatcher=TAG_PATTERN.matcher(line);
                if(tagMatcher.find()){
                    switch(tagMatcher.group()){
                    case "#TYPEDEF":
                        extractTypeDef(line,tagMatcher.end());
                        break;
                    case "#MACRODEF":
                        extractMacroDef(line,tagMatcher.end());
                        break;
                    default:
                    }
                }
            }
        }
    }
    private static class StackElement{
        int lineNumber;
        String line;
        StackElement(int lineNumber,String line){
            this.lineNumber=lineNumber;
            this.line=line;
        }
    }
    private class SourceGenerator{
        final boolean validate;
        final List<StackElement> ifElseStack;
        int activeDepth;
        boolean skipToEndIf;
        final List<StackElement> macroStack;
        final TypeDefinition typeDef;
        int currIndent;
        final List<String> output;
        final Map<String,String> currentSwitches;
        private void validateControlStatementArgs(String[] args) throws Exception{
            if(activeDepth<0){
                trouble=true;
                throw new Exception("Mismatched IF/ELSE structure detected on line "+currIndex);
            }
            int numArgs;
            if((numArgs=args.length)==0){
                trouble=true;
                throw new Exception("No arguments found for control statement on line "+currIndex);
            }
            int i=0;
            do{
                final String arg=args[i];
                try{
                    if(typeDefs.containsKey(TypeDefinition.valueOf(arg))){
                        continue;
                    }
                }catch(final IllegalArgumentException e){
                    // do nothing here
                }
                trouble=true;
                throw new Exception("Unknown type definition "+arg+" mentioned on line "+currIndex);
            }while(++i!=numArgs);
        }
        private void processIf(String trimmedLine,int tagEnd,boolean matchArg,boolean isSwitch) throws Exception{
            if(activeDepth==ifElseStack.size()){
                final String[] args=extractAndTrimArgs(trimmedLine.substring(tagEnd).trim());
                if(isSwitch){
                    if(validate){
                        // TODO
                    }
                    final String firstToken=args[0];
                    final int delimiterIndex=firstToken.indexOf("==");
                    if(delimiterIndex<0){
                        trouble=true;
                        throw new Exception("Invalid IFSWITCH statement at line "+currIndex);
                    }
                    final String key=firstToken.substring(0,delimiterIndex).trim();
                    String value=firstToken.substring(delimiterIndex+2).trim();
                    final String definedValue=currentSwitches.get(key);
                    if(definedValue==null){
                        trouble=true;
                        throw new Exception("Unknown If-switch parameter "+key+" on line "+currIndex);
                    }
                    if(matchArg){
                        for(int i=1;;){
                            if(definedValue.equals(value)){
                                ++activeDepth;
                                break;
                            }
                            if(i==args.length){
                                break;
                            }
                            value=args[i++].trim();
                        }
                    }else{
                        for(int i=1;;){
                            if(definedValue.equals(value)){
                                break;
                            }
                            if(i==args.length){
                                ++activeDepth;
                                break;
                            }
                            value=args[i++].trim();
                        }
                    }

                }else{
                    if(validate){
                        validateControlStatementArgs(args);
                    }
                    if(matchArg==typeDef.matchTypeDef(args)){
                        ++activeDepth;
                    }
                }
            }else if(validate){
                if(isSwitch){
                    // TODO
                }else{
                    validateControlStatementArgs(extractAndTrimArgs(trimmedLine.substring(tagEnd).trim()));
                }
            }
            ifElseStack.add(new StackElement(currIndex,trimmedLine));
        }
        private void processElseIf(String trimmedLine,int tagEnd,boolean matchArg,boolean isSwitch) throws Exception{
            int depth;
            switch((depth=ifElseStack.size())-activeDepth){
            case 1:
                if(!skipToEndIf){
                    final String[] args=extractAndTrimArgs(trimmedLine.substring(tagEnd).trim());
                    if(isSwitch){
                        if(validate){
                            // TODO
                        }
                        final String firstToken=args[0];
                        final int delimiterIndex=firstToken.indexOf("==");
                        if(delimiterIndex<0){
                            trouble=true;
                            throw new Exception("Invalid IFSWITCH statement at line "+currIndex);
                        }
                        final String key=firstToken.substring(0,delimiterIndex).trim();
                        String value=firstToken.substring(delimiterIndex+2).trim();
                        final String definedValue=currentSwitches.get(key);
                        if(definedValue==null){
                            trouble=true;
                            throw new Exception("Unknown IFSWITCH parameter "+key+" on line "+currIndex);
                        }
                        for(int i=1;;){
                            if(matchArg==definedValue.equals(value)){
                                ++activeDepth;
                                break;
                            }
                            if(i==args.length){
                                break;
                            }
                            value=args[i++].trim();
                        }
                    }else{
                        if(validate){
                            validateControlStatementArgs(args);
                        }
                        if(matchArg==typeDef.matchTypeDef(args)){
                            ++activeDepth;
                        }
                    }
                }
                break;
            case 0:
                skipToEndIf=true;
                --activeDepth;
            default:
                if(validate){
                    if(isSwitch){
                        // TODO
                    }else{
                        validateControlStatementArgs(extractAndTrimArgs(trimmedLine.substring(tagEnd).trim()));
                    }
                }
            }
            ifElseStack.set(depth-1,new StackElement(currIndex,trimmedLine));
        }
        private void processElse(String trimmedLine,int tagEnd) throws Exception{
            if(validate){
                if(!trimmedLine.substring(tagEnd).trim().isEmpty()){
                    trouble=true;
                    throw new Exception("Unexpected content on line "+currIndex);
                }
            }
            int depth;
            switch((depth=ifElseStack.size())-activeDepth){
            case 1:
                if(!skipToEndIf){
                    ++activeDepth;
                }
                break;
            case 0:
                skipToEndIf=true;
                --activeDepth;
            default:
                if(activeDepth<0){
                    trouble=true;
                    printStructureStack(ifElseStack);
                    throw new Exception("Mismatched IF/ELSE structure detected on line "+currIndex);
                }
            }
            ifElseStack.set(depth-1,new StackElement(currIndex,trimmedLine));
        }
        private void processEndIf(String trimmedLine,int tagEnd) throws Exception{
            if(validate){
                if(!trimmedLine.substring(tagEnd).trim().isEmpty()){
                    trouble=true;
                    throw new Exception("Unexpected content on line "+currIndex);
                }
            }
            int depth;
            switch((depth=ifElseStack.size())-activeDepth){
            case 0:
                --activeDepth;
            case 1:
                skipToEndIf=false;
            default:
            }
            if(depth==0||activeDepth<0){
                trouble=true;
                printStructureStack(ifElseStack);
                throw new Exception("Mismatched IF/ELSE structure detected on line "+currIndex);
            }
            ifElseStack.remove(depth-1);
        }
        private void processMacro(String trimmedLine,int tagEnd) throws Exception{
            macroStack.add(new StackElement(currIndex,trimmedLine));
            final String lineTail=trimmedLine.substring(tagEnd).trim();
            final int paramBegin=lineTail.indexOf('(');
            final int paramEnd=lineTail.lastIndexOf(')');
            if(validate){
                if(paramBegin<0||paramEnd<0){
                    trouble=true;
                    throw new Exception("No parameter clause found for macro reference on line "+currIndex);
                }
            }
            final String paramStr=lineTail.substring(paramBegin+1,paramEnd);
            String[] params;
            if(paramStr.isEmpty()){
                params=new String[]{};
            }else{
                params=paramStr.split(DELIMITER_PATTERN,-1);
            }
            for(int i=0;i<params.length;++i){
                params[i]=params[i].replaceAll("\\$",Matcher.quoteReplacement("\\$"));
            }
            String[] switches;
            String switchStr="";
            int switchBegin=lineTail.indexOf('<');
            if(switchBegin>=0&&switchBegin<paramBegin){
                final int switchEnd=lineTail.lastIndexOf('>',paramBegin);
                switchStr=lineTail.substring(switchBegin+1,switchEnd);
                if(switchStr.isEmpty()){
                    switches=new String[]{};
                }else{
                    switches=switchStr.split(DELIMITER_PATTERN,-1);
                }
            }else{
                switchBegin=paramBegin;
                switches=new String[]{};
            }
            for(int i=0;i<switches.length;++i){
                switches[i]=switches[i].replaceAll("\\$",Matcher.quoteReplacement("\\$"));
            }
            final String macroName=lineTail.substring(0,switchBegin).trim();
            if(macroName.isBlank()){
                trouble=true;
                throw new Exception("No macro name found for macro reference at line "+currIndex);
            }
            final MacroSignature macroSig=new MacroSignature(macroName,switches.length,params.length);
            final MacroDefinition2 macroDef=macroDefs.get(macroSig);
            if(macroDef==null){
                trouble=true;
                System.err.println("Macro name    : "+macroName);
                System.err.println("params.length : "+params.length);
                System.err.println("paramStr      : ("+paramStr+")");
                for(int i=0;i!=params.length;++i){
                    System.err.println("  param "+i+" = \""+params[i]+"\"");
                }
                System.err.println("switches.length : "+switches.length);
                System.err.println("switchStr       : <"+switchStr+">");
                for(int i=0;i!=switches.length;++i){
                    System.err.println("  switch "+i+" = \""+switches[i]+"\"");
                }
                throw new Exception("Unknown macro reference "+macroName+" on line "+currIndex);
            }
            ++macroDef.numUses;
            for(int i=0;i<macroSig.numSwitches;++i){
                final String key=macroDef.switches[i];
                final String value=switches[i];
                final String definedVal=currentSwitches.put(key,value);
                if(definedVal!=null&&!definedVal.equals(value)){
                    trouble=true;
                    throw new Exception("Redefinition of switch "+key+" at line "+currIndex);
                }
            }
            final int oldIndex=currIndex;
            currIndex=macroDef.lineNumber;
            for(;;){
                String sourceLine=lines[currIndex++];
                if(sourceLine.trim().startsWith("#ENDDEF")){
                    break;
                }
                for(int i=0;i!=params.length;++i){
                    sourceLine=sourceLine.replaceAll(macroDef.parameters[i],params[i]);
                }
                final var tagMatcher=TAG_PATTERN.matcher(sourceLine.trim());
                if(!tagMatcher.find()||"#MACRO".equals(tagMatcher.group())){
                    for(final var switchPair:currentSwitches.entrySet()){
                        sourceLine=sourceLine.replaceAll(switchPair.getKey(),switchPair.getValue());
                    }
                }
                processSourceLine(sourceLine);
            }
            for(int i=0;i<macroSig.numSwitches;++i){
                final String key=macroDef.switches[i];
                currentSwitches.remove(key);
            }
            currIndex=oldIndex;
            macroStack.remove(macroStack.size()-1);
        }
        private void processSourceLine(String line) throws Exception{
            if(!line.isBlank()){
                final String trimmedLine=line.trim();
                final var tagMatcher=TAG_PATTERN.matcher(trimmedLine);
                if(tagMatcher.find()){
                    final String tag=tagMatcher.group();
                    final int tagEnd=tagMatcher.end();
                    switch(tag){
                    case "#MACRODEF":
                        skipDefinition();
                    case "#TYPEDEF":
                        break;
                    case "#MACRO":
                        final int rawIndent=line.indexOf(tag);
                        currIndent+=rawIndent;
                        processMacro(trimmedLine,tagEnd);
                        currIndent-=rawIndent;
                        break;
                    case "#IFSWITCH":
                    case "#IFNOTSWITCH":
                    case "#IF":
                    case "#IFNOT":
                        processIf(trimmedLine,tagEnd,!tag.contains("NOT"),tag.contains("SWITCH"));
                        break;
                    case "#ELSEIFSWITCH":
                    case "#ELSEIFNOTSWITCH":
                    case "#ELSEIF":
                    case "#ELSEIFNOT":
                        processElseIf(trimmedLine,tagEnd,!tag.contains("NOT"),tag.contains("SWITCH"));
                        break;
                    case "#ELSE":
                        processElse(trimmedLine,tagEnd);
                        break;
                    case "#ENDIF":
                        processEndIf(trimmedLine,tagEnd);
                        break;
                    default:
                        trouble=true;
                        throw new Exception("Unexpected tag "+tag+" found on line "+currIndex);
                    }
                }else{
                    if(activeDepth==ifElseStack.size()){
                        line=typeDef.parseLine(line);
                        line=" ".repeat(currIndent)+line;
                        output.add(line);
                    }
                }
            }
        }
        private void createSource() throws Exception{
            final int bound;
            final String[] lines;
            for(currIndex=0,bound=(lines=TemplateProcessor2.this.lines).length;currIndex!=bound;){
                try{
                    processSourceLine(lines[currIndex++]);
                }catch(final Exception e){
                    trouble=true;
                    System.err.println("Exception thrown in typeDef "+typeDef.name()+". currIndex = "+currIndex+"; outputIndex = "
                            +output.size());
                    throw e;
                }
            }
        }
        private void printStructureStack(List<StackElement> structure){
            System.err.println("Structure size = "+structure.size());
            int indent=0;
            for(final StackElement stackElement:ifElseStack){
                System.err.println("\t".repeat(indent++)+"line "+stackElement.lineNumber+" : "+stackElement.line);
            }
        }
        private void postValidate() throws Exception{
            if(validate){
                macroDefs.forEach((macroSig,macroDef)->{
                    final int numUses=macroDef.numUses;
                    if(numUses<1){
                        trouble=true;
                        System.err.println(
                                "WARNING : Macro "+macroSig.macroName+" defined on line "+macroDef.lineNumber+" num uses = "+numUses);
                        System.err.println("numParams   = "+macroSig.numParams);
                        System.err.println("numSwitches = "+macroSig.numSwitches);
                        System.err.println("parameters");
                        for(int i=0;i<macroSig.numParams;++i){
                            System.err.println("  param  : "+macroDef.parameters[i]);
                        }
                        System.err.println("switches");
                        for(int i=0;i<macroSig.numSwitches;++i){
                            System.err.println("  switch : "+macroDef.switches[i]);
                        }
                    }
                });
                if(!ifElseStack.isEmpty()){
                    trouble=true;
                    printStructureStack(ifElseStack);
                    throw new Exception("Mismatched IF/ELSE structure");
                }
                if(!macroStack.isEmpty()){
                    trouble=true;
                    printStructureStack(macroStack);
                    throw new Exception("Incomplete macro structure");
                }
            }
            for(final var srcItr=output.listIterator();srcItr.hasNext();){
                final String line=srcItr.next().trim();
                if(line.startsWith("//")){
                    continue;
                }
                if(line.indexOf('#')>=0||line.indexOf('$')>=0){
                    trouble=true;
                    System.err.println("WARNING: output line "+srcItr.nextIndex()+" for definition "+typeDef.name()
                    +" may contain illegal characters");
                }
            }
        }
        private void skipDefinition() throws Exception{
            final int initialIndex=currIndex;
            while(currIndex!=lines.length){
                final String line=lines[currIndex++].trim();
                if(line.startsWith("#ENDDEF")){ return; }
            }
            trouble=true;
            throw new Exception("Unexpected end to file at index "+currIndex+". Definition began at "+initialIndex);
        }
        SourceGenerator(boolean validate,TypeDefinition typeDef) throws Exception{
            this.validate=validate;
            this.typeDef=typeDef;
            output=new ArrayList<>();
            ifElseStack=new ArrayList<>();
            macroStack=new ArrayList<>();
            currentSwitches=new HashMap<>();
            createSource();
            postValidate();
        }
    }
    TemplateProcessor2(Path templateFile) throws Exception{
        lines=Files.readAllLines(templateFile).toArray(String[]::new);
        typeDefs=new HashMap<>();
        macroDefs=new HashMap<>();
        sources=new HashMap<>();
        extractDefinitions();
        final var defItr=typeDefs.keySet().iterator();
        if(defItr.hasNext()){
            TypeDefinition typeDef;
            for(sources.put(typeDef=defItr.next(),new SourceGenerator(true,typeDef).output);defItr.hasNext();
                    sources.put(typeDef=defItr.next(),new SourceGenerator(false,typeDef).output)){}
        }
    }
}
