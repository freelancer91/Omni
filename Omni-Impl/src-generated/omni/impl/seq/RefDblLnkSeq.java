package omni.impl.seq;
import omni.api.OmniList;
import omni.api.OmniDeque;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import omni.util.OmniArray;
import java.util.function.IntFunction;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.util.BitSetUtils;
import java.util.Objects;
import omni.util.OmniPred;
import omni.util.RefSortUtil;
public abstract class RefDblLnkSeq<E> extends AbstractSeq implements OmniList.OfRef<E>
{
  private static <E> boolean uncheckedcontains(Node<E> begin,Node<E> end,Predicate<Object> pred)
  {
    while(!pred.test(begin.val))
    {
      if(begin==end)
      {
        return false;
      }
      begin=begin.next;
    }
    return true;
  }
  private static <E> int uncheckedindexOf(Node<E> begin,Node<E> end,Predicate<Object> pred)
  {
    int index=0;
    while(!pred.test(begin.val))
    {
      if((begin=begin.next)==null)
      {
        return -1;
      }
      ++index;
    }
    return index;
  }
  private static <E> int uncheckedlastIndexOf(Node<E> end,int size,Predicate<Object> pred)
  {
    while(!pred.test(end.val) && --size!=0)
    {
      end=end.prev;
    }
    return size-1;
  }
  private static <E> int uncheckedsearch(Node<E> begin,Predicate<Object> pred)
  {
    int index=1;
    while(!pred.test(begin.val))
    {
      if((begin=begin.next)==null)
      {
        return -1;
      }
      ++index;
    }
    return index;
  }
  transient Node<E> head;
  transient Node<E> tail;
  private RefDblLnkSeq()
  {
    super();
  }
  private RefDblLnkSeq(Node<E> onlyNode)
  {
    super(1);
    privateInit(onlyNode);
  }
  private RefDblLnkSeq(Node<E> head,int size,Node<E> tail)
  {
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override
  public final boolean add(final E val)
  {
    addLast((E)val);
    return true;
  }
  public final void addFirst(final E val)
  {
    push((E)val);
  }
  public final boolean offer(final E val)
  {
    addLast((E)val);
    return true;
  }
  public final boolean offerLast(final E val)
  {
    addLast((E)val);
    return true;
  }
  public final boolean offerFirst(final E val)
  {
    push((E)val);
    return true;
  }
  abstract void pushtailHelper(Node<E> oldtail,E val);
  public void addLast(final E val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      pushtailHelper(tail,val);
    }
    else
    {
      initHelper(val);
    }
  }
  abstract void pushheadHelper(Node<E> oldhead,E val);
  public void push(final E val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      pushheadHelper(head,val);
    }
    else
    {
      initHelper(val);
    }
  }
  public final E getFirst()
  {
    return element();
  }
  public final E remove()
  {
    return pop();
  }
  public final E removeFirst()
  {
    return pop();
  }
  public final E peekFirst()
  {
    return peek();
  }
  public final E pollFirst()
  {
    return poll();
  }
  public E peek()
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return (E)(head.val);
    }
    return null;
  }
  public E peekLast()
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return (E)(tail.val);
    }
    return null;
  }
  public E poll()
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      --this.size;
      privatechophead(head);
      return (E)(head.val);
    }
    return null;
  }
  public E pollLast()
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      --this.size;
      privatechoptail(tail);
      return (E)(tail.val);
    }
    return null;
  }
  public E element()
  {
    return head.val;
  }
  public E getLast()
  {
    return tail.val;
  }
  public E pop()
  {
    --this.size;
    Node<E> head;
    privatechophead(head=this.head);
    return head.val;
  }
  public E removeLast()
  {
    --this.size;
    Node<E> tail;
    privatechoptail(tail=this.tail);
    return tail.val;
  }
  public void clear()
  {
    this.size=0;
    ((RefDblLnkSeq<E>)this).privateInit(null);
  }
  @Override
  public boolean contains(final boolean val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      {
        return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final long val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final float val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final byte val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final char val)
  {
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final short val)
  {
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final Boolean val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Byte val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Character val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Short val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Integer val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Long val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Float val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Double val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public int indexOf(final boolean val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final int val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      {
        return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int indexOf(final long val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final float val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final double val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final byte val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final char val)
  {
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int indexOf(final short val)
  {
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int indexOf(final Boolean val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final Byte val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final Character val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final Short val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final Integer val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final Long val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final Float val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final Double val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final boolean val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final int val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      {
        return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final long val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final float val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final double val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final byte val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final char val)
  {
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final short val)
  {
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final Boolean val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final Byte val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final Character val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final Short val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final Integer val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final Long val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final Float val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final Double val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final boolean val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final int val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      {
        return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(final long val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final float val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final double val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final byte val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final char val)
  {
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(final short val)
  {
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(final Boolean val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Byte val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Character val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Short val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Integer val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Long val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Float val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Double val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public boolean contains(final Object val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      {
        return RefDblLnkSeq.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public int indexOf(final Object val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      {
        return RefDblLnkSeq.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final Object val)
  {
    final Node<E> tail;
    if((tail=this.tail)!=null)
    {
      {
        return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(final Object val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      {
        return RefDblLnkSeq.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public void forEach(final Consumer<? super E> action)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      head.uncheckedForEachForward(this.tail,action);
    }
  }
  @Override
  public E get(int index)
  {
    return privateGetNode(index,size-index).val;
  }
  abstract void initHelper(E val);
  @Override
  public int hashCode()
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedForwardHashCode(this.tail);
    }
    return 1;
  }
  @Override
  public void put(int index,E val)
  {
    privateGetNode(index,size-index).val=val;
  }
  public final boolean removeFirstOccurrence(final Object val)
  {
    return remove(val);
  }
  @Override
  public void replaceAll(UnaryOperator<E> operator)
  {
    Node<E> head;
    if((head=this.head)!=null)
    {
      head.uncheckedReplaceAll(this.tail,operator);
    }
  }
  @Override
  public void reverseSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      head.uncheckedreverseSort(size,tail);
    }
  }
  @Override
  public void sort()
  {
    final int size;
    if((size=this.size)>1)
    {
      head.uncheckedsort(size,tail);
    }
  }
  @Override
  public void sort(Comparator<? super E> sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      head.uncheckedcomparatorSort(size,tail,sorter);
    }
  }
  @Override
  public E set(final int index,final E val)
  {
    final Node<E> node;
    final var oldVal=(node=privateGetNode(index,size-index)).val;
    node.val=val;
    return oldVal;
  }
    @Override
    public Object[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Object[] dst;
        head.uncheckedCopyForward(dst=new Object[size],0,size);
        return dst;
      }
      return OmniArray.OfRef.DEFAULT_ARR;
    }
  @Override
  public <T> T[] toArray(final IntFunction<T[]> arrConstructor)
  {
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0)
    {
      head.uncheckedCopyForward(dst,0,size);
    }
    return dst;
  }
  @Override
  public <T> T[] toArray(T[] dst)
  {
    final int size;
    if((size=this.size)!=0)
    {
      head.uncheckedCopyForward(dst=OmniArray.uncheckedArrResize(size,dst),0,size);
    }
    else if(dst.length!=0)
    {
      dst[0]=null;
    }
    return dst;
  }
  @Override
  public String toString()
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      final StringBuilder builder;
      head.uncheckedForwardToString(tail,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  private void privateInit(Node<E> onlyNode)
  {
    this.head=onlyNode;
    this.tail=onlyNode;
  }
  private void uncheckedAddHelper(int headDist,int newSize,E val)
  {
    this.size=newSize;
    if(headDist==0)
    {
      this.head=new Node<E>(val,this.head);
    }
    else
    {
      if((newSize-=headDist)==0)
      {
        this.tail=new Node<E>(this.tail,val);
      }
      else
      {
        Node<E> before,after;
        if(headDist<=newSize)
        {
          after=(before=this.head.iterateForward(headDist-1)).next;
        }
        else
        {
          before=(after=this.tail.iterateReverse(newSize-1)).prev;
        }
        new Node<E>(before,val,after);
      }
    }
  }
  private void privatesettail(Node<E> newTail)
  {
    this.tail=newTail;
    newTail.next=null;
  }
  private void privatesethead(Node<E> newHead)
  {
    this.head=newHead;
    newHead.prev=null;
  }
  private void privatechoptail(Node<E> oldTail)
  {
    this.tail=oldTail=oldTail.prev;
    oldTail.next=null;
  }
  private void privatechophead(Node<E> oldHead)
  {
    this.head=oldHead=oldHead.next;
    oldHead.prev=null;
  }
  private Node<E> removeAtIndexHelper(int headDist,int newSize)
  {
    Node<E> node;
    this.size=newSize;
    if(headDist==0)
    {
      privatechophead(node=this.head);
    }
    else
    {
      if((newSize-=headDist)==0)
      {
        privatechoptail(node=this.tail);
      }
      else
      {
        if(headDist<=newSize)
        {
          Node<E> before;
          (before=this.head.iterateForward(headDist-1)).joinnext((node=before.next).next);
        }
        else
        {
          Node<E> after;
          (node=(after=this.tail.iterateReverse(newSize-1)).prev).prev.joinnext(after);
        }
      }
    }
    return node;
  }
  private void subSeqInsertHelper(Node<E> newNode,int index)
  {
    final int tailDist;
    final Node<E> before,after;
    if(index<=(tailDist=this.size-index))
    {
      after=(before=this.head.iterateForward(index-1)).next;
    }
    else
    {
      before=(after=this.tail.iterateReverse(tailDist-1)).prev;
    }
    before.joinnext(newNode);
    newNode.joinnext(after);
  }
  private void collapsehead(Node<E> head,Node<E> tail,Predicate<? super E> filter)
  {
    int numRemoved;
    for(numRemoved=1;(head=head.next)!=tail;++numRemoved)
    {
      if(!filter.test(head.val))
      {
        numRemoved+=head.collapseBodyHelper(tail,filter);
        break;
      }
    }
    ((RefDblLnkSeq<E>)this).privatesethead(head);
    this.size-=numRemoved;
  }
   void collapsetail(Node<E> head,Node<E> tail,Predicate<? super E> filter)
  {
    int numRemoved;
    for(numRemoved=1;(tail=tail.prev)!=head;++numRemoved)
    {
      if(!filter.test(tail.val))
      {
        numRemoved+=head.collapseBodyHelper(tail,filter);
        break;
      }
    }
    ((RefDblLnkSeq<E>)this).privatesettail(tail);
    this.size-=numRemoved;
  }
  boolean collapseBody(Node<E> head,Node<E> tail,Predicate<? super E> filter)
  {
    Node<E> prev;
    while((head=(prev=head).next)!=tail)
    {
      if(filter.test(head.val))
      {
        int numRemoved;
        for(numRemoved=1;(head=head.next)!=tail;++numRemoved)
        {
          if(!filter.test(head.val))
          {
            numRemoved+=head.collapseBodyHelper(tail,filter);
            break;
          }
        }
        prev.joinnext(head);
        size-=numRemoved;
        return true;
      }
    }
    return false;
  }
  void findNewHead(Node<E> head,Predicate<? super E> filter)
  {
    final Node<E> tail;
    if((tail=this.tail)!=head)
    {
      if(filter.test(tail.val))
      {
        collapseHeadAndTail(head,tail,filter);
      }
      else
      {
        collapsehead(head.next,tail,filter);
      }
    }
    else
    {
      ((RefDblLnkSeq<E>)this).privateInit(null);
      this.size=0;
    }
  }
  private void collapseHeadAndTail(Node<E> head,Node<E> tail,Predicate<? super E> filter)
  {
    for(int numRemoved=2;(head=head.next)!=tail;++numRemoved)
    {
      if(!filter.test(head.val))
      {
        while((tail=tail.prev)!=head)
        {
          if(!filter.test(tail.val))
          {
            numRemoved+=head.collapseBodyHelper(tail,filter);
            break;
          }
          ++numRemoved;
        }
        ((RefDblLnkSeq<E>)this).privatesethead(head);
        ((RefDblLnkSeq<E>)this).privatesettail(tail);
        size-=numRemoved;
        return;
      }
    }
    ((RefDblLnkSeq<E>)this).privateInit(null);
    this.size=0;
  }
  private boolean uncheckedRemoveIf(Node<E> head,Predicate<? super E> filter)
  {
    if(filter.test(head.val))
    {
      findNewHead(head,filter);
    }
    else
    {
      final Node<E> tail;
      if(head==(tail=this.tail))
      {
        return false;
      }
      if(!filter.test(tail.val))
      {
        return collapseBody(head,tail,filter);
      }
      collapsetail(head,tail,filter);
    }
    return true;
  }
  private Node<E> privateGetNode(int headDist,int tailDist)
  {
    if(headDist<=tailDist)
    {
      return head.iterateForward(headDist);
    }
    return tail.iterateReverse(tailDist-1);
  }
  private Node<E> getItrNode(int headDist,int tailDist)
  {
    if(tailDist==0)
    {
      return null;
    }
    return privateGetNode(headDist,tailDist);
  }
  public static class Checked<E> extends RefDblLnkSeq<E> implements OmniDeque.OfRef<E>
  {
    transient int modCount;
    public Checked()
    {
      super();
    }
    public Checked(Node<E> onlyNode)
    {
      super(onlyNode);
    }
    public Checked(Node<E> head,int size,Node<E> tail)
    {
      super(head,size,tail);
    }
    @Override
    public Object clone()
    {
      Node<E> newHead,newTail;
      final int size;
      if((size=this.size)!=0)
      {
        Node<E> oldHead,oldTail;
        for(newHead=new Node<E>((oldHead=this.head).val),newTail=newHead,oldTail=this.tail;oldHead!=oldTail;new Node<E>(newTail,(oldHead=oldHead.next).val))
        {
        }
      }
      else
      {
        newHead=null;
        newTail=null;
      }
      return new Checked<E>(newHead,size,newTail);
    }
    public void clear()
    {
      if(this.size!=0)
      {
        ++this.modCount;
        super.privateInit(null);
        this.size=0;
      }
    }
    private OmniList.OfRef<E> getDefaultSubList(int headDist,int subListSize,int tailDist)
    {
      final Node<E> subListHead=head;
      Node<E> subListTail=tail;
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList<E>(this,null,subListHead,subListSize,subListTail);
        }
        return new SubList.SuffixList<E>(this,null,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail);
      }
      subListTail=tailDist<=subListSize?subListTail.uncheckedIterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
      if(headDist==0)
      {
        return new SubList.PrefixList<E>(this,null,subListHead,subListSize,subListTail);
      }
      return new SubList.BodyList<E>(this,null,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail,headDist);
    }
    private OmniList.OfRef<E> getEmptySubList(int headDist,int tailDist)
    {
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList<E>(this,null);
        }
        return new SubList.SuffixList<E>(this,null);
      }
      else if(headDist==0)
      {
        return new SubList.PrefixList<E>(this,null);
      }
      return new SubList.BodyList<E>(this,null,headDist);
    }
    private OmniList.OfRef<E> getSubList1(int headDist,int tailDist)
    {
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList<E>(this,null,head);
        }
        return new SubList.SuffixList<E>(this,null,tail);
      }
      else if(headDist==0)
      {
        return new SubList.PrefixList<E>(this,null,head);
      }
      return new SubList.BodyList<E>(this,null,((RefDblLnkSeq<E>)this).privateGetNode(headDist,tailDist),headDist);
    }
    @Override
    void initHelper(E val)
    {
      ++this.modCount;
      this.size=1;
      super.privateInit(new Node<E>(val));
    }
    @Override
    void pushheadHelper(Node<E> oldhead,E val)
    {
      ++this.modCount;
      ++this.size;
      this.head=new Node<E>(val,this.head);
    }
    @Override
    void pushtailHelper(Node<E> oldtail,E val)
    {
      ++this.modCount;
      ++this.size;
      this.tail=new Node<E>(this.tail,val);
    }
    @Override
    public E element()
    {
      Node<E> head;
      if((head=this.head)!=null)
      {
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override
    public E getLast()
    {
      Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override
    public E pop()
    {
      final int size;
      if((size=this.size)!=0)
      {
        this.size=size-1;
        ++this.modCount;
        Node<E> head;
        ((RefDblLnkSeq<E>)this).privatechophead(head=this.head);
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override
    public E removeLast()
    {
      final int size;
      if((size=this.size)!=0)
      {
        this.size=size-1;
        ++this.modCount;
        Node<E> tail;
        ((RefDblLnkSeq<E>)this).privatechoptail(tail=this.tail);
        return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override
    public E get(int index)
    {
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return super.privateGetNode(index,size-index).val;
    }
    @Override
    public int hashCode()
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        int modCount=this.modCount;
        try
        {
          return head.uncheckedForwardHashCode(this.tail);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
      return 1;
    }
    @Override
    public String toString()
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        final StringBuilder builder=new StringBuilder("[");
        int modCount=this.modCount;
        try
        {
          head.uncheckedForwardToString(tail,builder);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        return builder.append(']').toString();
      }
      return "[]";
    }
    @Override
    public int indexOf(final Object val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        if(val!=null)
        {
          int modCount=this.modCount;
          try
          {
            return RefDblLnkSeq.uncheckedindexOf(head,tail,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkSeq.uncheckedindexOf(head,tail,Objects::isNull);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(final Object val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        if(val!=null)
        {
          int modCount=this.modCount;
          try
          {
            return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkSeq.uncheckedlastIndexOf(tail,size,Objects::isNull);
      }
      return -1;
    }
    @Override
    public int search(final Object val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        if(val!=null)
        {
          int modCount=this.modCount;
          try
          {
            return RefDblLnkSeq.uncheckedsearch(head,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkSeq.uncheckedsearch(head,Objects::isNull);
      }
      return -1;
    }
    @Override
    public boolean contains(final Object val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        if(val!=null)
        {
          int modCount=this.modCount;
          try
          {
            return RefDblLnkSeq.uncheckedcontains(head,tail,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkSeq.uncheckedcontains(head,tail,Objects::isNull);
      }
      return false;
    }
    @Override
    public E set(final int index,final E val)
    {
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final Node<E> node;
      final var oldVal=(node=super.privateGetNode(index,size-index)).val;
      node.val=val;
      return oldVal;
    }
    @Override
    public <T> T[] toArray(final IntFunction<T[]> arrConstructor)
    {
      return super.toArray(size->
      {
        int modCount=this.modCount;
        try
        {
           return arrConstructor.apply(size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override
    public void forEach(final Consumer<? super E> action)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        int modCount=this.modCount;
        try
        {
          head.uncheckedForEachForward(this.tail,action);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    public E poll()
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        ++this.modCount;
        --this.size;
        ((RefDblLnkSeq<E>)this).privatechophead(head);
        return (E)(head.val);
      }
      return null;
    }
    @Override
    public E pollLast()
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        ++this.modCount;
        --this.size;
        ((RefDblLnkSeq<E>)this).privatechoptail(tail);
        return (E)(tail.val);
      }
      return null;
    }
    @Override
    public void add(int index,E val)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++modCount;
      if(++size!=1)
      {
        super.uncheckedAddHelper(index,size,val);
      }
      else
      {
        this.size=1;
        super.privateInit(new Node<E>(val));
      }
    }
    @Override
    public E remove(int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++this.modCount;
      Node<E> node;
      if(--size!=0)
      {
        node=super.removeAtIndexHelper(index,size);
      }
      else
      {
        node=this.head;
        this.size=0;
        super.privateInit(null);
      }
      return node.val;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new AscendingItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator()
    {
      return new BidirectionalItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr<E>(this,(size-=index)==0?null:super.privateGetNode(index,size),index);
    }
    @Override
    public OmniIterator.OfRef<E> descendingIterator()
    {
      return new DescendingItr<E>(this);
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
    {
      int size;
      CheckedCollection.checkSubListRange(fromIndex,toIndex,size=this.size);
      size-=toIndex;
      int subListSize;
      switch(subListSize=toIndex-fromIndex)
      {
      default:
        return getDefaultSubList(fromIndex,subListSize,size);
      case 1:
        return getSubList1(fromIndex,size);
      case 0:
        return getEmptySubList(fromIndex,size);
      }
    }
    @Override
    public void put(int index,E val)
    {
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      super.privateGetNode(index,size-index).val=val;
    }
    @Override
    public void replaceAll(UnaryOperator<E> operator)
    {
      Node<E> head;
      if((head=this.head)!=null)
      {
        int modCount=this.modCount;
        try
        {
          head.uncheckedReplaceAll(this.tail,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void reverseSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        try
        {
           head.uncheckedreverseSort(size,tail);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort()
    {
      final int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        try
        {
           head.uncheckedsort(size,tail);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        try
        {
          head.uncheckedcomparatorSort(size,tail,sorter);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public boolean remove(Object val)
    {
      Node<E> head;
      if((head=this.head)!=null)
      {
        if(val!=null)
        {
          return uncheckedremoveValNonNull(head,val);
        }
        return uncheckedremoveVal(head,Objects::isNull);
      }
      return false;
    }
    private boolean uncheckedremoveValNonNull(Node<E> head,Object nonNull)
    {
      int modCount=this.modCount;
      try
      {
        if(!nonNull.equals(head.val))
        {
          Node<E> lastVisited;
          do
          {
            if((head=(lastVisited=head).next)==null)
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              return false;
            }
          }
          while(!nonNull.equals(head.val));
          --this.size;
          if(head==tail)
          {
            ((RefDblLnkSeq<E>)this).privatesettail(lastVisited);
          }
          else
          {
            lastVisited.joinnext(head.next);
          }
        }
        else
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          if(--this.size==0)
          {
            ((RefDblLnkSeq<E>)this).privateInit(null);
          }
          else
          {
            ((RefDblLnkSeq<E>)this).privatechophead(head);
          }
        }
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return true;
    }
    private boolean uncheckedremoveVal(Node<E> head,Predicate<Object> pred)
    {
      if(!pred.test(head.val))
      {
        Node<E> lastVisited;
        do
        {
          if((head=(lastVisited=head).next)==null)
          {
            return false;
          }
        }
        while(!pred.test(head.val));
        --this.size;
        if(head==tail)
        {
          ((RefDblLnkSeq<E>)this).privatesettail(lastVisited);
        }
        else
        {
          lastVisited.joinnext(head.next);
        }
      }
      else
      {
        if(--this.size==0)
        {
          ((RefDblLnkSeq<E>)this).privateInit(null);
        }
        else
        {
          ((RefDblLnkSeq<E>)this).privatechophead(head);
        }
      }
      ++this.modCount;
      return true;
    }
    @Override
    public boolean removeVal(final boolean val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final int val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(final long val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final float val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final double val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final byte val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final char val)
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(final short val)
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(final Boolean val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Byte val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Character val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Short val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Integer val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Long val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Float val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Double val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(Object val)
    {
      Node<E> tail;
      if((tail=this.tail)!=null)
      {
        if(val!=null)
        {
          return uncheckedremoveLastOccurrenceNonNull(tail,val);
        }
        return uncheckedremoveLastOccurrence(tail,Objects::isNull);
      }
      return false;
    }
    private boolean uncheckedremoveLastOccurrenceNonNull(Node<E> tail,Object nonNull)
    {
      int modCount=this.modCount;
      try
      {
        if(!nonNull.equals(tail.val))
        {
          Node<E> lastVisited;
          do
          {
            if((tail=(lastVisited=tail).prev)==null)
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              return false;
            }
          }
          while(!nonNull.equals(tail.val));
          --this.size;
          if(tail==head)
          {
            ((RefDblLnkSeq<E>)this).privatesethead(lastVisited);
          }
          else
          {
            lastVisited.joinprev(tail.prev);
          }
        }
        else
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          if(--this.size==0)
          {
            ((RefDblLnkSeq<E>)this).privateInit(null);
          }
          else
          {
            ((RefDblLnkSeq<E>)this).privatechoptail(tail);
          }
        }
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return true;
    }
    private boolean uncheckedremoveLastOccurrence(Node<E> tail,Predicate<Object> pred)
    {
      if(!pred.test(tail.val))
      {
        Node<E> lastVisited;
        do
        {
          if((tail=(lastVisited=tail).prev)==null)
          {
            return false;
          }
        }
        while(!pred.test(tail.val));
        --this.size;
        if(tail==head)
        {
          ((RefDblLnkSeq<E>)this).privatesethead(lastVisited);
        }
        else
        {
          lastVisited.joinprev(tail.prev);
        }
      }
      else
      {
        if(--this.size==0)
        {
          ((RefDblLnkSeq<E>)this).privateInit(null);
        }
        else
        {
          ((RefDblLnkSeq<E>)this).privatechoptail(tail);
        }
      }
      ++this.modCount;
      return true;
    }
    @Override
    public boolean removeLastOccurrence(final boolean val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final int val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        {
          return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final long val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final float val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final double val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final byte val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final char val)
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final short val)
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Boolean val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Byte val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Character val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Short val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Integer val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Long val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Float val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Double val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    private void collapseHeadAndTail(int modCount,Node<E> head,Node<E> tail,Predicate<? super E> filter)
    {
      int oldSize=this.size;
      int numConsumed=2;
      for(;;)
      {
        if(numConsumed==oldSize)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          super.clear();
          return;
        }
        ++numConsumed;
        if(!filter.test((head=head.next).val))
        {
          for(;;)
          {
            if(numConsumed==oldSize)
            {
               CheckedCollection.checkModCount(modCount,this.modCount);
               this.size=1;
               tail=head;
               break;
            }
            ++numConsumed;
            if(!filter.test((tail=tail.prev).val))
            {
              size=(oldSize-=numConsumed)+1-head.collapseBodyHelper(new ModCountChecker(modCount),oldSize,tail,filter);
              break;
            }
          }
          ((RefDblLnkSeq<E>)this).privatesethead(head);
          ((RefDblLnkSeq<E>)this).privatesettail(tail);
          return;
        }
      }
    }
    private boolean collapseBody(int modCount,Node<E> prev,Node<E> next,Predicate<? super E> filter)
    {
      int numLeft=size;
      int numConsumed=2;
      for(Node<E> before;numConsumed!=numLeft;prev=before)
      {
        ++numConsumed;
        if(filter.test((before=prev.next).val))
        {
          int newSize=numConsumed-1;
          for(Node<E> after;;next=after,++newSize)
          {
            if(numConsumed==numLeft)
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              break;
            }
            ++numConsumed;
            if(filter.test((after=next.prev).val))
            {
              long[] survivorSet;
              if((numLeft-=numConsumed)!=0&&(numLeft=(before=before.next).markSurvivors(numLeft,survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0)
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                newSize+=numLeft;
                prev=prev.retainSurvivors(before,numLeft,survivorSet);
              }
              else
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
              }
              break;
            }
          }
          prev.joinnext(next);
          this.modCount=modCount+1;
          this.size=newSize;
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    private void collapsehead(int modCount,Node<E> head,Node<E> tail,Predicate<? super E> filter)
    {
      int oldSize,numConsumed;
      for(head=head.next,oldSize=this.size,numConsumed=2;;)
      {
        if(numConsumed==oldSize)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          size=1;
          break;
        }
        ++numConsumed;
        if(!filter.test(head.val))
        {
          size=(oldSize-=numConsumed)+1-head.collapseBodyHelper(new ModCountChecker(modCount),oldSize,tail,filter);
          break;
        }
        ((RefDblLnkSeq<E>)this).privatesethead(head);
      }
    }
    private void collapsetail(int modCount,Node<E> tail,Node<E> head,Predicate<? super E> filter)
    {
      int oldSize,numConsumed;
      for(tail=tail.prev,oldSize=this.size,numConsumed=2;;)
      {
        if(numConsumed==oldSize)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          size=1;
          break;
        }
        ++numConsumed;
        if(!filter.test(tail.val))
        {
          size=(oldSize-=numConsumed)+1-head.collapseBodyHelper(new ModCountChecker(modCount),oldSize,tail,filter);
          break;
        }
        ((RefDblLnkSeq<E>)this).privatesettail(tail);
      }
    }
    private boolean uncheckedRemoveIf(Node<E> head,Predicate<? super E> filter)
    {
      final int modCount=this.modCount;
      try
      {
        final var tail=this.tail;
        if(filter.test(head.val))
        {
          if(head==tail)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            super.clear();
          }
          else if(filter.test(tail.val))
          {
            collapseHeadAndTail(modCount,head,tail,filter);
          }
          else
          {
            collapsehead(modCount,head,tail,filter);
          }
          this.modCount=modCount+1;
          return true;
        }
        else if(head!=tail)
        {
          if(filter.test(tail.val))
          {
            collapsetail(modCount,head,tail,filter);
            this.modCount=modCount+1;
            return true;
          }
          return collapseBody(modCount,head,tail,filter);
        }
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super E> filter)
    {
      final Node<E> head;
      return (head=this.head)!=null && this.uncheckedRemoveIf(head,filter);
    }
    private static class SubList<E> extends RefDblLnkSeq<E>
    {
      private static class AscendingItr<E> implements OmniIterator.OfRef<E>
      {
        transient final SubList<E> parent;
        transient Node<E> cursor;
        transient Node<E> lastRet;
        transient int modCount;
        AscendingItr(SubList<E> parent)
        {
          this.parent=parent;
          this.cursor=parent.head;
          this.modCount=parent.modCount;
        }
        AscendingItr(SubList<E> parent,Node<E> cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
          this.modCount=parent.modCount;
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action)
        {
          Node<E> cursor;
          if((cursor=this.cursor)!=null)
          {
            final var parent=this.parent;
            int modCount=this.modCount;
            try
            {
              cursor.uncheckedForEachForward(cursor=parent.tail,action);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,parent.root.modCount);
            }
            this.cursor=null;
            this.lastRet=cursor;
          }
        }
        @Override
        public boolean hasNext()
        {
          return cursor!=null;
        }
        @Override
        public E next()
        {
          final SubList<E> parent;
          CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
          Node<E> cursor;
          if((cursor=this.cursor)!=null)
          {
            if(cursor==parent.tail)
            {
              this.cursor=null;
            }
            else
            {
              this.cursor=cursor.next;
            }
            this.lastRet=cursor;
            return cursor.val;
          }
          throw new NoSuchElementException();
        }
        @Override
        public void remove()
        {
          parent.ascItrRemove(this);
          this.lastRet=null;
        }
      }
      private static class BidirectionalItr<E> extends AscendingItr<E> implements OmniListIterator.OfRef<E>
      {
        private transient int nextIndex;
        private BidirectionalItr(SubList<E> parent)
        {
          super(parent);
        }
        private BidirectionalItr(SubList<E> parent,Node<E> cursor,int nextIndex)
        {
          super(parent,cursor);
          this.nextIndex=nextIndex;
        }
        @Override
        public void add(E val)
        {
          int modCount;
          final Checked<E> root;
          final SubList<E> parent;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          final int nextIndex=this.nextIndex++;
          int size;
          if((size=parent.size++)!=0)
          {
            if(nextIndex==0)
            {
              parent.prependHelper(val);
            }
            else if(nextIndex==size)
            {
              parent.appendHelper(val);
            }
            else
            {
              Node<E> cursor;
              parent.insertHelper((cursor=this.cursor).prev,val,cursor);
            }
          }
          else
          {
            parent.initHelper(root,val);
          }
          ++root.size;
          this.modCount=modCount;
          this.lastRet=null;
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action)
        {
          Node<E> cursor;
          if((cursor=this.cursor)!=null)
          {
            final var parent=this.parent;
            int modCount=this.modCount;
            try
            {
              cursor.uncheckedForEachForward(cursor=parent.tail,action);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,parent.root.modCount);
            }
            this.nextIndex=parent.size;
            this.cursor=null;
            this.lastRet=cursor;
          }
        }
        @Override
        public boolean hasPrevious()
        {
          return nextIndex!=0;
        }
        @Override
        public E next()
        {
          final SubList<E> parent;
          CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
          Node<E> cursor;
          if((cursor=this.cursor)!=null)
          {
            if(cursor==parent.tail)
            {
              this.cursor=null;
            }
            else
            {
              this.cursor=cursor.next;
            }
            ++nextIndex;
            this.lastRet=cursor;
            return cursor.val;
          }
          throw new NoSuchElementException();
        }
        @Override
        public int nextIndex()
        {
          return this.nextIndex;
        }
        @Override
        public E previous()
        {
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          int nextIndex;
          if((nextIndex=this.nextIndex)!=0)
          {
            this.nextIndex=nextIndex-1;
            Node<E> cursor;
            this.cursor=cursor=this.cursor.prev;
            this.lastRet=cursor;
            return cursor.val;
          }
          throw new NoSuchElementException();
        }
        @Override
        public int previousIndex()
        {
          return this.nextIndex-1;
        }
        @Override
        public void remove()
        {
          this.parent.bidirectItrRemove(this);
          this.lastRet=null;
        }
        @Override
        public void set(E val)
        {
          Node<E> lastRet;
          if((lastRet=this.lastRet)!=null)
          {
            CheckedCollection.checkModCount(modCount,parent.root.modCount);
            lastRet.val=val;
            return;
          }
          throw new IllegalStateException();
        }
      }
      transient int modCount;
      transient final Checked<E> root;
      transient final SubList<E> parent;
      SubList(Checked<E> root,SubList<E> parent)
      {
        super();
        this.root=root;
        this.parent=parent;
        this.modCount=root.modCount;
      }
      SubList(Checked<E> root,SubList<E> parent,Node<E> onlyNode)
      {
        super(onlyNode);
        this.root=root;
        this.parent=parent;
        this.modCount=root.modCount;
      }
      SubList(Checked<E> root,SubList<E> parent,Node<E> head,int size,Node<E> tail)
      {
        super(head,size,tail);
        this.root=root;
        this.parent=parent;
        this.modCount=root.modCount;
      }
      @Override
      public int size()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return this.size;
      }
      @Override
      public boolean isEmpty()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return this.size==0;
      }
      @Override
      public boolean remove(Object val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null)
          {
            return uncheckedremoveValNonNull(head,val);
          }
          return uncheckedremoveVal(head,Objects::isNull);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final boolean val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final int val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          {
            return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final long val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final float val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final double val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final byte val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final char val)
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final short val)
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Boolean val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Byte val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Character val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Short val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Integer val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Long val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Float val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Double val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
            void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter)
            {
              final Node<E> tail;
              if(curr!=(tail=this.tail))
              {
                if(!filter.test(tail.val))
                {
                  rootCollapsehead(modCount,curr,tail,filter);
                  return;
                }
                collapseHeadAndTail(modCount,curr,tail,filter);
                return;
              }
               final Checked<E> root;
               CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
               root.modCount=modCount+1;
              ((RefDblLnkSeq<E>)root).privateInit(null);
              root.size=0;
              bubbleUpclearRoot();
            }
      private void collapseHeadAndTail(int modCount,Node<E> head,Node<E> tail,Predicate<? super E> filter)
      {
        final int oldSize=this.size;
        int numConsumed=2;
        final var root=this.root;
        for(;;)
        {
          if(numConsumed==oldSize)
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            bubbleUpclearRoot();
            ((RefDblLnkSeq<E>)root).privateInit(null);
            break;
          }
          ++numConsumed;
          if(!filter.test((head=head.next).val))
          {
            for(;;)
            {
              if(numConsumed==oldSize)
              {
                CheckedCollection.checkModCount(modCount,root.modCount);
                --numConsumed;
                break;
              }
              ++numConsumed;
              if(filter.test((tail=tail.prev).val))
              {
                numConsumed-=2-head.collapseBodyHelper(root.new ModCountChecker(modCount),oldSize-numConsumed,tail,filter);
                break;
              }
            }
            bubbleUpcollapseHeadAndTail(head,tail,numConsumed);
            ((RefDblLnkSeq<E>)root).privatesethead(head);
            ((RefDblLnkSeq<E>)root).privatesettail(tail);
            break;
          }
        }
        root.modCount=modCount+1;
        root.size-=numConsumed;
      }
      private boolean collapseBody(int modCount,Node<E> prev,Node<E> next,Predicate<? super E> filter)
      {
        final int oldSize=size;
        int numConsumed=2;
        final var root=this.root;
        for(Node<E> before;numConsumed!=oldSize;prev=before)
        {
          ++numConsumed;
          if(filter.test((before=prev.next).val))
          {
            int numRemoved=1;
            for(Node<E> after;;next=after)
            {
              if(numConsumed==oldSize)
              {
                CheckedCollection.checkModCount(modCount,root.modCount);
                break;
              }
              ++numConsumed;
              if(filter.test((after=prev.next).val))
              {
                ++numRemoved;
                long[] survivorSet;
                int numLeft,numSurvivors;
                if((numLeft=oldSize-numConsumed)!=0&&(numSurvivors=(before=before.next).markSurvivors(numLeft,survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0)
                {
                  CheckedCollection.checkModCount(modCount,root.modCount);
                  numRemoved+=numLeft-numSurvivors;
                  prev=prev.retainSurvivors(before,numSurvivors,survivorSet);
                }
                else
                {
                  CheckedCollection.checkModCount(modCount,root.modCount);
                  numRemoved+=oldSize;
                }
                break;
              }
            }
            prev.joinnext(next);
            root.modCount=modCount+1;
            bubbleUpdecrementSize(numRemoved);
            root.size-=numRemoved;
            return true;
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      boolean removeIfHelper(int modCount,Node<E> curr,Predicate<? super E> filter)
      {
        final Node<E> tail;
        if(curr!=(tail=this.tail))
        {
          if(filter.test(tail.val))
          {
            rootCollapsetail(modCount,curr,tail,filter);
            return true;
          }
          return collapseBody(modCount,curr,tail,filter);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      private void prefixCollapsetail(int modCount,Node<E> head,Node<E> tail,Predicate<? super E> filter)
      {
        final int oldSize=this.size;
        int numConsumed=2;
        final var root=this.root;
        var tailCandidate=tail.prev;
        for(;;tailCandidate=tailCandidate.prev)
        {
          if(numConsumed==oldSize)
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            --numConsumed;
            break;
          }
          ++numConsumed;
          if(!filter.test(tailCandidate.val))
          {
            numConsumed-=2-head.collapseBodyHelper(root.new ModCountChecker(modCount),oldSize-numConsumed,tailCandidate,filter);
            break;
          }
        }
        root.modCount=modCount+1;
        bubbleUpprefixCollapsetail(numConsumed,tail,tailCandidate);
        root.size-=numConsumed;
        tail.prev.joinprev(tailCandidate);
      }
      private void rootCollapsetail(int modCount,Node<E> head,Node<E> tail,Predicate<? super E> filter)
      {
        final int oldSize=this.size;
        int numConsumed=2;
        final var root=this.root;
        for(;;)
        {
          tail=tail.prev;
          if(numConsumed==oldSize)
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            --numConsumed;
            break;
          }
          ++numConsumed;
          if(!filter.test(tail.val))
          {
            numConsumed-=2-head.collapseBodyHelper(root.new ModCountChecker(modCount),oldSize-numConsumed,tail,filter);
            break;
          }
        }
        root.modCount=modCount+1;
        bubbleUprootCollapsetail(numConsumed,tail);
        ((RefDblLnkSeq<E>)root).privatesettail(tail);
        root.size-=numConsumed;
      }
      private void suffixCollapsehead(int modCount,Node<E> head,Node<E> tail,Predicate<? super E> filter)
      {
        final int oldSize=this.size;
        int numConsumed=2;
        final var root=this.root;
        var headCandidate=head.next;
        for(;;headCandidate=headCandidate.next)
        {
          if(numConsumed==oldSize)
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            --numConsumed;
            break;
          }
          ++numConsumed;
          if(!filter.test(headCandidate.val))
          {
            numConsumed-=2-headCandidate.collapseBodyHelper(root.new ModCountChecker(modCount),oldSize-numConsumed,tail,filter);
            break;
          }
        }
        root.modCount=modCount+1;
        bubbleUpsuffixCollapsehead(numConsumed,head,headCandidate);
        root.size-=numConsumed;
        head.prev.joinnext(headCandidate);
      }
      private void rootCollapsehead(int modCount,Node<E> head,Node<E> tail,Predicate<? super E> filter)
      {
        final int oldSize=this.size;
        int numConsumed=2;
        final var root=this.root;
        for(;;)
        {
          head=head.next;
          if(numConsumed==oldSize)
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            --numConsumed;
            break;
          }
          ++numConsumed;
          if(!filter.test(head.val))
          {
            numConsumed-=2-head.collapseBodyHelper(root.new ModCountChecker(modCount),oldSize-numConsumed,tail,filter);
            break;
          }
        }
        root.modCount=modCount+1;
        bubbleUprootCollapsehead(numConsumed,head);
        ((RefDblLnkSeq<E>)root).privatesethead(head);
        root.size-=numConsumed;
      }
      private void privateCollapseHeadAndTail(int size,Node<E> head,Node<E> tail)
      {
        ++this.modCount;
        this.size=size;
        this.head=head;
        this.tail=tail;
      }
      int getParentOffset()
      {
        return 0;
      }
      @Override
      public void add(int index,E val)
      {
        final int modCount;
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        ++root.size;
        root.modCount=modCount+1;
        if(size!=0)
        {
          if(index==0)
          {
            Node<E> newNode;
            root.head=newNode=new Node<E>(val,this.head);
            ((SubList<E>)this).bubbleUppushhead(newNode);
          }
          else
          {
            if((size-=index)==0)
            {
              Node<E> newNode;
              root.tail=newNode=new Node<E>(this.tail,val);
              ((SubList<E>)this).bubbleUppushtail(newNode);
            }
            else
            {
              Node<E> before,after;
              if(index<=size)
              {
                after=(before=this.head.iterateForward(index-1)).next;
              }
              else
              {
                before=(after=this.tail.iterateReverse(size-1)).prev;
              }
              new Node<E>(before,val,after);
              ((SubList<E>)this).bubbleUpincrementSize();
            }
          }
        }
        else
        {
          Node<E> newNode;
          ((RefDblLnkSeq<E>)root).privateInit(newNode=new Node<E>(val));
          bubbleUpinit(newNode);
        }
      }
      @Override
      public E remove(int index)
      {
        final int modCount;
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        root.modCount=modCount+1;
        --root.size;
        Node<E> node;
        if(--size!=0)
        {
          if(index==0)
          {
            Node<E> newHead;
            ((RefDblLnkSeq<E>)root).privatesethead(newHead=(node=this.head).next);
            ((SubList<E>)this).bubbleUprootchophead(newHead);
          }
          else
          {
            if((size-=index)==0)
            {
              Node<E> newTail;
              ((RefDblLnkSeq<E>)root).privatesettail(newTail=(node=this.tail).prev);
              ((SubList<E>)this).bubbleUprootchoptail(newTail);
            }
            else
            {
              if(index<=size)
              {
                Node<E> before;
                (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
              }
              else
              {
                Node<E> after;
                (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
              }
              ((SubList<E>)this).bubbleUpdecrementSize();
            }
          }
        }
        else
        {
          node=this.head;
          ((RefDblLnkSeq<E>)root).privateInit(null);
          bubbleUpclearRoot();
        }
        return node.val;
      }
      @Override
      void initHelper(E val)
      {
        final Checked<E> root;
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=modCount+1;
        root.size=1;
        Node<E> newNode;
        ((RefDblLnkSeq<E>)root).privateInit(newNode=new Node<E>(val));
        bubbleUpinit(newNode);
      }
      @Override
      void pushheadHelper(Node<E> oldhead,E val)
      {
        final Checked<E> root;
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=modCount+1;
        ++root.size;
        Node<E> newNode;
        root.head=newNode=new Node<E>(val,oldhead);
        bubbleUppushhead(newNode);
      }
      private void bubbleUppushhead(Node<E> newhead)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;++curr.size;curr.head=newhead;
        }
        while((curr=curr.parent)!=null);
      }
      @Override
      void pushtailHelper(Node<E> oldtail,E val)
      {
        final Checked<E> root;
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=modCount+1;
        ++root.size;
        Node<E> newNode;
        root.tail=newNode=new Node<E>(oldtail,val);
        bubbleUppushtail(newNode);
      }
      private void bubbleUppushtail(Node<E> newtail)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;++curr.size;curr.tail=newtail;
        }
        while((curr=curr.parent)!=null);
      }
      @Override
      public void clear()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        if(size!=0)
        {
          ((SubList<E>)this).clearRoot();
        }
      }
      @Override
      public E get(int index)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        return super.privateGetNode(index,size-index).val;
      }
      @Override
      public int hashCode()
      {
        int modCount=this.modCount;
        try
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return head.uncheckedForwardHashCode(this.tail);
          }
          return 1;
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public String toString()
      {
        int modCount=this.modCount;
        try
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            final StringBuilder builder;
            head.uncheckedForwardToString(tail,builder=new StringBuilder("["));
            return builder.append(']').toString();
          }
          return "[]";
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public boolean contains(final Object val)
      {
        int modCount=this.modCount;
        try
        {
          return super.contains(val);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public int indexOf(final Object val)
      {
        int modCount=this.modCount;
        try
        {
          return super.indexOf(val);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public int lastIndexOf(final Object val)
      {
        int modCount=this.modCount;
        try
        {
          return super.lastIndexOf(val);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public E set(int index,E val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final Node<E> node;
        final var oldVal=(node=super.privateGetNode(index,size-index)).val;
        node.val=val;
        return oldVal;
      }
      @Override
      public <T> T[] toArray(T[] arr)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.toArray(arr);
      }
      @Override
      public <T> T[] toArray(final IntFunction<T[]> arrConstructor)
      {
        return super.toArray(size->
        {
          int modCount=this.modCount;
          try
          {
            return arrConstructor.apply(size);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        });
      }
      @Override
      public boolean contains(final boolean val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final int val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final long val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final float val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final double val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final byte val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final char val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final short val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final Boolean val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final Byte val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final Character val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final Short val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final Integer val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final Long val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final Float val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public boolean contains(final Double val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.contains(val);
      }
      @Override
      public int indexOf(final boolean val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final int val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final long val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final float val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final double val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final byte val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final char val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final short val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final Boolean val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final Byte val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final Character val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final Short val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final Integer val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final Long val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final Float val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int indexOf(final Double val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.indexOf(val);
      }
      @Override
      public int lastIndexOf(final boolean val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final int val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final long val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final float val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final double val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final byte val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final char val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final short val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final Boolean val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final Byte val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final Character val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final Short val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final Integer val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final Long val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final Float val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public int lastIndexOf(final Double val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.lastIndexOf(val);
      }
      @Override
      public void forEach(Consumer<? super E> action)
      {
        int modCount=this.modCount;
        try
        {
          super.forEach(action);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void put(int index,E val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        super.privateGetNode(index,size-index).val=val;
      }
      @Override
      public void replaceAll(UnaryOperator<E> operator)
      {
        final Checked<E> root=this.root;
        int modCount=this.modCount;
        try
        {
          Node<E> head;if((head=this.head)==null){return;}head.uncheckedReplaceAll(tail,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        root.modCount=modCount+1;
        ((SubList<E>)this).bubbleUpincrementModCount();
      }
      @Override
      public void sort()
      {
        final Checked<E> root=this.root;
        int modCount=this.modCount;
        try
        {
          int size;if((size=this.size)<2){return;}head.uncheckedsort(size,tail);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        root.modCount=modCount+1;
        this.bubbleUpincrementModCount();
      }
      @Override
      public void reverseSort()
      {
        final Checked<E> root=this.root;
        int modCount=this.modCount;
        try
        {
          int size;if((size=this.size)<2){return;}head.uncheckedreverseSort(size,tail);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        root.modCount=modCount+1;
        this.bubbleUpincrementModCount();
      }
      @Override
      public void sort(Comparator<? super E> sorter)
      {
        Checked<E> root=this.root;
        int modCount=this.modCount;
        try
        {
          final int size;if((size=this.size)<2){return;}head.uncheckedcomparatorSort(size,tail,sorter);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        root.modCount=modCount+1;
        ((SubList<E>)this).bubbleUpincrementModCount();
      }
        @Override
        public Object[] toArray()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            final Object[] dst;
            head.uncheckedCopyForward(dst=new Object[size],0,size);
            return dst;
          }
          return OmniArray.OfRef.DEFAULT_ARR;
        }
      @Override
      public boolean equals(Object val)
      {
        //TODO implements equals method
        return false;
      }
      @Override
      public Object clone()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        Node<E> newHead,newTail;
        final int size;
        if((size=this.size)!=0)
        {
          Node<E> oldHead,oldTail;
          for(newHead=new Node<E>((oldHead=this.head).val),newTail=newHead,oldTail=this.tail;oldHead!=oldTail;new Node<E>(newTail,(oldHead=oldHead.next).val))
          {
          }
        }
        else
        {
          newHead=null;
          newTail=null;
        }
        return new Checked<E>(newHead,size,newTail);
      }
      @Override
      public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
      {
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int size;
        CheckedCollection.checkSubListRange(fromIndex,toIndex,size=this.size);
        size-=toIndex;
        int subListSize;
        switch(subListSize=toIndex-fromIndex)
        {
        default:
          return getDefaultSubList(root,fromIndex,subListSize,size);
        case 1:
          return getSubList1(root,fromIndex,size);
        case 0:
          return getEmptySubList(root,fromIndex,size);
        }
      }
      private OmniList.OfRef<E> getDefaultSubList(Checked<E> root,int headDist,int subListSize,int tailDist)
      {
        final Node<E> subListHead=head;
        Node<E> subListTail=tail;
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList<E>(root,this,subListHead,subListSize,subListTail);
          }
          return new SubList.SuffixList<E>(root,this,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail);
        }
        subListTail=tailDist<=subListSize?subListTail.uncheckedIterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
        if(headDist==0)
        {
          return new SubList.PrefixList<E>(root,this,subListHead,subListSize,subListTail);
        }
        return new SubList.BodyList<E>(root,this,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail,headDist);
      }
      private OmniList.OfRef<E> getEmptySubList(Checked<E> root,int headDist,int tailDist)
      {
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList<E>(root,this);
          }
          return new SubList.SuffixList<E>(root,this);
        }
        else if(headDist==0)
        {
          return new SubList.PrefixList<E>(root,this);
        }
        return new SubList.BodyList<E>(root,this,headDist);
      }
      private OmniList.OfRef<E> getSubList1(Checked<E> root,int headDist,int tailDist)
      {
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList<E>(root,this,head);
          }
          return new SubList.SuffixList<E>(root,this,tail);
        }
        else if(headDist==0)
        {
          return new SubList.PrefixList<E>(root,this,head);
        }
        return new SubList.BodyList<E>(root,this,((RefDblLnkSeq<E>)this).privateGetNode(headDist,tailDist),headDist);
      }
      @Override
      public OmniIterator.OfRef<E> iterator()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return new AscendingItr<E>(this);
      }
      @Override
      public OmniListIterator.OfRef<E> listIterator()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return new BidirectionalItr<E>(this);
      }
      @Override
      public OmniListIterator.OfRef<E> listIterator(int index)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        return new BidirectionalItr<E>(this,super.getItrNode(index,size-index),index);
      }
      private void bubbleUpincrementModCount()
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpsuffixchophead(Node<E> oldHead,Node<E> newHead)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;--curr.size;curr.head=newHead;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.head==oldHead);
        curr.bubbleUpdecrementSize();oldHead.prev.joinnext(newHead);
      }
      private void bubbleUpprefixchoptail(Node<E> oldTail,Node<E> newTail)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;--curr.size;curr.tail=newTail;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.tail==oldTail);
        curr.bubbleUpdecrementSize();newTail.joinnext(oldTail.next);
      }
      private void bubbleUpclearRoot()
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;curr.size=0;((RefDblLnkSeq<E>)curr).privateInit(null);
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpincrementSize()
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;++curr.size;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpdecrementSize()
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;--curr.size;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpdecrementSize(int numRemoved)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;curr.size-=numRemoved;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchophead(Node<E> newHead)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;--curr.size;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchoptail(Node<E> newTail)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;--curr.size;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchophead(int numRemoved,Node<E> newHead)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;curr.size-=numRemoved;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchoptail(int numRemoved,Node<E> newTail)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;curr.size-=numRemoved;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpprefixPushtail(Node<E> oldTail,Node<E> newTail)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;++curr.size;curr.tail=newTail;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.tail==oldTail);
        curr.bubbleUpincrementSize();
      }
      private void bubbleUpsuffixPushhead(Node<E> oldHead,Node<E> newHead)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;++curr.size;curr.head=newHead;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.head==oldHead);
        curr.bubbleUpincrementSize();
      }
      private void bubbleUpclearPrefix(Node<E> oldTail,Node<E> newHead,int numRemoved)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;curr.size=0;((RefDblLnkSeq<E>)curr).privateInit(null);
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.tail==oldTail);
        curr.bubbleUprootchophead(numRemoved,newHead);
      }
      private void bubbleUpclearSuffix(Node<E> oldHead,Node<E> newTail,int numRemoved)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;curr.size=0;((RefDblLnkSeq<E>)curr).privateInit(null);
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.head==oldHead);
        curr.bubbleUprootchoptail(numRemoved,newTail);
      }
      private void bubbleUpclearBody(Node<E> oldhead,Node<E> oldtail,int numRemoved)
      {
        Node<E> prev,next;
        (prev=oldhead.prev).joinnext(next=tail.next);
        var curr=this;
        for(;;)
        {
          ++curr.modCount;
          curr.size=0;
          ((RefDblLnkSeq<E>)curr).privateInit(null);
          if((curr=curr.parent)==null)
          {
            return;
          }
          if(curr.head!=oldhead)
          {
            while(curr.tail==oldtail)
            {
              ++curr.modCount;
              curr.size-=numRemoved;
              curr.tail=prev;
              if((curr=curr.parent)==null)
              {
                return;
              }
            }
            break;
          }
          if(curr.tail!=oldtail)
          {
            do
            {
              ++curr.modCount;
              curr.size-=numRemoved;
              curr.head=next;
              if((curr=curr.parent)==null)
              {
                return;
              }
            }
            while(curr.head==oldhead);
            break;
          }
        }
        curr.bubbleUpdecrementSize(numRemoved);
      }
      private void bubbleUpprefixCollapsetail(int numRemoved,Node<E> oldTail,Node<E> newTail)
      {
        SubList<E> curr=this;
        while(curr.tail==oldTail)
        {
          ++curr.modCount;curr.size-=numRemoved;curr.tail=newTail;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        curr.bubbleUpdecrementSize(numRemoved);
      }
      private void bubbleUpsuffixCollapsehead(int numRemoved,Node<E> oldHead,Node<E> newHead)
      {
        SubList<E> curr=this;
        while(curr.head==oldHead)
        {
          ++curr.modCount;curr.size-=numRemoved;curr.head=newHead;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        curr.bubbleUpdecrementSize(numRemoved);
      }
      private void bubbleUprootCollapsehead(int numRemoved,Node<E> newHead)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;curr.size-=numRemoved;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootCollapsetail(int numRemoved,Node<E> newTail)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;curr.size-=numRemoved;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpcollapseHeadAndTail(Node<E> newHead,Node<E> newTail,int newSize)
      {
        SubList<E> curr=this;
        do
        {
          curr.privateCollapseHeadAndTail(newSize,newHead,newTail);
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpinit(Node<E> newNode)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.modCount;curr.size=1;((RefDblLnkSeq<E>)curr).privateInit(newNode);
        }
        while((curr=curr.parent)!=null);
      }
      private void clearRoot()
      {
        Checked<E> root;
        ++(root=this.root).modCount;
        root.size=0;
        ((RefDblLnkSeq<E>)root).privateInit(null);
        bubbleUpclearRoot();
      }
      private void clearPrefix(int numRemoved)
      {
        Node<E> oldtail;
        Node<E> newhead=(oldtail=this.tail).next;
        final Checked<E> root;
        ++(root=this.root).modCount;
        root.size-=numRemoved;
        ((RefDblLnkSeq<E>)root).privatesethead(newhead);
        bubbleUpclearPrefix(oldtail,newhead,numRemoved);
      }
      private void clearSuffix(int numRemoved)
      {
        Node<E> oldhead;
        Node<E> newtail=(oldhead=this.head).prev;
        final Checked<E> root;
        ++(root=this.root).modCount;
        root.size-=numRemoved;
        ((RefDblLnkSeq<E>)root).privatesettail(newtail);
        bubbleUpclearSuffix(oldhead,newtail,numRemoved);
      }
      private void clearBody(int numRemoved)
      {
        final Checked<E> root;
        ++(root=this.root).modCount;
        root.size-=numRemoved;
        bubbleUpclearBody(this.head,this.tail,numRemoved);
      }
      @Override
      public boolean removeIf(Predicate<? super E> filter)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return uncheckedRemoveIf(head,filter);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      private boolean uncheckedRemoveIf(Node<E> head,Predicate<? super E> filter)
      {
        final int modCount=this.modCount;
        try
        {
          if(filter.test(head.val))
          {
            findNewHead(modCount,head,filter);
            return true;
          }
          return removeIfHelper(modCount,head,filter);
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      private void uncheckedItrRemove(Node<E> lastRet)
      {
        if(lastRet==tail)
        {
          if(lastRet==head)
          {
            ((SubList<E>)this).bubbleUpclearRoot();
            ((RefDblLnkSeq<E>)root).privateInit(null);
          }
          else
          {
            ((SubList<E>)this).bubbleUprootchoptail(lastRet=lastRet.prev);
            ((RefDblLnkSeq<E>)root).privatesettail(lastRet);
          }
        }
        else
        {
          if(lastRet==head)
          {
            ((SubList<E>)this).bubbleUprootchophead(lastRet=lastRet.next);
            ((RefDblLnkSeq<E>)root).privatesethead(lastRet);
          }
          else
          {
            ((SubList<E>)this).bubbleUpdecrementSize();
            lastRet.prev.joinnext(lastRet.next);
          }
        }
      }
      void ascItrRemove(AscendingItr<E> itr)
      {
        Node<E> lastRet;
        if((lastRet=itr.lastRet)!=null)
        {
          int modCount;
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          uncheckedItrRemove(lastRet);
          itr.modCount=modCount;
          --root.size;
          return;
        }
        throw new IllegalStateException();
      }
      void bidirectItrRemove(BidirectionalItr<E> itr)
      {
        Node<E> lastRet;
        if((lastRet=itr.lastRet)!=null)
        {
          int modCount;
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          uncheckedItrRemove(lastRet);
          if(lastRet!=itr.cursor)
          {
            --itr.nextIndex;
          }
          itr.modCount=modCount;
          --root.size;
          return;
        }
        throw new IllegalStateException();
      }
      void removeFirstHelper(int modCount,Node<E> curr)
      {
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=modCount+1;
        if(curr==tail)
        {
          bubbleUpclearRoot();
          ((RefDblLnkSeq<E>)root).privateInit(null);
        }
        else
        {
          bubbleUprootchophead(curr=curr.next);
          ((RefDblLnkSeq<E>)root).privatesethead(curr);
        }
        --root.size;
      }
      boolean uncheckedremoveValHelper(int modCount,Node<E> curr,Predicate<Object> pred)
      {
        final var root=this.root;
        final var tail=this.tail;
        Node<E> prev;
        do
        {
          if(curr==tail)
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
        }
        while(!pred.test((curr=(prev=curr).next).val));
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=modCount+1;
        --root.size;
        if(curr==tail)
        {
          bubbleUprootchoptail(prev);root.tail=prev;prev.next=null;
        }
        else
        {
          ((SubList<E>)this).bubbleUpdecrementSize();
          prev.joinnext(curr.next);
        }
        return true;
      }
      private boolean uncheckedremoveValNonNull(Node<E> head,Object nonNull)
      {
        final int modCount=this.modCount;
        try
        {
          if(!nonNull.equals(head.val))
          {
            return uncheckedremoveValHelper(modCount,head,nonNull::equals);
          }
          removeFirstHelper(modCount,head);
          return true;
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      void initHelper(Checked<E> root,E val)
      {
        final var newNode=new Node<E>(val);
        for(var curr=this;;curr.size=1)
        {
          ((RefDblLnkSeq<E>)curr).privateInit(newNode);
          if((curr=curr.parent)==null)
          {
            ((RefDblLnkSeq<E>)root).privateInit(newNode);
            return;
          }
        }
      }
      void prependHelper(E val)
      {
        ++this.modCount;
        Node<E> newNode;
        this.head=newNode=new Node<E>(val,this.head);
        SubList<E> parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUppushhead(newNode);
        }
      }
      void appendHelper(E val)
      {
        ++this.modCount;
        Node<E> newNode;
        this.tail=newNode=new Node<E>(this.tail,val);
        SubList<E> parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUppushtail(newNode);
        }
      }
      private void insertHelper(Node<E> before,E val,Node<E> after)
      {
        ++this.modCount;
        new Node<E>(before,val,after);
        SubList<E> parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUpincrementSize();
        }
      }
      private boolean uncheckedremoveVal(Node<E> head,Predicate<Object> pred)
      {
        final int modCount=this.modCount;
        try
        {
          if(!pred.test(head.val))
          {
            return uncheckedremoveValHelper(modCount,head,pred);
          }
          removeFirstHelper(modCount,head);
          return true;
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      private static class PrefixList<E> extends SubList<E>
      {
        PrefixList(Checked<E> root,SubList<E> parent)
        {
          super(root,parent);
        }
        PrefixList(Checked<E> root,SubList<E> parent,Node<E> onlyNode)
        {
          super(root,parent,onlyNode);
        }
        PrefixList(Checked<E> root,SubList<E> parent,Node<E> head,int size,Node<E> tail)
        {
          super(root,parent,head,size,tail);
        }
        @Override
        boolean uncheckedremoveValHelper(int modCount,Node<E> curr,Predicate<Object> pred)
        {
          final var root=this.root;
          final var tail=this.tail;
          Node<E> prev;
          do
          {
            if(curr==tail)
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
              return false;
            }
          }
          while(!pred.test((curr=(prev=curr).next).val));
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=modCount+1;
          --root.size;
          if(curr==tail)
          {
            ((SubList<E>)this).bubbleUpprefixchoptail(curr,prev);
          }
          else
          {
            ((SubList<E>)this).bubbleUpdecrementSize();
            prev.joinnext(curr.next);
          }
          return true;
        }
        private void bubbleUpinit(Node<E> newNode)
        {
          SubList<E> curr=this;
          do
          {
            ++curr.modCount;curr.size=1;curr.head=newNode;curr.tail=newNode;
            if((curr=curr.parent)==null)
            {
              return;
            }
          }
          while(curr.size!=0);
          curr.bubbleUppushhead(newNode);
        }
        @Override
        void initHelper(E val)
        {
          final Checked<E> root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          Node<E> newNode;
          root.head=newNode=new Node<E>(val,root.head);
          bubbleUpinit(newNode);
        }
        private void bubbleUppushtail(Node<E> oldtail,Node<E> newNode)
        {
          SubList<E> curr=this;
          do
          {
            ++curr.modCount;++curr.size;curr.tail=newNode;
            if((curr=curr.parent)==null)
            {
              return;
            }
          }
          while(curr.tail==oldtail);
          curr.bubbleUpincrementSize();
        }
        @Override
        void pushtailHelper(Node<E> oldtail,E val)
        {
          final Checked<E> root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          bubbleUppushtail(oldtail,new Node<E>(oldtail=this.tail,val,oldtail.next));
        }
        @Override
        public void clear()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList<E>)this).clearPrefix(size);
          }
        }
        @Override
        public void add(int index,E val)
        {
          final int modCount;
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          CheckedCollection.checkLo(index);
          int size;
          CheckedCollection.checkWriteHi(index,size=this.size);
          ++root.size;
          root.modCount=modCount+1;
          if(size!=0)
          {
            if(index==0)
            {
              Node<E> newNode;
              root.head=newNode=new Node<E>(val,this.head);
              ((SubList<E>)this).bubbleUppushhead(newNode);
            }
            else
            {
              if((size-=index)==0)
              {
                Node<E> oldtail;
                ((PrefixList<E>)this).bubbleUppushtail(oldtail=this.tail,new Node<E>(oldtail,val,oldtail.next));
              }
              else
              {
                Node<E> before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node<E>(before,val,after);
                ((SubList<E>)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            Node<E> newNode;
            root.head=newNode=new Node<E>(val,root.head);
            bubbleUpinit(newNode);
          }
        }
        @Override
        public E remove(int index)
        {
          final int modCount;
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          CheckedCollection.checkLo(index);
          int size;
          CheckedCollection.checkReadHi(index,size=this.size);
          root.modCount=modCount+1;
          --root.size;
          Node<E> node;
          if(--size!=0)
          {
            if(index==0)
            {
              Node<E> newHead;
              ((RefDblLnkSeq<E>)root).privatesethead(newHead=(node=this.head).next);
              ((SubList<E>)this).bubbleUprootchophead(newHead);
            }
            else
            {
              if((size-=index)==0)
              {
                ((SubList<E>)this).bubbleUpprefixchoptail(node=this.tail,node.prev);
              }
              else
              {
                if(index<=size)
                {
                  Node<E> before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node<E> after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList<E>)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            node=this.head;
            ((RefDblLnkSeq<E>)root).privateInit(null);
            ((SubList<E>)this).bubbleUpclearPrefix(node=this.head,node.next,1);
          }
          return node.val;
        }
        @Override
        void appendHelper(E val)
        {
          ++this.modCount;
          Node<E> newNode,oldtail;
          tail=newNode=new Node<E>(oldtail=tail,val,oldtail.next);
          SubList<E> parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpprefixPushtail(newNode,oldtail);
          }
        }   
        @Override
        void initHelper(Checked<E> root,E val)
        {
          final Node<E> after,newNode=new Node<E>(val);
          for(SubList<E> curr=this;;curr.size=1)
          {
            ((RefDblLnkSeq<E>)curr).privateInit(newNode);
            if((curr=curr.parent)==null)
            {
              after=root.head;
              break;
            }
            if(curr.size!=0)
            {
              after=curr.head;
              curr.bubbleUppushhead(newNode);
              break;
            }
          }
          after.joinprev(newNode);
        }
        private void bubbleUpcollapseHeadAndTail(Node<E> oldtail,Node<E> newhead,Node<E> newtail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList<E> curr=this;;)
          {
            curr.privateCollapseHeadAndTail(newSize,newhead,newtail);
            if((curr=curr.parent)==null)
            {
              break;
            }
            if(curr.tail!=oldtail)
            {
              curr.bubbleUprootCollapsehead(numRemoved,newhead);
              break;
            }
          }
          newtail.joinnext(oldtail.next);
        }
        private void collapseHeadAndTail(int modCount,Node<E> head,Node<E> tail,Predicate<? super E> filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
              ((SubList<E>)this).bubbleUpclearPrefix(tail,head=tail.next,oldSize);
              break;
            }
            ++numConsumed;
            if(!filter.test((head=head.next).val))
            {
              var tailCandidate=tail;
              for(;;)
              {
                if(numConsumed==oldSize)
                {
                  CheckedCollection.checkModCount(modCount,root.modCount);
                  --numConsumed;
                  break;
                }
                ++numConsumed;
                if(!filter.test((tailCandidate=tailCandidate.prev).val))
                {
                  numConsumed-=2-head.collapseBodyHelper(root.new ModCountChecker(modCount),oldSize-numConsumed,tailCandidate,filter);
                  break;
                }
                bubbleUpcollapseHeadAndTail(tail,head,tailCandidate,numConsumed);
                break;
              }
            }
            root.modCount=modCount+1;
            root.head=head;
            head.prev=null;
            root.size-=numConsumed;
          }
        }
        @Override
        void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter)
        {
          final Node<E> tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList<E>)this).rootCollapsehead(modCount,curr,tail,filter);
              return;
            }
            collapseHeadAndTail(modCount,curr,tail,filter);
            return;
          }
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          --root.size;
          ((SubList<E>)this).bubbleUpclearPrefix(curr,curr=tail.next,1);
          ((RefDblLnkSeq<E>)root).privatesethead(curr);
        }
        @Override
        boolean removeIfHelper(int modCount,Node<E> curr,Predicate<? super E> filter)
        {
          final Node<E> tail;
          if(curr!=(tail=this.tail))
          {
            if(filter.test(tail.val))
            {
              super.prefixCollapsetail(modCount,curr,tail,filter);
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,root.modCount);
          return false;
        }
        @Override
        void ascItrRemove(AscendingItr<E> itr)
        {
          Node<E> lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked<E> root;
            CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            uncheckedItrRemove(lastRet,root);
            itr.modCount=modCount;
            --root.size;
            return;
          }
          throw new IllegalStateException();
        }
        @Override
        void bidirectItrRemove(BidirectionalItr<E> itr)
        {
          Node<E> lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked<E> root;
            CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            uncheckedItrRemove(lastRet,root);
            if(lastRet!=itr.cursor)
            {
              --itr.nextIndex;
            }
            itr.modCount=modCount;
            --root.size;
            return;
          }
          throw new IllegalStateException();
        }
        private void uncheckedItrRemove(Node<E> lastRet,Checked<E> root)
        {
          if(lastRet==head)
          {
            if(lastRet==tail)
            {
              ((SubList<E>)this).bubbleUpclearPrefix(lastRet,lastRet=lastRet.next,1);
            }
            else
            {
              ((SubList<E>)this).bubbleUprootchophead(lastRet=lastRet.next);
            }
            ((RefDblLnkSeq<E>)root).privatesethead(lastRet);
          }
          else
          {
            if(lastRet==tail)
            {
              ((SubList<E>)this).bubbleUpprefixchoptail(lastRet,lastRet.prev);
            }
            else
            {
              ((SubList<E>)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
      }
      private static class SuffixList<E> extends SubList<E>
      {
        SuffixList(Checked<E> root,SubList<E> parent)
        {
          super(root,parent);
        }
        SuffixList(Checked<E> root,SubList<E> parent,Node<E> onlyNode)
        {
          super(root,parent,onlyNode);
        }
        SuffixList(Checked<E> root,SubList<E> parent,Node<E> head,int size,Node<E> tail)
        {
          super(root,parent,head,size,tail);
        }
        private void bubbleUpinit(Node<E> newNode)
        {
          SubList<E> curr=this;
          do
          {
            ++curr.modCount;curr.size=1;curr.head=newNode;curr.tail=newNode;
            if((curr=curr.parent)==null)
            {
              return;
            }
          }
          while(curr.size!=0);
          curr.bubbleUppushtail(newNode);
        }
        @Override
        void initHelper(E val)
        {
          final Checked<E> root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          Node<E> newNode;
          root.tail=newNode=new Node<E>(root.tail,val);
          bubbleUpinit(newNode);
        }
        private void bubbleUppushhead(Node<E> oldhead,Node<E> newNode)
        {
          SubList<E> curr=this;
          do
          {
            ++curr.modCount;++curr.size;curr.head=newNode;
            if((curr=curr.parent)==null)
            {
              return;
            }
          }
          while(curr.head==oldhead);
          curr.bubbleUpincrementSize();
        }
        @Override
        void pushheadHelper(Node<E> oldhead,E val)
        {
          final Checked<E> root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          bubbleUppushhead(oldhead,new Node<E>((oldhead=this.head).prev,val,oldhead));
        }
        @Override
        public void clear()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList<E>)this).clearSuffix(size);
          }
        }
        @Override
        public void add(int index,E val)
        {
          final int modCount;
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          CheckedCollection.checkLo(index);
          int size;
          CheckedCollection.checkWriteHi(index,size=this.size);
          ++root.size;
          root.modCount=modCount+1;
          if(size!=0)
          {
            if(index==0)
            {
              Node<E> oldhead;
              bubbleUppushhead(oldhead=this.head,new Node<E>(oldhead.prev,val,oldhead));
            }
            else
            {
              if((size-=index)==0)
              {
                Node<E> newNode;
                root.tail=newNode=new Node<E>(this.tail,val);
                ((SubList<E>)this).bubbleUppushtail(newNode);
              }
              else
              {
                Node<E> before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node<E>(before,val,after);
                ((SubList<E>)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            Node<E> newNode;
            root.tail=newNode=new Node<E>(root.tail,val);
            bubbleUpinit(newNode);
          }
        }
        @Override
        public E remove(int index)
        {
          final int modCount;
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          CheckedCollection.checkLo(index);
          int size;
          CheckedCollection.checkReadHi(index,size=this.size);
          root.modCount=modCount+1;
          --root.size;
          Node<E> node;
          if(--size!=0)
          {
            if(index==0)
            {
              ((SubList<E>)this).bubbleUpsuffixchophead(node=this.head,node.next);
            }
            else
            {
              if((size-=index)==0)
              {
                Node<E> newTail;
                ((RefDblLnkSeq<E>)root).privatesettail(newTail=(node=this.tail).prev);
                ((SubList<E>)this).bubbleUprootchoptail(newTail);
              }
              else
              {
                if(index<=size)
                {
                  Node<E> before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node<E> after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList<E>)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            ((RefDblLnkSeq<E>)root).privateInit(null);
            ((SubList<E>)this).bubbleUpclearSuffix(node=this.head,node.prev,1);
          }
          return node.val;
        }
        @Override
        void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter)
        {
          final Node<E> tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList<E>)this).suffixCollapsehead(modCount,curr,tail,filter);
              return;
            }
            collapseHeadAndTail(modCount,curr,tail,filter);
            return;
          }
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          --root.size;
          ((SubList<E>)this).bubbleUpclearSuffix(curr,curr=curr.prev,1);
          ((RefDblLnkSeq<E>)root).privatesettail(curr);
        }
        private void bubbleUpcollapseHeadAndTail(Node<E> oldhead,Node<E> newhead,Node<E> newtail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList<E> curr=this;;)
          {
            curr.privateCollapseHeadAndTail(newSize,newhead,newtail);
            if((curr=curr.parent)==null)
            {
              break;
            }
            if(curr.head!=oldhead)
            {
              curr.bubbleUprootCollapsetail(numRemoved,newtail);
              break;
            }
          }
          newhead.joinnext(oldhead.next);
        }
        private void collapseHeadAndTail(int modCount,Node<E> head,Node<E> tail,Predicate<? super E> filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
              ((SubList<E>)this).bubbleUpclearSuffix(head,tail=head.next,oldSize);
              break;
            }
            ++numConsumed;
            if(!filter.test((tail=tail.next).val))
            {
              var headCandidate=head;
              for(;;)
              {
                if(numConsumed==oldSize)
                {
                  CheckedCollection.checkModCount(modCount,root.modCount);
                  --numConsumed;
                  break;
                }
                ++numConsumed;
                if(!filter.test((headCandidate=headCandidate.next).val))
                {
                  numConsumed-=2-headCandidate.collapseBodyHelper(root.new ModCountChecker(modCount),oldSize-numConsumed,tail,filter);
                  break;
                }
                bubbleUpcollapseHeadAndTail(head,headCandidate,tail,numConsumed);
                break;
              }
            }
            root.modCount=modCount+1;
            root.tail=tail;
            tail.next=null;
            root.size-=numConsumed;
          }
        }
        @Override
        void removeFirstHelper(int modCount,Node<E> curr)
        {
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          if(curr==tail)
          {
            ((SubList<E>)this).bubbleUpclearSuffix(curr,curr=curr.prev,1);
            ((RefDblLnkSeq<E>)root).privatesettail(curr);
          }
          else
          {
            ((SubList<E>)this).bubbleUpsuffixchophead(curr,curr.next);
          }
          --root.size;
        }
        @Override
        void ascItrRemove(AscendingItr<E> itr)
        {
          Node<E> lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked<E> root;
            CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            uncheckedItrRemove(lastRet,root);
            itr.modCount=modCount;
            --root.size;
            return;
          }
          throw new IllegalStateException();
        }
        @Override
        void bidirectItrRemove(BidirectionalItr<E> itr)
        {
          Node<E> lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked<E> root;
            CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            uncheckedItrRemove(lastRet,root);
            if(lastRet!=itr.cursor)
            {
              --itr.nextIndex;
            }
            itr.modCount=modCount;
            --root.size;
            return;
          }
          throw new IllegalStateException();
        }
        private void uncheckedItrRemove(Node<E> lastRet,Checked<E> root)
        {
          if(lastRet==tail)
          {
            if(lastRet==head)
            {
              ((SubList<E>)this).bubbleUpclearSuffix(lastRet,lastRet=lastRet.prev,1);
            }
            else
            {
              ((SubList<E>)this).bubbleUprootchoptail(lastRet=lastRet.prev);
            }
            ((RefDblLnkSeq<E>)root).privatesettail(lastRet);
          }
          else
          {
            if(lastRet==head)
            {
              ((SubList<E>)this).bubbleUpsuffixchophead(lastRet,lastRet.next);
            }
            else
            {
              ((SubList<E>)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
        @Override
        void prependHelper(E val)
        {
          ++this.modCount;
          Node<E> newNode,oldhead;
          head=newNode=new Node<E>((oldhead=head).prev,val,oldhead);
          SubList<E> parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpsuffixPushhead(newNode,oldhead);
          }
        }
        @Override
        void initHelper(Checked<E> root,E val)
        {
          final Node<E> before,newNode=new Node<E>(val);
          for(SubList<E> curr=this;;curr.size=1)
          {
            ((RefDblLnkSeq<E>)curr).privateInit(newNode);
            if((curr=curr.parent)==null)
            {
              before=root.tail;
              break;
            }
            if(curr.size!=0)
            {
              before=curr.tail;
              curr.bubbleUppushtail(newNode);
              break;
            }
          }
          before.joinnext(newNode);
        }
      }
      private static class BodyList<E> extends PrefixList<E>
      {
        private transient final int parentOffset;
        BodyList(Checked<E> root,SubList<E> parent,int parentOffset)
        {
          super(root,parent);
          this.parentOffset=parentOffset;
        }
        BodyList(Checked<E> root,SubList<E> parent,Node<E> onlyNode,int parentOffset)
        {
          super(root,parent,onlyNode);
          this.parentOffset=parentOffset;
        }
        BodyList(Checked<E> root,SubList<E> parent,Node<E> head,int size,Node<E> tail,int parentOffset)
        {
          super(root,parent,head,size,tail);
          this.parentOffset=parentOffset;
        }
        @Override
        void removeFirstHelper(int modCount,Node<E> curr)
        {
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          if(curr==tail)
          {
            Node<E> node;
            ((SubList<E>)this).bubbleUpclearBody(node=this.head,node,1);
          }
          else
          {
            ((SubList<E>)this).bubbleUpsuffixchophead(curr,curr.next);
          }
          --root.size;
        }
        @Override
        int getParentOffset()
        {
          return this.parentOffset;
        }
        @Override
        public void add(int index,E val)
        {
          final int modCount;
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          CheckedCollection.checkLo(index);
          int size;
          CheckedCollection.checkWriteHi(index,size=this.size);
          ++root.size;
          root.modCount=modCount+1;
          if(size!=0)
          {
            if(index==0)
            {
              Node<E> oldhead;
              bubbleUppushhead(oldhead=this.head,new Node<E>(oldhead.prev,val,oldhead));
            }
            else
            {
              if((size-=index)==0)
              {
                Node<E> oldtail;
                ((PrefixList<E>)this).bubbleUppushtail(oldtail=this.tail,new Node<E>(oldtail,val,oldtail.next));
              }
              else
              {
                Node<E> before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node<E>(before,val,after);
                ((SubList<E>)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            bubbleUpinit(new Node<E>(val));
          }
        }
        @Override
        public E remove(int index)
        {
          final Checked<E> root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          CheckedCollection.checkLo(index);
          int size;
          CheckedCollection.checkReadHi(index,size=this.size);
          root.modCount=modCount+1;
          --root.size;
          Node<E> node;
          if(--size!=0)
          {
            if(index==0)
            {
              ((SubList<E>)this).bubbleUpsuffixchophead(node=this.head,node.next);
            }
            else
            {
              if((size-=index)==0)
              {
                ((SubList<E>)this).bubbleUpprefixchoptail(node=this.tail,node.prev);
              }
              else
              {
                if(index<=size)
                {
                  Node<E> before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node<E> after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList<E>)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            ((SubList<E>)this).bubbleUpclearBody(node=this.head,node,1);
          }
          return node.val;
        }
        private void bubbleUpinit(Node<E> newNode)
        {
          SubList<E> curr=this;
          Node<E> before,after;
          for(;;)
          {
            ++curr.modCount;
            curr.size=1;
            ((RefDblLnkSeq<E>)curr).privateInit(newNode);
            int currParentOffset=curr.getParentOffset();
            if((curr=curr.parent)==null)
            {
              int tailDist;
              if(currParentOffset<=(tailDist=root.size-currParentOffset))
              {
                after=(before=root.head.iterateForward(currParentOffset-1)).next;
              }
              else
              {
                before=(after=root.tail.iterateReverse(tailDist-1)).prev;
              }
              break;
            }
            int currSize;
            if((currSize=curr.size)!=0)
            {
              if(currParentOffset==0)
              {
                before=(after=curr.head).prev;
                curr.bubbleUpsuffixPushhead(after,newNode);
              }
              else
              {
                if((currSize-=currParentOffset)==0)
                {
                  after=(before=curr.tail).next;
                  curr.bubbleUpprefixPushtail(before,newNode);
                }
                else
                {
                  if(currParentOffset<=currSize)
                  {
                    after=(before=curr.head.iterateForward(currParentOffset-1)).next;
                  }
                  else
                  {
                    before=(after=curr.tail.iterateReverse(currSize-1)).prev;
                  }
                  curr.bubbleUpincrementSize();
                }
              }
              break;
            }
          }
          before.joinnext(newNode);
          newNode.joinnext(after);
        }
        @Override
        void initHelper(E val)
        {
          final Checked<E> root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          bubbleUpinit(new Node<E>(val));
        }
        @Override
        void prependHelper(E val)
        {
          ++this.modCount;
          Node<E> newNode,oldhead;
          head=newNode=new Node<E>((oldhead=head).prev,val,oldhead);
          SubList<E> parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpsuffixPushhead(newNode,oldhead);
          }
        }
        @Override
        void initHelper(Checked<E> root,E val)
        {
          final Node<E> newNode;
          ((RefDblLnkSeq<E>)this).privateInit(newNode=new Node<E>(val));
          SubList<E> parent,curr;
          if((parent=(curr=this).parent)!=null)
          {
            do
            {
              int parentSize;
              if((parentSize=parent.size)!=0)
              {
                Node<E> before,after;
                int headDist,tailDist;
                if((headDist=curr.getParentOffset())==0)
                {
                  parent.bubbleUpsuffixPushhead(newNode,after=parent.head);
                  before=after.prev;
                }
                else if((tailDist=parentSize-headDist)==0)
                {
                  parent.bubbleUpprefixPushtail(newNode,before=parent.tail);
                  after=before.next;
                }
                else
                {
                  if(headDist<=tailDist)
                  {
                    after=(before=root.head.iterateForward(headDist-1)).next;
                  }
                  else
                  {
                    before=(after=root.tail.iterateReverse(tailDist-1)).prev;
                  }
                  parent.bubbleUpincrementSize();
                }
                before.joinnext(newNode);
                newNode.joinnext(after);
                return;
              }
              parent=(curr=parent).parent;
              curr.size=1;
              ((RefDblLnkSeq<E>)curr).privateInit(newNode);
            }
            while(parent!=null);
          }
          ((RefDblLnkSeq<E>)root).subSeqInsertHelper(newNode,curr.getParentOffset());
        }
        private void bubbleUppushhead(Node<E> oldhead,Node<E> newNode)
        {
          SubList<E> curr=this;
          do
          {
            ++curr.modCount;++curr.size;curr.head=newNode;
            if((curr=curr.parent)==null)
            {
              return;
            }
          }
          while(curr.head==oldhead);
          curr.bubbleUpincrementSize();
        }
        @Override
        void pushheadHelper(Node<E> oldhead,E val)
        {
          final Checked<E> root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          bubbleUppushhead(oldhead,new Node<E>((oldhead=this.head).prev,val,oldhead));
        }
        @Override
        public void clear()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList<E>)this).clearBody(size);
          }
        }
        private void bubbleUpcollapseHeadAndTail(Node<E> oldHead,Node<E> oldTail,Node<E> newHead,Node<E> newTail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList<E> curr=this;;)
          {
            curr.privateCollapseHeadAndTail(newSize,newHead,newTail);
            if((curr=curr.parent)==null)
            {
              break;
            }
            if(curr.head!=oldHead)
            {
              curr.bubbleUpprefixCollapsetail(numRemoved,oldTail,newTail);
              break;
            }
            if(curr.tail!=oldTail)
            {
              ++curr.modCount;
              curr.size-=numRemoved;
              curr.head=newHead;
              if((curr=curr.parent)!=null)
              {
                curr.bubbleUpsuffixCollapsehead(numRemoved,oldHead,newHead);
              }
              break;
            }
          }
          oldHead.prev.joinnext(newHead);
          newTail.joinnext(oldTail.next);
        }
        private void collapseHeadAndTail(int modCount,final Node<E> head,final Node<E> tail,final Predicate<? super E> filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var headCandidate=head;
          final var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
              ((SubList<E>)this).bubbleUpclearBody(head,tail,oldSize);
              break;
            }
            ++numConsumed;
            if(!filter.test((headCandidate=headCandidate.next).val))
            {
              var tailCandidate=tail;
              for(;;)
              {
                if(numConsumed==oldSize)
                {
                  CheckedCollection.checkModCount(modCount,root.modCount);
                  --numConsumed;
                  break;
                }
                ++numConsumed;
                if(filter.test((tailCandidate=tailCandidate.prev).val))
                {
                  numConsumed-=2-headCandidate.collapseBodyHelper(root.new ModCountChecker(modCount),oldSize-numConsumed,tailCandidate,filter);
                  break;
                }
              }
              bubbleUpcollapseHeadAndTail(head,tail,headCandidate,tailCandidate,numConsumed);
              break;
            }
          }
          root.modCount=modCount+1;
          root.size-=numConsumed;
        }
        @Override
        void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter)
        {
          final Node<E> tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList<E>)this).suffixCollapsehead(modCount,curr,tail,filter);
              return;
            }
            collapseHeadAndTail(modCount,curr,tail,filter);
            return;
          }
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          --root.size;
          ((SubList<E>)this).bubbleUpclearBody(curr,tail,1);
        }
        @Override
        void ascItrRemove(AscendingItr<E> itr)
        {
          Node<E> lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked<E> root;
            CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            uncheckedItrRemove(lastRet);
            itr.modCount=modCount;
            --root.size;
            return;
          }
          throw new IllegalStateException();
        }
        @Override
        void bidirectItrRemove(BidirectionalItr<E> itr)
        {
          Node<E> lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked<E> root;
            CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            uncheckedItrRemove(lastRet);
            if(lastRet!=itr.cursor)
            {
              --itr.nextIndex;
            }
            itr.modCount=modCount;
            --root.size;
            return;
          }
          throw new IllegalStateException();
        }
        private void uncheckedItrRemove(Node<E> lastRet)
        {
          if(lastRet==head)
          {
            if(lastRet==tail)
            {
              ((SubList<E>)this).bubbleUpclearBody(lastRet,lastRet,1);
            }
            else
            {
              ((SubList<E>)this).bubbleUpsuffixchophead(lastRet,lastRet.next);
            }
          }
          else
          {
            if(lastRet==tail)
            {
              ((SubList<E>)this).bubbleUpprefixchoptail(lastRet,lastRet.prev);
            }
            else
            {
              ((SubList<E>)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
      }
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker
    {
      public ModCountChecker(int expectedModCount)
      {
        super(expectedModCount);
      }
      @Override protected int getActualModCount()
      {
        return modCount;
      }
    }
    private static class AscendingItr<E> implements OmniIterator.OfRef<E>
    {
      transient final Checked<E> root;
      transient Node<E> cursor;
      transient Node<E> lastRet;
      transient int modCount;
      AscendingItr(Checked<E> root)
      {
        this.root=root;
        this.cursor=root.head;
        this.modCount=root.modCount;
      }
      AscendingItr(Checked<E> root,Node<E> cursor)
      {
        this.root=root;
        this.cursor=cursor;
        this.modCount=root.modCount;
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        Node<E> cursor;
        if((cursor=this.cursor)!=null)
        {
          final var root=this.root;
          int modCount=this.modCount;
          try
          {
            cursor=uncheckedForEach(cursor,root,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.cursor=null;
          this.lastRet=cursor;
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=null;
      }
      @Override
      public E next()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        Node<E> cursor;
        if((cursor=this.cursor)!=null)
        {
          this.cursor=iterate(cursor);
          this.lastRet=cursor;
          return cursor.val;
        }
        throw new NoSuchElementException();
      }
      @Override
      public void remove()
      {
        Node<E> lastRet;
        if((lastRet=this.lastRet)!=null)
        {
            int modCount;
            final Checked<E> root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            uncheckedRemove(lastRet,root);
            this.lastRet=null;
            return;
        }
        throw new IllegalStateException();
      }
      Node<E> iterate(Node<E> cursor)
      {
        return cursor.next;
      }
      Node<E> uncheckedForEach(Node<E> cursor,Checked<E> root,Consumer<? super E> action)
      {
        cursor.uncheckedForEachForward(cursor=root.tail,action);
        return cursor;
      }
      void uncheckedRemove(Node<E> lastRet,Checked<E> root)
      {
        if(--root.size==0)
        {
          ((RefDblLnkSeq<E>)root).privateInit(null);
        }
        else if(lastRet==root.head)
        {
          ((RefDblLnkSeq<E>)root).privatesethead(cursor);
        }
        else if(lastRet==root.tail)
        {
          ((RefDblLnkSeq<E>)root).privatechoptail(lastRet);
        }
        else
        {
          lastRet.prev.joinnext(cursor);
        }
      }
    }
    private static class DescendingItr<E> extends AscendingItr<E>
    {
      DescendingItr(Checked<E> root)
      {
        super(root,root.tail);
      }
      @Override
      Node<E> iterate(Node<E> cursor)
      {
        return cursor.prev;
      }
      @Override
      Node<E> uncheckedForEach(Node<E> cursor,Checked<E> root,Consumer<? super E> action)
      {
        cursor.uncheckedForEachReverse(action);
        return root.head;
      }
      @Override
      void uncheckedRemove(Node<E> lastRet,Checked<E> root)
      {
        if(--root.size==0)
        {
          ((RefDblLnkSeq<E>)root).privateInit(null);
        }
        else if(lastRet==root.head)
        {
          ((RefDblLnkSeq<E>)root).privatechophead(lastRet);
        }
        else if(lastRet==root.tail)
        {
          ((RefDblLnkSeq<E>)root).privatesettail(cursor);
        }
        else
        {
          cursor.joinnext(lastRet.next);
        }
      }
    }
    private static class BidirectionalItr<E> extends AscendingItr<E> implements OmniListIterator.OfRef<E>
    {
      private transient int nextIndex;
      BidirectionalItr(Checked<E> root)
      {
        super(root);
      }
      BidirectionalItr(Checked<E> root,Node<E> cursor,int index)
      {
        super(root,cursor);
        this.nextIndex=index;
      }
      public void add(E val)
      {
        int modCount;
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        if(++root.size!=1)
        {
          Node<E> cursor;
          if((cursor=this.cursor)==null)
          {
            root.tail=new Node<E>(root.tail,val);
          }
          else if(cursor==root.head)
          {
            root.head=new Node<E>(val,cursor);
          }
          else
          {
            new Node<E>(cursor.prev,val,cursor);
          }
        }
        else
        {
          ((RefDblLnkSeq<E>)root).privateInit(new Node<E>(val));
        }
        ++nextIndex;
        lastRet=null;
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        Node<E> cursor;
        if((cursor=this.cursor)!=null)
        {
          final var root=this.root;
          int modCount=this.modCount;
          try
          {
            cursor.uncheckedForEachForward(cursor=root.tail,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.nextIndex=root.size;
          this.cursor=null;
          lastRet=null;
        }
      }
      @Override
      public boolean hasPrevious()
      {
        return this.nextIndex!=0;
      }
      @Override
      public E next()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        Node<E> cursor;
        if((cursor=this.cursor)!=null)
        {
          ++nextIndex;
          this.cursor=cursor.next;
          lastRet=cursor;
          return cursor.val;
        }
        throw new NoSuchElementException();
      }
      @Override
      public int nextIndex()
      {
        return this.nextIndex;
      }
      @Override
      public E previous()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        int nextIndex;
        if((nextIndex=this.nextIndex)!=0)
        {
          this.nextIndex=nextIndex-1;
          Node<E> cursor;
          this.cursor=cursor=this.cursor.prev;
          lastRet=cursor;
          return cursor.val;
        }
        throw new NoSuchElementException();
      }
      @Override
      public int previousIndex()
      {
        return this.nextIndex-1;
      }
      @Override
      public void set(E val)
      {
        Node<E> lastRet;
        if((lastRet=this.lastRet)!=null)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      void uncheckedRemove(Node<E> lastRet,Checked<E> root)
      {
        if(--root.size!=0)
        {
          Node<E> cursor;
          if(lastRet!=(cursor=this.cursor))
          {
            --nextIndex;
            if(lastRet==root.head)
            {
              ((RefDblLnkSeq<E>)root).privatesethead(cursor);
            }
            else if(lastRet==root.tail)
            {
              ((RefDblLnkSeq<E>)root).privatechoptail(lastRet);
            }
            else
            {
              lastRet.prev.joinnext(cursor);
            }
          }
          else
          {
            if(lastRet==root.head)
            {
              ((RefDblLnkSeq<E>)root).privatechophead(lastRet);
            }
            else if(lastRet==root.tail)
            {
              ((RefDblLnkSeq<E>)root).privatechoptail(lastRet);
            }
            else
            {
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
        else
        {
          ((RefDblLnkSeq<E>)root).privateInit(null);
          if(lastRet!=cursor)
          {
            --nextIndex;
          }
        }
      }
    }
  }
  static class Node<E>
  {
    transient Node<E> prev;
    transient E val;
    transient Node<E> next;
    Node(E val)
    {
      this.val=val;
    }
    Node(Node<E> prev,E val)
    {
      this.prev=prev;
      this.val=val;
      prev.joinnext(this);
    }
    Node(E val,Node<E> next)
    {
      this.val=val;
      this.next=next;
      joinnext(next);
    }
    Node(Node<E> prev,E val,Node<E> next)
    {
      this.prev=prev;
      this.val=val;
      this.next=next;
      prev.joinnext(this);
      joinnext(next);
    }
    private int collapseBodyHelper(Checked<E>.ModCountChecker modCountChecker,int numLeft,Node<E> next,Predicate<? super E> filter)
    {
      int numRemoved=0;
      for(Node<E> before,prev=this;;prev=before)
      {
        if(numLeft==0)
        {
          modCountChecker.checkModCount();
          break;
        }
        --numLeft;
        if(filter.test((before=prev.next).val))
        {
          ++numRemoved;
          for(Node<E> after;;next=after)
          {
            if(numLeft==0)
            {
              modCountChecker.checkModCount();
              break;
            }
            --numLeft;
            if(filter.test((after=next.prev).val))
            {
              ++numRemoved;
              long[] survivorSet;
              int numSurvivors;
              if(numLeft!=0&&(numSurvivors=(before=before.next).markSurvivors(numLeft,survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0)
              {
                modCountChecker.checkModCount();
                numRemoved+=numLeft-numSurvivors;
                prev=prev.retainSurvivors(before,numSurvivors,survivorSet);
              }
              else
              {
                numRemoved+=numLeft;
                modCountChecker.checkModCount();
              }
              break;
            }
          }
          prev.joinnext(next);
          break;
        }
      }
      return numRemoved;
    }
    private int markSurvivors(int numLeft,long[] survivorSet,Predicate<? super E> filter)
    {
      long survivorWord;
      var begin=this;
      for(int survivorOffset=0,numSurvivors=0;;survivorSet[survivorOffset++]=survivorWord)
      {
        survivorWord=0L;
        long marker=1L;
        do
        {
          if(!filter.test(begin.val))
          {
            survivorWord|=marker;
            ++numSurvivors;
          }
          if(--numLeft==0)
          {
            survivorSet[survivorOffset]=survivorWord;
            return numSurvivors;
          }
          begin=begin.next;
        }
        while((marker<<=1)!=0);
      }
    }
    private Node<E> retainSurvivors(Node<E> curr,int numSurvivors,long[] survivorSet)
    {
      var lastKnownSurvivor=this;
      for(int survivorOffset=0;;++survivorOffset)
      {
        long survivorWord;
        int runLength;
        curr=curr.iterateForward(runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorOffset]));
        if(runLength!=64)
        {
          lastKnownSurvivor.joinnext(lastKnownSurvivor=curr);
          runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength));
          do
          {
            lastKnownSurvivor=lastKnownSurvivor.uncheckedIterateForward(runLength);
            if((numSurvivors-=runLength)==0)
            {
              return lastKnownSurvivor;
            }
            else if(runLength==64)
            {
              survivorWord=survivorSet[survivorOffset++];
            }
          }
          while((runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)))!=0);
          curr=lastKnownSurvivor;
        }
      }
    }
    private int collapseBodyHelper(Node<E> next,Predicate<? super E> filter)
    {
      int numRemoved=0;
      for(Node<E> curr,prev=this;(curr=prev.next)!=next;prev=curr)
      {
        if(filter.test(curr.val))
        {
          do
          {
            ++numRemoved;
            if((curr=curr.next)==next)
            {
              prev.joinnext(next);
              return numRemoved;
            }
          }
          while(filter.test(curr.val));
          prev.joinnext(curr);
        }
      }
      return numRemoved;
  }
    private void joinnext(Node<E> next)
    {
      this.next=next;
      next.prev=this;
    }
    private void joinprev(Node<E> prev)
    {
      this.prev=prev;
      prev.next=this;
    }
    private Node<E> iterateForward(int dist)
    {
      if(dist!=0)
      {
        return uncheckedIterateForward(dist);
      }
      return this;
    }
    private Node<E> iterateReverse(int dist)
    {
      if(dist!=0)
      {
        return uncheckedIterateReverse(dist);
      }
      return this;
    }
    private void uncheckedForEachForward(Node<E> end,Consumer<? super E> action)
    {
      for(var curr=this;;curr=curr.next)
      {
        action.accept(curr.val);
        if(curr==end)
        {
          return;
        }
      }
    }
    private void uncheckedForEachReverse(Consumer<? super E> action)
    {
      for(var curr=this;;)
      {
        action.accept(curr.val);
        if((curr=curr.prev)==null)
        {
          return;
        }
      }
    }
    private int uncheckedForwardHashCode(Node<E> end)
    {
      int hash=31+Objects.hashCode(this.val);
      for(var curr=this;curr!=end;hash=hash*31+Objects.hashCode((curr=curr.next).val))
      {  
      }
      return hash;
    }
    private void uncheckedForwardToString(Node<E> end,StringBuilder builder)
    {
      Node<E> curr;
      for(builder.append((curr=this).val);curr!=end;builder.append(',').append(' ').append((curr=curr.next).val))
      {
      }
    }
    private Node<E> uncheckedIterateForward(int dist)
    {
      var curr=next;
      while(--dist!=0)
      {
        curr=curr.next;
      }
      return curr;
    }
    private Node<E> uncheckedIterateReverse(int dist)
    {
      var curr=prev;
      while(--dist!=0)
      {
        curr=curr.prev;
      }
      return curr;
    }
    private void uncheckedReplaceAll(Node<E> end,UnaryOperator<E> operator)
    {
      for(var curr=this;;curr=curr.next)
      {
        curr.val=operator.apply(curr.val);
        if(curr==end)
        {
          return;
        }
      }
    }
    @SuppressWarnings("unchecked")
    private void copyFromArray(Object[] src,int srcOffset,int length)
    {
      for(Node<E> curr=this;;++srcOffset,curr=curr.next)
      {
        curr.val=(E)src[srcOffset];
        if(--length==0)
        {
          return;
        }
      }
    }
    private void uncheckedsort(int size,Node<E> end)
    {
      Object[] tmpArr;
      uncheckedCopyForward(tmpArr=new Object[size],0,size);
      RefSortUtil.uncheckedsort(tmpArr,0,size-1);
      copyFromArray(tmpArr,0,size);
    }
    private void uncheckedreverseSort(int size,Node<E> end)
    {
      Object[] tmpArr;
      uncheckedCopyForward(tmpArr=new Object[size],0,size);
      RefSortUtil.uncheckedreverseSort(tmpArr,0,size-1);
      copyFromArray(tmpArr,0,size);
    }
    private void uncheckedcomparatorSort(int size,Node<E> end,Comparator<? super E> sorter)
    {
      Object[] tmpArr;
      uncheckedCopyForward(tmpArr=new Object[size],0,size);
      RefSortUtil.uncheckedcomparatorSort(tmpArr,0,size-1,sorter);
      copyFromArray(tmpArr,0,size);
    }
    private void uncheckedCopyForward(Object[] dst,int dstOffset,int length)
    {
      for(var src=this;;++dstOffset,src=src.next)
      {
        dst[dstOffset]=(Object)(src.val);
        if(--length==0)
        {
          return;
        }
      }
    }
  }
  public static class Unchecked<E> extends RefDblLnkSeq<E> implements OmniDeque.OfRef<E>
  {
    public Unchecked()
    {
      super();
    }
    public Unchecked(Node<E> onlyNode)
    {
      super(onlyNode);
    }
    public Unchecked(Node<E> head,int size,Node<E> tail)
    {
      super(head,size,tail);
    }
    @Override
    public boolean removeIf(Predicate<? super E> filter)
    {
      final Node<E> head;
      return (head=this.head)!=null && super.uncheckedRemoveIf(head,filter);
    }
    @Override
    void initHelper(E val)
    {
      this.size=1;
      ((RefDblLnkSeq<E>)this).privateInit(new Node<E>(val));
    }
    @Override
    public void add(int index,E val)
    {
      int size;
      if((size=this.size)!=0)
      {
        super.uncheckedAddHelper(index,size+1,val);
      }
      else
      {
        this.size=1;
        ((RefDblLnkSeq<E>)this).privateInit(new Node<E>(val));
      }
    }
    @Override
    public E remove(int index)
    {
      Node<E> node;
      int size;
      if((size=this.size)!=1)
      {
        node=super.removeAtIndexHelper(index,size-1);
      }
      else
      {
        node=this.head;
        this.size=0;
        ((RefDblLnkSeq<E>)this).privateInit(null);
      }
      return node.val;
    }
    @Override
    public boolean remove(Object val)
    {
      Node<E> head;
      if((head=this.head)!=null)
      {
        {
          return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    private boolean uncheckedremoveVal(Node<E> head,Predicate<Object> pred)
    {
      if(!pred.test(head.val))
      {
        Node<E> lastVisited;
        do
        {
          if((head=(lastVisited=head).next)==null)
          {
            return false;
          }
        }
        while(!pred.test(head.val));
        --this.size;
        if(head==tail)
        {
          ((RefDblLnkSeq<E>)this).privatesettail(lastVisited);
        }
        else
        {
          lastVisited.joinnext(head.next);
        }
      }
      else
      {
        if(--this.size==0)
        {
          ((RefDblLnkSeq<E>)this).privateInit(null);
        }
        else
        {
          ((RefDblLnkSeq<E>)this).privatechophead(head);
        }
      }
      return true;
    }
    @Override
    public boolean removeVal(final boolean val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final int val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(final long val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final float val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final double val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final byte val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final char val)
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(final short val)
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(final Boolean val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Byte val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Character val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Short val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Integer val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Long val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Float val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(final Double val)
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(Object val)
    {
      Node<E> tail;
      if((tail=this.tail)!=null)
      {
        {
          return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    private boolean uncheckedremoveLastOccurrence(Node<E> tail,Predicate<Object> pred)
    {
      if(!pred.test(tail.val))
      {
        Node<E> lastVisited;
        do
        {
          if((tail=(lastVisited=tail).next)==null)
          {
            return false;
          }
        }
        while(!pred.test(tail.val));
        --this.size;
        if(tail==head)
        {
          ((RefDblLnkSeq<E>)this).privatesethead(lastVisited);
        }
        else
        {
          lastVisited.joinnext(tail.next);
        }
      }
      else
      {
        if(--this.size==0)
        {
          ((RefDblLnkSeq<E>)this).privateInit(null);
        }
        else
        {
          ((RefDblLnkSeq<E>)this).privatechoptail(tail);
        }
      }
      return true;
    }
    @Override
    public boolean removeLastOccurrence(final boolean val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final int val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        {
          return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final long val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final float val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final double val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final byte val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final char val)
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final short val)
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Boolean val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Byte val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Character val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Short val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Integer val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Long val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Float val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final Double val)
    {
      final Node<E> tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    void pushheadHelper(Node<E> oldhead,E val)
    {
      ++this.size;
      this.head=new Node<E>(val,this.head);
    }
    @Override
    void pushtailHelper(Node<E> oldtail,E val)
    {
      ++this.size;
      this.tail=new Node<E>(this.tail,val);
    }
    @Override
    public Object clone()
    {
      Node<E> newHead,newTail;
      final int size;
      if((size=this.size)!=0)
      {
        Node<E> oldHead,oldTail;
        for(newHead=new Node<E>((oldHead=this.head).val),newTail=newHead,oldTail=this.tail;oldHead!=oldTail;new Node<E>(newTail,(oldHead=oldHead.next).val))
        {
        }
      }
      else
      {
        newHead=null;
        newTail=null;
      }
      return new Unchecked<E>(newHead,size,newTail);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new AscendingItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator()
    {
      return new BidirectionalItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index)
    {
      final int tailDist;
      return new BidirectionalItr<E>(this,(tailDist=size-index)==0?null:super.privateGetNode(index,tailDist),index);
    }
    @Override
    public OmniIterator.OfRef<E> descendingIterator()
    {
      return new DescendingItr<E>(this);
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
    {
      final int tailDist=size-toIndex;
      int subListSize;
      switch(subListSize=toIndex-fromIndex)
      {
      default:
        return getDefaultSubList(fromIndex,subListSize,tailDist);
      case 1:
        return getSubList1(fromIndex,tailDist);
      case 0:
        return getEmptySubList(fromIndex,tailDist);
      }
    }
    private OmniList.OfRef<E> getDefaultSubList(int headDist,int subListSize,int tailDist)
    {
      final Node<E> subListHead=head;
      Node<E> subListTail=tail;
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList<E>(this,null,subListHead,subListSize,subListTail);
        }
        return new SubList.SuffixList<E>(this,null,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail);
      }
      subListTail=tailDist<=subListSize?subListTail.uncheckedIterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
      if(headDist==0)
      {
        return new SubList.PrefixList<E>(this,null,subListHead,subListSize,subListTail);
      }
      return new SubList.BodyList<E>(this,null,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail,headDist);
    }
    private OmniList.OfRef<E> getEmptySubList(int headDist,int tailDist)
    {
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList<E>(this,null);
        }
        return new SubList.SuffixList<E>(this,null);
      }
      else if(headDist==0)
      {
        return new SubList.PrefixList<E>(this,null);
      }
      return new SubList.BodyList<E>(this,null,headDist);
    }
    private OmniList.OfRef<E> getSubList1(int headDist,int tailDist)
    {
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList<E>(this,null,head);
        }
        return new SubList.SuffixList<E>(this,null,tail);
      }
      else if(headDist==0)
      {
        return new SubList.PrefixList<E>(this,null,head);
      }
      return new SubList.BodyList<E>(this,null,((RefDblLnkSeq<E>)this).privateGetNode(headDist,tailDist),headDist);
    }
    private static class AscendingItr<E> implements OmniIterator.OfRef<E>
    {
      transient final Unchecked<E> root;
      transient Node<E> cursor;
      AscendingItr(Unchecked<E> root)
      {
        this.root=root;
        cursor=root.head;
      }
      AscendingItr(Unchecked<E> root,Node<E> cursor)
      {
        this.root=root;
        this.cursor=cursor;
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        Node<E> cursor;
        if((cursor=this.cursor)!=null)
        {
          cursor.uncheckedForEachForward(root.tail,action);
          this.cursor=null;
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=null;
      }
      @Override
      public E next()
      {
        Node<E> lastRet;
        cursor=(lastRet=cursor).next;
        return lastRet.val;
      }
      @Override
      public void remove()
      {
        final RefDblLnkSeq<E> root;
        if(--(root=this.root).size!=0)
        {
          Node<E> cursor;
          if((cursor=this.cursor)!=null)
          {
            Node<E> lastRet;
            if((lastRet=cursor.prev)==root.head)
            {
              root.privatesethead(cursor);
            }
            else
            {
              lastRet.prev.joinnext(cursor);
            }
          }
          else
          {
            root.privatechoptail(root.tail);
          }
        }
        else
        {
          root.privateInit(null);
        }
      }
    }
    private static class DescendingItr<E> extends AscendingItr<E>
    {
      DescendingItr(Unchecked<E> root)
      {
        super(root,root.tail);
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        Node<E> cursor;
        if((cursor=this.cursor)!=null)
        {
          cursor.uncheckedForEachReverse(action);
          this.cursor=null;
        }
      }
      @Override public E next()
      {
        Node<E> lastRet;
        cursor=(lastRet=cursor).prev;
        return lastRet.val;
      }
      @Override public void remove()
      {
        final RefDblLnkSeq<E> root;
        if(--(root=this.root).size!=0)
        {
          Node<E> cursor;
          if((cursor=this.cursor)!=null)
          {
            Node<E> lastRet;
            if((lastRet=cursor.next)==root.tail)
            {
              root.privatesettail(cursor);
            }
            else
            {
              cursor.joinnext(lastRet.next);
            }
          }
          else
          {
            root.privatechophead(root.head);
          }
        }
        else
        {
          root.privateInit(null);
        }
      }
    }
    static class BidirectionalItr<E> extends AscendingItr<E> implements OmniListIterator.OfRef<E>
    {
      private transient Node<E> lastRet;
      private transient int nextIndex;
      BidirectionalItr(Unchecked<E> root)
      {
        super(root);
      }
      BidirectionalItr(Unchecked<E> root,Node<E> cursor,int nextIndex)
      {
        super(root,cursor);
        this.nextIndex=nextIndex;
      }
      @Override
      public void add(E val)
      {
        final RefDblLnkSeq<E> root;
        if(++(root=this.root).size!=1)
        {
          Node<E> cursor;
          if((cursor=this.cursor)==null)
          {
            root.tail=new Node<E>(root.tail,val);
          }
          else if(cursor==root.head)
          {
            root.head=new Node<E>(val,cursor);
          }
          else
          {
            new Node<E>(cursor.prev,val,cursor);
          }
        }
        else
        {
          root.privateInit(new Node<E>(val));
        }
        ++nextIndex;
        lastRet=null;
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        Node<E> cursor;
        if((cursor=this.cursor)!=null)
        {
          Node<E> bound;
          Unchecked<E> root;
          cursor.uncheckedForEachForward(bound=(root=this.root).tail,action);
          this.cursor=null;
          lastRet=bound;
          nextIndex=root.size;
        }
      }
      @Override
      public boolean hasPrevious()
      {
        return nextIndex!=0;
      }
      @Override
      public E next()
      {
        Node<E> lastRet;
        this.lastRet=lastRet=cursor;
        cursor=lastRet.next;
        ++nextIndex;
        return lastRet.val;
      }
      @Override
      public int nextIndex()
      {
        return nextIndex;
      }
      @Override
      public E previous()
      {
        Node<E> lastRet;
        this.lastRet=lastRet=cursor.prev;
        cursor=lastRet;
        --nextIndex;
        return lastRet.val;
      }
      @Override
      public int previousIndex()
      {
        return nextIndex-1;
      }
      @Override
      public void remove()
      {
        final var lastRet=this.lastRet;
        final RefDblLnkSeq<E> root;
        if(--(root=this.root).size!=0)
        {
          Node<E> cursor;
          if(lastRet!=(cursor=this.cursor))
          {
            --nextIndex;
            if(lastRet==root.head)
            {
              root.privatesethead(cursor);
            }
            else if(lastRet==root.tail)
            {
              root.privatechoptail(lastRet);
            }
            else
            {
              lastRet.prev.joinnext(cursor);
            }
          }
          else
          {
            if(lastRet==root.head)
            {
              root.privatechophead(lastRet);
            }
            else if(lastRet==root.tail)
            {
              root.privatechoptail(lastRet);
            }
            else
            {
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
        else
        {
          root.privateInit(null);
          if(lastRet!=cursor)
          {
            --nextIndex;
          }
        }
        this.lastRet=null;
      }
      @Override
      public void set(E val)
      {
        lastRet.val=val;
      }
    }
    private static class SubList<E> extends RefDblLnkSeq<E>
    {
      transient final Unchecked<E> root;
      transient final SubList<E> parent;
      SubList(Unchecked<E> root,SubList<E> parent)
      {
        super();
        this.root=root;
        this.parent=parent;
      }
      SubList(Unchecked<E> root,SubList<E> parent,Node<E> onlyNode)
      {
        super(onlyNode);
        this.root=root;
        this.parent=parent;
      }
      SubList(Unchecked<E> root,SubList<E> parent,Node<E> head,int size,Node<E> tail)
      {
        super(head,size,tail);
        this.root=root;
        this.parent=parent;
      }
      @Override
      public void clear()
      {
        if(size!=0)
        {
          ((SubList<E>)this).clearRoot();
        }
      }
      int getParentOffset()
      {
        return 0;
      }
      @Override
      public boolean remove(final Object val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final boolean val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final int val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          {
            return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final long val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final float val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final double val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final byte val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final char val)
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final short val)
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final Boolean val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Byte val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Character val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Short val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Integer val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Long val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Float val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Double val)
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public void add(int index,E val)
      {
        final Unchecked<E> root;
        ++(root=this.root).size;
        int size;
        if((size=this.size)!=0)
        {
          if(index==0)
          {
            Node<E> newNode;
            root.head=newNode=new Node<E>(val,this.head);
            ((SubList<E>)this).bubbleUppushhead(newNode);
          }
          else
          {
            if((size-=index)==0)
            {
              Node<E> newNode;
              root.tail=newNode=new Node<E>(this.tail,val);
              ((SubList<E>)this).bubbleUppushtail(newNode);
            }
            else
            {
              Node<E> before,after;
              if(index<=size)
              {
                after=(before=this.head.iterateForward(index-1)).next;
              }
              else
              {
                before=(after=this.tail.iterateReverse(size-1)).prev;
              }
              new Node<E>(before,val,after);
              ((SubList<E>)this).bubbleUpincrementSize();
            }
          }
        }
        else
        {
          Node<E> newNode;
          ((RefDblLnkSeq<E>)root).privateInit(newNode=new Node<E>(val));
          bubbleUpinit(newNode);
        }
      }
      private static class AscendingItr<E> implements OmniIterator.OfRef<E>
      {
        transient final SubList<E> parent;
        transient Node<E> cursor;
        AscendingItr(SubList<E> parent)
        {
          this.parent=parent;
          cursor=parent.head;
        }
        AscendingItr(SubList<E> parent,Node<E> cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action)
        {
          Node<E> cursor;
          if((cursor=this.cursor)!=null)
          {
            cursor.uncheckedForEachForward(parent.tail,action);
            this.cursor=null;
          }
        }
        @Override
        public boolean hasNext()
        {
          return this.cursor!=null;
        }
        @Override
        public E next()
        {
          Node<E> cursor;
          if((cursor=this.cursor)==parent.tail)
          {
            this.cursor=null;
          }
          else
          {
            this.cursor=cursor.next;
          }
          return cursor.val;
        }
        @Override
        public void remove()
        {
          parent.ascItrRemove(cursor);
        }
      }
      private static class BidirectionalItr<E> extends AscendingItr<E> implements OmniListIterator.OfRef<E>
      {
        private transient int nextIndex;
        private transient Node<E> lastRet;
        private BidirectionalItr(SubList<E> parent)
        {
          super(parent);
        }
        private BidirectionalItr(SubList<E> parent,Node<E> cursor,int nextIndex)
        {
          super(parent,cursor);
          this.nextIndex=nextIndex;
        }
        @Override
        public void add(E val)
        {
          final int nextIndex=this.nextIndex++;
          int size;
          final SubList<E> parent;
          final var root=(parent=this.parent).root;
          if((size=parent.size++)!=0)
          {
            if(nextIndex==0)
            {
              parent.prependHelper(val);
            }
            else if(nextIndex==size)
            {
              parent.appendHelper(val);
            }
            else
            {
              Node<E> cursor;
              parent.insertHelper((cursor=this.cursor).prev,val,cursor);
            }
          }
          else
          {
            parent.initHelper(root,val);
          }
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action)
        {
          Node<E> cursor;
          if((cursor=this.cursor)!=null)
          {
            SubList<E> parent;
            cursor.uncheckedForEachForward(cursor=(parent=this.parent).tail,action);
            nextIndex=parent.size;
            this.cursor=null;
            lastRet=cursor;
          }
        }
        @Override
        public boolean hasPrevious()
        {
          return nextIndex!=0;
        }
        @Override
        public E next()
        {
          Node<E> cursor;
          if((cursor=this.cursor)==parent.tail)
          {
            this.cursor=null;
          }
          else
          {
            this.cursor=cursor.next;
          }
          ++nextIndex;
          lastRet=cursor;
          return cursor.val;
        }
        @Override
        public int nextIndex()
        {
          return nextIndex;
        }
        @Override
        public E previous()
        {
          Node<E> lastRet;
          this.lastRet=lastRet=cursor.prev;
          cursor=lastRet;
          --nextIndex;
          return lastRet.val;
        }
        @Override
        public int previousIndex()
        {
          return nextIndex-1;
        }
        @Override
        public void remove()
        {
          Node<E> lastRet;
          parent.bidirectItrRemove(lastRet=this.lastRet);
          if(lastRet!=cursor)
          {
            --nextIndex;
          }
          this.lastRet=null;
        }
        @Override
        public void set(E val)
        {
          lastRet.val=val;
        }
      }
      @Override
      void initHelper(E val)
      {
        RefDblLnkSeq<E> root;
        (root=this.root).size=1;
        Node<E> newNode;
        root.privateInit(newNode=new Node<E>(val));
        bubbleUpinit(newNode);
      }
      void removeFirstHelper(Node<E> curr)
      {
        final var root=this.root;
        if(curr==tail)
        {
          bubbleUpclearRoot();
          ((RefDblLnkSeq<E>)root).privateInit(null);
        }
        else
        {
          bubbleUprootchophead(curr=curr.next);
          ((RefDblLnkSeq<E>)root).privatesethead(curr);
        }
        --root.size;
      }
      @Override
      void pushheadHelper(Node<E> oldhead,E val)
      {
        final Unchecked<E> root;
        ++(root=this.root).size;
        Node<E> newNode;
        root.head=newNode=new Node<E>(val,oldhead);
        bubbleUppushhead(newNode);
      }
      private void bubbleUppushhead(Node<E> newhead)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.size;curr.head=newhead;
        }
        while((curr=curr.parent)!=null);
      }
      @Override
      void pushtailHelper(Node<E> oldtail,E val)
      {
        final Unchecked<E> root;
        ++(root=this.root).size;
        Node<E> newNode;
        root.tail=newNode=new Node<E>(oldtail,val);
        bubbleUppushtail(newNode);
      }
      private void bubbleUppushtail(Node<E> newtail)
      {
        SubList<E> curr=this;
        do
        {
          ++curr.size;curr.tail=newtail;
        }
        while((curr=curr.parent)!=null);
      }
      private void privateCollapseHeadAndTail(int size,Node<E> head,Node<E> tail)
      {
        this.size=size;
        this.head=head;
        this.tail=tail;
      }
      @Override
      public E remove(int index)
      {
        Unchecked<E> root;
        --(root=this.root).size;
        Node<E> node;
        int size;
        if((size=--this.size)!=0)
        {
          if(index==0)
          {
            Node<E> newHead;
            ((RefDblLnkSeq<E>)root).privatesethead(newHead=(node=this.head).next);
            ((SubList<E>)this).bubbleUprootchophead(newHead);
          }
          else
          {
            if((size-=index)==0)
            {
              Node<E> newTail;
              ((RefDblLnkSeq<E>)root).privatesettail(newTail=(node=this.tail).prev);
              ((SubList<E>)this).bubbleUprootchoptail(newTail);
            }
            else
            {
              if(index<=size)
              {
                Node<E> before;
                (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
              }
              else
              {
                Node<E> after;
                (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
              }
              ((SubList<E>)this).bubbleUpdecrementSize();
            }
          }
        }
        else
        {
          node=this.head;
          ((RefDblLnkSeq<E>)root).privateInit(null);
          bubbleUpclearRoot();
        }
        return node.val;
      }
      private void bubbleUpsuffixchophead(Node<E> oldHead,Node<E> newHead)
      {
        SubList<E> curr=this;
        do
        {
           --curr.size;curr.head=newHead;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.head==oldHead);
        curr.bubbleUpdecrementSize();oldHead.prev.joinnext(newHead);
      }
      private void bubbleUpprefixchoptail(Node<E> oldTail,Node<E> newTail)
      {
        SubList<E> curr=this;
        do
        {
           --curr.size;curr.tail=newTail;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.tail==oldTail);
        curr.bubbleUpdecrementSize();newTail.joinnext(oldTail.next);
      }
      private void bubbleUpclearRoot()
      {
        SubList<E> curr=this;
        do
        {
           curr.size=0;((RefDblLnkSeq<E>)curr).privateInit(null);
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpincrementSize()
      {
        SubList<E> curr=this;
        do
        {
           ++curr.size;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpdecrementSize()
      {
        SubList<E> curr=this;
        do
        {
           --curr.size;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpdecrementSize(int numRemoved)
      {
        SubList<E> curr=this;
        do
        {
           curr.size-=numRemoved;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchophead(Node<E> newHead)
      {
        SubList<E> curr=this;
        do
        {
           --curr.size;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchoptail(Node<E> newTail)
      {
        SubList<E> curr=this;
        do
        {
           --curr.size;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchophead(int numRemoved,Node<E> newHead)
      {
        SubList<E> curr=this;
        do
        {
           curr.size-=numRemoved;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchoptail(int numRemoved,Node<E> newTail)
      {
        SubList<E> curr=this;
        do
        {
           curr.size-=numRemoved;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpprefixPushtail(Node<E> oldTail,Node<E> newTail)
      {
        SubList<E> curr=this;
        do
        {
           ++curr.size;curr.tail=newTail;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.tail==oldTail);
        curr.bubbleUpincrementSize();
      }
      private void bubbleUpsuffixPushhead(Node<E> oldHead,Node<E> newHead)
      {
        SubList<E> curr=this;
        do
        {
           ++curr.size;curr.head=newHead;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.head==oldHead);
        curr.bubbleUpincrementSize();
      }
      private void bubbleUpclearPrefix(Node<E> oldTail,Node<E> newHead,int numRemoved)
      {
        SubList<E> curr=this;
        do
        {
           curr.size=0;((RefDblLnkSeq<E>)curr).privateInit(null);
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.tail==oldTail);
        curr.bubbleUprootchophead(numRemoved,newHead);
      }
      private void bubbleUpclearSuffix(Node<E> oldHead,Node<E> newTail,int numRemoved)
      {
        SubList<E> curr=this;
        do
        {
           curr.size=0;((RefDblLnkSeq<E>)curr).privateInit(null);
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.head==oldHead);
        curr.bubbleUprootchoptail(numRemoved,newTail);
      }
      private void bubbleUpclearBody(Node<E> oldhead,Node<E> oldtail,int numRemoved)
      {
        Node<E> prev,next;
        (prev=oldhead.prev).joinnext(next=tail.next);
        var curr=this;
        for(;;)
        {
          curr.size=0;
          ((RefDblLnkSeq<E>)curr).privateInit(null);
          if((curr=curr.parent)==null)
          {
            return;
          }
          if(curr.head!=oldhead)
          {
            while(curr.tail==oldtail)
            {
              curr.size-=numRemoved;
              curr.tail=prev;
              if((curr=curr.parent)==null)
              {
                return;
              }
            }
            break;
          }
          if(curr.tail!=oldtail)
          {
            do
            {
              curr.size-=numRemoved;
              curr.head=next;
              if((curr=curr.parent)==null)
              {
                return;
              }
            }
            while(curr.head==oldhead);
            break;
          }
        }
        curr.bubbleUpdecrementSize(numRemoved);
      }
      private void bubbleUpprefixCollapsetail(int numRemoved,Node<E> oldTail,Node<E> newTail)
      {
        SubList<E> curr=this;
        while(curr.tail==oldTail)
        {
           curr.size-=numRemoved;curr.tail=newTail;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        curr.bubbleUpdecrementSize(numRemoved);
      }
      private void bubbleUpsuffixCollapsehead(int numRemoved,Node<E> oldHead,Node<E> newHead)
      {
        SubList<E> curr=this;
        while(curr.head==oldHead)
        {
           curr.size-=numRemoved;curr.head=newHead;
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        curr.bubbleUpdecrementSize(numRemoved);
      }
      private void bubbleUprootCollapsehead(int numRemoved,Node<E> newHead)
      {
        SubList<E> curr=this;
        do
        {
           curr.size-=numRemoved;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootCollapsetail(int numRemoved,Node<E> newTail)
      {
        SubList<E> curr=this;
        do
        {
           curr.size-=numRemoved;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpcollapseHeadAndTail(Node<E> newHead,Node<E> newTail,int newSize)
      {
        SubList<E> curr=this;
        do
        {
          curr.privateCollapseHeadAndTail(newSize,newHead,newTail);
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpinit(Node<E> newNode)
      {
        SubList<E> curr=this;
        do
        {
           curr.size=1;((RefDblLnkSeq<E>)curr).privateInit(newNode);
        }
        while((curr=curr.parent)!=null);
      }
      @Override
      public boolean equals(Object val)
      {
        //TODO implements equals method
        return false;
      }
      @Override
      public Object clone()
      {
        Node<E> newHead,newTail;
        final int size;
        if((size=this.size)!=0)
        {
          Node<E> oldHead,oldTail;
          for(newHead=new Node<E>((oldHead=this.head).val),newTail=newHead,oldTail=this.tail;oldHead!=oldTail;new Node<E>(newTail,(oldHead=oldHead.next).val))
          {
          }
        }
        else
        {
          newHead=null;
          newTail=null;
        }
        return new Unchecked<E>(newHead,size,newTail);
      }
      @Override
      public OmniIterator.OfRef<E> iterator()
      {
        return new AscendingItr<E>(this);
      }
      @Override
      public OmniListIterator.OfRef<E> listIterator()
      {
        return new BidirectionalItr<E>(this);
      }
      @Override
      public OmniListIterator.OfRef<E> listIterator(int index)
      {
        return new BidirectionalItr<E>(this,super.getItrNode(index,size-index),index);
      }
      boolean uncheckedremoveValHelper(Node<E> curr,Predicate<Object> pred)
      {
        final var root=this.root;
        final var tail=this.tail;
        Node<E> prev;
        do
        {
          if(curr==tail)
          {
            return false;
          }
        }
        while(!pred.test((curr=(prev=curr).next).val));
        --root.size;
        if(curr==tail)
        {
          bubbleUprootchoptail(prev);root.tail=prev;prev.next=null;
        }
        else
        {
          ((SubList<E>)this).bubbleUpdecrementSize();
          prev.joinnext(curr.next);
        }
        return true;
      }
      @Override
      public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
      {
        final int tailDist=size-toIndex;
        int subListSize;
        switch(subListSize=toIndex-fromIndex)
        {
        default:
          return getDefaultSubList(root,fromIndex,subListSize,tailDist);
        case 1:
          return getSubList1(root,fromIndex,tailDist);
        case 0:
          return getEmptySubList(root,fromIndex,tailDist);
        }
      }
      @Override
      public boolean removeIf(Predicate<? super E> filter)
      {
        final Node<E> head;
        return (head=this.head)!=null && this.uncheckedRemoveIf(head,filter);
      }
      private boolean uncheckedRemoveIf(Node<E> head,Predicate<? super E> filter)
      {
        if(filter.test(head.val))
        {
          findNewHead(head,filter);
          return true;
        }
        final Node<E> tail;
        if(head!=(tail=this.tail))
        {
          if(!filter.test(tail.val))
          {
            return collapseBody(head,tail,filter);
          }
          collapseTail(head,tail,filter);
          return true;
        }
        return false;
      }
      private OmniList.OfRef<E> getDefaultSubList(Unchecked<E> root,int headDist,int subListSize,int tailDist)
      {
        final Node<E> subListHead=head;
        Node<E> subListTail=tail;
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList<E>(root,this,subListHead,subListSize,subListTail);
          }
          return new SubList.SuffixList<E>(root,this,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail);
        }
        subListTail=tailDist<=subListSize?subListTail.uncheckedIterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
        if(headDist==0)
        {
          return new SubList.PrefixList<E>(root,this,subListHead,subListSize,subListTail);
        }
        return new SubList.BodyList<E>(root,this,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail,headDist);
      }
      private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist,int tailDist)
      {
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList<E>(root,this);
          }
          return new SubList.SuffixList<E>(root,this);
        }
        else if(headDist==0)
        {
          return new SubList.PrefixList<E>(root,this);
        }
        return new SubList.BodyList<E>(root,this,headDist);
      }
      private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist)
      {
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList<E>(root,this,head);
          }
          return new SubList.SuffixList<E>(root,this,tail);
        }
        else if(headDist==0)
        {
          return new SubList.PrefixList<E>(root,this,head);
        }
        return new SubList.BodyList<E>(root,this,((RefDblLnkSeq<E>)this).privateGetNode(headDist,tailDist),headDist);
      }
      private void collapseHeadAndTail(Node<E> head,Node<E> tail,Predicate<? super E> filter)
      {
        final int oldSize=this.size;
        int numConsumed=2;
        final var root=this.root;
        for(;;)
        {
          if(numConsumed==oldSize)
          {
            bubbleUpclearRoot();
            ((RefDblLnkSeq<E>)root).privateInit(null);
            break;
          }
          ++numConsumed;
          if(!filter.test((head=head.next).val))
          {
            for(;;)
            {
              if(numConsumed==oldSize)
              {
                --numConsumed;
                break;
              }
              ++numConsumed;
              if(filter.test((tail=tail.prev).val))
              {
                numConsumed-=2-head.collapseBodyHelper(tail,filter);
                break;
              }
            }
            bubbleUpcollapseHeadAndTail(head,tail,numConsumed);
            ((RefDblLnkSeq<E>)root).privatesethead(head);
            ((RefDblLnkSeq<E>)root).privatesettail(tail);
            break;
          }
        }
        root.size-=numConsumed;
      }
      private void suffixCollapsehead(Node<E> head,Node<E> tail,Predicate<? super E> filter)
      {
        final int oldSize=this.size;
        int numConsumed=2;
        final var root=this.root;
        var headCandidate=head.next;
        for(;;headCandidate=headCandidate.next)
        {
          if(numConsumed==oldSize)
          {
            --numConsumed;
            break;
          }
          ++numConsumed;
          if(!filter.test(headCandidate.val))
          {
            numConsumed-=2-headCandidate.collapseBodyHelper(tail,filter);
            break;
          }
        }
        bubbleUpsuffixCollapsehead(numConsumed,head,headCandidate);
        root.size-=numConsumed;
        head.prev.joinnext(headCandidate);
      }
      private void rootCollapsehead(Node<E> head,Node<E> tail,Predicate<? super E> filter)
      {
        final int oldSize=this.size;
        int numConsumed=2;
        final var root=this.root;
        for(;;)
        {
          head=head.next;
          if(numConsumed==oldSize)
          {
            --numConsumed;
            break;
          }
          ++numConsumed;
          if(!filter.test(head.val))
          {
            numConsumed-=2-head.collapseBodyHelper(tail,filter);
            break;
          }
        }
        bubbleUprootCollapsehead(numConsumed,head);
        ((RefDblLnkSeq<E>)root).privatesethead(head);
        root.size-=numConsumed;
      }
      private void clearRoot()
      {
        final RefDblLnkSeq<E> root;
        (root=this.root).size=0;
        root.privateInit(null);
        bubbleUpclearRoot();
      }
      private void clearPrefix(int numRemoved)
      {
        Node<E> oldtail;
        Node<E> newhead=(oldtail=this.tail).next;
        final RefDblLnkSeq<E> root;
        (root=this.root).size-=numRemoved;
        root.privatesethead(newhead);
        bubbleUpclearPrefix(oldtail,newhead,numRemoved);
      }
      private void clearSuffix(int numRemoved)
      {
        Node<E> oldhead;
        Node<E> newtail=(oldhead=this.head).prev;
        final RefDblLnkSeq<E> root;
        (root=this.root).size-=numRemoved;
        root.size-=numRemoved;
        root.privatesettail(newtail);
        bubbleUpclearSuffix(oldhead,newtail,numRemoved);
      }
      private void clearBody(int numRemoved)
      {
        root.size-=numRemoved;
        bubbleUpclearBody(this.head,this.tail,numRemoved);
      }
            void findNewHead(Node<E> curr,Predicate<? super E> filter)
            {
              final Node<E> tail;
              if(curr!=(tail=this.tail))
              {
                if(!filter.test(tail.val))
                {
                  rootCollapsehead(curr,tail,filter);
                  return;
                }
                collapseHeadAndTail(curr,tail,filter);
                return;
              }
              final RefDblLnkSeq<E> root;(root=this.root).privateInit(null);
              root.size=0;
              bubbleUpclearRoot();
            }
      void collapseTail(Node<E> head,Node<E> tail,Predicate<? super E> filter)
      {
        int numRemoved;
        for(numRemoved=1;(tail=tail.prev)!=head;++numRemoved)
        {
          if(!filter.test(tail.val))
          {
            numRemoved+=head.collapseBodyHelper(tail,filter);
            break;
          }
        }
        bubbleUprootCollapsetail(numRemoved,tail);
        final RefDblLnkSeq<E> root;
        (root=this.root).privatesettail(tail);
        root.size-=numRemoved;
      }
      @Override
      boolean collapseBody(Node<E> prev,Node<E> next,Predicate<? super E> filter)
      {
        for(Node<E> curr;(curr=prev.next)!=next;prev=curr)
        {
          if(filter.test(curr.val))
          {
            int numRemoved;
            for(numRemoved=1;(curr=curr.next)!=tail;++numRemoved)
            {
              if(!filter.test(curr.val))
              {
                numRemoved+=curr.collapseBodyHelper(next,filter);
                break;
              }
            }
            prev.joinnext(curr);
            bubbleUpdecrementSize(numRemoved);
            root.size-=numRemoved;
            return true;
          }
        }
        return false;
      }
      private boolean uncheckedremoveVal(Node<E> head,Predicate<Object> pred)
      {
        if(!pred.test(head.val))
        {
          return uncheckedremoveValHelper(head,pred);
        }
        removeFirstHelper(head);
        return true;
      }
      void initHelper(Unchecked<E> root,E val)
      {
        final var newNode=new Node<E>(val);
        for(var curr=this;;curr.size=1)
        {
          ((RefDblLnkSeq<E>)curr).privateInit(newNode);
          if((curr=curr.parent)==null)
          {
            ((RefDblLnkSeq<E>)root).privateInit(newNode);
            return;
          }
        }
      }
      void prependHelper(E val)
      {
        Node<E> newNode;
        this.head=newNode=new Node<E>(val,this.head);
        SubList<E> parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUppushhead(newNode);
        }
      }
      void appendHelper(E val)
      {
        Node<E> newNode;
        this.tail=newNode=new Node<E>(this.tail,val);
        SubList<E> parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUppushtail(newNode);
        }
      }
      private void insertHelper(Node<E> before,E val,Node<E> after)
      {
        new Node<E>(before,val,after);
        SubList<E> parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUpincrementSize();
        }
      }
      void ascItrRemove(Node<E> cursor)
      {
        final Unchecked<E> root;
        --(root=this.root).size;
        if(cursor!=null)
        {
          Node<E> lastRet;
          if((lastRet=cursor.prev)==head)
          {
            ((SubList<E>)this).bubbleUprootchophead(cursor);
            ((RefDblLnkSeq<E>)root).privatesethead(cursor);
          }
          else
          {
            ((SubList<E>)this).bubbleUpdecrementSize();
            lastRet.prev.joinnext(cursor);
          }
        }
        else
        {
          if(size==1)
          {
            ((SubList<E>)this).bubbleUpclearRoot();
            ((RefDblLnkSeq<E>)root).privateInit(null);
          }
          else
          {
            ((SubList<E>)this).bubbleUprootchoptail(cursor=tail.prev);
            ((RefDblLnkSeq<E>)root).privatesettail(cursor);
          }
        }
      }
      void bidirectItrRemove(Node<E> lastRet)
      {
        final Unchecked<E> root;
        --(root=this.root).size;
        if(lastRet==tail)
        {
          if(lastRet==head)
          {
            ((SubList<E>)this).bubbleUpclearRoot();
            ((RefDblLnkSeq<E>)root).privateInit(null);
          }
          else
          {
            ((SubList<E>)this).bubbleUprootchoptail(lastRet=lastRet.prev);
            ((RefDblLnkSeq<E>)root).privatesettail(lastRet);
          }
        }
        else
        {
          if(lastRet==head)
          {
            ((SubList<E>)this).bubbleUprootchophead(lastRet=lastRet.next);
            ((RefDblLnkSeq<E>)root).privatesethead(lastRet);
          }
          else
          {
            ((SubList<E>)this).bubbleUpdecrementSize();
            lastRet.prev.joinnext(lastRet.next);
          }
        }
      }
      private static class PrefixList<E> extends SubList<E>
      {
        PrefixList(Unchecked<E> root,SubList<E> parent)
        {
          super(root,parent);
        }
        PrefixList(Unchecked<E> root,SubList<E> parent,Node<E> onlyNode)
        {
          super(root,parent,onlyNode);
        }
        PrefixList(Unchecked<E> root,SubList<E> parent,Node<E> head,int size,Node<E> tail)
        {
          super(root,parent,head,size,tail);
        }
        @Override
        public void add(int index,E val)
        {
          final Unchecked<E> root;
          ++(root=this.root).size;
          int size;
          if((size=this.size)!=0)
          {
            if(index==0)
            {
              Node<E> newNode;
              root.head=newNode=new Node<E>(val,this.head);
              ((SubList<E>)this).bubbleUppushhead(newNode);
            }
            else
            {
              if((size-=index)==0)
              {
                Node<E> oldtail;
                ((PrefixList<E>)this).bubbleUppushtail(oldtail=this.tail,new Node<E>(oldtail,val,oldtail.next));
              }
              else
              {
                Node<E> before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node<E>(before,val,after);
                ((SubList<E>)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            Node<E> newNode;
            root.head=newNode=new Node<E>(val,root.head);
            bubbleUpinit(newNode);
          }
        }
        @Override
        void appendHelper(E val)
        {
          Node<E> newNode,oldtail;
          tail=newNode=new Node<E>(oldtail=tail,val,oldtail.next);
          SubList<E> parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpprefixPushtail(newNode,oldtail);
          }
        }   
        @Override
        void initHelper(Unchecked<E> root,E val)
        {
          final Node<E> after,newNode=new Node<E>(val);
          for(SubList<E> curr=this;;curr.size=1)
          {
            ((RefDblLnkSeq<E>)curr).privateInit(newNode);
            if((curr=curr.parent)==null)
            {
              after=root.head;
              break;
            }
            if(curr.size!=0)
            {
              after=curr.head;
              curr.bubbleUppushhead(newNode);
              break;
            }
          }
          after.joinprev(newNode);
        }
        private void bubbleUppushtail(Node<E> oldtail,Node<E> newNode)
        {
          SubList<E> curr=this;
          do
          {
            ++curr.size;curr.tail=newNode;
            if((curr=curr.parent)==null)
            {
              return;
            }
          }
          while(curr.tail==oldtail);
          curr.bubbleUpincrementSize();
        }
        @Override
        void pushtailHelper(Node<E> oldtail,E val)
        {
          ++root.size;
          bubbleUppushtail(oldtail,new Node<E>(oldtail=this.tail,val,oldtail.next));
        }
        private void bubbleUpinit(Node<E> newNode)
        {
          SubList<E> curr=this;
          do
          {
            curr.size=1;curr.head=newNode;curr.tail=newNode;
            if((curr=curr.parent)==null)
            {
              return;
            }
          }
          while(curr.size!=0);
          curr.bubbleUppushhead(newNode);
        }
        @Override
        void initHelper(E val)
        {
          final Unchecked<E> root;
          ++(root=this.root).size;
          Node<E> newNode;
          root.head=newNode=new Node<E>(val,root.head);
          bubbleUpinit(newNode);
        }
        @Override
        public void clear()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList<E>)this).clearPrefix(size);
          }
        }
        @Override
        void findNewHead(Node<E> curr,Predicate<? super E> filter)
        {
          final Node<E> tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList<E>)this).rootCollapsehead(curr,tail,filter);
              return;
            }
            collapseHeadAndTail(curr,tail,filter);
            return;
          }
          --root.size;
          ((SubList<E>)this).bubbleUpclearPrefix(curr,curr=tail.next,1);
          ((RefDblLnkSeq<E>)root).privatesethead(curr);
        }
        private void bubbleUpcollapseHeadAndTail(Node<E> oldtail,Node<E> newhead,Node<E> newtail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList<E> curr=this;;)
          {
            curr.privateCollapseHeadAndTail(newSize,newhead,newtail);
            if((curr=curr.parent)==null)
            {
              break;
            }
            if(curr.tail!=oldtail)
            {
              curr.bubbleUprootCollapsehead(numRemoved,newhead);
              break;
            }
          }
          newtail.joinnext(oldtail.next);
        }
        private void collapseHeadAndTail(Node<E> head,Node<E> tail,Predicate<? super E> filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              ((SubList<E>)this).bubbleUpclearPrefix(tail,head=tail.next,oldSize);
              break;
            }
            ++numConsumed;
            if(!filter.test((head=head.next).val))
            {
              var tailCandidate=tail;
              for(;;)
              {
                if(numConsumed==oldSize)
                {
                  --numConsumed;
                  break;
                }
                ++numConsumed;
                if(!filter.test((tailCandidate=tailCandidate.prev).val))
                {
                  numConsumed-=2-head.collapseBodyHelper(tailCandidate,filter);
                  break;
                }
                bubbleUpcollapseHeadAndTail(tail,head,tailCandidate,numConsumed);
                break;
              }
            }
            root.head=head;
            head.prev=null;
            root.size-=numConsumed;
          }
        }
        @Override
        boolean uncheckedremoveValHelper(Node<E> curr,Predicate<Object> pred)
        {
          final var root=this.root;
          final var tail=this.tail;
          Node<E> prev;
          do
          {
            if(curr==tail)
            {
              return false;
            }
          }
          while(!pred.test((curr=(prev=curr).next).val));
          --root.size;
          if(curr==tail)
          {
            ((SubList<E>)this).bubbleUpprefixchoptail(curr,prev);
          }
          else
          {
            ((SubList<E>)this).bubbleUpdecrementSize();
            prev.joinnext(curr.next);
          }
          return true;
        }
        @Override
        void collapseTail(Node<E> head,Node<E> tail,Predicate<? super E> filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var tailCandidate=tail.prev;
          for(;;tailCandidate=tailCandidate.prev)
          {
            if(numConsumed==oldSize)
            {
              --numConsumed;
              break;
            }
            ++numConsumed;
            if(!filter.test(tailCandidate.val))
            {
              numConsumed-=2-head.collapseBodyHelper(tailCandidate,filter);
              break;
            }
          }
          ((SubList<E>)this).bubbleUpprefixCollapsetail(numConsumed,tail,tailCandidate);
          tailCandidate.joinnext(tail.next);
          root.size-=numConsumed;
        }
        @Override
        public E remove(int index)
        {
          Unchecked<E> root;
          --(root=this.root).size;
          Node<E> node;
          int size;
          if((size=--this.size)!=0)
          {
            if(index==0)
            {
              Node<E> newHead;
              ((RefDblLnkSeq<E>)root).privatesethead(newHead=(node=this.head).next);
              ((SubList<E>)this).bubbleUprootchophead(newHead);
            }
            else
            {
              if((size-=index)==0)
              {
                ((SubList<E>)this).bubbleUpprefixchoptail(node=this.tail,node.prev);
              }
              else
              {
                if(index<=size)
                {
                  Node<E> before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node<E> after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList<E>)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            node=this.head;
            ((RefDblLnkSeq<E>)root).privateInit(null);
            ((SubList<E>)this).bubbleUpclearPrefix(node=this.head,node.next,1);
          }
          return node.val;
        }
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
        {
          int subListSize;
          switch(subListSize=toIndex-fromIndex)
          {
          default:
            return getDefaultSubList(root,fromIndex,subListSize,size-toIndex);
          case 1:
            return getSubList1(root,fromIndex,size-toIndex);
          case 0:
            return getEmptySubList(root,fromIndex);
          }
        }
        private OmniList.OfRef<E> getDefaultSubList(Unchecked<E> root,int headDist,int subListSize,int tailDist)
        {
          Node<E> subListHead=head,subListTail=tail;
          if(headDist==0)
          {
            return new SubList.PrefixList<E>(root,this,subListHead,subListSize,tailDist<=subListSize?subListTail.iterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize));
          }
          if(headDist<=tailDist)
          {
            subListHead=subListHead.uncheckedIterateForward(headDist);
            subListTail=tailDist<=subListSize?subListTail.iterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
          }
          else
          {
            subListTail=subListTail.iterateReverse(tailDist);
            subListHead=headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize);
          }
          return new SubList.BodyList<E>(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist)
        {
          if(headDist==0)
          {
            return new SubList.PrefixList<E>(root,this);
          }
          return new SubList.BodyList<E>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist)
        {
          if(headDist==0)
          {
            return new SubList.PrefixList<E>(root,this,head);
          }
          return new SubList.BodyList<E>(root,this,tailDist<headDist?tail.iterateReverse(tailDist):head.uncheckedIterateForward(headDist),headDist);
        }
        @Override
        void ascItrRemove(Node<E> cursor)
        {
          final Unchecked<E> root;
          --(root=this.root).size;
          if(cursor!=null)
          {
            Node<E> lastRet;
            if((lastRet=cursor.prev)==head)
            {
              ((SubList<E>)this).bubbleUprootchophead(cursor);
            }
            else
            {
              ((SubList<E>)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(cursor);
              return;
            }
          }
          else
          {
            cursor=tail;
            if(size==1)
            {
              ((SubList<E>)this).bubbleUpclearPrefix(cursor,cursor=cursor.next,1);
            }
            else
            {
              ((SubList<E>)this).bubbleUpprefixchoptail(cursor,cursor.prev);
              return;
            }
          }
          ((RefDblLnkSeq<E>)root).privatesethead(cursor);
        }
        @Override
        void bidirectItrRemove(Node<E> lastRet)
        {
          final Unchecked<E> root;
          --(root=this.root).size;
          if(lastRet==head)
          {
            if(lastRet==tail)
            {
              ((SubList<E>)this).bubbleUpclearPrefix(lastRet,lastRet=lastRet.next,1);
            }
            else
            {
              ((SubList<E>)this).bubbleUprootchophead(lastRet=lastRet.next);
            }
            ((RefDblLnkSeq<E>)root).privatesethead(lastRet);
          }
          else
          {
            if(lastRet==tail)
            {
              ((SubList<E>)this).bubbleUpprefixchoptail(lastRet,lastRet.prev);
            }
            else
            {
              ((SubList<E>)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
      }
      private static class SuffixList<E> extends SubList<E>
      {
        SuffixList(Unchecked<E> root,SubList<E> parent)
        {
          super(root,parent);
        }
        SuffixList(Unchecked<E> root,SubList<E> parent,Node<E> onlyNode)
        {
          super(root,parent,onlyNode);
        }
        SuffixList(Unchecked<E> root,SubList<E> parent,Node<E> head,int size,Node<E> tail)
        {
          super(root,parent,head,size,tail);
        }
        @Override
        public void add(int index,E val)
        {
          final Unchecked<E> root;
          ++(root=this.root).size;
          int size;
          if((size=this.size)!=0)
          {
            if(index==0)
            {
              Node<E> oldhead;
              bubbleUppushhead(oldhead=this.head,new Node<E>(oldhead.prev,val,oldhead));
            }
            else
            {
              if((size-=index)==0)
              {
                Node<E> newNode;
                root.tail=newNode=new Node<E>(this.tail,val);
                ((SubList<E>)this).bubbleUppushtail(newNode);
              }
              else
              {
                Node<E> before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node<E>(before,val,after);
                ((SubList<E>)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            Node<E> newNode;
            root.tail=newNode=new Node<E>(root.tail,val);
            bubbleUpinit(newNode);
          }
        }
        @Override
        void removeFirstHelper(Node<E> curr)
        {
          final var root=this.root;
          if(curr==tail)
          {
            ((SubList<E>)this).bubbleUpclearSuffix(curr,curr=curr.prev,1);
            ((RefDblLnkSeq<E>)root).privatesettail(curr);
          }
          else
          {
            ((SubList<E>)this).bubbleUpsuffixchophead(curr,curr.next);
          }
          --root.size;
        }
        private void bubbleUpinit(Node<E> newNode)
        {
          SubList<E> curr=this;
          do
          {
            curr.size=1;curr.head=newNode;curr.tail=newNode;
            if((curr=curr.parent)==null)
            {
              return;
            }
          }
          while(curr.size!=0);
          curr.bubbleUppushtail(newNode);
        }
        @Override
        void initHelper(E val)
        {
          final Unchecked<E> root;
          ++(root=this.root).size;
          Node<E> newNode;
          root.tail=newNode=new Node<E>(root.tail,val);
          bubbleUpinit(newNode);
        }
        private void bubbleUppushhead(Node<E> oldhead,Node<E> newNode)
        {
          SubList<E> curr=this;
          do
          {
            ++curr.size;curr.head=newNode;
            if((curr=curr.parent)==null)
            {
              return;
            }
          }
          while(curr.head==oldhead);
          curr.bubbleUpincrementSize();
        }
        @Override
        void pushheadHelper(Node<E> oldhead,E val)
        {
          ++root.size;
          bubbleUppushhead(oldhead,new Node<E>((oldhead=this.head).prev,val,oldhead));
        }
        @Override
        public void clear()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList<E>)this).clearSuffix(size);
          }
        }
        @Override
        void findNewHead(Node<E> curr,Predicate<? super E> filter)
        {
          final Node<E> tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList<E>)this).suffixCollapsehead(curr,tail,filter);
              return;
            }
            collapseHeadAndTail(curr,tail,filter);
            return;
          }
          --root.size;
          ((SubList<E>)this).bubbleUpclearSuffix(curr,curr=curr.prev,1);
          ((RefDblLnkSeq<E>)root).privatesettail(curr);
        }
        private void bubbleUpcollapseHeadAndTail(Node<E> oldhead,Node<E> newhead,Node<E> newtail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList<E> curr=this;;)
          {
            curr.privateCollapseHeadAndTail(newSize,newhead,newtail);
            if((curr=curr.parent)==null)
            {
              break;
            }
            if(curr.head!=oldhead)
            {
              curr.bubbleUprootCollapsetail(numRemoved,newtail);
              break;
            }
          }
          newhead.joinnext(oldhead.next);
        }
        private void collapseHeadAndTail(Node<E> head,Node<E> tail,Predicate<? super E> filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              ((SubList<E>)this).bubbleUpclearSuffix(head,tail=head.next,oldSize);
              break;
            }
            ++numConsumed;
            if(!filter.test((tail=tail.next).val))
            {
              var headCandidate=head;
              for(;;)
              {
                if(numConsumed==oldSize)
                {
                  --numConsumed;
                  break;
                }
                ++numConsumed;
                if(!filter.test((headCandidate=headCandidate.next).val))
                {
                  numConsumed-=2-headCandidate.collapseBodyHelper(tail,filter);
                  break;
                }
                bubbleUpcollapseHeadAndTail(head,headCandidate,tail,numConsumed);
                break;
              }
            }
            root.tail=tail;
            tail.next=null;
            root.size-=numConsumed;
          }
        }
        @Override
        public E remove(int index)
        {
          Unchecked<E> root;
          --(root=this.root).size;
          Node<E> node;
          int size;
          if((size=--this.size)!=0)
          {
            if(index==0)
            {
              ((SubList<E>)this).bubbleUpsuffixchophead(node=this.head,node.next);
            }
            else
            {
              if((size-=index)==0)
              {
                Node<E> newTail;
                ((RefDblLnkSeq<E>)root).privatesettail(newTail=(node=this.tail).prev);
                ((SubList<E>)this).bubbleUprootchoptail(newTail);
              }
              else
              {
                if(index<=size)
                {
                  Node<E> before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node<E> after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList<E>)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            ((RefDblLnkSeq<E>)root).privateInit(null);
            ((SubList<E>)this).bubbleUpclearSuffix(node=this.head,node.prev,1);
          }
          return node.val;
        }
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
        {
          final int tailDist=size-toIndex;
          int subListSize;
          switch(subListSize=toIndex-fromIndex)
          {
          default:
            return getDefaultSubList(root,fromIndex,subListSize,tailDist);
          case 1:
            return getSubList1(root,fromIndex,tailDist);
          case 0:
            return getEmptySubList(root,fromIndex,tailDist);
          }
        }
        @Override
        void initHelper(Unchecked<E> root,E val)
        {
          final Node<E> before,newNode=new Node<E>(val);
          for(SubList<E> curr=this;;curr.size=1)
          {
            ((RefDblLnkSeq<E>)curr).privateInit(newNode);
            if((curr=curr.parent)==null)
            {
              before=root.tail;
              break;
            }
            if(curr.size!=0)
            {
              before=curr.tail;
              curr.bubbleUppushtail(newNode);
              break;
            }
          }
          before.joinnext(newNode);
        }
        @Override
        void prependHelper(E val)
        {
          Node<E> newNode,oldhead;
          head=newNode=new Node<E>((oldhead=head).prev,val,oldhead);
          SubList<E> parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpsuffixPushhead(newNode,oldhead);
          }
        }
        private OmniList.OfRef<E> getDefaultSubList(Unchecked<E> root,int headDist,int subListSize,int tailDist)
        {
          Node<E> subListHead=head,subListTail=tail;
          if(tailDist==0)
          {
            return new SubList.SuffixList<E>(root,this,headDist<=subListSize?subListHead.iterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize));
          }
          if(tailDist<=headDist)
          {
            subListTail=subListTail.uncheckedIterateReverse(tailDist);
            subListHead=headDist<=subListSize?subListHead.iterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize);
          }
          else
          {
            subListHead=subListHead.iterateForward(headDist);
            subListTail=tailDist<=subListSize?subListTail.uncheckedIterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
          }
          return new SubList.BodyList<E>(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist,int tailDist)
        {
          if(tailDist==0)
          {
            return new SubList.PrefixList<E>(root,this);
          }
          return new SubList.BodyList<E>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist)
        {
          if(tailDist==0)
          {
            return new SubList.SuffixList<E>(root,this,tail);
          }
          return new SubList.BodyList<E>(root,this,((RefDblLnkSeq<E>)this).privateGetNode(headDist,tailDist),headDist);
        }
        @Override
        void ascItrRemove(Node<E> cursor)
        {
          final Unchecked<E> root;
          --(root=this.root).size;
          if(cursor!=null)
          {
            Node<E> lastRet;
            if((lastRet=cursor.prev)==head)
            {
              ((SubList<E>)this).bubbleUpsuffixchophead(lastRet,cursor);
            }
            else
            {
              ((SubList<E>)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(cursor);
            }
          }
          else
          {
            if(size==1)
            {
              ((SubList<E>)this).bubbleUpclearSuffix(cursor=tail,cursor=cursor.prev,1);
            }
            else
            {
              ((SubList<E>)this).bubbleUprootchoptail(cursor=tail.prev);
            }
            ((RefDblLnkSeq<E>)root).privatesettail(cursor);
          }
        }
        @Override
        void bidirectItrRemove(Node<E> lastRet)
        {
          final Unchecked<E> root;
          --(root=this.root).size;
          if(lastRet==tail)
          {
            if(lastRet==head)
            {
              ((SubList<E>)this).bubbleUpclearSuffix(lastRet,lastRet=lastRet.prev,1);
            }
            else
            {
              ((SubList<E>)this).bubbleUprootchoptail(lastRet=lastRet.prev);
            }
            ((RefDblLnkSeq<E>)root).privatesettail(lastRet);
          }
          else
          {
            if(lastRet==head)
            {
              ((SubList<E>)this).bubbleUpsuffixchophead(lastRet,lastRet.next);
            }
            else
            {
              ((SubList<E>)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
      }
      private static class BodyList<E> extends PrefixList<E>
      {
        private transient final int parentOffset;
        BodyList(Unchecked<E> root,SubList<E> parent,int parentOffset)
        {
          super(root,parent);
          this.parentOffset=parentOffset;
        }
        BodyList(Unchecked<E> root,SubList<E> parent,Node<E> onlyNode,int parentOffset)
        {
          super(root,parent,onlyNode);
          this.parentOffset=parentOffset;
        }
        BodyList(Unchecked<E> root,SubList<E> parent,Node<E> head,int size,Node<E> tail,int parentOffset)
        {
          super(root,parent,head,size,tail);
          this.parentOffset=parentOffset;
        }
        @Override
        public void add(int index,E val)
        {
          ++root.size;
          int size;
          if((size=this.size)!=0)
          {
            if(index==0)
            {
              Node<E> oldhead;
              bubbleUppushhead(oldhead=this.head,new Node<E>(oldhead.prev,val,oldhead));
            }
            else
            {
              if((size-=index)==0)
              {
                Node<E> oldtail;
                ((PrefixList<E>)this).bubbleUppushtail(oldtail=this.tail,new Node<E>(oldtail,val,oldtail.next));
              }
              else
              {
                Node<E> before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node<E>(before,val,after);
                ((SubList<E>)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            bubbleUpinit(new Node<E>(val));
          }
        }
        @Override
        void removeFirstHelper(Node<E> curr)
        {
          if(curr==tail)
          {
            Node<E> node;
            ((SubList<E>)this).bubbleUpclearBody(node=this.head,node,1);
          }
          else
          {
            ((SubList<E>)this).bubbleUpsuffixchophead(curr,curr.next);
          }
          --root.size;
        }
        private void bubbleUpcollapseHeadAndTail(Node<E> oldHead,Node<E> oldTail,Node<E> newHead,Node<E> newTail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList<E> curr=this;;)
          {
            curr.privateCollapseHeadAndTail(newSize,newHead,newTail);
            if((curr=curr.parent)==null)
            {
              break;
            }
            if(curr.head!=oldHead)
            {
              curr.bubbleUpprefixCollapsetail(numRemoved,oldTail,newTail);
              break;
            }
            if(curr.tail!=oldTail)
            {
              curr.size-=numRemoved;
              curr.head=newHead;
              if((curr=curr.parent)!=null)
              {
                curr.bubbleUpsuffixCollapsehead(numRemoved,oldHead,newHead);
              }
              break;
            }
          }
          oldHead.prev.joinnext(newHead);
          newTail.joinnext(oldTail.next);
        }
        private void collapseHeadAndTail(final Node<E> head,final Node<E> tail,final Predicate<? super E> filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var headCandidate=head;
          final var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              ((SubList<E>)this).bubbleUpclearBody(head,tail,oldSize);
              break;
            }
            ++numConsumed;
            if(!filter.test((headCandidate=headCandidate.next).val))
            {
              var tailCandidate=tail;
              for(;;)
              {
                if(numConsumed==oldSize)
                {
                  --numConsumed;
                  break;
                }
                ++numConsumed;
                if(filter.test((tailCandidate=tailCandidate.prev).val))
                {
                  numConsumed-=2-headCandidate.collapseBodyHelper(tailCandidate,filter);
                  break;
                }
              }
              bubbleUpcollapseHeadAndTail(head,tail,headCandidate,tailCandidate,numConsumed);
              break;
            }
          }
          root.size-=numConsumed;
        }
        @Override
        void findNewHead(Node<E> curr,Predicate<? super E> filter)
        {
          final Node<E> tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList<E>)this).suffixCollapsehead(curr,tail,filter);
              return;
            }
            collapseHeadAndTail(curr,tail,filter);
            return;
          }
          --root.size;
          ((SubList<E>)this).bubbleUpclearBody(curr,tail,1);
        }
        private void bubbleUppushhead(Node<E> oldhead,Node<E> newNode)
        {
          SubList<E> curr=this;
          do
          {
            ++curr.size;curr.head=newNode;
            if((curr=curr.parent)==null)
            {
              return;
            }
          }
          while(curr.head==oldhead);
          curr.bubbleUpincrementSize();
        }
        @Override
        void pushheadHelper(Node<E> oldhead,E val)
        {
          ++root.size;
          bubbleUppushhead(oldhead,new Node<E>((oldhead=this.head).prev,val,oldhead));
        }
        private void bubbleUpinit(Node<E> newNode)
        {
          SubList<E> curr=this;
          Node<E> before,after;
          for(;;)
          {
            curr.size=1;
            ((RefDblLnkSeq<E>)curr).privateInit(newNode);
            int currParentOffset=curr.getParentOffset();
            if((curr=curr.parent)==null)
            {
              int tailDist;
              if(currParentOffset<=(tailDist=root.size-currParentOffset))
              {
                after=(before=root.head.iterateForward(currParentOffset-1)).next;
              }
              else
              {
                before=(after=root.tail.iterateReverse(tailDist-1)).prev;
              }
              break;
            }
            int currSize;
            if((currSize=curr.size)!=0)
            {
              if(currParentOffset==0)
              {
                before=(after=curr.head).prev;
                curr.bubbleUpsuffixPushhead(after,newNode);
              }
              else
              {
                if((currSize-=currParentOffset)==0)
                {
                  after=(before=curr.tail).next;
                  curr.bubbleUpprefixPushtail(before,newNode);
                }
                else
                {
                  if(currParentOffset<=currSize)
                  {
                    after=(before=curr.head.iterateForward(currParentOffset-1)).next;
                  }
                  else
                  {
                    before=(after=curr.tail.iterateReverse(currSize-1)).prev;
                  }
                  curr.bubbleUpincrementSize();
                }
              }
              break;
            }
          }
          before.joinnext(newNode);
          newNode.joinnext(after);
        }
        @Override
        public void clear()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList<E>)this).clearBody(size);
          }
        }
        @Override
        void initHelper(E val)
        {
          ++root.size;
          bubbleUpinit(new Node<E>(val));
        }
        @Override
        void prependHelper(E val)
        {
          Node<E> newNode,oldhead;
          head=newNode=new Node<E>((oldhead=head).prev,val,oldhead);
          SubList<E> parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpsuffixPushhead(newNode,oldhead);
          }
        }
        @Override
        void initHelper(Unchecked<E> root,E val)
        {
          final Node<E> newNode;
          ((RefDblLnkSeq<E>)this).privateInit(newNode=new Node<E>(val));
          SubList<E> parent,curr;
          if((parent=(curr=this).parent)!=null)
          {
            do
            {
              int parentSize;
              if((parentSize=parent.size)!=0)
              {
                Node<E> before,after;
                int headDist,tailDist;
                if((headDist=curr.getParentOffset())==0)
                {
                  parent.bubbleUpsuffixPushhead(newNode,after=parent.head);
                  before=after.prev;
                }
                else if((tailDist=parentSize-headDist)==0)
                {
                  parent.bubbleUpprefixPushtail(newNode,before=parent.tail);
                  after=before.next;
                }
                else
                {
                  if(headDist<=tailDist)
                  {
                    after=(before=root.head.iterateForward(headDist-1)).next;
                  }
                  else
                  {
                    before=(after=root.tail.iterateReverse(tailDist-1)).prev;
                  }
                  parent.bubbleUpincrementSize();
                }
                before.joinnext(newNode);
                newNode.joinnext(after);
                return;
              }
              parent=(curr=parent).parent;
              curr.size=1;
              ((RefDblLnkSeq<E>)curr).privateInit(newNode);
            }
            while(parent!=null);
          }
          ((RefDblLnkSeq<E>)root).subSeqInsertHelper(newNode,curr.getParentOffset());
        }
        @Override
        public E remove(int index)
        {
          --root.size;
          Node<E> node;
          int size;
          if((size=--this.size)!=0)
          {
            if(index==0)
            {
              ((SubList<E>)this).bubbleUpsuffixchophead(node=this.head,node.next);
            }
            else
            {
              if((size-=index)==0)
              {
                ((SubList<E>)this).bubbleUpprefixchoptail(node=this.tail,node.prev);
              }
              else
              {
                if(index<=size)
                {
                  Node<E> before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node<E> after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList<E>)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            ((SubList<E>)this).bubbleUpclearBody(node=this.head,node,1);
          }
          return node.val;
        }
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
        {
          int subListSize;
          switch(subListSize=toIndex-fromIndex)
          {
          default:
            return getDefaultSubList(root,fromIndex,subListSize,size-toIndex);
          case 1:
            return getSubList1(root,fromIndex,size-toIndex);
          case 0:
            return getEmptySubList(root,fromIndex);
          }
        }
        @Override
        int getParentOffset()
        {
          return parentOffset;
        }
        private OmniList.OfRef<E> getDefaultSubList(Unchecked<E> root,int headDist,int subListSize,int tailDist)
        {
          Node<E> subListHead,subListTail;
          if(headDist<=tailDist)
          {
            subListHead=head.iterateForward(headDist);
            subListTail=tailDist<subListSize?tail.iterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
          }
          else
          {
            subListTail=tail.iterateReverse(tailDist);
            subListHead=headDist<subListSize?head.iterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize);
          }
          return new SubList.BodyList<E>(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist)
        {
          return new SubList.BodyList<E>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist)
        {
          return new SubList.BodyList<E>(root,this,((RefDblLnkSeq<E>)this).privateGetNode(headDist,tailDist),headDist);
        }
        @Override 
        void ascItrRemove(Node<E> cursor)
        {
          --root.size;
          if(cursor!=null)
          {
            Node<E> lastRet;
            if((lastRet=cursor.prev)==head)
            {
              ((SubList<E>)this).bubbleUpsuffixchophead(lastRet,cursor);
            }
            else
            {
              ((SubList<E>)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(cursor);
            }
          }
          else
          {
            cursor=tail;
            if(size==1)
            {
              ((SubList<E>)this).bubbleUpclearBody(cursor,cursor,1);
            }
            else
            {
              ((SubList<E>)this).bubbleUpprefixchoptail(cursor,cursor.prev);
            }
          }
        }
        @Override
        void bidirectItrRemove(Node<E> lastRet)
        {
          --root.size;
          if(lastRet==head)
          {
            if(lastRet==tail)
            {
              ((SubList<E>)this).bubbleUpclearBody(lastRet,lastRet,1);
            }
            else
            {
              ((SubList<E>)this).bubbleUpsuffixchophead(lastRet,lastRet.next);
            }
          }
          else
          {
            if(lastRet==tail)
            {
              ((SubList<E>)this).bubbleUpprefixchoptail(lastRet,lastRet.prev);
            }
            else
            {
              ((SubList<E>)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
      }
    }
  }
}
