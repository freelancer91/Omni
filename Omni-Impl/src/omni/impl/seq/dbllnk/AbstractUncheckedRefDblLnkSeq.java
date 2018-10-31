package omni.impl.seq.dbllnk;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.util.OmniArray;
import omni.util.OmniPred;
abstract class AbstractUncheckedRefDblLnkSeq<E>extends AbstractRefDblLnkSeq<E>{
  static void decrementSize(AbstractUncheckedRefDblLnkSeq<?> curr){
    do{
      --curr.size;
    }while((curr=curr.getParent())!=null);
  }
  static void decrementSize(AbstractUncheckedRefDblLnkSeq<?> curr,int numRemoved){
    do{
      curr.size-=numRemoved;
    }while((curr=curr.getParent())!=null);
  }
  static void incrementSize(AbstractUncheckedRefDblLnkSeq<?> curr){
    do{
      ++curr.size;
    }while((curr=curr.getParent())!=null);
  }
  static <E> void uncheckedAppend(AbstractUncheckedRefDblLnkSeq<E> curr,Node<E> oldTail,final Node<E> newTail){
    do{
      ++curr.size;
      curr.tail=newTail;
    }while((curr=curr.getParent()).tail==oldTail);
    incrementSize(curr);
  }
  static <E> void uncheckedPrepend(AbstractUncheckedRefDblLnkSeq<E> curr,Node<E> oldHead,final Node<E> newHead){
    do{
      ++curr.size;
      curr.head=newHead;
    }while((curr=curr.getParent()).head==oldHead);
    incrementSize(curr);
  }
  AbstractUncheckedRefDblLnkSeq(){
    super();
  }
  AbstractUncheckedRefDblLnkSeq(Node<E> headAndTail){
    super(headAndTail);
  }
  AbstractUncheckedRefDblLnkSeq(Node<E> head,int size,Node<E> tail){
    super(head,size,tail);
  }
  @Override public boolean add(E val){
    super.addLast(val);
    return true;
  }
  @Override public void add(int index,E val){
    int size;
    if((size=this.size)!=0){
      int tailDist;
      if((tailDist=size-index)==0){
        uncheckedAppend(tail,val);
      }else{
        Node<E> before,after;
        if(index>=tailDist){
          after=uncheckedIterateReverse(tail,tailDist);
          before=after.prev;
        }else{
          before=head;
          if(index==0){
            uncheckedPrepend(before,val);
            return;
          }else{
            before=uncheckedIterateForward(before,index);
            after=before.next;
          }
        }
        new Node<>(before,val,after);
        incrementSize(this);
      }
    }else{
      uncheckedInit(val);
    }
  }
  @Override public Object clone(){
    Node<E> newHead,newTail;
    int size;
    if((size=this.size)!=0){
      Node<E> oldHead,oldTail;
      for(oldHead=head,oldTail=tail,newHead=new Node<>(oldHead.val),newTail=newHead;oldHead!=oldTail;
          newTail=new Node<>(newTail,(oldHead=oldHead.next).val)){}
    }else{
      newHead=null;
      newTail=null;
    }
    return new UncheckedRefDblLnkList<>(newHead,size,newTail);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public void forEach(Consumer<? super E> action){
    Node<E> head;
    if((head=this.head)!=null){
      uncheckedForEachForward(head,tail,action);
    }
  }
  @Override public E get(int index){
    return super.getReadableNode(index,size-index).val;
  }
  @Override public int hashCode(){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedForwardHashCode(head,tail); }
    return 1;
  }
  @Override public int indexOf(Object val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public OmniIterator.OfRef<E> iterator(){
    return new AbstractUncheckedRefDblLnkSeq.AscendingItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(){
    return new AbstractUncheckedRefDblLnkSeq.BidirectionalItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(int index){
    return new AbstractUncheckedRefDblLnkSeq.BidirectionalItr<>(this,index,super.getWritableNode(index,size-index));
  }
  @Override public void put(int index,E val){
    getReadableNode(index,size-index).val=val;
  }
  @Override public E remove(int index){
    int tailDist;
    if(index<=(tailDist=size-1-index)){
      return iterateForwardAndRemove(index,tailDist);
    }else{
      return iterateReverseAndRemove(index,tailDist);
    }
  }
  @Override public boolean remove(Object val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeIf(Predicate<? super E> filter){
    Node<E> curr;
    if((curr=head)!=null){
      final var oldTail=tail;
      if(filter.test(curr.val)){
        findNewHead(curr,oldTail,filter);
        return true;
      }else{
        while(curr!=oldTail){
          Node<E> newTail;
          if(filter.test((curr=(newTail=curr).next).val)){
            beginRemovingNodes(newTail,curr,oldTail,filter);
            return true;
          }
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(boolean val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Boolean val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(byte val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Byte val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(char val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Character val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(double val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Double val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(float val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Float val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(int val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Integer val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(long val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Long val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(short val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Short val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public void replaceAll(UnaryOperator<E> operator){
    Node<E> head;
    if((head=this.head)!=null){
      uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void reverseSort(){
    int size;
    if((size=this.size)>1){
      uncheckedReverseSort(head,size,tail);
    }
  }
  @Override public E set(int index,E val){
    Node<E> node;
    final var oldVal=(node=getReadableNode(index,size-index)).val;
    node.val=val;
    return oldVal;
  }
  @Override public void sort(){
    int size;
    if((size=this.size)>1){
      uncheckedSort(head,size,tail);
    }
  }
  @Override public void sort(Comparator<? super E> sorter){
    int size;
    if((size=this.size)>1){
      uncheckedSort(head,size,tail,sorter);
    }
  }
  @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
    final int tailDist=size-toIndex;
    int size;
    switch(size=toIndex-fromIndex){
    case 0:
      return getEmptySubList(fromIndex,tailDist);
    case 1:
      return getSubList1(fromIndex,tailDist);
    default:
      return getDefaultSublist(fromIndex,size,tailDist);
    }
  }
  @Override public Object[] toArray(){
    int size;
    if((size=this.size)!=0){
      Object[] dst;
      uncheckedCopyIntoArray(head,size,dst=new Object[size],0);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      uncheckedCopyIntoArray(head,size,dst,0);
    }
    return dst;
  }
  @Override public <T> T[] toArray(T[] dst){
    int size;
    if((size=this.size)!=0){
      uncheckedCopyIntoArray(head,size,dst=OmniArray.uncheckedArrResize(size,dst),0);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public String toString(){
    Node<E> head;
    if((head=this.head)!=null){
      final StringBuilder builder;
      uncheckedForwardToString(head,tail,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override protected int uncheckedLastIndexOfMatch(int size,Predicate<Object> pred){
    final var tail=this.tail;
    while(!pred.test(tail.val)&&--size!=0){}
    return size-1;
  }
  @Override protected int uncheckedLastIndexOfNonNull(int size,Object nonNull){
    final var tail=this.tail;
    while(!nonNull.equals(tail.val)&&--size!=0){}
    return size-1;
  }
  OmniList.OfRef<E> getDefaultSublist(int headDist,int size,int tailDist){
    Node<E> head,tail;
    if(tailDist!=0){
      if(headDist!=0){
        if(tailDist<=headDist){
          tail=uncheckedIterateReverse(this.tail,tailDist);
          head=size<=headDist?uncheckedIterateReverse(tail,size-1):uncheckedIterateForward(this.head,headDist);
        }else{
          head=uncheckedIterateForward(this.head,headDist);
          tail=size<=tailDist?uncheckedIterateForward(head,size-1):uncheckedIterateReverse(this.tail,tailDist);
        }
        return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(head,size,tail,this,headDist);
      }else{
        return new AbstractUncheckedRefDblLnkSeq.PrefixSubList<>(head=this.head,size,
            size<=tailDist?uncheckedIterateForward(head,size-1):uncheckedIterateReverse(this.tail,tailDist),this);
      }
    }else{
      tail=this.tail;
      if(headDist!=0){
        return new AbstractUncheckedRefDblLnkSeq.SuffixSubList<>(
            size<=headDist?uncheckedIterateReverse(tail,size-1):uncheckedIterateForward(this.head,headDist),size,tail,
            this);
      }else{
        return new AbstractUncheckedRefDblLnkSeq.RootSubList<>(this.head,size,tail,this);
      }
    }
  }
  OmniList.OfRef<E> getEmptySubList(int headDist,int tailDist){
    if(headDist!=0){
      if(tailDist!=0){ return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(this,headDist); }
      return new AbstractUncheckedRefDblLnkSeq.SuffixSubList<>(this);
    }else{
      if(tailDist!=0){
        return new AbstractUncheckedRefDblLnkSeq.PrefixSubList<>(this);
      }else{
        return new AbstractUncheckedRefDblLnkSeq.RootSubList<>(this);
      }
    }
  }
  AbstractUncheckedRefDblLnkSeq<E> getParent(){
    return null;
  }
  int getParentOffset(){
    return 0;
  }
  OmniList.OfRef<E> getSubList1(int headDist,int tailDist){
    if(headDist>=tailDist){
      final var tail=this.tail;
      if(tailDist!=0){
        return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(uncheckedIterateReverse(tail,tailDist),this,headDist);
      }else{
        if(headDist!=0){
          return new AbstractUncheckedRefDblLnkSeq.SuffixSubList<>(tail,this);
        }else{
          return new AbstractUncheckedRefDblLnkSeq.RootSubList<>(tail,this);
        }
      }
    }else{
      final var head=this.head;
      if(headDist!=0){
        return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(uncheckedIterateForward(head,headDist),this,headDist);
      }else{
        return new AbstractUncheckedRefDblLnkSeq.PrefixSubList<>(head,this);
      }
    }
  }
  void setNewHead(Node<E> newHead,int numRemoved){
    newHead.prev=null;
    head=newHead;
    size-=numRemoved;
  }
  void setNewHeadAndTail(Node<E> newHead,int numRemoved,Node<E> newTail){
    newHead.prev=null;
    newTail.next=null;
    head=newHead;
    tail=newTail;
    size-=numRemoved;
  }
  void setNewTail(Node<E> newTail,int numRemoved){
    newTail.next=null;
    tail=newTail;
    size-=numRemoved;
  }
  @Override void uncheckedAppend(Node<E> oldTail,E val){
    ++size;
    tail=new Node<>(oldTail,val);
  }
  @Override void uncheckedClear(){
    head=null;
    tail=null;
    size=0;
  }
  @Override void uncheckedInit(E val){
    final Node<E> node;
    head=node=new Node<>(val);
    tail=node;
    size=1;
  }
  @Override void uncheckedPrepend(Node<E> oldHead,E val){
    ++size;
    head=new Node<>(val,oldHead);
  }
  @Override void uncheckedRemoveFirst(Node<E> oldHead){
    --size;
    head=oldHead=oldHead.next;
    oldHead.prev=null;
  }
  @Override void uncheckedRemoveLast(Node<E> oldTail){
    --size;
    tail=oldTail=oldTail.prev;
    oldTail.next=null;
  }
  private void beginRemovingNodes(Node<E> newTail,Node<E> curr,Node<E> oldTail,Predicate<? super E> filter){
    int numRemoved=1;
    while(curr!=oldTail){
      if(!filter.test((curr=curr.next).val)){
        joinNodes(newTail,curr);
        do{
          if(curr==oldTail){
            decrementSize(this,numRemoved);
            return;
          }
        }while(!filter.test((curr=(newTail=curr).next).val));
      }
      ++numRemoved;
    }
    setNewTail(newTail,numRemoved);
  }
  private void findNewHead(Node<E> newHead,Node<E> oldTail,Predicate<? super E> filter){
    for(int numRemoved=1;newHead!=oldTail;++numRemoved){
      if(!filter.test((newHead=newHead.next).val)){
        var curr=newHead;
        while(curr!=oldTail){
          final Node<E> newTail;
          if(filter.test((curr=(newTail=curr).next).val)){
            do{
              ++numRemoved;
              if(curr==oldTail){
                setNewHeadAndTail(newHead,numRemoved,newTail);
                return;
              }
            }while(filter.test((curr=curr.next).val));
            joinNodes(newTail,curr);
          }
        }
        setNewHead(newHead,numRemoved);
        return;
      }
      ++numRemoved;
    }
    uncheckedClear();
    return;
  }
  private E iterateForwardAndRemove(int index,int tailDist){
    Node<E> node,before=head;
    switch(index){
    case 0:
      if(tailDist==0){
        uncheckedClear();
      }else{
        uncheckedRemoveFirst(before);
      }
      return before.val;
    default:
      before=uncheckedIterateForward(before,index-1);
    case 1:
      joinNodes(before,(node=before.next).next);
      decrementSize(this);
    }
    return node.val;
  }
  private E iterateReverseAndRemove(int index,int tailDist){
    Node<E> node,after=tail;
    switch(tailDist){
    case 0:
      uncheckedRemoveFirst(after);
      return after.val;
    default:
      after=uncheckedIterateReverse(after,index-1);
    case 1:
      joinNodes((node=after.prev).prev,after);
      decrementSize(this);
    }
    return node.val;
  }
  private boolean uncheckedRemoveFirstMatch(Node<E> head,Predicate<? super E> pred){
    final var tail=this.tail;
    if(pred.test(head.val)){
      if(head==tail){
        uncheckedClear();
      }else{
        uncheckedRemoveFirst(head);
      }
      return true;
    }else{
      do{
        if(head==tail){ return false; }
      }while(!pred.test((head=head.next).val));
      if(head==tail){
        uncheckedRemoveLast(head);
      }else{
        joinNodes(head.prev,head.next);
        decrementSize(this);
      }
    }
    return true;
  }
  static class AscendingItr<E> implements OmniIterator.OfRef<E>{
    transient final AbstractUncheckedRefDblLnkSeq<E> parent;
    transient Node<E> cursor;
    AscendingItr(AbstractUncheckedRefDblLnkSeq<E> parent){
      this.parent=parent;
      this.cursor=parent.head;
    }
    @Override public void forEachRemaining(Consumer<? super E> action){
      Node<E> cursor;
      if((cursor=this.cursor)!=null){
        uncheckedForEachForward(cursor,parent.tail,action);
        this.cursor=null;
      }
    }
    @Override public boolean hasNext(){
      return this.cursor!=null;
    }
    @Override public E next(){
      Node<E> cursor;
      final var ret=(cursor=this.cursor).val;
      if(cursor==parent.tail){
        this.cursor=null;
      }else{
        this.cursor=cursor.next;
      }
      return ret;
    }
    @Override public void remove(){
      final var parent=this.parent;
      Node<E> cursor;
      if((cursor=this.cursor)!=null){
        Node<E> lastRet;
        if((lastRet=cursor.prev)==parent.head){
          parent.uncheckedRemoveFirst(lastRet);
        }else{
          joinNodes(lastRet.prev,cursor);
          decrementSize(parent);
        }
      }else{
        Node<E> tail;
        if((tail=parent.tail)==parent.head){
          parent.uncheckedClear();
        }else{
          parent.uncheckedRemoveLast(tail);
        }
      }
    }
  }
  static class BidirectionalItr<E> implements OmniListIterator.OfRef<E>{
    transient final AbstractUncheckedRefDblLnkSeq<E> parent;
    transient Node<E> cursor;
    transient Node<E> lastRet;
    transient int nextIndex;
    BidirectionalItr(AbstractUncheckedRefDblLnkSeq<E> parent){
      this.parent=parent;
      this.cursor=parent.head;
    }
    BidirectionalItr(AbstractUncheckedRefDblLnkSeq<E> parent,int nextIndex,Node<E> cursor){
      this.parent=parent;
      this.nextIndex=nextIndex;
      this.cursor=cursor;
    }
    @Override public void add(E val){
      AbstractUncheckedRefDblLnkSeq<E> parent;
      int nextIndex,parentSize;
      if((nextIndex=this.nextIndex)==(parentSize=(parent=this.parent).size)){
        if(parentSize==0){
          parent.uncheckedInit(val);
        }else{
          parent.uncheckedAppend(parent.tail,val);
        }
      }else{
        if(nextIndex==0){
          parent.uncheckedPrepend(parent.head,val);
        }else{
          Node<E> cursor;
          new Node<>((cursor=this.cursor).prev,val,cursor);
          incrementSize(parent);
        }
      }
      this.nextIndex=nextIndex+1;
      this.lastRet=null;
    }
    @Override public void forEachRemaining(Consumer<? super E> action){
      AbstractUncheckedRefDblLnkSeq<E> parent;
      int parentSize;
      if(nextIndex!=(parentSize=(parent=this.parent).size)){
        Node<E> bound;
        uncheckedForEachForward(cursor,bound=parent.tail,action);
        this.lastRet=bound;
        this.cursor=null;
        this.nextIndex=parentSize;
      }
    }
    @Override public boolean hasNext(){
      return nextIndex!=parent.size;
    }
    @Override public boolean hasPrevious(){
      return nextIndex!=0;
    }
    @Override public E next(){
      Node<E> cursor;
      this.lastRet=cursor=this.cursor;
      this.cursor=cursor.next;
      ++nextIndex;
      return cursor.val;
    }
    @Override public int nextIndex(){
      return nextIndex;
    }
    @Override public E previous(){
      Node<E> cursor;
      this.lastRet=cursor=this.cursor.prev;
      this.cursor=cursor;
      --nextIndex;
      return cursor.val;
    }
    @Override public int previousIndex(){
      return nextIndex-1;
    }
    @Override public void remove(){
      final var parent=this.parent;
      Node<E> lastRet;
      if((lastRet=this.lastRet)==parent.head){
        if(lastRet==parent.tail){
          parent.uncheckedClear();
        }else{
          parent.uncheckedRemoveFirst(lastRet);
        }
      }else if(lastRet==parent.tail){
        parent.uncheckedRemoveLast(lastRet);
      }else{
        joinNodes(lastRet.prev,lastRet.next);
        decrementSize(parent);
      }
      if(lastRet!=this.cursor){
        --nextIndex;
      }
      this.lastRet=null;
    }
    @Override public void set(E val){
      lastRet.val=val;
    }
  }
  static class BodySubList<E>extends AbstractUncheckedRefDblLnkSeq.PrefixSubList<E>{
    private static <E> void uncheckedInsertNode(Node<E> node,AbstractUncheckedRefDblLnkSeq<E> curr,int headDist,
        int tailDist){
      Node<E> before,after;
      if(tailDist<=headDist){
        after=curr.tail;
        switch(tailDist){
        case 0:
          uncheckedAppend(curr,before=after,node);
          after=after.next;
          break;
        default:
          after=uncheckedIterateReverse(after,tailDist-1);
        case 1:
          before=after.prev;
          incrementSize(curr);
        }
      }else{
        before=curr.head;
        switch(headDist){
        case 0:
          uncheckedPrepend(curr,after=before,node);
          before=before.prev;
        default:
          before=uncheckedIterateForward(before,headDist-1);
        case 1:
          after=before.next;
          incrementSize(curr);
        }
      }
      joinNodes(before,node);
      joinNodes(node,after);
    }
    transient final int parentOffset;
    BodySubList(Node<E> head,int size,Node<E> tail,AbstractUncheckedRefDblLnkSeq<E> parent,int parentOffset){
      super(head,size,tail,parent);
      this.parentOffset=parentOffset;
    }
    BodySubList(Node<E> headAndTail,AbstractUncheckedRefDblLnkSeq<E> parent,int parentOffset){
      super(headAndTail,parent);
      this.parentOffset=parentOffset;
    }
    BodySubList(AbstractUncheckedRefDblLnkSeq<E> parent,int parentOffset){
      super(parent);
      this.parentOffset=parentOffset;
    }
    @Override OmniList.OfRef<E> getDefaultSublist(int headDist,int size,int tailDist){
      Node<E> head,tail;
      if(headDist!=0){
        if(tailDist<=headDist){
          tail=this.tail;
          if(tailDist!=0){
            tail=uncheckedIterateReverse(tail,tailDist);
          }
          head=size<=headDist?uncheckedIterateReverse(tail,size-1):uncheckedIterateForward(this.head,headDist);
        }else{
          head=uncheckedIterateForward(this.head,headDist);
          tail=size<=tailDist?uncheckedIterateForward(head,size-1):uncheckedIterateReverse(this.tail,tailDist);
        }
      }else{
        head=this.head;
        if(size<=tailDist){
          tail=uncheckedIterateForward(head,size-1);
        }else{
          tail=this.tail;
          if(tailDist!=0){
            tail=uncheckedIterateReverse(tail,tailDist);
          }
        }
      }
      return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(head,size,tail,this,headDist);
    }
    @Override OmniList.OfRef<E> getEmptySubList(int headDist,int tailDist){
      return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(this,headDist);
    }
    @Override int getParentOffset(){
      return this.parentOffset;
    }
    @Override OmniList.OfRef<E> getSubList1(int headDist,int tailDist){
      Node<E> node;
      if(headDist>=tailDist){
        node=tail;
        if(tailDist!=0){
          node=uncheckedIterateReverse(node,tailDist);
        }
      }else{
        node=head;
        if(headDist!=0){
          node=uncheckedIterateForward(node,headDist);
        }
      }
      return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(node,this,headDist);
    }
    @Override void setNewHead(Node<E> newHead,int numRemoved){
      AbstractUncheckedRefDblLnkSeq<E> curr;
      Node<E> oldHead;
      joinNodes((oldHead=(curr=this).head).prev,newHead);
      do{
        curr.head=newHead;
        curr.size-=numRemoved;
      }while((curr=curr.getParent()).head==oldHead);
      decrementSize(curr,numRemoved);
    }
    @Override void setNewHeadAndTail(Node<E> newHead,int numRemoved,Node<E> newTail){
      AbstractUncheckedRefDblLnkSeq<E> curr;
      Node<E> oldTail,oldHead;
      joinNodes((oldHead=(curr=this).head).prev,newHead);
      joinNodes(newTail,(oldTail=tail).next);
      for(;;){
        curr.head=newHead;
        curr.tail=newTail;
        curr.size-=numRemoved;
        if((curr=curr.getParent()).head!=oldHead){
          while(curr.tail==oldTail){
            curr.size-=numRemoved;
            curr.tail=newTail;
            curr=curr.getParent();
          }
          break;
        }else if(curr.tail!=oldTail){
          do{
            curr.size-=numRemoved;
            curr.head=newHead;
          }while((curr=curr.getParent()).head==oldHead);
          break;
        }
      }
      decrementSize(curr,numRemoved);
    }
    @Override void uncheckedClear(){
      AbstractUncheckedRefDblLnkSeq<E> curr;
      final Node<E> oldHead,oldTail,prev,next;
      final int numRemoved=(curr=this).size;
      joinNodes(prev=(oldHead=curr.head).prev,next=(oldTail=tail).next);
      for(;;){
        curr.head=null;
        curr.tail=null;
        curr.size=0;
        if((curr=curr.getParent()).head!=oldHead){
          while(curr.tail==oldTail){
            curr.size-=numRemoved;
            curr.tail=prev;
            curr=curr.getParent();
          }
          break;
        }else if(curr.tail!=oldTail){
          do{
            curr.size-=numRemoved;
            curr.head=next;
          }while((curr=curr.getParent()).head==oldHead);
          break;
        }
      }
      decrementSize(curr,numRemoved);
    }
    @Override void uncheckedInit(E val){
      final var node=new Node<>(val);
      AbstractUncheckedRefDblLnkSeq<E> curr=this;
      int headDist=this.parentOffset;
      int currSize;
      for(;;){
        curr.head=node;
        curr.tail=node;
        curr.size=1;
        if((currSize=(curr=curr.getParent()).size)!=0){
          break;
        }
        headDist=curr.getParentOffset();
      }
      uncheckedInsertNode(node,curr,headDist,currSize-headDist);
    }
    @Override void uncheckedPrepend(Node<E> oldHead,E val){
      final var newHead=new Node<>(oldHead.prev,val,oldHead);
      AbstractUncheckedRefDblLnkSeq<E> curr=this;
      do{
        ++curr.size;
        curr.head=newHead;
      }while((curr=curr.getParent()).head==oldHead);
      incrementSize(curr);
    }
    @Override void uncheckedRemoveFirst(Node<E> oldHead){
      final Node<E> newHead;
      joinNodes(oldHead.prev,newHead=oldHead.next);
      AbstractUncheckedRefDblLnkSeq<E> curr=this;
      do{
        curr.head=newHead;
        --curr.size;
      }while((curr=curr.getParent()).head==oldHead);
      decrementSize(curr);
    }
  }
  static class PrefixSubList<E>extends AbstractUncheckedRefDblLnkSeq.RootSubList<E>{
    public PrefixSubList(Node<E> head,int size,Node<E> tail,AbstractUncheckedRefDblLnkSeq<E> parent){
      super(head,size,tail,parent);
    }
    public PrefixSubList(Node<E> headAndTail,AbstractUncheckedRefDblLnkSeq<E> parent){
      super(headAndTail,parent);
    }
    public PrefixSubList(AbstractUncheckedRefDblLnkSeq<E> parent){
      super(parent);
    }
    @Override OmniList.OfRef<E> getDefaultSublist(int headDist,int size,int tailDist){
      if(headDist!=0){
        Node<E> head,tail;
        if(tailDist<=headDist){
          tail=this.tail;
          if(tailDist!=0){
            tail=uncheckedIterateReverse(tail,tailDist);
          }
          head=size<=headDist?uncheckedIterateReverse(tail,size-1):uncheckedIterateForward(this.head,headDist);
        }else{
          head=uncheckedIterateForward(this.head,headDist);
          tail=size<=tailDist?uncheckedIterateForward(head,size-1):uncheckedIterateReverse(this.tail,tailDist);
        }
        return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(head,size,tail,this,headDist);
      }else{
        final var head=this.head;
        Node<E> tail;
        if(size<=tailDist){
          tail=uncheckedIterateForward(head,size-1);
        }else{
          tail=this.tail;
          if(tailDist!=0){
            tail=uncheckedIterateReverse(tail,tailDist);
          }
        }
        return new AbstractUncheckedRefDblLnkSeq.PrefixSubList<>(head,size,tail,this);
      }
    }
    @Override OmniList.OfRef<E> getEmptySubList(int headDist,int tailDist){
      if(headDist!=0){
        return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(this,headDist);
      }else{
        return new AbstractUncheckedRefDblLnkSeq.PrefixSubList<>(this);
      }
    }
    @Override OmniList.OfRef<E> getSubList1(int headDist,int tailDist){
      if(headDist!=0){
        Node<E> node;
        if(headDist>=tailDist){
          node=tail;
          if(tailDist!=0){
            node=uncheckedIterateReverse(node,tailDist);
          }
        }else{
          node=uncheckedIterateForward(head,headDist);
        }
        return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(node,this,headDist);
      }else{
        return new AbstractUncheckedRefDblLnkSeq.PrefixSubList<>(head,this);
      }
    }
    @Override void setNewHeadAndTail(Node<E> newHead,int numRemoved,Node<E> newTail){
      AbstractUncheckedRefDblLnkSeq<E> curr;
      Node<E> oldTail;
      joinNodes(newTail,(oldTail=(curr=this).tail).next);
      do{
        curr.head=newHead;
        curr.size-=numRemoved;
        curr.tail=newTail;
      }while((curr=curr.getParent()).tail==oldTail);
      newHead.prev=null;
      setNewHead(curr,newHead,numRemoved);
    }
    @Override void setNewTail(Node<E> newTail,int numRemoved){
      AbstractUncheckedRefDblLnkSeq<E> curr;
      Node<E> oldTail;
      joinNodes(newTail,(oldTail=(curr=this).tail).next);
      do{
        curr.tail=newTail;
        curr.size-=numRemoved;
      }while((curr=curr.getParent()).tail==oldTail);
      decrementSize(curr,numRemoved);
    }
    @Override void uncheckedAppend(Node<E> oldTail,E val){
      uncheckedAppend(this,oldTail,new Node<>(oldTail,val,oldTail.next));
    }
    @Override void uncheckedClear(){
      AbstractUncheckedRefDblLnkSeq<E> curr;
      final int numRemoved=(curr=this).size;
      var oldTail=tail;
      do{
        curr.head=null;
        curr.tail=null;
        curr.size=0;
      }while((curr=curr.getParent()).tail==oldTail);
      (oldTail=oldTail.next).prev=null;
      setNewHead(curr,oldTail,numRemoved);
    }
    @Override void uncheckedInit(E val){
      final var node=new Node<>(val);
      AbstractUncheckedRefDblLnkSeq<E> curr=this;
      Node<E> oldHead;
      do{
        curr.head=node;
        curr.tail=node;
        curr.size=1;
      }while((oldHead=(curr=curr.getParent()).head)==null);
      joinNodes(node,oldHead);
      uncheckedPrepend(curr,node);
    }
    @Override void uncheckedRemoveLast(Node<E> oldTail){
      final Node<E> newTail;
      joinNodes(newTail=oldTail.prev,oldTail.next);
      AbstractUncheckedRefDblLnkSeq<E> curr=this;
      do{
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.getParent()).tail==oldTail);
      decrementSize(curr);
    }
  }
  static class RootSubList<E>extends AbstractUncheckedRefDblLnkSeq<E>{
    static <E> void setNewHead(AbstractUncheckedRefDblLnkSeq<E> curr,Node<E> newHead,int numRemoved){
      do{
        curr.head=newHead;
        curr.size-=numRemoved;
      }while((curr=curr.getParent())!=null);
    }
    static <E> void setNewTail(AbstractUncheckedRefDblLnkSeq<E> curr,Node<E> newTail,int numRemoved){
      do{
        curr.tail=newTail;
        curr.size-=numRemoved;
      }while((curr=curr.getParent())!=null);
    }
    static <E> void uncheckedAppend(AbstractUncheckedRefDblLnkSeq<E> curr,Node<E> newTail){
      do{
        ++curr.size;
        curr.tail=newTail;
      }while((curr=curr.getParent())!=null);
    }
    static <E> void uncheckedPrepend(AbstractUncheckedRefDblLnkSeq<E> curr,Node<E> newHead){
      do{
        ++curr.size;
        curr.head=newHead;
      }while((curr=curr.getParent())!=null);
    }
    transient final AbstractUncheckedRefDblLnkSeq<E> parent;
    public RootSubList(Node<E> head,int size,Node<E> tail,AbstractUncheckedRefDblLnkSeq<E> parent){
      super(head,size,tail);
      this.parent=parent;
    }
    public RootSubList(Node<E> headAndTail,AbstractUncheckedRefDblLnkSeq<E> parent){
      super(headAndTail);
      this.parent=parent;
    }
    public RootSubList(AbstractUncheckedRefDblLnkSeq<E> parent){
      super();
      this.parent=parent;
    }
    @Override AbstractUncheckedRefDblLnkSeq<E> getParent(){
      return this.parent;
    }
    @Override void setNewHead(Node<E> newHead,int numRemoved){
      super.setNewHead(newHead,numRemoved);
      setNewHead(parent,newHead,numRemoved);
    }
    @Override void setNewHeadAndTail(Node<E> newHead,int numRemoved,Node<E> newTail){
      super.setNewHeadAndTail(newHead,numRemoved,newTail);
      var curr=this.parent;
      do{
        curr.head=newHead;
        curr.tail=newTail;
        curr.size-=numRemoved;
      }while((curr=curr.getParent())!=null);
    }
    @Override void setNewTail(Node<E> newTail,int numRemoved){
      super.setNewTail(newTail,numRemoved);
      setNewTail(parent,newTail,numRemoved);
    }
    @Override void uncheckedAppend(Node<E> oldTail,E val){
      ++size;
      tail=oldTail=new Node<>(oldTail,val);
      uncheckedAppend(parent,oldTail);
    }
    @Override void uncheckedClear(){
      super.uncheckedClear();
      var curr=parent;
      do{
        curr.head=null;
        curr.tail=null;
        curr.size=0;
      }while((curr=curr.getParent())!=null);
    }
    @Override void uncheckedInit(E val){
      final var node=new Node<>(val);
      head=node;
      tail=node;
      size=1;
      var curr=parent;
      do{
        curr.head=node;
        curr.tail=node;
        curr.size=1;
      }while((curr=curr.getParent())!=null);
    }
    @Override void uncheckedPrepend(Node<E> oldHead,E val){
      ++size;
      head=oldHead=new Node<>(val,oldHead);
      uncheckedPrepend(parent,oldHead);
    }
    @Override void uncheckedRemoveFirst(Node<E> oldHead){
      final Node<E> newHead;
      oldHead.prev=null;
      --size;
      head=newHead=oldHead.next;
      var curr=parent;
      do{
        --curr.size;
        curr.head=newHead;
      }while((curr=curr.getParent())!=null);
    }
    @Override void uncheckedRemoveLast(Node<E> oldTail){
      final Node<E> newTail;
      oldTail.next=null;
      --size;
      tail=newTail=oldTail.prev;
      var curr=parent;
      do{
        --curr.size;
        curr.tail=newTail;
      }while((curr=curr.getParent())!=null);
    }
  }
  static class SuffixSubList<E>extends AbstractUncheckedRefDblLnkSeq.RootSubList<E>{
    SuffixSubList(Node<E> head,int size,Node<E> tail,AbstractUncheckedRefDblLnkSeq<E> parent){
      super(head,size,tail,parent);
    }
    SuffixSubList(Node<E> headAndTail,AbstractUncheckedRefDblLnkSeq<E> parent){
      super(headAndTail,parent);
    }
    SuffixSubList(AbstractUncheckedRefDblLnkSeq<E> parent){
      super(parent);
    }
    @Override OmniList.OfRef<E> getDefaultSublist(int headDist,int size,int tailDist){
      if(tailDist!=0){
        Node<E> head,tail;
        if(headDist>tailDist){
          tail=uncheckedIterateReverse(this.tail,tailDist);
          head=size<=headDist?uncheckedIterateReverse(tail,size-1):uncheckedIterateForward(this.head,headDist);
        }else{
          head=this.head;
          if(headDist!=0){
            head=uncheckedIterateForward(head,headDist);
          }
          tail=size<=tailDist?uncheckedIterateForward(head,size-1):uncheckedIterateReverse(this.tail,tailDist);
        }
        return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(head,size,tail,this,headDist);
      }else{
        final var tail=this.tail;
        Node<E> head;
        if(size<=headDist){
          head=uncheckedIterateReverse(tail,size-1);
        }else{
          head=this.head;
          if(headDist!=0){
            head=uncheckedIterateForward(head,headDist);
          }
        }
        return new AbstractUncheckedRefDblLnkSeq.SuffixSubList<>(head,size,tail,this);
      }
    }
    @Override OmniList.OfRef<E> getEmptySubList(int headDist,int tailDist){
      if(tailDist!=0){ return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(this,headDist); }
      return new AbstractUncheckedRefDblLnkSeq.SuffixSubList<>(this);
    }
    @Override OmniList.OfRef<E> getSubList1(int headDist,int tailDist){
      if(tailDist!=0){
        Node<E> node;
        if(tailDist<=headDist){
          node=uncheckedIterateReverse(tail,tailDist);
        }else{
          node=head;
          if(headDist!=0){
            node=uncheckedIterateForward(node,headDist);
          }
        }
        return new AbstractUncheckedRefDblLnkSeq.BodySubList<>(node,this,headDist);
      }else{
        return new AbstractUncheckedRefDblLnkSeq.SuffixSubList<>(tail,this);
      }
    }
    @Override void setNewHead(Node<E> newHead,int numRemoved){
      AbstractUncheckedRefDblLnkSeq<E> curr;
      Node<E> oldHead;
      joinNodes((oldHead=(curr=this).head).prev,newHead);
      do{
        curr.head=newHead;
        curr.size-=numRemoved;
      }while((curr=curr.getParent()).head==oldHead);
      decrementSize(curr,numRemoved);
    }
    @Override void setNewHeadAndTail(Node<E> newHead,int numRemoved,Node<E> newTail){
      AbstractUncheckedRefDblLnkSeq<E> curr;
      Node<E> oldHead;
      joinNodes((oldHead=(curr=this).head).prev,newHead);
      do{
        curr.head=newHead;
        curr.size-=numRemoved;
        curr.tail=newTail;
      }while((curr=curr.getParent()).head==oldHead);
      newTail.next=null;
      setNewTail(curr,newTail,numRemoved);
    }
    @Override void uncheckedClear(){
      AbstractUncheckedRefDblLnkSeq<E> curr;
      var oldHead=(curr=this).head;
      final int numRemoved=size;
      do{
        curr.head=null;
        curr.tail=null;
        curr.size=0;
      }while((curr=curr.getParent()).head==oldHead);
      (oldHead=oldHead.prev).next=null;
      setNewTail(curr,oldHead,numRemoved);
    }
    @Override void uncheckedInit(E val){
      final var node=new Node<>(val);
      AbstractUncheckedRefDblLnkSeq<E> curr=this;
      Node<E> oldTail;
      do{
        curr.head=node;
        curr.tail=node;
        curr.size=1;
      }while((oldTail=(curr=curr.getParent()).tail)==null);
      joinNodes(oldTail,node);
      uncheckedAppend(curr,node);
    }
    @Override void uncheckedPrepend(Node<E> oldHead,E val){
      uncheckedPrepend(this,oldHead,new Node<>(oldHead.prev,val,oldHead));
    }
    @Override void uncheckedRemoveFirst(Node<E> oldHead){
      final Node<E> newHead;
      joinNodes(oldHead.prev,newHead=oldHead.next);
      AbstractUncheckedRefDblLnkSeq<E> curr=this;
      do{
        curr.head=newHead;
        --curr.size;
      }while((curr=curr.getParent()).head==oldHead);
      decrementSize(curr);
    }
  }
}