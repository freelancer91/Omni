package omni.impl.seq.dbllnk.ofint;
class Node{
  transient Node prev;
  transient int val;
  transient Node next;
  Node(int val){
    this.val=val;
  }
  Node(int val,Node next){
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(this,next);
  }
  Node(Node prev,int val){
    this.prev=prev;
    this.val=val;
    AbstractSeq.joinNodes(prev,this);
  }
  Node(Node prev,int val,Node next){
    this.prev=prev;
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(prev,this);
    AbstractSeq.joinNodes(this,next);
  }
}
