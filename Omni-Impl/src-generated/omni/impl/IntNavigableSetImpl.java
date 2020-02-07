package omni.impl;
import java.io.Serializable;
import omni.api.OmniSortedSet;
import omni.util.ArrCopy;
import omni.api.OmniIterator;
import omni.util.OmniArray;
import java.util.function.IntPredicate;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.function.IntComparator;
import omni.util.TypeUtil;
import java.util.function.IntUnaryOperator;
public abstract class IntNavigableSetImpl
  extends IntUntetheredArrSeq implements OmniSortedSet.OfInt
{
  IntNavigableSetImpl(int head,int[] arr,int tail){
    super(head,arr,tail);
  }
  IntNavigableSetImpl(){
    super();
  }
  private static int privateCompare(int key1,int key2){
    if(key1==key2){
      return 0;
    }
    if(key1>key2){
      return 1;
    }
    return -1;
  }
  private static IntUnaryOperator getSearchFunction(int key){
    return (k)->privateCompare(k,key);
  }
  @Override public boolean add(int key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,IntNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Integer key){
    return add((int)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,(int)TypeUtil.castToByte(key),IntNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle((int)TypeUtil.castToByte(key));
      return true;
    }
  }
  @Override public boolean add(byte key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,IntNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(char key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,IntNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int i;
      if(key instanceof Integer || key instanceof Byte || key instanceof Short){
        i=((Number)key).intValue();
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(i=(int)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((double)(f=(float)key)!=(double)(i=(int)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(i=(int)d)){
          return false;
        }
      }else if(key instanceof Character){
        i=(char)key;
      }else if(key instanceof Boolean){
        i=(int)TypeUtil.castToByte((boolean)key);
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
      if(key instanceof Integer || key instanceof Byte || key instanceof Short){
        i=((Number)key).intValue();
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(i=(int)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((double)(f=(float)key)!=(double)(i=(int)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(i=(int)d)){
          return false;
        }
      }else if(key instanceof Character){
        i=(char)key;
      }else if(key instanceof Boolean){
        i=(int)TypeUtil.castToByte((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedRemoveMatch(tail,getSearchFunction(i));
    }
    return false;
  }
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,getSearchFunction((int)TypeUtil.castToByte(key)));
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getSearchFunction((int)TypeUtil.castToByte(key)));
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
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(int)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(int)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(i));
  }
  @Override public boolean contains(float key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && (double)key==(double)(i=(int)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
  }
  @Override public boolean removeVal(float key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && (double)key==(double)(i=(int)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(i));
  }
  @Override public boolean contains(double key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(int)key) && super.uncheckedContainsMatch(head,tail,getSearchFunction(i));
  }
  @Override public boolean removeVal(double key){
    final int tail;
    final int i;
    return (tail=this.tail)!=-1 && key==(i=(int)key) && super.uncheckedRemoveMatch(tail,getSearchFunction(i));
  }
  private static class AscendingFullView extends IntUntetheredArrSeq.AbstractFullView implements OmniSortedSet.OfInt,Cloneable,Serializable
  {
    AscendingFullView(IntUntetheredArrSeq root){
      super(root);
    }
    @Override public void forEach(Consumer<? super Integer> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfInt iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      int tail;
      final IntUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        int[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new int[size],0,size);
        }else{
          final int[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new int[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public Integer[] toArray(){
      int tail;
      final IntUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        Integer[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new Integer[size],0,size);
        }else{
          final int[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new Integer[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      int tail;
      final IntUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        double[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new double[size],0,size);
        }else{
          final int[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new double[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int tail;
      final IntUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new float[size],0,size);
        }else{
          final int[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new float[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int tail;
      final IntUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        long[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new long[size],0,size);
        }else{
          final int[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new long[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
  }
  private static class DescendingFullView extends IntUntetheredArrSeq.AbstractFullView implements OmniSortedSet.OfInt,Cloneable,Serializable
  {
    DescendingFullView(IntUntetheredArrSeq root){
      super(root);
    }
    @Override public void forEach(Consumer<? super Integer> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfInt iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      int tail;
      final IntUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        int[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new int[size+=arr.length],tail,size-tail);
        }else{
          final int[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new int[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public Integer[] toArray(){
      int tail;
      final IntUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        Integer[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Integer[size+=arr.length],tail,size-tail);
        }else{
          final int[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Integer[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      int tail;
      final IntUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        double[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new double[size+=arr.length],tail,size-tail);
        }else{
          final int[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new double[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int tail;
      final IntUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new float[size+=arr.length],tail,size-tail);
        }else{
          final int[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new float[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int tail;
      final IntUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        long[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new long[size+=arr.length],tail,size-tail);
        }else{
          final int[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new long[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
  }
  public static class Ascending extends IntNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,int[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public IntComparator comparator(){
      return Integer::compare;
    }
    @Override public int firstInt(){
      return (int)arr[head];
    }
    @Override public int lastInt(){
      return (int)arr[tail];
    }
    @Override public OmniSortedSet.OfInt headSet(int toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfInt headSet(Integer toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfInt tailSet(int fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfInt tailSet(Integer fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfInt subSet(int fromElement,int toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfInt subSet(Integer fromElement,Integer toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final int[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new int[size],0,size);
          cloneTail=size-1;
        }else{
          final int[] arr;
          dst=new int[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Ascending(0,dst,cloneTail);
      }
      return new Ascending();
    }
  }
  public static class Descending extends IntNavigableSetImpl implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,int[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public IntComparator comparator(){
      return IntComparator::descendingCompare;
    }
    @Override public int firstInt(){
      return (int)arr[tail];
    }
    @Override public int lastInt(){
      return (int)arr[head];
    }
    @Override public OmniSortedSet.OfInt headSet(int toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfInt headSet(Integer toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfInt tailSet(int fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfInt tailSet(Integer fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfInt subSet(int fromElement,int toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfInt subSet(Integer fromElement,Integer toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final int[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new int[size],0,size);
          cloneTail=size-1;
        }else{
          final int[] arr;
          dst=new int[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Descending(0,dst,cloneTail);
      }
      return new Descending();
    }
      @Override public int[] toIntArray(){
        int tail;
        if((tail=this.tail)!=-1){
          int[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new int[size+=arr.length],tail,size-tail);
          }else{
            final int[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new int[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public Integer[] toArray(){
        int tail;
        if((tail=this.tail)!=-1){
          Integer[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Integer[size+=arr.length],tail,size-tail);
          }else{
            final int[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Integer[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_BOXED_ARR;
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
            final int[] arr;
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
            final int[] arr;
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
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new long[size+=arr.length],tail,size-tail);
          }else{
            final int[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new long[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      /*
      @Override public Integer ceiling(int val){
        return super.floor(val);
      }
      @Override public Integer floor(int val){
        return super.ceiling(val);
      }
      @Override public Integer higher(int val){
        return super.lower(val);
      }
      @Override public Integer lower(int val){
        return super.higher(val);
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
  }
}
