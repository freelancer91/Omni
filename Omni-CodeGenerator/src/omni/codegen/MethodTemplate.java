package omni.codegen;
import java.util.List;
class MethodTemplate{
  Visibility visibility;
  String returnType;
  String methodName;
  boolean override;
  List<String> suppressWarnings;
  List<String[]> methodArgs;
  List<String> sourceLines;
  MethodTemplate(Visibility visibility,String returnType,String methodName,boolean override,
      List<String> suppressWarnings,List<String[]> methodArgs,List<String> sourceLines){
    this.visibility=visibility;
    this.returnType=returnType;
    this.methodName=methodName;
    this.override=override;
    this.suppressWarnings=suppressWarnings;
    this.methodArgs=methodArgs;
    this.sourceLines=sourceLines;
  }
}
