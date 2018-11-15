package omni.impl.seq.sngllnk.ofref;

class Node<E>{
    transient E val;
    transient Node<E> next;
    Node(E val){
        this.val=val;
    }
    Node(E val,Node<E> next){
        this.val=val;
        this.next=next;
    }
}
