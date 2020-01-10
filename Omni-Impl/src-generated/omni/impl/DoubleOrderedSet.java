package omni.impl;
import omni.api.OmniNavigableSet;
import omni.function.DoubleComparator;
public abstract class DoubleOrderedSet
  extends LongUntetheredArrSeq<Double>
  implements OmniNavigableSet.OfDouble
{
  DoubleOrderedSet(int head,long[] arr,int tail){
    super(head,arr,tail);
  }
  DoubleOrderedSet(){
    super();
  }
  public static class Ascending extends DoubleOrderedSet{
    Ascending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
  }
  public static class Descending extends DoubleOrderedSet{
    Descending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
  }
}
