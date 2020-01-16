package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.function.LongComparator;
import omni.util.TypeUtil;
import java.util.function.LongToIntFunction;
public abstract class LongOrderedSet
  extends LongUntetheredArrSeq
  implements OmniNavigableSet.OfLong
{
  LongOrderedSet(int head,long[] arr,int tail){
    super(head,arr,tail);
  }
  LongOrderedSet(){
    super();
  }
  abstract int insertionCompare(long key1,long key2);
  abstract LongToIntFunction getQueryComparator(long key);
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,getQueryComparator(TypeUtil.castToLong(key)));
  }
  @Override public boolean contains(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(float key){
    final int tail;
    final long k;
    return (tail=this.tail)!=-1 && TypeUtil.floatEquals(key,k=(long)key) && super.uncheckedContainsMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(double key){
    final int tail;
    final long k;
    return (tail=this.tail)!=-1 && TypeUtil.doubleEquals(key,k=(long)key) && super.uncheckedContainsMatch(tail,getQueryComparator(k));
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
      return super.uncheckedContainsMatch(tail,getQueryComparator(k));
    }
    return false;
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(TypeUtil.castToLong(key)));
  }
  @Override public boolean removeVal(byte key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(float key){
    final int tail;
    final long k;
    return (tail=this.tail)!=-1 && TypeUtil.floatEquals(key,k=(long)key) && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(double key){
    final int tail;
    final long k;
    return (tail=this.tail)!=-1 && TypeUtil.doubleEquals(key,k=(long)key) && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
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
  @Override public boolean add(Long key){
    return add((long)key);
  }
  @Override public boolean add(boolean key){
    return add(TypeUtil.castToLong(key));
  }
  @Override public boolean add(byte key){
    return add((long)key);
  }
  @Override public boolean add(char key){
    return add((long)key);
  }
  @Override public boolean add(int key){
    return add((long)key);
  }
  @Override public boolean add(long key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertMiddle(key);
      return true;
    }
  }
  public static class Ascending extends LongOrderedSet{
    Ascending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
    @Override public LongComparator comparator(){
      return Long::compare;
    }
    @Override LongToIntFunction getQueryComparator(long key){
      return k->{
        if(k==key){
          return 0;
        }
        if(key<k){
          return 1;
        }
        return -1;
      };
    }
    @Override int insertionCompare(long key1,long key2){
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
  public static class Descending extends LongOrderedSet{
    Descending(int head,long[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
    @Override public LongComparator comparator(){
      return LongComparator::descendingCompare;
    }
    @Override LongToIntFunction getQueryComparator(long key){
      return k->{
        if(k==key){
          return 0;
        }
        if(k<key){
          return 1;
        }
        return -1;
      };
    }
    @Override int insertionCompare(long key1,long key2){
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
}
