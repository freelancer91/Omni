package omni.impl;
import omni.api.OmniSortedSet;
import java.util.function.ToIntFunction;
abstract class ComparableUntetheredArrSeqSet<E extends Comparable<E>>
  extends ComparableUntetheredArrSeq<E> implements OmniSortedSet.OfRef<E>
{
  ComparableUntetheredArrSeqSet(int head,Comparable<E>[] arr,int tail){
    super(head,arr,tail);
  }
  ComparableUntetheredArrSeqSet(){
    super();
  }
  @SuppressWarnings("unchecked")
  @Override public E first(){
    return (E)arr[head];
  }
  @SuppressWarnings("unchecked")
  @Override public E last(){
    return (E)arr[tail];
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
}
