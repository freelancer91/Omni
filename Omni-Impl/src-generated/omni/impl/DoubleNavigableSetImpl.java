package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.ArrCopy;
import omni.api.OmniIterator;
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
  private static int privateCompare(double key1,double key2){
    if(key1==key2){
      return 0;
    }
    if(key1>key2){
      return 1;
    }
    return -1;
  }
  private static int comparePos0(double key){
    if(key<0){
      return 1;
    }
    final long bits;
    if((bits=Double.doubleToRawLongBits(key))==0){
      return 0;
    }else if(bits==Long.MIN_VALUE){
      return 1;
    }
    return -1;
  }
  private static int compareNeg0(double key){
    if(key<0){
      return 1;
    }
    if(Double.doubleToRawLongBits(key)==Long.MIN_VALUE){
      return 0;
    }
    return -1;
  }
  private boolean uncheckedAddNegInf(int tail){
    final double[] arr;
    final int head;
    if((arr=this.arr)[head=this.head]!=Double.NEGATIVE_INFINITY){
      super.insertAtHead(arr,Double.NEGATIVE_INFINITY,head,tail);
      return true;
    }
    return false;
  }
  private boolean uncheckedAddUndefined(int tail){
    double[] arr;
    if(!Double.isNaN((arr=this.arr)[tail])){
      int head;
      switch(Integer.signum((++tail)-(head=this.head))){
        case 0:
          //fragmented must grow
          final double[] tmp;
          int arrLength;
          ArrCopy.uncheckedCopy(arr,0,tmp=new double[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
          ArrCopy.uncheckedCopy(arr,tail,tmp,head-=(arrLength-=tail),arrLength);
          this.head=head;
          this.arr=arr=tmp;
          break;
        default:
          //nonfragmented
          if(tail==arr.length){
            if(head==0){
              //must grow
              ArrCopy.uncheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(tail)],0,tail);
              this.arr=arr;
            }else{
              tail=0;
            }
          }
        case -1:
          //fragmented
      }
      arr[tail]=Double.NaN;
      this.tail=tail;
      return true;
    }
    return false;
  }
  @Override public boolean add(double key){
    int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final DoubleToIntFunction compareFunc;
        final long bits;
        if((bits=Double.doubleToRawLongBits(key))==0){
          compareFunc=DoubleNavigableSetImpl::comparePos0;
        }else if(bits==Long.MIN_VALUE){
          compareFunc=DoubleNavigableSetImpl::compareNeg0;
        }else{
          return super.uncheckedAdd(tail,key,DoubleNavigableSetImpl::privateCompare);
        }
        return super.uncheckedAdd(tail,key,compareFunc);
      }
      return uncheckedAddUndefined(tail);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Double key){
    return add((double)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key){
        return super.uncheckedAdd(tail,(double)1,DoubleNavigableSetImpl::privateCompare);
      }
      return super.uncheckedAdd(tail,(double)0,DoubleNavigableSetImpl::comparePos0);
    }else{
      super.insertAtMiddle(TypeUtil.castToDouble(key));
      return true;
    }
  }
  @Override public boolean add(int key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==0){
        return super.uncheckedAdd(tail,0,DoubleNavigableSetImpl::comparePos0);
      }
      return super.uncheckedAdd(tail,key,DoubleNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(long key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==0){
        return super.uncheckedAdd(tail,0,DoubleNavigableSetImpl::comparePos0);
      }
      return super.uncheckedAdd(tail,key,DoubleNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final DoubleToIntFunction compareFunc;
        switch(Float.floatToRawIntBits(key)){
          default:
            return super.uncheckedAdd(tail,key,DoubleNavigableSetImpl::privateCompare);
          case 0xff800000:
            return this.uncheckedAddNegInf(tail);
          case 0:
            compareFunc=DoubleNavigableSetImpl::comparePos0;
            break;
          case Integer.MIN_VALUE:
            compareFunc=DoubleNavigableSetImpl::compareNeg0;
        }
        return super.uncheckedAdd(tail,key,compareFunc);
      }
      return uncheckedAddUndefined(tail);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  private static DoubleToIntFunction getSearchFunction(double key){
    return (k)->privateCompare(k,key);
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final DoubleToIntFunction searchFunction;
      if(key instanceof Double){
        final double k;
        if((k=(double)key)==k){
          final long bits;
          if((bits=Double.doubleToRawLongBits(k))==0){
            searchFunction=DoubleNavigableSetImpl::comparePos0;
          }else if(bits==Long.MIN_VALUE){
            searchFunction=DoubleNavigableSetImpl::compareNeg0;
          }else{
            searchFunction=getSearchFunction(k);
          }
        }else{
          return Double.isNaN(arr[tail]);
        }
      }else if(key instanceof Integer || key instanceof Byte || key instanceof Short){
        final int k;
        if((k=((Number)key).intValue())==0){
          searchFunction=DoubleNavigableSetImpl::comparePos0;
        }else{
          searchFunction=getSearchFunction(k);
        }
      }else if(key instanceof Long){
        final long k;
        if(!TypeUtil.checkCastToDouble(k=(long)key)){
          return false;
        }
        if(k==0){
          searchFunction=DoubleNavigableSetImpl::comparePos0;
        }else{
          searchFunction=getSearchFunction(k);
        }
      }else if(key instanceof Float){
        final float k;
        if((k=(float)key)==k){
          switch(Float.floatToRawIntBits(k)){
            case 0xff800000:
              return arr[head]==Double.NEGATIVE_INFINITY;
            case 0:
              searchFunction=DoubleNavigableSetImpl::comparePos0;
              break;
            case Integer.MIN_VALUE:
              searchFunction=DoubleNavigableSetImpl::compareNeg0;
              break;
            default:
              searchFunction=getSearchFunction(k);
          }
        }else{
          return Double.isNaN(arr[tail]);
        }
      }else if(key instanceof Character){
        final char k;
        if((k=(char)key)==0){
          searchFunction=DoubleNavigableSetImpl::comparePos0;
        }else{
          searchFunction=getSearchFunction(k);
        }
      }else if(key instanceof Boolean){
        if((boolean)key){
          searchFunction=getSearchFunction(1);
        }else{
          searchFunction=DoubleNavigableSetImpl::comparePos0;
        }
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(head,tail,searchFunction);
    }
    return false;
  }
  @Override public boolean remove(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      jumpToRemoveTail:for(;;){
        final DoubleToIntFunction searchFunction;
        if(key instanceof Double){
          final double k;
          if((k=(double)key)==k){
            final long bits;
            if((bits=Double.doubleToRawLongBits(k))==0){
              searchFunction=DoubleNavigableSetImpl::comparePos0;
            }else if(bits==Long.MIN_VALUE){
              searchFunction=DoubleNavigableSetImpl::compareNeg0;
            }else{
              searchFunction=getSearchFunction(k);
            }
          }else{
            break jumpToRemoveTail;
          }
        }else if(key instanceof Integer || key instanceof Byte || key instanceof Short){
          final int k;
          if((k=((Number)key).intValue())==0){
            searchFunction=DoubleNavigableSetImpl::comparePos0;
          }else{
            searchFunction=getSearchFunction(k);
          }
        }else if(key instanceof Long){
          final long k;
          if(!TypeUtil.checkCastToDouble(k=(long)key)){
            return false;
          }
          if(k==0){
            searchFunction=DoubleNavigableSetImpl::comparePos0;
          }else{
            searchFunction=getSearchFunction(k);
          }
        }else if(key instanceof Float){
          final float k;
          if((k=(float)key)==k){
            switch(Float.floatToRawIntBits(k)){
              case 0xff800000:
                return arr[head]==Double.NEGATIVE_INFINITY;
              case 0:
                searchFunction=DoubleNavigableSetImpl::comparePos0;
                break;
              case Integer.MIN_VALUE:
                searchFunction=DoubleNavigableSetImpl::compareNeg0;
                break;
              default:
                searchFunction=getSearchFunction(k);
            }
          }else{
            break jumpToRemoveTail;
          }
        }else if(key instanceof Character){
          final char k;
          if((k=(char)key)==0){
            searchFunction=DoubleNavigableSetImpl::comparePos0;
          }else{
            searchFunction=getSearchFunction(k);
          }
        }else if(key instanceof Boolean){
          if((boolean)key){
            searchFunction=getSearchFunction(1);
          }else{
            searchFunction=DoubleNavigableSetImpl::comparePos0;
          }
        }else{
          return false;
        }
        return super.uncheckedRemoveMatch(tail,searchFunction);
      }
      final double[] arr;
      if(Double.isNaN((arr=this.arr)[tail])){
        switch(Integer.signum(tail-head)){
          case 0:
            this.tail=-1;
            break;
          case -1:
            if(tail==0){
              this.tail=arr.length-1;
              break;
            }
          default:
            this.tail=tail-1;
        }
        return true;
      }
      return false;
    }
    return false;
  }
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,key?getSearchFunction(1):DoubleNavigableSetImpl::comparePos0);
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key?getSearchFunction(1):DoubleNavigableSetImpl::comparePos0);
  }
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,key==0?DoubleNavigableSetImpl::comparePos0:getSearchFunction(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key==0?DoubleNavigableSetImpl::comparePos0:getSearchFunction(key));
  }
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToDouble(key) && super.uncheckedContainsMatch(head,tail,key==0?DoubleNavigableSetImpl::comparePos0:getSearchFunction(key));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToDouble(key) && super.uncheckedRemoveMatch(tail,key==0?DoubleNavigableSetImpl::comparePos0:getSearchFunction(key));
  }
  @Override public boolean contains(float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final DoubleToIntFunction searchFunction;
        switch(Float.floatToRawIntBits(key)){
          case 0xff800000:
            return arr[head]==Double.NEGATIVE_INFINITY;
          case 0:
            searchFunction=DoubleNavigableSetImpl::comparePos0;
            break;
          case Integer.MIN_VALUE:
            searchFunction=DoubleNavigableSetImpl::compareNeg0;
            break;
          default:
            searchFunction=getSearchFunction(key);
        }
        return super.uncheckedContainsMatch(head,tail,searchFunction);
      }
      return Double.isNaN(arr[tail]);
    }
    return false;
  }
  @Override public boolean removeVal(float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final DoubleToIntFunction searchFunction;
        switch(Float.floatToRawIntBits(key)){
          case 0:
            searchFunction=DoubleNavigableSetImpl::comparePos0;
            break;
          case Integer.MIN_VALUE:
            searchFunction=DoubleNavigableSetImpl::compareNeg0;
            break;
          default:
            searchFunction=getSearchFunction(key);
        }
        return super.uncheckedRemoveMatch(tail,searchFunction);
      }
      final double[] arr;
      if(Double.isNaN((arr=this.arr)[tail])){
        switch(Integer.signum(tail-head)){
          case 0:
            this.tail=-1;
            break;
          case -1:
            if(tail==0){
              this.tail=arr.length-1;
              break;
            }
          default:
            this.tail=tail-1;
        }
        return true;
      }
    }
    return false;
  }
  @Override public boolean contains(double key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final DoubleToIntFunction searchFunction;
        final long bits;
        if((bits=Double.doubleToRawLongBits(key))==0){
          searchFunction=DoubleNavigableSetImpl::comparePos0;
        }else if(bits==Long.MIN_VALUE){
          searchFunction=DoubleNavigableSetImpl::compareNeg0;
        }else{
          searchFunction=getSearchFunction(key);
        }
        return super.uncheckedContainsMatch(head,tail,searchFunction);
      }
      return Double.isNaN(arr[tail]);
    }
    return false;
  }
  @Override public boolean removeVal(double key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final DoubleToIntFunction searchFunction;
        final long bits;
        if((bits=Double.doubleToRawLongBits(key))==0){
          searchFunction=DoubleNavigableSetImpl::comparePos0;
        }else if(bits==Long.MIN_VALUE){
          searchFunction=DoubleNavigableSetImpl::compareNeg0;
        }else{
          searchFunction=getSearchFunction(key);
        }
        return super.uncheckedRemoveMatch(tail,searchFunction);
      }
      final double[] arr;
      if(Double.isNaN((arr=this.arr)[tail])){
        switch(Integer.signum(tail-head)){
          case 0:
            this.tail=-1;
            break;
          case -1:
            if(tail==0){
              this.tail=arr.length-1;
              break;
            }
          default:
            this.tail=tail-1;
        }
        return true;
      }
    }
    return false;
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
  @Override public Double ceiling(double val){
    //TODO
    throw new omni.util.NotYetImplementedException();
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
  public static class Ascending extends DoubleNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,double[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public DoubleComparator comparator(){
      return Double::compare;
    }
    @Override public double firstDouble(){
      return (double)arr[head];
    }
    @Override public double lastDouble(){
      return (double)arr[tail];
    }
    @Override public OmniNavigableSet.OfDouble descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfDouble descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(double toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(Double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(double fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(double fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(Double fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble subSet(double fromElement,double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble subSet(double fromElement,boolean inclusiveFrom,double toElement,boolean inclusiveTo){
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
  }
  public static class Descending extends DoubleNavigableSetImpl implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,double[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public DoubleComparator comparator(){
      return DoubleComparator::descendingCompare;
    }
    @Override public double firstDouble(){
      return (double)arr[tail];
    }
    @Override public double lastDouble(){
      return (double)arr[head];
    }
    @Override public OmniNavigableSet.OfDouble descendingSet(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfDouble descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(double toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble headSet(Double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(double fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(double fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble tailSet(Double fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble subSet(double fromElement,double toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfDouble subSet(double fromElement,boolean inclusiveFrom,double toElement,boolean inclusiveTo){
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
      @Override public Double ceiling(double val){
        return super.floor(val);
      }
      @Override public Double floor(double val){
        return super.ceiling(val);
      }
      @Override public Double higher(double val){
        return super.lower(val);
      }
      @Override public Double lower(double val){
        return super.higher(val);
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
  }
}
