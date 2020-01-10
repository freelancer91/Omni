package omni.impl;
import omni.api.OmniNavigableSet;
import omni.function.CharComparator;
public abstract class CharOrderedSet
  extends CharUntetheredArrSeq<Character>
  implements OmniNavigableSet.OfChar
{
  CharOrderedSet(int head,char[] arr,int tail){
    super(head,arr,tail);
  }
  CharOrderedSet(){
    super();
  }
  abstract int compare(char val1,char val2);
  public static class Ascending extends CharOrderedSet{
    Ascending(int head,char[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
  }
  public static class Descending extends CharOrderedSet{
    Descending(int head,char[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
  }
}
