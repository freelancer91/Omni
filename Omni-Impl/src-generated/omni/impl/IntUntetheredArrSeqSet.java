package omni.impl;
import omni.api.OmniSortedSet;
import omni.util.TypeUtil;
import java.util.function.IntUnaryOperator;
abstract class IntUntetheredArrSeqSet
  extends IntUntetheredArrSeq implements OmniSortedSet.OfInt
{
  IntUntetheredArrSeqSet(int head,int[] arr,int tail){
    super(head,arr,tail);
  }
  IntUntetheredArrSeqSet(){
    super();
  }
  @Override public int firstInt(){
    return (int)arr[head];
  }
  @Override public int lastInt(){
    return (int)arr[tail];
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
  @Override public boolean add(Integer key){
    return this.add((int)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,(int)TypeUtil.castToByte(key),this::insertionCompare);
    }else{
      super.insertAtMiddle((int)TypeUtil.castToByte(key));
      return true;
    }
  }
  @Override public boolean add(byte key){
    return this.add((int)key);
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
  abstract int insertionCompare(int key1,int key2);
  IntUnaryOperator getQueryComparator(int key){
    return (k)->this.insertionCompare(key,k);
  }
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,getQueryComparator((int)TypeUtil.castToByte(key)));
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator((int)TypeUtil.castToByte(key)));
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
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(int)key)==key && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(long key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(int)key)==key && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(float key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(int)key)==(double)key && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(float key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(int)key)==(double)key && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(double key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(int)key)==key && super.uncheckedContainsMatch(this.head,tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(double key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(int)key)==key && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int k;
      if(key instanceof Integer || key instanceof Byte || key instanceof Short){
        k=((Number)key).intValue();
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(k=(int)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(double)(k=(int)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(k=(int)d)){
          return false;
        }
      }else if(key instanceof Character){
        k=(char)key;
      }else if(key instanceof Boolean){
        k=(int)TypeUtil.castToByte((boolean)key);
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
      if(key instanceof Integer || key instanceof Byte || key instanceof Short){
        k=((Number)key).intValue();
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(k=(int)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(double)(k=(int)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(k=(int)d)){
          return false;
        }
      }else if(key instanceof Character){
        k=(char)key;
      }else if(key instanceof Boolean){
        k=(int)TypeUtil.castToByte((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedRemoveMatch(tail,getQueryComparator(k));
    }
    return false;
  }
}
