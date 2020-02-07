package omni.impl;
import java.io.Serializable;
import omni.api.OmniSortedSet;
import omni.util.ArrCopy;
import omni.api.OmniIterator;
import omni.util.OmniArray;
import java.util.function.LongPredicate;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.function.LongComparator;
import omni.util.TypeUtil;
import java.util.function.LongToIntFunction;
public abstract class LongNavigableSetImpl
  extends LongUntetheredArrSeq implements OmniSortedSet.OfLong
{
  LongNavigableSetImpl(int head,long[] arr,int tail){
    super(head,arr,tail);
  }
  LongNavigableSetImpl(){
    super();
  }
  private static int privateCompare(long key1,long key2){
    if(key1==key2){
      return 0;
    }
    if(key1>key2){
      return 1;
    }
    return -1;
  }
  private static LongToIntFunction getSearchFunction(long key){
    return (k)->privateCompare(k,key);
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
  @Override public boolean add(Long key){
    return add((long)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,TypeUtil.castToLong(key),LongNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(TypeUtil.castToLong(key));
      return true;
    }
  }
  @Override public boolean add(byte key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,LongNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(char key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,LongNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(int key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,LongNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final long l;
      if(key instanceof Long || key instanceof Integer || key instanceof Byte || key instanceof Short){
        l=((Number)key).longValue();
      }else if(key instanceof Float){
        final float k;
        if(!TypeUtil.floatEquals(k=(float)key,l=(long)k)){
          return false;
        }
      }else if(key instanceof Double){
        final double k;
        if(!TypeUtil.doubleEquals(k=(double)key,l=(long)k)){
          return false;
        }
      }else if(key instanceof Character){
        l=(char)key;
      }else if(key instanceof Boolean){
        l=TypeUtil.castToLong((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(head,tail,getSearchFunction(l));
    }
    return false;
  }
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final long l;
      if(key instanceof Long || key instanceof Integer || key instanceof Byte || key instanceof Short){
        l=((Number)key).longValue();
      }else if(key instanceof Float){
        final float k;
        if(!TypeUtil.floatEquals(k=(float)key,l=(long)k)){
          return false;
        }
      }else if(key instanceof Double){
        final double k;
        if(!TypeUtil.doubleEquals(k=(double)key,l=(long)k)){
          return false;
        }
      }else if(key instanceof Character){
        l=(char)key;
      }else if(key instanceof Boolean){
        l=TypeUtil.castToLong((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedRemoveMatch(tail,getSearchFunction(l));
    }
    return false;
  }
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(TypeUtil.castToLong(key)));
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(TypeUtil.castToLong(key)));
  }
  @Override public boolean contains(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
  }
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
  }
  @Override public boolean contains(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
  }
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
  }
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
  }
  @Override public boolean contains(float key){
    final int tail;
    final long l;
    return (tail=this.tail)!=-1 && TypeUtil.floatEquals(key,l=(long)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(l));
  }
  @Override public boolean removeVal(float key){
    final int tail;
    final long l;
    return (tail=this.tail)!=-1 && TypeUtil.floatEquals(key,l=(long)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(l));
  }
  @Override public boolean contains(double key){
    final int tail;
    final long l;
    return (tail=this.tail)!=-1 && TypeUtil.doubleEquals(key,l=(long)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(l));
  }
  @Override public boolean removeVal(double key){
    final int tail;
    final long l;
    return (tail=this.tail)!=-1 && TypeUtil.doubleEquals(key,l=(long)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(l));
  }
  private static class AscendingFullView extends LongUntetheredArrSeq.AbstractFullView implements OmniSortedSet.OfLong,Cloneable,Serializable
  {
    AscendingFullView(LongUntetheredArrSeq root){
      super(root);
    }
    @Override public void forEach(Consumer<? super Long> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfLong iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      int tail;
      final LongUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        long[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new long[size],0,size);
        }else{
          final long[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new long[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public Long[] toArray(){
      int tail;
      final LongUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        Long[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new Long[size],0,size);
        }else{
          final long[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new Long[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      int tail;
      final LongUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        double[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new double[size],0,size);
        }else{
          final long[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new double[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int tail;
      final LongUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new float[size],0,size);
        }else{
          final long[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new float[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
  }
  private static class DescendingFullView extends LongUntetheredArrSeq.AbstractFullView implements OmniSortedSet.OfLong,Cloneable,Serializable
  {
    DescendingFullView(LongUntetheredArrSeq root){
      super(root);
    }
    @Override public void forEach(Consumer<? super Long> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfLong iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      int tail;
      final LongUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        long[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new long[size+=arr.length],tail,size-tail);
        }else{
          final long[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new long[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public Long[] toArray(){
      int tail;
      final LongUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        Long[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Long[size+=arr.length],tail,size-tail);
        }else{
          final long[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Long[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      int tail;
      final LongUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        double[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new double[size+=arr.length],tail,size-tail);
        }else{
          final long[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new double[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int tail;
      final LongUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new float[size+=arr.length],tail,size-tail);
        }else{
          final long[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new float[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
  }
  public static class Ascending extends LongNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public LongComparator comparator(){
      return Long::compare;
    }
    @Override public long firstLong(){
      return (long)arr[head];
    }
    @Override public long lastLong(){
      return (long)arr[tail];
    }
    @Override public OmniSortedSet.OfLong headSet(long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfLong headSet(Long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfLong tailSet(long fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfLong tailSet(Long fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfLong subSet(long fromElement,long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfLong subSet(Long fromElement,Long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
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
  }
  public static class Descending extends LongNavigableSetImpl implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public LongComparator comparator(){
      return LongComparator::descendingCompare;
    }
    @Override public long firstLong(){
      return (long)arr[tail];
    }
    @Override public long lastLong(){
      return (long)arr[head];
    }
    @Override public OmniSortedSet.OfLong headSet(long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfLong headSet(Long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfLong tailSet(long fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfLong tailSet(Long fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfLong subSet(long fromElement,long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfLong subSet(Long fromElement,Long toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
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
      @Override public long[] toLongArray(){
        int tail;
        if((tail=this.tail)!=-1){
          long[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new long[size+=arr.length],tail,size-tail);
          }else{
            final long[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new long[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public Long[] toArray(){
        int tail;
        if((tail=this.tail)!=-1){
          Long[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Long[size+=arr.length],tail,size-tail);
          }else{
            final long[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Long[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_BOXED_ARR;
      }
      @Override public double[] toDoubleArray(){
        int tail;
        if((tail=this.tail)!=-1){
          double[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new double[size+=arr.length],tail,size-tail);
          }else{
            final long[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new double[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int tail;
        if((tail=this.tail)!=-1){
          float[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new float[size+=arr.length],tail,size-tail);
          }else{
            final long[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new float[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      /*
      @Override public Long ceiling(long val){
        return super.floor(val);
      }
      @Override public Long floor(long val){
        return super.ceiling(val);
      }
      @Override public Long higher(long val){
        return super.lower(val);
      }
      @Override public Long lower(long val){
        return super.higher(val);
      }
      */
      /*
      @Override public long longCeiling(long val){
        return super.longFloor(val);
      }
      @Override public long longFloor(long val){
        return super.longCeiling(val);
      }
      @Override public long higherLong(long val){
        return super.lowerLong(val);
      }
      @Override public long lowerLong(long val){
        return super.higherLong(val);
      }
      */
      /*
      @Override public double doubleCeiling(double val){
        return super.doubleFloor(val);
      }
      @Override public double doubleFloor(double val){
        return super.doubleCeiling(val);
      }
      @Override public double higherDouble(double val){
        return super.lowerDouble(val);
      }
      @Override public double lowerDouble(double val){
        return super.higherDouble(val);
      }
      */
      /*
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      */
  }
}
