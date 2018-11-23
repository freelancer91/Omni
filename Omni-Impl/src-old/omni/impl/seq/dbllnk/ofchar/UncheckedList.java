package omni.impl.seq.dbllnk.ofchar;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.CharPredicate;
import omni.util.TypeUtil;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfChar{
  static int collapseBodyHelper(Node prev,Node next,CharPredicate filter){
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
    super.addLast(TypeUtil.castToChar(val));
    return true;
  }
  @Override public boolean add(char val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(Character val){
    super.addLast(val);
    return true;
  }
  @Override public void add(int index,char val){
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
  @Override public void addFirst(Character val){
    super.addFirst(val);
  }
  @Override public void addLast(Character val){
    super.addLast(val);
  }
  @Override public char charElement(){
    return head.val;
  }
  @Override public omni.api.OmniIterator.OfChar descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Character element(){
    return head.val;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Character getFirst(){
    return head.val;
  }
  @Override public char getFirstChar(){
    return head.val;
  }
  @Override public Character getLast(){
    return tail.val;
  }
  @Override public char getLastChar(){
    return tail.val;
  }
  @Override public omni.api.OmniIterator.OfChar iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfChar listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfChar listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(char val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(Character val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(char val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerFirst(Character val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerLast(char val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerLast(Character val){
    super.addLast(val);
    return true;
  }
  @Override public Character poll(){
    return super.pollFirst();
  }
  @Override public char pollChar(){
    return super.pollFirstChar();
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
  @Override public Character pollLast(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return null;
  }
  @Override public char pollLastChar(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return Character.MIN_VALUE;
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
  @Override public Character pop(){
    return super.removeFirstChar();
  }
  @Override public char popChar(){
    return super.removeFirstChar();
  }
  @Override public void push(char val){
    super.addFirst(val);
  }
  @Override public void push(Character val){
    super.addFirst(val);
  }
  @Override public Character remove(){
    return super.removeFirstChar();
  }
  @Override public char removeChar(){
    return super.removeFirstChar();
  }
  @Override public char removeCharAt(int index){
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
  @Override public Character removeFirst(){
    return super.removeFirstChar();
  }
  @Override public Character removeLast(){
    return super.removeLastChar();
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,TypeUtil.castToChar(val));
  }
  @Override public boolean removeLastOccurrence(char val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(double val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(char)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(float val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(char)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(int val){
    final Node tail;
    return (tail=this.tail)!=null&&val==(char)val&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(long val){
    final Node tail;
    int v;
    return (tail=this.tail)!=null&&val==(v=(char)val)&&uncheckedRemoveLastMatch(tail,v);
  }
  @Override public boolean removeLastOccurrence(Object val){
    final Node tail;
    return (tail=this.tail)!=null&&val instanceof Character&&uncheckedRemoveLastMatch(tail,(char)val);
  }
  @Override public boolean removeLastOccurrence(short val){
    final Node tail;
    return val>=0&&(tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override boolean collapseBody(Node head,Node tail,CharPredicate filter){
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
  @Override void collapseTail(Node head,Node tail,CharPredicate filter){
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
  @Override void findNewHead(Node head,CharPredicate filter){
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
  private void collapseHead(Node headCandidate,Node tail,CharPredicate filter){
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
  private void collapseHeadAndTail(Node head,Node tail,CharPredicate filter){
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
