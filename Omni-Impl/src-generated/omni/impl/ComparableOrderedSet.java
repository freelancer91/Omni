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
      super.insertMiddle(key);
      return true;
    }
  }
  abstract int insertionCompare(E key1,E key2);
  abstract boolean uncheckedAddNull(int tail);
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
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override boolean uncheckedAddNull(int tail){
      //TODO
      throw new omni.util.NotYetImplementedException();
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
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override boolean uncheckedAddNull(int tail){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
/*
  abstract int insertionCompare(E key1,E key2);
  abstract IntUnaryOperator getQueryComparator(int key);
  @Override public boolean add(E key){
    return add((E)key);
  }
  @Override public boolean add(boolean key){
    return add(OmniPred.OfRef.getEqualsPred(key));
  }
  @Override public boolean add(byte key){
    return add((E)key);
  }
  @Override public boolean add(char key){
    return add((E)key);
  }
  @Override public boolean add(int key){
    return add((E)key);
  }
  @Override public boolean add(E key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertMiddle(key);
      return true;
    }
  }
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
    @Override int insertionCompare(E key1,E key2){
      //key1 is guaranteed to be non-zero, non-infinity, and non-nan
      if(key1==key2){
        return 0;
      }
      if(key1>key2){
        return 1;
      }
      //ok if key2 is NaN
      return -1;
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
    @Override int insertionCompare(E key1,E key2){
      //key1 is guaranteed to be non-zero, non-infinity, and non-nan
      if(key1==key2){
        return 0;
      }
      if(key1>key2){
        return -1;
      }
      //ok if key2 is NaN
      return 1;
    }
  }
  */
}
