package omni.impl;
import omni.function.ShortConsumer;
import omni.function.ShortUnaryOperator;
public class ShortDblLnkNode implements Comparable<ShortDblLnkNode>
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
  public static  void uncheckedForEachAscending(ShortDblLnkNode node,int length,ShortConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(ShortDblLnkNode node,int length,ShortConsumer action){
    for(;;node=node.prev){
      action.accept(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(ShortDblLnkNode node,int length,ShortUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsShort(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void eraseNode(ShortDblLnkNode node){
    ShortDblLnkNode next,prev;
    (next=node.next).prev=(prev=node.prev);
    prev.next=next;
  }
  public static  ShortDblLnkNode uncheckedIterateAscending(ShortDblLnkNode node,int length){
    do{
      node=node.next;
    }while(--length!=0);
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
  public static  int uncheckedsearch (ShortDblLnkNode head,ShortDblLnkNode tail
  ,int val
  ){
    int index=1;
    for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
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
  @Override
  public int compareTo(ShortDblLnkNode that){
    if(this!=that){
      return this.val-that.val;
    }
    return 0;
  }
}
