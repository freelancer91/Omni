package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
import java.util.function.DoublePredicate;
public class DoubleArrDeq extends DoubleUntetheredArrSeq implements OmniDeque.OfDouble
{
  DoubleArrDeq(int head,double[] arr,int tail){
    super(head,arr,tail);
  }
  DoubleArrDeq(){
    super();
  }
  @Override public boolean add(double val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(double val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(double val){
    super.push(val);
    return true;
  }
  @Override public boolean offerLast(double val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(boolean val){
    super.addLast((double)TypeUtil.castToDouble(val));
    return true;
  }
  @Override public boolean add(int val){
    super.addLast((double)(val));
    return true;
  }
  @Override public boolean add(long val){
    super.addLast((double)(val));
    return true;
  }
  @Override public boolean add(float val){
    super.addLast((double)(val));
    return true;
  }
  @Override public boolean add(Double val){
    super.addLast((double)val);
    return true;
  }
  @Override public void addFirst(Double val){
    super.push((double)val);
  }
  @Override public void addLast(Double val){
    super.addLast((double)val);
  }
  @Override public boolean offer(Double val){
    super.addLast((double)val);
    return true;
  }
  @Override public boolean offerFirst(Double val){
    super.push((double)val);
    return true;
  }
  @Override public boolean offerLast(Double val){
    super.addLast((double)val);
    return true;
  }
  @Override public void push(Double val){
    super.push((double)val);
  }
  @Override public double doubleElement(){
    return (double)arr[head];
  }
  @Override public double getLastDouble(){
    return (double)arr[tail];
  }
  @Override public Double element(){
    return (double)arr[head];
  }
  @Override public Double getFirst(){
    return (double)arr[head];
  }
  @Override public Double getLast(){
    return (double)arr[tail];
  }
  @Override public double peekDouble(){
    if(this.tail!=-1){
      return (double)arr[head];
    }
    return Double.NaN;
  }
  @Override public double peekLastDouble(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (double)arr[tail];
    }
    return Double.NaN;
  }
  @Override public Double peek(){
    if(this.tail!=-1){
      return (Double)(arr[head]);
    }
    return null;
  }
  @Override public Double peekFirst(){
    if(this.tail!=-1){
      return (Double)(arr[head]);
    }
    return null;
  }
  @Override public Double peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (Double)(arr[tail]);
    }
    return null;
  }
  @Override public double popDouble(){
    return (double)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public double removeLastDouble(){
    return (double)super.uncheckedRemoveLast(this.tail);
  }
  @Override public Double pop(){
    return (Double)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Double remove(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Double removeFirst(){
    return super.uncheckedRemoveFirst(this.tail);
  }
  @Override public Double removeLast(){
    return super.uncheckedRemoveLast(this.tail);
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean contains(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean contains(long val){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToDouble(val) && super.uncheckedContainsMatch(tail,TypeUtil.doubleEquals(val));   
  }
  @Override public boolean contains(float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean contains(double val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean contains(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final DoublePredicate queryParam;
      if(val instanceof Double){
        queryParam=TypeUtil.doubleEquals((double)val);
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=TypeUtil.doubleEquals(((Number)val).intValue());
      }else if(val instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToDouble(l=(long)val)){
          return false;
        }
        queryParam=TypeUtil.doubleEquals(l);
      }else if(val instanceof Float){
        queryParam=TypeUtil.doubleEquals((float)val);
      }else if(val instanceof Character){
        queryParam=TypeUtil.doubleEquals((char)val);
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.doubleEquals((boolean)val);
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
    ){return super.uncheckedSearch(tail,TypeUtil.doubleEquals(val));    
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
    if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,TypeUtil.doubleEquals(val));    
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
    if((tail=this.tail)!=-1
    &&TypeUtil.checkCastToDouble(val)){ return super.uncheckedSearch(tail,TypeUtil.doubleEquals(val));   
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
    if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,TypeUtil.doubleEquals(val));    
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
    if((tail=this.tail)!=-1
    ){return super.uncheckedSearch(tail,TypeUtil.doubleEquals(val));    
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
    if((tail=this.tail)!=-1
    ){
      final DoublePredicate queryParam;
      if(val instanceof Double){
        queryParam=TypeUtil.doubleEquals((double)val);
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=TypeUtil.doubleEquals(((Number)val).intValue());
      }else if(val instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToDouble(l=(long)val)){
          return -1;
        }
        queryParam=TypeUtil.doubleEquals(l);
      }else if(val instanceof Float){
        queryParam=TypeUtil.doubleEquals((float)val);
      }else if(val instanceof Character){
        queryParam=TypeUtil.doubleEquals((char)val);
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.doubleEquals((boolean)val);
      }else{
        return -1;
      }
      return super.uncheckedSearch(tail,queryParam); 
    }
    return -1;
  }
  @Override public boolean removeVal(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean removeVal(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean removeVal(long val){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToDouble(val) && super.uncheckedRemoveFirstMatch(tail,TypeUtil.doubleEquals(val));   
  }
  @Override public boolean removeVal(float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean removeVal(double val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean remove(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final DoublePredicate queryParam;
      if(val instanceof Double){
        queryParam=TypeUtil.doubleEquals((double)val);
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=TypeUtil.doubleEquals(((Number)val).intValue());
      }else if(val instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToDouble(l=(long)val)){
          return false;
        }
        queryParam=TypeUtil.doubleEquals(l);
      }else if(val instanceof Float){
        queryParam=TypeUtil.doubleEquals((float)val);
      }else if(val instanceof Character){
        queryParam=TypeUtil.doubleEquals((char)val);
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.doubleEquals((boolean)val);
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
      final DoublePredicate queryParam;
      if(val instanceof Double){
        queryParam=TypeUtil.doubleEquals((double)val);
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=TypeUtil.doubleEquals(((Number)val).intValue());
      }else if(val instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToDouble(l=(long)val)){
          return false;
        }
        queryParam=TypeUtil.doubleEquals(l);
      }else if(val instanceof Float){
        queryParam=TypeUtil.doubleEquals((float)val);
      }else if(val instanceof Character){
        queryParam=TypeUtil.doubleEquals((char)val);
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.doubleEquals((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveFirstMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    return (tail=this.tail)!=-1 && TypeUtil.checkCastToDouble(val) && super.uncheckedRemoveLastMatch(tail,TypeUtil.doubleEquals(val));   
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.doubleEquals(val));    
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    if((tail=this.tail)!=-1){
      final DoublePredicate queryParam;
      if(val instanceof Double){
        queryParam=TypeUtil.doubleEquals((double)val);
      }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
        queryParam=TypeUtil.doubleEquals(((Number)val).intValue());
      }else if(val instanceof Long){
        final long l;
        if(!TypeUtil.checkCastToDouble(l=(long)val)){
          return false;
        }
        queryParam=TypeUtil.doubleEquals(l);
      }else if(val instanceof Float){
        queryParam=TypeUtil.doubleEquals((float)val);
      }else if(val instanceof Character){
        queryParam=TypeUtil.doubleEquals((char)val);
      }else if(val instanceof Boolean){
        queryParam=TypeUtil.doubleEquals((boolean)val);
      }else{
        return false;
      }
      return super.uncheckedRemoveLastMatch(tail,queryParam);
    }
    return false;     
  }
  @Override public OmniIterator.OfDouble descendingIterator(){
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
      double[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new double[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final double[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new double[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new DoubleArrDeq(head,copy,tail);
    }
    return new DoubleArrDeq();
  }
}
