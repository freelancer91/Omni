package omni.util;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
public class IntSnglLnkNode implements Comparable<IntSnglLnkNode>
{
  public transient int val;
  public transient IntSnglLnkNode next;
  public IntSnglLnkNode(int val)
  {
    this.val=val;
  }
  public IntSnglLnkNode(int val,IntSnglLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public static  int uncheckedToString(IntSnglLnkNode curr,byte[] buffer){
    int bufferOffset=1;
    for(;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
      bufferOffset=ToStringUtil.getStringInt(curr.val,buffer,bufferOffset);
      if((curr=curr.next)==null){
        return bufferOffset;
      }
    }
  }
  public static  void uncheckedToString(IntSnglLnkNode curr,ToStringUtil.OmniStringBuilderByte builder){
    for(;;builder.uncheckedAppendCommaAndSpace()){
      builder.uncheckedAppendInt(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  //public static  int retainSurvivors(IntSnglLnkNode prev, final IntPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft){
  //  //TODO
  //  return 0;
  //}
  //public static  int retainTrailingSurvivors(IntSnglLnkNode prev,IntSnglLnkNode curr,final IntPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft){
  //  //TODO
  //  return 0;
  //}
  static  IntSnglLnkNode uncheckedSkip(IntSnglLnkNode curr,int numToSkip){
    while(--numToSkip!=0){
      curr=curr.next;
    }
    return curr;
  }
  static  IntSnglLnkNode skip(IntSnglLnkNode curr,int numToSkip){
    if(numToSkip!=0){
      return uncheckedSkip(curr,numToSkip);
    }
    return curr;
  }
  public static  int retainSurvivors(IntSnglLnkNode prev, final IntPredicate filter){
    int numSurvivors=1;
    outer:for(IntSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  public static  int retainTrailingSurvivors(IntSnglLnkNode prev,IntSnglLnkNode curr,final IntPredicate filter){
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
  public static  int uncheckedHashCode(IntSnglLnkNode curr){
    int hash=31+(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+(curr.val)){}
    return hash;
  }
  public static  void uncheckedForEach(IntSnglLnkNode curr,IntConsumer action){
    do{
      action.accept(curr.val);
    }while((curr=curr.next)!=null);
  }
  public static  boolean uncheckedcontains (IntSnglLnkNode curr
    ,int val
  ){
    for(;val!=(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearch (IntSnglLnkNode curr
    ,int val
  ){
    int index=1;
    for(;val!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  void uncheckedCopyInto(IntSnglLnkNode curr,int[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(IntSnglLnkNode curr,Object[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(IntSnglLnkNode curr,Integer[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(IntSnglLnkNode curr,double[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(double)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(IntSnglLnkNode curr,float[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(float)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(IntSnglLnkNode curr,long[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(long)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  @Override
  public int compareTo(IntSnglLnkNode that)
  {
    if(this!=that)
    {
      int thisVal,thatVal;
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
