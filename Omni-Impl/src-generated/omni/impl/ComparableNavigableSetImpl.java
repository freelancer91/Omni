package omni.impl;
import java.io.Serializable;
import omni.api.OmniSortedSet;
import omni.util.ArrCopy;
import omni.api.OmniIterator;
import omni.util.OmniArray;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.util.OmniArray;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.ToIntFunction;
public abstract class ComparableNavigableSetImpl<E extends Comparable<E>>
  extends ComparableUntetheredArrSeq<E> implements OmniSortedSet.OfRef<E>
{
  ComparableNavigableSetImpl(int head,Comparable<E>[] arr,int tail){
    super(head,arr,tail);
  }
  ComparableNavigableSetImpl(){
    super();
  }
  private static <E extends Comparable<E>> int privateCompare(E key1,E key2){
    if(key2!=null){
      return Integer.signum(-key2.compareTo(key1));
    }
    return -1;
  }
  private static <E extends Comparable<E>> ToIntFunction<E> getSearchFunction(E key){
    return (k)->privateCompare(k,key);
  }
  @SuppressWarnings("unchecked")
  private boolean uncheckedAddUndefined(int tail){
    Comparable<E>[] arr;
    if((arr=this.arr)[tail]!=null){
      int head;
      switch(Integer.signum((++tail)-(head=this.head))){
        case 0:
          //fragmented must grow
          final Comparable<E>[] tmp;
          int arrLength;
          ArrCopy.uncheckedCopy(arr,0,tmp=new Comparable[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
          ArrCopy.uncheckedCopy(arr,tail,tmp,head-=(arrLength-=tail),arrLength);
          this.head=head;
          this.arr=tmp;
          break;
        default:
          //nonfragmented
          if(tail==arr.length){
            if(head==0){
              //must grow
              ArrCopy.uncheckedCopy(arr,0,arr=new Comparable[OmniArray.growBy50Pct(tail)],0,tail);
              this.arr=arr;
            }else{
              tail=0;
            }
          }
        case -1:
          //fragmented
      }
      this.tail=tail;
      return true;
    }
    return false;
  }
  @Override public boolean add(E key){
    int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedAdd(tail,key,ComparableNavigableSetImpl::privateCompare);
      }
      return uncheckedAddUndefined(tail);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key instanceof Comparable){
        return super.uncheckedContainsMatch(head,tail,getSearchFunction((E)key));
      }else if(key==null){
        return arr[tail]==null;
      }
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key instanceof Comparable){
        return super.uncheckedRemoveMatch(tail,getSearchFunction((E)key));
      }else if(key==null){
        return uncheckedRemoveNull(tail);
      }
    }
    return false;
  }
  private boolean uncheckedRemoveNull(int tail){
    final Comparable<E>[] arr;
    if(((arr=this.arr)[tail])==null){
      switch(Integer.signum(tail-head)){
        case 0:
          this.tail=-1;
          break;
        case -1:
          if(tail==0){
            this.tail=arr.length-1;
            break;
          }
        default:
          this.tail=tail-1;
      }
      return true;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,getSearchFunction((E)key));
      }
      return uncheckedRemoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(Boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedContainsMatch(head,tail,getSearchFunction((E)key));
      }
      return arr[tail]==null;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Byte key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,getSearchFunction((E)key));
      }
      return uncheckedRemoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(Byte key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedContainsMatch(head,tail,getSearchFunction((E)key));
      }
      return arr[tail]==null;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Character key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,getSearchFunction((E)key));
      }
      return uncheckedRemoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(Character key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedContainsMatch(head,tail,getSearchFunction((E)key));
      }
      return arr[tail]==null;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Short key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,getSearchFunction((E)key));
      }
      return uncheckedRemoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(Short key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedContainsMatch(head,tail,getSearchFunction((E)key));
      }
      return arr[tail]==null;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Integer key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,getSearchFunction((E)key));
      }
      return uncheckedRemoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(Integer key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedContainsMatch(head,tail,getSearchFunction((E)key));
      }
      return arr[tail]==null;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Long key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,getSearchFunction((E)key));
      }
      return uncheckedRemoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(Long key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedContainsMatch(head,tail,getSearchFunction((E)key));
      }
      return arr[tail]==null;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,getSearchFunction((E)key));
      }
      return uncheckedRemoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(Float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedContainsMatch(head,tail,getSearchFunction((E)key));
      }
      return arr[tail]==null;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Double key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,getSearchFunction((E)key));
      }
      return uncheckedRemoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(Double key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedContainsMatch(head,tail,getSearchFunction((E)key));
      }
      return arr[tail]==null;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction((E)(Boolean)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction((E)(Boolean)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction((E)(Byte)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction((E)(Byte)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction((E)(Character)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction((E)(Character)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction((E)(Short)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction((E)(Short)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction((E)(Integer)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction((E)(Integer)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction((E)(Long)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction((E)(Long)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(float key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction((E)(Float)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(float key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction((E)(Float)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(double key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction((E)(Double)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(double key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction((E)(Double)key));
  }
  private static class AscendingFullView<E extends Comparable<E>> extends ComparableUntetheredArrSeq.AbstractFullView<E> implements OmniSortedSet.OfRef<E>,Cloneable,Serializable
  {
    AscendingFullView(ComparableUntetheredArrSeq<E> root){
      super(root);
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
      @SuppressWarnings("unchecked")
    @Override public Comparable<E>[] toArray(){
      int tail;
      final ComparableUntetheredArrSeq<E> root;
      if((tail=(root=this.root).tail)!=-1){
        Comparable<E>[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new Comparable[size],0,size);
        }else{
          final Comparable<E>[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new Comparable[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return (Comparable<E>[])OmniArray.OfRef.DEFAULT_COMPARABLE_ARR;
    }
  }
  private static class DescendingFullView<E extends Comparable<E>> extends ComparableUntetheredArrSeq.AbstractFullView<E> implements OmniSortedSet.OfRef<E>,Cloneable,Serializable
  {
    DescendingFullView(ComparableUntetheredArrSeq<E> root){
      super(root);
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
      @SuppressWarnings("unchecked")
    @Override public Comparable<E>[] toArray(){
      int tail;
      final ComparableUntetheredArrSeq<E> root;
      if((tail=(root=this.root).tail)!=-1){
        Comparable<E>[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Comparable[size+=arr.length],tail,size-tail);
        }else{
          final Comparable<E>[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Comparable[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return (Comparable<E>[])OmniArray.OfRef.DEFAULT_COMPARABLE_ARR;
    }
  }
  public static class Ascending<E extends Comparable<E>> extends ComparableNavigableSetImpl<E> implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,Comparable<E>[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public Comparator<E> comparator(){
      return Comparator.nullsLast(Comparable::compareTo);
    }
    @SuppressWarnings("unchecked")
    @Override public E first(){
      return (E)arr[head];
    }
    @SuppressWarnings("unchecked")
    @Override public E last(){
      return (E)arr[tail];
    }
    @Override public OmniSortedSet.OfRef<E> headSet(E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfRef<E> tailSet(E fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfRef<E> subSet(E fromElement,E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @SuppressWarnings("unchecked")
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final Comparable<E>[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new Comparable[size],0,size);
          cloneTail=size-1;
        }else{
          final Comparable<E>[] arr;
          dst=new Comparable[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Ascending<E>(0,dst,cloneTail);
      }
      return new Ascending<E>();
    }
  }
  public static class Descending<E extends Comparable<E>> extends ComparableNavigableSetImpl<E> implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,Comparable<E>[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public Comparator<E> comparator(){
      return Comparator.nullsFirst(Collections.reverseOrder());
    }
    @SuppressWarnings("unchecked")
    @Override public E first(){
      return (E)arr[tail];
    }
    @SuppressWarnings("unchecked")
    @Override public E last(){
      return (E)arr[head];
    }
    @Override public OmniSortedSet.OfRef<E> headSet(E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfRef<E> tailSet(E fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfRef<E> subSet(E fromElement,E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @SuppressWarnings("unchecked")
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final Comparable<E>[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new Comparable[size],0,size);
          cloneTail=size-1;
        }else{
          final Comparable<E>[] arr;
          dst=new Comparable[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Descending<E>(0,dst,cloneTail);
      }
      return new Descending<E>();
    }
        @SuppressWarnings("unchecked")
      @Override public Comparable<E>[] toArray(){
        int tail;
        if((tail=this.tail)!=-1){
          Comparable<E>[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Comparable[size+=arr.length],tail,size-tail);
          }else{
            final Comparable<E>[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Comparable[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return (Comparable<E>[])OmniArray.OfRef.DEFAULT_COMPARABLE_ARR;
      }
      /*
      @Override public E ceiling(E val){
        return super.floor(val);
      }
      @Override public E floor(E val){
        return super.ceiling(val);
      }
      @Override public E higher(E val){
        return super.lower(val);
      }
      @Override public E lower(E val){
        return super.higher(val);
      }
      */
  }
}
