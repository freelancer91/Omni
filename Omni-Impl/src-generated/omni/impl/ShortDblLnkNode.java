package omni.impl;
import omni.function.ShortConsumer;
import omni.function.ShortUnaryOperator;
import omni.util.ToStringUtil;
public class ShortDblLnkNode
//implements Comparable<ShortDblLnkNode>
{
  public transient ShortDblLnkNode prev;
  public transient short val;
  public transient ShortDblLnkNode next;
  public ShortDblLnkNode(short val)
  {
    this.val=val;
  }
  public ShortDblLnkNode(ShortDblLnkNode prev,short val)
  {
    this.prev=prev;
    this.val=val;
  }
  public ShortDblLnkNode(short val,ShortDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public ShortDblLnkNode(ShortDblLnkNode prev,short val,ShortDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  //TODO example this implementation to other array types
  public static  void uncheckedCopyFrom(short[] src,int length,ShortDblLnkNode dst){
    for(;;dst=dst.prev)
    {
      dst.val=(short)src[--length];
      if(length==0)
      {
        return;
      }
    }
  }
  public static  int uncheckedToString(ShortDblLnkNode curr,ShortDblLnkNode tail,byte[] buffer){
    int bufferOffset=1;
    for(;;curr=curr.next,buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
      bufferOffset=ToStringUtil.getStringShort(curr.val,buffer,bufferOffset);
      if(curr==tail){
        return bufferOffset;
      }
    }
  }
  public static  void uncheckedToString(ShortDblLnkNode curr,ShortDblLnkNode tail,ToStringUtil.OmniStringBuilderByte builder){
    for(;;curr=curr.next,builder.uncheckedAppendCommaAndSpace()){
      builder.uncheckedAppendShort(curr.val);
      if(curr==tail){
        return;
      }
    }
  }
  public static  int uncheckedHashCode(ShortDblLnkNode curr,ShortDblLnkNode tail){
    int hash=31+(curr.val);
    while(curr!=tail){
      hash=(hash*31)+((curr=curr.next).val);
    }
    return hash;
  }
  public static  void uncheckedForEachAscending(ShortDblLnkNode node,int size,ShortConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(ShortDblLnkNode node,int size,ShortUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsShort(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void uncheckedForEachAscending(ShortDblLnkNode node,ShortConsumer action){
    for(;;){
      action.accept(node.val);
      if((node=node.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedForEachAscending(ShortDblLnkNode node,ShortDblLnkNode tail,ShortConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(node==tail){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(ShortDblLnkNode node,ShortDblLnkNode tail,ShortUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsShort(node.val);
      if(node==tail){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(ShortDblLnkNode node,ShortConsumer action){
    for(;;){
      action.accept(node.val);
      if((node=node.prev)==null){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(ShortDblLnkNode node,int size,ShortConsumer action){
    for(;;node=node.prev){
      action.accept(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void eraseNode(ShortDblLnkNode node){
    ShortDblLnkNode next,prev;
    (next=node.next).prev=(prev=node.prev);
    prev.next=next;
  }
  public static  ShortDblLnkNode iterateAscending(ShortDblLnkNode node,int length){
    if(length!=0){
      do{
        node=node.next;
      }while(--length!=0);
    }
    return node;
  }
  public static  ShortDblLnkNode iterateDescending(ShortDblLnkNode node,int length){
    if(length!=0){
      do{
        node=node.prev;
      }while(--length!=0);
    }
    return node;
  }
  public static  ShortDblLnkNode uncheckedIterateDescending(ShortDblLnkNode node,int length){
    do{
      node=node.prev;
    }while(--length!=0);
    return node;
  }
  public static  boolean uncheckedcontains (ShortDblLnkNode head,ShortDblLnkNode tail
  ,int val
  ){
    for(;val!=(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearch (ShortDblLnkNode head
  ,int val
  ){
    int index=1;
    for(;val!=(head.val);++index){if((head=head.next)==null){return -1;}}
    return index;
  }
  public static  int uncheckedindexOf (ShortDblLnkNode head,ShortDblLnkNode tail
  ,int val
  ){
    int index=0;
    for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOf (int length,ShortDblLnkNode tail
  ,int val
  ){
    for(;val!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  void uncheckedCopyInto(short[] dst,ShortDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Short[] dst,ShortDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Object[] dst,ShortDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(double[] dst,ShortDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(double)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(float[] dst,ShortDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(float)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(long[] dst,ShortDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(long)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(int[] dst,ShortDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(int)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  /*
  @Override
  public int compareTo(ShortDblLnkNode that){
    if(this!=that){
      return this.val-that.val;
    }
    return 0;
  }
  */
}
