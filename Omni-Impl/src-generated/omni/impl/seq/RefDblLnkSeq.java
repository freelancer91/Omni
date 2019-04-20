package omni.impl.seq;
import omni.util.RefSortUtil;
import omni.api.OmniList;
import omni.impl.RefDblLnkNode;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import omni.util.OmniArray;
import omni.util.OmniPred;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
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
public abstract class RefDblLnkSeq<E> extends AbstractSeq implements
   OmniList.OfRef<E>
{
  private static final long serialVersionUID=1L;
  transient RefDblLnkNode<E> head;
  transient RefDblLnkNode<E> tail;
  private  RefDblLnkSeq(){
  }
  private RefDblLnkSeq(RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  private static <E> int markSurvivors(RefDblLnkNode<E> curr,Predicate<? super E> filter,long[] survivorSet){
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
  private static <E> long markSurvivors(RefDblLnkNode<E> curr,Predicate<? super E> filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  public void addLast(E val){
    RefDblLnkNode<E> tail;
    if((tail=this.tail)==null){
      this.head=tail=new RefDblLnkNode<E>(val);
    }else{
      tail.next=tail=new RefDblLnkNode<E>(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(E val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,E val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new RefDblLnkNode<E>(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        RefDblLnkNode<E> before;
        (before=tail.prev).next=before=new RefDblLnkNode<E>(before,val,tail);
        tail.prev=before;
      }
    }else{
      RefDblLnkNode<E> head;
      if((head=this.head)==null){
        this.head=head=new RefDblLnkNode<E>(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new RefDblLnkNode<E>(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        RefDblLnkNode<E> after;
        (after=head.next).prev=after=new RefDblLnkNode<E>(head,val,after);
        head.next=after;
      }
    }
  }
  private RefDblLnkNode<E> getNode(int index,int size){
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
  @Override public E set(int index,E val){
    RefDblLnkNode<E> tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,E val){
    getNode(index,size).val=val;
  }
  @Override public E get(int index){
    return getNode(index,size).val;
  }
  @Override public E remove(int index){
    final E ret;
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
        ret=(tail=RefDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        RefDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=RefDblLnkNode.uncheckedIterateAscending(head,index)).val;
        RefDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(Consumer<? super E> action){
    final RefDblLnkNode<E> head;
    if((head=this.head)!=null){
      RefDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      RefDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      RefDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public Object[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Object[] dst;
      RefDblLnkNode.uncheckedCopyInto(dst=new Object[size],tail,size);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  @Override public void replaceAll(UnaryOperator<E> operator){
    final RefDblLnkNode<E> head;
    if((head=this.head)!=null){
      RefDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void sort(Comparator<? super E> sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
        }
        else
        {
          {
            RefSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
          RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
          RefSortUtil.uncheckedStableDescendingSort(tmp,0,size);
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableAscendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
          RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableDescendingSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
          RefSortUtil.uncheckedUnstableDescendingSort(tmp,0,size);
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(Comparator<? super E> sorter){
    final int size;
    if((size=this.size)>1)
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
        if(sorter==null)
        {
          RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
        }
        else
        {
          {
            RefSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final RefDblLnkNode<E> head;
    if((head=this.head)!=null){
      final StringBuilder builder;
      RefDblLnkNode.uncheckedToString(head,tail,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public int hashCode(){
    final RefDblLnkNode<E> head;
    if((head=this.head)!=null){
      return RefDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  private static class UncheckedSubList<E> extends RefDblLnkSeq<E>
  {
    private static final long serialVersionUID=1L;
    transient final UncheckedList<E> root;
    transient final UncheckedSubList<E> parent;
    transient final int rootOffset;
    private UncheckedSubList(UncheckedList<E> root,int rootOffset,RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList<E> parent,int rootOffset,RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
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
    private void bubbleUpClearBody(RefDblLnkNode<E> before,RefDblLnkNode<E> head,int numRemoved,RefDblLnkNode<E> tail,RefDblLnkNode<E> after){
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
    private void bubbleUpClearHead(RefDblLnkNode<E> tail, RefDblLnkNode<E> after,int numRemoved){
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
    private void bubbleUpClearTail(RefDblLnkNode<E> head, RefDblLnkNode<E> before,int numRemoved){
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
        final UncheckedList<E> root;
        (root=this.root).size-=size;
        RefDblLnkNode<E> before,head,tail,after=(tail=this.tail).next;
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
  private static class CheckedSubList<E> extends RefDblLnkSeq<E>
  {
    private static final long serialVersionUID=1L;
    transient final CheckedList<E> root;
    transient final CheckedSubList<E> parent;
    transient final int parentOffset;
    transient int modCount;
     private CheckedSubList(CheckedList<E> root,int rootOffset,RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList<E> parent,int parentOffset,RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
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
    private void bubbleUpClearBody(RefDblLnkNode<E> before,RefDblLnkNode<E> head,int numRemoved,RefDblLnkNode<E> tail,RefDblLnkNode<E> after){
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
    private void bubbleUpClearHead(RefDblLnkNode<E> tail, RefDblLnkNode<E> after,int numRemoved){
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
    private void bubbleUpClearTail(RefDblLnkNode<E> head, RefDblLnkNode<E> before,int numRemoved){
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
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        root.size-=size;
        RefDblLnkNode<E> before,head,tail,after=(tail=this.tail).next;
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
    @Override public void replaceAll(UnaryOperator<E> operator){
      int modCount=this.modCount;
      final CheckedList<E> root;
      try{
        final RefDblLnkNode<E> head;
        if((head=this.head)==null){
          return;
        }
        RefDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(Consumer<? super E> action){
      final int modCount=this.modCount;
      try{
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null){
          RefDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super E> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
          if(sorter==null)
          {
            RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
          }
          else
          {
            {
              RefSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
          }
        }
        finally
        {
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
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
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedStableDescendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedUnstableDescendingSort(tmp,0,size);
        }
        finally
        {
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(Comparator<? super E> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
          if(sorter==null)
          {
            RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
          }
          else
          {
            {
              RefSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
          }
        }
        finally
        {
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
  }
  public static class CheckedList<E> extends UncheckedList<E>{
    transient int modCount;
    public CheckedList(){
    }
    CheckedList(RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
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
    @Override public E set(int index,E val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      RefDblLnkNode<E> tmp;
      final var ret=(tmp=((RefDblLnkSeq<E>)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,E val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((RefDblLnkSeq<E>)this).getNode(index,size).val=val;
    }
    @Override public E get(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((RefDblLnkSeq<E>)this).getNode(index,size).val;
    }
    @Override public E remove(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final E ret;
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
          ret=(tail=RefDblLnkNode.uncheckedIterateDescending(tail,size)).val;
          RefDblLnkNode.eraseNode(tail);
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          ret=(head=RefDblLnkNode.uncheckedIterateAscending(head,index)).val;
          RefDblLnkNode.eraseNode(head);
        }
      }
      return ret;
    }
    @Override public void add(int index,E val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=size+1;
      if((size-=index)<=index){
        var tail=this.tail;
        if(size==0){
          tail.next=tail=new RefDblLnkNode<E>(tail,val);
          this.tail=tail;
        }else{
          while(--size!=0){
            tail=tail.prev;
          }
          RefDblLnkNode<E> before;
          (before=tail.prev).next=before=new RefDblLnkNode<E>(before,val,tail);
          tail.prev=before;
        }
      }else{
        RefDblLnkNode<E> head;
        if((head=this.head)==null){
          this.head=head=new RefDblLnkNode<E>(val);
          this.tail=head;
        }else if(index==0){
          head.prev=head=new RefDblLnkNode<E>(val,head);
          this.head=head;
        }else{
          while(--index!=0){
            head=head.next;
          }
          RefDblLnkNode<E> after;
          (after=head.next).prev=after=new RefDblLnkNode<E>(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void forEach(Consumer<? super E> action)
    {
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          RefDblLnkNode.uncheckedForEachAscending(head,this.size,action);
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
    @Override public void replaceAll(UnaryOperator<E> operator){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null)
      {
        final int modCount=this.modCount;
        try{
          RefDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    private void pullSurvivorsDown(RefDblLnkNode<E> prev,Predicate<? super E> filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(RefDblLnkNode<E> prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(RefDblLnkNode<E> prev,Predicate<? super E> filter,int numLeft,int modCount){
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
    @Override boolean uncheckedRemoveIf(RefDblLnkNode<E> head,Predicate<? super E> filter){
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
    @Override public void sort(Comparator<? super E> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
          if(sorter==null)
          {
            RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
          }
          else
          {
            {
              RefSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedStableDescendingSort(tmp,0,size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedUnstableDescendingSort(tmp,0,size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(Comparator<? super E> sorter){
      final int size;
      if((size=this.size)>1)
      {
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
          if(sorter==null)
          {
            RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
          }
          else
          {
            {
              RefSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public String toString(){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        final StringBuilder builder=new StringBuilder("[");
        final int modCount=this.modCount;
        try{
          RefDblLnkNode.uncheckedToString(head,tail,builder);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        return builder.append(']').toString();
      }
      return "[]";
    }
    @Override public int hashCode(){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          return RefDblLnkNode.uncheckedHashCode(head,tail);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
      return 1;
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
        RefDblLnkNode<E> head,newTail;
        final var newHead=newTail=new RefDblLnkNode<E>((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new RefDblLnkNode<E>(newTail,(head=head.next).val),++i){}
        return new CheckedList<E>(newHead,size,newTail);
      }
      return new CheckedList<E>();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    private static class DescendingItr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final CheckedList<E> parent;
      transient int modCount;
      transient RefDblLnkNode<E> curr;
      transient RefDblLnkNode<E> lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList<E> parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList<E> parent,RefDblLnkNode<E> curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public E next(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final RefDblLnkNode<E> curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        RefDblLnkNode<E> lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList<E> parent;
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
              RefDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        if(currIndex>0){
          final int modCount=this.modCount;
          final CheckedList<E> parent;
          try{
            RefDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
          }finally{
            CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
          }
          this.curr=null;
          this.lastRet=parent.head;
          this.currIndex=0;
        }
      }
    }
    private static class BidirectionalItr<E> extends DescendingItr<E> implements OmniListIterator.OfRef<E>
    {
      private BidirectionalItr(CheckedList<E> parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList<E> parent,RefDblLnkNode<E> curr,int currIndex){
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
      @Override public void set(E val){
        final RefDblLnkNode<E> lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(E val){
        final CheckedList<E> parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        RefDblLnkNode<E> newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new RefDblLnkNode<E>(val);
          }else{
            (newNode=parent.tail).next=newNode=new RefDblLnkNode<E>(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new RefDblLnkNode<E>(val,newNode);
          }else{
            final RefDblLnkNode<E> tmp;
            (newNode=curr).prev=newNode=new RefDblLnkNode<E>(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public E previous(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          final RefDblLnkNode<E> curr;
          this.lastRet=curr=this.curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public E next(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final RefDblLnkNode<E> curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        RefDblLnkNode<E> lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList<E> parent;
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
              RefDblLnkNode.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int size,numLeft;
        final CheckedList<E> parent;
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            RefDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      return new DescendingItr<E>(this);
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new BidirectionalItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      return new BidirectionalItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr<E>(this,((RefDblLnkSeq<E>)this).getNode(index,size),index);
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public E getLast(){
      final RefDblLnkNode<E> tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void addLast(E val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(E val){
      ++this.modCount;
      super.push(val);
    }
    @Override public E removeLast(){
      RefDblLnkNode<E> tail;
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
    @Override public E pop(){
      RefDblLnkNode<E> head;
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
    @Override public E element(){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
         return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public int search(Object val){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefDblLnkNode.uncheckedsearchNonNull(head,this.size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkNode.uncheckedsearchNull(head);
      }
      return -1;
    }
    @Override public boolean contains(Object val){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefDblLnkNode.uncheckedcontainsNonNull(head,this.size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkNode.uncheckedcontainsNull(head,tail);
      }
      return false;
    }
    @Override public int indexOf(Object val){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefDblLnkNode.uncheckedindexOfNonNull(head,this.size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkNode.uncheckedindexOfNull(head,tail);
      }
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      final RefDblLnkNode<E> tail;
      if((tail=this.tail)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefDblLnkNode.uncheckedlastIndexOfNonNull(this.size,tail,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkNode.uncheckedlastIndexOfNull(this.size,tail);
      }
      return -1;
    }
    boolean uncheckedremoveLastOccurrenceNonNull(RefDblLnkNode<E> tail
    ,Object nonNull
    ){
      int modCount=this.modCount;
      try
      {
        if(nonNull.equals(tail.val)){
          CheckedCollection.checkModCount(modCount,this.modCount);
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
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
          if(nonNull.equals(tail.val)){
            CheckedCollection.checkModCount(modCount,this.modCount);
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
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    boolean uncheckedremoveLastOccurrenceNull(RefDblLnkNode<E> tail
    ){
      {
        if(null==(tail.val)){
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
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
          if(null==(tail.val)){
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
    boolean uncheckedremoveLastOccurrence(RefDblLnkNode<E> tail
    ,Predicate<? super E> pred
    ){
      {
        if(pred.test(tail.val)){
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
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
          if(pred.test(tail.val)){
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
    boolean uncheckedremoveValNonNull(RefDblLnkNode<E> head
    ,Object nonNull
    ){
      int modCount=this.modCount;
      try
      {
        if(nonNull.equals(head.val)){
          CheckedCollection.checkModCount(modCount,this.modCount);
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
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
          if(nonNull.equals(head.val)){
            CheckedCollection.checkModCount(modCount,this.modCount);
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
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    boolean uncheckedremoveValNull(RefDblLnkNode<E> head
    ){
      {
        if(null==(head.val)){
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
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
          if(null==(head.val)){
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
    boolean uncheckedremoveVal(RefDblLnkNode<E> head
    ,Predicate<? super E> pred
    ){
      {
        if(pred.test(head.val)){
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
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
          if(pred.test(head.val)){
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
    @Override public E poll(){
      RefDblLnkNode<E> head;
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
    @Override public E pollLast(){
      RefDblLnkNode<E> tail;
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
  public static class UncheckedList<E> extends RefDblLnkSeq<E> implements OmniDeque.OfRef<E>,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
      super(head,size,tail);
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        do{
          out.writeObject(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @SuppressWarnings("unchecked")
    @Override public void readExternal(ObjectInput in) throws IOException
      ,ClassNotFoundException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        RefDblLnkNode<E> curr;
        for(this.head=curr=new RefDblLnkNode<E>((E)in.readObject());--size!=0;curr=curr.next=new RefDblLnkNode<E>(curr,(E)in.readObject())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        RefDblLnkNode<E> head,newTail;
        final var newHead=newTail=new RefDblLnkNode<E>((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new RefDblLnkNode<E>(newTail,(head=head.next).val),++i){}
        return new UncheckedList<E>(newHead,size,newTail);
      }
      return new UncheckedList<E>();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public boolean contains(boolean val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontainsNonNull(head,tail,val);
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Boolean val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Byte val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Character val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Short val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Integer val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Long val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Float val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Double val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOfNonNull(head,tail,val);
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(byte val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Boolean val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Byte val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Character val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Short val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Integer val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Long val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Float val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Double val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOfNonNull(size,tail,val);
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(byte val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Boolean val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Byte val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Character val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Short val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Integer val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Long val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Float val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Double val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveValNonNull(head,val);
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Boolean val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Byte val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Character val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Short val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Integer val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Long val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Float val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Double val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveValNonNull(RefDblLnkNode<E> head
    ,Object nonNull
    ){
      {
        if(nonNull.equals(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
          if(nonNull.equals(head.val)){
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
    boolean uncheckedremoveValNull(RefDblLnkNode<E> head
    ){
      {
        if(null==(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
          if(null==(head.val)){
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
    boolean uncheckedremoveVal(RefDblLnkNode<E> head
    ,Predicate<? super E> pred
    ){
      {
        if(pred.test(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
          if(pred.test(head.val)){
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
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      return new DescendingItr<E>(this);
    }
    private static class AscendingItr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final UncheckedList<E> parent;
      transient RefDblLnkNode<E> curr;
      private AscendingItr(UncheckedList<E> parent,RefDblLnkNode<E> curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedList<E> parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public void remove(){
        final UncheckedList<E> parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          RefDblLnkNode<E> curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            RefDblLnkNode<E> lastRet;
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
      @Override public E next(){
        final RefDblLnkNode<E> curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final RefDblLnkNode<E> curr;
        if((curr=this.curr)!=null){
          RefDblLnkNode.uncheckedForEachAscending(curr,action);
          this.curr=null;
        }
      }
    }
    private static class DescendingItr<E> extends AscendingItr<E>{
      private DescendingItr(UncheckedList<E> parent){
        super(parent,parent.tail);
      }
      @Override public void remove(){
        final UncheckedList<E> parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          RefDblLnkNode<E> curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            RefDblLnkNode<E> lastRet;
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
      @Override public E next(){
        final RefDblLnkNode<E> curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final RefDblLnkNode<E> curr;
        if((curr=this.curr)!=null){
          RefDblLnkNode.uncheckedForEachDescending(curr,action);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr<E> extends AscendingItr<E> implements OmniListIterator.OfRef<E>{
      transient int currIndex;
      transient RefDblLnkNode<E> lastRet;
      private BidirectionalItr(UncheckedList<E> parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList<E> parent,RefDblLnkNode<E> curr,int currIndex){
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
      @Override public void add(E val){
        final UncheckedList<E> parent;
        RefDblLnkNode<E> newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new RefDblLnkNode<E>(val);
          }else{
            (newNode=parent.tail).next=newNode=new RefDblLnkNode<E>(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new RefDblLnkNode<E>(val,newNode);
          }else{
            final RefDblLnkNode<E> tmp;
            (newNode=curr).prev=newNode=new RefDblLnkNode<E>(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(E val){
        lastRet.val=val;
      }
      @Override public E previous(){
        final RefDblLnkNode<E> curr;
        this.lastRet=curr=this.curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public E next(){
        final RefDblLnkNode<E> curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public void remove(){
        RefDblLnkNode<E> lastRet;
        if((lastRet=this.lastRet).next==curr){
          --currIndex;
        }
        final UncheckedList<E> parent;
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
            RefDblLnkNode.eraseNode(lastRet);
          }
        }
        this.lastRet=null;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final RefDblLnkNode<E> curr;
        if((curr=this.curr)!=null){
          RefDblLnkNode.uncheckedForEachAscending(curr,action);
          final UncheckedList<E> parent;
          this.lastRet=(parent=this.parent).tail;
          this.currIndex=parent.size;
          this.curr=null;
        }
      }
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new AscendingItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      return new BidirectionalItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      return new BidirectionalItr<E>(this,((RefDblLnkSeq<E>)this).getNode(index,this.size),index);
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      //TODO
      return null;
    }
    @Override public E getLast(){
      return tail.val;
    }
    @Override public boolean offerFirst(E val){
      push((E)val);
      return true;
    }
    @Override public boolean offerLast(E val){
      addLast((E)val);
      return true;
    }
    @Override public void addFirst(E val){
      push((E)val);
    }
    @Override public E removeFirst(){
      return pop();
    }
    @Override public void push(E val){
      RefDblLnkNode<E> head;
      if((head=this.head)==null){
        this.head=tail=new RefDblLnkNode<E>(val);
      }else{
        head.prev=head=new RefDblLnkNode<E>(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public E removeLast(){
      RefDblLnkNode<E> tail;
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
    @Override public E pop(){
      RefDblLnkNode<E> head;
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
    @Override public E element(){
      return head.val;
    }
    @Override public boolean offer(E val){
      addLast((E)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearchNonNull(head,val);
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Boolean val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Byte val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Character val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Short val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Integer val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Long val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Float val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Double val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(double val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrenceNonNull(tail,val);
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(byte val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(char val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(short val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Boolean val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Byte val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Character val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Short val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Integer val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Long val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Float val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Double val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrenceNonNull(RefDblLnkNode<E> tail
    ,Object nonNull
    ){
      {
        if(nonNull.equals(tail.val)){
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
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
          if(nonNull.equals(tail.val)){
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
    boolean uncheckedremoveLastOccurrenceNull(RefDblLnkNode<E> tail
    ){
      {
        if(null==(tail.val)){
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
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
          if(null==(tail.val)){
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
    boolean uncheckedremoveLastOccurrence(RefDblLnkNode<E> tail
    ,Predicate<? super E> pred
    ){
      {
        if(pred.test(tail.val)){
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
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
          if(pred.test(tail.val)){
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
    @Override public E remove(){
      return pop();
    }
    @Override public E pollFirst(){
      return poll();
    }
    @Override public E peekFirst(){
      return peek();
    }
    @Override public E getFirst(){
      return element();
    }
    @Override public E poll(){
      RefDblLnkNode<E> head;
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
    @Override public E pollLast(){
      RefDblLnkNode<E> tail;
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
    @Override public E peek(){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public E peekLast(){
      final RefDblLnkNode<E> tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public boolean removeIf(Predicate<? super E> filter){
      final RefDblLnkNode<E> head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    private int removeIfHelper(RefDblLnkNode<E> prev,RefDblLnkNode<E> tail,Predicate<? super E> filter){
      int numSurvivors=1;
      outer:for(RefDblLnkNode<E> next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(RefDblLnkNode<E> prev,RefDblLnkNode<E> curr,RefDblLnkNode<E> tail,Predicate<? super E> filter){
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
    boolean uncheckedRemoveIf(RefDblLnkNode<E> head,Predicate<? super E> filter){
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
          final RefDblLnkNode<E> prev;
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
