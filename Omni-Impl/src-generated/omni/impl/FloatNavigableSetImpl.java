package omni.impl;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.function.FloatComparator;
import omni.util.TypeUtil;
import omni.function.FloatToIntFunction;
public abstract class FloatNavigableSetImpl
  extends FloatUntetheredArrSeq implements OmniNavigableSet.OfFloat
{
  FloatNavigableSetImpl(int head,float[] arr,int tail){
    super(head,arr,tail);
  }
  FloatNavigableSetImpl(){
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
  public static class Ascending extends FloatNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,float[] arr,int tail){
      super(head,arr,tail);
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
    @Override public Float ceiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return null;
    }
    @Override public Float floor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Float higher(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Float lower(float val){
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
    @Override public OmniNavigableSet.OfFloat headSet(float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat headSet(float toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat tailSet(float fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat tailSet(float fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat subSet(float fromElement,float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat subSet(float fromElement, boolean fromInclusive, float toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfFloat descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat headSet(Float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat tailSet(Float fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat subSet(Float fromElement,Float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final float[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new float[size],0,size);
          cloneTail=size-1;
        }else{
          final float[] arr;
          dst=new float[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Ascending(0,dst,cloneTail);
      }
      return new Ascending();
    }
    @Override public FloatComparator comparator(){
      return Float::compare;
    }
    @Override boolean uncheckedremoveNaN(int tail){
       final float[] arr;
       if(Float.isNaN((arr=this.arr)[tail])){
         if(tail==this.head){
           tail=-1;
         }else if(--tail==-1){
           tail=arr.length-1;
         }
         this.tail=tail;
         return true;
       } 
       return false;
    }
    @Override boolean uncheckedremovePosInf(int tail){
      final float[] arr;
      if((arr=this.arr)[tail]==Float.POSITIVE_INFINITY){
        if(tail==this.head){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return true;
      }
      if(tail!=this.head){
        if(--tail==-1){
          tail=arr.length-1;
        }
        if(arr[tail]==Float.POSITIVE_INFINITY){
          arr[tail]=Float.NaN;
          this.tail=tail;
          return true;
        }
      }
      return false;
    }
    @Override boolean uncheckedremoveNegInf(int tail){
      final float[] arr;
      int head;
      if((arr=this.arr)[head=this.head]==Float.NEGATIVE_INFINITY){
        if(tail==head){
          this.tail=-1;
        }else{
          if(++head==arr.length){
            head=0;
          }
          this.head=head;
        }
        return true;
      }
      return false;
    }
    @Override int comparePos1(float key){
      if(1>key){
        //insert hi;
        return 1;
      }
      if(1==key){
        return 0;
      }
      return -1;
    }
    @Override int comparePos0(float key){
      if(0>key){
        //insert hi
        return 1;
      }
      switch(Float.floatToRawIntBits(key)){
        case 0: //pos0
          return 0;
        case Integer.MIN_VALUE: //neg0
          return 1;
        default:
      }
      return -1; //0<key || key!=key so insert lo
    }
    @Override int compareNeg0(float key){
      if(0>key){
        //insert hi
        return 1;
      }
      if(Float.floatToRawIntBits(key)==Integer.MIN_VALUE){
        return 0;
      }
      return -1; //0<key || key!=key so insert lo
    }
    @Override boolean uncheckedAddNaN(int tail){
      final float[] arr;
      if(!Float.isNaN((arr=this.arr)[tail])){
        super.insertAtTail(arr,Float.NaN,this.head,tail);
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddPosInf(int tail){
      float[] arr;
      final float topVal;
      if((topVal=(arr=this.arr)[tail])!=Float.POSITIVE_INFINITY){
        int head=this.head;
        if(Float.isNaN(topVal)){
          //add it before the tail
          int newTail;
          switch(Integer.signum((newTail=tail+1)-head)){
            case 0:
              //fragmented must grow
              final float[] tmp;
              int arrLength;
              ArrCopy.semicheckedCopy(arr,0,tmp=new float[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
              ArrCopy.uncheckedCopy(arr,newTail,tmp,head-=(arrLength-=newTail),arrLength);
              this.head=head;
              this.arr=arr=tmp;
              break;
            default:
              //nonfragmented
              if(newTail==arr.length){
                if(head==0){
                  //must grow
                  ArrCopy.semicheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(newTail)],0,tail);
                  this.arr=arr;
                }else{
                  newTail=0;
                }
              }
            case -1:
              //fragmented
          }
          arr[tail]=Float.POSITIVE_INFINITY;
          arr[newTail]=Float.NaN;
          this.tail=newTail;
        }else{
          super.insertAtTail(arr,Float.POSITIVE_INFINITY,head,tail);
        }
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddNegInf(int tail){
      final float[] arr;
      final int head;
      if(((arr=this.arr)[head=this.head])!=Float.NEGATIVE_INFINITY){
        super.insertAtHead(arr,Float.NEGATIVE_INFINITY,head,tail);
        return true;
      }
      return false;
    }
    @Override public boolean contains(double key){
      final int tail;
      if((tail=this.tail)!=-1){
        final float f;
        if((f=(float)key)==f){
          final FloatToIntFunction queryComparator;
          switch(Float.floatToRawIntBits(f)){
            case 0x7f800000: //pos inf
              final float[] arr;
              return ((arr=this.arr)[tail]==Float.POSITIVE_INFINITY) || (tail!=head && arr[tail==0?arr.length-1:tail-1]==Float.POSITIVE_INFINITY);
            case 0xff800000:
              return this.arr[head]==Float.NEGATIVE_INFINITY;
            case 0:
              queryComparator=this::comparePos0;
              break;
            case Integer.MIN_VALUE:
              queryComparator=this::compareNeg0;
              break;
            default:
              queryComparator=super.getQueryComparator(f);
          }
          return super.uncheckedContainsMatch(this.head,tail,queryComparator);
        }else if(f!=f){
          return Float.isNaN(arr[tail]);
        }
      }
      return false;
    }
    @Override public boolean contains(float key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key==key){
          final FloatToIntFunction queryComparator;
          switch(Float.floatToRawIntBits(key)){
            case 0x7f800000: //pos inf
              final float[] arr;
              return ((arr=this.arr)[tail]==Float.POSITIVE_INFINITY) || (tail!=head && arr[tail==0?arr.length-1:tail-1]==Float.POSITIVE_INFINITY);
            case 0xff800000:
              return this.arr[head]==Float.NEGATIVE_INFINITY;
            case 0:
              queryComparator=this::comparePos0;
              break;
            case Integer.MIN_VALUE:
              queryComparator=this::compareNeg0;
              break;
            default:
              queryComparator=super.getQueryComparator(key);
          }
          return super.uncheckedContainsMatch(this.head,tail,queryComparator);
        }
        return Float.isNaN(arr[tail]);
      }
      return false;
    }
    @Override public boolean contains(Object key){
      final int tail;
      if((tail=this.tail)!=-1){
        final FloatToIntFunction queryComparator;
        if(key instanceof Float){
          final float f;
          if((f=(float)key)==f){
            switch(Float.floatToRawIntBits(f)){
              case 0xff800000:
                return arr[this.head]==Float.NEGATIVE_INFINITY;
              case 0x7f800000:
                final float[] arr;
                return ((arr=this.arr)[tail]==Float.POSITIVE_INFINITY)
                     ||(tail!=this.head && (arr[tail==0?arr.length-1:tail-1]==Float.POSITIVE_INFINITY));
              case 0:
                queryComparator=this::comparePos0;
                break;
              case Integer.MIN_VALUE:
                queryComparator=this::compareNeg0;
                break;
              default:
                queryComparator=super.getQueryComparator(f);
            }
          }else{
            return Float.isNaN(arr[tail]);
          }
        }else if(key instanceof Integer){
          final int i;
          if(!TypeUtil.checkCastToFloat(i=(int)key)){
            return false;
          }
          queryComparator=(i==0)?this::comparePos0:super.getQueryComparator(i);
        }else if(key instanceof Long){
          final long l;
          if(!TypeUtil.checkCastToFloat(l=(long)key)){
            return false;
          }
          queryComparator=l==0?this::comparePos0:super.getQueryComparator(l);
        }else if(key instanceof Double){
          final double d;
          final float f;
          if((d=(double)key)==(f=(float)d)){
            switch(Float.floatToRawIntBits(f)){
              case 0xff800000:
                return arr[this.head]==Float.NEGATIVE_INFINITY;
              case 0x7f800000:
                final float[] arr;
                return ((arr=this.arr)[tail]==Float.POSITIVE_INFINITY)
                     ||(tail!=this.head && (arr[tail==0?arr.length-1:tail-1]==Float.POSITIVE_INFINITY));
              case 0:
                queryComparator=this::comparePos0;
                break;
              case Integer.MIN_VALUE:
                queryComparator=this::compareNeg0;
                break;
              default:
                queryComparator=super.getQueryComparator(f);
            }
          }else{
            return Float.isNaN(arr[tail]);
          }
        }else if(key instanceof Byte || key instanceof Short){
          final int i;
          queryComparator=((i=((Number)key).shortValue())==0)?this::comparePos0:super.getQueryComparator(i);
        }else if(key instanceof Character){
          final int i;
          queryComparator=((i=(char)key)==0)?this::comparePos0:super.getQueryComparator(i);
        }else if(key instanceof Boolean){
          queryComparator=((boolean)key)?this::comparePos1:this::comparePos0;
        }else{
          return false;
        }
        return super.uncheckedContainsMatch(this.head,tail,queryComparator);
      }
      return false;
    }
    @Override int insertionCompare(float key1,float key2){
      if(key1>key2){
        return 1;
      }
      if(key1==key2){
        return 0;
      }
      return -1;
    }
  }
  public static class Descending extends FloatNavigableSetImpl implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,float[] arr,int tail){
      super(head,arr,tail);
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
    @Override public Float ceiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return null;
    }
    @Override public Float floor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Float higher(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Float lower(float val){
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
    @Override public OmniNavigableSet.OfFloat headSet(float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat headSet(float toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat tailSet(float fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat tailSet(float fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat subSet(float fromElement,float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat subSet(float fromElement, boolean fromInclusive, float toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfFloat descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat headSet(Float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat tailSet(Float fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfFloat subSet(Float fromElement,Float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final float[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new float[size],0,size);
          cloneTail=size-1;
        }else{
          final float[] arr;
          dst=new float[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Descending(0,dst,cloneTail);
      }
      return new Descending();
    }
    @Override public FloatComparator comparator(){
      return FloatComparator::descendingCompare;
    }
    @Override int comparePos1(float key){
      if(1>key){
        return -1;
      }
      if(1==key){
        return 0;
      }
      return 1;
    }
    @Override int comparePos0(float key){
      if(0>key){
        return -1;
      }
      switch(Float.floatToRawIntBits(key)){
        case 0: //pos0
          return 0;
        case Integer.MIN_VALUE: //neg0
          return -1;
        default:
      }
      return 1; //key>pos0 || key!=key
    }
    @Override int compareNeg0(float key){
      if(0>key){
        return -1;
      }
      if(Float.floatToRawIntBits(key)==Integer.MIN_VALUE){
        return 0;
      }
      return 1; //key>neg0 || key!=key
    }
    @Override boolean uncheckedAddNaN(int tail){
      final float[] arr;
      final int head;
      if(!Float.isNaN((arr=this.arr)[head=this.head])){
        super.insertAtHead(arr,Float.NaN,head,tail);
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddPosInf(int tail){
      int head;
      float[] arr;
      final float bottomVal;
      if((bottomVal=(arr=this.arr)[head=this.head])!=Float.POSITIVE_INFINITY){
        if(Float.isNaN(bottomVal)){
          //add just after head
          int newHead;
          switch(Integer.signum(tail-(newHead=head-1))){
            case 0:
              //fragmented must grow
              final float[] tmp;
              int arrLength;
              ArrCopy.uncheckedCopy(arr,0,tmp=new float[tail=OmniArray.growBy50Pct(arrLength=arr.length)],0,head);
              ArrCopy.semicheckedCopy(arr,head,tmp,head=tail-(arrLength-=(head+1)),arrLength);
              --head;
              newHead=head-1;
              this.arr=arr=tmp;
              break;
            default:
              //nonfragmented
              if(newHead==-1){
                if(tail==(newHead=(tail=arr.length)-1)){
                  //must grow
                  this.tail=(head=OmniArray.growBy50Pct(tail))-1;
                  ArrCopy.semicheckedCopy(arr,0,arr=new float[head],head-=newHead,newHead);
                  --head;
                  this.arr=arr;
                }
                newHead=head-1;
              }
            case -1:
              //fragmented
          }
          arr[head]=Float.POSITIVE_INFINITY;
          arr[newHead]=Float.NaN;
          this.head=newHead;
        }else{
          super.insertAtHead(arr,Float.POSITIVE_INFINITY,head,tail);
        }
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddNegInf(int tail){
      final float[] arr;
      if(((arr=this.arr)[tail])!=Float.NEGATIVE_INFINITY){
        super.insertAtTail(arr,Float.NEGATIVE_INFINITY,this.head,tail);
        return true;
      }
      return false;
    }
    @Override public boolean contains(double key){
      final int tail;
      if((tail=this.tail)!=-1){
        final float f;
        if((f=(float)key)==f){
          final FloatToIntFunction queryComparator;
          switch(Float.floatToRawIntBits(f)){
            case 0xff800000:
              return this.arr[tail]==Float.NEGATIVE_INFINITY;
            case 0x7f800000: //pos inf
              final float[] arr;
              int head;
              return ((arr=this.arr)[head=this.head]==Float.POSITIVE_INFINITY) || (tail!=head && arr[++head==arr.length?0:head]==Float.POSITIVE_INFINITY);
            case 0:
              queryComparator=this::comparePos0;
              break;
            case Integer.MIN_VALUE:
              queryComparator=this::compareNeg0;
              break;
            default:
              queryComparator=super.getQueryComparator(f);
          }
          return super.uncheckedContainsMatch(this.head,tail,queryComparator);
        }else if(f!=f){
          return Float.isNaN(arr[head]);
        }
      }
      return false;
    }
    @Override public boolean contains(float key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key==key){
          final FloatToIntFunction queryComparator;
          switch(Float.floatToRawIntBits(key)){
            case 0xff800000:
              return this.arr[tail]==Float.NEGATIVE_INFINITY;
            case 0x7f800000: //pos inf
              final float[] arr;
              int head;
              return ((arr=this.arr)[head=this.head]==Float.POSITIVE_INFINITY) || (tail!=head && arr[++head==arr.length?0:head]==Float.POSITIVE_INFINITY);
            case 0:
              queryComparator=this::comparePos0;
              break;
            case Integer.MIN_VALUE:
              queryComparator=this::compareNeg0;
              break;
            default:
              queryComparator=super.getQueryComparator(key);
          }
          return super.uncheckedContainsMatch(this.head,tail,queryComparator);
        }
        return Float.isNaN(arr[head]);
      }
      return false;
    }
    @Override public boolean contains(Object key){
      final int tail;
      if((tail=this.tail)!=-1){
        final FloatToIntFunction queryComparator;
        if(key instanceof Float){
          final float f;
          if((f=(float)key)==f){
            switch(Float.floatToRawIntBits(f)){
              case 0xff800000:
                return arr[tail]==Float.NEGATIVE_INFINITY;
              case 0x7f800000:
                final float[] arr;
                int head;
                return ((arr=this.arr)[head=this.head]==Float.POSITIVE_INFINITY)
                     ||(tail!=head && (arr[++head==arr.length?0:head]==Float.POSITIVE_INFINITY));
              case 0:
                queryComparator=this::comparePos0;
                break;
              case Integer.MIN_VALUE:
                queryComparator=this::compareNeg0;
                break;
              default:
                queryComparator=super.getQueryComparator(f);
            }
          }else{
            return Float.isNaN(arr[head]);
          }
        }else if(key instanceof Integer){
          final int i;
          if(!TypeUtil.checkCastToFloat(i=(int)key)){
            return false;
          }
          queryComparator=(i==0)?this::comparePos0:super.getQueryComparator(i);
        }else if(key instanceof Long){
          final long l;
          if(!TypeUtil.checkCastToFloat(l=(long)key)){
            return false;
          }
          queryComparator=l==0?this::comparePos0:super.getQueryComparator(l);
        }else if(key instanceof Double){
          final double d;
          final float f;
          if((d=(double)key)==(f=(float)d)){
            switch(Float.floatToRawIntBits(f)){
              case 0xff800000:
                return arr[tail]==Float.NEGATIVE_INFINITY;
              case 0x7f800000:
                final float[] arr;
                int head;
                return ((arr=this.arr)[head=this.head]==Float.POSITIVE_INFINITY)
                     ||(tail!=head && (arr[++head==arr.length?0:head]==Float.POSITIVE_INFINITY));
              case 0:
                queryComparator=this::comparePos0;
                break;
              case Integer.MIN_VALUE:
                queryComparator=this::compareNeg0;
                break;
              default:
                queryComparator=super.getQueryComparator(f);
            }
          }else{
            return Float.isNaN(arr[head]);
          }
        }else if(key instanceof Byte || key instanceof Short){
          final int i;
          queryComparator=((i=((Number)key).shortValue())==0)?this::comparePos0:super.getQueryComparator(i);
        }else if(key instanceof Character){
          final int i;
          queryComparator=((i=(char)key)==0)?this::comparePos0:super.getQueryComparator(i);
        }else if(key instanceof Boolean){
          queryComparator=((boolean)key)?this::comparePos1:this::comparePos0;
        }else{
          return false;
        }
        return super.uncheckedContainsMatch(this.head,tail,queryComparator);
      }
      return false;
    }
    @Override boolean uncheckedremoveNaN(int tail){
       final float[] arr;
       int head;
       if(Float.isNaN((arr=this.arr)[head=this.head])){
         if(tail==head){
           this.tail=-1;
         }else{
           if(++head==arr.length){
             head=0;
           }
           this.head=head;
         }
         return true;
       } 
       return false;
    }
    @Override boolean uncheckedremovePosInf(int tail){
      final float[] arr;
      int head;
      if((arr=this.arr)[head=this.head]==Float.POSITIVE_INFINITY){
        if(tail==head){
          this.tail=-1;
        }else{
          if(++head==arr.length){
            head=0;
          }
          this.head=head;
        }
        return true;
      }
      if(tail!=head){
        if(++head==arr.length){
          head=0;
        }
        if(arr[head]==Float.POSITIVE_INFINITY){
          arr[head]=Float.NaN;
          this.head=head;
          return true;
        }
      }
      return false;
    }
    @Override boolean uncheckedremoveNegInf(int tail){
      final float[] arr;
      if((arr=this.arr)[tail]==Float.NEGATIVE_INFINITY){
        if(tail==head){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return true;
      }
      return false;
    }
    @Override int insertionCompare(float key1,float key2){
      if(key1>key2){
        return -1;
      }
      if(key1==key2){
        return 0;
      }
      return 1;
    }
  }
}
