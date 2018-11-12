package omni.impl.seq.dbllnk.ofshort;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.ShortPredicate;
import omni.util.TypeUtil;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfShort{
  static int collapseBodyHelper(Node prev,Node next,ShortPredicate filter){
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
  @Override public void add(int index,short val){
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
  @Override public boolean add(short val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(Short val){
    super.addLast(val);
    return true;
  }
  @Override public void addFirst(Short val){
    super.addFirst(val);
  }
  @Override public void addLast(Short val){
    super.addLast(val);
  }
  @Override public omni.api.OmniIterator.OfShort descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Short element(){
    return head.val;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Short getFirst(){
    return head.val;
  }
  @Override public short getFirstShort(){
    return head.val;
  }
  @Override public Short getLast(){
    return tail.val;
  }
  @Override public short getLastShort(){
    return tail.val;
  }
  @Override public omni.api.OmniIterator.OfShort iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfShort listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfShort listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(short val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(Short val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(short val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerFirst(Short val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerLast(short val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerLast(Short val){
    super.addLast(val);
    return true;
  }
  @Override public Short poll(){
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
  @Override public Short pollLast(){
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
  @Override public short pollLastShort(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return Short.MIN_VALUE;
  }
  @Override public long pollLong(){
    return super.pollFirstLong();
  }
  @Override public short pollShort(){
    return super.pollFirstShort();
  }
  @Override public Short pop(){
    return super.removeFirstShort();
  }
  @Override public short popShort(){
    return super.removeFirstShort();
  }
  @Override public void push(short val){
    super.addFirst(val);
  }
  @Override public void push(Short val){
    super.addFirst(val);
  }
  @Override public Short remove(){
    return super.removeFirstShort();
  }
  @Override public Short removeFirst(){
    return super.removeFirstShort();
  }
  @Override public Short removeLast(){
    return super.removeLastShort();
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,TypeUtil.castToByte(val));
  }
  @Override public boolean removeLastOccurrence(char val){
    final Node tail;
    return val<=Short.MAX_VALUE&&(tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(double val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(short)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(float val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(short)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(int val){
    final Node tail;
    return (tail=this.tail)!=null&&val==(short)val&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(long val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(short)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(Object val){
    final Node tail;
    return (tail=this.tail)!=null&&val instanceof Short&&uncheckedRemoveLastMatch(tail,(short)val);
  }
  @Override public boolean removeLastOccurrence(short val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public short removeShort(){
    return super.removeFirstShort();
  }
  @Override public short removeShortAt(int index){
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
  @Override public short shortElement(){
    return head.val;
  }
  @Override public OmniList.OfShort subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override boolean collapseBody(Node head,Node tail,ShortPredicate filter){
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
  @Override void collapseTail(Node head,Node tail,ShortPredicate filter){
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
  @Override void findNewHead(Node head,ShortPredicate filter){
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
  private void collapseHead(Node headCandidate,Node tail,ShortPredicate filter){
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
  private void collapseHeadAndTail(Node head,Node tail,ShortPredicate filter){
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
