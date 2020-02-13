package omni.impl;
import java.util.function.IntFunction;
import java.io.Serializable;
import omni.api.OmniSortedSet;
import omni.util.ArrCopy;
import omni.api.OmniIterator;
import omni.util.OmniArray;
import omni.function.ShortPredicate;
import omni.function.ShortConsumer;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.function.ShortComparator;
import omni.util.TypeUtil;
import java.util.function.IntUnaryOperator;
public abstract class ShortNavigableSetImpl
  extends ShortUntetheredArrSeq implements OmniSortedSet.OfShort
{
  ShortNavigableSetImpl(int head,short[] arr,int tail){
    super(head,arr,tail);
  }
  ShortNavigableSetImpl(){
    super();
  }
  private static int privateCompare(int key1,int key2){
    return Integer.signum(key1-key2);
  }
  private static IntUnaryOperator getSearchFunction(int key){
    return (k)->privateCompare(k,key);
  }
  @Override public boolean add(short key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,ShortNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Short key){
    return add((short)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,(short)TypeUtil.castToByte(key),ShortNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle((short)TypeUtil.castToByte(key));
      return true;
    }
  }
  @Override public boolean add(byte key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,ShortNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int i;
      if(key instanceof Short || key instanceof Byte){
        i=((Number)key).shortValue();
      }else if(key instanceof Integer){
        if((i=(int)key)!=(short)i){
          return false;
        }
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(i=(short)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(i=(short)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(i=(short)d)){
          return false;
        }
      }else if(key instanceof Character){
        if((i=(char)key)>Short.MAX_VALUE){
          return false;
        }
      }else if(key instanceof Boolean){
        i=(short)TypeUtil.castToByte((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
    }
    return false;
  }
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int i;
      if(key instanceof Short || key instanceof Byte){
        i=((Number)key).shortValue();
      }else if(key instanceof Integer){
        if((i=(int)key)!=(short)i){
          return false;
        }
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(i=(short)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(i=(short)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(i=(short)d)){
          return false;
        }
      }else if(key instanceof Character){
        if((i=(char)key)>Short.MAX_VALUE){
          return false;
        }
      }else if(key instanceof Boolean){
        i=(short)TypeUtil.castToByte((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedRemoveMatch(tail,getSearchFunction(i));
    }
    return false;
  }
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction((short)TypeUtil.castToByte(key)));
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction((short)TypeUtil.castToByte(key)));
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
    return key<=Short.MAX_VALUE && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return key<=Short.MAX_VALUE && (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
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
    return key==(short)key && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return key==(short)key && (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction(key));
  }
  @Override public boolean contains(long key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(short)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(short)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(i));
  }
  @Override public boolean contains(float key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(short)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
  }
  @Override public boolean removeVal(float key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(short)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(i));
  }
  @Override public boolean contains(double key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(short)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
  }
  @Override public boolean removeVal(double key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(short)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(i));
  }
  private static class AscendingFullView extends ShortUntetheredArrSeq.AbstractFullView implements OmniSortedSet.OfShort,Cloneable,Serializable
  {
    AscendingFullView(ShortUntetheredArrSeq root){
      super(root);
    }
    @Override public <T> T[] toArray(T[] arr){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public ShortComparator comparator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort headSet(short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort tailSet(short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort subSet(short fromElement, short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort headSet(Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort tailSet(Short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort subSet(Short fromElement,Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short firstShort(){
      final ShortUntetheredArrSeq root;
      return(short)(root=this.root).arr[root.head];
    }
    @Override public short lastShort(){
      final ShortUntetheredArrSeq root;
      return(short)(root=this.root).arr[root.tail];
    }
    @Override public void forEach(ShortConsumer action){
      final ShortUntetheredArrSeq root;
      int tail;
      if((tail=(root=this.root).tail)!=-1){
        root.ascendingForEach(root.head,tail,action);
      }
    }
    @Override public void forEach(Consumer<? super Short> action){
      final ShortUntetheredArrSeq root;
      int tail;
      if((tail=(root=this.root).tail)!=-1){
        root.ascendingForEach(root.head,tail,action::accept);
      }
    }
    @Override public OmniIterator.OfShort iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        short[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new short[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new short[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public Short[] toArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        Short[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new Short[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new Short[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        double[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new double[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new double[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new float[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new float[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        long[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new long[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new long[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        int[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new int[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new int[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
  }
  private static class DescendingFullView extends ShortUntetheredArrSeq.AbstractFullView implements OmniSortedSet.OfShort,Cloneable,Serializable
  {
    DescendingFullView(ShortUntetheredArrSeq root){
      super(root);
    }
    @Override public <T> T[] toArray(T[] arr){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public ShortComparator comparator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort headSet(short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort tailSet(short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort subSet(short fromElement, short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort headSet(Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort tailSet(Short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort subSet(Short fromElement,Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short firstShort(){
      final ShortUntetheredArrSeq root;
      return(short)(root=this.root).arr[root.tail];
    }
    @Override public short lastShort(){
      final ShortUntetheredArrSeq root;
      return(short)(root=this.root).arr[root.head];
    }
    @Override public void forEach(ShortConsumer action){
      final ShortUntetheredArrSeq root;
      int tail;
      if((tail=(root=this.root).tail)!=-1){
        root.descendingForEach(root.head,tail,action);
      }
    }
    @Override public void forEach(Consumer<? super Short> action){
      final ShortUntetheredArrSeq root;
      int tail;
      if((tail=(root=this.root).tail)!=-1){
        root.descendingForEach(root.head,tail,action::accept);
      }
    }
    @Override public OmniIterator.OfShort iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        short[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(root.arr,head,dst=new short[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new short[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public Short[] toArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        Short[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(root.arr,head,dst=new Short[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Short[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        double[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(root.arr,head,dst=new double[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new double[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(root.arr,head,dst=new float[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new float[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        long[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(root.arr,head,dst=new long[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new long[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int tail;
      final ShortUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        int[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(root.arr,head,dst=new int[size],0,size);
        }else{
          final short[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new int[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
  }
  public static class Ascending extends ShortNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,short[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public ShortComparator comparator(){
      return Short::compare;
    }
    @Override public short firstShort(){
      return (short)arr[head];
    }
    @Override public short lastShort(){
      return (short)arr[tail];
    }
    @Override public OmniSortedSet.OfShort headSet(short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort headSet(Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort tailSet(short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort tailSet(Short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort subSet(short fromElement,short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort subSet(Short fromElement,Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final short[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new short[size],0,size);
          cloneTail=size-1;
        }else{
          final short[] arr;
          dst=new short[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Ascending(0,dst,cloneTail);
      }
      return new Ascending();
    }
  }
  public static class Descending extends ShortNavigableSetImpl implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,short[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public ShortComparator comparator(){
      return ShortComparator::descendingCompare;
    }
    @Override public short firstShort(){
      return (short)arr[tail];
    }
    @Override public short lastShort(){
      return (short)arr[head];
    }
    @Override public OmniSortedSet.OfShort headSet(short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort headSet(Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort tailSet(short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort tailSet(Short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort subSet(short fromElement,short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfShort subSet(Short fromElement,Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final short[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new short[size],0,size);
          cloneTail=size-1;
        }else{
          final short[] arr;
          dst=new short[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Descending(0,dst,cloneTail);
      }
      return new Descending();
    }
      @Override public short[] toShortArray(){
        int tail;
        if((tail=this.tail)!=-1){
          short[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(this.arr,head,dst=new short[size],0,size);
          }else{
            final short[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new short[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public Short[] toArray(){
        int tail;
        if((tail=this.tail)!=-1){
          Short[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(this.arr,head,dst=new Short[size],0,size);
          }else{
            final short[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Short[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_BOXED_ARR;
      }
      @Override public double[] toDoubleArray(){
        int tail;
        if((tail=this.tail)!=-1){
          double[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(this.arr,head,dst=new double[size],0,size);
          }else{
            final short[] arr;
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
            ArrCopy.uncheckedReverseCopy(this.arr,head,dst=new float[size],0,size);
          }else{
            final short[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new float[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int tail;
        if((tail=this.tail)!=-1){
          long[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(this.arr,head,dst=new long[size],0,size);
          }else{
            final short[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new long[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int tail;
        if((tail=this.tail)!=-1){
          int[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(this.arr,head,dst=new int[size],0,size);
          }else{
            final short[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new int[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      /*
      @Override public Short ceiling(short val){
        return super.floor(val);
      }
      @Override public Short floor(short val){
        return super.ceiling(val);
      }
      @Override public Short higher(short val){
        return super.lower(val);
      }
      @Override public Short lower(short val){
        return super.higher(val);
      }
      */
      /*
      @Override public short shortCeiling(short val){
        return super.shortFloor(val);
      }
      @Override public short shortFloor(short val){
        return super.shortCeiling(val);
      }
      @Override public short higherShort(short val){
        return super.lowerShort(val);
      }
      @Override public short lowerShort(short val){
        return super.higherShort(val);
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
      @Override public int intCeiling(int val){
        return super.intFloor(val);
      }
      @Override public int intFloor(int val){
        return super.intCeiling(val);
      }
      @Override public int higherInt(int val){
        return super.lowerInt(val);
      }
      @Override public int lowerInt(int val){
        return super.higherInt(val);
      }
      */
  }
}
