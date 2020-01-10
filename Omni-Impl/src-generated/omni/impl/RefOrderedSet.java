package omni.impl;
import omni.api.OmniNavigableSet;
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
  public static class Ascending<E> extends RefOrderedSet<E>{
    Ascending(int head,Object[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
  }
  public static class Descending<E> extends RefOrderedSet<E>{
    Descending(int head,Object[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
  }
}
