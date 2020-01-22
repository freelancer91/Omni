package omni.impl;
import omni.util.ArrCopy;
import omni.api.OmniSortedSet;
import java.util.Comparator;
import java.util.function.ToIntFunction;
import omni.util.OmniArray;
public class ComparableAscendingSortedSet<E extends Comparable<E>> extends ComparableUntetheredArrSeqSet<E> implements Cloneable
{
  public ComparableAscendingSortedSet(){
    super();
  }
  public ComparableAscendingSortedSet(int head,Comparable<E>[] arr,int tail){
    super(head,arr,tail);
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
      return new ComparableAscendingSortedSet<E>(0,dst,cloneTail);
    }
    return new ComparableAscendingSortedSet<E>();
  }
  @Override public Comparator<E> comparator(){
    return Comparator.nullsLast(Comparable::compareTo);
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
  @Override int insertionCompare(E key1,E key2){
    return Integer.signum(-key2.compareTo(key1));
  }
}
