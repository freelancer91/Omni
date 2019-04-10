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
public abstract class RefSnglLnkSeq<E> implements OmniCollection.OfRef<E>,Cloneable{
  static class Node<E>{
    transient E val;
    transient Node<E> next;
    Node(E val){
      this.val=val;
    }
    Node(E val,Node<E> next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+Objects.hashCode(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+Objects.hashCode(curr.val);
      }
      return hash;
    }
    private void uncheckedToString(StringBuilder builder){
      for(var curr=this;;builder.append(',').append(' ')){
        builder.append(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(Consumer<? super E> action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(Object[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private boolean uncheckedcontainsNonNull(Object nonNull){
      for(var curr=this;!nonNull.equals(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    private int uncheckedsearchNonNull(Object nonNull){
      int index=1;
      for(var curr=this;!nonNull.equals(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    private boolean uncheckedcontainsNull(){
      for(var curr=this;null!=(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    private int uncheckedsearchNull(){
      int index=1;
      for(var curr=this;null!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    private boolean uncheckedcontains(Predicate<? super E> pred){
      for(var curr=this;!pred.test(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    private int uncheckedsearch(Predicate<? super E> pred){
      int index=1;
      for(var curr=this;!pred.test(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    private int retainSurvivors(final Predicate<? super E> filter){
      int numSurvivors=1;
      Node<E> prev,next;
      outer:for(next=(prev=this).next;next!=null;++numSurvivors,next=(prev=next).next){
        if(filter.test(next.val)){
          do{
            if((next=next.next)==null){
              prev.next=null;
              break outer;
            }
          }while(filter.test(next.val));
          prev.next=next;
        }
      }
      return numSurvivors;
    }
    private int retainTrailingSurvivors(final Predicate<? super E> filter){
      int numSurvivors=0;
      Node<E> prev,curr;
      outer:for(prev=this;;prev=curr){
        if((curr=prev.next)==null){
          prev.next=null;
          break;
        }
        if(!filter.test(curr.val)){
          prev.next=curr;
          do{
            ++numSurvivors;
            if((curr=(prev=curr).next)==null){
              break outer;
            }
          }
          while(!filter.test(curr.val));
        }
      }
      return numSurvivors;
    }
  }
  transient int size;
  transient Node<E> head;
  private RefSnglLnkSeq(){
  }
  private RefSnglLnkSeq(int size,Node<E> head){
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
    final Node<E> head;
    if((head=this.head)!=null){
      return head.uncheckedHashCode();
    }
    return 1;
  }
  @Override public String toString(){
    final Node<E> head;
    if((head=this.head)!=null){
      final StringBuilder builder=new StringBuilder("[");
      head.uncheckedToString(builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public void forEach(Consumer<? super E> action){
    final Node<E> head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      head.uncheckedCopyInto(arr);
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
    final Node<E> head;
    if((head=this.head)!=null){
      head.uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public Object[] toArray(){
    final Node<E> head;
    if((head=this.head)!=null){
      final Object[] dst;
      head.uncheckedCopyInto(dst=new Object[this.size]);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
            if(val!=null)
            {
              return head.uncheckedcontainsNonNull(val);
            }
            return head.uncheckedcontainsNull();
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
            return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred(val));
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
            if(val!=null)
            {
              return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return head.uncheckedcontainsNull();
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
            if(val!=null)
            {
              return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return head.uncheckedcontainsNull();
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
            if(val!=null)
            {
              return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return head.uncheckedcontainsNull();
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
            if(val!=null)
            {
              return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return head.uncheckedcontainsNull();
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
            if(val!=null)
            {
              return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return head.uncheckedcontainsNull();
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
            if(val!=null)
            {
              return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return head.uncheckedcontainsNull();
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
            if(val!=null)
            {
              return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return head.uncheckedcontainsNull();
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
            if(val!=null)
            {
              return head.uncheckedcontains(OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return head.uncheckedcontainsNull();
          } //end size check
        } //end checked sublist try modcount
      }//end val check
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
          final Node<E> head;
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
          final Node<E> head;
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
          final Node<E> head;
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
          final Node<E> head;
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
          final Node<E> head;
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
          final Node<E> head;
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
          final Node<E> head;
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
  abstract boolean uncheckedremoveValNonNull(Node<E> head,Object nonNull);
  abstract boolean uncheckedremoveValNull(Node<E> head);
  abstract boolean uncheckedremoveVal(Node<E> head,Predicate<? super E> pred);
  @Override public boolean removeIf(Predicate<? super E> filter){
    final Node<E> head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(Node<E> head,Predicate<? super E> filter);
  public static class CheckedStack<E> extends UncheckedStack<E>{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,Node<E> head){
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
      Node<E> head;
      if((head=this.head)!=null){
        final CheckedStack<E> clone;
        Node<E> newHead;
        for(clone=new CheckedStack<E>(this.size,newHead=new Node<E>(head.val));(head=head.next)!=null;newHead=newHead.next=new Node<E>(head.val)){}
        return clone;
      }
      return new CheckedStack<E>();
    }
    @Override public E pop(){
      Node<E> head;
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
    final Node<E> head;
    if((head=this.head)!=null){
      final int modCount=this.modCount;
      try{
        return head.uncheckedHashCode();
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    return 1;
  }
  @Override public String toString(){
    final Node<E> head;
    if((head=this.head)!=null){
      final StringBuilder builder=new StringBuilder("[");
      final int modCount=this.modCount;
      try{
        head.uncheckedToString(builder);
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
      final Node<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          head.uncheckedForEach(action);
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
    @Override boolean uncheckedremoveIf(Node<E> head,Predicate<? super E> filter){
      final int modCount=this.modCount;
      try
      {
        if(filter.test(head.val)){
          while((head=head.next)!=null){
            if(!filter.test(head.val)){
              this.size=head.retainSurvivors(filter,new ModCountChecker(modCount));
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
          for(int numSurvivors=1;(head=head.next)!=null;++numSurvivors){
            if(filter.test(head.val)){
              this.size=numSurvivors+head.retainTrailingSurvivors(filter,new ModCountChecker(modCount));
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
      final Node<E> head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override boolean uncheckedremoveValNonNull(Node<E> head
      ,Object nonNull
      ){
        final int modCount=this.modCount;
        if(nonNull.equals(head.val)){
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.head=head.next;
        }else{
          Node<E> prev;
          try
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!nonNull.equals(head.val));
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveValNull(Node<E> head
      ){
        if(null==(head.val)){
          this.head=head.next;
        }else{
          Node<E> prev;
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
    @Override boolean uncheckedremoveVal(Node<E> head
      ,Predicate<? super E> pred
      ){
        if(pred.test(head.val)){
          this.head=head.next;
        }else{
          Node<E> prev;
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
      final Node<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return head.uncheckedsearchNonNull(val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return head.uncheckedsearchNull();
      }
      return -1;
    }
    @Override
    public boolean contains(Object val){
      final Node<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return head.uncheckedcontainsNonNull(val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return head.uncheckedcontainsNull();
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
      transient Node<E> prev;
      transient Node<E> curr;
      transient Node<E> next;
      Itr(CheckedStack<E> parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public E next(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final Node<E> next;
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
      private void uncheckedForEachRemaining(Node<E> next,Consumer<? super E> action){
        final int modCount=this.modCount;
        Node<E> prev,curr;
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
        final Node<E> next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void remove(){
        final Node<E> prev;
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
    private UncheckedStack(int size,Node<E> head){
      super(size,head);
    }
    @Override public void push(E val){
      this.head=new Node<E>(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      Node<E> head;
      if((head=this.head)!=null){
        final UncheckedStack<E> clone;
        Node<E> newHead;
        for(clone=new UncheckedStack<E>(this.size,newHead=new Node<E>(head.val));(head=head.next)!=null;newHead=newHead.next=new Node<E>(head.val)){}
        return clone;
      }
      return new UncheckedStack<E>();
    }
    @Override public E pop(){
      Node<E> head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(Node<E> head,Predicate<? super E> filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=head.retainSurvivors(filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        for(int numSurvivors=1;(head=head.next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+head.retainTrailingSurvivors(filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override public E peek(){
      final Node<E> head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return null;
    }
    @Override public E poll(){
      final Node<E> head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override boolean uncheckedremoveValNonNull(Node<E> head
      ,Object nonNull
      ){
        if(nonNull.equals(head.val)){
          this.head=head.next;
        }else{
          Node<E> prev;
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
    @Override boolean uncheckedremoveValNull(Node<E> head
      ){
        if(null==(head.val)){
          this.head=head.next;
        }else{
          Node<E> prev;
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
    @Override boolean uncheckedremoveVal(Node<E> head
      ,Predicate<? super E> pred
      ){
        if(pred.test(head.val)){
          this.head=head.next;
        }else{
          Node<E> prev;
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
          final Node<E> head;
          if((head=this.head)!=null)
          {
            return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
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
            if(val!=null)
            {
              return head.uncheckedsearchNonNull(val);
            }
            return head.uncheckedsearchNull();
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
            return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
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
            return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred(val));
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
            if(val!=null)
            {
              return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return head.uncheckedsearchNull();
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
            if(val!=null)
            {
              return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return head.uncheckedsearchNull();
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
            if(val!=null)
            {
              return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return head.uncheckedsearchNull();
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
            if(val!=null)
            {
              return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return head.uncheckedsearchNull();
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
            if(val!=null)
            {
              return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return head.uncheckedsearchNull();
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
            if(val!=null)
            {
              return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return head.uncheckedsearchNull();
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
            if(val!=null)
            {
              return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return head.uncheckedsearchNull();
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
            if(val!=null)
            {
              return head.uncheckedsearch(OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return head.uncheckedsearchNull();
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
      transient Node<E> prev;
      transient Node<E> curr;
      transient Node<E> next;
      Itr(RefSnglLnkSeq<E> parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public E next(){
        final Node<E> next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(Node<E> next,Consumer<? super E> action){
        Node<E> prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final Node<E> next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void remove(){
        final RefSnglLnkSeq<E> parent;
        --(parent=this.parent).size;
        final Node<E> prev;
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
