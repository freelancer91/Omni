package omni.impl.seq.dbllnk.ofdouble;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import omni.api.OmniIterator;
import omni.impl.AbstractDoubleItr;
import omni.impl.CheckedCollection;
class CheckedAscendingSubItr extends AbstractDoubleItr implements OmniIterator.OfDouble{
    transient final CheckedSubList parent;
    transient Node cursor;
    transient Node lastRet;
    transient int modCount;
    CheckedAscendingSubItr(CheckedSubList parent){
        this.parent=parent;
        this.cursor=parent.head;
        this.modCount=parent.modCount;
    }
    CheckedAscendingSubItr(CheckedSubList parent,Node cursor){
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
    }
    @Override
    public void forEachRemaining(Consumer<? super Double> action){
        Node cursor;
        if((cursor=this.cursor)!=null){
            final int modCount=this.modCount;
            final var parent=this.parent;
            try{
                CheckedSubList.uncheckedForEachForward(cursor,cursor=parent.tail,action::accept);
            }finally{
                CheckedCollection.checkModCount(modCount,parent.root.modCount);
            }
            this.cursor=null;
            this.lastRet=cursor;
        }
    }
    @Override
    public void forEachRemaining(DoubleConsumer action){
        Node cursor;
        if((cursor=this.cursor)!=null){
            final int modCount=this.modCount;
            final var parent=this.parent;
            try{
                CheckedSubList.uncheckedForEachForward(cursor,cursor=parent.tail,action);
            }finally{
                CheckedCollection.checkModCount(modCount,parent.root.modCount);
            }
            this.cursor=null;
            this.lastRet=cursor;
        }
    }
    @Override
    public boolean hasNext(){
        return this.cursor!=null;
    }
    @Override
    public double nextDouble(){
        CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        Node cursor;
        if((cursor=this.cursor)!=null){
            if(cursor==parent.tail){
                this.cursor=null;
            }else{
                this.cursor=cursor.next;
            }
            this.lastRet=cursor;
            return cursor.val;
        }
        throw new NoSuchElementException();
    }
    @Override
    public void remove(){
        parent.ascItrRemove(this);
        this.lastRet=null;
    }
}
