package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
import omni.function.IntComparator;
public abstract class IntOrderedSet
  extends IntUntetheredArrSeq<Integer>
  implements OmniNavigableSet.OfInt
{
  IntOrderedSet(int head,int[] arr,int tail){
    super(head,arr,tail);
  }
  IntOrderedSet(){
    super();
  }
  @Override public boolean add(Integer key){
    return add((int)key);
  }
  abstract int insertionCompare(int key1,int key2);
  @Override public boolean add(boolean key){
    return add((int)TypeUtil.castToByte(key));
  }
  @Override public boolean add(byte key){
    return add((int)key);
  }
  @Override public boolean add(char key){
    return add((int)key);
  }
  @Override public boolean add(int key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertMiddle(key);
      return true;
    }
  }
  public static class Ascending extends IntOrderedSet{
    Ascending(int head,int[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
    @Override int insertionCompare(int key1,int key2){
      //key1 is guaranteed to be non-zero, non-infinity, and non-nan
      if(key1==key2){
        return 0;
      }
      if(key1>key2){
        return 1;
      }
      //ok if key2 is NaN
      return -1;
    }
  }
  public static class Descending extends IntOrderedSet{
    Descending(int head,int[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
    @Override int insertionCompare(int key1,int key2){
      //key1 is guaranteed to be non-zero, non-infinity, and non-nan
      if(key1==key2){
        return 0;
      }
      if(key1>key2){
        return -1;
      }
      //ok if key2 is NaN
      return 1;
    }
  }
}
