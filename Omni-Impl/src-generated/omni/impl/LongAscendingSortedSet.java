package omni.impl;
import omni.util.ArrCopy;
import omni.api.OmniSortedSet;
import omni.function.LongComparator;
public class LongAscendingSortedSet extends LongUntetheredArrSeqSet implements Cloneable
{
  public LongAscendingSortedSet(){
    super();
  }
  public LongAscendingSortedSet(int head,long[] arr,int tail){
    super(head,arr,tail);
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      final long[] dst;
      final int head,cloneTail;
      int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(arr,head,dst=new long[size],0,size);
        cloneTail=size-1;
      }else{
        final long[] arr;
        dst=new long[size+=(arr=this.arr).length];
        cloneTail=size-1;
        ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
        ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      }
      return new LongAscendingSortedSet(0,dst,cloneTail);
    }
    return new LongAscendingSortedSet();
  }
  @Override public LongComparator comparator(){
    return Long::compare;
  }
  @Override int insertionCompare(long key1,long key2){
    if(key1>key2){
      return 1;
    }
    if(key1==key2){
      return 0;
    }
    return -1;
  }
}
