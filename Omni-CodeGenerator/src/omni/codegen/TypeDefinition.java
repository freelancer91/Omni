package omni.codegen;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
class TypeDefinition{
    static class DefVar{
        public final String key;
        public final String val;
        private int numUses;
        DefVar(String key,String val){
            this.key=key;
            this.val=val;
        }
        public int getNumUses(){
            return numUses;
        }
        @Override
        public String toString(){
            return "{key = "+key+"; val = "+val+"; numUses = "+numUses+"}";
        }
        public String replace(String line){
            String after=line.replaceAll("\\$"+key+"\\$",val);
            if(!line.equals(after)){
                ++numUses;
            }
            return after;
        }
    }
    final List<DefVar> definitionVars;
    TypeDefinition(ListIterator<String> itr) throws Exception{
        this.definitionVars=new ArrayList<>();
        while(itr.hasNext()){
            final String line=itr.next();
            if(!line.isBlank()){
                final String trimmed=line.trim();
                if(trimmed.startsWith("#ENDDEF")){
                    return;
                }
                final int equalsIndex=trimmed.indexOf('=');
                if(equalsIndex<0){
                    break;
                }
                final String key=trimmed.substring(0,equalsIndex).trim();
                if(key.isBlank()){
                    break;
                }
                definitionVars.add(new DefVar(key,trimmed.substring(equalsIndex+1).trim()));
            }
        }
        throw new Exception("Incomplete definition at line "+itr.nextIndex());
    }

    String parseLine(String line){
        for(final var defVar:definitionVars){
            line=defVar.replace(line);
        }
        return line;
    }

}