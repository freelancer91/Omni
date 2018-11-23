package omni.impl.seq.dbllnk.offloat;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.util.TypeUtil;
class UncheckedSubList extends AbstractSeq.Unchecked{
  transient final Unchecked root;
  transient final UncheckedSubList parent;
  UncheckedSubList(Unchecked root,UncheckedSubList parent){
    super();
    this.root=root;
    this.parent=parent;
  }
  UncheckedSubList(Unchecked root,UncheckedSubList parent,Node onlyNode){
    super(onlyNode);
    this.root=root;
    this.parent=parent;
  }
  UncheckedSubList(Unchecked root,UncheckedSubList parent,Node head,int size,Node tail){
    super(head,size,tail);
    this.root=root;
    this.parent=parent;
  }
  @Override public boolean add(boolean val){
    final var root=this.root;
    final var v=TypeUtil.castToFloat(val);
    if(++size!=1){
      appendHelper(v);
    }else{
      initHelper(root,v);
    }
    ++root.size;
    return true;
  }
  @Override public boolean add(char val){
    final var root=this.root;
    if(++size!=1){
      appendHelper(val);
    }else{
      initHelper(root,val);
    }
    ++root.size;
    return true;
  }
  @Override public boolean add(float val){
    final var root=this.root;
    if(++size!=1){
      appendHelper(val);
    }else{
      initHelper(root,val);
    }
    ++root.size;
    return true;
  }
  @Override public boolean add(Float val){
    final var root=this.root;
    if(++size!=1){
      appendHelper(val);
    }else{
      initHelper(root,val);
    }
    ++root.size;
    return true;
  }
  @Override public boolean add(int val){
    final var root=this.root;
    if(++size!=1){
      appendHelper(val);
    }else{
      initHelper(root,val);
    }
    ++root.size;
    return true;
  }
  @Override public void add(int index,float val){
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
          Node before,after;
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
  @Override public boolean add(long val){
    final var root=this.root;
    if(++size!=1){
      appendHelper(val);
    }else{
      initHelper(root,val);
    }
    ++root.size;
    return true;
  }
  @Override public boolean add(short val){
    final var root=this.root;
    if(++size!=1){
      appendHelper(val);
    }else{
      initHelper(root,val);
    }
    ++root.size;
    return true;
  }
  @Override public void clear(){
    if(size!=0){
      bubbleUpClear();
      staticClear(root);
    }
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfFloat iterator(){
    return new UncheckedAscendingSubItr(this);
  }
  @Override public OmniListIterator.OfFloat listIterator(){
    return new UncheckedSubList.BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfFloat listIterator(int index){
    return new UncheckedSubList.BidirectionalItr(this,super.getItrNode(index,size-index),index);
  }
  @Override public float removeFloatAt(int index){
    Node node;
    int size;
    if((size=--this.size)!=0){
      if(index==0){
        node=uncheckedExtractHead(size);
      }else{
        int tailDist;
        if((tailDist=size-index)==0){
          node=uncheckedExtractTail(size);
        }else{
          UncheckedSubList parent;
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
  @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
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
  void appendHelper(float val){
    Node newNode;
    tail=newNode=new Node(tail,val);
    UncheckedSubList parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpRootPushTail(newNode);
    }
  }
  void ascItrRemove(Node cursor){
    final Unchecked root;
    --(root=this.root).size;
    if(cursor!=null){
      Node lastRet;
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
  void bidirectItrRemove(Node lastRet){
    final Unchecked root;
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
  @Override final boolean collapseBody(Node head,Node tail,FloatPredicate filter){
    Node prev;
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
  @Override void collapseTail(Node head,Node tail,FloatPredicate filter){
    int numRemoved;
    for(numRemoved=1;(tail=tail.prev)!=head;++numRemoved){
      if(!filter.test(tail.val)){
        numRemoved+=UncheckedList.collapseBodyHelper(head,tail,filter);
        break;
      }
    }
    bubbleUpRootCollapseTail(numRemoved,tail);
    Unchecked root;
    staticSetTail(root=this.root,tail);
    root.size-=numRemoved;
  }
  @Override void findNewHead(Node head,FloatPredicate filter){
    final Node tail;
    if((tail=this.tail)==head){
      bubbleUpClear();
      staticClear(root);
    }else if(filter.test(tail.val)){
      collapseHeadAndTail(head,tail,filter);
    }else{
      rootCollapseHead(head.next,tail,filter);
    }
  }
  int getParentOffset(){
    return 0;
  }
  void initHelper(Unchecked root,float val){
    final var newNode=new Node(val);
    for(var curr=this;;curr.size=1){
      staticInit(curr,newNode);
      if((curr=curr.parent)==null){
        staticInit(root,newNode);
        return;
      }
    }
  }
  void prependHelper(float val){
    Node newNode;
    head=newNode=new Node(val,head);
    UncheckedSubList parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpRootPushHead(newNode);
    }
  }
  void removeFirstHelper(Node curr){
    final Unchecked root;
    --(root=this.root).size;
    if(curr==tail){
      bubbleUpClear();
      staticClear(root);
    }else{
      bubbleUpRootEraseHead(curr=curr.next);
      staticSetHead(root,curr);
    }
  }
  Node uncheckedExtractHead(int newSize){
    Node oldHead,newHead;
    bubbleUpRootEraseHead(newHead=(oldHead=head).next);
    final Unchecked root;
    staticSetHead(root=this.root,newHead);
    root.size=newSize;
    return oldHead;
  }
  Node uncheckedExtractLastNode(){
    final var lastNode=head;
    bubbleUpClear();
    staticClear(root);
    return lastNode;
  }
  Node uncheckedExtractTail(int newSize){
    Node oldTail,newTail;
    bubbleUpRootEraseTail(newTail=(oldTail=tail).prev);
    final Unchecked root;
    staticSetTail(root=this.root,newTail);
    root.size=newSize;
    return oldTail;
  }
  @Override boolean uncheckedRemoveFirstFlt0(Node head){
    if(head.val==0){
      removeFirstHelper(head);
      return true;
    }
    return uncheckedRemoveFirstFlt0Helper(head);
  }
  boolean uncheckedRemoveFirstFlt0Helper(Node curr){
    final var tail=this.tail;
    Node prev;
    do{
      if(curr==tail){ return false; }
    }while((curr=(prev=curr).next).val!=0);
    final Unchecked root;
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
  @Override boolean uncheckedRemoveFirstFltBits(Node head,int fltBits){
    if(Float.floatToRawIntBits(head.val)==fltBits){
      removeFirstHelper(head);
      return true;
    }
    return uncheckedRemoveFirstFltBitsHelper(head,fltBits);
  }
  boolean uncheckedRemoveFirstFltBitsHelper(Node curr,int fltBits){
    final var tail=this.tail;
    Node prev;
    do{
      if(curr==tail){ return false; }
    }while(Float.floatToRawIntBits((curr=(prev=curr).next).val)!=fltBits);
    final Unchecked root;
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
  @Override boolean uncheckedRemoveFirstFltNaN(Node head){
    if(Float.isNaN(head.val)){
      removeFirstHelper(head);
      return true;
    }
    return uncheckedRemoveFirstFltNaNHelper(head);
  }
  boolean uncheckedRemoveFirstFltNaNHelper(Node curr){
    final var tail=this.tail;
    Node prev;
    do{
      if(curr==tail){ return false; }
    }while(!Float.isNaN((curr=(prev=curr).next).val));
    final Unchecked root;
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
  private void bubbleUpClear(){
    var curr=this;
    do{
      staticClear(curr);
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpCollapseHeadAndTail(Node newHead,Node newTail,int newSize){
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
  private void bubbleUpPrefixCollapseTail(int numRemoved,Node oldTail,Node newTail){
    var curr=this;
    while(curr.tail==oldTail){
      curr.size-=numRemoved;
      curr.tail=newTail;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpDecrementSize(numRemoved);
  }
  private void bubbleUpPrefixPushTail(Node newTail,Node oldTail){
    var curr=this;
    while(curr.tail==oldTail){
      ++curr.size;
      curr.tail=newTail;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpIncrementSize();
  }
  private void bubbleUpRootCollapseHead(int numRemoved,Node newHead){
    var curr=this;
    do{
      curr.size-=numRemoved;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootCollapseTail(int numRemoved,Node newTail){
    var curr=this;
    do{
      curr.size-=numRemoved;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootEraseHead(Node newHead){
    var curr=this;
    do{
      --curr.size;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootEraseTail(Node newTail){
    var curr=this;
    do{
      --curr.size;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootPushHead(Node newHead){
    var curr=this;
    do{
      ++curr.size;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootPushTail(Node newTail){
    var curr=this;
    do{
      ++curr.size;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpSuffixCollapseHead(int numRemoved,Node oldHead,Node newHead){
    var curr=this;
    while(curr.head==oldHead){
      curr.size-=numRemoved;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpDecrementSize(numRemoved);
  }
  private void bubbleUpSuffixEraseHead(Node oldHead,Node newHead){
    var curr=this;
    do{
      --curr.size;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }while(curr.head==oldHead);
    curr.bubbleUpDecrementSize();
    joinNodes(oldHead.prev,newHead);
  }
  private void bubbleUpSuffixPushHead(Node newHead,Node oldHead){
    var curr=this;
    while(curr.head==oldHead){
      ++curr.size;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpIncrementSize();
  }
  private void collapseHeadAndTail(Node head,Node tail,FloatPredicate filter){
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
        Unchecked root;
        staticSetHead(root=this.root,head);
        staticSetTail(root,tail);
        root.size=numRemoved;
        return;
      }
    }
  }
  private OmniList.OfFloat getDefaultSubList(Unchecked root,int headDist,int subListSize,int tailDist){
    final Node subListHead=head;
    Node subListTail=tail;
    if(tailDist==0){
      if(headDist==0){ return new UncheckedSubList(root,this,subListHead,subListSize,subListTail); }
      return new UncheckedSubList.Suffix(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
          :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail);
    }
    subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
        :uncheckedIterateForward(subListHead,subListSize);
    if(headDist==0){ return new UncheckedSubList.Prefix(root,this,subListHead,subListSize,subListTail); }
    return new UncheckedSubList.Body(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
        :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail,headDist);
  }
  private OmniList.OfFloat getEmptySubList(Unchecked root,int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new UncheckedSubList(root,this); }
      return new UncheckedSubList.Suffix(root,this);
    }else if(headDist==0){ return new UncheckedSubList.Prefix(root,this); }
    return new UncheckedSubList.Body(root,this,headDist);
  }
  private OmniList.OfFloat getSubList1(Unchecked root,int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new UncheckedSubList(root,this,head); }
      return new UncheckedSubList.Suffix(root,this,tail);
    }else if(headDist==0){ return new UncheckedSubList.Prefix(root,this,head); }
    return new UncheckedSubList.Body(root,this,staticGetNode(this,headDist,tailDist),headDist);
  }
  private void insertHelper(Node before,float val,Node after){
    new Node(before,val,after);
    UncheckedSubList parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpIncrementSize();
    }
  }
  private void privateCollapseHeadAndTail(int size,Node head,Node tail){
    this.size=size;
    this.head=head;
    this.tail=tail;
  }
  private void rootCollapseHead(Node headCandidate,Node tail,FloatPredicate filter){
    int numRemoved;
    for(numRemoved=1;headCandidate!=tail;++numRemoved,headCandidate=headCandidate.next){
      if(!filter.test(headCandidate.val)){
        numRemoved+=UncheckedList.collapseBodyHelper(headCandidate,tail,filter);
        break;
      }
    }
    bubbleUpRootCollapseHead(numRemoved,headCandidate);
    final Unchecked root;
    staticSetHead(root=this.root,headCandidate);
    root.size-=numRemoved;
  }
  private void suffixCollapseHead(Node oldHead,Node oldTail,FloatPredicate filter){
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
  static class Body extends UncheckedSubList.Prefix{
    private transient final int parentOffset;
    Body(Unchecked root,UncheckedSubList parent,int parentOffset){
      super(root,parent);
      this.parentOffset=parentOffset;
    }
    Body(Unchecked root,UncheckedSubList parent,Node onlyNode,int parentOffset){
      super(root,parent,onlyNode);
      this.parentOffset=parentOffset;
    }
    Body(Unchecked root,UncheckedSubList parent,Node head,int size,Node tail,int parentOffset){
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
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
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
    @Override void ascItrRemove(Node cursor){
      --root.size;
      if(cursor!=null){
        Node lastRet;
        if((lastRet=cursor.prev)==head){
          ((UncheckedSubList)this).bubbleUpSuffixEraseHead(lastRet,cursor);
        }else{
          ((UncheckedSubList)this).bubbleUpDecrementSize();
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
    @Override void bidirectItrRemove(Node lastRet){
      --root.size;
      if(lastRet==tail){
        if(lastRet==head){
          bubbleUpClear(1,lastRet,lastRet);
        }else{
          super.bubbleUpPrefixEraseTail(lastRet,lastRet.prev);
        }
      }else{
        if(lastRet==head){
          ((UncheckedSubList)this).bubbleUpSuffixEraseHead(lastRet,lastRet.next);
        }else{
          ((UncheckedSubList)this).bubbleUpDecrementSize();
          joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }
    @Override void findNewHead(Node curr,FloatPredicate filter){
      final Node tail;
      if(curr==(tail=this.tail)){
        bubbleUpClear(1,curr,tail);
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(curr,tail,filter);
      }else{
        ((UncheckedSubList)this).suffixCollapseHead(curr,tail,filter);
      }
    }
    @Override int getParentOffset(){
      return parentOffset;
    }
    @Override void initHelper(Unchecked root,float val){
      final Node newNode;
      staticInit(this,newNode=new Node(val));
      UncheckedSubList parent,curr;
      if((parent=(curr=this).parent)!=null){
        do{
          int parentSize;
          if((parentSize=parent.size)!=0){
            Node before,after;
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
    @Override void prependHelper(float val){
      Node newNode,oldHead;
      head=newNode=new Node((oldHead=head).prev,val,oldHead);
      UncheckedSubList parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpSuffixPushHead(newNode,oldHead);
      }
    }
    @Override void removeFirstHelper(Node curr){
      --root.size;
      if(curr==tail){
        bubbleUpClear(1,head,curr);
      }else{
        ((UncheckedSubList)this).bubbleUpSuffixEraseHead(curr,curr.next);
      }
    }
    @Override Node uncheckedExtractHead(int newSize){
      Node oldHead;
      ((UncheckedSubList)this).bubbleUpSuffixEraseHead(oldHead=head,oldHead.next);
      --root.size;
      return oldHead;
    }
    @Override Node uncheckedExtractLastNode(){
      final Node lastNode;
      bubbleUpClear(1,lastNode=head,lastNode);
      --root.size;
      return lastNode;
    }
    private void bubbleUpClear(int size,Node head,Node tail){
      final Node prev=head.prev,next=tail.next;
      for(UncheckedSubList curr=this;;){
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
    private void bubbleUpCollapseHeadAndTail(Node oldHead,Node oldTail,Node newHead,Node newTail,int numRemoved){
      final int newSize=size-numRemoved;
      for(UncheckedSubList curr=this;;){
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
    private void collapseHeadAndTail(final Node head,final Node tail,final FloatPredicate filter){
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
    private OmniList.OfFloat getDefaultSubList(Unchecked root,int headDist,int subListSize,int tailDist){
      Node subListHead,subListTail;
      if(headDist<=tailDist){
        subListHead=iterateForward(head,headDist);
        subListTail=tailDist<subListSize?iterateReverse(tail,tailDist):uncheckedIterateForward(subListHead,subListSize);
      }else{
        subListTail=iterateReverse(tail,tailDist);
        subListHead=headDist<subListSize?iterateForward(head,headDist):uncheckedIterateReverse(subListTail,subListSize);
      }
      return new UncheckedSubList.Body(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfFloat getEmptySubList(Unchecked root,int headDist){
      return new UncheckedSubList.Body(root,this,headDist);
    }
    private OmniList.OfFloat getSubList1(Unchecked root,int headDist,int tailDist){
      return new UncheckedSubList.Body(root,this,staticGetNode(this,headDist,tailDist),headDist);
    }
  }
  static class Prefix extends UncheckedSubList{
    Prefix(Unchecked root,UncheckedSubList parent){
      super(root,parent);
    }
    Prefix(Unchecked root,UncheckedSubList parent,Node onlyNode){
      super(root,parent,onlyNode);
    }
    Prefix(Unchecked root,UncheckedSubList parent,Node head,int size,Node tail){
      super(root,parent,head,size,tail);
    }
    @Override public void clear(){
      int size;
      if((size=this.size)!=0){
        Node tmp;
        bubbleUpClear(size,tmp=tail,tmp=tmp.next);
        final Unchecked root;
        staticSetHead(root=this.root,tmp);
        root.size-=size;
      }
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
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
    @Override void appendHelper(float val){
      Node newNode,oldTail;
      tail=newNode=new Node(oldTail=tail,val,oldTail.next);
      UncheckedSubList parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpPrefixPushTail(newNode,oldTail);
      }
    }
    @Override void ascItrRemove(Node cursor){
      final Unchecked root;
      --(root=this.root).size;
      if(cursor!=null){
        Node lastRet;
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
    @Override void bidirectItrRemove(Node lastRet){
      final Unchecked root;
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
    @Override void collapseTail(Node oldHead,Node oldTail,FloatPredicate filter){
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
    @Override void findNewHead(Node curr,FloatPredicate filter){
      final Node tail;
      if((tail=this.tail)==curr){
        bubbleUpClear(1,tail,curr=tail.next);
        final Unchecked root;
        staticSetHead(root=this.root,tail);
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(curr,tail,filter);
      }else{
        super.rootCollapseHead(curr.next,tail,filter);
      }
    }
    @Override void initHelper(Unchecked root,float val){
      final Node after,newNode=new Node(val);
      for(UncheckedSubList curr=this;;curr.size=1){
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
    @Override Node uncheckedExtractLastNode(){
      final Node next,lastNode;
      final Unchecked root;
      staticSetHead(root=this.root,next=(lastNode=tail).next);
      bubbleUpClear(1,lastNode,next);
      --root.size;
      return lastNode;
    }
    @Override Node uncheckedExtractTail(int newSize){
      Node oldTail;
      bubbleUpPrefixEraseTail(oldTail=tail,oldTail.prev);
      --root.size;
      return oldTail;
    }
    @Override boolean uncheckedRemoveFirstFlt0Helper(Node curr){
      final var tail=this.tail;
      Node prev;
      do{
        if(curr==tail){ return false; }
      }while((curr=(prev=curr).next).val!=0);
      --root.size;
      if(curr==tail){
        bubbleUpPrefixEraseTail(curr,prev);
      }else{
        super.bubbleUpDecrementSize();
        joinNodes(prev,curr.next);
      }
      return true;
    }
    @Override boolean uncheckedRemoveFirstFltBitsHelper(Node curr,int fltBits){
      final var tail=this.tail;
      Node prev;
      do{
        if(curr==tail){ return false; }
      }while(Float.floatToRawIntBits((curr=(prev=curr).next).val)!=fltBits);
      --root.size;
      if(curr==tail){
        bubbleUpPrefixEraseTail(curr,prev);
      }else{
        super.bubbleUpDecrementSize();
        joinNodes(prev,curr.next);
      }
      return true;
    }
    @Override boolean uncheckedRemoveFirstFltNaNHelper(Node curr){
      final var tail=this.tail;
      Node prev;
      do{
        if(curr==tail){ return false; }
      }while(!Float.isNaN((curr=(prev=curr).next).val));
      --root.size;
      if(curr==tail){
        bubbleUpPrefixEraseTail(curr,prev);
      }else{
        super.bubbleUpDecrementSize();
        joinNodes(prev,curr.next);
      }
      return true;
    }
    private void bubbleUpClear(int size,Node tail,final Node next){
      for(UncheckedSubList curr=this;;){
        staticClear(curr);
        if((curr=curr.parent)==null){
          break;
        }else if(curr.tail!=tail){
          curr.bubbleUpRootCollapseHead(size,next);
          break;
        }
      }
    }
    private void bubbleUpCollapseHeadAndTail(Node oldTail,Node newHead,Node newTail,int numRemoved){
      final int newSize=size-numRemoved;
      for(UncheckedSubList curr=this;;){
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
    private void bubbleUpPrefixEraseTail(Node oldTail,Node newTail){
      UncheckedSubList curr=this;
      do{
        --curr.size;
        curr.tail=newTail;
        if((curr=curr.parent)==null){ return; }
      }while(curr.tail==oldTail);
      curr.bubbleUpDecrementSize();
      joinNodes(newTail,oldTail.next);
    }
    private void collapseHeadAndTail(Node headCandidate,Node tail,FloatPredicate filter){
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
    private OmniList.OfFloat getDefaultSubList(Unchecked root,int headDist,int subListSize,int tailDist){
      Node subListHead=head,subListTail=tail;
      if(headDist==0){ return new UncheckedSubList.Prefix(root,this,subListHead,subListSize,tailDist<=subListSize
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
      return new UncheckedSubList.Body(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfFloat getEmptySubList(Unchecked root,int headDist){
      if(headDist==0){ return new UncheckedSubList.Prefix(root,this); }
      return new UncheckedSubList.Body(root,this,headDist);
    }
    private OmniList.OfFloat getSubList1(Unchecked root,int headDist,int tailDist){
      if(headDist==0){ return new UncheckedSubList.Prefix(root,this,head); }
      return new UncheckedSubList.Body(root,this,
          tailDist<headDist?iterateReverse(tail,tailDist):uncheckedIterateForward(head,headDist),headDist);
    }
  }
  static class Suffix extends UncheckedSubList{
    Suffix(Unchecked root,UncheckedSubList parent){
      super(root,parent);
    }
    Suffix(Unchecked root,UncheckedSubList parent,Node onlyNode){
      super(root,parent,onlyNode);
    }
    Suffix(Unchecked root,UncheckedSubList parent,Node head,int size,Node tail){
      super(root,parent,head,size,tail);
    }
    @Override public void clear(){
      int size;
      if((size=this.size)!=0){
        Node tmp;
        bubbleUpClear(size,tmp=head,tmp=tmp.prev);
        final Unchecked root;
        staticSetTail(root=this.root,tmp);
        root.size-=size;
      }
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
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
    @Override void ascItrRemove(Node cursor){
      final Unchecked root;
      --(root=this.root).size;
      if(cursor!=null){
        Node lastRet;
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
    @Override void bidirectItrRemove(Node lastRet){
      final Unchecked root;
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
    @Override void findNewHead(Node curr,FloatPredicate filter){
      final Node tail;
      if(curr==(tail=this.tail)){
        bubbleUpClear(1,curr,curr=curr.prev);
        final Unchecked root;
        staticSetTail(root=this.root,curr);
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(curr,tail,filter);
      }else{
        super.suffixCollapseHead(curr,tail,filter);
      }
    }
    @Override void initHelper(Unchecked root,float val){
      final Node before,newNode=new Node(val);
      for(UncheckedSubList curr=this;;curr.size=1){
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
    @Override void prependHelper(float val){
      Node newNode,oldHead;
      head=newNode=new Node((oldHead=head).prev,val,oldHead);
      UncheckedSubList parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpSuffixPushHead(newNode,oldHead);
      }
    }
    @Override void removeFirstHelper(Node curr){
      Unchecked root;
      --(root=this.root).size;
      if(curr==tail){
        bubbleUpClear(1,curr,curr=curr.prev);
        staticSetTail(root,curr);
      }else{
        super.bubbleUpSuffixEraseHead(curr,curr.next);
      }
    }
    @Override Node uncheckedExtractHead(int newSize){
      Node oldHead;
      super.bubbleUpSuffixEraseHead(oldHead=head,oldHead.next);
      --root.size;
      return oldHead;
    }
    @Override Node uncheckedExtractLastNode(){
      final Node prev,lastNode;
      Unchecked root;
      staticSetTail(root=this.root,prev=(lastNode=head).prev);
      bubbleUpClear(1,lastNode,prev);
      --root.size;
      return lastNode;
    }
    private void bubbleUpClear(int size,Node head,final Node prev){
      for(UncheckedSubList curr=this;;){
        staticClear(curr);
        if((curr=curr.parent)==null){
          break;
        }else if(curr.head!=head){
          curr.bubbleUpRootCollapseTail(size,prev);
          break;
        }
      }
    }
    private void bubbleUpCollapseHeadAndTail(Node oldHead,Node newHead,Node newTail,int numRemoved){
      final int newSize=size-numRemoved;
      for(UncheckedSubList curr=this;;){
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
    private void collapseHeadAndTail(Node head,Node tailCandidate,FloatPredicate filter){
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
    private OmniList.OfFloat getDefaultSubList(Unchecked root,int headDist,int subListSize,int tailDist){
      Node subListHead=head,subListTail=tail;
      if(tailDist==0){ return new UncheckedSubList.Suffix(root,this,headDist<=subListSize
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
      return new UncheckedSubList.Body(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfFloat getEmptySubList(Unchecked root,int headDist,int tailDist){
      if(tailDist==0){ return new UncheckedSubList.Prefix(root,this); }
      return new UncheckedSubList.Body(root,this,headDist);
    }
    private OmniList.OfFloat getSubList1(Unchecked root,int headDist,int tailDist){
      if(tailDist==0){ return new UncheckedSubList.Suffix(root,this,tail); }
      return new UncheckedSubList.Body(root,this,staticGetNode(this,headDist,tailDist),headDist);
    }
  }
  private static class BidirectionalItr extends UncheckedAscendingSubItr implements OmniListIterator.OfFloat{
    private transient int nextIndex;
    private transient Node lastRet;
    private BidirectionalItr(UncheckedSubList parent){
      super(parent);
    }
    private BidirectionalItr(UncheckedSubList parent,Node cursor,int nextIndex){
      super(parent,cursor);
      this.nextIndex=nextIndex;
    }
    @Override public void add(float val){
      final int nextIndex=this.nextIndex++;
      int size;
      final UncheckedSubList parent;
      final var root=(parent=this.parent).root;
      if((size=parent.size++)!=0){
        if(nextIndex==0){
          parent.prependHelper(val);
        }else if(nextIndex==size){
          parent.appendHelper(val);
        }else{
          Node cursor;
          parent.insertHelper((cursor=this.cursor).prev,val,cursor);
        }
      }else{
        parent.initHelper(root,val);
      }
      ++root.size;
      lastRet=null;
    }
    @Override public void forEachRemaining(Consumer<? super Float> action){
      Node cursor;
      if((cursor=this.cursor)!=null){
        UncheckedSubList parent;
        uncheckedForEachForward(cursor,cursor=(parent=this.parent).tail,action::accept);
        nextIndex=parent.size;
        this.cursor=null;
        lastRet=cursor;
      }
    }
    @Override public void forEachRemaining(FloatConsumer action){
      Node cursor;
      if((cursor=this.cursor)!=null){
        UncheckedSubList parent;
        uncheckedForEachForward(cursor,cursor=(parent=this.parent).tail,action);
        nextIndex=parent.size;
        this.cursor=null;
        lastRet=cursor;
      }
    }
    @Override public boolean hasPrevious(){
      return nextIndex!=0;
    }
    @Override public float nextFloat(){
      Node cursor;
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
      return nextIndex;
    }
    @Override public float previousFloat(){
      Node lastRet;
      this.lastRet=lastRet=cursor.prev;
      cursor=lastRet;
      --nextIndex;
      return lastRet.val;
    }
    @Override public int previousIndex(){
      return nextIndex-1;
    }
    @Override public void remove(){
      Node lastRet;
      parent.bidirectItrRemove(lastRet=this.lastRet);
      if(lastRet!=cursor){
        --nextIndex;
      }
      this.lastRet=null;
    }
    @Override public void set(float val){
      lastRet.val=val;
    }
  }
}
