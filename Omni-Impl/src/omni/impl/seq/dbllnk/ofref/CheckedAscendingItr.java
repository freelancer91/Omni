package omni.impl.seq.dbllnk.ofref;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import omni.impl.CheckedCollection;
import omni.impl.seq.dbllnk.ofref.AbstractRefDblLnkSeq.Checked;

class CheckedAscendingItr<E> implements OmniIterator.OfRef<E>{
    transient final Checked<E> root;
    transient Node<E> cursor;
    transient Node<E> lastRet;
    transient int modCount;
    CheckedAscendingItr(Checked<E> root){
        this.root=root;
        this.cursor=root.head;
        this.modCount=root.modCount;
    }
    CheckedAscendingItr(Checked<E> root,Node<E> cursor){
        this.root=root;
        this.cursor=cursor;
        this.modCount=root.modCount;
    }
    @Override
    public void forEachRemaining(Consumer<? super E> action){
        Node<E> cursor;
        if((cursor=this.cursor)!=null){
            final int modCount=this.modCount;
            final var root=this.root;
            try{
                cursor=uncheckedForEach(cursor,root,action);
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
            this.cursor=null;
            this.lastRet=cursor;
        }
    }
    @Override
    public boolean hasNext(){
        return cursor!=null;
    }
    @Override
    public E next(){
        CheckedCollection.checkModCount(modCount,root.modCount);
        Node<E> cursor;
        if((cursor=this.cursor)!=null){
            this.cursor=iterate(cursor);
            this.lastRet=cursor;
            return cursor.val;
        }
        throw new NoSuchElementException();
    }
    @Override
    public void remove(){
        Node<E> lastRet;
        if((lastRet=this.lastRet)!=null){
            int modCount;
            Checked<E> root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            uncheckedRemove(lastRet,root);
            this.lastRet=null;
            return;
        }
        throw new IllegalStateException();
    }
    Node<E> iterate(Node<E> cursor){
        return cursor.next;
    }
    Node<E> uncheckedForEach(Node<E> cursor,Checked<E> root,Consumer<? super E> action){
        CheckedRefDblLnkList.uncheckedForEachForward(cursor,cursor=root.tail,action);
        return cursor;
    }
    void uncheckedRemove(Node<E> lastRet,Checked<E> root){
        if(--root.size==0){
            CheckedRefDblLnkList.staticInit(root,null);
        }else{
            if(lastRet==root.head){
                CheckedRefDblLnkList.staticSetHead(root,cursor);
            }else if(lastRet==root.tail){
                CheckedRefDblLnkList.staticEraseTail(root,lastRet);
            }else{
                CheckedRefDblLnkList.joinNodes(lastRet.prev,cursor);
            }
        }
    }
}