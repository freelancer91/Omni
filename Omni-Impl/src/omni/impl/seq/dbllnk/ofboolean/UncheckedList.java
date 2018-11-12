package omni.impl.seq.dbllnk.ofboolean;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.BooleanPredicate;
import omni.util.TypeUtil;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniDeque.OfBoolean{
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
    super.addLast(val);
    return true;
  }
  @Override public boolean add(Boolean val){
    super.addLast(val);
    return true;
  }
  @Override public void add(int index,boolean val){
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
  @Override public void addFirst(Boolean val){
    super.addFirst(val);
  }
  @Override public void addLast(Boolean val){
    super.addLast(val);
  }
  @Override public boolean booleanElement(){
    return head.val;
  }
  @Override public omni.api.OmniIterator.OfBoolean descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Boolean element(){
    return head.val;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public Boolean getFirst(){
    return head.val;
  }
  @Override public boolean getFirstBoolean(){
    return head.val;
  }
  @Override public Boolean getLast(){
    return tail.val;
  }
  @Override public boolean getLastBoolean(){
    return tail.val;
  }
  @Override public omni.api.OmniIterator.OfBoolean iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfBoolean listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public omni.api.OmniListIterator.OfBoolean listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean offer(boolean val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offer(Boolean val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(boolean val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerFirst(Boolean val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerLast(boolean val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerLast(Boolean val){
    super.addLast(val);
    return true;
  }
  @Override public Boolean poll(){
    return super.pollFirst();
  }
  @Override public boolean pollBoolean(){
    return super.pollFirstBoolean();
  }
  @Override public byte pollByte(){
    return super.pollFirstByte();
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
  @Override public Boolean pollLast(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return null;
  }
  @Override public boolean pollLastBoolean(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return false;
  }
  @Override public byte pollLastByte(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return TypeUtil.castToByte(tail.val);
    }
    return Byte.MIN_VALUE;
  }
  @Override public char pollLastChar(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return TypeUtil.castToChar(tail.val);
    }
    return Character.MIN_VALUE;
  }
  @Override public double pollLastDouble(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return TypeUtil.castToDouble(tail.val);
    }
    return Double.NaN;
  }
  @Override public float pollLastFloat(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return TypeUtil.castToFloat(tail.val);
    }
    return Float.NaN;
  }
  @Override public int pollLastInt(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return TypeUtil.castToByte(tail.val);
    }
    return Integer.MIN_VALUE;
  }
  @Override public long pollLastLong(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return TypeUtil.castToLong(tail.val);
    }
    return Long.MIN_VALUE;
  }
  @Override public short pollLastShort(){
    Node tail;
    if((tail=this.tail)!=null){
      --size;
      staticEraseTail(this,tail);
      return TypeUtil.castToByte(tail.val);
    }
    return Short.MIN_VALUE;
  }
  @Override public long pollLong(){
    return super.pollFirstLong();
  }
  @Override public short pollShort(){
    return super.pollFirstShort();
  }
  @Override public Boolean pop(){
    return super.removeFirstBoolean();
  }
  @Override public boolean popBoolean(){
    return super.removeFirstBoolean();
  }
  @Override public void push(boolean val){
    super.addFirst(val);
  }
  @Override public void push(Boolean val){
    super.addFirst(val);
  }
  @Override public Boolean remove(){
    return super.removeFirstBoolean();
  }
  @Override public boolean removeBoolean(){
    return super.removeFirstBoolean();
  }
  @Override public boolean removeBooleanAt(int index){
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
  @Override public Boolean removeFirst(){
    return super.removeFirstBoolean();
  }
  @Override public Boolean removeLast(){
    return super.removeLastBoolean();
  }
  @Override public boolean removeLastOccurrence(boolean val){
    Node tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
  }
  @Override public boolean removeLastOccurrence(double val){
    Node tail;
    if((tail=this.tail)!=null){
      boolean v;
      long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
        v=false;
      }else if(val==TypeUtil.DBL_TRUE_BITS){
        v=true;
      }else{
        return false;
      }
      return uncheckedRemoveLastMatch(tail,v);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(float val){
    Node tail;
    if((tail=this.tail)!=null){
      boolean v;
      switch(Float.floatToRawIntBits(val)){
      default:
        return false;
      case 0:
      case Integer.MIN_VALUE:
        v=false;
        break;
      case TypeUtil.FLT_TRUE_BITS:
        v=true;
      }
      return uncheckedRemoveLastMatch(tail,v);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(int val){
    Node tail;
    if((tail=this.tail)!=null){
      boolean v;
      switch(val){
      default:
        return false;
      case 0:
        v=false;
        break;
      case 1:
        v=true;
      }
      return uncheckedRemoveLastMatch(tail,v);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(long val){
    Node tail;
    if((tail=this.tail)!=null){
      boolean v;
      if(val==0){
        v=false;
      }else if(val==1){
        v=true;
      }else{
        return false;
      }
      return uncheckedRemoveLastMatch(tail,v);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(Object val){
    Node tail;
    return (tail=this.tail)!=null&&val instanceof Boolean&&uncheckedRemoveLastMatch(tail,(boolean)val);
  }
  @Override public OmniList.OfBoolean subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override boolean uncheckedRemoveFirstMatch(Node curr,boolean val){
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
  @Override boolean uncheckedRemoveIf(Node head,BooleanPredicate filter){
    // TODO Auto-generated method stub
    return false;
  }
  private boolean uncheckedRemoveLastMatch(Node curr,boolean val){
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
