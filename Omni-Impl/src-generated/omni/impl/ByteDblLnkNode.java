package omni.impl;
import omni.function.ByteConsumer;
import omni.function.ByteUnaryOperator;
public class ByteDblLnkNode implements Comparable<ByteDblLnkNode>
{
  public transient ByteDblLnkNode prev;
  public transient byte val;
  public transient ByteDblLnkNode next;
  public ByteDblLnkNode(byte val)
  {
    this.val=val;
  }
  public ByteDblLnkNode(ByteDblLnkNode prev,byte val)
  {
    this.prev=prev;
    this.val=val;
  }
  public ByteDblLnkNode(byte val,ByteDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public ByteDblLnkNode(ByteDblLnkNode prev,byte val,ByteDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  public static  void uncheckedForEachAscending(ByteDblLnkNode node,int length,ByteConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(ByteDblLnkNode node,int length,ByteConsumer action){
    for(;;node=node.prev){
      action.accept(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(ByteDblLnkNode node,int length,ByteUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsByte(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void eraseNode(ByteDblLnkNode node){
    ByteDblLnkNode next,prev;
    (next=node.next).prev=(prev=node.prev);
    prev.next=next;
  }
  public static  ByteDblLnkNode uncheckedIterateAscending(ByteDblLnkNode node,int length){
    do{
      node=node.next;
    }while(--length!=0);
    return node;
  }
  public static  ByteDblLnkNode uncheckedIterateDescending(ByteDblLnkNode node,int length){
    do{
      node=node.prev;
    }while(--length!=0);
    return node;
  }
  public static  boolean uncheckedcontains (ByteDblLnkNode head,ByteDblLnkNode tail
  ,int val
  ){
    for(;val!=(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearch (ByteDblLnkNode head,ByteDblLnkNode tail
  ,int val
  ){
    int index=1;
    for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedindexOf (ByteDblLnkNode head,ByteDblLnkNode tail
  ,int val
  ){
    int index=0;
    for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOf (int length,ByteDblLnkNode tail
  ,int val
  ){
    for(;val!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  void uncheckedCopyInto(byte[] dst,ByteDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Byte[] dst,ByteDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Object[] dst,ByteDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(double[] dst,ByteDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(double)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(float[] dst,ByteDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(float)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(long[] dst,ByteDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(long)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(int[] dst,ByteDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(int)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(short[] dst,ByteDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(short)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  @Override
  public int compareTo(ByteDblLnkNode that){
    if(this!=that){
      return this.val-that.val;
    }
    return 0;
  }
}
