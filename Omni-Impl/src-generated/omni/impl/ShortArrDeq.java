package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
public class ShortArrDeq extends ShortUntetheredArrSeq implements OmniDeque.OfShort
{
  ShortArrDeq(int head,short[] arr,int tail){
    super(head,arr,tail);
  }
  ShortArrDeq(){
    super();
  }
  @Override public boolean add(short val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(short val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(short val){
    super.push(val);
    return true;
  }
  @Override public boolean offerLast(short val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(boolean val){
    super.addLast((short)(short)TypeUtil.castToByte(val));
    return true;
  }
  @Override public boolean add(byte val){
    super.addLast((short)(val));
    return true;
  }
  @Override public boolean add(Short val){
    super.addLast((short)val);
    return true;
  }
  @Override public void addFirst(Short val){
    super.push((short)val);
  }
  @Override public void addLast(Short val){
    super.addLast((short)val);
  }
  @Override public boolean offer(Short val){
    super.addLast((short)val);
    return true;
  }
  @Override public boolean offerFirst(Short val){
    super.push((short)val);
    return true;
  }
  @Override public boolean offerLast(Short val){
    super.addLast((short)val);
    return true;
  }
  @Override public void push(Short val){
    super.push((short)val);
  }
  @Override public short shortElement(){
    return (short)arr[head];
  }
  @Override public short getLastShort(){
    return (short)arr[tail];
  }
  @Override public Short element(){
    return (short)arr[head];
  }
  @Override public Short getFirst(){
    return (short)arr[head];
  }
  @Override public Short getLast(){
    return (short)arr[tail];
  }
  @Override public short peekShort(){
    if(this.tail!=-1){
      return (short)arr[head];
    }
    return Short.MIN_VALUE;
  }
  @Override public short peekLastShort(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (short)arr[tail];
    }
    return Short.MIN_VALUE;
  }
  @Override public Short peek(){
    if(this.tail!=-1){
      return (Short)(arr[head]);
    }
    return null;
  }
  @Override public Short peekFirst(){
    if(this.tail!=-1){
      return (Short)(arr[head]);
    }
    return null;
  }
  @Override public Short peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Short)(arr[tail]);
    }
    return null;
  }
  @Override public double peekDouble(){
    if(this.tail!=-1){
      return (double)(arr[head]);
    }
    return Double.NaN;
  }
  @Override public double peekLastDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (double)(arr[tail]);
    }
    return Double.NaN;
  }
  @Override public float peekFloat(){
    if(this.tail!=-1){
      return (float)(arr[head]);
    }
    return Float.NaN;
  }
  @Override public float peekLastFloat(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (float)(arr[tail]);
    }
    return Float.NaN;
  }
  @Override public long peekLong(){
    if(this.tail!=-1){
      return (long)(arr[head]);
    }
    return Long.MIN_VALUE;
  }
  @Override public long peekLastLong(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (long)(arr[tail]);
    }
    return Long.MIN_VALUE;
  }
  @Override public int peekInt(){
    if(this.tail!=-1){
      return (int)(arr[head]);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int peekLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)(arr[tail]);
    }
    return Integer.MIN_VALUE;
  }
  @Override public short popShort(){
    return (short)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public short removeLastShort(){
    return (short)super.uncheckedRemoveLast(this.tail);
  }
  @Override public Short pop(){
    return (Short)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Short remove(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Short removeFirst(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Short removeLast(){
    return super.uncheckedRemoveLast(this.tail);
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,(short)TypeUtil.castToByte(val));
  }
  @Override public boolean contains(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);  
  }
  @Override public boolean contains(char val){
    final int tail;
    return val<=Short.MAX_VALUE && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);  
  }
  @Override public boolean contains(short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);  
  }
  @Override public boolean contains(int val){
    final int tail;
    return val==(short)val && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);  
  }
  @Override public boolean contains(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(short)val)==val && super.uncheckedContainsMatch(tail,v); 
  }
  @Override public boolean contains(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(short)val)==val && super.uncheckedContainsMatch(tail,v); 
  }
  @Override public boolean contains(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(short)val)==val && super.uncheckedContainsMatch(tail,v); 
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Short || val instanceof Byte){
        queryParam=((Number)val).shortValue();
      }else if(val instanceof Integer){
        if((queryParam=(int)val)!=(short)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final double d;
        if((d=(double)val)!=(queryParam=(short)d)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(short)f)){
          return false;
        }
      }else if(val instanceof Double){
        final long l;
        if((l=(long)val)!=(queryParam=(short)l)){
          return false;
        }
      }else if(val instanceof Character){
        if((queryParam=(char)val)>Short.MAX_VALUE){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;   
  }
  @Override public int search(boolean val){
    final int tail;
    if((tail=this.tail)!=-1){ return super.uncheckedSearch(tail,(short)TypeUtil.castToByte(val));
    }
    return -1;
  }
  @Override public int search(char val){
    final int tail;
    if(val<=Short.MAX_VALUE && (tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(short val){
    final int tail;
    if((tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
    if(val==(short)val && (tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(short)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(short)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(short)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Short || val instanceof Byte){
        queryParam=((Number)val).shortValue();
      }else if(val instanceof Integer){
        if((queryParam=(int)val)!=(short)queryParam){
          return -1;
        }
      }else if(val instanceof Long){
        final double d;
        if((d=(double)val)!=(queryParam=(short)d)){
          return -1;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(short)f)){
          return -1;
        }
      }else if(val instanceof Double){
        final long l;
        if((l=(long)val)!=(queryParam=(short)l)){
          return -1;
        }
      }else if(val instanceof Character){
        if((queryParam=(char)val)>Short.MAX_VALUE){
          return -1;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
      }else{
        return -1;
      }
      return super.uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public boolean removeVal(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,(short)TypeUtil.castToByte(val));
  }
  @Override public boolean removeVal(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);  
  }
  @Override public boolean removeVal(char val){
    final int tail;
    return val<=Short.MAX_VALUE && (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);  
  }
  @Override public boolean removeVal(short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);  
  }
  @Override public boolean removeVal(int val){
    final int tail;
    return val==(short)val && (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);  
  }
  @Override public boolean removeVal(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(short)val)==val && super.uncheckedRemoveFirstMatch(tail,v); 
  }
  @Override public boolean removeVal(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(short)val)==val && super.uncheckedRemoveFirstMatch(tail,v); 
  }
  @Override public boolean removeVal(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(short)val)==val && super.uncheckedRemoveFirstMatch(tail,v); 
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Short || val instanceof Byte){
        queryParam=((Number)val).shortValue();
      }else if(val instanceof Integer){
        if((queryParam=(int)val)!=(short)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final double d;
        if((d=(double)val)!=(queryParam=(short)d)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(short)f)){
          return false;
        }
      }else if(val instanceof Double){
        final long l;
        if((l=(long)val)!=(queryParam=(short)l)){
          return false;
        }
      }else if(val instanceof Character){
        if((queryParam=(char)val)>Short.MAX_VALUE){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;   
  }
  @Override public boolean removeFirstOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Short || val instanceof Byte){
        queryParam=((Number)val).shortValue();
      }else if(val instanceof Integer){
        if((queryParam=(int)val)!=(short)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final double d;
        if((d=(double)val)!=(queryParam=(short)d)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(short)f)){
          return false;
        }
      }else if(val instanceof Double){
        final long l;
        if((l=(long)val)!=(queryParam=(short)l)){
          return false;
        }
      }else if(val instanceof Character){
        if((queryParam=(char)val)>Short.MAX_VALUE){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;   
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,(short)TypeUtil.castToByte(val));
  }
  @Override public boolean removeLastOccurrence(char val){
    final int tail;
    return val<=Short.MAX_VALUE && (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);  
  }
  @Override public boolean removeLastOccurrence(short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);  
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    return val==(short)val && (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);  
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(short)val)==val && super.uncheckedRemoveLastMatch(tail,v); 
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(short)val)==val && super.uncheckedRemoveLastMatch(tail,v); 
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(short)val)==val && super.uncheckedRemoveLastMatch(tail,v); 
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Short || val instanceof Byte){
        queryParam=((Number)val).shortValue();
      }else if(val instanceof Integer){
        if((queryParam=(int)val)!=(short)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final double d;
        if((d=(double)val)!=(queryParam=(short)d)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(short)f)){
          return false;
        }
      }else if(val instanceof Double){
        final long l;
        if((l=(long)val)!=(queryParam=(short)l)){
          return false;
        }
      }else if(val instanceof Character){
        if((queryParam=(char)val)>Short.MAX_VALUE){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;   
  }
  @Override public OmniIterator.OfShort descendingIterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-this.head)<=0){
        size+=arr.length;
      }
      return new DescendingUntetheredArrSeqItr(this,tail,size);
    }
    return new DescendingUntetheredArrSeqItr(this,-1,0);
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      short[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new short[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final short[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new short[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new ShortArrDeq(head,copy,tail);
    }
    return new ShortArrDeq();
  }
}
