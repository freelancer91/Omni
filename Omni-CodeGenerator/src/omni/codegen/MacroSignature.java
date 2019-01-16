package omni.codegen;

public class MacroSignature{
    final String macroName;
    final int numSwitches;
    final int numParams;
    MacroSignature(String macroName,int numSwitches,int numParams){
        this.macroName=macroName;
        this.numSwitches=numSwitches;
        this.numParams=numParams;
    }
    @Override
    public boolean equals(Object val){
        if(val instanceof MacroSignature){
            MacroSignature tmp=(MacroSignature)val;
            return tmp.macroName.equals(macroName)&&tmp.numParams==numParams&&tmp.numSwitches==numSwitches;
        }
        return false;
    }
    @Override
    public int hashCode(){
        return (macroName.hashCode()*31+numParams)*31+numSwitches;
    }
}
