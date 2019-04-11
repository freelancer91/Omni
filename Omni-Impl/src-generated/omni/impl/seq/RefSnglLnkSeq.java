package omni.impl.seq;
import java.util.function.IntFunction;
import omni.api.OmniIterator;
import omni.api.OmniCollection;
import omni.impl.CheckedCollection;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import omni.util.OmniArray;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.Objects;
import omni.util.OmniPred;
import omni.api.OmniStack;
import omni.util.RefSnglLnkNode;
public abstract class RefSnglLnkSeq<E> implements OmniCollection.OfRef<E>,Cloneable{
  transient int size;
  transient RefSnglLnkNode<E> head;
  private RefSnglLnkSeq(){
  }
  private RefSnglLnkSeq(int size,RefSnglLnkNode<E> head){
    this.size=size;
    this.head=head;
  }
  @Override public abstract Object clone();
  @Override public int size(){
    return this.size;
  }
  @Override public boolean isEmpty(){
    return this.size==0;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
  }
  @Override public int hashCode(){
    final RefSnglLnkNode<E> head;
    if((head=this.head)!=null){
      return RefSnglLnkNode.uncheckedHashCode(head);
    }
    return 1;
  }
  @Override public String toString(){
    final RefSnglLnkNode<E> head;
    if((head=this.head)!=null){
      final StringBuilder builder=new StringBuilder("[");
      RefSnglLnkNode.uncheckedToString(head,builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public void forEach(Consumer<? super E> action){
    final RefSnglLnkNode<E> head;
    if((head=this.head)!=null){
      RefSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      RefSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(E val);
  @Override public boolean add(E val)
  {
    push((val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final RefSnglLnkNode<E> head;
    if((head=this.head)!=null){
      RefSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public Object[] toArray(){
    final RefSnglLnkNode<E> head;
    if((head=this.head)!=null){
      final Object[] dst;
      RefSnglLnkNode.uncheckedCopyInto(head,dst=new Object[this.size]);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedcontainsNonNull(head,val);
            }
            return RefSnglLnkNode.uncheckedcontainsNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Boolean val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefSnglLnkNode.uncheckedcontainsNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Byte val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefSnglLnkNode.uncheckedcontainsNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Character val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefSnglLnkNode.uncheckedcontainsNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Short val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefSnglLnkNode.uncheckedcontainsNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Integer val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefSnglLnkNode.uncheckedcontainsNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Long val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefSnglLnkNode.uncheckedcontainsNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Float val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefSnglLnkNode.uncheckedcontainsNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Double val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefSnglLnkNode.uncheckedcontainsNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
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
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
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
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
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
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
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
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
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
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
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
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
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
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return uncheckedremoveVal(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return uncheckedremoveValNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveValNonNull(RefSnglLnkNode<E> head,Object nonNull);
  abstract boolean uncheckedremoveValNull(RefSnglLnkNode<E> head);
  abstract boolean uncheckedremoveVal(RefSnglLnkNode<E> head,Predicate<? super E> pred);
  @Override public boolean removeIf(Predicate<? super E> filter){
    final RefSnglLnkNode<E> head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(RefSnglLnkNode<E> head,Predicate<? super E> filter);
  public static class CheckedStack<E> extends UncheckedStack<E>{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,RefSnglLnkNode<E> head){
      super(size,head);
    }
    @Override public void push(E val){
      ++this.modCount;
      super.push(val);
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public void clear(){
     if(size!=0){
       ++this.modCount;
       this.size=0;
       this.head=null;
     }
    }
    @Override public Object clone(){
      RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        final CheckedStack<E> clone;
        RefSnglLnkNode<E> newHead;
        for(clone=new CheckedStack<E>(this.size,newHead=new RefSnglLnkNode<E>(head.val));(head=head.next)!=null;newHead=newHead.next=new RefSnglLnkNode<E>(head.val)){}
        return clone;
      }
      return new CheckedStack<E>();
    }
    @Override public E pop(){
      RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        ++this.modCount;
        var ret=head.val;
        this.head=head.next;
        --this.size;
        return ret;
      }
      throw new NoSuchElementException();
    }
  @Override public int hashCode(){
    final RefSnglLnkNode<E> head;
    if((head=this.head)!=null){
      final int modCount=this.modCount;
      try{
        return RefSnglLnkNode.uncheckedHashCode(head);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    return 1;
  }
  @Override public String toString(){
    final RefSnglLnkNode<E> head;
    if((head=this.head)!=null){
      final StringBuilder builder=new StringBuilder("[");
      final int modCount=this.modCount;
      try{
        RefSnglLnkNode.uncheckedToString(head,builder);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      return builder.append(']').toString();
    }
    return "[]";
  }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return CheckedStack.this.modCount;
      }
    }
    @Override public void forEach(Consumer<? super E> action){
      final RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          RefSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray((arrSize)->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override boolean uncheckedremoveIf(RefSnglLnkNode<E> head,Predicate<? super E> filter){
      final int modCount=this.modCount;
      try
      {
        int numLeft=this.size-1;
        if(filter.test(head.val)){
          while((head=head.next)!=null){
            --numLeft;
            if(!filter.test(head.val)){
              //TODO
              //this.size=RefSnglLnkNode.retainSurvivors(head,filter,new ModCountChecker(modCount),numLeft);
              this.modCount=modCount+1;
              this.head=head;
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=null;
          this.size=0;
          return true;
        }else{
          RefSnglLnkNode<E> prev;
          for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
            --numLeft;
            if(filter.test(head.val)){
              //TODO
              //this.size=numSurvivors+RefSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter,new ModCountChecker(modCount),numLeft);
              this.modCount=modCount+1;
              return true;
            }
          }
        }
      }
      catch(ConcurrentModificationException e)
      {
        throw e;
      }
      catch(RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override public E poll(){
      final RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override boolean uncheckedremoveValNonNull(RefSnglLnkNode<E> head
      ,Object nonNull
      ){
        final int modCount=this.modCount;
        if(nonNull.equals(head.val)){
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
          try
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!nonNull.equals(head.val));
          }
          finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveValNull(RefSnglLnkNode<E> head
      ){
        if(null==(head.val)){
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(null!=(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveVal(RefSnglLnkNode<E> head
      ,Predicate<? super E> pred
      ){
        if(pred.test(head.val)){
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!pred.test(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override
    public int search(Object val){
      final RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefSnglLnkNode.uncheckedsearchNonNull(head,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefSnglLnkNode.uncheckedsearchNull(head);
      }
      return -1;
    }
    @Override
    public boolean contains(Object val){
      final RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefSnglLnkNode.uncheckedcontainsNonNull(head,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefSnglLnkNode.uncheckedcontainsNull(head);
      }
      return false;
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr<E>(this);
    }
    private static class Itr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final CheckedStack<E> parent;
      transient int modCount;
      transient RefSnglLnkNode<E> prev;
      transient RefSnglLnkNode<E> curr;
      transient RefSnglLnkNode<E> next;
      Itr(CheckedStack<E> parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public E next(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final RefSnglLnkNode<E> next;
        if((next=this.next)!=null){
          this.next=next.next;
          this.prev=this.curr;
          this.curr=next;
          return next.val;
        }
        throw new NoSuchElementException();
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(RefSnglLnkNode<E> next,Consumer<? super E> action){
        final int modCount=this.modCount;
        RefSnglLnkNode<E> prev,curr;
        try{
          curr=this.curr;
          do{
            action.accept(next.val);
            prev=curr;
          }while((next=(curr=next).next)!=null);
        }finally{
          CheckedCollection.checkModCount(modCount,parent.modCount);
        }
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final RefSnglLnkNode<E> next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void remove(){
        final RefSnglLnkNode<E> prev;
        if(this.curr!=(prev=this.prev)){
          final CheckedStack<E> parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          if(prev==null){
            parent.head=next;
          }else{
            prev.next=next;
          }
          this.curr=prev;
          return;
        }
        throw new IllegalStateException();
      }
    }
  }
  public static class UncheckedStack<E> extends RefSnglLnkSeq<E> implements OmniStack.OfRef<E>{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,RefSnglLnkNode<E> head){
      super(size,head);
    }
    @Override public void push(E val){
      this.head=new RefSnglLnkNode<E>(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        final UncheckedStack<E> clone;
        RefSnglLnkNode<E> newHead;
        for(clone=new UncheckedStack<E>(this.size,newHead=new RefSnglLnkNode<E>(head.val));(head=head.next)!=null;newHead=newHead.next=new RefSnglLnkNode<E>(head.val)){}
        return clone;
      }
      return new UncheckedStack<E>();
    }
    @Override public E pop(){
      RefSnglLnkNode<E> head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(RefSnglLnkNode<E> head,Predicate<? super E> filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=RefSnglLnkNode.retainSurvivors(head,filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        RefSnglLnkNode<E> prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+RefSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override public E peek(){
      final RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public E poll(){
      final RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override boolean uncheckedremoveValNonNull(RefSnglLnkNode<E> head
      ,Object nonNull
      ){
        if(nonNull.equals(head.val)){
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!nonNull.equals(head.val));
          }
          prev.next=head.next;
        }
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveValNull(RefSnglLnkNode<E> head
      ){
        if(null==(head.val)){
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(null!=(head.val));
          }
          prev.next=head.next;
        }
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveVal(RefSnglLnkNode<E> head
      ,Predicate<? super E> pred
      ){
        if(pred.test(head.val)){
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!pred.test(head.val));
          }
          prev.next=head.next;
        }
        --this.size;
        return true;
      }
    @Override public int search(boolean val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedsearchNonNull(head,val);
            }
            return RefSnglLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Boolean val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return RefSnglLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Byte val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return RefSnglLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Character val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return RefSnglLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Short val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return RefSnglLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Integer val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return RefSnglLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Long val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return RefSnglLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Float val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return RefSnglLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Double val){
      {
        {
          final RefSnglLnkNode<E> head;
          if((head=this.head)!=null)
          {
            if(val!=null)
            {
              return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefSnglLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr<E>(this);
    }
    private static class Itr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final RefSnglLnkSeq<E> parent;
      transient RefSnglLnkNode<E> prev;
      transient RefSnglLnkNode<E> curr;
      transient RefSnglLnkNode<E> next;
      Itr(RefSnglLnkSeq<E> parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public E next(){
        final RefSnglLnkNode<E> next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(RefSnglLnkNode<E> next,Consumer<? super E> action){
        RefSnglLnkNode<E> prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final RefSnglLnkNode<E> next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void remove(){
        final RefSnglLnkSeq<E> parent;
        --(parent=this.parent).size;
        final RefSnglLnkNode<E> prev;
        if((prev=this.prev)==null){
          parent.head=next;
        }else{
          prev.next=next;
        }
        this.curr=prev;
      }
    }
  } 
}
