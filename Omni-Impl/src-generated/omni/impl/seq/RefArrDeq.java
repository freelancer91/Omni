package omni.impl.seq;
import omni.util.OmniArray;
import omni.api.OmniDeque;
import omni.util.ArrCopy;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import java.util.function.IntFunction;
import java.util.ConcurrentModificationException;
import omni.util.OmniPred;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
public class RefArrDeq<E> implements OmniDeque.OfRef<E>{
  transient Object[] arr;
  transient int head;
  transient int tail;
  public RefArrDeq(){
    super();
    this.arr=OmniArray.OfRef.DEFAULT_ARR;
    this.tail=-1;
  }
  public RefArrDeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new Object[initialCapacity];
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfRef.DEFAULT_ARR;
    case 0:
    }
    this.tail=-1;
  }
  RefArrDeq(int head,Object[] arr,int tail){
    super();
    this.arr=arr;
    this.head=head;
    this.tail=tail;
  }
  @Override public int size(){
    int tail;
    if((tail=this.tail+1)!=0){
      if((tail-=this.head)<=0){
        tail+=arr.length;
      }
    }
    return tail;
  }
  @Override public boolean isEmpty(){
    return this.tail!=-1;
  }
  @Override public void forEach(Consumer<? super E> action){
    final int tail;
    if((tail=this.tail)!=-1){
      uncheckedForEach(tail,action);
    }
  }
  @Override public boolean removeIf(Predicate<? super E> filter){
    final int tail;
    if((tail=this.tail)!=-1){
      final int head;
      if(tail<(head=this.head)){
        return fragmentedRemoveIf(head,tail,filter);
      }
      return nonfragmentedRemoveIf(head,tail,filter);
    }
    return false;
  }
  @Override public void clear(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final int head;
      if(tail<(head=this.head)){
        OmniArray.OfRef.nullifyRange(arr,tail,0);
        tail=arr.length-1;
      }
      OmniArray.OfRef.nullifyRange(arr,head,tail);
      this.tail=-1;
    }
  }
  void uncheckedForEach(final int tail,Consumer<? super E> action){
    final var arr=this.arr;
    int head;
    if(tail<(head=this.head)){
      OmniArray.OfRef.ascendingForEach(arr,head,arr.length-1,action);
      head=0;
    }
    OmniArray.OfRef.ascendingForEach(arr,head,tail,action);
  }
  boolean fragmentedRemoveIf(int head,int tail,Predicate<? super E> filter){
    //TODO
    return false;
  }
  boolean nonfragmentedRemoveIf(int head,int tail,Predicate<? super E> filter){
    //TODO
    return false;
  }
  @Override public OmniIterator.OfRef<E> iterator(){
    //TODO
    return null;
  }
  @Override public OmniIterator.OfRef<E> descendingIterator(){
    //TODO
    return null;
  }
  @Override public Object clone(){
    //TODO
    return null;
  }
  @Override public String toString(){
    //TODO
    return null;
  }
  @Override public int hashCode(){
    //TODO
    return 0;
  }
  @Override public boolean equals(Object obj){
    //TODO
    return false;
  }
  @Override public void push(E val){
    //TODO
  }
  @Override public E pop(){
    //TODO
    return null;
  }
  @Override public E removeLast(){
    //TODO
    return null;
  }
  @Override public boolean add(E val){
    addLast(val);
    return true;
  }
  @Override public void addLast(E val){
    //TODO
  }
  @Override public void addFirst(E val){
    push(val);
  }
  @Override public boolean offerFirst(E val){
    push(val);
    return true;
  }
  @Override public boolean offerLast(E val){
    addLast(val);
    return true;
  }
  @Override public E element(){
    return (E)arr[head];
  }
  @Override public E getLast(){
    return (E)arr[tail];
  }
  @Override public boolean offer(E val){
    addLast(val);
    return true;
  }
  @Override public boolean contains(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean contains(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean contains(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean contains(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean contains(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean contains(Object val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedcontainsNonNull(tail,val);
        }
        return uncheckedcontainsNull(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean contains(char val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean contains(short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean contains(Boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return uncheckedcontainsNull(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(Byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return uncheckedcontainsNull(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(Character val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return uncheckedcontainsNull(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(Short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return uncheckedcontainsNull(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(Integer val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return uncheckedcontainsNull(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(Long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return uncheckedcontainsNull(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(Float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return uncheckedcontainsNull(tail);
      }
    }
    return false;
  }
  @Override public boolean contains(Double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedcontains(tail,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return uncheckedcontainsNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean remove(Object val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveValNonNull(tail,val);
        }
        return uncheckedremoveValNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(char val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeVal(Boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return uncheckedremoveValNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(Byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return uncheckedremoveValNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(Character val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return uncheckedremoveValNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(Short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return uncheckedremoveValNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(Integer val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return uncheckedremoveValNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(Long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return uncheckedremoveValNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(Float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return uncheckedremoveValNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeVal(Double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveVal(tail,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return uncheckedremoveValNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Object val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveLastOccurrenceNonNull(tail,val);
        }
        return uncheckedremoveLastOccurrenceNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(char val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return uncheckedremoveLastOccurrenceNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return uncheckedremoveLastOccurrenceNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Character val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return uncheckedremoveLastOccurrenceNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return uncheckedremoveLastOccurrenceNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Integer val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return uncheckedremoveLastOccurrenceNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return uncheckedremoveLastOccurrenceNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return uncheckedremoveLastOccurrenceNull(tail);
      }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return uncheckedremoveLastOccurrenceNull(tail);
      }
    }
    return false;
  }
  @Override public int search(boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override public int search(int val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override public int search(long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override public int search(float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override public int search(double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override public int search(Object val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedsearchNonNull(tail,val);
        }
        return uncheckedsearchNull(tail);
      }
    }
    return -1;
  }
  @Override public int search(byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override public int search(char val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override public int search(short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override public int search(Boolean val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return uncheckedsearchNull(tail);
      }
    }
    return -1;
  }
  @Override public int search(Byte val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return uncheckedsearchNull(tail);
      }
    }
    return -1;
  }
  @Override public int search(Character val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return uncheckedsearchNull(tail);
      }
    }
    return -1;
  }
  @Override public int search(Short val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return uncheckedsearchNull(tail);
      }
    }
    return -1;
  }
  @Override public int search(Integer val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return uncheckedsearchNull(tail);
      }
    }
    return -1;
  }
  @Override public int search(Long val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return uncheckedsearchNull(tail);
      }
    }
    return -1;
  }
  @Override public int search(Float val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return uncheckedsearchNull(tail);
      }
    }
    return -1;
  }
  @Override public int search(Double val)
  {
    {
      int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          return uncheckedsearch(tail,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return uncheckedsearchNull(tail);
      }
    }
    return -1;
  }
  @Override public Object[] toArray(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final Object[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=new Object[size+=arr.length],size-=tail,tail);
      }else{
        dst=new Object[size];
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  @Override public E peek(){
    if(tail!=-1){
      return (E)(arr[head]);
    }
    return null;
  }
  @Override public E peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (E)(arr[tail]);
    }
    return null;
  }
  @Override public E poll(){
    int tail;
    if((tail=this.tail)!=-1){
      final Object[] arr;
      int head;
      var ret=(E)((arr=this.arr)[head=this.head]);
      arr[head]=null;
      if(head==tail){
        this.tail=-1;
        return ret;
      }else if(++head==arr.length){
        head=0;
      }
      this.head=head;
      return ret;
    }
    return null;
  }
  @Override public E pollLast(){
    int tail;
    if((tail=this.tail)!=-1){
      final Object[] arr;
      var ret=(E)((arr=this.arr)[tail]);
      arr[tail]=null;
      if(this.head==tail){
        tail=-1;
      }else if(--tail==-1){
        tail=arr.length-1;
      }
      this.tail=tail;
      return ret;
    }
    return null;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final T[] dst;
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=arrConstructor.apply(size+=arr.length),size-=tail,tail);
      }else{
        dst=arrConstructor.apply(size);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return dst;
    }
    return arrConstructor.apply(0);
  }
  @Override public <T> T[] toArray(T[] dst){
    int tail;
    if((tail=this.tail)!=-1){
      int size,head;
      if((size=(++tail)-(head=this.head))<=0){
        ArrCopy.uncheckedCopy(arr,0,dst=OmniArray.uncheckedArrResize(size+=arr.length,dst),size-=tail,tail);
      }else{
        dst=OmniArray.uncheckedArrResize(size,dst);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public E getFirst(){
    return element();
  }
  @Override public E peekFirst(){
    return peek();
  }
  @Override public E pollFirst(){
    return poll();
  }
  @Override public E removeFirst(){
    return pop();
  }
  @Override public E remove(){
    return pop();
  }
  @Override public boolean removeFirstOccurrence(Object val){
    return remove(val);
  }
  boolean uncheckedremoveValNonNull(int tail
  ,Object nonNull
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(index=head,bound=arr.length-1;;++index){
          if(nonNull.equals(arr[index])){
            int headDist,tailDist;
            if((headDist=index-head)<(tailDist=(bound-index)+tail)){
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=(tail>bound)?0:tail;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
              arr[bound]=arr[0];
              ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
              arr[tail]=null;
              this.tail=(--tail==-1)?bound:tail;
            }
            return true;
          }else if(index==bound){
            for(index=0;;++index){
              if(nonNull.equals(arr[index])){
                int headDist,tailDist;
                if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                  ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                  arr[0]=arr[bound];
                  ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                  arr[head]=null;
                  this.head=(tail>bound)?0:tail;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  arr[tail]=null;
                  this.tail=(--tail==-1)?bound:tail;
                }
                return true;
              }else if(index==tail){
                break;
              }
            }
          }
        }
      }else{
        for(index=head;;++index){
          if(nonNull.equals(arr[index])){
            int headDist,tailDist;
            if((tailDist=tail-index)<=(headDist=index-head)){
              if(headDist==0){
                this.tail=-1;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                this.tail=tail-1;
              }
              arr[tail]=null;
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=tail;
            }
            return true;
          }else if(index==tail){
            break;
          }
        }
      }
    }
    return false;
  }
  boolean uncheckedremoveValNull(int tail
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(index=head,bound=arr.length-1;;++index){
          if(null==(arr[index])){
            int headDist,tailDist;
            if((headDist=index-head)<(tailDist=(bound-index)+tail)){
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=(tail>bound)?0:tail;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
              arr[bound]=arr[0];
              ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
              arr[tail]=null;
              this.tail=(--tail==-1)?bound:tail;
            }
            return true;
          }else if(index==bound){
            for(index=0;;++index){
              if(null==(arr[index])){
                int headDist,tailDist;
                if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                  ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                  arr[0]=arr[bound];
                  ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                  arr[head]=null;
                  this.head=(tail>bound)?0:tail;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  arr[tail]=null;
                  this.tail=(--tail==-1)?bound:tail;
                }
                return true;
              }else if(index==tail){
                break;
              }
            }
          }
        }
      }else{
        for(index=head;;++index){
          if(null==(arr[index])){
            int headDist,tailDist;
            if((tailDist=tail-index)<=(headDist=index-head)){
              if(headDist==0){
                this.tail=-1;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                this.tail=tail-1;
              }
              arr[tail]=null;
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=tail;
            }
            return true;
          }else if(index==tail){
            break;
          }
        }
      }
    }
    return false;
  }
  boolean uncheckedremoveVal(int tail
  ,Predicate<Object> pred
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(index=head,bound=arr.length-1;;++index){
          if(pred.test(arr[index])){
            int headDist,tailDist;
            if((headDist=index-head)<(tailDist=(bound-index)+tail)){
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=(tail>bound)?0:tail;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
              arr[bound]=arr[0];
              ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
              arr[tail]=null;
              this.tail=(--tail==-1)?bound:tail;
            }
            return true;
          }else if(index==bound){
            for(index=0;;++index){
              if(pred.test(arr[index])){
                int headDist,tailDist;
                if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                  ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                  arr[0]=arr[bound];
                  ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                  arr[head]=null;
                  this.head=(tail>bound)?0:tail;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  arr[tail]=null;
                  this.tail=(--tail==-1)?bound:tail;
                }
                return true;
              }else if(index==tail){
                break;
              }
            }
          }
        }
      }else{
        for(index=head;;++index){
          if(pred.test(arr[index])){
            int headDist,tailDist;
            if((tailDist=tail-index)<=(headDist=index-head)){
              if(headDist==0){
                this.tail=-1;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                this.tail=tail-1;
              }
              arr[tail]=null;
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=tail;
            }
            return true;
          }else if(index==tail){
            break;
          }
        }
      }
    }
    return false;
  }
  boolean uncheckedremoveLastOccurrenceNonNull(int tail
  ,Object nonNull
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(bound=arr.length-1,index=tail;;--index){
          if(nonNull.equals(arr[index])){
            int headDist,tailDist;
            if((headDist=bound-head)+index+1<(tailDist=tail-index)){
              ArrCopy.semicheckedCopy(arr,0,arr,1,index);
              arr[0]=arr[bound];
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=(tail>bound)?0:tail;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
              arr[tail]=null;
              this.tail=(--tail==-1)?bound:tail;
            }
            return true;
          }else if(index==0){
            for(index=bound;;--index){
              if(nonNull.equals(arr[index])){
                int headDist,tailDist;
                if((headDist=index-head)<(tailDist=(bound-index)+tail)){
                  ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                  arr[head]=null;
                  this.head=(tail>bound)?0:tail;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  arr[bound]=arr[0];
                  ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                  arr[tail]=null;
                  this.tail=(--tail==-1)?bound:tail;
                }
                return true;
              }else if(index==tail){
                break;
              }
            }
          }
        }
      }else{
        for(index=tail;;--index){
          if(nonNull.equals(arr[index])){
            int headDist,tailDist;
            if((tailDist=tail-index)<=(headDist=index-head)){
              if(headDist==0){
                this.tail=-1;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                this.tail=tail-1;
              }
              arr[tail]=null;
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=tail;
            }
            return true;
          }else if(index==head){
            break;
          }
        }
      }
    }
    return false;
  }
  boolean uncheckedremoveLastOccurrenceNull(int tail
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(bound=arr.length-1,index=tail;;--index){
          if(null==(arr[index])){
            int headDist,tailDist;
            if((headDist=bound-head)+index+1<(tailDist=tail-index)){
              ArrCopy.semicheckedCopy(arr,0,arr,1,index);
              arr[0]=arr[bound];
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=(tail>bound)?0:tail;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
              arr[tail]=null;
              this.tail=(--tail==-1)?bound:tail;
            }
            return true;
          }else if(index==0){
            for(index=bound;;--index){
              if(null==(arr[index])){
                int headDist,tailDist;
                if((headDist=index-head)<(tailDist=(bound-index)+tail)){
                  ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                  arr[head]=null;
                  this.head=(tail>bound)?0:tail;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  arr[bound]=arr[0];
                  ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                  arr[tail]=null;
                  this.tail=(--tail==-1)?bound:tail;
                }
                return true;
              }else if(index==tail){
                break;
              }
            }
          }
        }
      }else{
        for(index=tail;;--index){
          if(null==(arr[index])){
            int headDist,tailDist;
            if((tailDist=tail-index)<=(headDist=index-head)){
              if(headDist==0){
                this.tail=-1;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                this.tail=tail-1;
              }
              arr[tail]=null;
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=tail;
            }
            return true;
          }else if(index==head){
            break;
          }
        }
      }
    }
    return false;
  }
  boolean uncheckedremoveLastOccurrence(int tail
  ,Predicate<Object> pred
  ){
    {
      final var arr=this.arr;
      int head,index;
      if(tail<(head=this.head)){
        final int bound;
        for(bound=arr.length-1,index=tail;;--index){
          if(pred.test(arr[index])){
            int headDist,tailDist;
            if((headDist=bound-head)+index+1<(tailDist=tail-index)){
              ArrCopy.semicheckedCopy(arr,0,arr,1,index);
              arr[0]=arr[bound];
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=(tail>bound)?0:tail;
            }else{
              ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
              arr[tail]=null;
              this.tail=(--tail==-1)?bound:tail;
            }
            return true;
          }else if(index==0){
            for(index=bound;;--index){
              if(pred.test(arr[index])){
                int headDist,tailDist;
                if((headDist=index-head)<(tailDist=(bound-index)+tail)){
                  ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                  arr[head]=null;
                  this.head=(tail>bound)?0:tail;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  arr[bound]=arr[0];
                  ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                  arr[tail]=null;
                  this.tail=(--tail==-1)?bound:tail;
                }
                return true;
              }else if(index==tail){
                break;
              }
            }
          }
        }
      }else{
        for(index=tail;;--index){
          if(pred.test(arr[index])){
            int headDist,tailDist;
            if((tailDist=tail-index)<=(headDist=index-head)){
              if(headDist==0){
                this.tail=-1;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                this.tail=tail-1;
              }
              arr[tail]=null;
            }else{
              ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
              arr[head]=null;
              this.head=tail;
            }
            return true;
          }else if(index==head){
            break;
          }
        }
      }
    }
    return false;
  }
  private boolean uncheckedcontainsNonNull(int tail
  ,Object nonNull
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfRef.uncheckedcontainsNonNull(arr,0,tail,nonNull) || OmniArray.OfRef.uncheckedcontainsNonNull(arr,head,arr.length-1,nonNull);
    }
    return OmniArray.OfRef.uncheckedcontainsNonNull(arr,head,tail,nonNull);
  }
  private boolean uncheckedcontainsNull(int tail
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfRef.uncheckedcontainsNull(arr,0,tail ) || OmniArray.OfRef.uncheckedcontainsNull(arr,head,arr.length-1 );
    }
    return OmniArray.OfRef.uncheckedcontainsNull(arr,head,tail );
  }
  private boolean uncheckedcontains (int tail
  ,Predicate<Object> pred
  ){
    final var arr=this.arr;
    final int head;
    if(tail<(head=this.head)){
      return OmniArray.OfRef.uncheckedcontains (arr,0,tail,pred) || OmniArray.OfRef.uncheckedcontains (arr,head,arr.length-1,pred);
    }
    return OmniArray.OfRef.uncheckedcontains (arr,head,tail,pred);
  }
  private int uncheckedsearchNonNull(int tail
  ,Object nonNull
  ){
    final var arr=this.arr;
    int head,prefix;
    if(tail<(head=this.head)){
      int bound;
      for(prefix=head,bound=arr.length;;)
      {
        if(nonNull.equals(arr[prefix]))
        {
          return prefix-head+1;
        }
        if(++prefix==bound){
          prefix=bound-head;
          head=0;
        }
      }
    }else{
      prefix=-head;
    }
    for(;;++head){
      if(nonNull.equals(arr[head]))
      {
        return prefix+head+1;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  private int uncheckedsearchNull(int tail
  ){
    final var arr=this.arr;
    int head,prefix;
    if(tail<(head=this.head)){
      int bound;
      for(prefix=head,bound=arr.length;;)
      {
        if(null==(arr[prefix]))
        {
          return prefix-head+1;
        }
        if(++prefix==bound){
          prefix=bound-head;
          head=0;
        }
      }
    }else{
      prefix=-head;
    }
    for(;;++head){
      if(null==(arr[head]))
      {
        return prefix+head+1;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  private int uncheckedsearch (int tail
  ,Predicate<Object> pred
  ){
    final var arr=this.arr;
    int head,prefix;
    if(tail<(head=this.head)){
      int bound;
      for(prefix=head,bound=arr.length;;)
      {
        if(pred.test(arr[prefix]))
        {
          return prefix-head+1;
        }
        if(++prefix==bound){
          prefix=bound-head;
          head=0;
        }
      }
    }else{
      prefix=-head;
    }
    for(;;++head){
      if(pred.test(arr[head]))
      {
        return prefix+head+1;
      }
      if(head==tail)
      {
        return -1;
      }
    }
  }
  public static class Checked<E> extends RefArrDeq<E>{
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(int initialCapacity){
      super(initialCapacity);
    }
    Checked(int head,Object[] arr,int tail){
      super(head,arr,tail);
    }
    @Override public void clear(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final var arr=this.arr;
        final int head;
        if(tail<(head=this.head)){
          OmniArray.OfRef.nullifyRange(arr,tail,0);
          tail=arr.length-1;
        }
        OmniArray.OfRef.nullifyRange(arr,head,tail);
        this.tail=-1;
      }
    }
    @Override public void push(E val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void addLast(E val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public E pop(){
      //TODO
      return null;
    }
    @Override public E removeLast(){
      //TODO
      return null;
    }
    @Override void uncheckedForEach(final int tail,Consumer<? super E> action){
      final int modCount=this.modCount;
      try{
        super.uncheckedForEach(tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override boolean fragmentedRemoveIf(int head,int tail,Predicate<? super E> filter){
      //TODO
      return false;
    }
    @Override boolean nonfragmentedRemoveIf(int head,int tail,Predicate<? super E> filter){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      //TODO
      return null;
    }
    @Override public Object clone(){
      //TODO
      return null;
    }
    @Override public boolean equals(Object obj){
      //TODO
      return false;
    }
    @Override public E element(){
      if(tail!=-1){
        return (E)arr[head];
      }
      throw new NoSuchElementException();
    }
    @Override public E getLast(){
      final int tail;
      if((tail=this.tail)!=-1){
        return (E)arr[tail];
      }
      throw new NoSuchElementException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public E poll(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final Object[] arr;
        int head;
        var ret=(E)((arr=this.arr)[head=this.head]);
        arr[head]=null;
        if(head==tail){
          this.tail=-1;
          return ret;
        }else if(++head==arr.length){
          head=0;
        }
        this.head=head;
        return ret;
      }
      return null;
    }
    @Override public E pollLast(){
      int tail;
      if((tail=this.tail)!=-1){
        ++this.modCount;
        final Object[] arr;
        var ret=(E)((arr=this.arr)[tail]);
        arr[tail]=null;
        if(this.head==tail){
          tail=-1;
        }else if(--tail==-1){
          tail=arr.length-1;
        }
        this.tail=tail;
        return ret;
      }
      return null;
    }
    @Override public String toString(){
      //TODO
      return null;
    }
    @Override public int hashCode(){
      //TODO
      return 0;
    }
    @Override public boolean contains(Object val){
      final int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return super.uncheckedcontainsNonNull(tail,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return super.uncheckedcontainsNull(tail);
      }
      return false;
    }
    @Override public int search(Object val){
      final int tail;
      if((tail=this.tail)!=-1){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return super.uncheckedsearchNonNull(tail,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return super.uncheckedsearchNull(tail);
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveValNonNull(int tail
    ,Object nonNull
    ){
      int modCount=this.modCount;
      try
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(index=head,bound=arr.length-1;;++index){
            if(nonNull.equals(arr[index])){
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((headDist=index-head)<(tailDist=(bound-index)+tail)){
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=(tail>bound)?0:tail;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                arr[bound]=arr[0];
                ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                arr[tail]=null;
                this.tail=(--tail==-1)?bound:tail;
              }
              return true;
            }else if(index==bound){
              for(index=0;;++index){
                if(nonNull.equals(arr[index])){
                  CheckedCollection.checkModCount(modCount,this.modCount);
                  this.modCount=modCount+1;
                  int headDist,tailDist;
                  if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                    ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                    arr[0]=arr[bound];
                    ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                    arr[head]=null;
                    this.head=(tail>bound)?0:tail;
                  }else{
                    ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                    arr[tail]=null;
                    this.tail=(--tail==-1)?bound:tail;
                  }
                  return true;
                }else if(index==tail){
                  break;
                }
              }
            }
          }
        }else{
          for(index=head;;++index){
            if(nonNull.equals(arr[index])){
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((tailDist=tail-index)<=(headDist=index-head)){
                if(headDist==0){
                  this.tail=-1;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  this.tail=tail-1;
                }
                arr[tail]=null;
              }else{
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=tail;
              }
              return true;
            }else if(index==tail){
              break;
            }
          }
        }
      }
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override
    boolean uncheckedremoveValNull(int tail
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(index=head,bound=arr.length-1;;++index){
            if(null==(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((headDist=index-head)<(tailDist=(bound-index)+tail)){
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=(tail>bound)?0:tail;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                arr[bound]=arr[0];
                ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                arr[tail]=null;
                this.tail=(--tail==-1)?bound:tail;
              }
              return true;
            }else if(index==bound){
              for(index=0;;++index){
                if(null==(arr[index])){
                  this.modCount=modCount+1;
                  int headDist,tailDist;
                  if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                    ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                    arr[0]=arr[bound];
                    ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                    arr[head]=null;
                    this.head=(tail>bound)?0:tail;
                  }else{
                    ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                    arr[tail]=null;
                    this.tail=(--tail==-1)?bound:tail;
                  }
                  return true;
                }else if(index==tail){
                  break;
                }
              }
            }
          }
        }else{
          for(index=head;;++index){
            if(null==(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((tailDist=tail-index)<=(headDist=index-head)){
                if(headDist==0){
                  this.tail=-1;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  this.tail=tail-1;
                }
                arr[tail]=null;
              }else{
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=tail;
              }
              return true;
            }else if(index==tail){
              break;
            }
          }
        }
      }
      return false;
    }
    @Override
    boolean uncheckedremoveVal(int tail
    ,Predicate<Object> pred
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(index=head,bound=arr.length-1;;++index){
            if(pred.test(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((headDist=index-head)<(tailDist=(bound-index)+tail)){
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=(tail>bound)?0:tail;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                arr[bound]=arr[0];
                ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                arr[tail]=null;
                this.tail=(--tail==-1)?bound:tail;
              }
              return true;
            }else if(index==bound){
              for(index=0;;++index){
                if(pred.test(arr[index])){
                  this.modCount=modCount+1;
                  int headDist,tailDist;
                  if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                    ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                    arr[0]=arr[bound];
                    ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                    arr[head]=null;
                    this.head=(tail>bound)?0:tail;
                  }else{
                    ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                    arr[tail]=null;
                    this.tail=(--tail==-1)?bound:tail;
                  }
                  return true;
                }else if(index==tail){
                  break;
                }
              }
            }
          }
        }else{
          for(index=head;;++index){
            if(pred.test(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((tailDist=tail-index)<=(headDist=index-head)){
                if(headDist==0){
                  this.tail=-1;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  this.tail=tail-1;
                }
                arr[tail]=null;
              }else{
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=tail;
              }
              return true;
            }else if(index==tail){
              break;
            }
          }
        }
      }
      return false;
    }
    @Override
    boolean uncheckedremoveLastOccurrenceNonNull(int tail
    ,Object nonNull
    ){
      int modCount=this.modCount;
      try
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(bound=arr.length-1,index=tail;;--index){
            if(nonNull.equals(arr[index])){
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                arr[0]=arr[bound];
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=(tail>bound)?0:tail;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                arr[tail]=null;
                this.tail=(--tail==-1)?bound:tail;
              }
              return true;
            }else if(index==0){
              for(index=bound;;--index){
                if(nonNull.equals(arr[index])){
                  CheckedCollection.checkModCount(modCount,this.modCount);
                  this.modCount=modCount+1;
                  int headDist,tailDist;
                  if((headDist=index-head)<(tailDist=(bound-index)+tail)){
                    ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                    arr[head]=null;
                    this.head=(tail>bound)?0:tail;
                  }else{
                    ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                    arr[bound]=arr[0];
                    ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                    arr[tail]=null;
                    this.tail=(--tail==-1)?bound:tail;
                  }
                  return true;
                }else if(index==tail){
                  break;
                }
              }
            }
          }
        }else{
          for(index=tail;;--index){
            if(nonNull.equals(arr[index])){
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((tailDist=tail-index)<=(headDist=index-head)){
                if(headDist==0){
                  this.tail=-1;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  this.tail=tail-1;
                }
                arr[tail]=null;
              }else{
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=tail;
              }
              return true;
            }else if(index==head){
              break;
            }
          }
        }
      }
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override
    boolean uncheckedremoveLastOccurrenceNull(int tail
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(bound=arr.length-1,index=tail;;--index){
            if(null==(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                arr[0]=arr[bound];
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=(tail>bound)?0:tail;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                arr[tail]=null;
                this.tail=(--tail==-1)?bound:tail;
              }
              return true;
            }else if(index==0){
              for(index=bound;;--index){
                if(null==(arr[index])){
                  this.modCount=modCount+1;
                  int headDist,tailDist;
                  if((headDist=index-head)<(tailDist=(bound-index)+tail)){
                    ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                    arr[head]=null;
                    this.head=(tail>bound)?0:tail;
                  }else{
                    ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                    arr[bound]=arr[0];
                    ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                    arr[tail]=null;
                    this.tail=(--tail==-1)?bound:tail;
                  }
                  return true;
                }else if(index==tail){
                  break;
                }
              }
            }
          }
        }else{
          for(index=tail;;--index){
            if(null==(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((tailDist=tail-index)<=(headDist=index-head)){
                if(headDist==0){
                  this.tail=-1;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  this.tail=tail-1;
                }
                arr[tail]=null;
              }else{
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=tail;
              }
              return true;
            }else if(index==head){
              break;
            }
          }
        }
      }
      return false;
    }
    @Override
    boolean uncheckedremoveLastOccurrence(int tail
    ,Predicate<Object> pred
    ){
      {
        final var arr=this.arr;
        int head,index;
        if(tail<(head=this.head)){
          final int bound;
          for(bound=arr.length-1,index=tail;;--index){
            if(pred.test(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((headDist=bound-head)+index+1<(tailDist=tail-index)){
                ArrCopy.semicheckedCopy(arr,0,arr,1,index);
                arr[0]=arr[bound];
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=(tail>bound)?0:tail;
              }else{
                ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                arr[tail]=null;
                this.tail=(--tail==-1)?bound:tail;
              }
              return true;
            }else if(index==0){
              for(index=bound;;--index){
                if(pred.test(arr[index])){
                  this.modCount=modCount+1;
                  int headDist,tailDist;
                  if((headDist=index-head)<(tailDist=(bound-index)+tail)){
                    ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                    arr[head]=null;
                    this.head=(tail>bound)?0:tail;
                  }else{
                    ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                    arr[bound]=arr[0];
                    ArrCopy.semicheckedSelfCopy(arr,0,1,tail);
                    arr[tail]=null;
                    this.tail=(--tail==-1)?bound:tail;
                  }
                  return true;
                }else if(index==tail){
                  break;
                }
              }
            }
          }
        }else{
          for(index=tail;;--index){
            if(pred.test(arr[index])){
              this.modCount=modCount+1;
              int headDist,tailDist;
              if((tailDist=tail-index)<=(headDist=index-head)){
                if(headDist==0){
                  this.tail=-1;
                }else{
                  ArrCopy.semicheckedSelfCopy(arr,index,index+1,tailDist);
                  this.tail=tail-1;
                }
                arr[tail]=null;
              }else{
                ArrCopy.semicheckedCopy(arr,head,arr,tail=head+1,headDist);
                arr[head]=null;
                this.head=tail;
              }
              return true;
            }else if(index==head){
              break;
            }
          }
        }
      }
      return false;
    }
  }
}
