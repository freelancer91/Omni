package omni.impl;
import omni.function.BooleanConsumer;
import omni.util.TypeUtil;
import omni.function.BooleanPredicate;
import omni.util.ToStringUtil;
public class BooleanDblLnkNode implements Comparable<BooleanDblLnkNode>
{
  public transient BooleanDblLnkNode prev;
  public transient boolean val;
  public transient BooleanDblLnkNode next;
  public BooleanDblLnkNode(boolean val)
  {
    this.val=val;
  }
  public BooleanDblLnkNode(BooleanDblLnkNode prev,boolean val)
  {
    this.prev=prev;
    this.val=val;
  }
  public BooleanDblLnkNode(boolean val,BooleanDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public BooleanDblLnkNode(BooleanDblLnkNode prev,boolean val,BooleanDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  //TODO example this implementation to other array types
  public static  void uncheckedCopyFrom(boolean[] src,int length,BooleanDblLnkNode dst){
    for(;;dst=dst.prev)
    {
      dst.val=(boolean)src[--length];
      if(length==0)
      {
        return;
      }
    }
  }
  public static  int uncheckedToString(BooleanDblLnkNode curr,BooleanDblLnkNode tail,byte[] buffer){
    int bufferOffset=1;
    for(;;curr=curr.next,buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
      bufferOffset=ToStringUtil.getStringBoolean(curr.val,buffer,bufferOffset);
      if(curr==tail){
        return bufferOffset;
      }
    }
  }
  public static  void uncheckedToString(BooleanDblLnkNode curr,BooleanDblLnkNode tail,ToStringUtil.OmniStringBuilderByte builder){
    for(;;curr=curr.next,builder.uncheckedAppendCommaAndSpace()){
      builder.uncheckedAppendBoolean(curr.val);
      if(curr==tail){
        return;
      }
    }
  }
  public static  int uncheckedHashCode(BooleanDblLnkNode curr,BooleanDblLnkNode tail){
    int hash=31+Boolean.hashCode(curr.val);
    while(curr!=tail){
      hash=(hash*31)+Boolean.hashCode((curr=curr.next).val);
    }
    return hash;
  }
  public static  void uncheckedForEachAscending(BooleanDblLnkNode node,int size,BooleanConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(--size!=0){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(BooleanDblLnkNode node,int size,BooleanPredicate operator){
    for(;;node=node.next){
      node.val=operator.test(node.val);
      if(--size!=0){
        return;
      }
    }
  }
  public static  void uncheckedForEachAscending(BooleanDblLnkNode node,BooleanConsumer action){
    for(;;){
      action.accept(node.val);
      if((node=node.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedForEachAscending(BooleanDblLnkNode node,BooleanDblLnkNode tail,BooleanConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(node==tail){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(BooleanDblLnkNode node,BooleanDblLnkNode tail,BooleanPredicate operator){
    for(;;node=node.next){
      node.val=operator.test(node.val);
      if(node==tail){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(BooleanDblLnkNode node,BooleanConsumer action){
    for(;;){
      action.accept(node.val);
      if((node=node.prev)==null){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(BooleanDblLnkNode node,int size,BooleanConsumer action){
    for(;;node=node.prev){
      action.accept(node.val);
      if(--size!=0){
        return;
      }
    }
  }
  public static  void eraseNode(BooleanDblLnkNode node){
    BooleanDblLnkNode next,prev;
    (next=node.next).prev=(prev=node.prev);
    prev.next=next;
  }
  public static  BooleanDblLnkNode iterateAscending(BooleanDblLnkNode node,int length){
    if(length!=0){
      do{
        node=node.next;
      }while(--length!=0);
    }
    return node;
  }
  public static  BooleanDblLnkNode iterateDescending(BooleanDblLnkNode node,int length){
    if(length!=0){
      do{
        node=node.prev;
      }while(--length!=0);
    }
    return node;
  }
  public static  BooleanDblLnkNode uncheckedIterateAscending(BooleanDblLnkNode node,int length){
    do{
      node=node.next;
    }while(--length!=0);
    return node;
  }
  public static  BooleanDblLnkNode uncheckedIterateDescending(BooleanDblLnkNode node,int length){
    do{
      node=node.prev;
    }while(--length!=0);
    return node;
  }
  public static  boolean uncheckedcontains (BooleanDblLnkNode head,BooleanDblLnkNode tail
  ,boolean val
  ){
    for(;val!=(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearch (BooleanDblLnkNode head
  ,boolean val
  ){
    int index=1;
    for(;val!=(head.val);++index,head=head.next){if((head=head.next)==null){return -1;}}
    return index;
  }
  public static  int uncheckedindexOf (BooleanDblLnkNode head,BooleanDblLnkNode tail
  ,boolean val
  ){
    int index=0;
    for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOf (int length,BooleanDblLnkNode tail
  ,boolean val
  ){
    for(;val!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  void uncheckedCopyInto(boolean[] dst,BooleanDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Boolean[] dst,BooleanDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Object[] dst,BooleanDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(double[] dst,BooleanDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=TypeUtil.castToDouble(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(float[] dst,BooleanDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=TypeUtil.castToFloat(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(long[] dst,BooleanDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=TypeUtil.castToLong(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(int[] dst,BooleanDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(int)TypeUtil.castToByte(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(short[] dst,BooleanDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(short)TypeUtil.castToByte(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(byte[] dst,BooleanDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=TypeUtil.castToByte(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(char[] dst,BooleanDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=TypeUtil.castToChar(curr.val);
      if(length==0){
        return;
      }
    }
  }
  @Override
  public int compareTo(BooleanDblLnkNode that){
    if(this!=that){
      if(this.val){
        if(!that.val){
          return 1;
        }
      }else if(that.val){
        return -1;
      }
    }
    return 0;
  }
}
