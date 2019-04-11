package omni.util;
import omni.function.BooleanConsumer;
public class BooleanSnglLnkNode implements Comparable<BooleanSnglLnkNode>
{
  public transient boolean val;
  public transient BooleanSnglLnkNode next;
  public BooleanSnglLnkNode(boolean val)
  {
    this.val=val;
  }
  public BooleanSnglLnkNode(boolean val,BooleanSnglLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public static  int uncheckedToString(BooleanSnglLnkNode curr,byte[] buffer){
    int bufferOffset=1;
    for(;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
      bufferOffset=ToStringUtil.getStringBoolean(curr.val,buffer,bufferOffset);
      if((curr=curr.next)==null){
        return bufferOffset;
      }
    }
  }
  public static  void uncheckedToString(BooleanSnglLnkNode curr,ToStringUtil.OmniStringBuilderByte builder){
    for(;;builder.uncheckedAppendCommaAndSpace()){
      builder.uncheckedAppendBoolean(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  int retainSurvivors(BooleanSnglLnkNode prev,final boolean retainThis){
    int numSurvivors=1;
    outer:for(BooleanSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
      if(next.val^retainThis){
        do{
          if((next=next.next)==null){
            prev.next=null;
            break outer;
          }
        }while(next.val^retainThis);
        prev.next=next;
      }
    }
    return numSurvivors;
  }
  public static  int retainTrailingSurvivors(BooleanSnglLnkNode prev,BooleanSnglLnkNode curr,final boolean retainThis){
    int numSurvivors=0;
    outer:for(;;curr=curr.next){
      if(curr==null){
        prev.next=null;
        break;
      }
      if(curr.val==retainThis){
        prev.next=curr;
        do{
          ++numSurvivors;
          if((curr=(prev=curr).next)==null){
            break outer;
          }
        }
        while(curr.val==retainThis);
      }
    }
    return numSurvivors;
  }
  public static  int uncheckedHashCode(BooleanSnglLnkNode curr){
    int hash=31+Boolean.hashCode(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+Boolean.hashCode(curr.val)){}
    return hash;
  }
  public static  void uncheckedForEach(BooleanSnglLnkNode curr,BooleanConsumer action){
    do{
      action.accept(curr.val);
    }while((curr=curr.next)!=null);
  }
  public static  boolean uncheckedcontains (BooleanSnglLnkNode curr
  ,boolean val
  ){
    for(;val!=(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearch (BooleanSnglLnkNode curr
  ,boolean val
  ){
    int index=1;
    for(;val!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  void uncheckedCopyInto(BooleanSnglLnkNode curr,boolean[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(BooleanSnglLnkNode curr,Object[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(BooleanSnglLnkNode curr,Boolean[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(BooleanSnglLnkNode curr,double[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=TypeUtil.castToDouble(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(BooleanSnglLnkNode curr,float[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=TypeUtil.castToFloat(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(BooleanSnglLnkNode curr,long[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=TypeUtil.castToLong(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(BooleanSnglLnkNode curr,int[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(int)TypeUtil.castToByte(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(BooleanSnglLnkNode curr,short[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(short)TypeUtil.castToByte(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(BooleanSnglLnkNode curr,byte[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=TypeUtil.castToByte(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(BooleanSnglLnkNode curr,char[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=TypeUtil.castToChar(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  @Override
  public int compareTo(BooleanSnglLnkNode that)
  {
    if(this!=that)
    {
      if(this.val)
      {
        if(!that.val)
        {
          return 1;
        }
      }else if(that.val)
      {
        return -1;
      }
    }
    return 0;
  }
}
