package omni.impl.seq.sngllnk.ofref;

import java.util.function.Predicate;
import omni.api.OmniStack;
import omni.util.OmniPred;

public class UncheckedStack<E>extends UncheckedSeq<E> implements OmniStack.OfRef<E>{
    private static <E> int removeNodes(Node<E> prev,Node<E> curr,Predicate<? super E> filter){
        int numRemoved=1;
        while((curr=curr.next)!=null){
            if(!filter.test(curr.val)){
                prev.next=curr;
                do{
                    if((curr=(prev=curr).next)==null){
                        return numRemoved;
                    }
                }while(!filter.test(curr.val));
            }
            ++numRemoved;
        }
        prev.next=curr;
        return numRemoved;
    }
    private static <E> int removeNodes(Node<E> prev,Predicate<? super E> filter){
        for(Node<E> curr;(curr=prev.next)!=null;prev=curr){
            if(filter.test(curr.val)){
                return removeNodes(prev,curr,filter);
            }
        }
        return 0;
    }
    UncheckedStack(){
        super();
    }
    UncheckedStack(int size,Node<E> head){
        super(size,head);
    }
    @Override
    public boolean add(E val){
        super.push(val);
        return true;
    }
    @Override
    public void clear(){
        this.size=0;
        this.head=null;
    }
    @Override
    public Object clone(){
        Node<E> head,newHead;
        if((head=this.head)!=null){
            Node<E> curr;
            for(curr=newHead=new Node<>(head.val);(head=head.next)!=null;curr.next=curr=new Node<>(head.val)){}
        }else{
            newHead=null;
        }
        return new UncheckedStack<>(this.size,newHead);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public omni.api.OmniIterator.OfRef<E> iterator(){
        return new UncheckedStackItr<>(this);
    }
    @Override
    public E poll(){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedPop(head);
        }
        return null;
    }
    @Override
    public E pop(){
        return uncheckedPop(head);
    }
    @Override
    public int search(Object val){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
    }
    @Override
    boolean uncheckedRemoveFirstMatch(Node<E> curr,Predicate<Object> pred){
        if(pred.test(curr.val)){
            if(--this.size==0){
                this.head=null;
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
            }else{
                this.head=curr.next;
            }
            return true;
        }
        return removeFirstMatchHelper(curr,nonNull::equals);
    }
    @Override
    void findNewHead(Node<E> curr,Predicate<? super E> filter){
        int numRemoved=1;
        while((curr=curr.next)!=null){
            if(!filter.test(curr.val)){
                numRemoved+=removeNodes(curr,filter);
                break;
            }
            ++numRemoved;
        }
        this.head=curr;
        this.size-=numRemoved;
    }
    @Override
    boolean removeIfHelper(Node<E> curr,Predicate<? super E> filter){
        for(Node<E> prev;(curr=(prev=curr).next)!=null;){
            if(filter.test(curr.val)){
                this.size-=removeNodes(prev,curr,filter);
                return true;
            }
        }
        return false;
    }
    private boolean removeFirstMatchHelper(Node<E> prev,Predicate<Object> pred){
        for(Node<E> curr;(curr=prev.next)!=null;prev=curr){
            if(pred.test(curr.val)){
                --this.size;
                prev.next=curr.next;
                return true;
            }
        }
        return false;
    }
    private E uncheckedPop(Node<E> oldHead){
        if(--size==0){
            this.head=null;
        }else{
            this.head=oldHead.next;
        }
        return oldHead.val;
    }
}