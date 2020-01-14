package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
import omni.function.FloatToIntFunction;
import omni.function.FloatComparator;
public abstract class FloatOrderedSet
  extends FloatUntetheredArrSeq<Float>
  implements OmniNavigableSet.OfFloat
{
  FloatOrderedSet(int head,float[] arr,int tail){
    super(head,arr,tail);
  }
  FloatOrderedSet(){
    super();
  }
  @Override public boolean add(Float key){
    return add((float)key);
  }
  abstract int insertionCompare(float key1,float key2);
  abstract boolean uncheckedAddNaN(int tail);
  abstract int comparePos0(float key);
  abstract int compareNeg0(float key);
  abstract boolean uncheckedAddPosInf(int tail);
  abstract boolean uncheckedAddNegInf(int tail);
  private void insertAtTail(float[] arr,float key,int head,int tail){
    switch(Integer.signum((++tail)-head)){
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
    arr[tail]=key;
    this.tail=tail;
  }
  private void insertAtHead(float[] arr,float key,int head,int tail){
      int newHead;
      switch(Integer.signum(tail-(newHead=head-1))){
        case 0:
          //fragmented must grow
          final float[] tmp;
          int arrLength;
          ArrCopy.uncheckedCopy(arr,0,tmp=new float[tail=OmniArray.growBy50Pct(arrLength=arr.length)],0,head);
          ArrCopy.uncheckedCopy(arr,head,tmp,newHead=tail-(arrLength-=head),arrLength);
          --newHead;
          this.arr=arr=tmp;
          break;
        default:
          //nonfragmented
          if(newHead==-1 && tail==(newHead=arr.length-1)){
            //must grow
            this.tail=(newHead=OmniArray.growBy50Pct(++tail))-1;
            ArrCopy.uncheckedCopy(arr,0,arr=new float[newHead],newHead-=(tail),tail);
            --newHead;
            this.arr=arr;
          }
        case -1:
          //fragmented
      }
      arr[newHead]=key;
      this.head=newHead;
    }
  @Override public boolean add(float key){
    int tail;
    if((tail=this.tail)!=-1){
      if(key==key){
        final FloatToIntFunction sorter;
        switch(Float.floatToRawIntBits(key)){
          case 0x7f800000:
            return this.uncheckedAddPosInf(tail);
          case 0xff800000:
            return this.uncheckedAddNegInf(tail);
          default:
            return super.uncheckedAdd(tail,key,this::insertionCompare);
          case 0:
            sorter=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            sorter=this::compareNeg0;
            break;
        }
        return super.uncheckedAdd(tail,key,sorter);
      }
      return this.uncheckedAddNaN(tail);
    }else{
      super.insertMiddle(key);
      return true;
    }
  }
  public static class Ascending extends FloatOrderedSet{
    Ascending(int head,float[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
    @Override int comparePos0(float key){
      if(0<key){
        return -1;
      }
      if(0>key){
        return 1;
      }
      switch(Float.floatToRawIntBits(key)){
        case 0:
          return 0;
        case Integer.MIN_VALUE:
          return 1;
        default:
      }
      //must be NaN
      return -1;
    }
    @Override int compareNeg0(float key){
      if(0<key){
        return -1;
      }
      if(0>key){
        return 1;
      }
      if(Float.floatToRawIntBits(key)==Integer.MIN_VALUE){
        return 0;
      }
      //is positive 0 or NaN
      return -1;
    }
    @Override boolean uncheckedAddPosInf(int tail){
      float[] arr;
      final float topVal;
      if((topVal=(arr=this.arr)[tail])!=Float.POSITIVE_INFINITY){
        if(topVal==topVal){
          super.insertAtTail(arr,Float.POSITIVE_INFINITY,this.head,tail);
        }else{
          int newTail,head;
          switch(Integer.signum((newTail=tail+1)-(head=this.head))){
            case 0:
              //fragmented must grow
              final float[] tmp;
              int arrLength;
              ArrCopy.uncheckedCopy(arr,0,tmp=new float[head=OmniArray.growBy50Pct(arrLength=arr.length)],0,tail);
              ArrCopy.uncheckedCopy(arr,newTail,tmp,head-=(arrLength-=newTail),arrLength);
              this.head=head;
              this.arr=arr=tmp;
              break;
            default:
              //nonfragmented
              if(newTail==arr.length){
                if(head==0){
                  //must grow
                  ArrCopy.uncheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(newTail)],0,tail);
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
        }
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddNegInf(int tail){
      float[] arr;
      final int head;
      if((arr=this.arr)[head=this.head]!=Float.NEGATIVE_INFINITY){
        super.insertAtHead(arr,Float.NEGATIVE_INFINITY,head,tail);
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddNaN(int tail){
      float[] arr;
      if(!Float.isNaN((arr=this.arr)[tail])){
        super.insertAtTail(arr,Float.NaN,this.head,tail);
        return true;
      }
      return false;
    }
    @Override int insertionCompare(float key1,float key2){
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
  public static class Descending extends FloatOrderedSet{
    Descending(int head,float[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
    @Override boolean uncheckedAddPosInf(int tail){
      float[] arr;
      final float bottomVal;
      int head;
      if((bottomVal=(arr=this.arr)[head=this.head])!=Float.POSITIVE_INFINITY){
        if(bottomVal==bottomVal){
          super.insertAtHead(arr,Float.POSITIVE_INFINITY,head,tail);
        }else{
          //TODO add just to the right of head
          throw new omni.util.NotYetImplementedException();
        }
        return true;
      }
      return false;
    }
    @Override boolean uncheckedAddNegInf(int tail){
      float[] arr;
      if((arr=this.arr)[tail]!=Float.NEGATIVE_INFINITY){
        super.insertAtTail(arr,Float.NEGATIVE_INFINITY,this.head,tail);
        return true;
      }
      return false;
    }
    @Override int comparePos0(float key){
      if(0<key){
        return 1;
      }
      if(0>key){
        return -1;
      }
      switch(Float.floatToRawIntBits(key)){
        case 0:
          return 0;
        case Integer.MIN_VALUE:
          return -1;
        default:
      }
      //must be NaN
      return 1;
    }
    @Override int compareNeg0(float key){
      if(0<key){
        return 1;
      }
      if(0>key){
        return -1;
      }
      if(Float.floatToRawIntBits(key)==Integer.MIN_VALUE){
        return 0;
      }
      //is positive 0 or NaN
      return 1;
    }
    @Override boolean uncheckedAddNaN(int tail){
      float[] arr;
      final int head;
      if(!Float.isNaN((arr=this.arr)[head=this.head])){
        super.insertAtHead(arr,Float.NaN,head,tail);
        return true;
      }
      return false;
    }
    @Override int insertionCompare(float key1,float key2){
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
