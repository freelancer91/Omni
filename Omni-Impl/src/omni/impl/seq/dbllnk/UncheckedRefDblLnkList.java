package omni.impl.seq.dbllnk;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.util.OmniPred;
public class UncheckedRefDblLnkList<E>extends AbstractRefDblLnkSeq.Unchecked<E> implements OmniDeque.OfRef<E>{
    private static <E> int collapseBodyHelper(Node<E> prev,Node<E> next,Predicate<? super E> filter){
        int numRemoved=0;
        for(Node<E> curr;(curr=prev.next)!=next;prev=curr){
            if(filter.test(curr.val)){
                do{
                    ++numRemoved;
                    if((curr=curr.next)==next){
                        joinNodes(prev,next);
                        return numRemoved;
                    }
                }while(filter.test(curr.val));
                joinNodes(prev,curr);
            }
        }
        return numRemoved;
    }
    UncheckedRefDblLnkList(){
        super();
    }
    UncheckedRefDblLnkList(Node<E> onlyNode){
        super(onlyNode);
    }
    UncheckedRefDblLnkList(Node<E> head,int size,Node<E> tail){
        super(head,size,tail);
    }
    @Override
    public boolean add(E val){
        super.addLast(val);
        return true;
    }
    @Override
    public void add(int index,E val){
        int size;
        if((size=this.size)!=0){
            if(index==0){
                head=new Node<>(val,head);
            }else{
                int tailDist;
                if((tailDist=size-index)==0){
                    tail=new Node<>(tail,val);
                }else{
                    staticInsertNode(this,index,val,tailDist);
                }
            }
        }else{
            staticInit(this,new Node<>(val));
        }
        this.size=size+1;
    }
    @Override
    public OmniIterator.OfRef<E> descendingIterator(){
        return new DescendingItr<>(this);
    }
    @Override
    public E element(){
        return head.val;
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public E getFirst(){
        return head.val;
    }
    @Override
    public E getLast(){
        return tail.val;
    }
    @Override
    public OmniIterator.OfRef<E> iterator(){
        return new AscendingItr<>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(){
        return new BidirectionalItr<>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index){
        return new BidirectionalItr<>(this,super.getItrNode(index,size-index),index);
    }
    @Override
    public boolean offer(E val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean offerFirst(E val){
        super.addFirst(val);
        return true;
    }
    @Override
    public boolean offerLast(E val){
        super.addLast(val);
        return true;
    }
    @Override
    public E poll(){
        Node<E> head;
        if((head=this.head)!=null){
            staticEraseHead(this,head);
            --size;
            return head.val;
        }
        return null;
    }
    @Override
    public E pollFirst(){
        Node<E> head;
        if((head=this.head)!=null){
            staticEraseHead(this,head);
            --size;
            return head.val;
        }
        return null;
    }
    @Override
    public E pollLast(){
        Node<E> tail;
        if((tail=this.tail)!=null){
            staticEraseTail(this,tail);
            --size;
            return tail.val;
        }
        return null;
    }
    @Override
    public E pop(){
        Node<E> head;
        staticEraseHead(this,head=this.head);
        --size;
        return head.val;
    }
    @Override
    public void push(E val){
        super.addFirst(val);
    }
    @Override
    public E remove(){
        Node<E> head;
        staticEraseHead(this,head=this.head);
        --size;
        return head.val;
    }
    @Override
    public E remove(int index){
        Node<E> node;
        int size;
        if((size=--this.size)!=0){
            if(index==0){
                staticEraseHead(this,node=head);
            }else{
                int tailDist;
                if((tailDist=size-index)==0){
                    staticEraseTail(this,node=tail);
                }else{
                    node=staticExtractNode(this,index,tailDist);
                }
            }
        }else{
            node=head;
            staticInit(this,null);
        }
        return node.val;
    }
    @Override
    public E removeFirst(){
        Node<E> head;
        staticEraseHead(this,head=this.head);
        --size;
        return head.val;
    }
    @Override
    public E removeLast(){
        Node<E> tail;
        staticEraseTail(this,tail=this.tail);
        --size;
        return tail.val;
    }
    @Override
    public boolean removeLastOccurrence(boolean val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Boolean val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(byte val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Byte val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(char val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Character val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(double val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Double val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(float val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Float val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(int val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Integer val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(long val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Long val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Object val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(short val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Short val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
        int subListSize;
        final int tailDist=size-toIndex;
        switch(subListSize=toIndex-fromIndex){
        default:
            return getDefaultSubList(fromIndex,subListSize,tailDist);
        case 1:
            return getSubList1(fromIndex,tailDist);
        case 0:
            return getEmptySubList(fromIndex,tailDist);
        }
    }
    @Override
    boolean collapseBody(Node<E> head,Node<E> tail,Predicate<? super E> filter){
        Node<E> prev;
        while((head=(prev=head).next)!=tail){
            if(filter.test(head.val)){
                int numRemoved;
                for(numRemoved=1;(head=head.next)!=tail;++numRemoved){
                    if(!filter.test(head.val)){
                        numRemoved+=collapseBodyHelper(head,tail,filter);
                        break;
                    }
                }
                joinNodes(prev,head);
                size-=numRemoved;
                return true;
            }
        }
        return false;
    }
    @Override
    void collapseTail(final Node<E> head,Node<E> tail,Predicate<? super E> filter){
        int numRemoved;
        for(numRemoved=1;(tail=tail.prev)!=head;++numRemoved){
            if(!filter.test(tail.val)){
                numRemoved+=collapseBodyHelper(head,tail,filter);
                break;
            }
        }
        staticSetTail(this,tail);
        size-=numRemoved;
    }
    @Override
    void findNewHead(Node<E> head,Predicate<? super E> filter){
        final Node<E> tail;
        if((tail=this.tail)!=head){
            if(filter.test(tail.val)){
                collapseHeadAndTail(head,tail,filter);
            }else{
                collapseHead(head.next,tail,filter);
            }
        }else{
            super.clear();
        }
    }
    @Override
    boolean uncheckedRemoveFirstMatch(Node<E> curr,Predicate<Object> pred){
        final var tail=this.tail;
        if(pred.test(curr.val)){
            if(curr==tail){
                staticInit(this,null);
            }else{
                staticEraseHead(this,curr);
            }
        }else{
            Node<E> prev;
            do{
                if(curr==tail){
                    return false;
                }
            }while(!pred.test((curr=(prev=curr).next).val));
            if(curr==tail){
                staticSetTail(this,prev);
            }else{
                joinNodes(prev,curr.next);
            }
        }
        --size;
        return true;
    }
    @Override
    boolean uncheckedRemoveFirstNonNull(Node<E> curr,Object nonNull){
        final var tail=this.tail;
        if(nonNull.equals(curr.val)){
            if(curr==tail){
                staticInit(this,null);
            }else{
                staticEraseHead(this,curr);
            }
        }else{
            Node<E> prev;
            do{
                if(curr==tail){
                    return false;
                }
            }while(!nonNull.equals((curr=(prev=curr).next).val));
            if(curr==tail){
                staticSetTail(this,prev);
            }else{
                joinNodes(prev,curr.next);
            }
        }
        --size;
        return true;
    }
    private void collapseHead(Node<E> headCandidate,Node<E> tail,Predicate<? super E> filter){
        int numRemoved;
        for(numRemoved=1;headCandidate!=tail;++numRemoved,headCandidate=headCandidate.next){
            if(!filter.test(headCandidate.val)){
                numRemoved+=collapseBodyHelper(headCandidate,tail,filter);
                break;
            }
        }
        staticSetHead(this,headCandidate);
        size-=numRemoved;
    }
    private void collapseHeadAndTail(Node<E> head,Node<E> tail,Predicate<? super E> filter){
        for(int numRemoved=2;(head=head.next)!=tail;++numRemoved){
            if(!filter.test(head.val)){
                while((tail=tail.prev)!=head){
                    if(!filter.test(tail.val)){
                        numRemoved+=collapseBodyHelper(head,tail,filter);
                        break;
                    }
                    ++numRemoved;
                }
                staticSetHead(this,head);
                staticSetTail(this,tail);
                size-=numRemoved;
                return;
            }
        }
        super.clear();
    }
    private OmniList.OfRef<E> getDefaultSubList(int headDist,int subListSize,int tailDist){
        final Node<E> subListHead=head;
        Node<E> subListTail=tail;
        if(tailDist==0){
            if(headDist==0){
                return new RootSubList<>(this,null,subListHead,subListSize,subListTail);
            }
            return new SuffixSubList<>(this,null,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
                    :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail);
        }
        subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
                :uncheckedIterateForward(subListHead,subListSize);
        if(headDist==0){
            return new PrefixSubList<>(this,null,subListHead,subListSize,subListTail);
        }
        return new BodySubList<>(this,null,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
                :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail,headDist);
    }
    private OmniList.OfRef<E> getEmptySubList(int headDist,int tailDist){
        if(tailDist==0){
            if(headDist==0){
                return new RootSubList<>(this,null);
            }
            return new SuffixSubList<>(this,null);
        }else if(headDist==0){
            return new PrefixSubList<>(this,null);
        }
        return new BodySubList<>(this,null,headDist);
    }
    private OmniList.OfRef<E> getSubList1(int headDist,int tailDist){
        if(tailDist==0){
            if(headDist==0){
                return new RootSubList<>(this,null,head);
            }
            return new SuffixSubList<>(this,null,tail);
        }else if(headDist==0){
            return new PrefixSubList<>(this,null,head);
        }
        return new BodySubList<>(this,null,staticGetNode(this,headDist,tailDist),headDist);
    }
    private boolean uncheckedRemoveLastMatch(Node<E> curr,Predicate<Object> pred){
        final var head=this.head;
        if(pred.test(curr.val)){
            if(curr==head){
                staticInit(this,null);
            }else{
                staticEraseTail(this,curr);
            }
        }else{
            Node<E> next;
            do{
                if(curr==head){
                    return false;
                }
            }while(!pred.test((curr=(next=curr).prev).val));
            if(curr==head){
                staticSetHead(this,next);
            }else{
                joinNodes(curr.prev,next);
            }
        }
        --size;
        return true;
    }
    private static class AscendingItr<E> implements OmniIterator.OfRef<E>{
        transient final Unchecked<E> root;
        transient Node<E> cursor;
        private AscendingItr(Unchecked<E> root){
            this.root=root;
            this.cursor=root.head;
        }
        private AscendingItr(Unchecked<E> root,Node<E> cursor){
            this.root=root;
            this.cursor=cursor;
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action){
            Node<E> cursor;
            if((cursor=this.cursor)!=null){
                uncheckedForEachForward(cursor,root.tail,action);
                this.cursor=null;
            }
        }
        @Override
        public boolean hasNext(){
            return this.cursor!=null;
        }
        @Override
        public E next(){
            Node<E> lastRet;
            this.cursor=(lastRet=this.cursor).next;
            return lastRet.val;
        }
        @Override
        public void remove(){
            Unchecked<E> root;
            if(--(root=this.root).size!=0){
                Node<E> cursor;
                if((cursor=this.cursor)!=null){
                    Node<E> lastRet;
                    if((lastRet=cursor.prev)==root.head){
                        staticSetHead(root,cursor);
                    }else{
                        joinNodes(lastRet.prev,cursor);
                    }
                }else{
                    staticEraseTail(root,root.tail);
                }
            }else{
                staticInit(root,null);
            }
        }
    }
    private static class BidirectionalItr<E>extends AscendingItr<E> implements OmniListIterator.OfRef<E>{
        private transient Node<E> lastRet;
        private transient int nextIndex;
        private BidirectionalItr(Unchecked<E> root){
            super(root);
        }
        private BidirectionalItr(Unchecked<E> root,Node<E> cursor,int nextIndex){
            super(root,cursor);
            this.nextIndex=nextIndex;
        }
        @Override
        public void add(E val){
            Unchecked<E> root;
            if(++(root=this.root).size!=1){
                Node<E> cursor;
                if((cursor=this.cursor)==null){
                    root.tail=new Node<>(root.tail,val);
                }else if(cursor==root.head){
                    root.head=new Node<>(val,cursor);
                }else{
                    new Node<>(cursor.prev,val,cursor);
                }
            }else{
                staticInit(root,new Node<>(val));
            }
            ++nextIndex;
            this.lastRet=null;
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action){
            Node<E> cursor;
            if((cursor=this.cursor)!=null){
                Node<E> bound;
                Unchecked<E> root;
                uncheckedForEachForward(cursor,bound=(root=this.root).tail,action);
                this.cursor=null;
                this.lastRet=bound;
                this.nextIndex=root.size;
            }
        }
        @Override
        public boolean hasPrevious(){
            return this.nextIndex!=0;
        }
        @Override
        public E next(){
            Node<E> lastRet;
            this.lastRet=lastRet=cursor;
            cursor=lastRet.next;
            ++nextIndex;
            return lastRet.val;
        }
        @Override
        public int nextIndex(){
            return this.nextIndex;
        }
        @Override
        public E previous(){
            Node<E> lastRet;
            this.lastRet=lastRet=cursor.prev;
            cursor=lastRet;
            --nextIndex;
            return lastRet.val;
        }
        @Override
        public int previousIndex(){
            return this.nextIndex-1;
        }
        @Override
        public void remove(){
            final var lastRet=this.lastRet;
            Unchecked<E> root;
            if(--(root=this.root).size!=0){
                Node<E> cursor;
                if(lastRet!=(cursor=this.cursor)){
                    --nextIndex;
                    if(lastRet==root.head){
                        staticSetHead(root,cursor);
                    }else if(lastRet==root.tail){
                        staticEraseTail(root,lastRet);
                    }else{
                        joinNodes(lastRet.prev,cursor);
                    }
                }else{
                    if(lastRet==root.head){
                        staticEraseHead(root,lastRet);
                    }else if(lastRet==root.tail){
                        staticEraseTail(root,lastRet);
                    }else{
                        joinNodes(lastRet.prev,lastRet.next);
                    }
                }
            }else{
                staticInit(root,null);
                if(lastRet!=cursor){
                    --nextIndex;
                }
            }
            this.lastRet=null;
        }
        @Override
        public void set(E val){
            lastRet.val=val;
        }
    }
    private static class BodySubList<E>extends PrefixSubList<E>{
        private transient final int parentOffset;
        private BodySubList(Unchecked<E> root,RootSubList<E> parent,int parentOffset){
            super(root,parent);
            this.parentOffset=parentOffset;
        }
        private BodySubList(Unchecked<E> root,RootSubList<E> parent,Node<E> onlyNode,int parentOffset){
            super(root,parent,onlyNode);
            this.parentOffset=parentOffset;
        }
        private BodySubList(Unchecked<E> root,RootSubList<E> parent,Node<E> head,int size,Node<E> tail,
                int parentOffset){
            super(root,parent,head,size,tail);
            this.parentOffset=parentOffset;
        }
        @Override
        public void clear(){
            int size;
            if((size=this.size)!=0){
                bubbleUpClear(size,head,tail);
                root.size-=size;
            }
        }
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
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
        @Override
        void ascItrRemove(Node<E> cursor){
            --root.size;
            if(cursor!=null){
                Node<E> lastRet;
                if((lastRet=cursor.prev)==head){
                    ((RootSubList<E>)this).bubbleUpSuffixEraseHead(lastRet,cursor);
                }else{
                    ((RootSubList<E>)this).bubbleUpDecrementSize();
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
        @Override
        void bidirectItrRemove(Node<E> lastRet){
            --root.size;
            if(lastRet==tail){
                if(lastRet==head){
                    bubbleUpClear(1,lastRet,lastRet);
                }else{
                    super.bubbleUpPrefixEraseTail(lastRet,lastRet.prev);
                }
            }else{
                if(lastRet==head){
                    ((RootSubList<E>)this).bubbleUpSuffixEraseHead(lastRet,lastRet.next);
                }else{
                    ((RootSubList<E>)this).bubbleUpDecrementSize();
                    joinNodes(lastRet.prev,lastRet.next);
                }
            }
        }
        @Override
        void findNewHead(Node<E> curr,Predicate<? super E> filter){
            final Node<E> tail;
            if(curr==(tail=this.tail)){
                bubbleUpClear(1,curr,tail);
                --root.size;
            }else if(filter.test(tail.val)){
                collapseHeadAndTail(curr,tail,filter);
            }else{
                ((RootSubList<E>)this).suffixCollapseHead(curr,tail,filter);
            }
        }
        @Override
        int getParentOffset(){
            return this.parentOffset;
        }
        @Override
        void initHelper(Unchecked<E> root,E val){
            final Node<E> newNode;
            staticInit(this,newNode=new Node<>(val));
            RootSubList<E> parent,curr;
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
        @Override
        void prependHelper(E val){
            Node<E> newNode,oldHead;
            head=newNode=new Node<>((oldHead=head).prev,val,oldHead);
            RootSubList<E> parent;
            if((parent=this.parent)!=null){
                parent.bubbleUpSuffixPushHead(newNode,oldHead);
            }
        }
        @Override
        void removeFirstHelper(Node<E> curr){
            --root.size;
            if(curr==tail){
                bubbleUpClear(1,head,curr);
            }else{
                ((RootSubList<E>)this).bubbleUpSuffixEraseHead(curr,curr.next);
            }
        }
        @Override
        Node<E> uncheckedExtractHead(int newSize){
            Node<E> oldHead;
            ((RootSubList<E>)this).bubbleUpSuffixEraseHead(oldHead=head,oldHead.next);
            --root.size;
            return oldHead;
        }
        @Override
        Node<E> uncheckedExtractLastNode(){
            final Node<E> lastNode;
            bubbleUpClear(1,lastNode=head,lastNode);
            --root.size;
            return lastNode;
        }
        private void bubbleUpClear(int size,Node<E> head,Node<E> tail){
            final Node<E> prev=head.prev,next=tail.next;
            for(RootSubList<E> curr=this;;){
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
            for(RootSubList<E> curr=this;;){
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
                            numConsumed-=2-collapseBodyHelper(headCandidate,tailCandidate,filter);
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
                subListTail=tailDist<subListSize?iterateReverse(tail,tailDist)
                        :uncheckedIterateForward(subListHead,subListSize);
            }else{
                subListTail=iterateReverse(tail,tailDist);
                subListHead=headDist<subListSize?iterateForward(head,headDist)
                        :uncheckedIterateReverse(subListTail,subListSize);
            }
            return new BodySubList<>(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist){
            return new BodySubList<>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist){
            return new BodySubList<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
        }
    }
    private static class DescendingItr<E>extends AscendingItr<E>{
        private DescendingItr(Unchecked<E> root){
            super(root,root.tail);
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action){
            Node<E> cursor;
            if((cursor=this.cursor)!=null){
                uncheckedForEachReverse(cursor,action);
                this.cursor=null;
            }
        }
        @Override
        public E next(){
            Node<E> lastRet;
            cursor=(lastRet=cursor).prev;
            return lastRet.val;
        }
        @Override
        public void remove(){
            Unchecked<E> root;
            if(--(root=this.root).size!=0){
                Node<E> cursor;
                if((cursor=this.cursor)!=null){
                    Node<E> lastRet;
                    if((lastRet=cursor.next)==root.tail){
                        staticSetTail(root,cursor);
                    }else{
                        joinNodes(cursor,lastRet.next);
                    }
                }else{
                    staticEraseHead(root,root.head);
                }
            }else{
                staticInit(root,null);
            }
        }
    }
    private static class PrefixSubList<E>extends RootSubList<E>{
        private PrefixSubList(Unchecked<E> root,RootSubList<E> parent){
            super(root,parent);
        }
        private PrefixSubList(Unchecked<E> root,RootSubList<E> parent,Node<E> onlyNode){
            super(root,parent,onlyNode);
        }
        private PrefixSubList(Unchecked<E> root,RootSubList<E> parent,Node<E> head,int size,Node<E> tail){
            super(root,parent,head,size,tail);
        }
        @Override
        public void clear(){
            int size;
            if((size=this.size)!=0){
                Node<E> tmp;
                bubbleUpClear(size,tmp=tail,tmp=tmp.next);
                final Unchecked<E> root;
                staticSetHead(root=this.root,tmp);
                root.size-=size;
            }
        }
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
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
        @Override
        void appendHelper(E val){
            Node<E> newNode,oldTail;
            tail=newNode=new Node<>(oldTail=tail,val,oldTail.next);
            RootSubList<E> parent;
            if((parent=this.parent)!=null){
                parent.bubbleUpPrefixPushTail(newNode,oldTail);
            }
        }
        @Override
        void ascItrRemove(Node<E> cursor){
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
        @Override
        void bidirectItrRemove(Node<E> lastRet){
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
        @Override
        void collapseTail(Node<E> oldHead,Node<E> oldTail,Predicate<? super E> filter){
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
                    numConsumed-=2-collapseBodyHelper(oldHead,tailCandidate,filter);
                    break;
                }
            }
            super.bubbleUpPrefixCollapseTail(numConsumed,oldTail,tailCandidate);
            joinNodes(tailCandidate,oldTail.next);
            root.size-=numConsumed;
        }
        @Override
        void findNewHead(Node<E> curr,Predicate<? super E> filter){
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
        @Override
        void initHelper(Unchecked<E> root,E val){
            final Node<E> after,newNode=new Node<>(val);
            for(RootSubList<E> curr=this;;curr.size=1){
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
        @Override
        Node<E> uncheckedExtractLastNode(){
            final Node<E> next,lastNode;
            final Unchecked<E> root;
            staticSetHead(root=this.root,next=(lastNode=tail).next);
            bubbleUpClear(1,lastNode,next);
            --root.size;
            return lastNode;
        }
        @Override
        Node<E> uncheckedExtractTail(int newSize){
            Node<E> oldTail;
            bubbleUpPrefixEraseTail(oldTail=tail,oldTail.prev);
            --root.size;
            return oldTail;
        }
        @Override
        boolean uncheckedRemoveFirstMatchHelper(Node<E> curr,Predicate<Object> pred){
            final var tail=this.tail;
            Node<E> prev;
            do{
                if(curr==tail){
                    return false;
                }
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
            for(RootSubList<E> curr=this;;){
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
            for(RootSubList<E> curr=this;;){
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
            RootSubList<E> curr=this;
            do{
                --curr.size;
                curr.tail=newTail;
                if((curr=curr.parent)==null){
                    return;
                }
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
                            numConsumed-=2-collapseBodyHelper(headCandidate,tailCandidate,filter);
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
            if(headDist==0){
                return new PrefixSubList<>(root,this,subListHead,subListSize,
                        tailDist<=subListSize?iterateReverse(subListTail,tailDist)
                                :uncheckedIterateForward(subListHead,subListSize));
            }
            if(headDist<=tailDist){
                subListHead=uncheckedIterateForward(subListHead,headDist);
                subListTail=tailDist<=subListSize?iterateReverse(subListTail,tailDist)
                        :uncheckedIterateForward(subListHead,subListSize);
            }else{
                subListTail=iterateReverse(subListTail,tailDist);
                subListHead=headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
                        :uncheckedIterateReverse(subListTail,subListSize);
            }
            return new BodySubList<>(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist){
            if(headDist==0){
                return new PrefixSubList<>(root,this);
            }
            return new BodySubList<>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist){
            if(headDist==0){
                return new PrefixSubList<>(root,this,head);
            }
            return new BodySubList<>(root,this,
                    tailDist<headDist?iterateReverse(tail,tailDist):uncheckedIterateForward(head,headDist),headDist);
        }
    }
    private static class RootSubList<E>extends AbstractRefDblLnkSeq.Unchecked<E>{
        transient final Unchecked<E> root;
        transient final RootSubList<E> parent;
        private RootSubList(Unchecked<E> root,RootSubList<E> parent){
            super();
            this.root=root;
            this.parent=parent;
        }
        private RootSubList(Unchecked<E> root,RootSubList<E> parent,Node<E> onlyNode){
            super(onlyNode);
            this.root=root;
            this.parent=parent;
        }
        private RootSubList(Unchecked<E> root,RootSubList<E> parent,Node<E> head,int size,Node<E> tail){
            super(head,size,tail);
            this.root=root;
            this.parent=parent;
        }
        @Override
        public boolean add(E val){
            final var root=this.root;
            if(++size!=1){
                appendHelper(val);
            }else{
                initHelper(root,val);
            }
            ++root.size;
            return true;
        }
        @Override
        public void add(int index,E val){
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
        @Override
        public void clear(){
            if(size!=0){
                bubbleUpClear();
                staticClear(this.root);
            }
        }
        @Override
        public boolean equals(Object val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public OmniIterator.OfRef<E> iterator(){
            return new AscendingItr<>(this);
        }
        @Override
        public OmniListIterator.OfRef<E> listIterator(){
            return new BidirectionalItr<>(this);
        }
        @Override
        public OmniListIterator.OfRef<E> listIterator(int index){
            return new BidirectionalItr<>(this,super.getItrNode(index,size-index),index);
        }
        @Override
        public E remove(int index){
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
                        RootSubList<E> parent;
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
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
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
            RootSubList<E> parent;
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
        @Override
        final boolean collapseBody(Node<E> head,Node<E> tail,Predicate<? super E> filter){
            Node<E> prev;
            while((head=(prev=head).next)!=tail){
                if(filter.test(head.val)){
                    int numRemoved;
                    for(numRemoved=1;(head=head.next)!=tail;++numRemoved){
                        if(!filter.test(head.val)){
                            numRemoved+=collapseBodyHelper(head,tail,filter);
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
        @Override
        void collapseTail(Node<E> head,Node<E> tail,Predicate<? super E> filter){
            int numRemoved;
            for(numRemoved=1;(tail=tail.prev)!=head;++numRemoved){
                if(!filter.test(tail.val)){
                    numRemoved+=collapseBodyHelper(head,tail,filter);
                    break;
                }
            }
            bubbleUpRootCollapseTail(numRemoved,tail);
            Unchecked<E> root;
            staticSetTail(root=this.root,tail);
            root.size-=numRemoved;
        }
        @Override
        void findNewHead(Node<E> head,Predicate<? super E> filter){
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
            RootSubList<E> parent;
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
        @Override
        boolean uncheckedRemoveFirstMatch(Node<E> head,Predicate<Object> pred){
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
                if(curr==tail){
                    return false;
                }
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
        @Override
        boolean uncheckedRemoveFirstNonNull(Node<E> head,Object nonNull){
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
                if((curr=curr.parent)==null){
                    return;
                }
            }
            curr.bubbleUpDecrementSize(numRemoved);
        }
        private void bubbleUpPrefixPushTail(Node<E> newTail,Node<E> oldTail){
            var curr=this;
            while(curr.tail==oldTail){
                ++curr.size;
                curr.tail=newTail;
                if((curr=curr.parent)==null){
                    return;
                }
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
                if((curr=curr.parent)==null){
                    return;
                }
            }
            curr.bubbleUpDecrementSize(numRemoved);
        }
        private void bubbleUpSuffixEraseHead(Node<E> oldHead,Node<E> newHead){
            var curr=this;
            do{
                --curr.size;
                curr.head=newHead;
                if((curr=curr.parent)==null){
                    return;
                }
            }while(curr.head==oldHead);
            curr.bubbleUpDecrementSize();
            joinNodes(oldHead.prev,newHead);
        }
        private void bubbleUpSuffixPushHead(Node<E> newHead,Node<E> oldHead){
            var curr=this;
            while(curr.head==oldHead){
                ++curr.size;
                curr.head=newHead;
                if((curr=curr.parent)==null){
                    return;
                }
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
                            numRemoved+=collapseBodyHelper(head,tail,filter);
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
                if(headDist==0){
                    return new RootSubList<>(root,this,subListHead,subListSize,subListTail);
                }
                return new SuffixSubList<>(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
                        :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail);
            }
            subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
                    :uncheckedIterateForward(subListHead,subListSize);
            if(headDist==0){
                return new PrefixSubList<>(root,this,subListHead,subListSize,subListTail);
            }
            return new BodySubList<>(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
                    :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist,int tailDist){
            if(tailDist==0){
                if(headDist==0){
                    return new RootSubList<>(root,this);
                }
                return new SuffixSubList<>(root,this);
            }else if(headDist==0){
                return new PrefixSubList<>(root,this);
            }
            return new BodySubList<>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist){
            if(tailDist==0){
                if(headDist==0){
                    return new RootSubList<>(root,this,head);
                }
                return new SuffixSubList<>(root,this,tail);
            }else if(headDist==0){
                return new PrefixSubList<>(root,this,head);
            }
            return new BodySubList<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
        }
        private void insertHelper(Node<E> before,E val,Node<E> after){
            new Node<>(before,val,after);
            RootSubList<E> parent;
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
                    numRemoved+=collapseBodyHelper(headCandidate,tail,filter);
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
                    numConsumed-=2-collapseBodyHelper(headCandidate,oldTail,filter);
                    break;
                }
            }
            bubbleUpSuffixCollapseHead(numConsumed,oldHead,headCandidate);
            joinNodes(oldHead.prev,headCandidate);
            root.size-=numConsumed;
        }
        private static class AscendingItr<E> implements OmniIterator.OfRef<E>{
            transient final RootSubList<E> parent;
            transient Node<E> cursor;
            private AscendingItr(RootSubList<E> parent){
                this.parent=parent;
                this.cursor=parent.head;
            }
            private AscendingItr(RootSubList<E> parent,Node<E> cursor){
                this.parent=parent;
                this.cursor=cursor;
            }
            @Override
            public void forEachRemaining(Consumer<? super E> action){
                Node<E> cursor;
                if((cursor=this.cursor)!=null){
                    uncheckedForEachForward(cursor,parent.tail,action);
                    this.cursor=null;
                }
            }
            @Override
            public boolean hasNext(){
                return cursor!=null;
            }
            @Override
            public E next(){
                Node<E> cursor;
                if((cursor=this.cursor)==parent.tail){
                    this.cursor=null;
                }else{
                    this.cursor=cursor.next;
                }
                return cursor.val;
            }
            @Override
            public void remove(){
                parent.ascItrRemove(this.cursor);
            }
        }
        private static class BidirectionalItr<E>extends AscendingItr<E> implements OmniListIterator.OfRef<E>{
            private transient int nextIndex;
            private transient Node<E> lastRet;
            private BidirectionalItr(RootSubList<E> parent){
                super(parent);
            }
            private BidirectionalItr(RootSubList<E> parent,Node<E> cursor,int nextIndex){
                super(parent,cursor);
                this.nextIndex=nextIndex;
            }
            @Override
            public void add(E val){
                final int nextIndex=this.nextIndex++;
                int size;
                final RootSubList<E> parent;
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
            @Override
            public void forEachRemaining(Consumer<? super E> action){
                Node<E> cursor;
                if((cursor=this.cursor)!=null){
                    RootSubList<E> parent;
                    uncheckedForEachForward(cursor,cursor=(parent=this.parent).tail,action);
                    this.nextIndex=parent.size;
                    this.cursor=null;
                    lastRet=cursor;
                }
            }
            @Override
            public boolean hasPrevious(){
                return this.nextIndex!=0;
            }
            @Override
            public E next(){
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
            @Override
            public int nextIndex(){
                return this.nextIndex;
            }
            @Override
            public E previous(){
                Node<E> lastRet;
                this.lastRet=lastRet=cursor.prev;
                cursor=lastRet;
                --nextIndex;
                return lastRet.val;
            }
            @Override
            public int previousIndex(){
                return this.nextIndex-1;
            }
            @Override
            public void remove(){
                Node<E> lastRet;
                parent.bidirectItrRemove(lastRet=this.lastRet);
                if(lastRet!=cursor){
                    --nextIndex;
                }
                this.lastRet=null;
            }
            @Override
            public void set(E val){
                lastRet.val=val;
            }
        }
    }
    private static class SuffixSubList<E>extends RootSubList<E>{
        SuffixSubList(Unchecked<E> root,RootSubList<E> parent){
            super(root,parent);
        }
        SuffixSubList(Unchecked<E> root,RootSubList<E> parent,Node<E> onlyNode){
            super(root,parent,onlyNode);
        }
        SuffixSubList(Unchecked<E> root,RootSubList<E> parent,Node<E> head,int size,Node<E> tail){
            super(root,parent,head,size,tail);
        }
        @Override
        public void clear(){
            int size;
            if((size=this.size)!=0){
                Node<E> tmp;
                bubbleUpClear(size,tmp=head,tmp=tmp.prev);
                final Unchecked<E> root;
                staticSetTail(root=this.root,tmp);
                root.size-=size;
            }
        }
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
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
        @Override
        void ascItrRemove(Node<E> cursor){
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
        @Override
        void bidirectItrRemove(Node<E> lastRet){
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
        @Override
        void findNewHead(Node<E> curr,Predicate<? super E> filter){
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
        @Override
        void initHelper(Unchecked<E> root,E val){
            final Node<E> before,newNode=new Node<>(val);
            for(RootSubList<E> curr=this;;curr.size=1){
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
        @Override
        void prependHelper(E val){
            Node<E> newNode,oldHead;
            head=newNode=new Node<>((oldHead=head).prev,val,oldHead);
            RootSubList<E> parent;
            if((parent=this.parent)!=null){
                parent.bubbleUpSuffixPushHead(newNode,oldHead);
            }
        }
        @Override
        void removeFirstHelper(Node<E> curr){
            Unchecked<E> root;
            --(root=this.root).size;
            if(curr==tail){
                bubbleUpClear(1,curr,curr=curr.prev);
                staticSetTail(root,curr);
            }else{
                super.bubbleUpSuffixEraseHead(curr,curr.next);
            }
        }
        @Override
        Node<E> uncheckedExtractHead(int newSize){
            Node<E> oldHead;
            super.bubbleUpSuffixEraseHead(oldHead=head,oldHead.next);
            --root.size;
            return oldHead;
        }
        @Override
        Node<E> uncheckedExtractLastNode(){
            final Node<E> prev,lastNode;
            Unchecked<E> root;
            staticSetTail(root=this.root,prev=(lastNode=head).prev);
            bubbleUpClear(1,lastNode,prev);
            --root.size;
            return lastNode;
        }
        private void bubbleUpClear(int size,Node<E> head,final Node<E> prev){
            for(RootSubList<E> curr=this;;){
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
            for(RootSubList<E> curr=this;;){
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
                            numConsumed-=2-+collapseBodyHelper(headCandidate,tailCandidate,filter);
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
            if(tailDist==0){
                return new SuffixSubList<>(root,this,headDist<=subListSize?iterateForward(subListHead,headDist)
                        :uncheckedIterateReverse(subListTail,subListSize));
            }
            if(tailDist<=headDist){
                subListTail=uncheckedIterateReverse(subListTail,tailDist);
                subListHead=headDist<=subListSize?iterateForward(subListHead,headDist)
                        :uncheckedIterateReverse(subListTail,subListSize);
            }else{
                subListHead=iterateForward(subListHead,headDist);
                subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
                        :uncheckedIterateForward(subListHead,subListSize);
            }
            return new BodySubList<>(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Unchecked<E> root,int headDist,int tailDist){
            if(tailDist==0){
                return new PrefixSubList<>(root,this);
            }
            return new BodySubList<>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Unchecked<E> root,int headDist,int tailDist){
            if(tailDist==0){
                return new SuffixSubList<>(root,this,tail);
            }
            return new BodySubList<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
        }
    }
}
