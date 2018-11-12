package omni.impl.seq.dbllnk.offloat;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.FloatPredicate;
import omni.util.TypeUtil;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfFloat{
  static int collapseBodyHelper(Node prev,Node next,FloatPredicate filter){
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
    super.addLast(TypeUtil.castToFloat(val));
    return true;
  }
  @Override public boolean add(char val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(float val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(Float val){
    super.addLast(val);
    return true;
  }
  @Override public boolean add(int val){
    super.addLast(val);
    return true;
  }
  @Override public void add(int index,float val){
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
  @Override public void addFirst(Float val){
    super.addFirst(val);
  }
  @Override public void addLast(Float val){
    super.addLast(val);
  }
  @Override public omni.api.OmniIterator.OfFloat descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Float element(){
    return head.val;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public float floatElement(){
    return head.val;
  }
  @Override public Float getFirst(){
    return head.val;
  }
  @Override public float getFirstFloat(){
    return head.val;
  }
  @Override public Float getLast(){
    return tail.val;
  }
  @Override public float getLastFloat(){
    return tail.val;
  }
  @Override public omni.api.OmniIterator.OfFloat iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfFloat listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfFloat listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(float val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(Float val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(float val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerFirst(Float val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerLast(float val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerLast(Float val){
    super.addLast(val);
    return true;
  }
  @Override public Float poll(){
    return super.pollFirst();
  }
  @Override public double pollDouble(){
    return super.pollFirstDouble();
  }
  @Override public float pollFloat(){
    return super.pollFirstFloat();
  }
  @Override public Float pollLast(){
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
  @Override public Float pop(){
    return super.removeFirstFloat();
  }
  @Override public float popFloat(){
    return super.removeFirstFloat();
  }
  @Override public void push(float val){
    super.addFirst(val);
  }
  @Override public void push(Float val){
    super.addFirst(val);
  }
  @Override public Float remove(){
    return super.removeFirstFloat();
  }
  @Override public Float removeFirst(){
    return super.removeFirstFloat();
  }
  @Override public float removeFloat(){
    return super.removeFirstFloat();
  }
  @Override public float removeFloatAt(int index){
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
  @Override public Float removeLast(){
    return super.removeLastFloat();
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final Node tail;
    if((tail=this.tail)!=null){
      if(val){ return uncheckedRemoveLastFltBits(tail,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedRemoveLastFlt0(tail);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(char val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastRawInt(tail,val);
  }
  @Override public boolean removeLastOccurrence(double val){
    final Node tail;
    if((tail=this.tail)!=null){
      float v;
      if(val==(v=(float)val)){
        return uncheckedRemoveLastFltBits(tail,Float.floatToRawIntBits(v));
      }else if(v!=v){ return uncheckedRemoveLastFltNaN(tail); }
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(float val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(int val){
    final Node tail;
    if((tail=this.tail)!=null){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedRemoveLastFltBits(tail,Float.floatToRawIntBits(val)); }
      return uncheckedRemoveLastFlt0(tail);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(long val){
    final Node tail;
    if((tail=this.tail)!=null){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedRemoveLastFltBits(tail,Float.floatToRawIntBits(val)); }
      return uncheckedRemoveLastFlt0(tail);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Object val){
    final Node tail;
    return (tail=this.tail)!=null&&val instanceof Float&&uncheckedRemoveLastMatch(tail,(float)val);
  }
  @Override public boolean removeLastOccurrence(short val){
    final Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastRawInt(tail,val);
  }
  @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override boolean collapseBody(Node head,Node tail,FloatPredicate filter){
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
  @Override void collapseTail(Node head,Node tail,FloatPredicate filter){
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
  @Override void findNewHead(Node head,FloatPredicate filter){
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
  @Override boolean uncheckedRemoveFirstFlt0(Node curr){
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
  @Override boolean uncheckedRemoveFirstFltBits(Node curr,int fltBits){
    final var tail=this.tail;
    if(Float.floatToRawIntBits(curr.val)==fltBits){
      if(curr==tail){
        staticInit(this,null);
      }else{
        staticEraseHead(this,curr);
      }
    }else{
      Node prev;
      do{
        if(curr==tail){ return false; }
      }while(Float.floatToRawIntBits((curr=(prev=curr).next).val)!=fltBits);
      if(curr==tail){
        staticSetTail(this,prev);
      }else{
        joinNodes(prev,curr.next);
      }
    }
    --size;
    return true;
  }
  @Override boolean uncheckedRemoveFirstFltNaN(Node curr){
    final var tail=this.tail;
    if(Float.isNaN(curr.val)){
      if(curr==tail){
        staticInit(this,null);
      }else{
        staticEraseHead(this,curr);
      }
    }else{
      Node prev;
      do{
        if(curr==tail){ return false; }
      }while(!Float.isNaN((curr=(prev=curr).next).val));
      if(curr==tail){
        staticSetTail(this,prev);
      }else{
        joinNodes(prev,curr.next);
      }
    }
    --size;
    return true;
  }
  private void collapseHead(Node headCandidate,Node tail,FloatPredicate filter){
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
  private void collapseHeadAndTail(Node head,Node tail,FloatPredicate filter){
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
  private boolean uncheckedRemoveLastFlt0(Node curr){
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
  private boolean uncheckedRemoveLastFltBits(Node curr,int fltBits){
    final var head=this.head;
    if(Float.floatToRawIntBits(curr.val)==fltBits){
      if(curr==head){
        staticInit(this,null);
      }else{
        staticEraseTail(this,curr);
      }
    }else{
      Node next;
      do{
        if(curr==head){ return false; }
      }while(Float.floatToRawIntBits((curr=(next=curr).prev).val)!=fltBits);
      if(curr==head){
        staticSetHead(this,next);
      }else{
        joinNodes(curr.prev,next);
      }
    }
    --size;
    return true;
  }
  private boolean uncheckedRemoveLastFltNaN(Node curr){
    final var head=this.head;
    if(Float.isNaN(curr.val)){
      if(curr==head){
        staticInit(this,null);
      }else{
        staticEraseTail(this,curr);
      }
    }else{
      Node next;
      do{
        if(curr==head){ return false; }
      }while(!Float.isNaN((curr=(next=curr).prev).val));
      if(curr==head){
        staticSetHead(this,next);
      }else{
        joinNodes(curr.prev,next);
      }
    }
    --size;
    return true;
  }
  private boolean uncheckedRemoveLastMatch(Node curr,float val){
    if(val==val){ return uncheckedRemoveLastFltBits(curr,Float.floatToRawIntBits(val)); }
    return uncheckedRemoveLastFltNaN(curr);
  }
  private boolean uncheckedRemoveLastRawInt(Node curr,int val){
    if(val!=0){ return uncheckedRemoveLastFltBits(curr,Float.floatToRawIntBits(val)); }
    return uncheckedRemoveLastFlt0(curr);
  }
}
