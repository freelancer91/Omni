package omni.impl;
import omni.function.FloatConsumer;
import omni.function.FloatUnaryOperator;
import omni.util.ToStringUtil;
import omni.util.HashUtil;
public class FloatDblLnkNode
//implements Comparable<FloatDblLnkNode>
{
  public transient FloatDblLnkNode prev;
  public transient float val;
  public transient FloatDblLnkNode next;
  public FloatDblLnkNode(float val)
  {
    this.val=val;
  }
  public FloatDblLnkNode(FloatDblLnkNode prev,float val)
  {
    this.prev=prev;
    this.val=val;
  }
  public FloatDblLnkNode(float val,FloatDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public FloatDblLnkNode(FloatDblLnkNode prev,float val,FloatDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  //TODO example this implementation to other array types
  public static  void uncheckedCopyFrom(float[] src,int length,FloatDblLnkNode dst){
    for(;;dst=dst.prev)
    {
      dst.val=(float)src[--length];
      if(length==0)
      {
        return;
      }
    }
  }
  public static  int uncheckedToString(FloatDblLnkNode curr,FloatDblLnkNode tail,byte[] buffer){
    int bufferOffset=1;
    for(;;curr=curr.next,buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
      bufferOffset=ToStringUtil.getStringFloat(curr.val,buffer,bufferOffset);
      if(curr==tail){
        return bufferOffset;
      }
    }
  }
  public static  void uncheckedToString(FloatDblLnkNode curr,FloatDblLnkNode tail,ToStringUtil.OmniStringBuilderByte builder){
    for(;;curr=curr.next,builder.uncheckedAppendCommaAndSpace()){
      builder.uncheckedAppendFloat(curr.val);
      if(curr==tail){
        return;
      }
    }
  }
  public static  int uncheckedHashCode(FloatDblLnkNode curr,FloatDblLnkNode tail){
    int hash=31+HashUtil.hashFloat(curr.val);
    while(curr!=tail){
      hash=(hash*31)+HashUtil.hashFloat((curr=curr.next).val);
    }
    return hash;
  }
  public static  void uncheckedForEachAscending(FloatDblLnkNode node,int size,FloatConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(FloatDblLnkNode node,int size,FloatUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsFloat(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void uncheckedForEachAscending(FloatDblLnkNode node,FloatConsumer action){
    for(;;){
      action.accept(node.val);
      if((node=node.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedForEachAscending(FloatDblLnkNode node,FloatDblLnkNode tail,FloatConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(node==tail){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(FloatDblLnkNode node,FloatDblLnkNode tail,FloatUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsFloat(node.val);
      if(node==tail){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(FloatDblLnkNode node,FloatConsumer action){
    for(;;){
      action.accept(node.val);
      if((node=node.prev)==null){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(FloatDblLnkNode node,int size,FloatConsumer action){
    for(;;node=node.prev){
      action.accept(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void eraseNode(FloatDblLnkNode node){
    FloatDblLnkNode next,prev;
    (next=node.next).prev=(prev=node.prev);
    prev.next=next;
  }
  public static  FloatDblLnkNode iterateAscending(FloatDblLnkNode node,int length){
    if(length!=0){
      do{
        node=node.next;
      }while(--length!=0);
    }
    return node;
  }
  public static  FloatDblLnkNode iterateDescending(FloatDblLnkNode node,int length){
    if(length!=0){
      do{
        node=node.prev;
      }while(--length!=0);
    }
    return node;
  }
  public static  FloatDblLnkNode uncheckedIterateDescending(FloatDblLnkNode node,int length){
    do{
      node=node.prev;
    }while(--length!=0);
    return node;
  }
  public static  boolean uncheckedcontainsBits(FloatDblLnkNode head,FloatDblLnkNode tail
  ,int bits
  ){
    for(;bits!=Float.floatToRawIntBits(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearchBits(FloatDblLnkNode head
  ,int bits
  ){
    int index=1;
    for(;bits!=Float.floatToRawIntBits(head.val);++index){if((head=head.next)==null){return -1;}}
    return index;
  }
  public static  int uncheckedindexOfBits(FloatDblLnkNode head,FloatDblLnkNode tail
  ,int bits
  ){
    int index=0;
    for(;bits!=Float.floatToRawIntBits(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOfBits(int length,FloatDblLnkNode tail
  ,int bits
  ){
    for(;bits!=Float.floatToRawIntBits(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  boolean uncheckedcontains0(FloatDblLnkNode head,FloatDblLnkNode tail
  ){
    for(;0!=(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearch0(FloatDblLnkNode head
  ){
    int index=1;
    for(;0!=(head.val);++index){if((head=head.next)==null){return -1;}}
    return index;
  }
  public static  int uncheckedindexOf0(FloatDblLnkNode head,FloatDblLnkNode tail
  ){
    int index=0;
    for(;0!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOf0(int length,FloatDblLnkNode tail
  ){
    for(;0!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  boolean uncheckedcontainsNaN(FloatDblLnkNode head,FloatDblLnkNode tail
  ){
    for(;!Float.isNaN(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearchNaN(FloatDblLnkNode head
  ){
    int index=1;
    for(;!Float.isNaN(head.val);++index){if((head=head.next)==null){return -1;}}
    return index;
  }
  public static  int uncheckedindexOfNaN(FloatDblLnkNode head,FloatDblLnkNode tail
  ){
    int index=0;
    for(;!Float.isNaN(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOfNaN(int length,FloatDblLnkNode tail
  ){
    for(;!Float.isNaN(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  void uncheckedCopyInto(float[] dst,FloatDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Float[] dst,FloatDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Object[] dst,FloatDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(double[] dst,FloatDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(double)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  /*
  @Override
  public int compareTo(FloatDblLnkNode that){
    if(this!=that){
      return Float.compare(this.val,that.val);
    }
    return 0;
  }
  */
}
