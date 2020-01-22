package omni.impl;
import omni.api.OmniSortedSet;
import omni.util.TypeUtil;
import java.util.function.IntUnaryOperator;
abstract class ShortUntetheredArrSeqSet
  extends ShortUntetheredArrSeq implements OmniSortedSet.OfShort
{
  ShortUntetheredArrSeqSet(int head,short[] arr,int tail){
    super(head,arr,tail);
  }
  ShortUntetheredArrSeqSet(){
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
}
