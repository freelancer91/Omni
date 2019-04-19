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
import omni.util.OmniPred;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.impl.RefSnglLnkNode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
public abstract class RefSnglLnkSeq<E> extends AbstractSeq implements OmniCollection.OfRef<E>,Externalizable{
  private static final long serialVersionUID=1L;
  transient RefSnglLnkNode<E> head;
  private RefSnglLnkSeq(){
  }
  private RefSnglLnkSeq(RefSnglLnkNode<E> head,int size){
    super(size);
    this.head=head;
  }
  private static <E> void pullSurvivorsDown(RefSnglLnkNode<E> prev,Predicate<? super E> filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
  private static <E> int markSurvivors(RefSnglLnkNode<E> curr,Predicate<? super E> filter,long[] survivorSet){
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
  private static <E> void pullSurvivorsDown(RefSnglLnkNode<E> prev,long word,int numSurvivors,int numRemoved){
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
  private static <E> long markSurvivors(RefSnglLnkNode<E> curr,Predicate<? super E> filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  private static <E> int retainSurvivors(RefSnglLnkNode<E> prev, final Predicate<? super E> filter){
    int numSurvivors=1;
    outer:for(RefSnglLnkNode<E> next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  private static <E> int retainTrailingSurvivors(RefSnglLnkNode<E> prev,RefSnglLnkNode<E> curr,final Predicate<? super E> filter){
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
  @Override public boolean add(E val){
    push((val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final RefSnglLnkNode<E> head;
    if((head=this.head)!=null){
      RefSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else if(arr.length!=0){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
          final RefSnglLnkNode<E> head;
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
  public E peek(){
    final RefSnglLnkNode<E> head;
    if((head=this.head)!=null){
      return (E)(head.val);
    }
    return null;
  }
  abstract boolean uncheckedremoveIf(RefSnglLnkNode<E> head,Predicate<? super E> filter);
  public static class CheckedStack<E> extends UncheckedStack<E>{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(){
    }
    CheckedStack(RefSnglLnkNode<E> head,int size){
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
        for(clone=new CheckedStack<E>(newHead=new RefSnglLnkNode<E>(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new RefSnglLnkNode<E>(head.val)){}
        return clone;
      }
      return new CheckedStack<E>();
    }
    @Override public E pop(){
      RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
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
    private int removeIfHelper(RefSnglLnkNode<E> prev,Predicate<? super E> filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(RefSnglLnkNode<E> head,Predicate<? super E> filter){
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
      try
      {
        if(nonNull.equals(head.val)){
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
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
    @Override boolean uncheckedremoveValNull(RefSnglLnkNode<E> head
    ){
      {
        if(null==(head.val)){
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
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
    @Override boolean uncheckedremoveVal(RefSnglLnkNode<E> head
      ,Predicate<? super E> pred
    ){
      {
        if(pred.test(head.val)){
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
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
    @Override public boolean contains(Object val){
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
    private static class Itr<E> extends AbstractItr<E>{
      final CheckedStack<E> parent;
      transient int modCount;
      Itr(CheckedStack<E> parent){
        super(parent.head);
        this.parent=parent;
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
      @Override void uncheckedForEachRemaining(RefSnglLnkNode<E> next,Consumer<? super E> action){
        final int modCount=this.modCount;
        RefSnglLnkNode<E> prev,curr;
        try{
          curr=this.curr;
          do{
            action.accept(next.val);
            prev=curr;
          }while((next=(curr=next).next)!=null);
        }finally{
          CheckedCollection.checkModCount(modCount,this.parent.modCount);
        }
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void remove(){
        final RefSnglLnkNode<E> prev;
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
    public UncheckedStack(){
    }
    UncheckedStack(RefSnglLnkNode<E> head,int size){
      super(head,size);
    }
    @SuppressWarnings("unchecked")
    @Override public void readExternal(ObjectInput in) throws IOException
      ,ClassNotFoundException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        RefSnglLnkNode<E> curr;
        for(this.head=curr=new RefSnglLnkNode<E>((E)in.readObject());--size!=0;curr=curr.next=new RefSnglLnkNode<E>((E)in.readObject())){}
      }
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
        for(clone=new UncheckedStack<E>(newHead=new RefSnglLnkNode<E>(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new RefSnglLnkNode<E>(head.val)){}
        return clone;
      }
      return new UncheckedStack<E>();
    }
    @Override public E pop(){
      RefSnglLnkNode<E> head;
      this.head=(head=this.head).next;
      --this.size;
      return head.val;
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
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
            if(val!=null){
              return RefSnglLnkNode.uncheckedsearch(head,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return RefSnglLnkNode.uncheckedsearchNull(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
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
    @Override boolean uncheckedremoveIf(RefSnglLnkNode<E> head,Predicate<? super E> filter){
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
        RefSnglLnkNode<E> prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveValNonNull(RefSnglLnkNode<E> head
      ,Object nonNull
    ){
      {
        if(nonNull.equals(head.val)){
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
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
    @Override boolean uncheckedremoveValNull(RefSnglLnkNode<E> head
    ){
      {
        if(null==(head.val)){
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
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
    @Override boolean uncheckedremoveVal(RefSnglLnkNode<E> head
      ,Predicate<? super E> pred
    ){
      {
        if(pred.test(head.val)){
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
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
      @Override public void remove(){
        final UncheckedStack<E> parent;
        --(parent=UncheckedStack.this).size;
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
  public static class CheckedQueue<E> extends UncheckedQueue<E>{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedQueue(){
      super();
    }
    CheckedQueue(RefSnglLnkNode<E> head,int size,RefSnglLnkNode<E> tail){
      super(head,size,tail);
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public E element(){
      final RefSnglLnkNode<E> head;
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
          RefSnglLnkNode<E> curr;
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
    @Override public int hashCode(){
      final RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          return RefSnglLnkNode.uncheckedHashCode(head,tail);
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
          RefSnglLnkNode.uncheckedToString(head,tail,builder);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        return builder.append(']').toString();
      }
      return "[]";
    }
    @Override public void forEach(Consumer<? super E> action){
      final RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          RefSnglLnkNode.uncheckedForEach(head,tail,action);
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
      RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        RefSnglLnkNode<E> newHead=new RefSnglLnkNode<E>(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new RefSnglLnkNode<E>((head=head.next).val)){}
        return new CheckedQueue<E>(newHead,this.size,newTail);
      }
      return new CheckedQueue<E>();
    }
    @Override void push(E val){
      ++this.modCount;
      super.push(val);
    }
    @Override public E remove(){
      final RefSnglLnkNode<E> head;
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
      final RefSnglLnkNode<E> head;
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
    private void pullSurvivorsDown(RefSnglLnkNode<E> prev,Predicate<? super E> filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(RefSnglLnkNode<E> prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(RefSnglLnkNode<E> prev,Predicate<? super E> filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(RefSnglLnkNode<E> head,Predicate<? super E> filter){
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
    @Override boolean uncheckedremoveValNonNull(RefSnglLnkNode<E> head
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
          RefSnglLnkNode<E> prev;
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
    @Override boolean uncheckedremoveValNull(RefSnglLnkNode<E> head
    ){
      {
        final var tail=this.tail;
        if(null==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
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
    @Override boolean uncheckedremoveVal(RefSnglLnkNode<E> head
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
          RefSnglLnkNode<E> prev;
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
      final RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return RefSnglLnkNode.uncheckedcontainsNonNull(head,tail,val);
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
    private static class Itr<E> extends AbstractItr<E>{
      transient final CheckedQueue<E> parent;
      transient int modCount;
      Itr(CheckedQueue<E> parent){
        super(parent.head);
        this.parent=parent;
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
      @Override void uncheckedForEachRemaining(RefSnglLnkNode<E> next,Consumer<? super E> action){
        final int modCount=this.modCount;
        RefSnglLnkNode<E> prev,curr;
        final CheckedQueue<E> parent;
        final var tail=(parent=this.parent).tail;
        try{
          for(curr=this.curr;;next=curr.next){
            action.accept(next.val);
            prev=curr;
            if((curr=next)==tail){
              break;
            }
          }
        }finally{
          CheckedCollection.checkModCount(modCount,parent.modCount);
        }
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void remove(){
        final RefSnglLnkNode<E> prev;
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
    transient RefSnglLnkNode<E> tail;
    public UncheckedQueue(){
      super();
    }
    UncheckedQueue(RefSnglLnkNode<E> head,int size,RefSnglLnkNode<E> tail){
      super(head,size);
      this.tail=tail;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public void readExternal(ObjectInput in) throws IOException
      ,ClassNotFoundException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        RefSnglLnkNode<E> curr;
        for(this.head=curr=new RefSnglLnkNode<E>((E)in.readObject());--size!=0;curr=curr.next=new RefSnglLnkNode<E>((E)in.readObject())){}
        this.tail=curr;
      }
    }
    @Override public void clear(){
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    @Override public Object clone(){
      RefSnglLnkNode<E> head;
      if((head=this.head)!=null){
        RefSnglLnkNode<E> newHead=new RefSnglLnkNode<E>(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new RefSnglLnkNode<E>((head=head.next).val)){}
        return new UncheckedQueue<E>(newHead,this.size,newTail);
      }
      return new UncheckedQueue<E>();
    }
    @Override void push(E val){
      final var newNode=new RefSnglLnkNode<E>(val);
      final RefSnglLnkNode<E> tail;
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
      final RefSnglLnkNode<E> head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public E poll(){
      final RefSnglLnkNode<E> head;
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
    private int removeIfHelper(RefSnglLnkNode<E> prev,RefSnglLnkNode<E> tail,Predicate<? super E> filter){
      int numSurvivors=1;
      outer:for(RefSnglLnkNode<E> next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(RefSnglLnkNode<E> prev,RefSnglLnkNode<E> curr,RefSnglLnkNode<E> tail,Predicate<? super E> filter){
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
    @Override boolean uncheckedremoveIf(RefSnglLnkNode<E> head,Predicate<? super E> filter){
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
          final RefSnglLnkNode<E> prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveValNonNull(RefSnglLnkNode<E> head
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
          RefSnglLnkNode<E> prev;
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
    @Override boolean uncheckedremoveValNull(RefSnglLnkNode<E> head
    ){
      {
        final var tail=this.tail;
        if(null==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          RefSnglLnkNode<E> prev;
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
    @Override boolean uncheckedremoveVal(RefSnglLnkNode<E> head
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
          RefSnglLnkNode<E> prev;
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
      @Override public void remove(){
        final UncheckedQueue<E> parent;
        --(parent=UncheckedQueue.this).size;
        final RefSnglLnkNode<E> prev;
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
  private static class AbstractItr<E>
      implements OmniIterator.OfRef<E>
  {
    transient RefSnglLnkNode<E> prev;
    transient RefSnglLnkNode<E> curr;
    transient RefSnglLnkNode<E> next;
    AbstractItr(RefSnglLnkNode<E> next){
      this.next=next; 
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
    void uncheckedForEachRemaining(RefSnglLnkNode<E> next,Consumer<? super E> action){
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
  }
}
