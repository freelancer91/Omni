package omni.impl;
import omni.api.OmniSortedSet;
import omni.util.TypeUtil;
import java.util.function.LongToIntFunction;
abstract class LongUntetheredArrSeqSet
  extends LongUntetheredArrSeq implements OmniSortedSet.OfLong
{
  LongUntetheredArrSeqSet(int head,long[] arr,int tail){
    super(head,arr,tail);
  }
  LongUntetheredArrSeqSet(){
    super();
  }
  @Override public long firstLong(){
    return (long)arr[head];
  }
  @Override public long lastLong(){
    return (long)arr[tail];
  }
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
  abstract int insertionCompare(long key1,long key2);
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
}
