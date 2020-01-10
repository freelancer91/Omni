package omni.impl;
import omni.api.OmniNavigableSet;
import omni.function.IntComparator;
public abstract class IntOrderedSet
  extends IntUntetheredArrSeq<Integer>
  implements OmniNavigableSet.OfInt
{
  IntOrderedSet(int head,int[] arr,int tail){
    super(head,arr,tail);
  }
  IntOrderedSet(){
    super();
  }
  abstract int compare(int val1,int val2);
  public static class Ascending extends IntOrderedSet{
    Ascending(int head,int[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
  }
  public static class Descending extends IntOrderedSet{
    Descending(int head,int[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
  }
}
