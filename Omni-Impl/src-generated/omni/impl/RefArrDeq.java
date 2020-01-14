package omni.impl;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.util.TypeUtil;
public class RefArrDeq<E> extends RefUntetheredArrSeq<E> implements OmniDeque.OfRef<E>
{
  RefArrDeq(int head,Object[] arr,int tail){
    super(head,arr,tail);
  }
  RefArrDeq(){
    super();
  }
  @Override public boolean add(E val){
    super.addLast(val);
    return true;
  }
  @Override public void addFirst(E val){
    super.push(val);
  }
  @Override public boolean offer(E val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(E val){
    super.push(val);
    return true;
  }
  @Override public boolean offerLast(E val){
    super.addLast(val);
    return true;
  }
  @SuppressWarnings("unchecked")
  @Override public E element(){
    return (E)arr[head];
  }
  @SuppressWarnings("unchecked")
  @Override public E getLast(){
    return (E)arr[tail];
  }
  @SuppressWarnings("unchecked")
  @Override public E getFirst(){
    return (E)arr[head];
  }
  @SuppressWarnings("unchecked")
  @Override public E peek(){
    if(this.tail!=-1){
      return (E)arr[head];
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  @Override public E peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (E)arr[tail];
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  @Override public E peekFirst(){
    if(this.tail!=-1){
      return (E)arr[head];
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  @Override public E pop(){
    return (E)super.uncheckedRemoveFirst(this.tail);
  }
  @SuppressWarnings("unchecked")
  @Override public E removeLast(){
    return (E)super.uncheckedRemoveLast(this.tail);
  }
  @SuppressWarnings("unchecked")
  @Override public E remove(){
    return (E)super.uncheckedRemoveFirst(this.tail);
  }
  @SuppressWarnings("unchecked")
  @Override public E removeFirst(){
    return (E)super.uncheckedRemoveFirst(this.tail);
  }
  @Override public boolean contains(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(long val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(double val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(Object val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(Boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(Byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(Character val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(Short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(Integer val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(Long val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(Float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean contains(Double val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedContainsMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public int search(boolean val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(byte val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(char val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(short val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(int val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(long val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(float val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(double val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(Object val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(Boolean val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(Byte val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(Character val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(Short val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(Integer val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(Long val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(Float val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public int search(Double val){
    final int tail;
    if((tail=this.tail)!=-1){return super.uncheckedSearch(tail,TypeUtil.refEquals(val));
    }
    return -1;
  }
  @Override public boolean removeVal(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(long val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(double val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean remove(Object val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(Boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(Byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(Character val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(Short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(Integer val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(Long val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(Float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeVal(Double val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeFirstOccurrence(Object val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveFirstMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(char val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(int val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(long val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(double val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(Object val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(Boolean val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(Byte val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(Character val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(Short val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(Integer val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(Long val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(Float val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public boolean removeLastOccurrence(Double val){
    final int tail;
    return (tail=this.tail)!=-1 && super.uncheckedRemoveLastMatch(tail,TypeUtil.refEquals(val));
  }
  @Override public OmniIterator.OfRef<E> descendingIterator(){
    int tail;
    if((tail=this.tail)!=-1){
      int size;
      if((size=(tail+1)-this.head)<=0){
        size+=arr.length;
      }
      return new DescendingUntetheredArrSeqItr<E>(this,tail,size);
    }
    return new DescendingUntetheredArrSeqItr<E>(this,-1,0);
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      Object[] copy;
      int size,head;
      if((size=(++tail)-(head=this.head))>0){
        ArrCopy.uncheckedCopy(this.arr,head,copy=new Object[size],0,size);
        head=0;
        tail=size-1;
      }else{
        final Object[] arr;
        ArrCopy.uncheckedCopy(arr=this.arr,0,copy=new Object[size+=arr.length],0,tail);
        ArrCopy.uncheckedCopy(arr,head,copy,tail,size-tail);
        head=tail--;
      }
      return new RefArrDeq<E>(head,copy,tail);
    }
    return new RefArrDeq<E>();
  }
}
