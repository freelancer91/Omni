package omni.util;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
public class RefSnglLnkNode<E> implements Comparable<RefSnglLnkNode<E>>
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
  public static <E> void uncheckedToString(RefSnglLnkNode<E> curr,StringBuilder builder){
    for(;;builder.append(',').append(' ')){
      builder.append(curr.val);
      if((curr=curr.next)==null){
        return;
      }
    }
  }
  public static <E> void pullSurvivorsDown(RefSnglLnkNode<E> prev,Predicate<? super E> filter,long[] survivorSet,int numSurvivors,int numRemoved){
    int wordOffset;
    for(long word=survivorSet[wordOffset=0],marker=1L;;){
      var curr=prev.next;
      if((marker&word)==0){
        do{
          if(--numRemoved==0){
            prev.next=null;
            return;
          }
          if((marker<<=1)==0){
            word=survivorSet[++wordOffset];
            marker=1L;
          }
          curr=curr.next;
        }while((marker&word)==0);
        prev.next=curr;
      }
      if(--numSurvivors==0){
        return;
      }
      if((marker<<=1)==0){
         word=survivorSet[++wordOffset];
         marker=1L;
      }
      prev=curr;
    }
  }
  public static <E> int markSurvivors(RefSnglLnkNode<E> curr,Predicate<? super E> filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test(curr.val)){
          word|=marker;
          ++numSurvivors;
        }
        if((curr=curr.next)==null){
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
      }
      while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  public static <E> void pullSurvivorsDown(RefSnglLnkNode<E> prev,long word,int numSurvivors,int numRemoved){
    for(long marker=1L;;marker<<=1){
      var curr=prev.next;
      if((marker&word)==0){
        do{
          if(--numRemoved==0){
            prev.next=null;
            return;
          }
          curr=curr.next;
        }while(((marker<<=1)&word)==0);
        prev.next=curr;
      }
      if(--numSurvivors==0){
        return;
      }
      prev=curr;
    }
  }
  public static <E> long markSurvivors(RefSnglLnkNode<E> curr,Predicate<? super E> filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  static <E> RefSnglLnkNode<E> uncheckedSkip(RefSnglLnkNode<E> curr,int numToSkip){
    while(--numToSkip!=0){
      curr=curr.next;
    }
    return curr;
  }
  static <E> RefSnglLnkNode<E> skip(RefSnglLnkNode<E> curr,int numToSkip){
    if(numToSkip!=0){
      return uncheckedSkip(curr,numToSkip);
    }
    return curr;
  }
  public static <E> int retainSurvivors(RefSnglLnkNode<E> prev, final Predicate<? super E> filter){
    int numSurvivors=1;
    outer:for(RefSnglLnkNode<E> next;(next=prev.next)!=null;++numSurvivors,prev=next){
      if(filter.test(next.val)){
        do{
          if((next=next.next)==null){
            prev.next=null;
            break outer;
          }
        }while(filter.test(next.val));
        prev.next=next;
      }
    }
    return numSurvivors;
  }
  public static <E> int retainTrailingSurvivors(RefSnglLnkNode<E> prev,RefSnglLnkNode<E> curr,final Predicate<? super E> filter){
    int numSurvivors=0;
    outer:for(;;curr=curr.next){
      if(curr==null){
        prev.next=null;
        break;
      }
      if(!filter.test(curr.val)){
        prev.next=curr;
        do{
          ++numSurvivors;
          if((curr=(prev=curr).next)==null){
            break outer;
          }
        }
        while(!filter.test(curr.val));
      }
    }
    return numSurvivors;
  }
  public static <E> int uncheckedHashCode(RefSnglLnkNode<E> curr){
    int hash=31+Objects.hashCode(curr.val);
    for(;(curr=curr.next)!=null;hash=(hash*31)+Objects.hashCode(curr.val)){}
    return hash;
  }
  public static <E> void uncheckedForEach(RefSnglLnkNode<E> curr,Consumer<? super E> action){
    do{
      action.accept(curr.val);
    }while((curr=curr.next)!=null);
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
  @SuppressWarnings("unchecked")
  @Override
  public int compareTo(RefSnglLnkNode<E> that)
  {
    if(this!=that)
    {
      return ((Comparable<E>)this.val).compareTo(that.val);
    }
    return 0;
  }
}
