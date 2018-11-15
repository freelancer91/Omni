package omni.impl.seq.sngllnk.ofref;

import java.util.function.Predicate;
import omni.util.OmniPred;

abstract class UncheckedSeq<E>extends AbstractSeq<E>{
    UncheckedSeq(){
    }

    UncheckedSeq(int size,Node<E> head){
        super(size,head);
    }

    public boolean contains(Object val){
        Node<E> head;
        return (head=this.head)!=null&&uncheckedAnyMatches(head,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public int hashCode(){
        Node<E> head;
        if((head=this.head)!=null){
            return uncheckedHashCode(head);
        }
        return 1;
    }



    @Override
    public String toString(){
        Node<E> head;
        if((head=this.head)!=null){
            final StringBuilder builder;
            uncheckedToString(head,builder=new StringBuilder("["));
            return builder.append(']').toString();
        }
        return "[]";
    }
    abstract void findNewHead(Node<E> curr,Predicate<? super E> filter);
    abstract boolean removeIfHelper(Node<E> curr,Predicate<? super E> filter);
    @Override
    boolean uncheckedRemoveIf(Node<E> curr,Predicate<? super E> filter){
        if(filter.test(curr.val)){
            findNewHead(curr,filter);
            return true;
        }
        return removeIfHelper(curr,filter);
    }
}