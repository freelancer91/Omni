package omni.impl.seq;
import omni.util.BooleanSortUtil;
import omni.api.OmniList;
import omni.impl.BooleanDblLnkNode;
import java.util.function.Consumer;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import java.util.function.UnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractBooleanItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.BooleanComparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
import java.io.Externalizable;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollection;
import omni.util.ToStringUtil;
public abstract class BooleanDblLnkSeq extends AbstractSeq implements
   BooleanSubListDefault
{
  private static final long serialVersionUID=1L;
  transient BooleanDblLnkNode head;
  transient BooleanDblLnkNode tail;
  private  BooleanDblLnkSeq(){
  }
  private BooleanDblLnkSeq(BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  public void addLast(boolean val){
    BooleanDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new BooleanDblLnkNode(val);
    }else{
      tail.next=tail=new BooleanDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(boolean val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,boolean val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new BooleanDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        BooleanDblLnkNode before;
        (before=tail.prev).next=before=new BooleanDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      BooleanDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new BooleanDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new BooleanDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        BooleanDblLnkNode after;
        (after=head.next).prev=after=new BooleanDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private BooleanDblLnkNode getNode(int index,int size){
    int tailDist;
    if((tailDist=size-index)<index){
      for(var tail=this.tail;;tail=tail.prev){
        if(--tailDist==0){
          return tail;
        }
      }
    }else{
      for(var head=this.head;;--index,head=head.next){
        if(index==0){
          return head;
        }
      }
    }
  }
  @Override public boolean set(int index,boolean val){
    BooleanDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,boolean val){
    getNode(index,size).val=val;
  }
  @Override public boolean getBoolean(int index){
    return getNode(index,size).val;
  }
  @Override public boolean removeBooleanAt(int index){
    final boolean ret;
    int tailDist;
    if((tailDist=--this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==0){
        ret=tail.val;
        if(index==0){
          this.head=null;
          this.tail=null;
        }else{
          this.tail=tail=tail.prev;
          tail.next=null;
        }
      }else{
        ret=(tail=BooleanDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        BooleanDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=BooleanDblLnkNode.uncheckedIterateAscending(head,index)).val;
        BooleanDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(BooleanConsumer action){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      BooleanDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      BooleanDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public boolean[] toBooleanArray(){
    int size;
    if((size=this.size)!=0){
      final boolean[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new boolean[size],tail,size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override public Boolean[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Boolean[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new Boolean[size],tail,size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      final long[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size)!=0){
      final int[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new int[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    int size;
    if((size=this.size)!=0){
      final short[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new short[size],tail,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public byte[] toByteArray(){
    int size;
    if((size=this.size)!=0){
      final byte[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new byte[size],tail,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public char[] toCharArray(){
    int size;
    if((size=this.size)!=0){
      final char[] dst;
      BooleanDblLnkNode.uncheckedCopyInto(dst=new char[size],tail,size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override public void replaceAll(BooleanPredicate operator){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Boolean> operator){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      BooleanDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(BooleanComparator sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final boolean[] tmp;
      final BooleanDblLnkNode tail;
      BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            BooleanSortUtil.uncheckedSort(tmp,0,size,sorter);
          }
        }
      }
      BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Boolean> sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final boolean[] tmp;
      final BooleanDblLnkNode tail;
      BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            BooleanSortUtil.uncheckedSort(tmp,0,size,sorter::compare);
          }
        }
      }
      BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final boolean[] tmp;
      final BooleanDblLnkNode tail;
      BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      {
          BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
      }
      BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final boolean[] tmp;
      final BooleanDblLnkNode tail;
      BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
      {
          BooleanSortUtil.uncheckedDescendingSort(tmp,0,size);
      }
      BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
        [size=BooleanDblLnkNode.uncheckedToString(head,tail,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        BooleanDblLnkNode.uncheckedToString(head,tail,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public int hashCode(){
    final BooleanDblLnkNode head;
    if((head=this.head)!=null){
      return BooleanDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  private static class UncheckedSubList extends BooleanDblLnkSeq
  {
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int rootOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
    }
    private void bubbleUpClearAll(){
      for(var curr=parent;curr!=null;curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
    }
    //TODO serialization methods
    private void bubbleUpDecrementSize(int numRemoved)
    {
      var curr=this;
      do
      {
        curr.size-=numRemoved;
      }
      while((curr=curr.parent)!=null);
    }
    private void bubbleUpClearBody(BooleanDblLnkNode before,BooleanDblLnkNode head,int numRemoved,BooleanDblLnkNode tail,BooleanDblLnkNode after){
      for(var curr=parent;curr!=null;curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          while(curr.tail==tail){
            curr.tail=before;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }else if(curr.tail!=tail){
          do{
            curr.head=after;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }while(curr.head==head);
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(BooleanDblLnkNode tail, BooleanDblLnkNode after,int numRemoved){
      for(var curr=parent;curr!=null;curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.tail!=tail){
          do{
            curr.head=after;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void bubbleUpClearTail(BooleanDblLnkNode head, BooleanDblLnkNode before,int numRemoved){
      for(var curr=parent;curr!=null;curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          do{
            curr.tail=before;
            curr.size-=numRemoved;
          }
          while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        final UncheckedList root;
        (root=this.root).size-=size;
        BooleanDblLnkNode before,head,tail,after=(tail=this.tail).next;
        if((before=(head=this.head).prev)==null){
          if(after==null){
            bubbleUpClearAll();
            root.head=null;
            root.tail=null;
          }else{
            after.prev=null;
            bubbleUpClearHead(tail,after,size);
            root.head=after;
          }
        }else{
          before.next=after;
          if(after==null){
            bubbleUpClearTail(head,before,size);
            root.tail=before;
          }else{
            after.prev=before;
            bubbleUpClearBody(before,head,size,tail,after);
          }
        }
        this.head=null;
        this.tail=null;
        this.size=0;
      }
    }
  }
  private static class CheckedSubList extends BooleanDblLnkSeq
  {
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
     private CheckedSubList(CheckedList root,int rootOffset,BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      this.modCount=parent.modCount;
    }
    private void bubbleUpClearAll(){
      for(var curr=parent;curr!=null;++curr.modCount,curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
    }
    //TODO serialization methods
    private void bubbleUpDecrementSize(int numRemoved)
    {
      var curr=this;
      do
      {
        ++curr.modCount;
        curr.size-=numRemoved;
      }
      while((curr=curr.parent)!=null);
    }
    private void bubbleUpClearBody(BooleanDblLnkNode before,BooleanDblLnkNode head,int numRemoved,BooleanDblLnkNode tail,BooleanDblLnkNode after){
      for(var curr=parent;curr!=null;++curr.modCount,curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          while(curr.tail==tail){
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }else if(curr.tail!=tail){
          do{
            ++curr.modCount;
            curr.head=after;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }while(curr.head==head);
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(BooleanDblLnkNode tail, BooleanDblLnkNode after,int numRemoved){
      for(var curr=parent;curr!=null;++curr.modCount,curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.tail!=tail){
          do{
            ++curr.modCount;
            curr.head=after;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void bubbleUpClearTail(BooleanDblLnkNode head, BooleanDblLnkNode before,int numRemoved){
      for(var curr=parent;curr!=null;++curr.modCount,curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          do{
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
          }
          while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    @Override public void clear(){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        root.size-=size;
        BooleanDblLnkNode before,head,tail,after=(tail=this.tail).next;
        if((before=(head=this.head).prev)==null){
          if(after==null){
            bubbleUpClearAll();
            root.head=null;
            root.tail=null;
          }else{
            after.prev=null;
            bubbleUpClearHead(tail,after,size);
            root.head=after;
          }
        }else{
          before.next=after;
          if(after==null){
            bubbleUpClearTail(head,before,size);
            root.tail=before;
          }else{
            after.prev=before;
            bubbleUpClearBody(before,head,size,tail,after);
          }
        }
        this.modCount=modCount;
        this.head=null;
        this.tail=null;
        this.size=0;
      }
    }
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override public void replaceAll(BooleanPredicate operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final BooleanDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        BooleanDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(BooleanConsumer action){
      final int modCount=this.modCount;
      try{
        final BooleanDblLnkNode head;
        if((head=this.head)!=null){
          BooleanDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Boolean> operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final BooleanDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        BooleanDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      final int modCount=this.modCount;
      try{
        final BooleanDblLnkNode head;
        if((head=this.head)!=null){
          BooleanDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(BooleanComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              BooleanSortUtil.uncheckedSort(tmp,0,size,sorter);
            }
            finally
            {
              final CheckedList root;
              CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
              this.modCount=modCount;
            }
          }
        }
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Boolean> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              BooleanSortUtil.uncheckedSort(tmp,0,size,sorter::compare);
            }
            finally
            {
              final CheckedList root;
              CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
              this.modCount=modCount;
            }
          }
        }
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            BooleanSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
  }
  public static class CheckedList extends UncheckedList{
    transient int modCount;
    public CheckedList(){
    }
    CheckedList(BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void clear(){
      if(size!=0){
        ++this.modCount;
        this.size=0;
        this.head=null;
        this.tail=null;
      }
    }
    @Override public boolean set(int index,boolean val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      BooleanDblLnkNode tmp;
      final var ret=(tmp=((BooleanDblLnkSeq)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,boolean val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((BooleanDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public boolean getBoolean(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((BooleanDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public boolean removeBooleanAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final boolean ret;
      this.size=--size;
      if((size-=index)<=index){
        var tail=this.tail;
        if(size==0){
          ret=tail.val;
          if(index==0){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail=tail.prev;
            tail.next=null;
          }
        }else{
          ret=(tail=BooleanDblLnkNode.uncheckedIterateDescending(tail,size)).val;
          BooleanDblLnkNode.eraseNode(tail);
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          ret=(head=BooleanDblLnkNode.uncheckedIterateAscending(head,index)).val;
          BooleanDblLnkNode.eraseNode(head);
        }
      }
      return ret;
    }
    @Override public void add(int index,boolean val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=size+1;
      if((size-=index)<=index){
        var tail=this.tail;
        if(size==0){
          tail.next=tail=new BooleanDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          while(--size!=0){
            tail=tail.prev;
          }
          BooleanDblLnkNode before;
          (before=tail.prev).next=before=new BooleanDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        BooleanDblLnkNode head;
        if((head=this.head)==null){
          this.head=head=new BooleanDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          head.prev=head=new BooleanDblLnkNode(val,head);
          this.head=head;
        }else{
          while(--index!=0){
            head=head.next;
          }
          BooleanDblLnkNode after;
          (after=head.next).prev=after=new BooleanDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void forEach(BooleanConsumer action)
    {
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          BooleanDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action)
    {
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          BooleanDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public void replaceAll(BooleanPredicate operator){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          BooleanDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Boolean> operator){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          BooleanDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    private int removeIfHelper(BooleanDblLnkNode prev,int numLeft,boolean retainThis){
      int numSurvivors=1;
      outer:for(BooleanDblLnkNode next;--numLeft!=0;prev=next,++numSurvivors){
        if((next=prev.next).val^retainThis){
          do{
            if(--numLeft==0){
              prev.next=null;
              this.tail=prev;
              break outer;
            }
          }while((next=next.next).val^retainThis);
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(BooleanDblLnkNode prev,BooleanDblLnkNode head,int numLeft,boolean retainThis){
      int numSurvivors=0;
      outer:for(;;){
        if(--numLeft==0){
          prev.next=null;
          this.tail=prev;
          break;
        }
        if((head=head.next).val==retainThis){
          prev.next=head;
          head.prev=prev;
          do{
            ++numSurvivors;
            if(--numLeft==0){
              break outer;
            }
          }
          while((head=(prev=head).next).val==retainThis);
        }
      }
      return numSurvivors;
    }
    @Override boolean uncheckedRemoveIf(BooleanDblLnkNode head,BooleanPredicate filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        boolean firstVal;
        if(filter.test(firstVal=head.val)){
          while(--numLeft!=0){
            if((head=head.next).val^firstVal){
              if(filter.test(firstVal=!firstVal)){
                break;
              }
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
              head.prev=null;
              this.head=head;
              this.size=removeIfHelper(head,numLeft,firstVal);
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=null;
          this.tail=null;
          this.size=0;
          return true;
        }else{
          for(int numSurvivors=1;--numLeft!=0;++numSurvivors){
            BooleanDblLnkNode prev;
            if((head=(prev=head).next).val^firstVal){
              if(filter.test(!firstVal)){
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.modCount=modCount+1;
                this.size=numSurvivors+removeIfHelper(prev,head,numLeft,firstVal);
                return true;
              }
              break;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return false;
    }
    @Override public void sort(BooleanComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              BooleanSortUtil.uncheckedSort(tmp,0,size,sorter);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void sort(Comparator<? super Boolean> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              BooleanSortUtil.uncheckedSort(tmp,0,size,sorter::compare);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        {
            BooleanSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        ++this.modCount;
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final boolean[] tmp;
        final BooleanDblLnkNode tail;
        BooleanDblLnkNode.uncheckedCopyInto(tmp=new boolean[size],tail=this.tail,size);
        {
            BooleanSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        ++this.modCount;
        BooleanDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        BooleanDblLnkNode head,newTail;
        final var newHead=newTail=new BooleanDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new BooleanDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    private static class DescendingItr
      extends AbstractBooleanItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient BooleanDblLnkNode curr;
      transient BooleanDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,BooleanDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public boolean nextBoolean(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        BooleanDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(--parent.size==0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet==parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet==parent.head){
              parent.head=lastRet=lastRet.next;
              lastRet.prev=null;
            }else{
              BooleanDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            BooleanDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            BooleanDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfBoolean
    {
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,BooleanDblLnkNode curr,int currIndex){
        super(parent,curr,currIndex);
      }
      @Override public boolean hasPrevious(){
        return this.currIndex!=0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(boolean val){
        final BooleanDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(boolean val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        BooleanDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new BooleanDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new BooleanDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new BooleanDblLnkNode(val,newNode);
          }else{
            final BooleanDblLnkNode tmp;
            (newNode=curr).prev=newNode=new BooleanDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public boolean previousBoolean(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          final BooleanDblLnkNode curr;
          this.lastRet=curr=this.curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean nextBoolean(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        BooleanDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(lastRet.next==curr){
            --currIndex;
          }
          if(--parent.size==0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet==parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet==parent.head){
              parent.head=lastRet=lastRet.next;
              lastRet.prev=null;
            }else{
              BooleanDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            BooleanDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            BooleanDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((BooleanDblLnkSeq)this).getNode(index,size),index);
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public boolean getLastBoolean(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void addLast(boolean val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(boolean val){
      ++this.modCount;
      super.push(val);
    }
    @Override public boolean removeLastBoolean(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=tail.val;
        if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public boolean popBoolean(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=head.val;
        if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public boolean booleanElement(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
         return head.val;
      }
      throw new NoSuchElementException();
    }
    boolean uncheckedremoveLastOccurrence(BooleanDblLnkNode tail
    ,boolean val
    ){
      {
        if(val==(tail.val)){
          this.modCount=modCount+1;
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --this.size;
          return true;
        }
        for(BooleanDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(val==(tail.val)){
            this.modCount=modCount+1;
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveVal(BooleanDblLnkNode head
    ,boolean val
    ){
      {
        if(val==(head.val)){
          this.modCount=modCount+1;
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(BooleanDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(val==(head.val)){
            this.modCount=modCount+1;
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public boolean pollBoolean(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return false;
    }
    @Override public boolean pollLastBoolean(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return false;
    }
    @Override public Boolean poll(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return null;
    }
    @Override public Boolean pollLast(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToDouble(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToDouble(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToFloat(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToFloat(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToLong(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToLong(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(int)TypeUtil.castToByte(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(int)TypeUtil.castToByte(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short pollShort(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(short)TypeUtil.castToByte(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(short)TypeUtil.castToByte(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte pollByte(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToByte(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToByte(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToChar(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=TypeUtil.castToChar(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
  }
  public static class UncheckedList extends BooleanDblLnkSeq implements OmniDeque.OfBoolean,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(BooleanDblLnkNode head,int size,BooleanDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        for(int word=TypeUtil.castToByte(curr.val),marker=1;;){
          if((curr=curr.next)==null){
            out.writeByte(word);
            return;
          }else if((marker<<=1)==(1<<8)){
            out.writeByte(word);
            word=0;
            marker=1;
          }
          if(curr.val){
            word|=marker;
          }
        }
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        BooleanDblLnkNode curr;
        int word,marker;
        for(this.head=curr=new BooleanDblLnkNode(((marker=1)&(word=in.readUnsignedByte()))!=0);--size!=0;curr=curr.next=new BooleanDblLnkNode(curr,(word&marker)!=0)){
          if((marker<<=1)==(1<<8)){
            word=in.readUnsignedByte();
            marker=1;
          }
        }
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        BooleanDblLnkNode head,newTail;
        final var newHead=newTail=new BooleanDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new BooleanDblLnkNode(newTail,(head=head.next).val),++i){}
        return new UncheckedList(newHead,size,newTail);
      }
      return new UncheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public boolean contains(boolean val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            return BooleanDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return BooleanDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return BooleanDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedcontains(head,tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            return BooleanDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return BooleanDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return BooleanDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedindexOf(head,tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedlastIndexOf(size,tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(BooleanDblLnkNode head
    ,boolean val
    ){
      {
        if(val==(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(BooleanDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(val==(head.val)){
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      return new DescendingItr(this);
    }
    private static class AscendingItr
      extends AbstractBooleanItr
    {
      transient final UncheckedList parent;
      transient BooleanDblLnkNode curr;
      private AscendingItr(UncheckedList parent,BooleanDblLnkNode curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedList parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public void remove(){
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          BooleanDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            BooleanDblLnkNode lastRet;
            if((lastRet=curr.prev)==parent.head){
              parent.head=curr;
              curr.prev=null;
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
            }
          }
        }
      }
      @Override public boolean nextBoolean(){
        final BooleanDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          BooleanDblLnkNode.uncheckedForEachAscending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          BooleanDblLnkNode.uncheckedForEachAscending(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class DescendingItr extends AscendingItr{
      private DescendingItr(UncheckedList parent){
        super(parent,parent.tail);
      }
      @Override public void remove(){
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          BooleanDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            BooleanDblLnkNode lastRet;
            if((lastRet=curr.next)==parent.tail){
              parent.tail=curr;
              curr.next=null;
            }else{
              curr.next=lastRet=lastRet.next;
              lastRet.prev=curr;
            }
          }
        }
      }
      @Override public boolean nextBoolean(){
        final BooleanDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          BooleanDblLnkNode.uncheckedForEachDescending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          BooleanDblLnkNode.uncheckedForEachDescending(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfBoolean{
      transient int currIndex;
      transient BooleanDblLnkNode lastRet;
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,BooleanDblLnkNode curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public boolean hasPrevious(){
        return curr.prev!=null;
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public int previousIndex(){
        return currIndex-1;
      }
      @Override public void add(boolean val){
        final UncheckedList parent;
        BooleanDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new BooleanDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new BooleanDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new BooleanDblLnkNode(val,newNode);
          }else{
            final BooleanDblLnkNode tmp;
            (newNode=curr).prev=newNode=new BooleanDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(boolean val){
        lastRet.val=val;
      }
      @Override public boolean previousBoolean(){
        final BooleanDblLnkNode curr;
        this.lastRet=curr=this.curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public boolean nextBoolean(){
        final BooleanDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public void remove(){
        BooleanDblLnkNode lastRet;
        if((lastRet=this.lastRet).next==curr){
          --currIndex;
        }
        final UncheckedList parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          if(lastRet==parent.tail){
            parent.tail=lastRet=lastRet.prev;
            lastRet.next=null;
          }else if(lastRet==parent.head){
            parent.head=lastRet=lastRet.next;
            lastRet.prev=null;
          }else{
            BooleanDblLnkNode.eraseNode(lastRet);
          }
        }
        this.lastRet=null;
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          BooleanDblLnkNode.uncheckedForEachAscending(curr,action);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final BooleanDblLnkNode curr;
        if((curr=this.curr)!=null){
          BooleanDblLnkNode.uncheckedForEachAscending(curr,action::accept);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfBoolean listIterator(int index){
      return new BidirectionalItr(this,((BooleanDblLnkSeq)this).getNode(index,this.size),index);
    }
    @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public boolean getLastBoolean(){
      return tail.val;
    }
    @Override public boolean offerFirst(boolean val){
      push((boolean)val);
      return true;
    }
    @Override public boolean offerLast(boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public void addFirst(boolean val){
      push((boolean)val);
    }
    @Override public boolean removeFirstBoolean(){
      return popBoolean();
    }
    @Override public void push(boolean val){
      BooleanDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new BooleanDblLnkNode(val);
      }else{
        head.prev=head=new BooleanDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public boolean removeLastBoolean(){
      BooleanDblLnkNode tail;
      final var ret=(tail=this.tail).val;
      if(--size==0){
        this.head=null;
        this.tail=null;
      }else{
        (tail=tail.prev).next=null;
        this.tail=tail;
      }
      return ret;
    }
    @Override public boolean popBoolean(){
      BooleanDblLnkNode head;
      final var ret=(head=this.head).val;
      if(--size==0){
        this.head=null;
        this.tail=null;
      }else{
        (head=head.next).prev=null;
        this.head=head;
      }
      return ret;
    }
    @Override public boolean removeFirstOccurrence(Object val){
      return remove(val);
    }
    @Override public boolean booleanElement(){
      return head.val;
    }
    @Override public boolean offer(boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            return BooleanDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return BooleanDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return BooleanDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final BooleanDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return BooleanDblLnkNode.uncheckedsearch(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(double val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final BooleanDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(tail,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(BooleanDblLnkNode tail
    ,boolean val
    ){
      {
        if(val==(tail.val)){
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --this.size;
          return true;
        }
        for(BooleanDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(val==(tail.val)){
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public Boolean peekFirst(){
      return peek();
    }
    @Override public Boolean pollFirst(){
      return poll();
    }
    @Override public Boolean pop(){
      return popBoolean();
    }
    @Override public Boolean remove(){
      return popBoolean();
    }
    @Override public boolean offer(Boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public Boolean element(){
      return booleanElement();
    }
    @Override public Boolean removeFirst(){
      return popBoolean();
    }
    @Override public Boolean removeLast(){
      return removeLastBoolean();
    }
    @Override public boolean offerFirst(Boolean val){
      push((boolean)val);
      return true;
    }
    @Override public boolean offerLast(Boolean val){
      addLast((boolean)val);
      return true;
    }
    @Override public void push(Boolean val){
      push((boolean)val);
    }
    @Override public void addFirst(Boolean val){
      push((boolean)val);
    }
    @Override public void addLast(Boolean val){
      addLast((boolean)val);
    }
    @Override public Boolean getFirst(){
      return booleanElement();
    }
    @Override public Boolean getLast(){
      return getLastBoolean();
    }
    @Override public boolean pollBoolean(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return false;
    }
    @Override public boolean pollLastBoolean(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return false;
    }
    @Override public Boolean poll(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return null;
    }
    @Override public Boolean pollLast(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToDouble(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToDouble(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToFloat(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToFloat(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToLong(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToLong(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)TypeUtil.castToByte(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(int)TypeUtil.castToByte(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short pollShort(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(short)TypeUtil.castToByte(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(short)TypeUtil.castToByte(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte pollByte(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToByte(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToByte(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      BooleanDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToChar(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=TypeUtil.castToChar(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public boolean peekBoolean(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return false;
    }
    @Override public boolean peekLastBoolean(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return false;
    }
    @Override public Boolean peek(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Boolean peekLast(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToDouble(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToDouble(tail.val);
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToFloat(head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToFloat(tail.val);
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToLong(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToLong(tail.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return (int)TypeUtil.castToByte(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int peekLastInt(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (int)TypeUtil.castToByte(tail.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public short peekShort(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return (short)TypeUtil.castToByte(head.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short peekLastShort(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (short)TypeUtil.castToByte(tail.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public byte peekByte(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToByte(head.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte peekLastByte(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToByte(tail.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public char peekChar(){
      final BooleanDblLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToChar(head.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public char peekLastChar(){
      final BooleanDblLnkNode tail;
      if((tail=this.tail)!=null){
        return TypeUtil.castToChar(tail.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final BooleanDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final BooleanDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(BooleanDblLnkNode prev,BooleanDblLnkNode tail,boolean retainThis){
      int numSurvivors=1;
      outer:for(BooleanDblLnkNode next;prev!=tail;++numSurvivors,prev=next){
        if((next=prev.next).val^retainThis){
          do{
            if(next==tail){
              this.tail=prev;
              prev.next=null;
              break outer;
            }
          }
          while((next=next.next).val^retainThis);
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(BooleanDblLnkNode prev,BooleanDblLnkNode curr,BooleanDblLnkNode tail,boolean retainThis){
      int numSurvivors=0;
      while(curr!=tail) {
        if((curr=curr.next).val==retainThis){
          prev.next=curr;
          curr.prev=prev;
          do{
            ++numSurvivors;
            if(curr==tail){
              return numSurvivors;
            }
          }while((curr=(prev=curr).next).val==retainThis);
        }
      }
      prev.next=null;
      this.tail=prev;
      return numSurvivors;
    }
    boolean uncheckedRemoveIf(BooleanDblLnkNode head,BooleanPredicate filter){
      boolean firstVal;
      if(filter.test(firstVal=head.val)){
        for(var tail=this.tail;head!=tail;){
          if((head=head.next).val^firstVal){
            if(filter.test(firstVal=!firstVal)){
              break;
            }
            head.prev=null;
            this.head=head;
            this.size=removeIfHelper(head,tail,firstVal);
            return true;
          }
        }
        this.head=null;
        this.tail=null;
        this.size=0;
        return true;
      }else{
        int numSurvivors=1;
        for(final var tail=this.tail;head!=tail;++numSurvivors){
          final BooleanDblLnkNode prev;
          if((head=(prev=head).next).val^firstVal){
            if(filter.test(!firstVal)){
              this.size=numSurvivors+removeIfHelper(prev,head,tail,firstVal);
              return true;
            }
            break;
          }
        }
        return false;
      }
    }
  }
}
