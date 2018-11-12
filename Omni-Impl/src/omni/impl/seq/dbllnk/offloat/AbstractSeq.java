package omni.impl.seq.dbllnk.offloat;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.FloatComparator;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.function.FloatUnaryOperator;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractFloatList;
import omni.util.HashUtils;
import omni.util.OmniArray;
import omni.util.TypeUtil;
abstract class AbstractSeq extends AbstractFloatList implements OmniList.OfFloat{
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
  static void staticInsertNode(AbstractSeq seq,int headDist,float val,int tailDist){
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
  static void uncheckedForEachForward(Node begin,Node end,FloatConsumer action){
    for(;;){
      action.accept(begin.val);
      if(begin==end){ return; }
      begin=begin.next;
    }
  }
  static void uncheckedForEachReverse(Node curr,FloatConsumer action){
    do{
      action.accept(curr.val);
    }while((curr=curr.prev)!=null);
  }
  static int uncheckedForwardHashCode(Node begin,Node end){
    int hash=31+HashUtils.hashFloat(begin.val);
    while(begin!=end){
      hash=hash*31+HashUtils.hashFloat((begin=begin.next).val);
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
  static void uncheckedReplaceAll(Node begin,Node end,FloatUnaryOperator operator){
    for(;;){
      begin.val=operator.applyAsFloat(begin.val);
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
  static void uncheckedSort(Node begin,int size,Node end,FloatComparator sorter){
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
  private static void uncheckedCopyForward(Node src,float[] dst,int dstOffset,int length){
    for(;;){
      dst[dstOffset]=src.val;
      if(--length==0){ return; }
      ++dstOffset;
      src=src.next;
    }
  }
  private static void uncheckedCopyForward(Node src,Float[] dst,int dstOffset,int length){
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
  public void addFirst(float val){
    Node head;
    if((head=this.head)!=null){
      this.head=new Node(val,head);
    }else{
      staticInit(this,new Node(val));
    }
    ++size;
  }
  public void addLast(float val){
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
      if(val){ return uncheckedAnyMatchesFltBits(head,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedAnyMatchesFlt0(head);
    }
    return false;
  }
  @Override protected boolean containsRawInt(int val){
    Node head;
    return (head=this.head)!=null&&uncheckedAnyMatchesRawInt(head,val);
  }
  @Override protected boolean removeRawInt(int val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstRawInt(head,val);
  }
  @Override public boolean contains(double val){
    Node head;
    if((head=this.head)!=null){
      float v;
      if(val==(v=(float)val)){ return uncheckedAnyMatchesFltBits(head,Float.floatToRawIntBits(v)); }
      return v!=v&&uncheckedAnyMatchesFltNaN(head);
    }
    return false;
  }
  @Override public boolean contains(float val){
    Node head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,val);
  }
  @Override public boolean contains(int val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedAnyMatchesFltBits(head,Float.floatToRawIntBits(val)); }
      return uncheckedAnyMatchesFlt0(head);
    }
    return false;
  }
  @Override public boolean contains(long val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedAnyMatchesFltBits(head,Float.floatToRawIntBits(val)); }
      return uncheckedAnyMatchesFlt0(head);
    }
    return false;
  }
  @Override public boolean contains(Object val){
    Node head;
    return (head=this.head)!=null&&val instanceof Float&&uncheckedAnyMatches(head,(float)val);
  }
  @Override public void forEach(Consumer<? super Float> action){
    Node head;
    if((head=this.head)!=null){
      uncheckedForEachForward(head,tail,action::accept);
    }
  }
  @Override public void forEach(FloatConsumer action){
    Node head;
    if((head=this.head)!=null){
      uncheckedForEachForward(head,tail,action);
    }
  }
  @Override public float getFloat(int index){
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
      if(val){ return uncheckedIndexOfFltBits(head,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedIndexOfFlt0(head);
    }
    return -1;
  }
  @Override public int indexOf(char val){
    Node head;
    if((head=this.head)!=null){ return uncheckedIndexOfRawInt(head,val); }
    return -1;
  }
  @Override public int indexOf(double val){
    Node head;
    if((head=this.head)!=null){
      float v;
      if(val==(v=(float)val)){
        return uncheckedIndexOfFltBits(head,Float.floatToRawIntBits(v));
      }else if(v!=v){ return uncheckedIndexOfFltNaN(head); }
    }
    return -1;
  }
  @Override public int indexOf(float val){
    Node head;
    if((head=this.head)!=null){ return uncheckedIndexOfMatch(head,val); }
    return -1;
  }
  @Override public int indexOf(int val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){
        if(!TypeUtil.checkCastToFloat(val)){ return -1; }
        return uncheckedIndexOfFltBits(head,Float.floatToRawIntBits(val));
      }
      return uncheckedIndexOfFlt0(head);
    }
    return -1;
  }
  @Override public int indexOf(long val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){
        if(!TypeUtil.checkCastToFloat(val)){ return -1; }
        return uncheckedIndexOfFltBits(head,Float.floatToRawIntBits(val));
      }
      return uncheckedIndexOfFlt0(head);
    }
    return -1;
  }
  @Override public int indexOf(Object val){
    Node head;
    if((head=this.head)!=null&&val instanceof Float){ return uncheckedIndexOfMatch(head,(float)val); }
    return -1;
  }
  @Override public int indexOf(short val){
    Node head;
    if((head=this.head)!=null){ return uncheckedIndexOfRawInt(head,val); }
    return -1;
  }
  public Float peek(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return null;
  }
  public double peekDouble(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Double.NaN;
  }
  public Float peekFirst(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return null;
  }
  public double peekFirstDouble(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Double.NaN;
  }
  public float peekFirstFloat(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Float.NaN;
  }
  public float peekFloat(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Float.NaN;
  }
  public Float peekLast(){
    Node tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return null;
  }
  public double peekLastDouble(){
    Node tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return Double.NaN;
  }
  public float peekLastFloat(){
    Node tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return Float.NaN;
  }
  @Override public void put(int index,float val){
    staticGetNode(this,index,size-index).val=val;
  }
  @Override public boolean remove(Object val){
    Node head;
    return (head=this.head)!=null&&val instanceof Float&&uncheckedRemoveFirstMatch(head,(float)val);
  }
  public boolean removeFirstOccurrence(boolean val){
    Node head;
    if((head=this.head)!=null){
      if(val){ return uncheckedRemoveFirstFltBits(head,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedRemoveFirstFlt0(head);
    }
    return false;
  }
  public boolean removeFirstOccurrence(char val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstRawInt(head,val);
  }
  public boolean removeFirstOccurrence(double val){
    Node head;
    if((head=this.head)!=null){
      float v;
      if(val==(v=(float)val)){ return uncheckedRemoveFirstFltBits(head,Float.floatToRawIntBits(v)); }
      return v!=v&&uncheckedRemoveFirstFltNaN(head);
    }
    return false;
  }
  public boolean removeFirstOccurrence(float val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  public boolean removeFirstOccurrence(int val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedRemoveFirstFltBits(head,Float.floatToRawIntBits(val)); }
      return uncheckedRemoveFirstFlt0(head);
    }
    return false;
  }
  public boolean removeFirstOccurrence(long val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedRemoveFirstFltBits(head,Float.floatToRawIntBits(val)); }
      return uncheckedRemoveFirstFlt0(head);
    }
    return false;
  }
  public boolean removeFirstOccurrence(Object val){
    Node head;
    return (head=this.head)!=null&&val instanceof Float&&uncheckedRemoveFirstMatch(head,(float)val);
  }
  public boolean removeFirstOccurrence(short val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstRawInt(head,val);
  }
  @Override public boolean removeVal(boolean val){
    Node head;
    if((head=this.head)!=null){
      if(val){ return uncheckedRemoveFirstFltBits(head,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedRemoveFirstFlt0(head);
    }
    return false;
  }
  @Override public boolean removeVal(double val){
    Node head;
    if((head=this.head)!=null){
      float v;
      if(val==(v=(float)val)){ return uncheckedRemoveFirstFltBits(head,Float.floatToRawIntBits(v)); }
      return v!=v&&uncheckedRemoveFirstFltNaN(head);
    }
    return false;
  }
  @Override public boolean removeVal(float val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  @Override public boolean removeVal(int val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedRemoveFirstFltBits(head,Float.floatToRawIntBits(val)); }
      return uncheckedRemoveFirstFlt0(head);
    }
    return false;
  }
  @Override public boolean removeVal(long val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedRemoveFirstFltBits(head,Float.floatToRawIntBits(val)); }
      return uncheckedRemoveFirstFlt0(head);
    }
    return false;
  }
  @Override public void replaceAll(FloatUnaryOperator operator){
    Node head;
    if((head=this.head)!=null){
      uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Float> operator){
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
      if(val){ return uncheckedSearchFltBits(head,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedSearchFlt0(head);
    }
    return -1;
  }
  public int search(char val){
    Node head;
    if((head=this.head)!=null){ return uncheckedSearchRawInt(head,val); }
    return -1;
  }
  public int search(double val){
    Node head;
    if((head=this.head)!=null){
      float v;
      if(val==(v=(float)val)){
        return uncheckedSearchFltBits(head,Float.floatToRawIntBits(v));
      }else if(v!=v){ return uncheckedSearchFltNaN(head); }
    }
    return -1;
  }
  public int search(float val){
    Node head;
    if((head=this.head)!=null){ return uncheckedSearch(head,val); }
    return -1;
  }
  public int search(int val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){
        if(!TypeUtil.checkCastToFloat(val)){ return -1; }
        return uncheckedSearchFltBits(head,Float.floatToRawIntBits(val));
      }
      return uncheckedSearchFlt0(head);
    }
    return -1;
  }
  public int search(long val){
    Node head;
    if((head=this.head)!=null){
      if(val!=0){
        if(!TypeUtil.checkCastToFloat(val)){ return -1; }
        return uncheckedSearchFltBits(head,Float.floatToRawIntBits(val));
      }
      return uncheckedSearchFlt0(head);
    }
    return -1;
  }
  public int search(Object val){
    Node head;
    if((head=this.head)!=null&&val instanceof Float){ return uncheckedSearch(head,(float)val); }
    return -1;
  }
  public int search(short val){
    Node head;
    if((head=this.head)!=null){ return uncheckedSearchRawInt(head,val); }
    return -1;
  }
  @Override public float set(int index,float val){
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
  @Override public void sort(Comparator<? super Float> sorter){
    int size;
    if((size=this.size)>1){
      uncheckedSort(head,size,tail,sorter::compare);
    }
  }
  @Override public void sort(FloatComparator sorter){
    int size;
    if((size=this.size)>1){
      uncheckedSort(head,size,tail,sorter);
    }
  }
  @Override public Float[] toArray(){
    int size;
    if((size=this.size)!=0){
      Float[] dst;
      uncheckedCopyForward(head,dst=new Float[size],0,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size)!=0){
      float[] dst;
      uncheckedCopyForward(head,dst=new float[size],0,size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
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
  @Override protected int uncheckedLastIndexOfFlt0(int size){
    for(var tail=this.tail;tail.val!=0&&--size!=0;tail=tail.prev){}
    return size-1;
  }
  @Override protected int uncheckedLastIndexOfFltBits(int size,int fltBits){
    for(var tail=this.tail;Float.floatToRawIntBits(tail.val)!=fltBits&&--size!=0;tail=tail.prev){}
    return size-1;
  }
  @Override protected int uncheckedLastIndexOfFltNaN(int size){
    for(var tail=this.tail;!Float.isNaN(tail.val)&&--size!=0;tail=tail.prev){}
    return size-1;
  }
  Node getItrNode(int headDist,int tailDist){
    if(tailDist==0){ return null; }
    return staticGetNode(this,headDist,tailDist);
  }
  abstract boolean uncheckedRemoveFirstFlt0(Node head);
  abstract boolean uncheckedRemoveFirstFltBits(Node head,int fltBits);
  abstract boolean uncheckedRemoveFirstFltNaN(Node head);
  private boolean uncheckedAnyMatches(Node head,float val){
    if(val==val){ return uncheckedAnyMatchesFltBits(head,Float.floatToRawIntBits(val)); }
    return uncheckedAnyMatchesFltNaN(head);
  }
  private boolean uncheckedAnyMatchesFlt0(Node head){
    if(head.val!=0){
      final var tail=this.tail;
      do{
        if(head==tail){ return false; }
      }while((head=head.next).val!=0);
    }
    return true;
  }
  private boolean uncheckedAnyMatchesFltBits(Node head,int fltBits){
    if(Float.floatToRawIntBits(head.val)!=fltBits){
      final var tail=this.tail;
      do{
        if(head==tail){ return false; }
      }while(Float.floatToRawIntBits((head=head.next).val)!=fltBits);
    }
    return true;
  }
  private boolean uncheckedAnyMatchesFltNaN(Node head){
    if(!Float.isNaN(head.val)){
      final var tail=this.tail;
      do{
        if(head==tail){ return false; }
      }while(!Float.isNaN((head=head.next).val));
    }
    return true;
  }
  private boolean uncheckedAnyMatchesRawInt(Node head,int val){
    if(val!=0){ return uncheckedAnyMatchesFltBits(head,Float.floatToRawIntBits(val)); }
    return uncheckedAnyMatchesFlt0(head);
  }
  private int uncheckedIndexOfFlt0(Node head){
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
  private int uncheckedIndexOfFltBits(Node head,int fltBits){
    int index=0;
    if(Float.floatToRawIntBits(head.val)!=fltBits){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while(Float.floatToRawIntBits((head=head.next).val)!=fltBits);
    }
    return index;
  }
  private int uncheckedIndexOfFltNaN(Node head){
    int index=0;
    if(!Float.isNaN(head.val)){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while(!Float.isNaN((head=head.next).val));
    }
    return index;
  }
  private int uncheckedIndexOfMatch(Node head,float val){
    if(val==val){ return uncheckedIndexOfFltBits(head,Float.floatToRawIntBits(val)); }
    return uncheckedIndexOfFltNaN(head);
  }
  private int uncheckedIndexOfRawInt(Node head,int val){
    if(val!=0){ return uncheckedIndexOfFltBits(head,Float.floatToRawIntBits(val)); }
    return uncheckedIndexOfFlt0(head);
  }
  private boolean uncheckedRemoveFirstMatch(Node head,float val){
    if(val==val){ return uncheckedRemoveFirstFltBits(head,Float.floatToRawIntBits(val)); }
    return uncheckedRemoveFirstFltNaN(head);
  }
  private boolean uncheckedRemoveFirstRawInt(Node head,int val){
    if(val!=0){ return uncheckedRemoveFirstFltBits(head,Float.floatToRawIntBits(val)); }
    return uncheckedRemoveFirstFlt0(head);
  }
  private int uncheckedSearch(Node head,float val){
    if(val==val){ return uncheckedSearchFltBits(head,Float.floatToRawIntBits(val)); }
    return uncheckedSearchFltNaN(head);
  }
  private int uncheckedSearchFlt0(Node head){
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
  private int uncheckedSearchFltBits(Node head,int fltBits){
    int index=1;
    if(Float.floatToRawIntBits(head.val)!=fltBits){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while(Float.floatToRawIntBits((head=head.next).val)!=fltBits);
    }
    return index;
  }
  private int uncheckedSearchFltNaN(Node head){
    int index=1;
    if(!Float.isNaN(head.val)){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while(!Float.isNaN((head=head.next).val));
    }
    return index;
  }
  private int uncheckedSearchRawInt(Node head,int val){
    if(val!=0){ return uncheckedSearchFltBits(head,Float.floatToRawIntBits(val)); }
    return uncheckedSearchFlt0(head);
  }
  static abstract class Checked extends AbstractSeq implements OmniDeque.OfFloat{
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
    @Override public void addFirst(float val){
      ++modCount;
      super.addFirst(val);
    }
    @Override public void addLast(float val){
      ++modCount;
      super.addLast(val);
    }
    @Override public float getFirstFloat(){
      Node head;
      if((head=this.head)!=null){ return head.val; }
      throw new NoSuchElementException();
    }
    @Override public float getLastFloat(){
      Node tail;
      if((tail=this.tail)!=null){ return tail.val; }
      throw new NoSuchElementException();
    }
    @Override public Float pollFirst(){
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
    @Override public float pollFirstFloat(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return Float.NaN;
    }
    @Override public float removeFirstFloat(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public float removeLastFloat(){
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
    public float removeFirstFloat(){
      Node head;
      --size;
      staticEraseHead(this,head=this.head);
      return head.val;
    }
    public float removeLastFloat(){
      Node tail;
      --size;
      staticEraseTail(this,tail=this.tail);
      return tail.val;
    }
    public Float pollFirst(){
      Node head;
      if((head=this.head)!=null){
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return null;
    }
    public double pollFirstDouble(){
      Node head;
      if((head=this.head)!=null){
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return Double.NaN;
    }
    public float pollFirstFloat(){
      Node head;
      if((head=this.head)!=null){
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return Float.NaN;
    }
    Unchecked(){
      super();
    }
    Unchecked(Node onlyNode){
      super(onlyNode);
    }
    Unchecked(Node head,int size,Node tail){
      super(head,size,tail);
    }
    @Override public boolean removeIf(FloatPredicate filter){
      Node head;
      return (head=this.head)!=null&&uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Float> filter){
      Node head;
      return (head=this.head)!=null&&uncheckedRemoveIf(head,filter::test);
    }
    abstract boolean collapseBody(Node head,Node tail,FloatPredicate filter);
    abstract void collapseTail(Node head,Node tail,FloatPredicate filter);
    abstract void findNewHead(Node head,FloatPredicate filter);
    private boolean uncheckedRemoveIf(Node head,FloatPredicate filter){
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
