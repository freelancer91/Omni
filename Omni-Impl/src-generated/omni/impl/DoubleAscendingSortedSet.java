package omni.impl;
import omni.util.ArrCopy;
import omni.api.OmniSortedSet;
import omni.util.TypeUtil;
import omni.function.DoubleComparator;
import java.util.function.DoubleToIntFunction;
import omni.util.OmniArray;
public class DoubleAscendingSortedSet extends DoubleUntetheredArrSeqSet implements Cloneable
{
  public DoubleAscendingSortedSet(){
    super();
  }
  public DoubleAscendingSortedSet(int head,double[] arr,int tail){
    super(head,arr,tail);
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
      return new DoubleAscendingSortedSet(0,dst,cloneTail);
    }
    return new DoubleAscendingSortedSet();
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
