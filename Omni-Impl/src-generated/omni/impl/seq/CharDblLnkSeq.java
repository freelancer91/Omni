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
import omni.util.TypeUtil;
import omni.impl.AbstractCharItr;
import omni.function.CharComparator;
import omni.function.CharPredicate;
import omni.function.CharConsumer;
import omni.function.CharUnaryOperator;
import omni.util.CharSortUtil;
public abstract class CharDblLnkSeq extends AbstractCharList implements OmniList.OfChar
{
  private static  boolean uncheckedcontains(Node begin,Node end,int val)
  {
    while(val!=(begin.val))
    {
      if(begin==end)
      {
        return false;
      }
      begin=begin.next;
    }
    return true;
  }
  private static  int uncheckedindexOf(Node begin,Node end,int val)
  {
    int index=0;
    while(val!=(begin.val))
    {
      if((begin=begin.next)==null)
      {
        return -1;
      }
      ++index;
    }
    return index;
  }
  private static  int uncheckedlastIndexOf(Node end,int size,int val)
  {
    while(val!=(end.val) && --size!=0)
    {
      end=end.prev;
    }
    return size-1;
  }
  private static  int uncheckedsearch(Node begin,int val)
  {
    int index=1;
    while(val!=(begin.val))
    {
      if((begin=begin.next)==null)
      {
        return -1;
      }
      ++index;
    }
    return index;
  }
  transient Node head;
  transient Node tail;
  private CharDblLnkSeq()
  {
    super();
  }
  private CharDblLnkSeq(Node onlyNode)
  {
    super(1);
    privateInit(onlyNode);
  }
  private CharDblLnkSeq(Node head,int size,Node tail)
  {
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override
  public final boolean add(final char val)
  {
    addLast((char)val);
    return true;
  }
  public final void addFirst(final char val)
  {
    push((char)val);
  }
  public final boolean offer(final char val)
  {
    addLast((char)val);
    return true;
  }
  public final boolean offerLast(final char val)
  {
    addLast((char)val);
    return true;
  }
  public final boolean offerFirst(final char val)
  {
    push((char)val);
    return true;
  }
  public final void addFirst(final Character val)
  {
    push((char)val);
  }
  public final void addLast(final Character val)
  {
    addLast((char)val);
  }
  public final boolean offer(final Character val)
  {
    addLast((char)val);
    return true;
  }
  public final boolean offerLast(final Character val)
  {
    addLast((char)val);
    return true;
  }
  public final boolean offerFirst(final Character val)
  {
    push((char)val);
    return true;
  }
  public final void push(final Character val)
  {
    push((char)val);
  }
  abstract void pushtailHelper(Node oldtail,char val);
  public void addLast(final char val)
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      pushtailHelper(tail,val);
    }
    else
    {
      initHelper(val);
    }
  }
  abstract void pushheadHelper(Node oldhead,char val);
  public void push(final char val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      pushheadHelper(head,val);
    }
    else
    {
      initHelper(val);
    }
  }
  public final Character getFirst()
  {
    return charElement();
  }
  public final Character remove()
  {
    return popChar();
  }
  public final Character removeFirst()
  {
    return popChar();
  }
  public final Character peekFirst()
  {
    return peek();
  }
  public final Character pollFirst()
  {
    return poll();
  }
  public final Character pop()
  {
    return popChar();
  }
  public final Character removeLast()
  {
    return removeLastChar();
  }
  public final Character get()
  {
    return charElement();
  }
  public final Character element()
  {
    return charElement();
  }
  public final Character getLast()
  {
    return getLastChar();
  }
  public char peekChar()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return (char)(head.val);
    }
    return Character.MIN_VALUE;
  }
  public Character peek()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return (head.val);
    }
    return null;
  }
  public double peekDouble()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return (double)(head.val);
    }
    return Double.NaN;
  }
  public float peekFloat()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return (float)(head.val);
    }
    return Float.NaN;
  }
  public long peekLong()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return (long)(head.val);
    }
    return Long.MIN_VALUE;
  }
  public int peekInt()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return (int)(head.val);
    }
    return Integer.MIN_VALUE;
  }
  public char peekLastChar()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      return (char)(tail.val);
    }
    return Character.MIN_VALUE;
  }
  public Character peekLast()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      return (tail.val);
    }
    return null;
  }
  public double peekLastDouble()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      return (double)(tail.val);
    }
    return Double.NaN;
  }
  public float peekLastFloat()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      return (float)(tail.val);
    }
    return Float.NaN;
  }
  public long peekLastLong()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      return (long)(tail.val);
    }
    return Long.MIN_VALUE;
  }
  public int peekLastInt()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      return (int)(tail.val);
    }
    return Integer.MIN_VALUE;
  }
  public char pollChar()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      --this.size;
      privatechophead(head);
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
      privatechophead(head);
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
      privatechophead(head);
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
      privatechophead(head);
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
      privatechophead(head);
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
      privatechophead(head);
      return (int)(head.val);
    }
    return Integer.MIN_VALUE;
  }
  public char pollLastChar()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      --this.size;
      privatechoptail(tail);
      return (char)(tail.val);
    }
    return Character.MIN_VALUE;
  }
  public Character pollLast()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      --this.size;
      privatechoptail(tail);
      return (tail.val);
    }
    return null;
  }
  public double pollLastDouble()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      --this.size;
      privatechoptail(tail);
      return (double)(tail.val);
    }
    return Double.NaN;
  }
  public float pollLastFloat()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      --this.size;
      privatechoptail(tail);
      return (float)(tail.val);
    }
    return Float.NaN;
  }
  public long pollLastLong()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      --this.size;
      privatechoptail(tail);
      return (long)(tail.val);
    }
    return Long.MIN_VALUE;
  }
  public int pollLastInt()
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      --this.size;
      privatechoptail(tail);
      return (int)(tail.val);
    }
    return Integer.MIN_VALUE;
  }
  public char charElement()
  {
    return head.val;
  }
  public char getLastChar()
  {
    return tail.val;
  }
  public char popChar()
  {
    --this.size;
    Node head;
    privatechophead(head=this.head);
    return head.val;
  }
  public char removeLastChar()
  {
    --this.size;
    Node tail;
    privatechoptail(tail=this.tail);
    return tail.val;
  }
  public void clear()
  {
    this.size=0;
    ((CharDblLnkSeq)this).privateInit(null);
  }
  @Override
  public boolean contains(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return CharDblLnkSeq.uncheckedcontains(head,tail,TypeUtil.castToChar(val));
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
        return CharDblLnkSeq.uncheckedcontains(head,tail,(val));
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
        return CharDblLnkSeq.uncheckedcontains(head,tail,v);
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
        return CharDblLnkSeq.uncheckedcontains(head,tail,v);
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
        return CharDblLnkSeq.uncheckedcontains(head,tail,v);
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
        return CharDblLnkSeq.uncheckedcontains(head,tail,(val));
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
        return CharDblLnkSeq.uncheckedcontains(head,tail,(val));
      }
    }
    return false;
  }
  @Override
  public int indexOf(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return CharDblLnkSeq.uncheckedindexOf(head,tail,TypeUtil.castToChar(val));
    }
    return -1;
  }
  @Override
  public int indexOf(final int val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val==(char)val)
      {
        return CharDblLnkSeq.uncheckedindexOf(head,tail,(val));
      }
    }
    return -1;
  }
  @Override
  public int indexOf(final long val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return CharDblLnkSeq.uncheckedindexOf(head,tail,v);
      }
    }
    return -1;
  }
  @Override
  public int indexOf(final float val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return CharDblLnkSeq.uncheckedindexOf(head,tail,v);
      }
    }
    return -1;
  }
  @Override
  public int indexOf(final double val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return CharDblLnkSeq.uncheckedindexOf(head,tail,v);
      }
    }
    return -1;
  }
  @Override
  public int indexOf(final char val)
  {
    {
      final Node head;
      if((head=this.head)!=null)
      {
        return CharDblLnkSeq.uncheckedindexOf(head,tail,(val));
      }
    }
    return -1;
  }
  @Override
  public int indexOf(final short val)
  {
    if(val>=0)
    {
      final Node head;
      if((head=this.head)!=null)
      {
        return CharDblLnkSeq.uncheckedindexOf(head,tail,(val));
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final boolean val)
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      return CharDblLnkSeq.uncheckedlastIndexOf(tail,size,TypeUtil.castToChar(val));
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final int val)
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      if(val==(char)val)
      {
        return CharDblLnkSeq.uncheckedlastIndexOf(tail,size,(val));
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final long val)
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return CharDblLnkSeq.uncheckedlastIndexOf(tail,size,v);
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final float val)
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return CharDblLnkSeq.uncheckedlastIndexOf(tail,size,v);
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final double val)
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return CharDblLnkSeq.uncheckedlastIndexOf(tail,size,v);
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final char val)
  {
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        return CharDblLnkSeq.uncheckedlastIndexOf(tail,size,(val));
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final short val)
  {
    if(val>=0)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        return CharDblLnkSeq.uncheckedlastIndexOf(tail,size,(val));
      }
    }
    return -1;
  }
  public int search(final boolean val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return CharDblLnkSeq.uncheckedsearch(head,TypeUtil.castToChar(val));
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
        return CharDblLnkSeq.uncheckedsearch(head,(val));
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
        return CharDblLnkSeq.uncheckedsearch(head,v);
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
        return CharDblLnkSeq.uncheckedsearch(head,v);
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
        return CharDblLnkSeq.uncheckedsearch(head,v);
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
        return CharDblLnkSeq.uncheckedsearch(head,(val));
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
        return CharDblLnkSeq.uncheckedsearch(head,(val));
      }
    }
    return -1;
  }
  @Override
  public boolean contains(final Object val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val instanceof Character)
      {
        return CharDblLnkSeq.uncheckedcontains(head,tail,(char)(val));
      }
    }
    return false;
  }
  @Override
  public int indexOf(final Object val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val instanceof Character)
      {
        return CharDblLnkSeq.uncheckedindexOf(head,tail,(char)(val));
      }
    }
    return -1;
  }
  @Override
  public int lastIndexOf(final Object val)
  {
    final Node tail;
    if((tail=this.tail)!=null)
    {
      if(val instanceof Character)
      {
        return CharDblLnkSeq.uncheckedlastIndexOf(tail,size,(char)(val));
      }
    }
    return -1;
  }
  public int search(final Object val)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      if(val instanceof Character)
      {
        return CharDblLnkSeq.uncheckedsearch(head,(char)(val));
      }
    }
    return -1;
  }
  @Override
  public void forEach(final CharConsumer action)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      head.uncheckedForEachForward(this.tail,action);
    }
  }
  @Override
  public void forEach(final Consumer<? super Character> action)
  {
    final Node head;
    if((head=this.head)!=null)
    {
      head.uncheckedForEachForward(this.tail,(CharConsumer)action::accept);
    }
  }
  @Override
  public char getChar(int index)
  {
    return privateGetNode(index,size-index).val;
  }
  abstract void initHelper(char val);
  @Override
  public int hashCode()
  {
    final Node head;
    if((head=this.head)!=null)
    {
      return head.uncheckedForwardHashCode(this.tail);
    }
    return 1;
  }
  @Override
  public void put(int index,char val)
  {
    privateGetNode(index,size-index).val=val;
  }
  public final boolean removeFirstOccurrence(final Object val)
  {
    return remove(val);
  }
  @Override
  public void replaceAll(CharUnaryOperator operator)
  {
    Node head;
    if((head=this.head)!=null)
    {
      head.uncheckedReplaceAll(this.tail,operator);
    }
  }
  @Override
  public void replaceAll(UnaryOperator<Character> operator)
  {
    Node head;
    if((head=this.head)!=null)
    {
      head.uncheckedReplaceAll(this.tail,operator::apply);
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
  public void sort(CharComparator sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      head.uncheckedcomparatorSort(size,tail,sorter);
    }
  }
  @Override
  public void sort(Comparator<? super Character> sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      head.uncheckedcomparatorSort(size,tail,sorter::compare);
    }
  }
  @Override
  public char set(final int index,final char val)
  {
    final Node node;
    final var oldVal=(node=privateGetNode(index,size-index)).val;
    node.val=val;
    return oldVal;
  }
    @Override
    public char[] toCharArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char[] dst;
        head.uncheckedCopyForward(dst=new char[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override
    public Character[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Character[] dst;
        head.uncheckedCopyForward(dst=new Character[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_BOXED_ARR;
    }
    @Override
    public double[] toDoubleArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] dst;
        head.uncheckedCopyForward(dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public float[] toFloatArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final float[] dst;
        head.uncheckedCopyForward(dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public long[] toLongArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final long[] dst;
        head.uncheckedCopyForward(dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] dst;
        head.uncheckedCopyForward(dst=new int[size],0,size);
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
    final Node head;
    if((head=this.head)!=null)
    {
      final StringBuilder builder;
      head.uncheckedForwardToString(tail,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  private void privateInit(Node onlyNode)
  {
    this.head=onlyNode;
    this.tail=onlyNode;
  }
  private void uncheckedAddHelper(int headDist,int newSize,char val)
  {
    this.size=newSize;
    if(headDist==0)
    {
      this.head=new Node(val,this.head);
    }
    else
    {
      if((newSize-=headDist)==0)
      {
        this.tail=new Node(this.tail,val);
      }
      else
      {
        Node before,after;
        if(headDist<=newSize)
        {
          after=(before=this.head.iterateForward(headDist-1)).next;
        }
        else
        {
          before=(after=this.tail.iterateReverse(newSize-1)).prev;
        }
        new Node(before,val,after);
      }
    }
  }
  private void privatesettail(Node newTail)
  {
    this.tail=newTail;
    newTail.next=null;
  }
  private void privatesethead(Node newHead)
  {
    this.head=newHead;
    newHead.prev=null;
  }
  private void privatechoptail(Node oldTail)
  {
    this.tail=oldTail=oldTail.prev;
    oldTail.next=null;
  }
  private void privatechophead(Node oldHead)
  {
    this.head=oldHead=oldHead.next;
    oldHead.prev=null;
  }
  private Node removeAtIndexHelper(int headDist,int newSize)
  {
    Node node;
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
          Node before;
          (before=this.head.iterateForward(headDist-1)).joinnext((node=before.next).next);
        }
        else
        {
          Node after;
          (node=(after=this.tail.iterateReverse(newSize-1)).prev).prev.joinnext(after);
        }
      }
    }
    return node;
  }
  private void subSeqInsertHelper(Node newNode,int index)
  {
    final int tailDist;
    final Node before,after;
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
  private void collapsehead(Node head,Node tail,CharPredicate filter)
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
    ((CharDblLnkSeq)this).privatesethead(head);
    this.size-=numRemoved;
  }
   void collapsetail(Node head,Node tail,CharPredicate filter)
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
    ((CharDblLnkSeq)this).privatesettail(tail);
    this.size-=numRemoved;
  }
  boolean collapseBody(Node head,Node tail,CharPredicate filter)
  {
    Node prev;
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
  void findNewHead(Node head,CharPredicate filter)
  {
    final Node tail;
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
      ((CharDblLnkSeq)this).privateInit(null);
      this.size=0;
    }
  }
  private void collapseHeadAndTail(Node head,Node tail,CharPredicate filter)
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
        ((CharDblLnkSeq)this).privatesethead(head);
        ((CharDblLnkSeq)this).privatesettail(tail);
        size-=numRemoved;
        return;
      }
    }
    ((CharDblLnkSeq)this).privateInit(null);
    this.size=0;
  }
  private boolean uncheckedRemoveIf(Node head,CharPredicate filter)
  {
    if(filter.test(head.val))
    {
      findNewHead(head,filter);
    }
    else
    {
      final Node tail;
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
  private Node privateGetNode(int headDist,int tailDist)
  {
    if(headDist<=tailDist)
    {
      return head.iterateForward(headDist);
    }
    return tail.iterateReverse(tailDist-1);
  }
  private Node getItrNode(int headDist,int tailDist)
  {
    if(tailDist==0)
    {
      return null;
    }
    return privateGetNode(headDist,tailDist);
  }
  public static class Checked extends CharDblLnkSeq implements OmniDeque.OfChar
  {
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
    public Object clone()
    {
      Node newHead,newTail;
      final int size;
      if((size=this.size)!=0)
      {
        Node oldHead,oldTail;
        for(newHead=new Node((oldHead=this.head).val),newTail=newHead,oldTail=this.tail;oldHead!=oldTail;new Node(newTail,(oldHead=oldHead.next).val))
        {
        }
      }
      else
      {
        newHead=null;
        newTail=null;
      }
      return new Checked(newHead,size,newTail);
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
    private OmniList.OfChar getDefaultSubList(int headDist,int subListSize,int tailDist)
    {
      final Node subListHead=head;
      Node subListTail=tail;
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList(this,null,subListHead,subListSize,subListTail);
        }
        return new SubList.SuffixList(this,null,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail);
      }
      subListTail=tailDist<=subListSize?subListTail.uncheckedIterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
      if(headDist==0)
      {
        return new SubList.PrefixList(this,null,subListHead,subListSize,subListTail);
      }
      return new SubList.BodyList(this,null,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail,headDist);
    }
    private OmniList.OfChar getEmptySubList(int headDist,int tailDist)
    {
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList(this,null);
        }
        return new SubList.SuffixList(this,null);
      }
      else if(headDist==0)
      {
        return new SubList.PrefixList(this,null);
      }
      return new SubList.BodyList(this,null,headDist);
    }
    private OmniList.OfChar getSubList1(int headDist,int tailDist)
    {
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList(this,null,head);
        }
        return new SubList.SuffixList(this,null,tail);
      }
      else if(headDist==0)
      {
        return new SubList.PrefixList(this,null,head);
      }
      return new SubList.BodyList(this,null,((CharDblLnkSeq)this).privateGetNode(headDist,tailDist),headDist);
    }
    @Override
    void initHelper(char val)
    {
      ++this.modCount;
      this.size=1;
      super.privateInit(new Node(val));
    }
    @Override
    void pushheadHelper(Node oldhead,char val)
    {
      ++this.modCount;
      ++this.size;
      this.head=new Node(val,this.head);
    }
    @Override
    void pushtailHelper(Node oldtail,char val)
    {
      ++this.modCount;
      ++this.size;
      this.tail=new Node(this.tail,val);
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
    public char getLastChar()
    {
      Node tail;
      if((tail=this.tail)!=null)
      {
        return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override
    public char popChar()
    {
      final int size;
      if((size=this.size)!=0)
      {
        this.size=size-1;
        ++this.modCount;
        Node head;
        ((CharDblLnkSeq)this).privatechophead(head=this.head);
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override
    public char removeLastChar()
    {
      final int size;
      if((size=this.size)!=0)
      {
        this.size=size-1;
        ++this.modCount;
        Node tail;
        ((CharDblLnkSeq)this).privatechoptail(tail=this.tail);
        return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override
    public char getChar(int index)
    {
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return super.privateGetNode(index,size-index).val;
    }
    @Override
    public char set(final int index,final char val)
    {
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final Node node;
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
    public void forEach(final CharConsumer action)
    {
      final Node head;
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
    public void forEach(final Consumer<? super Character> action)
    {
      final Node head;
      if((head=this.head)!=null)
      {
        int modCount=this.modCount;
        try
        {
          head.uncheckedForEachForward(this.tail,(CharConsumer)action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    public char pollChar()
    {
      final Node head;
      if((head=this.head)!=null)
      {
        ++this.modCount;
        --this.size;
        ((CharDblLnkSeq)this).privatechophead(head);
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
        ((CharDblLnkSeq)this).privatechophead(head);
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
        ((CharDblLnkSeq)this).privatechophead(head);
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
        ((CharDblLnkSeq)this).privatechophead(head);
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
        ((CharDblLnkSeq)this).privatechophead(head);
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
        ((CharDblLnkSeq)this).privatechophead(head);
        return (int)(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public char pollLastChar()
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        ++this.modCount;
        --this.size;
        ((CharDblLnkSeq)this).privatechoptail(tail);
        return (char)(tail.val);
      }
      return Character.MIN_VALUE;
    }
    @Override
    public Character pollLast()
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        ++this.modCount;
        --this.size;
        ((CharDblLnkSeq)this).privatechoptail(tail);
        return (tail.val);
      }
      return null;
    }
    @Override
    public double pollLastDouble()
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        ++this.modCount;
        --this.size;
        ((CharDblLnkSeq)this).privatechoptail(tail);
        return (double)(tail.val);
      }
      return Double.NaN;
    }
    @Override
    public float pollLastFloat()
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        ++this.modCount;
        --this.size;
        ((CharDblLnkSeq)this).privatechoptail(tail);
        return (float)(tail.val);
      }
      return Float.NaN;
    }
    @Override
    public long pollLastLong()
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        ++this.modCount;
        --this.size;
        ((CharDblLnkSeq)this).privatechoptail(tail);
        return (long)(tail.val);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int pollLastInt()
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        ++this.modCount;
        --this.size;
        ((CharDblLnkSeq)this).privatechoptail(tail);
        return (int)(tail.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public void add(int index,char val)
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
        super.privateInit(new Node(val));
      }
    }
    @Override
    public char removeCharAt(int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++this.modCount;
      Node node;
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
    public OmniIterator.OfChar iterator()
    {
      return new AscendingItr(this);
    }
    @Override
    public OmniListIterator.OfChar listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfChar listIterator(int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,(size-=index)==0?null:super.privateGetNode(index,size),index);
    }
    @Override
    public OmniIterator.OfChar descendingIterator()
    {
      return new DescendingItr(this);
    }
    @Override
    public OmniList.OfChar subList(int fromIndex,int toIndex)
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
    public void put(int index,char val)
    {
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      super.privateGetNode(index,size-index).val=val;
    }
    @Override
    public void replaceAll(CharUnaryOperator operator)
    {
      Node head;
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
    public void replaceAll(UnaryOperator<Character> operator)
    {
      Node head;
      if((head=this.head)!=null)
      {
        int modCount=this.modCount;
        try
        {
          head.uncheckedReplaceAll(this.tail,operator::apply);
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
        head.uncheckedreverseSort(size,tail);
        ++this.modCount;
      }
    }
    @Override
    public void sort()
    {
      final int size;
      if((size=this.size)>1)
      {
        head.uncheckedsort(size,tail);
        ++this.modCount;
      }
    }
    @Override
    public void sort(CharComparator sorter)
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
    public void sort(Comparator<? super Character> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        try
        {
          head.uncheckedcomparatorSort(size,tail,sorter::compare);
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
    private boolean uncheckedremoveVal(Node head,int val)
    {
      if(val!=(head.val))
      {
        Node lastVisited;
        do
        {
          if((head=(lastVisited=head).next)==null)
          {
            return false;
          }
        }
        while(val!=(head.val));
        --this.size;
        if(head==tail)
        {
          ((CharDblLnkSeq)this).privatesettail(lastVisited);
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
          ((CharDblLnkSeq)this).privateInit(null);
        }
        else
        {
          ((CharDblLnkSeq)this).privatechophead(head);
        }
      }
      ++this.modCount;
      return true;
    }
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
    public boolean removeLastOccurrence(Object val)
    {
      Node tail;
      if((tail=this.tail)!=null)
      {
        if(val instanceof Character)
        {
          return uncheckedremoveLastOccurrence(tail,(char)(val));
        }
      }
      return false;
    }
    private boolean uncheckedremoveLastOccurrence(Node tail,int val)
    {
      if(val!=(tail.val))
      {
        Node lastVisited;
        do
        {
          if((tail=(lastVisited=tail).prev)==null)
          {
            return false;
          }
        }
        while(val!=(tail.val));
        --this.size;
        if(tail==head)
        {
          ((CharDblLnkSeq)this).privatesethead(lastVisited);
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
          ((CharDblLnkSeq)this).privateInit(null);
        }
        else
        {
          ((CharDblLnkSeq)this).privatechoptail(tail);
        }
      }
      ++this.modCount;
      return true;
    }
    @Override
    public boolean removeLastOccurrence(final boolean val)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,TypeUtil.castToChar(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final int val)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        if(val==(char)val)
        {
          return this.uncheckedremoveLastOccurrence(tail,(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final long val)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return this.uncheckedremoveLastOccurrence(tail,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final float val)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return this.uncheckedremoveLastOccurrence(tail,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final double val)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return this.uncheckedremoveLastOccurrence(tail,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final char val)
    {
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          return this.uncheckedremoveLastOccurrence(tail,(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final short val)
    {
      if(val>=0)
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          return this.uncheckedremoveLastOccurrence(tail,(val));
        }
      }
      return false;
    }
    private void collapseHeadAndTail(int modCount,Node head,Node tail,CharPredicate filter)
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
          ((CharDblLnkSeq)this).privatesethead(head);
          ((CharDblLnkSeq)this).privatesettail(tail);
          return;
        }
      }
    }
    private boolean collapseBody(int modCount,Node prev,Node next,CharPredicate filter)
    {
      int numLeft=size;
      int numConsumed=2;
      for(Node before;numConsumed!=numLeft;prev=before)
      {
        ++numConsumed;
        if(filter.test((before=prev.next).val))
        {
          int newSize=numConsumed-1;
          for(Node after;;next=after,++newSize)
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
    private void collapsehead(int modCount,Node head,Node tail,CharPredicate filter)
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
        ((CharDblLnkSeq)this).privatesethead(head);
      }
    }
    private void collapsetail(int modCount,Node tail,Node head,CharPredicate filter)
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
        ((CharDblLnkSeq)this).privatesettail(tail);
      }
    }
    private boolean uncheckedRemoveIf(Node head,CharPredicate filter)
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
    public boolean removeIf(CharPredicate filter)
    {
      final Node head;
      return (head=this.head)!=null && this.uncheckedRemoveIf(head,filter);
    }
    @Override
    public boolean removeIf(Predicate<? super Character> filter)
    {
      final Node head;
      return (head=this.head)!=null && this.uncheckedRemoveIf(head,filter::test);
    }
    private static class SubList extends CharDblLnkSeq
    {
      private static class AscendingItr extends AbstractCharItr implements OmniIterator.OfChar
      {
        transient final SubList parent;
        transient Node cursor;
        transient Node lastRet;
        transient int modCount;
        AscendingItr(SubList parent)
        {
          this.parent=parent;
          this.cursor=parent.head;
          this.modCount=parent.modCount;
        }
        AscendingItr(SubList parent,Node cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
          this.modCount=parent.modCount;
        }
        @Override
        public void forEachRemaining(CharConsumer action)
        {
          Node cursor;
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
        public void forEachRemaining(Consumer<? super Character> action)
        {
          Node cursor;
          if((cursor=this.cursor)!=null)
          {
            final var parent=this.parent;
            int modCount=this.modCount;
            try
            {
              cursor.uncheckedForEachForward(cursor=parent.tail,(CharConsumer)action::accept);
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
        public char nextChar()
        {
          final SubList parent;
          CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
          Node cursor;
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
      private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfChar
      {
        private transient int nextIndex;
        private BidirectionalItr(SubList parent)
        {
          super(parent);
        }
        private BidirectionalItr(SubList parent,Node cursor,int nextIndex)
        {
          super(parent,cursor);
          this.nextIndex=nextIndex;
        }
        @Override
        public void add(char val)
        {
          int modCount;
          final Checked root;
          final SubList parent;
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
              Node cursor;
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
        public void forEachRemaining(CharConsumer action)
        {
          Node cursor;
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
        public void forEachRemaining(Consumer<? super Character> action)
        {
          Node cursor;
          if((cursor=this.cursor)!=null)
          {
            final var parent=this.parent;
            int modCount=this.modCount;
            try
            {
              cursor.uncheckedForEachForward(cursor=parent.tail,(CharConsumer)action::accept);
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
        public char nextChar()
        {
          final SubList parent;
          CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
          Node cursor;
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
        public char previousChar()
        {
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          int nextIndex;
          if((nextIndex=this.nextIndex)!=0)
          {
            this.nextIndex=nextIndex-1;
            Node cursor;
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
        public void set(char val)
        {
          Node lastRet;
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
      transient final Checked root;
      transient final SubList parent;
      SubList(Checked root,SubList parent)
      {
        super();
        this.root=root;
        this.parent=parent;
        this.modCount=root.modCount;
      }
      SubList(Checked root,SubList parent,Node onlyNode)
      {
        super(onlyNode);
        this.root=root;
        this.parent=parent;
        this.modCount=root.modCount;
      }
      SubList(Checked root,SubList parent,Node head,int size,Node tail)
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
        final Node head;
        if((head=this.head)!=null)
        {
          if(val instanceof Character)
          {
            return uncheckedremoveVal(head,(char)(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final boolean val)
      {
        final Node head;
        if((head=this.head)!=null)
        {
          return this.uncheckedremoveVal(head,TypeUtil.castToChar(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
            void findNewHead(int modCount,Node curr,CharPredicate filter)
            {
              final Node tail;
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
               final Checked root;
               CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
               root.modCount=modCount+1;
              ((CharDblLnkSeq)root).privateInit(null);
              root.size=0;
              bubbleUpclearRoot();
            }
      private void collapseHeadAndTail(int modCount,Node head,Node tail,CharPredicate filter)
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
            ((CharDblLnkSeq)root).privateInit(null);
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
            ((CharDblLnkSeq)root).privatesethead(head);
            ((CharDblLnkSeq)root).privatesettail(tail);
            break;
          }
        }
        root.modCount=modCount+1;
        root.size-=numConsumed;
      }
      private boolean collapseBody(int modCount,Node prev,Node next,CharPredicate filter)
      {
        final int oldSize=size;
        int numConsumed=2;
        final var root=this.root;
        for(Node before;numConsumed!=oldSize;prev=before)
        {
          ++numConsumed;
          if(filter.test((before=prev.next).val))
          {
            int numRemoved=1;
            for(Node after;;next=after)
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
      boolean removeIfHelper(int modCount,Node curr,CharPredicate filter)
      {
        final Node tail;
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
      private void prefixCollapsetail(int modCount,Node head,Node tail,CharPredicate filter)
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
      private void rootCollapsetail(int modCount,Node head,Node tail,CharPredicate filter)
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
        ((CharDblLnkSeq)root).privatesettail(tail);
        root.size-=numConsumed;
      }
      private void suffixCollapsehead(int modCount,Node head,Node tail,CharPredicate filter)
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
      private void rootCollapsehead(int modCount,Node head,Node tail,CharPredicate filter)
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
        ((CharDblLnkSeq)root).privatesethead(head);
        root.size-=numConsumed;
      }
      private void privateCollapseHeadAndTail(int size,Node head,Node tail)
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
      public void add(int index,char val)
      {
        final int modCount;
        final Checked root;
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
            Node newNode;
            root.head=newNode=new Node(val,this.head);
            ((SubList)this).bubbleUppushhead(newNode);
          }
          else
          {
            if((size-=index)==0)
            {
              Node newNode;
              root.tail=newNode=new Node(this.tail,val);
              ((SubList)this).bubbleUppushtail(newNode);
            }
            else
            {
              Node before,after;
              if(index<=size)
              {
                after=(before=this.head.iterateForward(index-1)).next;
              }
              else
              {
                before=(after=this.tail.iterateReverse(size-1)).prev;
              }
              new Node(before,val,after);
              ((SubList)this).bubbleUpincrementSize();
            }
          }
        }
        else
        {
          Node newNode;
          ((CharDblLnkSeq)root).privateInit(newNode=new Node(val));
          bubbleUpinit(newNode);
        }
      }
      @Override
      public char removeCharAt(int index)
      {
        final int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        root.modCount=modCount+1;
        --root.size;
        Node node;
        if(--size!=0)
        {
          if(index==0)
          {
            Node newHead;
            ((CharDblLnkSeq)root).privatesethead(newHead=(node=this.head).next);
            ((SubList)this).bubbleUprootchophead(newHead);
          }
          else
          {
            if((size-=index)==0)
            {
              Node newTail;
              ((CharDblLnkSeq)root).privatesettail(newTail=(node=this.tail).prev);
              ((SubList)this).bubbleUprootchoptail(newTail);
            }
            else
            {
              if(index<=size)
              {
                Node before;
                (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
              }
              else
              {
                Node after;
                (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
              }
              ((SubList)this).bubbleUpdecrementSize();
            }
          }
        }
        else
        {
          node=this.head;
          ((CharDblLnkSeq)root).privateInit(null);
          bubbleUpclearRoot();
        }
        return node.val;
      }
      @Override
      void initHelper(char val)
      {
        final Checked root;
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=modCount+1;
        root.size=1;
        Node newNode;
        ((CharDblLnkSeq)root).privateInit(newNode=new Node(val));
        bubbleUpinit(newNode);
      }
      @Override
      void pushheadHelper(Node oldhead,char val)
      {
        final Checked root;
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=modCount+1;
        ++root.size;
        Node newNode;
        root.head=newNode=new Node(val,oldhead);
        bubbleUppushhead(newNode);
      }
      private void bubbleUppushhead(Node newhead)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;++curr.size;curr.head=newhead;
        }
        while((curr=curr.parent)!=null);
      }
      @Override
      void pushtailHelper(Node oldtail,char val)
      {
        final Checked root;
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=modCount+1;
        ++root.size;
        Node newNode;
        root.tail=newNode=new Node(oldtail,val);
        bubbleUppushtail(newNode);
      }
      private void bubbleUppushtail(Node newtail)
      {
        SubList curr=this;
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
          ((SubList)this).clearRoot();
        }
      }
      @Override
      public char getChar(int index)
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.hashCode();
      }
      @Override
      public String toString()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return super.toString();
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
      public char set(int index,char val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final Node node;
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
      public void forEach(CharConsumer action)
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
      public void forEach(Consumer<? super Character> action)
      {
        int modCount=this.modCount;
        try
        {
          super.forEach((CharConsumer)action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void put(int index,char val)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        super.privateGetNode(index,size-index).val=val;
      }
      @Override
      public void replaceAll(CharUnaryOperator operator)
      {
        final Checked root=this.root;
        int modCount=this.modCount;
        try
        {
          Node head;if((head=this.head)==null){return;}head.uncheckedReplaceAll(tail,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        root.modCount=modCount+1;
        ((SubList)this).bubbleUpincrementModCount();
      }
      @Override
      public void replaceAll(UnaryOperator<Character> operator)
      {
        final Checked root=this.root;
        int modCount=this.modCount;
        try
        {
          Node head;if((head=this.head)==null){return;}head.uncheckedReplaceAll(tail,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        root.modCount=modCount+1;
        ((SubList)this).bubbleUpincrementModCount();
      }
      @Override
      public void sort()
      {
        int modCount;
        Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)<2)
        {
          return;
        }
        head.uncheckedsort(size,tail);
        root.modCount=modCount+1;
        this.bubbleUpincrementModCount();
      }
      @Override
      public void reverseSort()
      {
        int modCount;
        Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)<2)
        {
          return;
        }
        head.uncheckedreverseSort(size,tail);
        root.modCount=modCount+1;
        this.bubbleUpincrementModCount();
      }
      @Override
      public void sort(CharComparator sorter)
      {
        Checked root=this.root;
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
        ((SubList)this).bubbleUpincrementModCount();
      }
      @Override
      public void sort(Comparator<? super Character> sorter)
      {
        Checked root=this.root;
        int modCount=this.modCount;
        try
        {
          final int size;if((size=this.size)<2){return;}head.uncheckedcomparatorSort(size,tail,sorter::compare);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        root.modCount=modCount+1;
        ((SubList)this).bubbleUpincrementModCount();
      }
        @Override
        public char[] toCharArray()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            final char[] dst;
            head.uncheckedCopyForward(dst=new char[size],0,size);
            return dst;
          }
          return OmniArray.OfChar.DEFAULT_ARR;
        }
        @Override
        public Character[] toArray()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            final Character[] dst;
            head.uncheckedCopyForward(dst=new Character[size],0,size);
            return dst;
          }
          return OmniArray.OfChar.DEFAULT_BOXED_ARR;
        }
        @Override
        public double[] toDoubleArray()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            final double[] dst;
            head.uncheckedCopyForward(dst=new double[size],0,size);
            return dst;
          }
          return OmniArray.OfDouble.DEFAULT_ARR;
        }
        @Override
        public float[] toFloatArray()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            final float[] dst;
            head.uncheckedCopyForward(dst=new float[size],0,size);
            return dst;
          }
          return OmniArray.OfFloat.DEFAULT_ARR;
        }
        @Override
        public long[] toLongArray()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            final long[] dst;
            head.uncheckedCopyForward(dst=new long[size],0,size);
            return dst;
          }
          return OmniArray.OfLong.DEFAULT_ARR;
        }
        @Override
        public int[] toIntArray()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            final int[] dst;
            head.uncheckedCopyForward(dst=new int[size],0,size);
            return dst;
          }
          return OmniArray.OfInt.DEFAULT_ARR;
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
        Node newHead,newTail;
        final int size;
        if((size=this.size)!=0)
        {
          Node oldHead,oldTail;
          for(newHead=new Node((oldHead=this.head).val),newTail=newHead,oldTail=this.tail;oldHead!=oldTail;new Node(newTail,(oldHead=oldHead.next).val))
          {
          }
        }
        else
        {
          newHead=null;
          newTail=null;
        }
        return new Checked(newHead,size,newTail);
      }
      @Override
      public OmniList.OfChar subList(int fromIndex,int toIndex)
      {
        final Checked root;
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
      private OmniList.OfChar getDefaultSubList(Checked root,int headDist,int subListSize,int tailDist)
      {
        final Node subListHead=head;
        Node subListTail=tail;
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList(root,this,subListHead,subListSize,subListTail);
          }
          return new SubList.SuffixList(root,this,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail);
        }
        subListTail=tailDist<=subListSize?subListTail.uncheckedIterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
        if(headDist==0)
        {
          return new SubList.PrefixList(root,this,subListHead,subListSize,subListTail);
        }
        return new SubList.BodyList(root,this,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail,headDist);
      }
      private OmniList.OfChar getEmptySubList(Checked root,int headDist,int tailDist)
      {
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList(root,this);
          }
          return new SubList.SuffixList(root,this);
        }
        else if(headDist==0)
        {
          return new SubList.PrefixList(root,this);
        }
        return new SubList.BodyList(root,this,headDist);
      }
      private OmniList.OfChar getSubList1(Checked root,int headDist,int tailDist)
      {
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList(root,this,head);
          }
          return new SubList.SuffixList(root,this,tail);
        }
        else if(headDist==0)
        {
          return new SubList.PrefixList(root,this,head);
        }
        return new SubList.BodyList(root,this,((CharDblLnkSeq)this).privateGetNode(headDist,tailDist),headDist);
      }
      @Override
      public OmniIterator.OfChar iterator()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return new AscendingItr(this);
      }
      @Override
      public OmniListIterator.OfChar listIterator()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        return new BidirectionalItr(this);
      }
      @Override
      public OmniListIterator.OfChar listIterator(int index)
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        return new BidirectionalItr(this,super.getItrNode(index,size-index),index);
      }
      private void bubbleUpincrementModCount()
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpsuffixchophead(Node oldHead,Node newHead)
      {
        SubList curr=this;
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
      private void bubbleUpprefixchoptail(Node oldTail,Node newTail)
      {
        SubList curr=this;
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
        SubList curr=this;
        do
        {
          ++curr.modCount;curr.size=0;((CharDblLnkSeq)curr).privateInit(null);
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpincrementSize()
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;++curr.size;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpdecrementSize()
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;--curr.size;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpdecrementSize(int numRemoved)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;curr.size-=numRemoved;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchophead(Node newHead)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;--curr.size;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchoptail(Node newTail)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;--curr.size;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchophead(int numRemoved,Node newHead)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;curr.size-=numRemoved;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchoptail(int numRemoved,Node newTail)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;curr.size-=numRemoved;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpprefixPushtail(Node oldTail,Node newTail)
      {
        SubList curr=this;
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
      private void bubbleUpsuffixPushhead(Node oldHead,Node newHead)
      {
        SubList curr=this;
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
      private void bubbleUpclearPrefix(Node oldTail,Node newHead,int numRemoved)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;curr.size=0;((CharDblLnkSeq)curr).privateInit(null);
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.tail==oldTail);
        curr.bubbleUprootchophead(numRemoved,newHead);
      }
      private void bubbleUpclearSuffix(Node oldHead,Node newTail,int numRemoved)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;curr.size=0;((CharDblLnkSeq)curr).privateInit(null);
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.head==oldHead);
        curr.bubbleUprootchoptail(numRemoved,newTail);
      }
      private void bubbleUpclearBody(Node oldhead,Node oldtail,int numRemoved)
      {
        Node prev,next;
        (prev=oldhead.prev).joinnext(next=tail.next);
        var curr=this;
        for(;;)
        {
          ++curr.modCount;
          curr.size=0;
          ((CharDblLnkSeq)curr).privateInit(null);
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
      private void bubbleUpprefixCollapsetail(int numRemoved,Node oldTail,Node newTail)
      {
        SubList curr=this;
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
      private void bubbleUpsuffixCollapsehead(int numRemoved,Node oldHead,Node newHead)
      {
        SubList curr=this;
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
      private void bubbleUprootCollapsehead(int numRemoved,Node newHead)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;curr.size-=numRemoved;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootCollapsetail(int numRemoved,Node newTail)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;curr.size-=numRemoved;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpcollapseHeadAndTail(Node newHead,Node newTail,int newSize)
      {
        SubList curr=this;
        do
        {
          curr.privateCollapseHeadAndTail(newSize,newHead,newTail);
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpinit(Node newNode)
      {
        SubList curr=this;
        do
        {
          ++curr.modCount;curr.size=1;((CharDblLnkSeq)curr).privateInit(newNode);
        }
        while((curr=curr.parent)!=null);
      }
      private void clearRoot()
      {
        Checked root;
        ++(root=this.root).modCount;
        root.size=0;
        ((CharDblLnkSeq)root).privateInit(null);
        bubbleUpclearRoot();
      }
      private void clearPrefix(int numRemoved)
      {
        Node oldtail;
        Node newhead=(oldtail=this.tail).next;
        final Checked root;
        ++(root=this.root).modCount;
        root.size-=numRemoved;
        ((CharDblLnkSeq)root).privatesethead(newhead);
        bubbleUpclearPrefix(oldtail,newhead,numRemoved);
      }
      private void clearSuffix(int numRemoved)
      {
        Node oldhead;
        Node newtail=(oldhead=this.head).prev;
        final Checked root;
        ++(root=this.root).modCount;
        root.size-=numRemoved;
        ((CharDblLnkSeq)root).privatesettail(newtail);
        bubbleUpclearSuffix(oldhead,newtail,numRemoved);
      }
      private void clearBody(int numRemoved)
      {
        final Checked root;
        ++(root=this.root).modCount;
        root.size-=numRemoved;
        bubbleUpclearBody(this.head,this.tail,numRemoved);
      }
      @Override
      public boolean removeIf(CharPredicate filter)
      {
        final Node head;
        if((head=this.head)!=null)
        {
          return uncheckedRemoveIf(head,filter);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeIf(Predicate<? super Character> filter)
      {
        final Node head;
        if((head=this.head)!=null)
        {
          return uncheckedRemoveIf(head,filter::test);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      private boolean uncheckedRemoveIf(Node head,CharPredicate filter)
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
      private void uncheckedItrRemove(Node lastRet)
      {
        if(lastRet==tail)
        {
          if(lastRet==head)
          {
            ((SubList)this).bubbleUpclearRoot();
            ((CharDblLnkSeq)root).privateInit(null);
          }
          else
          {
            ((SubList)this).bubbleUprootchoptail(lastRet=lastRet.prev);
            ((CharDblLnkSeq)root).privatesettail(lastRet);
          }
        }
        else
        {
          if(lastRet==head)
          {
            ((SubList)this).bubbleUprootchophead(lastRet=lastRet.next);
            ((CharDblLnkSeq)root).privatesethead(lastRet);
          }
          else
          {
            ((SubList)this).bubbleUpdecrementSize();
            lastRet.prev.joinnext(lastRet.next);
          }
        }
      }
      void ascItrRemove(AscendingItr itr)
      {
        Node lastRet;
        if((lastRet=itr.lastRet)!=null)
        {
          int modCount;
          final Checked root;
          CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          uncheckedItrRemove(lastRet);
          itr.modCount=modCount;
          --root.size;
          return;
        }
        throw new IllegalStateException();
      }
      void bidirectItrRemove(BidirectionalItr itr)
      {
        Node lastRet;
        if((lastRet=itr.lastRet)!=null)
        {
          int modCount;
          final Checked root;
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
      void removeFirstHelper(int modCount,Node curr)
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=modCount+1;
        if(curr==tail)
        {
          bubbleUpclearRoot();
          ((CharDblLnkSeq)root).privateInit(null);
        }
        else
        {
          bubbleUprootchophead(curr=curr.next);
          ((CharDblLnkSeq)root).privatesethead(curr);
        }
        --root.size;
      }
      boolean uncheckedremoveValHelper(int modCount,Node curr,int val)
      {
        final var root=this.root;
        final var tail=this.tail;
        Node prev;
        do
        {
          if(curr==tail)
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
        }
        while(val!=((curr=(prev=curr).next).val));
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=modCount+1;
        --root.size;
        if(curr==tail)
        {
          bubbleUprootchoptail(prev);root.tail=prev;prev.next=null;
        }
        else
        {
          ((SubList)this).bubbleUpdecrementSize();
          prev.joinnext(curr.next);
        }
        return true;
      }
      void initHelper(Checked root,char val)
      {
        final var newNode=new Node(val);
        for(var curr=this;;curr.size=1)
        {
          ((CharDblLnkSeq)curr).privateInit(newNode);
          if((curr=curr.parent)==null)
          {
            ((CharDblLnkSeq)root).privateInit(newNode);
            return;
          }
        }
      }
      void prependHelper(char val)
      {
        ++this.modCount;
        Node newNode;
        this.head=newNode=new Node(val,this.head);
        SubList parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUppushhead(newNode);
        }
      }
      void appendHelper(char val)
      {
        ++this.modCount;
        Node newNode;
        this.tail=newNode=new Node(this.tail,val);
        SubList parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUppushtail(newNode);
        }
      }
      private void insertHelper(Node before,char val,Node after)
      {
        ++this.modCount;
        new Node(before,val,after);
        SubList parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUpincrementSize();
        }
      }
      private boolean uncheckedremoveVal(Node head,int val)
      {
        final int modCount=this.modCount;
        try
        {
          if(val!=(head.val))
          {
            return uncheckedremoveValHelper(modCount,head,val);
          }
          removeFirstHelper(modCount,head);
          return true;
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      private static class PrefixList extends SubList
      {
        PrefixList(Checked root,SubList parent)
        {
          super(root,parent);
        }
        PrefixList(Checked root,SubList parent,Node onlyNode)
        {
          super(root,parent,onlyNode);
        }
        PrefixList(Checked root,SubList parent,Node head,int size,Node tail)
        {
          super(root,parent,head,size,tail);
        }
        @Override
        boolean uncheckedremoveValHelper(int modCount,Node curr,int val)
        {
          final var root=this.root;
          final var tail=this.tail;
          Node prev;
          do
          {
            if(curr==tail)
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
              return false;
            }
          }
          while(val!=((curr=(prev=curr).next).val));
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=modCount+1;
          --root.size;
          if(curr==tail)
          {
            ((SubList)this).bubbleUpprefixchoptail(curr,prev);
          }
          else
          {
            ((SubList)this).bubbleUpdecrementSize();
            prev.joinnext(curr.next);
          }
          return true;
        }
        private void bubbleUpinit(Node newNode)
        {
          SubList curr=this;
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
        void initHelper(char val)
        {
          final Checked root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          Node newNode;
          root.head=newNode=new Node(val,root.head);
          bubbleUpinit(newNode);
        }
        private void bubbleUppushtail(Node oldtail,Node newNode)
        {
          SubList curr=this;
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
        void pushtailHelper(Node oldtail,char val)
        {
          final Checked root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          bubbleUppushtail(oldtail,new Node(oldtail=this.tail,val,oldtail.next));
        }
        @Override
        public void clear()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList)this).clearPrefix(size);
          }
        }
        @Override
        public void add(int index,char val)
        {
          final int modCount;
          final Checked root;
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
              Node newNode;
              root.head=newNode=new Node(val,this.head);
              ((SubList)this).bubbleUppushhead(newNode);
            }
            else
            {
              if((size-=index)==0)
              {
                Node oldtail;
                ((PrefixList)this).bubbleUppushtail(oldtail=this.tail,new Node(oldtail,val,oldtail.next));
              }
              else
              {
                Node before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node(before,val,after);
                ((SubList)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            Node newNode;
            root.head=newNode=new Node(val,root.head);
            bubbleUpinit(newNode);
          }
        }
        @Override
        public char removeCharAt(int index)
        {
          final int modCount;
          final Checked root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          CheckedCollection.checkLo(index);
          int size;
          CheckedCollection.checkReadHi(index,size=this.size);
          root.modCount=modCount+1;
          --root.size;
          Node node;
          if(--size!=0)
          {
            if(index==0)
            {
              Node newHead;
              ((CharDblLnkSeq)root).privatesethead(newHead=(node=this.head).next);
              ((SubList)this).bubbleUprootchophead(newHead);
            }
            else
            {
              if((size-=index)==0)
              {
                ((SubList)this).bubbleUpprefixchoptail(node=this.tail,node.prev);
              }
              else
              {
                if(index<=size)
                {
                  Node before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            node=this.head;
            ((CharDblLnkSeq)root).privateInit(null);
            ((SubList)this).bubbleUpclearPrefix(node=this.head,node.next,1);
          }
          return node.val;
        }
        @Override
        void appendHelper(char val)
        {
          ++this.modCount;
          Node newNode,oldtail;
          tail=newNode=new Node(oldtail=tail,val,oldtail.next);
          SubList parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpprefixPushtail(newNode,oldtail);
          }
        }   
        @Override
        void initHelper(Checked root,char val)
        {
          final Node after,newNode=new Node(val);
          for(SubList curr=this;;curr.size=1)
          {
            ((CharDblLnkSeq)curr).privateInit(newNode);
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
        private void bubbleUpcollapseHeadAndTail(Node oldtail,Node newhead,Node newtail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList curr=this;;)
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
        private void collapseHeadAndTail(int modCount,Node head,Node tail,CharPredicate filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
              ((SubList)this).bubbleUpclearPrefix(tail,head=tail.next,oldSize);
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
        void findNewHead(int modCount,Node curr,CharPredicate filter)
        {
          final Node tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList)this).rootCollapsehead(modCount,curr,tail,filter);
              return;
            }
            collapseHeadAndTail(modCount,curr,tail,filter);
            return;
          }
          final Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          --root.size;
          ((SubList)this).bubbleUpclearPrefix(curr,curr=tail.next,1);
          ((CharDblLnkSeq)root).privatesethead(curr);
        }
        @Override
        boolean removeIfHelper(int modCount,Node curr,CharPredicate filter)
        {
          final Node tail;
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
        void ascItrRemove(AscendingItr itr)
        {
          Node lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked root;
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
        void bidirectItrRemove(BidirectionalItr itr)
        {
          Node lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked root;
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
        private void uncheckedItrRemove(Node lastRet,Checked root)
        {
          if(lastRet==head)
          {
            if(lastRet==tail)
            {
              ((SubList)this).bubbleUpclearPrefix(lastRet,lastRet=lastRet.next,1);
            }
            else
            {
              ((SubList)this).bubbleUprootchophead(lastRet=lastRet.next);
            }
            ((CharDblLnkSeq)root).privatesethead(lastRet);
          }
          else
          {
            if(lastRet==tail)
            {
              ((SubList)this).bubbleUpprefixchoptail(lastRet,lastRet.prev);
            }
            else
            {
              ((SubList)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
      }
      private static class SuffixList extends SubList
      {
        SuffixList(Checked root,SubList parent)
        {
          super(root,parent);
        }
        SuffixList(Checked root,SubList parent,Node onlyNode)
        {
          super(root,parent,onlyNode);
        }
        SuffixList(Checked root,SubList parent,Node head,int size,Node tail)
        {
          super(root,parent,head,size,tail);
        }
        private void bubbleUpinit(Node newNode)
        {
          SubList curr=this;
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
        void initHelper(char val)
        {
          final Checked root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          Node newNode;
          root.tail=newNode=new Node(root.tail,val);
          bubbleUpinit(newNode);
        }
        private void bubbleUppushhead(Node oldhead,Node newNode)
        {
          SubList curr=this;
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
        void pushheadHelper(Node oldhead,char val)
        {
          final Checked root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          bubbleUppushhead(oldhead,new Node((oldhead=this.head).prev,val,oldhead));
        }
        @Override
        public void clear()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList)this).clearSuffix(size);
          }
        }
        @Override
        public void add(int index,char val)
        {
          final int modCount;
          final Checked root;
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
              Node oldhead;
              bubbleUppushhead(oldhead=this.head,new Node(oldhead.prev,val,oldhead));
            }
            else
            {
              if((size-=index)==0)
              {
                Node newNode;
                root.tail=newNode=new Node(this.tail,val);
                ((SubList)this).bubbleUppushtail(newNode);
              }
              else
              {
                Node before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node(before,val,after);
                ((SubList)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            Node newNode;
            root.tail=newNode=new Node(root.tail,val);
            bubbleUpinit(newNode);
          }
        }
        @Override
        public char removeCharAt(int index)
        {
          final int modCount;
          final Checked root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          CheckedCollection.checkLo(index);
          int size;
          CheckedCollection.checkReadHi(index,size=this.size);
          root.modCount=modCount+1;
          --root.size;
          Node node;
          if(--size!=0)
          {
            if(index==0)
            {
              ((SubList)this).bubbleUpsuffixchophead(node=this.head,node.next);
            }
            else
            {
              if((size-=index)==0)
              {
                Node newTail;
                ((CharDblLnkSeq)root).privatesettail(newTail=(node=this.tail).prev);
                ((SubList)this).bubbleUprootchoptail(newTail);
              }
              else
              {
                if(index<=size)
                {
                  Node before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            ((CharDblLnkSeq)root).privateInit(null);
            ((SubList)this).bubbleUpclearSuffix(node=this.head,node.prev,1);
          }
          return node.val;
        }
        @Override
        void findNewHead(int modCount,Node curr,CharPredicate filter)
        {
          final Node tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList)this).suffixCollapsehead(modCount,curr,tail,filter);
              return;
            }
            collapseHeadAndTail(modCount,curr,tail,filter);
            return;
          }
          final Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          --root.size;
          ((SubList)this).bubbleUpclearSuffix(curr,curr=curr.prev,1);
          ((CharDblLnkSeq)root).privatesettail(curr);
        }
        private void bubbleUpcollapseHeadAndTail(Node oldhead,Node newhead,Node newtail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList curr=this;;)
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
        private void collapseHeadAndTail(int modCount,Node head,Node tail,CharPredicate filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
              ((SubList)this).bubbleUpclearSuffix(head,tail=head.next,oldSize);
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
        void removeFirstHelper(int modCount,Node curr)
        {
          final Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          if(curr==tail)
          {
            ((SubList)this).bubbleUpclearSuffix(curr,curr=curr.prev,1);
            ((CharDblLnkSeq)root).privatesettail(curr);
          }
          else
          {
            ((SubList)this).bubbleUpsuffixchophead(curr,curr.next);
          }
          --root.size;
        }
        @Override
        void ascItrRemove(AscendingItr itr)
        {
          Node lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked root;
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
        void bidirectItrRemove(BidirectionalItr itr)
        {
          Node lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked root;
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
        private void uncheckedItrRemove(Node lastRet,Checked root)
        {
          if(lastRet==tail)
          {
            if(lastRet==head)
            {
              ((SubList)this).bubbleUpclearSuffix(lastRet,lastRet=lastRet.prev,1);
            }
            else
            {
              ((SubList)this).bubbleUprootchoptail(lastRet=lastRet.prev);
            }
            ((CharDblLnkSeq)root).privatesettail(lastRet);
          }
          else
          {
            if(lastRet==head)
            {
              ((SubList)this).bubbleUpsuffixchophead(lastRet,lastRet.next);
            }
            else
            {
              ((SubList)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
        @Override
        void prependHelper(char val)
        {
          ++this.modCount;
          Node newNode,oldhead;
          head=newNode=new Node((oldhead=head).prev,val,oldhead);
          SubList parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpsuffixPushhead(newNode,oldhead);
          }
        }
        @Override
        void initHelper(Checked root,char val)
        {
          final Node before,newNode=new Node(val);
          for(SubList curr=this;;curr.size=1)
          {
            ((CharDblLnkSeq)curr).privateInit(newNode);
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
      private static class BodyList extends PrefixList
      {
        private transient final int parentOffset;
        BodyList(Checked root,SubList parent,int parentOffset)
        {
          super(root,parent);
          this.parentOffset=parentOffset;
        }
        BodyList(Checked root,SubList parent,Node onlyNode,int parentOffset)
        {
          super(root,parent,onlyNode);
          this.parentOffset=parentOffset;
        }
        BodyList(Checked root,SubList parent,Node head,int size,Node tail,int parentOffset)
        {
          super(root,parent,head,size,tail);
          this.parentOffset=parentOffset;
        }
        @Override
        void removeFirstHelper(int modCount,Node curr)
        {
          final Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          if(curr==tail)
          {
            Node node;
            ((SubList)this).bubbleUpclearBody(node=this.head,node,1);
          }
          else
          {
            ((SubList)this).bubbleUpsuffixchophead(curr,curr.next);
          }
          --root.size;
        }
        @Override
        int getParentOffset()
        {
          return this.parentOffset;
        }
        @Override
        public void add(int index,char val)
        {
          final int modCount;
          final Checked root;
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
              Node oldhead;
              bubbleUppushhead(oldhead=this.head,new Node(oldhead.prev,val,oldhead));
            }
            else
            {
              if((size-=index)==0)
              {
                Node oldtail;
                ((PrefixList)this).bubbleUppushtail(oldtail=this.tail,new Node(oldtail,val,oldtail.next));
              }
              else
              {
                Node before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node(before,val,after);
                ((SubList)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            bubbleUpinit(new Node(val));
          }
        }
        @Override
        public char removeCharAt(int index)
        {
          final Checked root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          CheckedCollection.checkLo(index);
          int size;
          CheckedCollection.checkReadHi(index,size=this.size);
          root.modCount=modCount+1;
          --root.size;
          Node node;
          if(--size!=0)
          {
            if(index==0)
            {
              ((SubList)this).bubbleUpsuffixchophead(node=this.head,node.next);
            }
            else
            {
              if((size-=index)==0)
              {
                ((SubList)this).bubbleUpprefixchoptail(node=this.tail,node.prev);
              }
              else
              {
                if(index<=size)
                {
                  Node before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            ((SubList)this).bubbleUpclearBody(node=this.head,node,1);
          }
          return node.val;
        }
        private void bubbleUpinit(Node newNode)
        {
          SubList curr=this;
          Node before,after;
          for(;;)
          {
            ++curr.modCount;
            curr.size=1;
            ((CharDblLnkSeq)curr).privateInit(newNode);
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
        void initHelper(char val)
        {
          final Checked root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          bubbleUpinit(new Node(val));
        }
        @Override
        void prependHelper(char val)
        {
          ++this.modCount;
          Node newNode,oldhead;
          head=newNode=new Node((oldhead=head).prev,val,oldhead);
          SubList parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpsuffixPushhead(newNode,oldhead);
          }
        }
        @Override
        void initHelper(Checked root,char val)
        {
          final Node newNode;
          ((CharDblLnkSeq)this).privateInit(newNode=new Node(val));
          SubList parent,curr;
          if((parent=(curr=this).parent)!=null)
          {
            do
            {
              int parentSize;
              if((parentSize=parent.size)!=0)
              {
                Node before,after;
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
              ((CharDblLnkSeq)curr).privateInit(newNode);
            }
            while(parent!=null);
          }
          ((CharDblLnkSeq)root).subSeqInsertHelper(newNode,curr.getParentOffset());
        }
        private void bubbleUppushhead(Node oldhead,Node newNode)
        {
          SubList curr=this;
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
        void pushheadHelper(Node oldhead,char val)
        {
          final Checked root;
          final int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          ++root.size;
          bubbleUppushhead(oldhead,new Node((oldhead=this.head).prev,val,oldhead));
        }
        @Override
        public void clear()
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList)this).clearBody(size);
          }
        }
        private void bubbleUpcollapseHeadAndTail(Node oldHead,Node oldTail,Node newHead,Node newTail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList curr=this;;)
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
        private void collapseHeadAndTail(int modCount,final Node head,final Node tail,final CharPredicate filter)
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
              ((SubList)this).bubbleUpclearBody(head,tail,oldSize);
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
        void findNewHead(int modCount,Node curr,CharPredicate filter)
        {
          final Node tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList)this).suffixCollapsehead(modCount,curr,tail,filter);
              return;
            }
            collapseHeadAndTail(modCount,curr,tail,filter);
            return;
          }
          final Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=modCount+1;
          --root.size;
          ((SubList)this).bubbleUpclearBody(curr,tail,1);
        }
        @Override
        void ascItrRemove(AscendingItr itr)
        {
          Node lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked root;
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
        void bidirectItrRemove(BidirectionalItr itr)
        {
          Node lastRet;
          if((lastRet=itr.lastRet)!=null)
          {
            int modCount;
            final Checked root;
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
        private void uncheckedItrRemove(Node lastRet)
        {
          if(lastRet==head)
          {
            if(lastRet==tail)
            {
              ((SubList)this).bubbleUpclearBody(lastRet,lastRet,1);
            }
            else
            {
              ((SubList)this).bubbleUpsuffixchophead(lastRet,lastRet.next);
            }
          }
          else
          {
            if(lastRet==tail)
            {
              ((SubList)this).bubbleUpprefixchoptail(lastRet,lastRet.prev);
            }
            else
            {
              ((SubList)this).bubbleUpdecrementSize();
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
    private static class AscendingItr extends AbstractCharItr implements OmniIterator.OfChar
    {
      transient final Checked root;
      transient Node cursor;
      transient Node lastRet;
      transient int modCount;
      AscendingItr(Checked root)
      {
        this.root=root;
        this.cursor=root.head;
        this.modCount=root.modCount;
      }
      AscendingItr(Checked root,Node cursor)
      {
        this.root=root;
        this.cursor=cursor;
        this.modCount=root.modCount;
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        Node cursor;
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
      public void forEachRemaining(Consumer<? super Character> action)
      {
        Node cursor;
        if((cursor=this.cursor)!=null)
        {
          final var root=this.root;
          int modCount=this.modCount;
          try
          {
            cursor=uncheckedForEach(cursor,root,(CharConsumer)action::accept);
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
      public char nextChar()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        Node cursor;
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
        Node lastRet;
        if((lastRet=this.lastRet)!=null)
        {
            int modCount;
            final Checked root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            uncheckedRemove(lastRet,root);
            this.lastRet=null;
            return;
        }
        throw new IllegalStateException();
      }
      Node iterate(Node cursor)
      {
        return cursor.next;
      }
      Node uncheckedForEach(Node cursor,Checked root,CharConsumer action)
      {
        cursor.uncheckedForEachForward(cursor=root.tail,action);
        return cursor;
      }
      void uncheckedRemove(Node lastRet,Checked root)
      {
        if(--root.size==0)
        {
          ((CharDblLnkSeq)root).privateInit(null);
        }
        else if(lastRet==root.head)
        {
          ((CharDblLnkSeq)root).privatesethead(cursor);
        }
        else if(lastRet==root.tail)
        {
          ((CharDblLnkSeq)root).privatechoptail(lastRet);
        }
        else
        {
          lastRet.prev.joinnext(cursor);
        }
      }
    }
    private static class DescendingItr extends AscendingItr
    {
      DescendingItr(Checked root)
      {
        super(root,root.tail);
      }
      @Override
      Node iterate(Node cursor)
      {
        return cursor.prev;
      }
      @Override
      Node uncheckedForEach(Node cursor,Checked root,CharConsumer action)
      {
        cursor.uncheckedForEachReverse(action);
        return root.head;
      }
      @Override
      void uncheckedRemove(Node lastRet,Checked root)
      {
        if(--root.size==0)
        {
          ((CharDblLnkSeq)root).privateInit(null);
        }
        else if(lastRet==root.head)
        {
          ((CharDblLnkSeq)root).privatechophead(lastRet);
        }
        else if(lastRet==root.tail)
        {
          ((CharDblLnkSeq)root).privatesettail(cursor);
        }
        else
        {
          cursor.joinnext(lastRet.next);
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfChar
    {
      private transient int nextIndex;
      BidirectionalItr(Checked root)
      {
        super(root);
      }
      BidirectionalItr(Checked root,Node cursor,int index)
      {
        super(root,cursor);
        this.nextIndex=index;
      }
      public void add(char val)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        if(++root.size!=1)
        {
          Node cursor;
          if((cursor=this.cursor)==null)
          {
            root.tail=new Node(root.tail,val);
          }
          else if(cursor==root.head)
          {
            root.head=new Node(val,cursor);
          }
          else
          {
            new Node(cursor.prev,val,cursor);
          }
        }
        else
        {
          ((CharDblLnkSeq)root).privateInit(new Node(val));
        }
        ++nextIndex;
        lastRet=null;
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        Node cursor;
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
      public void forEachRemaining(Consumer<? super Character> action)
      {
        Node cursor;
        if((cursor=this.cursor)!=null)
        {
          final var root=this.root;
          int modCount=this.modCount;
          try
          {
            cursor.uncheckedForEachForward(cursor=root.tail,(CharConsumer)action::accept);
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
      public char nextChar()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        Node cursor;
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
      public char previousChar()
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        int nextIndex;
        if((nextIndex=this.nextIndex)!=0)
        {
          this.nextIndex=nextIndex-1;
          Node cursor;
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
      public void set(char val)
      {
        Node lastRet;
        if((lastRet=this.lastRet)!=null)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      void uncheckedRemove(Node lastRet,Checked root)
      {
        if(--root.size!=0)
        {
          Node cursor;
          if(lastRet!=(cursor=this.cursor))
          {
            --nextIndex;
            if(lastRet==root.head)
            {
              ((CharDblLnkSeq)root).privatesethead(cursor);
            }
            else if(lastRet==root.tail)
            {
              ((CharDblLnkSeq)root).privatechoptail(lastRet);
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
              ((CharDblLnkSeq)root).privatechophead(lastRet);
            }
            else if(lastRet==root.tail)
            {
              ((CharDblLnkSeq)root).privatechoptail(lastRet);
            }
            else
            {
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
        else
        {
          ((CharDblLnkSeq)root).privateInit(null);
          if(lastRet!=cursor)
          {
            --nextIndex;
          }
        }
      }
    }
  }
  static class Node
  {
    transient Node prev;
    transient char val;
    transient Node next;
    Node(char val)
    {
      this.val=val;
    }
    Node(Node prev,char val)
    {
      this.prev=prev;
      this.val=val;
      prev.joinnext(this);
    }
    Node(char val,Node next)
    {
      this.val=val;
      this.next=next;
      joinnext(next);
    }
    Node(Node prev,char val,Node next)
    {
      this.prev=prev;
      this.val=val;
      this.next=next;
      prev.joinnext(this);
      joinnext(next);
    }
    private int collapseBodyHelper(Checked.ModCountChecker modCountChecker,int numLeft,Node next,CharPredicate filter)
    {
      int numRemoved=0;
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
          ++numRemoved;
          for(Node after;;next=after)
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
    private int collapseBodyHelper(Node next,CharPredicate filter)
    {
      int numRemoved=0;
      for(Node curr,prev=this;(curr=prev.next)!=next;prev=curr)
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
    private void joinnext(Node next)
    {
      this.next=next;
      next.prev=this;
    }
    private void joinprev(Node prev)
    {
      this.prev=prev;
      prev.next=this;
    }
    private Node iterateForward(int dist)
    {
      if(dist!=0)
      {
        return uncheckedIterateForward(dist);
      }
      return this;
    }
    private Node iterateReverse(int dist)
    {
      if(dist!=0)
      {
        return uncheckedIterateReverse(dist);
      }
      return this;
    }
    private void uncheckedForEachForward(Node end,CharConsumer action)
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
    private void uncheckedForEachReverse(CharConsumer action)
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
    private int uncheckedForwardHashCode(Node end)
    {
      int hash=31+(this.val);
      for(var curr=this;curr!=end;hash=hash*31+((curr=curr.next).val))
      {  
      }
      return hash;
    }
    private void uncheckedForwardToString(Node end,StringBuilder builder)
    {
      Node curr;
      for(builder.append((curr=this).val);curr!=end;builder.append(',').append(' ').append((curr=curr.next).val))
      {
      }
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
    private Node uncheckedIterateReverse(int dist)
    {
      var curr=prev;
      while(--dist!=0)
      {
        curr=curr.prev;
      }
      return curr;
    }
    private void uncheckedReplaceAll(Node end,CharUnaryOperator operator)
    {
      for(var curr=this;;curr=curr.next)
      {
        curr.val=operator.applyAsChar(curr.val);
        if(curr==end)
        {
          return;
        }
      }
    }
    private void copyFromArray(char[] src,int srcOffset,int length)
    {
      for(Node curr=this;;++srcOffset,curr=curr.next)
      {
        curr.val=(char)src[srcOffset];
        if(--length==0)
        {
          return;
        }
      }
    }
    private void countingsort(Node end)
    {
      int[] count=new int[(Character.MAX_VALUE+1)-Character.MIN_VALUE];
      for(Node i=this;;i=i.next)
      {
        ++count[i.val];
        if(i==end)
        {
          break;
        }
      }
      for(int i=(Character.MAX_VALUE+1)-Character.MIN_VALUE;;)
      {
        int s;
        while((s=count[--i])==0){}
        char value=(char)i;
        for(;;end=end.prev)
        {
          end.val=value;
          if(end==this)
          {
            return;
          }
          if(--s==0)
          {
            break;
          }
        }
      }
    }
    private void uncheckedsort(int size,Node end)
    {
      if(size<3200)
      {
        //doSort
        char[] tmpArr;
        uncheckedCopyForward(tmpArr=new char[size],0,size);
        CharSortUtil.dosort(tmpArr,0,size-1);
        copyFromArray(tmpArr,0,size);
      }
      else
      {
        countingsort(end);
      }
    }
    private void countingreverseSort(Node end)
    {
      int[] count=new int[(Character.MAX_VALUE+1)-Character.MIN_VALUE];
      for(Node i=this;;i=i.next)
      {
        ++count[i.val];
        if(i==end)
        {
          break;
        }
      }
      for(int i=-1;;)
      {
        int s;
        while((s=count[++i])==0){}
        char value=(char)i;
        for(;;end=end.prev)
        {
          end.val=value;
          if(end==this)
          {
            return;
          }
          if(--s==0)
          {
            break;
          }
        }
      }
    }
    private void uncheckedreverseSort(int size,Node end)
    {
      if(size<3200)
      {
        //doSort
        char[] tmpArr;
        uncheckedCopyForward(tmpArr=new char[size],0,size);
        CharSortUtil.doreverseSort(tmpArr,0,size-1);
        copyFromArray(tmpArr,0,size);
      }
      else
      {
        countingreverseSort(end);
      }
    }
    private void uncheckedcomparatorSort(int size,Node end,CharComparator sorter)
    {
      char[] tmpArr;
      uncheckedCopyForward(tmpArr=new char[size],0,size);
      CharSortUtil.uncheckedcomparatorSort(tmpArr,0,size-1,sorter);
      copyFromArray(tmpArr,0,size);
    }
    private void uncheckedCopyForward(char[] dst,int dstOffset,int length)
    {
      for(var src=this;;++dstOffset,src=src.next)
      {
        dst[dstOffset]=(char)(src.val);
        if(--length==0)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(Object[] dst,int dstOffset,int length)
    {
      for(var src=this;;++dstOffset,src=src.next)
      {
        dst[dstOffset]=(src.val);
        if(--length==0)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(Character[] dst,int dstOffset,int length)
    {
      for(var src=this;;++dstOffset,src=src.next)
      {
        dst[dstOffset]=(Character)(src.val);
        if(--length==0)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(double[] dst,int dstOffset,int length)
    {
      for(var src=this;;++dstOffset,src=src.next)
      {
        dst[dstOffset]=(double)(src.val);
        if(--length==0)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(float[] dst,int dstOffset,int length)
    {
      for(var src=this;;++dstOffset,src=src.next)
      {
        dst[dstOffset]=(float)(src.val);
        if(--length==0)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(long[] dst,int dstOffset,int length)
    {
      for(var src=this;;++dstOffset,src=src.next)
      {
        dst[dstOffset]=(long)(src.val);
        if(--length==0)
        {
          return;
        }
      }
    }
    private void uncheckedCopyForward(int[] dst,int dstOffset,int length)
    {
      for(var src=this;;++dstOffset,src=src.next)
      {
        dst[dstOffset]=(int)(src.val);
        if(--length==0)
        {
          return;
        }
      }
    }
  }
  public static class Unchecked extends CharDblLnkSeq implements OmniDeque.OfChar
  {
    public Unchecked()
    {
      super();
    }
    public Unchecked(Node onlyNode)
    {
      super(onlyNode);
    }
    public Unchecked(Node head,int size,Node tail)
    {
      super(head,size,tail);
    }
    @Override
    public boolean removeIf(CharPredicate filter)
    {
      final Node head;
      return (head=this.head)!=null && super.uncheckedRemoveIf(head,filter);
    }
    @Override
    public boolean removeIf(Predicate<? super Character> filter)
    {
      final Node head;
      return (head=this.head)!=null && super.uncheckedRemoveIf(head,filter::test);
    }
    @Override
    void initHelper(char val)
    {
      this.size=1;
      ((CharDblLnkSeq)this).privateInit(new Node(val));
    }
    @Override
    public void add(int index,char val)
    {
      int size;
      if((size=this.size)!=0)
      {
        super.uncheckedAddHelper(index,size+1,val);
      }
      else
      {
        this.size=1;
        ((CharDblLnkSeq)this).privateInit(new Node(val));
      }
    }
    @Override
    public char removeCharAt(int index)
    {
      Node node;
      int size;
      if((size=this.size)!=1)
      {
        node=super.removeAtIndexHelper(index,size-1);
      }
      else
      {
        node=this.head;
        this.size=0;
        ((CharDblLnkSeq)this).privateInit(null);
      }
      return node.val;
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
    private boolean uncheckedremoveVal(Node head,int val)
    {
      if(val!=(head.val))
      {
        Node lastVisited;
        do
        {
          if((head=(lastVisited=head).next)==null)
          {
            return false;
          }
        }
        while(val!=(head.val));
        --this.size;
        if(head==tail)
        {
          ((CharDblLnkSeq)this).privatesettail(lastVisited);
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
          ((CharDblLnkSeq)this).privateInit(null);
        }
        else
        {
          ((CharDblLnkSeq)this).privatechophead(head);
        }
      }
      return true;
    }
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
    public boolean removeLastOccurrence(Object val)
    {
      Node tail;
      if((tail=this.tail)!=null)
      {
        if(val instanceof Character)
        {
          return uncheckedremoveLastOccurrence(tail,(char)(val));
        }
      }
      return false;
    }
    private boolean uncheckedremoveLastOccurrence(Node tail,int val)
    {
      if(val!=(tail.val))
      {
        Node lastVisited;
        do
        {
          if((tail=(lastVisited=tail).next)==null)
          {
            return false;
          }
        }
        while(val!=(tail.val));
        --this.size;
        if(tail==head)
        {
          ((CharDblLnkSeq)this).privatesethead(lastVisited);
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
          ((CharDblLnkSeq)this).privateInit(null);
        }
        else
        {
          ((CharDblLnkSeq)this).privatechoptail(tail);
        }
      }
      return true;
    }
    @Override
    public boolean removeLastOccurrence(final boolean val)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        return this.uncheckedremoveLastOccurrence(tail,TypeUtil.castToChar(val));
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final int val)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        if(val==(char)val)
        {
          return this.uncheckedremoveLastOccurrence(tail,(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final long val)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return this.uncheckedremoveLastOccurrence(tail,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final float val)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return this.uncheckedremoveLastOccurrence(tail,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final double val)
    {
      final Node tail;
      if((tail=this.tail)!=null)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return this.uncheckedremoveLastOccurrence(tail,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final char val)
    {
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          return this.uncheckedremoveLastOccurrence(tail,(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeLastOccurrence(final short val)
    {
      if(val>=0)
      {
        final Node tail;
        if((tail=this.tail)!=null)
        {
          return this.uncheckedremoveLastOccurrence(tail,(val));
        }
      }
      return false;
    }
    @Override
    void pushheadHelper(Node oldhead,char val)
    {
      ++this.size;
      this.head=new Node(val,this.head);
    }
    @Override
    void pushtailHelper(Node oldtail,char val)
    {
      ++this.size;
      this.tail=new Node(this.tail,val);
    }
    @Override
    public Object clone()
    {
      Node newHead,newTail;
      final int size;
      if((size=this.size)!=0)
      {
        Node oldHead,oldTail;
        for(newHead=new Node((oldHead=this.head).val),newTail=newHead,oldTail=this.tail;oldHead!=oldTail;new Node(newTail,(oldHead=oldHead.next).val))
        {
        }
      }
      else
      {
        newHead=null;
        newTail=null;
      }
      return new Unchecked(newHead,size,newTail);
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
      return new AscendingItr(this);
    }
    @Override
    public OmniListIterator.OfChar listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfChar listIterator(int index)
    {
      final int tailDist;
      return new BidirectionalItr(this,(tailDist=size-index)==0?null:super.privateGetNode(index,tailDist),index);
    }
    @Override
    public OmniIterator.OfChar descendingIterator()
    {
      return new DescendingItr(this);
    }
    @Override
    public OmniList.OfChar subList(int fromIndex,int toIndex)
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
    private OmniList.OfChar getDefaultSubList(int headDist,int subListSize,int tailDist)
    {
      final Node subListHead=head;
      Node subListTail=tail;
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList(this,null,subListHead,subListSize,subListTail);
        }
        return new SubList.SuffixList(this,null,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail);
      }
      subListTail=tailDist<=subListSize?subListTail.uncheckedIterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
      if(headDist==0)
      {
        return new SubList.PrefixList(this,null,subListHead,subListSize,subListTail);
      }
      return new SubList.BodyList(this,null,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail,headDist);
    }
    private OmniList.OfChar getEmptySubList(int headDist,int tailDist)
    {
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList(this,null);
        }
        return new SubList.SuffixList(this,null);
      }
      else if(headDist==0)
      {
        return new SubList.PrefixList(this,null);
      }
      return new SubList.BodyList(this,null,headDist);
    }
    private OmniList.OfChar getSubList1(int headDist,int tailDist)
    {
      if(tailDist==0)
      {
        if(headDist==0)
        {
          return new SubList(this,null,head);
        }
        return new SubList.SuffixList(this,null,tail);
      }
      else if(headDist==0)
      {
        return new SubList.PrefixList(this,null,head);
      }
      return new SubList.BodyList(this,null,((CharDblLnkSeq)this).privateGetNode(headDist,tailDist),headDist);
    }
    private static class AscendingItr extends AbstractCharItr implements OmniIterator.OfChar
    {
      transient final Unchecked root;
      transient Node cursor;
      AscendingItr(Unchecked root)
      {
        this.root=root;
        cursor=root.head;
      }
      AscendingItr(Unchecked root,Node cursor)
      {
        this.root=root;
        this.cursor=cursor;
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        Node cursor;
        if((cursor=this.cursor)!=null)
        {
          cursor.uncheckedForEachForward(root.tail,action);
          this.cursor=null;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        Node cursor;
        if((cursor=this.cursor)!=null)
        {
          cursor.uncheckedForEachForward(root.tail,(CharConsumer)action::accept);
          this.cursor=null;
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=null;
      }
      @Override
      public char nextChar()
      {
        Node lastRet;
        cursor=(lastRet=cursor).next;
        return lastRet.val;
      }
      @Override
      public void remove()
      {
        final CharDblLnkSeq root;
        if(--(root=this.root).size!=0)
        {
          Node cursor;
          if((cursor=this.cursor)!=null)
          {
            Node lastRet;
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
    private static class DescendingItr extends AscendingItr
    {
      DescendingItr(Unchecked root)
      {
        super(root,root.tail);
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        Node cursor;
        if((cursor=this.cursor)!=null)
        {
          cursor.uncheckedForEachReverse(action);
          this.cursor=null;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        Node cursor;
        if((cursor=this.cursor)!=null)
        {
          cursor.uncheckedForEachReverse((CharConsumer)action::accept);
          this.cursor=null;
        }
      }
      @Override public char nextChar()
      {
        Node lastRet;
        cursor=(lastRet=cursor).prev;
        return lastRet.val;
      }
      @Override public void remove()
      {
        final CharDblLnkSeq root;
        if(--(root=this.root).size!=0)
        {
          Node cursor;
          if((cursor=this.cursor)!=null)
          {
            Node lastRet;
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
    static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfChar
    {
      private transient Node lastRet;
      private transient int nextIndex;
      BidirectionalItr(Unchecked root)
      {
        super(root);
      }
      BidirectionalItr(Unchecked root,Node cursor,int nextIndex)
      {
        super(root,cursor);
        this.nextIndex=nextIndex;
      }
      @Override
      public void add(char val)
      {
        final CharDblLnkSeq root;
        if(++(root=this.root).size!=1)
        {
          Node cursor;
          if((cursor=this.cursor)==null)
          {
            root.tail=new Node(root.tail,val);
          }
          else if(cursor==root.head)
          {
            root.head=new Node(val,cursor);
          }
          else
          {
            new Node(cursor.prev,val,cursor);
          }
        }
        else
        {
          root.privateInit(new Node(val));
        }
        ++nextIndex;
        lastRet=null;
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        Node cursor;
        if((cursor=this.cursor)!=null)
        {
          Node bound;
          Unchecked root;
          cursor.uncheckedForEachForward(bound=(root=this.root).tail,action);
          this.cursor=null;
          lastRet=bound;
          nextIndex=root.size;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        Node cursor;
        if((cursor=this.cursor)!=null)
        {
          Node bound;
          Unchecked root;
          cursor.uncheckedForEachForward(bound=(root=this.root).tail,(CharConsumer)action::accept);
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
      public char nextChar()
      {
        Node lastRet;
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
      public char previousChar()
      {
        Node lastRet;
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
        final CharDblLnkSeq root;
        if(--(root=this.root).size!=0)
        {
          Node cursor;
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
      public void set(char val)
      {
        lastRet.val=val;
      }
    }
    private static class SubList extends CharDblLnkSeq
    {
      transient final Unchecked root;
      transient final SubList parent;
      SubList(Unchecked root,SubList parent)
      {
        super();
        this.root=root;
        this.parent=parent;
      }
      SubList(Unchecked root,SubList parent,Node onlyNode)
      {
        super(onlyNode);
        this.root=root;
        this.parent=parent;
      }
      SubList(Unchecked root,SubList parent,Node head,int size,Node tail)
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
          ((SubList)this).clearRoot();
        }
      }
      int getParentOffset()
      {
        return 0;
      }
      @Override
      public boolean remove(final Object val)
      {
        final Node head;
        if((head=this.head)!=null)
        {
          if(val instanceof Character)
          {
            return uncheckedremoveVal(head,(char)(val));
          }
        }
        return false;
      }
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
      public void add(int index,char val)
      {
        final Unchecked root;
        ++(root=this.root).size;
        int size;
        if((size=this.size)!=0)
        {
          if(index==0)
          {
            Node newNode;
            root.head=newNode=new Node(val,this.head);
            ((SubList)this).bubbleUppushhead(newNode);
          }
          else
          {
            if((size-=index)==0)
            {
              Node newNode;
              root.tail=newNode=new Node(this.tail,val);
              ((SubList)this).bubbleUppushtail(newNode);
            }
            else
            {
              Node before,after;
              if(index<=size)
              {
                after=(before=this.head.iterateForward(index-1)).next;
              }
              else
              {
                before=(after=this.tail.iterateReverse(size-1)).prev;
              }
              new Node(before,val,after);
              ((SubList)this).bubbleUpincrementSize();
            }
          }
        }
        else
        {
          Node newNode;
          ((CharDblLnkSeq)root).privateInit(newNode=new Node(val));
          bubbleUpinit(newNode);
        }
      }
      private static class AscendingItr extends AbstractCharItr implements OmniIterator.OfChar
      {
        transient final SubList parent;
        transient Node cursor;
        AscendingItr(SubList parent)
        {
          this.parent=parent;
          cursor=parent.head;
        }
        AscendingItr(SubList parent,Node cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
        }
        @Override
        public void forEachRemaining(CharConsumer action)
        {
          Node cursor;
          if((cursor=this.cursor)!=null)
          {
            cursor.uncheckedForEachForward(parent.tail,action);
            this.cursor=null;
          }
        }
        @Override
        public void forEachRemaining(Consumer<? super Character> action)
        {
          Node cursor;
          if((cursor=this.cursor)!=null)
          {
            cursor.uncheckedForEachForward(parent.tail,(CharConsumer)action::accept);
            this.cursor=null;
          }
        }
        @Override
        public boolean hasNext()
        {
          return this.cursor!=null;
        }
        @Override
        public char nextChar()
        {
          Node cursor;
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
      private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfChar
      {
        private transient int nextIndex;
        private transient Node lastRet;
        private BidirectionalItr(SubList parent)
        {
          super(parent);
        }
        private BidirectionalItr(SubList parent,Node cursor,int nextIndex)
        {
          super(parent,cursor);
          this.nextIndex=nextIndex;
        }
        @Override
        public void add(char val)
        {
          final int nextIndex=this.nextIndex++;
          int size;
          final SubList parent;
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
              Node cursor;
              parent.insertHelper((cursor=this.cursor).prev,val,cursor);
            }
          }
          else
          {
            parent.initHelper(root,val);
          }
        }
        @Override
        public void forEachRemaining(CharConsumer action)
        {
          Node cursor;
          if((cursor=this.cursor)!=null)
          {
            SubList parent;
            cursor.uncheckedForEachForward(cursor=(parent=this.parent).tail,action);
            nextIndex=parent.size;
            this.cursor=null;
            lastRet=cursor;
          }
        }
        @Override
        public void forEachRemaining(Consumer<? super Character> action)
        {
          Node cursor;
          if((cursor=this.cursor)!=null)
          {
            SubList parent;
            cursor.uncheckedForEachForward(cursor=(parent=this.parent).tail,(CharConsumer)action::accept);
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
        public char nextChar()
        {
          Node cursor;
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
        public char previousChar()
        {
          Node lastRet;
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
          Node lastRet;
          parent.bidirectItrRemove(lastRet=this.lastRet);
          if(lastRet!=cursor)
          {
            --nextIndex;
          }
          this.lastRet=null;
        }
        @Override
        public void set(char val)
        {
          lastRet.val=val;
        }
      }
      @Override
      void initHelper(char val)
      {
        CharDblLnkSeq root;
        (root=this.root).size=1;
        Node newNode;
        root.privateInit(newNode=new Node(val));
        bubbleUpinit(newNode);
      }
      void removeFirstHelper(Node curr)
      {
        final var root=this.root;
        if(curr==tail)
        {
          bubbleUpclearRoot();
          ((CharDblLnkSeq)root).privateInit(null);
        }
        else
        {
          bubbleUprootchophead(curr=curr.next);
          ((CharDblLnkSeq)root).privatesethead(curr);
        }
        --root.size;
      }
      @Override
      void pushheadHelper(Node oldhead,char val)
      {
        final Unchecked root;
        ++(root=this.root).size;
        Node newNode;
        root.head=newNode=new Node(val,oldhead);
        bubbleUppushhead(newNode);
      }
      private void bubbleUppushhead(Node newhead)
      {
        SubList curr=this;
        do
        {
          ++curr.size;curr.head=newhead;
        }
        while((curr=curr.parent)!=null);
      }
      @Override
      void pushtailHelper(Node oldtail,char val)
      {
        final Unchecked root;
        ++(root=this.root).size;
        Node newNode;
        root.tail=newNode=new Node(oldtail,val);
        bubbleUppushtail(newNode);
      }
      private void bubbleUppushtail(Node newtail)
      {
        SubList curr=this;
        do
        {
          ++curr.size;curr.tail=newtail;
        }
        while((curr=curr.parent)!=null);
      }
      private void privateCollapseHeadAndTail(int size,Node head,Node tail)
      {
        this.size=size;
        this.head=head;
        this.tail=tail;
      }
      @Override
      public char removeCharAt(int index)
      {
        Unchecked root;
        --(root=this.root).size;
        Node node;
        int size;
        if((size=--this.size)!=0)
        {
          if(index==0)
          {
            Node newHead;
            ((CharDblLnkSeq)root).privatesethead(newHead=(node=this.head).next);
            ((SubList)this).bubbleUprootchophead(newHead);
          }
          else
          {
            if((size-=index)==0)
            {
              Node newTail;
              ((CharDblLnkSeq)root).privatesettail(newTail=(node=this.tail).prev);
              ((SubList)this).bubbleUprootchoptail(newTail);
            }
            else
            {
              if(index<=size)
              {
                Node before;
                (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
              }
              else
              {
                Node after;
                (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
              }
              ((SubList)this).bubbleUpdecrementSize();
            }
          }
        }
        else
        {
          node=this.head;
          ((CharDblLnkSeq)root).privateInit(null);
          bubbleUpclearRoot();
        }
        return node.val;
      }
      private void bubbleUpsuffixchophead(Node oldHead,Node newHead)
      {
        SubList curr=this;
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
      private void bubbleUpprefixchoptail(Node oldTail,Node newTail)
      {
        SubList curr=this;
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
        SubList curr=this;
        do
        {
           curr.size=0;((CharDblLnkSeq)curr).privateInit(null);
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpincrementSize()
      {
        SubList curr=this;
        do
        {
           ++curr.size;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpdecrementSize()
      {
        SubList curr=this;
        do
        {
           --curr.size;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpdecrementSize(int numRemoved)
      {
        SubList curr=this;
        do
        {
           curr.size-=numRemoved;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchophead(Node newHead)
      {
        SubList curr=this;
        do
        {
           --curr.size;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchoptail(Node newTail)
      {
        SubList curr=this;
        do
        {
           --curr.size;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchophead(int numRemoved,Node newHead)
      {
        SubList curr=this;
        do
        {
           curr.size-=numRemoved;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootchoptail(int numRemoved,Node newTail)
      {
        SubList curr=this;
        do
        {
           curr.size-=numRemoved;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpprefixPushtail(Node oldTail,Node newTail)
      {
        SubList curr=this;
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
      private void bubbleUpsuffixPushhead(Node oldHead,Node newHead)
      {
        SubList curr=this;
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
      private void bubbleUpclearPrefix(Node oldTail,Node newHead,int numRemoved)
      {
        SubList curr=this;
        do
        {
           curr.size=0;((CharDblLnkSeq)curr).privateInit(null);
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.tail==oldTail);
        curr.bubbleUprootchophead(numRemoved,newHead);
      }
      private void bubbleUpclearSuffix(Node oldHead,Node newTail,int numRemoved)
      {
        SubList curr=this;
        do
        {
           curr.size=0;((CharDblLnkSeq)curr).privateInit(null);
          if((curr=curr.parent)==null)
          {
            return;
          }
        }
        while(curr.head==oldHead);
        curr.bubbleUprootchoptail(numRemoved,newTail);
      }
      private void bubbleUpclearBody(Node oldhead,Node oldtail,int numRemoved)
      {
        Node prev,next;
        (prev=oldhead.prev).joinnext(next=tail.next);
        var curr=this;
        for(;;)
        {
          curr.size=0;
          ((CharDblLnkSeq)curr).privateInit(null);
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
      private void bubbleUpprefixCollapsetail(int numRemoved,Node oldTail,Node newTail)
      {
        SubList curr=this;
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
      private void bubbleUpsuffixCollapsehead(int numRemoved,Node oldHead,Node newHead)
      {
        SubList curr=this;
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
      private void bubbleUprootCollapsehead(int numRemoved,Node newHead)
      {
        SubList curr=this;
        do
        {
           curr.size-=numRemoved;curr.head=newHead;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUprootCollapsetail(int numRemoved,Node newTail)
      {
        SubList curr=this;
        do
        {
           curr.size-=numRemoved;curr.tail=newTail;
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpcollapseHeadAndTail(Node newHead,Node newTail,int newSize)
      {
        SubList curr=this;
        do
        {
          curr.privateCollapseHeadAndTail(newSize,newHead,newTail);
        }
        while((curr=curr.parent)!=null);
      }
      private void bubbleUpinit(Node newNode)
      {
        SubList curr=this;
        do
        {
           curr.size=1;((CharDblLnkSeq)curr).privateInit(newNode);
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
        Node newHead,newTail;
        final int size;
        if((size=this.size)!=0)
        {
          Node oldHead,oldTail;
          for(newHead=new Node((oldHead=this.head).val),newTail=newHead,oldTail=this.tail;oldHead!=oldTail;new Node(newTail,(oldHead=oldHead.next).val))
          {
          }
        }
        else
        {
          newHead=null;
          newTail=null;
        }
        return new Unchecked(newHead,size,newTail);
      }
      @Override
      public OmniIterator.OfChar iterator()
      {
        return new AscendingItr(this);
      }
      @Override
      public OmniListIterator.OfChar listIterator()
      {
        return new BidirectionalItr(this);
      }
      @Override
      public OmniListIterator.OfChar listIterator(int index)
      {
        return new BidirectionalItr(this,super.getItrNode(index,size-index),index);
      }
      boolean uncheckedremoveValHelper(Node curr,int val)
      {
        final var root=this.root;
        final var tail=this.tail;
        Node prev;
        do
        {
          if(curr==tail)
          {
            return false;
          }
        }
        while(val!=((curr=(prev=curr).next).val));
        --root.size;
        if(curr==tail)
        {
          bubbleUprootchoptail(prev);root.tail=prev;prev.next=null;
        }
        else
        {
          ((SubList)this).bubbleUpdecrementSize();
          prev.joinnext(curr.next);
        }
        return true;
      }
      @Override
      public OmniList.OfChar subList(int fromIndex,int toIndex)
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
      public boolean removeIf(CharPredicate filter)
      {
        final Node head;
        return (head=this.head)!=null && this.uncheckedRemoveIf(head,filter);
      }
      @Override
      public boolean removeIf(Predicate<? super Character> filter)
      {
        final Node head;
        return (head=this.head)!=null && this.uncheckedRemoveIf(head,filter::test);
      }
      private boolean uncheckedRemoveIf(Node head,CharPredicate filter)
      {
        if(filter.test(head.val))
        {
          findNewHead(head,filter);
          return true;
        }
        final Node tail;
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
      private OmniList.OfChar getDefaultSubList(Unchecked root,int headDist,int subListSize,int tailDist)
      {
        final Node subListHead=head;
        Node subListTail=tail;
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList(root,this,subListHead,subListSize,subListTail);
          }
          return new SubList.SuffixList(root,this,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail);
        }
        subListTail=tailDist<=subListSize?subListTail.uncheckedIterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize);
        if(headDist==0)
        {
          return new SubList.PrefixList(root,this,subListHead,subListSize,subListTail);
        }
        return new SubList.BodyList(root,this,headDist<=subListSize?subListHead.uncheckedIterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize),subListSize,subListTail,headDist);
      }
      private OmniList.OfChar getEmptySubList(Unchecked root,int headDist,int tailDist)
      {
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList(root,this);
          }
          return new SubList.SuffixList(root,this);
        }
        else if(headDist==0)
        {
          return new SubList.PrefixList(root,this);
        }
        return new SubList.BodyList(root,this,headDist);
      }
      private OmniList.OfChar getSubList1(Unchecked root,int headDist,int tailDist)
      {
        if(tailDist==0)
        {
          if(headDist==0)
          {
            return new SubList(root,this,head);
          }
          return new SubList.SuffixList(root,this,tail);
        }
        else if(headDist==0)
        {
          return new SubList.PrefixList(root,this,head);
        }
        return new SubList.BodyList(root,this,((CharDblLnkSeq)this).privateGetNode(headDist,tailDist),headDist);
      }
      private void collapseHeadAndTail(Node head,Node tail,CharPredicate filter)
      {
        final int oldSize=this.size;
        int numConsumed=2;
        final var root=this.root;
        for(;;)
        {
          if(numConsumed==oldSize)
          {
            bubbleUpclearRoot();
            ((CharDblLnkSeq)root).privateInit(null);
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
            ((CharDblLnkSeq)root).privatesethead(head);
            ((CharDblLnkSeq)root).privatesettail(tail);
            break;
          }
        }
        root.size-=numConsumed;
      }
      private void suffixCollapsehead(Node head,Node tail,CharPredicate filter)
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
      private void rootCollapsehead(Node head,Node tail,CharPredicate filter)
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
        ((CharDblLnkSeq)root).privatesethead(head);
        root.size-=numConsumed;
      }
      private void clearRoot()
      {
        final CharDblLnkSeq root;
        (root=this.root).size=0;
        root.privateInit(null);
        bubbleUpclearRoot();
      }
      private void clearPrefix(int numRemoved)
      {
        Node oldtail;
        Node newhead=(oldtail=this.tail).next;
        final CharDblLnkSeq root;
        (root=this.root).size-=numRemoved;
        root.privatesethead(newhead);
        bubbleUpclearPrefix(oldtail,newhead,numRemoved);
      }
      private void clearSuffix(int numRemoved)
      {
        Node oldhead;
        Node newtail=(oldhead=this.head).prev;
        final CharDblLnkSeq root;
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
            void findNewHead(Node curr,CharPredicate filter)
            {
              final Node tail;
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
              final CharDblLnkSeq root;(root=this.root).privateInit(null);
              root.size=0;
              bubbleUpclearRoot();
            }
      void collapseTail(Node head,Node tail,CharPredicate filter)
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
        final CharDblLnkSeq root;
        (root=this.root).privatesettail(tail);
        root.size-=numRemoved;
      }
      @Override
      boolean collapseBody(Node prev,Node next,CharPredicate filter)
      {
        for(Node curr;(curr=prev.next)!=next;prev=curr)
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
      private boolean uncheckedremoveVal(Node head,int val)
      {
        if(val!=(head.val))
        {
          return uncheckedremoveValHelper(head,val);
        }
        removeFirstHelper(head);
        return true;
      }
      void initHelper(Unchecked root,char val)
      {
        final var newNode=new Node(val);
        for(var curr=this;;curr.size=1)
        {
          ((CharDblLnkSeq)curr).privateInit(newNode);
          if((curr=curr.parent)==null)
          {
            ((CharDblLnkSeq)root).privateInit(newNode);
            return;
          }
        }
      }
      void prependHelper(char val)
      {
        Node newNode;
        this.head=newNode=new Node(val,this.head);
        SubList parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUppushhead(newNode);
        }
      }
      void appendHelper(char val)
      {
        Node newNode;
        this.tail=newNode=new Node(this.tail,val);
        SubList parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUppushtail(newNode);
        }
      }
      private void insertHelper(Node before,char val,Node after)
      {
        new Node(before,val,after);
        SubList parent;
        if((parent=this.parent)!=null)
        {
          parent.bubbleUpincrementSize();
        }
      }
      void ascItrRemove(Node cursor)
      {
        final Unchecked root;
        --(root=this.root).size;
        if(cursor!=null)
        {
          Node lastRet;
          if((lastRet=cursor.prev)==head)
          {
            ((SubList)this).bubbleUprootchophead(cursor);
            ((CharDblLnkSeq)root).privatesethead(cursor);
          }
          else
          {
            ((SubList)this).bubbleUpdecrementSize();
            lastRet.prev.joinnext(cursor);
          }
        }
        else
        {
          if(size==1)
          {
            ((SubList)this).bubbleUpclearRoot();
            ((CharDblLnkSeq)root).privateInit(null);
          }
          else
          {
            ((SubList)this).bubbleUprootchoptail(cursor=tail.prev);
            ((CharDblLnkSeq)root).privatesettail(cursor);
          }
        }
      }
      void bidirectItrRemove(Node lastRet)
      {
        final Unchecked root;
        --(root=this.root).size;
        if(lastRet==tail)
        {
          if(lastRet==head)
          {
            ((SubList)this).bubbleUpclearRoot();
            ((CharDblLnkSeq)root).privateInit(null);
          }
          else
          {
            ((SubList)this).bubbleUprootchoptail(lastRet=lastRet.prev);
            ((CharDblLnkSeq)root).privatesettail(lastRet);
          }
        }
        else
        {
          if(lastRet==head)
          {
            ((SubList)this).bubbleUprootchophead(lastRet=lastRet.next);
            ((CharDblLnkSeq)root).privatesethead(lastRet);
          }
          else
          {
            ((SubList)this).bubbleUpdecrementSize();
            lastRet.prev.joinnext(lastRet.next);
          }
        }
      }
      private static class PrefixList extends SubList
      {
        PrefixList(Unchecked root,SubList parent)
        {
          super(root,parent);
        }
        PrefixList(Unchecked root,SubList parent,Node onlyNode)
        {
          super(root,parent,onlyNode);
        }
        PrefixList(Unchecked root,SubList parent,Node head,int size,Node tail)
        {
          super(root,parent,head,size,tail);
        }
        @Override
        public void add(int index,char val)
        {
          final Unchecked root;
          ++(root=this.root).size;
          int size;
          if((size=this.size)!=0)
          {
            if(index==0)
            {
              Node newNode;
              root.head=newNode=new Node(val,this.head);
              ((SubList)this).bubbleUppushhead(newNode);
            }
            else
            {
              if((size-=index)==0)
              {
                Node oldtail;
                ((PrefixList)this).bubbleUppushtail(oldtail=this.tail,new Node(oldtail,val,oldtail.next));
              }
              else
              {
                Node before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node(before,val,after);
                ((SubList)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            Node newNode;
            root.head=newNode=new Node(val,root.head);
            bubbleUpinit(newNode);
          }
        }
        @Override
        void appendHelper(char val)
        {
          Node newNode,oldtail;
          tail=newNode=new Node(oldtail=tail,val,oldtail.next);
          SubList parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpprefixPushtail(newNode,oldtail);
          }
        }   
        @Override
        void initHelper(Unchecked root,char val)
        {
          final Node after,newNode=new Node(val);
          for(SubList curr=this;;curr.size=1)
          {
            ((CharDblLnkSeq)curr).privateInit(newNode);
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
        private void bubbleUppushtail(Node oldtail,Node newNode)
        {
          SubList curr=this;
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
        void pushtailHelper(Node oldtail,char val)
        {
          ++root.size;
          bubbleUppushtail(oldtail,new Node(oldtail=this.tail,val,oldtail.next));
        }
        private void bubbleUpinit(Node newNode)
        {
          SubList curr=this;
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
        void initHelper(char val)
        {
          final Unchecked root;
          ++(root=this.root).size;
          Node newNode;
          root.head=newNode=new Node(val,root.head);
          bubbleUpinit(newNode);
        }
        @Override
        public void clear()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList)this).clearPrefix(size);
          }
        }
        @Override
        void findNewHead(Node curr,CharPredicate filter)
        {
          final Node tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList)this).rootCollapsehead(curr,tail,filter);
              return;
            }
            collapseHeadAndTail(curr,tail,filter);
            return;
          }
          --root.size;
          ((SubList)this).bubbleUpclearPrefix(curr,curr=tail.next,1);
          ((CharDblLnkSeq)root).privatesethead(curr);
        }
        private void bubbleUpcollapseHeadAndTail(Node oldtail,Node newhead,Node newtail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList curr=this;;)
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
        private void collapseHeadAndTail(Node head,Node tail,CharPredicate filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              ((SubList)this).bubbleUpclearPrefix(tail,head=tail.next,oldSize);
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
        boolean uncheckedremoveValHelper(Node curr,int val)
        {
          final var root=this.root;
          final var tail=this.tail;
          Node prev;
          do
          {
            if(curr==tail)
            {
              return false;
            }
          }
          while(val!=((curr=(prev=curr).next).val));
          --root.size;
          if(curr==tail)
          {
            ((SubList)this).bubbleUpprefixchoptail(curr,prev);
          }
          else
          {
            ((SubList)this).bubbleUpdecrementSize();
            prev.joinnext(curr.next);
          }
          return true;
        }
        @Override
        void collapseTail(Node head,Node tail,CharPredicate filter)
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
          ((SubList)this).bubbleUpprefixCollapsetail(numConsumed,tail,tailCandidate);
          tailCandidate.joinnext(tail.next);
          root.size-=numConsumed;
        }
        @Override
        public char removeCharAt(int index)
        {
          Unchecked root;
          --(root=this.root).size;
          Node node;
          int size;
          if((size=--this.size)!=0)
          {
            if(index==0)
            {
              Node newHead;
              ((CharDblLnkSeq)root).privatesethead(newHead=(node=this.head).next);
              ((SubList)this).bubbleUprootchophead(newHead);
            }
            else
            {
              if((size-=index)==0)
              {
                ((SubList)this).bubbleUpprefixchoptail(node=this.tail,node.prev);
              }
              else
              {
                if(index<=size)
                {
                  Node before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            node=this.head;
            ((CharDblLnkSeq)root).privateInit(null);
            ((SubList)this).bubbleUpclearPrefix(node=this.head,node.next,1);
          }
          return node.val;
        }
        @Override
        public OmniList.OfChar subList(int fromIndex,int toIndex)
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
        private OmniList.OfChar getDefaultSubList(Unchecked root,int headDist,int subListSize,int tailDist)
        {
          Node subListHead=head,subListTail=tail;
          if(headDist==0)
          {
            return new SubList.PrefixList(root,this,subListHead,subListSize,tailDist<=subListSize?subListTail.iterateReverse(tailDist):subListHead.uncheckedIterateForward(subListSize));
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
          return new SubList.BodyList(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfChar getEmptySubList(Unchecked root,int headDist)
        {
          if(headDist==0)
          {
            return new SubList.PrefixList(root,this);
          }
          return new SubList.BodyList(root,this,headDist);
        }
        private OmniList.OfChar getSubList1(Unchecked root,int headDist,int tailDist)
        {
          if(headDist==0)
          {
            return new SubList.PrefixList(root,this,head);
          }
          return new SubList.BodyList(root,this,tailDist<headDist?tail.iterateReverse(tailDist):head.uncheckedIterateForward(headDist),headDist);
        }
        @Override
        void ascItrRemove(Node cursor)
        {
          final Unchecked root;
          --(root=this.root).size;
          if(cursor!=null)
          {
            Node lastRet;
            if((lastRet=cursor.prev)==head)
            {
              ((SubList)this).bubbleUprootchophead(cursor);
            }
            else
            {
              ((SubList)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(cursor);
              return;
            }
          }
          else
          {
            cursor=tail;
            if(size==1)
            {
              ((SubList)this).bubbleUpclearPrefix(cursor,cursor=cursor.next,1);
            }
            else
            {
              ((SubList)this).bubbleUpprefixchoptail(cursor,cursor.prev);
              return;
            }
          }
          ((CharDblLnkSeq)root).privatesethead(cursor);
        }
        @Override
        void bidirectItrRemove(Node lastRet)
        {
          final Unchecked root;
          --(root=this.root).size;
          if(lastRet==head)
          {
            if(lastRet==tail)
            {
              ((SubList)this).bubbleUpclearPrefix(lastRet,lastRet=lastRet.next,1);
            }
            else
            {
              ((SubList)this).bubbleUprootchophead(lastRet=lastRet.next);
            }
            ((CharDblLnkSeq)root).privatesethead(lastRet);
          }
          else
          {
            if(lastRet==tail)
            {
              ((SubList)this).bubbleUpprefixchoptail(lastRet,lastRet.prev);
            }
            else
            {
              ((SubList)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
      }
      private static class SuffixList extends SubList
      {
        SuffixList(Unchecked root,SubList parent)
        {
          super(root,parent);
        }
        SuffixList(Unchecked root,SubList parent,Node onlyNode)
        {
          super(root,parent,onlyNode);
        }
        SuffixList(Unchecked root,SubList parent,Node head,int size,Node tail)
        {
          super(root,parent,head,size,tail);
        }
        @Override
        public void add(int index,char val)
        {
          final Unchecked root;
          ++(root=this.root).size;
          int size;
          if((size=this.size)!=0)
          {
            if(index==0)
            {
              Node oldhead;
              bubbleUppushhead(oldhead=this.head,new Node(oldhead.prev,val,oldhead));
            }
            else
            {
              if((size-=index)==0)
              {
                Node newNode;
                root.tail=newNode=new Node(this.tail,val);
                ((SubList)this).bubbleUppushtail(newNode);
              }
              else
              {
                Node before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node(before,val,after);
                ((SubList)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            Node newNode;
            root.tail=newNode=new Node(root.tail,val);
            bubbleUpinit(newNode);
          }
        }
        @Override
        void removeFirstHelper(Node curr)
        {
          final var root=this.root;
          if(curr==tail)
          {
            ((SubList)this).bubbleUpclearSuffix(curr,curr=curr.prev,1);
            ((CharDblLnkSeq)root).privatesettail(curr);
          }
          else
          {
            ((SubList)this).bubbleUpsuffixchophead(curr,curr.next);
          }
          --root.size;
        }
        private void bubbleUpinit(Node newNode)
        {
          SubList curr=this;
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
        void initHelper(char val)
        {
          final Unchecked root;
          ++(root=this.root).size;
          Node newNode;
          root.tail=newNode=new Node(root.tail,val);
          bubbleUpinit(newNode);
        }
        private void bubbleUppushhead(Node oldhead,Node newNode)
        {
          SubList curr=this;
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
        void pushheadHelper(Node oldhead,char val)
        {
          ++root.size;
          bubbleUppushhead(oldhead,new Node((oldhead=this.head).prev,val,oldhead));
        }
        @Override
        public void clear()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ((SubList)this).clearSuffix(size);
          }
        }
        @Override
        void findNewHead(Node curr,CharPredicate filter)
        {
          final Node tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList)this).suffixCollapsehead(curr,tail,filter);
              return;
            }
            collapseHeadAndTail(curr,tail,filter);
            return;
          }
          --root.size;
          ((SubList)this).bubbleUpclearSuffix(curr,curr=curr.prev,1);
          ((CharDblLnkSeq)root).privatesettail(curr);
        }
        private void bubbleUpcollapseHeadAndTail(Node oldhead,Node newhead,Node newtail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList curr=this;;)
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
        private void collapseHeadAndTail(Node head,Node tail,CharPredicate filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              ((SubList)this).bubbleUpclearSuffix(head,tail=head.next,oldSize);
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
        public char removeCharAt(int index)
        {
          Unchecked root;
          --(root=this.root).size;
          Node node;
          int size;
          if((size=--this.size)!=0)
          {
            if(index==0)
            {
              ((SubList)this).bubbleUpsuffixchophead(node=this.head,node.next);
            }
            else
            {
              if((size-=index)==0)
              {
                Node newTail;
                ((CharDblLnkSeq)root).privatesettail(newTail=(node=this.tail).prev);
                ((SubList)this).bubbleUprootchoptail(newTail);
              }
              else
              {
                if(index<=size)
                {
                  Node before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            ((CharDblLnkSeq)root).privateInit(null);
            ((SubList)this).bubbleUpclearSuffix(node=this.head,node.prev,1);
          }
          return node.val;
        }
        @Override
        public OmniList.OfChar subList(int fromIndex,int toIndex)
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
        void initHelper(Unchecked root,char val)
        {
          final Node before,newNode=new Node(val);
          for(SubList curr=this;;curr.size=1)
          {
            ((CharDblLnkSeq)curr).privateInit(newNode);
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
        void prependHelper(char val)
        {
          Node newNode,oldhead;
          head=newNode=new Node((oldhead=head).prev,val,oldhead);
          SubList parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpsuffixPushhead(newNode,oldhead);
          }
        }
        private OmniList.OfChar getDefaultSubList(Unchecked root,int headDist,int subListSize,int tailDist)
        {
          Node subListHead=head,subListTail=tail;
          if(tailDist==0)
          {
            return new SubList.SuffixList(root,this,headDist<=subListSize?subListHead.iterateForward(headDist):subListTail.uncheckedIterateReverse(subListSize));
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
          return new SubList.BodyList(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfChar getEmptySubList(Unchecked root,int headDist,int tailDist)
        {
          if(tailDist==0)
          {
            return new SubList.PrefixList(root,this);
          }
          return new SubList.BodyList(root,this,headDist);
        }
        private OmniList.OfChar getSubList1(Unchecked root,int headDist,int tailDist)
        {
          if(tailDist==0)
          {
            return new SubList.SuffixList(root,this,tail);
          }
          return new SubList.BodyList(root,this,((CharDblLnkSeq)this).privateGetNode(headDist,tailDist),headDist);
        }
        @Override
        void ascItrRemove(Node cursor)
        {
          final Unchecked root;
          --(root=this.root).size;
          if(cursor!=null)
          {
            Node lastRet;
            if((lastRet=cursor.prev)==head)
            {
              ((SubList)this).bubbleUpsuffixchophead(lastRet,cursor);
            }
            else
            {
              ((SubList)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(cursor);
            }
          }
          else
          {
            if(size==1)
            {
              ((SubList)this).bubbleUpclearSuffix(cursor=tail,cursor=cursor.prev,1);
            }
            else
            {
              ((SubList)this).bubbleUprootchoptail(cursor=tail.prev);
            }
            ((CharDblLnkSeq)root).privatesettail(cursor);
          }
        }
        @Override
        void bidirectItrRemove(Node lastRet)
        {
          final Unchecked root;
          --(root=this.root).size;
          if(lastRet==tail)
          {
            if(lastRet==head)
            {
              ((SubList)this).bubbleUpclearSuffix(lastRet,lastRet=lastRet.prev,1);
            }
            else
            {
              ((SubList)this).bubbleUprootchoptail(lastRet=lastRet.prev);
            }
            ((CharDblLnkSeq)root).privatesettail(lastRet);
          }
          else
          {
            if(lastRet==head)
            {
              ((SubList)this).bubbleUpsuffixchophead(lastRet,lastRet.next);
            }
            else
            {
              ((SubList)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
      }
      private static class BodyList extends PrefixList
      {
        private transient final int parentOffset;
        BodyList(Unchecked root,SubList parent,int parentOffset)
        {
          super(root,parent);
          this.parentOffset=parentOffset;
        }
        BodyList(Unchecked root,SubList parent,Node onlyNode,int parentOffset)
        {
          super(root,parent,onlyNode);
          this.parentOffset=parentOffset;
        }
        BodyList(Unchecked root,SubList parent,Node head,int size,Node tail,int parentOffset)
        {
          super(root,parent,head,size,tail);
          this.parentOffset=parentOffset;
        }
        @Override
        public void add(int index,char val)
        {
          ++root.size;
          int size;
          if((size=this.size)!=0)
          {
            if(index==0)
            {
              Node oldhead;
              bubbleUppushhead(oldhead=this.head,new Node(oldhead.prev,val,oldhead));
            }
            else
            {
              if((size-=index)==0)
              {
                Node oldtail;
                ((PrefixList)this).bubbleUppushtail(oldtail=this.tail,new Node(oldtail,val,oldtail.next));
              }
              else
              {
                Node before,after;
                if(index<=size)
                {
                  after=(before=this.head.iterateForward(index-1)).next;
                }
                else
                {
                  before=(after=this.tail.iterateReverse(size-1)).prev;
                }
                new Node(before,val,after);
                ((SubList)this).bubbleUpincrementSize();
              }
            }
          }
          else
          {
            bubbleUpinit(new Node(val));
          }
        }
        @Override
        void removeFirstHelper(Node curr)
        {
          if(curr==tail)
          {
            Node node;
            ((SubList)this).bubbleUpclearBody(node=this.head,node,1);
          }
          else
          {
            ((SubList)this).bubbleUpsuffixchophead(curr,curr.next);
          }
          --root.size;
        }
        private void bubbleUpcollapseHeadAndTail(Node oldHead,Node oldTail,Node newHead,Node newTail,int numRemoved)
        {
          final int newSize=this.size-numRemoved;
          for(SubList curr=this;;)
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
        private void collapseHeadAndTail(final Node head,final Node tail,final CharPredicate filter)
        {
          final int oldSize=this.size;
          int numConsumed=2;
          var headCandidate=head;
          final var root=this.root;
          for(;;)
          {
            if(numConsumed==oldSize)
            {
              ((SubList)this).bubbleUpclearBody(head,tail,oldSize);
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
        void findNewHead(Node curr,CharPredicate filter)
        {
          final Node tail;
          if(curr!=(tail=this.tail))
          {
            if(!filter.test(tail.val))
            {
              ((SubList)this).suffixCollapsehead(curr,tail,filter);
              return;
            }
            collapseHeadAndTail(curr,tail,filter);
            return;
          }
          --root.size;
          ((SubList)this).bubbleUpclearBody(curr,tail,1);
        }
        private void bubbleUppushhead(Node oldhead,Node newNode)
        {
          SubList curr=this;
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
        void pushheadHelper(Node oldhead,char val)
        {
          ++root.size;
          bubbleUppushhead(oldhead,new Node((oldhead=this.head).prev,val,oldhead));
        }
        private void bubbleUpinit(Node newNode)
        {
          SubList curr=this;
          Node before,after;
          for(;;)
          {
            curr.size=1;
            ((CharDblLnkSeq)curr).privateInit(newNode);
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
            ((SubList)this).clearBody(size);
          }
        }
        @Override
        void initHelper(char val)
        {
          ++root.size;
          bubbleUpinit(new Node(val));
        }
        @Override
        void prependHelper(char val)
        {
          Node newNode,oldhead;
          head=newNode=new Node((oldhead=head).prev,val,oldhead);
          SubList parent;
          if((parent=this.parent)!=null)
          {
            parent.bubbleUpsuffixPushhead(newNode,oldhead);
          }
        }
        @Override
        void initHelper(Unchecked root,char val)
        {
          final Node newNode;
          ((CharDblLnkSeq)this).privateInit(newNode=new Node(val));
          SubList parent,curr;
          if((parent=(curr=this).parent)!=null)
          {
            do
            {
              int parentSize;
              if((parentSize=parent.size)!=0)
              {
                Node before,after;
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
              ((CharDblLnkSeq)curr).privateInit(newNode);
            }
            while(parent!=null);
          }
          ((CharDblLnkSeq)root).subSeqInsertHelper(newNode,curr.getParentOffset());
        }
        @Override
        public char removeCharAt(int index)
        {
          --root.size;
          Node node;
          int size;
          if((size=--this.size)!=0)
          {
            if(index==0)
            {
              ((SubList)this).bubbleUpsuffixchophead(node=this.head,node.next);
            }
            else
            {
              if((size-=index)==0)
              {
                ((SubList)this).bubbleUpprefixchoptail(node=this.tail,node.prev);
              }
              else
              {
                if(index<=size)
                {
                  Node before;
                  (before=this.head.iterateForward(index-1)).joinnext((node=before.next).next);
                }
                else
                {
                  Node after;
                  (node=(after=this.tail.iterateReverse(size-1)).prev).prev.joinnext(after);
                }
                ((SubList)this).bubbleUpdecrementSize();
              }
            }
          }
          else
          {
            ((SubList)this).bubbleUpclearBody(node=this.head,node,1);
          }
          return node.val;
        }
        @Override
        public OmniList.OfChar subList(int fromIndex,int toIndex)
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
        private OmniList.OfChar getDefaultSubList(Unchecked root,int headDist,int subListSize,int tailDist)
        {
          Node subListHead,subListTail;
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
          return new SubList.BodyList(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfChar getEmptySubList(Unchecked root,int headDist)
        {
          return new SubList.BodyList(root,this,headDist);
        }
        private OmniList.OfChar getSubList1(Unchecked root,int headDist,int tailDist)
        {
          return new SubList.BodyList(root,this,((CharDblLnkSeq)this).privateGetNode(headDist,tailDist),headDist);
        }
        @Override 
        void ascItrRemove(Node cursor)
        {
          --root.size;
          if(cursor!=null)
          {
            Node lastRet;
            if((lastRet=cursor.prev)==head)
            {
              ((SubList)this).bubbleUpsuffixchophead(lastRet,cursor);
            }
            else
            {
              ((SubList)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(cursor);
            }
          }
          else
          {
            cursor=tail;
            if(size==1)
            {
              ((SubList)this).bubbleUpclearBody(cursor,cursor,1);
            }
            else
            {
              ((SubList)this).bubbleUpprefixchoptail(cursor,cursor.prev);
            }
          }
        }
        @Override
        void bidirectItrRemove(Node lastRet)
        {
          --root.size;
          if(lastRet==head)
          {
            if(lastRet==tail)
            {
              ((SubList)this).bubbleUpclearBody(lastRet,lastRet,1);
            }
            else
            {
              ((SubList)this).bubbleUpsuffixchophead(lastRet,lastRet.next);
            }
          }
          else
          {
            if(lastRet==tail)
            {
              ((SubList)this).bubbleUpprefixchoptail(lastRet,lastRet.prev);
            }
            else
            {
              ((SubList)this).bubbleUpdecrementSize();
              lastRet.prev.joinnext(lastRet.next);
            }
          }
        }
      }
    }
  }
}
