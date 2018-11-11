package omni.impl.seq.dbllnk.ofbyte;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.function.ByteComparator;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.function.ByteUnaryOperator;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractByteList;
import omni.impl.seq.dbllnk.ofref.UncheckedList;
import omni.util.OmniArray;
import omni.util.TypeUtil;
abstract class AbstractSeq extends AbstractByteList implements OmniList.OfByte{
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
  static void staticInsertNode(AbstractSeq seq,int headDist,byte val,int tailDist){
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
  static void uncheckedForEachForward(Node begin,Node end,ByteConsumer action){
    for(;;){
      action.accept(begin.val);
      if(begin==end){ return; }
      begin=begin.next;
    }
  }
  static void uncheckedForEachReverse(Node curr,ByteConsumer action){
    do{
      action.accept(curr.val);
    }while((curr=curr.prev)!=null);
  }
  static int uncheckedForwardHashCode(Node begin,Node end){
    int hash=31+begin.val;
    while(begin!=end){
      hash=hash*31+(begin=begin.next).val;
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
  static void uncheckedReplaceAll(Node begin,Node end,ByteUnaryOperator operator){
    for(;;){
      begin.val=operator.applyAsByte(begin.val);
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
  static void uncheckedSort(Node begin,int size,Node end,ByteComparator sorter){
    // TODO
  }
  private static void uncheckedCopyForward(Node src,byte[] dst,int dstOffset,int length){
    for(;;){
      dst[dstOffset]=src.val;
      if(--length==0){ return; }
      ++dstOffset;
      src=src.next;
    }
  }
  private static void uncheckedCopyForward(Node src,Byte[] dst,int dstOffset,int length){
    for(;;){
      dst[dstOffset]=src.val;
      if(--length==0){ return; }
      ++dstOffset;
      src=src.next;
    }
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
  private static void uncheckedCopyForward(Node src,int[] dst,int dstOffset,int length){
    for(;;){
      dst[dstOffset]=src.val;
      if(--length==0){ return; }
      ++dstOffset;
      src=src.next;
    }
  }
  private static void uncheckedCopyForward(Node src,long[] dst,int dstOffset,int length){
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
  private static void uncheckedCopyForward(Node src,short[] dst,int dstOffset,int length){
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
  public void addFirst(byte val){
    Node head;
    if((head=this.head)!=null){
      this.head=new Node(val,head);
    }else{
      staticInit(this,new Node(val));
    }
    ++size;
  }
  public void addLast(byte val){
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
    return (head=this.head)!=null&&uncheckedAnyMatches(head,TypeUtil.castToByte(val));
  }
  @Override public boolean contains(byte val){
    Node head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,val);
  }
  @Override public boolean contains(char val){
    Node head;
    return val<=Byte.MAX_VALUE&&(head=this.head)!=null&&uncheckedAnyMatches(head,val);
  }
  @Override public boolean contains(double val){
    Node head;
    int v;
    return (head=this.head)!=null&&val==(v=(byte)val)&&uncheckedAnyMatches(head,v);
  }
  @Override public boolean contains(float val){
    Node head;
    int v;
    return (head=this.head)!=null&&val==(v=(byte)val)&&uncheckedAnyMatches(head,v);
  }
  @Override public boolean contains(int val){
    Node head;
    return (head=this.head)!=null&&val==(byte)val&&uncheckedAnyMatches(head,val);
  }
  @Override public boolean contains(long val){
    Node head;
    int v;
    return (head=this.head)!=null&&val==(v=(byte)val)&&uncheckedAnyMatches(head,v);
  }
  @Override public boolean contains(Object val){
    Node head;
    return (head=this.head)!=null&&val instanceof Byte&&uncheckedAnyMatches(head,(byte)val);
  }
  @Override public void forEach(ByteConsumer action){
    Node head;
    if((head=this.head)!=null){
      uncheckedForEachForward(head,tail,action);
    }
  }
  @Override public void forEach(Consumer<? super Byte> action){
    Node head;
    if((head=this.head)!=null){
      uncheckedForEachForward(head,tail,action::accept);
    }
  }
  @Override public byte getByte(int index){
    return staticGetNode(this,index,size-index).val;
  }
  @Override public int hashCode(){
    Node head;
    if((head=this.head)!=null){ return uncheckedForwardHashCode(head,tail); }
    return -1;
  }
  @Override public int indexOf(boolean val){
    Node head;
    if((head=this.head)!=null){ return uncheckedIndexOfMatch(head,TypeUtil.castToByte(val)); }
    return -1;
  }
  @Override public int indexOf(byte val){
    Node head;
    if((head=this.head)!=null){ return uncheckedIndexOfMatch(head,val); }
    return -1;
  }
  @Override public int indexOf(char val){
    Node head;
    if(val<=Byte.MAX_VALUE&&(head=this.head)!=null){ return uncheckedIndexOfMatch(head,val); }
    return -1;
  }
  @Override public int indexOf(double val){
    Node head;
    int v;
    if((head=this.head)!=null&&val==(v=(byte)val)){ return uncheckedIndexOfMatch(head,v); }
    return -1;
  }
  @Override public int indexOf(float val){
    Node head;
    int v;
    if((head=this.head)!=null&&val==(v=(byte)val)){ return uncheckedIndexOfMatch(head,v); }
    return -1;
  }
  @Override public int indexOf(int val){
    Node head;
    if((head=this.head)!=null&&val==(byte)val){ return uncheckedIndexOfMatch(head,val); }
    return -1;
  }
  @Override public int indexOf(long val){
    Node head;
    int v;
    if((head=this.head)!=null&&val==(v=(byte)val)){ return uncheckedIndexOfMatch(head,v); }
    return -1;
  }
  @Override public int indexOf(Object val){
    Node head;
    if((head=this.head)!=null&&val instanceof Byte){ return uncheckedIndexOfMatch(head,(byte)val); }
    return -1;
  }
  public Byte peek(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return null;
  }
  public byte peekByte(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Byte.MIN_VALUE;
  }
  public double peekDouble(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Double.NaN;
  }
  public Byte peekFirst(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return null;
  }
  public byte peekFirstByte(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Byte.MIN_VALUE;
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
  public int peekFirstInt(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Integer.MIN_VALUE;
  }
  public long peekFirstLong(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Long.MIN_VALUE;
  }
  public short peekFirstShort(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Short.MIN_VALUE;
  }
  public float peekFloat(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Float.NaN;
  }
  public int peekInt(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Integer.MIN_VALUE;
  }
  public Byte peekLast(){
    Node tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return null;
  }
  public byte peekLastByte(){
    Node tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return Byte.MIN_VALUE;
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
  public int peekLastInt(){
    Node tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return Integer.MIN_VALUE;
  }
  public long peekLastLong(){
    Node tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return Long.MIN_VALUE;
  }
  public short peekLastShort(){
    Node tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return Short.MIN_VALUE;
  }
  public long peekLong(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Long.MIN_VALUE;
  }
  public short peekShort(){
    Node head;
    if((head=this.head)!=null){ return head.val; }
    return Short.MIN_VALUE;
  }
  @Override public void put(int index,byte val){
    staticGetNode(this,index,size-index).val=val;
  }
  @Override public boolean remove(Object val){
    Node head;
    return (head=this.head)!=null&&val instanceof Byte&&uncheckedRemoveFirstMatch(head,(byte)val);
  }
  public boolean removeFirstOccurrence(boolean val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,TypeUtil.castToByte(val));
  }
  public boolean removeFirstOccurrence(byte val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  public boolean removeFirstOccurrence(char val){
    Node head;
    return val<=Byte.MAX_VALUE&&(head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  public boolean removeFirstOccurrence(double val){
    Node head;
    int v;
    return (head=this.head)!=null&&val==(v=(byte)val)&&uncheckedRemoveFirstMatch(head,v);
  }
  public boolean removeFirstOccurrence(float val){
    Node head;
    int v;
    return (head=this.head)!=null&&val==(v=(byte)val)&&uncheckedRemoveFirstMatch(head,v);
  }
  public boolean removeFirstOccurrence(int val){
    Node head;
    return (head=this.head)!=null&&val==(byte)val&&uncheckedRemoveFirstMatch(head,val);
  }
  public boolean removeFirstOccurrence(long val){
    Node head;
    int v;
    return (head=this.head)!=null&&val==(v=(byte)val)&&uncheckedRemoveFirstMatch(head,v);
  }
  public boolean removeFirstOccurrence(Object val){
    Node head;
    return (head=this.head)!=null&&val instanceof Byte&&uncheckedRemoveFirstMatch(head,(byte)val);
  }
  @Override public boolean removeVal(boolean val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,TypeUtil.castToByte(val));
  }
  @Override public boolean removeVal(byte val){
    Node head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  @Override public boolean removeVal(char val){
    Node head;
    return val<=Byte.MAX_VALUE&&(head=this.head)!=null&&uncheckedRemoveFirstMatch(head,val);
  }
  @Override public boolean removeVal(double val){
    Node head;
    int v;
    return (head=this.head)!=null&&val==(v=(byte)val)&&uncheckedRemoveFirstMatch(head,v);
  }
  @Override public boolean removeVal(float val){
    Node head;
    int v;
    return (head=this.head)!=null&&val==(v=(byte)val)&&uncheckedRemoveFirstMatch(head,v);
  }
  @Override public boolean removeVal(int val){
    Node head;
    return (head=this.head)!=null&&val==(byte)val&&uncheckedRemoveFirstMatch(head,val);
  }
  @Override public boolean removeVal(long val){
    Node head;
    int v;
    return (head=this.head)!=null&&val==(v=(byte)val)&&uncheckedRemoveFirstMatch(head,v);
  }
  @Override public void replaceAll(ByteUnaryOperator operator){
    Node head;
    if((head=this.head)!=null){
      uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void replaceAll(UnaryOperator<Byte> operator){
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
    if((head=this.head)!=null){ return uncheckedSearch(head,TypeUtil.castToByte(val)); }
    return -1;
  }
  public int search(byte val){
    Node head;
    if((head=this.head)!=null){ return uncheckedSearch(head,val); }
    return -1;
  }
  public int search(char val){
    Node head;
    if(val<=Byte.MAX_VALUE&&(head=this.head)!=null){ return uncheckedSearch(head,val); }
    return -1;
  }
  public int search(double val){
    Node head;
    int v;
    if((head=this.head)!=null&&val==(v=(byte)val)){ return uncheckedSearch(head,v); }
    return -1;
  }
  public int search(float val){
    Node head;
    int v;
    if((head=this.head)!=null&&val==(v=(byte)val)){ return uncheckedSearch(head,v); }
    return -1;
  }
  public int search(int val){
    Node head;
    if((head=this.head)!=null&&val==(byte)val){ return uncheckedSearch(head,val); }
    return -1;
  }
  public int search(long val){
    Node head;
    int v;
    if((head=this.head)!=null&&val==(v=(byte)val)){ return uncheckedSearch(head,v); }
    return -1;
  }
  public int search(Object val){
    Node head;
    if((head=this.head)!=null&&val instanceof Byte){ return uncheckedSearch(head,(byte)val); }
    return -1;
  }
  @Override public byte set(int index,byte val){
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
  @Override public void sort(ByteComparator sorter){
    int size;
    if((size=this.size)>1){
      uncheckedSort(head,size,tail,sorter);
    }
  }
  @Override public void sort(Comparator<? super Byte> sorter){
    int size;
    if((size=this.size)>1){
      uncheckedSort(head,size,tail,sorter::compare);
    }
  }
  @Override public Byte[] toArray(){
    int size;
    if((size=this.size)!=0){
      Byte[] dst;
      uncheckedCopyForward(head,dst=new Byte[size],0,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
  @Override public byte[] toByteArray(){
    int size;
    if((size=this.size)!=0){
      byte[] dst;
      uncheckedCopyForward(head,dst=new byte[size],0,size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
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
  @Override public int[] toIntArray(){
    int size;
    if((size=this.size)!=0){
      int[] dst;
      uncheckedCopyForward(head,dst=new int[size],0,size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    if((size=this.size)!=0){
      long[] dst;
      uncheckedCopyForward(head,dst=new long[size],0,size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    int size;
    if((size=this.size)!=0){
      short[] dst;
      uncheckedCopyForward(head,dst=new short[size],0,size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
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
  @Override protected int uncheckedLastIndexOfMatch(int size,int val){
    for(var tail=this.tail;tail.val!=val&&--size!=0;tail=tail.prev){}
    return size-1;
  }
  Node getItrNode(int headDist,int tailDist){
    if(tailDist==0){ return null; }
    return staticGetNode(this,headDist,tailDist);
  }
  abstract boolean uncheckedRemoveFirstMatch(Node head,int val);
  private boolean uncheckedAnyMatches(Node head,int val){
    if(head.val!=val){
      final var tail=this.tail;
      do{
        if(head==tail){ return false; }
      }while((head=head.next).val!=val);
    }
    return true;
  }
  private int uncheckedIndexOfMatch(Node head,int val){
    int index=0;
    if(head.val!=val){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while((head=head.next).val!=val);
    }
    return index;
  }
  private int uncheckedSearch(Node head,int val){
    int index=1;
    if(head.val!=val){
      final var tail=this.tail;
      do{
        if(head==tail){ return -1; }
        ++index;
      }while((head=head.next).val!=val);
    }
    return index;
  }
  static abstract class Checked extends AbstractSeq implements OmniDeque.OfByte{
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
    @Override public void addFirst(byte val){
      ++modCount;
      super.addFirst(val);
    }
    @Override public void addLast(byte val){
      ++modCount;
      super.addLast(val);
    }
    @Override public byte getFirstByte(){
      Node head;
      if((head=this.head)!=null){ return head.val; }
      throw new NoSuchElementException();
    }
    @Override public byte getLastByte(){
      Node tail;
      if((tail=this.tail)!=null){ return tail.val; }
      throw new NoSuchElementException();
    }
    @Override public Byte pollFirst(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return null;
    }
    @Override public byte pollFirstByte(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return Byte.MIN_VALUE;
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
    @Override public int pollFirstInt(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return Integer.MIN_VALUE;
    }
    @Override public long pollFirstLong(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return Long.MIN_VALUE;
    }
    @Override public short pollFirstShort(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte removeFirstByte(){
      Node head;
      if((head=this.head)!=null){
        ++modCount;
        --size;
        staticEraseHead(this,head);
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public byte removeLastByte(){
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
    @Override public boolean removeIf(BytePredicate filter){
      Node head;
      return (head=this.head)!=null&&uncheckedRemoveIf(head,filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      Node head;
      return (head=this.head)!=null&&uncheckedRemoveIf(head,filter::test);
    }
    abstract boolean collapseBody(Node head,Node tail,BytePredicate filter);
    abstract void collapseTail(Node head,Node tail,BytePredicate filter);
    abstract void findNewHead(Node head,BytePredicate filter);
    private boolean uncheckedRemoveIf(Node head,BytePredicate filter){
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
