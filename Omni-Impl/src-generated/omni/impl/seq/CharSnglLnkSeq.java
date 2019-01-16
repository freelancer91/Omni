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
import omni.util.BitSetUtils;
import omni.util.TypeUtil;
import omni.impl.AbstractCharItr;
import omni.function.CharPredicate;
import omni.function.CharConsumer;
public abstract class CharSnglLnkSeq 
extends AbstractSeq.Of16BitPrimitive
implements OmniCollection.OfChar
{
  transient Node head;
  private CharSnglLnkSeq()
  {
    super();
  }
  private CharSnglLnkSeq(Node onlyNode)
  {
    super(1);
    this.head=onlyNode;
  }
  private CharSnglLnkSeq(Node head,int size)
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
      if(val instanceof Character)
      {
        return head.uncheckedcontains((char)(val));
      }
    }
    return false;
  }
  public int search(Object val)
  {
    Node head;
    if((head=this.head)!=null)
    {
      if(val instanceof Character)
      {
        return head.uncheckedsearch((char)(val));
      }
    }
    return -1;
  }
  @Override
  public boolean removeIf(CharPredicate filter)
  {
    Node head;
    if((head=this.head)!=null)
    {
      return uncheckedRemoveIf(head,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super Character> filter)
  {
    Node head;
    if((head=this.head)!=null)
    {
      return uncheckedRemoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedRemoveIf(Node head,CharPredicate filter);
  public char peekChar()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return (char)(head.val);
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
  public Character peek()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return (Character)(head.val);
    }
    return null;
  }
  @Override
  public Character[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Character[] dst;
      head.uncheckedCopyForward(dst=new Character[size],0);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
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
  public float peekFloat()
  {
    Node head;
    if((head=this.head)!=null)
    {
      return (float)(head.val);
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
      return (long)(head.val);
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
      return (int)(head.val);
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
  public char pollChar()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      this.uncheckedchophead(head);
      return (char)(head.val);
    }
    return Character.MIN_VALUE;
  }
  public Character poll()
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
      return (double)(head.val);
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
      return (float)(head.val);
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
      return (long)(head.val);
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
      return (int)(head.val);
    }
    return Integer.MIN_VALUE;
  }
  @Override
  public boolean add(char val)
  {
    push((char)(val));
    return true;
  }
  public boolean offer(char val)
  {
    push((char)(val));
    return true;
  }
  public void push(Character val)
  {
    push((char)(val));
  }
  @Override
  public boolean add(Character val)
  {
    push((char)(val));
    return true;
  }
  public boolean offer(Character val)
  {
    push((char)(val));
    return true;
  }
  @Override
  public boolean add(boolean val)
  {
    push((char)TypeUtil.castToChar(val));
    return true;
  }
  public Character pop()
  {
    return (Character)popChar();
  }
  public char removeChar()
  {
    return (char)popChar();
  }
  public Character remove()
  {
    return (Character)popChar();
  }
  public char popChar()
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
      if(val instanceof Character)
      {
        return uncheckedremoveVal(head,(char)(val));
      }
    }
    return false;
  }
  abstract void push(char val);
  abstract void uncheckedchophead(Node oldHead);
  @Override
  public boolean removeVal(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return this.uncheckedremoveVal(head,TypeUtil.castToChar(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final int val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val==(char)val)
      {
        return this.uncheckedremoveVal(head,(val));
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
      {
        return this.uncheckedremoveVal(head,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final char val)
  {
    {
      final Node head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final short val)
  {
    if(val>=0)
    {
      final Node head;
      if((head=this.head)!=null)
      {
        return this.uncheckedremoveVal(head,(val));
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
      return head.uncheckedcontains(TypeUtil.castToChar(val));
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val==(char)val)
      {
        return head.uncheckedcontains((val));
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
      {
        return head.uncheckedcontains(v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(final char val)
  {
    {
      final Node head;
      if((head=this.head)!=null)
      {
        return head.uncheckedcontains((val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final short val)
  {
    if(val>=0)
    {
      final Node head;
      if((head=this.head)!=null)
      {
        return head.uncheckedcontains((val));
      }
    }
    return false;
  }
  public int search(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return head.uncheckedsearch(TypeUtil.castToChar(val));
    }
    return -1;
  }
  public int search(final int val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val==(char)val)
      {
        return head.uncheckedsearch((val));
      }
    }
    return -1;
  }
  public int search(final long val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
      {
        return head.uncheckedsearch(v);
      }
    }
    return -1;
  }
  public int search(final char val)
  {
    {
      final Node head;
      if((head=this.head)!=null)
      {
        return head.uncheckedsearch((val));
      }
    }
    return -1;
  }
  public int search(final short val)
  {
    if(val>=0)
    {
      final Node head;
      if((head=this.head)!=null)
      {
        return head.uncheckedsearch((val));
      }
    }
    return -1;
  }
  @Override
  public void forEach(CharConsumer action)
  {
    Node head;
    if((head=this.head)!=null)
    {
      head.uncheckedForEachForward(action);
    }
  }
  @Override
  public void forEach(Consumer<? super Character> action)
  {
    Node head;
    if((head=this.head)!=null)
    {
      head.uncheckedForEachForward(action::accept);
    }
  }
  boolean uncheckedremoveVal(Node head,int val)
  {
    if(val!=(head.val))
    {
      Node prev;
      do
      {
        if((head=(prev=head).next)==null)
        {
          return false;
        }
      }
      while(val!=(head.val));
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
    public char nextChar()
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
    void uncheckedForEachRemaining(Node next,CharConsumer action)
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
    extends AbstractCharItr
    implements OmniIterator.OfChar
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
    public char nextChar()
    {
      this.pPrev=this.prev;
      final Node ret;
      this.prev=ret=this.next;
      this.next=ret.next;
      return ret.val;
    }
    void uncheckedForEachRemaining(Node next,CharConsumer action)
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
    public void forEachRemaining(final CharConsumer action)
    {
      final Node next;
      if((next=this.next)!=null)
      {
         uncheckedForEachRemaining(next,action);
      }
    }
    @Override
    public void forEachRemaining(final Consumer<? super Character> action)
    {
      final Node next;
      if((next=this.next)!=null)
      {
         uncheckedForEachRemaining(next,action::accept);
      }
    }
  }
  public static class Stack extends CharSnglLnkSeq implements OmniStack.OfChar
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
    public void push(char val)
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
    public OmniIterator.OfChar iterator()
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
    private void uncheckedRemoveIfHelper(Node prev,Node curr,CharPredicate filter)
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
    boolean uncheckedRemoveIf(Node curr,CharPredicate filter)
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
      private void uncheckedRemoveIfHelper(int modCount,int numLeft,Node prev,Node curr,CharPredicate filter)
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
      boolean uncheckedRemoveIf(Node curr,CharPredicate filter)
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
      public void push(char val)
      {
        ++this.modCount;
        super.push(val);
      }
      @Override
      public OmniIterator.OfChar iterator()
      {
        return new Itr(this);
      }
      @Override
      public char popChar()
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
      public char pollChar()
      {
        final Node head;
        if((head=this.head)!=null)
        {
          ++this.modCount;
          --this.size;
          super.uncheckedchophead(head);
          return (char)(head.val);
        }
        return Character.MIN_VALUE;
      }
      @Override
      public Character poll()
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
          return (double)(head.val);
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
          return (float)(head.val);
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
          return (long)(head.val);
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
          return (int)(head.val);
        }
        return Integer.MIN_VALUE;
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
      public void forEach(CharConsumer action)
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
      public void forEach(Consumer<? super Character> action)
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
      boolean uncheckedremoveVal(Node head,int val)
      {
        if(val!=(head.val))
        {
          Node prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(val!=(head.val));
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
  public static class Queue extends CharSnglLnkSeq implements OmniQueue.OfChar
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
    public OmniIterator.OfChar iterator()
    {
      return new Itr(this);
    }
    @Override
    public char charElement()
    {
      return head.val;
    }
    @Override
    void push(char val)
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
    public Character element()
    {
      return (Character)charElement();
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
    private void uncheckedRemoveIfHelper(Node prev,Node curr,CharPredicate filter)
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
    private int uncheckedRemoveIfHelper(Node curr,CharPredicate filter)
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
    boolean uncheckedRemoveIf(Node curr,CharPredicate filter)
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
    boolean uncheckedremoveVal(Node head,int val)
    {
      if(val!=(head.val))
      {
        Node prev;
        do
        {
          if((head=(prev=head).next)==null)
          {
            return false;
          }
        }
        while(val!=(head.val));
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
      private int uncheckedRemoveIfHelper(int modCount,int numLeft,Node curr,CharPredicate filter)
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
            if(numLeft!=0 && (numLeft=(curr=curr.next).markSurvivors(numLeft,survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0)
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
      private void uncheckedRemoveIfHelper(int modCount,int numLeft,Node prev,Node curr,CharPredicate filter)
      {
        int numSurvivors;
        long[] survivorSet;
        if(numLeft!=0 && (numSurvivors=(curr=curr.next).markSurvivors(numLeft,survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0)
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
      boolean uncheckedRemoveIf(Node curr,CharPredicate filter)
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
      public char popChar()
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
      void push(char val)
      {
        ++this.modCount;
        super.push(val);
      }
      @Override
      public char charElement()
      {
        Node head;
        if((head=this.head)!=null)
        {
          return head.val;
        }
        throw new NoSuchElementException();
      }
      @Override
      public OmniIterator.OfChar iterator()
      {
        return new Itr(this);
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
          return (char)(head.val);
        }
        return Character.MIN_VALUE;
      }
      @Override
      public Character poll()
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
          return (double)(head.val);
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
          return (float)(head.val);
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
          return (long)(head.val);
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
          return (int)(head.val);
        }
        return Integer.MIN_VALUE;
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
      public void forEach(CharConsumer action)
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
      public void forEach(Consumer<? super Character> action)
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
      boolean uncheckedremoveVal(Node head,int val)
      {
        if(val!=(head.val))
        {
          Node prev;
          do
          {
            if((head=(prev=head).next)==null)
            {
              return false;
            }
          }
          while(val!=(head.val));
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
    private int countNumRemainingNodes(CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft,CharPredicate filter)
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
          if(numLeft!=0 && (numLeft=(before=before.next).markSurvivors(numLeft,survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0)
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
    private int countNumRemovedNodes(CheckedCollection.AbstractModCountChecker modCountChecker,int numLeft,CharPredicate filter)
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
          if(numLeft!=0 && (numSurvivors=(before=before.next).markSurvivors(numLeft,survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0)
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
    private int markSurvivors(int numLeft,long[] survivorSet,CharPredicate filter)
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
    private int countNumRemovedNodes(CharPredicate filter)
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
    private int countNumRemainingNodes(CharPredicate filter)
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
    transient char val;
    transient Node next;
    Node(char val)
    {
      this.val=val;
    }
    Node(char val,Node next)
    {
      this.val=val;
      this.next=next;
    }
    private void uncheckedForEachForward(CharConsumer action)
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
      int hash=31+(this.val);
      for(var curr=next;curr!=null;hash=hash*31+(curr.val),curr=curr.next)
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
    private boolean uncheckedcontains(int val)
    {
      for(var curr=this;val!=(curr.val);)
      {
        if((curr=curr.next)==null)
        {
          return false;
        }
      }
      return true;
    }
    private int uncheckedsearch(int val)
    {
      int index=1;
      for(var curr=this;val!=(curr.val);++index)
      {
        if((curr=curr.next)==null)
        {
          return -1;
        }
      }
      return index;
    }
    private void uncheckedCopyForward(char[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=(char)(src.val);
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
    private void uncheckedCopyForward(Character[] dst,int dstOffset)
    {
      for(var src=this;;++dstOffset)
      {
        dst[dstOffset]=(Character)(src.val);
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
        dst[dstOffset]=(double)(src.val);
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
        dst[dstOffset]=(float)(src.val);
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
        dst[dstOffset]=(long)(src.val);
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
        dst[dstOffset]=(int)(src.val);
        if((src=src.next)==null)
        {
          return;
        }
      }
    }
  }
}
