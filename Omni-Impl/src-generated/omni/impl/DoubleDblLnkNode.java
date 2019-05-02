package omni.impl;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import omni.util.HashUtil;
public class DoubleDblLnkNode implements Comparable<DoubleDblLnkNode>
{
  public transient DoubleDblLnkNode prev;
  public transient double val;
  public transient DoubleDblLnkNode next;
  public DoubleDblLnkNode(double val)
  {
    this.val=val;
  }
  public DoubleDblLnkNode(DoubleDblLnkNode prev,double val)
  {
    this.prev=prev;
    this.val=val;
  }
  public DoubleDblLnkNode(double val,DoubleDblLnkNode next)
  {
    this.val=val;
    this.next=next;
  }
  public DoubleDblLnkNode(DoubleDblLnkNode prev,double val,DoubleDblLnkNode next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  //TODO example this implementation to other array types
  public static  void uncheckedCopyFrom(double[] src,int length,DoubleDblLnkNode dst){
    for(;;dst=dst.prev)
    {
      dst.val=(double)src[--length];
      if(length==0)
      {
        return;
      }
    }
  }
  public static  void uncheckedToString(DoubleDblLnkNode curr,DoubleDblLnkNode tail,StringBuilder builder){
    for(;;curr=curr.next,builder.append(',').append(' ')){
      builder.append(curr.val);
      if(curr==tail){
        return;
      }
    }
  }
  public static  int uncheckedHashCode(DoubleDblLnkNode curr,DoubleDblLnkNode tail){
    int hash=31+HashUtil.hashDouble(curr.val);
    while(curr!=tail){
      hash=(hash*31)+HashUtil.hashDouble((curr=curr.next).val);
    }
    return hash;
  }
  public static  void uncheckedForEachAscending(DoubleDblLnkNode node,int size,DoubleConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(DoubleDblLnkNode node,int size,DoubleUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsDouble(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void uncheckedForEachAscending(DoubleDblLnkNode node,DoubleConsumer action){
    for(;;){
      action.accept(node.val);
      if((node=node.next)==null){
        return;
      }
    }
  }
  public static  void uncheckedForEachAscending(DoubleDblLnkNode node,DoubleDblLnkNode tail,DoubleConsumer action){
    for(;;node=node.next){
      action.accept(node.val);
      if(node==tail){
        return;
      }
    }
  }
  public static  void uncheckedReplaceAll(DoubleDblLnkNode node,DoubleDblLnkNode tail,DoubleUnaryOperator operator){
    for(;;node=node.next){
      node.val=operator.applyAsDouble(node.val);
      if(node==tail){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(DoubleDblLnkNode node,DoubleConsumer action){
    for(;;){
      action.accept(node.val);
      if((node=node.prev)==null){
        return;
      }
    }
  }
  public static  void uncheckedForEachDescending(DoubleDblLnkNode node,int size,DoubleConsumer action){
    for(;;node=node.prev){
      action.accept(node.val);
      if(--size==0){
        return;
      }
    }
  }
  public static  void eraseNode(DoubleDblLnkNode node){
    DoubleDblLnkNode next,prev;
    (next=node.next).prev=(prev=node.prev);
    prev.next=next;
  }
  public static  DoubleDblLnkNode iterateAscending(DoubleDblLnkNode node,int length){
    if(length!=0){
      do{
        node=node.next;
      }while(--length!=0);
    }
    return node;
  }
  public static  DoubleDblLnkNode iterateDescending(DoubleDblLnkNode node,int length){
    if(length!=0){
      do{
        node=node.prev;
      }while(--length!=0);
    }
    return node;
  }
  public static  DoubleDblLnkNode uncheckedIterateAscending(DoubleDblLnkNode node,int length){
    do{
      node=node.next;
    }while(--length!=0);
    return node;
  }
  public static  DoubleDblLnkNode uncheckedIterateDescending(DoubleDblLnkNode node,int length){
    do{
      node=node.prev;
    }while(--length!=0);
    return node;
  }
  public static  boolean uncheckedcontainsBits(DoubleDblLnkNode head,DoubleDblLnkNode tail
  ,long bits
  ){
    for(;bits!=Double.doubleToRawLongBits(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearchBits(DoubleDblLnkNode head
  ,long bits
  ){
    int index=1;
    for(;bits!=Double.doubleToRawLongBits(head.val);++index){if((head=head.next)==null){return -1;}}
    return index;
  }
  public static  int uncheckedindexOfBits(DoubleDblLnkNode head,DoubleDblLnkNode tail
  ,long bits
  ){
    int index=0;
    for(;bits!=Double.doubleToRawLongBits(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOfBits(int length,DoubleDblLnkNode tail
  ,long bits
  ){
    for(;bits!=Double.doubleToRawLongBits(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  boolean uncheckedcontains0(DoubleDblLnkNode head,DoubleDblLnkNode tail
  ){
    for(;0!=(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearch0(DoubleDblLnkNode head
  ){
    int index=1;
    for(;0!=(head.val);++index){if((head=head.next)==null){return -1;}}
    return index;
  }
  public static  int uncheckedindexOf0(DoubleDblLnkNode head,DoubleDblLnkNode tail
  ){
    int index=0;
    for(;0!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOf0(int length,DoubleDblLnkNode tail
  ){
    for(;0!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  boolean uncheckedcontainsNaN(DoubleDblLnkNode head,DoubleDblLnkNode tail
  ){
    for(;!Double.isNaN(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static  int uncheckedsearchNaN(DoubleDblLnkNode head
  ){
    int index=1;
    for(;!Double.isNaN(head.val);++index){if((head=head.next)==null){return -1;}}
    return index;
  }
  public static  int uncheckedindexOfNaN(DoubleDblLnkNode head,DoubleDblLnkNode tail
  ){
    int index=0;
    for(;!Double.isNaN(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static  int uncheckedlastIndexOfNaN(int length,DoubleDblLnkNode tail
  ){
    for(;!Double.isNaN(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static  void uncheckedCopyInto(double[] dst,DoubleDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Double[] dst,DoubleDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  public static  void uncheckedCopyInto(Object[] dst,DoubleDblLnkNode curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  @Override
  public int compareTo(DoubleDblLnkNode that){
    if(this!=that){
      return Double.compare(this.val,that.val);
    }
    return 0;
  }
}
