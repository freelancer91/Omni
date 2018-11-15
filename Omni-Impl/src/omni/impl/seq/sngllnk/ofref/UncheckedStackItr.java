package omni.impl.seq.sngllnk.ofref;

import omni.api.OmniIterator;

class UncheckedStackItr<E>extends AbstractUncheckedItr<E> implements OmniIterator.OfRef<E>{
    transient final UncheckedStack<E> root;
    UncheckedStackItr(UncheckedStack<E> root){
        super(root.head);
        this.root=root;
    }
    @Override
    public void remove(){
        final UncheckedStack<E> root;
        final var lastRetPrev=this.lastRetPrev;
        if(this.lastRet==(root=this.root).head){
            root.head=next;
        }else{
            lastRetPrev.next=next;
        }
        this.lastRet=lastRetPrev;
        --root.size;
    }
}