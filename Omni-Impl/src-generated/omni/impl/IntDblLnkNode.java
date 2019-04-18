package omni.impl;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
public class IntDblLnkNode implements Comparable<IntDblLnkNode>
{
  public transient IntDblLnkNode prev;
  public transient int val;
  public transient IntDblLnkNode next;
  public IntDblLnkNode(int val)
  {
    this.val=val;
  }
  public IntDblLnkNode(IntDblLnkNode prev,int val)
  {
    this.prev=prev;
    this.val=val;
  }
  public IntDblLnkNode(int val,IntDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public IntDblLnkNode(IntDblLnkNode prev,int val,IntDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  public static  void uncheckedForEachAscending(IntDblLnkNode node,int length,IntConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(IntDblLnkNode node,int length,IntConsumer action){
    for(;;node=node.prev){
      action.accept(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(IntDblLnkNode node,int length,IntUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsInt(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static  void eraseNode(IntDblLnkNode node){
    IntDblLnkNode next,prev;
    (next=node.next).prev=(prev=node.prev);
    prev.next=next;
  }
  public static  IntDblLnkNode uncheckedIterateAscending(IntDblLnkNode node,int length){
    do{
      node=node.next;
    }while(--length!=0);
    return node;
  }
  public static  IntDblLnkNode uncheckedIterateDescending(IntDblLnkNode node,int length){
    do{
      node=node.prev;
    }while(--length!=0);
    return node;
  }
  public static  boolean uncheckedcontains (IntDblLnkNode head,IntDblLnkNode tail
  ,int val
  ){
    for(;val!=(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearch (IntDblLnkNode head,IntDblLnkNode tail
  ,int val
  ){
    int index=1;
    for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedindexOf (IntDblLnkNode head,IntDblLnkNode tail
  ,int val
  ){
    int index=0;
    for(;val!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOf (int length,IntDblLnkNode tail
  ,int val
  ){
    for(;val!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  void uncheckedCopyInto(int[] dst,IntDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Integer[] dst,IntDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Object[] dst,IntDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(double[] dst,IntDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(double)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(float[] dst,IntDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(float)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(long[] dst,IntDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(long)(curr.val);
      if(length==0){
        return;
      }
    }
  }
  @Override
  public int compareTo(IntDblLnkNode that){
    if(this!=that){
      int thisVal,thatVal;
      if((thisVal=this.val)<(thatVal=that.val)){
        return -1;
      }else if(thisVal>thatVal){
        return 1;
      }
    }
    return 0;
  }
}
