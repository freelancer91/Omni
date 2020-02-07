package omni.impl;
import java.io.Serializable;
import omni.api.OmniSortedSet;
import omni.util.ArrCopy;
import omni.api.OmniIterator;
import omni.util.OmniArray;
import omni.function.FloatPredicate;
import omni.function.FloatConsumer;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.util.OmniArray;
import omni.function.FloatComparator;
import omni.util.TypeUtil;
import omni.function.FloatToIntFunction;
public abstract class FloatNavigableSetImpl
  extends FloatUntetheredArrSeq implements OmniSortedSet.OfFloat
{
  FloatNavigableSetImpl(int head,float[] arr,int tail){
    super(head,arr,tail);
  }
  FloatNavigableSetImpl(){
    super();
  }
  private static int privateCompare(float key1,float key2){
    if(key1==key2){
      return 0;
    }
    if(key1>key2){
      return 1;
    }
    return -1;
  }
  private static int comparePos0(float key){
    if(key<0){
      return 1;
    }
    switch(Float.floatToRawIntBits(key)){
      case 0:
        return 0;
      case Integer.MIN_VALUE:
        return 1;
      default:
    }
    return -1;
  }
  private static int compareNeg0(float key){
    if(key<0){
      return 1;
    }
    if(Float.floatToRawIntBits(key)==Integer.MIN_VALUE){
      return 0;
    }
    return -1;
  }
  private boolean uncheckedAddNegInf(int tail){
    final float[] arr;
    final int head;
    if((arr=this.arr)[head=this.head]!=Float.NEGATIVE_INFINITY){
      super.insertAtHead(arr,Float.NEGATIVE_INFINITY,head,tail);
      return true;
    }
    return false;
  }
  private static FloatToIntFunction getSearchFunction(float key){
    return (k)->privateCompare(k,key);
  }
  private boolean uncheckedAddUndefined(int tail){
    float[] arr;
    if(!Float.isNaN((arr=this.arr)[tail])){
      int head;
      switch(Integer.signum((++tail)-(head=this.head))){
        case 0:
          //fragmented must grow
          final float[] tmp;
          int arrLength;
          ArrCopy.uncheckedCopy(arr,0,tmp=new float[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
          ArrCopy.uncheckedCopy(arr,tail,tmp,head-=(arrLength-=tail),arrLength);
          this.head=head;
          this.arr=arr=tmp;
          break;
        default:
          //nonfragmented
          if(tail==arr.length){
            if(head==0){
              //must grow
              ArrCopy.uncheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(tail)],0,tail);
              this.arr=arr;
            }else{
              tail=0;
            }
          }
        case -1:
          //fragmented
      }
      arr[tail]=Float.NaN;
      this.tail=tail;
      return true;
    }
    return false;
  }
  @Override public boolean add(float key){
    int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final FloatToIntFunction compareFunc;
        switch(Float.floatToRawIntBits(key)){
          default:
            return super.uncheckedAdd(tail,key,FloatNavigableSetImpl::privateCompare);
          case 0xff800000:
            return this.uncheckedAddNegInf(tail);
          case 0:
            compareFunc=FloatNavigableSetImpl::comparePos0;
            break;
          case Integer.MIN_VALUE:
            compareFunc=FloatNavigableSetImpl::compareNeg0;
        }
        return super.uncheckedAdd(tail,key,compareFunc);
      }
      return uncheckedAddUndefined(tail);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(Float key){
    return add((float)key);
  }
  @Override public boolean add(boolean key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key){
        return super.uncheckedAdd(tail,(float)1,FloatNavigableSetImpl::privateCompare);
      }
      return super.uncheckedAdd(tail,(float)0,FloatNavigableSetImpl::comparePos0);
    }else{
      super.insertAtMiddle(TypeUtil.castToFloat(key));
      return true;
    }
  }
  @Override public boolean add(char key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==0){
        return super.uncheckedAdd(tail,0,FloatNavigableSetImpl::comparePos0);
      }
      return super.uncheckedAdd(tail,key,FloatNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(short key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==0){
        return super.uncheckedAdd(tail,0,FloatNavigableSetImpl::comparePos0);
      }
      return super.uncheckedAdd(tail,key,FloatNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(int key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==0){
        return super.uncheckedAdd(tail,0,FloatNavigableSetImpl::comparePos0);
      }
      return super.uncheckedAdd(tail,key,FloatNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean add(long key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==0){
        return super.uncheckedAdd(tail,0,FloatNavigableSetImpl::comparePos0);
      }
      return super.uncheckedAdd(tail,key,FloatNavigableSetImpl::privateCompare);
    }else{
      super.insertAtMiddle(key);
      return true;
    }
  }
  @Override public boolean contains(Object key){
    final int tail;
    if((tail=this.tail)!=-1){
      final FloatToIntFunction searchFunction;
      if(key instanceof Float){
        final float k;
        if((k=(float)key)==k){
          switch(Float.floatToRawIntBits(k)){
            case 0xff800000:
              return arr[head]==Float.NEGATIVE_INFINITY;
            case 0:
              searchFunction=FloatNavigableSetImpl::comparePos0;
              break;
            case Integer.MIN_VALUE:
              searchFunction=FloatNavigableSetImpl::compareNeg0;
              break;
            default:
              searchFunction=getSearchFunction(k);
          }
        }else{
          return Float.isNaN(arr[tail]);
        }
      }else if(key instanceof Integer){
        final int k;
        if(!TypeUtil.checkCastToFloat(k=(int)key)){
          return false;
        }
        if(k==0){
          searchFunction=FloatNavigableSetImpl::comparePos0;
        }else{
          searchFunction=getSearchFunction(k);
        }
      }else if(key instanceof Long){
        final long k;
        if(!TypeUtil.checkCastToFloat(k=(long)key)){
          return false;
        }
        if(k==0){
          searchFunction=FloatNavigableSetImpl::comparePos0;
        }else{
          searchFunction=getSearchFunction(k);
        }
      }else if(key instanceof Double){
        final double d;
        final float k;
        if((d=(double)key)==(k=(float)d)){
          switch(Float.floatToRawIntBits(k)){
            case 0xff800000:
              return arr[head]==Float.NEGATIVE_INFINITY;
            case 0:
              searchFunction=FloatNavigableSetImpl::comparePos0;
              break;
            case Integer.MIN_VALUE:
              searchFunction=FloatNavigableSetImpl::compareNeg0;
              break;
            default:
              searchFunction=getSearchFunction(k);
          }
        }else{
          return k!=k && Float.isNaN(arr[tail]);
        }
      }else if(key instanceof Byte || key instanceof Short){
        final short k;
        if((k=((Number)key).shortValue())==0){
          searchFunction=FloatNavigableSetImpl::comparePos0;
        }else{
          searchFunction=getSearchFunction(k);
        }
      }else if(key instanceof Character){
        final char k;
        if((k=(char)key)==0){
          searchFunction=FloatNavigableSetImpl::comparePos0;
        }else{
          searchFunction=getSearchFunction(k);
        }
      }else if(key instanceof Boolean){
        if((boolean)key){
          searchFunction=getSearchFunction(1);
        }else{
          searchFunction=FloatNavigableSetImpl::comparePos0;
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
        final FloatToIntFunction searchFunction;
        if(key instanceof Float){
          final float k;
          if((k=(float)key)==k){
            switch(Float.floatToRawIntBits(k)){
              case 0xff800000:
                return arr[head]==Float.NEGATIVE_INFINITY;
              case 0:
                searchFunction=FloatNavigableSetImpl::comparePos0;
                break;
              case Integer.MIN_VALUE:
                searchFunction=FloatNavigableSetImpl::compareNeg0;
                break;
              default:
                searchFunction=getSearchFunction(k);
            }
          }else{
            break jumpToRemoveTail;
          }
        }else if(key instanceof Integer){
          final int k;
          if(!TypeUtil.checkCastToFloat(k=(int)key)){
            return false;
          }
          if(k==0){
            searchFunction=FloatNavigableSetImpl::comparePos0;
          }else{
            searchFunction=getSearchFunction(k);
          }
        }else if(key instanceof Long){
          final long k;
          if(!TypeUtil.checkCastToFloat(k=(long)key)){
            return false;
          }
          if(k==0){
            searchFunction=FloatNavigableSetImpl::comparePos0;
          }else{
            searchFunction=getSearchFunction(k);
          }
        }else if(key instanceof Double){
          final double d;
          final float k;
          if((d=(double)key)==(k=(float)d)){
            switch(Float.floatToRawIntBits(k)){
              case 0xff800000:
                return arr[head]==Float.NEGATIVE_INFINITY;
              case 0:
                searchFunction=FloatNavigableSetImpl::comparePos0;
                break;
              case Integer.MIN_VALUE:
                searchFunction=FloatNavigableSetImpl::compareNeg0;
                break;
              default:
                searchFunction=getSearchFunction(k);
            }
          }else if(k!=k){
            break jumpToRemoveTail;
          }else{
            return false;
          }
        }else if(key instanceof Byte || key instanceof Short){
          final short k;
          if((k=((Number)key).shortValue())==0){
            searchFunction=FloatNavigableSetImpl::comparePos0;
          }else{
            searchFunction=getSearchFunction(k);
          }
        }else if(key instanceof Character){
          final char k;
          if((k=(char)key)==0){
            searchFunction=FloatNavigableSetImpl::comparePos0;
          }else{
            searchFunction=getSearchFunction(k);
          }
        }else if(key instanceof Boolean){
          if((boolean)key){
            searchFunction=getSearchFunction(1);
          }else{
            searchFunction=FloatNavigableSetImpl::comparePos0;
          }
        }else{
          return false;
        }
        return super.uncheckedRemoveMatch(tail,searchFunction);
      }
      final float[] arr;
      if(Float.isNaN((arr=this.arr)[tail])){
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
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,key?getSearchFunction(1):FloatNavigableSetImpl::comparePos0);
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key?getSearchFunction(1):FloatNavigableSetImpl::comparePos0);
  }
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,key!=0?getSearchFunction(key):FloatNavigableSetImpl::comparePos0);
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key!=0?getSearchFunction(key):FloatNavigableSetImpl::comparePos0);
  }
  @Override public boolean contains(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(head,tail,key!=0?getSearchFunction(key):FloatNavigableSetImpl::comparePos0);
  }
  @Override public boolean removeVal(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key!=0?getSearchFunction(key):FloatNavigableSetImpl::comparePos0);
  }
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key) && super.uncheckedContainsMatch(head,tail,key==0?FloatNavigableSetImpl::comparePos0:getSearchFunction(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key) && super.uncheckedRemoveMatch(tail,key==0?FloatNavigableSetImpl::comparePos0:getSearchFunction(key));
  }
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key) && super.uncheckedContainsMatch(head,tail,key==0?FloatNavigableSetImpl::comparePos0:getSearchFunction(key));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key) && super.uncheckedRemoveMatch(tail,key==0?FloatNavigableSetImpl::comparePos0:getSearchFunction(key));
  }
  @Override public boolean contains(float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final FloatToIntFunction searchFunction;
        switch(Float.floatToRawIntBits(key)){
          case 0xff800000:
            return arr[head]==Float.NEGATIVE_INFINITY;
          case 0:
            searchFunction=FloatNavigableSetImpl::comparePos0;
            break;
          case Integer.MIN_VALUE:
            searchFunction=FloatNavigableSetImpl::compareNeg0;
            break;
          default:
            searchFunction=getSearchFunction(key);
        }
        return super.uncheckedContainsMatch(head,tail,searchFunction);
      }
      return Float.isNaN(arr[tail]);
    }
    return false;
  }
  @Override public boolean removeVal(float key){
    final int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final FloatToIntFunction searchFunction;
        switch(Float.floatToRawIntBits(key)){
          case 0:
            searchFunction=FloatNavigableSetImpl::comparePos0;
            break;
          case Integer.MIN_VALUE:
            searchFunction=FloatNavigableSetImpl::compareNeg0;
            break;
          default:
            searchFunction=getSearchFunction(key);
        }
        return super.uncheckedRemoveMatch(tail,searchFunction);
      }
      final float[] arr;
      if(Float.isNaN((arr=this.arr)[tail])){
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
      final float f;
      if((f=(float)key)==key){
        final FloatToIntFunction searchFunction;
        switch(Float.floatToRawIntBits(f)){
          case 0xff800000:
            return arr[head]==Float.NEGATIVE_INFINITY;
          case 0:
            searchFunction=FloatNavigableSetImpl::comparePos0;
            break;
          case Integer.MIN_VALUE:
            searchFunction=FloatNavigableSetImpl::compareNeg0;
            break;
          default:
            searchFunction=getSearchFunction(f);
        }
        return super.uncheckedContainsMatch(head,tail,searchFunction);
      }
      return f!=f && Float.isNaN(arr[tail]);
    }
    return false;
  }
  @Override public boolean removeVal(double key){
    final int tail;
    if((tail=this.tail)!=-1){
      final float f;
      if((f=(float)key)==key){
        final FloatToIntFunction searchFunction;
        switch(Float.floatToRawIntBits(f)){
          case 0:
            searchFunction=FloatNavigableSetImpl::comparePos0;
            break;
          case Integer.MIN_VALUE:
            searchFunction=FloatNavigableSetImpl::compareNeg0;
            break;
          default:
            searchFunction=getSearchFunction(f);
        }
        return super.uncheckedRemoveMatch(tail,searchFunction);
      }
      else if(f!=f){
        final float[] arr;
        if(Float.isNaN((arr=this.arr)[tail])){
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
    }
    return false;
  }
  private static class AscendingFullView extends FloatUntetheredArrSeq.AbstractFullView implements OmniSortedSet.OfFloat,Cloneable,Serializable
  {
    AscendingFullView(FloatUntetheredArrSeq root){
      super(root);
    }
    @Override public void forEach(Consumer<? super Float> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfFloat iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      int tail;
      final FloatUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new float[size],0,size);
        }else{
          final float[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new float[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public Float[] toArray(){
      int tail;
      final FloatUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        Float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new Float[size],0,size);
        }else{
          final float[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new Float[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      int tail;
      final FloatUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        double[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedCopy(root.arr,head,dst=new double[size],0,size);
        }else{
          final float[] arr;
          ArrCopy.uncheckedCopy(arr=root.arr,head,dst=new double[size+=arr.length],0,size-=tail);
          ArrCopy.uncheckedCopy(arr,0,dst,size,tail);
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
  }
  private static class DescendingFullView extends FloatUntetheredArrSeq.AbstractFullView implements OmniSortedSet.OfFloat,Cloneable,Serializable
  {
    DescendingFullView(FloatUntetheredArrSeq root){
      super(root);
    }
    @Override public void forEach(Consumer<? super Float> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfFloat iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      int tail;
      final FloatUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new float[size+=arr.length],tail,size-tail);
        }else{
          final float[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new float[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public Float[] toArray(){
      int tail;
      final FloatUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        Float[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Float[size+=arr.length],tail,size-tail);
        }else{
          final float[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new Float[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      int tail;
      final FloatUntetheredArrSeq root;
      if((tail=(root=this.root).tail)!=-1){
        double[] dst;
        final int head;
        int size;
        if((size=(++tail)-(head=root.head))>0){
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new double[size+=arr.length],tail,size-tail);
        }else{
          final float[] arr;
          ArrCopy.uncheckedReverseCopy(arr=root.arr,head,dst=new double[size+=arr.length],tail,size-tail);
          ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
  }
  public static class Ascending extends FloatNavigableSetImpl implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,float[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public FloatComparator comparator(){
      return Float::compare;
    }
    @Override public float firstFloat(){
      return (float)arr[head];
    }
    @Override public float lastFloat(){
      return (float)arr[tail];
    }
    @Override public OmniSortedSet.OfFloat headSet(float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfFloat headSet(Float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfFloat tailSet(float fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfFloat tailSet(Float fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfFloat subSet(float fromElement,float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfFloat subSet(Float fromElement,Float toElement){
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
  }
  public static class Descending extends FloatNavigableSetImpl implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,float[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public FloatComparator comparator(){
      return FloatComparator::descendingCompare;
    }
    @Override public float firstFloat(){
      return (float)arr[tail];
    }
    @Override public float lastFloat(){
      return (float)arr[head];
    }
    @Override public OmniSortedSet.OfFloat headSet(float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfFloat headSet(Float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfFloat tailSet(float fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfFloat tailSet(Float fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfFloat subSet(float fromElement,float toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniSortedSet.OfFloat subSet(Float fromElement,Float toElement){
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
      @Override public float[] toFloatArray(){
        int tail;
        if((tail=this.tail)!=-1){
          float[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new float[size+=arr.length],tail,size-tail);
          }else{
            final float[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new float[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public Float[] toArray(){
        int tail;
        if((tail=this.tail)!=-1){
          Float[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Float[size+=arr.length],tail,size-tail);
          }else{
            final float[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new Float[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
      }
      @Override public double[] toDoubleArray(){
        int tail;
        if((tail=this.tail)!=-1){
          double[] dst;
          final int head;
          int size;
          if((size=(++tail)-(head=this.head))>0){
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new double[size+=arr.length],tail,size-tail);
          }else{
            final float[] arr;
            ArrCopy.uncheckedReverseCopy(arr=this.arr,head,dst=new double[size+=arr.length],tail,size-tail);
            ArrCopy.uncheckedReverseCopy(arr,0,dst,0,tail);
          }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      /*
      @Override public Float ceiling(float val){
        return super.floor(val);
      }
      @Override public Float floor(float val){
        return super.ceiling(val);
      }
      @Override public Float higher(float val){
        return super.lower(val);
      }
      @Override public Float lower(float val){
        return super.higher(val);
      }
      */
      /*
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      */
      /*
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
      */
  }
}
