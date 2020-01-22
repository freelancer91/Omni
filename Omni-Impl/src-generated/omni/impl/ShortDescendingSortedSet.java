package omni.impl;
import omni.util.ArrCopy;
import omni.api.OmniSortedSet;
import omni.function.ShortComparator;
public class ShortDescendingSortedSet extends ShortUntetheredArrSeqSet implements Cloneable
{
  public ShortDescendingSortedSet(){
    super();
  }
  public ShortDescendingSortedSet(int head,short[] arr,int tail){
    super(head,arr,tail);
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      final short[] dst;
      final int head,cloneTail;
      int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(arr,head,dst=new short[size],0,size);
        cloneTail=size-1;
      }else{
        final short[] arr;
        dst=new short[size+=(arr=this.arr).length];
        cloneTail=size-1;
        ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
        ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      }
      return new ShortDescendingSortedSet(0,dst,cloneTail);
    }
    return new ShortDescendingSortedSet();
  }
  @Override public ShortComparator comparator(){
    return ShortComparator::descendingCompare;
  }
  @Override int insertionCompare(int key1,int key2){
    return Integer.signum(key2-key1);
  }
}
