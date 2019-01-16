package omni.codegen;
enum Visibility{
  PUBLIC("public",3),PROTECTED("protected",2),PACKAGE("",1),PRIVATE("private",0);
  public final String displayName;
  public final int rank;
  Visibility(String displayName,int rank){
    this.displayName=displayName;
    this.rank=rank;
  }
}
