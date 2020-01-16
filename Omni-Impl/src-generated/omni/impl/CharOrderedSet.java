package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.function.CharComparator;
import omni.util.TypeUtil;
import java.util.function.IntUnaryOperator;
public abstract class CharOrderedSet
  extends CharUntetheredArrSeq
  implements OmniNavigableSet.OfChar
{
  CharOrderedSet(int head,char[] arr,int tail){
    super(head,arr,tail);
  }
  CharOrderedSet(){
    super();
  }
  abstract int insertionCompare(char key1,char key2);
  abstract IntUnaryOperator getQueryComparator(int key);
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,getQueryComparator(TypeUtil.castToChar(key)));
  }
  @Override public boolean contains(byte key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(short key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(int key){
    final int tail;
    return key==(char)key && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,getQueryComparator(key));
  }
  @Override public boolean contains(long key){
    final int tail;
    final int k;
    return (tail=this.tail)!=-1 && (k=(char)key)==key && super.uncheckedContainsMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(float key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(char)key)==key && super.uncheckedContainsMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(double key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(char)key)==key && super.uncheckedContainsMatch(tail,getQueryComparator(k));
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int k;
      if(key instanceof Character){
        k=(char)key;
      }else if(key instanceof Integer || key instanceof Byte || key instanceof Short){
        if((k=((Number)key).intValue())!=(char)k){
          return false;
        }
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(k=(char)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(k=(char)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(k=(char)d)){
          return false;
        }
      }else if(key instanceof Boolean){
        k=TypeUtil.castToChar((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(tail,getQueryComparator(k));
    }
    return false;
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(TypeUtil.castToChar(key)));
  }
  @Override public boolean removeVal(byte key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(short key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return key==(char)key && (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,getQueryComparator(key));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    final int k;
    return (tail=this.tail)!=-1 && (k=(char)key)==key && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(float key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(char)key)==key && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean removeVal(double key){
    final int tail,k;
    return (tail=this.tail)!=-1 && (k=(char)key)==key && super.uncheckedRemoveMatch(tail,getQueryComparator(k));
  }
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int k;
      if(key instanceof Character){
        k=(char)key;
      }else if(key instanceof Integer || key instanceof Byte || key instanceof Short){
        if((k=((Number)key).intValue())!=(char)k){
          return false;
        }
      }else if(key instanceof Long){
        final long l;
        if((l=(long)key)!=(k=(char)l)){
          return false;
        }
      }else if(key instanceof Float){
        final float f;
        if((f=(float)key)!=(k=(char)f)){
          return false;
        }
      }else if(key instanceof Double){
        final double d;
        if((d=(double)key)!=(k=(char)d)){
          return false;
        }
      }else if(key instanceof Boolean){
        k=TypeUtil.castToChar((boolean)key);
      }else{
        return false;
      }
      return super.uncheckedRemoveMatch(tail,getQueryComparator(k));
    }
    return false;
  }
  @Override public boolean add(Character key){
    return add((char)key);
  }
  @Override public boolean add(boolean key){
    return add(TypeUtil.castToChar(key));
  }
  @Override public boolean add(char key){
    int tail;
    if((tail=this.tail)!=-1){
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertMiddle(key);
      return true;
    }
  }
  public static class Ascending extends CharOrderedSet{
    Ascending(int head,char[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
    @Override public CharComparator comparator(){
      return Character::compare;
    }
    @Override IntUnaryOperator getQueryComparator(int key){
      return k->Integer.signum(k-key);
    }
    @Override int insertionCompare(char key1,char key2){
      return Integer.signum(key1-key2);
    }
  }
  public static class Descending extends CharOrderedSet{
    Descending(int head,char[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
    @Override public CharComparator comparator(){
      return CharComparator::descendingCompare;
    }
    @Override IntUnaryOperator getQueryComparator(int key){
      return k->Integer.signum(key-k);
    }
    @Override int insertionCompare(char key1,char key2){
      return Integer.signum(key2-key1);
    }
  }
}
