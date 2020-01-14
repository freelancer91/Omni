package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
import omni.function.LongComparator;
public abstract class LongOrderedSet
  extends LongUntetheredArrSeq<Long>
  implements OmniNavigableSet.OfLong
{
  LongOrderedSet(int head,long[] arr,int tail){
    super(head,arr,tail);
  }
  LongOrderedSet(){
    super();
  }
  @Override public boolean add(Long key){
    return add((long)key);
  }
  abstract int insertionCompare(long key1,long key2);
  @Override public boolean add(boolean key){
    return add(TypeUtil.castToLong(key));
  }
  @Override public boolean add(byte key){
    return add((long)key);
  }
  @Override public boolean add(char key){
    return add((long)key);
  }
  @Override public boolean add(int key){
    return add((long)key);
  }
  @Override public boolean add(long key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertMiddle(key);
      return true;
    }
  }
  public static class Ascending extends LongOrderedSet{
    Ascending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
    @Override int insertionCompare(long key1,long key2){
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
  public static class Descending extends LongOrderedSet{
    Descending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
    @Override int insertionCompare(long key1,long key2){
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
