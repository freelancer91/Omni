package omni.impl.seq.sngllnk.ofref;

import java.util.function.Consumer;

class AbstractUncheckedItr<E>{
    transient Node<E> next;
    transient Node<E> lastRet;
    transient Node<E> lastRetPrev;
    AbstractUncheckedItr(Node<E> next){
        this.next=next;
    }
    public boolean hasNext(){
        return next!=null;
    }
    public E next(){
        this.lastRetPrev=this.lastRet;
        Node<E> lastRet;
        this.lastRet=lastRet=this.next;
        this.next=lastRet.next;
        return lastRet.val;
    }
    public void forEachRemaining(Consumer<? super E> action){
        Node<E> next;
        if((next=this.next)!=null){
            for(Node<E> lastRet,lastRetPrev=this.lastRet;;lastRetPrev=lastRet){
                action.accept((lastRet=next).val);
                if((next=next.next)==null){
                    this.next=null;
                    this.lastRet=lastRet;
                    this.lastRetPrev=lastRetPrev;
                    return;
                }
            }
        }
    }
}