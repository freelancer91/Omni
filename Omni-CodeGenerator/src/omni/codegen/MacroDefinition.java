package omni.codegen;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
class MacroDefinition{
    final String[] parameters;
    final List<String> source;
    MacroDefinition(ListIterator<String> itr,String...parameters) throws Exception{
        this.parameters=parameters;
        this.source=new ArrayList<>();
        while(itr.hasNext()){
            final String line=itr.next();
            if(line.trim().startsWith("#ENDDEF")){
                return;
            }
            source.add(line);
        }
        throw new Exception("Incomplete macro at line "+itr.nextIndex());
    }
    void resolveMacro(ListIterator<String> output,String...parameters) throws Exception{
        for(String sourceLine:source){
            for(int i=0;i!=parameters.length;++i){
                sourceLine=sourceLine.replaceAll(this.parameters[i],parameters[i]);
            }
            output.add(sourceLine);
        }
    }
}
