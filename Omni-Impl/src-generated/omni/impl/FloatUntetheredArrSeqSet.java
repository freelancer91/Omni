package omni.impl;
import omni.api.OmniSortedSet;
import omni.util.TypeUtil;
import omni.function.FloatToIntFunction;
abstract class FloatUntetheredArrSeqSet
  extends FloatUntetheredArrSeq implements OmniSortedSet.OfFloat
{
  FloatUntetheredArrSeqSet(int head,float[] arr,int tail){
    super(head,arr,tail);
  }
  FloatUntetheredArrSeqSet(){
    super();
  }
  @Override public float firstFloat(){
    return (float)arr[head];
  }
  @Override public float lastFloat(){
    return (float)arr[tail];
  }
  @Override public boolean add(float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final FloatToIntFunction insertionComparator;
        switch(Float.floatToRawIntBits(key)){
          default:
            return super.uncheckedAdd(tail,key,this::insertionCompare);
          case 0x7f800000:
            return this.uncheckedAddPosInf(tail);
          case 0xff800000:
            return this.uncheckedAddNegInf(tail);
          case 0:
            insertionComparator=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            insertionComparator=this::compareNeg0;
        }
        return super.uncheckedAdd(tail,key,insertionComparator);
      }
      return this.uncheckedAddNaN(tail);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Float key){
    return this.add((float)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key){
        return super.uncheckedAdd(tail,1,this::insertionCompare);
      }else{
        return super.uncheckedAdd(tail,0,this::comparePos0);
      }
    }else{
      super.insertAtMiddle(TypeUtil.castToFloat(key));
      return true;
    }
  }
  @Override public boolean add(char key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==0){
        return super.uncheckedAdd(tail,key,this::comparePos0);
      }
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(short key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==0){
        return super.uncheckedAdd(tail,key,this::comparePos0);
      }
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(int key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==0){
        return super.uncheckedAdd(tail,key,this::comparePos0);
      }
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(long key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==0){
        return super.uncheckedAdd(tail,key,this::comparePos0);
      }
      return super.uncheckedAdd(tail,key,this::insertionCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  abstract int insertionCompare(float key1,float key2);
  abstract int comparePos0(float key);
  abstract int compareNeg0(float key);
  abstract int comparePos1(float key);
  abstract boolean uncheckedAddNaN(int tail);
  abstract boolean uncheckedAddPosInf(int tail);
  abstract boolean uncheckedAddNegInf(int tail);
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,key?this::comparePos1:this::comparePos0);
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key?this::comparePos1:this::comparePos0);
  }
  FloatToIntFunction getQueryComparator(float key){
    return (k)->this.insertionCompare(key,k);
  }
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key)&&super.uncheckedContainsMatch(this.head,tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key)&&super.uncheckedRemoveMatch(tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  abstract boolean uncheckedremoveNaN(int tail);
  abstract boolean uncheckedremovePosInf(int tail);
  abstract boolean uncheckedremoveNegInf(int tail);
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  @Override public boolean contains(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  @Override public boolean removeVal(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key) && super.uncheckedContainsMatch(this.head,tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key) && super.uncheckedRemoveMatch(tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  @Override public boolean removeVal(double key){
    final int tail;
    if((tail=this.tail)!=-1){
      final float f;
      if((f=(float)key)==f){
        final FloatToIntFunction queryComparator;
        switch(Float.floatToRawIntBits(f)){
          case 0x7f800000:
            return this.uncheckedremovePosInf(tail);
          case 0xff800000:
            return this.uncheckedremoveNegInf(tail);
          case 0:
            queryComparator=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            queryComparator=this::compareNeg0;
            break;
          default:
            queryComparator=this.getQueryComparator(f);
        }
        return super.uncheckedRemoveMatch(tail,queryComparator);
      }else if(f!=f){
        return this.uncheckedremoveNaN(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final FloatToIntFunction queryComparator;
        switch(Float.floatToRawIntBits(key)){
          case 0x7f800000:
            return this.uncheckedremovePosInf(tail);
          case 0xff800000:
            return this.uncheckedremoveNegInf(tail);
          case 0:
            queryComparator=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            queryComparator=this::compareNeg0;
            break;
          default:
            queryComparator=this.getQueryComparator(key);
        }
        return super.uncheckedRemoveMatch(tail,queryComparator);
      }
      return this.uncheckedremoveNaN(tail);
    }
    return false;
  }
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final FloatToIntFunction queryComparator;
      if(key instanceof Float){
       final float f;
        if((f=(float)key)==f){
          switch(Float.floatToRawIntBits(f)){
            case 0x7f800000:
              return this.uncheckedremovePosInf(tail);
            case 0xff800000:
              return this.uncheckedremoveNegInf(tail);
            case 0:
              queryComparator=this::comparePos0;
              break;
            case Integer.MIN_VALUE:
              queryComparator=this::compareNeg0;
              break;
            default:
              queryComparator=this.getQueryComparator(f);
          }
        }else{
          return this.uncheckedremoveNaN(tail);
        }
      }else if(key instanceof Integer){
        final int i;
        if(!TypeUtil.checkCastToFloat(i=(int)key)){
          return false;
        }
        queryComparator=(i==0)?this::comparePos0:this.getQueryComparator(i);
      }else if(key instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToFloat(l=(long)key)){
          return false;
        }
        queryComparator=(l==0)?this::comparePos0:this.getQueryComparator(l);
      }else if(key instanceof Double){
        final double d;
        float f;
        if((d=(double)key)==(f=(float)d)){
          switch(Float.floatToRawIntBits(f)){
            case 0x7f800000:
              return this.uncheckedremovePosInf(tail);
            case 0xff800000:
              return this.uncheckedremoveNegInf(tail);
            case 0:
              queryComparator=this::comparePos0;
              break;
            case Integer.MIN_VALUE:
              queryComparator=this::compareNeg0;
              break;
            default:
              queryComparator=this.getQueryComparator(f);
          }
        }else if(f!=f){
          return this.uncheckedremoveNaN(tail);
        }else{
          return false;
        }
      }else if(key instanceof Byte || key instanceof Short){
        final int i;
        queryComparator=((i=((Number)key).shortValue())==0)?this::comparePos0:this.getQueryComparator(i);
      }else if(key instanceof Character){
        final int i;
        queryComparator=((i=(char)key)==0)?this::comparePos0:this.getQueryComparator(i);
      }else if(key instanceof Boolean){
        queryComparator=((boolean)key)?this::comparePos1:this::comparePos0;
      }else{
        return false;
      }
      return super.uncheckedRemoveMatch(tail,queryComparator);
    }
    return false;
  }
}
