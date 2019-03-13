package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.RefSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import java.util.ConcurrentModificationException;
import omni.util.ToStringUtil;
import omni.util.BitSetUtil;
public abstract class RefArrSeq<E> implements OmniCollection.OfRef<E>
{
  transient int size;
  transient Object[] arr;
  private RefArrSeq()
  {
    super();
  }
  private RefArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new Object[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfRef.DEFAULT_ARR;
    case 0:
    }
  }
  private RefArrSeq(int size,Object[] arr)
  {
    super();
    this.size=size;
    this.arr=arr;
  }
  @Override
  public int size()
  {
    return this.size;
  }
  @Override
  public boolean isEmpty()
  {
    return this.size==0;
  }
  @Override
  public void clear()
  {
    int size;
    if((size=this.size)!=0)
    {
      this.size=0;
      OmniArray.OfRef.nullifyRange(this.arr,size-1,0);
    }
  }
  @Override
  public int hashCode()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedHashCode(size);
    }
    return 1;
  }
  @Override
  public String toString()
  {
    int size;
    if((size=this.size)!=0)
    {
      if(size>(Integer.MAX_VALUE>>1))
      {
        throw new OutOfMemoryError();
      }
      final StringBuilder builder=new StringBuilder("[");
      uncheckedToString(size,builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  abstract int uncheckedHashCode(int size);
  abstract void uncheckedToString(int size,StringBuilder builder);
  public
    static class UncheckedStack<E>
      extends RefArrSeq<E>
      implements OmniStack.OfRef<E>
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedStack(int size,Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedStack<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new UncheckedStack<E>(size,copy);
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      OmniArray.OfRef.descendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfRef.descendingSeqHashCode(this.arr,0,size-1);
    }
  }
  public
    static class UncheckedList<E>
      extends RefArrSeq<E>
      implements OmniList.OfRef<E>
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedList(int size,Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedList<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new UncheckedList<E>(size,copy);
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      OmniArray.OfRef.ascendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfRef.ascendingSeqHashCode(this.arr,0,size-1);
    }
  }
  public
    static class UncheckedSubList<E>
      implements OmniList.OfRef<E>
  {
    transient final int rootOffset;
    transient int size;
    transient final UncheckedList<E> root;
    transient final UncheckedSubList<E> parent;
    private UncheckedSubList(UncheckedList<E> root,int rootOffset,int size)
    {
      super();
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    private UncheckedSubList(UncheckedSubList<E> parent,int rootOffset,int size)
    {
      super();
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedSubList<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new UncheckedList<E>(size,copy);
    }
    @Override
    public String toString()
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(size>(Integer.MAX_VALUE>>1))
          {
            throw new OutOfMemoryError();
          }
          final StringBuilder builder;
          final int rootOffset;
          OmniArray.OfRef.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new StringBuilder("["));
          return builder.append(']').toString();
        }
        return "[]";
      }
    }
    @Override
    public int hashCode()
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfRef.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
        return 1;
      }
    }
    @Override
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        for(var curr=parent;curr!=null;curr.size-=size,curr=curr.parent){}
        final UncheckedList<E> root;
        (root=this.root).size=OmniArray.OfRef.removeRangeAndPullDown(root.arr,this.rootOffset,root.size,size);
        this.size=0;
      }
    }
  }
  public
    static class CheckedStack<E>
      extends UncheckedStack<E>
  {
    transient int modCount;
    public CheckedStack()
    {
      super();
    }
    public CheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    private CheckedStack(int size,Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedStack<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new CheckedStack<E>(size,copy);
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      final int modCount=this.modCount;
      try
      {
        super.uncheckedToString(size,builder);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    int uncheckedHashCode(int size)
    {
      final int modCount=this.modCount;
      try
      {
        return super.uncheckedHashCode(size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        this.size=0;
        OmniArray.OfRef.nullifyRange(this.arr,size-1,0);
      }
    }
  }
  public
    static class CheckedList<E>
      extends UncheckedList<E>
  {
    transient int modCount;
    public CheckedList()
    {
      super();
    }
    public CheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    private CheckedList(int size,Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedList<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new CheckedList<E>(size,copy);
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      final int modCount=this.modCount;
      try
      {
        super.uncheckedToString(size,builder);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    int uncheckedHashCode(int size)
    {
      final int modCount=this.modCount;
      try
      {
        return super.uncheckedHashCode(size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        this.size=0;
        OmniArray.OfRef.nullifyRange(this.arr,size-1,0);
      }
    }
  }
  private
    static class CheckedSubList<E>
      implements OmniList.OfRef<E>
  {
    transient int modCount;
    transient final int rootOffset;
    transient int size;
    transient final CheckedList<E> root;
    transient final CheckedSubList<E> parent;
    private CheckedSubList(CheckedList<E> root,int rootOffset,int size)
    {
      super();
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList<E> parent,int rootOffset,int size)
    {
      super();
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
      this.modCount=parent.modCount;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedSubList<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final CheckedList<E> root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new CheckedList<E>(size,copy);
    }
    @Override
    public String toString()
    {
      final int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(size>(Integer.MAX_VALUE>>1))
          {
            throw new OutOfMemoryError();
          }
          final StringBuilder builder;
          final int rootOffset;
          OmniArray.OfRef.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new StringBuilder("["));
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
    public int hashCode()
    {
      final int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfRef.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
        return 1;
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
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
    public void clear()
    {
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=size,curr=curr.parent){}
        root.size=OmniArray.OfRef.removeRangeAndPullDown(root.arr,this.rootOffset,root.size,size);
        this.size=0;
      }
    }
  }
}
