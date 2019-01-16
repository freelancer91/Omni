package omni.codegen;

public class MacroDefinition2{
    final int lineNumber;
    // final String[] source;
    final String[] parameters;
    final String[] switches;
    int numUses;
    MacroDefinition2(int lineNumber,String[] switches,String[] params){
        this.lineNumber=lineNumber;
        // this.source=source;
        this.switches=switches;
        this.parameters=params;
    }

}
