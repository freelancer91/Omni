package omni.impl;
import omni.util.ArrCopy;
import omni.api.OmniSortedSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.ToIntFunction;
import omni.util.OmniArray;
public class ComparableDescendingSortedSet<E extends Comparable<E>> extends ComparableUntetheredArrSeqSet<E> implements Cloneable
{
  public ComparableDescendingSortedSet(){
    super();
  }
  public ComparableDescendingSortedSet(int head,Comparable<E>[] arr,int tail){
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
      return new ComparableDescendingSortedSet<E>(0,dst,cloneTail);
    }
    return new ComparableDescendingSortedSet<E>();
  }
  @Override public Comparator<E> comparator(){
    return Comparator.nullsFirst(Collections.reverseOrder());
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
