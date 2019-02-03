package omni.impl.seq;
import omni.api.OmniCollection;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.impl.CheckedCollection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import omni.util.OmniArray;
import omni.api.OmniIterator;
import java.util.NoSuchElementException;
import omni.util.BitSetUtil;
import java.util.Objects;
import omni.util.OmniPred;
public abstract class RefSnglLnkSeq<E> 
extends AbstractSeq
implements OmniCollection.OfRef<E>
{
  transient Node<E> head;
  private RefSnglLnkSeq()
  {
    super();
  }
  private RefSnglLnkSeq(Node<E> onlyNode)
  {
    super(1);
    this.head=onlyNode;
  }
  private RefSnglLnkSeq(Node<E> head,int size)
  {
    super(size);
    this.head=head;
  }
  @Override
  public void clear()
  {
    this.head=null;
    this.size=0;
  }
  @Override
  public int hashCode()
  {
    Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedForwardHashCode();
    }
    return 1;
  }
  @Override
  public String toString()
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      final StringBuilder builder;
      head.uncheckedForwardToString(builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override
  public boolean contains(Object val)
  {
    Node<E> head;
    if((head=this.head)!=null)
    {
      {
        return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public int search(Object val)
  {
    Node<E> head;
    if((head=this.head)!=null)
    {
      {
        return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public boolean removeIf(Predicate<? super E> filter)
  {
    Node<E> head;
    if((head=this.head)!=null)
    {
      return uncheckedRemoveIf(head,filter);
    }
    return false;
  }
  abstract boolean uncheckedRemoveIf(Node<E> head,Predicate<? super E> filter);
  public E peek()
  {
    Node<E> head;
    if((head=this.head)!=null)
    {
      return (E)(head.val);
    }
    return null;
  }
  @Override
  public Object[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Object[] dst;
      head.uncheckedCopyForward(dst=new Object[size],0);
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
      head.uncheckedCopyForward(dst,0);
    }
    return dst;
  }
  @Override
  public <T> T[] toArray(T[] dst)
  {
    final int size;
    if((size=this.size)!=0)
    {
      head.uncheckedCopyForward(dst=OmniArray.uncheckedArrResize(size,dst),0);
    }
    else if(dst.length!=0)
    {
      dst[0]=null;
    }
    return dst;
  }
  public E poll()
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return (E)(head.val);
    }
    return null;
  }
  @Override
  public boolean add(E val)
  {
    push((E)(val));
    return true;
  }
  public boolean offer(E val)
  {
    push((E)(val));
    return true;
  }
  public E remove()
  {
    return (E)pop();
  }
  public E pop()
  {
    Node<E> head;
    --this.size;
    uncheckedchophead(head=this.head);
    return head.val;
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
  abstract void push(E val);
  abstract void uncheckedchophead(Node<E> oldHead);
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
  public boolean contains(final boolean val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
        return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final float val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final byte val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
        return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
        return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Byte val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Character val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Short val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Integer val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Long val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Float val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Double val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int search(final boolean val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final int val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      {
        return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(final long val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final float val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final double val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final byte val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final char val)
  {
    {
      final Node<E> head;
      if((head=this.head)!=null)
      {
        return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
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
        return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(final Boolean val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Byte val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Character val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Short val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Integer val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Long val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Float val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Double val)
  {
    final Node<E> head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public void forEach(Consumer<? super E> action)
  {
    Node<E> head;
    if((head=this.head)!=null)
    {
      head.uncheckedForEachForward(action);
    }
  }
  boolean uncheckedremoveVal(Node<E> head,Predicate<Object> pred)
  {
    if(!pred.test(head.val))
    {
      Node<E> prev;
      do
      {
        if((head=(prev=head).next)==null)
        {
          return false;
        }
      }
      while(!pred.test(head.val));
      prev.next=head.next;
    }
    else
    {
      this.head=head.next;
    }
    --this.size;
    return true;
  }
  private static abstract class AbstractCheckedItr<E> extends AbstractUncheckedItr<E>
  {
    transient int modCount;
    abstract void checkModCount(int modCount);
    AbstractCheckedItr(Node<E> next,int modCount)
    {
      super(next);
      this.modCount=modCount;
    }
    @Override
    public E next()
    {
      checkModCount(this.modCount);
      final Node<E> ret;
      if((ret=this.next)!=null)
      {
        this.pPrev=this.prev;
        this.prev=ret;
        this.next=ret.next;
        return ret.val;
      }
      throw new NoSuchElementException();
    }
    @Override
    void uncheckedForEachRemaining(Node<E> next,Consumer<? super E> action)
    {
      Node<E> prev,pPrev;
      final int modCount=this.modCount;
      try
      {
        for(pPrev=this.prev;;pPrev=prev)
        {
          action.accept(next.val);
          if((next=(prev=next).next)==null)
          {
            break;
          }
        }
      }
      finally
      {
        checkModCount(modCount);
      }
      this.pPrev=pPrev;
      this.prev=prev;
      this.next=null;
    }
  }
  private static abstract class AbstractUncheckedItr<E>
    implements OmniIterator.OfRef<E>
  {
    Node<E> next;
    Node<E> prev;
    Node<E> pPrev;
    AbstractUncheckedItr(Node<E> next)
    {
      this.next=next;
    }
    @Override
    public boolean hasNext()
    {
      return this.next!=null;
    }
    @Override
    public E next()
    {
      this.pPrev=this.prev;
      final Node<E> ret;
      this.prev=ret=this.next;
      this.next=ret.next;
      return ret.val;
    }
    void uncheckedForEachRemaining(Node<E> next,Consumer<? super E> action)
    {
      for(Node<E> prev,pPrev=this.prev;;pPrev=prev)
      {
        action.accept(next.val);
        if((next=(prev=next).next)==null)
        {
          this.pPrev=pPrev;
          this.prev=prev;
          this.next=null;
          return;
        }
      }
    }
    @Override
    public void forEachRemaining(final Consumer<? super E> action)
    {
      final Node<E> next;
      if((next=this.next)!=null)
      {
         uncheckedForEachRemaining(next,action);
      }
    }
  }
  public static class Stack<E> extends RefSnglLnkSeq<E> implements OmniStack.OfRef<E>
  {
    public Stack()
    {
      super();
    }
    public Stack(Node<E> onlyNode)
    {
      super(onlyNode);
    }
    public Stack(Node<E> head,int size)
    {
      super(head,size);
    }
    @Override
    public void push(E val)
    {
      ++this.size;
      this.head=new Node<E>(val,this.head);
    }
    @Override
    public Object clone()
    {
      Node<E> oldHead,newHead;
      if((oldHead=this.head)!=null)
      {
        for(var newTail=newHead=new Node<E>(oldHead.val);(oldHead=oldHead.next)!=null;newTail.next=newTail=new Node<E>(oldHead.val)){}
      }
      else
      {
        newHead=null;
      }
      return new Stack<E>(newHead,this.size);
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
      return new Itr<E>(this);
    }
    @Override
    void uncheckedchophead(Node<E> oldHead)
    {
      this.head=oldHead.next;
    }
    private static class Itr<E> extends AbstractUncheckedItr<E>
    {
      private transient final Stack<E> root;
      Itr(Stack<E> root)
      {
        super(root.head);
        this.root=root;
      }
      @Override
      public void remove()
      {
        final Stack<E> root;
        --(root=this.root).size;
        if(this.prev==root.head)
        {
          root.head=this.next;
          this.prev=null;
        }
        else
        {
          final Node<E> pPrev;
          (pPrev=this.pPrev).next=this.next;
          this.prev=pPrev;
        }
      }
    }
    private void uncheckedRemoveIfHelper(Node<E> prev,Node<E> curr,Predicate<? super E> filter)
    {
      int numRemoved=1;
      while((curr=curr.next)!=null)
      {
        if(!filter.test(curr.val))
        {
          prev.next=curr;
          this.size-=(numRemoved+curr.countNumRemovedNodes(filter));
          return;
        }
        ++numRemoved;
      }
      prev.next=null;
      this.size-=numRemoved;
    }
    @Override
    boolean uncheckedRemoveIf(Node<E> curr,Predicate<? super E> filter)
    {
      if(filter.test(curr.val))
      {
        while((curr=curr.next)!=null)
        {
          if(!filter.test(curr.val))
          {
            this.size=curr.countNumRemainingNodes(filter);
            this.head=curr;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }
      else
      {
        Node<E> prev;
        while((curr=(prev=curr).next)!=null)
        {
          if(filter.test(curr.val))
          {
            uncheckedRemoveIfHelper(prev,curr,filter);
            return true;
          }
        }
        return false;
      }
    }
    public static class Checked<E> extends Stack<E>
    {
      private static class Itr<E> extends AbstractCheckedItr<E>
      {
        private transient final Checked<E> root;
        Itr(Checked<E> root)
        {
          super(root.head,root.modCount);
          this.root=root;
        }
        @Override
        void checkModCount(int modCount)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        @Override
        public void remove()
        {
          final Node<E> prev;
          if((prev=this.prev)!=null)
          {
            final Checked<E> root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            if(prev==root.head)
            {
              root.head=this.next;
              this.prev=null;
            }
            else
            {
              final Node<E> pPrev;
              (pPrev=this.pPrev).next=this.next;
              this.prev=pPrev;
            }
            return;
          }
          throw new IllegalStateException();
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
      private void uncheckedRemoveIfHelper(int modCount,int numLeft,Node<E> prev,Node<E> curr,Predicate<? super E> filter)
      {
        int numRemoved=1;
        for(;;)
        {
          if(numLeft==0)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            prev.next=null;
            break;
          }
          --numLeft;
          if(!filter.test((curr=curr.next).val))
          {
            numRemoved+=curr.countNumRemovedNodes(new ModCountChecker(modCount),numLeft,filter);
            prev.next=curr;
            break;
          }
        }
        this.size-=numRemoved;
      }
      @Override
      boolean uncheckedRemoveIf(Node<E> curr,Predicate<? super E> filter)
      {
         int modCount=this.modCount;
         try
         {
           int numLeft=this.size-1;
           if(filter.test(curr.val))
           {
             while((curr=curr.next)!=null)
             {
               --numLeft;
               if(!filter.test(curr.val))
               {
                 this.size=curr.countNumRemainingNodes(new ModCountChecker(modCount),numLeft,filter);
                 this.modCount=modCount+1;
                 this.head=curr;
                 return true;
               }
             }
             CheckedCollection.checkModCount(modCount,this.modCount);
             this.modCount=modCount+1;
             this.head=null;
             this.size=0;
             return true;
           }
           else
           {
             Node<E> prev;
             while((curr=(prev=curr).next)!=null)
             {
               --numLeft;
               if(filter.test(curr.val))
               {
                 uncheckedRemoveIfHelper(modCount,numLeft,prev,curr,filter);
                 this.modCount=modCount+1;
                 return true;
               }
             }
             CheckedCollection.checkModCount(modCount,this.modCount);
             return false;
           }
         }
         catch(final RuntimeException e)
         {
           throw CheckedCollection.checkModCount(modCount,this.modCount,e);
         }
      }
      transient int modCount;
      public Checked()
      {
        super();
      }
      public Checked(Node<E> onlyNode)
      {
        super(onlyNode);
      }
      public Checked(Node<E> head,int size)
      {
        super(head,size);
      }
      @Override
      public void push(E val)
      {
        ++this.modCount;
        super.push(val);
      }
      @Override
      public OmniIterator.OfRef<E> iterator()
      {
        return new Itr<E>(this);
      }
      @Override
      public E pop()
      {
        Node<E> head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return head.val;
        }
        throw new NoSuchElementException();
      }
      @Override
      public E poll()
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (E)(head.val);
        }
        return null;
      }
      @Override
      public void clear()
      {
        if(size!=0)
        {
          ++this.modCount;
          super.clear();
        }
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
      public void forEach(Consumer<? super E> action)
      {
        Node<E> head;
        if((head=this.head)!=null)
        {
          int modCount=this.modCount;
          try
          {
            head.uncheckedForEachForward(action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
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
          return super.uncheckedremoveVal(head,Objects::isNull);
        }
        return false;
      }
      @Override
      public boolean contains(Object val)
      {
        Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null)
          {
            int modCount=this.modCount;
            try
            {
              return head.uncheckedcontains(val::equals);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
            }
          }
          return head.uncheckedcontains(Objects::isNull);
        }
        return false;
      }
      @Override
      public int hashCode()
      {
        Node<E> head;
        if((head=this.head)!=null)
        {
          int modCount=this.modCount;
          try
          {
            return head.uncheckedForwardHashCode();
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
        Node<E> head;
        if((head=this.head)!=null)
        {
          int modCount=this.modCount;
          try
          {
            final StringBuilder builder;head.uncheckedForwardToString(builder=new StringBuilder("["));return builder.append(']').toString();
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return "[]";
      }
      private boolean uncheckedremoveValNonNull(Node<E> head,Object val)
      {
        int modCount=this.modCount;
        try
        {
          if(!val.equals(head.val))
          {
            Node<E> prev;
            do
            {
              if((head=(prev=head).next)==null)
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                return false;
              }
            }
            while(!val.equals(head.val));
            CheckedCollection.checkModCount(modCount,this.modCount);
            prev.next=head.next;
          }
          else
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.head=head.next;
          }
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
      @Override
      public int search(Object val)
      {
        Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null)
          {
            int modCount=this.modCount;
            try
            {
              return head.uncheckedsearch(val::equals);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
            }
          }
          return head.uncheckedsearch(Objects::isNull);
        }
        return -1;
      }
      @Override
      public Object clone()
      {
        Node<E> oldHead,newHead;
        if((oldHead=this.head)!=null)
        {
          for(var newTail=newHead=new Node<E>(oldHead.val);(oldHead=oldHead.next)!=null;newTail.next=newTail=new Node<E>(oldHead.val)){}
        }
        else
        {
          newHead=null;
        }
        return new Checked<E>(newHead,this.size);
      }
      @Override
      boolean uncheckedremoveVal(Node<E> head,Predicate<Object> pred)
      {
        if(!pred.test(head.val))
        {
          Node<E> prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(!pred.test(head.val));
          prev.next=head.next;
        }
        else
        {
          this.head=head.next;
        }
        ++this.modCount;
        --this.size;
        return true;
      }
    }
  }
  public static class Queue<E> extends RefSnglLnkSeq<E> implements OmniQueue.OfRef<E>
  {
    transient Node<E> tail;
    public Queue()
    {
      super();
    }
    public Queue(Node<E> onlyNode)
    {
      super(onlyNode);
      this.tail=onlyNode;
    }
    public Queue(Node<E> head,int size,Node<E> tail)
    {
      super(head,size);
      this.tail=tail;
    }
    @Override
    public void clear()
    {
      super.clear();
      this.tail=null;
    }
    @Override
    public Object clone()
    {
      Node<E> oldHead,newHead,newTail;
      if((oldHead=this.head)!=null)
      {
        for(newTail=newHead=new Node<E>(oldHead.val);(oldHead=oldHead.next)!=null;newTail.next=newTail=new Node<E>(oldHead.val)){}
      }
      else
      {
        newHead=null;
        newTail=null;
      }
      return new Queue<E>(newHead,this.size,newTail);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method
      return false;
    }
    private static class Itr<E> extends AbstractUncheckedItr<E>
    {
      private transient final Queue<E> root;
      Itr(Queue<E> root)
      {
        super(root.head);
        this.root=root;
      }
      @Override
      public void remove()
      {
        final Queue<E> root;
        --(root=this.root).size;
        final Node<E> prev;
        if((prev=this.prev)==root.head)
        {
          root.head=this.next;
          if(prev==root.tail)
          {
            root.tail=null;
          }
          this.prev=null;
        }
        else
        {
          final Node<E> pPrev;
          (pPrev=this.pPrev).next=this.next;
          if(prev==root.tail)
          {
            root.tail=pPrev;
          }
          this.prev=pPrev;
        }
      }
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new Itr<E>(this);
    }
    @Override
    public E element()
    {
      return head.val;
    }
    @Override
    void push(E val)
    {
      final var newNode=new Node<E>(val);
      if(++this.size==1)
      {
        this.head=newNode;
        this.tail=newNode;
      }
      else
      {
        this.tail=this.tail.next=newNode;
      }
    }
    @Override
    void uncheckedchophead(Node<E> oldHead)
    {
      if(oldHead==this.tail)
      {
        this.head=null;
        this.tail=null;
      }
      else
      {
        this.head=oldHead.next;
      }
    }
    private void uncheckedRemoveIfHelper(Node<E> prev,Node<E> curr,Predicate<? super E> filter)
    {
      int numRemoved=1;
      for(final var tail=this.tail;curr!=tail;++numRemoved)
      {
        if(!filter.test((curr=curr.next).val))
        {
          prev.next=curr;
          do
          {
            if(curr==tail)
            {
              this.size-=numRemoved;
              return;
            }
          }
          while(!filter.test((curr=(prev=curr).next).val));
        }
      }
      this.tail=prev;
      prev.next=null;
      this.size-=numRemoved;
    }
    private int uncheckedRemoveIfHelper(Node<E> curr,Predicate<? super E> filter)
    {
      int numSurvivors=1;
      for(final var tail=this.tail;curr!=tail;++numSurvivors)
      {
        Node<E> prev;
        if(filter.test((curr=(prev=curr).next).val))
        {
          do
          {
            if(curr==tail)
            {
              this.tail=prev;
              prev.next=null;
              return numSurvivors;
            }
          }
          while(filter.test((curr=curr.next).val));
          prev.next=curr;
        }
      }
      return numSurvivors;
    }
    @Override
    boolean uncheckedRemoveIf(Node<E> curr,Predicate<? super E> filter)
    {
      if(filter.test(curr.val))
      {
        while((curr=curr.next)!=null)
        {
          if(!filter.test(curr.val))
          {
            this.size=uncheckedRemoveIfHelper(curr,filter);
            this.head=curr;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }
      else
      {
        Node<E> prev;
        while((curr=(prev=curr).next)!=null)
        {
          if(filter.test(curr.val))
          {
            uncheckedRemoveIfHelper(prev,curr,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override
    boolean uncheckedremoveVal(Node<E> head,Predicate<Object> pred)
    {
      if(!pred.test(head.val))
      {
        Node<E> prev;
        do
        {
          if((head=(prev=head).next)==null)
          {
            return false;
          }
        }
        while(!pred.test(head.val));
        prev.next=head.next;
        if(head==tail)
        {
          this.tail=prev;
        }
      }
      else
      {
        this.head=head.next;
        if(head==tail)
        {
          this.tail=null;
        }
      }
      --this.size;
      return true;
    }
    public static class Checked<E> extends Queue<E>
    {
      private static class Itr<E> extends AbstractCheckedItr<E>
      {
        private transient final Checked<E> root;
        Itr(Checked<E> root)
        {
          super(root.head,root.modCount);
          this.root=root;
        }
        @Override
        void checkModCount(int modCount)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        @Override
        public void remove()
        {
          final Node<E> prev;
          if((prev=this.prev)!=null)
          {
            final Checked<E> root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            if(prev==root.head)
            {
              root.head=this.next;
              if(prev==root.tail)
              {
                root.tail=null;
              }
              this.prev=null;
            }
            else
            {
              final Node<E> pPrev;
              (pPrev=this.pPrev).next=this.next;
              if(prev==root.tail)
              {
                root.tail=pPrev;
              }
              this.prev=pPrev;
            }
            return;
          }
          throw new IllegalStateException();
        }
      }
      private int uncheckedRemoveIfHelper(int modCount,int numLeft,Node<E> curr,Predicate<? super E> filter)
      {
        int numSurvivors=1;
        for(Node<E> prev;;++numSurvivors)
        {
          if(numLeft==0)
          {
           CheckedCollection.checkModCount(modCount,this.modCount);
           break;
          }
          --numLeft;
          if(filter.test((curr=(prev=curr).next).val))
          {
            long[] survivorSet;
            if(numLeft!=0 && (numLeft=(curr=curr.next).markSurvivors(numLeft,survivorSet=BitSetUtil.getBitSet(numLeft),filter))!=0)
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              numSurvivors+=numLeft;
              prev=prev.retainSurvivors(curr,numLeft,survivorSet);
            }
            else
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.tail=prev;
            prev.next=null;
            break;
          }
        }
        return numSurvivors;
      }
      private void uncheckedRemoveIfHelper(int modCount,int numLeft,Node<E> prev,Node<E> curr,Predicate<? super E> filter)
      {
        int numSurvivors;
        long[] survivorSet;
        if(numLeft!=0 && (numSurvivors=(curr=curr.next).markSurvivors(numLeft,survivorSet=BitSetUtil.getBitSet(numLeft),filter))!=0)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.size-=(1+(numLeft-numSurvivors));
          prev=prev.retainSurvivors(curr,numSurvivors,survivorSet);
        }
        else
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          --this.size;
        }
        this.tail=prev;
        prev.next=null;
      }
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
      boolean uncheckedRemoveIf(Node<E> curr,Predicate<? super E> filter)
      {
         int modCount=this.modCount;
         try
         {
           int numLeft=this.size-1;
           if(filter.test(curr.val))
           {
             while((curr=curr.next)!=null)
             {
               --numLeft;
               if(!filter.test(curr.val))
               {
                 this.size=uncheckedRemoveIfHelper(modCount,numLeft,curr,filter);
                 this.modCount=modCount+1;
                 this.head=curr;
                 return true;
               }
             }
             CheckedCollection.checkModCount(modCount,this.modCount);
             this.modCount=modCount+1;
             this.head=null;
             this.size=0;
             return true;
           }
           else
           {
             Node<E> prev;
             while((curr=(prev=curr).next)!=null)
             {
               --numLeft;
               if(filter.test(curr.val))
               {
                 uncheckedRemoveIfHelper(modCount,numLeft,prev,curr,filter);
                 this.modCount=modCount+1;
                 return true;
               }
             }
             CheckedCollection.checkModCount(modCount,this.modCount);
             return false;
           }
         }
         catch(final RuntimeException e)
         {
           throw CheckedCollection.checkModCount(modCount,this.modCount,e);
         }
      }
      private boolean uncheckedremoveValNonNull(Node<E> head,Object val)
      {
        int modCount=this.modCount;
        try
        {
          if(!val.equals(head.val))
          {
            Node<E> prev;
            do
            {
              if((head=(prev=head).next)==null)
              {
                CheckedCollection.checkModCount(modCount,this.modCount);
                return false;
              }
            }
            while(!val.equals(head.val));
            CheckedCollection.checkModCount(modCount,this.modCount);
            prev.next=head.next;
            if(head==tail)
            {
              this.tail=prev;
            }
          }
          else
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.head=head.next;
            if(head==tail)
            {
              this.tail=null;
            }
          }
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
      @Override
      public E pop()
      {
        Node<E> head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return head.val;
        }
        throw new NoSuchElementException();
      }
      @Override
      void push(E val)
      {
        ++this.modCount;
        super.push(val);
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
      public OmniIterator.OfRef<E> iterator()
      {
        return new Itr<E>(this);
      }
      @Override
      public E poll()
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (E)(head.val);
        }
        return null;
      }
      @Override
      public void clear()
      {
        if(size!=0)
        {
          ++this.modCount;
          super.clear();
        }
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
      public void forEach(Consumer<? super E> action)
      {
        Node<E> head;
        if((head=this.head)!=null)
        {
          int modCount=this.modCount;
          try
          {
            head.uncheckedForEachForward(action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
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
          return super.uncheckedremoveVal(head,Objects::isNull);
        }
        return false;
      }
      @Override
      public boolean contains(Object val)
      {
        Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null)
          {
            int modCount=this.modCount;
            try
            {
              return head.uncheckedcontains(val::equals);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
            }
          }
          return head.uncheckedcontains(Objects::isNull);
        }
        return false;
      }
      @Override
      public int hashCode()
      {
        Node<E> head;
        if((head=this.head)!=null)
        {
          int modCount=this.modCount;
          try
          {
            return head.uncheckedForwardHashCode();
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
        Node<E> head;
        if((head=this.head)!=null)
        {
          int modCount=this.modCount;
          try
          {
            final StringBuilder builder;head.uncheckedForwardToString(builder=new StringBuilder("["));return builder.append(']').toString();
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return "[]";
      }
      @Override
      public Object clone()
      {
        Node<E> oldHead,newHead,newTail;
        if((oldHead=this.head)!=null)
        {
          for(newTail=newHead=new Node<E>(oldHead.val);(oldHead=oldHead.next)!=null;newTail.next=newTail=new Node<E>(oldHead.val)){}
        }
        else
        {
          newHead=null;
          newTail=null;
        }
        return new Checked<E>(newHead,this.size,newTail);
      }
      @Override
      boolean uncheckedremoveVal(Node<E> head,Predicate<Object> pred)
      {
        if(!pred.test(head.val))
        {
          Node<E> prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(!pred.test(head.val));
          prev.next=head.next;
          if(head==tail)
          {
            this.tail=prev;
          }
        }
        else
        {
          this.head=head.next;
          if(head==tail)
          {
            this.tail=null;
          }
        }
        ++this.modCount;
        --this.size;
        return true;
      }
    }
  }
  static class Node<E>
  {
    private int countNumRemainingNodes(CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft,Predicate<? super E> filter)
    {
      int numSurvivors=1;
      for(Node<E> before,prev=this;;++numSurvivors,prev=before)
      {
        if(numLeft==0)
        {
         modCountChecker.checkModCount();
         break;
        }
        --numLeft;
        if(filter.test((before=prev.next).val)) 
        {
          long[] survivorSet;
          if(numLeft!=0 && (numLeft=(before=before.next).markSurvivors(numLeft,survivorSet=BitSetUtil.getBitSet(numLeft),filter))!=0)
          {
            modCountChecker.checkModCount();
            numSurvivors+=numLeft;
            prev=prev.retainSurvivors(before,numLeft,survivorSet);
          }
          else
          {
            modCountChecker.checkModCount();
          }
          prev.next=null;
          break;
        }
      }
      return numSurvivors;
    }
    private int countNumRemovedNodes(CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft,Predicate<? super E> filter)
    {
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
          int numRemoved=1;
          long[] survivorSet;
          int numSurvivors;
          if(numLeft!=0 && (numSurvivors=(before=before.next).markSurvivors(numLeft,survivorSet=BitSetUtil.getBitSet(numLeft),filter))!=0)
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
          prev.next=null;
          return numRemoved;
        }
      }
      return 0;
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
          lastKnownSurvivor.next=(lastKnownSurvivor=curr);
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
    private int countNumRemovedNodes(Predicate<? super E> filter)
    {
      int numRemoved=0;
      for(Node<E> prev,curr=(prev=this).next;curr!=null;curr=(prev=curr).next)
      {
        if(filter.test(curr.val))
        {
          do
          {
            ++numRemoved;
            if((curr=curr.next)==null)
            {
              prev.next=null;
              return numRemoved;
            }
          }
          while(filter.test(curr.val));
          prev.next=curr;
        }
      }
      return numRemoved;
    }
    private int countNumRemainingNodes(Predicate<? super E> filter)
    {
      int numLeft=1;
      for(Node<E> prev,curr=(prev=this).next;curr!=null;++numLeft,curr=(prev=curr).next)
      {
        if(filter.test(curr.val))
        {
          do
          {
            if((curr=curr.next)==null)
            {
              prev.next=null;
              return numLeft;
            }
          }
          while(filter.test(curr.val));
          prev.next=curr;
        }
      }
      return numLeft;
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
    private Node<E> iterateForward(int dist)
    {
      if(dist!=0)
      {
        return uncheckedIterateForward(dist);
      }
      return this;
    }
    transient E val;
    transient Node<E> next;
    Node(E val)
    {
      this.val=val;
    }
    Node(E val,Node<E> next)
    {
      this.val=val;
      this.next=next;
    }
    private void uncheckedForEachForward(Consumer<? super E> action)
    {
      var curr=this;
      do
      {
        action.accept(curr.val);
      }
      while((curr=curr.next)!=null);
    }
    private int uncheckedForwardHashCode()
    {
      int hash=31+Objects.hashCode(this.val);
      for(var curr=next;curr!=null;hash=hash*31+Objects.hashCode(curr.val),curr=curr.next)
      {  
      }
      return hash;
    }
    private void uncheckedForwardToString(StringBuilder builder)
    {
      Node<E> curr;
      builder.append((curr=this).val);
      while((curr=curr.next)!=null)
      {
        builder.append(',').append(' ').append(curr.val);
      }
    }
    private boolean uncheckedcontains(Predicate<Object> pred)
    {
      for(var curr=this;!pred.test(curr.val);)
      {
        if((curr=curr.next)==null)
        {
          return false;
        }
      }
      return true;
    }
    private int uncheckedsearch(Predicate<Object> pred)
    {
      int index=1;
      for(var curr=this;!pred.test(curr.val);++index)
      {
        if((curr=curr.next)==null)
        {
          return -1;
        }
      }
      return index;
    }
    private void uncheckedCopyForward(Object[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=(Object)(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
  }
}
