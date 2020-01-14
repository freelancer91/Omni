package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
public class IntArrDeq extends IntUntetheredArrSeq implements OmniDeque.OfInt
{
  IntArrDeq(int head,int[] arr,int tail){
    super(head,arr,tail);
  }
  IntArrDeq(){
    super();
  }
  @Override public boolean add(int val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(int val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(int val){
    super.push(val);
    return true;
  }
  @Override public boolean offerLast(int val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(boolean val){
    super.addLast((int)(int)TypeUtil.castToByte(val));
    return true;
  }
  @Override public boolean add(byte val){
    super.addLast((int)(val));
    return true;
  }
  @Override public boolean add(char val){
    super.addLast((int)(val));
    return true;
  }
  @Override public boolean add(Integer val){
    super.addLast((int)val);
    return true;
  }
  @Override public void addFirst(Integer val){
    super.push((int)val);
  }
  @Override public void addLast(Integer val){
    super.addLast((int)val);
  }
  @Override public boolean offer(Integer val){
    super.addLast((int)val);
    return true;
  }
  @Override public boolean offerFirst(Integer val){
    super.push((int)val);
    return true;
  }
  @Override public boolean offerLast(Integer val){
    super.addLast((int)val);
    return true;
  }
  @Override public void push(Integer val){
    super.push((int)val);
  }
  @Override public int intElement(){
    return (int)arr[head];
  }
  @Override public int getLastInt(){
    return (int)arr[tail];
  }
  @Override public Integer element(){
    return (int)arr[head];
  }
  @Override public Integer getFirst(){
    return (int)arr[head];
  }
  @Override public Integer getLast(){
    return (int)arr[tail];
  }
  @Override public int peekInt(){
    if(this.tail!=-1){
      return (int)arr[head];
    }
    return Integer.MIN_VALUE;
  }
  @Override public int peekLastInt(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (int)arr[tail];
    }
    return Integer.MIN_VALUE;
  }
  @Override public Integer peek(){
    if(this.tail!=-1){
      return (Integer)(arr[head]);
    }
    return null;
  }
  @Override public Integer peekFirst(){
    if(this.tail!=-1){
      return (Integer)(arr[head]);
    }
    return null;
  }
  @Override public Integer peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Integer)(arr[tail]);
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
  @Override public int popInt(){
    return (int)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public int removeLastInt(){
    return (int)super.uncheckedRemoveLast(this.tail);
  }
  @Override public Integer pop(){
    return (Integer)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Integer remove(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Integer removeFirst(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Integer removeLast(){
    return super.uncheckedRemoveLast(this.tail);
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,(int)TypeUtil.castToByte(val));  
  }
  @Override public boolean contains(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);    
  }
  @Override public boolean contains(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);    
  }
  @Override public boolean contains(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,val);    
  }
  @Override public boolean contains(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedContainsMatch(tail,v); 
  }
  @Override public boolean contains(float val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (double)val==(v=(int)val)){
      return super.uncheckedContainsMatch(tail,v);
    }
    return false;
  }
  @Override public boolean contains(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedContainsMatch(tail,v); 
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(int)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(int)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(int)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
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
      if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,(int)TypeUtil.castToByte(val));  
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
      if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,val);    
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
      if((tail=this.tail)!=-1
    ){final int v;if((v=(int)val)==val){return super.uncheckedSearch(tail,v);}
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
      if((tail=this.tail)!=-1
    ){final int v;if((double)val==(v=(int)val)){return super.uncheckedSearch(tail,v);}
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
      if((tail=this.tail)!=-1
    ){final int v;if((v=(int)val)==val){return super.uncheckedSearch(tail,v);}
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
      if((tail=this.tail)!=-1
    ){
      final int queryParam;
      if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(int)l)){
          return -1;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(int)f)){
          return -1;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(int)d)){
          return -1;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
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
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,(int)TypeUtil.castToByte(val));  
  }
  @Override public boolean removeVal(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);    
  }
  @Override public boolean removeVal(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);    
  }
  @Override public boolean removeVal(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,val);    
  }
  @Override public boolean removeVal(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedRemoveFirstMatch(tail,v); 
  }
  @Override public boolean removeVal(float val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (double)val==(v=(int)val)){
      return super.uncheckedRemoveFirstMatch(tail,v);
    }
    return false;
  }
  @Override public boolean removeVal(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedRemoveFirstMatch(tail,v); 
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(int)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(int)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(int)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
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
      if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(int)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(int)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(int)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
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
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,(int)TypeUtil.castToByte(val));  
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,val);    
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedRemoveLastMatch(tail,v); 
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    final int v;
    if((tail=this.tail)!=-1 && (double)val==(v=(int)val)){
      return super.uncheckedRemoveLastMatch(tail,v);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    final int v;
    return (tail=this.tail)!=-1 && (v=(int)val)==val && super.uncheckedRemoveLastMatch(tail,v); 
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final int queryParam;
      if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(queryParam=(int)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(queryParam=(int)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(queryParam=(int)d)){
          return false;
        }
      }else if(val instanceof Character){
        queryParam=(char)val;
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.castToByte((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public OmniIterator.OfInt descendingIterator(){
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
      int[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new int[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final int[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new int[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new IntArrDeq(head,copy,tail);
    }
    return new IntArrDeq();
  }
}
