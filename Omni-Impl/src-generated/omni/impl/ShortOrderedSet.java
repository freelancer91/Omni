package omni.impl;
import omni.api.OmniNavigableSet;
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
  public static class Ascending extends ShortOrderedSet{
    Ascending(int head,short[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
  }
  public static class Descending extends ShortOrderedSet{
    Descending(int head,short[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
  }
}
