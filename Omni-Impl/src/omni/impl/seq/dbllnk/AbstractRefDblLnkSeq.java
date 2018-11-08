package omni.impl.seq.dbllnk;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractRefList;
import omni.util.OmniArray;
import omni.util.OmniPred;
abstract class AbstractRefDblLnkSeq<E>extends AbstractRefList<E> implements OmniList.OfRef<E>{
    static <E> Node<E> iterateForward(Node<E> curr,int dist){
        if(dist!=0){
            return uncheckedIterateForward(curr,dist);
        }
        return curr;
    }
    static <E> Node<E> iterateReverse(Node<E> curr,int dist){
        if(dist!=0){
            return uncheckedIterateReverse(curr,dist);
        }
        return curr;
    }
    static <E> void joinNodes(Node<E> prev,Node<E> next){
        prev.next=next;
        next.prev=prev;
    }
    static void staticClear(AbstractRefDblLnkSeq<?> seq){
        seq.size=0;
        staticInit(seq,null);
    }
    static <E> void staticEraseHead(AbstractRefDblLnkSeq<E> seq,Node<E> oldHead){
        seq.head=oldHead=oldHead.next;
        oldHead.prev=null;
    }
    static <E> void staticEraseTail(AbstractRefDblLnkSeq<E> seq,Node<E> oldTail){
        seq.tail=oldTail=oldTail.prev;
        oldTail.next=null;
    }
    static <E> Node<E> staticExtractNode(AbstractRefDblLnkSeq<E> seq,int headDist,int tailDist){
        Node<E> node;
        if(headDist<=tailDist){
            joinNodes(node=iterateForward(seq.head,headDist-1),(node=node.next).next);
        }else{
            Node<E> after;
            joinNodes((node=(after=iterateReverse(seq.tail,tailDist-1)).prev).prev,after);
        }
        return node;
    }
    static <E> Node<E> staticGetNode(AbstractRefDblLnkSeq<E> seq,int headDist,int tailDist){
        if(headDist<=tailDist){
            return iterateForward(seq.head,headDist);
        }
        return iterateReverse(seq.tail,tailDist-1);
    }
    static <E> void staticInit(AbstractRefDblLnkSeq<E> seq,Node<E> node){
        seq.head=node;
        seq.tail=node;
    }
    static <E> void staticInsertNode(AbstractRefDblLnkSeq<E> seq,int headDist,E val,int tailDist){
        Node<E> before,after;
        if(headDist<=tailDist){
            after=(before=iterateForward(seq.head,headDist-1)).next;
        }else{
            before=(after=iterateReverse(seq.tail,tailDist-1)).prev;
        }
        new Node<>(before,val,after);
    }
    static <E> void staticSetHead(AbstractRefDblLnkSeq<E> seq,Node<E> newHead){
        seq.head=newHead;
        newHead.prev=null;
    }
    static <E> void staticSetTail(AbstractRefDblLnkSeq<E> seq,Node<E> newTail){
        seq.tail=newTail;
        newTail.next=null;
    }
    static <E> void subSeqInsertHelper(AbstractRefDblLnkSeq<E> root,Node<E> newNode,int index){
        int tailDist;
        Node<E> before,after;
        if(index<=(tailDist=root.size-index)){
            after=(before=iterateForward(root.head,index-1)).next;
        }else{
            before=(after=iterateReverse(root.tail,tailDist-1)).prev;
        }
        joinNodes(before,newNode);
        joinNodes(newNode,after);
    }
    private static <SRC extends DST,DST> void uncheckedCopyForward(Node<SRC> src,DST[] dst,int dstOffset,int length){
        for(;;){
            dst[dstOffset]=src.val;
            if(--length==0){
                return;
            }
            ++dstOffset;
            src=src.next;
        }
    }
    static <E> void uncheckedForEachForward(Node<E> begin,Node<E> end,Consumer<? super E> action){
        for(;;){
            action.accept(begin.val);
            if(begin==end){
                return;
            }
            begin=begin.next;
        }
    }
    static <E> void uncheckedForEachReverse(Node<E> curr,Consumer<? super E> action){
        do{
            action.accept(curr.val);
        }while((curr=curr.prev)!=null);
    }
    static <E> int uncheckedForwardHashCode(Node<E> begin,Node<E> end){
        int hash=31+Objects.hashCode(begin.val);
        while(begin!=end){
            hash=hash*31+Objects.hashCode((begin=begin.next).val);
        }
        return hash;
    }
    static <E> void uncheckedForwardToString(Node<E> begin,Node<E> end,StringBuilder builder){
        for(builder.append(begin.val);begin!=end;builder.append(',').append(' ').append((begin=begin.next).val)){}
    }
    static <E> Node<E> uncheckedIterateForward(Node<E> curr,int dist){
        do{
            curr=curr.next;
        }while(--dist!=0);
        return curr;
    }
    static <E> Node<E> uncheckedIterateReverse(Node<E> curr,int dist){
        do{
            curr=curr.prev;
        }while(--dist!=0);
        return curr;
    }
    static <E> void uncheckedReplaceAll(Node<E> begin,Node<E> end,Function<? super E,? extends E> operator){
        for(;;){
            begin.val=operator.apply(begin.val);
            if(begin==end){
                return;
            }
            begin=begin.next;
        }
    }
    static <E> void uncheckedSort(Node<?> begin,int size,Node<?> end,Comparator<? super E> sorter){
        // TODO
    }
    transient Node<E> head;
    transient Node<E> tail;
    AbstractRefDblLnkSeq(){
        super();
    }
    AbstractRefDblLnkSeq(Node<E> onlyNode){
        super(1);
        staticInit(this,onlyNode);
    }
    AbstractRefDblLnkSeq(Node<E> head,int size,Node<E> tail){
        super(size);
        this.head=head;
        this.tail=tail;
    }
    public void addFirst(E val){
        Node<E> head;
        if((head=this.head)!=null){
            this.head=new Node<>(val,head);
        }else{
            staticInit(this,new Node<>(val));
        }
        ++size;
    }
    public void addLast(E val){
        Node<E> tail;
        if((tail=this.tail)!=null){
            this.tail=new Node<>(tail,val);
        }else{
            staticInit(this,new Node<>(val));
        }
        ++size;
    }
    @Override
    public void clear(){
        staticClear(this);
    }
    @Override
    public Object clone(){
        Node<E> newHead,newTail;
        int size;
        if((size=this.size)!=0){
            Node<E> oldHead,oldTail;
            for(newHead=new Node<>(
                    (oldHead=this.head).val),newTail=newHead,oldTail=this.tail;oldHead!=oldTail;newTail=new Node<>(
                            newTail,(oldHead=oldHead.next).val)){}
        }else{
            newHead=null;
            newTail=null;
        }
        return new UncheckedRefDblLnkList<>(newHead,size,newTail);
    }
    @Override
    public boolean contains(boolean val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Boolean val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(byte val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Byte val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(char val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Character val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(double val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Double val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(float val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Float val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(int val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Integer val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(long val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Long val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Object val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(short val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean contains(Short val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public void forEach(Consumer<? super E> action){
        Node<E> head;
        if((head=this.head)!=null){
            uncheckedForEachForward(head,tail,action);
        }
    }
    @Override
    public E get(int index){
        return staticGetNode(this,index,size-index).val;
    }
    @Override
    public int hashCode(){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedForwardHashCode(head,tail);
        }
        return -1;
    }
    @Override
    public int indexOf(boolean val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Boolean val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(byte val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Byte val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(char val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Character val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(double val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Double val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(float val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Float val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(int val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Integer val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(long val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Long val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Object val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(short val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public int indexOf(Short val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedIndexOfMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public E peek(){
        Node<E> head;
        if((head=this.head)!=null){
            return head.val;
        }
        return null;
    }
    public E peekFirst(){
        Node<E> head;
        if((head=this.head)!=null){
            return head.val;
        }
        return null;
    }
    public E peekLast(){
        Node<E> tail;
        if((tail=this.tail)!=null){
            return tail.val;
        }
        return null;
    }
    @Override
    public void put(int index,E val){
        staticGetNode(this,index,size-index).val=val;
    }
    @Override
    public boolean remove(Object val){
        Node<E> head;
        if((head=this.head)!=null){
            if(val!=null){
                return uncheckedRemoveFirstNonNull(head,val);
            }
            return uncheckedRemoveFirstMatch(head,Objects::isNull);
        }
        return false;
    }
    public boolean removeFirstOccurrence(boolean val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(Boolean val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(byte val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(Byte val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(char val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(Character val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(double val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(Double val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(float val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(Float val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(int val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(Integer val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(long val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(Long val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(Object val){
        Node<E> head;
        if((head=this.head)!=null){
            if(val!=null){
                return uncheckedRemoveFirstNonNull(head,val);
            }
            return uncheckedRemoveFirstMatch(head,Objects::isNull);
        }
        return false;
    }
    public boolean removeFirstOccurrence(short val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    public boolean removeFirstOccurrence(Short val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(boolean val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(Boolean val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(byte val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(Byte val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(char val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(Character val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(double val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(Double val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(float val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(Float val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(int val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(Integer val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(long val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(Long val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(short val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public boolean removeVal(Short val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
    }
    @Override
    public void replaceAll(UnaryOperator<E> operator){
        Node<E> head;
        if((head=this.head)!=null){
            uncheckedReplaceAll(head,tail,operator);
        }
    }
    @Override
    public void reverseSort(){
        int size;
        if((size=this.size)>1){
            uncheckedSort(head,size,tail,Comparator.reverseOrder());
        }
    }
    public int search(boolean val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Boolean val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(byte val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Byte val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(char val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Character val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(double val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Double val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(float val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Float val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(int val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Integer val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(long val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Long val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Object val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(short val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    public int search(Short val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    public E set(int index,E val){
        Node<E> node;
        final var oldVal=(node=staticGetNode(this,index,size-index)).val;
        node.val=val;
        return oldVal;
    }
    @Override
    public void sort(){
        int size;
        if((size=this.size)>1){
            uncheckedSort(head,size,tail,Comparator.naturalOrder());
        }
    }
    @Override
    public void sort(Comparator<? super E> sorter){
        int size;
        if((size=this.size)>1){
            uncheckedSort(head,size,tail,sorter);
        }
    }
    @Override
    public Object[] toArray(){
        int size;
        if((size=this.size)!=0){
            Object[] dst;
            uncheckedCopyForward(head,dst=new Object[size],0,size);
            return dst;
        }
        return OmniArray.OfRef.DEFAULT_ARR;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        int size;
        final T[] arr=arrConstructor.apply(size=this.size);
        if(size!=0){
            uncheckedCopyForward(head,arr,0,size);
        }
        return arr;
    }
    @Override
    public <T> T[] toArray(T[] dst){
        int size;
        if((size=this.size)!=0){
            uncheckedCopyForward(head,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
        }else if(dst.length!=0){
            dst[0]=null;
        }
        return dst;
    }
    @Override
    public String toString(){
        Node<E> head;
        if((head=this.head)!=null){
            StringBuilder builder;
            uncheckedForwardToString(head,tail,builder=new StringBuilder("["));
            return builder.append(']').toString();
        }
        return "[]";
    }
    @Override
    protected int uncheckedLastIndexOfMatch(int size,Predicate<Object> pred){
        for(var tail=this.tail;!pred.test(tail.val)&&--size!=0;tail=tail.prev){}
        return size-1;
    }
    @Override
    protected int uncheckedLastIndexOfNonNull(int size,Object nonNull){
        for(var tail=this.tail;!nonNull.equals(tail.val)&&--size!=0;tail=tail.prev){}
        return size-1;
    }
    final Node<E> getItrNode(int headDist,int tailDist){
        if(tailDist==0){
            return null;
        }
        return staticGetNode(this,headDist,tailDist);
    }
    abstract boolean uncheckedRemoveFirstMatch(Node<E> head,Predicate<Object> pred);
    abstract boolean uncheckedRemoveFirstNonNull(Node<E> head,Object nonNull);
    private boolean uncheckedAnyMatches(Node<E> head,Predicate<Object> pred){
        if(!pred.test(head.val)){
            final var tail=this.tail;
            do{
                if(head==tail){
                    return false;
                }
            }while(!pred.test((head=head.next).val));
        }
        return true;
    }
    private int uncheckedIndexOfMatch(Node<E> head,Predicate<Object> pred){
        int index=0;
        if(!pred.test(head.val)){
            final var tail=this.tail;
            do{
                if(head==tail){
                    return -1;
                }
                ++index;
            }while(!pred.test((head=head.next).val));
        }
        return index;
    }
    private int uncheckedSearch(Node<E> head,Predicate<Object> pred){
        int index=1;
        if(!pred.test(head.val)){
            final var tail=this.tail;
            do{
                if(head==tail){
                    return -1;
                }
                ++index;
            }while(!pred.test((head=head.next).val));
        }
        return index;
    }
    static abstract class Checked<E>extends AbstractRefDblLnkSeq<E> implements OmniDeque.OfRef<E>{
        transient int modCount;
        Checked(){
            super();
        }
        Checked(Node<E> onlyNode){
            super(onlyNode);
        }
        Checked(Node<E> head,int size,Node<E> tail){
            super(head,size,tail);
        }
        @Override
        public void addFirst(E val){
            ++modCount;
            super.addFirst(val);
        }
        @Override
        public void addLast(E val){
            ++this.modCount;
            super.addLast(val);
        }
        @Override
        public boolean contains(Object val){
            Node<E> head;
            if((head=this.head)!=null){
                if(val!=null){
                    final int modCount=this.modCount;
                    try{
                        super.uncheckedAnyMatches(head,val::equals);
                    }finally{
                        CheckedCollection.checkModCount(modCount,this.modCount);
                    }
                }
                return super.uncheckedAnyMatches(head,Objects::isNull);
            }
            return false;
        }

        @Override
        public E getFirst(){
            Node<E> head;
            if((head=this.head)!=null){
                return head.val;
            }
            throw new NoSuchElementException();
        }
        @Override
        public int indexOf(Object val){
            Node<E> head;
            if((head=this.head)!=null){
                if(val!=null){
                    final int modCount=this.modCount;
                    try{
                        return super.uncheckedIndexOfMatch(head,val::equals);
                    }finally{
                        CheckedCollection.checkModCount(modCount,this.modCount);
                    }
                }
                return super.uncheckedIndexOfMatch(head,Objects::isNull);
            }
            return -1;
        }
        @Override
        public E pollFirst(){
            Node<E> head;
            if((head=this.head)!=null){
                ++modCount;
                --size;
                staticEraseHead(this,head);
                return head.val;
            }
            return null;
        }

        @Override
        public E removeFirst(){
            Node<E> head;
            if((head=this.head)!=null){
                ++modCount;
                --size;
                staticEraseHead(this,head);
                return head.val;
            }
            throw new NoSuchElementException();
        }
        @Override
        public int search(Object val){
            Node<E> head;
            if((head=this.head)!=null){
                if(val!=null){
                    final int modCount=this.modCount;
                    try{
                        return super.uncheckedSearch(head,val::equals);
                    }finally{
                        CheckedCollection.checkModCount(modCount,this.modCount);
                    }
                }
                return super.uncheckedSearch(head,Objects::isNull);
            }
            return -1;
        }

        protected class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
            public ModCountChecker(int expectedModCount){
                super(expectedModCount);
            }
            @Override
            protected int getActualModCount(){
                return Checked.this.modCount;
            }
        }

    }
    static class Node<E>{
        transient Node<E> prev;
        transient E val;
        transient Node<E> next;
        Node(E val){
            this.val=val;
        }
        Node(E val,Node<E> next){
            this.val=val;
            this.next=next;
            joinNodes(this,next);
        }
        Node(Node<E> prev,E val){
            this.prev=prev;
            this.val=val;
            joinNodes(prev,this);
        }
        Node(Node<E> prev,E val,Node<E> next){
            this.prev=prev;
            this.val=val;
            this.next=next;
            joinNodes(prev,this);
            joinNodes(this,next);
        }
    }
    static abstract class Unchecked<E>extends AbstractRefDblLnkSeq<E>{
        // TODO maybe do something with this, or delete it
        Unchecked(){
            super();
        }
        Unchecked(Node<E> onlyNode){
            super(onlyNode);
        }
        Unchecked(Node<E> head,int size,Node<E> tail){
            super(head,size,tail);
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter){
            Node<E> head;
            if((head=this.head)!=null){
                if(filter.test(head.val)){
                    findNewHead(head,filter);
                    return true;
                }
                final Node<E> tail;
                if(head!=(tail=this.tail)){
                    if(filter.test(tail.val)){
                        collapseTail(head,tail,filter);
                        return true;
                    }
                    return collapseBody(head,tail,filter);
                }
            }
            return false;
        }
        abstract boolean collapseBody(Node<E> head,Node<E> tail,Predicate<? super E> filter);
        abstract void collapseTail(Node<E> head,Node<E> tail,Predicate<? super E> filter);
        abstract void findNewHead(Node<E> head,Predicate<? super E> filter);
    }
}
