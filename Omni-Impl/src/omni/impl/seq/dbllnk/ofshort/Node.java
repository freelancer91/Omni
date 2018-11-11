package omni.impl.seq.dbllnk.ofshort;
class Node{
  transient Node prev;
  transient short val;
  transient Node next;
  Node(Node prev,short val){
    this.prev=prev;
    this.val=val;
    AbstractSeq.joinNodes(prev,this);
  }
  Node(Node prev,short val,Node next){
    this.prev=prev;
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(prev,this);
    AbstractSeq.joinNodes(this,next);
  }
  Node(short val){
    this.val=val;
  }
  Node(short val,Node next){
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(this,next);
  }
}
