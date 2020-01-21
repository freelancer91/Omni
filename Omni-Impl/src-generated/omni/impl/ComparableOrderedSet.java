package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
import java.util.function.ToIntFunction;
public abstract class ComparableOrderedSet<E extends Comparable<E>>
  extends ComparableUntetheredArrSeq<E> implements OmniNavigableSet.OfRef<E>
{
  ComparableOrderedSet(int head,Comparable<E>[] arr,int tail){
    super(head,arr,tail);
  }
  ComparableOrderedSet(){
    super();
  }
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
  abstract int insertionCompare(E key1,E key2);
  abstract boolean uncheckedAddNull(int tail);
  abstract boolean uncheckedremoveNull(int tail);
  abstract ToIntFunction<E> getQueryComparator(E key);
  @SuppressWarnings("unchecked")
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,this.getQueryComparator((E)(Boolean)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,this.getQueryComparator((E)(Byte)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,this.getQueryComparator((E)(Character)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,this.getQueryComparator((E)(Short)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,this.getQueryComparator((E)(Integer)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,this.getQueryComparator((E)(Long)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(float key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,this.getQueryComparator((E)(Float)key));
  }
  @SuppressWarnings("unchecked")
  @Override public boolean contains(double key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,this.getQueryComparator((E)(Double)key));
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
  public static class Ascending<E extends Comparable<E>>
    extends ComparableOrderedSet<E> implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,Comparable<E>[] arr,int tail){
      super(head,arr,tail);
    }
    @Override int insertionCompare(E key1,E key2){
      return Integer.signum(-key2.compareTo(key1));
    }
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
    @Override ToIntFunction<E> getQueryComparator(E key){
      return (k)->Integer.signum(-k.compareTo(key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
        }
        return arr[tail]==null;
      }
      return false;
    }
  }
  public static class Descending<E extends Comparable<E>>
    extends ComparableOrderedSet<E> implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,Comparable<E>[] arr,int tail){
      super(head,arr,tail);
    }
    @Override int insertionCompare(E key1,E key2){
      return Integer.signum(key2.compareTo(key1));
    }
    @SuppressWarnings("unchecked")
    @Override boolean uncheckedAddNull(int tail){
      int head;
      final Comparable<E>[] arr;
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
    @Override ToIntFunction<E> getQueryComparator(E key){
      return (k)->Integer.signum(k.compareTo(key));
    }
    @SuppressWarnings("unchecked")
    @Override public boolean contains(Object key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key!=null){
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
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
          return super.uncheckedContainsMatch(tail,this.getQueryComparator((E)key));
        }
        return arr[head]==null;
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
  }
/*
  abstract int insertionCompare(E key1,E key2);
  abstract IntUnaryOperator getQueryComparator(int key);
  public static class Ascending<E> extends ComparableOrderedSet<E>{
    Ascending(int head,Comparable<E>[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
    @Override public Comparator<E> comparator(){
      return E::compare;
    }
  }
  public static class Descending<E> extends ComparableOrderedSet<E>{
    Descending(int head,Comparable<E>[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
    @Override public Comparator<E> comparator(){
      return Comparator::descendingCompare;
    }
  }
  */
}
