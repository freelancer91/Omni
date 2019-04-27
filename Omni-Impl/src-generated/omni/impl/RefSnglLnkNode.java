package omni.impl;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Consumer;
public class RefSnglLnkNode<E>
{
  public transient E val;
  public transient RefSnglLnkNode<E> next;
  public RefSnglLnkNode(E val)
  {
    this.val=val;
  }
  public RefSnglLnkNode(E val,RefSnglLnkNode<E> next)
  {
    this.val=val;
    this.next=next;
  }
  public static <E> void uncheckedToString(RefSnglLnkNode<E> curr,RefSnglLnkNode<E> tail,StringBuilder builder){
    for(builder.append(curr.val);curr!=tail;builder.append(',').append(' ').append((curr=curr.next).val)){}
  }
  public static <E> void uncheckedToString(RefSnglLnkNode<E> curr,StringBuilder builder){
    for(;;builder.append(',').append(' ')){
      builder.append(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static <E> int uncheckedHashCode(RefSnglLnkNode<E> curr,RefSnglLnkNode<E> tail){
    int hash=31+Objects.hashCode(curr.val);
    while(curr!=tail){
      hash=(hash*31)+Objects.hashCode((curr=curr.next).val);
    }
    return hash;
  }
  public static <E> int uncheckedHashCode(RefSnglLnkNode<E> curr){
    int hash=31+Objects.hashCode(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+Objects.hashCode(curr.val)){}
    return hash;
  }
  public static <E> void uncheckedForEach(RefSnglLnkNode<E> curr,RefSnglLnkNode<E> tail,Consumer<? super E> action){
    for(action.accept(curr.val);curr!=tail;action.accept((curr=curr.next).val)){}
  }
  public static <E> void uncheckedForEach(RefSnglLnkNode<E> curr,Consumer<? super E> action){
    do{
      action.accept(curr.val);
    }while((curr=curr.next)!=null);
  }
  public static <E> boolean uncheckedcontainsNonNull(RefSnglLnkNode<E> curr,RefSnglLnkNode<E> tail,Object nonNull){
    for(;!nonNull.equals(curr.val);curr=curr.next){if(curr==tail){return false;}}
    return true;
  }
  public static <E> boolean uncheckedcontainsNonNull(RefSnglLnkNode<E> curr
  ,Object nonNull
  ){
    for(;!nonNull.equals(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static <E> int uncheckedsearchNonNull(RefSnglLnkNode<E> curr
  ,Object nonNull
  ){
    int index=1;
    for(;!nonNull.equals(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static <E> boolean uncheckedcontainsNull(RefSnglLnkNode<E> curr
  ){
    for(;null!=(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static <E> int uncheckedsearchNull(RefSnglLnkNode<E> curr
  ){
    int index=1;
    for(;null!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static <E> boolean uncheckedcontains (RefSnglLnkNode<E> curr
  ,Predicate<? super E> pred
  ){
    for(;!pred.test(curr.val);){if((curr=curr.next)==null){return false;}}
    return true;
  }
  public static <E> int uncheckedsearch (RefSnglLnkNode<E> curr
  ,Predicate<? super E> pred
  ){
    int index=1;
    for(;!pred.test(curr.val);++index){if((curr=curr.next)==null){return -1;}}
    return index;
  }
  public static <E> void uncheckedCopyInto(RefSnglLnkNode<E> curr,Object[] dst){
    for(int dstOffset=0;;++dstOffset){
      dst[dstOffset]=(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
}