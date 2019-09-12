package omni.impl.seq;
import java.util.Collection;
import java.util.function.IntFunction;
import omni.api.OmniIterator;
import omni.api.OmniCollection;
import omni.impl.CheckedCollection;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import omni.util.OmniArray;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.util.OmniPred;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.impl.AbstractOmniCollection;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import java.util.Objects;
public abstract class RefSnglLnkSeq<E> extends 
AbstractOmniCollection<E>
 implements OmniCollection.OfRef<E>,Externalizable{
  static final class Node<E>{
    transient E val;
    transient Node<E> next;
    Node(E val){
      this.val=val;
    }
    Node(E val,Node<E> next){
      this.val=val;
      this.next=next;
    }
    public static <E> void uncheckedToString(Node<E> curr,Node<E> tail,StringBuilder builder){
      for(builder.append(curr.val);curr!=tail;builder.append(',').append(' ').append((curr=curr.next).val)){}
    }
    public static <E> void uncheckedToString(Node<E> curr,StringBuilder builder){
      for(;;builder.append(',').append(' ')){
        builder.append(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static <E> int uncheckedHashCode(Node<E> curr,Node<E> tail){
      int hash=31+Objects.hashCode(curr.val);
      while(curr!=tail){
        hash=(hash*31)+Objects.hashCode((curr=curr.next).val);
      }
      return hash;
    }
    public static <E> int uncheckedHashCode(Node<E> curr){
      int hash=31+Objects.hashCode(curr.val);
      for(;(curr=curr.next)!=null;hash=(hash*31)+Objects.hashCode(curr.val)){}
      return hash;
    }
    public static <E> void uncheckedForEach(Node<E> curr,Node<E> tail,Consumer<? super E> action){
      for(action.accept(curr.val);curr!=tail;action.accept((curr=curr.next).val)){}
    }
    public static <E> void uncheckedForEach(Node<E> curr,Consumer<? super E> action){
      do{
        action.accept(curr.val);
      }while((curr=curr.next)!=null);
    }
    public static <E> boolean uncheckedcontainsNonNull(Node<E> curr,Node<E> tail,Object nonNull){
      for(;!nonNull.equals(curr.val);curr=curr.next){if(curr==tail){return false;}}
      return true;
    }
    public static <E> boolean uncheckedcontainsNonNull(Node<E> curr
    ,Object nonNull
    ){
      for(;!nonNull.equals(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    public static <E> int uncheckedsearchNonNull(Node<E> curr
    ,Object nonNull
    ){
      int index=1;
      for(;!nonNull.equals(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    public static <E> boolean uncheckedcontainsNull(Node<E> curr
    ){
      for(;null!=(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    public static <E> int uncheckedsearchNull(Node<E> curr
    ){
      int index=1;
      for(;null!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    public static <E> boolean uncheckedcontains (Node<E> curr
    ,Predicate<? super E> pred
    ){
      for(;!pred.test(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    public static <E> int uncheckedsearch (Node<E> curr
    ,Predicate<? super E> pred
    ){
      int index=1;
      for(;!pred.test(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    public static <E> void uncheckedCopyInto(Node<E> curr,Object[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
  }
  private static final long serialVersionUID=1L;
  transient Node<E> head;
  private RefSnglLnkSeq(Collection<? extends E> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private RefSnglLnkSeq(OmniCollection.OfRef<? extends E> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private RefSnglLnkSeq(){
  }
  private RefSnglLnkSeq(Node<E> head,int size){
    super(size);
    this.head=head;
  }
  private static <E> void pullSurvivorsDown(Node<E> prev,Predicate<? super E> filter,long[] survivorSet,int numSurvivors,int numRemoved){
    int wordOffset;
    for(long word=survivorSet[wordOffset=0],marker=1L;;){
      var curr=prev.next;
      if((marker&word)==0){
        do{
          if(--numRemoved==0){
            prev.next=curr.next;
            return;
          }else if((marker<<=1)==0){
            word=survivorSet[++wordOffset];
            marker=1L;
          }
          curr=curr.next;
        }while((marker&word)==0);
        prev.next=curr;
      }
      if(--numSurvivors==0){
        curr.next=null;
        return;
      }else if((marker<<=1)==0){
         word=survivorSet[++wordOffset];
         marker=1L;
      }
      prev=curr;
    }
  }
  private static <E> int markSurvivors(Node<E> curr,Predicate<? super E> filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test(curr.val)){
          word|=marker;
          ++numSurvivors;
        }
        if((curr=curr.next)==null){
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
      }while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  private static <E> void pullSurvivorsDown(Node<E> prev,long word,int numSurvivors,int numRemoved){
    for(long marker=1L;;marker<<=1){
      var curr=prev.next;
      if((marker&word)==0){
        do{
          if(--numRemoved==0){
            prev.next=curr.next;
            return;
          }
          curr=curr.next;
        }while(((marker<<=1)&word)==0);
        prev.next=curr;
      }
      if(--numSurvivors==0){
        curr.next=null;
        return;
      }
      prev=curr;
    }
  }
  private static <E> long markSurvivors(Node<E> curr,Predicate<? super E> filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  private static <E> int retainSurvivors(Node<E> prev, final Predicate<? super E> filter){
    int numSurvivors=1;
    outer:for(Node<E> next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  private static <E> int retainTrailingSurvivors(Node<E> prev,Node<E> curr,final Predicate<? super E> filter){
    int numSurvivors=0;
    outer:for(;;curr=curr.next){
      if(curr==null){
        prev.next=null;
        break;
      }else if(!filter.test(curr.val)){
        prev.next=curr;
        do{
          ++numSurvivors;
          if((curr=(prev=curr).next)==null){
            break outer;
          }
        }while(!filter.test(curr.val));
      }
    }
    return numSurvivors;
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
  @Override public void clear(){
    this.head=null;
    this.size=0;
  }
  @Override public String toString(){
    final Node<E> head;
    if((head=this.head)!=null){
      final StringBuilder builder=new StringBuilder("[");
      Node.uncheckedToString(head,builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public void forEach(Consumer<? super E> action){
    final Node<E> head;
    if((head=this.head)!=null){
      Node.uncheckedForEach(head,action);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      Node.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(E val);
  @Override public boolean add(E val){
    push((val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final Node<E> head;
    if((head=this.head)!=null){
      Node.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else if(arr.length!=0){
      arr[0]=null;
    }
    return arr;
  }
  @Override public Object[] toArray(){
    final Node<E> head;
    if((head=this.head)!=null){
      final Object[] dst;
      Node.uncheckedCopyInto(head,dst=new Object[this.size]);
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
            return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
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
            return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
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
            return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
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
            return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
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
            return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
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
              return Node.uncheckedcontainsNonNull(head,val);
            }
            return Node.uncheckedcontainsNull(head);
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
            return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
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
            return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
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
            return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred(val));
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
              return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return Node.uncheckedcontainsNull(head);
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
              return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return Node.uncheckedcontainsNull(head);
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
              return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return Node.uncheckedcontainsNull(head);
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
              return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return Node.uncheckedcontainsNull(head);
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
              return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return Node.uncheckedcontainsNull(head);
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
              return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return Node.uncheckedcontainsNull(head);
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
              return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return Node.uncheckedcontainsNull(head);
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
              return Node.uncheckedcontains(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return Node.uncheckedcontainsNull(head);
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
  public E peek(){
    final Node<E> head;
    if((head=this.head)!=null){
      return (E)(head.val);
    }
    return null;
  }
  abstract boolean uncheckedremoveIf(Node<E> head,Predicate<? super E> filter);
  public static class CheckedStack<E> extends UncheckedStack<E>{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(Collection<? extends E> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfRef<? extends E> that){
      super(that);
    }
    public CheckedStack(){
    }
    CheckedStack(Node<E> head,int size){
      super(head,size);
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void push(E val){
      ++this.modCount;
      super.push(val);
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
        for(clone=new CheckedStack<E>(newHead=new Node<E>(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new Node<E>(head.val)){}
        return clone;
      }
      return new CheckedStack<E>();
    }
    @Override public E pop(){
      Node<E> head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public String toString(){
      final Node<E> head;
      if((head=this.head)!=null){
        final StringBuilder builder=new StringBuilder("[");
        final int modCount=this.modCount;
        try{
          Node.uncheckedToString(head,builder);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        return builder.append(']').toString();
      }
      return "[]";
    }
    @Override public void forEach(Consumer<? super E> action){
      final Node<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          Node.uncheckedForEach(head,action);
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
    private int removeIfHelper(Node<E> prev,Predicate<? super E> filter,int numLeft,int modCount){
      if(numLeft!=0){
        int numSurvivors;
        if(numLeft>64){
          long[] survivorSet;
          numSurvivors=markSurvivors(prev.next,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(prev,filter,survivorSet,numSurvivors,numLeft);
          }
        }else{
          long survivorWord=markSurvivors(prev.next,filter);
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
    @Override boolean uncheckedremoveIf(Node<E> head,Predicate<? super E> filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        if(filter.test(head.val)){
          while(--numLeft!=0){
            if(!filter.test((head=head.next).val)){
              this.size=1+removeIfHelper(head,filter,--numLeft,modCount);
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
      try
      {
        if(nonNull.equals(head.val)){
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if((head=(prev=head).next)==null){
              CheckedCollection.checkModCount(modCount,this.modCount);
              return false;
            }
          }while(!nonNull.equals(head.val));
          CheckedCollection.checkModCount(modCount,this.modCount);
          prev.next=head.next;
        }
      }
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveValNull(Node<E> head
    ){
      {
        if(null==(head.val)){
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(null!=(head.val));
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveVal(Node<E> head
      ,Predicate<? super E> pred
    ){
      {
        if(pred.test(head.val)){
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(!pred.test(head.val));
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override public int search(Object val){
      final Node<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return Node.uncheckedsearchNonNull(head,val);
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
            return Node.uncheckedcontainsNonNull(head,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return Node.uncheckedcontainsNull(head);
      }
      return false;
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr<E>(this);
    }
    private static class Itr<E> extends AbstractItr<E>{
      final CheckedStack<E> parent;
      transient int modCount;
      Itr(CheckedStack<E> parent){
        super(parent.head);
        this.parent=parent;
        this.modCount=parent.modCount;
      }
      Itr(Itr<E> itr){
        super(itr);
        this.parent=itr.parent;
        this.modCount=itr.modCount;
      }
      @Override public Object clone(){
        return new Itr<E>(this);
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
      @Override void uncheckedForEachRemaining(final Node<E> expectedNext,Consumer<? super E> action){
        final int modCount=this.modCount;
        Node<E> prev,curr,next;
        try{
          curr=this.curr;
          next=expectedNext;
          do{
            action.accept(next.val);
            prev=curr;
          }while((next=(curr=next).next)!=null);
        }finally{
          CheckedCollection.checkModCount(modCount,this.parent.modCount,expectedNext,this.next);
        }
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void remove(){
        final Node<E> prev;
        if(this.curr!=(prev=this.prev)){
          final CheckedStack<E> parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          --parent.size;
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
    private static final long serialVersionUID=1L;
    public UncheckedStack(Collection<? extends E> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfRef<? extends E> that){
      super(that);
    }
    public UncheckedStack(){
    }
    UncheckedStack(Node<E> head,int size){
      super(head,size);
    }
    @SuppressWarnings("unchecked")
    @Override public void readExternal(ObjectInput in) throws IOException
      ,ClassNotFoundException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        Node<E> curr;
        for(this.head=curr=new Node<E>((E)in.readObject());--size!=0;curr=curr.next=new Node<E>((E)in.readObject())){}
      }
    }
    @Override public void push(E val){
      this.head=new Node<E>(val,this.head);
      ++this.size;
    }
    @Override public Object clone(){
      Node<E> head;
      if((head=this.head)!=null){
        final UncheckedStack<E> clone;
        Node<E> newHead;
        for(clone=new UncheckedStack<E>(newHead=new Node<E>(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new Node<E>(head.val)){}
        return clone;
      }
      return new UncheckedStack<E>();
    }
    @Override public E pop(){
      Node<E> head;
      this.head=(head=this.head).next;
      --this.size;
      return head.val;
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
    @Override boolean uncheckedremoveIf(Node<E> head,Predicate<? super E> filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=retainSurvivors(head,filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        Node<E> prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveValNonNull(Node<E> head
      ,Object nonNull
    ){
      {
        if(nonNull.equals(head.val)){
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(!nonNull.equals(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveValNull(Node<E> head
    ){
      {
        if(null==(head.val)){
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(null!=(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveVal(Node<E> head
      ,Predicate<? super E> pred
    ){
      {
        if(pred.test(head.val)){
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(!pred.test(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr();
    }
    private class Itr extends AbstractItr<E>{
      Itr(){
        super(UncheckedStack.this.head);
      }
      Itr(Itr itr){
        super(itr);
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public void remove(){
        final UncheckedStack<E> parent;
        --(parent=UncheckedStack.this).size;
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
  public static class CheckedQueue<E> extends UncheckedQueue<E>{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedQueue(Collection<? extends E> that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfRef<? extends E> that){
      super(that);
    }
    public CheckedQueue(){
      super();
    }
    CheckedQueue(Node<E> head,int size,Node<E> tail){
      super(head,size,tail);
    }
    @Override public E element(){
      final Node<E> head;
      if((head=this.head)!=null){
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        int size;
        final var tail=this.tail;
        out.writeInt(size=this.size);
        if(size!=0){
          Node<E> curr;
          for(curr=this.head;;curr=curr.next){
            out.writeObject(curr.val);
            if(curr==tail){
              return;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public String toString(){
      final Node<E> head;
      if((head=this.head)!=null){
        final StringBuilder builder=new StringBuilder("[");
        final int modCount=this.modCount;
        try{
          Node.uncheckedToString(head,tail,builder);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        return builder.append(']').toString();
      }
      return "[]";
    }
    @Override public void forEach(Consumer<? super E> action){
      final Node<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          Node.uncheckedForEach(head,tail,action);
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
    @Override public void clear(){
      if(size!=0){
        ++this.modCount;
        this.head=null;
        this.tail=null;
        this.size=0;
      }
    }
    @Override public Object clone(){
      Node<E> head;
      if((head=this.head)!=null){
        Node<E> newHead=new Node<E>(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new Node<E>((head=head.next).val)){}
        return new CheckedQueue<E>(newHead,this.size,newTail);
      }
      return new CheckedQueue<E>();
    }
    @Override void push(E val){
      ++this.modCount;
      super.push(val);
    }
    @Override public E remove(){
      final Node<E> head;
      if((head=this.head)!=null){
        ++this.modCount;
        if(head==tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public E poll(){
      final Node<E> head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    private void pullSurvivorsDown(Node<E> prev,Predicate<? super E> filter,long[] survivorSet,int numSurvivors,int numRemoved){
      int wordOffset;
      for(long word=survivorSet[wordOffset=0],marker=1L;;){
        var curr=prev.next;
        if((marker&word)==0){
          do{
            if(--numRemoved==0){
              prev.next=curr.next;
              if(curr==tail){
                this.tail=prev;
              }
              return;
            }else if((marker<<=1)==0){
              word=survivorSet[++wordOffset];
              marker=1L;
            }
            curr=curr.next;
          }while((marker&word)==0);
          prev.next=curr;
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
              prev.next=curr.next;
              if(curr==tail){
                this.tail=prev;
              }
              return;
            }
            curr=curr.next;
          }while(((marker<<=1)&word)==0);
          prev.next=curr;
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
          long[] survivorSet;
          numSurvivors=markSurvivors(prev.next,filter,survivorSet=new long[(numLeft-1>>6)+1]);
          CheckedCollection.checkModCount(modCount,this.modCount);
          if((numLeft-=numSurvivors)!=0){
            pullSurvivorsDown(prev,filter,survivorSet,numSurvivors,numLeft);
          }
        }else{
          long survivorWord=markSurvivors(prev.next,filter);
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
    @Override boolean uncheckedremoveIf(Node<E> head,Predicate<? super E> filter){
      final int modCount=this.modCount;
      try{
        int numLeft=this.size;
        if(filter.test(head.val)){
          while(--numLeft!=0){
            if(!filter.test((head=head.next).val)){
              this.size=1+removeIfHelper(head,filter,--numLeft,modCount);
              this.modCount=modCount+1;
              this.head=head;
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
    @Override boolean uncheckedremoveValNonNull(Node<E> head
    ,Object nonNull
    ){
      final int modCount=this.modCount;
      try
      {
        final var tail=this.tail;
        if(nonNull.equals(head.val)){
          CheckedCollection.checkModCount(modCount,this.modCount);
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if(head==tail){
              CheckedCollection.checkModCount(modCount,this.modCount);
              return false;
            }
          }while(!nonNull.equals((head=(prev=head).next).val));
          CheckedCollection.checkModCount(modCount,this.modCount);
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveValNull(Node<E> head
    ){
      {
        final var tail=this.tail;
        if(null==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if(head==tail){
              return false;
            }
          }while(null!=((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveVal(Node<E> head
    ,Predicate<? super E> pred
    ){
      {
        final var tail=this.tail;
        if(pred.test(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if(head==tail){
              return false;
            }
          }while(!pred.test((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override public boolean contains(Object val){
      final Node<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return Node.uncheckedcontainsNonNull(head,tail,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return Node.uncheckedcontainsNull(head);
      }
      return false;
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr<E>(this);
    }
    private static class Itr<E> extends AbstractItr<E>{
      transient final CheckedQueue<E> parent;
      transient int modCount;
      Itr(CheckedQueue<E> parent){
        super(parent.head);
        this.parent=parent;
        this.modCount=parent.modCount;
      }
      Itr(Itr<E> itr){
        super(itr);
        this.parent=itr.parent;
        this.modCount=itr.modCount;
      }
      @Override public Object clone(){
        return new Itr<E>(this);
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
      @Override void uncheckedForEachRemaining(final Node<E> expectedNext,Consumer<? super E> action){
        final int modCount=this.modCount;
        Node<E> prev,curr,next;
        final CheckedQueue<E> parent;
        final var tail=(parent=this.parent).tail;
        try{
          for(curr=this.curr,next=expectedNext;;next=curr.next){
            action.accept(next.val);
            prev=curr;
            if((curr=next)==tail){
              break;
            }
          }
        }finally{
          CheckedCollection.checkModCount(modCount,parent.modCount,expectedNext,this.next);
        }
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void remove(){
        final Node<E> prev;
        if(this.curr!=(prev=this.prev)){
          final CheckedQueue<E> parent;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(parent=this.parent).modCount);
          parent.modCount=++modCount;
          this.modCount=modCount;
          --parent.size;
          if(prev==null){
            parent.head=next;
          }else{
            prev.next=next;
          }
          if(curr==parent.tail){
            parent.tail=prev;
          }
          this.curr=prev;
          return;
        }
        throw new IllegalStateException();
      }
    }
  }
  public static class UncheckedQueue<E> extends RefSnglLnkSeq<E> implements OmniQueue.OfRef<E>{
    private static final long serialVersionUID=1L;
    transient Node<E> tail;
    public UncheckedQueue(Collection<? extends E> that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfRef<? extends E> that){
      super(that);
    }
    public UncheckedQueue(){
      super();
    }
    UncheckedQueue(Node<E> head,int size,Node<E> tail){
      super(head,size);
      this.tail=tail;
    }
    @SuppressWarnings("unchecked")
    @Override public void readExternal(ObjectInput in) throws IOException
      ,ClassNotFoundException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        Node<E> curr;
        for(this.head=curr=new Node<E>((E)in.readObject());--size!=0;curr=curr.next=new Node<E>((E)in.readObject())){}
        this.tail=curr;
      }
    }
    @Override public void clear(){
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    @Override public Object clone(){
      Node<E> head;
      if((head=this.head)!=null){
        Node<E> newHead=new Node<E>(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new Node<E>((head=head.next).val)){}
        return new UncheckedQueue<E>(newHead,this.size,newTail);
      }
      return new UncheckedQueue<E>();
    }
    @Override void push(E val){
      final var newNode=new Node<E>(val);
      final Node<E> tail;
      if((tail=this.tail)!=null){
        tail.next=newNode;
      }else{
        this.head=newNode;
      }
      this.tail=newNode;
      ++this.size;
    }
    @Override public E element(){
      return head.val;
    }
    @Override public boolean offer(E val){
      push(val);
      return true;
    }
    @Override public E remove(){
      final Node<E> head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public E poll(){
      final Node<E> head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
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
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(Node<E> prev,Node<E> curr,Node<E> tail,Predicate<? super E> filter){
      int numSurvivors=0;
      while(curr!=tail) {
        if(!filter.test((curr=curr.next).val)){
          prev.next=curr;
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
    @Override boolean uncheckedremoveIf(Node<E> head,Predicate<? super E> filter){
      if(filter.test(head.val)){
        for(var tail=this.tail;head!=tail;){
          if(!filter.test((head=head.next).val)){
            this.size=removeIfHelper(head,tail,filter);
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
    @Override boolean uncheckedremoveValNonNull(Node<E> head
    ,Object nonNull
    ){
      {
        final var tail=this.tail;
        if(nonNull.equals(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if(head==tail){
              return false;
            }
          }while(!nonNull.equals((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveValNull(Node<E> head
    ){
      {
        final var tail=this.tail;
        if(null==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if(head==tail){
              return false;
            }
          }while(null!=((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveVal(Node<E> head
    ,Predicate<? super E> pred
    ){
      {
        final var tail=this.tail;
        if(pred.test(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          Node<E> prev;
          do{
            if(head==tail){
              return false;
            }
          }while(!pred.test((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr();
    }
    private class Itr extends AbstractItr<E>{
      Itr(){
        super(UncheckedQueue.this.head);
      }
      Itr(Itr itr){
        super(itr);
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public void remove(){
        final UncheckedQueue<E> parent;
        --(parent=UncheckedQueue.this).size;
        final Node<E> prev;
        if((prev=this.prev)==null){
          parent.head=next;
        }else{
          prev.next=next;
        }
        if(this.curr==parent.tail){
          parent.tail=prev;
        }
        this.curr=prev;
      }
    }
  }
  private abstract static class AbstractItr<E>
      implements OmniIterator.OfRef<E>
  {
    transient Node<E> prev;
    transient Node<E> curr;
    transient Node<E> next;
    AbstractItr(AbstractItr<E> itr){
      this.prev=itr.prev;
      this.curr=itr.curr;
      this.next=itr.next;
    }
    AbstractItr(Node<E> next){
      this.next=next; 
    }
    @Override public abstract Object clone();
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
    void uncheckedForEachRemaining(Node<E> next,Consumer<? super E> action){
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
  }
}
