package omni.impl;
import omni.api.OmniNavigableSet;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
import omni.function.FloatToIntFunction;
public abstract class FloatOrderedSet
  extends FloatUntetheredArrSeq implements OmniNavigableSet.OfFloat
{
  FloatOrderedSet(int head,float[] arr,int tail){
    super(head,arr,tail);
  }
  FloatOrderedSet(){
    super();
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
  abstract boolean uncheckedAddNaN(int tail);
  abstract boolean uncheckedAddPosInf(int tail);
  abstract boolean uncheckedAddNegInf(int tail);
  public static class Ascending
    extends FloatOrderedSet implements Cloneable
  {
    public Ascending(){
      super();
    }
    public Ascending(int head,float[] arr,int tail){
      super(head,arr,tail);
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
  }
  public static class Descending
    extends FloatOrderedSet implements Cloneable
  {
    public Descending(){
      super();
    }
    public Descending(int head,float[] arr,int tail){
      super(head,arr,tail);
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
  }
/*
  abstract int insertionCompare(float key1,float key2);
  abstract FloatToIntFunction getQueryComparator(float key);
  @Override public boolean contains(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,key?this::comparePos1:this::comparePos0);
  }
  @Override public boolean contains(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,key==0?this::comparePos0:getQueryComparator(key));
  }
  @Override public boolean contains(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,key==0?this::comparePos0:getQueryComparator(key));
  }
  @Override public boolean contains(int key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key) && super.uncheckedContainsMatch(tail,key==0?this::comparePos0:getQueryComparator(key));
  }
  @Override public boolean contains(long key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key) && super.uncheckedContainsMatch(tail,key==0?this::comparePos0:getQueryComparator(key));
  }
  @Override public boolean removeVal(boolean key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key?this::comparePos1:this::comparePos0);
  }
  @Override public boolean removeVal(char key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key==0?this::comparePos0:getQueryComparator(key));
  }
  @Override public boolean removeVal(short key){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveMatch(tail,key==0?this::comparePos0:getQueryComparator(key));
  }
  @Override public boolean removeVal(int key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key) && super.uncheckedRemoveMatch(tail,key==0?this::comparePos0:getQueryComparator(key));
  }
  @Override public boolean removeVal(long key){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToFloat(key) && super.uncheckedRemoveMatch(tail,key==0?this::comparePos0:getQueryComparator(key));
  }
  abstract boolean uncheckedContainsMatch(int tail,double key);
  abstract boolean uncheckedRemoveMatch(int tail,double key);
  abstract boolean uncheckedContainsMatch(int tail,float key);
  abstract boolean uncheckedRemoveMatch(int tail,float key);
  @Override public boolean contains(float key){
    final int tail;
    return (tail=this.tail)!=-1 && this.uncheckedContainsMatch(tail,key);
  }
  @Override public boolean contains(double key){
    final int tail;
    return (tail=this.tail)!=-1 && this.uncheckedContainsMatch(tail,key);
  }
  @Override public boolean removeVal(float key){
    final int tail;
    return (tail=this.tail)!=-1 && this.uncheckedRemoveMatch(tail,key);
  }
  @Override public boolean removeVal(double key){
    final int tail;
    return (tail=this.tail)!=-1 && this.uncheckedRemoveMatch(tail,key);
  }
    @Override public boolean contains(Object key){
      final int tail;
      if((tail=this.tail)!=-1){
        final FloatToIntFunction comparator;
        if(key instanceof Float){
          return this.uncheckedContainsMatch(tail,(float)key);
        }else if(key instanceof Integer){
          final int i;
          if(!TypeUtil.checkCastToFloat(i=(int)key)){
            return false;
          }
          comparator = i==0?this::comparePos0:this.getQueryComparator(i);
        }else if(key instanceof Long){
          final long l;
          if(!TypeUtil.checkCastToFloat(l=(long)key)){
            return false;
          }
          comparator = l==0?this::comparePos0:this.getQueryComparator(l);
        }else if(key instanceof Double){
          return this.uncheckedContainsMatch(tail,(double)key);
        }else if(key instanceof Byte || key instanceof Short){
          final int i;
          comparator = (i=((Number)key).intValue())==0?this::comparePos0:this.getQueryComparator(i);
        }else if(key instanceof Character){
          final int i;
          comparator = (i=(char)key)==0?this::comparePos0:this.getQueryComparator(i);
        }else if(key instanceof Boolean){
          comparator = (boolean)key ? this::comparePos1: this::comparePos0;
        }else{
          return false;
        }
        return super.uncheckedContainsMatch(tail,comparator);
      }
      return false;
    }
    @Override public boolean remove(Object key){
      final int tail;
      if((tail=this.tail)!=-1){
        final FloatToIntFunction comparator;
        if(key instanceof Float){
          return this.uncheckedRemoveMatch(tail,(float)key);
        }else if(key instanceof Integer){
          final int i;
          if(!TypeUtil.checkCastToFloat(i=(int)key)){
            return false;
          }
          comparator = i==0?this::comparePos0:this.getQueryComparator(i);
        }else if(key instanceof Long){
          final long l;
          if(!TypeUtil.checkCastToFloat(l=(long)key)){
            return false;
          }
          comparator = l==0?this::comparePos0:this.getQueryComparator(l);
        }else if(key instanceof Double){
          return this.uncheckedRemoveMatch(tail,(double)key);
        }else if(key instanceof Byte || key instanceof Short){
          final int i;
          comparator = (i=((Number)key).intValue())==0?this::comparePos0:this.getQueryComparator(i);
        }else if(key instanceof Character){
          final int i;
          comparator = (i=(char)key)==0?this::comparePos0:this.getQueryComparator(i);
        }else if(key instanceof Boolean){
          comparator = (boolean)key ? this::comparePos1: this::comparePos0;
        }else{
          return false;
        }
        return super.uncheckedRemoveMatch(tail,comparator);
      }
      return false;
    }
  public static class Ascending extends FloatOrderedSet{
    Ascending(int head,float[] arr,int tail){
      super(head,arr,tail);
    }
    Ascending(){
      super();
    }
    @Override boolean uncheckedContainsMatch(int tail,double key){
      final float f;
      if((f=(float)key)==key){
        final FloatToIntFunction comparator;
        switch(Float.floatToRawIntBits(f)){
          case 0xff800000: //neg inf
            return this.arr[this.head]==Float.NEGATIVE_INFINITY;
          default:
            comparator=this.getQueryComparator(f);
            break;
          case 0:
            comparator=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            comparator=this::compareNeg0;
        }
        return super.uncheckedContainsMatch(tail,comparator);
      }
      return f!=f && Float.isNaN(this.arr[tail]);
    }
    @Override boolean uncheckedRemoveMatch(int tail,double key){
      final float f;
      if((f=(float)key)==key){
        FloatToIntFunction comparator;
        switch(Float.floatToRawIntBits(f)){
          case 0xff800000: //neg inf
            int head;
            final float[] arr;
            if(((arr=this.arr)[head=this.head])==Float.NEGATIVE_INFINITY){
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
          default:
            comparator=this.getQueryComparator(f);
            break;
          case 0:
            comparator=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            comparator=this::compareNeg0;
        }
        return super.uncheckedRemoveMatch(tail,comparator);
      }else if(f!=f)
      {
        final float[] arr;
        if(Float.isNaN((arr=this.arr)[tail])){
          if(tail==this.head){
            tail=-1;
          }else{
            if(--tail==-1){
              tail=arr.length-1;
            }
          }
          this.tail=tail;
          return true;
        }
      }
      return false;
    }
    @Override boolean uncheckedContainsMatch(int tail,float key){
      if(key==key){
        FloatToIntFunction comparator;
        switch(Float.floatToRawIntBits(key)){
          case 0xff800000: //neg inf
            return this.arr[head]==Float.NEGATIVE_INFINITY;
          default:
            comparator=this.getQueryComparator(key);
            break;
          case 0:
            comparator=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            comparator=this::compareNeg0;
        }
        return super.uncheckedContainsMatch(tail,comparator);
      }
      return Float.isNaN(this.arr[tail]);
    }
    @Override boolean uncheckedRemoveMatch(int tail,float key){
      if(key==key){
        FloatToIntFunction comparator;
        switch(Float.floatToRawIntBits(key)){
          case 0xff800000: //neg inf
            int head;
            final float[] arr;
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
          default:
            comparator=this.getQueryComparator(key);
            break;
          case 0:
            comparator=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            comparator=this::compareNeg0;
        }
        return super.uncheckedRemoveMatch(tail,comparator);
      }
      final float[] arr;
      if(Float.isNaN((arr=this.arr)[tail])){
        if(tail==this.head){
          tail=-1;
        }else{
          if(--tail==-1){
            tail=arr.length-1;
          }
        }
        this.tail=tail;
        return true;
      }
      return false;
    }
    @Override public FloatComparator comparator(){
      return Float::compare;
    }
    @Override FloatToIntFunction getQueryComparator(float key){
      return k->{
        if(k==key){
          return 0;
        }
        if(key<k){
          return 1;
        }
        return -1;
      };
    }
  }
  public static class Descending extends FloatOrderedSet{
    Descending(int head,float[] arr,int tail){
      super(head,arr,tail);
    }
    Descending(){
      super();
    }
    @Override boolean uncheckedContainsMatch(int tail,double key){
        final float f;
        if((f=(float)key)==key){
          final FloatToIntFunction comparator;
          switch(Float.floatToRawIntBits(f)){
            case 0xff800000: //neg inf
              return this.arr[tail]==Float.NEGATIVE_INFINITY;
            default:
              comparator=this.getQueryComparator(f);
              break;
            case 0:
              comparator=this::comparePos0;
              break;
            case Integer.MIN_VALUE:
              comparator=this::compareNeg0;
          }
          return super.uncheckedContainsMatch(tail,comparator);
        }
        return f!=f && Float.isNaN(this.arr[head]);
    }
    @Override boolean uncheckedRemoveMatch(int tail,double key){
      final float f;
      if((f=(float)key)==key){
        FloatToIntFunction comparator;
        switch(Float.floatToRawIntBits(f)){
          case 0xff800000: //neg inf
            final float[] arr;
            if((arr=this.arr)[tail]==Float.NEGATIVE_INFINITY){
              if(tail==this.head){
                tail=-1;
              }else{
                if(--tail==-1){
                  tail=arr.length-1;
                }
              }
              this.tail=tail;
              return true;
            }
            return false;
          default:
            comparator=this.getQueryComparator(f);
            break;
          case 0:
            comparator=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            comparator=this::compareNeg0;
        }
        return super.uncheckedRemoveMatch(tail,comparator);
      }else if(f!=f)
      {
        int head;
        final float[] arr;
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
      }
      return false;
    }
    @Override boolean uncheckedContainsMatch(int tail,float key){
      if(key==key){
        FloatToIntFunction comparator;
        switch(Float.floatToRawIntBits(key)){
          case 0xff800000: //neg inf
            return this.arr[tail]==Float.NEGATIVE_INFINITY;
          default:
            comparator=this.getQueryComparator(key);
            break;
          case 0:
            comparator=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            comparator=this::compareNeg0;
        }
        return super.uncheckedContainsMatch(tail,comparator);
      }
      return Float.isNaN(this.arr[head]);
    }
    @Override boolean uncheckedRemoveMatch(int tail,float key){
      if(key==key){
        FloatToIntFunction comparator;
        switch(Float.floatToRawIntBits(key)){
          case 0xff800000: //neg inf
            final float[] arr;
            if((arr=this.arr)[tail]==Float.NEGATIVE_INFINITY){
              if(tail==this.head){
                tail=-1;
              }else{
                if(--tail==-1){
                  tail=arr.length-1;
                }
              }
              this.tail=tail;
              return true;
            }
            return false;
          default:
            comparator=this.getQueryComparator(key);
            break;
          case 0:
            comparator=this::comparePos0;
            break;
          case Integer.MIN_VALUE:
            comparator=this::compareNeg0;
        }
        return super.uncheckedRemoveMatch(tail,comparator);
      }
      int head;
      final float[] arr;
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
    @Override public FloatComparator comparator(){
      return FloatComparator::descendingCompare;
    }
    @Override FloatToIntFunction getQueryComparator(float key){
      return k->{
        if(k==key){
          return 0;
        }
        if(k<key){
          return 1;
        }
        return -1;
      };
    }
  }
  */
}
