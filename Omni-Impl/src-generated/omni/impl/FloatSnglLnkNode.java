package omni.impl;
import omni.function.FloatConsumer;
import omni.util.ToStringUtil;
import omni.util.HashUtil;
public class FloatSnglLnkNode
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
  public static  int uncheckedHashCode(FloatSnglLnkNode curr){
    int hash=31+HashUtil.hashFloat(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+HashUtil.hashFloat(curr.val)){}
    return hash;
  }
  public static  void uncheckedForEach(FloatSnglLnkNode curr,FloatSnglLnkNode tail,FloatConsumer action){
    for(action.accept(curr.val);curr!=tail;action.accept((curr=curr.next).val)){}
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
    for(;0!=(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearch0(FloatSnglLnkNode curr
  ){
    int index=1;
    for(;0!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  boolean uncheckedcontainsNaN(FloatSnglLnkNode curr
  ){
    for(;!Float.isNaN(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearchNaN(FloatSnglLnkNode curr
  ){
    int index=1;
    for(;!Float.isNaN(curr.val);++index){if((curr=curr.next)==null){return -1;}}
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
}
