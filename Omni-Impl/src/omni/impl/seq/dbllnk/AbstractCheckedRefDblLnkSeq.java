package omni.impl.seq.dbllnk;
import omni.impl.CheckedCollection;
abstract class AbstractCheckedRefDblLnkSeq<E>extends AbstractRefDblLnkSeq<E>{
  int getParentOffset(){
    return 0;
  }
  AbstractCheckedRefDblLnkSeq<E> getParent(){
    return null;
  }
  static void decrementSize(AbstractCheckedRefDblLnkSeq<?> curr){
    do{
      --curr.size;
      ++curr.modCount;
    }while((curr=curr.getParent())!=null);
  }
  static void decrementSize(AbstractCheckedRefDblLnkSeq<?> curr,int numRemoved){
    do{
      curr.size-=numRemoved;
    }while((curr=curr.getParent())!=null);
  }
  static void incrementSize(AbstractCheckedRefDblLnkSeq<?> curr){
    do{
      ++curr.size;
    }while((curr=curr.getParent())!=null);
  }
  static <E> void uncheckedAppend(AbstractCheckedRefDblLnkSeq<E> curr,Node<E> oldTail,final Node<E> newTail){
    do{
      ++curr.size;
      curr.tail=newTail;
    }while((curr=curr.getParent()).tail==oldTail);
    incrementSize(curr);
  }
  static <E> void uncheckedPrepend(AbstractCheckedRefDblLnkSeq<E> curr,Node<E> oldHead,final Node<E> newHead){
    do{
      ++curr.size;
      curr.head=newHead;
    }while((curr=curr.getParent()).head==oldHead);
    incrementSize(curr);
  }
  transient int modCount;
  public AbstractCheckedRefDblLnkSeq(){
    super();
  }
  public AbstractCheckedRefDblLnkSeq(Node<E> head,int size,Node<E> tail){
    super(head,size,tail);
  }
  public AbstractCheckedRefDblLnkSeq(Node<E> headAndTail){
    super(headAndTail);
  }
  @Override public E get(int index){
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    return super.getReadableNode(index,size-index).val;
  }
  @Override public void put(int index,E val){
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    super.getReadableNode(index,size-index).val=val;
  }
  @Override public E set(int index,E val){
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    Node<E> node;
    final var oldVal=(node=super.getReadableNode(index,size-index)).val;
    node.val=val;
    return oldVal;
  }
}
