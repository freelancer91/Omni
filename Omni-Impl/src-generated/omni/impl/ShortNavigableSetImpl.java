package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.ArrCopy;
import omni.api.OmniIterator;
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
  private static int privateCompare(int key1,int key2){
    return Integer.signum(key1-key2);
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
  private static IntUnaryOperator getSearchFunction(int key){
    return (k)->privateCompare(k,key);
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
  @Override public short shortCeiling(short val){
    //TODO
    throw new omni.util.NotYetImplementedException();
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
    @Override public OmniNavigableSet.OfShort descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfShort descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(short toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(short fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(Short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort subSet(short fromElement,short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort subSet(short fromElement,boolean inclusiveFrom,short toElement,boolean inclusiveTo){
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
    @Override public OmniNavigableSet.OfShort descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfShort descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(short toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort headSet(Short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(short fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort tailSet(Short fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort subSet(short fromElement,short toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfShort subSet(short fromElement,boolean inclusiveFrom,short toElement,boolean inclusiveTo){
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
  }
}
