package omni.impl.seq.dbllnk.offloat;
public class Node{
  transient Node prev;
  transient float val;
  transient Node next;
  Node(float val){
    this.val=val;
  }
  Node(float val,Node next){
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(this,next);
  }
  Node(Node prev,float val){
    this.prev=prev;
    this.val=val;
    AbstractSeq.joinNodes(prev,this);
  }
  Node(Node prev,float val,Node next){
    this.prev=prev;
    this.val=val;
    this.next=next;
    AbstractSeq.joinNodes(prev,this);
    AbstractSeq.joinNodes(this,next);
  }
}
