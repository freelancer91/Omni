package omni.util;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
public class FloatSnglLnkNode implements Comparable<FloatSnglLnkNode>
{
  public transient float val;
  public transient FloatSnglLnkNode next;
  public FloatSnglLnkNode(float val)
  {
    this.val=val;
  }
  public FloatSnglLnkNode(float val,FloatSnglLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public static  int uncheckedToString(FloatSnglLnkNode curr,byte[] buffer){
    int bufferOffset=1;
    for(;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
      bufferOffset=ToStringUtil.getStringFloat(curr.val,buffer,bufferOffset);
      if((curr=curr.next)==null){
        return bufferOffset;
      }
    }
  }
  public static  void uncheckedToString(FloatSnglLnkNode curr,ToStringUtil.OmniStringBuilderByte builder){
    for(;;builder.uncheckedAppendCommaAndSpace()){
      builder.uncheckedAppendFloat(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void pullSurvivorsDown(FloatSnglLnkNode prev,FloatPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
    int wordOffset;
    for(long word=survivorSet[wordOffset=0],marker=1L;;){
      var curr=prev.next;
      if((marker&word)==0){
        do{
          if(--numRemoved==0){
            prev.next=null;
            return;
          }
          if((marker<<=1)==0){
            word=survivorSet[++wordOffset];
            marker=1L;
          }
          curr=curr.next;
        }while((marker&word)==0);
        prev.next=curr;
      }
      if(--numSurvivors==0){
        return;
      }
      if((marker<<=1)==0){
         word=survivorSet[++wordOffset];
         marker=1L;
      }
      prev=curr;
    }
  }
  public static  int markSurvivors(FloatSnglLnkNode curr,FloatPredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test(curr.val)){
          word|=marker;
          ++numSurvivors;
        }
        if((curr=curr.next)==null){
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
      }
      while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  public static  void pullSurvivorsDown(FloatSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
    for(long marker=1L;;marker<<=1){
      var curr=prev.next;
      if((marker&word)==0){
        do{
          if(--numRemoved==0){
            prev.next=null;
            return;
          }
          curr=curr.next;
        }while(((marker<<=1)&word)==0);
        prev.next=curr;
      }
      if(--numSurvivors==0){
        return;
      }
      prev=curr;
    }
  }
  public static  long markSurvivors(FloatSnglLnkNode curr,FloatPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  static  FloatSnglLnkNode uncheckedSkip(FloatSnglLnkNode curr,int numToSkip){
    while(--numToSkip!=0){
      curr=curr.next;
    }
    return curr;
  }
  static  FloatSnglLnkNode skip(FloatSnglLnkNode curr,int numToSkip){
    if(numToSkip!=0){
      return uncheckedSkip(curr,numToSkip);
    }
    return curr;
  }
  public static  int retainSurvivors(FloatSnglLnkNode prev, final FloatPredicate filter){
    int numSurvivors=1;
    outer:for(FloatSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  public static  int retainTrailingSurvivors(FloatSnglLnkNode prev,FloatSnglLnkNode curr,final FloatPredicate filter){
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
  public static  int uncheckedHashCode(FloatSnglLnkNode curr){
    int hash=31+HashUtil.hashFloat(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+HashUtil.hashFloat(curr.val)){}
    return hash;
  }
  public static  void uncheckedForEach(FloatSnglLnkNode curr,FloatConsumer action){
    do{
      action.accept(curr.val);
    }while((curr=curr.next)!=null);
  }
  public static  boolean uncheckedcontainsBits(FloatSnglLnkNode curr
  ,int bits
  ){
    for(;bits!=Float.floatToRawIntBits(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearchBits(FloatSnglLnkNode curr
  ,int bits
  ){
    int index=1;
    for(;bits!=Float.floatToRawIntBits(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  boolean uncheckedcontains0(FloatSnglLnkNode curr
  ){
    for(;!Float.isNaN(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearch0(FloatSnglLnkNode curr
  ){
    int index=1;
    for(;!Float.isNaN(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  boolean uncheckedcontainsNaN(FloatSnglLnkNode curr
  ){
    for(;0!=(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearchNaN(FloatSnglLnkNode curr
  ){
    int index=1;
    for(;0!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  void uncheckedCopyInto(FloatSnglLnkNode curr,float[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(FloatSnglLnkNode curr,Object[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(FloatSnglLnkNode curr,Float[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(FloatSnglLnkNode curr,double[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(double)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  @Override
  public int compareTo(FloatSnglLnkNode that)
  {
    if(this!=that)
    {
      return Float.compare(this.val,that.val);
    }
    return 0;
  }
}
