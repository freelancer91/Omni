package omni.impl.seq;
import omni.api.OmniList;
import java.io.Serializable;
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
public abstract class RefDblLnkSeq<E> implements
   OmniList.OfRef<E>
  ,Cloneable,Serializable{
  private static final long serialVersionUID=1L;
  transient int size;
  transient RefDblLnkNode<E> head;
  transient RefDblLnkNode<E> tail;
  private  RefDblLnkSeq(){
  }
  private RefDblLnkSeq(RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
    this.head=head;
    this.size=size;
    this.tail=tail;
  }
  @Override public int size(){
    return this.size;
  }
  @Override public boolean isEmpty(){
    return this.size==0;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
    this.tail=null;
  }
  public void addLast(E val){
    RefDblLnkNode<E> tail;
    if((tail=this.tail)==null){
      this.head=tail=new RefDblLnkNode<E>(val);
    }else{
      tail.next=tail=new RefDblLnkNode<E>(tail,val);
    }
    this.tail=tail;
    ++this.size;
  }
  @Override public boolean add(E val){
    addLast(val);
    return true;
  }
  @Override public void add(int index,E val){
    int tailDist;
    if((tailDist=++this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==1){
        tail.next=tail=new RefDblLnkNode<E>(tail,val);
        this.tail=tail;
      }else{
        while(--tailDist!=1){
          tail=tail.prev;
        }
        RefDblLnkNode<E> before;
        (before=tail.prev).next=before=new RefDblLnkNode<E>(before,val,tail);
        tail.prev=before;
      }
    }else{
      RefDblLnkNode<E> head;
      if((head=this.head)==null){
        this.head=head=new RefDblLnkNode<E>(val);
        this.tail=head;
      }else if(index==0){
        head.prev=head=new RefDblLnkNode<E>(val,head);
        this.head=head;
      }else{
        while(--index!=0){
          head=head.next;
        }
        RefDblLnkNode<E> after;
        (after=head.next).prev=after=new RefDblLnkNode<E>(head,val,after);
        head.next=after;
      }
    }
  }
  private RefDblLnkNode<E> getNode(int index,int size)
  {
    int tailDist;
    if((tailDist=size-index)<index){
      for(var tail=this.tail;;tail=tail.prev){
        if(--tailDist==0){
          return tail;
        }
      }
    }else{
      for(var head=this.head;;--index,head=head.next){
        if(index==0){
          return head;
        }
      }
    }
  }
  @Override public E set(int index,E val){
    RefDblLnkNode<E> tmp;
    var ret=(tmp=getNode(index,size)).val;
    tmp.val=val;
    return ret;
  }
  @Override public void put(int index,E val){
    getNode(index,size).val=val;
  }
  @Override public E get(int index){
    return getNode(index,size).val;
  }
  @Override public E remove(int index){
    final E ret;
    int tailDist;
    if((tailDist=--this.size-index)<=index){
      var tail=this.tail;
      if(tailDist==0){
        ret=tail.val;
        if(index==0){
          this.head=null;
          this.tail=null;
        }else{
          this.tail=tail=tail.prev;
          tail.next=null;
        }
      }else{
        ret=(tail=RefDblLnkNode.uncheckedIterateDescending(tail,tailDist)).val;
        RefDblLnkNode.eraseNode(tail);
      }
    }else{
      var head=this.head;
      if(index==0){
        ret=head.val;
        this.head=head=head.next;
        head.prev=null;
      }else{
        ret=(head=RefDblLnkNode.uncheckedIterateAscending(head,index)).val;
        RefDblLnkNode.eraseNode(head);
      }
    }
    return ret;
  }
  @Override public void forEach(Consumer<? super E> action)
  {
    final RefDblLnkNode<E> head;
    if((head=this.head)!=null){
      RefDblLnkNode.uncheckedForEachAscending(head,size,action);
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
  @Override public boolean contains(boolean val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
            return RefDblLnkNode.uncheckedcontains(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return RefDblLnkNode.uncheckedcontainsNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(long val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(float val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(double val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean remove(Object val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
            return uncheckedremoveValNonNull(head,tail,val);
          }
          return uncheckedremoveValNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(byte val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(char val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(short val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Boolean val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
            return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }
          return uncheckedremoveValNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Byte val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
            return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }
          return uncheckedremoveValNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Character val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
            return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
          }
          return uncheckedremoveValNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Short val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
            return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
          }
          return uncheckedremoveValNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Integer val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
            return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
          }
          return uncheckedremoveValNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Long val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
            return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
          }
          return uncheckedremoveValNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Float val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
            return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
          }
          return uncheckedremoveValNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Double val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
            return uncheckedremoveVal(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return uncheckedremoveValNull(head,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public int indexOf(boolean val){
    {
      {
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
        final RefDblLnkNode<E> tail;
        if((tail=this.tail)!=null)
        {
          if(val!=null)
          {
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
          if(val!=null)
          {
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
          if(val!=null)
          {
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
          if(val!=null)
          {
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
          if(val!=null)
          {
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
          if(val!=null)
          {
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
          if(val!=null)
          {
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
          if(val!=null)
          {
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
          if(val!=null)
          {
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
          if(val!=null)
          {
            return RefDblLnkNode.uncheckedlastIndexOf(size,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return RefDblLnkNode.uncheckedlastIndexOfNull(size,tail);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return -1;
  }
  boolean uncheckedremoveValNonNull(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ,Object nonNull
  ){
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
    while(head!=tail){
      if(nonNull.equals((head=head.next).val)){
        if(head==tail){
          this.tail=head=head.prev;
          head.next=null;
        }else{
          RefDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  boolean uncheckedremoveValNull(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ){
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
    while(head!=tail){
      if(null==((head=head.next).val)){
        if(head==tail){
          this.tail=head=head.prev;
          head.next=null;
        }else{
          RefDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  boolean uncheckedremoveVal(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
  ,Predicate<? super E> pred
  ){
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
    while(head!=tail){
      if(pred.test((head=head.next).val)){
        if(head==tail){
          this.tail=head=head.prev;
          head.next=null;
        }else{
          RefDblLnkNode.eraseNode(head);
        }
        --size;
        return true;
      }
    }
    return false;
  }
  @Override public abstract Object clone();
  @Override public void replaceAll(UnaryOperator<E> operator){
    final RefDblLnkNode<E> head;
    if((head=this.head)!=null){
      RefDblLnkNode.uncheckedReplaceAll(head,size,operator);
    }
  }
  @Override public boolean removeIf(Predicate<? super E> filter)
  {
    final RefDblLnkNode<E> head;
    return (head=this.head)!=null && uncheckedRemoveIf(head,size,filter);
  }
  boolean uncheckedRemoveIf(RefDblLnkNode<E> head,int size,Predicate<? super E> filter)
  {
    //TODO
    return false;
  }
  @Override public void sort(Comparator<? super E> sorter){
    //TODO
  }
  @Override public void stableAscendingSort(){
    //TODO
  }
  @Override public void stableDescendingSort(){
    //TODO
  }
  @Override public void unstableAscendingSort(){
    //TODO
  }
  @Override public void unstableDescendingSort(){
    //TODO
  }
  @Override public void unstableSort(Comparator<? super E> sorter){
    //TODO
  }
  @Override public String toString(){
    //TODO
    return null;
  }
  @Override public int hashCode(){
    //TODO
    return 0;
  }
  //TODO serialization methods
  public static class UncheckedList<E> extends RefDblLnkSeq<E> implements OmniDeque.OfRef<E>{
    private static final long serialVersionUID=1L;
    public UncheckedList(){
    }
    UncheckedList(RefDblLnkNode<E> head,int size,RefDblLnkNode<E> tail){
      super(head,size,tail);
    }
    @Override public Object clone(){
      final int size;
      if((size=this.size)!=0)
      {
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
    @Override public OmniIterator.OfRef<E> descendingIterator(){
      //TODO
      return null;
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      //TODO
      return null;
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      //TODO
      return null;
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      //TODO
      return null;
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
    @Override public E removeLast(){
      RefDblLnkNode<E> tail;
      final var ret=(tail=this.tail).val;
      if(--size==0){
        this.head=null;
        this.tail=null;
      }else{
        (tail=tail.prev).next=null;
        this.tail=tail;
      }
      return ret;
    }
    @Override public E pop(){
      RefDblLnkNode<E> head;
      final var ret=(head=this.head).val;
      if(--size==0){
        this.head=null;
        this.tail=null;
      }else{
        (head=head.next).prev=null;
        this.head=head;
      }
      return ret;
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
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null)
            {
              return RefDblLnkNode.uncheckedsearchNonNull(head,tail,val);
            }
            return RefDblLnkNode.uncheckedsearchNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Boolean val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null)
            {
              return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Byte val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null)
            {
              return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Character val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null)
            {
              return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Short val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null)
            {
              return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Integer val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null)
            {
              return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Long val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null)
            {
              return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Float val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null)
            {
              return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Double val){
      {
        {
          final RefDblLnkNode<E> tail;
          if((tail=this.tail)!=null)
          {
            if(val!=null)
            {
              return RefDblLnkNode.uncheckedsearch(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefDblLnkNode.uncheckedsearchNull(head,tail);
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
            return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred(val));
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
            return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred(val));
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
            return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred(val));
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
            return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred(val));
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
            return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred(val));
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
            if(val!=null)
            {
              return uncheckedremoveLastOccurrenceNonNull(head,tail,val);
            }
            return uncheckedremoveLastOccurrenceNull(head,tail);
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
            return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred(val));
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
            return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred(val));
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
            return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred(val));
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
            if(val!=null)
            {
              return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(head,tail);
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
            if(val!=null)
            {
              return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(head,tail);
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
            if(val!=null)
            {
              return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(head,tail);
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
            if(val!=null)
            {
              return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(head,tail);
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
            if(val!=null)
            {
              return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(head,tail);
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
            if(val!=null)
            {
              return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(head,tail);
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
            if(val!=null)
            {
              return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(head,tail);
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
            if(val!=null)
            {
              return uncheckedremoveLastOccurrence(head,tail,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return uncheckedremoveLastOccurrenceNull(head,tail);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    boolean uncheckedremoveLastOccurrenceNonNull(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
    ,Object nonNull
    ){
      if(nonNull.equals(tail.val)){
        if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          this.tail=tail=tail.prev;
          tail.next=null;
        }
        return true;
      }
      while(head!=tail){
        if(nonNull.equals((tail=tail.prev).val)){
          if(head==tail){
            this.head=tail=tail.next;
            tail.prev=null;
          }else{
            RefDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
        }
      }
      return false;
    }
    boolean uncheckedremoveLastOccurrenceNull(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
    ){
      if(null==(tail.val)){
        if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          this.tail=tail=tail.prev;
          tail.next=null;
        }
        return true;
      }
      while(head!=tail){
        if(null==((tail=tail.prev).val)){
          if(head==tail){
            this.head=tail=tail.next;
            tail.prev=null;
          }else{
            RefDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
        }
      }
      return false;
    }
    boolean uncheckedremoveLastOccurrence(RefDblLnkNode<E> head,RefDblLnkNode<E> tail
    ,Predicate<? super E> pred
    ){
      if(pred.test(tail.val)){
        if(--size==0){
          this.head=null;
          this.tail=null;
        }else{
          this.tail=tail=tail.prev;
          tail.next=null;
        }
        return true;
      }
      while(head!=tail){
        if(pred.test((tail=tail.prev).val)){
          if(head==tail){
            this.head=tail=tail.next;
            tail.prev=null;
          }else{
            RefDblLnkNode.eraseNode(tail);
          }
          --size;
          return true;
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
  }
}
