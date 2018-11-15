package omni.codegen;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
class Definition{
  private final String name;
  private Map<String,String> map;
  Definition(String name){
    this.name=Objects.requireNonNull(name);
    map=new HashMap<>();
  }
  Definition(String name,Map<String,String> map){
    this.name=Objects.requireNonNull(name);
    this.map=new HashMap<>(map);
  }
  @Override public boolean equals(Object val){
    return val instanceof Definition&&name.equals(((Definition)val).name);
  }
  @Override public int hashCode(){
    return name.hashCode();
  }
  public String getName(){
    return name;
  }
  public Map<String,String> getMap(){
    return map;
  }
  public void setMap(Map<String,String> map){
    this.map=map;
  }
  Path resolvePath(String srcFolder,String fqtString){
    return Paths.get(srcFolder,parseLine(fqtString).replaceAll("\\.",File.separator)+".java");
  }
  String parseLine(String line){
    for(final var pair:map.entrySet()){
      line=line.replaceAll("\\$"+pair.getKey()+"\\$",pair.getValue());
    }
    return line;
  }
}
