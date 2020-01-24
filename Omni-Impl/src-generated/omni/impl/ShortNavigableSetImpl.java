package omni.impl;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.util.ArrCopy;
import omni.function.ShortComparator;
import omni.util.TypeUtil;
import java.util.function.IntUnaryOperator;
public abstract class ShortNavigableSetImpl
  extends ShortUntetheredArrSeq implements OmniNavigableSet.OfShort
{
  ShortNavigableSetImpl(int head,short[] arr,int tail){
    super(head,arr,tail);
  }
  ShortNavigableSetImpl(){
    super();
  }
  @Override public short firstShort(){
    return (short)arr[head];
  }
  @Override public short lastShort(){
    return (short)arr[tail];
  }
  @Override public boolean add(short key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Short key){
    return this.add((short)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,(short)TypeUtil.castToByte(key),this::insertionCompare);
    }else{
      super.insertAtMiddle((short)TypeUtil.castToByte(key));
      return true;
    }
  }
  @Override public boolean add(byte key){
    return this.add((short)key);
  }
  abstract int insertionCompare(int key1,int key2);
  IntUnaryOperator getQueryComparator(int key){
    return (k)->this.insertionCompare(key,k);
  }
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,getQueryComparator((short)TypeUtil.castToByte(key)));
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator((short)TypeUtil.castToByte(key)));
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
    return key<=Short.MAX_VALUE && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return key<=Short.MAX_VALUE && (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(int key){
    final int tail;
    return key==(short)key && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return key==(short)key && (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(long key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(short)key)==key && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(long key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(short)key)==key && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(float key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(short)key)==key && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(float key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(short)key)==key && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(double key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(short)key)==key && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(double key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(short)key)==key && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int k;
      if(key instanceof Short || key instanceof Byte){
        k=((Number)key).shortValue();
      }else if(key instanceof Integer){
        if((k=(int)key)!=(short)k){
          return false;
        }
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(k=(short)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(k=(short)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(k=(short)d)){
          return false;
        }
      }else if(key instanceof Character){
        if((k=(char)key)>Short.MAX_VALUE){
          return false;
        }
      }else if(key instanceof Boolean){
        k=(short)TypeUtil.castToByte((boolean)key);
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
      final int k;
      if(key instanceof Short || key instanceof Byte){
        k=((Number)key).shortValue();
      }else if(key instanceof Integer){
        if((k=(int)key)!=(short)k){
          return false;
        }
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(k=(short)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(k=(short)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(k=(short)d)){
          return false;
        }
      }else if(key instanceof Character){
        if((k=(char)key)>Short.MAX_VALUE){
          return false;
        }
      }else if(key instanceof Boolean){
        k=(short)TypeUtil.castToByte((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedRemoveMatch(tail,getQueryComparator(k));
    }
    return false;
  }
  public static class Ascending extends ShortNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,short[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public short shortCeiling(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Short.MIN_VALUE;
    }
    @Override public short shortFloor(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short higherShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short lowerShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Short ceiling(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return null;
    }
    @Override public Short floor(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Short higher(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Short lower(short val){
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
    @Override public int intCeiling(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int higherInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int lowerInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(short toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(short fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort subSet(short fromElement,short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort subSet(short fromElement, boolean fromInclusive, short toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfShort descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(Short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort subSet(Short fromElement,Short toElement){
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
    @Override public ShortComparator comparator(){
      return Short::compare;
    }
    @Override int insertionCompare(int key1,int key2){
      return Integer.signum(key1-key2);
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
    @Override public short shortCeiling(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Short.MIN_VALUE;
    }
    @Override public short shortFloor(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short higherShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short lowerShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Short ceiling(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return null;
    }
    @Override public Short floor(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Short higher(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Short lower(short val){
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
    @Override public int intCeiling(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int higherInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int lowerInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(short toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(short fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort subSet(short fromElement,short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort subSet(short fromElement, boolean fromInclusive, short toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfShort descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(Short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort subSet(Short fromElement,Short toElement){
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
    @Override public ShortComparator comparator(){
      return ShortComparator::descendingCompare;
    }
    @Override int insertionCompare(int key1,int key2){
      return Integer.signum(key2-key1);
    }
  }
}
