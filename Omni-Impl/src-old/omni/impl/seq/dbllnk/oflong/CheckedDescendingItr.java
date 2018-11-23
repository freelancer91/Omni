package omni.impl.seq.dbllnk.oflong;

import java.util.function.LongConsumer;
import omni.impl.seq.dbllnk.oflong.AbstractSeq.Checked;
class CheckedDescendingItr extends CheckedAscendingItr{
    CheckedDescendingItr(Checked root){
        super(root,root.tail);
    }
    @Override
    Node iterate(Node cursor){
        return cursor.prev;
    }
    @Override
    Node uncheckedForEach(Node cursor,Checked root,LongConsumer action){
        AbstractSeq.uncheckedForEachReverse(cursor,action);
        return root.head;
    }
    @Override
    void uncheckedRemove(Node lastRet,Checked root){
        if(--root.size==0){
            AbstractSeq.staticInit(root,null);
        }else{
            if(lastRet==root.head){
                AbstractSeq.staticEraseHead(root,lastRet);
            }else if(lastRet==root.tail){
                AbstractSeq.staticSetTail(root,cursor);
            }else{
                AbstractSeq.joinNodes(cursor,lastRet.next);
            }
        }
    }
}