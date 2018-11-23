package omni.impl.seq.sngllnk.ofref;

import java.util.Objects;
import java.util.function.Predicate;
import omni.api.OmniQueue;
import omni.api.OmniStack;
import omni.impl.CheckedCollection;

abstract class CheckedSeq<E>extends AbstractSeq<E>{
    transient int modCount;
    CheckedSeq(){
    }
    CheckedSeq(int size,Node<E> head){
        super(size,head);
    }
    static class CheckedQueue<E>extends CheckedSeq<E> implements OmniQueue.OfRef<E>{
        transient Node<E> tail;
        CheckedQueue(){
            super();
        }
        CheckedQueue(Node<E> onlyNode){
            super(1,onlyNode);
            this.tail=onlyNode;
        }
        CheckedQueue(int size,Node<E> head,Node<E> tail){
            super(size,head);
            this.tail=tail;
        }
        @Override
        public void clear(){
            // TODO
        }
        @Override
        public Object clone(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public boolean equals(Object val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public omni.api.OmniIterator.OfRef<E> iterator(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public boolean add(E val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public E poll(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public E element(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public boolean offer(E val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public E remove(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter){
            // TODO Auto-generated method stub
        }
        @Override
        boolean removeIfHelper(int modCount,Node<E> curr,Predicate<? super E> filter){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean uncheckedRemoveFirstMatch(Node<E> curr,Predicate<Object> pred){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean uncheckedRemoveFirstNonNull(Node<E> curr,Object nonNull){
            // TODO Auto-generated method stub
            return false;
        }
    }
    static class CheckedStack<E>extends CheckedSeq<E> implements OmniStack.OfRef<E>{
        CheckedStack(){
            super();
        }
        CheckedStack(int size,Node<E> head){
            super(size,head);
        }
        @Override
        public int search(Object val){
            Node<E> head;
            if((head=this.head)!=null){
                if(val!=null){
                    final int modCount=this.modCount;
                    try{
                        return uncheckedSearch(head,val::equals);
                    }finally{
                        CheckedCollection.checkModCount(modCount,this.modCount);
                    }
                }
                return uncheckedSearch(head,Objects::isNull);
            }
            return -1;
        }
        @Override
        public boolean add(E val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public E poll(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public E pop(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        boolean uncheckedRemoveFirstMatch(Node<E> curr,Predicate<Object> pred){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean uncheckedRemoveFirstNonNull(Node<E> curr,Object nonNull){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter){
            // TODO Auto-generated method stub
        }
        @Override
        boolean removeIfHelper(int modCount,Node<E> curr,Predicate<? super E> filter){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public void clear(){
            // TODO
        }
        @Override
        public Object clone(){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public boolean equals(Object val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public omni.api.OmniIterator.OfRef<E> iterator(){
            // TODO Auto-generated method stub
            return null;
        }
    }
    abstract void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter);
    abstract boolean removeIfHelper(int modCount,Node<E> curr,Predicate<? super E> filter);
    @Override
    boolean uncheckedRemoveIf(Node<E> curr,Predicate<? super E> filter){
        int modCount=this.modCount;
        try{
            if(filter.test(curr.val)){
                findNewHead(modCount,curr,filter);
                return true;
            }
            return removeIfHelper(modCount,curr,filter);
        }catch(RuntimeException e){
            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
    }

    public boolean contains(Object val){
        Node<E> head;
        if((head=this.head)!=null){
            if(val!=null){
                final int modCount=this.modCount;
                try{
                    return uncheckedAnyMatches(head,val::equals);
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                }
            }
            return uncheckedAnyMatches(head,Objects::isNull);
        }
        return false;
    }

    @Override
    public int hashCode(){
        Node<E> head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                return uncheckedHashCode(head);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        return 1;
    }


    @Override
    public String toString(){
        Node<E> head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            final StringBuilder builder;
            try{
                uncheckedToString(head,builder=new StringBuilder("["));
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            return builder.append(']').toString();
        }
        return "[]";
    }
}