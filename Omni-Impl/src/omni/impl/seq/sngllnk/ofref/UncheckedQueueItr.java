package omni.impl.seq.sngllnk.ofref;

import omni.api.OmniIterator;

class UncheckedQueueItr<E>extends AbstractUncheckedItr<E> implements OmniIterator.OfRef<E>{
    transient final UncheckedQueue<E> root;
    UncheckedQueueItr(UncheckedQueue<E> root){
        super(root.head);
        this.root=root;
    }
    @Override
    public void remove(){
        final UncheckedQueue<E> root;
        final var lastRetPrev=this.lastRetPrev;
        Node<E> lastRet;
        if((lastRet=this.lastRet)==(root=this.root).head){
            if(lastRet==root.tail){
                root.tail=null;
            }
            root.head=next;
        }else{
            if(lastRet==root.tail){
                root.tail=lastRetPrev;
            }
            lastRetPrev.next=next;
        }
        this.lastRet=lastRetPrev;
        --root.size;
    }
}