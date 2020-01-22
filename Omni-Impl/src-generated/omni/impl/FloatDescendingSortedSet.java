package omni.impl;
import omni.util.ArrCopy;
import omni.api.OmniSortedSet;
import omni.util.TypeUtil;
import omni.function.FloatComparator;
import omni.function.FloatToIntFunction;
import omni.util.OmniArray;
public class FloatDescendingSortedSet extends FloatUntetheredArrSeqSet implements Cloneable
{
  public FloatDescendingSortedSet(){
    super();
  }
  public FloatDescendingSortedSet(int head,float[] arr,int tail){
    super(head,arr,tail);
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
      return new FloatDescendingSortedSet(0,dst,cloneTail);
    }
    return new FloatDescendingSortedSet();
  }
  @Override public FloatComparator comparator(){
    return FloatComparator::descendingCompare;
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
}