package omni.impl.seq.dbllnk.ofdouble;
class Node{
  transient Node prev;
  transient double val;
  transient Node next;
  Node(double val){
    this.val=val;
  }
  Node(double val,Node next){
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(this,next);
  }
  Node(Node prev,double val){
    this.prev=prev;
    this.val=val;
    AbstractSeq.joinNodes(prev,this);
  }
  Node(Node prev,double val,Node next){
    this.prev=prev;
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(prev,this);
    AbstractSeq.joinNodes(this,next);
  }
}
