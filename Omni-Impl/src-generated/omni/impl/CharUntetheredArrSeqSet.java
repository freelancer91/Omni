package omni.impl;
import omni.api.OmniSortedSet;
import omni.util.TypeUtil;
abstract class CharUntetheredArrSeqSet
  extends CharUntetheredArrSeq implements OmniSortedSet.OfChar
{
  CharUntetheredArrSeqSet(int head,char[] arr,int tail){
    super(head,arr,tail);
  }
  CharUntetheredArrSeqSet(){
    super();
  }
  @Override public char firstChar(){
    return (char)arr[head];
  }
  @Override public char lastChar(){
    return (char)arr[tail];
  }
  @Override public boolean add(char key){
    final int tail;
    if((tail=this.tail)!=-1){
      switch(key){
        case 0:
          return this.uncheckedAdd0(tail);
        case 1:
          return this.uncheckedAdd1(tail);
        default:
      }
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Character key){
    return this.add((char)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key){
        return this.uncheckedAdd0(tail);
      }else{
        return this.uncheckedAdd1(tail);
      }
    }else{
      super.insertAtMiddle(TypeUtil.castToChar(key));
      return true;
    }
  }
  abstract int insertionCompare(char key1,char key2);
  abstract boolean uncheckedAdd0(int tail);
  abstract boolean uncheckedAdd1(int tail);
  abstract boolean uncheckedcontains(int tail,int key);
  abstract boolean uncheckedremoveVal(int tail,int key);
  abstract boolean uncheckedcontains(int tail,boolean key);
  abstract boolean uncheckedremoveVal(int tail,boolean key);
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedcontains(tail,key);
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedremoveVal(tail,key);
  }
  @Override public boolean contains(byte key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && uncheckedcontains(tail,key);
  }
  @Override public boolean removeVal(byte key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && uncheckedremoveVal(tail,key);
  }
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedcontains(tail,key);
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && uncheckedremoveVal(tail,key);
  }
  @Override public boolean contains(short key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && uncheckedcontains(tail,key);
  }
  @Override public boolean removeVal(short key){
    final int tail;
    return key>=0 && (tail=this.tail)!=-1 && uncheckedremoveVal(tail,key);
  }
  @Override public boolean contains(int key){
    final int tail;
    return key==(char)key && (tail=this.tail)!=-1 && uncheckedcontains(tail,key);
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return key==(char)key && (tail=this.tail)!=-1 && uncheckedremoveVal(tail,key);
  }
  @Override public boolean contains(long key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedcontains(tail,k);
  }
  @Override public boolean removeVal(long key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedremoveVal(tail,k);
  }
  @Override public boolean contains(float key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedcontains(tail,k);
  }
  @Override public boolean removeVal(float key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedremoveVal(tail,k);
  }
  @Override public boolean contains(double key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedcontains(tail,k);
  }
  @Override public boolean removeVal(double key){
    final int tail,k;
    return (tail=this.tail)!=-1 && key==(k=(char)key) && uncheckedremoveVal(tail,k);
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int k;
      if(key instanceof Character){
        k=(char)key;
      }else if(key instanceof Integer){
        if((k=(int)key)!=(char)k){
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
      }else if(key instanceof Byte || key instanceof Short){
        if((k=((Number)key).shortValue())<0){
          return false;
        }
      }else if(key instanceof Boolean){
        return uncheckedcontains(tail,(boolean)key);
      }else{
        return false;
      }
      return uncheckedcontains(tail,k);
    }
    return false;
  }
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final int k;
      if(key instanceof Character){
        k=(char)key;
      }else if(key instanceof Integer){
        if((k=(int)key)!=(char)k){
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
      }else if(key instanceof Byte || key instanceof Short){
        if((k=((Number)key).shortValue())<0){
          return false;
        }
      }else if(key instanceof Boolean){
        return uncheckedremoveVal(tail,(boolean)key);
      }else{
        return false;
      }
      return uncheckedremoveVal(tail,k);
    }
    return false;
  }
}
