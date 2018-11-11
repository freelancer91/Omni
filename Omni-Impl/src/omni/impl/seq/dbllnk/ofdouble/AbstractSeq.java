package omni.impl.seq.dbllnk.ofdouble;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.DoubleComparator;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractDoubleList;
import omni.util.HashUtils;
import omni.util.OmniArray;
import omni.util.TypeUtil;
abstract class AbstractSeq extends AbstractDoubleList implements OmniList.OfDouble{
  transient Node head;
  transient Node tail;
  static Node iterateForward(Node curr,int dist){
    if(dist!=0){ return uncheckedIterateForward(curr,dist); }
    return curr;
  }
  static Node iterateReverse(Node curr,int dist){
    if(dist!=0){ return uncheckedIterateReverse(curr,dist); }
    return curr;
  }
  static void joinNodes(Node prev,Node next){
    prev.next=next;
    next.prev=prev;
  }
  static void staticClear(AbstractSeq seq){
    seq.size=0;
    staticInit(seq,null);
  }
  static void staticEraseHead(AbstractSeq seq,Node oldHead){
    seq.head=oldHead=oldHead.next;
    oldHead.prev=null;
  }
  static void staticEraseTail(AbstractSeq seq,Node oldTail){
    seq.tail=oldTail=oldTail.prev;
    oldTail.next=null;
  }
  static Node staticExtractNode(AbstractSeq seq,int headDist,int tailDist){
    Node node;
    if(headDist<=tailDist){
      joinNodes(node=iterateForward(seq.head,headDist-1),(node=node.next).next);
    }else{
      Node after;
      joinNodes((node=(after=iterateReverse(seq.tail,tailDist-1)).prev).prev,after);
    }
    return node;
  }
  static Node staticGetNode(AbstractSeq seq,int headDist,int tailDist){
    if(headDist<=tailDist){ return iterateForward(seq.head,headDist); }
    return iterateReverse(seq.tail,tailDist-1);
  }
  static void staticInit(AbstractSeq seq,Node node){
    seq.head=node;
    seq.tail=node;
  }
  static void staticInsertNode(AbstractSeq seq,int headDist,double val,int tailDist){
    Node before,after;
    if(headDist<=tailDist){
      after=(before=iterateForward(seq.head,headDist-1)).next;
    }else{
      before=(after=iterateReverse(seq.tail,tailDist-1)).prev;
    }
    new Node(before,val,after);
  }
  static void staticSetHead(AbstractSeq seq,Node newHead){
    seq.head=newHead;
    newHead.prev=null;
  }
  static void staticSetTail(AbstractSeq seq,Node newTail){
    seq.tail=newTail;
    newTail.next=null;
  }
  static void subSeqInsertHelper(AbstractSeq root,Node newNode,int index){
    int tailDist;
    Node before,after;
    if(index<=(tailDist=root.size-index)){
      after=(before=iterateForward(root.head,index-1)).next;
    }else{
      before=(after=iterateReverse(root.tail,tailDist-1)).prev;
    }
    joinNodes(before,newNode);
    joinNodes(newNode,after);
  }
  static void uncheckedForEachForward(Node begin,Node end,DoubleConsumer action){
    for(;;){
      action.accept(begin.val);
      if(begin==end){ return; }
      begin=begin.next;
    }
  }
  static void uncheckedForEachReverse(Node curr,DoubleConsumer action){
    do{
      action.accept(curr.val);
    }while((curr=curr.prev)!=null);
  }
  static int uncheckedForwardHashCode(Node begin,Node end){
    int hash=31+HashUtils.hashDouble(begin.val);
    while(begin!=end){
      hash=hash*31+HashUtils.hashDouble((begin=begin.next).val);
    }
    return hash;
  }
  static void uncheckedForwardToString(Node begin,Node end,StringBuilder builder){
    for(builder.append(begin.val);begin!=end;builder.append(',').append(' ').append((begin=begin.next).val)){}
  }
  static Node uncheckedIterateForward(Node curr,int dist){
    do{
      curr=curr.next;
    }while(--dist!=0);
    return curr;
  }
  static Node uncheckedIterateReverse(Node curr,int dist){
    do{
      curr=curr.prev;
    }while(--dist!=0);
    return curr;
  }
  static void uncheckedReplaceAll(Node begin,Node end,DoubleUnaryOperator operator){
    for(;;){
      begin.val=operator.applyAsDouble(begin.val);
      if(begin==end){ return; }
      begin=begin.next;
    }
  }
  static void uncheckedReverseSort(Node begin,int size,Node end){
    // TODO
  }
  static void uncheckedSort(Node begin,int size,Node end){
    // TODO
  }
  static void uncheckedSort(Node begin,int size,Node end,DoubleComparator sorter){
    // TODO
  }
  private static void uncheckedCopyForward(Node src,double[] dst,int dstOffset,int length){
    for(;;){
      dst[dstOffset]=src.val;
      if(--length==0){ return; }
      ++dstOffset;
      src=src.next;
    }
  }
  private static void uncheckedCopyForward(Node src,Double[] dst,int dstOffset,int length){
    for(;;){
      dst[dstOffset]=src.val;
      if(--length==0){ return; }
      ++dstOffset;
      src=src.next;
    }
  }
  private static void uncheckedCopyForward(Node src,Object[] dst,int dstOffset,int length){
    for(;;){
      dst[dstOffset]=src.val;
      if(--length==0){ return; }
      ++dstOffset;
      src=src.next;
    }
  }
  AbstractSeq(){
    super();
  }
  AbstractSeq(Node onlyNode){
    super(1);
    staticInit(this,onlyNode);
  }
  AbstractSeq(Node head,int size,Node tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  public void addFirst(double val){
    Node head;
    if((head=this.head)!=null){
      this.head=new Node(val,head);
    }else{
      staticInit(this,new Node(val));
    }
    ++size;
  }
  public void addLast(double val){
    Node tail;
    if((tail=this.tail)!=null){
      this.tail=new Node(tail,val);
    }else{
      staticInit(this,new Node(val));
    }
    ++size;
  }
  @Override public void clear(){
    staticClear(this);
  }
  @Override public Object clone(){
    Node newHead,newTail;
    int size;
    if((size=this.size)!=0){
      Node oldHead,oldTail;
      for(newHead=new Node((oldHead=head).val),newTail=newHead,oldTail=tail;oldHead!=oldTail;
          newTail=new Node(newTail,(oldHead=oldHead.next).val)){}
    }else{
      newHead=null;
      newTail=null;
    }
    return new UncheckedList(newHead,size,newTail);
  }
  @Override public boolean contains(boolean val){
    Node head;
    if((head=this.head)!=null){
      if(val){ return uncheckedAnyMatchesDblBits(head,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedAnyMatchesDbl0(head);
    }
    return false;
  }
  @Override public boolean contains(byte val){
    Node head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,val);
  }
  @Override public boolean contains(char val){
    Node head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,val);
  }
  @Override public boolean contains(double val){
    Node head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,val);
  }
  @Override public boolean contains(float val){
    Node head;
    if((head=this.head)!=null){
      if(val==val){ return uncheckedAnyMatchesDblBits(head,Double.doubleToRawLongBits(val)); }
      return uncheckedAnyMatchesDblNaN(head);
    }
    return false;
  }
  @Override public boolean contains(int val){
    Node head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,val);
  }
  @Override public boolean contains(long val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return TypeUtil.checkCastToDouble(val)
          &&uncheckedAnyMatchesDblBits(head,Double.doubleToRawLongBits(val)); }
      return uncheckedAnyMatchesDbl0(head);
    }
    return false;
  }
  @Override public boolean contains(Object val){
    Node head;
    return (head=this.head)!=null&&val instanceof Double&&uncheckedAnyMatches(head,(double)val);
  }
  @Override public boolean contains(short val){
    Node head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,val);
  }
  @Override public void forEach(Consumer<? super Double> action){
    Node head;
    if((head=this.head)!=null){
      uncheckedForEachForward(head,tail,action::accept);
    }
  }
  @Override public void forEach(DoubleConsumer action){
    Node head;
    if((head=this.head)!=null){
      uncheckedForEachForward(head,tail,action);
    }
  }
  @Override public double getDouble(int index){
    return staticGetNode(this,index,size-index).val;
  }
  @Override public int hashCode(){
    Node head;
    if((head=this.head)!=null){ return uncheckedForwardHashCode(head,tail); }
    return -1;
  }
  @Override public int indexOf(boolean val){
    Node head;
    if((head=this.head)!=null){
      if(val){ return uncheckedIndexOfDblBits(head,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedIndexOfDbl0(head);
    }
    return -1;
  }
  @Override public int indexOf(double val){
    Node head;
    if((head=this.head)!=null){ return uncheckedIndexOfMatch(head,val); }
    return -1;
  }
  @Override public int indexOf(float val){
    Node head;
    if((head=this.head)!=null){
      if(val==val){ return uncheckedIndexOfDblBits(head,Double.doubleToRawLongBits(val)); }
      return uncheckedIndexOfDblNaN(head);
    }
    return -1;
  }
  @Override public int indexOf(int val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return uncheckedIndexOfDblBits(head,Double.doubleToRawLongBits(val)); }
      return uncheckedIndexOfDbl0(head);
    }
    return -1;
  }
  @Override public int indexOf(long val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){
        if(!TypeUtil.checkCastToDouble(val)){ return -1; }
        return uncheckedIndexOfDblBits(head,Double.doubleToRawLongBits(val));
      }
      return uncheckedIndexOfDbl0(head);
    }
    return -1;
  }
  @Override public int indexOf(Object val){
    Node head;
    if((head=this.head)!=null&&val instanceof Double){ return uncheckedIndexOfMatch(head,(double)val); }
    return -1;
  }
  public Double peek(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return null;
  }
  public double peekDouble(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Double.NaN;
  }
  public Double peekFirst(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return null;
  }
  public double peekFirstDouble(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Double.NaN;
  }
  public Double peekLast(){
    Node tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return null;
  }
  public double peekLastDouble(){
    Node tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return Double.NaN;
  }
  @Override public void put(int index,double val){
    staticGetNode(this,index,size-index).val=val;
  }
  @Override public boolean remove(Object val){
    Node head;
    return (head=this.head)!=null&&val instanceof Double&&uncheckedRemoveFirstMatch(head,(double)val);
  }
  public boolean removeFirstOccurrence(boolean val){
    Node head;
    if((head=this.head)!=null){
      if(val){ return uncheckedRemoveFirstDblBits(head,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedRemoveFirstDbl0(head);
    }
    return false;
  }
  public boolean removeFirstOccurrence(byte val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  public boolean removeFirstOccurrence(char val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  public boolean removeFirstOccurrence(double val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  public boolean removeFirstOccurrence(float val){
    Node head;
    if((head=this.head)!=null){
      if(val==val){ return uncheckedRemoveFirstDblBits(head,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveFirstDbl0(head);
    }
    return false;
  }
  public boolean removeFirstOccurrence(int val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  public boolean removeFirstOccurrence(long val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return TypeUtil.checkCastToDouble(val)
          &&uncheckedRemoveFirstDblBits(head,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveFirstDbl0(head);
    }
    return false;
  }
  public boolean removeFirstOccurrence(Object val){
    Node head;
    return (head=this.head)!=null&&val instanceof Double&&uncheckedRemoveFirstMatch(head,(double)val);
  }
  public boolean removeFirstOccurrence(short val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  @Override public boolean removeVal(boolean val){
    Node head;
    if((head=this.head)!=null){
      if(val){ return uncheckedRemoveFirstDblBits(head,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedRemoveFirstDbl0(head);
    }
    return false;
  }
  @Override public boolean removeVal(byte val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  @Override public boolean removeVal(char val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  @Override public boolean removeVal(double val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  @Override public boolean removeVal(float val){
    Node head;
    if((head=this.head)!=null){
      if(val==val){ return uncheckedRemoveFirstDblBits(head,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveFirstDbl0(head);
    }
    return false;
  }
  @Override public boolean removeVal(int val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  @Override public boolean removeVal(long val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return TypeUtil.checkCastToDouble(val)
          &&uncheckedRemoveFirstDblBits(head,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveFirstDbl0(head);
    }
    return false;
  }
  @Override public boolean removeVal(short val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  @Override public void replaceAll(DoubleUnaryOperator operator){
    Node head;
    if((head=this.head)!=null){
      uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Double> operator){
    Node head;
    if((head=this.head)!=null){
      uncheckedReplaceAll(head,tail,operator::apply);
    }
  }
  @Override public void reverseSort(){
    int size;
    if((size=this.size)>1){
      uncheckedReverseSort(head,size,tail);
    }
  }
  public int search(boolean val){
    Node head;
    if((head=this.head)!=null){
      if(val){ return uncheckedSearchDblBits(head,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedSearchDbl0(head);
    }
    return -1;
  }
  public int search(double val){
    Node head;
    if((head=this.head)!=null){ return uncheckedSearch(head,val); }
    return -1;
  }
  public int search(float val){
    Node head;
    if((head=this.head)!=null){
      if(val==val){ return uncheckedSearchDblBits(head,Double.doubleToRawLongBits(val)); }
      return uncheckedSearchDblNaN(head);
    }
    return -1;
  }
  public int search(int val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return uncheckedSearchDblBits(head,Double.doubleToRawLongBits(val)); }
      return uncheckedSearchDbl0(head);
    }
    return -1;
  }
  public int search(long val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){
        if(!TypeUtil.checkCastToDouble(val)){ return -1; }
        return uncheckedSearchDblBits(head,Double.doubleToRawLongBits(val));
      }
      return uncheckedSearchDbl0(head);
    }
    return -1;
  }
  public int search(Object val){
    Node head;
    if((head=this.head)!=null&&val instanceof Double){ return uncheckedSearch(head,(double)val); }
    return -1;
  }
  @Override public double set(int index,double val){
    Node node;
    final var oldVal=(node=staticGetNode(this,index,size-index)).val;
    node.val=val;
    return oldVal;
  }
  @Override public void sort(){
    int size;
    if((size=this.size)>1){
      uncheckedSort(head,size,tail);
    }
  }
  @Override public void sort(Comparator<? super Double> sorter){
    int size;
    if((size=this.size)>1){
      uncheckedSort(head,size,tail,sorter::compare);
    }
  }
  @Override public void sort(DoubleComparator sorter){
    int size;
    if((size=this.size)>1){
      uncheckedSort(head,size,tail,sorter);
    }
  }
  @Override public Double[] toArray(){
    int size;
    if((size=this.size)!=0){
      Double[] dst;
      uncheckedCopyForward(head,dst=new Double[size],0,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    int size;
    final T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      uncheckedCopyForward(head,arr,0,size);
    }
    return arr;
  }
  @Override public <T> T[] toArray(T[] dst){
    int size;
    if((size=this.size)!=0){
      uncheckedCopyForward(head,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size)!=0){
      double[] dst;
      uncheckedCopyForward(head,dst=new double[size],0,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public String toString(){
    Node head;
    if((head=this.head)!=null){
      StringBuilder builder;
      uncheckedForwardToString(head,tail,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override protected int uncheckedLastIndexOfDbl0(int size){
    for(var tail=this.tail;tail.val!=0&&--size!=0;tail=tail.prev){}
    return size-1;
  }
  @Override protected int uncheckedLastIndexOfDblBits(int size,long dblBits){
    for(var tail=this.tail;Double.doubleToRawLongBits(tail.val)!=dblBits&&--size!=0;tail=tail.prev){}
    return size-1;
  }
  @Override protected int uncheckedLastIndexOfDblNaN(int size){
    for(var tail=this.tail;!Double.isNaN(tail.val)&&--size!=0;tail=tail.prev){}
    return size-1;
  }
  Node getItrNode(int headDist,int tailDist){
    if(tailDist==0){ return null; }
    return staticGetNode(this,headDist,tailDist);
  }
  abstract boolean uncheckedRemoveFirstDbl0(Node head);
  abstract boolean uncheckedRemoveFirstDblBits(Node head,long dblBits);
  abstract boolean uncheckedRemoveFirstDblNaN(Node head);
  private boolean uncheckedAnyMatches(Node head,double val){
    if(val==val){ return uncheckedAnyMatchesDblBits(head,Double.doubleToRawLongBits(val)); }
    return uncheckedAnyMatchesDblNaN(head);
  }
  private boolean uncheckedAnyMatches(Node head,int val){
    if(val!=0){ return uncheckedAnyMatchesDblBits(head,Double.doubleToRawLongBits(val)); }
    return uncheckedAnyMatchesDbl0(head);
  }
  private boolean uncheckedAnyMatchesDbl0(Node head){
    if(head.val!=0){
      final var tail=this.tail;
      do{
        if(head==tail){ return false; }
      }while((head=head.next).val!=0);
    }
    return true;
  }
  private boolean uncheckedAnyMatchesDblBits(Node head,long dblBits){
    if(Double.doubleToRawLongBits(head.val)!=dblBits){
      final var tail=this.tail;
      do{
        if(head==tail){ return false; }
      }while(Double.doubleToRawLongBits((head=head.next).val)!=dblBits);
    }
    return true;
  }
  private boolean uncheckedAnyMatchesDblNaN(Node head){
    if(!Double.isNaN(head.val)){
      final var tail=this.tail;
      do{
        if(head==tail){ return false; }
      }while(!Double.isNaN((head=head.next).val));
    }
    return true;
  }
  private int uncheckedIndexOfDbl0(Node head){
    int index=0;
    if(head.val!=0){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while((head=head.next).val!=0);
    }
    return index;
  }
  private int uncheckedIndexOfDblBits(Node head,long dblBits){
    int index=0;
    if(Double.doubleToRawLongBits(head.val)!=dblBits){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while(Double.doubleToRawLongBits((head=head.next).val)!=dblBits);
    }
    return index;
  }
  private int uncheckedIndexOfDblNaN(Node head){
    int index=0;
    if(!Double.isNaN(head.val)){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while(!Double.isNaN((head=head.next).val));
    }
    return index;
  }
  private int uncheckedIndexOfMatch(Node head,double val){
    if(val==val){ return uncheckedIndexOfDblBits(head,Double.doubleToRawLongBits(val)); }
    return uncheckedIndexOfDblNaN(head);
  }
  private boolean uncheckedRemoveFirstMatch(Node head,double val){
    if(val==val){ return uncheckedRemoveFirstDblBits(head,Double.doubleToRawLongBits(val)); }
    return uncheckedRemoveFirstDblNaN(head);
  }
  private boolean uncheckedRemoveFirstMatch(Node head,int val){
    if(val!=0){ return uncheckedRemoveFirstDblBits(head,Double.doubleToRawLongBits(val)); }
    return uncheckedRemoveFirstDbl0(head);
  }
  private int uncheckedSearch(Node head,double val){
    if(val==val){ return uncheckedSearchDblBits(head,Double.doubleToRawLongBits(val)); }
    return uncheckedSearchDblNaN(head);
  }
  private int uncheckedSearchDbl0(Node head){
    int index=1;
    if(head.val!=0){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while((head=head.next).val!=0);
    }
    return index;
  }
  private int uncheckedSearchDblBits(Node head,long dblBits){
    int index=1;
    if(Double.doubleToRawLongBits(head.val)!=dblBits){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while(Double.doubleToRawLongBits((head=head.next).val)!=dblBits);
    }
    return index;
  }
  private int uncheckedSearchDblNaN(Node head){
    int index=1;
    if(!Double.isNaN(head.val)){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while(!Double.isNaN((head=head.next).val));
    }
    return index;
  }
  static abstract class Checked extends AbstractSeq implements OmniDeque.OfDouble{
    transient int modCount;
    Checked(){
      super();
    }
    Checked(Node onlyNode){
      super(onlyNode);
    }
    Checked(Node head,int size,Node tail){
      super(head,size,tail);
    }
    @Override public void addFirst(double val){
      ++modCount;
      super.addFirst(val);
    }
    @Override public void addLast(double val){
      ++modCount;
      super.addLast(val);
    }
    @Override public double getFirstDouble(){
      Node head;
      if((head=this.head)!=null){ return head.val; }
      throw new NoSuchElementException();
    }
    @Override public double getLastDouble(){
      Node tail;
      if((tail=this.tail)!=null){ return tail.val; }
      throw new NoSuchElementException();
    }
    @Override public Double pollFirst(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return null;
    }
    @Override public double pollFirstDouble(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return Double.NaN;
    }
    @Override public double removeFirstDouble(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public double removeLastDouble(){
      Node tail;
      if((tail=this.tail)!=null){
        ++modCount;
        --size;
        staticEraseTail(this,tail);
        return tail.val;
      }
      throw new NoSuchElementException();
    }
    class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      public ModCountChecker(int expectedModCount){
        super(expectedModCount);
      }
      @Override protected int getActualModCount(){
        return modCount;
      }
    }
  }
  static abstract class Unchecked extends AbstractSeq{
    Unchecked(){
      super();
    }
    Unchecked(Node onlyNode){
      super(onlyNode);
    }
    Unchecked(Node head,int size,Node tail){
      super(head,size,tail);
    }
    @Override public boolean removeIf(DoublePredicate filter){
      Node head;
      return (head=this.head)!=null&&uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Double> filter){
      Node head;
      return (head=this.head)!=null&&uncheckedRemoveIf(head,filter::test);
    }
    abstract boolean collapseBody(Node head,Node tail,DoublePredicate filter);
    abstract void collapseTail(Node head,Node tail,DoublePredicate filter);
    abstract void findNewHead(Node head,DoublePredicate filter);
    private boolean uncheckedRemoveIf(Node head,DoublePredicate filter){
      if(filter.test(head.val)){
        findNewHead(head,filter);
        return true;
      }
      final Node tail;
      if(head!=(tail=this.tail)){
        if(filter.test(tail.val)){
          collapseTail(head,tail,filter);
          return true;
        }
        return collapseBody(head,tail,filter);
      }
      return false;
    }
  }
}
