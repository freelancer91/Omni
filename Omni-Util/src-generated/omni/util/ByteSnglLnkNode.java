package omni.util;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
public class ByteSnglLnkNode implements Comparable<ByteSnglLnkNode>
{
  public transient byte val;
  public transient ByteSnglLnkNode next;
  public ByteSnglLnkNode(byte val)
  {
    this.val=val;
  }
  public ByteSnglLnkNode(byte val,ByteSnglLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public static  int uncheckedToString(ByteSnglLnkNode curr,byte[] buffer){
    int bufferOffset=1;
    for(;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
      bufferOffset=ToStringUtil.getStringShort(curr.val,buffer,bufferOffset);
      if((curr=curr.next)==null){
        return bufferOffset;
      }
    }
  }
  public static  void uncheckedToString(ByteSnglLnkNode curr,ToStringUtil.OmniStringBuilderByte builder){
    for(;;builder.uncheckedAppendCommaAndSpace()){
      builder.uncheckedAppendShort(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  //public static  int retainSurvivors(ByteSnglLnkNode prev, final BytePredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft){
  //  //TODO
  //  return 0;
  //}
  //public static  int retainTrailingSurvivors(ByteSnglLnkNode prev,ByteSnglLnkNode curr,final BytePredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft){
  //  //TODO
  //  return 0;
  //}
  static  ByteSnglLnkNode uncheckedSkip(ByteSnglLnkNode curr,int numToSkip){
    while(--numToSkip!=0){
      curr=curr.next;
    }
    return curr;
  }
  static  ByteSnglLnkNode skip(ByteSnglLnkNode curr,int numToSkip){
    if(numToSkip!=0){
      return uncheckedSkip(curr,numToSkip);
    }
    return curr;
  }
  public static  int retainSurvivors(ByteSnglLnkNode prev, final BytePredicate filter){
    int numSurvivors=1;
    outer:for(ByteSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  public static  int retainTrailingSurvivors(ByteSnglLnkNode prev,ByteSnglLnkNode curr,final BytePredicate filter){
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
  public static  int uncheckedHashCode(ByteSnglLnkNode curr){
    int hash=31+(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+(curr.val)){}
    return hash;
  }
  public static  void uncheckedForEach(ByteSnglLnkNode curr,ByteConsumer action){
    do{
      action.accept(curr.val);
    }while((curr=curr.next)!=null);
  }
  public static  boolean uncheckedcontains (ByteSnglLnkNode curr
    ,int val
  ){
    for(;val!=(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static  int uncheckedsearch (ByteSnglLnkNode curr
    ,int val
  ){
    int index=1;
    for(;val!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static  void uncheckedCopyInto(ByteSnglLnkNode curr,byte[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(ByteSnglLnkNode curr,Object[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(ByteSnglLnkNode curr,Byte[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(ByteSnglLnkNode curr,double[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(double)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(ByteSnglLnkNode curr,float[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(float)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(ByteSnglLnkNode curr,long[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(long)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(ByteSnglLnkNode curr,int[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(int)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(ByteSnglLnkNode curr,short[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(short)(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  @Override
  public int compareTo(ByteSnglLnkNode that)
  {
    if(this!=that)
    {
      return this.val-that.val;
    }
    return 0;
  }
}
