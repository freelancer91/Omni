package omni.impl;
import java.util.function.LongConsumer;
import java.util.function.LongUnaryOperator;
import omni.util.ToStringUtil;
public class LongDblLnkNode
//implements Comparable<LongDblLnkNode>
{
  public transient LongDblLnkNode prev;
  public transient long val;
  public transient LongDblLnkNode next;
  public LongDblLnkNode(long val)
  {
    this.val=val;
  }
  public LongDblLnkNode(LongDblLnkNode prev,long val)
  {
    this.prev=prev;
    this.val=val;
  }
  public LongDblLnkNode(long val,LongDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public LongDblLnkNode(LongDblLnkNode prev,long val,LongDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  //TODO example this implementation to other array types
  public static  void uncheckedCopyFrom(long[] src,int length,LongDblLnkNode dst){
    for(;;dst=dst.prev)
    {
      dst.val=(long)src[--length];
      if(length==0)
      {
        return;
      }
    }
  }
  public static  int uncheckedToString(LongDblLnkNode curr,LongDblLnkNode tail,byte[] buffer){
    int bufferOffset=1;
    for(;;curr=curr.next,buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
      bufferOffset=ToStringUtil.getStringLong(curr.val,buffer,bufferOffset);
      if(curr==tail){
        return bufferOffset;
      }
    }
  }
  public static  void uncheckedToString(LongDblLnkNode curr,LongDblLnkNode tail,ToStringUtil.OmniStringBuilderByte builder){
    for(;;curr=curr.next,builder.uncheckedAppendCommaAndSpace()){
      builder.uncheckedAppendLong(curr.val);
      if(curr==tail){
        return;
      }
    }
  }
  public static  int uncheckedHashCode(LongDblLnkNode curr,LongDblLnkNode tail){
    int hash=31+Long.hashCode(curr.val);
    while(curr!=tail){
      hash=(hash*31)+Long.hashCode((curr=curr.next).val);
    }
    return hash;
  }
  public static  void uncheckedForEachAscending(LongDblLnkNode node,int size,LongConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(LongDblLnkNode node,int size,LongUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsLong(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void uncheckedForEachAscending(LongDblLnkNode node,LongConsumer action){
    for(;;){
      action.accept(node.val);
      if((node=node.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedForEachAscending(LongDblLnkNode node,LongDblLnkNode tail,LongConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(node==tail){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(LongDblLnkNode node,LongDblLnkNode tail,LongUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsLong(node.val);
      if(node==tail){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(LongDblLnkNode node,LongConsumer action){
    for(;;){
      action.accept(node.val);
      if((node=node.prev)==null){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(LongDblLnkNode node,int size,LongConsumer action){
    for(;;node=node.prev){
      action.accept(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void eraseNode(LongDblLnkNode node){
    LongDblLnkNode next,prev;
    (next=node.next).prev=(prev=node.prev);
    prev.next=next;
  }
  public static  LongDblLnkNode iterateAscending(LongDblLnkNode node,int length){
    if(length!=0){
      do{
        node=node.next;
      }while(--length!=0);
    }
    return node;
  }
  public static  LongDblLnkNode iterateDescending(LongDblLnkNode node,int length){
    if(length!=0){
      do{
        node=node.prev;
      }while(--length!=0);
    }
    return node;
  }
  public static  LongDblLnkNode uncheckedIterateDescending(LongDblLnkNode node,int length){
    do{
      node=node.prev;
    }while(--length!=0);
    return node;
  }
  public static  boolean uncheckedcontains (LongDblLnkNode head,LongDblLnkNode tail
  ,long val
  ){
    for(;val!=(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearch (LongDblLnkNode head
  ,long val
  ){
    int index=1;
    for(;val!=(head.val);++index){if((head=head.next)==null){return -1;}}
    return index;
  }
  public static  int uncheckedindexOf (LongDblLnkNode head,LongDblLnkNode tail
  ,long val
  ){
    int index=0;
    for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOf (int length,LongDblLnkNode tail
  ,long val
  ){
    for(;val!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  void uncheckedCopyInto(long[] dst,LongDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Long[] dst,LongDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Object[] dst,LongDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(double[] dst,LongDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(double)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(float[] dst,LongDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(float)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  /*
  @Override
  public int compareTo(LongDblLnkNode that){
    if(this!=that){
      long thisVal,thatVal;
      if((thisVal=this.val)<(thatVal=that.val)){
        return -1;
      }else if(thisVal>thatVal){
        return 1;
      }
    }
    return 0;
  }
  */
}
