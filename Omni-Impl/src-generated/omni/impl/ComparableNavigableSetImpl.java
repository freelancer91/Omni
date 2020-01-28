package omni.impl;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import java.util.Comparator;
import java.util.Collections;
import java.util.function.ToIntFunction;
public abstract class ComparableNavigableSetImpl<E extends Comparable<E>>
  extends ComparableUntetheredArrSeq<E> implements OmniSet.OfRef<E>
{
  ComparableNavigableSetImpl(int head,Comparable<E>[] arr,int tail){
    super(head,arr,tail);
  }
  ComparableNavigableSetImpl(){
    super();
  }
  private static <E> int privateCompare(E key1,E key2){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean add(E key){
    int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedAdd(tail,key,ComparableNavigableSetImpl::privateCompare);
      }
      Comparable<E>[] arr;
      if((arr=this.arr)[tail]!=null)
      {
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
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  /*
  @Override public boolean add(E key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedAdd(tail,key,this::insertionCompare);
      }
      return this.uncheckedAddNull(tail);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  private int insertionCompare(E key1,E key2)
  {
  }
  private boolean uncheckedAddNull(int tail)
  {
  }
  private boolean uncheckedremoveNull(int tail)
  {
  }
  private ToIntFunction<E> getQueryComparator(E key)
  {
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)(Boolean)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)(Byte)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)(Character)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)(Short)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)(Integer)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)(Long)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(float key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)(Float)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(double key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)(Double)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)(Boolean)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)(Byte)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)(Character)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)(Short)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)(Integer)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)(Long)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(float key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)(Float)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(double key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)(Double)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)key));
      }
      return this.uncheckedremoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)key));
      }
      return this.uncheckedremoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Byte key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)key));
      }
      return this.uncheckedremoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Character key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)key));
      }
      return this.uncheckedremoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Short key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)key));
      }
      return this.uncheckedremoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Integer key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)key));
      }
      return this.uncheckedremoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Long key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)key));
      }
      return this.uncheckedremoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)key));
      }
      return this.uncheckedremoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public boolean removeVal(Double key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key!=null){
        return super.uncheckedRemoveMatch(tail,this.getQueryComparator((E)key));
      }
      return this.uncheckedremoveNull(tail);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public E first(){
    return (E)arr[head];
  }
  @SuppressWarnings("unchecked")
  @Override public E last(){
    return (E)arr[tail];
  }
  */
  public static class Ascending<E extends Comparable<E>> extends ComparableNavigableSetImpl<E> implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,Comparable<E>[] arr,int tail){
      super(head,arr,tail);
    }
    /*
    @SuppressWarnings("unchecked")
    @Override public E ceiling(E val){
      int tail;
      if(val!=null && (tail=this.tail)!=-1){
        final var arr=this.arr;
        int head;
        if((head=this.head)<=tail){
          //non-fragmented
          for(var mid=(head+tail)>>>1;;){
            final Comparable<E> tmp;
            if((tmp=arr[mid])==null){
              //We have encountered the greatest possible value (also the default value)
              //We could have only gotten here by starting at a set of size 1 OR by searching for null, so return null
              break;
            }
            switch(Integer.signum(tmp.compareTo(val))){
              case 0:
                return (E)tmp;
              case -1:
                //the encountered value is less then the search value, so search to the right (higher values)
                head=mid+1;
                break;
              default:
                //the encountered value is greater than the search value, so search to the left (lower values)
                tail=mid-1;
            }
            if((mid=(head+tail)>>>1)<head){
              //the midpoint is less then the lower point, so return the head, which is at guaranteed greater then the search value
              return (E)arr[head];
            }
          }
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      return null;
    }
    @Override public E floor(E val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public E higher(E val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public E lower(E val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> headSet(E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> headSet(E toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> tailSet(E fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> tailSet(E fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> subSet(E fromElement,E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Comparator<E> comparator(){
      return Comparator.nullsLast(Comparable::compareTo);
    }
    */
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
    /*
    @SuppressWarnings("unchecked")
    @Override boolean uncheckedAddNull(int tail){
      //add at tail
      final Comparable<E>[] arr;
      if((arr=this.arr)[tail]!=null){
        int head;
        switch(Integer.signum(++tail-(head=this.head))){
          case 0:
            //fragmented must grow
            int arrLength;
            final Comparable<E>[] tmp;
            ArrCopy.uncheckedCopy(arr,0,tmp=new Comparable[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
            ArrCopy.uncheckedCopy(arr,tail,tmp,head-=(arrLength-=tail),arrLength);
            this.head=head;
            this.arr=tmp;
            break;
          case 1:
            //nonfragmented
            if(tail==arr.length){
              if(0==head){
                //must grow
                ArrCopy.uncheckedCopy(arr,0,tmp=new Comparable[head=OmniArray.growBy50Pct(tail)],0,tail);
                this.arr=tmp;
              }else{
                tail=0;
              }
            }
          default:
            //fragmented
        }
        this.tail=tail;
        return true;
      }
      return false;
    }
    @Override boolean uncheckedremoveNull(int tail){
      final Comparable<E>[] arr;
      if((arr=this.arr)[tail]==null){
        if(tail==this.head){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return true;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Object key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[tail]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Boolean key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[tail]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Byte key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[tail]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Character key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[tail]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Short key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[tail]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Integer key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[tail]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Long key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[tail]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Float key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[tail]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Double key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[tail]==null;
      }
      return false;
    }
    @Override ToIntFunction<E> getQueryComparator(E key){
      return (k)->Integer.signum(-k.compareTo(key));
    }
    */
    /*
    @Override int insertionCompare(E key1,E key2){
      return Integer.signum(-key2.compareTo(key1));
    }
    */
  }
  public static class Descending<E extends Comparable<E>> extends ComparableNavigableSetImpl<E> implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,Comparable<E>[] arr,int tail){
      super(head,arr,tail);
    }
    /*
    @SuppressWarnings("unchecked")
    @Override public E ceiling(E val){
      int tail;
      if(val!=null && (tail=this.tail)!=-1){
        final var arr=this.arr;
        int head;
        if((head=this.head)<=tail){
          //non-fragmented
          for(var mid=(head+tail)>>>1;;){
            final Comparable<E> tmp;
            if((tmp=arr[mid])==null){
              //we have encountered the least(greatest) possible value (also the default value)
              //The search value might be to the right, so search there
              head=mid+1;
            }else{
              switch(Integer.signum(tmp.compareTo(val))){
                case 0:
                  return (E) tmp;
                case -1:
                  tail=mid-1;
                  break;
                default:
                  head=mid+1;
              }
            }
            if((mid=(head+tail)>>>1)<head){
              break;
            }
          }
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      return null;
    }
    @Override public E floor(E val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public E higher(E val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public E lower(E val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> headSet(E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> headSet(E toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> tailSet(E fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> tailSet(E fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> subSet(E fromElement,E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Comparator<E> comparator(){
      return Comparator.nullsFirst(Collections.reverseOrder());
    }
    */
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
    /*
    @SuppressWarnings("unchecked")
    @Override boolean uncheckedAddNull(int tail){
      //add at tail
      final Comparable<E>[] arr;
      int head;
      if((arr=this.arr)[head=this.head]!=null){
        switch(Integer.signum(tail-(--head))){
          case 0:
            //fragmented must grow
            final Comparable<E>[] tmp;
            int arrLength;
            ArrCopy.uncheckedCopy(arr,0,tmp=new Comparable[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,++tail);
            ArrCopy.uncheckedCopy(arr,tail,tmp,head-=(arrLength-=tail),arrLength);
            --head;
            this.arr=tmp;
            break;
          default:
            //nonfragmented
            if(head==-1){
              if(tail==(head=arr.length-1)){
                //must grow
                this.tail=(head=OmniArray.growBy50Pct(++tail))-1;
                ArrCopy.uncheckedCopy(arr,0,tmp=new Comparable[head],head-=tail,tail);
                --head;
                this.arr=tmp;
              }
            }
          case -1:
            //fragmented
        }
        this.head=head;
        return true;
      }
      return false;
    }
    @Override boolean uncheckedremoveNull(int tail){
      final Comparable<E>[] arr;
      int head;
      if((arr=this.arr)[head=this.head]==null){
        if(tail==head){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return true;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Object key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[head]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Boolean key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[head]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Byte key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[head]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Character key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[head]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Short key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[head]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Integer key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[head]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Long key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[head]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Float key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[head]==null;
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Double key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(this.head,tail,this.getQueryComparator((E)key));
        }
        return arr[head]==null;
      }
      return false;
    }
    @Override ToIntFunction<E> getQueryComparator(E key){
      return (k)->Integer.signum(k.compareTo(key));
    }
    */
    /*
    @Override int insertionCompare(E key1,E key2){
      return Integer.signum(key2.compareTo(key1));
    }
    */
  }
}
