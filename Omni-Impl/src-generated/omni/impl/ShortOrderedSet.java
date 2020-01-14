package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
import omni.function.ShortComparator;
public abstract class ShortOrderedSet
  extends ShortUntetheredArrSeq<Short>
  implements OmniNavigableSet.OfShort
{
  ShortOrderedSet(int head,short[] arr,int tail){
    super(head,arr,tail);
  }
  ShortOrderedSet(){
    super();
  }
  @Override public boolean add(Short key){
    return add((short)key);
  }
  abstract int insertionCompare(short key1,short key2);
  @Override public boolean add(boolean key){
    return add((short)TypeUtil.castToByte(key));
  }
  @Override public boolean add(byte key){
    return add((short)key);
  }
  @Override public boolean add(short key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertMiddle(key);
      return true;
    }
  }
  public static class Ascending extends ShortOrderedSet{
    Ascending(int head,short[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
    @Override int insertionCompare(short key1,short key2){
      return Integer.signum(key1-key2);
    }
  }
  public static class Descending extends ShortOrderedSet{
    Descending(int head,short[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
    @Override int insertionCompare(short key1,short key2){
      return Integer.signum(key2-key1);
    }
  }
}
