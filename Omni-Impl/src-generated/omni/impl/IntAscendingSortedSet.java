package omni.impl;
import omni.util.ArrCopy;
import omni.api.OmniSortedSet;
import omni.function.IntComparator;
public class IntAscendingSortedSet extends IntUntetheredArrSeqSet implements Cloneable
{
  public IntAscendingSortedSet(){
    super();
  }
  public IntAscendingSortedSet(int head,int[] arr,int tail){
    super(head,arr,tail);
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      final int[] dst;
      final int head,cloneTail;
      int size;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(arr,head,dst=new int[size],0,size);
        cloneTail=size-1;
      }else{
        final int[] arr;
        dst=new int[size+=(arr=this.arr).length];
        cloneTail=size-1;
        ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
        ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      }
      return new IntAscendingSortedSet(0,dst,cloneTail);
    }
    return new IntAscendingSortedSet();
  }
  @Override public IntComparator comparator(){
    return Integer::compare;
  }
  @Override int insertionCompare(int key1,int key2){
    if(key1>key2){
      return 1;
    }
    if(key1==key2){
      return 0;
    }
    return -1;
  }
}
