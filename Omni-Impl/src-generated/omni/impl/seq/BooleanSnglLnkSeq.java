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
import omni.util.TypeUtil;
import omni.impl.AbstractBooleanItr;
import omni.function.BooleanPredicate;
import omni.function.BooleanConsumer;
public abstract class BooleanSnglLnkSeq 
extends AbstractSeq
implements OmniCollection.OfBoolean
{
  transient Node head;
  private BooleanSnglLnkSeq()
  {
    super();
  }
  private BooleanSnglLnkSeq(Node onlyNode)
  {
    super(1);
    this.head=onlyNode;
  }
  private BooleanSnglLnkSeq(Node head,int size)
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
      if(val instanceof Boolean)
      {
        return head.uncheckedcontains((boolean)(val));
      }
    }
    return false;
  }
  public int search(Object val)
  {
    Node head;
    if((head=this.head)!=null)
    {
      if(val instanceof Boolean)
      {
        return head.uncheckedsearch((boolean)(val));
      }
    }
    return -1;
  }
  @Override
  public boolean removeIf(BooleanPredicate filter)
  {
    Node head;
    if((head=this.head)!=null)
    {
      return uncheckedRemoveIf(head,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super Boolean> filter)
  {
    Node head;
    if((head=this.head)!=null)
    {
      return uncheckedRemoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedRemoveIf(Node head,BooleanPredicate filter);
  public boolean peekBoolean()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return (boolean)(head.val);
    }
    return false;
  }
  @Override
  public boolean[] toBooleanArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean[] dst;
      head.uncheckedCopyForward(dst=new boolean[size],0);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  public Boolean peek()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return (Boolean)(head.val);
    }
    return null;
  }
  @Override
  public Boolean[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Boolean[] dst;
      head.uncheckedCopyForward(dst=new Boolean[size],0);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  public double peekDouble()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return TypeUtil.castToDouble(head.val);
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
  public float peekFloat()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return TypeUtil.castToFloat(head.val);
    }
    return Float.NaN;
  }
  @Override
  public float[] toFloatArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float[] dst;
      head.uncheckedCopyForward(dst=new float[size],0);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  public long peekLong()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return TypeUtil.castToLong(head.val);
    }
    return Long.MIN_VALUE;
  }
  @Override
  public long[] toLongArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long[] dst;
      head.uncheckedCopyForward(dst=new long[size],0);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  public int peekInt()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return (int)TypeUtil.castToByte(head.val);
    }
    return Integer.MIN_VALUE;
  }
  @Override
  public int[] toIntArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int[] dst;
      head.uncheckedCopyForward(dst=new int[size],0);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  public short peekShort()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return (short)TypeUtil.castToByte(head.val);
    }
    return Short.MIN_VALUE;
  }
  @Override
  public short[] toShortArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short[] dst;
      head.uncheckedCopyForward(dst=new short[size],0);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  public byte peekByte()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return TypeUtil.castToByte(head.val);
    }
    return Byte.MIN_VALUE;
  }
  @Override
  public byte[] toByteArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte[] dst;
      head.uncheckedCopyForward(dst=new byte[size],0);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  public char peekChar()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return TypeUtil.castToChar(head.val);
    }
    return Character.MIN_VALUE;
  }
  @Override
  public char[] toCharArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char[] dst;
      head.uncheckedCopyForward(dst=new char[size],0);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
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
  public boolean pollBoolean()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return (boolean)(head.val);
    }
    return false;
  }
  public Boolean poll()
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
  public double pollDouble()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return TypeUtil.castToDouble(head.val);
    }
    return Double.NaN;
  }
  public float pollFloat()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return TypeUtil.castToFloat(head.val);
    }
    return Float.NaN;
  }
  public long pollLong()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return TypeUtil.castToLong(head.val);
    }
    return Long.MIN_VALUE;
  }
  public int pollInt()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return (int)TypeUtil.castToByte(head.val);
    }
    return Integer.MIN_VALUE;
  }
  public short pollShort()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return (short)TypeUtil.castToByte(head.val);
    }
    return Short.MIN_VALUE;
  }
  public byte pollByte()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return TypeUtil.castToByte(head.val);
    }
    return Byte.MIN_VALUE;
  }
  public char pollChar()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return TypeUtil.castToChar(head.val);
    }
    return Character.MIN_VALUE;
  }
  @Override
  public boolean add(boolean val)
  {
    push((boolean)(val));
    return true;
  }
  public boolean offer(boolean val)
  {
    push((boolean)(val));
    return true;
  }
  public void push(Boolean val)
  {
    push((boolean)(val));
  }
  @Override
  public boolean add(Boolean val)
  {
    push((boolean)(val));
    return true;
  }
  public boolean offer(Boolean val)
  {
    push((boolean)(val));
    return true;
  }
  public Boolean pop()
  {
    return (Boolean)popBoolean();
  }
  public boolean removeBoolean()
  {
    return (boolean)popBoolean();
  }
  public Boolean remove()
  {
    return (Boolean)popBoolean();
  }
  public boolean popBoolean()
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
      if(val instanceof Boolean)
      {
        return uncheckedremoveVal(head,(boolean)(val));
      }
    }
    return false;
  }
  abstract void push(boolean val);
  abstract void uncheckedchophead(Node oldHead);
  @Override
  public boolean removeVal(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return this.uncheckedremoveVal(head,(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final int val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final boolean v;
      switch(val)
      {
        default:
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return this.uncheckedremoveVal(head,v);
    }
    return false;
  }
  @Override
  public boolean removeVal(final long val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return this.uncheckedremoveVal(head,v);
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
      final boolean v;
      switch(Float.floatToRawIntBits(val))
      {
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return this.uncheckedremoveVal(head,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final double val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return this.uncheckedremoveVal(head,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return head.uncheckedcontains((val));
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final boolean v;
      switch(val)
      {
        default:
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return head.uncheckedcontains(v);
    }
    return false;
  }
  @Override
  public boolean contains(final long val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return head.uncheckedcontains(v);
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
      final boolean v;
      switch(Float.floatToRawIntBits(val))
      {
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return head.uncheckedcontains(v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return head.uncheckedcontains(v);
      }
    }
    return false;
  }
  public int search(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch((val));
    }
    return -1;
  }
  public int search(final int val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final boolean v;
      switch(val)
      {
        default:
          return -1;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return head.uncheckedsearch(v);
    }
    return -1;
  }
  public int search(final long val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return -1;
      }
      {
        return head.uncheckedsearch(v);
      }
    }
    return -1;
  }
  public int search(final float val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final boolean v;
      switch(Float.floatToRawIntBits(val))
      {
        default:
          return -1;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return head.uncheckedsearch(v);
      }
    }
    return -1;
  }
  public int search(final double val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return -1;
      }
      {
        return head.uncheckedsearch(v);
      }
    }
    return -1;
  }
  @Override
  public void forEach(BooleanConsumer action)
  {
    Node head;
    if((head=this.head)!=null)
    {
      head.uncheckedForEachForward(action);
    }
  }
  @Override
  public void forEach(Consumer<? super Boolean> action)
  {
    Node head;
    if((head=this.head)!=null)
    {
      head.uncheckedForEachForward(action::accept);
    }
  }
  boolean uncheckedremoveVal(Node head,boolean val)
  {
    if(val^(head.val))
    {
      Node prev;
      do
      {
        if((head=(prev=head).next)==null)
        {
          return false;
        }
      }
      while(val^(head.val));
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
    public boolean nextBoolean()
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
    void uncheckedForEachRemaining(Node next,BooleanConsumer action)
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
    extends AbstractBooleanItr
    implements OmniIterator.OfBoolean
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
    public boolean nextBoolean()
    {
      this.pPrev=this.prev;
      final Node ret;
      this.prev=ret=this.next;
      this.next=ret.next;
      return ret.val;
    }
    void uncheckedForEachRemaining(Node next,BooleanConsumer action)
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
    public void forEachRemaining(final BooleanConsumer action)
    {
      final Node next;
      if((next=this.next)!=null)
      {
         uncheckedForEachRemaining(next,action);
      }
    }
    @Override
    public void forEachRemaining(final Consumer<? super Boolean> action)
    {
      final Node next;
      if((next=this.next)!=null)
      {
         uncheckedForEachRemaining(next,action::accept);
      }
    }
  }
  public static class Stack extends BooleanSnglLnkSeq implements OmniStack.OfBoolean
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
    public void push(boolean val)
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
    public OmniIterator.OfBoolean iterator()
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
    private void uncheckedRemoveIfHelper(Node prev,Node curr,boolean retainThis)
    {
      int numRemoved=1;
      while((curr=curr.next)!=null)
      {
        if(retainThis==(curr.val))
        {
          prev.next=curr;
          this.size-=(numRemoved+curr.countNumRemovedNodes(retainThis));
          return;
        }
        ++numRemoved;
      }
      prev.next=null;
      this.size-=numRemoved;
    }
    @Override
    boolean uncheckedRemoveIf(Node curr,BooleanPredicate filter)
    {
      boolean v;
      if(filter.test(v=curr.val))
      {
        while((curr=curr.next)!=null)
        {
          if(v^curr.val)
          {
            if(filter.test(v=!v))
            {
              break;
            }
            this.size=curr.countNumRemainingNodes(v);
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
          if(v^curr.val)
          {
            if(!filter.test(!v))
            {
              break;
            }
            uncheckedRemoveIfHelper(prev,curr,v);
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
      @Override
      boolean uncheckedRemoveIf(Node curr,BooleanPredicate filter)
      {
         int modCount=this.modCount;
         try
         {
           boolean v;
           if(filter.test(v=curr.val))
           {
             while((curr=curr.next)!=null)
             {
               if(v^curr.val)
               {
                 if(filter.test(v=!v))
                 {
                   break;
                 }
                 CheckedCollection.checkModCount(modCount,this.modCount);
                 this.size=curr.countNumRemainingNodes(v);
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
               if(v^curr.val)
               {
                 if(!filter.test(!v))
                 {
                   break;
                 }
                 CheckedCollection.checkModCount(modCount,this.modCount);
                 super.uncheckedRemoveIfHelper(prev,curr,v);
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
      public void push(boolean val)
      {
        ++this.modCount;
        super.push(val);
      }
      @Override
      public OmniIterator.OfBoolean iterator()
      {
        return new Itr(this);
      }
      @Override
      public boolean popBoolean()
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
      public boolean pollBoolean()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (boolean)(head.val);
        }
        return false;
      }
      @Override
      public Boolean poll()
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
      public double pollDouble()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return TypeUtil.castToDouble(head.val);
        }
        return Double.NaN;
      }
      @Override
      public float pollFloat()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return TypeUtil.castToFloat(head.val);
        }
        return Float.NaN;
      }
      @Override
      public long pollLong()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return TypeUtil.castToLong(head.val);
        }
        return Long.MIN_VALUE;
      }
      @Override
      public int pollInt()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (int)TypeUtil.castToByte(head.val);
        }
        return Integer.MIN_VALUE;
      }
      @Override
      public short pollShort()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (short)TypeUtil.castToByte(head.val);
        }
        return Short.MIN_VALUE;
      }
      @Override
      public byte pollByte()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return TypeUtil.castToByte(head.val);
        }
        return Byte.MIN_VALUE;
      }
      @Override
      public char pollChar()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return TypeUtil.castToChar(head.val);
        }
        return Character.MIN_VALUE;
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
      public void forEach(BooleanConsumer action)
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
      public void forEach(Consumer<? super Boolean> action)
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
      boolean uncheckedremoveVal(Node head,boolean val)
      {
        if(val^(head.val))
        {
          Node prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(val^(head.val));
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
  public static class Queue extends BooleanSnglLnkSeq implements OmniQueue.OfBoolean
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
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
    }
    @Override
    public boolean booleanElement()
    {
      return head.val;
    }
    @Override
    void push(boolean val)
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
    public Boolean element()
    {
      return (Boolean)booleanElement();
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
    private void uncheckedRemoveIfHelper(Node prev,Node curr,boolean retainThis)
    {
      int numRemoved=1;
      for(final var tail=this.tail;curr!=tail;++numRemoved)
      {
        if(retainThis==((curr=curr.next).val))
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
          while(retainThis==((curr=(prev=curr).next).val));
        }
      }
      this.tail=prev;
      prev.next=null;
      this.size-=numRemoved;
    }
    private int uncheckedRemoveIfHelper(Node curr,boolean retainThis)
    {
      int numSurvivors=1;
      for(final var tail=this.tail;curr!=tail;++numSurvivors)
      {
        Node prev;
        if(retainThis^((curr=(prev=curr).next).val))
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
          while(retainThis^((curr=curr.next).val));
          prev.next=curr;
        }
      }
      return numSurvivors;
    }
    @Override
    boolean uncheckedRemoveIf(Node curr,BooleanPredicate filter)
    {
      boolean v;
      if(filter.test(v=curr.val))
      {
        while((curr=curr.next)!=null)
        {
          if(v^curr.val)
          {
            if(filter.test(v=!v))
            {
              break;
            }
            this.size=uncheckedRemoveIfHelper(curr,v);
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
          if(v^curr.val)
          {
            if(!filter.test(!v))
            {
              break;
            }
            uncheckedRemoveIfHelper(prev,curr,v);
            return true;
          }
        }
        return false;
      }
    }
    @Override
    boolean uncheckedremoveVal(Node head,boolean val)
    {
      if(val^(head.val))
      {
        Node prev;
        do
        {
          if((head=(prev=head).next)==null)
          {
            return false;
          }
        }
        while(val^(head.val));
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
      boolean uncheckedRemoveIf(Node curr,BooleanPredicate filter)
      {
         int modCount=this.modCount;
         try
         {
           boolean v;
           if(filter.test(v=curr.val))
           {
             while((curr=curr.next)!=null)
             {
               if(v^curr.val)
               {
                 if(filter.test(v=!v))
                 {
                   break;
                 }
                 CheckedCollection.checkModCount(modCount,this.modCount);
                 this.size=super.uncheckedRemoveIfHelper(curr,v);
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
               if(v^curr.val)
               {
                 if(!filter.test(!v))
                 {
                   break;
                 }
                 CheckedCollection.checkModCount(modCount,this.modCount);
                 super.uncheckedRemoveIfHelper(prev,curr,v);
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
      public boolean popBoolean()
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
      void push(boolean val)
      {
        ++this.modCount;
        super.push(val);
      }
      @Override
      public boolean booleanElement()
      {
        Node head;
        if((head=this.head)!=null)
        {
          return head.val;
        }
        throw new NoSuchElementException();
      }
      @Override
      public OmniIterator.OfBoolean iterator()
      {
        return new Itr(this);
      }
      @Override
      public boolean pollBoolean()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (boolean)(head.val);
        }
        return false;
      }
      @Override
      public Boolean poll()
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
      public double pollDouble()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return TypeUtil.castToDouble(head.val);
        }
        return Double.NaN;
      }
      @Override
      public float pollFloat()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return TypeUtil.castToFloat(head.val);
        }
        return Float.NaN;
      }
      @Override
      public long pollLong()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return TypeUtil.castToLong(head.val);
        }
        return Long.MIN_VALUE;
      }
      @Override
      public int pollInt()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (int)TypeUtil.castToByte(head.val);
        }
        return Integer.MIN_VALUE;
      }
      @Override
      public short pollShort()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (short)TypeUtil.castToByte(head.val);
        }
        return Short.MIN_VALUE;
      }
      @Override
      public byte pollByte()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return TypeUtil.castToByte(head.val);
        }
        return Byte.MIN_VALUE;
      }
      @Override
      public char pollChar()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return TypeUtil.castToChar(head.val);
        }
        return Character.MIN_VALUE;
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
      public void forEach(BooleanConsumer action)
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
      public void forEach(Consumer<? super Boolean> action)
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
      boolean uncheckedremoveVal(Node head,boolean val)
      {
        if(val^(head.val))
        {
          Node prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(val^(head.val));
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
    private int countNumRemovedNodes(boolean retainThis)
    {
      int numRemoved=0;
      for(Node prev,curr=(prev=this).next;curr!=null;curr=(prev=curr).next)
      {
        if(retainThis^(curr.val))
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
          while(retainThis^(curr.val));
          prev.next=curr;
        }
      }
      return numRemoved;
    }
    private int countNumRemainingNodes(boolean retainThis)
    {
      int numLeft=1;
      for(Node prev,curr=(prev=this).next;curr!=null;++numLeft,curr=(prev=curr).next)
      {
        if(retainThis^(curr.val))
        {
          do
          {
            if((curr=curr.next)==null)
            {
              prev.next=null;
              return numLeft;
            }
          }
          while(retainThis^(curr.val));
          prev.next=curr;
        }
      }
      return numLeft;
    }
    transient boolean val;
    transient Node next;
    Node(boolean val)
    {
      this.val=val;
    }
    Node(boolean val,Node next)
    {
      this.val=val;
      this.next=next;
    }
    private void uncheckedForEachForward(BooleanConsumer action)
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
      int hash=31+Boolean.hashCode(this.val);
      for(var curr=next;curr!=null;hash=hash*31+Boolean.hashCode(curr.val),curr=curr.next)
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
    private boolean uncheckedcontains(boolean val)
    {
      for(var curr=this;val^(curr.val);)
      {
        if((curr=curr.next)==null)
        {
          return false;
        }
      }
      return true;
    }
    private int uncheckedsearch(boolean val)
    {
      int index=1;
      for(var curr=this;val^(curr.val);++index)
      {
        if((curr=curr.next)==null)
        {
          return -1;
        }
      }
      return index;
    }
    private void uncheckedCopyForward(boolean[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=(boolean)(src.val);
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
    private void uncheckedCopyForward(Boolean[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=(Boolean)(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(double[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=TypeUtil.castToDouble(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(float[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=TypeUtil.castToFloat(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(long[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=TypeUtil.castToLong(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(int[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=(int)TypeUtil.castToByte(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(short[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=(short)TypeUtil.castToByte(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(byte[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=TypeUtil.castToByte(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(char[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=TypeUtil.castToChar(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
  }
}
