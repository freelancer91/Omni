package omni.util;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
public class DoubleSnglLnkNode implements Comparable<DoubleSnglLnkNode>
{
  public transient double val;
  public transient DoubleSnglLnkNode next;
  public DoubleSnglLnkNode(double val)
  {
    this.val=val;
  }
  public DoubleSnglLnkNode(double val,DoubleSnglLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public static  void uncheckedToString(DoubleSnglLnkNode curr,StringBuilder builder){
    for(;;builder.append(',').append(' ')){
      builder.append(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  //public static  int retainSurvivors(DoubleSnglLnkNode prev, final DoublePredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft){
  //  //TODO
  //  return 0;
  //}
  //public static  int retainTrailingSurvivors(DoubleSnglLnkNode prev,DoubleSnglLnkNode curr,final DoublePredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft){
  //  //TODO
  //  return 0;
  //}
  static  DoubleSnglLnkNode uncheckedSkip(DoubleSnglLnkNode curr,int numToSkip){
    while(--numToSkip!=0){
      curr=curr.next;
    }
    return curr;
  }
  static  DoubleSnglLnkNode skip(DoubleSnglLnkNode curr,int numToSkip){
    if(numToSkip!=0){
      return uncheckedSkip(curr,numToSkip);
    }
    return curr;
  }
  public static  int retainSurvivors(DoubleSnglLnkNode prev, final DoublePredicate filter){
    int numSurvivors=1;
    outer:for(DoubleSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
      if(filter.test(next.val)){
        do{
          if((next=next.next)==null){
            prev.next=null;
            break outer;
          }
        }while(filter.test(next.val));
        prev.next=next;
      }
    }
    return numSurvivors;
  }
  public static  int retainTrailingSurvivors(DoubleSnglLnkNode prev,DoubleSnglLnkNode curr,final DoublePredicate filter){
    int numSurvivors=0;
    outer:for(;;curr=curr.next){
      if(curr==null){
        prev.next=null;
        break;
      }
      if(!filter.test(curr.val)){
        prev.next=curr;
        do{
          ++numSurvivors;
          if((curr=(prev=curr).next)==null){
            break outer;
          }
        }
        while(!filter.test(curr.val));
      }
    }
    return numSurvivors;
  }
  public static  int uncheckedHashCode(DoubleSnglLnkNode curr){
    int hash=31+HashUtil.hashDouble(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+HashUtil.hashDouble(curr.val)){}
    return hash;
  }
  public static  void uncheckedForEach(DoubleSnglLnkNode curr,DoubleConsumer action){
    do{
      action.accept(curr.val);
    }while((curr=curr.next)!=null);
  }
  public static  boolean uncheckedcontainsBits(DoubleSnglLnkNode curr
    ,long bits
  ){
    for(;bits!=Double.doubleToRawLongBits(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearchBits(DoubleSnglLnkNode curr
    ,long bits
  ){
    int index=1;
    for(;bits!=Double.doubleToRawLongBits(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  boolean uncheckedcontains0(DoubleSnglLnkNode curr
  ){
    for(;!Double.isNaN(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearch0(DoubleSnglLnkNode curr
  ){
    int index=1;
    for(;!Double.isNaN(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  boolean uncheckedcontainsNaN(DoubleSnglLnkNode curr
  ){
    for(;0!=(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearchNaN(DoubleSnglLnkNode curr
  ){
    int index=1;
    for(;0!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  void uncheckedCopyInto(DoubleSnglLnkNode curr,double[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(DoubleSnglLnkNode curr,Object[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(DoubleSnglLnkNode curr,Double[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  @Override
  public int compareTo(DoubleSnglLnkNode that)
  {
    if(this!=that)
    {
      return Double.compare(this.val,that.val);
    }
    return 0;
  }
}
