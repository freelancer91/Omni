package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
import omni.function.DoubleComparator;
public abstract class DoubleOrderedSet
  extends DoubleUntetheredArrSeq<Double>
  implements OmniNavigableSet.OfDouble
{
  DoubleOrderedSet(int head,double[] arr,int tail){
    super(head,arr,tail);
  }
  DoubleOrderedSet(){
    super();
  }
  @Override public boolean add(Double key){
    return add((double)key);
  }
  abstract int insertionCompare(double key1,double key2);
  abstract boolean uncheckedAddNaN(int tail);
  abstract int comparePos0(double key);
  abstract int compareNeg0(double key);
  abstract boolean uncheckedAddPosInf(int tail);
  abstract boolean uncheckedAddNegInf(int tail);
  private void insertAtTail(double[] arr,double key,int head,int tail){
    switch(Integer.signum((++tail)-head)){
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
    arr[tail]=key;
    this.tail=tail;
  }
  private void insertAtHead(double[] arr,double key,int head,int tail){
      int newHead;
      switch(Integer.signum(tail-(newHead=head-1))){
        case 0:
          //fragmented must grow
          final double[] tmp;
          int arrLength;
          ArrCopy.uncheckedCopy(arr,0,tmp=new double[tail=OmniArray.growBy50Pct(arrLength=arr.length)],0,head);
          ArrCopy.uncheckedCopy(arr,head,tmp,newHead=tail-(arrLength-=head),arrLength);
          --newHead;
          this.arr=arr=tmp;
          break;
        default:
          //nonfragmented
          if(newHead==-1 && tail==(newHead=arr.length-1)){
            //must grow
            this.tail=(newHead=OmniArray.growBy50Pct(++tail))-1;
            ArrCopy.uncheckedCopy(arr,0,arr=new double[newHead],newHead-=(tail),tail);
            --newHead;
            this.arr=arr;
          }
        case -1:
          //fragmented
      }
      arr[newHead]=key;
      this.head=newHead;
    }
  @Override public boolean add(double key){
    int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final DoubleToIntFunction sorter;
        final long bits;
        if((bits=Double.doubleToRawLongBits(key)==0L){
          sorter=this::comparePos0;
        }else if(bits==Long.MIN_VALUE){
          sorter=this::compareNeg0;
        }else if(bits==0x7ff0000000000000L){
          return this.uncheckedAddPosInf(tail);
        }else if(bits==0xfff0000000000000L){
          return this.uncheckedAddNegInf(tail);
        }else{
          return super.uncheckedAdd(tail,key,this::insertionCompare);
        }
        return super.uncheckedAdd(tail,key,sorter);
      }
      return this.uncheckedAddNaN(tail);
    }else{
      super.insertMiddle(key);
      return true;
    }
  }
  public static class Ascending extends DoubleOrderedSet{
    Ascending(int head,double[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
    @Override int comparePos0(double key){
      if(0<key){
        return -1;
      }
      if(0>key){
        return 1;
      }
      final long bits;
      if((bits=Double.doubleToRawLongBits(key))==0L){
        return 0;
      }
      if(bits==Long.MIN_VALUE){
        return 1;
      }
      //must be NaN
      return -1;
    }
    @Override int compareNeg0(double key){
      if(0<key){
        return -1;
      }
      if(0>key){
        return 1;
      }
      if(Double.doubleToRawLongBits(key)==Long.MIN_VALUE){
        return 0;
      }
      //is positive 0 or NaN
      return -1;
    }
    @Override boolean uncheckedAddPosInf(int tail){
      double[] arr;
      final double topVal;
      if((topVal=(arr=this.arr)[tail])!=Double.POSITIVE_INFINITY){
        if(topVal==topVal){
          super.insertAtTail(arr,Double.POSITIVE_INFINITY,this.head,tail);
        }else{
          int newTail,head;
          switch(Integer.signum((newTail=tail+1)-(head=this.head))){
            case 0:
              //fragmented must grow
              final double[] tmp;
              int arrLength;
              ArrCopy.uncheckedCopy(arr,0,tmp=new double[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
              ArrCopy.uncheckedCopy(arr,newTail,tmp,head-=(arrLength-=newTail),arrLength);
              this.head=head;
              this.arr=arr=tmp;
              break;
            default:
              //nonfragmented
              if(newTail==arr.length){
                if(head==0){
                  //must grow
                  ArrCopy.uncheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(newTail)],0,tail);
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
        }
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddNegInf(int tail){
      double[] arr;
      final int head;
      if((arr=this.arr)[head=this.head]!=Double.NEGATIVE_INFINITY){
        super.insertAtHead(arr,Double.NEGATIVE_INFINITY,head,tail);
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddNaN(int tail){
      double[] arr;
      if(!Double.isNaN((arr=this.arr)[tail])){
        super.insertAtTail(arr,Double.NaN,this.head,tail);
        return true;
      }
      return false;
    }
    @Override int insertionCompare(double key1,double key2){
      //key1 is guaranteed to be non-zero, non-infinity, and non-nan
      if(key1==key2){
        return 0;
      }
      if(key1>key2){
        return 1;
      }
      //ok if key2 is NaN
      return -1;
    }
  }
  public static class Descending extends DoubleOrderedSet{
    Descending(int head,double[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
    @Override boolean uncheckedAddPosInf(int tail){
      double[] arr;
      final double bottomVal;
      int head;
      if((bottomVal=(arr=this.arr)[head=this.head])!=Double.POSITIVE_INFINITY){
        if(bottomVal==bottomVal){
          super.insertAtHead(arr,Double.POSITIVE_INFINITY,head,tail);
        }else{
          //TODO add just to the right of head
          throw new omni.util.NotYetImplementedException();
        }
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddNegInf(int tail){
      double[] arr;
      if((arr=this.arr)[tail]!=Double.NEGATIVE_INFINITY){
        super.insertAtTail(arr,Double.NEGATIVE_INFINITY,this.head,tail);
        return true;
      }
      return false;
    }
    @Override int comparePos0(double key){
      if(0<key){
        return 1;
      }
      if(0>key){
        return -1;
      }
      final long bits;
      if((bits=Double.doubleToRawLongBits(key))==0L){
        return 0;
      }
      if(bits==Long.MIN_VALUE){
        return 1;
      }
      //must be NaN
      return 1;
    }
    @Override int compareNeg0(double key){
      if(0<key){
        return 1;
      }
      if(0>key){
        return -1;
      }
      if(Double.doubleToRawLongBits(key)==Long.MIN_VALUE){
        return 0;
      }
      //is positive 0 or NaN
      return 1;
    }
    @Override boolean uncheckedAddNaN(int tail){
      double[] arr;
      final int head;
      if(!Double.isNaN((arr=this.arr)[head=this.head])){
        super.insertAtHead(arr,Double.NaN,head,tail);
        return true;
      }
      return false;
    }
    @Override int insertionCompare(double key1,double key2){
      //key1 is guaranteed to be non-zero, non-infinity, and non-nan
      if(key1==key2){
        return 0;
      }
      if(key1>key2){
        return -1;
      }
      //ok if key2 is NaN
      return 1;
    }
  }
}
