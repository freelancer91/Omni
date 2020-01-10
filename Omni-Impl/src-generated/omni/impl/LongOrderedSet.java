package omni.impl;
import omni.api.OmniNavigableSet;
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
  public static class Ascending extends LongOrderedSet{
    Ascending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
  }
  public static class Descending extends LongOrderedSet{
    Descending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
  }
}
