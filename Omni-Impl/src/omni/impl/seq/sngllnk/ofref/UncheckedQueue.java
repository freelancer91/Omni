package omni.impl.seq.sngllnk.ofref;

import java.util.function.Predicate;
import omni.api.OmniQueue;

public class UncheckedQueue<E>extends UncheckedSeq<E> implements OmniQueue.OfRef<E>{
    transient Node<E> tail;
    UncheckedQueue(){
        super();
    }
    UncheckedQueue(Node<E> onlyNode){
        super(1,onlyNode);
        this.tail=onlyNode;
    }
    UncheckedQueue(int size,Node<E> head,Node<E> tail){
        super(size,head);
        this.tail=tail;
    }
    @Override
    public void clear(){
        this.size=0;
        this.head=null;
        this.tail=null;
    }
    @Override
    public Object clone(){
        Node<E> head,newHead,newTail;
        if((head=this.head)!=null){
            for(newTail=newHead=new Node<>(head.val);(head=head.next)!=null;newTail.next=newTail=new Node<>(
                    head.val)){}
        }else{
            newHead=null;
            newTail=null;
        }
        return new UncheckedQueue<>(this.size,newHead,newTail);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public omni.api.OmniIterator.OfRef<E> iterator(){
        return new UncheckedQueueItr<>(this);
    }
    @Override
    boolean uncheckedRemoveFirstMatch(Node<E> curr,Predicate<Object> pred){
        if(pred.test(curr.val)){
            if(--this.size==0){
                this.head=null;
                this.tail=null;
            }else{
                this.head=curr.next;
            }
            return true;
        }
        return removeFirstMatchHelper(curr,pred);
    }
    @Override
    boolean uncheckedRemoveFirstNonNull(Node<E> curr,Object nonNull){
        if(nonNull.equals(curr.val)){
            if(--this.size==0){
                this.head=null;
                this.tail=null;
            }else{
                this.head=curr.next;
            }
            return true;
        }
        return removeFirstMatchHelper(curr,nonNull::equals);
    }
    @Override
    boolean removeIfHelper(Node<E> curr,Predicate<? super E> filter){
        final var tail=this.tail;
        while(curr!=tail){
            Node<E> prev;
            if(filter.test((curr=(prev=curr).next).val)){
                this.size-=removeIfHelper(prev,curr,tail,filter);
                return true;
            }
        }
        return false;
    }
    private int removeIfHelper(Node<E> prev,Node<E> curr,Node<E> tail,Predicate<? super E> filter){
        int numRemoved=1;
        while(curr!=tail){
            if(!filter.test((curr=curr.next).val)){
                prev.next=curr;
                do{
                    if(curr==tail){
                        return numRemoved;
                    }
                }while(!filter.test((curr=(prev=curr).next).val));
            }
            ++numRemoved;
        }
        prev.next=null;
        this.tail=prev;
        return numRemoved;
    }
    @Override
    void findNewHead(Node<E> curr,Predicate<? super E> filter){
        int numRemoved=1;
        outer:for(final var tail=this.tail;;++numRemoved){
            if(curr==tail){
                this.head=null;
                this.tail=null;
                break;
            }
            if(!filter.test((curr=curr.next).val)){
                this.head=curr;
                while(curr!=tail){
                    Node<E> prev;
                    if(filter.test((curr=(prev=curr).next).val)){
                        numRemoved+=removeIfHelper(prev,curr,tail,filter);
                        break outer;
                    }
                }
                break;
            }
        }
        this.size-=numRemoved;
    }
    private boolean removeFirstMatchHelper(Node<E> prev,Predicate<Object> pred){
        final Node<E> tail;
        if(prev!=(tail=this.tail)){
            for(Node<E> curr;(curr=prev.next)!=tail;prev=curr){
                if(pred.test(curr.val)){
                    --this.size;
                    prev.next=curr.next;
                    return true;
                }
            }
            if(pred.test(tail.val)){
                --this.size;
                prev.next=null;
                this.tail=prev;
                return true;
            }
        }
        return false;
    }
    private void addNode(Node<E> newTail){
        Node<E> oldTail;
        if((oldTail=this.tail)!=null){
            oldTail.next=newTail;
        }
        this.tail=newTail;
        ++this.size;
    }
    @Override
    public boolean add(E val){
        addNode(new Node<>(val));
        return true;
    }
    @Override
    public E poll(){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedPop(head);
        }
        return null;
    }
    private E uncheckedPop(Node<E> oldHead){
        if(--size==0){
            this.head=null;
            this.tail=null;
        }else{
            this.head=oldHead.next;
        }
        return oldHead.val;
    }
    @Override
    public E element(){
        return head.val;
    }
    @Override
    public boolean offer(E val){
        addNode(new Node<>(val));
        return true;
    }
    @Override
    public E remove(){
        return uncheckedPop(head);
    }
}