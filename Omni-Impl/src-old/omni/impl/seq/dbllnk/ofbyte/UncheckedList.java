package omni.impl.seq.dbllnk.ofbyte;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.BytePredicate;
import omni.util.TypeUtil;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfByte{
  static int collapseBodyHelper(Node prev,Node next,BytePredicate filter){
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
  @Override public boolean add(byte val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(Byte val){
    super.addLast(val);
    return true;
  }
  @Override public void add(int index,byte val){
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
  @Override public void addFirst(Byte val){
    super.addFirst(val);
  }
  @Override public void addLast(Byte val){
    super.addLast(val);
  }
  @Override public byte byteElement(){
    return head.val;
  }
  @Override public omni.api.OmniIterator.OfByte descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Byte element(){
    return head.val;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Byte getFirst(){
    return head.val;
  }
  @Override public byte getFirstByte(){
    return head.val;
  }
  @Override public Byte getLast(){
    return tail.val;
  }
  @Override public byte getLastByte(){
    return tail.val;
  }
  @Override public omni.api.OmniIterator.OfByte iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfByte listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfByte listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(byte val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(Byte val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(byte val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerFirst(Byte val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerLast(byte val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerLast(Byte val){
    super.addLast(val);
    return true;
  }
  @Override public Byte poll(){
    return super.pollFirst();
  }
  @Override public byte pollByte(){
    return super.pollFirstByte();
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
  @Override public Byte pollLast(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return null;
  }
  @Override public byte pollLastByte(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return Byte.MIN_VALUE;
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
  @Override public Byte pop(){
    return super.removeFirstByte();
  }
  @Override public byte popByte(){
    return super.removeFirstByte();
  }
  @Override public void push(byte val){
    super.addFirst(val);
  }
  @Override public void push(Byte val){
    super.addFirst(val);
  }
  @Override public Byte remove(){
    return super.removeFirstByte();
  }
  @Override public byte removeByte(){
    return super.removeFirstByte();
  }
  @Override public byte removeByteAt(int index){
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
  @Override public Byte removeFirst(){
    return super.removeFirstByte();
  }
  @Override public Byte removeLast(){
    return super.removeLastByte();
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,TypeUtil.castToByte(val));
  }
  @Override public boolean removeLastOccurrence(byte val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(char val){
    final Node tail;
    return val<=Byte.MAX_VALUE&&(tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(double val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(byte)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(float val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(byte)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(int val){
    final Node tail;
    return (tail=this.tail)!=null&&val==(byte)val&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(long val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(byte)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(Object val){
    final Node tail;
    return (tail=this.tail)!=null&&val instanceof Byte&&uncheckedRemoveLastMatch(tail,(byte)val);
  }
  @Override public OmniList.OfByte subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override boolean collapseBody(Node head,Node tail,BytePredicate filter){
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
  @Override void collapseTail(Node head,Node tail,BytePredicate filter){
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
  @Override void findNewHead(Node head,BytePredicate filter){
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
  private void collapseHead(Node headCandidate,Node tail,BytePredicate filter){
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
  private void collapseHeadAndTail(Node head,Node tail,BytePredicate filter){
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
