package omni.impl.seq;
import omni.util.LongSortUtil;
import omni.api.OmniList;
import omni.impl.LongDblLnkNode;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.UnaryOperator;
import java.util.function.LongUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractLongItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.LongComparator;
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
public abstract class LongDblLnkSeq extends AbstractSeq implements
   LongSubListDefault
{
  private static final long serialVersionUID=1L;
  transient LongDblLnkNode head;
  transient LongDblLnkNode tail;
  private  LongDblLnkSeq(){
  }
  private LongDblLnkSeq(LongDblLnkNode head,int size,LongDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  private static  int markSurvivors(LongDblLnkNode curr,LongPredicate filter,long[] survivorSet){
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
  private static  long markSurvivors(LongDblLnkNode curr,LongPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  public void addLast(long val){
    LongDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new LongDblLnkNode(val);
    }else{
      tail.next=tail=new LongDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(long val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,long val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new LongDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        LongDblLnkNode before;
        (before=tail.prev).next=before=new LongDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      LongDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new LongDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new LongDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        LongDblLnkNode after;
        (after=head.next).prev=after=new LongDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private LongDblLnkNode getNode(int index,int size){
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
  @Override public long set(int index,long val){
    LongDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,long val){
    getNode(index,size).val=val;
  }
  @Override public long getLong(int index){
    return getNode(index,size).val;
  }
  @Override public long removeLongAt(int index){
    final long ret;
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
        ret=(tail=LongDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        LongDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=LongDblLnkNode.uncheckedIterateAscending(head,index)).val;
        LongDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(LongConsumer action){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Long> action){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      LongDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      LongDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      final long[] dst;
      LongDblLnkNode.uncheckedCopyInto(dst=new long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public Long[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Long[] dst;
      LongDblLnkNode.uncheckedCopyInto(dst=new Long[size],tail,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      LongDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      LongDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public void replaceAll(LongUnaryOperator operator){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Long> operator){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      LongDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(LongComparator sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final long[] tmp;
      final LongDblLnkNode tail;
      LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          LongSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            LongSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }
      }
      LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Long> sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final long[] tmp;
      final LongDblLnkNode tail;
      LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          LongSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            LongSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }
        }
      }
      LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final long[] tmp;
      final LongDblLnkNode tail;
      LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
      {
          LongSortUtil.uncheckedAscendingSort(tmp,0,size);
      }
      LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final long[] tmp;
      final LongDblLnkNode tail;
      LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
      {
          LongSortUtil.uncheckedDescendingSort(tmp,0,size);
      }
      LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(LongComparator sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final long[] tmp;
      final LongDblLnkNode tail;
      LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          LongSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            LongSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }
      }
      LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/22)){(buffer=new byte[size*22])
        [size=LongDblLnkNode.uncheckedToString(head,tail,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        LongDblLnkNode.uncheckedToString(head,tail,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public int hashCode(){
    final LongDblLnkNode head;
    if((head=this.head)!=null){
      return LongDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  private static class UncheckedSubList extends LongDblLnkSeq
  {
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int rootOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,LongDblLnkNode head,int size,LongDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,LongDblLnkNode head,int size,LongDblLnkNode tail){
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
    private void bubbleUpClearBody(LongDblLnkNode before,LongDblLnkNode head,int numRemoved,LongDblLnkNode tail,LongDblLnkNode after){
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
    private void bubbleUpClearHead(LongDblLnkNode tail, LongDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(LongDblLnkNode head, LongDblLnkNode before,int numRemoved){
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
        LongDblLnkNode before,head,tail,after=(tail=this.tail).next;
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
  private static class CheckedSubList extends LongDblLnkSeq
  {
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
     private CheckedSubList(CheckedList root,int rootOffset,LongDblLnkNode head,int size,LongDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,LongDblLnkNode head,int size,LongDblLnkNode tail){
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
    private void bubbleUpClearBody(LongDblLnkNode before,LongDblLnkNode head,int numRemoved,LongDblLnkNode tail,LongDblLnkNode after){
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
    private void bubbleUpClearHead(LongDblLnkNode tail, LongDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(LongDblLnkNode head, LongDblLnkNode before,int numRemoved){
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
        LongDblLnkNode before,head,tail,after=(tail=this.tail).next;
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
    @Override public void replaceAll(LongUnaryOperator operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final LongDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        LongDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(LongConsumer action){
      final int modCount=this.modCount;
      try{
        final LongDblLnkNode head;
        if((head=this.head)!=null){
          LongDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Long> operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final LongDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        LongDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(Consumer<? super Long> action){
      final int modCount=this.modCount;
      try{
        final LongDblLnkNode head;
        if((head=this.head)!=null){
          LongDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(LongComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedStableSort(tmp,0,size,sorter);
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
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Long> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
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
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            LongSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(LongComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
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
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
    CheckedList(LongDblLnkNode head,int size,LongDblLnkNode tail){
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
    @Override public long set(int index,long val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      LongDblLnkNode tmp;
      final var ret=(tmp=((LongDblLnkSeq)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,long val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((LongDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public long getLong(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((LongDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public long removeLongAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final long ret;
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
          ret=(tail=LongDblLnkNode.uncheckedIterateDescending(tail,size)).val;
          LongDblLnkNode.eraseNode(tail);
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          ret=(head=LongDblLnkNode.uncheckedIterateAscending(head,index)).val;
          LongDblLnkNode.eraseNode(head);
        }
      }
      return ret;
    }
    @Override public void add(int index,long val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=size+1;
      if((size-=index)<=index){
        var tail=this.tail;
        if(size==0){
          tail.next=tail=new LongDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          while(--size!=0){
            tail=tail.prev;
          }
          LongDblLnkNode before;
          (before=tail.prev).next=before=new LongDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        LongDblLnkNode head;
        if((head=this.head)==null){
          this.head=head=new LongDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          head.prev=head=new LongDblLnkNode(val,head);
          this.head=head;
        }else{
          while(--index!=0){
            head=head.next;
          }
          LongDblLnkNode after;
          (after=head.next).prev=after=new LongDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void forEach(LongConsumer action)
    {
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Long> action)
    {
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
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
    @Override public void replaceAll(LongUnaryOperator operator){
      final LongDblLnkNode head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          LongDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Long> operator){
      final LongDblLnkNode head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          LongDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    private void pullSurvivorsDown(LongDblLnkNode prev,LongPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(LongDblLnkNode prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(LongDblLnkNode prev,LongPredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedRemoveIf(LongDblLnkNode head,LongPredicate filter){
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
    @Override public void sort(LongComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void sort(Comparator<? super Long> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        ++this.modCount;
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
            LongSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        ++this.modCount;
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(LongComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final long[] tmp;
        final LongDblLnkNode tail;
        LongDblLnkNode.uncheckedCopyInto(tmp=new long[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            LongSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              LongSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        LongDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        LongDblLnkNode head,newTail;
        final var newHead=newTail=new LongDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new LongDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    private static class DescendingItr
      extends AbstractLongItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient LongDblLnkNode curr;
      transient LongDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,LongDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public long nextLong(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        LongDblLnkNode lastRet;
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
              LongDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(LongConsumer action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            LongDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            LongDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfLong
    {
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,LongDblLnkNode curr,int currIndex){
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
      @Override public void set(long val){
        final LongDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(long val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        LongDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new LongDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new LongDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new LongDblLnkNode(val,newNode);
          }else{
            final LongDblLnkNode tmp;
            (newNode=curr).prev=newNode=new LongDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public long previousLong(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          final LongDblLnkNode curr;
          this.lastRet=curr=this.curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public long nextLong(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        LongDblLnkNode lastRet;
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
              LongDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(LongConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            LongDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            LongDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
    @Override public OmniIterator.OfLong descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfLong iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((LongDblLnkSeq)this).getNode(index,size),index);
    }
    @Override public OmniList.OfLong subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public long getLastLong(){
      final LongDblLnkNode tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void addLast(long val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(long val){
      ++this.modCount;
      super.push(val);
    }
    @Override public long removeLastLong(){
      LongDblLnkNode tail;
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
    @Override public long popLong(){
      LongDblLnkNode head;
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
    @Override public long longElement(){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
         return head.val;
      }
      throw new NoSuchElementException();
    }
    boolean uncheckedremoveLastOccurrence(LongDblLnkNode tail
    ,long val
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
        for(LongDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveVal(LongDblLnkNode head
    ,long val
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
        for(LongDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    @Override public long pollLong(){
      LongDblLnkNode head;
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
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      LongDblLnkNode tail;
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
      return Long.MIN_VALUE;
    }
    @Override public Long poll(){
      LongDblLnkNode head;
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
    @Override public Long pollLast(){
      LongDblLnkNode tail;
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
      LongDblLnkNode head;
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
      LongDblLnkNode tail;
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
      LongDblLnkNode head;
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
      LongDblLnkNode tail;
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
  }
  public static class UncheckedList extends LongDblLnkSeq implements OmniDeque.OfLong,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(LongDblLnkNode head,int size,LongDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        do{
          out.writeLong(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        LongDblLnkNode curr;
        for(this.head=curr=new LongDblLnkNode((long)in.readLong());--size!=0;curr=curr.next=new LongDblLnkNode(curr,(long)in.readLong())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        LongDblLnkNode head,newTail;
        final var newHead=newTail=new LongDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new LongDblLnkNode(newTail,(head=head.next).val),++i){}
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedcontains(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return LongDblLnkNode.uncheckedcontains(head,tail,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedcontains(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedindexOf(head,tail,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedindexOf(head,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return LongDblLnkNode.uncheckedindexOf(head,tail,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(byte val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedindexOf(head,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedlastIndexOf(size,tail,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return LongDblLnkNode.uncheckedlastIndexOf(size,tail,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(byte val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return LongDblLnkNode.uncheckedlastIndexOf(size,tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final LongDblLnkNode head;
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final LongDblLnkNode head;
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
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveVal(LongDblLnkNode head
    ,long val
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
        for(LongDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    @Override public OmniIterator.OfLong descendingIterator(){
      return new DescendingItr(this);
    }
    private static class AscendingItr
      extends AbstractLongItr
    {
      transient final UncheckedList parent;
      transient LongDblLnkNode curr;
      private AscendingItr(UncheckedList parent,LongDblLnkNode curr){
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
          LongDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            LongDblLnkNode lastRet;
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
      @Override public long nextLong(){
        final LongDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      @Override public void forEachRemaining(LongConsumer action){
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          LongDblLnkNode.uncheckedForEachAscending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          LongDblLnkNode.uncheckedForEachAscending(curr,action::accept);
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
          LongDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            LongDblLnkNode lastRet;
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
      @Override public long nextLong(){
        final LongDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override public void forEachRemaining(LongConsumer action){
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          LongDblLnkNode.uncheckedForEachDescending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          LongDblLnkNode.uncheckedForEachDescending(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfLong{
      transient int currIndex;
      transient LongDblLnkNode lastRet;
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,LongDblLnkNode curr,int currIndex){
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
      @Override public void add(long val){
        final UncheckedList parent;
        LongDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new LongDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new LongDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new LongDblLnkNode(val,newNode);
          }else{
            final LongDblLnkNode tmp;
            (newNode=curr).prev=newNode=new LongDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(long val){
        lastRet.val=val;
      }
      @Override public long previousLong(){
        final LongDblLnkNode curr;
        this.lastRet=curr=this.curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public long nextLong(){
        final LongDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public void remove(){
        LongDblLnkNode lastRet;
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
            LongDblLnkNode.eraseNode(lastRet);
          }
        }
        this.lastRet=null;
      }
      @Override public void forEachRemaining(LongConsumer action){
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          LongDblLnkNode.uncheckedForEachAscending(curr,action);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final LongDblLnkNode curr;
        if((curr=this.curr)!=null){
          LongDblLnkNode.uncheckedForEachAscending(curr,action::accept);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
    }
    @Override public OmniIterator.OfLong iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfLong listIterator(int index){
      return new BidirectionalItr(this,((LongDblLnkSeq)this).getNode(index,this.size),index);
    }
    @Override public OmniList.OfLong subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public long getLastLong(){
      return tail.val;
    }
    @Override public boolean offerFirst(long val){
      push((long)val);
      return true;
    }
    @Override public boolean offerLast(long val){
      addLast((long)val);
      return true;
    }
    @Override public void addFirst(long val){
      push((long)val);
    }
    @Override public long removeFirstLong(){
      return popLong();
    }
    @Override public void push(long val){
      LongDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new LongDblLnkNode(val);
      }else{
        head.prev=head=new LongDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public long removeLastLong(){
      LongDblLnkNode tail;
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
    @Override public long popLong(){
      LongDblLnkNode head;
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
    @Override public long longElement(){
      return head.val;
    }
    @Override public boolean offer(long val){
      addLast((long)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongDblLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return LongDblLnkNode.uncheckedsearch(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final LongDblLnkNode head;
          if((head=this.head)!=null)
          {
            return LongDblLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final LongDblLnkNode tail;
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveLastOccurrence(tail,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(byte val){
      {
        {
          final LongDblLnkNode tail;
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
          final LongDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrence(LongDblLnkNode tail
    ,long val
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
        for(LongDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    @Override public Long peekFirst(){
      return peek();
    }
    @Override public Long pollFirst(){
      return poll();
    }
    @Override public Long pop(){
      return popLong();
    }
    @Override public Long remove(){
      return popLong();
    }
    @Override public boolean offer(Long val){
      addLast((long)val);
      return true;
    }
    @Override public Long element(){
      return longElement();
    }
    @Override public Long removeFirst(){
      return popLong();
    }
    @Override public Long removeLast(){
      return removeLastLong();
    }
    @Override public boolean offerFirst(Long val){
      push((long)val);
      return true;
    }
    @Override public boolean offerLast(Long val){
      addLast((long)val);
      return true;
    }
    @Override public void push(Long val){
      push((long)val);
    }
    @Override public void addFirst(Long val){
      push((long)val);
    }
    @Override public void addLast(Long val){
      addLast((long)val);
    }
    @Override public Long getFirst(){
      return longElement();
    }
    @Override public Long getLast(){
      return getLastLong();
    }
    @Override public long pollLong(){
      LongDblLnkNode head;
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
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      LongDblLnkNode tail;
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
      return Long.MIN_VALUE;
    }
    @Override public Long poll(){
      LongDblLnkNode head;
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
    @Override public Long pollLast(){
      LongDblLnkNode tail;
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
      LongDblLnkNode head;
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
      LongDblLnkNode tail;
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
      LongDblLnkNode head;
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
      LongDblLnkNode tail;
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
    @Override public long peekLong(){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLastLong(){
      final LongDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public Long peek(){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Long peekLast(){
      final LongDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final LongDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (double)(tail.val);
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final LongDblLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final LongDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (float)(tail.val);
      }
      return Float.NaN;
    }
    @Override public boolean removeIf(LongPredicate filter){
      final LongDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Long> filter){
      final LongDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(LongDblLnkNode prev,LongDblLnkNode tail,LongPredicate filter){
      int numSurvivors=1;
      outer:for(LongDblLnkNode next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(LongDblLnkNode prev,LongDblLnkNode curr,LongDblLnkNode tail,LongPredicate filter){
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
    boolean uncheckedRemoveIf(LongDblLnkNode head,LongPredicate filter){
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
          final LongDblLnkNode prev;
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
