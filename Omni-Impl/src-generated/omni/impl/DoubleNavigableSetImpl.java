package omni.impl;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.function.DoubleComparator;
import omni.util.TypeUtil;
import java.util.function.DoubleToIntFunction;
public abstract class DoubleNavigableSetImpl
  extends DoubleUntetheredArrSeq implements OmniNavigableSet.OfDouble
{
  DoubleNavigableSetImpl(int head,double[] arr,int tail){
    super(head,arr,tail);
  }
  DoubleNavigableSetImpl(){
    super();
  }
  @Override public double firstDouble(){
    return (double)arr[head];
  }
  @Override public double lastDouble(){
    return (double)arr[tail];
  }
  @Override public boolean add(double key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final DoubleToIntFunction insertionComparator;
        final long bits;
        if((bits=Double.doubleToRawLongBits(key))==0L){
          insertionComparator=this::comparePos0;
        }else if(bits==Long.MIN_VALUE){
          insertionComparator=this::compareNeg0;
        }else{
          return super.uncheckedAdd(tail,key,this::insertionCompare);
        }
        return super.uncheckedAdd(tail,key,insertionComparator);
      }
      return this.uncheckedAddNaN(tail);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Double key){
    return this.add((double)key);
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
      super.insertAtMiddle(TypeUtil.castToDouble(key));
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
  @Override public boolean add(float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        DoubleToIntFunction insertionComparator;
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
  abstract int insertionCompare(double key1,double key2);
  abstract int comparePos0(double key);
  abstract int compareNeg0(double key);
  abstract int comparePos1(double key);
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
  DoubleToIntFunction getQueryComparator(double key){
    return (k)->this.insertionCompare(key,k);
  }
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToDouble(key)&&super.uncheckedContainsMatch(this.head,tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToDouble(key)&&super.uncheckedRemoveMatch(tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  abstract boolean uncheckedremoveNaN(int tail);
  abstract boolean uncheckedremovePosInf(int tail);
  abstract boolean uncheckedremoveNegInf(int tail);
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(this.head,tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key==0?this::comparePos0:this.getQueryComparator(key));
  }
  @Override public boolean removeVal(double key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final DoubleToIntFunction queryComparator;
        final long bits;
        if((bits=Double.doubleToRawLongBits(key))==0){
          queryComparator=this::comparePos0;
        }else if(bits==Long.MIN_VALUE){
          queryComparator=this::compareNeg0;
        }else{
          queryComparator=this.getQueryComparator(key);
        }
        return super.uncheckedRemoveMatch(tail,queryComparator);
      }
      return this.uncheckedremoveNaN(tail);
    }
    return false;
  }
  @Override public boolean removeVal(float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final DoubleToIntFunction queryComparator;
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
      final DoubleToIntFunction queryComparator;
      if(key instanceof Double){
        final double d;
        if((d=(double)key)==d){
          final long bits;
          if((bits=Double.doubleToRawLongBits(d))==0){
            queryComparator=this::comparePos0;
          }else if(bits==Long.MIN_VALUE){
            queryComparator=this::compareNeg0;
          }else{
            queryComparator=this.getQueryComparator(d);
          }
        }else{
          return this.uncheckedremoveNaN(tail);
        }
      }else if(key instanceof Integer || key instanceof Byte || key instanceof Short){
        final int i;
        queryComparator=((i=((Number)key).intValue())==0)?this::comparePos0:this.getQueryComparator(i);
      }else if(key instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToDouble(l=(long)key)){
          return false;
        }
        queryComparator=(l==0)?this::comparePos0:this.getQueryComparator(l);
      }else if(key instanceof Float){
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
  public static class Ascending extends DoubleNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,double[] arr,int tail){
      super(head,arr,tail);
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
    @Override public Double ceiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return null;
    }
    @Override public Double floor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Double higher(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Double lower(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(double toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(double fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(double fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble subSet(double fromElement,double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble subSet(double fromElement, boolean fromInclusive, double toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfDouble descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(Double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(Double fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble subSet(Double fromElement,Double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final double[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new double[size],0,size);
          cloneTail=size-1;
        }else{
          final double[] arr;
          dst=new double[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Ascending(0,dst,cloneTail);
      }
      return new Ascending();
    }
    @Override public DoubleComparator comparator(){
      return Double::compare;
    }
    @Override public boolean contains(double key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key==key){
          final DoubleToIntFunction queryComparator;
          final long bits;
          if((bits=Double.doubleToRawLongBits(key))==0){
            queryComparator=this::comparePos0;
          }else if(bits==Long.MIN_VALUE){
            queryComparator=this::compareNeg0;
          }else{
            queryComparator=super.getQueryComparator(key);
          }
          return super.uncheckedContainsMatch(this.head,tail,queryComparator);
        }
        return Double.isNaN(arr[tail]);
      }
      return false;
    }
    @Override public boolean contains(float key){
      int tail;
      if((tail=this.tail)!=-1){
        if(key==key){
          final DoubleToIntFunction queryComparator;
          switch(Float.floatToRawIntBits(key)){
            case 0x7f800000: //pos inf
              final double[] arr;
              return ((arr=this.arr)[tail]==Double.POSITIVE_INFINITY) || (tail!=head && arr[tail==0?arr.length-1:tail-1]==Double.POSITIVE_INFINITY);
            case 0xff800000:
              return this.arr[head]==Double.NEGATIVE_INFINITY;
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
        return Double.isNaN(arr[tail]);
      }
      return false;
    }
    @Override public boolean contains(Object key){
      final int tail;
      if((tail=this.tail)!=-1){
        final DoubleToIntFunction queryComparator;
        if(key instanceof Double){
          final double d;
          if((d=(double)key)==d){
            final long bits;
            if((bits=Double.doubleToRawLongBits(d))==0){
              queryComparator=this::comparePos0;
            }else if(bits==Long.MIN_VALUE){
              queryComparator=this::compareNeg0;
            }else{
              queryComparator=super.getQueryComparator(d);
            }
          }else{
            return Double.isNaN(arr[tail]);
          }
        }else if(key instanceof Integer || key instanceof Byte || key instanceof Short){
          final int i;
          queryComparator=((i=((Number)key).intValue())==0)?this::comparePos0:super.getQueryComparator(i);
        }else if(key instanceof Long){
          final long l;
          if(!TypeUtil.checkCastToDouble(l=(long)key)){
            return false;
          }
          queryComparator=l==0?this::comparePos0:super.getQueryComparator(l);
        }else if(key instanceof Float){
          final float f;
          if((f=(float)key)==f){
            switch(Float.floatToRawIntBits(f)){
              case 0xff800000:
                return arr[this.head]==Double.NEGATIVE_INFINITY;
              case 0x7f800000:
                final double[] arr;
                return ((arr=this.arr)[tail]==Double.POSITIVE_INFINITY)
                     ||(tail!=this.head && (arr[tail==0?arr.length-1:tail-1]==Double.POSITIVE_INFINITY));
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
            return Double.isNaN(arr[tail]);
          }
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
       final double[] arr;
       if(Double.isNaN((arr=this.arr)[tail])){
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
      final double[] arr;
      if((arr=this.arr)[tail]==Double.POSITIVE_INFINITY){
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
        if(arr[tail]==Double.POSITIVE_INFINITY){
          arr[tail]=Double.NaN;
          this.tail=tail;
          return true;
        }
      }
      return false;
    }
    @Override boolean uncheckedremoveNegInf(int tail){
      final double[] arr;
      int head;
      if((arr=this.arr)[head=this.head]==Double.NEGATIVE_INFINITY){
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
    @Override int comparePos1(double key){
      if(1>key){
        //insert hi;
        return 1;
      }
      if(1==key){
        return 0;
      }
      return -1;
    }
    @Override int comparePos0(double key){
      if(0>key){
        //insert hi
        return 1;
      }
      final long bits;
      if((bits=Double.doubleToRawLongBits(key))==0){ //pos0
        return 0;
      }
      if(bits==Long.MIN_VALUE){ //neg0
        return 1;
      }
      return -1; //0<key || key!=key so insert lo
    }
    @Override int compareNeg0(double key){
      if(0>key){
        //insert hi
        return 1;
      }
      if(Double.doubleToRawLongBits(key)==Long.MIN_VALUE){
        return 0;
      }
      return -1; //0<key || key!=key so insert lo
    }
    @Override boolean uncheckedAddNaN(int tail){
      final double[] arr;
      if(!Double.isNaN((arr=this.arr)[tail])){
        super.insertAtTail(arr,Double.NaN,this.head,tail);
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddPosInf(int tail){
      double[] arr;
      final double topVal;
      if((topVal=(arr=this.arr)[tail])!=Double.POSITIVE_INFINITY){
        int head=this.head;
        if(Double.isNaN(topVal)){
          //add it before the tail
          int newTail;
          switch(Integer.signum((newTail=tail+1)-head)){
            case 0:
              //fragmented must grow
              final double[] tmp;
              int arrLength;
              ArrCopy.semicheckedCopy(arr,0,tmp=new double[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
              ArrCopy.uncheckedCopy(arr,newTail,tmp,head-=(arrLength-=newTail),arrLength);
              this.head=head;
              this.arr=arr=tmp;
              break;
            default:
              //nonfragmented
              if(newTail==arr.length){
                if(head==0){
                  //must grow
                  ArrCopy.semicheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(newTail)],0,tail);
                  this.arr=arr;
                }else{
                  newTail=0;
                }
              }
            case -1:
              //fragmented
          }
          arr[tail]=Double.POSITIVE_INFINITY;
          arr[newTail]=Double.NaN;
          this.tail=newTail;
        }else{
          super.insertAtTail(arr,Double.POSITIVE_INFINITY,head,tail);
        }
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddNegInf(int tail){
      final double[] arr;
      final int head;
      if(((arr=this.arr)[head=this.head])!=Double.NEGATIVE_INFINITY){
        super.insertAtHead(arr,Double.NEGATIVE_INFINITY,head,tail);
        return true;
      }
      return false;
    }
    @Override int insertionCompare(double key1,double key2){
      if(key1>key2){
        return 1;
      }
      if(key1==key2){
        return 0;
      }
      return -1;
    }
  }
  public static class Descending extends DoubleNavigableSetImpl implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,double[] arr,int tail){
      super(head,arr,tail);
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
    @Override public Double ceiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
      return null;
    }
    @Override public Double floor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Double higher(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Double lower(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(double toElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(double fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(double fromElement, boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble subSet(double fromElement,double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble subSet(double fromElement, boolean fromInclusive, double toElement, boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfDouble descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(Double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(Double fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble subSet(Double fromElement,Double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final double[] dst;
        final int head,cloneTail;
        int size;
        if((size=(++tail)-(head=this.head))>0){
          ArrCopy.uncheckedCopy(arr,head,dst=new double[size],0,size);
          cloneTail=size-1;
        }else{
          final double[] arr;
          dst=new double[size+=(arr=this.arr).length];
          cloneTail=size-1;
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
          ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        }
        return new Descending(0,dst,cloneTail);
      }
      return new Descending();
    }
    @Override public DoubleComparator comparator(){
      return DoubleComparator::descendingCompare;
    }
    @Override int comparePos1(double key){
      if(1>key){
        return -1;
      }
      if(1==key){
        return 0;
      }
      return 1;
    }
    @Override int comparePos0(double key){
      if(0>key){
        return -1;
      }
      final long bits;
      if((bits=Double.doubleToRawLongBits(key))==0){ //pos0
        return 0;
      }else if(bits==Long.MIN_VALUE){ //neg0
        return -1;
      }
      return 1; //key>pos0 || key!=key
    }
    @Override int compareNeg0(double key){
      if(0>key){
        return -1;
      }
      if(Double.doubleToRawLongBits(key)==Long.MIN_VALUE){
        return 0;
      }
      return 1; //key>neg0 || key!=key
    }
    @Override boolean uncheckedAddNaN(int tail){
      final double[] arr;
      final int head;
      if(!Double.isNaN((arr=this.arr)[head=this.head])){
        super.insertAtHead(arr,Double.NaN,head,tail);
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddPosInf(int tail){
      int head;
      double[] arr;
      final double bottomVal;
      if((bottomVal=(arr=this.arr)[head=this.head])!=Double.POSITIVE_INFINITY){
        if(Double.isNaN(bottomVal)){
          //add just after head
          int newHead;
          switch(Integer.signum(tail-(newHead=head-1))){
            case 0:
              //fragmented must grow
              final double[] tmp;
              int arrLength;
              ArrCopy.uncheckedCopy(arr,0,tmp=new double[tail=OmniArray.growBy50Pct(arrLength=arr.length)],0,head);
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
                  ArrCopy.semicheckedCopy(arr,0,arr=new double[head],head-=newHead,newHead);
                  --head;
                  this.arr=arr;
                }
                newHead=head-1;
              }
            case -1:
              //fragmented
          }
          arr[head]=Double.POSITIVE_INFINITY;
          arr[newHead]=Double.NaN;
          this.head=newHead;
        }else{
          super.insertAtHead(arr,Double.POSITIVE_INFINITY,head,tail);
        }
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddNegInf(int tail){
      final double[] arr;
      if(((arr=this.arr)[tail])!=Double.NEGATIVE_INFINITY){
        super.insertAtTail(arr,Double.NEGATIVE_INFINITY,this.head,tail);
        return true;
      }
      return false;
    }
    @Override public boolean contains(double key){
      final int tail;
      if((tail=this.tail)!=-1){
        if(key==key){
          final DoubleToIntFunction queryComparator;
          final long bits;
          if((bits=Double.doubleToRawLongBits(key))==0){
            queryComparator=this::comparePos0;
          }else if(bits==Long.MIN_VALUE){
            queryComparator=this::compareNeg0;
          }else{
            queryComparator=super.getQueryComparator(key);
          }
          return super.uncheckedContainsMatch(this.head,tail,queryComparator);
        }
        return Double.isNaN(arr[head]);
      }
      return false;
    }
    @Override public boolean contains(float key){
      int tail;
      if((tail=this.tail)!=-1){
        if(key==key){
          final DoubleToIntFunction queryComparator;
          switch(Float.floatToRawIntBits(key)){
            case 0xff800000:
              return this.arr[tail]==Double.NEGATIVE_INFINITY;
            case 0x7f800000: //pos inf
              final double[] arr;
              int head;
              return ((arr=this.arr)[head=this.head]==Double.POSITIVE_INFINITY) || (tail!=head && arr[++head==arr.length?0:head]==Double.POSITIVE_INFINITY);
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
        return Double.isNaN(arr[head]);
      }
      return false;
    }
    @Override public boolean contains(Object key){
      final int tail;
      if((tail=this.tail)!=-1){
        final DoubleToIntFunction queryComparator;
        if(key instanceof Double){
          final double d;
          if((d=(double)key)==d){
            final long bits;
            if((bits=Double.doubleToRawLongBits(d))==0){
              queryComparator=this::comparePos0;
            }else if(bits==Long.MIN_VALUE){
              queryComparator=this::compareNeg0;
            }else{
              queryComparator=super.getQueryComparator(d);
            }
          }else{
            return Double.isNaN(arr[head]);
          }
        }else if(key instanceof Integer || key instanceof Byte || key instanceof Short){
          final int i;
          queryComparator=((i=((Number)key).intValue())==0)?this::comparePos0:super.getQueryComparator(i);
        }else if(key instanceof Long){
          final long l;
          if(!TypeUtil.checkCastToDouble(l=(long)key)){
            return false;
          }
          queryComparator=l==0?this::comparePos0:super.getQueryComparator(l);
        }else if(key instanceof Float){
          final float f;
          if((f=(float)key)==f){
            switch(Float.floatToRawIntBits(f)){
             case 0xff800000:
                return this.arr[tail]==Double.NEGATIVE_INFINITY;
              case 0x7f800000: //pos inf
                final double[] arr;
                int head;
                return ((arr=this.arr)[head=this.head]==Double.POSITIVE_INFINITY) || (tail!=head && arr[++head==arr.length?0:head]==Double.POSITIVE_INFINITY);
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
            return Double.isNaN(arr[head]);
          }
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
       final double[] arr;
       int head;
       if(Double.isNaN((arr=this.arr)[head=this.head])){
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
      final double[] arr;
      int head;
      if((arr=this.arr)[head=this.head]==Double.POSITIVE_INFINITY){
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
        if(arr[head]==Double.POSITIVE_INFINITY){
          arr[head]=Double.NaN;
          this.head=head;
          return true;
        }
      }
      return false;
    }
    @Override boolean uncheckedremoveNegInf(int tail){
      final double[] arr;
      if((arr=this.arr)[tail]==Double.NEGATIVE_INFINITY){
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
    @Override int insertionCompare(double key1,double key2){
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
