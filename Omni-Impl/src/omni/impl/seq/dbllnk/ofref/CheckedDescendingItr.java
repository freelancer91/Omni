package omni.impl.seq.dbllnk.ofref;

import java.util.function.Consumer;
import omni.impl.seq.dbllnk.ofref.AbstractRefDblLnkSeq.Checked;

class CheckedDescendingItr<E>extends CheckedAscendingItr<E>{
    CheckedDescendingItr(Checked<E> root){
        super(root,root.tail);
    }
    @Override
    Node<E> iterate(Node<E> cursor){
        return cursor.prev;
    }
    @Override
    Node<E> uncheckedForEach(Node<E> cursor,Checked<E> root,Consumer<? super E> action){
        CheckedRefDblLnkList.uncheckedForEachReverse(cursor,action);
        return root.head;
    }
    @Override
    void uncheckedRemove(Node<E> lastRet,Checked<E> root){
        if(--root.size==0){
            CheckedRefDblLnkList.staticInit(root,null);
        }else{
            if(lastRet==root.head){
                CheckedRefDblLnkList.staticEraseHead(root,lastRet);
            }else if(lastRet==root.tail){
                CheckedRefDblLnkList.staticSetTail(root,cursor);
            }else{
                CheckedRefDblLnkList.joinNodes(cursor,lastRet.next);
            }
        }
    }
}