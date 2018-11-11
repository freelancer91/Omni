package omni.impl.seq.dbllnk.ofbyte;
class Node{
  transient Node prev;
  transient byte val;
  transient Node next;
  Node(byte val){
    this.val=val;
  }
  Node(byte val,Node next){
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(this,next);
  }
  Node(Node prev,byte val){
    this.prev=prev;
    this.val=val;
    AbstractSeq.joinNodes(prev,this);
  }
  Node(Node prev,byte val,Node next){
    this.prev=prev;
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(prev,this);
    AbstractSeq.joinNodes(this,next);
  }
}
