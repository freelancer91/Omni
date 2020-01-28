package omni.impl;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.util.ArrCopy;
import omni.function.LongComparator;
import omni.util.TypeUtil;
import java.util.function.LongToIntFunction;
public abstract class LongNavigableSetImpl
  extends LongUntetheredArrSeq implements OmniSet.OfLong
{
  LongNavigableSetImpl(int head,long[] arr,int tail){
    super(head,arr,tail);
  }
  LongNavigableSetImpl(){
    super();
  }
  private static  int privateCompare(long key1,long key2){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean add(long key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,LongNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  /*
  @Override public boolean add(long key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Long key){
    return this.add((long)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,TypeUtil.castToLong(key),this::insertionCompare);
    }else{
      super.insertAtMiddle(TypeUtil.castToLong(key));
      return true;
    }
  }
  @Override public boolean add(byte key){
    return this.add((long)key);
  }
  @Override public boolean add(char key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(int key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  private int insertionCompare(long key1,long key2)
  {
  }
  LongToIntFunction getQueryComparator(long key){
    return (k)->this.insertionCompare(key,k);
  }
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(TypeUtil.castToLong(key)));
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(TypeUtil.castToLong(key)));
  }
  @Override public boolean contains(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(float key){
    final int tail;
    final long k;
    return (tail=this.tail)!=-1 && TypeUtil.floatEquals(key,k=(long)key) && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(float key){
    final int tail;
    final long k;
    return (tail=this.tail)!=-1 && TypeUtil.floatEquals(key,k=(long)key) && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(double key){
    final int tail;
    final long k;
    return (tail=this.tail)!=-1 && TypeUtil.doubleEquals(key,k=(long)key) && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(double key){
    final int tail;
    final long k;
    return (tail=this.tail)!=-1 && TypeUtil.doubleEquals(key,k=(long)key) && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final long k;
      if(key instanceof Long || key instanceof Integer || key instanceof Byte || key instanceof Short){
        k=((Number)key).longValue();
      }else if(key instanceof Float){
        final float f;
        if(!TypeUtil.floatEquals(f=(float)key,k=(long)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if(!TypeUtil.doubleEquals(d=(double)key,k=(long)d)){
          return false;
        }
      }else if(key instanceof Character){
        k=(char)key;
      }else if(key instanceof Boolean){
        k=TypeUtil.castToLong((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(this.head,tail,getQueryComparator(k));
    }
    return false;
  }
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final long k;
      if(key instanceof Long || key instanceof Integer || key instanceof Byte || key instanceof Short){
        k=((Number)key).longValue();
      }else if(key instanceof Float){
        final float f;
        if(!TypeUtil.floatEquals(f=(float)key,k=(long)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if(!TypeUtil.doubleEquals(d=(double)key,k=(long)d)){
          return false;
        }
      }else if(key instanceof Character){
        k=(char)key;
      }else if(key instanceof Boolean){
        k=TypeUtil.castToLong((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedRemoveMatch(tail,getQueryComparator(k));
    }
    return false;
  }
  @Override public long firstLong(){
    return (long)arr[head];
  }
  @Override public long lastLong(){
    return (long)arr[tail];
  }
  */
  public static class Ascending extends LongNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    /*
    @Override public long longCeiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long higherLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long lowerLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Long ceiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return null;
    }
    @Override public Long floor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Long higher(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Long lower(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleCeiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double higherDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double lowerDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatCeiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float higherFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float lowerFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong headSet(long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong headSet(long toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong tailSet(long fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong tailSet(long fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong subSet(long fromElement,long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong subSet(long fromElement, boolean fromInclusive, long toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfLong descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong headSet(Long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong tailSet(Long fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong subSet(Long fromElement,Long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public LongComparator comparator(){
      return Long::compare;
    }
    */
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final long[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new long[size],0,size);
          cloneTail=size-1;
        }else{
          final long[] arr;
          dst=new long[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Ascending(0,dst,cloneTail);
      }
      return new Ascending();
    }
    /*
    */
    /*
    @Override int insertionCompare(long key1,long key2){
      if(key1>key2){
        return 1;
      }
      if(key1==key2){
        return 0;
      }
      return -1;
    }
    */
  }
  public static class Descending extends LongNavigableSetImpl implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    /*
    @Override public long longCeiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long higherLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long lowerLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Long ceiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return null;
    }
    @Override public Long floor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Long higher(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Long lower(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleCeiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double higherDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double lowerDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatCeiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float higherFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float lowerFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong headSet(long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong headSet(long toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong tailSet(long fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong tailSet(long fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong subSet(long fromElement,long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong subSet(long fromElement, boolean fromInclusive, long toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfLong descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong headSet(Long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong tailSet(Long fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfLong subSet(Long fromElement,Long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public LongComparator comparator(){
      return LongComparator::descendingCompare;
    }
    */
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final long[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new long[size],0,size);
          cloneTail=size-1;
        }else{
          final long[] arr;
          dst=new long[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Descending(0,dst,cloneTail);
      }
      return new Descending();
    }
    /*
    */
    /*
    @Override int insertionCompare(long key1,long key2){
      if(key1>key2){
        return -1;
      }
      if(key1==key2){
        return 0;
      }
      return 1;
    }
    */
  }
}
