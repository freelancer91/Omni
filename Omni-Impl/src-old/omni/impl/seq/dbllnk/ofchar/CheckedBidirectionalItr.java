package omni.impl.seq.dbllnk.ofchar;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import omni.api.OmniListIterator;
import omni.function.CharConsumer;
import omni.impl.CheckedCollection;
import omni.impl.seq.dbllnk.ofchar.AbstractSeq.Checked;
class CheckedBidirectionalItr extends CheckedAscendingItr implements OmniListIterator.OfChar{
    private transient int nextIndex;
    CheckedBidirectionalItr(Checked root){
        super(root);
    }
    CheckedBidirectionalItr(Checked root,Node cursor,int index){
        super(root,cursor);
        this.nextIndex=index;
    }
    @Override
    public void add(char val){
        int modCount;
        Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        if(++root.size!=1){
            Node cursor;
            if((cursor=this.cursor)==null){
                root.tail=new Node(root.tail,val);
            }else if(cursor==root.head){
                root.head=new Node(val,cursor);
            }else{
                new Node(cursor.prev,val,cursor);
            }
        }else{
            CheckedList.staticInit(root,new Node(val));
        }
        ++nextIndex;
        lastRet=null;
    }
    @Override
    public void forEachRemaining(Consumer<? super Character> action){
        Node cursor;
        if((cursor=this.cursor)!=null){
            final int modCount=this.modCount;
            final var root=this.root;
            try{
                CheckedList.uncheckedForEachForward(cursor,cursor=root.tail,action::accept);
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
            this.nextIndex=root.size;
            this.cursor=null;
            lastRet=cursor;
        }
    }
    @Override
    public void forEachRemaining(CharConsumer action){
        Node cursor;
        if((cursor=this.cursor)!=null){
            final int modCount=this.modCount;
            final var root=this.root;
            try{
                CheckedList.uncheckedForEachForward(cursor,cursor=root.tail,action);
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
            this.nextIndex=root.size;
            this.cursor=null;
            lastRet=cursor;
        }
    }
    @Override
    public boolean hasPrevious(){
        return this.nextIndex!=0;
    }
    @Override
    public char nextChar(){
        CheckedCollection.checkModCount(modCount,root.modCount);
        Node cursor;
        if((cursor=this.cursor)!=null){
            ++nextIndex;
            this.cursor=cursor.next;
            lastRet=cursor;
            return cursor.val;
        }
        throw new NoSuchElementException();
    }
    @Override
    public int nextIndex(){
        return this.nextIndex;
    }
    @Override
    public char previousChar(){
        CheckedCollection.checkModCount(modCount,root.modCount);
        int nextIndex;
        if((nextIndex=this.nextIndex)!=0){
            this.nextIndex=nextIndex-1;
            Node cursor;
            this.cursor=cursor=this.cursor.prev;
            lastRet=cursor;
            return cursor.val;
        }
        throw new NoSuchElementException();
    }
    @Override
    public int previousIndex(){
        return this.nextIndex-1;
    }
    @Override
    public void set(char val){
        Node lastRet;
        if((lastRet=this.lastRet)!=null){
            CheckedCollection.checkModCount(modCount,root.modCount);
            lastRet.val=val;
            return;
        }
        throw new IllegalStateException();
    }
    @Override
    void uncheckedRemove(Node lastRet,Checked root){
        if(--root.size!=0){
            Node cursor;
            if(lastRet!=(cursor=this.cursor)){
                --nextIndex;
                if(lastRet==root.head){
                    CheckedList.staticSetHead(root,cursor);
                }else if(lastRet==root.tail){
                    CheckedList.staticEraseTail(root,lastRet);
                }else{
                    CheckedList.joinNodes(lastRet.prev,cursor);
                }
            }else{
                if(lastRet==root.head){
                    CheckedList.staticEraseHead(root,lastRet);
                }else if(lastRet==root.tail){
                    CheckedList.staticEraseTail(root,lastRet);
                }else{
                    CheckedList.joinNodes(lastRet.prev,lastRet.next);
                }
            }
        }else{
            CheckedList.staticInit(root,null);
            if(lastRet!=cursor){
                --nextIndex;
            }
        }
    }
}
