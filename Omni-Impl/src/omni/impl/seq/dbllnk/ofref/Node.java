package omni.impl.seq.dbllnk.ofref;
class Node<E>{
  transient Node<E> prev;
  transient E val;
  transient Node<E> next;
  Node(E val){
    this.val=val;
  }
  Node(E val,Node<E> next){
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(this,next);
  }
  Node(Node<E> prev,E val){
    this.prev=prev;
    this.val=val;
    AbstractSeq.joinNodes(prev,this);
  }
  Node(Node<E> prev,E val,Node<E> next){
    this.prev=prev;
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(prev,this);
    AbstractSeq.joinNodes(this,next);
  }
}