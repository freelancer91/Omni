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
import omni.util.TypeUtil;
import omni.impl.AbstractDoubleItr;
import omni.util.HashUtil;
import java.util.function.DoublePredicate;
import java.util.function.DoubleConsumer;
public abstract class DoubleSnglLnkSeq 
extends AbstractSeq.OfDouble
implements OmniCollection.OfDouble
{
  transient Node head;
  private DoubleSnglLnkSeq()
  {
    super();
  }
  private DoubleSnglLnkSeq(Node onlyNode)
  {
    super(1);
    this.head=onlyNode;
  }
  private DoubleSnglLnkSeq(Node head,int size)
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
    Node head;
    if((head=this.head)!=null)
    {
      return head.uncheckedForwardHashCode();
    }
    return 1;
  }
  @Override
  public String toString()
  {
    final Node head;
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
    Node head;
    if((head=this.head)!=null)
    {
      if(val instanceof Double)
      {
        return head.uncheckedcontains((double)(val));
      }
    }
    return false;
  }
  public int search(Object val)
  {
    Node head;
    if((head=this.head)!=null)
    {
      if(val instanceof Double)
      {
        return head.uncheckedsearch((double)(val));
      }
    }
    return -1;
  }
  @Override
  public boolean removeIf(DoublePredicate filter)
  {
    Node head;
    if((head=this.head)!=null)
    {
      return uncheckedRemoveIf(head,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super Double> filter)
  {
    Node head;
    if((head=this.head)!=null)
    {
      return uncheckedRemoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedRemoveIf(Node head,DoublePredicate filter);
  private boolean uncheckedremoveVal(Node head,double val)
  {
    if(val==val)
    {
      return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
    }
    return uncheckedremoveValNaN(head);
  }
  public double peekDouble()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return (double)(head.val);
    }
    return Double.NaN;
  }
  @Override
  public double[] toDoubleArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final double[] dst;
      head.uncheckedCopyForward(dst=new double[size],0);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  public Double peek()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return (Double)(head.val);
    }
    return null;
  }
  @Override
  public Double[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Double[] dst;
      head.uncheckedCopyForward(dst=new Double[size],0);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
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
  public double pollDouble()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return (double)(head.val);
    }
    return Double.NaN;
  }
  public Double poll()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return (head.val);
    }
    return null;
  }
  @Override
  public boolean add(double val)
  {
    push((double)(val));
    return true;
  }
  public boolean offer(double val)
  {
    push((double)(val));
    return true;
  }
  public void push(Double val)
  {
    push((double)(val));
  }
  @Override
  public boolean add(Double val)
  {
    push((double)(val));
    return true;
  }
  public boolean offer(Double val)
  {
    push((double)(val));
    return true;
  }
  @Override
  public boolean add(boolean val)
  {
    push((double)TypeUtil.castToDouble(val));
    return true;
  }
  @Override
  public boolean add(int val)
  {
    push((double)(val));
    return true;
  }
  @Override
  public boolean add(char val)
  {
    push((double)(val));
    return true;
  }
  @Override
  public boolean add(short val)
  {
    push((double)(val));
    return true;
  }
  @Override
  public boolean add(long val)
  {
    push((double)(val));
    return true;
  }
  @Override
  public boolean add(float val)
  {
    push((double)(val));
    return true;
  }
  public Double pop()
  {
    return (Double)popDouble();
  }
  public double removeDouble()
  {
    return (double)popDouble();
  }
  public Double remove()
  {
    return (Double)popDouble();
  }
  public double popDouble()
  {
    Node head;
    --this.size;
    uncheckedchophead(head=this.head);
    return head.val;
  }
  @Override
  public boolean remove(Object val)
  {
    Node head;
    if((head=this.head)!=null)
    {
      if(val instanceof Double)
      {
        return uncheckedremoveVal(head,(double)(val));
      }
    }
    return false;
  }
  abstract void push(double val);
  abstract void uncheckedchophead(Node oldHead);
  @Override
  public boolean removeVal(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val)
      {
        return this.uncheckedremoveValBits(head,TypeUtil.DBL_TRUE_BITS);
      }
      return this.uncheckedremoveVal0(head);
    }
    return false;
  }
  @Override
  public boolean removeVal(final int val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val!=0)
      {
        {
          return this.uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return this.uncheckedremoveVal0(head);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final long val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return this.uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return this.uncheckedremoveVal0(head);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final float val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val==val)
      {
        return this.uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
      }
      return this.uncheckedremoveValNaN(head);
    }
    return false;
  }
  @Override
  public boolean removeVal(final double val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return this.uncheckedremoveVal(head,(val));
    }
    return false;
  }
  @Override
  public boolean contains(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val)
      {
        return head.uncheckedcontainsBits(TypeUtil.DBL_TRUE_BITS);
      }
      return head.uncheckedcontains0();
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val!=0)
      {
        {
          return head.uncheckedcontainsBits(Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return head.uncheckedcontains0();
      }
    }
    return false;
  }
  @Override
  public boolean contains(final long val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return head.uncheckedcontainsBits(Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return head.uncheckedcontains0();
      }
    }
    return false;
  }
  @Override
  public boolean contains(final float val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val==val)
      {
        return head.uncheckedcontainsBits(Double.doubleToRawLongBits(val));
      }
      return head.uncheckedcontainsNaN();
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains((val));
    }
    return false;
  }
  public int search(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val)
      {
        return head.uncheckedsearchBits(TypeUtil.DBL_TRUE_BITS);
      }
      return head.uncheckedsearch0();
    }
    return -1;
  }
  public int search(final int val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val!=0)
      {
        {
          return head.uncheckedsearchBits(Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return head.uncheckedsearch0();
      }
    }
    return -1;
  }
  public int search(final long val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return head.uncheckedsearchBits(Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return head.uncheckedsearch0();
      }
    }
    return -1;
  }
  public int search(final float val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val==val)
      {
        return head.uncheckedsearchBits(Double.doubleToRawLongBits(val));
      }
      return head.uncheckedsearchNaN();
    }
    return -1;
  }
  public int search(final double val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch((val));
    }
    return -1;
  }
  @Override
  public void forEach(DoubleConsumer action)
  {
    Node head;
    if((head=this.head)!=null)
    {
      head.uncheckedForEachForward(action);
    }
  }
  @Override
  public void forEach(Consumer<? super Double> action)
  {
    Node head;
    if((head=this.head)!=null)
    {
      head.uncheckedForEachForward(action::accept);
    }
  }
  boolean uncheckedremoveValBits(Node head,long bits)
  {
    if(bits!=Double.doubleToRawLongBits(head.val))
    {
      Node prev;
      do
      {
        if((head=(prev=head).next)==null)
        {
          return false;
        }
      }
      while(bits!=Double.doubleToRawLongBits(head.val));
      prev.next=head.next;
    }
    else
    {
      this.head=head.next;
    }
    --this.size;
    return true;
  }
  boolean uncheckedremoveVal0(Node head)
  {
    if(0!=(head.val))
    {
      Node prev;
      do
      {
        if((head=(prev=head).next)==null)
        {
          return false;
        }
      }
      while(0!=(head.val));
      prev.next=head.next;
    }
    else
    {
      this.head=head.next;
    }
    --this.size;
    return true;
  }
  boolean uncheckedremoveValNaN(Node head)
  {
    if(!Double.isNaN(head.val))
    {
      Node prev;
      do
      {
        if((head=(prev=head).next)==null)
        {
          return false;
        }
      }
      while(!Double.isNaN(head.val));
      prev.next=head.next;
    }
    else
    {
      this.head=head.next;
    }
    --this.size;
    return true;
  }
  private static abstract class AbstractCheckedItr extends AbstractUncheckedItr
  {
    transient int modCount;
    abstract void checkModCount(int modCount);
    AbstractCheckedItr(Node next,int modCount)
    {
      super(next);
      this.modCount=modCount;
    }
    @Override
    public double nextDouble()
    {
      checkModCount(this.modCount);
      final Node ret;
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
    void uncheckedForEachRemaining(Node next,DoubleConsumer action)
    {
      Node prev,pPrev;
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
  private static abstract class AbstractUncheckedItr
    extends AbstractDoubleItr
    implements OmniIterator.OfDouble
  {
    Node next;
    Node prev;
    Node pPrev;
    AbstractUncheckedItr(Node next)
    {
      this.next=next;
    }
    @Override
    public boolean hasNext()
    {
      return this.next!=null;
    }
    @Override
    public double nextDouble()
    {
      this.pPrev=this.prev;
      final Node ret;
      this.prev=ret=this.next;
      this.next=ret.next;
      return ret.val;
    }
    void uncheckedForEachRemaining(Node next,DoubleConsumer action)
    {
      for(Node prev,pPrev=this.prev;;pPrev=prev)
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
    public void forEachRemaining(final DoubleConsumer action)
    {
      final Node next;
      if((next=this.next)!=null)
      {
         uncheckedForEachRemaining(next,action);
      }
    }
    @Override
    public void forEachRemaining(final Consumer<? super Double> action)
    {
      final Node next;
      if((next=this.next)!=null)
      {
         uncheckedForEachRemaining(next,action::accept);
      }
    }
  }
  public static class Stack extends DoubleSnglLnkSeq implements OmniStack.OfDouble
  {
    public Stack()
    {
      super();
    }
    public Stack(Node onlyNode)
    {
      super(onlyNode);
    }
    public Stack(Node head,int size)
    {
      super(head,size);
    }
    @Override
    public void push(double val)
    {
      ++this.size;
      this.head=new Node(val,this.head);
    }
    @Override
    public Object clone()
    {
      Node oldHead,newHead;
      if((oldHead=this.head)!=null)
      {
        for(var newTail=newHead=new Node(oldHead.val);(oldHead=oldHead.next)!=null;newTail.next=newTail=new Node(oldHead.val)){}
      }
      else
      {
        newHead=null;
      }
      return new Stack(newHead,this.size);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      return new Itr(this);
    }
    @Override
    void uncheckedchophead(Node oldHead)
    {
      this.head=oldHead.next;
    }
    private static class Itr extends AbstractUncheckedItr
    {
      private transient final Stack root;
      Itr(Stack root)
      {
        super(root.head);
        this.root=root;
      }
      @Override
      public void remove()
      {
        final Stack root;
        --(root=this.root).size;
        if(this.prev==root.head)
        {
          root.head=this.next;
          this.prev=null;
        }
        else
        {
          final Node pPrev;
          (pPrev=this.pPrev).next=this.next;
          this.prev=pPrev;
        }
      }
    }
    private void uncheckedRemoveIfHelper(Node prev,Node curr,DoublePredicate filter)
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
    boolean uncheckedRemoveIf(Node curr,DoublePredicate filter)
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
        Node prev;
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
    public static class Checked extends Stack
    {
      private static class Itr extends AbstractCheckedItr
      {
        private transient final Checked root;
        Itr(Checked root)
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
          final Node prev;
          if((prev=this.prev)!=null)
          {
            final Checked root;
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
              final Node pPrev;
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
      private void uncheckedRemoveIfHelper(int modCount,int numLeft,Node prev,Node curr,DoublePredicate filter)
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
      boolean uncheckedRemoveIf(Node curr,DoublePredicate filter)
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
             Node prev;
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
      public Checked(Node onlyNode)
      {
        super(onlyNode);
      }
      public Checked(Node head,int size)
      {
        super(head,size);
      }
      @Override
      public void push(double val)
      {
        ++this.modCount;
        super.push(val);
      }
      @Override
      public OmniIterator.OfDouble iterator()
      {
        return new Itr(this);
      }
      @Override
      public double popDouble()
      {
        Node head;
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
      public double pollDouble()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (double)(head.val);
        }
        return Double.NaN;
      }
      @Override
      public Double poll()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (head.val);
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
      public void forEach(DoubleConsumer action)
      {
        Node head;
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
      public void forEach(Consumer<? super Double> action)
      {
        Node head;
        if((head=this.head)!=null)
        {
          int modCount=this.modCount;
          try
          {
            head.uncheckedForEachForward(action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
      }
      @Override
      public Object clone()
      {
        Node oldHead,newHead;
        if((oldHead=this.head)!=null)
        {
          for(var newTail=newHead=new Node(oldHead.val);(oldHead=oldHead.next)!=null;newTail.next=newTail=new Node(oldHead.val)){}
        }
        else
        {
          newHead=null;
        }
        return new Checked(newHead,this.size);
      }
      @Override
      boolean uncheckedremoveValBits(Node head,long bits)
      {
        if(bits!=Double.doubleToRawLongBits(head.val))
        {
          Node prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(bits!=Double.doubleToRawLongBits(head.val));
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
      @Override
      boolean uncheckedremoveVal0(Node head)
      {
        if(0!=(head.val))
        {
          Node prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(0!=(head.val));
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
      @Override
      boolean uncheckedremoveValNaN(Node head)
      {
        if(!Double.isNaN(head.val))
        {
          Node prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(!Double.isNaN(head.val));
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
  public static class Queue extends DoubleSnglLnkSeq implements OmniQueue.OfDouble
  {
    transient Node tail;
    public Queue()
    {
      super();
    }
    public Queue(Node onlyNode)
    {
      super(onlyNode);
      this.tail=onlyNode;
    }
    public Queue(Node head,int size,Node tail)
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
      Node oldHead,newHead,newTail;
      if((oldHead=this.head)!=null)
      {
        for(newTail=newHead=new Node(oldHead.val);(oldHead=oldHead.next)!=null;newTail.next=newTail=new Node(oldHead.val)){}
      }
      else
      {
        newHead=null;
        newTail=null;
      }
      return new Queue(newHead,this.size,newTail);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method
      return false;
    }
    private static class Itr extends AbstractUncheckedItr
    {
      private transient final Queue root;
      Itr(Queue root)
      {
        super(root.head);
        this.root=root;
      }
      @Override
      public void remove()
      {
        final Queue root;
        --(root=this.root).size;
        final Node prev;
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
          final Node pPrev;
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
    public OmniIterator.OfDouble iterator()
    {
      return new Itr(this);
    }
    @Override
    public double doubleElement()
    {
      return head.val;
    }
    @Override
    void push(double val)
    {
      final var newNode=new Node(val);
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
    public Double element()
    {
      return (Double)doubleElement();
    }
    @Override
    void uncheckedchophead(Node oldHead)
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
    private void uncheckedRemoveIfHelper(Node prev,Node curr,DoublePredicate filter)
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
    private int uncheckedRemoveIfHelper(Node curr,DoublePredicate filter)
    {
      int numSurvivors=1;
      for(final var tail=this.tail;curr!=tail;++numSurvivors)
      {
        Node prev;
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
    boolean uncheckedRemoveIf(Node curr,DoublePredicate filter)
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
        Node prev;
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
    boolean uncheckedremoveValBits(Node head,long bits)
    {
      if(bits!=Double.doubleToRawLongBits(head.val))
      {
        Node prev;
        do
        {
          if((head=(prev=head).next)==null)
          {
            return false;
          }
        }
        while(bits!=Double.doubleToRawLongBits(head.val));
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
    @Override
    boolean uncheckedremoveVal0(Node head)
    {
      if(0!=(head.val))
      {
        Node prev;
        do
        {
          if((head=(prev=head).next)==null)
          {
            return false;
          }
        }
        while(0!=(head.val));
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
    @Override
    boolean uncheckedremoveValNaN(Node head)
    {
      if(!Double.isNaN(head.val))
      {
        Node prev;
        do
        {
          if((head=(prev=head).next)==null)
          {
            return false;
          }
        }
        while(!Double.isNaN(head.val));
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
    public static class Checked extends Queue
    {
      private static class Itr extends AbstractCheckedItr
      {
        private transient final Checked root;
        Itr(Checked root)
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
          final Node prev;
          if((prev=this.prev)!=null)
          {
            final Checked root;
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
              final Node pPrev;
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
      private int uncheckedRemoveIfHelper(int modCount,int numLeft,Node curr,DoublePredicate filter)
      {
        int numSurvivors=1;
        for(Node prev;;++numSurvivors)
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
      private void uncheckedRemoveIfHelper(int modCount,int numLeft,Node prev,Node curr,DoublePredicate filter)
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
      public Checked(Node onlyNode)
      {
        super(onlyNode);
      }
      public Checked(Node head,int size,Node tail)
      {
        super(head,size,tail);
      }
      @Override
      boolean uncheckedRemoveIf(Node curr,DoublePredicate filter)
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
             Node prev;
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
      @Override
      public double popDouble()
      {
        Node head;
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
      void push(double val)
      {
        ++this.modCount;
        super.push(val);
      }
      @Override
      public double doubleElement()
      {
        Node head;
        if((head=this.head)!=null)
        {
          return head.val;
        }
        throw new NoSuchElementException();
      }
      @Override
      public OmniIterator.OfDouble iterator()
      {
        return new Itr(this);
      }
      @Override
      public double pollDouble()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (double)(head.val);
        }
        return Double.NaN;
      }
      @Override
      public Double poll()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (head.val);
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
      public void forEach(DoubleConsumer action)
      {
        Node head;
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
      public void forEach(Consumer<? super Double> action)
      {
        Node head;
        if((head=this.head)!=null)
        {
          int modCount=this.modCount;
          try
          {
            head.uncheckedForEachForward(action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
      }
      @Override
      public Object clone()
      {
        Node oldHead,newHead,newTail;
        if((oldHead=this.head)!=null)
        {
          for(newTail=newHead=new Node(oldHead.val);(oldHead=oldHead.next)!=null;newTail.next=newTail=new Node(oldHead.val)){}
        }
        else
        {
          newHead=null;
          newTail=null;
        }
        return new Checked(newHead,this.size,newTail);
      }
      @Override
      boolean uncheckedremoveValBits(Node head,long bits)
      {
        if(bits!=Double.doubleToRawLongBits(head.val))
        {
          Node prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(bits!=Double.doubleToRawLongBits(head.val));
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
      @Override
      boolean uncheckedremoveVal0(Node head)
      {
        if(0!=(head.val))
        {
          Node prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(0!=(head.val));
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
      @Override
      boolean uncheckedremoveValNaN(Node head)
      {
        if(!Double.isNaN(head.val))
        {
          Node prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(!Double.isNaN(head.val));
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
  static class Node
  {
    private int countNumRemainingNodes(CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft,DoublePredicate filter)
    {
      int numSurvivors=1;
      for(Node before,prev=this;;++numSurvivors,prev=before)
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
    private int countNumRemovedNodes(CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft,DoublePredicate filter)
    {
      for(Node before,prev=this;;prev=before)
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
    private int markSurvivors(int numLeft,long[] survivorSet,DoublePredicate filter)
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
    private Node retainSurvivors(Node curr,int numSurvivors,long[] survivorSet)
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
    private int countNumRemovedNodes(DoublePredicate filter)
    {
      int numRemoved=0;
      for(Node prev,curr=(prev=this).next;curr!=null;curr=(prev=curr).next)
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
    private int countNumRemainingNodes(DoublePredicate filter)
    {
      int numLeft=1;
      for(Node prev,curr=(prev=this).next;curr!=null;++numLeft,curr=(prev=curr).next)
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
    private Node uncheckedIterateForward(int dist)
    {
      var curr=next;
      while(--dist!=0)
      {
        curr=curr.next;
      }
      return curr;
    }
    private Node iterateForward(int dist)
    {
      if(dist!=0)
      {
        return uncheckedIterateForward(dist);
      }
      return this;
    }
    transient double val;
    transient Node next;
    Node(double val)
    {
      this.val=val;
    }
    Node(double val,Node next)
    {
      this.val=val;
      this.next=next;
    }
    private void uncheckedForEachForward(DoubleConsumer action)
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
      int hash=31+HashUtil.hashDouble(this.val);
      for(var curr=next;curr!=null;hash=hash*31+HashUtil.hashDouble(curr.val),curr=curr.next)
      {  
      }
      return hash;
    }
    private void uncheckedForwardToString(StringBuilder builder)
    {
      Node curr;
      builder.append((curr=this).val);
      while((curr=curr.next)!=null)
      {
        builder.append(',').append(' ').append(curr.val);
      }
    }
    private boolean uncheckedcontainsBits(long bits)
    {
      for(var curr=this;bits!=Double.doubleToRawLongBits(curr.val);)
      {
        if((curr=curr.next)==null)
        {
          return false;
        }
      }
      return true;
    }
    private boolean uncheckedcontains0()
    {
      for(var curr=this;0!=(curr.val);)
      {
        if((curr=curr.next)==null)
        {
          return false;
        }
      }
      return true;
    }
    private boolean uncheckedcontainsNaN()
    {
      for(var curr=this;!Double.isNaN(curr.val);)
      {
        if((curr=curr.next)==null)
        {
          return false;
        }
      }
      return true;
    }
    private boolean uncheckedcontains(double val)
    {
      if(val==val)
      {
        return uncheckedcontainsBits(Double.doubleToRawLongBits(val));
      }
      return uncheckedcontainsNaN();
    }
    private int uncheckedsearchBits(long bits)
    {
      int index=1;
      for(var curr=this;bits!=Double.doubleToRawLongBits(curr.val);++index)
      {
        if((curr=curr.next)==null)
        {
          return -1;
        }
      }
      return index;
    }
    private int uncheckedsearch0()
    {
      int index=1;
      for(var curr=this;0!=(curr.val);++index)
      {
        if((curr=curr.next)==null)
        {
          return -1;
        }
      }
      return index;
    }
    private int uncheckedsearchNaN()
    {
      int index=1;
      for(var curr=this;!Double.isNaN(curr.val);++index)
      {
        if((curr=curr.next)==null)
        {
          return -1;
        }
      }
      return index;
    }
    private int uncheckedsearch(double val)
    {
      if(val==val)
      {
        return uncheckedsearchBits(Double.doubleToRawLongBits(val));
      }
      return uncheckedsearchNaN();
    }
    private void uncheckedCopyForward(double[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=(double)(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(Object[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(Double[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=(Double)(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
  }
}
