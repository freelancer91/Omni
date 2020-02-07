package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.ArrCopy;
import omni.api.OmniIterator;
import omni.util.OmniArray;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.ToIntFunction;
public abstract class ComparableNavigableSetImpl<E extends Comparable<E>>
  extends ComparableUntetheredArrSeq<E> implements OmniNavigableSet.OfRef<E>
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
  private static <E extends Comparable<E>> ToIntFunction<E> getSearchFunction(E key){
    return (k)->privateCompare(k,key);
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
  @SuppressWarnings("unchecked")
  private static <E extends Comparable<E>> E nonfragmentedCeilingImpl(Comparable<E>[] arr,int head,int tail,E val){
    //assert arr!=null;
    //assert val!=null;
    //assert tail>=head;
    //assert head>=0;
    //assert tail<arr.length-1;
    //assert arr[tail+1]==null || arr[tail+1].compareTo(val)>0;
    for(;;){
      final int mid;
      final Comparable<E> tmp;
      switch(Integer.signum((tmp=arr[mid=(head+tail)>>>1]).compareTo(val))){
        case 1:
          if((tail=mid-1)>=head){
            continue;
          }
        case 0:
          return (E)tmp;
        default:
          if((head=mid+1)>tail){
            return (E)arr[head];
          }
      }
    }
  }
  @SuppressWarnings("unchecked")
  private static <E extends Comparable<E>> E nonfragmentedFloorImpl(Comparable<E>[] arr,int head,int tail,E val){
    //assert arr!=null;
    //assert val!=null;
    //assert tail>=head;
    //assert head>0;
    //assert tail<arr.length;
    //assert arr[head-1]!=null;
    //assert arr[head-1].compareTo(val)<0;
    for(;;){
      final int mid;
      final Comparable<E> tmp;
      switch(Integer.signum((tmp=arr[mid=(head+tail)>>>1]).compareTo(val))){
        case -1:
          if((head=mid+1)<=tail){
            continue;
          }
        case 0:
          return (E)tmp;
        default:
          if((tail=mid-1)<head){
            return (E)arr[tail];
          }
      }
    }
  }
  @SuppressWarnings("unchecked")
  private static <E extends Comparable<E>> E nonfragmentedHigherImpl(Comparable<E>[] arr,int head,int tail,E val){
    //assert arr!=null;
    //assert val!=null;
    //assert tail>=head;
    //assert head>=0;
    //assert tail<arr.length-1;
    //assert arr[tail+1]==null || arr[tail+1].compareTo(val)>0;
    for(;;){
      final int mid;
      final Comparable<E> tmp;
      switch(Integer.signum((tmp=arr[mid=(head+tail)>>>1]).compareTo(val))){
        case 1:
          if((tail=mid-1)>=head){
            continue;
          }
          return (E)tmp;
        case 0:
          return (E)arr[mid+1];
        default:
          if((head=mid+1)>tail){
            return (E)arr[head];
          }
      }
    }
  }
  @SuppressWarnings("unchecked")
  private static <E extends Comparable<E>> E nonfragmentedLowerImpl(Comparable<E>[] arr,int head,int tail,E val){
    //assert arr!=null;
    //assert val!=null;
    //assert tail>=head;
    //assert head>0;
    //assert tail<arr.length;
    //assert arr[head-1]!=null;
    //assert arr[head-1].compareTo(val)<0;
    for(;;){
      final int mid;
      final Comparable<E> tmp;
      switch(Integer.signum((tmp=arr[mid=(head+tail)>>>1]).compareTo(val))){
        case -1:
          if((head=mid+1)<=tail){
            continue;
          }
          return (E)tmp;
        case 0:
          return (E)arr[mid-1];
        default:
          if((tail=mid-1)<head){
            return (E)arr[tail];
          }
      }
    }
  }
  @SuppressWarnings("unchecked")
  private static <E extends Comparable<E>> E fragmentedCeilingImpl(Comparable<E>[] arr,int head,int tail,E val){
    final Comparable<E> tmp;
    final int mid;
    switch((tmp=arr[mid=arr.length-1]).compareTo(val)){
      case 1:
        if((tail=mid-1)>=head){
          break;
        }
      case 0:
        return (E)tmp;
      default:
        if((head=0)>tail){
          return (E)arr[0];
        }
    }
    return nonfragmentedCeilingImpl(arr,head,tail,val);
  }
  @SuppressWarnings("unchecked")
  private static <E extends Comparable<E>> E fragmentedFloorImpl(Comparable<E>[] arr,int head,int tail,E val){
    final Comparable<E> tmp;
    final int mid;
    switch((tmp=arr[mid=arr.length-1]).compareTo(val)){
      case -1:
        if((head=0)<=tail){
          break;
        }
      case 0:
        return (E)tmp;
      default:
        if((tail=mid-1)<head){
          return (E)arr[tail];
        }
    }
    return nonfragmentedFloorImpl(arr,head,tail,val);
  }
  @SuppressWarnings("unchecked")
  private static <E extends Comparable<E>> E fragmentedHigherImpl(Comparable<E>[] arr,int head,int tail,E val){
    final Comparable<E> tmp;
    final int mid;
    switch((tmp=arr[mid=arr.length-1]).compareTo(val)){
      case 1:
        if((tail=mid-1)>=head){
          break;
        }
        return (E)tmp;
      default:
        if((head=0)<=tail){
          break;
        }
      case 0:
        return (E)arr[0];
    }
    return nonfragmentedHigherImpl(arr,head,tail,val);
  }
  @SuppressWarnings("unchecked")
  private static <E extends Comparable<E>> E fragmentedLowerImpl(Comparable<E>[] arr,int head,int tail,E val){
    final Comparable<E> tmp;
    final int mid;
    switch((tmp=arr[mid=arr.length-1]).compareTo(val)){
      case -1:
        if((head=0)<=tail){
          break;
        }
        return (E)tmp;
      default:
        if((tail=mid-1)>=head){
          break;
        }
      case 0:
        return (E)arr[mid-1];
    }
    return nonfragmentedLowerImpl(arr,head,tail,val);
  }
  @SuppressWarnings("unchecked")
  @Override public E ceiling(E val){
    final int tail;
    if(val!=null && (tail=this.tail)!=-1){
      final Comparable<E>[] arr;
      final Comparable<E> tmp;
      if((tmp=(arr=this.arr)[tail])==null){
        final int head;
        switch(Integer.signum(tail-(head=this.head))){
          case 1:
            return nonfragmentedCeilingImpl(arr,head,tail-1,val);
          default:
            return fragmentedCeilingImpl(arr,head,tail-1,val);
          case 0:
        }
      }else{
        switch(Integer.signum(tmp.compareTo(val))){
          case 1:
            final int head;
            switch(Integer.signum(tail-(head=this.head))){
              case 1:
                return nonfragmentedCeilingImpl(arr,head,tail-1,val);
              default:
                return fragmentedCeilingImpl(arr,head,tail-1,val);
              case 0:
            }
           case 0:
            return (E)tmp;
          default:
        }
      }
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  @Override public E higher(E val){
    final int tail;
    if(val!=null && (tail=this.tail)!=-1){
      final Comparable<E>[] arr;
      final Comparable<E> tmp;
      if((tmp=(arr=this.arr)[tail])==null){
        final int head;
        switch(Integer.signum(tail-(head=this.head))){
          case 1:
            return nonfragmentedHigherImpl(arr,head,tail-1,val);
          default:
            return fragmentedHigherImpl(arr,head,tail-1,val);
          case 0:
        }
      }else{
        if(tmp.compareTo(val)>0){
          final int head;
          switch(Integer.signum(tail-(head=this.head))){
            case 0:
              return (E)tmp;
            case 1:
              return nonfragmentedHigherImpl(arr,head,tail-1,val);
            default:
              return fragmentedHigherImpl(arr,head,tail-1,val);
          }
        }
      }
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  @Override public E floor(E val){
    final int tail;
    if((tail=this.tail)!=-1){
      if(val==null){
        return (E)arr[tail];
      }
      final int head;
      final Comparable<E>[] arr;
      final Comparable<E> tmp;
      if((tmp=(arr=this.arr)[head=this.head])!=null){
        switch(Integer.signum(tmp.compareTo(val))){
          case -1:
            switch(Integer.signum(tail-head)){
              case 1:
                return nonfragmentedFloorImpl(arr,head+1,tail,val);
              default:
                return fragmentedFloorImpl(arr,head+1,tail,val);
              case 0:
            }
          case 0:
            return (E)tmp;
          default:
        }
      }
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  @Override public E lower(E val){
    final int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final Comparable<E> tmp;
      if(val==null){
        if((tmp=arr[tail])==null){
          switch(Integer.signum(tail-head)){
            default:
              if(tail==0){
                return (E)arr[arr.length-1];
              }
            case 1:
              return (E)arr[tail-1];
            case 0:
          }
        }
        return (E)tmp;
      }else{
        final int head;
        if((tmp=arr[head=this.head])!=null && tmp.compareTo(val)<0){
          switch(Integer.signum(tail-head)){
            case 0:
              return (E)tmp;
            case 1:
              return nonfragmentedLowerImpl(arr,head+1,tail,val);
            default:
              return fragmentedLowerImpl(arr,head+1,tail,val);
          }
        }
      }
    }
    return null;
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
    @Override public OmniNavigableSet.OfRef<E> descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> headSet(E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> headSet(E toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> tailSet(E fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> tailSet(E fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> subSet(E fromElement,E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> subSet(E fromElement,boolean inclusiveFrom,E toElement,boolean inclusiveTo){
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
    @Override public OmniNavigableSet.OfRef<E> descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> headSet(E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> headSet(E toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> tailSet(E fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> tailSet(E fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> subSet(E fromElement,E toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfRef<E> subSet(E fromElement,boolean inclusiveFrom,E toElement,boolean inclusiveTo){
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
  }
}
