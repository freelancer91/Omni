package omni.impl.seq;
import omni.util.RefSortUtil;
import omni.api.OmniList;
import omni.impl.RefDblLnkNode;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import omni.util.OmniArray;
import omni.util.OmniPred;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.Comparator;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.api.OmniDeque;
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
public abstract class RefDblLnkSeq<E> extends AbstractSeq implements
   OmniList.OfRef<E>
{
  private static final long serialVersionUID=1L;
  transient RefDblLnkNode<E> head;
  transient RefDblLnkNode<E> tail;
  private  RefDblLnkSeq(){
  }
  private RefDblLnkSeq(RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  abstract void addLast(E val);
  @Override public boolean add(E val){
    addLast(val);
    return true;
  }
  private void iterateDescendingAndInsert(int dist,RefDblLnkNode<E> after,RefDblLnkNode<E> newNode){
    newNode.next=after=RefDblLnkNode.iterateDescending(after,dist-2);
    final RefDblLnkNode<E> before;
    newNode.prev=before=after.prev;
    before.next=newNode;
    after.prev=newNode;
  }
  private void iterateAscendingAndInsert(int dist,RefDblLnkNode<E> before,RefDblLnkNode<E> newNode){
    newNode.prev=before=RefDblLnkNode.iterateAscending(before,dist-1);
    final RefDblLnkNode<E> after;
    newNode.next=after=before.next;
    before.next=newNode;
    after.prev=newNode;
  }
  private void insertNode(int index,RefDblLnkNode<E> newNode){
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
      RefDblLnkNode<E> head;
      if((head=this.head)==null){
        //the root was empty, so initialize it
        this.head=newNode;
        this.tail=newNode;
      }else if(index==0){
        //the insertion point IS the head
        head.prev=newNode;
        newNode.next=head;
        this.head=newNode;
      }else{
        //iterate from the root's head 
        iterateAscendingAndInsert(index,head,newNode);
      }
    }
  }
  private static <E> int markSurvivors(RefDblLnkNode<E> curr,int numLeft,Predicate<? super E> filter,long[] survivorSet){
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
  private static <E> long markSurvivors(RefDblLnkNode<E> curr,int numLeft,Predicate<? super E> filter){
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
  private RefDblLnkNode<E> getNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      return RefDblLnkNode.iterateDescending(tail,size-1);
    }else{
      //the node is closer to the head
      return RefDblLnkNode.iterateAscending(head,index);
    }
  }
  private RefDblLnkNode<E> getItrNode(int index,int size){
    if((size-=index)<=index){
      //the node is closer to the tail
      switch(size){
      case 0:
        return null;
      case 1:
        return tail;
      default:
        return RefDblLnkNode.uncheckedIterateDescending(tail,size-1);
      }
    }else{
      //the node is closer to the head
      return RefDblLnkNode.iterateAscending(head,index);
    }
  }
  @Override public E set(int index,E val){
    RefDblLnkNode<E> tmp;
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
    final RefDblLnkNode<E> head;
    if((head=this.head)!=null){
      RefDblLnkNode.uncheckedForEachAscending(head,tail,action);
    }
  }
  @Override public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      RefDblLnkNode.uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),this.tail,size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      RefDblLnkNode.uncheckedCopyInto(dst,this.tail,size);
    }
    return dst;
  }
  @Override public Object[] toArray(){
    int size;
    if((size=this.size)!=0){
      final Object[] dst;
      RefDblLnkNode.uncheckedCopyInto(dst=new Object[size],tail,size);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  @Override public void replaceAll(UnaryOperator<E> operator){
    final RefDblLnkNode<E> head;
    if((head=this.head)!=null){
      RefDblLnkNode.uncheckedReplaceAll(head,tail,operator);
    }
  }
  @Override public void sort(Comparator<? super E> sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
        if(sorter==null){
          RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
        }else{
          {
            RefSortUtil.uncheckedStableSort(tmp,0,size,sorter);
          }
        }
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableAscendingSort()
  {
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
          RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void stableDescendingSort()
  {
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
          RefSortUtil.uncheckedStableDescendingSort(tmp,0,size);
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableAscendingSort()
  {
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
          RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableDescendingSort()
  {
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
          RefSortUtil.uncheckedUnstableDescendingSort(tmp,0,size);
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public void unstableSort(Comparator<? super E> sorter){
    final int size;
    if((size=this.size)>1){
      //todo: see about making an in-place sort implementation rather than copying to an array
      final Object[] tmp;
      final RefDblLnkNode<E> tail;
      RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
      {
        if(sorter==null){
          RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
        }else{
          {
            RefSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
          }
        }
      }
      RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
    }
  }
  @Override public String toString(){
    final RefDblLnkNode<E> head;
    if((head=this.head)!=null){
      final StringBuilder builder;
      RefDblLnkNode.uncheckedToString(head,tail,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public int hashCode(){
    final RefDblLnkNode<E> head;
    if((head=this.head)!=null){
      return RefDblLnkNode.uncheckedHashCode(head,tail);
    }
    return 1;
  }
  @Override public boolean contains(boolean val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedcontainsNonNull(head,tail,val);
          }
          return RefDblLnkNode.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(short val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Boolean val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }
          return RefDblLnkNode.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Byte val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }
          return RefDblLnkNode.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Character val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
          }
          return RefDblLnkNode.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Short val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
          }
          return RefDblLnkNode.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Integer val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
          }
          return RefDblLnkNode.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Long val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
          }
          return RefDblLnkNode.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Float val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
          }
          return RefDblLnkNode.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Double val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return RefDblLnkNode.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(int val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(long val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(float val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(double val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Object val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedindexOfNonNull(head,tail,val);
          }
          return RefDblLnkNode.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(byte val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(char val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(short val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Boolean val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }
          return RefDblLnkNode.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Byte val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }
          return RefDblLnkNode.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Character val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
          }
          return RefDblLnkNode.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Short val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
          }
          return RefDblLnkNode.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Integer val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
          }
          return RefDblLnkNode.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Long val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
          }
          return RefDblLnkNode.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Float val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
          }
          return RefDblLnkNode.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int indexOf(Double val){
    {
      {
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return RefDblLnkNode.uncheckedindexOfNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(boolean val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(int val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(long val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(float val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(double val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Object val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedlastIndexOfNonNull(size,tail,val);
          }
          return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(byte val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(char val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(short val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Boolean val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }
          return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Byte val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }
          return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Character val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
          }
          return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Short val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
          }
          return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Integer val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
          }
          return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Long val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
          }
          return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Float val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
          }
          return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  @Override public int lastIndexOf(Double val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null){
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  private static <E> int collapseBodyHelper(RefDblLnkNode<E> newHead,RefDblLnkNode<E> newTail,Predicate<? super E> filter){
    int numRemoved=0;
    outer:for(RefDblLnkNode<E> prev;(newHead=(prev=newHead).next)!=newTail;){
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
  private static class UncheckedSubList<E> extends RefDblLnkSeq<E>{
    private static final long serialVersionUID=1L;
    transient final UncheckedList<E> root;
    transient final UncheckedSubList<E> parent;
    transient final int parentOffset;
    private UncheckedSubList(UncheckedList<E> root,int rootOffset,RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList<E> parent,int parentOffset,RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
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
    private void bubbleUpAppend(RefDblLnkNode<E> oldTail,RefDblLnkNode<E> newTail){
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
    private void bubbleUpAppend(RefDblLnkNode<E> newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
      }
    }
    private void bubbleUpPrepend(RefDblLnkNode<E> oldHead,RefDblLnkNode<E> newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;currList.head=newHead,currList=currList.parent){
        ++currList.size;
        if(currList.head!=oldHead){
          currList.bubbleUpIncrementSize();
          return;
        }
      }
    }
    private void bubbleUpPrepend(RefDblLnkNode<E> newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
      }
    }
    private void bubbleUpRootInit(RefDblLnkNode<E> newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,RefDblLnkNode<E> newNode){
      RefDblLnkNode<E> after,before;   
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
          before=(after=RefDblLnkNode.iterateDescending(before,size-2)).prev;
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
          after=(before=RefDblLnkNode.uncheckedIterateAscending(after,index)).next;
          before.prev=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(RefDblLnkNode<E> newNode){
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
      final var newNode=new RefDblLnkNode<E>(val);
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
          RefDblLnkNode<E> currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new RefDblLnkNode<E>(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new RefDblLnkNode<E>(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new RefDblLnkNode<E>(val));
        }
      }else{
        RefDblLnkNode<E> newNode;
        bubbleUpRootInit(newNode=new RefDblLnkNode<E>(val));
        this.size=1;
        root.head=newNode;
        root.tail=newNode;
      }
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    private void bubbleUpPeelHead(RefDblLnkNode<E> newHead,RefDblLnkNode<E> oldHead){
      var curr=parent;
      do{
        if(curr.head!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelHead(RefDblLnkNode<E> newHead){
      var curr=this;
      do{
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)==null);
    }
    private void bubbleUpPeelTail(RefDblLnkNode<E> newTail,RefDblLnkNode<E> oldTail){
      var curr=parent;
      do{
        if(curr.tail!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(RefDblLnkNode<E> newTail){
      var curr=this;
      do{
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)==null);
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
    private void peelTail(RefDblLnkNode<E> tail){
      RefDblLnkNode<E> after,before;
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
    private void removeLastNode(RefDblLnkNode<E> lastNode){
      RefDblLnkNode<E> after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        UncheckedList<E> root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          before.next=null;
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          after.prev=null;
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          var curr=parent;
          do{
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                --curr.size;
                curr.tail=before;
              }
              while((curr=curr.parent)!=null);
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
          while((curr=curr.parent)!=null);
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(RefDblLnkNode<E> head){
      RefDblLnkNode<E> after,before;
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
          RefDblLnkNode<E> before;
          ret=(before=( tail=RefDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          RefDblLnkNode<E> after;
          ret=(after=( head=RefDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(Predicate<? super E> filter){
      final RefDblLnkNode<E> head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    private void collapsehead(RefDblLnkNode<E> oldhead,RefDblLnkNode<E> tail,Predicate<? super E> filter
    ){
      int numRemoved=1;
      RefDblLnkNode<E> newhead;
      outer:
      for(newhead=oldhead.next;;
      ++numRemoved,newhead=newhead.next){
        if(newhead==tail)
        {
          break;
        }
        if(!filter.test(newhead.val)){
          RefDblLnkNode<E> prev,curr;
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
      RefDblLnkNode<E> tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(RefDblLnkNode<E> oldtail,RefDblLnkNode<E> head,Predicate<? super E> filter
    ){
      int numRemoved=1;
      RefDblLnkNode<E> newtail;
      outer:
      for(newtail=oldtail.prev;;
      ++numRemoved,newtail=newtail.next){
        if(newtail==head)
        {
          break;
        }
        if(!filter.test(newtail.val)){
          RefDblLnkNode<E> next,curr;
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
      RefDblLnkNode<E> tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void bubbleUpCollapseHeadAndTail(RefDblLnkNode<E> oldHead,RefDblLnkNode<E> newHead,int numRemoved,RefDblLnkNode<E> newTail,RefDblLnkNode<E> oldTail){
      this.head=newHead;
      this.tail=newTail;
      final RefDblLnkNode<E> after,before=oldHead.prev;
      if((after=oldHead.next)==null){
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
    private boolean uncheckedRemoveIf(RefDblLnkNode<E> head,Predicate<? super E> filter){
      RefDblLnkNode<E> tail;
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
    private void clearAllHelper(int size,RefDblLnkNode<E> head,RefDblLnkNode<E> tail,UncheckedList<E> root){
      RefDblLnkNode<E> before,after=tail.next;
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
      var curr=this;
      do{
        curr.size-=numRemoved;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpClearBody(RefDblLnkNode<E> before,RefDblLnkNode<E> head,int numRemoved,RefDblLnkNode<E> tail,RefDblLnkNode<E> after){
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
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(RefDblLnkNode<E> tail, RefDblLnkNode<E> after,int numRemoved){
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
    private void bubbleUpClearTail(RefDblLnkNode<E> head, RefDblLnkNode<E> before,int numRemoved){
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
    private void collapseHeadAndTail(RefDblLnkNode<E> head,RefDblLnkNode<E> tail,Predicate<? super E> filter
    ){
      RefDblLnkNode<E> newHead;
      if((newHead=head.next)!=tail){
        for(int numRemoved=2;;++numRemoved){
          if(!filter.test(newHead.val)){
            RefDblLnkNode<E> prev;
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
    private boolean collapseBody(RefDblLnkNode<E> head,RefDblLnkNode<E> tail,Predicate<? super E> filter
    ){
      for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=tail;){
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
          bubbleUpDecrementSize(numRemoved);
          return true;
        }
      }
      return false;
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        RefDblLnkNode<E> head,newTail;
        final var newHead=newTail=new RefDblLnkNode<E>((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new RefDblLnkNode<E>(newTail,(head=head.next).val),++i){}
        return new UncheckedList<E>(newHead,size,newTail);
      }
      return new UncheckedList<E>();
    }
    private static class AscendingItr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final UncheckedSubList<E> parent;
      transient RefDblLnkNode<E> curr;
      private AscendingItr(UncheckedSubList<E> parent,RefDblLnkNode<E> curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedSubList<E> parent){
        this.parent=parent;
        this.curr=parent.head;
      }
      @Override public boolean hasNext(){
        return curr!=null;
      }
      @Override public E next(){
        final RefDblLnkNode<E> curr;
        this.curr=(curr=this.curr)==parent.tail?null:curr.next;
        return curr.val;
      }
      @Override public void remove(){
        UncheckedSubList<E> parent;
        if(--(parent=this.parent).size==0){
          parent.removeLastNode(parent.tail);
        }else{
          RefDblLnkNode<E> curr;
          if((curr=this.curr)==null){
            parent.peelTail(parent.tail);
          }else{
            RefDblLnkNode<E> lastRet;
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
        final RefDblLnkNode<E> curr;
        if((curr=this.curr)!=null){
          RefDblLnkNode.uncheckedForEachAscending(curr,parent.tail,action);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr<E> extends AscendingItr<E> implements OmniListIterator.OfRef<E>{
      private transient int currIndex;
      private transient RefDblLnkNode<E> lastRet;
      private BidirectionalItr(UncheckedSubList<E> parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedSubList<E> parent,RefDblLnkNode<E> curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public E next(){
        final RefDblLnkNode<E> curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public E previous(){
        RefDblLnkNode<E> curr;
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
            RefDblLnkNode<E> after,before,newNode;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                currList.bubbleUpIncrementSize();
                before.next=newNode=new RefDblLnkNode<E>(before,val,after);
              }else{
                currList.bubbleUpPrepend(newNode=new RefDblLnkNode<E>(val,after));
                root.head=newNode;
              }
              after.prev=newNode;
            }else{
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new RefDblLnkNode<E>(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new RefDblLnkNode<E>(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new RefDblLnkNode<E>(val));
          }
        }else{
          RefDblLnkNode<E> newNode;
          currList.bubbleUpRootInit(newNode=new RefDblLnkNode<E>(val));
          currList.size=1;
          root.head=newNode;
          root.tail=newNode;
        }
      }
      @Override public void remove(){
        RefDblLnkNode<E> lastRet;
        if((lastRet=this.lastRet).next==curr){
          --currIndex;
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
              RefDblLnkNode.eraseNode(lastRet);
              parent.bubbleUpDecrementSize();
            }
          }
        }
        --parent.root.size;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int bound;
        final UncheckedSubList<E> parent;
        if(this.currIndex<(bound=(parent=this.parent).size)){
          final RefDblLnkNode<E> lastRet;
          RefDblLnkNode.uncheckedForEachAscending(this.curr,lastRet=parent.tail,action);
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
        final RefDblLnkNode<E> subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=RefDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?RefDblLnkNode.uncheckedIterateDescending(subListTail,subListSize):RefDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=RefDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?RefDblLnkNode.uncheckedIterateAscending(subListHead,subListSize):RefDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new UncheckedSubList<E>(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new UncheckedSubList<E>(this,fromIndex);
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
    boolean uncheckedremoveValNonNull(RefDblLnkNode<E> head
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
          if(nonNull.equals((head=head.next).val)){
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(head);
            }else{
              RefDblLnkNode<E> before,after;
              (before=head.prev).next=(after=head.next);
              after.prev=before;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveValNull(RefDblLnkNode<E> head
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
          if(null==((head=head.next).val)){
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(head);
            }else{
              RefDblLnkNode<E> before,after;
              (before=head.prev).next=(after=head.next);
              after.prev=before;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveVal(RefDblLnkNode<E> head
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
          if(pred.test((head=head.next).val)){
            --root.size;
            --this.size;
            if(head==tail){
              peelTail(head);
            }else{
              RefDblLnkNode<E> before,after;
              (before=head.prev).next=(after=head.next);
              after.prev=before;
              bubbleUpDecrementSize();
            }
            return true;
          }
        }
      }
      return false;
    }
  }
  private static class CheckedSubList<E> extends RefDblLnkSeq<E>{
    private static final long serialVersionUID=1L;
    transient final CheckedList<E> root;
    transient final CheckedSubList<E> parent;
    transient final int parentOffset;
    transient int modCount;
    private CheckedSubList(CheckedList<E> root,int rootOffset,RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
      super(head,size,tail);
      this.root=root;
      this.parent=null;
      this.parentOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList<E> parent,int parentOffset,RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
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
    boolean uncheckedremoveValNonNull(RefDblLnkNode<E> head
    ,Object nonNull
    ){
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        if(nonNull.equals(head.val)){
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          --root.size;
          this.modCount=modCount;
          if(--this.size==0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }
        int size,numLeft=(size=this.size-1);
        for(;numLeft!=0;--numLeft){
          RefDblLnkNode<E> prev;
          if(nonNull.equals((head=(prev=head).next).val)){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            if(numLeft==1){
              this.tail=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            this.size=size;
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
    boolean uncheckedremoveValNull(RefDblLnkNode<E> head
    ){
      int modCount;
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(null==(head.val)){
          root.modCount=++modCount;
          --root.size;
          this.modCount=modCount;
          if(--this.size==0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
          if(null==(head.val)){
            this.modCount=modCount+1;
            if((head=head.next)==null){
              this.head=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --this.size;
            return true;
          }
        }
      }
      return false;
    }
    boolean uncheckedremoveVal(RefDblLnkNode<E> head
    ,Predicate<? super E> pred
    ){
      int modCount;
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      {
        if(pred.test(head.val)){
          root.modCount=++modCount;
          --root.size;
          this.modCount=modCount;
          if(--this.size==0){
            removeLastNode(head);
          }else{
            peelHead(head);
          }
          return true;
        }
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
          if(pred.test(head.val)){
            this.modCount=modCount+1;
            if((head=head.next)==null){
              this.head=prev;
              prev.next=null;
            }else{
              head.prev=prev;
              prev.next=head;
            }
            --this.size;
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
      private transient RefDblLnkNode<E> curr;
      private transient RefDblLnkNode<E> lastRet;
      private transient int currIndex;
      private BidirectionalItr(CheckedSubList<E> parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=parent.head;
      }
      private BidirectionalItr(CheckedSubList<E> parent,RefDblLnkNode<E> curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public E next(){
        final CheckedSubList<E> parent;
        CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
        final int currIndex;
        if((currIndex=this.currIndex)<parent.size){
          RefDblLnkNode<E> curr;
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
          RefDblLnkNode<E> curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
          this.currIndex=currIndex-1;
          return curr.val;
        }
        throw new NoSuchElementException();
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
        final RefDblLnkNode<E> lastRet;
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
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)>0){
          final int modCount=this.modCount;
          try{
            RefDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.root.modCount);
          }
          this.lastRet=parent.tail;
          this.curr=null;
          this.currIndex=size;
        }
      }
      @Override public void remove(){
        RefDblLnkNode<E> lastRet;
        if((lastRet=this.lastRet)!=null){
          CheckedSubList<E> parent;
          CheckedList<E> root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          parent.modCount=modCount;
          if(lastRet.next==curr){
            --currIndex;
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
                RefDblLnkNode.eraseNode(lastRet);
                parent.bubbleUpDecrementSize();
              }
            }
          }
          --root.size;
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
            RefDblLnkNode<E> after,before,newNode;
            if((after=this.curr)!=null){
              if((before=after.prev)!=null){
                currList.bubbleUpIncrementSize();
                before.next=newNode=new RefDblLnkNode<E>(before,val,after);
              }else{
                currList.bubbleUpPrepend(newNode=new RefDblLnkNode<E>(val,after));
                root.head=newNode;
              }
              after.prev=newNode;
            }else{
              if((after=(before=currList.tail).next)!=null){
                currList.bubbleUpAppend(before,newNode=new RefDblLnkNode<E>(before,val,after));
                after.prev=newNode;
              }else{
                currList.bubbleUpAppend(newNode=new RefDblLnkNode<E>(before,val));
                root.tail=newNode;
              }
              before.next=newNode;
            }
          }else{
            currList.bubbleUpInit(new RefDblLnkNode<E>(val));
          }
        }else{
          RefDblLnkNode<E> newNode;
          currList.bubbleUpRootInit(newNode=new RefDblLnkNode<E>(val));
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
        final RefDblLnkNode<E> subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=RefDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?RefDblLnkNode.uncheckedIterateDescending(subListTail,subListSize):RefDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=RefDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?RefDblLnkNode.uncheckedIterateAscending(subListHead,subListSize):RefDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList<E>(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList<E>(this,fromIndex);
    }
    @Override public Object clone(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int size;
      if((size=this.size)!=0){
        RefDblLnkNode<E> head,newTail;
        final var newHead=newTail=new RefDblLnkNode<E>((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new RefDblLnkNode<E>(newTail,(head=head.next).val),++i){}
        return new CheckedList<E>(newHead,size,newTail);
      }
      return new CheckedList<E>();
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
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
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOfNonNull(head,this.size,val);
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
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
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Character val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Integer val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedindexOf(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefDblLnkNode.uncheckedindexOfNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
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
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOfNonNull(size,tail,val);
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
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
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Character val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Integer val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean contains(boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
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
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontainsNonNull(head,this.size,val);
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
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
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Boolean val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Byte val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Character val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Short val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Integer val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Long val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Float val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Double val){
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefDblLnkNode.uncheckedcontainsNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    private static class SerializableSubList<E> implements Serializable{
      private static final long serialVersionUID=1L;
      private transient RefDblLnkNode<E> head;
      private transient RefDblLnkNode<E> tail;
      private transient int size;
      private transient final CheckedList<E>.ModCountChecker modCountChecker;
      private SerializableSubList(RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail,CheckedList<E>.ModCountChecker modCountChecker){
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
          RefDblLnkNode<E> curr;
          for(this.head=curr=new RefDblLnkNode<E>((E)ois.readObject());--size!=0;curr=curr.next=new RefDblLnkNode<E>(curr,(E)ois.readObject())){}
          this.tail=curr;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try{
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            var curr=this.head;
            do{
              oos.writeObject(curr.val);
            }while((curr=curr.next)!=null);
          }
        }finally{
          modCountChecker.checkModCount();
        }
      }
    }
    private Object writeReplace(){
      return new SerializableSubList<E>(this.head,this.size,this.tail,root.new ModCountChecker(this.modCount));
    }   
    private static <E> void pullSurvivorsDown(RefDblLnkNode<E> prev,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
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
          return;
        }
        if((marker<<=1)==0){
           word=survivorSet[++wordOffset];
           marker=1L;
        }
        prev=curr;
      }
    }
    private static <E> void pullSurvivorsDown(RefDblLnkNode<E> prev,long word,int numSurvivors,int numRemoved){
      for(long marker=1L;;marker<<=1){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr=curr.next;
              curr.prev=prev;
              return;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
          curr.prev=prev;
        }
        if(--numSurvivors==0){
          return;
        }
        prev=curr;
      }
    }
    private void bubbleUpPeelHead(RefDblLnkNode<E> newHead,RefDblLnkNode<E> oldHead){
      var curr=parent;
      do{
        if(curr.head!=oldHead){
          curr.bubbleUpPeelHead(newHead);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelHead(RefDblLnkNode<E> newHead){
      var curr=this;
      do{
        ++curr.modCount;
        curr.head=newHead;
        --curr.size; 
      }while((curr=curr.parent)==null);
    }
    private void bubbleUpPeelTail(RefDblLnkNode<E> newTail,RefDblLnkNode<E> oldTail){
      var curr=parent;
      do{
        if(curr.tail!=oldTail){
          curr.bubbleUpPeelTail(newTail);
          break;
        }
        ++curr.modCount;
        curr.size=0;
        curr.head=null;
        curr.tail=null;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpPeelTail(RefDblLnkNode<E> newTail){
      var curr=this;
      do{
        ++curr.modCount;
        curr.tail=newTail;
        --curr.size;
      }while((curr=curr.parent)==null);
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
    private void peelTail(RefDblLnkNode<E> tail){
      RefDblLnkNode<E> after,before;
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
    private void removeLastNode(RefDblLnkNode<E> lastNode){
      RefDblLnkNode<E> after,before=lastNode.prev;
      if((after=lastNode.next)==null){
        CheckedList<E> root;
        (root=this.root).tail=before;
        if(before==null){
          for(var curr=parent;curr!=null;
          ++curr.modCount,
          curr.head=null,curr.tail=null,curr.size=0,curr=curr.parent){}
          root.head=null;
        }else{
          before.next=null;
          bubbleUpPeelTail(before,lastNode);
        }
      }else{
        if(before==null){
          after.prev=null;
          bubbleUpPeelHead(after,lastNode);
          root.head=after;
        }else{
          var curr=parent;
          do{
            if(curr.head!=lastNode){
              do{
                if(curr.tail!=lastNode){
                  curr.uncheckedBubbleUpDecrementSize();
                  break;
                }
                ++curr.modCount;
                --curr.size;
                curr.tail=before;
              }
              while((curr=curr.parent)!=null);
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
          while((curr=curr.parent)!=null);
        }
      }
      this.head=null;
      this.tail=null;
    }
    private void peelHead(RefDblLnkNode<E> head){
      RefDblLnkNode<E> after,before;
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
          RefDblLnkNode<E> before;
          ret=(before=( tail=RefDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          RefDblLnkNode<E> after;
          ret=(after=( head=RefDblLnkNode.iterateAscending(head,index)).next).val;
          (after=after.next).prev=head;
          head.next=after;
          bubbleUpDecrementSize();
        }
      }
      --root.size;
      return ret;
    }
    @Override public boolean removeIf(Predicate<? super E> filter){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        return uncheckedRemoveIf(head,filter);
      }else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    private void collapsehead(RefDblLnkNode<E> oldhead,RefDblLnkNode<E> tail,Predicate<? super E> filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList<E> root=this.root;
      RefDblLnkNode<E> newhead;
      for(newhead=oldhead.next;;
      --numLeft,
      ++numRemoved,newhead=newhead.next){
        if(newhead==tail)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newhead.val)){
          numRemoved+=collapseBodyHelper(newhead,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      this.head=newhead;
      RefDblLnkNode<E> tmp;
      if((tmp=oldhead.prev)==null){
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){}
        root.head=newhead;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.head=newhead,parent.size-=numRemoved,parent=parent.parent){
          if(parent.head!=oldhead){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.next=newhead;
      }
      newhead.prev=tmp;
    }
    private void collapsetail(RefDblLnkNode<E> oldtail,RefDblLnkNode<E> head,Predicate<? super E> filter
      ,int size,int modCount
    ){
      int numRemoved;
      int numLeft=size-(numRemoved=1)-1;
      final CheckedList<E> root=this.root;
      RefDblLnkNode<E> newtail;
      for(newtail=oldtail.prev;;
      --numLeft,
      ++numRemoved,newtail=newtail.next){
        if(newtail==head)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          break;
        }
        if(!filter.test(newtail.val)){
          numRemoved+=collapseBodyHelper(head,--numLeft,filter,root.new ModCountChecker(modCount));
          break;
        }
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=numRemoved;
      this.size-=numRemoved;
      this.tail=newtail;
      RefDblLnkNode<E> tmp;
      if((tmp=oldtail.next)==null){
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){}
        root.tail=newtail;
      }else{
        for(var parent=this.parent;parent!=null;
          parent.tail=newtail,parent.size-=numRemoved,parent=parent.parent){
          if(parent.tail!=oldtail){
            parent.bubbleUpDecrementSize(numRemoved);
            break;
          }
        }
        tmp.prev=newtail;
      }
      newtail.next=tmp;
    }
    private void bubbleUpCollapseHeadAndTail(RefDblLnkNode<E> oldHead,RefDblLnkNode<E> newHead,int numRemoved,RefDblLnkNode<E> newTail,RefDblLnkNode<E> oldTail){
      this.head=newHead;
      this.tail=newTail;
      final RefDblLnkNode<E> after,before=oldHead.prev;
      if((after=oldHead.next)==null){
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
    private boolean uncheckedRemoveIf(RefDblLnkNode<E> head,Predicate<? super E> filter){
      RefDblLnkNode<E> tail;
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
    private void clearAllHelper(int size,RefDblLnkNode<E> head,RefDblLnkNode<E> tail,CheckedList<E> root){
      RefDblLnkNode<E> before,after=tail.next;
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
      var curr=this;
      do{
        ++curr.modCount;
        curr.size-=numRemoved;
      }while((curr=curr.parent)!=null);
    }
    private void bubbleUpClearBody(RefDblLnkNode<E> before,RefDblLnkNode<E> head,int numRemoved,RefDblLnkNode<E> tail,RefDblLnkNode<E> after){
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
          curr.bubbleUpDecrementSize(numRemoved);
          return;
        }
      }
    }
    private void bubbleUpClearHead(RefDblLnkNode<E> tail, RefDblLnkNode<E> after,int numRemoved){
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
    private void bubbleUpClearTail(RefDblLnkNode<E> head, RefDblLnkNode<E> before,int numRemoved){
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
    private static <E> int collapseBodyHelper(RefDblLnkNode<E> newHead,int numLeft,Predicate<? super E> filter,CheckedList<E>.ModCountChecker modCountChecker)
    {
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(newHead.next,numLeft,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          modCountChecker.checkModCount();
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(newHead,survivorSet,numSurvivors,numLeft);
          }
        }else{
          final long survivorWord=markSurvivors(newHead.next,numLeft,filter);
          modCountChecker.checkModCount();
          if((numLeft-=(numSurvivors=Long.bitCount(survivorWord)))!=0){
            pullSurvivorsDown(newHead,survivorWord,numSurvivors,numLeft);
          }
        }
      }else{
        modCountChecker.checkModCount();
      }
      return numLeft;
    }
    private void collapseHeadAndTail(RefDblLnkNode<E> head,RefDblLnkNode<E> tail,Predicate<? super E> filter
      ,int size,int modCount
    ){
      int numRemoved;
      if((numRemoved=2)!=size){
        var newHead=head.next;
        do{
          if(!filter.test(newHead.val)){
            var newTail=tail.prev;
            final CheckedList<E> root=this.root;
            for(--size;numRemoved!=size;++numRemoved,newTail=newTail.prev){
              if(numRemoved==size){
                 CheckedCollection.checkModCount(modCount,root.modCount);
                 break;
              }
              if(!filter.test(newTail.val)){
                numRemoved+=collapseBodyHelper(newHead,size-1-numRemoved,filter,root.new ModCountChecker(modCount));
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
        }
        while(++numRemoved!=size);
      }
      CheckedList<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      root.size-=size;
      clearAllHelper(size,head,tail,root);
    }
    private boolean collapseBody(RefDblLnkNode<E> head,RefDblLnkNode<E> tail,Predicate<? super E> filter
      ,int size,int modCount
    ){
      for(int numLeft=size-2;numLeft!=0;--numLeft){
        RefDblLnkNode<E> prev;
        if(filter.test((head=(prev=head).next).val)){
          int numRemoved=1;
          var root=this.root;
          for(;;++numRemoved){
            if(--numLeft==0){
              CheckedCollection.checkModCount(modCount,root.modCount);
              break;
            }else if(!filter.test((head=head.next).val)){
              numRemoved+=collapseBodyHelper(head,--numLeft,filter,root.new ModCountChecker(modCount));
              break;
            }
          }
          root.modCount=++modCount;
          this.modCount=modCount;
          head.prev=prev;
          prev.next=head;
          root.size-=numRemoved;
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
    private void bubbleUpAppend(RefDblLnkNode<E> oldTail,RefDblLnkNode<E> newTail){
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
    private void bubbleUpAppend(RefDblLnkNode<E> newTail){
      this.tail=newTail;
      for(var currList=parent;currList!=null;++currList.size,currList.tail=newTail,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpPrepend(RefDblLnkNode<E> oldHead,RefDblLnkNode<E> newHead){
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
    private void bubbleUpPrepend(RefDblLnkNode<E> newHead){
      this.head=newHead;
      for(var currList=parent;currList!=null;++currList.size,currList.head=newHead,currList=currList.parent){
        ++currList.modCount;
      }
    }
    private void bubbleUpRootInit(RefDblLnkNode<E> newNode){
      this.head=newNode;
      this.tail=newNode;
      for(var parent=this.parent;parent!=null;parent=parent.parent){
        ++parent.modCount;
        parent.size=1;
        parent.head=newNode;
        parent.tail=newNode;
      }
    }
    private void bubbleUpInitHelper(int index,int size,RefDblLnkNode<E> newNode){
      RefDblLnkNode<E> after,before;   
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
          before=(after=RefDblLnkNode.iterateDescending(before,size-2)).prev;
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
          after=(before=RefDblLnkNode.uncheckedIterateAscending(after,index)).next;
          before.prev=newNode;
        }
        after.prev=newNode;
      }
      newNode.next=after;
      newNode.prev=before;
    }
    private void bubbleUpInit(RefDblLnkNode<E> newNode){
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
      final var newNode=new RefDblLnkNode<E>(val);
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
          RefDblLnkNode<E> currTail,after;
          if((after=(currTail=this.tail).next)==null){
            currTail.next=currTail=new RefDblLnkNode<E>(currTail,val);
            bubbleUpAppend(currTail);
            root.tail=currTail;
          }else{
            bubbleUpAppend(currTail,currTail=new RefDblLnkNode<E>(currTail,val,after));
            after.prev=currTail;
          }
        }else{
          bubbleUpInit(new RefDblLnkNode<E>(val));
        }
      }else{
        RefDblLnkNode<E> newNode;
        bubbleUpRootInit(newNode=new RefDblLnkNode<E>(val));
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
      final RefDblLnkNode<E> node;
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
      int modCount=this.modCount;
      final CheckedList<E> root;
      try{
        final RefDblLnkNode<E> head;
        if((head=this.head)==null){
          return;
        }
        RefDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
      }finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      root.modCount=++modCount;
      var curr=this;
      do{
        curr.modCount=modCount;
      }while((curr=curr.parent)!=null);
    }
    @Override public void forEach(Consumer<? super E> action){
      final int modCount=this.modCount;
      try{
        final RefDblLnkNode<E> head;
        if((head=this.head)!=null){
          RefDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void sort(Comparator<? super E> sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
          if(sorter==null){
            RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
          }else{
            {
              RefSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
          }
        }
        finally{
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
        }
        finally{
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedStableDescendingSort(tmp,0,size);
        }
        finally{
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
        }
        finally{
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedUnstableDescendingSort(tmp,0,size);
        }
        finally{
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void unstableSort(Comparator<? super E> sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
          if(sorter==null){
            RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
          }else{
            {
              RefSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
          }
        }
        finally{
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
          this.modCount=modCount;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
      else{
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
      int modCount=this.modCount;
      try{
        return super.toString();
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public int hashCode(){
      int modCount=this.modCount;
      try{
        return super.hashCode();
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    } 
  }
  public static class CheckedList<E> extends UncheckedList<E>{
    transient int modCount;
    public CheckedList(){
    }
    CheckedList(RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
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
      RefDblLnkNode<E> tail;
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
      RefDblLnkNode<E> head;
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
          RefDblLnkNode<E> before;
          ret=(before=(tail=RefDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          RefDblLnkNode<E> after;
          ret=(after=(head=RefDblLnkNode.iterateAscending(head,index)).next).val;
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
          tail.next=tail=new RefDblLnkNode<E>(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          RefDblLnkNode<E> before;
          (before=(tail=RefDblLnkNode.iterateDescending(tail,size-2)).prev).next=before=new RefDblLnkNode<E>(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        RefDblLnkNode<E> head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new RefDblLnkNode<E>(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new RefDblLnkNode<E>(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          RefDblLnkNode<E> after;
          (after=(head=RefDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new RefDblLnkNode<E>(head,val,after);
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
      RefDblLnkNode<E> tmp;
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
      final RefDblLnkNode<E> tail;
      if((tail=this.tail)!=null){
         return tail.val;
      }
      throw new NoSuchElementException();
    }
    @Override public E element(){
      final RefDblLnkNode<E> head;
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
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          RefDblLnkNode.uncheckedForEachAscending(head,this.size,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void replaceAll(UnaryOperator<E> operator){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          RefDblLnkNode.uncheckedReplaceAll(head,this.size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public void sort(Comparator<? super E> sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
          if(sorter==null){
            RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
          }else{
            {
              RefSortUtil.uncheckedStableSort(tmp,0,size,sorter);
            }
          }
        }
        finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedStableAscendingSort(tmp,0,size);
        }
        finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedStableDescendingSort(tmp,0,size);
        }
        finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public String toString(){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        final StringBuilder builder=new StringBuilder("[");
        final int modCount=this.modCount;
        try{
          RefDblLnkNode.uncheckedToString(head,tail,builder);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        return builder.append(']').toString();
      }
      return "[]";
    }
    @Override public int hashCode(){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          return RefDblLnkNode.uncheckedHashCode(head,tail);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
      return 1;
    }
    @Override public void unstableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
        }
        finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
            RefSortUtil.uncheckedUnstableDescendingSort(tmp,0,size);
        }
        finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    @Override public void unstableSort(Comparator<? super E> sorter){
      final int size;
      if((size=this.size)>1){
        //todo: see about making an in-place sort implementation rather than copying to an array
        final Object[] tmp;
        final RefDblLnkNode<E> tail;
        RefDblLnkNode.uncheckedCopyInto(tmp=new Object[size],tail=this.tail,size);
        int modCount=this.modCount;
        try
        {
          if(sorter==null){
            RefSortUtil.uncheckedUnstableAscendingSort(tmp,0,size);
          }else{
            {
              RefSortUtil.uncheckedUnstableSort(tmp,0,size,sorter);
            }
          }
        }
        finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
        RefDblLnkNode.uncheckedCopyFrom(tmp,size,tail);
      }
    }
    private void pullSurvivorsDown(RefDblLnkNode<E> prev,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(RefDblLnkNode<E> prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(RefDblLnkNode<E> prev,Predicate<? super E> filter,int numLeft,int modCount){
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
    @Override boolean uncheckedRemoveIf(RefDblLnkNode<E> head,Predicate<? super E> filter){
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
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        RefDblLnkNode<E> head,newTail;
        final var newHead=newTail=new RefDblLnkNode<E>((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new RefDblLnkNode<E>(newTail,(head=head.next).val),++i){}
        return new CheckedList<E>(newHead,size,newTail);
      }
      return new CheckedList<E>();
    }
    @Override public boolean equals(Object val){
      //TODO
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
        final RefDblLnkNode<E> subListHead,subListTail;
        if((tailDist-=toIndex)<=fromIndex){
          subListTail=RefDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?RefDblLnkNode.uncheckedIterateDescending(subListTail,subListSize):RefDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=RefDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?RefDblLnkNode.uncheckedIterateAscending(subListHead,subListSize):RefDblLnkNode.iterateDescending(this.tail,tailDist);
        }
        return new CheckedSubList<E>(this,fromIndex,subListHead,subListSize,subListTail);
      }
      return new CheckedSubList<E>(this,fromIndex);
    } 
    @Override public int search(Object val){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefDblLnkNode.uncheckedsearchNonNull(head,this.size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkNode.uncheckedsearchNull(head);
      }
      return -1;
    }
    @Override public boolean contains(Object val){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefDblLnkNode.uncheckedcontainsNonNull(head,this.size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkNode.uncheckedcontainsNull(head,tail);
      }
      return false;
    }
    @Override public int indexOf(Object val){
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefDblLnkNode.uncheckedindexOfNonNull(head,this.size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkNode.uncheckedindexOfNull(head,tail);
      }
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      final RefDblLnkNode<E> tail;
      if((tail=this.tail)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefDblLnkNode.uncheckedlastIndexOfNonNull(this.size,tail,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefDblLnkNode.uncheckedlastIndexOfNull(this.size,tail);
      }
      return -1;
    }
    boolean uncheckedremoveLastOccurrenceNonNull(RefDblLnkNode<E> tail
    ,Object nonNull
    ){
      int modCount=this.modCount;
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
          --this.size;
          return true;
        }
        int size,numLeft=(size=this.size-1);
        for(;numLeft!=0;--numLeft){
          RefDblLnkNode<E> next;
          tail=(next=tail).prev;
          if(nonNull.equals((tail=(next=tail).prev).val)){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            if(numLeft==1)
            {
              this.head=next;
              next.prev=null;
            }else{
              tail.next=next;
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
    boolean uncheckedremoveLastOccurrenceNull(RefDblLnkNode<E> tail
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
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveLastOccurrence(RefDblLnkNode<E> tail
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
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveValNonNull(RefDblLnkNode<E> head
    ,Object nonNull
    ){
      int modCount=this.modCount;
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
          return true;
        }
        int size,numLeft=(size=this.size-1);
        for(;numLeft!=0;--numLeft){
          RefDblLnkNode<E> prev;
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
    boolean uncheckedremoveValNull(RefDblLnkNode<E> head
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
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
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
    boolean uncheckedremoveVal(RefDblLnkNode<E> head
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
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
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
      RefDblLnkNode<E> head;
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
      RefDblLnkNode<E> tail;
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
      transient RefDblLnkNode<E> curr;
      transient RefDblLnkNode<E> lastRet;
      transient int currIndex;
      private DescendingItr(CheckedList<E> parent){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.currIndex=parent.size;
        this.curr=parent.tail;
      }
      private DescendingItr(CheckedList<E> parent,RefDblLnkNode<E> curr,int currIndex){
        this.parent=parent;
        this.modCount=parent.modCount;
        this.curr=curr;
        this.currIndex=currIndex;
      }
      @Override public boolean hasNext(){
        return this.curr!=null;
      }
      @Override public E next(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final RefDblLnkNode<E> curr;
        if((curr=this.curr)!=null){
          this.lastRet=curr;
          this.curr=curr.prev;
          --currIndex;
          return curr.val;
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        RefDblLnkNode<E> lastRet;
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
              RefDblLnkNode.eraseNode(lastRet);
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
          RefDblLnkNode.uncheckedForEachDescending(this.curr,currIndex,action);
        }finally{
          CheckedCollection.checkModCount(modCount,(parent=this.parent).modCount);
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
      private BidirectionalItr(CheckedList<E> parent){
        super(parent,parent.head,0);
      }
      private BidirectionalItr(CheckedList<E> parent,RefDblLnkNode<E> curr,int currIndex){
        super(parent,curr,currIndex);
      }
      @Override public E next(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final RefDblLnkNode<E> curr;
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
          RefDblLnkNode<E> curr;
          this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
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
        final RefDblLnkNode<E> lastRet;
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
        RefDblLnkNode<E> newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++parent.size){
          if(currIndex==1){
            parent.head=newNode=new RefDblLnkNode<E>(val);
          }else{
            (newNode=parent.tail).next=newNode=new RefDblLnkNode<E>(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new RefDblLnkNode<E>(val,newNode);
          }else{
            final RefDblLnkNode<E> tmp;
            (newNode=curr).prev=newNode=new RefDblLnkNode<E>(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void remove(){
        RefDblLnkNode<E> lastRet;
        if((lastRet=this.lastRet)!=null){
          final CheckedList<E> parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(lastRet.next==curr){
            --currIndex;
          }
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
              RefDblLnkNode.eraseNode(lastRet);
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
        if((numLeft=(size=(parent=this.parent).size)-this.currIndex)!=0){
          final int modCount=this.modCount;
          try{
            RefDblLnkNode.uncheckedForEachAscending(this.curr,numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
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
    public UncheckedList(){
    }
    UncheckedList(RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
      super(head,size,tail);
    }
    @Override public void clear(){
      this.head=null;
      this.size=0;
      this.tail=null;
    }
    @Override public E removeLast(){
      RefDblLnkNode<E> tail;
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
      RefDblLnkNode<E> head;
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
          RefDblLnkNode<E> before;
          ret=(before=(tail=RefDblLnkNode.iterateDescending(tail,size-1)).prev).val;
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
          RefDblLnkNode<E> after;
          ret=(after=(head=RefDblLnkNode.iterateAscending(head,index)).next).val;
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
          tail.next=tail=new RefDblLnkNode<E>(tail,val);
          this.tail=tail;
        }else{
          //iterate from the tail and insert
          RefDblLnkNode<E> before;
          (before=(tail=RefDblLnkNode.iterateDescending(tail,size-2)).prev).next=before=new RefDblLnkNode<E>(before,val,tail);
          tail.prev=before;
        }
      }else{
        //the insertion point is closer to the head
        RefDblLnkNode<E> head;
        if((head=this.head)==null){
          //initialize the list
          this.head=head=new RefDblLnkNode<E>(val);
          this.tail=head;
        }else if(index==0){
          //the insertion point IS the head
          head.prev=head=new RefDblLnkNode<E>(val,head);
          this.head=head;
        }else{
          //iterate from the head and insert
          RefDblLnkNode<E> after;
          (after=(head=RefDblLnkNode.iterateAscending(head,index-1)).next).prev=after=new RefDblLnkNode<E>(head,val,after);
          head.next=after;
        }
      }
    }
    @Override public void addLast(E val){
      RefDblLnkNode<E> tail;
      if((tail=this.tail)==null){
        this.head=tail=new RefDblLnkNode<E>(val);
      }else{
        tail.next=tail=new RefDblLnkNode<E>(tail,val);
      }
      this.tail=tail;
      ++this.size;
    }
    @Override public void push(E val){
      RefDblLnkNode<E> head;
      if((head=this.head)==null){
        this.head=tail=new RefDblLnkNode<E>(val);
      }else{
        head.prev=head=new RefDblLnkNode<E>(val,head);
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
        RefDblLnkNode<E> curr;
        for(this.head=curr=new RefDblLnkNode<E>((E)in.readObject());--size!=0;curr=curr.next=new RefDblLnkNode<E>(curr,(E)in.readObject())){}
        this.tail=curr;
      }
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0){
        RefDblLnkNode<E> head,newTail;
        final var newHead=newTail=new RefDblLnkNode<E>((head=this.head).val);
        for(int i=1;i!=size;newTail=newTail.next=new RefDblLnkNode<E>(newTail,(head=head.next).val),++i){}
        return new UncheckedList<E>(newHead,size,newTail);
      }
      return new UncheckedList<E>();
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
          final RefDblLnkNode<E> head;
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
    boolean uncheckedremoveValNonNull(RefDblLnkNode<E> head
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
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
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
    boolean uncheckedremoveValNull(RefDblLnkNode<E> head
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
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
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
    boolean uncheckedremoveVal(RefDblLnkNode<E> head
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
        for(RefDblLnkNode<E> prev;(head=(prev=head).next)!=null;){
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
        final RefDblLnkNode<E> subListHead,subListTail;
        if((tailDist=this.size-toIndex)<=fromIndex){
          subListTail=RefDblLnkNode.iterateDescending(this.tail,tailDist);
          subListHead=subListSize<=fromIndex?RefDblLnkNode.uncheckedIterateDescending(subListTail,subListSize):RefDblLnkNode.iterateAscending(this.head,fromIndex);
        }else{
          subListHead=RefDblLnkNode.iterateAscending(this.head,fromIndex);
          subListTail=subListSize<=tailDist?RefDblLnkNode.uncheckedIterateAscending(subListHead,subListSize):RefDblLnkNode.iterateDescending(this.tail,tailDist);
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
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearchNonNull(head,val);
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Boolean val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Byte val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Character val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Short val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Integer val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Long val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Float val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Double val){
      {
        {
          final RefDblLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null){
              return RefDblLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public boolean removeLastOccurrence(boolean val){
      {
        {
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
          final RefDblLnkNode<E> tail;
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
    boolean uncheckedremoveLastOccurrenceNonNull(RefDblLnkNode<E> tail
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
          --this.size;
          return true;
        }
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveLastOccurrenceNull(RefDblLnkNode<E> tail
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
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
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
    boolean uncheckedremoveLastOccurrence(RefDblLnkNode<E> tail
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
        for(RefDblLnkNode<E> next;(tail=(next=tail).prev)!=null;){
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
      RefDblLnkNode<E> head;
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
      RefDblLnkNode<E> tail;
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
      final RefDblLnkNode<E> head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public E peekLast(){
      final RefDblLnkNode<E> tail;
      if((tail=this.tail)!=null){
        return (tail.val);
      }
      return null;
    }
    @Override public boolean removeIf(Predicate<? super E> filter){
      final RefDblLnkNode<E> head;
      return (head=this.head)!=null && uncheckedRemoveIf(head,filter);
    }
    private int removeIfHelper(RefDblLnkNode<E> prev,RefDblLnkNode<E> tail,Predicate<? super E> filter){
      int numSurvivors=1;
      outer:for(RefDblLnkNode<E> next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(RefDblLnkNode<E> prev,RefDblLnkNode<E> curr,RefDblLnkNode<E> tail,Predicate<? super E> filter){
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
    boolean uncheckedRemoveIf(RefDblLnkNode<E> head,Predicate<? super E> filter){
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
          final RefDblLnkNode<E> prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    private static class DescendingItr<E> extends AscendingItr<E>{
      private DescendingItr(UncheckedList<E> parent){
        super(parent,parent.tail);
      }
      @Override public void remove(){
        final UncheckedList<E> parent;
        if(--(parent=this.parent).size==0){
          parent.head=null;
          parent.tail=null;
        }else{
          RefDblLnkNode<E> curr;
          if((curr=this.curr)==null){
            (curr=parent.head.next).prev=null;
            parent.head=curr;
          }else{
            RefDblLnkNode<E> lastRet;
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
        final RefDblLnkNode<E> curr;
        this.curr=(curr=this.curr).prev;
        return curr.val;
      }
      @Override void uncheckedForEachRemaining(RefDblLnkNode<E> curr,Consumer<? super E> action){
        RefDblLnkNode.uncheckedForEachDescending(curr,action);
      }
    }
    private static class AscendingItr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final UncheckedList<E> parent;
      transient RefDblLnkNode<E> curr;
      private AscendingItr(UncheckedList<E> parent,RefDblLnkNode<E> curr){
        this.parent=parent;
        this.curr=curr;
      }
      private AscendingItr(UncheckedList<E> parent){
        this.parent=parent;
        this.curr=parent.head;
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
          RefDblLnkNode<E> curr;
          if((curr=this.curr)==null){
            (curr=parent.tail.prev).next=null;
            parent.tail=curr;
          }else{
            RefDblLnkNode<E> lastRet;
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
        final RefDblLnkNode<E> curr;
        this.curr=(curr=this.curr).next;
        return curr.val;
      }
      void uncheckedForEachRemaining(RefDblLnkNode<E> curr,Consumer<? super E> action){
        RefDblLnkNode.uncheckedForEachAscending(curr,action);
        this.curr=null;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final RefDblLnkNode<E> curr;
        if((curr=this.curr)!=null){
          uncheckedForEachRemaining(curr,action);
          this.curr=null;
        }
      }
    }
    private static class BidirectionalItr<E> extends AscendingItr<E> implements OmniListIterator.OfRef<E>{
      private transient int currIndex;
      private transient RefDblLnkNode<E> lastRet;
      private BidirectionalItr(UncheckedList<E> parent){
        super(parent);
      }
      private BidirectionalItr(UncheckedList<E> parent,RefDblLnkNode<E> curr,int currIndex){
        super(parent,curr);
        this.currIndex=currIndex;
      }
      @Override public E next(){
        final RefDblLnkNode<E> curr;
        this.lastRet=curr=this.curr;
        this.curr=curr.next;
        ++this.currIndex;
        return curr.val;
      }
      @Override public E previous(){
        RefDblLnkNode<E> curr;
        this.lastRet=curr=(curr=this.curr)==null?parent.tail:curr.prev;
        this.curr=curr;
        --this.currIndex;
        return curr.val;
      }
      @Override public boolean hasPrevious(){
        return curr.prev!=null;
      }
      @Override public int nextIndex(){
        return currIndex;
      }
      @Override public int previousIndex(){
        return currIndex-1;
      }
      @Override public void add(E val){
        final UncheckedList<E> parent;
        RefDblLnkNode<E> newNode;
        final int currIndex;
        if((currIndex=++this.currIndex)==++(parent=this.parent).size){
          if(currIndex==1){
            parent.head=newNode=new RefDblLnkNode<E>(val);
          }else{
            (newNode=parent.tail).next=newNode=new RefDblLnkNode<E>(newNode,val);
          }
          parent.tail=newNode;
        }else{
          if(currIndex==1){
            (newNode=parent.head).prev=newNode=new RefDblLnkNode<E>(val,newNode);
          }else{
            final RefDblLnkNode<E> tmp;
            (newNode=curr).prev=newNode=new RefDblLnkNode<E>(tmp=newNode.prev,val,newNode);
            tmp.next=newNode;
          }
        }
        this.lastRet=null;
      }
      @Override public void set(E val){
        lastRet.val=val;
      }
      @Override public void remove(){
        RefDblLnkNode<E> lastRet;
        if((lastRet=this.lastRet).next==curr){
          --currIndex;
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
            parent.head=lastRet=lastRet.next;
            lastRet.prev=null;
          }else{
            RefDblLnkNode.eraseNode(lastRet);
          }
        }
        this.lastRet=null;
      }
      @Override void uncheckedForEachRemaining(RefDblLnkNode<E> curr,Consumer<? super E> action){
        RefDblLnkNode.uncheckedForEachAscending(curr,action);
        final UncheckedList<E> parent;
        this.lastRet=(parent=this.parent).tail;
        this.currIndex=parent.size;
      }
    }
  }
}
