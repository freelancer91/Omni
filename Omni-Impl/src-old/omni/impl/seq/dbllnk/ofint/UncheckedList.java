package omni.impl.seq.dbllnk.ofint;
import java.util.function.IntPredicate;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.util.TypeUtil;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfInt{
  static int collapseBodyHelper(Node prev,Node next,IntPredicate filter){
    int numRemoved=0;
    for(Node curr;(curr=prev.next)!=next;prev=curr){
      if(filter.test(curr.val)){
        do{
          ++numRemoved;
          if((curr=curr.next)==next){
            joinNodes(prev,next);
            return numRemoved;
          }
        }while(filter.test(curr.val));
        joinNodes(prev,curr);
      }
    }
    return numRemoved;
  }
  UncheckedList(){
    super();
  }
  UncheckedList(Node onlyNode){
    super(onlyNode);
  }
  UncheckedList(Node head,int size,Node tail){
    super(head,size,tail);
  }
  @Override public boolean add(boolean val){
    super.addLast(TypeUtil.castToByte(val));
    return true;
  }
  @Override public boolean add(int val){
    super.addLast(val);
    return true;
  }
  @Override public void add(int index,int val){
    int size;
    if((size=this.size)!=0){
      if(index==0){
        head=new Node(val,head);
      }else{
        int tailDist;
        if((tailDist=size-index)==0){
          tail=new Node(tail,val);
        }else{
          staticInsertNode(this,index,val,tailDist);
        }
      }
    }else{
      staticInit(this,new Node(val));
    }
    this.size=size+1;
  }
  @Override public boolean add(Integer val){
    super.addLast(val);
    return true;
  }
  @Override public void addFirst(Integer val){
    super.addFirst(val);
  }
  @Override public void addLast(Integer val){
    super.addLast(val);
  }
  @Override public omni.api.OmniIterator.OfInt descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Integer element(){
    return head.val;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Integer getFirst(){
    return head.val;
  }
  @Override public int getFirstInt(){
    return head.val;
  }
  @Override public Integer getLast(){
    return tail.val;
  }
  @Override public int getLastInt(){
    return tail.val;
  }
  @Override public int intElement(){
    return head.val;
  }
  @Override public omni.api.OmniIterator.OfInt iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfInt listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfInt listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(int val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(Integer val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(int val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerFirst(Integer val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerLast(int val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerLast(Integer val){
    super.addLast(val);
    return true;
  }
  @Override public Integer poll(){
    return super.pollFirst();
  }
  @Override public double pollDouble(){
    return super.pollFirstDouble();
  }
  @Override public float pollFloat(){
    return super.pollFirstFloat();
  }
  @Override public int pollInt(){
    return super.pollFirstInt();
  }
  @Override public Integer pollLast(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return null;
  }
  @Override public double pollLastDouble(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return Double.NaN;
  }
  @Override public float pollLastFloat(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return Float.NaN;
  }
  @Override public int pollLastInt(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return Integer.MIN_VALUE;
  }
  @Override public long pollLastLong(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollLong(){
    return super.pollFirstLong();
  }
  @Override public Integer pop(){
    return super.removeFirstInt();
  }
  @Override public int popInt(){
    return super.removeFirstInt();
  }
  @Override public void push(int val){
    super.addFirst(val);
  }
  @Override public void push(Integer val){
    super.addFirst(val);
  }
  @Override public Integer remove(){
    return super.removeFirstInt();
  }
  @Override public Integer removeFirst(){
    return super.removeFirstInt();
  }
  @Override public int removeInt(){
    return super.removeFirstInt();
  }
  @Override public int removeIntAt(int index){
    Node node;
    int size;
    if((size=--this.size)!=0){
      if(index==0){
        staticEraseHead(this,node=head);
      }else{
        int tailDist;
        if((tailDist=size-index)==0){
          staticEraseTail(this,node=tail);
        }else{
          node=staticExtractNode(this,index,tailDist);
        }
      }
    }else{
      node=head;
      staticInit(this,null);
    }
    return node.val;
  }
  @Override public Integer removeLast(){
    return super.removeLastInt();
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,TypeUtil.castToByte(val));
  }
  @Override public boolean removeLastOccurrence(double val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(int)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(float val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&(double)val==(double)(v=(int)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(int val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(long val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(int)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(Object val){
    final Node tail;
    return (tail=this.tail)!=null&&val instanceof Integer&&uncheckedRemoveLastMatch(tail,(int)val);
  }
  @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override boolean collapseBody(Node head,Node tail,IntPredicate filter){
    Node prev;
    while((head=(prev=head).next)!=tail){
      if(filter.test(head.val)){
        int numRemoved;
        for(numRemoved=1;(head=head.next)!=tail;++numRemoved){
          if(!filter.test(head.val)){
            numRemoved+=collapseBodyHelper(head,tail,filter);
            break;
          }
        }
        joinNodes(prev,head);
        size-=numRemoved;
        return true;
      }
    }
    return false;
  }
  @Override void collapseTail(Node head,Node tail,IntPredicate filter){
    int numRemoved;
    for(numRemoved=1;(tail=tail.prev)!=head;++numRemoved){
      if(!filter.test(tail.val)){
        numRemoved+=collapseBodyHelper(head,tail,filter);
        break;
      }
    }
    staticSetTail(this,tail);
    size-=numRemoved;
  }
  @Override void findNewHead(Node head,IntPredicate filter){
    final Node tail;
    if((tail=this.tail)!=head){
      if(filter.test(tail.val)){
        collapseHeadAndTail(head,tail,filter);
      }else{
        collapseHead(head.next,tail,filter);
      }
    }else{
      super.clear();
    }
  }
  @Override boolean uncheckedRemoveFirstMatch(Node curr,int val){
    final var tail=this.tail;
    if(curr.val==val){
      if(curr==tail){
        staticInit(this,null);
      }else{
        staticEraseHead(this,curr);
      }
    }else{
      Node prev;
      do{
        if(curr==tail){ return false; }
      }while((curr=(prev=curr).next).val!=val);
      if(curr==tail){
        staticSetTail(this,prev);
      }else{
        joinNodes(prev,curr.next);
      }
    }
    --size;
    return true;
  }
  private void collapseHead(Node headCandidate,Node tail,IntPredicate filter){
    int numRemoved;
    for(numRemoved=1;headCandidate!=tail;++numRemoved,headCandidate=headCandidate.next){
      if(!filter.test(headCandidate.val)){
        numRemoved+=collapseBodyHelper(headCandidate,tail,filter);
        break;
      }
    }
    staticSetHead(this,headCandidate);
    size-=numRemoved;
  }
  private void collapseHeadAndTail(Node head,Node tail,IntPredicate filter){
    for(int numRemoved=2;(head=head.next)!=tail;++numRemoved){
      if(!filter.test(head.val)){
        while((tail=tail.prev)!=head){
          if(!filter.test(tail.val)){
            numRemoved+=collapseBodyHelper(head,tail,filter);
            break;
          }
          ++numRemoved;
        }
        staticSetHead(this,head);
        staticSetTail(this,tail);
        size-=numRemoved;
        return;
      }
    }
    super.clear();
  }
  private boolean uncheckedRemoveLastMatch(Node curr,int val){
    final var head=this.head;
    if(curr.val==val){
      if(curr==head){
        staticInit(this,null);
      }else{
        staticEraseTail(this,curr);
      }
    }else{
      Node next;
      do{
        if(curr==head){ return false; }
      }while((curr=(next=curr).prev).val!=val);
      if(curr==head){
        staticSetHead(this,next);
      }else{
        joinNodes(curr.prev,next);
      }
    }
    --size;
    return true;
  }
}
