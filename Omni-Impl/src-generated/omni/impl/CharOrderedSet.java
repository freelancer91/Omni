package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
import omni.function.CharComparator;
public abstract class CharOrderedSet
  extends CharUntetheredArrSeq<Character>
  implements OmniNavigableSet.OfChar
{
  CharOrderedSet(int head,char[] arr,int tail){
    super(head,arr,tail);
  }
  CharOrderedSet(){
    super();
  }
  @Override public boolean add(Character key){
    return add((char)key);
  }
  abstract int insertionCompare(char key1,char key2);
  @Override public boolean add(boolean key){
    return add(TypeUtil.castToChar(key));
  }
  @Override public boolean add(char key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertMiddle(key);
      return true;
    }
  }
  public static class Ascending extends CharOrderedSet{
    Ascending(int head,char[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
    @Override int insertionCompare(char key1,char key2){
      return Integer.signum(key1-key2);
    }
  }
  public static class Descending extends CharOrderedSet{
    Descending(int head,char[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
    @Override int insertionCompare(char key1,char key2){
      return Integer.signum(key2-key1);
    }
  }
}
