package omni.impl.seq;
import omni.util.DoubleSortUtil;
import omni.api.OmniList;
import omni.impl.DoubleDblLnkNode;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.UnaryOperator;
import java.util.function.DoubleUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractDoubleItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.DoubleComparator;
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
public abstract class DoubleDblLnkSeq extends AbstractSeq implements
   DoubleSubListDefault
{
  private static final long serialVersionUID=1L;
  transient DoubleDblLnkNode head;
  transient DoubleDblLnkNode tail;
  private  DoubleDblLnkSeq(){
  }
  private DoubleDblLnkSeq(DoubleDblLnkNode head,int size,DoubleDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  private static  int markSurvivors(DoubleDblLnkNode curr,DoublePredicate filter,long[] survivorSet){
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
  private static  long markSurvivors(DoubleDblLnkNode curr,DoublePredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  public void addLast(double val){
    DoubleDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new DoubleDblLnkNode(val);
    }else{
      tail.next=tail=new DoubleDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(double val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,double val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new DoubleDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        DoubleDblLnkNode before;
        (before=tail.prev).next=before=new DoubleDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      DoubleDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new DoubleDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new DoubleDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        DoubleDblLnkNode after;
        (after=head.next).prev=after=new DoubleDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private DoubleDblLnkNode getNode(int index,int size){
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
  @Override public double set(int index,double val){
    DoubleDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,double val){
    getNode(index,size).val=val;
  }
  @Override public double getDouble(int index){
    return getNode(index,size).val;
  }
  @Override public double removeDoubleAt(int index){
    final double ret;
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
        ret=(tail=DoubleDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        DoubleDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=DoubleDblLnkNode.uncheckedIterateAscending(head,index)).val;
        DoubleDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(DoubleConsumer action){
    final DoubleDblLnkNode head;
    if((head=this.head)!=null){
      DoubleDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Double> action){
    final DoubleDblLnkNode head;
    if((head=this.head)!=null){
      DoubleDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      DoubleDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      DoubleDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      DoubleDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public Double[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Double[] dst;
      DoubleDblLnkNode.uncheckedCopyInto(dst=new Double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
  @Override public void replaceAll(DoubleUnaryOperator operator){
    final DoubleDblLnkNode head;
    if((head=this.head)!=null){
      DoubleDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Double> operator){
    final DoubleDblLnkNode head;
    if((head=this.head)!=null){
      DoubleDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(DoubleComparator sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final double[] tmp;
      final DoubleDblLnkNode tail;
      DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            DoubleSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }
      }
      DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Double> sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final double[] tmp;
      final DoubleDblLnkNode tail;
      DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            DoubleSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }
        }
      }
      DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final double[] tmp;
      final DoubleDblLnkNode tail;
      DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
      {
          DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
      }
      DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final double[] tmp;
      final DoubleDblLnkNode tail;
      DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
      {
          DoubleSortUtil.uncheckedDescendingSort(tmp,0,size);
      }
      DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(DoubleComparator sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final double[] tmp;
      final DoubleDblLnkNode tail;
      DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            DoubleSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }
      }
      DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final DoubleDblLnkNode head;
    if((head=this.head)!=null){
      final StringBuilder builder;
      DoubleDblLnkNode.uncheckedToString(head,tail,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public int hashCode(){
    final DoubleDblLnkNode head;
    if((head=this.head)!=null){
      return DoubleDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  private static class UncheckedSubList extends DoubleDblLnkSeq
  {
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int rootOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,DoubleDblLnkNode head,int size,DoubleDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,DoubleDblLnkNode head,int size,DoubleDblLnkNode tail){
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
    private void bubbleUpClearBody(DoubleDblLnkNode before,DoubleDblLnkNode head,int numRemoved,DoubleDblLnkNode tail,DoubleDblLnkNode after){
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
    private void bubbleUpClearHead(DoubleDblLnkNode tail, DoubleDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(DoubleDblLnkNode head, DoubleDblLnkNode before,int numRemoved){
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
        DoubleDblLnkNode before,head,tail,after=(tail=this.tail).next;
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
  private static class CheckedSubList extends DoubleDblLnkSeq
  {
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
     private CheckedSubList(CheckedList root,int rootOffset,DoubleDblLnkNode head,int size,DoubleDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,DoubleDblLnkNode head,int size,DoubleDblLnkNode tail){
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
    private void bubbleUpClearBody(DoubleDblLnkNode before,DoubleDblLnkNode head,int numRemoved,DoubleDblLnkNode tail,DoubleDblLnkNode after){
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
    private void bubbleUpClearHead(DoubleDblLnkNode tail, DoubleDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(DoubleDblLnkNode head, DoubleDblLnkNode before,int numRemoved){
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
        DoubleDblLnkNode before,head,tail,after=(tail=this.tail).next;
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
    @Override public void replaceAll(DoubleUnaryOperator operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final DoubleDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        DoubleDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(DoubleConsumer action){
      final int modCount=this.modCount;
      try{
        final DoubleDblLnkNode head;
        if((head=this.head)!=null){
          DoubleDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Double> operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final DoubleDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        DoubleDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(Consumer<? super Double> action){
      final int modCount=this.modCount;
      try{
        final DoubleDblLnkNode head;
        if((head=this.head)!=null){
          DoubleDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(DoubleComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final double[] tmp;
        final DoubleDblLnkNode tail;
        DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              DoubleSortUtil.uncheckedStableSort(tmp,0,size,sorter);
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
        DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Double> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final double[] tmp;
        final DoubleDblLnkNode tail;
        DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              DoubleSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
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
        DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final double[] tmp;
        final DoubleDblLnkNode tail;
        DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final double[] tmp;
        final DoubleDblLnkNode tail;
        DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            DoubleSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(DoubleComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final double[] tmp;
        final DoubleDblLnkNode tail;
        DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              DoubleSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
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
        DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
    CheckedList(DoubleDblLnkNode head,int size,DoubleDblLnkNode tail){
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
    @Override public double set(int index,double val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      DoubleDblLnkNode tmp;
      final var ret=(tmp=((DoubleDblLnkSeq)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,double val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((DoubleDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public double getDouble(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((DoubleDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public double removeDoubleAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final double ret;
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
          ret=(tail=DoubleDblLnkNode.uncheckedIterateDescending(tail,size)).val;
          DoubleDblLnkNode.eraseNode(tail);
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          ret=(head=DoubleDblLnkNode.uncheckedIterateAscending(head,index)).val;
          DoubleDblLnkNode.eraseNode(head);
        }
      }
      return ret;
    }
    @Override public void add(int index,double val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=size+1;
      if((size-=index)<=index){
        var tail=this.tail;
        if(size==0){
          tail.next=tail=new DoubleDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          while(--size!=0){
            tail=tail.prev;
          }
          DoubleDblLnkNode before;
          (before=tail.prev).next=before=new DoubleDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        DoubleDblLnkNode head;
        if((head=this.head)==null){
          this.head=head=new DoubleDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          head.prev=head=new DoubleDblLnkNode(val,head);
          this.head=head;
        }else{
          while(--index!=0){
            head=head.next;
          }
          DoubleDblLnkNode after;
          (after=head.next).prev=after=new DoubleDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void forEach(DoubleConsumer action)
    {
      final DoubleDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          DoubleDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Double> action)
    {
      final DoubleDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          DoubleDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
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
    @Override public void replaceAll(DoubleUnaryOperator operator){
      final DoubleDblLnkNode head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          DoubleDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Double> operator){
      final DoubleDblLnkNode head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          DoubleDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    private void pullSurvivorsDown(DoubleDblLnkNode prev,DoublePredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(DoubleDblLnkNode prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(DoubleDblLnkNode prev,DoublePredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedRemoveIf(DoubleDblLnkNode head,DoublePredicate filter){
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
    @Override public void sort(DoubleComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final double[] tmp;
        final DoubleDblLnkNode tail;
        DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              DoubleSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void sort(Comparator<? super Double> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final double[] tmp;
        final DoubleDblLnkNode tail;
        DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              DoubleSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final double[] tmp;
        final DoubleDblLnkNode tail;
        DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
        {
            DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        ++this.modCount;
        DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final double[] tmp;
        final DoubleDblLnkNode tail;
        DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
        {
            DoubleSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        ++this.modCount;
        DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(DoubleComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final double[] tmp;
        final DoubleDblLnkNode tail;
        DoubleDblLnkNode.uncheckedCopyInto(tmp=new double[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            DoubleSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              DoubleSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        DoubleDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        DoubleDblLnkNode head,newTail;
        final var newHead=newTail=new DoubleDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new DoubleDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    private static class DescendingItr
      extends AbstractDoubleItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient DoubleDblLnkNode curr;
      transient DoubleDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,DoubleDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public double nextDouble(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final DoubleDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        DoubleDblLnkNode lastRet;
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
              DoubleDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            DoubleDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            DoubleDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfDouble
    {
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,DoubleDblLnkNode curr,int currIndex){
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
      @Override public void set(double val){
        final DoubleDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(double val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        DoubleDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new DoubleDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new DoubleDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new DoubleDblLnkNode(val,newNode);
          }else{
            final DoubleDblLnkNode tmp;
            (newNode=curr).prev=newNode=new DoubleDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public double previousDouble(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          final DoubleDblLnkNode curr;
          this.lastRet=curr=this.curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public double nextDouble(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final DoubleDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        DoubleDblLnkNode lastRet;
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
              DoubleDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            DoubleDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            DoubleDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
    @Override public OmniIterator.OfDouble descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfDouble listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfDouble listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((DoubleDblLnkSeq)this).getNode(index,size),index);
    }
    @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public double getLastDouble(){
      final DoubleDblLnkNode tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void addLast(double val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(double val){
      ++this.modCount;
      super.push(val);
    }
    @Override public double removeLastDouble(){
      DoubleDblLnkNode tail;
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
    @Override public double popDouble(){
      DoubleDblLnkNode head;
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
    @Override public double doubleElement(){
      final DoubleDblLnkNode head;
      if((head=this.head)!=null){
         return head.val;
      }
      throw new NoSuchElementException();
    }
    boolean uncheckedremoveLastOccurrenceBits(DoubleDblLnkNode tail
    ,long bits
    ){
      {
        if(bits==Double.doubleToRawLongBits(tail.val)){
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
        for(DoubleDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(bits==Double.doubleToRawLongBits(tail.val)){
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
    boolean uncheckedremoveLastOccurrence0(DoubleDblLnkNode tail
    ){
      {
        if(0==(tail.val)){
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
        for(DoubleDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(0==(tail.val)){
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
    boolean uncheckedremoveLastOccurrenceNaN(DoubleDblLnkNode tail
    ){
      {
        if(Double.isNaN(tail.val)){
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
        for(DoubleDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(Double.isNaN(tail.val)){
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
    boolean uncheckedremoveValBits(DoubleDblLnkNode head
    ,long bits
    ){
      {
        if(bits==Double.doubleToRawLongBits(head.val)){
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
        for(DoubleDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(bits==Double.doubleToRawLongBits(head.val)){
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
    boolean uncheckedremoveVal0(DoubleDblLnkNode head
    ){
      {
        if(0==(head.val)){
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
        for(DoubleDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(0==(head.val)){
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
    boolean uncheckedremoveValNaN(DoubleDblLnkNode head
    ){
      {
        if(Double.isNaN(head.val)){
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
        for(DoubleDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(Double.isNaN(head.val)){
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
    @Override public double pollDouble(){
      DoubleDblLnkNode head;
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
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      DoubleDblLnkNode tail;
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
      return Double.NaN;
    }
    @Override public Double poll(){
      DoubleDblLnkNode head;
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
    @Override public Double pollLast(){
      DoubleDblLnkNode tail;
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
  }
  public static class UncheckedList extends DoubleDblLnkSeq implements OmniDeque.OfDouble,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(DoubleDblLnkNode head,int size,DoubleDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        do{
          out.writeDouble(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        DoubleDblLnkNode curr;
        for(this.head=curr=new DoubleDblLnkNode((double)in.readDouble());--size!=0;curr=curr.next=new DoubleDblLnkNode(curr,(double)in.readDouble())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        DoubleDblLnkNode head,newTail;
        final var newHead=newTail=new DoubleDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new DoubleDblLnkNode(newTail,(head=head.next).val),++i){}
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
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,TypeUtil.DBL_TRUE_BITS);
            }
            return DoubleDblLnkNode.uncheckedcontains0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              {
                return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleDblLnkNode.uncheckedcontains0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleDblLnkNode.uncheckedcontains0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedcontainsNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedcontainsNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(d));
                }
                return DoubleDblLnkNode.uncheckedcontainsNaN(head,tail);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(f));
                }
                return DoubleDblLnkNode.uncheckedcontainsNaN(head,tail);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(i));
                }
                return DoubleDblLnkNode.uncheckedcontains0(head,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(l));
                }
                return DoubleDblLnkNode.uncheckedcontains0(head,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(i));
                }
                return DoubleDblLnkNode.uncheckedcontains0(head,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,TypeUtil.DBL_TRUE_BITS);
                }
                return DoubleDblLnkNode.uncheckedcontains0(head,tail);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedcontains0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedcontains0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedcontainsBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedcontains0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,TypeUtil.DBL_TRUE_BITS);
            }
            return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              {
                return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedindexOfNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedindexOfNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(d));
                }
                return DoubleDblLnkNode.uncheckedindexOfNaN(head,tail);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(f));
                }
                return DoubleDblLnkNode.uncheckedindexOfNaN(head,tail);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(i));
                }
                return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(l));
                }
                return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(i));
                }
                return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,TypeUtil.DBL_TRUE_BITS);
                }
                return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(byte val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedindexOfBits(head,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedindexOf0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val){
              return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,TypeUtil.DBL_TRUE_BITS);
            }
            return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              {
                return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val){
              return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val){
              return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(d));
                }
                return DoubleDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(f));
                }
                return DoubleDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(i));
                }
                return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(l));
                }
                return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(i));
                }
                return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,TypeUtil.DBL_TRUE_BITS);
                }
                return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(byte val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedlastIndexOfBits(size,tail,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedlastIndexOf0(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return uncheckedremoveValBits(head,TypeUtil.DBL_TRUE_BITS);
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              {
                return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveValNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveValNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return uncheckedremoveValBits(head,Double.doubleToRawLongBits(d));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveValBits(head,Double.doubleToRawLongBits(f));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return uncheckedremoveValBits(head,Double.doubleToRawLongBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Double.doubleToRawLongBits(l));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveValBits(head,Double.doubleToRawLongBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveValBits(head,TypeUtil.DBL_TRUE_BITS);
                }
                return uncheckedremoveVal0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveValBits(DoubleDblLnkNode head
    ,long bits
    ){
      {
        if(bits==Double.doubleToRawLongBits(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(DoubleDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(bits==Double.doubleToRawLongBits(head.val)){
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
    boolean uncheckedremoveVal0(DoubleDblLnkNode head
    ){
      {
        if(0==(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(DoubleDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(0==(head.val)){
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
    boolean uncheckedremoveValNaN(DoubleDblLnkNode head
    ){
      {
        if(Double.isNaN(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(DoubleDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(Double.isNaN(head.val)){
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
    @Override public OmniIterator.OfDouble descendingIterator(){
      return new DescendingItr(this);
    }
    private static class AscendingItr
      extends AbstractDoubleItr
    {
      transient final UncheckedList parent;
      transient DoubleDblLnkNode curr;
      private AscendingItr(UncheckedList parent,DoubleDblLnkNode curr){
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
          DoubleDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            DoubleDblLnkNode lastRet;
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
      @Override public double nextDouble(){
        final DoubleDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final DoubleDblLnkNode curr;
        if((curr=this.curr)!=null){
          DoubleDblLnkNode.uncheckedForEachAscending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final DoubleDblLnkNode curr;
        if((curr=this.curr)!=null){
          DoubleDblLnkNode.uncheckedForEachAscending(curr,action::accept);
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
          DoubleDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            DoubleDblLnkNode lastRet;
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
      @Override public double nextDouble(){
        final DoubleDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final DoubleDblLnkNode curr;
        if((curr=this.curr)!=null){
          DoubleDblLnkNode.uncheckedForEachDescending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final DoubleDblLnkNode curr;
        if((curr=this.curr)!=null){
          DoubleDblLnkNode.uncheckedForEachDescending(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfDouble{
      transient int currIndex;
      transient DoubleDblLnkNode lastRet;
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,DoubleDblLnkNode curr,int currIndex){
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
      @Override public void add(double val){
        final UncheckedList parent;
        DoubleDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new DoubleDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new DoubleDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new DoubleDblLnkNode(val,newNode);
          }else{
            final DoubleDblLnkNode tmp;
            (newNode=curr).prev=newNode=new DoubleDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(double val){
        lastRet.val=val;
      }
      @Override public double previousDouble(){
        final DoubleDblLnkNode curr;
        this.lastRet=curr=this.curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public double nextDouble(){
        final DoubleDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public void remove(){
        DoubleDblLnkNode lastRet;
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
            DoubleDblLnkNode.eraseNode(lastRet);
          }
        }
        this.lastRet=null;
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final DoubleDblLnkNode curr;
        if((curr=this.curr)!=null){
          DoubleDblLnkNode.uncheckedForEachAscending(curr,action);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final DoubleDblLnkNode curr;
        if((curr=this.curr)!=null){
          DoubleDblLnkNode.uncheckedForEachAscending(curr,action::accept);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfDouble listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfDouble listIterator(int index){
      return new BidirectionalItr(this,((DoubleDblLnkSeq)this).getNode(index,this.size),index);
    }
    @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public double getLastDouble(){
      return tail.val;
    }
    @Override public boolean offerFirst(double val){
      push((double)val);
      return true;
    }
    @Override public boolean offerLast(double val){
      addLast((double)val);
      return true;
    }
    @Override public void addFirst(double val){
      push((double)val);
    }
    @Override public double removeFirstDouble(){
      return popDouble();
    }
    @Override public void push(double val){
      DoubleDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new DoubleDblLnkNode(val);
      }else{
        head.prev=head=new DoubleDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public double removeLastDouble(){
      DoubleDblLnkNode tail;
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
    @Override public double popDouble(){
      DoubleDblLnkNode head;
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
    @Override public double doubleElement(){
      return head.val;
    }
    @Override public boolean offer(double val){
      addLast((double)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return DoubleDblLnkNode.uncheckedsearchBits(head,TypeUtil.DBL_TRUE_BITS);
            }
            return DoubleDblLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              {
                return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleDblLnkNode.uncheckedsearch0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleDblLnkNode.uncheckedsearch0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedsearchNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedsearchNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(d));
                }
                return DoubleDblLnkNode.uncheckedsearchNaN(head);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(f));
                }
                return DoubleDblLnkNode.uncheckedsearchNaN(head);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(i));
                }
                return DoubleDblLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(l));
                }
                return DoubleDblLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(i));
                }
                return DoubleDblLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return DoubleDblLnkNode.uncheckedsearchBits(head,TypeUtil.DBL_TRUE_BITS);
                }
                return DoubleDblLnkNode.uncheckedsearch0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final DoubleDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return DoubleDblLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleDblLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val){
              return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.DBL_TRUE_BITS);
            }
            return uncheckedremoveLastOccurrence0(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              {
                return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
              }
            }else{
              return uncheckedremoveLastOccurrence0(tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
              }
            }else{
              return uncheckedremoveLastOccurrence0(tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val){
              return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveLastOccurrenceNaN(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(double val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val){
              return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveLastOccurrenceNaN(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(d));
                }
                return uncheckedremoveLastOccurrenceNaN(tail);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(f));
                }
                return uncheckedremoveLastOccurrenceNaN(tail);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(i));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(l));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(i));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.DBL_TRUE_BITS);
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(byte val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveLastOccurrence0(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(char val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveLastOccurrence0(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(short val){
      {
        {
          final DoubleDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveLastOccurrence0(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrenceBits(DoubleDblLnkNode tail
    ,long bits
    ){
      {
        if(bits==Double.doubleToRawLongBits(tail.val)){
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
        for(DoubleDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(bits==Double.doubleToRawLongBits(tail.val)){
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
    boolean uncheckedremoveLastOccurrence0(DoubleDblLnkNode tail
    ){
      {
        if(0==(tail.val)){
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
        for(DoubleDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(0==(tail.val)){
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
    boolean uncheckedremoveLastOccurrenceNaN(DoubleDblLnkNode tail
    ){
      {
        if(Double.isNaN(tail.val)){
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
        for(DoubleDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(Double.isNaN(tail.val)){
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
    @Override public Double peekFirst(){
      return peek();
    }
    @Override public Double pollFirst(){
      return poll();
    }
    @Override public Double pop(){
      return popDouble();
    }
    @Override public Double remove(){
      return popDouble();
    }
    @Override public boolean offer(Double val){
      addLast((double)val);
      return true;
    }
    @Override public Double element(){
      return doubleElement();
    }
    @Override public Double removeFirst(){
      return popDouble();
    }
    @Override public Double removeLast(){
      return removeLastDouble();
    }
    @Override public boolean offerFirst(Double val){
      push((double)val);
      return true;
    }
    @Override public boolean offerLast(Double val){
      addLast((double)val);
      return true;
    }
    @Override public void push(Double val){
      push((double)val);
    }
    @Override public void addFirst(Double val){
      push((double)val);
    }
    @Override public void addLast(Double val){
      addLast((double)val);
    }
    @Override public Double getFirst(){
      return doubleElement();
    }
    @Override public Double getLast(){
      return getLastDouble();
    }
    @Override public double pollDouble(){
      DoubleDblLnkNode head;
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
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      DoubleDblLnkNode tail;
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
      return Double.NaN;
    }
    @Override public Double poll(){
      DoubleDblLnkNode head;
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
    @Override public Double pollLast(){
      DoubleDblLnkNode tail;
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
    @Override public double peekDouble(){
      final DoubleDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final DoubleDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Double.NaN;
    }
    @Override public Double peek(){
      final DoubleDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Double peekLast(){
      final DoubleDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public boolean removeIf(DoublePredicate filter){
      final DoubleDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Double> filter){
      final DoubleDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(DoubleDblLnkNode prev,DoubleDblLnkNode tail,DoublePredicate filter){
      int numSurvivors=1;
      outer:for(DoubleDblLnkNode next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(DoubleDblLnkNode prev,DoubleDblLnkNode curr,DoubleDblLnkNode tail,DoublePredicate filter){
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
    boolean uncheckedRemoveIf(DoubleDblLnkNode head,DoublePredicate filter){
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
          final DoubleDblLnkNode prev;
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
