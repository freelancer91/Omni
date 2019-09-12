package omni.impl.seq;
import java.util.Collection;
import omni.api.OmniCollection;
import java.util.ListIterator;
import java.util.List;
import omni.util.RefSortUtil;
import omni.api.OmniList;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import omni.util.OmniArray;
import java.util.Objects;
import omni.util.OmniPred;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
import omni.impl.AbstractOmniCollection;
import java.io.Externalizable;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollection;
public abstract class RefDblLnkSeq<E> extends 
AbstractOmniCollection<E>
 implements
   OmniList.OfRef<E>
{
  static final class Node<E>{
    transient Node<E> prev;
    transient E val;
    transient Node<E> next;
    Node(E val){
      this.val=val;
    }
    Node(Node<E> prev,E val){
      this.prev=prev;
      this.val=val;
    }
    Node(E val,Node<E> next){
      this.val=val;
      this.next=next;
    }
    Node(Node<E> prev,E val,Node<E> next){
      this.prev=prev;
      this.val=val;
      this.next=next;
    }
    @SuppressWarnings("unchecked")
    static <E> void uncheckedCopyFrom(Object[] src,int length,Node<E> dst){
      for(;;dst=dst.prev)
      {
        dst.val=(E)src[--length];
        if(length==0)
        {
          return;
        }
      }
    }
    static <E> void uncheckedToString(Node<E> curr,int size,StringBuilder builder){
      for(builder.append(curr.val);--size!=0;builder.append(',').append(' ').append((curr=curr.next).val)){}
    }
    static <E> void uncheckedToString(Node<E> curr,Node<E> tail,StringBuilder builder){
      for(;;curr=curr.next,builder.append(',').append(' ')){
        builder.append(curr.val);
        if(curr==tail){
          return;
        }
      }
    }
    static <E> int uncheckedHashCode(Node<E> curr,Node<E> tail){
      int hash=31+Objects.hashCode(curr.val);
      while(curr!=tail){
        hash=(hash*31)+Objects.hashCode((curr=curr.next).val);
      }
      return hash;
    }
    static <E> int uncheckedHashCode(Node<E> curr,int size){
      int hash=31+Objects.hashCode(curr.val);
      while(--size!=0){
        hash=(hash*31)+Objects.hashCode((curr=curr.next).val);
      }
      return hash;
    }
    static <E> void uncheckedForEachAscending(Node<E> node,int size,Consumer<? super E> action){
      for(;;node=node.next){
        action.accept(node.val);
        if(--size==0){
          return;
        }
      }
    }
    static <E> void uncheckedReplaceAll(Node<E> node,int size,UnaryOperator<E> operator){
      for(;;node=node.next){
        node.val=operator.apply(node.val);
        if(--size==0){
          return;
        }
      }
    }
    static <E> void uncheckedForEachAscending(Node<E> node,Consumer<? super E> action){
      for(;;){
        action.accept(node.val);
        if((node=node.next)==null){
          return;
        }
      }
    }
    static <E> void uncheckedForEachAscending(Node<E> node,Node<E> tail,Consumer<? super E> action){
      for(;;node=node.next){
        action.accept(node.val);
        if(node==tail){
          return;
        }
      }
    }
    static <E> void uncheckedReplaceAll(Node<E> node,Node<E> tail,UnaryOperator<E> operator){
      for(;;node=node.next){
        node.val=operator.apply(node.val);
        if(node==tail){
          return;
        }
      }
    }
    static <E> void uncheckedForEachDescending(Node<E> node,Consumer<? super E> action){
      for(;;){
        action.accept(node.val);
        if((node=node.prev)==null){
          return;
        }
      }
    }
    static <E> void uncheckedForEachDescending(Node<E> node,int size,Consumer<? super E> action){
      for(;;node=node.prev){
        action.accept(node.val);
        if(--size==0){
          return;
        }
      }
    }
    static <E> void eraseNode(Node<E> node){
      Node<E> next,prev;
      (next=node.next).prev=(prev=node.prev);
      prev.next=next;
    }
    static <E> Node<E> iterateAscending(Node<E> node,int length){
      if(length!=0){
        do{
          node=node.next;
        }while(--length!=0);
      }
      return node;
    }
    static <E> Node<E> iterateDescending(Node<E> node,int length){
      if(length!=0){
        do{
          node=node.prev;
        }while(--length!=0);
      }
      return node;
    }
    static <E> Node<E> uncheckedIterateDescending(Node<E> node,int length){
      do{
        node=node.prev;
      }while(--length!=0);
      return node;
    }
    static <E> boolean uncheckedcontainsNonNull(Node<E> head,int size,Object nonNull){
      for(;!nonNull.equals(head.val);head=head.next){if(--size==0){return false;}}
      return true;
    }
    static <E> int uncheckedsearchNonNull(Node<E> head,int size,Object nonNull){
      int index=1;
      for(;!nonNull.equals(head.val);++index,head=head.next){if(--size==0){return -1;}}
      return index;
    }
    static <E> int uncheckedindexOfNonNull(Node<E> head,int size,Object nonNull){
      int index=0;
      for(;!nonNull.equals(head.val);++index,head=head.next){if(--size==0){return -1;}}
      return index;
    }
    static <E> boolean uncheckedcontainsNonNull(Node<E> head,Node<E> tail
    ,Object nonNull
    ){
      for(;!nonNull.equals(head.val);head=head.next){if(head==tail){return false;}}
      return true;
    }
    static <E> int uncheckedsearchNonNull(Node<E> head
    ,Object nonNull
    ){
      int index=1;
      for(;!nonNull.equals(head.val);++index){if((head=head.next)==null){return -1;}}
      return index;
    }
    static <E> int uncheckedindexOfNonNull(Node<E> head,Node<E> tail
    ,Object nonNull
    ){
      int index=0;
      for(;!nonNull.equals(head.val);++index,head=head.next){if(head==tail){return -1;}}
      return index;
    }
    static <E> int uncheckedlastIndexOfNonNull(int length,Node<E> tail
    ,Object nonNull
    ){
      for(;!nonNull.equals(tail.val);tail=tail.prev){if(--length==0){return -1;}}
      return length-1;
    }
    static <E> boolean uncheckedcontainsNull(Node<E> head,Node<E> tail
    ){
      for(;null!=(head.val);head=head.next){if(head==tail){return false;}}
      return true;
    }
    static <E> int uncheckedsearchNull(Node<E> head
    ){
      int index=1;
      for(;null!=(head.val);++index){if((head=head.next)==null){return -1;}}
      return index;
    }
    static <E> int uncheckedindexOfNull(Node<E> head,Node<E> tail
    ){
      int index=0;
      for(;null!=(head.val);++index,head=head.next){if(head==tail){return -1;}}
      return index;
    }
    static <E> int uncheckedlastIndexOfNull(int length,Node<E> tail
    ){
      for(;null!=(tail.val);tail=tail.prev){if(--length==0){return -1;}}
      return length-1;
    }
    static <E> boolean uncheckedcontains (Node<E> head,Node<E> tail
    ,Predicate<? super E> pred
    ){
      for(;!pred.test(head.val);head=head.next){if(head==tail){return false;}}
      return true;
    }
    static <E> int uncheckedsearch (Node<E> head
    ,Predicate<? super E> pred
    ){
      int index=1;
      for(;!pred.test(head.val);++index){if((head=head.next)==null){return -1;}}
      return index;
    }
    static <E> int uncheckedindexOf (Node<E> head,Node<E> tail
    ,Predicate<? super E> pred
    ){
      int index=0;
      for(;!pred.test(head.val);++index,head=head.next){if(head==tail){return -1;}}
      return index;
    }
    static <E> int uncheckedlastIndexOf (int length,Node<E> tail
    ,Predicate<? super E> pred
    ){
      for(;!pred.test(tail.val);tail=tail.prev){if(--length==0){return -1;}}
      return length-1;
    }
    static <E> void uncheckedCopyInto(Object[] dst,Node<E> curr,int length){
      for(;;curr=curr.prev){
        dst[--length]=(curr.val);
        if(length==0){
          return;
        }
      }
    }
  }
  private static final long serialVersionUID=1L;
  transient Node<E> head;
  transient Node<E> tail;
  private RefDblLnkSeq(Collection<? extends E> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private RefDblLnkSeq(OmniCollection.OfRef<? extends E> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private  RefDblLnkSeq(){
  }
  private RefDblLnkSeq(Node<E> head,int size,Node<E> tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  abstract void addLast(E val);
  @Override public boolean add(E val){
    addLast(val);
    return true;
  }
  private void iterateDescendingAndInsert(int dist,Node<E> after,Node<E> newNode){
    newNode.next=after=Node.iterateDescending(after,dist-2);
    final Node<E> before;
    newNode.prev=before=after.prev;
    before.next=newNode;
    after.prev=newNode;
  }
  private void iterateAscendingAndInsert(int dist,Node<E> before,Node<E> newNode){
    newNode.prev=before=Node.iterateAscending(before,dist-1);
    final Node<E> after;
    newNode.next=after=before.next;
    before.next=newNode;
    after.prev=newNode;
  }
  private void insertNode(int index,Node<E> newNode){
    int tailDist;
    if((tailDist=this.size-index)<=index){
      //the insertion point is closer to the tail
      var tail=this.tail;
      if(tailDist==1){
        //the insertion point IS the tail
        newNode.prev=tail;
        tail.next=newNode;
        this.tail=newNode;
      }else{
        //iterate from the root's tail
        iterateDescendingAndInsert(tailDist,tail,newNode);
      }
    }else{
      //the insertion point is closer to the head
      if(index==0){
        //the insertion point IS the head
        Node<E> head;
        (head=this.head).prev=newNode;
        newNode.next=head;
        this.head=newNode;
      }else{
        //iterate from the root's head 
        iterateAscendingAndInsert(index,this.head,newNode);
      }
    }
  }
  private static <E> int markSurvivors(Node<E> curr,int numLeft,Predicate<? super E> filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test(curr.val)){
          word|=marker;
          ++numSurvivors;
        }
        if(--numLeft==0)
        {
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
        curr=curr.next;
      }while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  private static <E> long markSurvivors(Node<E> curr,int numLeft,Predicate<? super E> filter){
    for(long word=0L,marker=1L;;marker<<=1,curr=curr.next){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if(--numLeft==0)
      {
        return word;
      }
    }
  }
  private Node<E> getNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      return Node.iterateDescending(tail,size-1);
    }else{
      //the node is closer to the head
      return Node.iterateAscending(head,index);
    }
  }
  private Node<E> getItrNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      switch(size){
      case 0:
        return null;
      case 1:
        return tail;
      default:
        return Node.uncheckedIterateDescending(tail,size-1);
      }
    }else{
      //the node is closer to the head
      return Node.iterateAscending(head,index);
    }
  }
  @Override public E set(int index,E val){
    Node<E> tmp;
    final var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,E val){
    getNode(index,size).val=val;
  }
  @Override public E get(int index){
    return getNode(index,size).val;
  }
  @Override public void forEach(Consumer<? super E> action){
    final Node<E> head;
    if((head=this.head)!=null){
      Node.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      Node.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      Node.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public Object[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Object[] dst;
      Node.uncheckedCopyInto(dst=new Object[size],tail,size);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  @Override public void replaceAll(UnaryOperator<E> operator){
    final Node<E> head;
    if((head=this.head)!=null){
      Node.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void sort(Comparator<? super E> sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final Object[] tmp;
      final Node<E> tail;
      Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      if(sorter==null){
        RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
      }else{
        RefSortUtil.uncheckedStableSort(tmp,0,size,sorter);
      }
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final Object[] tmp;
      final Node<E> tail;
      Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final Object[] tmp;
      final Node<E> tail;
      Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      RefSortUtil.uncheckedStableDescendingSort(tmp,0,size);
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableAscendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final Object[] tmp;
      final Node<E> tail;
      Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableDescendingSort()
  {
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final Object[] tmp;
      final Node<E> tail;
      Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      RefSortUtil.uncheckedUnstableDescendingSort(tmp,0,size);
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(Comparator<? super E> sorter){
    //todo: see about making an in-place sort implementation rather than copying to an array
    final int size;
    if((size=this.size)>1){
      final Object[] tmp;
      final Node<E> tail;
      Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      if(sorter==null){
        RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
      }else{
        RefSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
      }
      Node.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final Node<E> head;
    if((head=this.head)!=null){
      final StringBuilder builder;
      Node.uncheckedToString(head,tail,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public int hashCode(){
    final Node<E> head;
    if((head=this.head)!=null){
      return Node.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedcontainsNonNull(head,tail,val);
          }
          return Node.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(short val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Boolean val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }
          return Node.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Byte val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }
          return Node.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Character val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
          }
          return Node.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Short val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
          }
          return Node.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Integer val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
          }
          return Node.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Long val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
          }
          return Node.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Float val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
          }
          return Node.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Double val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return Node.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedindexOfNonNull(head,tail,val);
          }
          return Node.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(byte val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(short val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Boolean val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }
          return Node.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Byte val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }
          return Node.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Character val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
          }
          return Node.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Short val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
          }
          return Node.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Integer val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
          }
          return Node.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Long val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
          }
          return Node.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Float val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
          }
          return Node.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Double val){
    {
      {
        final Node<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return Node.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return Node.uncheckedlastIndexOfNonNull(size,tail,val);
          }
          return Node.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(byte val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(char val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(short val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Boolean val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }
          return Node.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Byte val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }
          return Node.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Character val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
          }
          return Node.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Short val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
          }
          return Node.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Integer val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
          }
          return Node.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Long val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
          }
          return Node.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Float val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
          }
          return Node.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Double val){
    {
      {
        final Node<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return Node.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  private static <E> int collapseBodyHelper(Node<E> newHead,Node<E> newTail,Predicate<? super E> filter){
    int numRemoved=0;
    outer:for(Node<E> prev;(newHead=(prev=newHead).next)!=newTail;){
      if(filter.test(newHead.val)){
        do{
          ++numRemoved;
          if((newHead=newHead.next)==newTail){
            newHead.prev=prev;
            prev.next=newHead;
            break outer;
          }
        }while(filter.test(newHead.val));
        newHead.prev=prev;
        prev.next=newHead;
      }
    }
    return numRemoved;
  }
  static class UncheckedSubList<E> extends RefDblLnkSeq<E>{
    private static final long serialVersionUID=1L;
    transient final UncheckedList<E> root;
    transient final UncheckedSubList<E> parent;
    transient final int parentOffset;
    private UncheckedSubList(UncheckedList<E> root,int rootOffset,Node<E> head,int size,Node<E> tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList<E> parent,int parentOffset,Node<E> head,int size,Node<E> tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
    }
    private UncheckedSubList(UncheckedList<E> root,int rootOffset){
      super();
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList<E> parent,int parentOffset){
      super();
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
    }
    private Object writeReplace(){
      return new UncheckedList<E>(this.head,this.size,this.tail);
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){
      }
    }
    private void bubbleUpAppend(Node<E> oldTail,Node<E> newTail){
      oldTail.next=newTail;
      this.tail=newTail;
      for(var currList=parent;currList!=null;currList.tail=newTail,currList=currList.parent){
        ++currList.size;
        if(currList.tail!=oldTail){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpAppend(Node<E> newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
      }
    }
    private void bubbleUpPrepend(Node<E> oldHead,Node<E> newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;currList.head=newHead,currList=currList.parent){
        ++currList.size;
        if(currList.head!=oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(Node<E> newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
      }
    }
    private void bubbleUpRootInit(Node<E> newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,Node<E> newNode){
      Node<E> after,before;   
      if((size-=index)<=index){
        before=this.tail;
        if(size==1){
          if((after=before.next)==null){
            this.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            this.bubbleUpAppend(before,newNode);
            after.prev=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          before=(after=Node.iterateDescending(before,size-2)).prev;
          after.prev=newNode;
        }
        before.next=newNode;        
      }else{
        after=this.head;
        if(index==0){
          if((before=after.prev)==null){
            this.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            this.bubbleUpPrepend(after,newNode);
            before.next=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          after=(before=Node.iterateAscending(after,index-1)).next;
          before.next=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(Node<E> newNode){
      this.head=newNode;
      this.tail=newNode;
      UncheckedSubList<E> curr;
      for(var currParent=(curr=this).parent;currParent!=null;currParent=(curr=currParent).parent){
        int parentSize;
        if((parentSize=++currParent.size)!=1){
          currParent.bubbleUpInitHelper(curr.parentOffset,parentSize,newNode);
          return;
        }
        currParent.head=newNode;
        currParent.tail=newNode;
      }
      ((RefDblLnkSeq<E>)root).insertNode(curr.parentOffset,newNode);
    }
    @Override public void add(int index,E val){
      final UncheckedList<E> root;
      final var newNode=new Node<E>(val);
      if(++(root=this.root).size!=1){
        final int currSize;
        if((currSize=++this.size)!=1){
          bubbleUpInitHelper(index,currSize,newNode);
        }else{
          bubbleUpInit(newNode);
        }
      }else{
        bubbleUpRootInit(newNode);
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override void addLast(E val){
      final UncheckedList<E> root;
      if(++(root=this.root).size!=1){
        if(++this.size!=1){
          Node<E> currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new Node<E>(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new Node<E>(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new Node<E>(val));
        }
      }else{
        Node<E> newNode;
        bubbleUpRootInit(newNode=new Node<E>(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractOmniCollection){
          if(list instanceof OmniList.OfRef){
            return root.isEqualTo(this.head,size,(OmniList.OfRef<?>)list);
          }else if(list instanceof OmniList.OfInt){
            return root.isEqualTo(this.head,size,(OmniList.OfInt)list);
          }else if(list instanceof OmniList.OfFloat){
            return root.isEqualTo(this.head,size,(OmniList.OfFloat)list);
          }else if(list instanceof OmniList.OfLong){
            return root.isEqualTo(this.head,size,(OmniList.OfLong)list);
          }else if(list instanceof OmniList.OfDouble){
            return root.isEqualTo(this.head,size,(OmniList.OfDouble)list);
          }else if(list instanceof OmniList.OfByte){
            return root.isEqualTo(this.head,size,(OmniList.OfByte)list);
          }else if(list instanceof OmniList.OfChar){
            return root.isEqualTo(this.head,size,(OmniList.OfChar)list);
          }else if(list instanceof OmniList.OfShort){
            return root.isEqualTo(this.head,size,(OmniList.OfShort)list);
          }else{
            return root.isEqualTo(this.head,size,(OmniList.OfBoolean)list);
          }
        }else{
          return UncheckedList.isEqualTo(list.listIterator(),this.head,this.tail);
        }
      }
      return false;
    }
    private void bubbleUpPeelHead(Node<E> newHead,Node<E> oldHead){
      newHead.prev=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.tail!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelHead(Node<E> newHead){
      var curr=this;
      do{
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(Node<E> newTail,Node<E> oldTail){
      newTail.next=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.head!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelTail(Node<E> newTail){
      var curr=this;
      do{
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)!=null);
    }
    private void uncheckedBubbleUpDecrementSize(){
      var curr=this;
      do{
        --curr.size;    
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpDecrementSize(){
       UncheckedSubList<E> parent;
       if((parent=this.parent)!=null){
         parent.uncheckedBubbleUpDecrementSize();
       }
    }
    private void peelTail(Node<E> newTail,Node<E> oldTail){
      this.tail=newTail;
      Node<E> after;
      if((after=oldTail.next)==null){
        final UncheckedSubList<E> parent;
        if((parent=this.parent)!=null){
          parent.bubbleUpPeelTail(newTail);
        }
        root.tail=newTail;
      }else{
        after.prev=newTail;
        for(var curr=parent;curr!=null;curr=curr.parent){
          if(curr.tail!=oldTail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.tail=newTail;
        }
      }
      newTail.next=after;
    }
    private void peelTail(Node<E> tail){
      Node<E> after,before;
      (before=tail.prev).next=(after=tail.next);
      this.tail=before;
      if(after==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          --curr.size;
          curr.tail=before;
        }
        root.tail=before;
      }else{
        after.prev=before;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.tail!=tail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.tail=before;
        }
      }
    }
    private void removeLastNode(Node<E> lastNode){
      Node<E> after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        UncheckedList<E> root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          after.prev=before;
          before.next=after;
          for(var curr=parent;curr!=null;curr=curr.parent){
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                --curr.size;
                curr.tail=before;
              }while((curr=curr.parent)!=null);
              break;
            }
            if(curr.tail!=lastNode){
              for(;;){
                --curr.size;
                curr.head=after;
                if((curr=curr.parent)==null){
                  break;
                }
                if(curr.head!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
              }
              break;
            }
            curr.head=null;
            curr.tail=null;
            curr.size=0;
          }
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(Node<E> head){
      Node<E> after,before;
      (after=head.next).prev=(before=head.prev);
      this.head=after;
      if(before==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          --curr.size;
          curr.head=after;
        }
        root.head=after;
      }else{
        before.next=after;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.head!=head){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          --curr.size;
          curr.head=after;
        }
      }
    }
    @Override public E remove(int index){
      final E ret;
      int size;
      if((size=(--this.size)-index)<=index){
        var tail=this.tail;
        if(size==0){
          ret=tail.val;
          if(index==0){
            removeLastNode(tail);
          }else{
            peelTail(tail);
          }
        }else{
          Node<E> before;
          ret=(before=( tail=Node.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
          bubbleUpDecrementSize();
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          peelHead(head);
        }else{
          Node<E> after;
          ret=(after=(head=Node.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(Predicate<? super E> filter){
      final Node<E> head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    private void collapsehead(Node<E> oldhead,Node<E> tail,Predicate<? super E> filter
    ){
      int numRemoved=1;
      Node<E> newhead;
      outer:
      for(newhead=oldhead.next;;
      ++numRemoved,newhead=newhead.next){ 
        if(newhead==tail){
          break;
        }
        if(!filter.test(newhead.val)){
          Node<E> prev,curr;
          for(curr=(prev=newhead).next;curr!=tail;curr=(prev=curr).next){
            if(filter.test(curr.val)){
              do{
                ++numRemoved;
                if((curr=curr.next)==tail){
                  curr.prev=prev;
                  prev.next=curr;
                  break outer;
                }
              }while(filter.test(curr.val));
              curr.prev=prev;
              prev.next=curr;
            }
          }
          break;
        }
      }
      UncheckedList<E> root;
      (root=this.root).size-=numRemoved;
      this.size-=numRemoved;
      this.head=newhead;
      Node<E> tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
        }
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(Node<E> oldtail,Node<E> head,Predicate<? super E> filter
    ){
      int numRemoved=1;
      Node<E> newtail;
      outer:
      for(newtail=oldtail.prev;;
      ++numRemoved,newtail=newtail.prev){ 
        if(newtail==head){
          break;
        }
        if(!filter.test(newtail.val)){
          Node<E> next,curr;
          for(curr=(next=newtail).prev;curr!=head;curr=(next=curr).prev){
            if(filter.test(curr.val)){
              do{
                ++numRemoved;
                if((curr=curr.prev)==head){
                  curr.next=next;
                  next.prev=curr;
                  break outer;
                }
              }while(filter.test(curr.val));
              curr.next=next;
              next.prev=curr;
            }
          }
          break;
        }
      }
      UncheckedList<E> root;
      (root=this.root).size-=numRemoved;
      this.size-=numRemoved;
      this.tail=newtail;
      Node<E> tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
        }
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void bubbleUpCollapseHeadAndTail(Node<E> oldHead,Node<E> newHead,int numRemoved,Node<E> newTail,Node<E> oldTail){
      this.head=newHead;
      this.tail=newTail;
      final Node<E> after,before=oldHead.prev;
      if((after=oldTail.next)==null){
        if(before==null){
          for(var parent=this.parent;parent!=null;
          parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){}
          UncheckedList<E> root;
          (root=this.root).head=newHead;
          root.tail=newTail;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.tail=newTail;
        }
      }else{
        after.prev=newTail;
        if(before==null){
          for(var parent=this.parent;parent!=null;
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.tail!=oldTail){
              do{
                parent.size-=numRemoved;
                parent.head=newHead;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.head=newHead;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                if(parent.tail!=oldTail){
                  parent.size-=numRemoved;
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
            if(parent.tail!=oldTail){
              for(;;){
                parent.size-=numRemoved;
                parent.head=newHead;
                if((parent=parent.parent)==null){
                  break;
                }
                if(parent.head!=oldHead){
                  parent.size-=numRemoved;
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
              }
              break;
            }
          }
        }
      }
      newHead.prev=before;
      newTail.next=after;
    }
    private boolean uncheckedRemoveIf(Node<E> head,Predicate<? super E> filter){
      Node<E> tail;
      {
        if(filter.test((tail=this.tail).val)){
          if(tail==head){
            --root.size;
            this.size=size-1;
            //only one node was in the list; remove it
            removeLastNode(head);
          }else{
            if(filter.test(head.val)){
              collapseHeadAndTail(head,tail,filter
              );
            }else{
              collapsetail(tail,head,filter
              );
            }
          }
          return true;
        }else{
          if(tail!=head){
            if(filter.test(head.val)){
              collapsehead(head,tail,filter
              );
              return true;
            }else{
              return collapseBody(head,tail,filter
              );
            }
          }
        }
      }
      return false;
    }
    @Override public void clear(){
      int size;
      if((size=this.size)!=0){
        final UncheckedList<E> root;
        (root=this.root).size-=size;
        clearAllHelper(size,this.head,this.tail,root);
      }
    }
    private void clearAllHelper(int size,Node<E> head,Node<E> tail,UncheckedList<E> root){
      Node<E> before,after=tail.next;
      if((before=head.prev)==null){
        //this sublist is not preceded by nodes
        if(after==null){
          bubbleUpClearAll();
          root.tail=null;
        }else{
          bubbleUpClearHead(tail,after,size);
          after.prev=null;
        }
        root.head=after;
      }else{
        before.next=after;
        if(after==null){
          bubbleUpClearTail(head,before,size);
          root.tail=before;
        }else{
          bubbleUpClearBody(before,head,size,tail,after);
          after.prev=before;
        }
      }
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    private void bubbleUpClearAll(){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
    }
    private void bubbleUpDecrementSize(int numRemoved){
      for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){
      }
    }
    private void bubbleUpClearBody(Node<E> before,Node<E> head,int numRemoved,Node<E> tail,Node<E> after){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          while(curr.tail==tail){
            curr.tail=before;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }else if(curr.tail!=tail){
          do{
            curr.head=after;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }while(curr.head==head);
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(Node<E> tail, Node<E> after,int numRemoved){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.tail!=tail){
          do{
            curr.head=after;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void bubbleUpClearTail(Node<E> head, Node<E> before,int numRemoved){
      for(var curr=parent;curr!=null;
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          do{
            curr.tail=before;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void collapseHeadAndTail(Node<E> head,Node<E> tail,Predicate<? super E> filter
    ){
      Node<E> newHead;
      if((newHead=head.next)!=tail){
        for(int numRemoved=2;;++numRemoved){
          if(!filter.test(newHead.val)){
            Node<E> prev;
            outer: for(var curr=(prev=newHead).next;curr!=tail;curr=(prev=curr).next){
              if(filter.test(curr.val)){
                do{
                  ++numRemoved;
                  if((curr=curr.next)==tail){
                    break outer;
                  }
                }while(filter.test(curr.val));
                prev.next=curr;
                curr.prev=prev;
              }
            }
            this.size-=numRemoved;
            root.size-=numRemoved; 
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,prev,tail);
            return;
          }else if((newHead=newHead.next)==tail){
            break;
          }
        }
      }
      UncheckedList<E> root;
      int size;
      (root=this.root).size-=(size=this.size);
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(Node<E> head,Node<E> tail,Predicate<? super E> filter
    ){
      for(Node<E> prev;(head=(prev=head).next)!=tail;){
        if(filter.test(head.val)){
          int numRemoved=1;
          for(;(head=head.next)!=tail;++numRemoved){
            if(!filter.test(head.val)){
              numRemoved+=collapseBodyHelper(head,tail,filter);
              break;
            }
          }
          head.prev=prev;
          prev.next=head;
          root.size-=numRemoved;
          this.size=size-numRemoved;
          bubbleUpDecrementSize(numRemoved);
          return true;
        }
      }
      return false;
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        Node<E> head,newTail;
        final var newHead=newTail=new Node<E>((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new Node<E>(newTail,(head=head.next).val),++i){}
        return new UncheckedList<E>(newHead,size,newTail);
      }
      return new UncheckedList<E>();
    }
    private static class AscendingItr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final UncheckedSubList<E> parent;
      transient Node<E> curr;
      private AscendingItr(UncheckedSubList<E> parent,Node<E> curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(AscendingItr<E> itr){
        this.parent=itr.parent;
        this.curr=itr.curr;
      }
      private AscendingItr(UncheckedSubList<E> parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public Object clone(){
        return new AscendingItr<E>(this);
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public E next(){
        final Node<E> curr;
        this.curr=(curr=this.curr)==parent.tail?null:curr.next;
        return curr.val;
      }
      @Override public void remove(){
        UncheckedSubList<E> parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          Node<E> curr;
          if((curr=this.curr)==null){
            parent.peelTail(parent.tail);
          }else{
            Node<E> lastRet;
            if((lastRet=curr.prev)==parent.head){
              parent.peelHead(lastRet);
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
              parent.bubbleUpDecrementSize();
            }
          }
        }
        --parent.root.size;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final Node<E> curr;
        if((curr=this.curr)!=null){
          Node.uncheckedForEachAscending(curr,parent.tail,action);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr<E> extends AscendingItr<E> implements OmniListIterator.OfRef<E>{
      private transient int currIndex;
      private transient Node<E> lastRet;
      private BidirectionalItr(BidirectionalItr<E> itr){
        super(itr);
        this.currIndex=itr.currIndex;
        this.lastRet=itr.lastRet;
      }
      private BidirectionalItr(UncheckedSubList<E> parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedSubList<E> parent,Node<E> curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new BidirectionalItr<E>(this);
      }
      @Override public E next(){
        final Node<E> curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public E previous(){
        Node<E> curr;
        this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public boolean hasNext(){
        return currIndex<parent.size;
      }
      @Override public boolean hasPrevious(){
        return currIndex>0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(E val){
        lastRet.val=val;
      }
      @Override public void add(E val){
        final UncheckedSubList<E> currList;
        final UncheckedList<E> root;
        this.lastRet=null;
        ++currIndex;
        if(++(root=(currList=this.parent).root).size!=1){
          if(++currList.size!=1){
            Node<E> after,before;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                before.next=before=new Node<E>(before,val,after);
                if(after==currList.head){
                  currList.bubbleUpPrepend(after,before);
                }else{
                  currList.bubbleUpIncrementSize();
                }
              }else{
                currList.bubbleUpPrepend(before=new Node<E>(val,after));
                root.head=before;
              }
              after.prev=before;
            }else{
              Node<E> newNode;
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new Node<E>(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new Node<E>(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new Node<E>(val));
          }
        }else{
          Node<E> newNode;
          currList.bubbleUpRootInit(newNode=new Node<E>(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
      @Override public void remove(){
        Node<E> lastRet,curr;
        if((curr=(lastRet=this.lastRet).next)==this.curr){
          --currIndex;
        }else{
          this.curr=curr;
        }
        UncheckedSubList<E> parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          if(lastRet==parent.tail){
            parent.peelTail(lastRet);
          }else{
            if(lastRet==parent.head){
              parent.peelHead(lastRet);
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
              parent.bubbleUpDecrementSize();
            }
          }
        }
        this.lastRet=null;
        --parent.root.size;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int bound;
        final UncheckedSubList<E> parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final Node<E> lastRet;
          Node.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action);
          this.lastRet=lastRet;
          this.curr=null;
          this.currIndex=bound;
        }
      }
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new AscendingItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      return new BidirectionalItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      return new BidirectionalItr<E>(this,((RefDblLnkSeq<E>)this).getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex-fromIndex)!=0){
        int tailDist;
        final Node<E> subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=Node.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?Node.iterateDescending(subListTail,subListSize-1):Node.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=Node.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?Node.iterateAscending(subListHead,subListSize-1):Node.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList<E>(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList<E>(this,fromIndex);
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveValNonNull(head,val);
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Boolean val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Byte val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Character val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Short val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Integer val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Long val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Float val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Double val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveValNonNull(Node<E> head
    ,Object nonNull
    ){
      if(nonNull.equals(head.val)){
        --root.size;
        if(--this.size==0){
          removeLastNode(head);
        }else{
          peelHead(head);
        }
        return true;
      }else{
        for(final var tail=this.tail;tail!=head;){
          Node<E> prev;
          if(nonNull.equals((head=(prev=head).next).val)){
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(prev,head);
            }else{
              prev.next=head=head.next;
              head.prev=prev;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveValNull(Node<E> head
    ){
      if(null==(head.val)){
        --root.size;
        if(--this.size==0){
          removeLastNode(head);
        }else{
          peelHead(head);
        }
        return true;
      }else{
        for(final var tail=this.tail;tail!=head;){
          Node<E> prev;
          if(null==((head=(prev=head).next).val)){
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(prev,head);
            }else{
              prev.next=head=head.next;
              head.prev=prev;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveVal(Node<E> head
    ,Predicate<? super E> pred
    ){
      if(pred.test(head.val)){
        --root.size;
        if(--this.size==0){
          removeLastNode(head);
        }else{
          peelHead(head);
        }
        return true;
      }else{
        for(final var tail=this.tail;tail!=head;){
          Node<E> prev;
          if(pred.test((head=(prev=head).next).val)){
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(prev,head);
            }else{
              prev.next=head=head.next;
              head.prev=prev;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
  }
  static class CheckedSubList<E> extends RefDblLnkSeq<E>{
    private static final long serialVersionUID=1L;
    transient final CheckedList<E> root;
    transient final CheckedSubList<E> parent;
    transient final int parentOffset;
    transient int modCount;
    private CheckedSubList(CheckedList<E> root,int rootOffset,Node<E> head,int size,Node<E> tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList<E> parent,int parentOffset,Node<E> head,int size,Node<E> tail){
      super(head,size,tail);
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      this.modCount=parent.modCount;
    }
    private CheckedSubList(CheckedList<E> root,int rootOffset){
      super();
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList<E> parent,int parentOffset){
      super();
      this.root=parent.root;
      this.parent=parent;
      this.parentOffset=parentOffset;
      this.modCount=parent.modCount;
    }
    boolean uncheckedremoveValNonNull(Node<E> head
    ,Object nonNull
    ){
      int modCount=this.modCount;
      final var root=this.root;
      int size=this.size;
      try
      {
        if(nonNull.equals(head.val)){
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          --root.size;
          if(--this.size==0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }
        for(int numLeft=--size;numLeft!=0;--numLeft){
          Node<E> prev;
          if(nonNull.equals((head=(prev=head).next).val)){
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            this.size=size;
            if(numLeft==1){
              peelTail(prev,head);
            }else{
              prev.next=head=head.next;
              head.prev=prev;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    boolean uncheckedremoveValNull(Node<E> head
    ){
      int modCount;
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(null==(head.val)){
          root.modCount=++modCount;
          this.modCount=modCount;
          --root.size;
          if(--this.size==0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }
        for(final var tail=this.tail;head!=tail;){
          Node<E> prev;
          if(null==((head=(prev=head).next).val)){
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(prev,head);
            }else{
              prev.next=head=head.next;
              head.prev=prev;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveVal(Node<E> head
    ,Predicate<? super E> pred
    ){
      int modCount;
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(pred.test(head.val)){
          root.modCount=++modCount;
          this.modCount=modCount;
          --root.size;
          if(--this.size==0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }
        for(final var tail=this.tail;head!=tail;){
          Node<E> prev;
          if(pred.test((head=(prev=head).next).val)){
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(prev,head);
            }else{
              prev.next=head=head.next;
              head.prev=prev;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new BidirectionalItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr<E>(this,((RefDblLnkSeq<E>)this).getItrNode(index,size),index);
    }
    private static class BidirectionalItr<E>
      implements OmniListIterator.OfRef<E>{
      private transient final CheckedSubList<E> parent;
      private transient int modCount;
      private transient Node<E> curr;
      private transient Node<E> lastRet;
      private transient int currIndex;
      private BidirectionalItr(BidirectionalItr<E> itr){
        this.parent=itr.parent;
        this.modCount=itr.modCount;
        this.curr=itr.curr;
        this.lastRet=itr.lastRet;
        this.currIndex=itr.currIndex;
      }
      private BidirectionalItr(CheckedSubList<E> parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=parent.head;
      }
      private BidirectionalItr(CheckedSubList<E> parent,Node<E> curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new BidirectionalItr<E>(this);
      }
      @Override public E next(){
        final CheckedSubList<E> parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)<parent.size){
          Node<E> curr;
          this.lastRet=curr=this.curr;
          this.curr=curr.next;
          this.currIndex=currIndex+1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public E previous(){
        final CheckedSubList<E> parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          Node<E> curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean hasNext(){
        return currIndex<parent.size;
      }
      @Override public boolean hasPrevious(){
        return currIndex!=0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(E val){
        final Node<E> lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.root.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        int size,numLeft;
        final CheckedSubList<E> parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size)-(currIndex=this.currIndex))>0){
          final int modCount=this.modCount;
          try{
            Node.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount,currIndex,this.currIndex);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void remove(){
        Node<E> lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedSubList<E> parent;
          CheckedList<E> root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          parent.modCount=modCount;
          Node<E> curr;
          if((curr=lastRet.next)==this.curr){
            --currIndex;
          }else{
            this.curr=curr;
          }
          if(--(parent=this.parent).size==0){
            parent.removeLastNode(parent.tail);
          }else{
            if(lastRet==parent.tail){
              parent.peelTail(lastRet);
            }else{
              if(lastRet==parent.head){
                parent.peelHead(lastRet);
              }else{
                curr.prev=lastRet=lastRet.prev;
                lastRet.next=curr;
                parent.bubbleUpDecrementSize();
              }
            }
          }
          --root.size;
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(E val){
        final CheckedSubList<E> currList;
        final CheckedList<E> root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(currList=this.parent).root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        currList.modCount=modCount;
        this.lastRet=null;
        ++currIndex;
        if(++root.size!=1){
          if(++currList.size!=1){
            Node<E> after,before;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                before.next=before=new Node<E>(before,val,after);
                if(after==currList.head){
                  currList.bubbleUpPrepend(after,before);
                }else{
                  currList.bubbleUpIncrementSize();
                }
              }else{
                currList.bubbleUpPrepend(before=new Node<E>(val,after));
                root.head=before;
              }
              after.prev=before;
            }else{
              Node<E> newNode;
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new Node<E>(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new Node<E>(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new Node<E>(val));
          }
        }else{
          Node<E> newNode;
          currList.bubbleUpRootInit(newNode=new Node<E>(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size))!=0){
        final Node<E> subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=Node.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?Node.iterateDescending(subListTail,subListSize-1):Node.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=Node.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?Node.iterateAscending(subListHead,subListSize-1):Node.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList<E>(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList<E>(this,fromIndex);
    }
    @Override public Object clone(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int size;
      if((size=this.size)!=0){
        Node<E> head,newTail;
        final var newHead=newTail=new Node<E>((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new Node<E>(newTail,(head=head.next).val),++i){}
        return new CheckedList<E>(newHead,size,newTail);
      }
      return new CheckedList<E>();
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveValNonNull(head,val);
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Boolean val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Byte val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Character val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Short val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Integer val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Long val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Float val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Double val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        final int modCount=this.modCount;
        try
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedindexOfNonNull(head,this.size,val);
            }
            return Node.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return Node.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return Node.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Character val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return Node.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return Node.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Integer val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return Node.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return Node.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return Node.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return Node.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        final int modCount=this.modCount;
        try
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return Node.uncheckedlastIndexOfNonNull(size,tail,val);
            }
            return Node.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return Node.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return Node.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Character val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return Node.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return Node.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Integer val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return Node.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return Node.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return Node.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return Node.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return Node.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean contains(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        final int modCount=this.modCount;
        try
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedcontainsNonNull(head,this.size,val);
            }
            return Node.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return Node.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return Node.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Character val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return Node.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return Node.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Integer val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return Node.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return Node.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return Node.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return Node.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    private static class SerializableSubList<E> implements Serializable{
      private static final long serialVersionUID=1L;
      private transient Node<E> head;
      private transient Node<E> tail;
      private transient int size;
      private transient final CheckedList<E>.ModCountChecker modCountChecker;
      private SerializableSubList(Node<E> head,int size,Node<E> tail,CheckedList<E>.ModCountChecker modCountChecker){
        this.head=head;
        this.tail=tail;
        this.size=size;
        this.modCountChecker=modCountChecker;
      }
      private Object readResolve(){
        return new CheckedList<E>(head,size,tail);
      }
      @SuppressWarnings("unchecked")
      private void readObject(ObjectInputStream ois) throws IOException
        ,ClassNotFoundException
      {
        int size;
        this.size=size=ois.readInt();
        if(size!=0){
          Node<E> curr;
          for(this.head=curr=new Node<E>((E)ois.readObject());--size!=0;curr=curr.next=new Node<E>(curr,(E)ois.readObject())){}
          this.tail=curr;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try{
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            var curr=this.head;
            for(;;curr=curr.next){
              oos.writeObject(curr.val);
              if(--size==0){
                break;
              }
            }
          }
        }finally{
          modCountChecker.checkModCount();
        }
      }
    }
    private Object writeReplace(){
      return new SerializableSubList<E>(this.head,this.size,this.tail,root.new ModCountChecker(this.modCount));
    }   
    private static <E> Node<E> pullSurvivorsDown(Node<E> prev,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return null;
            }else if((marker<<=1)==0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          return curr;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private static <E> Node<E> pullSurvivorsDown(Node<E> prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return null;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          return curr;
        }
        prev=curr;
      }
    }
    private void bubbleUpPeelHead(Node<E> newHead,Node<E> oldHead){
      newHead.prev=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.tail!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelHead(Node<E> newHead){
      var curr=this;
      do{
        ++curr.modCount;
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(Node<E> newTail,Node<E> oldTail){
      newTail.next=null;
      for(var curr=parent;curr!=null;curr=curr.parent){
        if(curr.head!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }
    }
    private void bubbleUpPeelTail(Node<E> newTail){
      var curr=this;
      do{
        ++curr.modCount;
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)!=null);
    }
    private void uncheckedBubbleUpDecrementSize(){
      var curr=this;
      do{
        ++curr.modCount;
        --curr.size;    
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpDecrementSize(){
       CheckedSubList<E> parent;
       if((parent=this.parent)!=null){
         parent.uncheckedBubbleUpDecrementSize();
       }
    }
    private void peelTail(Node<E> newTail,Node<E> oldTail){
      this.tail=newTail;
      Node<E> after;
      if((after=oldTail.next)==null){
        final CheckedSubList<E> parent;
        if((parent=this.parent)!=null){
          parent.bubbleUpPeelTail(newTail);
        }
        root.tail=newTail;
      }else{
        after.prev=newTail;
        for(var curr=parent;curr!=null;curr=curr.parent){
          if(curr.tail!=oldTail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.tail=newTail;
        }
      }
      newTail.next=after;
    }
    private void peelTail(Node<E> tail){
      Node<E> after,before;
      (before=tail.prev).next=(after=tail.next);
      this.tail=before;
      if(after==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          ++curr.modCount;
          --curr.size;
          curr.tail=before;
        }
        root.tail=before;
      }else{
        after.prev=before;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.tail!=tail){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.tail=before;
        }
      }
    }
    private void removeLastNode(Node<E> lastNode){
      Node<E> after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        CheckedList<E> root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          ++curr.modCount,
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          after.prev=before;
          before.next=after;
          for(var curr=parent;curr!=null;curr=curr.parent){
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                ++curr.modCount;
                --curr.size;
                curr.tail=before;
              }while((curr=curr.parent)!=null);
              break;
            }
            if(curr.tail!=lastNode){
              for(;;){
                ++curr.modCount;
                --curr.size;
                curr.head=after;
                if((curr=curr.parent)==null){
                  break;
                }
                if(curr.head!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
              }
              break;
            }
            ++curr.modCount;
            curr.head=null;
            curr.tail=null;
            curr.size=0;
          }
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(Node<E> head){
      Node<E> after,before;
      (after=head.next).prev=(before=head.prev);
      this.head=after;
      if(before==null){
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          ++curr.modCount;
          --curr.size;
          curr.head=after;
        }
        root.head=after;
      }else{
        before.next=after;
        for(var curr=this.parent;curr!=null;curr=curr.parent){
          if(curr.head!=head){
            curr.uncheckedBubbleUpDecrementSize();
            break;
          }
          ++curr.modCount;
          --curr.size;
          curr.head=after;
        }
      }
    }
    @Override public E remove(int index){
      final E ret;
      int size;
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      this.size=--size;
      if((size-=index)<=index){
        var tail=this.tail;
        if(size==0){
          ret=tail.val;
          if(index==0){
            removeLastNode(tail);
          }else{
            peelTail(tail);
          }
        }else{
          Node<E> before;
          ret=(before=( tail=Node.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
          bubbleUpDecrementSize();
        }
      }else{
        var head=this.head;
        if(index==0){
          ret=head.val;
          peelHead(head);
        }else{
          Node<E> after;
          ret=(after=(head=Node.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(Predicate<? super E> filter){
      final Node<E> head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    private void collapsehead(Node<E> oldhead,Node<E> tail,Predicate<? super E> filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList<E> root=this.root;
      Node<E> newhead;
      for(newhead=oldhead.next;;
      --numLeft,
      ++numRemoved,newhead=newhead.next){ 
        if(numLeft==0){
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newhead.val)){
          numRemoved+=collapseBodyHelper(newhead,tail,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      this.head=newhead;
      Node<E> tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
            ++parent.modCount;
        }
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            ++parent.modCount;
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
          ++parent.modCount;
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(Node<E> oldtail,Node<E> head,Predicate<? super E> filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList<E> root=this.root;
      Node<E> newtail;
      for(newtail=oldtail.prev;;
      --numLeft,
      ++numRemoved,newtail=newtail.prev){ 
        if(numLeft==0){
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newtail.val)){
          numRemoved+=collapseBodyHelper(head,newtail,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      this.tail=newtail;
      Node<E> tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
            ++parent.modCount;
        }
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            ++parent.modCount;
            parent.size-=numRemoved;
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
          ++parent.modCount;
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void bubbleUpCollapseHeadAndTail(Node<E> oldHead,Node<E> newHead,int numRemoved,Node<E> newTail,Node<E> oldTail){
      this.head=newHead;
      this.tail=newTail;
      final Node<E> after,before=oldHead.prev;
      if((after=oldTail.next)==null){
        if(before==null){
          for(var parent=this.parent;parent!=null;
          ++parent.modCount,
          parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){}
          CheckedList<E> root;
          (root=this.root).head=newHead;
          root.tail=newTail;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            ++parent.modCount,
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.tail=newTail;
        }
      }else{
        after.prev=newTail;
        if(before==null){
          for(var parent=this.parent;parent!=null;
            ++parent.modCount,
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.tail!=oldTail){
              do{
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.head=newHead;
              }while((parent=parent.parent)!=null);
              break;
            }
          }
          root.head=newHead;
        }else{
          before.next=newHead;
          for(var parent=this.parent;parent!=null;
            ++parent.modCount,
            parent.size-=numRemoved,parent.head=newHead,parent.tail=newTail,parent=parent.parent){
            if(parent.head!=oldHead){
              do{
                if(parent.tail!=oldTail){
                  ++parent.modCount;
                  parent.size-=numRemoved;
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.tail=newTail;
              }while((parent=parent.parent)!=null);
              break;
            }
            if(parent.tail!=oldTail){
              for(;;){
                ++parent.modCount;
                parent.size-=numRemoved;
                parent.head=newHead;
                if((parent=parent.parent)==null){
                  break;
                }
                if(parent.head!=oldHead){
                  ++parent.modCount;
                  parent.size-=numRemoved;
                  parent.bubbleUpDecrementSize(numRemoved);
                  break;
                }
              }
              break;
            }
          }
        }
      }
      newHead.prev=before;
      newTail.next=after;
    }
    private boolean uncheckedRemoveIf(Node<E> head,Predicate<? super E> filter){
      Node<E> tail;
      int modCount=this.modCount;
      int size=this.size;
      try
      {
        if(filter.test((tail=this.tail).val)){
          if(size==1){
            final CheckedList<E> root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            --root.size;
            this.size=size-1;
            //only one node was in the list; remove it
            removeLastNode(head);
          }else{
            if(filter.test(head.val)){
              collapseHeadAndTail(head,tail,filter
                ,size,modCount
              );
            }else{
              collapsetail(tail,head,filter
                ,size,modCount
              );
            }
          }
          return true;
        }else{
          if(size!=1){
            if(filter.test(head.val)){
              collapsehead(head,tail,filter
                ,size,modCount
              );
              return true;
            }else{
              return collapseBody(head,tail,filter
                ,size,modCount
              );
            }
          }
        }
      }
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public void clear(){
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        this.modCount=modCount;
        root.size-=size;
        clearAllHelper(size,this.head,this.tail,root);
      }
    }
    private void clearAllHelper(int size,Node<E> head,Node<E> tail,CheckedList<E> root){
      Node<E> before,after=tail.next;
      if((before=head.prev)==null){
        //this sublist is not preceded by nodes
        if(after==null){
          bubbleUpClearAll();
          root.tail=null;
        }else{
          bubbleUpClearHead(tail,after,size);
          after.prev=null;
        }
        root.head=after;
      }else{
        before.next=after;
        if(after==null){
          bubbleUpClearTail(head,before,size);
          root.tail=before;
        }else{
          bubbleUpClearBody(before,head,size,tail,after);
          after.prev=before;
        }
      }
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    private void bubbleUpClearAll(){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
    }
    private void bubbleUpDecrementSize(int numRemoved){
      for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){
        ++curr.modCount;
      }
    }
    private void bubbleUpClearBody(Node<E> before,Node<E> head,int numRemoved,Node<E> tail,Node<E> after){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          while(curr.tail==tail){
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }
          ++curr.modCount;
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }else if(curr.tail!=tail){
          do{
            ++curr.modCount;
            curr.head=after;
            curr.size-=numRemoved;
            if((curr=curr.parent)==null){
              return;
            }
          }while(curr.head==head);
          ++curr.modCount;
          curr.size-=numRemoved;
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(Node<E> tail, Node<E> after,int numRemoved){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.tail!=tail){
          do{
            ++curr.modCount;
            curr.head=after;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private void bubbleUpClearTail(Node<E> head, Node<E> before,int numRemoved){
      for(var curr=parent;curr!=null;
      ++curr.modCount,
      curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){
        if(curr.head!=head){
          do{
            ++curr.modCount;
            curr.tail=before;
            curr.size-=numRemoved;
          }while((curr=curr.parent)!=null);
          break;
        }
      }
    }
    private static <E> int collapseBodyHelper(Node<E> newHead,Node<E> newTail,int numLeft,Predicate<? super E> filter,CheckedList<E>.ModCountChecker modCountChecker)
    {
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(newHead.next,numLeft,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          modCountChecker.checkModCount();
          if((numLeft-=numSurvivors)!=0){
            if((newHead=pullSurvivorsDown(newHead,survivorSet,numSurvivors,numLeft))!=null){
              newHead.next=newTail;
              newTail.prev=newHead;
            }
          }
        }else{
          final long survivorWord=markSurvivors(newHead.next,numLeft,filter);
          modCountChecker.checkModCount();
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            if((newHead=pullSurvivorsDown(newHead,survivorWord,numSurvivors,numLeft))!=null){
              newHead.next=newTail;
              newTail.prev=newHead;
            }
          }
        }
      }else{
        modCountChecker.checkModCount();
      }
      return numLeft;
    }
    private void collapseHeadAndTail(Node<E> head,Node<E> tail,Predicate<? super E> filter
      ,int size,int modCount
    ){
      int numRemoved;
      if((numRemoved=2)!=size){
        for(var newHead=head.next;;newHead=newHead.next){
          if(!filter.test(newHead.val)){
            var newTail=tail.prev;
            final CheckedList<E> root=this.root;
            for(--size;;++numRemoved,newTail=newTail.prev){
              if(numRemoved==size){
                 CheckedCollection.checkModCount(modCount,root.modCount);
                 break;
              }
              if(!filter.test(newTail.val)){
                numRemoved+=collapseBodyHelper(newHead,newTail,size-1-numRemoved,filter,root.new ModCountChecker(modCount));
                break;
              }
            }
            root.modCount=++modCount;
            this.modCount=modCount;
            root.size-=numRemoved;
            this.size=size+1-numRemoved;
            bubbleUpCollapseHeadAndTail(head,newHead,numRemoved,newTail,tail);
            return;
          }
          if(++numRemoved==size){
            break;
          }
        }
      }
      CheckedList<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=size;
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(Node<E> head,Node<E> tail,Predicate<? super E> filter
      ,int size,int modCount
    ){
      for(int numLeft=size-2;numLeft!=0;--numLeft){
        Node<E> prev;
        if(filter.test((head=(prev=head).next).val)){
          int numRemoved=1;
          var root=this.root;
          for(;;++numRemoved){
            head=head.next;
            if(--numLeft==0){
              CheckedCollection.checkModCount(modCount,root.modCount);
              break;
            }else if(!filter.test(head.val)){
              numRemoved+=collapseBodyHelper(head,tail,--numLeft,filter,root.new ModCountChecker(modCount));
              break;
            }
          }
          root.modCount=++modCount;
          this.modCount=modCount;
          head.prev=prev;
          prev.next=head;
          root.size-=numRemoved;
          this.size=size-numRemoved;
          bubbleUpDecrementSize(numRemoved);
          return true;
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    private void bubbleUpIncrementSize(){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){
        ++curr.modCount;
      }
    }
    private void bubbleUpAppend(Node<E> oldTail,Node<E> newTail){
      oldTail.next=newTail;
      this.tail=newTail;
      for(var currList=parent;currList!=null;currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
        ++currList.size;
        if(currList.tail!=oldTail){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpAppend(Node<E> newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpPrepend(Node<E> oldHead,Node<E> newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
        ++currList.size;
        if(currList.head!=oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(Node<E> newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpRootInit(Node<E> newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        ++parent.modCount;
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,Node<E> newNode){
      Node<E> after,before;   
      if((size-=index)<=index){
        before=this.tail;
        if(size==1){
          if((after=before.next)==null){
            this.bubbleUpAppend(newNode);
            root.tail=newNode;
          }else{
            this.bubbleUpAppend(before,newNode);
            after.prev=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          before=(after=Node.iterateDescending(before,size-2)).prev;
          after.prev=newNode;
        }
        before.next=newNode;        
      }else{
        after=this.head;
        if(index==0){
          if((before=after.prev)==null){
            this.bubbleUpPrepend(newNode);
            root.head=newNode;
          }else{
            this.bubbleUpPrepend(after,newNode);
            before.next=newNode;
          }
        }else{
          this.bubbleUpIncrementSize();
          after=(before=Node.iterateAscending(after,index-1)).next;
          before.next=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(Node<E> newNode){
      this.head=newNode;
      this.tail=newNode;
      CheckedSubList<E> curr;
      for(var currParent=(curr=this).parent;currParent!=null;currParent=(curr=currParent).parent){
        ++currParent.modCount;
        int parentSize;
        if((parentSize=++currParent.size)!=1){
          currParent.bubbleUpInitHelper(curr.parentOffset,parentSize,newNode);
          return;
        }
        currParent.head=newNode;
        currParent.tail=newNode;
      }
      ((RefDblLnkSeq<E>)root).insertNode(curr.parentOffset,newNode);
    }
    @Override public void add(int index,E val){
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int currSize;
      CheckedCollection.checkWriteHi(index,currSize=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      final var newNode=new Node<E>(val);
      if(++root.size!=1){
        this.size=++currSize;
        if(currSize!=1){    
          bubbleUpInitHelper(index,currSize,newNode);
        }else{
          bubbleUpInit(newNode);
        }
      }else{
        bubbleUpRootInit(newNode);
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override void addLast(E val){
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      if(++root.size!=1){
        if(++this.size!=1){
          Node<E> currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new Node<E>(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new Node<E>(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new Node<E>(val));
        }
      }else{
        Node<E> newNode;
        bubbleUpRootInit(newNode=new Node<E>(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public E set(int index,E val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final Node<E> node;
      final var ret=(node=((RefDblLnkSeq<E>)this).getNode(index,size)).val;
      node.val=val;
      return ret;
    }
    @Override public void put(int index,E val){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((RefDblLnkSeq<E>)this).getNode(index,size).val=val;
    }
    @Override public E get(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((RefDblLnkSeq<E>)this).getNode(index,size).val;
    }
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override public void replaceAll(UnaryOperator<E> operator){
      final Node<E> head;
      if((head=this.head)==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final CheckedList<E> root;
      int modCount=this.modCount;
      try{
        Node.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        var curr=this;
        do{
          curr.modCount=modCount;
        }while((curr=curr.parent)!=null);
      }
    }
    @Override public void forEach(Consumer<? super E> action){
      final int modCount=this.modCount;
      try{
        final Node<E> head;
        if((head=this.head)!=null){
          Node.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super E> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList<E> root;
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        try{
          if(sorter==null){
            RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
          }else{
            RefSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableAscendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        final CheckedList<E> root;
        int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableDescendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        final CheckedList<E> root;
        int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedStableDescendingSort(tmp,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableAscendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        final CheckedList<E> root;
        int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableDescendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        final CheckedList<E> root;
        int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedUnstableDescendingSort(tmp,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(Comparator<? super E> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        int modCount=this.modCount;
        final CheckedList<E> root;
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        try{
          if(sorter==null){
            RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
          }else{
            RefSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        }
        root.modCount=++modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        this.modCount=modCount;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public <T> T[] toArray(T[] dst){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toArray(dst);
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->
      {
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      });
    }
    @Override public Object[] toArray(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return super.toArray();
    }
    @Override public String toString(){
      final int modCount=this.modCount;
      try{
        final Node<E> head;
        if((head=this.head)!=null){
           final StringBuilder builder;
           Node.uncheckedToString(head,size,builder=new StringBuilder("["));
           return builder.append(']').toString();
        }
        return "[]";
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public int hashCode(){
      final int modCount=this.modCount;
      try{
        final Node<E> head;
        if((head=this.head)!=null){
           return Node.uncheckedHashCode(head,size);
        }
        return 1;
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int size;
          if((size=this.size)==0){
            return ((List<?>)val).isEmpty();
          }
          final List<?> list;
          if((list=(List<?>)val) instanceof AbstractOmniCollection){
            if(list instanceof OmniList.OfRef){
              return root.isEqualTo(this.head,size,(OmniList.OfRef<?>)list);
            }else if(list instanceof OmniList.OfInt){
              return ((UncheckedList<E>)root).isEqualTo(this.head,size,(OmniList.OfInt)list);
            }else if(list instanceof OmniList.OfFloat){
              return ((UncheckedList<E>)root).isEqualTo(this.head,size,(OmniList.OfFloat)list);
            }else if(list instanceof OmniList.OfLong){
              return ((UncheckedList<E>)root).isEqualTo(this.head,size,(OmniList.OfLong)list);
            }else if(list instanceof OmniList.OfDouble){
              return ((UncheckedList<E>)root).isEqualTo(this.head,size,(OmniList.OfDouble)list);
            }else if(list instanceof OmniList.OfByte){
              return ((UncheckedList<E>)root).isEqualTo(this.head,size,(OmniList.OfByte)list);
            }else if(list instanceof OmniList.OfChar){
              return ((UncheckedList<E>)root).isEqualTo(this.head,size,(OmniList.OfChar)list);
            }else if(list instanceof OmniList.OfShort){
              return ((UncheckedList<E>)root).isEqualTo(this.head,size,(OmniList.OfShort)list);
            }else{
              return ((UncheckedList<E>)root).isEqualTo(this.head,size,(OmniList.OfBoolean)list);
            }
          }else{
            return UncheckedList.isEqualTo(list.listIterator(),this.head,this.tail);
          }
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      return false;
    } 
  }
  public static class CheckedList<E> extends UncheckedList<E>{
    transient int modCount;
    public CheckedList(Collection<? extends E> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfRef<? extends E> that){
      super(that);
    }
    public CheckedList(){
    }
    CheckedList(Node<E> head,int size,Node<E> tail){
      super(head,size,tail);
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker
    {
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return CheckedList.this.modCount;
      }
    }
    @Override public void clear(){
      if(size!=0){
        ++this.modCount;
        this.size=0;
        this.head=null;
        this.tail=null;
      }
    }
    @Override public E removeLast(){
      Node<E> tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=tail.val;
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public E pop(){
      Node<E> head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=head.val;
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public E remove(int index){
      final E ret;
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++this.modCount;
      this.size=--size;
      if((size-=index)<=index){
        //the node to remove is closer to the tail
        var tail=this.tail;
        if(size==0){
          //the node to the remove IS the tail
          ret=tail.val;
          if(index==0){
            //the node is the last node
            this.head=null;
            this.tail=null;
          }else{
            //peel off the tail
            this.tail=tail=tail.prev;
            tail.next=null;
          }
        }else{
          //iterate from the tail
          Node<E> before;
          ret=(before=(tail=Node.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
        }
      }else{
        //the node to remove is close to the head
        var head=this.head;
        if(index==0){
          //peel off the head
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          //iterate from the head
          Node<E> after;
          ret=(after=(head=Node.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,E val){
      int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      this.size=++size;
      if((size-=index)<=index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new Node<E>(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          Node<E> before;
          (before=(tail=Node.iterateDescending(tail,size-2)).prev).next=before=new Node<E>(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        Node<E> head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new Node<E>(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new Node<E>(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          Node<E> after;
          (after=(head=Node.iterateAscending(head,index-1)).next).prev=after=new Node<E>(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(E val){
      ++this.modCount;
      super.addLast(val);
    }
    @Override public void push(E val){
      ++this.modCount;
      super.push(val);
    }
    @Override public E set(int index,E val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      Node<E> tmp;
      final var ret=(tmp=((RefDblLnkSeq<E>)this).getNode(index,size)).val;
      tmp.val=val;
      return ret;
    }
    @Override public void put(int index,E val){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ((RefDblLnkSeq<E>)this).getNode(index,size).val=val;
    }
    @Override public E get(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      return ((RefDblLnkSeq<E>)this).getNode(index,size).val;
    }
    @Override public E getLast(){
      final Node<E> tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public E element(){
      final Node<E> head;
      if((head=this.head)!=null){
         return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public void forEach(Consumer<? super E> action){
      final Node<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          Node.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<E> operator){
      final Node<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          Node.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(Comparator<? super E> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try{
          if(sorter==null){
            RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
          }else{
            RefSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedStableDescendingSort(tmp,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public String toString(){
      final Node<E> head;
      if((head=this.head)!=null){
        final StringBuilder builder=new StringBuilder("[");
        final int modCount=this.modCount;
        try{
          Node.uncheckedToString(head,size,builder);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        return builder.append(']').toString();
      }
      return "[]";
    }
    @Override public int hashCode(){
      final Node<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          return Node.uncheckedHashCode(head,size);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
      return 1;
    }
    @Override public void unstableAscendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableDescendingSort()
    {
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedUnstableDescendingSort(tmp,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(Comparator<? super E> sorter){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final int size;
      if((size=this.size)>1){
        final Object[] tmp;
        final Node<E> tail;
        Node.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try{
          if(sorter==null){
            RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
          }else{
            RefSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
        Node.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    private void pullSurvivorsDown(Node<E> prev,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              if(curr==tail){
                prev.next=null;
                this.tail=prev;
              }else{
                prev.next=curr=curr.next;
                curr.prev=prev;
              }
              return;
            }else if((marker<<=1)==0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          this.tail=curr;
          curr.next=null;
          return;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private void pullSurvivorsDown(Node<E> prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              if(curr==tail){
                prev.next=null;
                this.tail=prev;
              }else{
                prev.next=curr=curr.next;
                curr.prev=prev;
              }
              return;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          this.tail=curr;
          curr.next=null;
          return;
        }
        prev=curr;
      }
    }
    private int removeIfHelper(Node<E> prev,Predicate<? super E> filter,int numLeft,int modCount){
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          final long[] survivorSet;
          numSurvivors=markSurvivors(prev.next,numLeft,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(prev,survivorSet,numSurvivors,numLeft);
          }
        }else{
          final long survivorWord=markSurvivors(prev.next,numLeft,filter);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            pullSurvivorsDown(prev,survivorWord,numSurvivors,numLeft);
          }
        }
        return numSurvivors;
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return 0;
    }
    @Override boolean uncheckedRemoveIf(Node<E> head,Predicate<? super E> filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        if(filter.test(head.val)){
          while(--numLeft!=0){
            if(!filter.test((head=head.next).val)){
              this.size=1+removeIfHelper(head,filter,--numLeft,modCount);
              this.modCount=modCount+1;
              this.head=head;
              head.prev=null;
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=null;
          this.tail=null;
          this.size=0;
          return true;
        }else{
          int numSurvivors;
          if(--numLeft!=(numSurvivors=removeIfHelper(head,filter,numLeft,modCount))){
            this.modCount=modCount+1;
            this.size=1+numSurvivors;
            return true;
          }
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return false;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        int size;
        out.writeInt(size=this.size);
        if(size!=0){
          var curr=this.head;
          for(;;curr=curr.next){
            out.writeObject(curr.val);
            if(--size==0){
              break;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        Node<E> head,newTail;
        final var newHead=newTail=new Node<E>((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new Node<E>(newTail,(head=head.next).val),++i){}
        return new CheckedList<E>(newHead,size,newTail);
      }
      return new CheckedList<E>();
    }
    private boolean isEqualTo(int size,OmniList.OfRef<?> list){
      if(list instanceof RefDblLnkSeq){
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedList){
          final RefDblLnkSeq.CheckedList<?> that;
          final int thatModCount=(that=(RefDblLnkSeq.CheckedList<?>)dls).modCount;
          final int thisModCount=this.modCount;
          try{
            return that.size==size && UncheckedList.isEqualToHelper(this.head,that.head,this.tail);
          }finally{
            CheckedCollection.checkModCount(thisModCount,this.modCount);
            CheckedCollection.checkModCount(thatModCount,that.modCount);
          }
        }else if(dls instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> that;
          final int thatModCount=(that=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount;
          final int thisModCount=this.modCount;
          try{
            final Node<?> thatHead,thisHead;
            return size==that.size && (((thatHead=that.head)==(thisHead=this.head))||UncheckedList.isEqualToHelper(thisHead,thatHead,this.tail));
          }finally{
            CheckedCollection.checkModCount(thisModCount,this.modCount);
            CheckedCollection.checkModCount(thatModCount,that.root.modCount);
          }
        }else{
          final int thisModCount=this.modCount;
          try{
            return dls.size==size && UncheckedList.isEqualToHelper(this.head,dls.head,this.tail);
          }finally{
            CheckedCollection.checkModCount(thisModCount,this.modCount);
          }
        }
      }else{
        final int thisModCount=this.modCount;
        try{
          if(list instanceof RefArrSeq.UncheckedList){
            final RefArrSeq.UncheckedList<?> uncheckedList;
            if((uncheckedList=(RefArrSeq.UncheckedList<?>)list) instanceof RefArrSeq.CheckedList){
              final RefArrSeq.CheckedList<?> that;
              final int thatModCount=(that=(RefArrSeq.CheckedList<?>)uncheckedList).modCount;
              try{
                return size==that.size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,this.head);
              }finally{
                CheckedCollection.checkModCount(thatModCount,that.modCount);
              }
            }else{
              return size==uncheckedList.size && SequenceEqualityUtil.isEqualTo(uncheckedList.arr,0,size,this.head);
            }
          }else if(list instanceof RefArrSeq.CheckedSubList){
            final RefArrSeq.CheckedSubList<?> subList;
            final RefArrSeq.CheckedList<?> thatRoot=(subList=(RefArrSeq.CheckedSubList<?>)list).root;
            final int thatModCount=subList.modCount;
            try{
              final int rootOffset;
              return size==subList.size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,this.head);
            }finally{
              CheckedCollection.checkModCount(thatModCount,thatRoot.modCount);
            }
          }else{
            //must be RefArrSeq.UncheckedSubList
            final RefArrSeq.UncheckedSubList<?> subList;
            final int rootOffset;
            return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,this.head);
          }
        }finally{
          CheckedCollection.checkModCount(thisModCount,this.modCount);
        }
      }
    }
    private boolean isEqualTo(Node<E> thisHead,int size,OmniList.OfRef<?> list){
      if(list instanceof RefDblLnkSeq){
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedList){
          final RefDblLnkSeq.CheckedList<?> that;
          final int thatModCount=(that=(RefDblLnkSeq.CheckedList<?>)dls).modCount;
          try{
            final Node<?> thatHead;
            return size==that.size && (((thatHead=that.head)==thisHead)||UncheckedList.isEqualToHelper(thatHead,thisHead,dls.tail));
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.modCount);
          }
        }else if(list instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> that;
          final int thatModCount=(that=(RefDblLnkSeq.CheckedSubList<?>)list).modCount;
          try{
            final Node<?> thatHead;
            return size==that.size && ((thatHead=that.head)==thisHead||UncheckedList.isEqualToHelper(thatHead,thisHead,dls.tail));
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.root.modCount);
          }
        }else{
          return dls.size==size && UncheckedList.isEqualToHelper(dls.head,thisHead,dls.tail);
        }
      }else if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> uncheckedList;
        if((uncheckedList=(RefArrSeq.UncheckedList<?>)list) instanceof RefArrSeq.CheckedList){
          final RefArrSeq.CheckedList<?> that;
          final int thatModCount=(that=(RefArrSeq.CheckedList<?>)uncheckedList).modCount;
          try{
            return size==that.size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.modCount);
          }
        }else{
          return uncheckedList.size==size && SequenceEqualityUtil.isEqualTo(uncheckedList.arr,0,size,thisHead);
        }
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot=(subList=(RefArrSeq.CheckedSubList<?>)list).root;
        final int modCount=subList.modCount;
        try{
          final int rootOffset;
          return size==subList.size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
        }finally{
          CheckedCollection.checkModCount(modCount,thatRoot.modCount);
        }
      }else{
        //must be RefArrSeq.CheckedSubList
        final RefArrSeq.UncheckedSubList<?> subList;
        final int rootOffset;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractOmniCollection){
          if(list instanceof OmniList.OfRef){
            return this.isEqualTo(size,(OmniList.OfRef<?>)list);
          }else if(list instanceof OmniList.OfInt){
            return super.isEqualTo(this.head,size,(OmniList.OfInt)list);
          }else if(list instanceof OmniList.OfFloat){
            return super.isEqualTo(this.head,size,(OmniList.OfFloat)list);
          }else if(list instanceof OmniList.OfLong){
            return super.isEqualTo(this.head,size,(OmniList.OfLong)list);
          }else if(list instanceof OmniList.OfDouble){
            return super.isEqualTo(this.head,size,(OmniList.OfDouble)list);
          }else if(list instanceof OmniList.OfByte){
            return super.isEqualTo(this.head,size,(OmniList.OfByte)list);
          }else if(list instanceof OmniList.OfChar){
            return super.isEqualTo(this.head,size,(OmniList.OfChar)list);
          }else if(list instanceof OmniList.OfShort){
            return super.isEqualTo(this.head,size,(OmniList.OfShort)list);
          }else{
            return super.isEqualTo(this.head,size,(OmniList.OfBoolean)list);
          }
        }else{
          final int modCount=this.modCount;
          try{
            return UncheckedList.isEqualTo(list.listIterator(),this.head,this.tail);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      return new DescendingItr<E>(this);
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new BidirectionalItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      return new BidirectionalItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      return new BidirectionalItr<E>(this,((RefDblLnkSeq<E>)this).getItrNode(index,size),index);
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      int tailDist;
      final int subListSize;
      if((subListSize=CheckedCollection.checkSubListRange(fromIndex,toIndex,tailDist=this.size))!=0){
        final Node<E> subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=Node.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?Node.iterateDescending(subListTail,subListSize-1):Node.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=Node.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?Node.iterateAscending(subListHead,subListSize-1):Node.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList<E>(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList<E>(this,fromIndex);
    } 
    @Override public int search(Object val){
      final Node<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return Node.uncheckedsearchNonNull(head,this.size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return Node.uncheckedsearchNull(head);
      }
      return -1;
    }
    @Override public boolean contains(Object val){
      final Node<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return Node.uncheckedcontainsNonNull(head,this.size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return Node.uncheckedcontainsNull(head,tail);
      }
      return false;
    }
    @Override public int indexOf(Object val){
      final Node<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return Node.uncheckedindexOfNonNull(head,this.size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return Node.uncheckedindexOfNull(head,tail);
      }
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      final Node<E> tail;
      if((tail=this.tail)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return Node.uncheckedlastIndexOfNonNull(this.size,tail,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return Node.uncheckedlastIndexOfNull(this.size,tail);
      }
      return -1;
    }
    boolean uncheckedremoveLastOccurrenceNonNull(Node<E> tail
    ,Object nonNull
    ){
      int modCount=this.modCount;
      int size=this.size;
      try
      {
        if(nonNull.equals(tail.val)){
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          this.size=size-1;
          return true;
        }
        int numLeft=--size;
        for(;numLeft!=0;--numLeft){
          Node<E> next;
          if(nonNull.equals((tail=(next=tail).prev).val)){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            if(numLeft==1)
            {
              this.head=next;
              next.prev=null;
            }else{
              (tail=tail.prev).next=next;
              next.prev=tail;
            }
            this.size=size;
            return true;
          }
        }
      }
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    boolean uncheckedremoveLastOccurrenceNull(Node<E> tail
    ){
      {
        if(null==(tail.val)){
          this.modCount=modCount+1;
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --this.size;
          return true;
        }
        for(Node<E> next;(tail=(next=tail).prev)!=null;){
          if(null==(tail.val)){
            this.modCount=modCount+1;
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveLastOccurrence(Node<E> tail
    ,Predicate<? super E> pred
    ){
      {
        if(pred.test(tail.val)){
          this.modCount=modCount+1;
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --this.size;
          return true;
        }
        for(Node<E> next;(tail=(next=tail).prev)!=null;){
          if(pred.test(tail.val)){
            this.modCount=modCount+1;
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveValNonNull(Node<E> head
    ,Object nonNull
    ){
      int modCount=this.modCount;
      int size=this.size;
      try{
        if(nonNull.equals(head.val)){
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          this.size=size;
          return true;
        }
        int numLeft=--size;
        for(;numLeft!=0;--numLeft){
          Node<E> prev;
          if(nonNull.equals((head=(prev=head).next).val)){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            if(numLeft==1){
              this.tail=prev;
              prev.next=null;
            }else{
              (head=head.next).prev=prev;
              prev.next=head;
            }
            this.size=size;
            return true;
          }
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    boolean uncheckedremoveValNull(Node<E> head
    ){
      {
        if(null==(head.val)){
          ++this.modCount;
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(Node<E> prev;(head=(prev=head).next)!=null;){
          if(null==(head.val)){
            ++this.modCount;
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveVal(Node<E> head
    ,Predicate<? super E> pred
    ){
      {
        if(pred.test(head.val)){
          ++this.modCount;
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(Node<E> prev;(head=(prev=head).next)!=null;){
          if(pred.test(head.val)){
            ++this.modCount;
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public E poll(){
      Node<E> head;
      if((head=this.head)!=null){
        ++this.modCount;
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return null;
    }
    @Override public E pollLast(){
      Node<E> tail;
      if((tail=this.tail)!=null){
        ++this.modCount;
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return null;
    }
    private static class DescendingItr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final CheckedList<E> parent;
      transient int modCount;
      transient Node<E> curr;
      transient Node<E> lastRet;
      transient int currIndex;
      private DescendingItr(DescendingItr<E> itr){
        this.parent=itr.parent;
        this.modCount=itr.modCount;
        this.curr=itr.curr;
        this.lastRet=itr.lastRet;
        this.currIndex=itr.currIndex;
      }
      private DescendingItr(CheckedList<E> parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList<E> parent,Node<E> curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new DescendingItr<E>(this);
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public E next(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final Node<E> curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        Node<E> lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList<E> parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(--parent.size==0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet==parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet==parent.head){
              parent.head=lastRet=lastRet.next;
              lastRet.prev=null;
            }else{
              Node.eraseNode(lastRet);
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      private void uncheckedForEachRemaining(int currIndex,Consumer<? super E> action){
        final int modCount=this.modCount;
        final CheckedList<E> parent;
        try{
          Node.uncheckedForEachDescending(this.curr,currIndex,action);
        }finally{
          CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount,currIndex,this.currIndex);
        }
        this.curr=null;
        this.lastRet=parent.head;
        this.currIndex=0;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int currIndex;
        if((currIndex=this.currIndex)>0){
          uncheckedForEachRemaining(currIndex,action);
        }
      }
    }
    private static class BidirectionalItr<E> extends DescendingItr<E> implements OmniListIterator.OfRef<E>{
      private BidirectionalItr(BidirectionalItr<E> itr){
        super(itr);
      }
      private BidirectionalItr(CheckedList<E> parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList<E> parent,Node<E> curr,int currIndex){
        super(parent,curr,currIndex);
      }
      @Override public Object clone(){
        return new BidirectionalItr<E>(this);
      }
      @Override public E next(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final Node<E> curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.next;
          ++this.currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public E previous(){
        final CheckedList<E> parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
        final int currIndex;
        if((currIndex=this.currIndex)!=0){
          Node<E> curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
          this.curr=curr;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean hasPrevious(){
        return this.currIndex!=0;
      }
      @Override public int nextIndex(){
        return this.currIndex;
      }
      @Override public int previousIndex(){
        return this.currIndex-1;
      }
      @Override public void set(E val){
        final Node<E> lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedCollection.checkModCount(modCount,parent.modCount);
          lastRet.val=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(E val){
        final CheckedList<E> parent;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
        parent.modCount=++modCount;
        this.modCount=modCount;
        Node<E> newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new Node<E>(val);
          }else{
            (newNode=parent.tail).next=newNode=new Node<E>(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new Node<E>(val,newNode);
            parent.head=newNode;
          }else{
            final Node<E> tmp;
            (newNode=curr).prev=newNode=new Node<E>(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void remove(){
        Node<E> lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList<E> parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          Node<E> curr;
          if((curr=lastRet.next)==this.curr){
            --currIndex;
          }else{
            this.curr=curr;
          }
          if(--parent.size==0){
            parent.head=null;
            parent.tail=null;
          }else{
            if(lastRet==parent.tail){
              parent.tail=lastRet=lastRet.prev;
              lastRet.next=null;
            }else if(lastRet==parent.head){
              parent.head=curr;
              curr.prev=null;
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
            }
          }
          this.lastRet=null;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int size,numLeft;
        final CheckedList<E> parent;
        final int currIndex;
        if((numLeft=(size=(parent=this.parent).size)-(currIndex=this.currIndex))!=0){
          final int modCount=this.modCount;
          try{
            Node.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,currIndex,this.currIndex);
          }
          this.curr=null;
          this.lastRet=parent.tail;
          this.currIndex=size;
        }
      }
    }
  }
  public static class UncheckedList<E> extends RefDblLnkSeq<E> implements OmniDeque.OfRef<E>,Externalizable{
    private static final long serialVersionUID=1L;
    public UncheckedList(Collection<? extends E> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfRef<? extends E> that){
      super(that);
    }
    public UncheckedList(){
    }
    UncheckedList(Node<E> head,int size,Node<E> tail){
      super(head,size,tail);
    }
    @Override public void clear(){
      this.head=null;
      this.size=0;
      this.tail=null;
    }
    @Override public E removeLast(){
      Node<E> tail;
      final var ret=(tail=this.tail).val;{
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
    }
    @Override public E pop(){
      Node<E> head;
      final var ret=(head=this.head).val;{
      if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
    }
    @Override public E remove(int index){
      final E ret;
      int size;
      if((size=--this.size-index)<=index){
        //the node to remove is closer to the tail
        var tail=this.tail;
        if(size==0){
          //the node to the remove IS the tail
          ret=tail.val;
          if(index==0){
            //the node is the last node
            this.head=null;
            this.tail=null;
          }else{
            //peel off the tail
            this.tail=tail=tail.prev;
            tail.next=null;
          }
        }else{
          //iterate from the tail
          Node<E> before;
          ret=(before=(tail=Node.iterateDescending(tail,size-1)).prev).val;
          (before=before.prev).next=tail;
          tail.prev=before;
        }
      }else{
        //the node to remove is close to the head
        var head=this.head;
        if(index==0){
          //peel off the head
          ret=head.val;
          this.head=head=head.next;
          head.prev=null;
        }else{
          //iterate from the head
          Node<E> after;
          ret=(after=(head=Node.iterateAscending(head,index-1)).next).val;
          (after=after.next).prev=head;
          head.next=after;
        }
      }
      return ret;
    }
    @Override public void add(int index,E val){
      int size;
      if((size=++this.size-index)<=index){
        //the insertion point is closer to the tail
        var tail=this.tail;
        if(size==1){
          //the insertion point IS the tail
          tail.next=tail=new Node<E>(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          Node<E> before;
          (before=(tail=Node.iterateDescending(tail,size-2)).prev).next=before=new Node<E>(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        Node<E> head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new Node<E>(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new Node<E>(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          Node<E> after;
          (after=(head=Node.iterateAscending(head,index-1)).next).prev=after=new Node<E>(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(E val){
      Node<E> tail;
      if((tail=this.tail)==null){
        this.head=tail=new Node<E>(val);
      }else{
        tail.next=tail=new Node<E>(tail,val);
      }
      this.tail=tail;
      ++this.size;
    }
    @Override public void push(E val){
      Node<E> head;
      if((head=this.head)==null){
        tail=head=new Node<E>(val);
      }else{
        head.prev=head=new Node<E>(val,head);
      }
      this.head=head;
      ++this.size;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size!=0){
        var curr=this.head;
        do{
          out.writeObject(curr.val);
        }
        while((curr=curr.next)!=null);
      }
    }
    @SuppressWarnings("unchecked")
    @Override public void readExternal(ObjectInput in) throws IOException
      ,ClassNotFoundException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        Node<E> curr;
        for(this.head=curr=new Node<E>((E)in.readObject());--size!=0;curr=curr.next=new Node<E>(curr,(E)in.readObject())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        Node<E> head,newTail;
        final var newHead=newTail=new Node<E>((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new Node<E>(newTail,(head=head.next).val),++i){}
        return new UncheckedList<E>(newHead,size,newTail);
      }
      return new UncheckedList<E>();
    }
    private static boolean isEqualToHelper(Node<?> thisHead,Node<?> thatHead,Node<?> thisTail){
      for(;Objects.equals(thisHead.val,thatHead.val);thisHead=thisHead.next,thatHead=thatHead.next){
        if(thisHead==thisTail){
          return true;
        }
      }
      return false;
    }
    private boolean isEqualTo(int size,OmniList.OfRef<?> list){
      if(list instanceof RefDblLnkSeq){
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedList){
          final RefDblLnkSeq.CheckedList<?> that;
          final int thatModCount=(that=(RefDblLnkSeq.CheckedList<?>)dls).modCount;
          try{
            return size==that.size && isEqualToHelper(this.head,that.head,this.tail);
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.modCount);
          }
        }else if(list instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> that;
          final int thatModCount=(that=(RefDblLnkSeq.CheckedSubList<?>)list).modCount;
          try{
            return size==that.size && isEqualToHelper(this.head,that.head,this.tail);
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.root.modCount);
          }
        }else{
          final Node<?> thatHead,thisHead;
          return size==dls.size && ((thatHead=dls.head)==(thisHead=this.head)|| isEqualToHelper(thisHead,thatHead,this.tail));
        }
      }else if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> uncheckedList;
        if((uncheckedList=(RefArrSeq.UncheckedList<?>)list) instanceof RefArrSeq.CheckedList){
          final RefArrSeq.CheckedList<?> that;
          final int thatModCount=(that=(RefArrSeq.CheckedList<?>)uncheckedList).modCount;
          try{
            return size==that.size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,this.head);
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.modCount);
          }
        }else{
          return size==uncheckedList.size && SequenceEqualityUtil.isEqualTo(uncheckedList.arr,0,size,this.head);
        }
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        final int rootOffset;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,this.head);
      }else{
        //must be RefArrSeq.CheckedSubList
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot=(subList=(RefArrSeq.CheckedSubList<?>)list).root;
        final int modCount=subList.modCount;
        try{
          final int rootOffset;
          return subList.size == size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,this.head);
        }finally{
          CheckedCollection.checkModCount(modCount,thatRoot.modCount);
        }
      }
    }
    private boolean isEqualTo(Node<E> thisHead,int size,OmniList.OfRef<?> list){
      if(list instanceof RefDblLnkSeq){
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedList){
          final RefDblLnkSeq.CheckedList<?> that;
          final int thatModCount=(that=(RefDblLnkSeq.CheckedList<?>)dls).modCount;
          try{
            return size==that.size && isEqualToHelper(that.head,thisHead,that.tail);
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.modCount);
          }
        }else if(list instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> that;
          final int thatModCount=(that=(RefDblLnkSeq.CheckedSubList<?>)list).modCount;
          try{
            return size==that.size && isEqualToHelper(that.head,thisHead,that.tail);
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.root.modCount);
          }
        }else{
          final Node<?> thatHead;
          return dls.size==size && ((thatHead=dls.head)==thisHead|| isEqualToHelper(thatHead,thisHead,dls.tail));
        }
      }else if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> uncheckedList;
        if((uncheckedList=(RefArrSeq.UncheckedList<?>)list) instanceof RefArrSeq.CheckedList){
          final RefArrSeq.CheckedList<?> that;
          final int thatModCount=(that=(RefArrSeq.CheckedList<?>)uncheckedList).modCount;
          try{
            return size==that.size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.modCount);
          }
        }else{
          return size==uncheckedList.size && SequenceEqualityUtil.isEqualTo(uncheckedList.arr,0,size,thisHead);
        }
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        final int rootOffset;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }else{
        //must be RefArrSeq.CheckedSubList
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot=(subList=(RefArrSeq.CheckedSubList<?>)list).root;
        final int modCount=subList.modCount;
        try{
          final int rootOffset;
          return size==subList.size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
        }finally{
          CheckedCollection.checkModCount(modCount,thatRoot.modCount);
        }
      }
    }
    private boolean isEqualTo(Node<E> thisHead,int size,OmniList.OfBoolean list){
      if(list instanceof AbstractBooleanArrSeq){
        final AbstractBooleanArrSeq abstractArrSeq;
        if(size!=(abstractArrSeq=(AbstractBooleanArrSeq)list).size){
          return false;
        }
        if(abstractArrSeq instanceof PackedBooleanArrSeq.UncheckedList){
          return SequenceEqualityUtil.isEqualTo(0,((PackedBooleanArrSeq.UncheckedList)abstractArrSeq).words,size,thisHead);
        }else{
          return SequenceEqualityUtil.isEqualTo(((BooleanArrSeq.UncheckedList)abstractArrSeq).arr,0,size,thisHead);
        }
      }else if(list instanceof BooleanDblLnkSeq){
        final BooleanDblLnkSeq dls;
        if((dls=(BooleanDblLnkSeq)list) instanceof BooleanDblLnkSeq.CheckedSubList){
          final BooleanDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(BooleanDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(dls.head,dls.tail,thisHead);
      }else if(list instanceof PackedBooleanArrSeq.UncheckedSubList){
        final PackedBooleanArrSeq.UncheckedSubList subList;
        final int rootOffset;
        return size==(subList=(PackedBooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(rootOffset=subList.rootOffset,subList.root.words,rootOffset+size,thisHead);
      }else if(list instanceof PackedBooleanArrSeq.CheckedSubList){
        final PackedBooleanArrSeq.CheckedSubList subList;
        final PackedBooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(PackedBooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int rootOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(rootOffset=subList.rootOffset,thatRoot.words,rootOffset+size,thisHead);
      }else if(list instanceof BooleanArrSeq.UncheckedSubList){
        final BooleanArrSeq.UncheckedSubList subList;
        final int rootOffset;
        return size==(subList=(BooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }else{
        //Must be BooleanArrSeq.CheckedSubList
        final BooleanArrSeq.CheckedSubList subList;
        final BooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(BooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int rootOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }
    }
    private boolean isEqualTo(Node<E> thisHead,int size,OmniList.OfByte list){
      if(list instanceof ByteArrSeq.UncheckedList){
        final ByteArrSeq.UncheckedList that;
        return size==(that=(ByteArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
      }else if(list instanceof ByteDblLnkSeq){
        final ByteDblLnkSeq dls;
        if((dls=(ByteDblLnkSeq)list) instanceof ByteDblLnkSeq.CheckedSubList){
          final ByteDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(ByteDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(dls.head,dls.tail,thisHead);
      }else if(list instanceof ByteArrSeq.UncheckedSubList){
        final ByteArrSeq.UncheckedSubList subList;
        final int rootOffset;
        return size==(subList=(ByteArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }else{
        //Must be ByteArrSeq.CheckedSubList
        final ByteArrSeq.CheckedSubList subList;
        final ByteArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(ByteArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int rootOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }
    }
    private boolean isEqualTo(Node<E> thisHead,int size,OmniList.OfChar list){
      if(list instanceof CharArrSeq.UncheckedList){
        final CharArrSeq.UncheckedList that;
        return size==(that=(CharArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
      }else if(list instanceof CharDblLnkSeq){
        final CharDblLnkSeq dls;
        if((dls=(CharDblLnkSeq)list) instanceof CharDblLnkSeq.CheckedSubList){
          final CharDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(CharDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(dls.head,dls.tail,thisHead);
      }else if(list instanceof CharArrSeq.UncheckedSubList){
        final CharArrSeq.UncheckedSubList subList;
        final int rootOffset;
        return size==(subList=(CharArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }else{
        //Must be CharArrSeq.CheckedSubList
        final CharArrSeq.CheckedSubList subList;
        final CharArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(CharArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int rootOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }
    }
    private boolean isEqualTo(Node<E> thisHead,int size,OmniList.OfShort list){
      if(list instanceof ShortArrSeq.UncheckedList){
        final ShortArrSeq.UncheckedList that;
        return size==(that=(ShortArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
      }else if(list instanceof ShortDblLnkSeq){
        final ShortDblLnkSeq dls;
        if((dls=(ShortDblLnkSeq)list) instanceof ShortDblLnkSeq.CheckedSubList){
          final ShortDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(ShortDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(dls.head,dls.tail,thisHead);
      }else if(list instanceof ShortArrSeq.UncheckedSubList){
        final ShortArrSeq.UncheckedSubList subList;
        final int rootOffset;
        return size==(subList=(ShortArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }else{
        //Must be ShortArrSeq.CheckedSubList
        final ShortArrSeq.CheckedSubList subList;
        final ShortArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(ShortArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int rootOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }
    }
    private boolean isEqualTo(Node<E> thisHead,int size,OmniList.OfInt list){
      if(list instanceof IntArrSeq.UncheckedList){
        final IntArrSeq.UncheckedList that;
        return size==(that=(IntArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
      }else if(list instanceof IntDblLnkSeq){
        final IntDblLnkSeq dls;
        if((dls=(IntDblLnkSeq)list) instanceof IntDblLnkSeq.CheckedSubList){
          final IntDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(IntDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(dls.head,dls.tail,thisHead);
      }else if(list instanceof IntArrSeq.UncheckedSubList){
        final IntArrSeq.UncheckedSubList subList;
        final int rootOffset;
        return size==(subList=(IntArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }else{
        //Must be IntArrSeq.CheckedSubList
        final IntArrSeq.CheckedSubList subList;
        final IntArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(IntArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int rootOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }
    }
    private boolean isEqualTo(Node<E> thisHead,int size,OmniList.OfLong list){
      if(list instanceof LongArrSeq.UncheckedList){
        final LongArrSeq.UncheckedList that;
        return size==(that=(LongArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
      }else if(list instanceof LongDblLnkSeq){
        final LongDblLnkSeq dls;
        if((dls=(LongDblLnkSeq)list) instanceof LongDblLnkSeq.CheckedSubList){
          final LongDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(LongDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(dls.head,dls.tail,thisHead);
      }else if(list instanceof LongArrSeq.UncheckedSubList){
        final LongArrSeq.UncheckedSubList subList;
        final int rootOffset;
        return size==(subList=(LongArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }else{
        //Must be LongArrSeq.CheckedSubList
        final LongArrSeq.CheckedSubList subList;
        final LongArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(LongArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int rootOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }
    }
    private boolean isEqualTo(Node<E> thisHead,int size,OmniList.OfFloat list){
      if(list instanceof FloatArrSeq.UncheckedList){
        final FloatArrSeq.UncheckedList that;
        return size==(that=(FloatArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
      }else if(list instanceof FloatDblLnkSeq){
        final FloatDblLnkSeq dls;
        if((dls=(FloatDblLnkSeq)list) instanceof FloatDblLnkSeq.CheckedSubList){
          final FloatDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(FloatDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(dls.head,dls.tail,thisHead);
      }else if(list instanceof FloatArrSeq.UncheckedSubList){
        final FloatArrSeq.UncheckedSubList subList;
        final int rootOffset;
        return size==(subList=(FloatArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }else{
        //Must be FloatArrSeq.CheckedSubList
        final FloatArrSeq.CheckedSubList subList;
        final FloatArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(FloatArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int rootOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }
    }
    private boolean isEqualTo(Node<E> thisHead,int size,OmniList.OfDouble list){
      if(list instanceof DoubleArrSeq.UncheckedList){
        final DoubleArrSeq.UncheckedList that;
        return size==(that=(DoubleArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(that.arr,0,size,thisHead);
      }else if(list instanceof DoubleDblLnkSeq){
        final DoubleDblLnkSeq dls;
        if((dls=(DoubleDblLnkSeq)list) instanceof DoubleDblLnkSeq.CheckedSubList){
          final DoubleDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(DoubleDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(dls.head,dls.tail,thisHead);
      }else if(list instanceof DoubleArrSeq.UncheckedSubList){
        final DoubleArrSeq.UncheckedSubList subList;
        final int rootOffset;
        return size==(subList=(DoubleArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(subList.root.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }else{
        //Must be DoubleArrSeq.CheckedSubList
        final DoubleArrSeq.CheckedSubList subList;
        final DoubleArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(DoubleArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int rootOffset;
        return subList.size==size && SequenceEqualityUtil.isEqualTo(thatRoot.arr,rootOffset=subList.rootOffset,rootOffset+size,thisHead);
      }
    }
    private static boolean isEqualTo(ListIterator<?> itr,Node<?> head,Node<?> tail){ 
      while(itr.hasNext() && Objects.equals(head.val,itr.next())){
        if(head==tail){
          return !itr.hasNext();
        }
        head=head.next;
      }
      return false;
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractOmniCollection){
          if(list instanceof OmniList.OfRef){
            return this.isEqualTo(size,(OmniList.OfRef<?>)list);
          }else if(list instanceof OmniList.OfInt){
            return this.isEqualTo(this.head,size,(OmniList.OfInt)list);
          }else if(list instanceof OmniList.OfFloat){
            return this.isEqualTo(this.head,size,(OmniList.OfFloat)list);
          }else if(list instanceof OmniList.OfLong){
            return this.isEqualTo(this.head,size,(OmniList.OfLong)list);
          }else if(list instanceof OmniList.OfDouble){
            return this.isEqualTo(this.head,size,(OmniList.OfDouble)list);
          }else if(list instanceof OmniList.OfByte){
            return this.isEqualTo(this.head,size,(OmniList.OfByte)list);
          }else if(list instanceof OmniList.OfChar){
            return this.isEqualTo(this.head,size,(OmniList.OfChar)list);
          }else if(list instanceof OmniList.OfShort){
            return this.isEqualTo(this.head,size,(OmniList.OfShort)list);
          }else{
            return this.isEqualTo(this.head,size,(OmniList.OfBoolean)list);
          }
        }else{
          return UncheckedList.isEqualTo(list.listIterator(),this.head,this.tail);
        }
      }
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveValNonNull(head,val);
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Boolean val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Byte val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Character val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Short val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Integer val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Long val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Float val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Double val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveValNonNull(Node<E> head
    ,Object nonNull
    ){
      {
        if(nonNull.equals(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(Node<E> prev;(head=(prev=head).next)!=null;){
          if(nonNull.equals(head.val)){
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveValNull(Node<E> head
    ){
      {
        if(null==(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(Node<E> prev;(head=(prev=head).next)!=null;){
          if(null==(head.val)){
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveVal(Node<E> head
    ,Predicate<? super E> pred
    ){
      {
        if(pred.test(head.val)){
          if(--size==0){
            this.head=null;
            this.tail=null;
          }else{
            this.head=head=head.next;
            head.prev=null;
          }
          return true;
        }
        for(Node<E> prev;(head=(prev=head).next)!=null;){
          if(pred.test(head.val)){
            if((head=head.next)==null){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      return new DescendingItr<E>(this);
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new AscendingItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      return new BidirectionalItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      return new BidirectionalItr<E>(this,((RefDblLnkSeq<E>)this).getItrNode(index,this.size),index);
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      final int subListSize;
      if((subListSize=toIndex-fromIndex)!=0){
        final int tailDist;
        final Node<E> subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=Node.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?Node.iterateDescending(subListTail,subListSize-1):Node.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=Node.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?Node.iterateAscending(subListHead,subListSize-1):Node.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList<E>(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList<E>(this,fromIndex);
    }
    @Override public E getLast(){
      return tail.val;
    }
    @Override public boolean offerFirst(E val){
      push((E)val);
      return true;
    }
    @Override public boolean offerLast(E val){
      addLast((E)val);
      return true;
    }
    @Override public void addFirst(E val){
      push((E)val);
    }
    @Override public E removeFirst(){
      return pop();
    }
    @Override public boolean removeFirstOccurrence(Object val){
      return remove(val);
    }
    @Override public E element(){
      return head.val;
    }
    @Override public boolean offer(E val){
      addLast((E)val);
      return true;
    }
    @Override public int search(boolean val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedsearchNonNull(head,val);
            }
            return Node.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Boolean val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return Node.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Byte val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return Node.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Character val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return Node.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Short val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return Node.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Integer val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return Node.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Long val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return Node.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Float val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return Node.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Double val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return Node.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return Node.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(int val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(long val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(float val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(double val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Object val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrenceNonNull(tail,val);
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(byte val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(char val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(short val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Boolean val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Byte val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Character val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Short val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Integer val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Long val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Float val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeLastOccurrence(Double val){
      {
        {
          final Node<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return uncheckedremoveLastOccurrence(tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrenceNonNull(Node<E> tail
    ,Object nonNull
    ){
      {
        if(nonNull.equals(tail.val)){
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          this.size=size-1;
          return true;
        }
        for(Node<E> next;(tail=(next=tail).prev)!=null;){
          if(nonNull.equals(tail.val)){
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveLastOccurrenceNull(Node<E> tail
    ){
      {
        if(null==(tail.val)){
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --this.size;
          return true;
        }
        for(Node<E> next;(tail=(next=tail).prev)!=null;){
          if(null==(tail.val)){
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveLastOccurrence(Node<E> tail
    ,Predicate<? super E> pred
    ){
      {
        if(pred.test(tail.val)){
          if((tail=tail.prev)==null){
            this.head=null;
            this.tail=null;
          }else{
            this.tail=tail;
            tail.next=null;
          }
          --this.size;
          return true;
        }
        for(Node<E> next;(tail=(next=tail).prev)!=null;){
          if(pred.test(tail.val)){
            if((tail=tail.prev)==null){
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
              next.prev=tail;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    @Override public E remove(){
      return pop();
    }
    @Override public E pollFirst(){
      return poll();
    }
    @Override public E peekFirst(){
      return peek();
    }
    @Override public E getFirst(){
      return element();
    }
    @Override public E poll(){
      Node<E> head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (head=head.next).prev=null;
          this.head=head;
        }
        return ret;
      }
      return null;
    }
    @Override public E pollLast(){
      Node<E> tail;
      if((tail=this.tail)!=null){
        final var ret=(tail.val);
        if(--this.size==0){
          this.head=null;
          this.tail=null;
        }else{
          (tail=tail.prev).next=null;
          this.tail=tail;
        }
        return ret;
      }
      return null;
    }
    @Override public E peek(){
      final Node<E> head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public E peekLast(){
      final Node<E> tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public boolean removeIf(Predicate<? super E> filter){
      final Node<E> head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    private int removeIfHelper(Node<E> prev,Node<E> tail,Predicate<? super E> filter){
      int numSurvivors=1;
      outer:for(Node<E> next;prev!=tail;++numSurvivors,prev=next){
        if(filter.test((next=prev.next).val)){
          do{
            if(next==tail){
              this.tail=prev;
              prev.next=null;
              break outer;
            }
          }
          while(filter.test((next=next.next).val));
          prev.next=next;
          next.prev=prev;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(Node<E> prev,Node<E> curr,Node<E> tail,Predicate<? super E> filter){
      int numSurvivors=0;
      while(curr!=tail) {
        if(!filter.test((curr=curr.next).val)){
          prev.next=curr;
          curr.prev=prev;
          do{
            ++numSurvivors;
            if(curr==tail){
              return numSurvivors;
            }
          }while(!filter.test((curr=(prev=curr).next).val));
        }
      }
      prev.next=null;
      this.tail=prev;
      return numSurvivors;
    }
    boolean uncheckedRemoveIf(Node<E> head,Predicate<? super E> filter){
      if(filter.test(head.val)){
        for(var tail=this.tail;head!=tail;){
          if(!filter.test((head=head.next).val)){
            this.size=removeIfHelper(head,tail,filter);
            head.prev=null;
            this.head=head;
            return true;  
          }
        }
        this.head=null;
        this.tail=null;
        this.size=0;
        return true;
      }else{
        int numSurvivors=1;
        for(final var tail=this.tail;head!=tail;++numSurvivors){
          final Node<E> prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    private static class DescendingItr<E> extends AscendingItr<E>{
      private DescendingItr(DescendingItr<E> itr){
        super(itr);
      }
      private DescendingItr(UncheckedList<E> parent){
        super(parent,parent.tail);
      }
      @Override public Object clone(){
        return new DescendingItr<E>(this);
      }
      @Override public void remove(){
        final UncheckedList<E> parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          Node<E> curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            Node<E> lastRet;
            if((lastRet=curr.next)==parent.tail){
              parent.tail=curr;
              curr.next=null;
            }else{
              curr.next=lastRet=lastRet.next;
              lastRet.prev=curr;
            }
          }
        }
      }
      @Override public E next(){
        final Node<E> curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override void uncheckedForEachRemaining(Node<E> curr,Consumer<? super E> action){
        Node.uncheckedForEachDescending(curr,action);
      }
    }
    private static class AscendingItr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final UncheckedList<E> parent;
      transient Node<E> curr;
      private AscendingItr(AscendingItr<E> itr){
        this.parent=itr.parent;
        this.curr=itr.curr;
      }
      private AscendingItr(UncheckedList<E> parent,Node<E> curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedList<E> parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public Object clone(){
        return new AscendingItr<E>(this);
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public void remove(){
        final UncheckedList<E> parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          Node<E> curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            Node<E> lastRet;
            if((lastRet=curr.prev)==parent.head){
              parent.head=curr;
              curr.prev=null;
            }else{
              curr.prev=lastRet=lastRet.prev;
              lastRet.next=curr;
            }
          }
        }
      }
      @Override public E next(){
        final Node<E> curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      void uncheckedForEachRemaining(Node<E> curr,Consumer<? super E> action){
        Node.uncheckedForEachAscending(curr,action);
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final Node<E> curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr<E> extends AscendingItr<E> implements OmniListIterator.OfRef<E>{
      private transient int currIndex;
      private transient Node<E> lastRet;
      private BidirectionalItr(BidirectionalItr<E> itr){
        super(itr);
        this.currIndex=itr.currIndex;
        this.lastRet=itr.lastRet;
      }
      private BidirectionalItr(UncheckedList<E> parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList<E> parent,Node<E> curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public Object clone(){
        return new BidirectionalItr<E>(this);
      }
      @Override public E next(){
        final Node<E> curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public E previous(){
        Node<E> curr;
        this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public boolean hasPrevious(){
        return currIndex>0;
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public int previousIndex(){
        return currIndex-1;
      }
      @Override public void add(E val){
        final UncheckedList<E> parent;
        Node<E> newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new Node<E>(val);
          }else{
            (newNode=parent.tail).next=newNode=new Node<E>(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new Node<E>(val,newNode);
            parent.head=newNode;
          }else{
            final Node<E> tmp;
            (newNode=curr).prev=newNode=new Node<E>(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(E val){
        lastRet.val=val;
      }
      @Override public void remove(){
        Node<E> lastRet,curr;
        if((curr=(lastRet=this.lastRet).next)==this.curr){
          --currIndex;
        }else{
          this.curr=curr;
        }
        final UncheckedList<E> parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          if(lastRet==parent.tail){
            parent.tail=lastRet=lastRet.prev;
            lastRet.next=null;
          }else if(lastRet==parent.head){
            parent.head=curr;
            curr.prev=null;
          }else{
            curr.prev=lastRet=lastRet.prev;
            lastRet.next=curr;
          }
        }
        this.lastRet=null;
      }
      @Override void uncheckedForEachRemaining(Node<E> curr,Consumer<? super E> action){
        Node.uncheckedForEachAscending(curr,action);
        final UncheckedList<E> parent;
        this.lastRet=(parent=this.parent).tail;
        this.currIndex=parent.size;
      }
    }
  }
}
