package omni.impl.seq.dbllnk.ofref;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import omni.util.BitSetUtils;
import omni.util.OmniPred;
class CheckedSubList<E>extends AbstractRefDblLnkSeq<E>{
  static class Suffix<E>extends CheckedSubList<E>{
    Suffix(Checked<E> root,CheckedSubList<E> parent){
      super(root,parent);
    }
    Suffix(Checked<E> root,CheckedSubList<E> parent,Node<E> onlyNode){
      super(root,parent,onlyNode);
    }
    Suffix(Checked<E> root,CheckedSubList<E> parent,Node<E> head,int size,Node<E> tail){
      super(root,parent,head,size,tail);
    }
    @Override public void clear(){
      int modCount;
      Checked<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        Node<E> tmp;
        bubbleUpClear(modCount,size,tmp=head,tmp=tmp.prev);
        staticSetTail(root,tmp);
        root.size-=size;
      }
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      Checked<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
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
    @Override void ascItrRemove(CheckedAscendingSubItr<E> itr){
      Node<E> lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked<E> root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount,root);
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void bidirectItrRemove(CheckedSubList.BidirectionalItr<E> itr){
      Node<E> lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked<E> root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount,root);
        if(lastRet!=itr.cursor){
          --itr.nextIndex;
        }
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter){
      final Node<E> tail;
      if(curr==(tail=this.tail)){
        Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        bubbleUpClear(modCount,1,curr,curr=curr.prev);
        staticSetTail(root,curr);
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(modCount,curr,tail,filter);
      }else{
        super.suffixCollapseHead(modCount,curr,tail,filter);
      }
    }
    @Override void initHelper(Checked<E> root,E val,int modCount){
      final Node<E> before,newNode=new Node<>(val);
      for(CheckedSubList<E> curr=this;;curr.size=1){
        curr.privateInit(newNode,modCount);
        if((curr=curr.parent)==null){
          before=root.tail;
          break;
        }else if(curr.size!=0){
          before=curr.tail;
          curr.bubbleUpRootPushTail(modCount,newNode);
          break;
        }
      }
      joinNodes(before,newNode);
    }
    @Override void prependHelper(E val,int modCount){
      this.modCount=modCount;
      Node<E> newNode,oldHead;
      head=newNode=new Node<>((oldHead=head).prev,val,oldHead);
      CheckedSubList<E> parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpSuffixPushHead(modCount,newNode,oldHead);
      }
    }
    @Override void removeFirstHelper(int modCount,Node<E> curr){
      Checked<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      if(curr==tail){
        bubbleUpClear(modCount,1,curr,curr=curr.prev);
        staticSetTail(root,curr);
      }else{
        super.bubbleUpSuffixEraseHead(modCount,curr,curr.next);
      }
      --root.size;
    }
    @Override Node<E> uncheckedExtractHead(int newSize){
      Checked<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      Node<E> oldHead;
      super.bubbleUpSuffixEraseHead(modCount,oldHead=head,oldHead.next);
      --root.size;
      return oldHead;
    }
    @Override Node<E> uncheckedExtractLastNode(){
      int modCount;
      Checked<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      final Node<E> prev,lastNode;
      staticSetTail(root,prev=(lastNode=head).prev);
      bubbleUpClear(modCount,1,lastNode,prev);
      --root.size;
      return lastNode;
    }
    private void bubbleUpClear(int modCount,int size,Node<E> head,final Node<E> prev){
      for(CheckedSubList<E> curr=this;;){
        curr.modCount=modCount;
        staticClear(curr);
        if((curr=curr.parent)==null){
          break;
        }else if(curr.head!=head){
          curr.bubbleUpRootCollapseTail(modCount,size,prev);
          break;
        }
      }
    }
    private void bubbleUpCollapseHeadAndTail(int newModCount,Node<E> oldHead,Node<E> newHead,Node<E> newTail,
        int numRemoved){
      final int newSize=size-numRemoved;
      for(CheckedSubList<E> curr=this;;){
        curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
        if((curr=curr.parent)==null){
          break;
        }
        if(curr.head!=oldHead){
          curr.bubbleUpRootCollapseTail(newModCount,numRemoved,newTail);
          break;
        }
      }
      joinNodes(oldHead.prev,newHead);
    }
    private void collapseHeadAndTail(int modCount,Node<E> head,Node<E> tailCandidate,Predicate<? super E> filter){
      final int oldSize=size;
      int numConsumed=2;
      final var root=this.root;
      for(;;){
        if(numConsumed==oldSize){
          CheckedCollection.checkModCount(modCount,root.modCount);
          bubbleUpClear(++modCount,oldSize,head,tailCandidate=head.prev);
          break;
        }
        ++numConsumed;
        if(!filter.test((tailCandidate=tailCandidate.prev).val)){
          var headCandidate=head;
          for(;;){
            if(numConsumed==oldSize){
              CheckedCollection.checkModCount(modCount,root.modCount);
              --numConsumed;
              break;
            }
            ++numConsumed;
            if(!filter.test((headCandidate=headCandidate.next).val)){
              numConsumed-=2-+CheckedRefDblLnkList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                  oldSize-numConsumed,tailCandidate,filter);
              break;
            }
          }
          bubbleUpCollapseHeadAndTail(++modCount,head,headCandidate,tailCandidate,numConsumed);
          break;
        }
      }
      root.modCount=modCount;
      staticSetTail(root,tailCandidate);
      root.size-=numConsumed;
    }
    private OmniList.OfRef<E> getDefaultSubList(Checked<E> root,int headDist,int subListSize,int tailDist){
      Node<E> subListHead=head,subListTail=tail;
      if(tailDist==0){ return new CheckedSubList.Suffix<>(root,this,headDist<=subListSize
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
      return new CheckedSubList.Body<>(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfRef<E> getEmptySubList(Checked<E> root,int headDist,int tailDist){
      if(tailDist==0){ return new CheckedSubList.Prefix<>(root,this); }
      return new CheckedSubList.Body<>(root,this,headDist);
    }
    private OmniList.OfRef<E> getSubList1(Checked<E> root,int headDist,int tailDist){
      if(tailDist==0){ return new CheckedSubList.Suffix<>(root,this,tail); }
      return new CheckedSubList.Body<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
    }
    private void uncheckedItrRemove(Node<E> lastRet,int modCount,Checked<E> root){
      if(lastRet==tail){
        if(lastRet==head){
          bubbleUpClear(modCount,1,lastRet,lastRet=lastRet.prev);
        }else{
          super.bubbleUpRootEraseTail(modCount,lastRet=lastRet.prev);
        }
        staticSetTail(root,lastRet);
      }else{
        if(lastRet==head){
          super.bubbleUpSuffixEraseHead(modCount,lastRet,lastRet.next);
        }else{
          super.bubbleUpDecrementSize(modCount);
          joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }
  }
  static class Body<E>extends CheckedSubList.Prefix<E>{
    private transient final int parentOffset;
    Body(Checked<E> root,CheckedSubList<E> parent,int parentOffset){
      super(root,parent);
      this.parentOffset=parentOffset;
    }
    Body(Checked<E> root,CheckedSubList<E> parent,Node<E> onlyNode,int parentOffset){
      super(root,parent,onlyNode);
      this.parentOffset=parentOffset;
    }
    Body(Checked<E> root,CheckedSubList<E> parent,Node<E> head,int size,Node<E> tail,int parentOffset){
      super(root,parent,head,size,tail);
      this.parentOffset=parentOffset;
    }
    @Override public void clear(){
      Checked<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        bubbleUpClear(modCount,size,head,tail);
        root.size-=size;
      }
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      Checked<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
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
    @Override void ascItrRemove(CheckedAscendingSubItr<E> itr){
      Node<E> lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked<E> root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount);
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void bidirectItrRemove(CheckedSubList.BidirectionalItr<E> itr){
      Node<E> lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked<E> root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount);
        if(lastRet!=itr.cursor){
          --itr.nextIndex;
        }
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter){
      final Node<E> tail;
      if(curr==(tail=this.tail)){
        Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        bubbleUpClear(++modCount,1,head,tail);
        root.modCount=modCount;
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(modCount,curr,tail,filter);
      }else{
        ((CheckedSubList<E>)this).suffixCollapseHead(modCount,curr,tail,filter);
      }
    }
    @Override int getParentOffset(){
      return this.parentOffset;
    }
    @Override void initHelper(Checked<E> root,E val,int modCount){
      final Node<E> newNode;
      ((CheckedSubList<E>)this).privateInit(newNode=new Node<>(val),modCount);
      CheckedSubList<E> parent,curr;
      if((parent=(curr=this).parent)!=null){
        do{
          int parentSize;
          if((parentSize=parent.size)!=0){
            Node<E> before,after;
            int headDist,tailDist;
            if((headDist=curr.getParentOffset())==0){
              parent.bubbleUpSuffixPushHead(modCount,newNode,after=parent.head);
              before=after.prev;
            }else if((tailDist=parentSize-headDist)==0){
              parent.bubbleUpPrefixPushTail(modCount,newNode,before=parent.tail);
              after=before.next;
            }else{
              if(headDist<=tailDist){
                after=(before=iterateForward(root.head,headDist-1)).next;
              }else{
                before=(after=iterateReverse(root.tail,tailDist-1)).prev;
              }
              parent.bubbleUpIncrementSize(modCount);
            }
            joinNodes(before,newNode);
            joinNodes(newNode,after);
            return;
          }
          parent=(curr=parent).parent;
          curr.privateInit(newNode,modCount);
          curr.size=1;
        }while(parent!=null);
      }
      subSeqInsertHelper(root,newNode,curr.getParentOffset());
    }
    @Override void prependHelper(E val,int modCount){
      this.modCount=modCount;
      Node<E> newNode,oldHead;
      head=newNode=new Node<>((oldHead=head).prev,val,oldHead);
      CheckedSubList<E> parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpSuffixPushHead(modCount,newNode,oldHead);
      }
    }
    @Override void removeFirstHelper(int modCount,Node<E> curr){
      Checked<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      if(curr==tail){
        bubbleUpClear(modCount,1,head,curr);
      }else{
        ((CheckedSubList<E>)this).bubbleUpSuffixEraseHead(modCount,curr,curr.next);
      }
      --root.size;
    }
    @Override Node<E> uncheckedExtractHead(int newSize){
      Checked<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      Node<E> oldHead;
      ((CheckedSubList<E>)this).bubbleUpSuffixEraseHead(modCount,oldHead=head,oldHead.next);
      --root.size;
      return oldHead;
    }
    @Override Node<E> uncheckedExtractLastNode(){
      final Node<E> lastNode;
      final Checked<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      bubbleUpClear(modCount,1,lastNode=head,lastNode);
      --root.size;
      return lastNode;
    }
    private void bubbleUpClear(int modCount,int size,Node<E> head,Node<E> tail){
      final Node<E> prev=head.prev,next=tail.next;
      for(CheckedSubList<E> curr=this;;){
        curr.modCount=modCount;
        staticClear(curr);
        if((curr=curr.parent)!=null){
          if(curr.head!=head){
            curr.bubbleUpPrefixCollapseTail(modCount,size,tail,prev);
            break;
          }else if(curr.tail!=tail){
            curr.modCount=modCount;
            curr.size-=size;
            curr.head=next;
            if((curr=curr.parent)!=null){
              curr.bubbleUpSuffixCollapseHead(modCount,size,head,next);
            }
            break;
          }
          continue;
        }
        break;
      }
      joinNodes(prev,next);
    }
    private void bubbleUpCollapseHeadAndTail(int newModCount,Node<E> oldHead,Node<E> oldTail,Node<E> newHead,
        Node<E> newTail,int numRemoved){
      final int newSize=size-numRemoved;
      for(CheckedSubList<E> curr=this;;){
        curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
        if((curr=curr.parent)==null){
          break;
        }
        if(curr.head!=oldHead){
          curr.bubbleUpPrefixCollapseTail(newModCount,numRemoved,oldTail,newTail);
          break;
        }else if(curr.tail!=oldTail){
          curr.modCount=newModCount;
          curr.size-=numRemoved;
          curr.head=newHead;
          if((curr=curr.parent)!=null){
            curr.bubbleUpSuffixCollapseHead(newModCount,numRemoved,oldHead,newHead);
          }
          break;
        }
      }
      joinNodes(oldHead.prev,newHead);
      joinNodes(newTail,oldTail.next);
    }
    private void collapseHeadAndTail(int modCount,final Node<E> head,final Node<E> tail,
        final Predicate<? super E> filter){
      final int oldSize=size;
      int numConsumed=2;
      var headCandidate=head;
      final var root=this.root;
      for(;;){
        if(numConsumed==oldSize){
          CheckedCollection.checkModCount(modCount,root.modCount);
          bubbleUpClear(++modCount,oldSize,head,tail);
          break;
        }
        ++numConsumed;
        if(!filter.test((headCandidate=headCandidate.next).val)){
          var tailCandidate=tail;
          for(;;){
            if(numConsumed==oldSize){
              CheckedCollection.checkModCount(modCount,root.modCount);
              --numConsumed;
              break;
            }
            ++numConsumed;
            if(!filter.test((tailCandidate=tailCandidate.prev).val)){
              numConsumed-=2-CheckedRefDblLnkList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                  oldSize-numConsumed,tailCandidate,filter);
              break;
            }
          }
          bubbleUpCollapseHeadAndTail(++modCount,head,tail,headCandidate,tailCandidate,numConsumed);
          break;
        }
      }
      root.modCount=modCount;
      root.size-=numConsumed;
    }
    private OmniList.OfRef<E> getDefaultSubList(Checked<E> root,int headDist,int subListSize,int tailDist){
      Node<E> subListHead,subListTail;
      if(headDist<=tailDist){
        subListHead=iterateForward(head,headDist);
        subListTail=tailDist<subListSize?iterateReverse(tail,tailDist):uncheckedIterateForward(subListHead,subListSize);
      }else{
        subListTail=iterateReverse(tail,tailDist);
        subListHead=headDist<subListSize?iterateForward(head,headDist):uncheckedIterateReverse(subListTail,subListSize);
      }
      return new CheckedSubList.Body<>(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfRef<E> getEmptySubList(Checked<E> root,int headDist){
      return new CheckedSubList.Body<>(root,this,headDist);
    }
    private OmniList.OfRef<E> getSubList1(Checked<E> root,int headDist,int tailDist){
      return new CheckedSubList.Body<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
    }
    private void uncheckedItrRemove(Node<E> lastRet,int modCount){
      if(lastRet==head){
        if(lastRet==tail){
          bubbleUpClear(modCount,1,lastRet,lastRet);
        }else{
          ((CheckedSubList<E>)this).bubbleUpSuffixEraseHead(modCount,lastRet,lastRet.next);
        }
      }else{
        if(lastRet==tail){
          super.bubbleUpPrefixEraseTail(modCount,lastRet,lastRet.prev);
        }else{
          ((CheckedSubList<E>)this).bubbleUpDecrementSize(modCount);
          joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }
  }
  static class Prefix<E>extends CheckedSubList<E>{
    Prefix(Checked<E> root,CheckedSubList<E> parent){
      super(root,parent);
    }
    Prefix(Checked<E> root,CheckedSubList<E> parent,Node<E> onlyNode){
      super(root,parent,onlyNode);
    }
    Prefix(Checked<E> root,CheckedSubList<E> parent,Node<E> head,int size,Node<E> tail){
      super(root,parent,head,size,tail);
    }
    @Override public void clear(){
      int modCount;
      Checked<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        Node<E> tmp;
        bubbleUpClear(modCount,size,tmp=tail,tmp=tmp.next);
        staticSetHead(root,tmp);
        root.size-=size;
      }
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      Checked<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
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
    @Override void appendHelper(E val,int modCount){
      this.modCount=modCount;
      Node<E> newNode,oldTail;
      tail=newNode=new Node<>(oldTail=tail,val,oldTail.next);
      CheckedSubList<E> parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpPrefixPushTail(modCount,newNode,oldTail);
      }
    }
    @Override void ascItrRemove(CheckedAscendingSubItr<E> itr){
      Node<E> lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked<E> root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount,root);
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void bidirectItrRemove(CheckedSubList.BidirectionalItr<E> itr){
      Node<E> lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked<E> root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount,root);
        if(lastRet!=itr.cursor){
          --itr.nextIndex;
        }
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter){
      final Node<E> tail;
      if(curr==(tail=this.tail)){
        Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        bubbleUpClear(modCount,1,curr,curr=tail.next);
        staticSetHead(root,curr);
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(modCount,curr,tail,filter);
      }else{
        super.rootCollapseHead(modCount,curr.next,tail,filter);
      }
    }
    @Override void initHelper(Checked<E> root,E val,int modCount){
      final Node<E> after,newNode=new Node<>(val);
      for(CheckedSubList<E> curr=this;;curr.size=1){
        curr.privateInit(newNode,modCount);
        if((curr=curr.parent)==null){
          after=root.head;
          break;
        }else if(curr.size!=0){
          after=curr.head;
          curr.bubbleUpRootPushHead(modCount,newNode);
          break;
        }
      }
      joinNodes(newNode,after);
    }
    @Override boolean removeIfHelper(int modCount,Node<E> curr,Predicate<? super E> filter){
      final Node<E> tail;
      if(curr!=(tail=this.tail)){
        if(filter.test(tail.val)){
          prefixCollapseTail(modCount,curr,tail,filter);
          return true;
        }
        return super.collapseBody(modCount,curr,tail,filter);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override Node<E> uncheckedExtractLastNode(){
      Checked<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      final Node<E> next,lastNode;
      staticSetHead(root,next=(lastNode=tail).next);
      bubbleUpClear(modCount,1,lastNode,next);
      --root.size;
      return lastNode;
    }
    @Override Node<E> uncheckedExtractTail(int newSize){
      Checked<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      Node<E> oldTail;
      bubbleUpPrefixEraseTail(modCount,oldTail=tail,oldTail.prev);
      --root.size;
      return oldTail;
    }
    @Override boolean uncheckedRemoveFirstMatchHelper(int modCount,Node<E> curr,Predicate<Object> pred){
      final var root=this.root;
      final var tail=this.tail;
      Node<E> prev;
      do{
        if(curr==tail){
          CheckedCollection.checkModCount(modCount,root.modCount);
          return false;
        }
      }while(!pred.test((curr=(prev=curr).next).val));
      CheckedCollection.checkModCount(modCount,root.modCount);
      root.modCount=++modCount;
      --root.size;
      if(curr==tail){
        bubbleUpPrefixEraseTail(modCount,curr,prev);
      }else{
        super.bubbleUpDecrementSize(modCount);
        joinNodes(prev,curr.next);
      }
      return true;
    }
    private void bubbleUpClear(int modCount,int size,Node<E> tail,final Node<E> next){
      for(CheckedSubList<E> curr=this;;){
        curr.modCount=modCount;
        staticClear(curr);
        if((curr=curr.parent)==null){
          break;
        }else if(curr.tail!=tail){
          curr.bubbleUpRootCollapseHead(modCount,size,next);
          break;
        }
      }
    }
    private void bubbleUpCollapseHeadAndTail(int newModCount,Node<E> oldTail,Node<E> newHead,Node<E> newTail,
        int numRemoved){
      final int newSize=size-numRemoved;
      for(CheckedSubList<E> curr=this;;){
        curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
        if((curr=curr.parent)==null){
          break;
        }
        if(curr.tail!=oldTail){
          curr.bubbleUpRootCollapseHead(newModCount,numRemoved,newHead);
          break;
        }
      }
      joinNodes(newTail,oldTail.next);
    }
    private void bubbleUpPrefixEraseTail(int newModCount,Node<E> oldTail,Node<E> newTail){
      CheckedSubList<E> curr=this;
      do{
        curr.modCount=newModCount;
        --curr.size;
        curr.tail=newTail;
        if((curr=curr.parent)==null){ return; }
      }while(curr.tail==oldTail);
      curr.bubbleUpDecrementSize(newModCount);
      joinNodes(newTail,oldTail.next);
    }
    private void collapseHeadAndTail(int modCount,Node<E> headCandidate,Node<E> tail,Predicate<? super E> filter){
      final int oldSize=size;
      int numConsumed=2;
      final var root=this.root;
      for(;;){
        if(numConsumed==oldSize){
          CheckedCollection.checkModCount(modCount,root.modCount);
          bubbleUpClear(++modCount,oldSize,tail,headCandidate=tail.next);
          break;
        }
        ++numConsumed;
        if(!filter.test((headCandidate=headCandidate.next).val)){
          var tailCandidate=tail;
          for(;;){
            if(numConsumed==oldSize){
              CheckedCollection.checkModCount(modCount,root.modCount);
              --numConsumed;
              break;
            }
            ++numConsumed;
            if(!filter.test((tailCandidate=tailCandidate.prev).val)){
              numConsumed-=2-CheckedRefDblLnkList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                  oldSize-numConsumed,tailCandidate,filter);
              break;
            }
          }
          bubbleUpCollapseHeadAndTail(++modCount,tail,headCandidate,tailCandidate,numConsumed);
          break;
        }
      }
      root.modCount=modCount;
      staticSetHead(root,headCandidate);
      root.size-=numConsumed;
    }
    private OmniList.OfRef<E> getDefaultSubList(Checked<E> root,int headDist,int subListSize,int tailDist){
      Node<E> subListHead=head,subListTail=tail;
      if(headDist==0){ return new CheckedSubList.Prefix<>(root,this,subListHead,subListSize,tailDist<=subListSize
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
      return new CheckedSubList.Body<>(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfRef<E> getEmptySubList(Checked<E> root,int headDist){
      if(headDist==0){ return new CheckedSubList.Prefix<>(root,this); }
      return new CheckedSubList.Body<>(root,this,headDist);
    }
    private OmniList.OfRef<E> getSubList1(Checked<E> root,int headDist,int tailDist){
      if(headDist==0){ return new CheckedSubList.Prefix<>(root,this,head); }
      return new CheckedSubList.Body<>(root,this,
          tailDist<headDist?iterateReverse(tail,tailDist):uncheckedIterateForward(head,headDist),headDist);
    }
    private void prefixCollapseTail(int modCount,Node<E> oldHead,Node<E> oldTail,Predicate<? super E> filter){
      final int oldSize=size;
      int numConsumed=2;
      final var root=this.root;
      var tailCandidate=oldTail.prev;
      for(;;tailCandidate=tailCandidate.prev){
        if(numConsumed==oldSize){
          CheckedCollection.checkModCount(modCount,root.modCount);
          --numConsumed;
          break;
        }
        ++numConsumed;
        if(!filter.test(tailCandidate.val)){
          numConsumed-=2-CheckedRefDblLnkList.collapseBodyHelper(root.new ModCountChecker(modCount),oldHead,
              oldSize-numConsumed,tailCandidate,filter);
          break;
        }
      }
      root.modCount=++modCount;
      super.bubbleUpPrefixCollapseTail(modCount,numConsumed,oldTail,tailCandidate);
      joinNodes(tailCandidate,oldTail.next);
      root.size-=numConsumed;
    }
    private void uncheckedItrRemove(Node<E> lastRet,int modCount,Checked<E> root){
      if(lastRet==head){
        if(lastRet==tail){
          bubbleUpClear(modCount,1,lastRet,lastRet=lastRet.next);
        }else{
          super.bubbleUpRootEraseHead(modCount,lastRet=lastRet.next);
        }
        staticSetHead(root,lastRet);
      }else{
        if(lastRet==tail){
          bubbleUpPrefixEraseTail(modCount,lastRet,lastRet.prev);
        }else{
          super.bubbleUpDecrementSize(modCount);
          joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }
  }
  transient final CheckedSubList<E> parent;
  transient final Checked<E> root;
  transient int modCount;
  CheckedSubList(Checked<E> root,CheckedSubList<E> parent){
    super();
    this.root=root;
    this.parent=parent;
  }
  CheckedSubList(Checked<E> root,CheckedSubList<E> parent,Node<E> onlyNode){
    super(onlyNode);
    this.root=root;
    this.parent=parent;
  }
  CheckedSubList(Checked<E> root,CheckedSubList<E> parent,Node<E> head,int size,Node<E> tail){
    super(head,size,tail);
    this.root=root;
    this.parent=parent;
  }
  @Override public boolean add(E val){
    int modCount;
    Checked<E> root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    if(++size!=1){
      appendHelper(val,modCount);
    }else{
      initHelper(root,val,modCount);
    }
    ++root.size;
    return true;
  }
  @Override public void add(int index,E val){
    int modCount;
    Checked<E> root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkWriteHi(index,size=this.size);
    root.modCount=++modCount;
    if(size!=0){
      if(index==0){
        prependHelper(val,modCount);
      }else{
        int tailDist;
        if((tailDist=size-index)==0){
          appendHelper(val,modCount);
        }else{
          Node<E> before,after;
          if(index<=tailDist){
            after=(before=iterateForward(head,index-1)).next;
          }else{
            before=(after=iterateReverse(tail,tailDist-1)).prev;
          }
          insertHelper(before,val,after,modCount);
        }
      }
    }else{
      initHelper(root,val,modCount);
    }
    ++root.size;
    this.size=size+1;
  }
  @Override public void clear(){
    int modCount;
    Checked<E> root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    if(size!=0){
      root.modCount=++modCount;
      bubbleUpClear(modCount);
      staticClear(root);
    }
  }
  @Override public Object clone(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    Node<E> newHead,newTail;
    int size;
    if((size=this.size)!=0){
      Node<E> oldHead,oldTail;
      for(newHead=new Node<>((oldHead=head).val),newTail=newHead,oldTail=tail;oldHead!=oldTail;
          newTail=new Node<>(newTail,(oldHead=oldHead.next).val)){}
    }else{
      newHead=null;
      newTail=null;
    }
    return new CheckedRefDblLnkList<>(newHead,size,newTail);
  }
  @Override public boolean contains(boolean val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(Boolean val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(byte val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(Byte val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(char val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(Character val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(double val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(Double val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(float val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(Float val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(int val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(Integer val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(long val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(Long val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(Object val){
    final int modCount=this.modCount;
    try{
      return super.contains(val);
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public boolean contains(short val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(Short val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public void forEach(Consumer<? super E> action){
    final int modCount=this.modCount;
    try{
      super.forEach(action);
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public E get(int index){
    CheckedCollection.checkModCount(modCount,root.modCount);
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    return staticGetNode(this,index,size-index).val;
  }
  @Override public int hashCode(){
    final int modCount=this.modCount;
    try{
      return super.hashCode();
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public int indexOf(boolean val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(Boolean val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(byte val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(Byte val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(char val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(Character val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(double val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(Double val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(float val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(Float val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(int val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(Integer val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(long val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(Long val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(Object val){
    final int modCount=this.modCount;
    try{
      return super.indexOf(val);
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public int indexOf(short val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(Short val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public boolean isEmpty(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return size==0;
  }
  @Override public OmniIterator.OfRef<E> iterator(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return new CheckedAscendingSubItr<>(this);
  }
  @Override public int lastIndexOf(boolean val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(Boolean val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(byte val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(Byte val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(char val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(Character val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(double val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(Double val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(float val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(Float val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(int val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(Integer val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(long val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(Long val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    final int modCount=this.modCount;
    try{
      final int size;
      if((size=this.size)!=0){
        if(val!=null){ return super.uncheckedLastIndexOfNonNull(size,val); }
        return super.uncheckedLastIndexOfMatch(size,Objects::isNull);
      }
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
    return -1;
  }
  @Override public int lastIndexOf(short val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int lastIndexOf(Short val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    final int size;
    if((size=this.size)!=0){ return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public OmniListIterator.OfRef<E> listIterator(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return new CheckedSubList.BidirectionalItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(int index){
    CheckedCollection.checkModCount(modCount,root.modCount);
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkWriteHi(index,size=this.size);
    return new CheckedSubList.BidirectionalItr<>(this,super.getItrNode(index,size-index),index);
  }
  @Override public void put(int index,E val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    staticGetNode(this,index,size-index).val=val;
  }
  @Override public E remove(int index){
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    Node<E> node;
    if(--size!=0){
      if(index==0){
        node=uncheckedExtractHead(size);
      }else{
        int tailDist;
        if((tailDist=size-index)==0){
          node=uncheckedExtractTail(size);
        }else{
          int modCount;
          Checked<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          CheckedSubList<E> parent;
          if((parent=this.parent)!=null){
            parent.bubbleUpDecrementSize(modCount);
          }
          --root.size;
          node=staticExtractNode(this,index,tailDist);
        }
      }
    }else{
      node=uncheckedExtractLastNode();
    }
    this.size=size;
    return node.val;
  }
  @Override public boolean remove(Object val){
    Node<E> head;
    if((head=this.head)!=null){
      if(val!=null){ return uncheckedRemoveFirstNonNull(head,val); }
      return uncheckedRemoveFirstMatch(head,Objects::isNull);
    }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeIf(Predicate<? super E> filter){
    final int modCount=this.modCount;
    Node<E> head;
    if((head=this.head)!=null){
      try{
        if(filter.test(head.val)){
          findNewHead(modCount,head,filter);
          return true;
        }
        return removeIfHelper(modCount,head,filter);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
    }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(boolean val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(Boolean val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(byte val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(Byte val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(char val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(Character val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(double val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(Double val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(float val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(Float val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(int val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(Integer val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(long val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(Long val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(short val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(Short val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public void replaceAll(UnaryOperator<E> operator){
    int modCount=this.modCount;
    Checked<E> root;
    try{
      Node<E> head;
      if((head=this.head)==null){ return; }
      uncheckedReplaceAll(head,tail,operator);
    }finally{
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    }
    root.modCount=++modCount;
    bubbleUpSetModCount(modCount);
  }
  @Override public void reverseSort(){
    int modCount=this.modCount;
    Checked<E> root;
    try{
      int size;
      if((size=this.size)<2){ return; }
      uncheckedSort(head,size,tail,Comparator.reverseOrder());
    }finally{
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    }
    root.modCount=++modCount;
    bubbleUpSetModCount(modCount);
  }
  @Override public E set(int index,E val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    Node<E> node;
    final var oldVal=(node=staticGetNode(this,index,size-index)).val;
    node.val=val;
    return oldVal;
  }
  @Override public int size(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return size;
  }
  @Override public void sort(){
    int modCount=this.modCount;
    Checked<E> root;
    try{
      int size;
      if((size=this.size)<2){ return; }
      uncheckedSort(head,size,tail,Comparator.naturalOrder());
    }finally{
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    }
    root.modCount=++modCount;
    bubbleUpSetModCount(modCount);
  }
  @Override public void sort(Comparator<? super E> sorter){
    int modCount=this.modCount;
    Checked<E> root;
    try{
      int size;
      if((size=this.size)<2){ return; }
      uncheckedSort(head,size,tail,sorter);
    }finally{
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    }
    root.modCount=++modCount;
    bubbleUpSetModCount(modCount);
  }
  @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
    Checked<E> root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    int size;
    CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
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
  @Override public Object[] toArray(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.toArray();
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    return super.toArray(size->{
      final int modCount=this.modCount;
      try{
        return arrConstructor.apply(size);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    });
  }
  @Override public <T> T[] toArray(T[] dst){
    final int modCount=this.modCount;
    try{
      return super.toArray(dst);
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public String toString(){
    final int modCount=this.modCount;
    try{
      return super.toString();
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  void appendHelper(E val,int modCount){
    this.modCount=modCount;
    Node<E> newNode;
    tail=newNode=new Node<>(tail,val);
    CheckedSubList<E> parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpRootPushTail(modCount,newNode);
    }
  }
  void ascItrRemove(CheckedAscendingSubItr<E> itr){
    Node<E> lastRet;
    if((lastRet=itr.lastRet)!=null){
      int modCount;
      Checked<E> root;
      CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      uncheckedItrRemove(lastRet,modCount,root);
      itr.modCount=modCount;
      --root.size;
      return;
    }
    throw new IllegalStateException();
  }
  void bidirectItrRemove(CheckedSubList.BidirectionalItr<E> itr){
    Node<E> lastRet;
    if((lastRet=itr.lastRet)!=null){
      int modCount;
      Checked<E> root;
      CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      uncheckedItrRemove(lastRet,modCount,root);
      if(lastRet!=itr.cursor){
        --itr.nextIndex;
      }
      itr.modCount=modCount;
      --root.size;
      return;
    }
    throw new IllegalStateException();
  }
  void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter){
    final Node<E> tail;
    if(curr==(tail=this.tail)){
      Checked<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      bubbleUpClear(modCount);
      staticClear(root);
    }else if(filter.test(tail.val)){
      collapseHeadAndTail(modCount,curr,tail,filter);
    }else{
      rootCollapseHead(modCount,curr.next,tail,filter);
    }
  }
  int getParentOffset(){
    return 0;
  }
  void initHelper(Checked<E> root,E val,int modCount){
    final var newNode=new Node<>(val);
    for(var curr=this;;curr.size=1){
      curr.privateInit(newNode,modCount);
      if((curr=curr.parent)==null){
        staticInit(root,newNode);
        return;
      }
    }
  }
  void prependHelper(E val,int modCount){
    this.modCount=modCount;
    Node<E> newNode;
    head=newNode=new Node<>(val,head);
    CheckedSubList<E> parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpRootPushHead(modCount,newNode);
    }
  }
  void removeFirstHelper(int modCount,Node<E> curr){
    Checked<E> root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    if(curr==tail){
      bubbleUpClear(modCount);
      staticClear(root);
    }else{
      bubbleUpRootEraseHead(modCount,curr=curr.next);
      staticSetHead(root,curr);
      --root.size;
    }
  }
  boolean removeIfHelper(int modCount,Node<E> curr,Predicate<? super E> filter){
    final Node<E> tail;
    if(curr!=(tail=this.tail)){
      if(filter.test(tail.val)){
        rootCollapseTail(modCount,curr,tail.prev,filter);
        return true;
      }
      return collapseBody(modCount,curr,tail,filter);
    }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  Node<E> uncheckedExtractHead(int newSize){
    Checked<E> root;
    int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    Node<E> oldHead,newHead;
    bubbleUpRootEraseHead(modCount,newHead=(oldHead=head).next);
    staticSetHead(root,newHead);
    root.size=newSize;
    return oldHead;
  }
  Node<E> uncheckedExtractLastNode(){
    int modCount;
    Checked<E> root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    final var lastNode=head;
    bubbleUpClear(modCount);
    staticClear(root);
    return lastNode;
  }
  Node<E> uncheckedExtractTail(int newSize){
    Checked<E> root;
    int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    Node<E> oldTail,newTail;
    bubbleUpRootEraseTail(modCount,newTail=(oldTail=tail).prev);
    staticSetTail(root,newTail);
    root.size=newSize;
    return oldTail;
  }
  @Override boolean uncheckedRemoveFirstMatch(Node<E> head,Predicate<Object> pred){
    final int modCount=this.modCount;
    try{
      if(pred.test(head.val)){
        removeFirstHelper(modCount,head);
        return true;
      }
      return uncheckedRemoveFirstMatchHelper(modCount,head,pred);
    }catch(final RuntimeException e){
      throw CheckedCollection.checkModCount(modCount,root.modCount,e);
    }
  }
  boolean uncheckedRemoveFirstMatchHelper(int modCount,Node<E> curr,Predicate<Object> pred){
    final var root=this.root;
    final var tail=this.tail;
    Node<E> prev;
    do{
      if(curr==tail){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
    }while(!pred.test((curr=(prev=curr).next).val));
    CheckedCollection.checkModCount(modCount,root.modCount);
    root.modCount=++modCount;
    --root.size;
    if(curr==tail){
      bubbleUpRootEraseTail(modCount,prev);
      staticSetTail(root,prev);
    }else{
      bubbleUpDecrementSize(modCount);
      joinNodes(prev,curr.next);
    }
    return true;
  }
  @Override boolean uncheckedRemoveFirstNonNull(Node<E> head,Object nonNull){
    final int modCount=this.modCount;
    try{
      if(nonNull.equals(head.val)){
        removeFirstHelper(modCount,head);
        return true;
      }
      return uncheckedRemoveFirstMatchHelper(modCount,head,nonNull::equals);
    }catch(final RuntimeException e){
      throw CheckedCollection.checkModCount(modCount,root.modCount,e);
    }
  }
  private void bubbleUpClear(int modCount){
    var curr=this;
    do{
      curr.modCount=modCount;
      staticClear(curr);
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpCollapseHeadAndTail(int newModCount,Node<E> newHead,Node<E> newTail,int newSize){
    var curr=this;
    do{
      curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpDecrementSize(int newModCount){
    var curr=this;
    do{
      curr.modCount=newModCount;
      --curr.size;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpDecrementSize(int newModCount,int numRemoved){
    var curr=this;
    do{
      curr.modCount=newModCount;
      curr.size-=numRemoved;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpIncrementSize(int newModCount){
    var curr=this;
    do{
      curr.modCount=newModCount;
      ++curr.size;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpPrefixCollapseTail(int newModCount,int numRemoved,Node<E> oldTail,Node<E> newTail){
    var curr=this;
    while(curr.tail==oldTail){
      curr.modCount=newModCount;
      curr.size-=numRemoved;
      curr.tail=newTail;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpDecrementSize(newModCount,numRemoved);
  }
  private void bubbleUpPrefixPushTail(int newModCount,Node<E> newTail,Node<E> oldTail){
    var curr=this;
    while(curr.tail==oldTail){
      curr.modCount=newModCount;
      ++curr.size;
      curr.tail=newTail;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpIncrementSize(newModCount);
  }
  private void bubbleUpRootCollapseHead(int newModCount,int numRemoved,Node<E> newHead){
    var curr=this;
    do{
      curr.modCount=newModCount;
      curr.size-=numRemoved;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootCollapseTail(int newModCount,int numRemoved,Node<E> newTail){
    var curr=this;
    do{
      curr.modCount=newModCount;
      curr.size-=numRemoved;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootEraseHead(int newModCount,Node<E> newHead){
    var curr=this;
    do{
      curr.modCount=newModCount;
      --curr.size;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootEraseTail(int newModCount,Node<E> newTail){
    var curr=this;
    do{
      curr.modCount=newModCount;
      --curr.size;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootPushHead(int newModCount,Node<E> newHead){
    var curr=this;
    do{
      curr.modCount=newModCount;
      ++curr.size;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootPushTail(int newModCount,Node<E> newTail){
    var curr=this;
    do{
      curr.modCount=newModCount;
      ++curr.size;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpSetModCount(int newModCount){
    var curr=this;
    do{
      curr.modCount=newModCount;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpSuffixCollapseHead(int newModCount,int numRemoved,Node<E> oldHead,Node<E> newHead){
    var curr=this;
    while(curr.head==oldHead){
      curr.modCount=newModCount;
      curr.size-=numRemoved;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpDecrementSize(newModCount,numRemoved);
  }
  private void bubbleUpSuffixEraseHead(int newModCount,Node<E> oldHead,Node<E> newHead){
    var curr=this;
    do{
      curr.modCount=newModCount;
      --curr.size;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }while(curr.head==oldHead);
    curr.bubbleUpDecrementSize(newModCount);
    joinNodes(oldHead.prev,newHead);
  }
  private void bubbleUpSuffixPushHead(int newModCount,Node<E> newHead,Node<E> oldHead){
    var curr=this;
    while(curr.head==oldHead){
      curr.modCount=newModCount;
      ++curr.size;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpIncrementSize(newModCount);
  }
  private boolean collapseBody(int modCount,Node<E> prev,Node<E> next,Predicate<? super E> filter){
    final int oldSize=size;
    int numConsumed=2;
    final var root=this.root;
    for(Node<E> before;numConsumed!=oldSize;prev=before){
      ++numConsumed;
      if(filter.test((before=prev.next).val)){
        int numRemoved=1;
        for(Node<E> after;;next=after){
          if(numConsumed==oldSize){
            CheckedCollection.checkModCount(modCount,root.modCount);
            break;
          }
          ++numConsumed;
          if(filter.test((after=prev.next).val)){
            ++numRemoved;
            long[] survivorSet;
            int numLeft,numSurvivors;
            if((numLeft=oldSize-numConsumed)!=0&&(numSurvivors=CheckedRefDblLnkList.markSurvivors(before=before.next,
                numLeft,survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0){
              CheckedCollection.checkModCount(modCount,root.modCount);
              numRemoved+=numLeft-numSurvivors;
              prev=CheckedRefDblLnkList.retainSurvivors(prev,before,numSurvivors,survivorSet);
            }else{
              CheckedCollection.checkModCount(modCount,root.modCount);
              numRemoved+=oldSize;
            }
            break;
          }
        }
        joinNodes(prev,next);
        root.modCount=++modCount;
        bubbleUpDecrementSize(modCount,numRemoved);
        root.size-=numRemoved;
        return true;
      }
    }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  private void collapseHeadAndTail(int modCount,Node<E> headCandidate,Node<E> tailCandidate,
      final Predicate<? super E> filter){
    int size=this.size;
    int numConsumed=2;
    final var root=this.root;
    for(;;){
      if(numConsumed==size){
        CheckedCollection.checkModCount(modCount,root.modCount);
        bubbleUpClear(++modCount);
        staticClear(root);
        break;
      }
      ++numConsumed;
      if(!filter.test((headCandidate=headCandidate.next).val)){
        for(;;){
          if(numConsumed==size){
            CheckedCollection.checkModCount(modCount,root.modCount);
            size=1;
            break;
          }
          ++numConsumed;
          if(!filter.test((tailCandidate=tailCandidate.prev).val)){
            size-=numConsumed-2-CheckedRefDblLnkList.collapseBodyHelper(root.new ModCountChecker(modCount),
                headCandidate,size-numConsumed,tailCandidate,filter);
            break;
          }
        }
        bubbleUpCollapseHeadAndTail(++modCount,headCandidate,tailCandidate,size);
        staticSetHead(root,headCandidate);
        staticSetTail(root,tailCandidate);
        root.size=size;
        break;
      }
    }
    root.modCount=modCount;
  }
  private OmniList.OfRef<E> getDefaultSubList(Checked<E> root,int headDist,int subListSize,int tailDist){
    final Node<E> subListHead=head;
    Node<E> subListTail=tail;
    if(tailDist==0){
      if(headDist==0){ return new CheckedSubList<>(root,this,subListHead,subListSize,subListTail); }
      return new CheckedSubList.Suffix<>(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
          :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail);
    }
    subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
        :uncheckedIterateForward(subListHead,subListSize);
    if(headDist==0){ return new CheckedSubList.Prefix<>(root,this,subListHead,subListSize,subListTail); }
    return new CheckedSubList.Body<>(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
        :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail,headDist);
  }
  private OmniList.OfRef<E> getEmptySubList(Checked<E> root,int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new CheckedSubList<>(root,this); }
      return new CheckedSubList.Suffix<>(root,this);
    }else if(headDist==0){ return new CheckedSubList.Prefix<>(root,this); }
    return new CheckedSubList.Body<>(root,this,headDist);
  }
  private OmniList.OfRef<E> getSubList1(Checked<E> root,int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new CheckedSubList<>(root,this,head); }
      return new CheckedSubList.Suffix<>(root,this,tail);
    }else if(headDist==0){ return new CheckedSubList.Prefix<>(root,this,head); }
    return new CheckedSubList.Body<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
  }
  private void insertHelper(Node<E> before,E val,Node<E> after,int modCount){
    new Node<>(before,val,after);
    this.modCount=modCount;
    CheckedSubList<E> parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpIncrementSize(modCount);
    }
  }
  private void privateCollapseHeadAndTail(int modCount,int size,Node<E> head,Node<E> tail){
    this.modCount=modCount;
    this.size=size;
    this.head=head;
    this.tail=tail;
  }
  private void privateInit(Node<E> node,int modCount){
    this.modCount=modCount;
    staticInit(this,node);
  }
  private void rootCollapseHead(int modCount,Node<E> headCandidate,final Node<E> oldTail,Predicate<? super E> filter){
    final int oldSize=size;
    int numConsumed=2;
    final var root=this.root;
    for(;;headCandidate=headCandidate.next){
      if(numConsumed==oldSize){
        CheckedCollection.checkModCount(modCount,root.modCount);
        --numConsumed;
        break;
      }
      ++numConsumed;
      if(!filter.test(headCandidate.val)){
        numConsumed-=2-CheckedRefDblLnkList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
            oldSize-numConsumed,oldTail,filter);
        break;
      }
    }
    root.modCount=++modCount;
    bubbleUpRootCollapseHead(modCount,numConsumed,headCandidate);
    staticSetHead(root,headCandidate);
    root.size-=numConsumed;
  }
  private void rootCollapseTail(int modCount,final Node<E> oldHead,Node<E> tailCandidate,Predicate<? super E> filter){
    final int oldSize=size;
    int numConsumed=2;
    final var root=this.root;
    for(;;tailCandidate=tailCandidate.prev){
      if(numConsumed==oldSize){
        CheckedCollection.checkModCount(modCount,root.modCount);
        --numConsumed;
        break;
      }
      ++numConsumed;
      if(!filter.test(tailCandidate.val)){
        numConsumed-=2-CheckedRefDblLnkList.collapseBodyHelper(root.new ModCountChecker(modCount),oldHead,
            oldSize-numConsumed,tailCandidate,filter);
        break;
      }
    }
    root.modCount=++modCount;
    bubbleUpRootCollapseTail(modCount,numConsumed,tailCandidate);
    staticSetTail(root,tailCandidate);
    root.size-=numConsumed;
  }
  private void suffixCollapseHead(int modCount,Node<E> oldHead,Node<E> oldTail,Predicate<? super E> filter){
    final int oldSize=size;
    int numConsumed=2;
    final var root=this.root;
    var headCandidate=oldHead.next;
    for(;;headCandidate=headCandidate.next){
      if(numConsumed==oldSize){
        CheckedCollection.checkModCount(modCount,root.modCount);
        --numConsumed;
        break;
      }
      ++numConsumed;
      if(!filter.test(headCandidate.val)){
        numConsumed-=2-CheckedRefDblLnkList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
            oldSize-numConsumed,oldTail,filter);
        break;
      }
    }
    root.modCount=++modCount;
    bubbleUpSuffixCollapseHead(modCount,numConsumed,oldHead,headCandidate);
    joinNodes(oldHead.prev,headCandidate);
    root.size-=numConsumed;
  }
  private void uncheckedItrRemove(Node<E> lastRet,int modCount,Checked<E> root){
    if(lastRet==tail){
      if(lastRet==head){
        bubbleUpClear(modCount);
        staticInit(root,null);
      }else{
        bubbleUpRootEraseTail(modCount,lastRet=lastRet.prev);
        staticSetTail(root,lastRet);
      }
    }else{
      if(lastRet==head){
        bubbleUpRootEraseHead(modCount,lastRet=lastRet.next);
        staticSetHead(root,lastRet);
      }else{
        bubbleUpDecrementSize(modCount);
        joinNodes(lastRet.prev,lastRet.next);
      }
    }
  }
  private static class BidirectionalItr<E>extends CheckedAscendingSubItr<E> implements OmniListIterator.OfRef<E>{
    private transient int nextIndex;
    private BidirectionalItr(CheckedSubList<E> parent){
      super(parent);
    }
    private BidirectionalItr(CheckedSubList<E> parent,Node<E> cursor,int nextIndex){
      super(parent,cursor);
      this.nextIndex=nextIndex;
    }
    @Override public void add(E val){
      int modCount;
      Checked<E> root;
      CheckedSubList<E> parent;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
      root.modCount=++modCount;
      final int nextIndex=this.nextIndex++;
      int size;
      if((size=parent.size++)!=0){
        if(nextIndex==0){
          parent.prependHelper(val,modCount);
        }else if(nextIndex==size){
          parent.appendHelper(val,modCount);
        }else{
          Node<E> cursor;
          parent.insertHelper((cursor=this.cursor).prev,val,cursor,modCount);
        }
      }else{
        parent.initHelper(root,val,modCount);
      }
      ++root.size;
      this.modCount=modCount;
      lastRet=null;
    }
    @Override public void forEachRemaining(Consumer<? super E> action){
      Node<E> cursor;
      if((cursor=this.cursor)!=null){
        final int modCount=this.modCount;
        final var parent=this.parent;
        try{
          uncheckedForEachForward(cursor,cursor=parent.tail,action);
        }finally{
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
        }
        this.nextIndex=parent.size;
        this.cursor=null;
        lastRet=cursor;
      }
    }
    @Override public boolean hasPrevious(){
      return this.nextIndex!=0;
    }
    @Override public E next(){
      CheckedSubList<E> parent;
      CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
      Node<E> cursor;
      if((cursor=this.cursor)!=null){
        if(cursor==parent.tail){
          this.cursor=null;
        }else{
          this.cursor=cursor.next;
        }
        ++nextIndex;
        lastRet=cursor;
        return cursor.val;
      }
      throw new NoSuchElementException();
    }
    @Override public int nextIndex(){
      return this.nextIndex;
    }
    @Override public E previous(){
      CheckedCollection.checkModCount(modCount,parent.root.modCount);
      int nextIndex;
      if((nextIndex=this.nextIndex)!=0){
        this.nextIndex=nextIndex-1;
        Node<E> cursor;
        this.cursor=cursor=this.cursor.prev;
        lastRet=cursor;
        return cursor.val;
      }
      throw new NoSuchElementException();
    }
    @Override public int previousIndex(){
      return this.nextIndex-1;
    }
    @Override public void remove(){
      parent.bidirectItrRemove(this);
      lastRet=null;
    }
    @Override public void set(E val){
      lastRet.val=val;
    }
  }
}