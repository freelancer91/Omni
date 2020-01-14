package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
public class CharArrDeq extends CharUntetheredArrSeq implements OmniDeque.OfChar
{
  CharArrDeq(int head,char[] arr,int tail){
    super(head,arr,tail);
  }
  CharArrDeq(){
    super();
  }
  @Override public boolean add(char val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(char val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(char val){
    super.push(val);
    return true;
  }
  @Override public boolean offerLast(char val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(boolean val){
    super.addLast((char)TypeUtil.castToChar(val));
    return true;
  }
  @Override public boolean add(Character val){
    super.addLast((char)val);
    return true;
  }
  @Override public void addFirst(Character val){
    super.push((char)val);
  }
  @Override public void addLast(Character val){
    super.addLast((char)val);
  }
  @Override public boolean offer(Character val){
    super.addLast((char)val);
    return true;
  }
  @Override public boolean offerFirst(Character val){
    super.push((char)val);
    return true;
  }
  @Override public boolean offerLast(Character val){
    super.addLast((char)val);
    return true;
  }
  @Override public void push(Character val){
    super.push((char)val);
  }
  @Override public char charElement(){
    return (char)arr[head];
  }
  @Override public char getLastChar(){
    return (char)arr[tail];
  }
  @Override public Character element(){
    return (char)arr[head];
  }
  @Override public Character getFirst(){
    return (char)arr[head];
  }
  @Override public Character getLast(){
    return (char)arr[tail];
  }
  @Override public char peekChar(){
    if(this.tail!=-1){
      return (char)arr[head];
    }
    return Character.MIN_VALUE;
  }
  @Override public char peekLastChar(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (char)arr[tail];
    }
    return Character.MIN_VALUE;
  }
  @Override public Character peek(){
    if(this.tail!=-1){
      return (Character)(arr[head]);
    }
    return null;
  }
  @Override public Character peekFirst(){
    if(this.tail!=-1){
      return (Character)(arr[head]);
    }
    return null;
  }
  @Override public Character peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Character)(arr[tail]);
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
  @Override public char popChar(){
    return (char)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public char removeLastChar(){
    return (char)super.uncheckedRemoveLast(this.tail);
  }
  @Override public Character pop(){
    return (Character)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Character remove(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Character removeFirst(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Character removeLast(){
    return super.uncheckedRemoveLast(this.tail);
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.castToChar(val));
  }
  @Override public boolean contains(byte val){
    final int tail;
    return val>=0 && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);
  }
  @Override public boolean contains(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);
  }
  @Override public boolean contains(short val){
    final int tail;
    return val>=0 && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);
  }
  @Override public boolean contains(int val){
    final int tail;
    return val==(char)val && (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val); 
  }
  @Override public boolean contains(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedContainsMatch(tail,v);
  }
  @Override public boolean contains(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedContainsMatch(tail,v);
  }
  @Override public boolean contains(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedContainsMatch(tail,v);
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Character){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(char)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(char)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(char)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(char)d)){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToChar((boolean)val);
      }else{
        return false;
      } 
      return super.uncheckedContainsMatch(tail,queryParam);
    }
    return false;  
  }
  @Override public int search(boolean val){
    final int tail;
    if((tail=this.tail)!=-1){ return super.uncheckedSearch(tail,TypeUtil.castToChar(val));
    }
    return -1;
  }
  @Override public int search(char val){
    final int tail;
    if((tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(short val){
    final int tail;
    if(val>=0 && (tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
    if(val==(char)val && (tail=this.tail)!=-1){ return super.uncheckedSearch(tail,val);  
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(char)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(char)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (v=(char)val)==val){ return super.uncheckedSearch(tail,v);
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Character){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(char)queryParam){
          return -1;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(char)l)){
          return -1;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(char)f)){
          return -1;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(char)d)){
          return -1;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToChar((boolean)val);
      }else{
        return -1;
      } 
      return super.uncheckedSearch(tail,queryParam);
    }
    return -1;
  }
  @Override public boolean removeVal(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.castToChar(val));
  }
  @Override public boolean removeVal(byte val){
    final int tail;
    return val>=0 && (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);
  }
  @Override public boolean removeVal(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);
  }
  @Override public boolean removeVal(short val){
    final int tail;
    return val>=0 && (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);
  }
  @Override public boolean removeVal(int val){
    final int tail;
    return val==(char)val && (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val); 
  }
  @Override public boolean removeVal(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveFirstMatch(tail,v);
  }
  @Override public boolean removeVal(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveFirstMatch(tail,v);
  }
  @Override public boolean removeVal(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveFirstMatch(tail,v);
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Character){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(char)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(char)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(char)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(char)d)){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToChar((boolean)val);
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
      if(val instanceof Character){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(char)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(char)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(char)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(char)d)){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToChar((boolean)val);
      }else{
        return false;
      } 
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;  
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.castToChar(val));
  }
  @Override public boolean removeLastOccurrence(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(short val){
    final int tail;
    return val>=0 && (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    return val==(char)val && (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val); 
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(char)val)==val && super.uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Character){
        queryParam=(byte)val;
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        if((queryParam=((Number)val).intValue())!=(char)queryParam){
          return false;
        }
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(char)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(char)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(char)d)){
          return false;
        }
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToChar((boolean)val);
      }else{
        return false;
      } 
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;  
  }
  @Override public OmniIterator.OfChar descendingIterator(){
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
      char[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new char[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final char[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new char[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new CharArrDeq(head,copy,tail);
    }
    return new CharArrDeq();
  }
}
