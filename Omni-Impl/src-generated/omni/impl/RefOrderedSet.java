package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import java.util.Comparator;
public abstract class RefOrderedSet<E>
  extends RefUntetheredArrSeq<E>
  implements OmniNavigableSet.OfRef<E>
{
  RefOrderedSet(int head,Object[] arr,int tail){
    super(head,arr,tail);
  }
  RefOrderedSet(){
    super();
  }
  abstract int insertionCompare(E key1,E key2);
  @Override public boolean add(E key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertMiddle(key);
      return true;
    }
  }
  public static class Ascending<E> extends RefOrderedSet<E>{
    Ascending(int head,Object[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
     @SuppressWarnings("unchecked")
    @Override int insertionCompare(E key1,E key2){
      return Integer.signum(((Comparable<E>)key1).compareTo(key2));
    }
  }
  public static class Descending<E> extends RefOrderedSet<E>{
    Descending(int head,Object[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
     @SuppressWarnings("unchecked")
    @Override int insertionCompare(E key1,E key2){
      return Integer.signum(((Comparable<E>)key2).compareTo(key1));
    }
  }
}
