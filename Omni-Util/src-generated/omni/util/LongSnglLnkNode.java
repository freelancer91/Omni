package omni.util;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
public class LongSnglLnkNode implements Comparable<LongSnglLnkNode>
{
  public transient long val;
  public transient LongSnglLnkNode next;
  public LongSnglLnkNode(long val)
  {
    this.val=val;
  }
  public LongSnglLnkNode(long val,LongSnglLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public static  int uncheckedToString(LongSnglLnkNode curr,byte[] buffer){
    int bufferOffset=1;
    for(;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
      bufferOffset=ToStringUtil.getStringLong(curr.val,buffer,bufferOffset);
      if((curr=curr.next)==null){
        return bufferOffset;
      }
    }
  }
  public static  void uncheckedToString(LongSnglLnkNode curr,ToStringUtil.OmniStringBuilderByte builder){
    for(;;builder.uncheckedAppendCommaAndSpace()){
      builder.uncheckedAppendLong(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  //public static  int retainSurvivors(LongSnglLnkNode prev, final LongPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft){
  //  //TODO
  //  return 0;
  //}
  //public static  int retainTrailingSurvivors(LongSnglLnkNode prev,LongSnglLnkNode curr,final LongPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft){
  //  //TODO
  //  return 0;
  //}
  static  LongSnglLnkNode uncheckedSkip(LongSnglLnkNode curr,int numToSkip){
    while(--numToSkip!=0){
      curr=curr.next;
    }
    return curr;
  }
  static  LongSnglLnkNode skip(LongSnglLnkNode curr,int numToSkip){
    if(numToSkip!=0){
      return uncheckedSkip(curr,numToSkip);
    }
    return curr;
  }
  public static  int retainSurvivors(LongSnglLnkNode prev, final LongPredicate filter){
    int numSurvivors=1;
    outer:for(LongSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  public static  int retainTrailingSurvivors(LongSnglLnkNode prev,LongSnglLnkNode curr,final LongPredicate filter){
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
  public static  int uncheckedHashCode(LongSnglLnkNode curr){
    int hash=31+Long.hashCode(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+Long.hashCode(curr.val)){}
    return hash;
  }
  public static  void uncheckedForEach(LongSnglLnkNode curr,LongConsumer action){
    do{
      action.accept(curr.val);
    }while((curr=curr.next)!=null);
  }
  public static  boolean uncheckedcontains (LongSnglLnkNode curr
    ,long val
  ){
    for(;val!=(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearch (LongSnglLnkNode curr
    ,long val
  ){
    int index=1;
    for(;val!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  void uncheckedCopyInto(LongSnglLnkNode curr,long[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(LongSnglLnkNode curr,Object[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(LongSnglLnkNode curr,Long[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(LongSnglLnkNode curr,double[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(double)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(LongSnglLnkNode curr,float[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(float)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  @Override
  public int compareTo(LongSnglLnkNode that)
  {
    if(this!=that)
    {
      long thisVal,thatVal;
      if((thisVal=this.val)<(thatVal=that.val))
      {
        return -1;
      }
      if(thisVal>thatVal)
      {
        return 1;
      }
    }
    return 0;
  }
}
