package omni.impl.seq;
import omni.util.FloatSortUtil;
import omni.api.OmniList;
import omni.impl.FloatDblLnkNode;
import java.util.function.Consumer;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import java.util.function.UnaryOperator;
import omni.function.FloatUnaryOperator;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.impl.AbstractFloatItr;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.function.FloatComparator;
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
public abstract class FloatDblLnkSeq extends AbstractSeq implements
   FloatSubListDefault
{
  private static final long serialVersionUID=1L;
  transient FloatDblLnkNode head;
  transient FloatDblLnkNode tail;
  private  FloatDblLnkSeq(){
  }
  private FloatDblLnkSeq(FloatDblLnkNode head,int size,FloatDblLnkNode tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  private static  int markSurvivors(FloatDblLnkNode curr,FloatPredicate filter,long[] survivorSet){
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
  private static  long markSurvivors(FloatDblLnkNode curr,FloatPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  public void addLast(float val){
    FloatDblLnkNode tail;
    if((tail=this.tail)==null){
      this.head=tail=new FloatDblLnkNode(val);
    }else{
      tail.next=tail=new FloatDblLnkNode(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(float val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,float val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new FloatDblLnkNode(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        FloatDblLnkNode before;
        (before=tail.prev).next=before=new FloatDblLnkNode(before,val,tail);
        tail.prev=before;
      }
    }else{
      FloatDblLnkNode head;
      if((head=this.head)==null){
        this.head=head=new FloatDblLnkNode(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new FloatDblLnkNode(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        FloatDblLnkNode after;
        (after=head.next).prev=after=new FloatDblLnkNode(head,val,after);
        head.next=after;
      }
    }
  }
  private FloatDblLnkNode getNode(int index,int size){
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
  @Override public float set(int index,float val){
    FloatDblLnkNode tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,float val){
    getNode(index,size).val=val;
  }
  @Override public float getFloat(int index){
    return getNode(index,size).val;
  }
  @Override public float removeFloatAt(int index){
    final float ret;
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
        ret=(tail=FloatDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        FloatDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=FloatDblLnkNode.uncheckedIterateAscending(head,index)).val;
        FloatDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(FloatConsumer action){
    final FloatDblLnkNode head;
    if((head=this.head)!=null){
      FloatDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
    final FloatDblLnkNode head;
    if((head=this.head)!=null){
      FloatDblLnkNode.uncheckedForEachAscending(head,tail,action::accept);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      FloatDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      FloatDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      final float[] dst;
      FloatDblLnkNode.uncheckedCopyInto(dst=new float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public Float[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Float[] dst;
      FloatDblLnkNode.uncheckedCopyInto(dst=new Float[size],tail,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      final double[] dst;
      FloatDblLnkNode.uncheckedCopyInto(dst=new double[size],tail,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public void replaceAll(FloatUnaryOperator operator){
    final FloatDblLnkNode head;
    if((head=this.head)!=null){
      FloatDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Float> operator){
    final FloatDblLnkNode head;
    if((head=this.head)!=null){
      FloatDblLnkNode.uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void sort(FloatComparator sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final float[] tmp;
      final FloatDblLnkNode tail;
      FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }
      }
      FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Float> sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final float[] tmp;
      final FloatDblLnkNode tail;
      FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
          }
        }
      }
      FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final float[] tmp;
      final FloatDblLnkNode tail;
      FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
      {
          FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
      }
      FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final float[] tmp;
      final FloatDblLnkNode tail;
      FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
      {
          FloatSortUtil.uncheckedDescendingSort(tmp,0,size);
      }
      FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(FloatComparator sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final float[] tmp;
      final FloatDblLnkNode tail;
      FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        else
        {
          {
            FloatSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }
      }
      FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final FloatDblLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
        [size=FloatDblLnkNode.uncheckedToString(head,tail,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        FloatDblLnkNode.uncheckedToString(head,tail,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public int hashCode(){
    final FloatDblLnkNode head;
    if((head=this.head)!=null){
      return FloatDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  private static class UncheckedSubList extends FloatDblLnkSeq
  {
    private static final long serialVersionUID=1L;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int rootOffset;
    private UncheckedSubList(UncheckedList root,int rootOffset,FloatDblLnkNode head,int size,FloatDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,FloatDblLnkNode head,int size,FloatDblLnkNode tail){
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
    private void bubbleUpClearBody(FloatDblLnkNode before,FloatDblLnkNode head,int numRemoved,FloatDblLnkNode tail,FloatDblLnkNode after){
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
    private void bubbleUpClearHead(FloatDblLnkNode tail, FloatDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(FloatDblLnkNode head, FloatDblLnkNode before,int numRemoved){
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
        FloatDblLnkNode before,head,tail,after=(tail=this.tail).next;
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
  private static class CheckedSubList extends FloatDblLnkSeq
  {
    private static final long serialVersionUID=1L;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int parentOffset;
    transient int modCount;
     private CheckedSubList(CheckedList root,int rootOffset,FloatDblLnkNode head,int size,FloatDblLnkNode tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int parentOffset,FloatDblLnkNode head,int size,FloatDblLnkNode tail){
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
    private void bubbleUpClearBody(FloatDblLnkNode before,FloatDblLnkNode head,int numRemoved,FloatDblLnkNode tail,FloatDblLnkNode after){
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
    private void bubbleUpClearHead(FloatDblLnkNode tail, FloatDblLnkNode after,int numRemoved){
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
    private void bubbleUpClearTail(FloatDblLnkNode head, FloatDblLnkNode before,int numRemoved){
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
        FloatDblLnkNode before,head,tail,after=(tail=this.tail).next;
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
    @Override public void replaceAll(FloatUnaryOperator operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final FloatDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        FloatDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(FloatConsumer action){
      final int modCount=this.modCount;
      try{
        final FloatDblLnkNode head;
        if((head=this.head)!=null){
          FloatDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void replaceAll(UnaryOperator<Float> operator){
      int modCount=this.modCount;
      final CheckedList root;
      try{
        final FloatDblLnkNode head;
        if((head=this.head)==null){
          return;
        }
        FloatDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(Consumer<? super Float> action){
      final int modCount=this.modCount;
      try{
        final FloatDblLnkNode head;
        if((head=this.head)!=null){
          FloatDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(FloatComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final float[] tmp;
        final FloatDblLnkNode tail;
        FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter);
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
        FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super Float> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final float[] tmp;
        final FloatDblLnkNode tail;
        FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
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
        FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final float[] tmp;
        final FloatDblLnkNode tail;
        FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final float[] tmp;
        final FloatDblLnkNode tail;
        FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            FloatSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(FloatComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final float[] tmp;
        final FloatDblLnkNode tail;
        FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            final CheckedList root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
            this.modCount=modCount;
            FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              FloatSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
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
        FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
    CheckedList(FloatDblLnkNode head,int size,FloatDblLnkNode tail){
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
    @Override public float set(int index,float val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      FloatDblLnkNode tmp;
      final var ret=(tmp=((FloatDblLnkSeq)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,float val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((FloatDblLnkSeq)this).getNode(index,size).val=val;
    }
    @Override public float getFloat(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((FloatDblLnkSeq)this).getNode(index,size).val;
    }
    @Override public float removeFloatAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final float ret;
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
          ret=(tail=FloatDblLnkNode.uncheckedIterateDescending(tail,size)).val;
          FloatDblLnkNode.eraseNode(tail);
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          ret=(head=FloatDblLnkNode.uncheckedIterateAscending(head,index)).val;
          FloatDblLnkNode.eraseNode(head);
        }
      }
      return ret;
    }
    @Override public void add(int index,float val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=size+1;
      if((size-=index)<=index){
        var tail=this.tail;
        if(size==0){
          tail.next=tail=new FloatDblLnkNode(tail,val);
          this.tail=tail;
        }else{
          while(--size!=0){
            tail=tail.prev;
          }
          FloatDblLnkNode before;
          (before=tail.prev).next=before=new FloatDblLnkNode(before,val,tail);
          tail.prev=before;
        }
      }else{
        FloatDblLnkNode head;
        if((head=this.head)==null){
          this.head=head=new FloatDblLnkNode(val);
          this.tail=head;
        }else if(index==0){
          head.prev=head=new FloatDblLnkNode(val,head);
          this.head=head;
        }else{
          while(--index!=0){
            head=head.next;
          }
          FloatDblLnkNode after;
          (after=head.next).prev=after=new FloatDblLnkNode(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void forEach(FloatConsumer action)
    {
      final FloatDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          FloatDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Float> action)
    {
      final FloatDblLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          FloatDblLnkNode.uncheckedForEachAscending(head,this.size,action::accept);
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
    @Override public void replaceAll(FloatUnaryOperator operator){
      final FloatDblLnkNode head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          FloatDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<Float> operator){
      final FloatDblLnkNode head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          FloatDblLnkNode.uncheckedReplaceAll(head,this.size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    private void pullSurvivorsDown(FloatDblLnkNode prev,FloatPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(FloatDblLnkNode prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(FloatDblLnkNode prev,FloatPredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedRemoveIf(FloatDblLnkNode head,FloatPredicate filter){
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
    @Override public void sort(FloatComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final float[] tmp;
        final FloatDblLnkNode tail;
        FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void sort(Comparator<? super Float> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final float[] tmp;
        final FloatDblLnkNode tail;
        FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              FloatSortUtil.uncheckedStableSort(tmp,0,size,sorter::compare);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final float[] tmp;
        final FloatDblLnkNode tail;
        FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        {
            FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
        }
        ++this.modCount;
        FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final float[] tmp;
        final FloatDblLnkNode tail;
        FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        {
            FloatSortUtil.uncheckedDescendingSort(tmp,0,size);
        }
        ++this.modCount;
        FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(FloatComparator sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final float[] tmp;
        final FloatDblLnkNode tail;
        FloatDblLnkNode.uncheckedCopyInto(tmp=new float[size],tail=this.tail,size);
        {
          if(sorter==null)
          {
            ++this.modCount;
            FloatSortUtil.uncheckedAscendingSort(tmp,0,size);
          }
          else
          {
            int modCount=this.modCount;
            try
            {
              FloatSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
            }
          }
        }
        FloatDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        FloatDblLnkNode head,newTail;
        final var newHead=newTail=new FloatDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new FloatDblLnkNode(newTail,(head=head.next).val),++i){}
        return new CheckedList(newHead,size,newTail);
      }
      return new CheckedList();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    private static class DescendingItr
      extends AbstractFloatItr
    {
      transient final CheckedList parent;
      transient int modCount;
      transient FloatDblLnkNode curr;
      transient FloatDblLnkNode lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList parent,FloatDblLnkNode curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public float nextFloat(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final FloatDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        FloatDblLnkNode lastRet;
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
              FloatDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(FloatConsumer action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            FloatDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList parent;
          try{
            FloatDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
    }
    private static class BidirectionalItr extends DescendingItr implements OmniListIterator.OfFloat
    {
      private BidirectionalItr(CheckedList parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList parent,FloatDblLnkNode curr,int currIndex){
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
      @Override public void set(float val){
        final FloatDblLnkNode lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(float val){
        final CheckedList parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        FloatDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new FloatDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new FloatDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new FloatDblLnkNode(val,newNode);
          }else{
            final FloatDblLnkNode tmp;
            (newNode=curr).prev=newNode=new FloatDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public float previousFloat(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          final FloatDblLnkNode curr;
          this.lastRet=curr=this.curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public float nextFloat(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final FloatDblLnkNode curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        FloatDblLnkNode lastRet;
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
              FloatDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            FloatDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final int size,numLeft;
        final CheckedList parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            FloatDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
    @Override public OmniIterator.OfFloat descendingIterator(){
      return new DescendingItr(this);
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr(this,((FloatDblLnkSeq)this).getNode(index,size),index);
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public float getLastFloat(){
      final FloatDblLnkNode tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void addLast(float val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(float val){
      ++this.modCount;
      super.push(val);
    }
    @Override public float removeLastFloat(){
      FloatDblLnkNode tail;
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
    @Override public float popFloat(){
      FloatDblLnkNode head;
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
    @Override public float floatElement(){
      final FloatDblLnkNode head;
      if((head=this.head)!=null){
         return head.val;
      }
      throw new NoSuchElementException();
    }
    boolean uncheckedremoveLastOccurrenceBits(FloatDblLnkNode tail
    ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(tail.val)){
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
        for(FloatDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(bits==Float.floatToRawIntBits(tail.val)){
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
    boolean uncheckedremoveLastOccurrence0(FloatDblLnkNode tail
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
        for(FloatDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveLastOccurrenceNaN(FloatDblLnkNode tail
    ){
      {
        if(Float.isNaN(tail.val)){
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
        for(FloatDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(Float.isNaN(tail.val)){
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
    boolean uncheckedremoveValBits(FloatDblLnkNode head
    ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(head.val)){
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
        for(FloatDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(bits==Float.floatToRawIntBits(head.val)){
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
    boolean uncheckedremoveVal0(FloatDblLnkNode head
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
        for(FloatDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    boolean uncheckedremoveValNaN(FloatDblLnkNode head
    ){
      {
        if(Float.isNaN(head.val)){
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
        for(FloatDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(Float.isNaN(head.val)){
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
    @Override public float pollFloat(){
      FloatDblLnkNode head;
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
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      FloatDblLnkNode tail;
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
      return Float.NaN;
    }
    @Override public Float poll(){
      FloatDblLnkNode head;
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
    @Override public Float pollLast(){
      FloatDblLnkNode tail;
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
      FloatDblLnkNode head;
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
      FloatDblLnkNode tail;
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
  }
  public static class UncheckedList extends FloatDblLnkSeq implements OmniDeque.OfFloat,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(FloatDblLnkNode head,int size,FloatDblLnkNode tail){
      super(head,size,tail);
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        do{
          out.writeFloat(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        FloatDblLnkNode curr;
        for(this.head=curr=new FloatDblLnkNode((float)in.readFloat());--size!=0;curr=curr.next=new FloatDblLnkNode(curr,(float)in.readFloat())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        FloatDblLnkNode head,newTail;
        final var newHead=newTail=new FloatDblLnkNode((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new FloatDblLnkNode(newTail,(head=head.next).val),++i){}
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return FloatDblLnkNode.uncheckedcontainsBits(head,tail,TypeUtil.FLT_TRUE_BITS);
            }
            return FloatDblLnkNode.uncheckedcontains0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatDblLnkNode.uncheckedcontains0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatDblLnkNode.uncheckedcontains0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedcontainsNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return FloatDblLnkNode.uncheckedcontainsNaN(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(f));
                }
                return FloatDblLnkNode.uncheckedcontainsNaN(head,tail);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return FloatDblLnkNode.uncheckedcontainsNaN(head,tail);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedcontains0(head,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(l));
                }
                return FloatDblLnkNode.uncheckedcontains0(head,tail);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedcontains0(head,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedcontains0(head,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return FloatDblLnkNode.uncheckedcontainsBits(head,tail,TypeUtil.FLT_TRUE_BITS);
                }
                return FloatDblLnkNode.uncheckedcontains0(head,tail);
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedcontains0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedcontains0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedcontainsBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedcontains0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return FloatDblLnkNode.uncheckedindexOfBits(head,tail,TypeUtil.FLT_TRUE_BITS);
            }
            return FloatDblLnkNode.uncheckedindexOf0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatDblLnkNode.uncheckedindexOf0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatDblLnkNode.uncheckedindexOf0(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedindexOfNaN(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return FloatDblLnkNode.uncheckedindexOfNaN(head,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(f));
                }
                return FloatDblLnkNode.uncheckedindexOfNaN(head,tail);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return FloatDblLnkNode.uncheckedindexOfNaN(head,tail);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedindexOf0(head,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(l));
                }
                return FloatDblLnkNode.uncheckedindexOf0(head,tail);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedindexOf0(head,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedindexOf0(head,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return FloatDblLnkNode.uncheckedindexOfBits(head,tail,TypeUtil.FLT_TRUE_BITS);
                }
                return FloatDblLnkNode.uncheckedindexOf0(head,tail);
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedindexOf0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedindexOf0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedindexOfBits(head,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedindexOf0(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val){
              return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,TypeUtil.FLT_TRUE_BITS);
            }
            return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val){
              return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return FloatDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(f));
                }
                return FloatDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return FloatDblLnkNode.uncheckedlastIndexOfNaN(size,tail);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(l));
                }
                return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,TypeUtil.FLT_TRUE_BITS);
                }
                return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedlastIndexOfBits(size,tail,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedlastIndexOf0(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return uncheckedremoveValNaN(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return uncheckedremoveValNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(l));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveValBits(FloatDblLnkNode head
    ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(FloatDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(bits==Float.floatToRawIntBits(head.val)){
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
    boolean uncheckedremoveVal0(FloatDblLnkNode head
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
        for(FloatDblLnkNode prev;(head=(prev=head).next)!=null;){
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
    boolean uncheckedremoveValNaN(FloatDblLnkNode head
    ){
      {
        if(Float.isNaN(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(FloatDblLnkNode prev;(head=(prev=head).next)!=null;){
          if(Float.isNaN(head.val)){
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
    @Override public OmniIterator.OfFloat descendingIterator(){
      return new DescendingItr(this);
    }
    private static class AscendingItr
      extends AbstractFloatItr
    {
      transient final UncheckedList parent;
      transient FloatDblLnkNode curr;
      private AscendingItr(UncheckedList parent,FloatDblLnkNode curr){
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
          FloatDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            FloatDblLnkNode lastRet;
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
      @Override public float nextFloat(){
        final FloatDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final FloatDblLnkNode curr;
        if((curr=this.curr)!=null){
          FloatDblLnkNode.uncheckedForEachAscending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final FloatDblLnkNode curr;
        if((curr=this.curr)!=null){
          FloatDblLnkNode.uncheckedForEachAscending(curr,action::accept);
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
          FloatDblLnkNode curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            FloatDblLnkNode lastRet;
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
      @Override public float nextFloat(){
        final FloatDblLnkNode curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final FloatDblLnkNode curr;
        if((curr=this.curr)!=null){
          FloatDblLnkNode.uncheckedForEachDescending(curr,action);
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final FloatDblLnkNode curr;
        if((curr=this.curr)!=null){
          FloatDblLnkNode.uncheckedForEachDescending(curr,action::accept);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr extends AscendingItr implements OmniListIterator.OfFloat{
      transient int currIndex;
      transient FloatDblLnkNode lastRet;
      private BidirectionalItr(UncheckedList parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList parent,FloatDblLnkNode curr,int currIndex){
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
      @Override public void add(float val){
        final UncheckedList parent;
        FloatDblLnkNode newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new FloatDblLnkNode(val);
          }else{
            (newNode=parent.tail).next=newNode=new FloatDblLnkNode(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new FloatDblLnkNode(val,newNode);
          }else{
            final FloatDblLnkNode tmp;
            (newNode=curr).prev=newNode=new FloatDblLnkNode(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(float val){
        lastRet.val=val;
      }
      @Override public float previousFloat(){
        final FloatDblLnkNode curr;
        this.lastRet=curr=this.curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public float nextFloat(){
        final FloatDblLnkNode curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public void remove(){
        FloatDblLnkNode lastRet;
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
            FloatDblLnkNode.eraseNode(lastRet);
          }
        }
        this.lastRet=null;
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final FloatDblLnkNode curr;
        if((curr=this.curr)!=null){
          FloatDblLnkNode.uncheckedForEachAscending(curr,action);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final FloatDblLnkNode curr;
        if((curr=this.curr)!=null){
          FloatDblLnkNode.uncheckedForEachAscending(curr,action::accept);
          final UncheckedList parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new AscendingItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(){
      return new BidirectionalItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      return new BidirectionalItr(this,((FloatDblLnkSeq)this).getNode(index,this.size),index);
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public float getLastFloat(){
      return tail.val;
    }
    @Override public boolean offerFirst(float val){
      push((float)val);
      return true;
    }
    @Override public boolean offerLast(float val){
      addLast((float)val);
      return true;
    }
    @Override public void addFirst(float val){
      push((float)val);
    }
    @Override public float removeFirstFloat(){
      return popFloat();
    }
    @Override public void push(float val){
      FloatDblLnkNode head;
      if((head=this.head)==null){
        this.head=tail=new FloatDblLnkNode(val);
      }else{
        head.prev=head=new FloatDblLnkNode(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public float removeLastFloat(){
      FloatDblLnkNode tail;
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
    @Override public float popFloat(){
      FloatDblLnkNode head;
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
    @Override public float floatElement(){
      return head.val;
    }
    @Override public boolean offer(float val){
      addLast((float)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return FloatDblLnkNode.uncheckedsearchBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return FloatDblLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatDblLnkNode.uncheckedsearch0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatDblLnkNode.uncheckedsearch0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedsearchNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return FloatDblLnkNode.uncheckedsearchNaN(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(f));
                }
                return FloatDblLnkNode.uncheckedsearchNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return FloatDblLnkNode.uncheckedsearchNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(l));
                }
                return FloatDblLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return FloatDblLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return FloatDblLnkNode.uncheckedsearchBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return FloatDblLnkNode.uncheckedsearch0(head);
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
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final FloatDblLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatDblLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return FloatDblLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val){
              return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.FLT_TRUE_BITS);
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val==val){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return uncheckedremoveLastOccurrenceNaN(tail);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            //todo: a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(f));
                }
                return uncheckedremoveLastOccurrenceNaN(tail);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return uncheckedremoveLastOccurrenceNaN(tail);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(l));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveLastOccurrence0(tail);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveLastOccurrenceBits(tail,TypeUtil.FLT_TRUE_BITS);
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
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
          final FloatDblLnkNode tail;
          if((tail=this.tail)!=null)
          {
            if(val!=0){
              return uncheckedremoveLastOccurrenceBits(tail,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveLastOccurrence0(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrenceBits(FloatDblLnkNode tail
    ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(tail.val)){
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
        for(FloatDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(bits==Float.floatToRawIntBits(tail.val)){
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
    boolean uncheckedremoveLastOccurrence0(FloatDblLnkNode tail
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
        for(FloatDblLnkNode next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveLastOccurrenceNaN(FloatDblLnkNode tail
    ){
      {
        if(Float.isNaN(tail.val)){
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
        for(FloatDblLnkNode next;(tail=(next=tail).prev)!=null;){
          if(Float.isNaN(tail.val)){
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
    @Override public Float peekFirst(){
      return peek();
    }
    @Override public Float pollFirst(){
      return poll();
    }
    @Override public Float pop(){
      return popFloat();
    }
    @Override public Float remove(){
      return popFloat();
    }
    @Override public boolean offer(Float val){
      addLast((float)val);
      return true;
    }
    @Override public Float element(){
      return floatElement();
    }
    @Override public Float removeFirst(){
      return popFloat();
    }
    @Override public Float removeLast(){
      return removeLastFloat();
    }
    @Override public boolean offerFirst(Float val){
      push((float)val);
      return true;
    }
    @Override public boolean offerLast(Float val){
      addLast((float)val);
      return true;
    }
    @Override public void push(Float val){
      push((float)val);
    }
    @Override public void addFirst(Float val){
      push((float)val);
    }
    @Override public void addLast(Float val){
      addLast((float)val);
    }
    @Override public Float getFirst(){
      return floatElement();
    }
    @Override public Float getLast(){
      return getLastFloat();
    }
    @Override public float pollFloat(){
      FloatDblLnkNode head;
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
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      FloatDblLnkNode tail;
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
      return Float.NaN;
    }
    @Override public Float poll(){
      FloatDblLnkNode head;
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
    @Override public Float pollLast(){
      FloatDblLnkNode tail;
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
      FloatDblLnkNode head;
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
      FloatDblLnkNode tail;
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
    @Override public float peekFloat(){
      final FloatDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Float.NaN;
    }
    @Override public float peekLastFloat(){
      final FloatDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return Float.NaN;
    }
    @Override public Float peek(){
      final FloatDblLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public Float peekLast(){
      final FloatDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public double peekDouble(){
      final FloatDblLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double peekLastDouble(){
      final FloatDblLnkNode tail;
      if((tail=this.tail)!=null){
        return (double)(tail.val);
      }
      return Double.NaN;
    }
    @Override public boolean removeIf(FloatPredicate filter){
      final FloatDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Float> filter){
      final FloatDblLnkNode head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter::test);
    }
    private int removeIfHelper(FloatDblLnkNode prev,FloatDblLnkNode tail,FloatPredicate filter){
      int numSurvivors=1;
      outer:for(FloatDblLnkNode next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(FloatDblLnkNode prev,FloatDblLnkNode curr,FloatDblLnkNode tail,FloatPredicate filter){
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
    boolean uncheckedRemoveIf(FloatDblLnkNode head,FloatPredicate filter){
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
          final FloatDblLnkNode prev;
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
