package omni.impl.seq.dbllnk.ofchar;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.CharComparator;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.function.CharUnaryOperator;
import omni.impl.CheckedCollection;
import omni.util.BitSetUtils;
import omni.util.TypeUtil;
public class CheckedSubList extends AbstractSeq{
  transient final CheckedSubList parent;
  transient final Checked root;
  transient int modCount;
  CheckedSubList(Checked root,CheckedSubList parent){
    super();
    this.root=root;
    this.parent=parent;
  }
  CheckedSubList(Checked root,CheckedSubList parent,Node onlyNode){
    super(onlyNode);
    this.root=root;
    this.parent=parent;
  }
  CheckedSubList(Checked root,CheckedSubList parent,Node head,int size,Node tail){
    super(head,size,tail);
    this.root=root;
    this.parent=parent;
  }
  @Override public boolean add(boolean val){
    int modCount;
    Checked root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    uncheckedAddLast(TypeUtil.castToChar(val));
    ++root.size;
    return true;
  }
  @Override public boolean add(char val){
    int modCount;
    Checked root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    uncheckedAddLast(val);
    ++root.size;
    return true;
  }
  @Override public boolean add(Character val){
    int modCount;
    Checked root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    uncheckedAddLast(val);
    ++root.size;
    return true;
  }
  @Override public void add(int index,char val){
    int modCount;
    Checked root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkWriteHi(index,size=this.size);
    root.modCount=++modCount;
    if(size!=0){
      if(index==0){
        prependHelper(val,modCount);
      }else{
        int tailDist;
        if((tailDist=size-index)==0){
          appendHelper(val,modCount);
        }else{
          Node before,after;
          if(index<=tailDist){
            after=(before=iterateForward(head,index-1)).next;
          }else{
            before=(after=iterateReverse(tail,tailDist-1)).prev;
          }
          insertHelper(before,val,after,modCount);
        }
      }
    }else{
      initHelper(root,val,modCount);
    }
    ++root.size;
    this.size=size+1;
  }
  @Override public void clear(){
    int modCount;
    Checked root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    if(size!=0){
      root.modCount=++modCount;
      bubbleUpClear(modCount);
      staticClear(root);
    }
  }
  @Override public Object clone(){
    CheckedCollection.checkModCount(modCount,root.modCount);
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
    return new CheckedList(newHead,size,newTail);
  }
  @Override public boolean contains(boolean val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(char val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(double val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(float val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(int val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(long val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(Object val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean contains(short val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.contains(val);
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public void forEach(Consumer<? super Character> action){
    final int modCount=this.modCount;
    try{
      super.forEach(action);
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public void forEach(CharConsumer action){
    final int modCount=this.modCount;
    try{
      super.forEach(action);
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public char getChar(int index){
    CheckedCollection.checkModCount(modCount,root.modCount);
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    return staticGetNode(this,index,size-index).val;
  }
  @Override public int hashCode(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.hashCode();
  }
  @Override public int indexOf(boolean val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(char val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(double val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(float val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(int val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(long val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(Object val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public int indexOf(short val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.indexOf(val);
  }
  @Override public boolean isEmpty(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return size==0;
  }
  @Override public OmniIterator.OfChar iterator(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return new CheckedAscendingSubItr(this);
  }
  @Override public int lastIndexOf(boolean val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.lastIndexOf(val);
  }
  @Override public int lastIndexOf(char val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.lastIndexOf(val);
  }
  @Override public int lastIndexOf(double val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.lastIndexOf(val);
  }
  @Override public int lastIndexOf(float val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.lastIndexOf(val);
  }
  @Override public int lastIndexOf(int val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.lastIndexOf(val);
  }
  @Override public int lastIndexOf(long val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.lastIndexOf(val);
  }
  @Override public int lastIndexOf(Object val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.lastIndexOf(val);
  }
  @Override public int lastIndexOf(short val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.lastIndexOf(val);
  }
  @Override public OmniListIterator.OfChar listIterator(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return new CheckedSubList.BidirectionalItr(this);
  }
  @Override public OmniListIterator.OfChar listIterator(int index){
    CheckedCollection.checkModCount(modCount,root.modCount);
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkWriteHi(index,size=this.size);
    return new CheckedSubList.BidirectionalItr(this,super.getItrNode(index,size-index),index);
  }
  @Override public void put(int index,char val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    staticGetNode(this,index,size-index).val=val;
  }
  @Override public boolean remove(Object val){
    Node head;
    if((head=this.head)!=null&&val instanceof Short){ return uncheckedRemoveFirstMatch(head,(char)val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(boolean val){
    Node head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,TypeUtil.castToByte(val)); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(char val){
    Node head;
    if((head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(double val){
    Node head;
    int v;
    if((head=this.head)!=null&&val==(v=(char)val)){ return uncheckedRemoveFirstMatch(head,v); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(float val){
    Node head;
    int v;
    if((head=this.head)!=null&&val==(v=(char)val)){ return uncheckedRemoveFirstMatch(head,v); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(int val){
    Node head;
    if((head=this.head)!=null&&val==(char)val){ return uncheckedRemoveFirstMatch(head,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(long val){
    Node head;
    int v;
    if((head=this.head)!=null&&val==(v=(char)val)){ return uncheckedRemoveFirstMatch(head,v); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeVal(short val){
    Node head;
    if(val>=0&&(head=this.head)!=null){ return uncheckedRemoveFirstMatch(head,val); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeIf(CharPredicate filter){
    Node head;
    if((head=this.head)!=null){ return uncheckedRemoveIf(head,filter); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Character> filter){
    Node head;
    if((head=this.head)!=null){ return uncheckedRemoveIf(head,filter::test); }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  @Override public char removeCharAt(int index){
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    Node node;
    if(--size!=0){
      if(index==0){
        node=uncheckedExtractHead(size);
      }else{
        int tailDist;
        if((tailDist=size-index)==0){
          node=uncheckedExtractTail(size);
        }else{
          int modCount;
          Checked root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          CheckedSubList parent;
          if((parent=this.parent)!=null){
            parent.bubbleUpDecrementSize(modCount);
          }
          --root.size;
          node=staticExtractNode(this,index,tailDist);
        }
      }
    }else{
      node=uncheckedExtractLastNode();
    }
    this.size=size;
    return node.val;
  }
  @Override public void replaceAll(CharUnaryOperator operator){
    int modCount=this.modCount;
    Checked root;
    try{
      Node head;
      if((head=this.head)==null){ return; }
      uncheckedReplaceAll(head,tail,operator);
    }finally{
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    }
    root.modCount=++modCount;
    bubbleUpSetModCount(modCount);
  }
  @Override public void replaceAll(UnaryOperator<Character> operator){
    int modCount=this.modCount;
    Checked root;
    try{
      Node head;
      if((head=this.head)==null){ return; }
      uncheckedReplaceAll(head,tail,operator::apply);
    }finally{
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    }
    root.modCount=++modCount;
    bubbleUpSetModCount(modCount);
  }
  @Override public void reverseSort(){
    int modCount=this.modCount;
    Checked root;
    try{
      int size;
      if((size=this.size)<2){ return; }
      uncheckedReverseSort(head,size,tail);
    }finally{
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    }
    root.modCount=++modCount;
    bubbleUpSetModCount(modCount);
  }
  @Override public char set(int index,char val){
    CheckedCollection.checkModCount(modCount,root.modCount);
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    Node node;
    final var oldVal=(node=staticGetNode(this,index,size-index)).val;
    node.val=val;
    return oldVal;
  }
  @Override public int size(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return size;
  }
  @Override public void sort(){
    int modCount=this.modCount;
    Checked root;
    try{
      int size;
      if((size=this.size)<2){ return; }
      uncheckedSort(head,size,tail);
    }finally{
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    }
    root.modCount=++modCount;
    bubbleUpSetModCount(modCount);
  }
  @Override public void sort(Comparator<? super Character> sorter){
    int modCount=this.modCount;
    Checked root;
    try{
      int size;
      if((size=this.size)<2){ return; }
      uncheckedSort(head,size,tail,sorter::compare);
    }finally{
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    }
    root.modCount=++modCount;
    bubbleUpSetModCount(modCount);
  }
  @Override public void sort(CharComparator sorter){
    int modCount=this.modCount;
    Checked root;
    try{
      int size;
      if((size=this.size)<2){ return; }
      uncheckedSort(head,size,tail,sorter);
    }finally{
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    }
    root.modCount=++modCount;
    bubbleUpSetModCount(modCount);
  }
  @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
    Checked root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    int size;
    CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
    final int tailDist=size-toIndex;
    int subListSize;
    switch(subListSize=toIndex-fromIndex){
    default:
      return getDefaultSubList(root,fromIndex,subListSize,tailDist);
    case 1:
      return getSubList1(root,fromIndex,tailDist);
    case 0:
      return getEmptySubList(root,fromIndex,tailDist);
    }
  }
  @Override public Character[] toArray(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.toArray();
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    return super.toArray(size->{
      final int modCount=this.modCount;
      try{
        return arrConstructor.apply(size);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    });
  }
  @Override public <T> T[] toArray(T[] dst){
    final int modCount=this.modCount;
    try{
      return super.toArray(dst);
    }finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
  @Override public double[] toDoubleArray(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.toDoubleArray();
  }
  @Override public float[] toFloatArray(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.toFloatArray();
  }
  @Override public int[] toIntArray(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.toIntArray();
  }
  @Override public long[] toLongArray(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.toLongArray();
  }
  @Override public char[] toCharArray(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.toCharArray();
  }
  @Override public String toString(){
    CheckedCollection.checkModCount(modCount,root.modCount);
    return super.toString();
  }
  void appendHelper(char val,int modCount){
    this.modCount=modCount;
    Node newNode;
    tail=newNode=new Node(tail,val);
    CheckedSubList parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpRootPushTail(modCount,newNode);
    }
  }
  void ascItrRemove(CheckedAscendingSubItr itr){
    Node lastRet;
    if((lastRet=itr.lastRet)!=null){
      int modCount;
      Checked root;
      CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      uncheckedItrRemove(lastRet,modCount,root);
      itr.modCount=modCount;
      --root.size;
      return;
    }
    throw new IllegalStateException();
  }
  void bidirectItrRemove(CheckedSubList.BidirectionalItr itr){
    Node lastRet;
    if((lastRet=itr.lastRet)!=null){
      int modCount;
      Checked root;
      CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      uncheckedItrRemove(lastRet,modCount,root);
      if(lastRet!=itr.cursor){
        --itr.nextIndex;
      }
      itr.modCount=modCount;
      --root.size;
      return;
    }
    throw new IllegalStateException();
  }
  void findNewHead(int modCount,Node curr,CharPredicate filter){
    final Node tail;
    if(curr==(tail=this.tail)){
      Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      bubbleUpClear(modCount);
      staticClear(root);
    }else if(filter.test(tail.val)){
      collapseHeadAndTail(modCount,curr,tail,filter);
    }else{
      rootCollapseHead(modCount,curr.next,tail,filter);
    }
  }
  int getParentOffset(){
    return 0;
  }
  void initHelper(Checked root,char val,int modCount){
    final var newNode=new Node(val);
    for(var curr=this;;curr.size=1){
      curr.privateInit(newNode,modCount);
      if((curr=curr.parent)==null){
        staticInit(root,newNode);
        return;
      }
    }
  }
  void prependHelper(char val,int modCount){
    this.modCount=modCount;
    Node newNode;
    head=newNode=new Node(val,head);
    CheckedSubList parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpRootPushHead(modCount,newNode);
    }
  }
  void removeFirstHelper(int modCount,Node curr){
    Checked root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    if(curr==tail){
      bubbleUpClear(modCount);
      staticClear(root);
    }else{
      bubbleUpRootEraseHead(modCount,curr=curr.next);
      staticSetHead(root,curr);
      --root.size;
    }
  }
  boolean removeIfHelper(int modCount,Node curr,CharPredicate filter){
    final Node tail;
    if(curr!=(tail=this.tail)){
      if(filter.test(tail.val)){
        rootCollapseTail(modCount,curr,tail.prev,filter);
        return true;
      }
      return collapseBody(modCount,curr,tail,filter);
    }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  Node uncheckedExtractHead(int newSize){
    Checked root;
    int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    Node oldHead,newHead;
    bubbleUpRootEraseHead(modCount,newHead=(oldHead=head).next);
    staticSetHead(root,newHead);
    root.size=newSize;
    return oldHead;
  }
  Node uncheckedExtractLastNode(){
    int modCount;
    Checked root;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    final var lastNode=head;
    bubbleUpClear(modCount);
    staticClear(root);
    return lastNode;
  }
  Node uncheckedExtractTail(int newSize){
    Checked root;
    int modCount;
    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
    root.modCount=++modCount;
    Node oldTail,newTail;
    bubbleUpRootEraseTail(modCount,newTail=(oldTail=tail).prev);
    staticSetTail(root,newTail);
    root.size=newSize;
    return oldTail;
  }
  @Override boolean uncheckedRemoveFirstMatch(Node head,int val){
    final int modCount=this.modCount;
    try{
      if(head.val==val){
        removeFirstHelper(modCount,head);
        return true;
      }
      return uncheckedRemoveFirstMatchHelper(modCount,head,val);
    }catch(final RuntimeException e){
      throw CheckedCollection.checkModCount(modCount,root.modCount,e);
    }
  }
  boolean uncheckedRemoveFirstMatchHelper(int modCount,Node curr,int val){
    final var root=this.root;
    final var tail=this.tail;
    Node prev;
    do{
      if(curr==tail){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
    }while((curr=(prev=curr).next).val!=val);
    CheckedCollection.checkModCount(modCount,root.modCount);
    root.modCount=++modCount;
    --root.size;
    if(curr==tail){
      bubbleUpRootEraseTail(modCount,prev);
      staticSetTail(root,prev);
    }else{
      bubbleUpDecrementSize(modCount);
      joinNodes(prev,curr.next);
    }
    return true;
  }
  private void bubbleUpClear(int modCount){
    var curr=this;
    do{
      curr.modCount=modCount;
      staticClear(curr);
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpCollapseHeadAndTail(int newModCount,Node newHead,Node newTail,int newSize){
    var curr=this;
    do{
      curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpDecrementSize(int newModCount){
    var curr=this;
    do{
      curr.modCount=newModCount;
      --curr.size;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpDecrementSize(int newModCount,int numRemoved){
    var curr=this;
    do{
      curr.modCount=newModCount;
      curr.size-=numRemoved;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpIncrementSize(int newModCount){
    var curr=this;
    do{
      curr.modCount=newModCount;
      ++curr.size;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpPrefixCollapseTail(int newModCount,int numRemoved,Node oldTail,Node newTail){
    var curr=this;
    while(curr.tail==oldTail){
      curr.modCount=newModCount;
      curr.size-=numRemoved;
      curr.tail=newTail;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpDecrementSize(newModCount,numRemoved);
  }
  private void bubbleUpPrefixPushTail(int newModCount,Node newTail,Node oldTail){
    var curr=this;
    while(curr.tail==oldTail){
      curr.modCount=newModCount;
      ++curr.size;
      curr.tail=newTail;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpIncrementSize(newModCount);
  }
  private void bubbleUpRootCollapseHead(int newModCount,int numRemoved,Node newHead){
    var curr=this;
    do{
      curr.modCount=newModCount;
      curr.size-=numRemoved;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootCollapseTail(int newModCount,int numRemoved,Node newTail){
    var curr=this;
    do{
      curr.modCount=newModCount;
      curr.size-=numRemoved;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootEraseHead(int newModCount,Node newHead){
    var curr=this;
    do{
      curr.modCount=newModCount;
      --curr.size;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootEraseTail(int newModCount,Node newTail){
    var curr=this;
    do{
      curr.modCount=newModCount;
      --curr.size;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootPushHead(int newModCount,Node newHead){
    var curr=this;
    do{
      curr.modCount=newModCount;
      ++curr.size;
      curr.head=newHead;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpRootPushTail(int newModCount,Node newTail){
    var curr=this;
    do{
      curr.modCount=newModCount;
      ++curr.size;
      curr.tail=newTail;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpSetModCount(int newModCount){
    var curr=this;
    do{
      curr.modCount=newModCount;
    }while((curr=curr.parent)!=null);
  }
  private void bubbleUpSuffixCollapseHead(int newModCount,int numRemoved,Node oldHead,Node newHead){
    var curr=this;
    while(curr.head==oldHead){
      curr.modCount=newModCount;
      curr.size-=numRemoved;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpDecrementSize(newModCount,numRemoved);
  }
  private void bubbleUpSuffixEraseHead(int newModCount,Node oldHead,Node newHead){
    var curr=this;
    do{
      curr.modCount=newModCount;
      --curr.size;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }while(curr.head==oldHead);
    curr.bubbleUpDecrementSize(newModCount);
    joinNodes(oldHead.prev,newHead);
  }
  private void bubbleUpSuffixPushHead(int newModCount,Node newHead,Node oldHead){
    var curr=this;
    while(curr.head==oldHead){
      curr.modCount=newModCount;
      ++curr.size;
      curr.head=newHead;
      if((curr=curr.parent)==null){ return; }
    }
    curr.bubbleUpIncrementSize(newModCount);
  }
  private boolean collapseBody(int modCount,Node prev,Node next,CharPredicate filter){
    final int oldSize=size;
    int numConsumed=2;
    final var root=this.root;
    for(Node before;numConsumed!=oldSize;prev=before){
      ++numConsumed;
      if(filter.test((before=prev.next).val)){
        int numRemoved=1;
        for(Node after;;next=after){
          if(numConsumed==oldSize){
            CheckedCollection.checkModCount(modCount,root.modCount);
            break;
          }
          ++numConsumed;
          if(filter.test((after=prev.next).val)){
            ++numRemoved;
            long[] survivorSet;
            int numLeft,numSurvivors;
            if((numLeft=oldSize-numConsumed)!=0&&(numSurvivors=CheckedList.markSurvivors(before=before.next,numLeft,
                survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0){
              CheckedCollection.checkModCount(modCount,root.modCount);
              numRemoved+=numLeft-numSurvivors;
              prev=CheckedList.retainSurvivors(prev,before,numSurvivors,survivorSet);
            }else{
              CheckedCollection.checkModCount(modCount,root.modCount);
              numRemoved+=oldSize;
            }
            break;
          }
        }
        joinNodes(prev,next);
        root.modCount=++modCount;
        bubbleUpDecrementSize(modCount,numRemoved);
        root.size-=numRemoved;
        return true;
      }
    }
    CheckedCollection.checkModCount(modCount,root.modCount);
    return false;
  }
  private void collapseHeadAndTail(int modCount,Node headCandidate,Node tailCandidate,final CharPredicate filter){
    int size=this.size;
    int numConsumed=2;
    final var root=this.root;
    for(;;){
      if(numConsumed==size){
        CheckedCollection.checkModCount(modCount,root.modCount);
        bubbleUpClear(++modCount);
        staticClear(root);
        break;
      }
      ++numConsumed;
      if(!filter.test((headCandidate=headCandidate.next).val)){
        for(;;){
          if(numConsumed==size){
            CheckedCollection.checkModCount(modCount,root.modCount);
            size=1;
            break;
          }
          ++numConsumed;
          if(!filter.test((tailCandidate=tailCandidate.prev).val)){
            size-=numConsumed-2-CheckedList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                size-numConsumed,tailCandidate,filter);
            break;
          }
        }
        bubbleUpCollapseHeadAndTail(++modCount,headCandidate,tailCandidate,size);
        staticSetHead(root,headCandidate);
        staticSetTail(root,tailCandidate);
        root.size=size;
        break;
      }
    }
    root.modCount=modCount;
  }
  private OmniList.OfChar getDefaultSubList(Checked root,int headDist,int subListSize,int tailDist){
    final Node subListHead=head;
    Node subListTail=tail;
    if(tailDist==0){
      if(headDist==0){ return new CheckedSubList(root,this,subListHead,subListSize,subListTail); }
      return new CheckedSubList.Suffix(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
          :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail);
    }
    subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
        :uncheckedIterateForward(subListHead,subListSize);
    if(headDist==0){ return new CheckedSubList.Prefix(root,this,subListHead,subListSize,subListTail); }
    return new CheckedSubList.Body(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
        :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail,headDist);
  }
  private OmniList.OfChar getEmptySubList(Checked root,int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new CheckedSubList(root,this); }
      return new CheckedSubList.Suffix(root,this);
    }else if(headDist==0){ return new CheckedSubList.Prefix(root,this); }
    return new CheckedSubList.Body(root,this,headDist);
  }
  private OmniList.OfChar getSubList1(Checked root,int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new CheckedSubList(root,this,head); }
      return new CheckedSubList.Suffix(root,this,tail);
    }else if(headDist==0){ return new CheckedSubList.Prefix(root,this,head); }
    return new CheckedSubList.Body(root,this,staticGetNode(this,headDist,tailDist),headDist);
  }
  private void insertHelper(Node before,char val,Node after,int modCount){
    new Node(before,val,after);
    this.modCount=modCount;
    CheckedSubList parent;
    if((parent=this.parent)!=null){
      parent.bubbleUpIncrementSize(modCount);
    }
  }
  private void privateCollapseHeadAndTail(int modCount,int size,Node head,Node tail){
    this.modCount=modCount;
    this.size=size;
    this.head=head;
    this.tail=tail;
  }
  private void privateInit(Node node,int modCount){
    this.modCount=modCount;
    staticInit(this,node);
  }
  private void rootCollapseHead(int modCount,Node headCandidate,final Node oldTail,CharPredicate filter){
    final int oldSize=size;
    int numConsumed=2;
    final var root=this.root;
    for(;;headCandidate=headCandidate.next){
      if(numConsumed==oldSize){
        CheckedCollection.checkModCount(modCount,root.modCount);
        --numConsumed;
        break;
      }
      ++numConsumed;
      if(!filter.test(headCandidate.val)){
        numConsumed-=2-CheckedList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
            oldSize-numConsumed,oldTail,filter);
        break;
      }
    }
    root.modCount=++modCount;
    bubbleUpRootCollapseHead(modCount,numConsumed,headCandidate);
    staticSetHead(root,headCandidate);
    root.size-=numConsumed;
  }
  private void rootCollapseTail(int modCount,final Node oldHead,Node tailCandidate,CharPredicate filter){
    final int oldSize=size;
    int numConsumed=2;
    final var root=this.root;
    for(;;tailCandidate=tailCandidate.prev){
      if(numConsumed==oldSize){
        CheckedCollection.checkModCount(modCount,root.modCount);
        --numConsumed;
        break;
      }
      ++numConsumed;
      if(!filter.test(tailCandidate.val)){
        numConsumed-=2-CheckedList.collapseBodyHelper(root.new ModCountChecker(modCount),oldHead,oldSize-numConsumed,
            tailCandidate,filter);
        break;
      }
    }
    root.modCount=++modCount;
    bubbleUpRootCollapseTail(modCount,numConsumed,tailCandidate);
    staticSetTail(root,tailCandidate);
    root.size-=numConsumed;
  }
  private void suffixCollapseHead(int modCount,Node oldHead,Node oldTail,CharPredicate filter){
    final int oldSize=size;
    int numConsumed=2;
    final var root=this.root;
    var headCandidate=oldHead.next;
    for(;;headCandidate=headCandidate.next){
      if(numConsumed==oldSize){
        CheckedCollection.checkModCount(modCount,root.modCount);
        --numConsumed;
        break;
      }
      ++numConsumed;
      if(!filter.test(headCandidate.val)){
        numConsumed-=2-CheckedList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
            oldSize-numConsumed,oldTail,filter);
        break;
      }
    }
    root.modCount=++modCount;
    bubbleUpSuffixCollapseHead(modCount,numConsumed,oldHead,headCandidate);
    joinNodes(oldHead.prev,headCandidate);
    root.size-=numConsumed;
  }
  private void uncheckedAddLast(char val){
    if(++size!=1){
      appendHelper(val,modCount);
    }else{
      initHelper(root,val,modCount);
    }
  }
  private void uncheckedItrRemove(Node lastRet,int modCount,Checked root){
    if(lastRet==tail){
      if(lastRet==head){
        bubbleUpClear(modCount);
        staticInit(root,null);
      }else{
        bubbleUpRootEraseTail(modCount,lastRet=lastRet.prev);
        staticSetTail(root,lastRet);
      }
    }else{
      if(lastRet==head){
        bubbleUpRootEraseHead(modCount,lastRet=lastRet.next);
        staticSetHead(root,lastRet);
      }else{
        bubbleUpDecrementSize(modCount);
        joinNodes(lastRet.prev,lastRet.next);
      }
    }
  }
  private boolean uncheckedRemoveIf(Node head,CharPredicate filter){
    final int modCount=this.modCount;
    try{
      if(filter.test(head.val)){
        findNewHead(modCount,head,filter);
        return true;
      }
      return removeIfHelper(modCount,head,filter);
    }catch(final RuntimeException e){
      throw CheckedCollection.checkModCount(modCount,root.modCount,e);
    }
  }
  static class Body extends CheckedSubList.Prefix{
    private transient final int parentOffset;
    Body(Checked root,CheckedSubList parent,int parentOffset){
      super(root,parent);
      this.parentOffset=parentOffset;
    }
    Body(Checked root,CheckedSubList parent,Node onlyNode,int parentOffset){
      super(root,parent,onlyNode);
      this.parentOffset=parentOffset;
    }
    Body(Checked root,CheckedSubList parent,Node head,int size,Node tail,int parentOffset){
      super(root,parent,head,size,tail);
      this.parentOffset=parentOffset;
    }
    @Override public void clear(){
      Checked root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        bubbleUpClear(modCount,size,head,tail);
        root.size-=size;
      }
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
      int subListSize;
      switch(subListSize=toIndex-fromIndex){
      default:
        return getDefaultSubList(root,fromIndex,subListSize,size-toIndex);
      case 1:
        return getSubList1(root,fromIndex,size-toIndex);
      case 0:
        return getEmptySubList(root,fromIndex);
      }
    }
    @Override void ascItrRemove(CheckedAscendingSubItr itr){
      Node lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount);
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void bidirectItrRemove(CheckedSubList.BidirectionalItr itr){
      Node lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount);
        if(lastRet!=itr.cursor){
          --itr.nextIndex;
        }
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void findNewHead(int modCount,Node curr,CharPredicate filter){
      final Node tail;
      if(curr==(tail=this.tail)){
        Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        bubbleUpClear(++modCount,1,curr,tail);
        root.modCount=modCount;
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(modCount,curr,tail,filter);
      }else{
        ((CheckedSubList)this).suffixCollapseHead(modCount,curr,tail,filter);
      }
    }
    @Override int getParentOffset(){
      return parentOffset;
    }
    @Override void initHelper(Checked root,char val,int modCount){
      final Node newNode;
      ((CheckedSubList)this).privateInit(newNode=new Node(val),modCount);
      CheckedSubList parent,curr;
      if((parent=(curr=this).parent)!=null){
        do{
          int parentSize;
          if((parentSize=parent.size)!=0){
            Node before,after;
            int headDist,tailDist;
            if((headDist=curr.getParentOffset())==0){
              parent.bubbleUpSuffixPushHead(modCount,newNode,after=parent.head);
              before=after.prev;
            }else if((tailDist=parentSize-headDist)==0){
              parent.bubbleUpPrefixPushTail(modCount,newNode,before=parent.tail);
              after=before.next;
            }else{
              if(headDist<=tailDist){
                after=(before=iterateForward(root.head,headDist-1)).next;
              }else{
                before=(after=iterateReverse(root.tail,tailDist-1)).prev;
              }
              parent.bubbleUpIncrementSize(modCount);
            }
            joinNodes(before,newNode);
            joinNodes(newNode,after);
            return;
          }
          parent=(curr=parent).parent;
          curr.privateInit(newNode,modCount);
          curr.size=1;
        }while(parent!=null);
      }
      subSeqInsertHelper(root,newNode,curr.getParentOffset());
    }
    @Override void prependHelper(char val,int modCount){
      this.modCount=modCount;
      Node newNode,oldHead;
      head=newNode=new Node((oldHead=head).prev,val,oldHead);
      CheckedSubList parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpSuffixPushHead(modCount,newNode,oldHead);
      }
    }
    @Override void removeFirstHelper(int modCount,Node curr){
      Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      if(curr==tail){
        bubbleUpClear(modCount,1,head,curr);
      }else{
        ((CheckedSubList)this).bubbleUpSuffixEraseHead(modCount,curr,curr.next);
      }
      --root.size;
    }
    @Override Node uncheckedExtractHead(int newSize){
      Checked root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      Node oldHead;
      ((CheckedSubList)this).bubbleUpSuffixEraseHead(modCount,oldHead=head,oldHead.next);
      --root.size;
      return oldHead;
    }
    @Override Node uncheckedExtractLastNode(){
      final Node lastNode;
      final Checked root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      bubbleUpClear(modCount,1,lastNode=head,lastNode);
      --root.size;
      return lastNode;
    }
    private void bubbleUpClear(int modCount,int size,Node head,Node tail){
      final Node prev=head.prev,next=tail.next;
      for(CheckedSubList curr=this;;){
        curr.modCount=modCount;
        staticClear(curr);
        if((curr=curr.parent)!=null){
          if(curr.head!=head){
            curr.bubbleUpPrefixCollapseTail(modCount,size,tail,prev);
            break;
          }else if(curr.tail!=tail){
            curr.modCount=modCount;
            curr.size-=size;
            curr.head=next;
            if((curr=curr.parent)!=null){
              curr.bubbleUpSuffixCollapseHead(modCount,size,head,next);
            }
            break;
          }
          continue;
        }
        break;
      }
      joinNodes(prev,next);
    }
    private void bubbleUpCollapseHeadAndTail(int newModCount,Node oldHead,Node oldTail,Node newHead,Node newTail,
        int numRemoved){
      final int newSize=size-numRemoved;
      for(CheckedSubList curr=this;;){
        curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
        if((curr=curr.parent)==null){
          break;
        }
        if(curr.head!=oldHead){
          curr.bubbleUpPrefixCollapseTail(newModCount,numRemoved,oldTail,newTail);
          break;
        }else if(curr.tail!=oldTail){
          curr.modCount=newModCount;
          curr.size-=numRemoved;
          curr.head=newHead;
          if((curr=curr.parent)!=null){
            curr.bubbleUpSuffixCollapseHead(newModCount,numRemoved,oldHead,newHead);
          }
          break;
        }
      }
      joinNodes(oldHead.prev,newHead);
      joinNodes(newTail,oldTail.next);
    }
    private void collapseHeadAndTail(int modCount,final Node head,final Node tail,final CharPredicate filter){
      final int oldSize=size;
      int numConsumed=2;
      var headCandidate=head;
      final var root=this.root;
      for(;;){
        if(numConsumed==oldSize){
          CheckedCollection.checkModCount(modCount,root.modCount);
          bubbleUpClear(++modCount,oldSize,head,tail);
          break;
        }
        ++numConsumed;
        if(!filter.test((headCandidate=headCandidate.next).val)){
          var tailCandidate=tail;
          for(;;){
            if(numConsumed==oldSize){
              CheckedCollection.checkModCount(modCount,root.modCount);
              --numConsumed;
              break;
            }
            ++numConsumed;
            if(!filter.test((tailCandidate=tailCandidate.prev).val)){
              numConsumed-=2-CheckedList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                  oldSize-numConsumed,tailCandidate,filter);
              break;
            }
          }
          bubbleUpCollapseHeadAndTail(++modCount,head,tail,headCandidate,tailCandidate,numConsumed);
          break;
        }
      }
      root.modCount=modCount;
      root.size-=numConsumed;
    }
    private OmniList.OfChar getDefaultSubList(Checked root,int headDist,int subListSize,int tailDist){
      Node subListHead,subListTail;
      if(headDist<=tailDist){
        subListHead=iterateForward(head,headDist);
        subListTail=tailDist<subListSize?iterateReverse(tail,tailDist):uncheckedIterateForward(subListHead,subListSize);
      }else{
        subListTail=iterateReverse(tail,tailDist);
        subListHead=headDist<subListSize?iterateForward(head,headDist):uncheckedIterateReverse(subListTail,subListSize);
      }
      return new CheckedSubList.Body(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfChar getEmptySubList(Checked root,int headDist){
      return new CheckedSubList.Body(root,this,headDist);
    }
    private OmniList.OfChar getSubList1(Checked root,int headDist,int tailDist){
      return new CheckedSubList.Body(root,this,staticGetNode(this,headDist,tailDist),headDist);
    }
    private void uncheckedItrRemove(Node lastRet,int modCount){
      if(lastRet==head){
        if(lastRet==tail){
          bubbleUpClear(modCount,1,lastRet,lastRet);
        }else{
          ((CheckedSubList)this).bubbleUpSuffixEraseHead(modCount,lastRet,lastRet.next);
        }
      }else{
        if(lastRet==tail){
          super.bubbleUpPrefixEraseTail(modCount,lastRet,lastRet.prev);
        }else{
          ((CheckedSubList)this).bubbleUpDecrementSize(modCount);
          joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }
  }
  static class Prefix extends CheckedSubList{
    Prefix(Checked root,CheckedSubList parent){
      super(root,parent);
    }
    Prefix(Checked root,CheckedSubList parent,Node onlyNode){
      super(root,parent,onlyNode);
    }
    Prefix(Checked root,CheckedSubList parent,Node head,int size,Node tail){
      super(root,parent,head,size,tail);
    }
    @Override public void clear(){
      int modCount;
      Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        Node tmp;
        bubbleUpClear(modCount,size,tmp=tail,tmp=tmp.next);
        staticSetHead(root,tmp);
        root.size-=size;
      }
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
      int subListSize;
      switch(subListSize=toIndex-fromIndex){
      default:
        return getDefaultSubList(root,fromIndex,subListSize,size-toIndex);
      case 1:
        return getSubList1(root,fromIndex,size-toIndex);
      case 0:
        return getEmptySubList(root,fromIndex);
      }
    }
    @Override void appendHelper(char val,int modCount){
      this.modCount=modCount;
      Node newNode,oldTail;
      tail=newNode=new Node(oldTail=tail,val,oldTail.next);
      CheckedSubList parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpPrefixPushTail(modCount,newNode,oldTail);
      }
    }
    @Override void ascItrRemove(CheckedAscendingSubItr itr){
      Node lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount,root);
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void bidirectItrRemove(CheckedSubList.BidirectionalItr itr){
      Node lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount,root);
        if(lastRet!=itr.cursor){
          --itr.nextIndex;
        }
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void findNewHead(int modCount,Node curr,CharPredicate filter){
      final Node tail;
      if(curr==(tail=this.tail)){
        Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        bubbleUpClear(modCount,1,curr,curr=tail.next);
        staticSetHead(root,curr);
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(modCount,curr,tail,filter);
      }else{
        super.rootCollapseHead(modCount,curr.next,tail,filter);
      }
    }
    @Override void initHelper(Checked root,char val,int modCount){
      final Node after,newNode=new Node(val);
      for(CheckedSubList curr=this;;curr.size=1){
        curr.privateInit(newNode,modCount);
        if((curr=curr.parent)==null){
          after=root.head;
          break;
        }else if(curr.size!=0){
          after=curr.head;
          curr.bubbleUpRootPushHead(modCount,newNode);
          break;
        }
      }
      joinNodes(newNode,after);
    }
    @Override boolean removeIfHelper(int modCount,Node curr,CharPredicate filter){
      final Node tail;
      if(curr!=(tail=this.tail)){
        if(filter.test(tail.val)){
          prefixCollapseTail(modCount,curr,tail,filter);
          return true;
        }
        return super.collapseBody(modCount,curr,tail,filter);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override Node uncheckedExtractLastNode(){
      Checked root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      final Node next,lastNode;
      staticSetHead(root,next=(lastNode=tail).next);
      bubbleUpClear(modCount,1,lastNode,next);
      --root.size;
      return lastNode;
    }
    @Override Node uncheckedExtractTail(int newSize){
      Checked root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      Node oldTail;
      bubbleUpPrefixEraseTail(modCount,oldTail=tail,oldTail.prev);
      --root.size;
      return oldTail;
    }
    @Override boolean uncheckedRemoveFirstMatchHelper(int modCount,Node curr,int val){
      final var root=this.root;
      final var tail=this.tail;
      Node prev;
      do{
        if(curr==tail){
          CheckedCollection.checkModCount(modCount,root.modCount);
          return false;
        }
      }while((curr=(prev=curr).next).val!=val);
      CheckedCollection.checkModCount(modCount,root.modCount);
      root.modCount=++modCount;
      --root.size;
      if(curr==tail){
        bubbleUpPrefixEraseTail(modCount,curr,prev);
      }else{
        super.bubbleUpDecrementSize(modCount);
        joinNodes(prev,curr.next);
      }
      return true;
    }
    private void bubbleUpClear(int modCount,int size,Node tail,final Node next){
      for(CheckedSubList curr=this;;){
        curr.modCount=modCount;
        staticClear(curr);
        if((curr=curr.parent)==null){
          break;
        }else if(curr.tail!=tail){
          curr.bubbleUpRootCollapseHead(modCount,size,next);
          break;
        }
      }
    }
    private void bubbleUpCollapseHeadAndTail(int newModCount,Node oldTail,Node newHead,Node newTail,int numRemoved){
      final int newSize=size-numRemoved;
      for(CheckedSubList curr=this;;){
        curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
        if((curr=curr.parent)==null){
          break;
        }
        if(curr.tail!=oldTail){
          curr.bubbleUpRootCollapseHead(newModCount,numRemoved,newHead);
          break;
        }
      }
      joinNodes(newTail,oldTail.next);
    }
    private void bubbleUpPrefixEraseTail(int newModCount,Node oldTail,Node newTail){
      CheckedSubList curr=this;
      do{
        curr.modCount=newModCount;
        --curr.size;
        curr.tail=newTail;
        if((curr=curr.parent)==null){ return; }
      }while(curr.tail==oldTail);
      curr.bubbleUpDecrementSize(newModCount);
      joinNodes(newTail,oldTail.next);
    }
    private void collapseHeadAndTail(int modCount,Node headCandidate,Node tail,CharPredicate filter){
      final int oldSize=size;
      int numConsumed=2;
      final var root=this.root;
      for(;;){
        if(numConsumed==oldSize){
          CheckedCollection.checkModCount(modCount,root.modCount);
          bubbleUpClear(++modCount,oldSize,tail,headCandidate=tail.next);
          break;
        }
        ++numConsumed;
        if(!filter.test((headCandidate=headCandidate.next).val)){
          var tailCandidate=tail;
          for(;;){
            if(numConsumed==oldSize){
              CheckedCollection.checkModCount(modCount,root.modCount);
              --numConsumed;
              break;
            }
            ++numConsumed;
            if(!filter.test((tailCandidate=tailCandidate.prev).val)){
              numConsumed-=2-CheckedList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                  oldSize-numConsumed,tailCandidate,filter);
              break;
            }
          }
          bubbleUpCollapseHeadAndTail(++modCount,tail,headCandidate,tailCandidate,numConsumed);
          break;
        }
      }
      root.modCount=modCount;
      staticSetHead(root,headCandidate);
      root.size-=numConsumed;
    }
    private OmniList.OfChar getDefaultSubList(Checked root,int headDist,int subListSize,int tailDist){
      Node subListHead=head,subListTail=tail;
      if(headDist==0){ return new CheckedSubList.Prefix(root,this,subListHead,subListSize,tailDist<=subListSize
          ?iterateReverse(subListTail,tailDist):uncheckedIterateForward(subListHead,subListSize)); }
      if(headDist<=tailDist){
        subListHead=uncheckedIterateForward(subListHead,headDist);
        subListTail=tailDist<=subListSize?iterateReverse(subListTail,tailDist)
            :uncheckedIterateForward(subListHead,subListSize);
      }else{
        subListTail=iterateReverse(subListTail,tailDist);
        subListHead=headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
            :uncheckedIterateReverse(subListTail,subListSize);
      }
      return new CheckedSubList.Body(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfChar getEmptySubList(Checked root,int headDist){
      if(headDist==0){ return new CheckedSubList.Prefix(root,this); }
      return new CheckedSubList.Body(root,this,headDist);
    }
    private OmniList.OfChar getSubList1(Checked root,int headDist,int tailDist){
      if(headDist==0){ return new CheckedSubList.Prefix(root,this,head); }
      return new CheckedSubList.Body(root,this,
          tailDist<headDist?iterateReverse(tail,tailDist):uncheckedIterateForward(head,headDist),headDist);
    }
    private void prefixCollapseTail(int modCount,Node oldHead,Node oldTail,CharPredicate filter){
      final int oldSize=size;
      int numConsumed=2;
      final var root=this.root;
      var tailCandidate=oldTail.prev;
      for(;;tailCandidate=tailCandidate.prev){
        if(numConsumed==oldSize){
          CheckedCollection.checkModCount(modCount,root.modCount);
          --numConsumed;
          break;
        }
        ++numConsumed;
        if(!filter.test(tailCandidate.val)){
          numConsumed-=2-CheckedList.collapseBodyHelper(root.new ModCountChecker(modCount),oldHead,oldSize-numConsumed,
              tailCandidate,filter);
          break;
        }
      }
      root.modCount=++modCount;
      super.bubbleUpPrefixCollapseTail(modCount,numConsumed,oldTail,tailCandidate);
      joinNodes(tailCandidate,oldTail.next);
      root.size-=numConsumed;
    }
    private void uncheckedItrRemove(Node lastRet,int modCount,Checked root){
      if(lastRet==head){
        if(lastRet==tail){
          bubbleUpClear(modCount,1,lastRet,lastRet=lastRet.next);
        }else{
          super.bubbleUpRootEraseHead(modCount,lastRet=lastRet.next);
        }
        staticSetHead(root,lastRet);
      }else{
        if(lastRet==tail){
          bubbleUpPrefixEraseTail(modCount,lastRet,lastRet.prev);
        }else{
          super.bubbleUpDecrementSize(modCount);
          joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }
  }
  static class Suffix extends CheckedSubList{
    Suffix(Checked root,CheckedSubList parent){
      super(root,parent);
    }
    Suffix(Checked root,CheckedSubList parent,Node onlyNode){
      super(root,parent,onlyNode);
    }
    Suffix(Checked root,CheckedSubList parent,Node head,int size,Node tail){
      super(root,parent,head,size,tail);
    }
    @Override public void clear(){
      int modCount;
      Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        Node tmp;
        bubbleUpClear(modCount,size,tmp=head,tmp=tmp.prev);
        staticSetTail(root,tmp);
        root.size-=size;
      }
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
      final int tailDist=size-toIndex;
      int subListSize;
      switch(subListSize=toIndex-fromIndex){
      default:
        return getDefaultSubList(root,fromIndex,subListSize,tailDist);
      case 1:
        return getSubList1(root,fromIndex,tailDist);
      case 0:
        return getEmptySubList(root,fromIndex,tailDist);
      }
    }
    @Override void ascItrRemove(CheckedAscendingSubItr itr){
      Node lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount,root);
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void bidirectItrRemove(CheckedSubList.BidirectionalItr itr){
      Node lastRet;
      if((lastRet=itr.lastRet)!=null){
        int modCount;
        Checked root;
        CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        uncheckedItrRemove(lastRet,modCount,root);
        if(lastRet!=itr.cursor){
          --itr.nextIndex;
        }
        itr.modCount=modCount;
        --root.size;
        return;
      }
      throw new IllegalStateException();
    }
    @Override void findNewHead(int modCount,Node curr,CharPredicate filter){
      final Node tail;
      if(curr==(tail=this.tail)){
        Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        bubbleUpClear(modCount,1,curr,curr=curr.prev);
        staticSetTail(root,curr);
        --root.size;
      }else if(filter.test(tail.val)){
        collapseHeadAndTail(modCount,curr,tail,filter);
      }else{
        super.suffixCollapseHead(modCount,curr,tail,filter);
      }
    }
    @Override void initHelper(Checked root,char val,int modCount){
      final Node before,newNode=new Node(val);
      for(CheckedSubList curr=this;;curr.size=1){
        curr.privateInit(newNode,modCount);
        if((curr=curr.parent)==null){
          before=root.tail;
          break;
        }else if(curr.size!=0){
          before=curr.tail;
          curr.bubbleUpRootPushTail(modCount,newNode);
          break;
        }
      }
      joinNodes(before,newNode);
    }
    @Override void prependHelper(char val,int modCount){
      this.modCount=modCount;
      Node newNode,oldHead;
      head=newNode=new Node((oldHead=head).prev,val,oldHead);
      CheckedSubList parent;
      if((parent=this.parent)!=null){
        parent.bubbleUpSuffixPushHead(modCount,newNode,oldHead);
      }
    }
    @Override void removeFirstHelper(int modCount,Node curr){
      Checked root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      if(curr==tail){
        bubbleUpClear(modCount,1,curr,curr=curr.prev);
        staticSetTail(root,curr);
      }else{
        super.bubbleUpSuffixEraseHead(modCount,curr,curr.next);
      }
      --root.size;
    }
    @Override Node uncheckedExtractHead(int newSize){
      Checked root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      Node oldHead;
      super.bubbleUpSuffixEraseHead(modCount,oldHead=head,oldHead.next);
      --root.size;
      return oldHead;
    }
    @Override Node uncheckedExtractLastNode(){
      int modCount;
      Checked root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      final Node prev,lastNode;
      staticSetTail(root,prev=(lastNode=head).prev);
      bubbleUpClear(modCount,1,lastNode,prev);
      --root.size;
      return lastNode;
    }
    private void bubbleUpClear(int modCount,int size,Node head,final Node prev){
      for(CheckedSubList curr=this;;){
        curr.modCount=modCount;
        staticClear(curr);
        if((curr=curr.parent)==null){
          break;
        }else if(curr.head!=head){
          curr.bubbleUpRootCollapseTail(modCount,size,prev);
          break;
        }
      }
    }
    private void bubbleUpCollapseHeadAndTail(int newModCount,Node oldHead,Node newHead,Node newTail,int numRemoved){
      final int newSize=size-numRemoved;
      for(CheckedSubList curr=this;;){
        curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
        if((curr=curr.parent)==null){
          break;
        }
        if(curr.head!=oldHead){
          curr.bubbleUpRootCollapseTail(newModCount,numRemoved,newTail);
          break;
        }
      }
      joinNodes(oldHead.prev,newHead);
    }
    private void collapseHeadAndTail(int modCount,Node head,Node tailCandidate,CharPredicate filter){
      final int oldSize=size;
      int numConsumed=2;
      final var root=this.root;
      for(;;){
        if(numConsumed==oldSize){
          CheckedCollection.checkModCount(modCount,root.modCount);
          bubbleUpClear(++modCount,oldSize,head,tailCandidate=head.prev);
          break;
        }
        ++numConsumed;
        if(!filter.test((tailCandidate=tailCandidate.prev).val)){
          var headCandidate=head;
          for(;;){
            if(numConsumed==oldSize){
              CheckedCollection.checkModCount(modCount,root.modCount);
              --numConsumed;
              break;
            }
            ++numConsumed;
            if(!filter.test((headCandidate=headCandidate.next).val)){
              numConsumed-=2-+CheckedList.collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                  oldSize-numConsumed,tailCandidate,filter);
              break;
            }
          }
          bubbleUpCollapseHeadAndTail(++modCount,head,headCandidate,tailCandidate,numConsumed);
          break;
        }
      }
      root.modCount=modCount;
      staticSetTail(root,tailCandidate);
      root.size-=numConsumed;
    }
    private OmniList.OfChar getDefaultSubList(Checked root,int headDist,int subListSize,int tailDist){
      Node subListHead=head,subListTail=tail;
      if(tailDist==0){ return new CheckedSubList.Suffix(root,this,headDist<=subListSize
          ?iterateForward(subListHead,headDist):uncheckedIterateReverse(subListTail,subListSize)); }
      if(tailDist<=headDist){
        subListTail=uncheckedIterateReverse(subListTail,tailDist);
        subListHead=headDist<=subListSize?iterateForward(subListHead,headDist)
            :uncheckedIterateReverse(subListTail,subListSize);
      }else{
        subListHead=iterateForward(subListHead,headDist);
        subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
            :uncheckedIterateForward(subListHead,subListSize);
      }
      return new CheckedSubList.Body(root,this,subListHead,subListSize,subListTail,headDist);
    }
    private OmniList.OfChar getEmptySubList(Checked root,int headDist,int tailDist){
      if(tailDist==0){ return new CheckedSubList.Prefix(root,this); }
      return new CheckedSubList.Body(root,this,headDist);
    }
    private OmniList.OfChar getSubList1(Checked root,int headDist,int tailDist){
      if(tailDist==0){ return new CheckedSubList.Suffix(root,this,tail); }
      return new CheckedSubList.Body(root,this,staticGetNode(this,headDist,tailDist),headDist);
    }
    private void uncheckedItrRemove(Node lastRet,int modCount,Checked root){
      if(lastRet==tail){
        if(lastRet==head){
          bubbleUpClear(modCount,1,lastRet,lastRet=lastRet.prev);
        }else{
          super.bubbleUpRootEraseTail(modCount,lastRet=lastRet.prev);
        }
        staticSetTail(root,lastRet);
      }else{
        if(lastRet==head){
          super.bubbleUpSuffixEraseHead(modCount,lastRet,lastRet.next);
        }else{
          super.bubbleUpDecrementSize(modCount);
          joinNodes(lastRet.prev,lastRet.next);
        }
      }
    }
  }
  private static class BidirectionalItr extends CheckedAscendingSubItr implements OmniListIterator.OfChar{
    private transient int nextIndex;
    private BidirectionalItr(CheckedSubList parent){
      super(parent);
    }
    private BidirectionalItr(CheckedSubList parent,Node cursor,int nextIndex){
      super(parent,cursor);
      this.nextIndex=nextIndex;
    }
    @Override public void add(char val){
      int modCount;
      Checked root;
      CheckedSubList parent;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
      root.modCount=++modCount;
      final int nextIndex=this.nextIndex++;
      int size;
      if((size=parent.size++)!=0){
        if(nextIndex==0){
          parent.prependHelper(val,modCount);
        }else if(nextIndex==size){
          parent.appendHelper(val,modCount);
        }else{
          Node cursor;
          parent.insertHelper((cursor=this.cursor).prev,val,cursor,modCount);
        }
      }else{
        parent.initHelper(root,val,modCount);
      }
      ++root.size;
      this.modCount=modCount;
      lastRet=null;
    }
    @Override public void forEachRemaining(Consumer<? super Character> action){
      Node cursor;
      if((cursor=this.cursor)!=null){
        final int modCount=this.modCount;
        final var parent=this.parent;
        try{
          uncheckedForEachForward(cursor,cursor=parent.tail,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
        }
        nextIndex=parent.size;
        this.cursor=null;
        lastRet=cursor;
      }
    }
    @Override public void forEachRemaining(CharConsumer action){
      Node cursor;
      if((cursor=this.cursor)!=null){
        final int modCount=this.modCount;
        final var parent=this.parent;
        try{
          uncheckedForEachForward(cursor,cursor=parent.tail,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
        }
        nextIndex=parent.size;
        this.cursor=null;
        lastRet=cursor;
      }
    }
    @Override public boolean hasPrevious(){
      return nextIndex!=0;
    }
    @Override public int nextIndex(){
      return nextIndex;
    }
    @Override public char nextChar(){
      CheckedSubList parent;
      CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
      Node cursor;
      if((cursor=this.cursor)!=null){
        if(cursor==parent.tail){
          this.cursor=null;
        }else{
          this.cursor=cursor.next;
        }
        ++nextIndex;
        lastRet=cursor;
        return cursor.val;
      }
      throw new NoSuchElementException();
    }
    @Override public int previousIndex(){
      return nextIndex-1;
    }
    @Override public char previousChar(){
      CheckedCollection.checkModCount(modCount,parent.root.modCount);
      int nextIndex;
      if((nextIndex=this.nextIndex)!=0){
        this.nextIndex=nextIndex-1;
        Node cursor;
        this.cursor=cursor=this.cursor.prev;
        lastRet=cursor;
        return cursor.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void remove(){
      parent.bidirectItrRemove(this);
      lastRet=null;
    }
    @Override public void set(char val){
      Node lastRet;
      if((lastRet=this.lastRet)!=null){
        CheckedCollection.checkModCount(modCount,parent.root.modCount);
        lastRet.val=val;
      }
      throw new IllegalStateException();
    }
  }
}
