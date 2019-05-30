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
import java.util.Objects;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.util.RandomAccess;
public class RefArrDeq<E> implements OmniDeque.OfRef<E>,Externalizable,Cloneable,RandomAccess{
  private static final long serialVersionUID=1L;
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
    return this.tail==-1;
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
      OmniArray.OfRef.nullifyRange(arr,tail,head);
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
  @Override public boolean add(E val){
    addLast(val);
    return true;
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
  @SuppressWarnings("unchecked")
  @Override public E element(){
    return (E)arr[head];
  }
  @SuppressWarnings("unchecked")
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
  @SuppressWarnings("unchecked")
  @Override public E peek(){
    if(tail!=-1){
      return (E)(arr[head]);
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  @Override public E peekLast(){
    final int tail;
    if((tail=this.tail)!=-1){
      return (E)(arr[tail]);
    }
    return null;
  }
  @SuppressWarnings("unchecked")
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
  @SuppressWarnings("unchecked")
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
            if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
            break;
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
            if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
            break;
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
            if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
            break;
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
                if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
            break;
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
                if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
            break;
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
                if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
            break;
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
          break;
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
          break;
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
          break;
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
  @Override public String toString(){
    final int tail;
    if((tail=this.tail)!=-1){
      return uncheckedToString(tail);
    }
    return "[]";
  }
  @Override public int hashCode(){
    final int tail;
    if((tail=this.tail)!=-1){
      return uncheckedHashCode(tail);
    }
    return 1;
  }
  @SuppressWarnings("unchecked")
  @Override public E pop(){
    final Object[] arr;
    int head;
    var ret=(E)((arr=this.arr)[head=this.head]);
    arr[head]=null;
    if(head==this.tail){
      this.tail=-1;
      return ret;
    }else if(++head==arr.length){
      head=0;
    }
    this.head=head;
    return ret;
  }
  @SuppressWarnings("unchecked")
  @Override public E removeLast(){
    final Object[] arr;
    int tail;
    var ret=(E)((arr=this.arr)[tail=this.tail]);
    arr[tail]=null;
    if(this.head==tail){
      tail=-1;
    }else if(--tail==-1){
      tail=arr.length-1;
    }
    this.tail=tail;
    return ret;
  }
  @Override public Object clone(){
    int tail;
    if((tail=this.tail)!=-1){
      final var arr=this.arr;
      final Object[] dst;
      int size,head;
      RefArrDeq<E> clone;
      if((size=(++tail)-(head=this.head))<=0){
        clone=new RefArrDeq<E>(0,dst=new Object[size+=arr.length],size-1);
        ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
      }else{
        clone=new RefArrDeq<E>(0,dst=new Object[size],size-1);
      }
      ArrCopy.uncheckedCopy(arr,head,dst,0,size);
      return clone;
    }
    return new RefArrDeq<E>();
  }
  private String uncheckedToString(int tail){
    final var arr=this.arr;
    final var builder=new StringBuilder("[");
    int head;
    if(tail<(head=this.head)){
      for(int bound=arr.length;;){
        builder.append(arr[head]).append(',').append(' ');
        if(++head==bound){
          head=0;
          break;
        }
      }
    }
    for(;;builder.append(',').append(' '),++head){
      builder.append(arr[head]);
      if(head==tail){
        return builder.append(']').toString();
      }
    }
  }
  private int uncheckedHashCode(int tail){
    final Object[] arr;
    int head;
    int hash=31+Objects.hashCode((arr=this.arr)[head=this.head]);
    if(tail<head){
      for(final int bound=arr.length;;){  
        if(++head==bound){
          hash=hash*31+Objects.hashCode(arr[head=0]);
          break;
        }
        hash=(hash*31)+Objects.hashCode(arr[head]);
      }
    }
    for(;head!=tail;hash=(hash*31)+Objects.hashCode(arr[++head])){}
    return hash;
  }
  @Override public void push(E val){
    Object[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfRef.DEFAULT_ARR){
        this.head=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.tail=OmniArray.DEFAULT_ARR_SEQ_CAP-1;
        this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
        arr[OmniArray.DEFAULT_ARR_SEQ_CAP-1]=val;
      }else{
        int tail;
        if((tail=this.tail)==-1){
          arr[tail=arr.length-1]=val;
          this.tail=tail;
          this.head=tail;
        }else{
          int head;
          if((head=this.head-1)==tail){
            final Object[] newArr;
            int newCap,size;
            this.tail=(newCap=OmniArray.growBy50Pct(head+(size=arr.length)))-1;
            ArrCopy.uncheckedCopy(arr,0,newArr=new Object[newCap],newCap-=(++tail),tail);
            ArrCopy.uncheckedCopy(arr,head+1,newArr,head=newCap-(size-=tail),size);
            this.arr=arr=newArr;
            --head;
          }else if(head==-1 && tail==(head=arr.length-1)){
            int newCap;
            this.tail=(newCap=OmniArray.growBy50Pct(++tail))-1;
            ArrCopy.uncheckedCopy(arr,0,arr=new Object[newCap],head=newCap-tail,tail);
            this.arr=arr;
            --head;
          }
          arr[head]=val;
          this.head=head;
        }
      }
    }else{
      initFromNullArr(val);
    }
  }
  private void initFromNullArr(E val){
    this.head=0;
    this.tail=0;
    this.arr=new Object[]{val};
  }
  @Override public void addLast(E val){
    Object[] arr;
    if((arr=this.arr)!=null){
      if(arr==OmniArray.OfRef.DEFAULT_ARR){
        this.head=0;
        this.tail=0;
        this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
        arr[0]=val;
      }else{
        int tail;
        if((tail=this.tail)==-1){
          arr[0]=val;
          this.tail=0;
          this.head=0;
        }else{
          int head;
          if(++tail==(head=this.head)){
            this.head=0;
            final Object[] newArr;
            (newArr=new Object[OmniArray.growBy50Pct(tail=arr.length)])[tail]=val;
            this.tail=tail;
            ArrCopy.uncheckedCopy(arr,head,newArr,0,tail-=head);
            ArrCopy.uncheckedCopy(arr,0,newArr,tail,head);
            this.arr=newArr;
          }else{
            if(tail==arr.length){
              if(head==0){
                ArrCopy.uncheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(tail)],0,tail);
                this.arr=arr;
              }else{
                tail=0;
              }
            }
            arr[tail]=val;
            this.tail=tail;
          }
        }
      }
    }else{
      initFromNullArr(val);
    }
  }
  private void eraseHead(){
    int head;
    switch(Integer.signum(this.tail-(head=this.head))){
      case -1:
        Object[] arr;
        (arr=this.arr)[head]=null;
        this.head=head==arr.length-1?0:head+1;
        return;
      case 0:
        this.tail=-1;
        break;
      default:
        this.head=head+1;
    }
    arr[head]=null;
  }
  private void eraseTail(){
    int tail;
    switch(Integer.signum((tail=this.tail)-this.head)){
      case -1:
        Object[] arr;
        (arr=this.arr)[tail]=null;
        this.tail=tail==0?arr.length-1:tail-1;
        return;
      case 0:
        this.tail=-1;
        break;
      default:
        this.tail=tail-1;
    }
    arr[tail]=null;
  }
  private static abstract class AbstractDeqItr<E>
    implements OmniIterator.OfRef<E>
  {
    transient int cursor;
    AbstractDeqItr(int cursor){
      this.cursor=cursor;
    }
    @Override public boolean hasNext(){
      return this.cursor!=-1;
    }
    abstract void uncheckedForEachRemaining(int cursor,Consumer<? super E> action);
    @Override public void forEachRemaining(Consumer<? super E> action){
      int cursor;
      if((cursor=this.cursor)!=-1){
        uncheckedForEachRemaining(cursor,action);
      }
    }
  }
  private static int pullUp(Object[] arr,int head,int headDist){
    final int tmp;
    ArrCopy.semicheckedCopy(arr,tmp=head,arr,++head,headDist);
    arr[tmp]=null;
    return head;
  }
  private static int fragmentedPullUp(Object[] arr,int head,int headDist){
    if(headDist==0){
      arr[head]=null;
      return 0;
    }else{
      int tmp;
      ArrCopy.uncheckedCopy(arr,tmp=head,arr,++head,headDist);
      arr[tmp]=null;
      return head;
    }
  }
  private static int fragmentedPullDown(Object[] arr,int arrBound,int tail){
    if(tail==0){
      arr[0]=null;
      return arrBound;
    }
    ArrCopy.uncheckedSelfCopy(arr,0,1,tail);
    arr[tail]=null;
    return tail-1;
  }
  private static class AscendingItr<E> extends AbstractDeqItr<E>
  {
    transient final RefArrDeq<E> root;
    private AscendingItr(RefArrDeq<E> root){
      super(root.tail!=-1?root.head:-1);
      this.root=root;
    }
    private AscendingItr(RefArrDeq<E> root,int cursor){
      super(cursor);
      this.root=root;
    }
    @SuppressWarnings("unchecked")
    @Override public E next(){
      final Object[] arr;
      int cursor;
      final RefArrDeq<E> root;
      final var ret=(E)(arr=(root=this.root).arr)[cursor=this.cursor];
      if(cursor==root.tail){
        cursor=-1;
      }else if(++cursor==arr.length){
        cursor=0;
      }
      this.cursor=cursor;
      return ret;
    }
    private void eraseAtSplit(){
      final int head,tail,headDist,arrBound;
      final RefArrDeq<E> root;
      final Object[] arr;
      if((tail=(root=this.root).tail)<(headDist=(arrBound=(arr=root.arr).length-1)-(head=root.head))){
        arr[arrBound]=arr[0];
        root.tail=fragmentedPullDown(arr,arrBound,tail);
        this.cursor=arrBound;
      }else{
        root.head=fragmentedPullUp(arr,head,headDist);
      }
    }
    private void fragmentedAscendingRemove(int head,int lastRet,int tail,RefArrDeq<E> root){
      Object[] arr;
      int headDist,tailDist,arrBound=(arr=root.arr).length-1;
      if((headDist=lastRet-head)>=0){
        //index to remove is in head run
        if(headDist<=(tailDist=arrBound-lastRet)+tail+1){
          root.head=pullUp(arr,head,headDist);
        }else{
          ArrCopy.semicheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
          arr[arrBound]=arr[0];
          root.tail=fragmentedPullDown(arr,arrBound,tail);
          this.cursor=lastRet;
        }
      }else{
        if((tailDist=tail-lastRet)<=(headDist=arrBound-head)+lastRet+1){
          ArrCopy.uncheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
          arr[tail]=null;
          root.tail=tail-1;
          this.cursor=lastRet;
        }else{
          ArrCopy.semicheckedCopy(arr,0,arr,1,lastRet);
          arr[0]=arr[arrBound];
          root.head=fragmentedPullUp(arr,head,headDist);
        }
      }
    }
    private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,RefArrDeq<E> root){
      int headDist,tailDist;
      if((headDist=lastRet-head)<=(tailDist=tail-lastRet)){
        root.head=pullUp(root.arr,head,headDist);
      }else{
        Object[] arr;
        ArrCopy.uncheckedSelfCopy(arr=root.arr,lastRet,lastRet+1,tailDist);
        arr[tail]=null;
        root.tail=tail-1;
        this.cursor=lastRet;
      }
    }
    @Override public void remove(){
      final int cursor;
      switch(cursor=this.cursor){
        case -1:
          root.eraseTail();
          break;
        case 0:
          eraseAtSplit();
          break;
        default:
          final int head,tail;
          final RefArrDeq<E> root;
          if((tail=(root=this.root).tail)<(head=root.head)){
            fragmentedAscendingRemove(head,cursor-1,tail,root);
          }else{
            nonfragmentedAscendingRemove(head,cursor-1,tail,root);
          }
      }
    }
    @Override void uncheckedForEachRemaining(int cursor,Consumer<? super E> action){
      final RefArrDeq<E> root;
      int tail;
      final var arr=(root=this.root).arr;
      if(cursor>(tail=root.tail)){
        OmniArray.OfRef.ascendingForEach(arr,cursor,arr.length-1,action);
        cursor=0;
      }
      OmniArray.OfRef.ascendingForEach(arr,cursor,tail,action);
      this.cursor=-1;
    }
  }
  private static class DescendingItr<E> extends AscendingItr<E>{
    private DescendingItr(RefArrDeq<E> root){
      super(root,root.tail);
    }
    @Override void uncheckedForEachRemaining(int cursor,Consumer<? super E> action){
      final RefArrDeq<E> root;
      final int head;
      final var arr=(root=this.root).arr;
      if(cursor<(head=root.head)){
        OmniArray.OfRef.descendingForEach(arr,0,cursor,action);
        cursor=arr.length-1;
      }
      OmniArray.OfRef.descendingForEach(arr,head,cursor,action);
      this.cursor=-1;
    }
    @SuppressWarnings("unchecked")
    @Override public E next(){
      int cursor;
      final RefArrDeq<E> root;
      final var arr=(root=this.root).arr;
      this.cursor=(cursor=this.cursor)==root.head?-1:cursor==0?arr.length-1:cursor-1;
      return (E)arr[cursor];
    }
    private void fragmentedDescendingRemove(int head,int cursor,int tail,RefArrDeq<E> root){
      Object[] arr;
      int arrBound;
      if((arrBound=(arr=root.arr).length-1)==cursor){
        //remove index 0
        if(tail<=(cursor=arrBound-head)){
          root.tail=fragmentedPullDown(arr,arrBound,tail);
        }else{
          arr[0]=arr[arrBound];
          root.head=fragmentedPullUp(arr,head,cursor);
          this.cursor=0;
        }
      }else{
        int headDist,tailDist;
        if((headDist=(++cursor)-head)>0){
          //removing from head run
          if(headDist<=(tailDist=arrBound-cursor)+tail+1){
            ArrCopy.uncheckedCopy(arr,tail=head,arr,++head,headDist);
            arr[tail]=null;
            root.head=head;
            this.cursor=cursor;
          }else{
            ArrCopy.semicheckedSelfCopy(arr,cursor,cursor+1,tailDist);
            arr[arrBound]=arr[0];
            root.tail=fragmentedPullDown(arr,arrBound,tail);
          }
        }else{
          //removing from tail run
          if((tailDist=tail-cursor)<=(headDist=arrBound-head)+cursor+1){
            ArrCopy.semicheckedSelfCopy(arr,cursor,cursor+1,tailDist);
            arr[tail]=null;
            root.tail=tail-1;
          }else{
            ArrCopy.uncheckedCopy(arr,0,arr,1,cursor);
            arr[0]=arr[arrBound];
            root.head=fragmentedPullUp(arr,head,headDist);
            this.cursor=cursor;
          }
        }
      }
    }
    private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,RefArrDeq<E> root){
      int tailDist,headDist;
      if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
        Object[] arr;
        ArrCopy.semicheckedSelfCopy(arr=root.arr,lastRet,lastRet+1,tailDist);
        arr[tail]=null;
        root.tail=tail-1;
      }else{
        Object[] arr;
        ArrCopy.uncheckedCopy(arr=root.arr,tail=head,arr,++head,headDist);
        arr[tail]=null;
        root.head=head;
        this.cursor=lastRet;
      }
    }
    @Override public void remove(){
      int cursor;
      if((cursor=this.cursor)==-1){
        root.eraseHead();
      }else{
        RefArrDeq<E> root;
        int head,tail;
        if((tail=(root=this.root).tail)<(head=root.head)){
          fragmentedDescendingRemove(head,cursor,tail,root);
        }else{
          nonfragmentedDescendingRemove(head,cursor+1,tail,root);
        }
      }
    }
  } 
  @Override public OmniIterator.OfRef<E> iterator(){
    return new AscendingItr<E>(this);
  }
  @Override public OmniIterator.OfRef<E> descendingIterator(){
    return new DescendingItr<E>(this);
  }
  @SuppressWarnings("unchecked")
  boolean fragmentedRemoveIf(int head,int tail,Predicate<? super E> filter){
    Object[] arr;
    if(filter.test((E)(arr=this.arr)[head]))
    {
      if(filter.test((E)arr[tail]))
      {
        fragmentedCollapseHeadAndTail(arr,head,tail,filter);
      }
      else
      {
        fragmentedCollapsehead(arr,head,tail,filter);
      }
      return true;
    }
    else if(filter.test((E)arr[tail]))
    {
      fragmentedCollapsetail(arr,head,tail,filter);
      return true;
    }
    return fragmentedCollapseBody(arr,head,tail,filter);
  }
  @SuppressWarnings("unchecked")
  private static <E> int pullDown(Object[] arr,int dstOffset,int srcBound,Predicate<? super E> filter){
    for(int srcOffset=dstOffset+1;srcOffset!=srcBound;++srcOffset)
    {
      final Object v;
      if(!filter.test((E)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    OmniArray.OfRef.nullifyRange(arr,srcBound,dstOffset+1);
    return dstOffset;
  }
  @SuppressWarnings("unchecked")
  private static <E> int pullUp(Object[] arr,int dstOffset,int srcBound,Predicate<? super E> filter){
    for(int srcOffset=dstOffset-1;srcOffset!=srcBound;--srcOffset)
    {
      final Object v;
      if(!filter.test((E)(v=arr[srcOffset])))
      {
        arr[dstOffset--]=v;
      }
    }
    arr[dstOffset]=arr[srcBound];
    OmniArray.OfRef.nullifyRange(arr,dstOffset-1,srcBound);
    return dstOffset;
  }
  @SuppressWarnings("unchecked")
  private void fragmentedCollapseBodyHelper(Object[] arr,int head,int tail,Predicate<? super E> filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset)
    {
      if(filter.test((E)arr[srcOffset]))
      {
        tail=pullDown(arr,srcOffset,tail,filter);
        break;
      }
    }
    this.tail=tail;
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset)
    {
      if(filter.test((E)arr[srcOffset]))
      {
        head=pullUp(arr,srcOffset,head,filter);
        break;
      }
    }
    this.head=head;
  }
  @SuppressWarnings("unchecked")
  private void collapseBodyHelper(Object[] ar,int head,int tail,Predicate<? super E> filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((E)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((E)arr[midPoint])){
            tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        this.tail=tail;
        return;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((E)arr[midPoint])){
        tail=pullDown(arr,midPoint,tail,filter);
        break;
      }
    }
    this.head=head;
    this.tail=tail;
  }
  @SuppressWarnings("unchecked")
  private void fragmentedCollapseHeadAndTail(Object[] arr,int head,int tail,Predicate<? super E> filter){
    arr[head]=null;
    outer:for(;;){
      do{
        arr[tail]=null;
        if(tail==0){
          for(tail=arr.length-1;tail!=head;--tail){
            if(!filter.test((E)arr[tail])){
              break outer;
            }
            arr[tail]=null;
          }  
          this.tail=-1;
          return;
        }
      }while(filter.test((E)arr[--tail]));
      for(int bound=arr.length;++head!=bound;){
        if(!filter.test((E)arr[head])){
          fragmentedCollapseBodyHelper(arr,head,tail,filter);
          return;
        }
        arr[head]=null;
      }
      head=-1;
      break;
    }
    while(++head!=tail){
      if(!filter.test((E)arr[head])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
      arr[head]=null;
    }
    this.head=head;
    this.tail=tail;
  }
  @SuppressWarnings("unchecked")
  private boolean fragmentedCollapseBody(Object[] arr,int head,int tail,Predicate<? super E> filter){
    for(int srcOffset=0;srcOffset!=tail;++srcOffset){
      if(filter.test((E)arr[srcOffset])){
        this.tail=pullDown(arr,srcOffset,tail,filter);
        for(srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
          if(filter.test((E)arr[srcOffset])){
            this.head=pullUp(arr,srcOffset,head,filter);
            break;
          }
        }
        return true;
      }
    }
    for(int srcOffset=arr.length-1;srcOffset!=head;--srcOffset){
      if(filter.test((E)arr[srcOffset]))
      {
        this.head=pullUp(arr,srcOffset,head,filter);
        return true;
      }
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  private void fragmentedCollapsehead(Object[] arr,int head,int tail,Predicate<? super E> filter){
    for(int bound=arr.length;;)
    {
      arr[head]=null;
      if(++head==bound){
        for(head=0;;++head)
        {
          if(head==tail){
            this.head=head;
            return;
          }else if(!filter.test((E)arr[head])){
            collapseBodyHelper(arr,head,tail,filter);
            return;
          }
          arr[head]=null;
        }
      }
      if(!filter.test((E)arr[head]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  @SuppressWarnings("unchecked")
  private void collapsehead(Object[] arr,int head,int tail,Predicate<? super E> filter){
    do{
      arr[head]=null;
      if(++head==tail){
        this.head=head;
        return;
      }
    }while(filter.test((E)arr[head]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  @SuppressWarnings("unchecked")
  private void fragmentedCollapsetail(Object[] arr,int head,int tail,Predicate<? super E> filter){
    for(;;)
    {
      arr[tail]=null;
      if(tail==0){
        for(tail=arr.length-1;;--tail)
        {
          if(tail==head){
            this.tail=head;
            return;
          }else if(!filter.test((E)arr[tail])){
            collapseBodyHelper(arr,head,tail,filter);
            return;
          }
          arr[tail]=null;
        }
      }
      if(!filter.test((E)arr[--tail]))
      {
        fragmentedCollapseBodyHelper(arr,head,tail,filter);
        return;
      }
    }
  }
  @SuppressWarnings("unchecked")
  private void collapsetail(Object[] arr,int head,int tail,Predicate<? super E> filter){
    do{
      arr[tail]=null;
      if(--tail==head){
        this.tail=tail;
        return;
      }
    }while(filter.test((E)arr[tail]));
    collapseBodyHelper(arr,head,tail,filter);
  }
  @SuppressWarnings("unchecked")
  private void collapseHeadAndTail(Object[] arr,int head,int tail,Predicate<? super E> filter){
    arr[tail]=null;
    do{
      arr[head]=null;
      if(++head==tail){
        this.tail=-1;
        return;
      }
    }while(filter.test((E)arr[head]));
    while(--tail!=head){
      if(!filter.test((E)arr[tail])){
        collapseBodyHelper(arr,head,tail,filter);
        return;
      }
      arr[tail]=null;
    }
    this.head=head;
    this.tail=head;
  }
  @SuppressWarnings("unchecked")
  private boolean collapseBody(Object[] arr,int head,int tail,Predicate<? super E> filter){
    int midPoint;
    for(int srcOffset=midPoint=(head+tail)>>1;srcOffset!=head;--srcOffset){
      if(filter.test((E)arr[srcOffset])){
        this.head=pullUp(arr,srcOffset,head,filter);
        while(++midPoint!=tail){
          if(filter.test((E)arr[midPoint])){
            this.tail=pullDown(arr,midPoint,tail,filter);
            break;
          }
        }
        return true;
      }
    }
    while(++midPoint!=tail){
      if(filter.test((E)arr[midPoint])){
        this.tail=pullDown(arr,midPoint,tail,filter);
        return true;
      }
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  boolean nonfragmentedRemoveIf(int head,int tail,Predicate<? super E> filter){
    final Object[] arr;
    if(filter.test((E)(arr=this.arr)[head])){
      if(head==tail){
        arr[tail]=null;
        this.tail=-1;
      }else{
        if(filter.test((E)arr[tail])){
          collapseHeadAndTail(arr,head,tail,filter);
        }else{
          collapsehead(arr,head,tail,filter);
        }
      }
      return true;
    }else if(head!=tail){
      if(filter.test((E)arr[tail])){
        collapsetail(arr,head,tail,filter);
        return true;
      }
      return collapseBody(arr,head,tail,filter);
    }
    return false;
  }
  @Override public void readExternal(ObjectInput input) throws IOException
    ,ClassNotFoundException
  {
    int size;
    if((size=input.readInt())!=0){
      Object[] arr;
      OmniArray.OfRef.readArray(arr=new Object[size],0,--size,input);
      this.tail=size;
      this.head=0;
      this.arr=arr;
    }else{
      this.tail=-1;
    }
  }
  @Override public void writeExternal(ObjectOutput output) throws IOException{
    int tail;
    if((tail=this.tail)!=-1){
      int head,size;
      if((size=(++tail)-(head=this.head))<=0){
        Object[] arr;
        output.writeInt(size+(size=(arr=this.arr).length));
        OmniArray.OfRef.writeArray(arr,head,size-1,output);
        OmniArray.OfRef.writeArray(arr,0,tail-1,output);
      }else{
        output.writeInt(size);
        OmniArray.OfRef.writeArray(arr,head,tail-1,output);
      }
    }else{
      output.writeInt(0);
    }
  }
  @Override public boolean equals(Object obj){
      //TODO
      return false;
  }
  public static class Checked<E> extends RefArrDeq<E>{
    private static final long serialVersionUID=1L;
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
    @Override public boolean equals(Object obj){
      //TODO
      return false;
    }
    @SuppressWarnings("unchecked")
    private void collapseheadHelper(Object[] arr,int tail,Predicate<? super E> filter,int modCount){
      if(tail==0){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.head=0;
        this.tail=0;
      }else{
        for(int head=0;;)
        {
          if(!filter.test((E)arr[head])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }else if(++head==tail){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.tail=tail;
            this.head=head;
            break;
          }
        }
        if(head!=0)
        {
          OmniArray.OfRef.nullifyRange(arr,head-1,0);
        }
      }
    }
    @SuppressWarnings("unchecked")
    private void fragmentedCollapseheadHelper(Object[] arr,int tail,Predicate<? super E> filter,int modCount){
      if(tail==0){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.head=0;
      }else{
        int head;
        for(head=0;;++head)
        {
          if(head==tail){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.head=head;
            break;
          }else if(!filter.test((E)arr[head])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }
        }
        if(head!=0)
        {
          OmniArray.OfRef.nullifyRange(arr,head-1,0);
        }
      }
    }
    @SuppressWarnings("unchecked")
    private void fragmentedCollapsehead(Object[] arr,int head,int tail,Predicate<? super E> filter,int modCount){
      int newhead;
      int bound;
      for(newhead=head+1,bound=arr.length-1;;++newhead){
        if(newhead>bound){
          fragmentedCollapseheadHelper(arr,tail,filter,modCount);
          break;
        }
        if(!filter.test((E)arr[newhead])){
          fragmentedCollapseBodyHelper(arr,newhead,tail,filter,modCount);
          break;
        }
      }
      OmniArray.OfRef.nullifyRange(arr,newhead-1,head);
    }
    @SuppressWarnings("unchecked")
    private void collapsehead(Object[] arr,int head,int tail,Predicate<? super E> filter,int modCount){
      int srcOffset;
      for(srcOffset=head;++srcOffset!=tail;){
        if(!filter.test((E)arr[srcOffset])){
          collapseBodyHelper(arr,srcOffset,tail,filter,modCount);
          OmniArray.OfRef.nullifyRange(arr,srcOffset-1,head);
          this.modCount=modCount+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      OmniArray.OfRef.nullifyRange(arr,srcOffset-1,head);
      this.head=srcOffset;
    }
    @SuppressWarnings("unchecked")
    private void collapsetailHelper(Object[] arr,int head,Predicate<? super E> filter,int modCount){
      int bound;  
      if((bound=arr.length-1)==head){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.tail=bound;
        this.head=head;
      }else{
        for(int tail=bound;;)
        {
          if(!filter.test((E)arr[tail])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }else if(--tail==head){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.tail=tail;
            this.head=head;
            break;
          }
        }
        if(tail!=bound)
        {
          OmniArray.OfRef.nullifyRange(arr,bound,tail+1);
        }
      }
    }
    @SuppressWarnings("unchecked")
    private void fragmentedCollapsetailHelper(Object[] arr,int head,Predicate<? super E> filter,int modCount){
      int bound;
      if((bound=arr.length-1)==head){
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.tail=head;
      }else{
        int tail;
        for(tail=bound;;--tail)
        {
          if(tail==head){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.tail=tail;
            break;
          }else if(!filter.test((E)arr[tail])){
            collapseBodyHelper(arr,head,tail,filter,modCount);
            break;
          }
        }
        if(tail!=bound)
        {
          OmniArray.OfRef.nullifyRange(arr,bound,tail+1);
        }
      }
    }
    @SuppressWarnings("unchecked")
    private void fragmentedCollapsetail(Object[] arr,int head,int tail,Predicate<? super E> filter,int modCount){
      int newtail;
      for(newtail=tail-1;;--newtail){
        if(newtail==-1){
          fragmentedCollapsetailHelper(arr,head,filter,modCount);
          break;
        }
        if(!filter.test((E)arr[newtail])){
          fragmentedCollapseBodyHelper(arr,head,newtail,filter,modCount);
          break;
        }
      }
      OmniArray.OfRef.nullifyRange(arr,tail,newtail+1);  
    }
    @SuppressWarnings("unchecked")
    private void collapsetail(Object[] arr,int head,int tail,Predicate<? super E> filter,int modCount){
      int srcOffset;
      for(srcOffset=tail;--srcOffset!=head;){
        if(!filter.test((E)arr[srcOffset])){
          collapseBodyHelper(arr,head,srcOffset,filter,modCount);
          OmniArray.OfRef.nullifyRange(arr,tail,srcOffset+1);
          this.modCount=modCount+1;
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.modCount=modCount+1;
      OmniArray.OfRef.nullifyRange(arr,tail,srcOffset+1);
      this.tail=srcOffset;
    }
    private static class BigCollapseData<E> extends CollapseData<E>{
      final long[] survivorSet;
      @SuppressWarnings("unchecked")
      BigCollapseData(Object[] arr,int srcOffset,int numLeft,Predicate<? super E> filter,int arrBound){
        assert srcOffset>0;
        assert numLeft>64;
        assert srcOffset+numLeft>=arrBound;
        assert srcOffset<=arrBound;
        var survivorSet=new long[((numLeft-1)>>6)+1];
        numLeft+=(srcOffset-arrBound);
        int wordOffset=-1,survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;
        long word=0L,marker=1L;
        outer: for(;;)
        {
          if(srcOffset!=arrBound)
          {
            for(;;){
              if(filter.test((E)arr[srcOffset])){
                currentRunLength=0;
              }else{
                word|=marker;
                if(currentRunLength==0){
                  currentRunBegin=srcOffset;
                }
                if(currentRunLength==biggestRunLength){
                  survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
                  survivorsAfterBiggestRun=0;
                  biggestRunBegin=currentRunBegin;
                  biggestRunLength=++currentRunLength;
                }else{
                  ++currentRunLength;
                  ++survivorsAfterBiggestRun;
                }
              }
              if(++srcOffset==arrBound)
              {
                if(numLeft==0)
                {
                  survivorSet[++wordOffset]=word;
                  break outer;
                }
                if((marker<<=1)==0L)
                {
                  survivorSet[++wordOffset]=word;
                  word=0L;
                  marker=1L;
                }
                break;
              }
              if((marker<<=1)==0L){
                survivorSet[++wordOffset]=word;
                word=0L;
                marker=1L;
              }
            }
          }
          for(srcOffset=0;;)
          {
            if(filter.test((E)arr[srcOffset])){
              currentRunLength=0;
            }else{
              word|=marker;
              if(currentRunLength==0){
                currentRunBegin=srcOffset;
              }
              if(currentRunLength==biggestRunLength){
                survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
                survivorsAfterBiggestRun=0;
                biggestRunBegin=currentRunBegin;
                biggestRunLength=++currentRunLength;
              }else{
                ++currentRunLength;
                ++survivorsAfterBiggestRun;
              }
            }
            if(++srcOffset==numLeft)
            {
              survivorSet[++wordOffset]=word;
              break outer;
            }
            if((marker<<=1)==0L){
              survivorSet[++wordOffset]=word;
              word=0L;
              marker=1L;
            }
          }
        }
        this.biggestRunBegin=biggestRunBegin;
        this.biggestRunLength=biggestRunLength;
        this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
        this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
        this.survivorSet=survivorSet;
      }
      @SuppressWarnings("unchecked")
      BigCollapseData(Object[] arr,int srcOffset,int numLeft,Predicate<? super E> filter){
        assert srcOffset>=0;
        assert numLeft>64;
        var survivorSet=new long[((numLeft-1)>>6)+1];
        numLeft+=srcOffset;
        for(int wordOffset=-1,survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;;){
          long word=0L,marker=1L;
          do{
            if(filter.test((E)arr[srcOffset])){
              currentRunLength=0;
            }else{
              word|=marker;
              if(currentRunLength==0){
                currentRunBegin=srcOffset;
              }
              if(currentRunLength==biggestRunLength){
                survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
                survivorsAfterBiggestRun=0;
                biggestRunBegin=currentRunBegin;
                biggestRunLength=++currentRunLength;
              }else{
                ++currentRunLength;
                ++survivorsAfterBiggestRun;
              }
            }
            if(++srcOffset==numLeft){
              survivorSet[++wordOffset]=word;
              this.biggestRunBegin=biggestRunBegin;
              this.biggestRunLength=biggestRunLength;
              this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
              this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
              this.survivorSet=survivorSet;
              return;
            }
          }while((marker<<=1)!=0L);
          survivorSet[++wordOffset]=word;
        }
      }
      @Override void fragmentedCollapseBiggestRunInHead(int head,RefArrDeq<E> deq,int tail){
        int overflow;
        int biggestRunEnd;
        int arrLength;
        int biggestRunBegin;
        Object[] arr;
        int survivorsBeforeAndAfter=this.survivorsAfterBiggestRun;
        if((overflow=(biggestRunEnd=(biggestRunBegin=this.biggestRunBegin)+this.biggestRunLength)-(arrLength=(arr=deq.arr).length))>=0){
          if(survivorsBeforeAndAfter!=0){
            nonfragmentedPullSurvivorsDown(arr,overflow,biggestRunEnd-head,overflow+=survivorsBeforeAndAfter);
          }
          if(overflow!=tail){
            arr[overflow]=arr[tail];
            OmniArray.OfRef.nullifyRange(arr,tail,overflow+1);
          }
        }else{
          int newTail;
          switch(Integer.signum(overflow=(newTail=biggestRunEnd+survivorsBeforeAndAfter)-arrLength)){
            case -1:
              if(survivorsBeforeAndAfter!=0){
                if(tail==0){
                  nonfragmentedPullSurvivorsDown(arr,biggestRunEnd,biggestRunEnd-head,newTail);
                }else{
                  fragmentedPullSurvivorsDownToNonFragmented(arr,biggestRunEnd,biggestRunEnd-head,newTail);
                }
              }
              if(newTail!=--arrLength){
                OmniArray.OfRef.nullifyRange(arr,arrLength,newTail+1);
              }
              arr[newTail]=arr[tail];
              overflow=newTail;
              newTail=0;
              break;
            case 0:
              fragmentedPullSurvivorsDownToNonFragmented(arr,biggestRunEnd,biggestRunEnd-head,newTail);             
              arr[0]=arr[tail];
              newTail=1;
              break;
            default:
              fragmentedPullSurvivorsDown(arr,biggestRunEnd,biggestRunEnd-head,overflow);
              arr[overflow]=arr[tail];
              newTail=overflow+1;
          }
          OmniArray.OfRef.nullifyRange(arr,tail,newTail);
        }
        deq.tail=overflow;
        if((survivorsBeforeAndAfter=this.survivorsBeforeBiggestRun)!=0){
          nonfragmentedPullSurvivorsUp(arr,biggestRunBegin,(biggestRunBegin-head)-3,biggestRunBegin-=survivorsBeforeAndAfter);
        }
        if(--biggestRunBegin!=head){
          arr[biggestRunBegin]=arr[head];
          OmniArray.OfRef.nullifyRange(arr,biggestRunBegin-1,head);
        }
        deq.head=biggestRunBegin;
      }
      @Override void fragmentedCollapseBiggestRunInTail(int head,RefArrDeq<E> deq,int tail){
        //TODO
        throw new UnsupportedOperationException();
      }
      private void fragmentedPullSurvivorsDownToNonFragmented(Object[] arr,int dstOffset,int numToSkip,int dstBound){
        int wordOffset;
        long[] survivorSet;
        long word=(survivorSet=this.survivorSet)[wordOffset=numToSkip>>6]>>>numToSkip;
        int s=dstOffset+1;
        int srcOffset=s+(((-numToSkip)-1)&63)+1;
        int arrLength=arr.length;
        int numToRetain;
        int srcOverflow;
        for(;;)
        {
          if((numToSkip=Long.numberOfTrailingZeros(word))==64)
          {
            s=srcOffset;
            srcOffset+=64;
            word=survivorSet[++wordOffset];
            continue;
          }
          numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip));
          if((srcOverflow=(s+=numToSkip)-arrLength)>=0)
          {
            ArrCopy.uncheckedCopy(arr,srcOverflow,arr,dstOffset,numToRetain);
            srcOverflow+=numToRetain;
            break;
          }
          int srcBound;
          switch(Integer.signum(srcOverflow=(srcBound=s+numToRetain)-arrLength))
          {
            default:
              //no overflow detected yet
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,s,numToRetain);
              if((dstOffset+=numToRetain)==dstBound)
              {
                return;
              }
              s=srcBound;
              word>>>=numToRetain;
              continue;
            case 1:
              //the source bound overflowed, so wrap around
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,s,srcBound=numToRetain-srcOverflow);
              ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+srcBound,srcOverflow);
              break;
            case 0:
              //the source bound goes right up to arrLength, so the next skip will overflow
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,s,numToRetain);
          }
          break;
        }
        if((dstOffset+=numToRetain)!=dstBound)
        {
          for(srcOffset-=arrLength,word>>>=numToRetain;;)
          {
            if((numToSkip=Long.numberOfTrailingZeros(word))==64)
            {
              srcOverflow=srcOffset;
              srcOffset+=64;
              word=survivorSet[++wordOffset];
              continue;
            }
            ArrCopy.uncheckedCopy(arr,srcOverflow+=numToSkip,arr,dstOffset,numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip)));
            if((dstOffset+=numToRetain)==dstBound)
            {
              return;
            }
            srcOverflow+=numToRetain;
            word>>>=numToRetain;
          }
        }
      }
      private void nonfragmentedPullSurvivorsUp(Object[] arr,int dstOffset,int numToSkip,int dstBound){
        int wordOffset;
        long[] survivorSet;
        long word=(survivorSet=this.survivorSet)[wordOffset=numToSkip>>6]<<(-(numToSkip+1));
        for(int s,srcOffset=(s=dstOffset-1)-(((numToSkip)&63)+1);;)
        {
          while((numToSkip=Long.numberOfLeadingZeros(word))!=64)
          {
            ArrCopy.uncheckedCopy(arr,s-=(numToSkip+(numToSkip=Long.numberOfLeadingZeros(~(word<<=numToSkip)))),arr,dstOffset-=numToSkip,numToSkip);
            if(dstOffset==dstBound)
            {
              return;
            }
            //else if(numToSkip==64)
            //{
            //  break;
            //}
            word<<=numToSkip;
          }
          word=survivorSet[--wordOffset];
          s=srcOffset;
          srcOffset-=64;
        }
      }
      private void fragmentedPullSurvivorsDown(Object[] arr,int dstOffset,int numToSkip,int dstBound){
        //TODO
        throw new UnsupportedOperationException();
      }
      private void nonfragmentedPullSurvivorsDown(Object[] arr,int dstOffset,int numToSkip,int dstBound){
        int wordOffset;
        long[] survivorSet;
        long word=(survivorSet=this.survivorSet)[wordOffset=numToSkip>>6]>>>numToSkip;
        for(int s=dstOffset+1,srcOffset=s+(((-numToSkip)-1)&63)+1;;)
        {
          while((numToSkip=Long.numberOfTrailingZeros(word))!=64)
          {
            ArrCopy.uncheckedSelfCopy(arr,dstOffset,s+=numToSkip,numToSkip=Long.numberOfTrailingZeros(~(word>>>=numToSkip)));
            if((dstOffset+=numToSkip)==dstBound)
            {
              return;
            }
            else if(numToSkip==64)
            {
              break;
            }
            s+=numToSkip;
            word>>>=numToSkip;
          }
          word=survivorSet[++wordOffset];
          s=srcOffset;
          srcOffset+=64;
        }
      }
      @Override void nonfragmentedCollapse(int head,RefArrDeq<E> deq,int tail){
        final var arr=deq.arr;
        int biggestRunBegin;
        int biggestRunEnd=(biggestRunBegin=this.biggestRunBegin)+this.biggestRunLength;
        int survivorsBeforeAndAfter;
        if((survivorsBeforeAndAfter=this.survivorsAfterBiggestRun)!=0){
          nonfragmentedPullSurvivorsDown(arr,biggestRunEnd,biggestRunEnd-head,biggestRunEnd+=survivorsBeforeAndAfter);
        }
        arr[biggestRunEnd]=arr[tail];
        if(biggestRunEnd!=tail){
          OmniArray.OfRef.nullifyRange(arr,tail,biggestRunEnd+1);
        }
        deq.tail=biggestRunEnd;
        if((survivorsBeforeAndAfter=this.survivorsBeforeBiggestRun)!=0){
          nonfragmentedPullSurvivorsUp(arr,biggestRunBegin,(biggestRunBegin-head)-3,biggestRunBegin-=survivorsBeforeAndAfter);
        }
        arr[--biggestRunBegin]=arr[head];
        if(biggestRunBegin!=head){
          OmniArray.OfRef.nullifyRange(arr,biggestRunBegin-1,head);
        }
        deq.head=biggestRunBegin;
      }
    }
    private static class SmallCollapseData<E> extends CollapseData<E>{
      private static void fragmentedPullSurvivorsDownHelper(Object[] arr,int dstOffset,int srcOffset,long word){
        for(int arrLength=arr.length;;){
          //check to see if a dst bound overflow occurred
          int dstOverflow,numToRetain,dstBound;
          switch(Integer.signum(dstOverflow=(dstBound=dstOffset+(numToRetain=Long.numberOfTrailingZeros(~(word))))-arrLength)){
            default:
              //no dst bound overflow detected yet
              ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numToRetain);
              dstOffset=dstBound;
              srcOffset+=(numToRetain+(dstBound=Long.numberOfTrailingZeros(word>>>=numToRetain)));
              word>>>=dstBound;
              continue;
            case 0:
              //the dst bound goes right up to the array bound. The next copy will wrap
              ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numToRetain);
              dstBound=Long.numberOfTrailingZeros(word>>>=numToRetain);
              break;
            case 1:
              //dst bound overflow detected
              ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,dstOffset=numToRetain-dstOverflow);
              ArrCopy.uncheckedSelfCopy(arr,0,srcOffset+dstOffset,dstOverflow);
              if((dstBound=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
                return;
              }
          }
          finalizeNonfragmentedPullDown(arr,dstOverflow,srcOffset+numToRetain+dstBound,word>>>dstBound);
          return;
        }
      }
      private static void fragmentedPullSurvivorsUpHelper(Object[] arr,int dstOffset,int srcOffset,long word)
      {
        for(;;)
        {
          //check to see if dst bound overflow occurred
          int lead0s,dstBound;
          switch(Integer.signum(dstBound=dstOffset-(lead0s=Long.numberOfLeadingZeros(~word))))
          {
            default:
              //no dst bound overflow detected yet
               ArrCopy.uncheckedCopy(arr,srcOffset-=lead0s,arr,dstOffset=dstBound,lead0s);
               srcOffset-=(lead0s=Long.numberOfLeadingZeros(word<<=lead0s));
               word<<=lead0s;
               continue;
            case 0:
              //the dst bound goes right down to zero. The next copy will wrap
              ArrCopy.uncheckedCopy(arr,srcOffset-=lead0s,arr,0,lead0s);
              lead0s=Long.numberOfLeadingZeros(word<<=lead0s);
              dstOffset=arr.length;
              break;
            case -1:
              //dst bound overflow detected
              ArrCopy.uncheckedCopy(arr,srcOffset-dstOffset,arr,0,dstOffset);
              ArrCopy.uncheckedCopy(arr,srcOffset-=lead0s,arr,dstOffset=arr.length+dstBound,-dstBound);
              if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
              {
                return;
              }
          }
          finalizeNonfragmentedPullUp(arr,dstOffset,srcOffset-lead0s,word<<lead0s);
          return;
        }
      }
      private static void fragmentedPullSurvivorsDown(Object[] arr,int dstOffset,long word)
      {
        int numToSkip;
        for(int arrLength=arr.length,srcOffset=dstOffset+1+(numToSkip=Long.numberOfTrailingZeros(word));;)
        {
          int srcOverflow;
          if((srcOverflow=srcOffset-arrLength)>=0)
          {
            //the source offset overflowed on a skip
            fragmentedPullSurvivorsDownHelper(arr,dstOffset,srcOverflow,word>>>numToSkip);
            return;
          }
          int srcBound,numToRetain;
          switch(Integer.signum(srcOverflow=(srcBound=srcOffset+(numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip))))-arrLength))
          {
            default:
              //no overflow detected yet
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
              dstOffset+=numToRetain;
              srcOffset=srcBound+(numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain));
              continue;
            case 1:
              //source bound overflowed, check to see if dst overflowed
              int dstOverflow;
              switch(Integer.signum(dstOverflow=(srcBound=dstOffset+numToRetain)-arrLength))
              {
                default:
                  //no dst overflow detected
                  ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,dstOverflow=numToRetain-srcOverflow);
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+dstOverflow,srcOverflow);
                  fragmentedPullSurvivorsDownHelper(arr,srcBound,srcOverflow+(numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain)),word>>>numToSkip);
                  return;
                case 1:
                  //dst overflow detected
                  ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,srcBound=numToRetain-srcOverflow);
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+srcBound,srcBound=srcOverflow-dstOverflow);
                  ArrCopy.uncheckedSelfCopy(arr,0,srcBound,dstOverflow);
                  if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64)
                  {
                    return;
                  }
                  break;
                case 0:
                  //dst bound goes right up to the end, next copy will overflow
                  ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,srcBound=numToRetain-srcOverflow);
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+srcBound,srcOverflow);
                  numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain);
              }
              finalizeNonfragmentedPullDown(arr,dstOverflow,srcOverflow+numToSkip,word>>>numToSkip);
              return;
            case 0:
              //the source bound goes right up to arrLength, so the next skip will overflow
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
              fragmentedPullSurvivorsDownHelper(arr,dstOffset+numToRetain,numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain),word>>>numToSkip);
              return;
          }
        }
      }
      private static void fragmentedPullSurvivorsUp(Object[] arr,int dstOffset,long word)
      {
        int lead0s;
        for(int srcOffset=dstOffset-1-(lead0s=Long.numberOfLeadingZeros(word));;)
        {
          if(srcOffset<=0)
          {
            //the source offset overflowed on a skip
            fragmentedPullSurvivorsUpHelper(arr,dstOffset,srcOffset+arr.length,word<<lead0s);
            return;
          }
          int srcBound;
          switch(Integer.signum(srcBound=srcOffset-(lead0s=Long.numberOfLeadingZeros(~(word<<=lead0s)))))
          {
            default:
              //no overflow detected yet
              ArrCopy.uncheckedCopy(arr,srcBound,arr,dstOffset-=lead0s,lead0s);
              srcOffset=srcBound-(lead0s=Long.numberOfLeadingZeros(word<<=lead0s));
              continue;
            case -1:
              //the source bound overflowed, check to see if dst overflowed
              int dstBound;
              switch(Integer.signum(dstBound=dstOffset-lead0s))
              {
                default:
                  //no dst overflow detected
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-srcOffset,srcOffset);
                  ArrCopy.uncheckedCopy(arr,srcOffset=arr.length+srcBound,arr,dstBound,-srcBound);
                  fragmentedPullSurvivorsUpHelper(arr,dstBound,srcOffset-(lead0s=Long.numberOfLeadingZeros(word<<=lead0s)),word<<lead0s);
                  return;
                case -1:
                  //dst overflow detected
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-srcOffset,srcOffset);
                  ArrCopy.uncheckedCopy(arr,srcOffset=(dstOffset=arr.length)-(srcBound=dstBound-srcBound),arr,0,srcBound);
                  ArrCopy.uncheckedCopy(arr,srcOffset+=dstBound,arr,dstOffset+=dstBound,-dstBound);
                  if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
                  {
                    return;
                  }
                  break;
                case 0:
                  //the dst bound goes right to zero, so the next copy will wrap
                  ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-srcOffset,srcOffset);
                  ArrCopy.uncheckedCopy(arr,srcOffset=(dstOffset=arr.length)+srcBound,arr,0,-srcBound);
                  lead0s=Long.numberOfLeadingZeros(word<<=lead0s);
                  break;
              }
              finalizeNonfragmentedPullUp(arr,dstOffset,srcOffset-=lead0s,word<<lead0s);
              return;
            case 0:
              //the source bound goes right to zero, so the next skip will overflow
              ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-=lead0s,lead0s);
              fragmentedPullSurvivorsUpHelper(arr,dstOffset,arr.length-(lead0s=Long.numberOfLeadingZeros(word<<=lead0s)),word<<lead0s);
              return;
          }
        }
      }
      private static void fragmentedPullSurvivorsDownToNonFragmented(Object[] arr,int dstOffset,long word){
        int numToRetain,srcOverflow,numToSkip;
        for(int arrLength=arr.length,srcOffset=dstOffset+1+(numToSkip=Long.numberOfTrailingZeros(word));;)
        {
          numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip));
          if((srcOverflow=srcOffset-arrLength)>=0)
          {
            //the source offset overflowed on a skip
            ArrCopy.uncheckedCopy(arr,srcOverflow,arr,dstOffset,numToRetain);
            srcOverflow+=numToRetain;
            break;
          }
          int srcBound;
          switch(Integer.signum(srcOverflow=(srcBound=srcOffset+numToRetain)-arrLength))
          {
            default:
              //no overflow detected yet
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
              if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64)
              {
                return;
              }
              dstOffset+=numToRetain;
              srcOffset=srcBound+numToSkip;
              continue;
            case 1:
              //the source bound overflowed, so wrap around
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,srcBound=numToRetain-srcOverflow);
              ArrCopy.uncheckedCopy(arr,0,arr,dstOffset+srcBound,srcOverflow);
              break;
            case 0:
              //the source bound goes right up to arrLength, so the next skip will overflow
              ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain);
          }
          break;
        }
        if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))!=64)
        {
          finalizeFragmentedPullDown(arr,dstOffset+numToRetain,srcOverflow+numToSkip,word>>>numToSkip);
        }
      }
      private static void fragmentedPullSurvivorsUpToNonFragmented(Object[] arr,int dstOffset,long word)
      {
        int lead0s,srcOffset;
        for(srcOffset=dstOffset-1-(lead0s=Long.numberOfLeadingZeros(word));;)
        {
          lead0s=Long.numberOfLeadingZeros(~(word<<=lead0s));
          if(srcOffset<=0)
          {
            //the source offset overflowed on a kip
            ArrCopy.uncheckedCopy(arr,srcOffset+=(arr.length-lead0s),arr,dstOffset-=lead0s,lead0s);
            break;
          }
          int srcBound;
          switch(Integer.signum(srcBound=srcOffset-lead0s))
          {
            default:
              //no overflow detected yet
              ArrCopy.uncheckedCopy(arr,srcBound,arr,dstOffset-=lead0s,lead0s);
              if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
              {
                return;
              }
              srcOffset=srcBound-lead0s;
              continue;
            case -1:
              //the source bound overflowed, so wrap around
              ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-srcOffset,srcOffset);
              ArrCopy.uncheckedCopy(arr,srcOffset=arr.length+srcBound,arr,dstOffset-=lead0s,-srcBound);
              break;
            case 0:
              //the source bound goes right to zero, so the next skip will overflow
              ArrCopy.uncheckedCopy(arr,0,arr,dstOffset-=lead0s,lead0s);
              srcOffset=arr.length;
          }
          break;
        }
        if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))!=64)
        {
          finalizeFragmentedPullUp(arr,dstOffset,srcOffset-lead0s,word<<lead0s);
        }
      }
      private static void finalizeNonfragmentedPullUp(Object[] arr,int dstOffset,int srcOffset,long word){
        for(;;){
          int lead0s;
          ArrCopy.uncheckedCopy(arr,srcOffset-=(lead0s=Long.numberOfLeadingZeros(~word)),arr,dstOffset-=lead0s,lead0s);
          if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
          {
            return;
          }
          srcOffset-=lead0s;
          word<<=lead0s;
        }
      }
      private static void finalizeNonfragmentedPullDown(Object[] arr,int dstOffset,int srcOffset,long word){
        for(;;){
          int numToRetain;
          ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain=Long.numberOfTrailingZeros(~word));
          int numToSkip;
          if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
            return;
          }
          srcOffset+=(numToSkip+numToRetain);
          dstOffset+=numToRetain;
          word>>>=numToSkip;
        }
      }
      private static void finalizeFragmentedPullDown(Object[] arr,int dstOffset,int srcOffset,long word){
        for(;;){
          int numToRetain;
          ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numToRetain=Long.numberOfTrailingZeros(~word));
          int numToSkip;
          if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
            return;
          }
          srcOffset+=(numToSkip+numToRetain);
          dstOffset+=numToRetain;
          word>>>=numToSkip;
        }
      }
      private static void finalizeFragmentedPullUp(Object[] arr,int dstOffset,int srcOffset,long word)
      {
        for(;;)
        {
          int lead0s;
          ArrCopy.uncheckedCopy(arr,srcOffset-=(lead0s=Long.numberOfLeadingZeros(~word)),arr,dstOffset-=lead0s,lead0s);
          if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
          {
            return;
          }
          srcOffset-=lead0s;
          word<<=lead0s;
        }
      }
      private static void nonfragmentedPullSurvivorsDown(Object[] arr,int dstOffset,long word){
        int numToSkip;
        for(int srcOffset=dstOffset+1+(numToSkip=Long.numberOfTrailingZeros(word));;)
        {
          int numToRetain;
          ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset,numToRetain=Long.numberOfTrailingZeros(~(word>>>=numToSkip)));
          if((numToSkip=Long.numberOfTrailingZeros(word>>>=numToRetain))==64){
            return;
          }
          dstOffset+=numToRetain;
          srcOffset+=(numToSkip+numToRetain);
        }
      }
      private static void nonfragmentedPullSurvivorsUp(Object[] arr,int dstOffset,long word)
      {
        int lead0s;
        if((lead0s=Long.numberOfLeadingZeros(word))!=64)
        {
          for(int srcOffset=dstOffset-1-lead0s;;)
          {
            ArrCopy.uncheckedCopy(arr,srcOffset-=(lead0s=Long.numberOfLeadingZeros(~(word<<=lead0s))),arr,dstOffset-=lead0s,lead0s);
            if((lead0s=Long.numberOfLeadingZeros(word<<=lead0s))==64)
            {
              return;
            }
            srcOffset-=lead0s;
          }
        }
      }
      final long survivorWord;
      @SuppressWarnings("unchecked")
      SmallCollapseData(Object[] arr,int head,int tail,Predicate<? super E> filter){
        assert head>=0;
        assert tail+1-head<=64;
        int survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;
        for(long word=0L,marker=1L;;++head,marker<<=1){
          if(filter.test((E)arr[head])){
            currentRunLength=0;
          }else{
            word|=marker;
            if(currentRunLength==0){
              currentRunBegin=head;
            }
            if(currentRunLength==biggestRunLength){
              survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
              survivorsAfterBiggestRun=0;
              biggestRunBegin=currentRunBegin;
              biggestRunLength=++currentRunLength;
            }else{
              ++currentRunLength;
              ++survivorsAfterBiggestRun;
            }
          }
          if(head==tail){
            this.survivorWord=word;
            this.biggestRunBegin=biggestRunBegin;
            this.biggestRunLength=biggestRunLength;
            this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
            this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
            return;
          }
        }
      }
      @SuppressWarnings("unchecked")
      SmallCollapseData(Object[] arr,int head,int tail,Predicate<? super E> filter,int arrBound){
        assert tail<head;
        assert tail>=0;
        assert head<=arrBound;
        assert tail-head+arrBound<=64;
        int survivorsBeforeBiggestRun=0,survivorsAfterBiggestRun=0,currentRunLength=0,currentRunBegin=0,biggestRunLength=0,biggestRunBegin=0;
        for(long word=0L,marker=1L;;++head,marker<<=1){
          if(head==arrBound){
            for(head=0;;++head,marker<<=1){
              if(head==tail){
                this.survivorWord=word;
                this.biggestRunBegin=biggestRunBegin;
                this.biggestRunLength=biggestRunLength;
                this.survivorsBeforeBiggestRun=survivorsBeforeBiggestRun;
                this.survivorsAfterBiggestRun=survivorsAfterBiggestRun;
                return;
              }
              if(filter.test((E)arr[head])){
                currentRunLength=0;
              }else{
                word|=marker;
                if(currentRunLength==0){
                  currentRunBegin=head;
                }
                if(currentRunLength==biggestRunLength){
                  survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
                  survivorsAfterBiggestRun=0;
                  biggestRunBegin=currentRunBegin;
                  biggestRunLength=++currentRunLength;
                }else{
                  ++currentRunLength;
                  ++survivorsAfterBiggestRun;
                }
              }
            }
          }
          if(filter.test((E)arr[head])){
            currentRunLength=0;
          }else{
            word|=marker;
            if(currentRunLength==0){
              currentRunBegin=head;
            }
            if(currentRunLength==biggestRunLength){
              survivorsBeforeBiggestRun+=survivorsAfterBiggestRun;
              survivorsAfterBiggestRun=0;
              biggestRunBegin=currentRunBegin;
              biggestRunLength=++currentRunLength;
            }else{
              ++currentRunLength;
              ++survivorsAfterBiggestRun;
            }
          }
        }
      }
      @Override void fragmentedCollapseBiggestRunInTail(int head,RefArrDeq<E> deq,int tail){
        int biggestRunBegin;
        int biggestRunEnd=(biggestRunBegin=this.biggestRunBegin)+this.biggestRunLength;
        Object[] arr;
        int arrLength;
        int overflow=(arrLength=(arr=deq.arr).length)-head;
        int survivorsBeforeAndAfter;
        if((survivorsBeforeAndAfter=this.survivorsAfterBiggestRun)!=0)
        {
          nonfragmentedPullSurvivorsDown(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd+overflow));
        }
        if((biggestRunEnd+=survivorsBeforeAndAfter)!=tail)
        {
          arr[biggestRunEnd]=arr[tail];
          OmniArray.OfRef.nullifyRange(arr,tail,biggestRunEnd+1);
        }
        deq.tail=biggestRunEnd;
        switch(Integer.signum(biggestRunEnd=biggestRunBegin-(survivorsBeforeAndAfter=this.survivorsBeforeBiggestRun)))
        {
          case 1:
            //no overflow detected
            if(survivorsBeforeAndAfter!=0)
            {
              if(overflow==1)
              {
                nonfragmentedPullSurvivorsUp(arr,biggestRunBegin,this.survivorWord<<(1-biggestRunBegin));
              }
              else
              {
                fragmentedPullSurvivorsUpToNonFragmented(arr,biggestRunBegin,this.survivorWord<<(2-(biggestRunBegin+overflow)));
              }
            }
            if(biggestRunEnd!=1)
            {
              OmniArray.OfRef.nullifyRange(arr,biggestRunEnd-2,0);
            }
            arr[--biggestRunEnd]=arr[head];
            --arrLength;
            break;
          case 0:
            if(survivorsBeforeAndAfter!=0)
            {
              fragmentedPullSurvivorsUpToNonFragmented(arr,biggestRunBegin,this.survivorWord<<(2-(biggestRunBegin+overflow)));
            }
            arr[biggestRunEnd=--arrLength]=arr[head];
            --arrLength;
            break;
          default:
            if(biggestRunBegin==0)
            {
              nonfragmentedPullSurvivorsUp(arr,arrLength,this.survivorWord<<(2-overflow));
            }
            else
            {
              fragmentedPullSurvivorsUp(arr,biggestRunBegin,this.survivorWord<<(2-(biggestRunBegin+overflow)));
            }
            arr[biggestRunEnd+=(arrLength-1)]=arr[head];
            arrLength=biggestRunEnd-1;      
        }
        deq.head=biggestRunEnd;
        if(head<=arrLength)
        {
          OmniArray.OfRef.nullifyRange(arr,arrLength,head);
        }
      }
      @Override void fragmentedCollapseBiggestRunInHead(int head,final RefArrDeq<E> deq,int tail){
        int overflow;
        int biggestRunEnd;
        int arrLength;
        int biggestRunBegin;
        Object[] arr;
        int survivorsBeforeAndAfter=this.survivorsAfterBiggestRun;
        if((overflow=(biggestRunEnd=(biggestRunBegin=this.biggestRunBegin)+this.biggestRunLength)-(arrLength=(arr=deq.arr).length))>=0){
          if(survivorsBeforeAndAfter!=0){
            nonfragmentedPullSurvivorsDown(arr,overflow,this.survivorWord>>>(biggestRunEnd-head));
          }
          if((overflow+=survivorsBeforeAndAfter)!=tail){
            arr[overflow]=arr[tail];
            OmniArray.OfRef.nullifyRange(arr,tail,overflow+1);
          }
        }else{
          int newTail;
          switch(Integer.signum(overflow=(newTail=biggestRunEnd+survivorsBeforeAndAfter)-arrLength)){
            case -1:
              if(survivorsBeforeAndAfter!=0){
                if(tail==0){
                  nonfragmentedPullSurvivorsDown(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd-head));
                }else{
                  fragmentedPullSurvivorsDownToNonFragmented(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd-head));
                }
              }
              if(newTail!=--arrLength){
                OmniArray.OfRef.nullifyRange(arr,arrLength,newTail+1);
              }
              arr[newTail]=arr[tail];
              overflow=newTail;
              newTail=0;
              break;
            case 0:
              fragmentedPullSurvivorsDownToNonFragmented(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd-head));             
              arr[0]=arr[tail];
              newTail=1;
              break;
            default:
              fragmentedPullSurvivorsDown(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd-head));
              arr[overflow]=arr[tail];
              newTail=overflow+1;
          }
          OmniArray.OfRef.nullifyRange(arr,tail,newTail);
        }
        deq.tail=overflow;
        if((survivorsBeforeAndAfter=this.survivorsBeforeBiggestRun)!=0){
          nonfragmentedPullSurvivorsUp(arr,biggestRunBegin,this.survivorWord<<(2-(biggestRunBegin-head)));
        }
        if((biggestRunBegin-=(survivorsBeforeAndAfter+1))!=head){
          arr[biggestRunBegin]=arr[head];
          OmniArray.OfRef.nullifyRange(arr,biggestRunBegin-1,head);
        }
        deq.head=biggestRunBegin;
      }
      @Override void nonfragmentedCollapse(int head,RefArrDeq<E> deq,int tail)
      {
        final var arr=deq.arr;
        int biggestRunBegin;
        int biggestRunEnd=(biggestRunBegin=this.biggestRunBegin)+this.biggestRunLength;
        int survivorsBeforeAndAfter;
        if((survivorsBeforeAndAfter=this.survivorsAfterBiggestRun)!=0){
          nonfragmentedPullSurvivorsDown(arr,biggestRunEnd,this.survivorWord>>>(biggestRunEnd-head));
        }
        arr[biggestRunEnd+=survivorsBeforeAndAfter]=arr[tail];
        if(biggestRunEnd!=tail){
          OmniArray.OfRef.nullifyRange(arr,tail,biggestRunEnd+1);
        }
        deq.tail=biggestRunEnd;
        if((survivorsBeforeAndAfter=this.survivorsBeforeBiggestRun)!=0){
          nonfragmentedPullSurvivorsUp(arr,biggestRunBegin,this.survivorWord<<(2-(biggestRunBegin-head)));
        }
        arr[biggestRunBegin-=(survivorsBeforeAndAfter+1)]=arr[head];
        if(biggestRunBegin!=head){
          OmniArray.OfRef.nullifyRange(arr,biggestRunBegin-1,head);
        }
        deq.head=biggestRunBegin;
      }
    }
    private static abstract class CollapseData<E>{
      int survivorsBeforeBiggestRun;
      int survivorsAfterBiggestRun;
      int biggestRunBegin;
      int biggestRunLength;
      abstract void fragmentedCollapseBiggestRunInHead(int head,RefArrDeq<E> deq,int tail);
      abstract void fragmentedCollapseBiggestRunInTail(int head,RefArrDeq<E> deq,int tail);
      abstract void nonfragmentedCollapse(int head,RefArrDeq<E> deq,int tail);
      private int getNumSurvivors(){
        return this.survivorsBeforeBiggestRun+this.biggestRunLength+this.survivorsAfterBiggestRun;
      }
    }
    private void collapseBodyHelper(Object[] arr,int head,int tail,Predicate<? super E> filter,int modCount){
      int numLeft,srcOffset;
      if((numLeft=tail-(srcOffset=head+1))!=0){
        CollapseData<E> collapseData;
        if(numLeft>64){
          collapseData=new BigCollapseData<E>(arr,srcOffset,numLeft,filter);
        }else{
          collapseData=new SmallCollapseData<E>(arr,srcOffset,tail-1,filter);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        int numSurvivors;
        if((numSurvivors=collapseData.getNumSurvivors())!=numLeft){
          if(numSurvivors==0){
            nonfragmentedCollapseAll(head,tail);
          }else{
            collapseData.nonfragmentedCollapse(head,this,tail);
          }
          return;
        }
      }else{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.head=head;
      this.tail=tail;
    }
    private void fragmentedCollapseAll(int head,int tail){
      this.tail=tail;
      final var arr=this.arr;
      if(tail==0){
        arr[tail=arr.length-1]=arr[head];
        this.head=tail;
        OmniArray.OfRef.nullifyRange(arr,tail-1,head);
      }else{
        arr[--tail]=arr[head];
        this.head=tail;
        OmniArray.OfRef.nullifyRange(arr,arr.length-1,head);
        if(tail!=0){
          OmniArray.OfRef.nullifyRange(arr,tail-1,0);
        }
      }
    }
    private void nonfragmentedCollapseAll(int head,int tail){
      this.head=head;
      this.tail=++head;
      Object[] arr;
      (arr=this.arr)[head]=arr[tail];
      OmniArray.OfRef.nullifyRange(arr,tail,head+1);
    }
    private void fragmentedCollapseBodyHelper(Object[] arr,int head,int tail,Predicate<? super E> filter,int modCount){
      int numLeft,arrLength,srcOffset;
      if((numLeft=tail-(srcOffset=head+1)+(arrLength=arr.length))!=0){
        CollapseData<E> collapseData;
        if(numLeft>64){
          collapseData=new BigCollapseData<E>(arr,srcOffset,numLeft,filter,arrLength);
        }else{
          collapseData=new SmallCollapseData<E>(arr,srcOffset,tail,filter,arrLength);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        int numSurvivors;
        if((numSurvivors=collapseData.getNumSurvivors())!=numLeft){
          if(numSurvivors==0){
            fragmentedCollapseAll(head,tail);
          }else{
            if(collapseData.biggestRunBegin>head){
              collapseData.fragmentedCollapseBiggestRunInHead(head,this,tail);
            }else{
              collapseData.fragmentedCollapseBiggestRunInTail(head,this,tail);
            }
          }
          return;
        }
      }else{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.head=head;
      this.tail=tail;
    }
    private boolean collapseBody(Object[] arr,int head,int tail,Predicate<? super E> filter,int modCount){
      int numLeft,srcOffset;
      if((numLeft=tail-(srcOffset=head+1))!=0){
        CollapseData<E> collapseData;
        if(numLeft>64){
          collapseData=new BigCollapseData<E>(arr,srcOffset,numLeft,filter);
        }else{
          collapseData=new SmallCollapseData<E>(arr,srcOffset,tail-1,filter);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        int numSurvivors;
        if((numSurvivors=collapseData.getNumSurvivors())!=numLeft){
          if(numSurvivors==0){
            nonfragmentedCollapseAll(head,tail);
          }else{
            collapseData.nonfragmentedCollapse(head,this,tail);
          }
          this.modCount=modCount+1;
          return true;
        }
      }else{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      return false;
    }
    private boolean fragmentedCollapseBody(Object[] arr,int head,int tail,Predicate<? super E> filter,int modCount){
      int numLeft,arrLength,srcOffset;
      if((numLeft=tail-(srcOffset=head+1)+(arrLength=arr.length))!=0){
        CollapseData<E> collapseData;
        if(numLeft>64){
          collapseData=new BigCollapseData<E>(arr,srcOffset,numLeft,filter,arrLength);
        }else{
          collapseData=new SmallCollapseData<E>(arr,srcOffset,tail,filter,arrLength);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        int numSurvivors;
        if((numSurvivors=collapseData.getNumSurvivors())!=numLeft){
          if(numSurvivors==0){
            fragmentedCollapseAll(head,tail);
          }else{
            if(collapseData.biggestRunBegin>head){
              collapseData.fragmentedCollapseBiggestRunInHead(head,this,tail);
            }else{
              collapseData.fragmentedCollapseBiggestRunInTail(head,this,tail);
            }
          }
          this.modCount=modCount+1;
          return true;
        }
      }else{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    private void fragmentedCollapseHeadAndTail(Object[] arr,int head,int tail,Predicate<? super E> filter,int modCount){
      int newTail=tail-1,newHead=head+1,bound=arr.length-1;
      for(;;--newTail){
        if(newTail==-1){
          for(;;++newHead){
            if(newHead>bound){
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.tail=-1;
              break;
            }else if(!filter.test((E)arr[newHead])){
              collapsetailHelper(arr,newHead,filter,modCount);
              break;
            }
          }
          break;
        }else if(!filter.test((E)arr[newTail])){
          for(;;++newHead){
            if(newHead>bound){
              collapseheadHelper(arr,newTail,filter,modCount);
              break;
            }else if(!filter.test((E)arr[newHead])){
              fragmentedCollapseBodyHelper(arr,newHead,newTail,filter,modCount);
              break;
            }
          }
          break;
        }
      }
      OmniArray.OfRef.nullifyRange(arr,newHead-1,head);
      OmniArray.OfRef.nullifyRange(arr,tail,newTail+1);
    }
    @SuppressWarnings("unchecked")
    private void collapseHeadAndTail(Object[] arr,int head,int tail,Predicate<? super E> filter,int modCount){
      for(int headOffset=head+1;headOffset!=tail;++headOffset){
        if(!filter.test((E)arr[headOffset])){
          for(int tailOffset=tail-1;tailOffset!=headOffset;--tailOffset){
            if(!filter.test((E)arr[tailOffset])){
              collapseBodyHelper(arr,headOffset,tailOffset,filter,modCount);
              OmniArray.OfRef.nullifyRange(arr,tail,tailOffset+1);
              OmniArray.OfRef.nullifyRange(arr,headOffset-1,head);
              return;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.head=headOffset;
          this.tail=headOffset;
          OmniArray.OfRef.nullifyRange(arr,tail,headOffset+1);
          OmniArray.OfRef.nullifyRange(arr,headOffset-1,head);
          return;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      this.tail=-1;
      OmniArray.OfRef.nullifyRange(arr,tail,head);
    }
    @SuppressWarnings("unchecked")
    @Override boolean fragmentedRemoveIf(int head,int tail,Predicate<? super E> filter){
      int modCount=this.modCount;
      try{
        final Object[] arr;
        if(filter.test((E)(arr=this.arr)[head])){
          if(filter.test((E)arr[tail])){
            fragmentedCollapseHeadAndTail(arr,head,tail,filter,modCount);
          }else{
            fragmentedCollapsehead(arr,head,tail,filter,modCount);
          }
          this.modCount=modCount+1;
          return true;
        }else{
          if(filter.test((E)arr[tail])){
            fragmentedCollapsetail(arr,head,tail,filter,modCount);
            this.modCount=modCount+1;
            return true;
          }
          return fragmentedCollapseBody(arr,head,tail,filter,modCount);
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @SuppressWarnings("unchecked")
    @Override boolean nonfragmentedRemoveIf(int head,int tail,Predicate<? super E> filter){
      final int modCount=this.modCount;
      try{
        final Object[] arr;
        if(filter.test((E)(arr=this.arr)[head])){
          if(head==tail){
            CheckedCollection.checkModCount(modCount,this.modCount);
            arr[tail]=null;
            this.tail=-1;
          }else if(filter.test((E)arr[tail])){
            collapseHeadAndTail(arr,head,tail,filter,modCount);
          }else{
            collapsehead(arr,head,tail,filter,modCount);
          }
          this.modCount=modCount+1;
          return true;
        }else if(head!=tail){
          if(filter.test((E)arr[tail])){
            collapsetail(arr,head,tail,filter,modCount);
            this.modCount=modCount+1;
            return true;
          }
          return collapseBody(arr,head,tail,filter,modCount);
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new AscendingItr<E>(this);
    }
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      return new DescendingItr<E>(this);
    }
    private static class AscendingItr<E> extends AbstractDeqItr<E>{
      transient int modCount;
      transient int lastRet;
      transient final Checked<E> root;
      private AscendingItr(Checked<E> root){
        super(root.tail==-1?-1:root.head);
        this.root=root;
        this.modCount=root.modCount;
        this.lastRet=-1;
      }
      private AscendingItr(Checked<E> root,int cursor){
        super(cursor);
        this.root=root;
        this.modCount=root.modCount;
        this.lastRet=-1;
      }
      @SuppressWarnings("unchecked")
      @Override public E next(){
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final Object[] arr;
          final var ret=(E)(arr=root.arr)[cursor];
          this.lastRet=cursor;
          if(cursor==root.tail){
            cursor=-1;
          }else if(++cursor==arr.length){
            cursor=0;
          }
          this.cursor=cursor;
          return ret;
        }
        throw new NoSuchElementException();
      }
      private void fragmentedAscendingRemove(int head,int lastRet,int tail,RefArrDeq<E> root){
        Object[] arr;
        int headDist,tailDist,arrBound=(arr=root.arr).length-1;
        if((headDist=lastRet-head)>=0){
          //index to remove is in head run
          if(headDist<=(tailDist=arrBound-lastRet)+tail+1){
            if(headDist==0){
              if(tailDist==0){
                root.head=0;
              }
              else{
                root.head=head+1;
              }
              arr[head]=null;
            }else{
              ArrCopy.uncheckedCopy(arr,tail=head,arr,++head,headDist);
              arr[tail]=null;
              root.head=head;
            }
          }else{
            ArrCopy.semicheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
            arr[arrBound]=arr[0];
            root.tail=fragmentedPullDown(arr,arrBound,tail);
            this.cursor=lastRet;
          }
        }else{
          if((tailDist=tail-lastRet)<=(headDist=arrBound-head)+lastRet+1){
            if(tailDist==0){
              if(lastRet==0){
                root.tail=arrBound;
              }else{
                root.tail=tail-1;
              }
            }else{
              ArrCopy.uncheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
              root.tail=tail-1;
              this.cursor=lastRet;
            }
            arr[tail]=null;
          }else{
            ArrCopy.semicheckedCopy(arr,0,arr,1,lastRet);
            arr[0]=arr[arrBound];
            root.head=fragmentedPullUp(arr,head,headDist);
          }
        }
      }
      private void nonfragmentedAscendingRemove(int head,int lastRet,int tail,RefArrDeq<E> root){
        int headDist,tailDist;
        if((headDist=lastRet-head)<=(tailDist=tail-lastRet)){
          root.head=pullUp(root.arr,head,headDist);
        }else{
          if(tailDist==0){
            root.arr[tail]=null;
          }else
          {
            Object[] arr;
            ArrCopy.uncheckedSelfCopy(arr=root.arr,lastRet,lastRet+1,tailDist);
            arr[tail]=null;
            this.cursor=lastRet;
          }
          root.tail=tail-1;
        }
      }
      @Override public void remove(){
        int lastRet;
        if((lastRet=this.lastRet)!=-1){
          int modCount;
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          final int head,tail;
          switch(Integer.signum((tail=root.tail)-(head=root.head))){
            case -1:
              fragmentedAscendingRemove(head,lastRet,tail,root);
              break;
            case 0:
              root.tail=-1;
              root.arr[tail]=null;
              break;
            default:
              nonfragmentedAscendingRemove(head,lastRet,tail,root);
          }
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override void uncheckedForEachRemaining(int cursor,Consumer<? super E> action){
        int modCount=this.modCount;
        final Checked<E> root;
        int tail=(root=this.root).tail;
        try{
          final var arr=root.arr;
          if(cursor>tail){
            OmniArray.OfRef.ascendingForEach(arr,cursor,arr.length-1,action);
            cursor=0;
          }
          OmniArray.OfRef.ascendingForEach(arr,cursor,tail,action);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        this.lastRet=tail;
        this.cursor=-1;
      }
    }
    private static class DescendingItr<E> extends AscendingItr<E>{
      private DescendingItr(Checked<E> root){
        super(root,root.tail);
      }
      @SuppressWarnings("unchecked")
      @Override public E next(){
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=-1){
          final Object[] arr;
          final var ret=(E)(arr=root.arr)[cursor];
          this.lastRet=cursor;
          if(cursor==root.head){
            cursor=-1;
          }else if(--cursor==-1){
            cursor=arr.length-1;
          }
          this.cursor=cursor;
          return ret;
        }
        throw new NoSuchElementException();
      }
      private void fragmentedDescendingRemove(int head,int lastRet,int tail,RefArrDeq<E> root){
        Object[] arr;
        int headDist,tailDist,arrBound=(arr=root.arr).length-1;
        if((headDist=lastRet-head)>=0){
          if(headDist<=(tailDist=arrBound-lastRet)+tail+1){
            if(headDist==0){
              if(lastRet==arrBound){
                root.head=0;
              }else{
                root.head=head+1;
              }
              arr[head]=null;
            }else{
              ArrCopy.uncheckedCopy(arr,tail=head,arr,++head,headDist);
              arr[tail]=null;
              root.head=head;
              this.cursor=lastRet;
            }
          }else{
            ArrCopy.semicheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
            arr[arrBound]=arr[0];
            root.tail=fragmentedPullDown(arr,arrBound,tail);
          }
        }else{
          if((tailDist=tail-lastRet)<=(headDist=arrBound-head)+lastRet+1){
            if(tailDist==0){
              if(lastRet==0){
                root.tail=arrBound;
              }else{
                root.tail=tail-1;
              }
            }else{
              ArrCopy.uncheckedSelfCopy(arr,lastRet,lastRet+1,tailDist);
              root.tail=tail-1;
            }
            arr[tail]=null;
          }else{
            ArrCopy.semicheckedCopy(arr,0,arr,1,lastRet);
            arr[0]=arr[arrBound];
            root.head=fragmentedPullUp(arr,head,headDist);
            this.cursor=lastRet;
          }
        }
      }
      private void nonfragmentedDescendingRemove(int head,int lastRet,int tail,RefArrDeq<E> root){
        int tailDist,headDist;
        if((tailDist=tail-lastRet)<=(headDist=lastRet-head)){
          Object[] arr;
          ArrCopy.semicheckedSelfCopy(arr=root.arr,lastRet,lastRet+1,tailDist);
          arr[tail]=null;
          root.tail=tail-1;
        }else{
          if(headDist==0){
            root.head=head+1;
            root.arr[head]=null;
          }else{
            Object[] arr;
            ArrCopy.uncheckedCopy(arr=root.arr,tail=head,arr,++head,headDist);
            arr[tail]=null;
            this.cursor=lastRet;
            root.head=head;
          }
        }
      }
      @Override public void remove(){
        int lastRet;
        if((lastRet=this.lastRet)!=-1){
          int modCount;
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          final int head,tail;
          switch(Integer.signum((tail=root.tail)-(head=root.head))){
            case -1:
              fragmentedDescendingRemove(head,lastRet,tail,root);
              break;
            case 0:
              root.tail=-1;
              root.arr[tail]=null;
              break;
            default:
              nonfragmentedDescendingRemove(head,lastRet,tail,root);
          }
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override void uncheckedForEachRemaining(int cursor,Consumer<? super E> action){
        int modCount=this.modCount;
        final Checked<E> root;
        int head=(root=this.root).head;
        try{
          final var arr=root.arr;
          if(cursor<head){
            OmniArray.OfRef.descendingForEach(arr,0,cursor,action);
            cursor=arr.length-1;
          }
          OmniArray.OfRef.descendingForEach(arr,head,cursor,action);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        this.lastRet=head;
        this.cursor=-1;
      }
    }
    @Override public Object clone(){
      int tail;
      if((tail=this.tail)!=-1){
        final var arr=this.arr;
        final Object[] dst;
        int size,head;
        Checked<E> clone;
        if((size=(++tail)-(head=this.head))<=0){
          clone=new Checked<E>(0,dst=new Object[size+=arr.length],size-1);
          ArrCopy.uncheckedCopy(arr,0,dst,size-=tail,tail);
        }else{
          clone=new Checked<E>(0,dst=new Object[size],size-1);
        }
        ArrCopy.uncheckedCopy(arr,head,dst,0,size);
        return clone;
      }
      return new Checked<E>();
    }
    @SuppressWarnings("unchecked")
    @Override public E removeLast(){
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
      throw new NoSuchElementException();
    }
    @SuppressWarnings("unchecked")
    @Override public E pop(){
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
      throw new NoSuchElementException();
    }
    @Override public void writeExternal(ObjectOutput output) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(output);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
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
        OmniArray.OfRef.nullifyRange(arr,tail,head);
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
    @Override void uncheckedForEach(final int tail,Consumer<? super E> action){
      final int modCount=this.modCount;
      try{
        super.uncheckedForEach(tail,action);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @SuppressWarnings("unchecked")
    @Override public E element(){
      if(tail!=-1){
        return (E)arr[head];
      }
      throw new NoSuchElementException();
    }
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
      final int tail;
      if((tail=this.tail)!=-1){
        int modCount=this.modCount;
        try{
          return super.uncheckedToString(tail);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
      return "[]";
    }
    @Override public int hashCode(){
      final int tail;
      if((tail=this.tail)!=-1){
        int modCount=this.modCount;
        try{
          return super.uncheckedHashCode(tail);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
      return 1;
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
              if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
              break;
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
              if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
              break;
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
              if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
              break;
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
                  if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
              break;
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
                  if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
              break;
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
                  if((headDist=index-head)<((tailDist=bound-index)+tail)){
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
              break;
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
