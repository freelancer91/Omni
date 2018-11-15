package omni.impl.seq.dbllnk.oflong;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractLongItr;
import omni.impl.CheckedCollection;
import omni.impl.seq.dbllnk.oflong.AbstractSeq.Checked;
class CheckedAscendingItr extends AbstractLongItr implements OmniIterator.OfLong{
    transient final Checked root;
    transient Node cursor;
    transient Node lastRet;
    transient int modCount;
    CheckedAscendingItr(Checked root){
        this.root=root;
        this.cursor=root.head;
        this.modCount=root.modCount;
    }
    CheckedAscendingItr(Checked root,Node cursor){
        this.root=root;
        this.cursor=cursor;
        this.modCount=root.modCount;
    }
    @Override
    public void forEachRemaining(Consumer<? super Long> action){
        Node cursor;
        if((cursor=this.cursor)!=null){
            final int modCount=this.modCount;
            final var root=this.root;
            try{
                cursor=uncheckedForEach(cursor,root,action::accept);
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
            this.cursor=null;
            this.lastRet=cursor;
        }
    }
    @Override
    public void forEachRemaining(LongConsumer action){
        Node cursor;
        if((cursor=this.cursor)!=null){
            final int modCount=this.modCount;
            final var root=this.root;
            try{
                cursor=uncheckedForEach(cursor,root,action::accept);
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
    public long nextLong(){
        CheckedCollection.checkModCount(modCount,root.modCount);
        Node cursor;
        if((cursor=this.cursor)!=null){
            this.cursor=iterate(cursor);
            this.lastRet=cursor;
            return cursor.val;
        }
        throw new NoSuchElementException();
    }
    @Override
    public void remove(){
        Node lastRet;
        if((lastRet=this.lastRet)!=null){
            int modCount;
            Checked root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            uncheckedRemove(lastRet,root);
            this.lastRet=null;
            return;
        }
        throw new IllegalStateException();
    }
    Node iterate(Node cursor){
        return cursor.next;
    }
    Node uncheckedForEach(Node cursor,Checked root,LongConsumer action){
        CheckedList.uncheckedForEachForward(cursor,cursor=root.tail,action);
        return cursor;
    }
    void uncheckedRemove(Node lastRet,Checked root){
        if(--root.size==0){
            CheckedList.staticInit(root,null);
        }else{
            if(lastRet==root.head){
                CheckedList.staticSetHead(root,cursor);
            }else if(lastRet==root.tail){
                CheckedList.staticEraseTail(root,lastRet);
            }else{
                CheckedList.joinNodes(lastRet.prev,cursor);
            }
        }
    }
}