package omni.impl.seq.dbllnk.ofref;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
class UncheckedSubList<E>extends AbstractSeq.Unchecked<E>{
  transient final Unchecked<E> root;
  transient final UncheckedSubList<E> parent;
  UncheckedSubList(Unchecked<E> root,UncheckedSubList<E> parent){
    super();
    this.root=root;
    this.parent=parent;
  }
  UncheckedSubList(Unchecked<E> root,UncheckedSubList<E> parent,Node<E> onlyNode){
    super(onlyNode);
    this.root=root;
    this.parent=parent;
  }
  UncheckedSubList(Unchecked<E> root,UncheckedSubList<E> parent,Node<E> head,int size,Node<E> tail){
    super(head,size,tail);
    this.root=root;
    this.parent=parent;
  }
  @Override public boolean add(E val){
    final var root=this.root;
    if(++size!=1){
      appendHelper(val);
    }else{
      initHelper(root,val);
    }
    ++root.size;
    return true;
  }
  @Override public void add(int index,E val){
    final var root=this.root;
    int size;
    if((size=this.size)!=0){
      if(index==0){
        prependHelper(val);
      }else{
        int tailDist;
        if((tailDist=size-index)==0){
          appendHelper(val);
        }else{
          Node<E> before,after;
          if(index<=tailDist){
            after=(before=iterateForward(head,index-1)).next;
          }else{
            before=(after=iterateReverse(tail,tailDist-1)).prev;
          }
          insertHelper(before,val,after);
        }
      }
    }else{
      initHelper(root,val);
    }
    ++root.size;
    this.size=size+1;
  }
  @Override public void clear(){
    if(size!=0){
      bubbleUpClear();
      staticClear(this.root);
    }
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfRef<E> iterator(){
    return new UncheckedAscendingSubItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(){
    return new UncheckedSubList.BidirectionalItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(int index){
    return new UncheckedSubList.BidirectionalItr<>(this,super.getItrNode(index,size-index),index);
  }
  @Override public E remove(int index){
    Node<E> node;
    int size;
    if((size=--this.size)!=0){
      if(index==0){
        node=uncheckedExtractHead(size);
      }else{
        int tailDist;
        if((tailDist=size-index)==0){
          node=uncheckedExtractTail(size);
        }else{
          UncheckedSubList<E> parent;
          if((parent=this.parent)!=null){
            parent.bubbleUpDecrementSize();
          }
          --root.size;
          node=staticExtractNode(this,index,tailDist);
        }
      }
    }else{
      node=uncheckedExtractLastNode();
    }
    return node.val;
  }
  @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
    final int tailDist=size-toIndex;
    int subListSize;
    switch(subListSize=toIndex-fromIndex){
    default:
      return getDefaultSubList(root,fromIndex,subListSize,tailDist);
    case 1:
      return getSubList1(root,fromIndex,tailDist);
    case 0:
      return getEmptySubList(root,fromIndex,tailDist);
    }
  }
  void appendHelper(E val){
    Node<E> newNode;
    tail=newNode=new Node<>(tail,val);
    UncheckedSubList<E> parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpRootPushTail(newNode);
    }
  }
  void ascItrRemove(Node<E> cursor){
    final Unchecked<E> root;
    --(root=this.root).size;
    if(cursor!=null){
      Node<E> lastRet;
      if((lastRet=cursor.prev)==head){
        bubbleUpRootEraseHead(cursor);
        staticSetHead(root,cursor);
      }else{
        bubbleUpDecrementSize();
        joinNodes(lastRet.prev,cursor);
      }
    }else{
      if(size==1){
        bubbleUpClear();
        staticInit(root,null);
      }else{
        bubbleUpRootEraseTail(cursor=tail);
        staticSetTail(root,cursor);
      }
    }
  }
  void bidirectItrRemove(Node<E> lastRet){
    final Unchecked<E> root;
    --(root=this.root).size;
    if(lastRet==tail){
      if(lastRet==head){
        bubbleUpClear();
        staticInit(root,null);
      }else{
        bubbleUpRootEraseTail(lastRet=lastRet.prev);
        staticSetTail(root,lastRet);
      }
    }else{
      if(lastRet==head){
        bubbleUpRootEraseHead(lastRet=lastRet.next);
        staticSetHead(root,lastRet);
      }else{
        bubbleUpDecrementSize();
        joinNodes(lastRet.prev,lastRet.next);
      }
    }
  }
  @Override final boolean collapseBody(Node<E> head,Node<E> tail,Predicate<? super E> filter){
    Node<E> prev;
    while((head=(prev=head).next)!=tail){
      if(filter.test(head.val)){
        int numRemoved;
        for(numRemoved=1;(head=head.next)!=tail;++numRemoved){
          if(!filter.test(head.val)){
            numRemoved+=UncheckedList.collapseBodyHelper(head,tail,filter);
            break;
          }
        }
        joinNodes(prev,head);
        bubbleUpDecrementSize(numRemoved);
        root.size-=numRemoved;
        return true;
      }
    }
    return false;
  }
  @Override void collapseTail(Node<E> head,Node<E> tail,Predicate<? super E> filter){
    int numRemoved;
    for(numRemoved=1;(tail=tail.prev)!=head;++numRemoved){
      if(!filter.test(tail.val)){
        numRemoved+=UncheckedList.collapseBodyHelper(head,tail,filter);
        break;
      }
    }
    bubbleUpRootCollapseTail(numRemoved,tail);
    Unchecked<E> root;
    staticSetTail(root=this.root,tail);
    root.size-=numRemoved;
  }
  @Override void findNewHead(Node<E> head,Predicate<? super E> filter){
    final Node<E> tail;
    if((tail=this.tail)==head){
      bubbleUpClear();
      staticClear(this.root);
    }else if(filter.test(tail.val)){
      collapseHeadAndTail(head,tail,filter);
    }else{
      rootCollapseHead(head.next,tail,filter);
    }
  }
  int getParentOffset(){
    return 0;
  }
  void initHelper(Unchecked<E> root,E val){
    final var newNode=new Node<>(val);
    for(var curr=this;;curr.size=1){
      staticInit(curr,newNode);
      if((curr=curr.parent)==null){
        staticInit(root,newNode);
        return;
      }
    }
  }
  void prependHelper(E val){
    Node<E> newNode;
    head=newNode=new Node<>(val,head);
    UncheckedSubList<E> parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpRootPushHead(newNode);
    }
  }
  void removeFirstHelper(Node<E> curr){
    final Unchecked<E> root;
    --(root=this.root).size;
    if(curr==tail){
      bubbleUpClear();
      staticClear(root);
    }else{
      bubbleUpRootEraseHead(curr=curr.next);
      staticSetHead(root,curr);
    }
  }
  Node<E> uncheckedExtractHead(int newSize){
    Node<E> oldHead,newHead;
    bubbleUpRootEraseHead(newHead=(oldHead=head).next);
    final Unchecked<E> root;
    staticSetHead(root=this.root,newHead);
    root.size=newSize;
    return oldHead;
  }
  Node<E> uncheckedExtractLastNode(){
    final var lastNode=head;
    bubbleUpClear();
    staticClear(this.root);
    return lastNode;
  }
  Node<E> uncheckedExtractTail(int newSize){
    Node<E> oldTail,newTail;
    bubbleUpRootEraseTail(newTail=(oldTail=tail).prev);
    final Unchecked<E> root;
    staticSetTail(root=this.root,newTail);
    root.size=newSize;
    return oldTail;
  }
  @Override boolean uncheckedRemoveFirstMatch(Node<E> head,Predicate<Object> pred){
    if(pred.test(head.val)){
      removeFirstHelper(head);
      return true;
    }
    return uncheckedRemoveFirstMatchHelper(head,pred);
  }
  boolean uncheckedRemoveFirstMatchHelper(Node<E> curr,Predicate<Object> pred){
    final var tail=this.tail;
    Node<E> prev;
    do{
      if(curr==tail){ return false; }
    }while(!pred.test((curr=(prev=curr).next).val));
    final Unchecked<E> root;
    --(root=this.root).size;
    if(curr==tail){
      bubbleUpRootEraseTail(prev);
      staticSetTail(root,prev);
    }else{
      bubbleUpDecrementSize();
      joinNodes(prev,curr.next);
    }
    return true;
  }
  @Override boolean uncheckedRemoveFirstNonNull(Node<E> head,Object nonNull){
    if(nonNull.equals(head.val)){
      removeFirstHelper(head);
      return true;
    }
    return uncheckedRemoveFirstMatchHelper(head,nonNull::equals);
  }
  private void bubbleUpClear(){
    var curr=this;
    do{
      staticClear(curr);
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpCollapseHeadAndTail(Node<E> newHead,Node<E> newTail,int newSize){
    var curr=this;
    do{
      curr.privateCollapseHeadAndTail(newSize,newHead,newTail);
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpDecrementSize(){
    var curr=this;
    do{
      --curr.size;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpDecrementSize(int numRemoved){
    var curr=this;
    do{
      curr.size-=numRemoved;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpIncrementSize(){
    var curr=this;
    do{
      ++curr.size;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpPrefixCollapseTail(int numRemoved,Node<E> oldTail,Node<E> newTail){
    var curr=this;
    while(curr.tail==oldTail){
      curr.size-=numRemoved;
      curr.tail=newTail;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpDecrementSize(numRemoved);
  }
  private void bubbleUpPrefixPushTail(Node<E> newTail,Node<E> oldTail){
    var curr=this;
    while(curr.tail==oldTail){
      ++curr.size;
      curr.tail=newTail;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpIncrementSize();
  }
  private void bubbleUpRootCollapseHead(int numRemoved,Node<E> newHead){
    var curr=this;
    do{
      curr.size-=numRemoved;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootCollapseTail(int numRemoved,Node<E> newTail){
    var curr=this;
    do{
      curr.size-=numRemoved;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootEraseHead(Node<E> newHead){
    var curr=this;
    do{
      --curr.size;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootEraseTail(Node<E> newTail){
    var curr=this;
    do{
      --curr.size;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootPushHead(Node<E> newHead){
    var curr=this;
    do{
      ++curr.size;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootPushTail(Node<E> newTail){
    var curr=this;
    do{
      ++curr.size;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpSuffixCollapseHead(int numRemoved,Node<E> oldHead,Node<E> newHead){
    var curr=this;
    while(curr.head==oldHead){
      curr.size-=numRemoved;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpDecrementSize(numRemoved);
  }
  private void bubbleUpSuffixEraseHead(Node<E> oldHead,Node<E> newHead){
    var curr=this;
    do{
      --curr.size;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }while(curr.head==oldHead);
    curr.bubbleUpDecrementSize();
    joinNodes(oldHead.prev,newHead);
  }
  private void bubbleUpSuffixPushHead(Node<E> newHead,Node<E> oldHead){
    var curr=this;
    while(curr.head==oldHead){
      ++curr.size;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpIncrementSize();
  }
  private void collapseHeadAndTail(Node<E> head,Node<E> tail,Predicate<? super E> filter){
    int numRemoved=2;
    for(;;++numRemoved){
      if((head=head.next)==tail){
        bubbleUpClear();
        staticClear(root);
        return;
      }
      if(!filter.test(head.val)){
        while((tail=tail.prev)!=head){
          if(!filter.test(tail.val)){
            numRemoved+=UncheckedList.collapseBodyHelper(head,tail,filter);
            break;
          }
          ++numRemoved;
        }
        bubbleUpCollapseHeadAndTail(head,tail,numRemoved=size-numRemoved);
        Unchecked<E> root;
        staticSetHead(root=this.root,head);
        staticSetTail(root,tail);
        root.size=numRemoved;
        return;
      }
    }
  }
  private OmniList.OfRef<E> getDefaultSubList(Unchecked<E> root,int headDist,int subListSize,int tailDist){
    final Node<E> subListHead=head;
    Node<E> subListTail=tail;
    if(tailDist==0){
      if(headDist==0){ return new UncheckedSubList<>(root,this,subListHead,subListSize,subListTail); }
      return new UncheckedSubList.Suffix<>(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
          :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail);
    }
    subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
        :uncheckedIterateForward(subListHead,subListSize);
    if(headDist==0){ return new UncheckedSubList.Prefix<>(root,this,subListHead,subListSize,subListTail); }
    return new UncheckedSubList.Body<>(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
        :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail,headDist);
  }
  private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new UncheckedSubList<>(root,this); }
      return new UncheckedSubList.Suffix<>(root,this);
    }else if(headDist==0){ return new UncheckedSubList.Prefix<>(root,this); }
    return new UncheckedSubList.Body<>(root,this,headDist);
  }
  private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new UncheckedSubList<>(root,this,head); }
      return new UncheckedSubList.Suffix<>(root,this,tail);
    }else if(headDist==0){ return new UncheckedSubList.Prefix<>(root,this,head); }
    return new UncheckedSubList.Body<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
  }
  private void insertHelper(Node<E> before,E val,Node<E> after){
    new Node<>(before,val,after);
    UncheckedSubList<E> parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpIncrementSize();
    }
  }
  private void privateCollapseHeadAndTail(int size,Node<E> head,Node<E> tail){
    this.size=size;
    this.head=head;
    this.tail=tail;
  }
  private void rootCollapseHead(Node<E> headCandidate,Node<E> tail,Predicate<? super E> filter){
    int numRemoved;
    for(numRemoved=1;headCandidate!=tail;++numRemoved,headCandidate=headCandidate.next){
      if(!filter.test(headCandidate.val)){
        numRemoved+=UncheckedList.collapseBodyHelper(headCandidate,tail,filter);
        break;
      }
    }
    bubbleUpRootCollapseHead(numRemoved,headCandidate);
    final Unchecked<E> root;
    staticSetHead(root=this.root,headCandidate);
    root.size-=numRemoved;
  }
  private void suffixCollapseHead(Node<E> oldHead,Node<E> oldTail,Predicate<? super E> filter){
    final int oldSize=size;
    int numConsumed=2;
    final var root=this.root;
    var headCandidate=oldHead.next;
    for(;;headCandidate=headCandidate.next){
      if(numConsumed==oldSize){
        --numConsumed;
        break;
      }
      ++numConsumed;
      if(!filter.test(headCandidate.val)){
        numConsumed-=2-UncheckedList.collapseBodyHelper(headCandidate,oldTail,filter);
        break;
      }
    }
    bubbleUpSuffixCollapseHead(numConsumed,oldHead,headCandidate);
    joinNodes(oldHead.prev,headCandidate);
    root.size-=numConsumed;
  }
  static class Body<E>extends UncheckedSubList.Prefix<E>{
    private transient final int parentOffset;
    Body(Unchecked<E> root,UncheckedSubList<E> parent,int parentOffset){
      super(root,parent);
      this.parentOffset=parentOffset;
    }
    Body(Unchecked<E> root,UncheckedSubList<E> parent,Node<E> onlyNode,int parentOffset){
      super(root,parent,onlyNode);
      this.parentOffset=parentOffset;
    }
    Body(Unchecked<E> root,UncheckedSubList<E> parent,Node<E> head,int size,Node<E> tail,int parentOffset){
      super(root,parent,head,size,tail);
      this.parentOffset=parentOffset;
    }
    @Override public void clear(){
      int size;
      if((size=this.size)!=0){
        bubbleUpClear(size,head,tail);
        root.size-=size;
      }
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      int subListSize;
      switch(subListSize=toIndex-fromIndex){
      default:
        return getDefaultSubList(root,fromIndex,subListSize,size-toIndex);
      case 1:
        return getSubList1(root,fromIndex,size-toIndex);
      case 0:
        return getEmptySubList(root,fromIndex);
      }
    }
    @Override void ascItrRemove(Node<E> cursor){
      --root.size;
      if(cursor!=null){
        Node<E> lastRet;
        if((lastRet=cursor.prev)==head){
          ((UncheckedSubList<E>)this).bubbleUpSuffixEraseHead(lastRet,cursor);
        }else{
          ((UncheckedSubList<E>)this).bubbleUpDecrementSize();
          joinNodes(lastRet.prev,cursor);
        }
      }else{
        cursor=tail;
        if(size==1){
          bubbleUpClear(1,cursor,cursor);
        }else{
          super.bubbleUpPrefixEraseTail(cursor,cursor.prev);
        }
      }
    }
    @Override void bidirectItrRemove(Node<E> lastRet){
      --root.size;
      if(lastRet==tail){
        if(lastRet==head){
          bubbleUpClear(1,lastRet,lastRet);
        }else{
          super.bubbleUpPrefixEraseTail(lastRet,lastRet.prev);
        }
      }else{
        if(lastRet==head){
          ((UncheckedSubList<E>)this).bubbleUpSuffixEraseHead(lastRet,lastRet.next);
        }else{
          ((UncheckedSubList<E>)this).bubbleUpDecrementSize();
          joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }
    @Override void findNewHead(Node<E> curr,Predicate<? super E> filter){
      final Node<E> tail;
      if(curr==(tail=this.tail)){
        bubbleUpClear(1,curr,tail);
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(curr,tail,filter);
      }else{
        ((UncheckedSubList<E>)this).suffixCollapseHead(curr,tail,filter);
      }
    }
    @Override int getParentOffset(){
      return this.parentOffset;
    }
    @Override void initHelper(Unchecked<E> root,E val){
      final Node<E> newNode;
      staticInit(this,newNode=new Node<>(val));
      UncheckedSubList<E> parent,curr;
      if((parent=(curr=this).parent)!=null){
        do{
          int parentSize;
          if((parentSize=parent.size)!=0){
            Node<E> before,after;
            int headDist,tailDist;
            if((headDist=curr.getParentOffset())==0){
              parent.bubbleUpSuffixPushHead(newNode,after=parent.head);
              before=after.prev;
            }else if((tailDist=parentSize-headDist)==0){
              parent.bubbleUpPrefixPushTail(newNode,before=parent.tail);
              after=before.next;
            }else{
              if(headDist<=tailDist){
                after=(before=iterateForward(root.head,headDist-1)).next;
              }else{
                before=(after=iterateReverse(root.tail,tailDist-1)).prev;
              }
              parent.bubbleUpIncrementSize();
            }
            joinNodes(before,newNode);
            joinNodes(newNode,after);
            return;
          }
          parent=(curr=parent).parent;
          curr.size=1;
          staticInit(curr,newNode);
        }while(parent!=null);
      }
      subSeqInsertHelper(root,newNode,curr.getParentOffset());
    }
    @Override void prependHelper(E val){
      Node<E> newNode,oldHead;
      head=newNode=new Node<>((oldHead=head).prev,val,oldHead);
      UncheckedSubList<E> parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpSuffixPushHead(newNode,oldHead);
      }
    }
    @Override void removeFirstHelper(Node<E> curr){
      --root.size;
      if(curr==tail){
        bubbleUpClear(1,head,curr);
      }else{
        ((UncheckedSubList<E>)this).bubbleUpSuffixEraseHead(curr,curr.next);
      }
    }
    @Override Node<E> uncheckedExtractHead(int newSize){
      Node<E> oldHead;
      ((UncheckedSubList<E>)this).bubbleUpSuffixEraseHead(oldHead=head,oldHead.next);
      --root.size;
      return oldHead;
    }
    @Override Node<E> uncheckedExtractLastNode(){
      final Node<E> lastNode;
      bubbleUpClear(1,lastNode=head,lastNode);
      --root.size;
      return lastNode;
    }
    private void bubbleUpClear(int size,Node<E> head,Node<E> tail){
      final Node<E> prev=head.prev,next=tail.next;
      for(UncheckedSubList<E> curr=this;;){
        staticClear(curr);
        if((curr=curr.parent)!=null){
          if(curr.head!=head){
            curr.bubbleUpPrefixCollapseTail(size,tail,prev);
            break;
          }else if(curr.tail!=tail){
            curr.size-=size;
            curr.head=next;
            if((curr=curr.parent)!=null){
              curr.bubbleUpSuffixCollapseHead(size,head,next);
            }
            break;
          }
          continue;
        }
        break;
      }
      joinNodes(prev,next);
    }
    private void bubbleUpCollapseHeadAndTail(Node<E> oldHead,Node<E> oldTail,Node<E> newHead,Node<E> newTail,
        int numRemoved){
      final int newSize=size-numRemoved;
      for(UncheckedSubList<E> curr=this;;){
        curr.privateCollapseHeadAndTail(newSize,newHead,newTail);
        if((curr=curr.parent)==null){
          break;
        }
        if(curr.head!=oldHead){
          curr.bubbleUpPrefixCollapseTail(numRemoved,oldTail,newTail);
          break;
        }else if(curr.tail!=oldTail){
          curr.size-=numRemoved;
          curr.head=newHead;
          if((curr=curr.parent)!=null){
            curr.bubbleUpSuffixCollapseHead(numRemoved,oldHead,newHead);
          }
          break;
        }
      }
      joinNodes(oldHead.prev,newHead);
      joinNodes(newTail,oldTail.next);
    }
    private void collapseHeadAndTail(final Node<E> head,final Node<E> tail,final Predicate<? super E> filter){
      final int oldSize=size;
      int numConsumed=2;
      var headCandidate=head;
      final var root=this.root;
      for(;;){
        if(numConsumed==oldSize){
          bubbleUpClear(oldSize,head,tail);
          break;
        }
        ++numConsumed;
        if(!filter.test((headCandidate=headCandidate.next).val)){
          var tailCandidate=tail;
          for(;;){
            if(numConsumed==oldSize){
              --numConsumed;
              break;
            }
            ++numConsumed;
            if(!filter.test((tailCandidate=tailCandidate.prev).val)){
              numConsumed-=2-UncheckedList.collapseBodyHelper(headCandidate,tailCandidate,filter);
              break;
            }
          }
          bubbleUpCollapseHeadAndTail(head,tail,headCandidate,tailCandidate,numConsumed);
          break;
        }
      }
      root.size-=numConsumed;
    }
    private OmniList.OfRef<E> getDefaultSubList(Unchecked<E> root,int headDist,int subListSize,int tailDist){
      Node<E> subListHead,subListTail;
      if(headDist<=tailDist){
        subListHead=iterateForward(head,headDist);
        subListTail=tailDist<subListSize?iterateReverse(tail,tailDist):uncheckedIterateForward(subListHead,subListSize);
      }else{
        subListTail=iterateReverse(tail,tailDist);
        subListHead=headDist<subListSize?iterateForward(head,headDist):uncheckedIterateReverse(subListTail,subListSize);
      }
      return new UncheckedSubList.Body<>(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist){
      return new UncheckedSubList.Body<>(root,this,headDist);
    }
    private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist){
      return new UncheckedSubList.Body<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
    }
  }
  static class Prefix<E>extends UncheckedSubList<E>{
    Prefix(Unchecked<E> root,UncheckedSubList<E> parent){
      super(root,parent);
    }
    Prefix(Unchecked<E> root,UncheckedSubList<E> parent,Node<E> onlyNode){
      super(root,parent,onlyNode);
    }
    Prefix(Unchecked<E> root,UncheckedSubList<E> parent,Node<E> head,int size,Node<E> tail){
      super(root,parent,head,size,tail);
    }
    @Override public void clear(){
      int size;
      if((size=this.size)!=0){
        Node<E> tmp;
        bubbleUpClear(size,tmp=tail,tmp=tmp.next);
        final Unchecked<E> root;
        staticSetHead(root=this.root,tmp);
        root.size-=size;
      }
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      int subListSize;
      switch(subListSize=toIndex-fromIndex){
      default:
        return getDefaultSubList(root,fromIndex,subListSize,size-toIndex);
      case 1:
        return getSubList1(root,fromIndex,size-toIndex);
      case 0:
        return getEmptySubList(root,fromIndex);
      }
    }
    @Override void appendHelper(E val){
      Node<E> newNode,oldTail;
      tail=newNode=new Node<>(oldTail=tail,val,oldTail.next);
      UncheckedSubList<E> parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpPrefixPushTail(newNode,oldTail);
      }
    }
    @Override void ascItrRemove(Node<E> cursor){
      final Unchecked<E> root;
      --(root=this.root).size;
      if(cursor!=null){
        Node<E> lastRet;
        if((lastRet=cursor.prev)==head){
          super.bubbleUpRootEraseHead(cursor);
          staticSetHead(root,cursor);
        }else{
          super.bubbleUpDecrementSize();
          joinNodes(lastRet.prev,cursor);
        }
      }else{
        cursor=tail;
        if(size==1){
          bubbleUpClear(1,cursor,cursor=cursor.next);
          staticSetHead(root,cursor);
        }else{
          bubbleUpPrefixEraseTail(cursor,cursor.prev);
        }
      }
    }
    @Override void bidirectItrRemove(Node<E> lastRet){
      final Unchecked<E> root;
      --(root=this.root).size;
      if(lastRet==head){
        if(lastRet==tail){
          bubbleUpClear(1,lastRet,lastRet=lastRet.next);
        }else{
          super.bubbleUpRootEraseHead(lastRet=lastRet.next);
        }
        staticSetHead(root,lastRet);
      }else{
        if(lastRet==tail){
          bubbleUpPrefixEraseTail(lastRet,lastRet.prev);
        }else{
          super.bubbleUpDecrementSize();
          joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }
    @Override void collapseTail(Node<E> oldHead,Node<E> oldTail,Predicate<? super E> filter){
      final int oldSize=size;
      int numConsumed=2;
      final var root=this.root;
      var tailCandidate=oldTail.prev;
      for(;;tailCandidate=tailCandidate.prev){
        if(numConsumed==oldSize){
          --numConsumed;
          break;
        }
        ++numConsumed;
        if(!filter.test(tailCandidate.val)){
          numConsumed-=2-UncheckedList.collapseBodyHelper(oldHead,tailCandidate,filter);
          break;
        }
      }
      super.bubbleUpPrefixCollapseTail(numConsumed,oldTail,tailCandidate);
      joinNodes(tailCandidate,oldTail.next);
      root.size-=numConsumed;
    }
    @Override void findNewHead(Node<E> curr,Predicate<? super E> filter){
      final Node<E> tail;
      if((tail=this.tail)==curr){
        bubbleUpClear(1,tail,curr=tail.next);
        final Unchecked<E> root;
        staticSetHead(root=this.root,tail);
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(curr,tail,filter);
      }else{
        super.rootCollapseHead(curr.next,tail,filter);
      }
    }
    @Override void initHelper(Unchecked<E> root,E val){
      final Node<E> after,newNode=new Node<>(val);
      for(UncheckedSubList<E> curr=this;;curr.size=1){
        staticInit(curr,newNode);
        if((curr=curr.parent)==null){
          after=root.head;
          break;
        }else if(curr.size!=0){
          after=curr.head;
          curr.bubbleUpRootPushHead(newNode);
          break;
        }
      }
      joinNodes(newNode,after);
    }
    @Override Node<E> uncheckedExtractLastNode(){
      final Node<E> next,lastNode;
      final Unchecked<E> root;
      staticSetHead(root=this.root,next=(lastNode=tail).next);
      bubbleUpClear(1,lastNode,next);
      --root.size;
      return lastNode;
    }
    @Override Node<E> uncheckedExtractTail(int newSize){
      Node<E> oldTail;
      bubbleUpPrefixEraseTail(oldTail=tail,oldTail.prev);
      --root.size;
      return oldTail;
    }
    @Override boolean uncheckedRemoveFirstMatchHelper(Node<E> curr,Predicate<Object> pred){
      final var tail=this.tail;
      Node<E> prev;
      do{
        if(curr==tail){ return false; }
      }while(!pred.test((curr=(prev=curr).next).val));
      --root.size;
      if(curr==tail){
        bubbleUpPrefixEraseTail(curr,prev);
      }else{
        super.bubbleUpDecrementSize();
        joinNodes(prev,curr.next);
      }
      return true;
    }
    private void bubbleUpClear(int size,Node<E> tail,final Node<E> next){
      for(UncheckedSubList<E> curr=this;;){
        staticClear(curr);
        if((curr=curr.parent)==null){
          break;
        }else if(curr.tail!=tail){
          curr.bubbleUpRootCollapseHead(size,next);
          break;
        }
      }
    }
    private void bubbleUpCollapseHeadAndTail(Node<E> oldTail,Node<E> newHead,Node<E> newTail,int numRemoved){
      final int newSize=size-numRemoved;
      for(UncheckedSubList<E> curr=this;;){
        curr.privateCollapseHeadAndTail(newSize,newHead,newTail);
        if((curr=curr.parent)==null){
          break;
        }
        if(curr.tail!=oldTail){
          curr.bubbleUpRootCollapseHead(numRemoved,newHead);
          break;
        }
      }
      joinNodes(newTail,oldTail.next);
    }
    private void bubbleUpPrefixEraseTail(Node<E> oldTail,Node<E> newTail){
      UncheckedSubList<E> curr=this;
      do{
        --curr.size;
        curr.tail=newTail;
        if((curr=curr.parent)==null){ return; }
      }while(curr.tail==oldTail);
      curr.bubbleUpDecrementSize();
      joinNodes(newTail,oldTail.next);
    }
    private void collapseHeadAndTail(Node<E> headCandidate,Node<E> tail,Predicate<? super E> filter){
      final int oldSize=size;
      int numConsumed=2;
      final var root=this.root;
      for(;;){
        if(numConsumed==oldSize){
          bubbleUpClear(oldSize,tail,headCandidate=tail.next);
          break;
        }
        ++numConsumed;
        if(!filter.test((headCandidate=headCandidate.next).val)){
          var tailCandidate=tail;
          for(;;){
            if(numConsumed==oldSize){
              --numConsumed;
              break;
            }
            ++numConsumed;
            if(!filter.test((tailCandidate=tailCandidate.prev).val)){
              numConsumed-=2-UncheckedList.collapseBodyHelper(headCandidate,tailCandidate,filter);
              break;
            }
          }
          bubbleUpCollapseHeadAndTail(tail,headCandidate,tailCandidate,numConsumed);
          break;
        }
      }
      staticSetHead(root,headCandidate);
      root.size-=numConsumed;
    }
    private OmniList.OfRef<E> getDefaultSubList(Unchecked<E> root,int headDist,int subListSize,int tailDist){
      Node<E> subListHead=head,subListTail=tail;
      if(headDist==0){ return new UncheckedSubList.Prefix<>(root,this,subListHead,subListSize,tailDist<=subListSize
          ?iterateReverse(subListTail,tailDist):uncheckedIterateForward(subListHead,subListSize)); }
      if(headDist<=tailDist){
        subListHead=uncheckedIterateForward(subListHead,headDist);
        subListTail=tailDist<=subListSize?iterateReverse(subListTail,tailDist)
            :uncheckedIterateForward(subListHead,subListSize);
      }else{
        subListTail=iterateReverse(subListTail,tailDist);
        subListHead=headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
            :uncheckedIterateReverse(subListTail,subListSize);
      }
      return new UncheckedSubList.Body<>(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist){
      if(headDist==0){ return new UncheckedSubList.Prefix<>(root,this); }
      return new UncheckedSubList.Body<>(root,this,headDist);
    }
    private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist){
      if(headDist==0){ return new UncheckedSubList.Prefix<>(root,this,head); }
      return new UncheckedSubList.Body<>(root,this,
          tailDist<headDist?iterateReverse(tail,tailDist):uncheckedIterateForward(head,headDist),headDist);
    }
  }
  static class Suffix<E>extends UncheckedSubList<E>{
    Suffix(Unchecked<E> root,UncheckedSubList<E> parent){
      super(root,parent);
    }
    Suffix(Unchecked<E> root,UncheckedSubList<E> parent,Node<E> onlyNode){
      super(root,parent,onlyNode);
    }
    Suffix(Unchecked<E> root,UncheckedSubList<E> parent,Node<E> head,int size,Node<E> tail){
      super(root,parent,head,size,tail);
    }
    @Override public void clear(){
      int size;
      if((size=this.size)!=0){
        Node<E> tmp;
        bubbleUpClear(size,tmp=head,tmp=tmp.prev);
        final Unchecked<E> root;
        staticSetTail(root=this.root,tmp);
        root.size-=size;
      }
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      final int tailDist=size-toIndex;
      int subListSize;
      switch(subListSize=toIndex-fromIndex){
      default:
        return getDefaultSubList(root,fromIndex,subListSize,tailDist);
      case 1:
        return getSubList1(root,fromIndex,tailDist);
      case 0:
        return getEmptySubList(root,fromIndex,tailDist);
      }
    }
    @Override void ascItrRemove(Node<E> cursor){
      final Unchecked<E> root;
      --(root=this.root).size;
      if(cursor!=null){
        Node<E> lastRet;
        if((lastRet=cursor.prev)==head){
          super.bubbleUpSuffixEraseHead(lastRet,cursor);
        }else{
          super.bubbleUpDecrementSize();
          joinNodes(lastRet.prev,cursor);
        }
      }else{
        if(size==1){
          bubbleUpClear(1,cursor=tail,cursor=cursor.prev);
          staticInit(root,null);
        }else{
          super.bubbleUpRootEraseTail(cursor=tail.prev);
        }
        staticSetTail(root,cursor);
      }
    }
    @Override void bidirectItrRemove(Node<E> lastRet){
      final Unchecked<E> root;
      --(root=this.root).size;
      if(lastRet==tail){
        if(lastRet==head){
          bubbleUpClear(1,lastRet,lastRet=lastRet.prev);
        }else{
          super.bubbleUpRootEraseTail(lastRet=lastRet.prev);
        }
        staticSetTail(root,lastRet);
      }else{
        if(lastRet==head){
          super.bubbleUpSuffixEraseHead(lastRet,lastRet.next);
        }else{
          super.bubbleUpDecrementSize();
          joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }
    @Override void findNewHead(Node<E> curr,Predicate<? super E> filter){
      final Node<E> tail;
      if(curr==(tail=this.tail)){
        bubbleUpClear(1,curr,curr=curr.prev);
        final Unchecked<E> root;
        staticSetTail(root=this.root,curr);
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(curr,tail,filter);
      }else{
        super.suffixCollapseHead(curr,tail,filter);
      }
    }
    @Override void initHelper(Unchecked<E> root,E val){
      final Node<E> before,newNode=new Node<>(val);
      for(UncheckedSubList<E> curr=this;;curr.size=1){
        staticInit(curr,newNode);
        if((curr=curr.parent)==null){
          before=root.tail;
          break;
        }else if(curr.size!=0){
          before=curr.tail;
          curr.bubbleUpRootPushTail(newNode);
          break;
        }
      }
      joinNodes(before,newNode);
    }
    @Override void prependHelper(E val){
      Node<E> newNode,oldHead;
      head=newNode=new Node<>((oldHead=head).prev,val,oldHead);
      UncheckedSubList<E> parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpSuffixPushHead(newNode,oldHead);
      }
    }
    @Override void removeFirstHelper(Node<E> curr){
      Unchecked<E> root;
      --(root=this.root).size;
      if(curr==tail){
        bubbleUpClear(1,curr,curr=curr.prev);
        staticSetTail(root,curr);
      }else{
        super.bubbleUpSuffixEraseHead(curr,curr.next);
      }
    }
    @Override Node<E> uncheckedExtractHead(int newSize){
      Node<E> oldHead;
      super.bubbleUpSuffixEraseHead(oldHead=head,oldHead.next);
      --root.size;
      return oldHead;
    }
    @Override Node<E> uncheckedExtractLastNode(){
      final Node<E> prev,lastNode;
      Unchecked<E> root;
      staticSetTail(root=this.root,prev=(lastNode=head).prev);
      bubbleUpClear(1,lastNode,prev);
      --root.size;
      return lastNode;
    }
    private void bubbleUpClear(int size,Node<E> head,final Node<E> prev){
      for(UncheckedSubList<E> curr=this;;){
        staticClear(curr);
        if((curr=curr.parent)==null){
          break;
        }else if(curr.head!=head){
          curr.bubbleUpRootCollapseTail(size,prev);
          break;
        }
      }
    }
    private void bubbleUpCollapseHeadAndTail(Node<E> oldHead,Node<E> newHead,Node<E> newTail,int numRemoved){
      final int newSize=size-numRemoved;
      for(UncheckedSubList<E> curr=this;;){
        curr.privateCollapseHeadAndTail(newSize,newHead,newTail);
        if((curr=curr.parent)==null){
          break;
        }
        if(curr.head!=oldHead){
          curr.bubbleUpRootCollapseTail(numRemoved,newTail);
          break;
        }
      }
      joinNodes(oldHead.prev,newHead);
    }
    private void collapseHeadAndTail(Node<E> head,Node<E> tailCandidate,Predicate<? super E> filter){
      final int oldSize=size;
      int numConsumed=2;
      final var root=this.root;
      for(;;){
        if(numConsumed==oldSize){
          bubbleUpClear(oldSize,head,tailCandidate=head.prev);
          break;
        }
        ++numConsumed;
        if(!filter.test((tailCandidate=tailCandidate.prev).val)){
          var headCandidate=head;
          for(;;){
            if(numConsumed==oldSize){
              --numConsumed;
              break;
            }
            ++numConsumed;
            if(!filter.test((headCandidate=headCandidate.next).val)){
              numConsumed-=2-+UncheckedList.collapseBodyHelper(headCandidate,tailCandidate,filter);
              break;
            }
          }
          bubbleUpCollapseHeadAndTail(head,headCandidate,tailCandidate,numConsumed);
          break;
        }
      }
      staticSetTail(root,tailCandidate);
      root.size-=numConsumed;
    }
    private OmniList.OfRef<E> getDefaultSubList(Unchecked<E> root,int headDist,int subListSize,int tailDist){
      Node<E> subListHead=head,subListTail=tail;
      if(tailDist==0){ return new UncheckedSubList.Suffix<>(root,this,headDist<=subListSize
          ?iterateForward(subListHead,headDist):uncheckedIterateReverse(subListTail,subListSize)); }
      if(tailDist<=headDist){
        subListTail=uncheckedIterateReverse(subListTail,tailDist);
        subListHead=headDist<=subListSize?iterateForward(subListHead,headDist)
            :uncheckedIterateReverse(subListTail,subListSize);
      }else{
        subListHead=iterateForward(subListHead,headDist);
        subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
            :uncheckedIterateForward(subListHead,subListSize);
      }
      return new UncheckedSubList.Body<>(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist,int tailDist){
      if(tailDist==0){ return new UncheckedSubList.Prefix<>(root,this); }
      return new UncheckedSubList.Body<>(root,this,headDist);
    }
    private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist){
      if(tailDist==0){ return new UncheckedSubList.Suffix<>(root,this,tail); }
      return new UncheckedSubList.Body<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
    }
  }
  private static class BidirectionalItr<E>extends UncheckedAscendingSubItr<E> implements OmniListIterator.OfRef<E>{
    private transient int nextIndex;
    private transient Node<E> lastRet;
    private BidirectionalItr(UncheckedSubList<E> parent){
      super(parent);
    }
    private BidirectionalItr(UncheckedSubList<E> parent,Node<E> cursor,int nextIndex){
      super(parent,cursor);
      this.nextIndex=nextIndex;
    }
    @Override public void add(E val){
      final int nextIndex=this.nextIndex++;
      int size;
      final UncheckedSubList<E> parent;
      final var root=(parent=this.parent).root;
      if((size=parent.size++)!=0){
        if(nextIndex==0){
          parent.prependHelper(val);
        }else if(nextIndex==size){
          parent.appendHelper(val);
        }else{
          Node<E> cursor;
          parent.insertHelper((cursor=this.cursor).prev,val,cursor);
        }
      }else{
        parent.initHelper(root,val);
      }
      ++root.size;
      lastRet=null;
    }
    @Override public void forEachRemaining(Consumer<? super E> action){
      Node<E> cursor;
      if((cursor=this.cursor)!=null){
        UncheckedSubList<E> parent;
        uncheckedForEachForward(cursor,cursor=(parent=this.parent).tail,action);
        this.nextIndex=parent.size;
        this.cursor=null;
        lastRet=cursor;
      }
    }
    @Override public boolean hasPrevious(){
      return this.nextIndex!=0;
    }
    @Override public E next(){
      Node<E> cursor;
      if((cursor=this.cursor)==parent.tail){
        this.cursor=null;
      }else{
        this.cursor=cursor.next;
      }
      ++nextIndex;
      lastRet=cursor;
      return cursor.val;
    }
    @Override public int nextIndex(){
      return this.nextIndex;
    }
    @Override public E previous(){
      Node<E> lastRet;
      this.lastRet=lastRet=cursor.prev;
      cursor=lastRet;
      --nextIndex;
      return lastRet.val;
    }
    @Override public int previousIndex(){
      return this.nextIndex-1;
    }
    @Override public void remove(){
      Node<E> lastRet;
      parent.bidirectItrRemove(lastRet=this.lastRet);
      if(lastRet!=cursor){
        --nextIndex;
      }
      this.lastRet=null;
    }
    @Override public void set(E val){
      lastRet.val=val;
    }
  }
}