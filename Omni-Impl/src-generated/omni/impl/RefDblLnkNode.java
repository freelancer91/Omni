package omni.impl;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
public class RefDblLnkNode<E> implements Comparable<RefDblLnkNode<E>>
{
  public transient RefDblLnkNode<E> prev;
  public transient E val;
  public transient RefDblLnkNode<E> next;
  public RefDblLnkNode(E val)
  {
    this.val=val;
  }
  public RefDblLnkNode(RefDblLnkNode<E> prev,E val)
  {
    this.prev=prev;
    this.val=val;
  }
  public RefDblLnkNode(E val,RefDblLnkNode<E> next)
  {
    this.val=val;
    this.next=next;
  }
  public RefDblLnkNode(RefDblLnkNode<E> prev,E val,RefDblLnkNode<E> next)
  {
    this.prev=prev;
    this.val=val;
    this.next=next;
  }
  public static <E> void uncheckedForEachAscending(RefDblLnkNode<E> node,int length,Consumer<? super E> action){
    for(;;node=node.next){
      action.accept(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static <E> void uncheckedForEachDescending(RefDblLnkNode<E> node,int length,Consumer<? super E> action){
    for(;;node=node.prev){
      action.accept(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static <E> void uncheckedReplaceAll(RefDblLnkNode<E> node,int length,UnaryOperator<E> operator){
    for(;;node=node.next){
      node.val=operator.apply(node.val);
      if(--length==0){
        return;
      }
    }
  }
  public static <E> void eraseNode(RefDblLnkNode<E> node){
    RefDblLnkNode<E> next,prev;
    (next=node.next).prev=(prev=node.prev);
    prev.next=next;
  }
  public static <E> RefDblLnkNode<E> uncheckedIterateAscending(RefDblLnkNode<E> node,int length){
    do{
      node=node.next;
    }while(--length!=0);
    return node;
  }
  public static <E> RefDblLnkNode<E> uncheckedIterateDescending(RefDblLnkNode<E> node,int length){
    do{
      node=node.prev;
    }while(--length!=0);
    return node;
  }
  public static <E> boolean uncheckedcontainsNonNull(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ,Object nonNull
  ){
    for(;!nonNull.equals(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static <E> int uncheckedsearchNonNull(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ,Object nonNull
  ){
    int index=1;
    for(;!nonNull.equals(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static <E> int uncheckedindexOfNonNull(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ,Object nonNull
  ){
    int index=0;
    for(;!nonNull.equals(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static <E> int uncheckedlastIndexOfNonNull(int length,RefDblLnkNode<E> tail
  ,Object nonNull
  ){
    for(;!nonNull.equals(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static <E> boolean uncheckedcontainsNull(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ){
    for(;null!=(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static <E> int uncheckedsearchNull(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ){
    int index=1;
    for(;null!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static <E> int uncheckedindexOfNull(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ){
    int index=0;
    for(;null!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static <E> int uncheckedlastIndexOfNull(int length,RefDblLnkNode<E> tail
  ){
    for(;null!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static <E> boolean uncheckedcontains (RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ,Predicate<? super E> pred
  ){
    for(;!pred.test(head.val);head=head.next){if(head==tail){return false;}}
    return true;
  }
  public static <E> int uncheckedsearch (RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ,Predicate<? super E> pred
  ){
    int index=1;
    for(;!pred.test(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static <E> int uncheckedindexOf (RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ,Predicate<? super E> pred
  ){
    int index=0;
    for(;!pred.test(head.val);++index,head=head.next){if(head==tail){return -1;}}
    return index;
  }
  public static <E> int uncheckedlastIndexOf (int length,RefDblLnkNode<E> tail
  ,Predicate<? super E> pred
  ){
    for(;!pred.test(tail.val);tail=tail.prev){if(--length==0){return -1;}}
    return length-1;
  }
  public static <E> void uncheckedCopyInto(Object[] dst,RefDblLnkNode<E> curr,int length){
    for(;;curr=curr.prev){
      dst[--length]=(curr.val);
      if(length==0){
        return;
      }
    }
  }
  @SuppressWarnings("unchecked")
  @Override
  public int compareTo(RefDblLnkNode<E> that){
    if(this!=that){
      return ((Comparable<E>)this.val).compareTo(that.val);
    }
    return 0;
  }
}
