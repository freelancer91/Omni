package omni.impl;
import omni.api.OmniNavigableSet;
import omni.function.FloatComparator;
public abstract class FloatOrderedSet
  extends IntUntetheredArrSeq<Float>
  implements OmniNavigableSet.OfFloat
{
  FloatOrderedSet(int head,int[] arr,int tail){
    super(head,arr,tail);
  }
  FloatOrderedSet(){
    super();
  }
  public static class Ascending extends FloatOrderedSet{
    Ascending(int head,int[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
  }
  public static class Descending extends FloatOrderedSet{
    Descending(int head,int[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
  }
}
