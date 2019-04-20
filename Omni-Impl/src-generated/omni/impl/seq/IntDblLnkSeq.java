package omni.impl.seq;
import omni.util.IntSortUtil;
import omni.api.OmniList;
import omni.impl.IntDblLnkNode;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.UnaryOperator;
import java.util.function.IntUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractIntItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import java.util.function.IntBinaryOperator;
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
public abstract class IntDblLnkSeq extends AbstractSeq implements
   IntSubListDefault
{
  private static final long serialVersionUID=1L;
  transient IntDblLnkNode head;
  transient IntDblLnkNode tail;
  private  IntDblLnkSeq(){
  }
  private IntDblLnkSeq(IntDblLnkNode head,int size,IntDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  private static  int markSurvivors(IntDblLnkNode curr,IntPredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test(curr.val)){
          word|=marker;
          ++numSurvivors;
        }
        if((curr=curr.next)==null){
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
      }while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  private static  long markSurvivors(IntDblLnkNode curr,IntPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  public void addLast(int val){
    IntDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new IntDblLnkNode(val);
    }else{
      tail.next=tail=new IntDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(int val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,int val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new IntDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        IntDblLnkNode before;
        (before=tail.prev).next=before=new IntDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      IntDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new IntDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new IntDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        IntDblLnkNode after;
        (after=head.next).prev=after=new IntDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private IntDblLnkNode getNode(int index,int size){
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
  @Override public int set(int index,int val){
    IntDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,int val){
    getNode(index,size).val=val;
  }
  @Override public int getInt(int index){
    return getNode(index,size).val;
  }
  @Override public int removeIntAt(int index){
    final int ret;
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
        ret=(tail=IntDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        IntDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=IntDblLnkNode.uncheckedIterateAscending(head,index)).val;
        IntDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(IntConsumer action){
    final IntDblLnkNode head;
    if((head=this.head)!=null){
      IntDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Integer> action){
    final IntDblLnkNode head;
    if((head=this.head)!=null){
      IntDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      IntDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      IntDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size)!=0){
      final int[] dst;
      IntDblLnkNode.uncheckedCopyInto(dst=new int[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public Integer[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Integer[] dst;
      IntDblLnkNode.uncheckedCopyInto(dst=new Integer[size],tail,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      IntDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      IntDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      final long[] dst;
      IntDblLnkNode.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public void replaceAll(IntUnaryOperator operator){
    final IntDblLnkNode head;
    if((head=this.head)!=null){
      IntDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Integer> operator){
    final IntDblLnkNode head;
    if((head=this.head)!=null){
      IntDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(IntBinaryOperator sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int[] tmp;
      final IntDblLnkNode tail;
      IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          IntSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            IntSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }
      }
      IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Integer> sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int[] tmp;
      final IntDblLnkNode tail;
      IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          IntSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            IntSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }
        }
      }
      IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int[] tmp;
      final IntDblLnkNode tail;
      IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
      {
          IntSortUtil.uncheckedAscendingSort(tmp,0,size);
      }
      IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int[] tmp;
      final IntDblLnkNode tail;
      IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
      {
          IntSortUtil.uncheckedDescendingSort(tmp,0,size);
      }
      IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(IntBinaryOperator sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int[] tmp;
      final IntDblLnkNode tail;
      IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          IntSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            IntSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }
      }
      IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final IntDblLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/13)){(buffer=new byte[size*13])
        [size=IntDblLnkNode.uncheckedToString(head,tail,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        IntDblLnkNode.uncheckedToString(head,tail,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public int hashCode(){
    final IntDblLnkNode head;
    if((head=this.head)!=null){
      return IntDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  private static class UncheckedSubList extends IntDblLnkSeq
  {
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int rootOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,IntDblLnkNode head,int size,IntDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,IntDblLnkNode head,int size,IntDblLnkNode tail){
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
    private void bubbleUpClearBody(IntDblLnkNode before,IntDblLnkNode head,int numRemoved,IntDblLnkNode tail,IntDblLnkNode after){
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
    private void bubbleUpClearHead(IntDblLnkNode tail, IntDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(IntDblLnkNode head, IntDblLnkNode before,int numRemoved){
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
        IntDblLnkNode before,head,tail,after=(tail=this.tail).next;
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
  private static class CheckedSubList extends IntDblLnkSeq
  {
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
     private CheckedSubList(CheckedList root,int rootOffset,IntDblLnkNode head,int size,IntDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,IntDblLnkNode head,int size,IntDblLnkNode tail){
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
    private void bubbleUpClearBody(IntDblLnkNode before,IntDblLnkNode head,int numRemoved,IntDblLnkNode tail,IntDblLnkNode after){
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
    private void bubbleUpClearHead(IntDblLnkNode tail, IntDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(IntDblLnkNode head, IntDblLnkNode before,int numRemoved){
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
        IntDblLnkNode before,head,tail,after=(tail=this.tail).next;
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
    @Override public void replaceAll(IntUnaryOperator operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final IntDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        IntDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(IntConsumer action){
      final int modCount=this.modCount;
      try{
        final IntDblLnkNode head;
        if((head=this.head)!=null){
          IntDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Integer> operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final IntDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        IntDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(Consumer<? super Integer> action){
      final int modCount=this.modCount;
      try{
        final IntDblLnkNode head;
        if((head=this.head)!=null){
          IntDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(IntBinaryOperator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final int[] tmp;
        final IntDblLnkNode tail;
        IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            IntSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              IntSortUtil.uncheckedStableSort(tmp,0,size,sorter);
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
        IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Integer> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final int[] tmp;
        final IntDblLnkNode tail;
        IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            IntSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              IntSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
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
        IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final int[] tmp;
        final IntDblLnkNode tail;
        IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            IntSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final int[] tmp;
        final IntDblLnkNode tail;
        IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            IntSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(IntBinaryOperator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final int[] tmp;
        final IntDblLnkNode tail;
        IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            IntSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              IntSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
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
        IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
    CheckedList(IntDblLnkNode head,int size,IntDblLnkNode tail){
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
    @Override public int set(int index,int val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      IntDblLnkNode tmp;
      final var ret=(tmp=((IntDblLnkSeq)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,int val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((IntDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public int getInt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((IntDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public int removeIntAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final int ret;
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
          ret=(tail=IntDblLnkNode.uncheckedIterateDescending(tail,size)).val;
          IntDblLnkNode.eraseNode(tail);
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          ret=(head=IntDblLnkNode.uncheckedIterateAscending(head,index)).val;
          IntDblLnkNode.eraseNode(head);
        }
      }
      return ret;
    }
    @Override public void add(int index,int val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=size+1;
      if((size-=index)<=index){
        var tail=this.tail;
        if(size==0){
          tail.next=tail=new IntDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          while(--size!=0){
            tail=tail.prev;
          }
          IntDblLnkNode before;
          (before=tail.prev).next=before=new IntDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        IntDblLnkNode head;
        if((head=this.head)==null){
          this.head=head=new IntDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          head.prev=head=new IntDblLnkNode(val,head);
          this.head=head;
        }else{
          while(--index!=0){
            head=head.next;
          }
          IntDblLnkNode after;
          (after=head.next).prev=after=new IntDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void forEach(IntConsumer action)
    {
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          IntDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Integer> action)
    {
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          IntDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
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
    @Override public void replaceAll(IntUnaryOperator operator){
      final IntDblLnkNode head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          IntDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Integer> operator){
      final IntDblLnkNode head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          IntDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    private void pullSurvivorsDown(IntDblLnkNode prev,IntPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              if(curr==tail){
                prev.next=null;
                this.tail=prev;
              }else{
                prev.next=curr=curr.next;
                curr.prev=prev;
              }
              return;
            }else if((marker<<=1)==0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          this.tail=curr;
          curr.next=null;
          return;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private void pullSurvivorsDown(IntDblLnkNode prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              if(curr==tail){
                prev.next=null;
                this.tail=prev;
              }else{
                prev.next=curr=curr.next;
                curr.prev=prev;
              }
              return;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          this.tail=curr;
          curr.next=null;
          return;
        }
        prev=curr;
      }
    }
    private int removeIfHelper(IntDblLnkNode prev,IntPredicate filter,int numLeft,int modCount){
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(prev.next,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(prev,filter,survivorSet,numSurvivors,numLeft);
          }
        }else{
          long survivorWord=markSurvivors(prev.next,filter);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            pullSurvivorsDown(prev,survivorWord,numSurvivors,numLeft);
          }
        }
        return numSurvivors;
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return 0;
    }
    @Override boolean uncheckedRemoveIf(IntDblLnkNode head,IntPredicate filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        if(filter.test(head.val)){
          while(--numLeft!=0){
            if(!filter.test((head=head.next).val)){
              this.size=1+removeIfHelper(head,filter,--numLeft,modCount);
              this.modCount=modCount+1;
              this.head=head;
              head.prev=null;
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
          int numSurvivors;
          if(--numLeft!=(numSurvivors=removeIfHelper(head,filter,numLeft,modCount))){
            this.modCount=modCount+1;
            this.size=1+numSurvivors;
            return true;
          }
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return false;
    }
    @Override public void sort(IntBinaryOperator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final int[] tmp;
        final IntDblLnkNode tail;
        IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            IntSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              IntSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void sort(Comparator<? super Integer> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final int[] tmp;
        final IntDblLnkNode tail;
        IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            IntSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              IntSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final int[] tmp;
        final IntDblLnkNode tail;
        IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
        {
            IntSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        ++this.modCount;
        IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final int[] tmp;
        final IntDblLnkNode tail;
        IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
        {
            IntSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        ++this.modCount;
        IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(IntBinaryOperator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final int[] tmp;
        final IntDblLnkNode tail;
        IntDblLnkNode.uncheckedCopyInto(tmp=new int[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            IntSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              IntSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        IntDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        IntDblLnkNode head,newTail;
        final var newHead=newTail=new IntDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new IntDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    private static class DescendingItr
      extends AbstractIntItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient IntDblLnkNode curr;
      transient IntDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,IntDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public int nextInt(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final IntDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        IntDblLnkNode lastRet;
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
              IntDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(IntConsumer action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            IntDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            IntDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfInt
    {
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,IntDblLnkNode curr,int currIndex){
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
      @Override public void set(int val){
        final IntDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(int val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        IntDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new IntDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new IntDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new IntDblLnkNode(val,newNode);
          }else{
            final IntDblLnkNode tmp;
            (newNode=curr).prev=newNode=new IntDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public int previousInt(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          final IntDblLnkNode curr;
          this.lastRet=curr=this.curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public int nextInt(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final IntDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        IntDblLnkNode lastRet;
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
              IntDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(IntConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            IntDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            IntDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
    @Override public OmniIterator.OfInt descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfInt iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfInt listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfInt listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((IntDblLnkSeq)this).getNode(index,size),index);
    }
    @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public int getLastInt(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void addLast(int val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(int val){
      ++this.modCount;
      super.push(val);
    }
    @Override public int removeLastInt(){
      IntDblLnkNode tail;
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
    @Override public int popInt(){
      IntDblLnkNode head;
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
    @Override public int intElement(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
         return head.val;
      }
      throw new NoSuchElementException();
    }
    boolean uncheckedremoveLastOccurrence(IntDblLnkNode tail
    ,int val
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
        for(IntDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveVal(IntDblLnkNode head
    ,int val
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
        for(IntDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    @Override public int pollInt(){
      IntDblLnkNode head;
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
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      IntDblLnkNode tail;
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
      return Integer.MIN_VALUE;
    }
    @Override public Integer poll(){
      IntDblLnkNode head;
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
    @Override public Integer pollLast(){
      IntDblLnkNode tail;
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
      IntDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(double)(head.val);
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
      IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(double)(tail.val);
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
      IntDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(float)(head.val);
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
      IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(float)(tail.val);
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
      IntDblLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(long)(head.val);
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
      IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(long)(tail.val);
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
  }
  public static class UncheckedList extends IntDblLnkSeq implements OmniDeque.OfInt,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(IntDblLnkNode head,int size,IntDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        do{
          out.writeInt(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        IntDblLnkNode curr;
        for(this.head=curr=new IntDblLnkNode((int)in.readInt());--size!=0;curr=curr.next=new IntDblLnkNode(curr,(int)in.readInt())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        IntDblLnkNode head,newTail;
        final var newHead=newTail=new IntDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new IntDblLnkNode(newTail,(head=head.next).val),++i){}
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
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedcontains(head,tail,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((v=(int)val)==val){
              return IntDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return IntDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return IntDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return IntDblLnkNode.uncheckedcontains(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedindexOf(head,tail,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((v=(int)val)==val){
              return IntDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return IntDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return IntDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return IntDblLnkNode.uncheckedindexOf(head,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(byte val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return IntDblLnkNode.uncheckedlastIndexOf(size,tail,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return IntDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if((v=(int)val)==val){
              return IntDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return IntDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return IntDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return IntDblLnkNode.uncheckedlastIndexOf(size,tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(byte val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return IntDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return IntDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((v=(int)val)==val){
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
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
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
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if(val==(v=(int)val))
            {
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
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(IntDblLnkNode head
    ,int val
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
        for(IntDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    @Override public OmniIterator.OfInt descendingIterator(){
      return new DescendingItr(this);
    }
    private static class AscendingItr
      extends AbstractIntItr
    {
      transient final UncheckedList parent;
      transient IntDblLnkNode curr;
      private AscendingItr(UncheckedList parent,IntDblLnkNode curr){
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
          IntDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            IntDblLnkNode lastRet;
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
      @Override public int nextInt(){
        final IntDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      @Override public void forEachRemaining(IntConsumer action){
        final IntDblLnkNode curr;
        if((curr=this.curr)!=null){
          IntDblLnkNode.uncheckedForEachAscending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final IntDblLnkNode curr;
        if((curr=this.curr)!=null){
          IntDblLnkNode.uncheckedForEachAscending(curr,action::accept);
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
          IntDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            IntDblLnkNode lastRet;
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
      @Override public int nextInt(){
        final IntDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override public void forEachRemaining(IntConsumer action){
        final IntDblLnkNode curr;
        if((curr=this.curr)!=null){
          IntDblLnkNode.uncheckedForEachDescending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final IntDblLnkNode curr;
        if((curr=this.curr)!=null){
          IntDblLnkNode.uncheckedForEachDescending(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfInt{
      transient int currIndex;
      transient IntDblLnkNode lastRet;
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,IntDblLnkNode curr,int currIndex){
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
      @Override public void add(int val){
        final UncheckedList parent;
        IntDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new IntDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new IntDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new IntDblLnkNode(val,newNode);
          }else{
            final IntDblLnkNode tmp;
            (newNode=curr).prev=newNode=new IntDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(int val){
        lastRet.val=val;
      }
      @Override public int previousInt(){
        final IntDblLnkNode curr;
        this.lastRet=curr=this.curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public int nextInt(){
        final IntDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public void remove(){
        IntDblLnkNode lastRet;
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
            IntDblLnkNode.eraseNode(lastRet);
          }
        }
        this.lastRet=null;
      }
      @Override public void forEachRemaining(IntConsumer action){
        final IntDblLnkNode curr;
        if((curr=this.curr)!=null){
          IntDblLnkNode.uncheckedForEachAscending(curr,action);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final IntDblLnkNode curr;
        if((curr=this.curr)!=null){
          IntDblLnkNode.uncheckedForEachAscending(curr,action::accept);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
    }
    @Override public OmniIterator.OfInt iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfInt listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfInt listIterator(int index){
      return new BidirectionalItr(this,((IntDblLnkSeq)this).getNode(index,this.size),index);
    }
    @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public int getLastInt(){
      return tail.val;
    }
    @Override public boolean offerFirst(int val){
      push((int)val);
      return true;
    }
    @Override public boolean offerLast(int val){
      addLast((int)val);
      return true;
    }
    @Override public void addFirst(int val){
      push((int)val);
    }
    @Override public int removeFirstInt(){
      return popInt();
    }
    @Override public void push(int val){
      IntDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new IntDblLnkNode(val);
      }else{
        head.prev=head=new IntDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public int removeLastInt(){
      IntDblLnkNode tail;
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
    @Override public int popInt(){
      IntDblLnkNode head;
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
    @Override public int intElement(){
      return head.val;
    }
    @Override public boolean offer(int val){
      addLast((int)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedsearch(head,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((v=(int)val)==val){
              return IntDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return IntDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return IntDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return IntDblLnkNode.uncheckedsearch(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final IntDblLnkNode head;
          if((head=this.head)!=null)
          {
            return IntDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if((v=(int)val)==val){
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
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
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
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final int v;
            if(val==(v=(int)val))
            {
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
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(tail,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(byte val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(char val){
      {
        {
          final IntDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(IntDblLnkNode tail
    ,int val
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
        for(IntDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    @Override public Integer peekFirst(){
      return peek();
    }
    @Override public Integer pollFirst(){
      return poll();
    }
    @Override public Integer pop(){
      return popInt();
    }
    @Override public Integer remove(){
      return popInt();
    }
    @Override public boolean offer(Integer val){
      addLast((int)val);
      return true;
    }
    @Override public Integer element(){
      return intElement();
    }
    @Override public Integer removeFirst(){
      return popInt();
    }
    @Override public Integer removeLast(){
      return removeLastInt();
    }
    @Override public boolean offerFirst(Integer val){
      push((int)val);
      return true;
    }
    @Override public boolean offerLast(Integer val){
      addLast((int)val);
      return true;
    }
    @Override public void push(Integer val){
      push((int)val);
    }
    @Override public void addFirst(Integer val){
      push((int)val);
    }
    @Override public void addLast(Integer val){
      addLast((int)val);
    }
    @Override public Integer getFirst(){
      return intElement();
    }
    @Override public Integer getLast(){
      return getLastInt();
    }
    @Override public int pollInt(){
      IntDblLnkNode head;
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
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      IntDblLnkNode tail;
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
      return Integer.MIN_VALUE;
    }
    @Override public Integer poll(){
      IntDblLnkNode head;
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
    @Override public Integer pollLast(){
      IntDblLnkNode tail;
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
      IntDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
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
      IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(double)(tail.val);
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
      IntDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
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
      IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(float)(tail.val);
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
      IntDblLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
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
      IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        final var ret=(long)(tail.val);
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
    @Override public int peekInt(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int peekLastInt(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public Integer peek(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Integer peekLast(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (double)(tail.val);
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (float)(tail.val);
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final IntDblLnkNode head;
      if((head=this.head)!=null){
        return (long)(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final IntDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (long)(tail.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public boolean removeIf(IntPredicate filter){
      final IntDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Integer> filter){
      final IntDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(IntDblLnkNode prev,IntDblLnkNode tail,IntPredicate filter){
      int numSurvivors=1;
      outer:for(IntDblLnkNode next;prev!=tail;++numSurvivors,prev=next){
        if(filter.test((next=prev.next).val)){
          do{
            if(next==tail){
              this.tail=prev;
              prev.next=null;
              break outer;
            }
          }
          while(filter.test((next=next.next).val));
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(IntDblLnkNode prev,IntDblLnkNode curr,IntDblLnkNode tail,IntPredicate filter){
      int numSurvivors=0;
      while(curr!=tail) {
        if(!filter.test((curr=curr.next).val)){
          prev.next=curr;
          curr.prev=prev;
          do{
            ++numSurvivors;
            if(curr==tail){
              return numSurvivors;
            }
          }while(!filter.test((curr=(prev=curr).next).val));
        }
      }
      prev.next=null;
      this.tail=prev;
      return numSurvivors;
    }
    boolean uncheckedRemoveIf(IntDblLnkNode head,IntPredicate filter){
      if(filter.test(head.val)){
        for(var tail=this.tail;head!=tail;){
          if(!filter.test((head=head.next).val)){
            this.size=removeIfHelper(head,tail,filter);
            head.prev=null;
            this.head=head;
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
          final IntDblLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
  }
}
