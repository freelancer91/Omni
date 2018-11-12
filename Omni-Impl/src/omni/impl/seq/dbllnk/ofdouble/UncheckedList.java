package omni.impl.seq.dbllnk.ofdouble;
import java.util.function.DoublePredicate;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.util.TypeUtil;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfDouble{
  static int collapseBodyHelper(Node prev,Node next,DoublePredicate filter){
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
    super.addLast(TypeUtil.castToDouble(val));
    return true;
  }
  @Override public boolean add(char val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(double val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(Double val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(float val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(int val){
    super.addLast(val);
    return true;
  }
  @Override public void add(int index,double val){
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
  @Override public boolean add(long val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(short val){
    super.addLast(val);
    return true;
  }
  @Override public void addFirst(Double val){
    super.addFirst(val);
  }
  @Override public void addLast(Double val){
    super.addLast(val);
  }
  @Override public omni.api.OmniIterator.OfDouble descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public double doubleElement(){
    return head.val;
  }
  @Override public Double element(){
    return head.val;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Double getFirst(){
    return head.val;
  }
  @Override public double getFirstDouble(){
    return head.val;
  }
  @Override public Double getLast(){
    return tail.val;
  }
  @Override public double getLastDouble(){
    return tail.val;
  }
  @Override public omni.api.OmniIterator.OfDouble iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfDouble listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfDouble listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(double val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(Double val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(double val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerFirst(Double val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerLast(double val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerLast(Double val){
    super.addLast(val);
    return true;
  }
  @Override public Double poll(){
    return super.pollFirst();
  }
  @Override public double pollDouble(){
    return super.pollFirstDouble();
  }
  @Override public Double pollLast(){
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
  @Override public Double pop(){
    return super.removeFirstDouble();
  }
  @Override public double popDouble(){
    return super.removeFirstDouble();
  }
  @Override public void push(double val){
    super.addFirst(val);
  }
  @Override public void push(Double val){
    super.addFirst(val);
  }
  @Override public Double remove(){
    return super.removeFirstDouble();
  }
  @Override public double removeDouble(){
    return super.removeFirstDouble();
  }
  @Override public double removeDoubleAt(int index){
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
  @Override public Double removeFirst(){
    return super.removeFirstDouble();
  }
  @Override public Double removeLast(){
    return super.removeLastDouble();
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final Node tail;
    if((tail=this.tail)!=null){
      if(val){ return uncheckedRemoveLastDblBits(tail,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedRemoveLastDbl0(tail);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(double val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(float val){
    final Node tail;
    if((tail=this.tail)!=null){
      if(val==val){ return uncheckedRemoveLastDblBits(tail,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveLastDblNaN(tail);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(int val){
    final Node tail;
    if((tail=this.tail)!=null){
      if(val!=0){ return uncheckedRemoveLastDblBits(tail,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveLastDbl0(tail);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(long val){
    final Node tail;
    if((tail=this.tail)!=null){
      if(val!=0){ return TypeUtil.checkCastToDouble(val)
          &&uncheckedRemoveLastDblBits(tail,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveLastDbl0(tail);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Object val){
    final Node tail;
    return (tail=this.tail)!=null&&val instanceof Double&&uncheckedRemoveLastMatch(tail,(double)val);
  }
  @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override boolean collapseBody(Node head,Node tail,DoublePredicate filter){
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
  @Override void collapseTail(Node head,Node tail,DoublePredicate filter){
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
  @Override void findNewHead(Node head,DoublePredicate filter){
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
  @Override boolean uncheckedRemoveFirstDbl0(Node curr){
    final var tail=this.tail;
    if(curr.val==0){
      if(curr==tail){
        staticInit(this,null);
      }else{
        staticEraseHead(this,curr);
      }
    }else{
      Node prev;
      do{
        if(curr==tail){ return false; }
      }while((curr=(prev=curr).next).val!=0);
      if(curr==tail){
        staticSetTail(this,prev);
      }else{
        joinNodes(prev,curr.next);
      }
    }
    --size;
    return true;
  }
  @Override boolean uncheckedRemoveFirstDblBits(Node curr,long dblBits){
    final var tail=this.tail;
    if(Double.doubleToRawLongBits(curr.val)==dblBits){
      if(curr==tail){
        staticInit(this,null);
      }else{
        staticEraseHead(this,curr);
      }
    }else{
      Node prev;
      do{
        if(curr==tail){ return false; }
      }while(Double.doubleToRawLongBits((curr=(prev=curr).next).val)!=dblBits);
      if(curr==tail){
        staticSetTail(this,prev);
      }else{
        joinNodes(prev,curr.next);
      }
    }
    --size;
    return true;
  }
  @Override boolean uncheckedRemoveFirstDblNaN(Node curr){
    final var tail=this.tail;
    if(Double.isNaN(curr.val)){
      if(curr==tail){
        staticInit(this,null);
      }else{
        staticEraseHead(this,curr);
      }
    }else{
      Node prev;
      do{
        if(curr==tail){ return false; }
      }while(!Double.isNaN((curr=(prev=curr).next).val));
      if(curr==tail){
        staticSetTail(this,prev);
      }else{
        joinNodes(prev,curr.next);
      }
    }
    --size;
    return true;
  }
  private void collapseHead(Node headCandidate,Node tail,DoublePredicate filter){
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
  private void collapseHeadAndTail(Node head,Node tail,DoublePredicate filter){
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
  private boolean uncheckedRemoveLastDbl0(Node curr){
    final var head=this.head;
    if(curr.val==0){
      if(curr==head){
        staticInit(this,null);
      }else{
        staticEraseTail(this,curr);
      }
    }else{
      Node next;
      do{
        if(curr==head){ return false; }
      }while((curr=(next=curr).prev).val!=0);
      if(curr==head){
        staticSetHead(this,next);
      }else{
        joinNodes(curr.prev,next);
      }
    }
    --size;
    return true;
  }
  private boolean uncheckedRemoveLastDblBits(Node curr,long dblBits){
    final var head=this.head;
    if(Double.doubleToRawLongBits(curr.val)==dblBits){
      if(curr==head){
        staticInit(this,null);
      }else{
        staticEraseTail(this,curr);
      }
    }else{
      Node next;
      do{
        if(curr==head){ return false; }
      }while(Double.doubleToRawLongBits((curr=(next=curr).prev).val)!=dblBits);
      if(curr==head){
        staticSetHead(this,next);
      }else{
        joinNodes(curr.prev,next);
      }
    }
    --size;
    return true;
  }
  private boolean uncheckedRemoveLastDblNaN(Node curr){
    final var head=this.head;
    if(Double.isNaN(curr.val)){
      if(curr==head){
        staticInit(this,null);
      }else{
        staticEraseTail(this,curr);
      }
    }else{
      Node next;
      do{
        if(curr==head){ return false; }
      }while(!Double.isNaN((curr=(next=curr).prev).val));
      if(curr==head){
        staticSetHead(this,next);
      }else{
        joinNodes(curr.prev,next);
      }
    }
    --size;
    return true;
  }
  private boolean uncheckedRemoveLastMatch(Node curr,double val){
    if(val==val){ return uncheckedRemoveLastDblBits(curr,Double.doubleToRawLongBits(val)); }
    return uncheckedRemoveLastDblNaN(curr);
  }
}
