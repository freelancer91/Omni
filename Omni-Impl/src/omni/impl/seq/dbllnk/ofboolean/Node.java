package omni.impl.seq.dbllnk.ofboolean;
class Node{
  transient Node prev;
  transient boolean val;
  transient Node next;
  Node(boolean val){
    this.val=val;
  }
  Node(boolean val,Node next){
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(this,next);
  }
  Node(Node prev,boolean val){
    this.prev=prev;
    this.val=val;
    AbstractSeq.joinNodes(prev,this);
  }
  Node(Node prev,boolean val,Node next){
    this.prev=prev;
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(prev,this);
    AbstractSeq.joinNodes(this,next);
  }
}
