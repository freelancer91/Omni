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
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractDoubleItr;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.impl.DoubleSnglLnkNode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
public abstract class DoubleSnglLnkSeq extends 
AbstractSeq<Double>
 implements OmniCollection.OfDouble,Externalizable{
  private static final long serialVersionUID=1L;
  transient DoubleSnglLnkNode head;
  private DoubleSnglLnkSeq(){
  }
  private DoubleSnglLnkSeq(DoubleSnglLnkNode head,int size){
    super(size);
    this.head=head;
  }
  private static  void pullSurvivorsDown(DoubleSnglLnkNode prev,DoublePredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
  private static  int markSurvivors(DoubleSnglLnkNode curr,DoublePredicate filter,long[] survivorSet){
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
  private static  void pullSurvivorsDown(DoubleSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
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
  private static  long markSurvivors(DoubleSnglLnkNode curr,DoublePredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  private static  int retainSurvivors(DoubleSnglLnkNode prev, final DoublePredicate filter){
    int numSurvivors=1;
    outer:for(DoubleSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  private static  int retainTrailingSurvivors(DoubleSnglLnkNode prev,DoubleSnglLnkNode curr,final DoublePredicate filter){
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
        out.writeDouble(curr.val);
      }
      while((curr=curr.next)!=null);
    }
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
  }
  @Override public int hashCode(){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      return DoubleSnglLnkNode.uncheckedHashCode(head);
    }
    return 1;
  }
  @Override public String toString(){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      final StringBuilder builder=new StringBuilder("[");
      DoubleSnglLnkNode.uncheckedToString(head,builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public void forEach(DoubleConsumer action){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      DoubleSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Double> action){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      DoubleSnglLnkNode.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      DoubleSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(double val);
  @Override public boolean add(double val){
    push((val));
    return true;
  }
  public void push(Double val){
    push((double)val);
  }
  @Override public boolean add(Double val){
    push((double)(val));
    return true;
  }
  @Override public boolean add(boolean val){
    push((double)TypeUtil.castToDouble(val));
    return true;
  }
  @Override public boolean add(int val){
    push((double)(val));
    return true;
  }
  @Override public boolean add(long val){
    push((double)(val));
    return true;
  }
  @Override public boolean add(float val){
    push((double)(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      DoubleSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else if(arr.length!=0){
      arr[0]=null;
    }
    return arr;
  }
  @Override public double[] toDoubleArray(){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      final double[] dst;
      DoubleSnglLnkNode.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public Double[] toArray(){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      final Double[] dst;
      DoubleSnglLnkNode.uncheckedCopyInto(head,dst=new Double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return DoubleSnglLnkNode.uncheckedcontainsBits(head,TypeUtil.DBL_TRUE_BITS);
            }
            return DoubleSnglLnkNode.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              {
                return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleSnglLnkNode.uncheckedcontains0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleSnglLnkNode.uncheckedcontains0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleSnglLnkNode.uncheckedcontainsNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleSnglLnkNode.uncheckedcontainsNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(d));
                }
                return DoubleSnglLnkNode.uncheckedcontainsNaN(head);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(f));
                }
                return DoubleSnglLnkNode.uncheckedcontainsNaN(head);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(i));
                }
                return DoubleSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(l));
                }
                return DoubleSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(i));
                }
                return DoubleSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return DoubleSnglLnkNode.uncheckedcontainsBits(head,TypeUtil.DBL_TRUE_BITS);
                }
                return DoubleSnglLnkNode.uncheckedcontains0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return uncheckedremoveValBits(head,TypeUtil.DBL_TRUE_BITS);
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              {
                return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveValNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveValNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return uncheckedremoveValBits(head,Double.doubleToRawLongBits(d));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveValBits(head,Double.doubleToRawLongBits(f));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return uncheckedremoveValBits(head,Double.doubleToRawLongBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Double.doubleToRawLongBits(l));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveValBits(head,Double.doubleToRawLongBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveValBits(head,TypeUtil.DBL_TRUE_BITS);
                }
                return uncheckedremoveVal0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveValBits(DoubleSnglLnkNode head,long bits);
  abstract boolean uncheckedremoveVal0(DoubleSnglLnkNode head);
  abstract boolean uncheckedremoveValNaN(DoubleSnglLnkNode head);
  @Override public boolean removeIf(DoublePredicate filter){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Double> filter){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  public double peekDouble(){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      return (double)(head.val);
    }
    return Double.NaN;
  }
  public Double peek(){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      return (Double)(head.val);
    }
    return null;
  }
  abstract boolean uncheckedremoveIf(DoubleSnglLnkNode head,DoublePredicate filter);
  public static class CheckedStack extends UncheckedStack{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(){
    }
    CheckedStack(DoubleSnglLnkNode head,int size){
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
    @Override public void push(double val){
      ++this.modCount;
      super.push(val);
    }
    @Override public boolean equals(Object val){
      //TODO
                  throw omni.util.NotYetImplementedException.getNYI();
    }
    @Override public void clear(){
     if(size!=0){
       ++this.modCount;
       this.size=0;
       this.head=null;
     }
    }
    @Override public Object clone(){
      DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        DoubleSnglLnkNode newHead;
        for(clone=new CheckedStack(newHead=new DoubleSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new DoubleSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public double popDouble(){
      DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void forEach(DoubleConsumer action){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          DoubleSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Double> action){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          DoubleSnglLnkNode.uncheckedForEach(head,action::accept);
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
    private int removeIfHelper(DoubleSnglLnkNode prev,DoublePredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(DoubleSnglLnkNode head,DoublePredicate filter){
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
    @Override public double pollDouble(){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Double.NaN;
    }
    @Override public Double poll(){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Double)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override boolean uncheckedremoveValBits(DoubleSnglLnkNode head
      ,long bits
    ){
      {
        if(bits==Double.doubleToRawLongBits(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(bits!=Double.doubleToRawLongBits(head.val));
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveVal0(DoubleSnglLnkNode head
    ){
      {
        if(0==(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(0!=(head.val));
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveValNaN(DoubleSnglLnkNode head
    ){
      {
        if(Double.isNaN(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(!Double.isNaN(head.val));
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    private static class Itr extends AbstractItr{
      final CheckedStack parent;
      transient int modCount;
      Itr(CheckedStack parent){
        super(parent.head);
        this.parent=parent;
        this.modCount=parent.modCount;
      }
      Itr(Itr itr){
        super(itr);
        this.parent=itr.parent;
        this.modCount=itr.modCount;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public double nextDouble(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final DoubleSnglLnkNode next;
        if((next=this.next)!=null){
          this.next=next.next;
          this.prev=this.curr;
          this.curr=next;
          return next.val;
        }
        throw new NoSuchElementException();
      }
      @Override void uncheckedForEachRemaining(final DoubleSnglLnkNode expectedNext,DoubleConsumer action){
        final int modCount=this.modCount;
        DoubleSnglLnkNode prev,curr,next;
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
        final DoubleSnglLnkNode prev;
        if(this.curr!=(prev=this.prev)){
          final CheckedStack parent;
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
  public static class UncheckedStack extends DoubleSnglLnkSeq implements OmniStack.OfDouble{
    private static final long serialVersionUID=1L;
    public UncheckedStack(){
    }
    UncheckedStack(DoubleSnglLnkNode head,int size){
      super(head,size);
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        DoubleSnglLnkNode curr;
        for(this.head=curr=new DoubleSnglLnkNode((double)in.readDouble());--size!=0;curr=curr.next=new DoubleSnglLnkNode((double)in.readDouble())){}
      }
    }
    @Override public void push(double val){
      this.head=new DoubleSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
                  throw omni.util.NotYetImplementedException.getNYI();
    }
    @Override public Object clone(){
      DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        DoubleSnglLnkNode newHead;
        for(clone=new UncheckedStack(newHead=new DoubleSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new DoubleSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public double popDouble(){
      DoubleSnglLnkNode head;
      this.head=(head=this.head).next;
      --this.size;
      return head.val;
    }
    @Override public int search(boolean val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return DoubleSnglLnkNode.uncheckedsearchBits(head,TypeUtil.DBL_TRUE_BITS);
            }
            return DoubleSnglLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              {
                return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleSnglLnkNode.uncheckedsearch0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
              }
            }else{
              return DoubleSnglLnkNode.uncheckedsearch0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleSnglLnkNode.uncheckedsearchNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleSnglLnkNode.uncheckedsearchNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(d));
                }
                return DoubleSnglLnkNode.uncheckedsearchNaN(head);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(f));
                }
                return DoubleSnglLnkNode.uncheckedsearchNaN(head);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(i));
                }
                return DoubleSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(l));
                }
                return DoubleSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(i));
                }
                return DoubleSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return DoubleSnglLnkNode.uncheckedsearchBits(head,TypeUtil.DBL_TRUE_BITS);
                }
                return DoubleSnglLnkNode.uncheckedsearch0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public double pollDouble(){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public Double pop(){
      return popDouble();
    }
    @Override public Double poll(){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override boolean uncheckedremoveIf(DoubleSnglLnkNode head,DoublePredicate filter){
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
        DoubleSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveValBits(DoubleSnglLnkNode head
      ,long bits
    ){
      {
        if(bits==Double.doubleToRawLongBits(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(bits!=Double.doubleToRawLongBits(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveVal0(DoubleSnglLnkNode head
    ){
      {
        if(0==(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(0!=(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveValNaN(DoubleSnglLnkNode head
    ){
      {
        if(Double.isNaN(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(!Double.isNaN(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr();
    }
    private class Itr extends AbstractItr{
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
        final UncheckedStack parent;
        --(parent=UncheckedStack.this).size;
        final DoubleSnglLnkNode prev;
        if((prev=this.prev)==null){
          parent.head=next;
        }else{
          prev.next=next;
        }
        this.curr=prev;
      }
    }
  }
  public static class CheckedQueue extends UncheckedQueue{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedQueue(){
      super();
    }
    CheckedQueue(DoubleSnglLnkNode head,int size,DoubleSnglLnkNode tail){
      super(head,size,tail);
    }
    @Override public boolean equals(Object val){
      //TODO
                  throw omni.util.NotYetImplementedException.getNYI();
    }
    @Override public double doubleElement(){
      final DoubleSnglLnkNode head;
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
          DoubleSnglLnkNode curr;
          for(curr=this.head;;curr=curr.next){
            out.writeDouble(curr.val);
            if(curr==tail){
              return;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void forEach(DoubleConsumer action){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          DoubleSnglLnkNode.uncheckedForEach(head,tail,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Double> action){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          DoubleSnglLnkNode.uncheckedForEach(head,tail,action::accept);
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
      DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        DoubleSnglLnkNode newHead=new DoubleSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new DoubleSnglLnkNode((head=head.next).val)){}
        return new CheckedQueue(newHead,this.size,newTail);
      }
      return new CheckedQueue();
    }
    @Override void push(double val){
      ++this.modCount;
      super.push(val);
    }
    @Override public double removeDouble(){
      final DoubleSnglLnkNode head;
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
    @Override public double pollDouble(){
      final DoubleSnglLnkNode head;
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
      return Double.NaN;
    }
    @Override public Double poll(){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Double)(head.val);
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
    private void pullSurvivorsDown(DoubleSnglLnkNode prev,DoublePredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(DoubleSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(DoubleSnglLnkNode prev,DoublePredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(DoubleSnglLnkNode head,DoublePredicate filter){
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
    @Override boolean uncheckedremoveValBits(DoubleSnglLnkNode head
    ,long bits
    ){
      {
        final var tail=this.tail;
        if(bits==Double.doubleToRawLongBits(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(bits!=Double.doubleToRawLongBits((head=(prev=head).next).val));
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
    @Override boolean uncheckedremoveVal0(DoubleSnglLnkNode head
    ){
      {
        final var tail=this.tail;
        if(0==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(0!=((head=(prev=head).next).val));
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
    @Override boolean uncheckedremoveValNaN(DoubleSnglLnkNode head
    ){
      {
        final var tail=this.tail;
        if(Double.isNaN(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(!Double.isNaN((head=(prev=head).next).val));
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
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    private static class Itr extends AbstractItr{
      transient final CheckedQueue parent;
      transient int modCount;
      Itr(CheckedQueue parent){
        super(parent.head);
        this.parent=parent;
        this.modCount=parent.modCount;
      }
      Itr(Itr itr){
        super(itr);
        this.parent=itr.parent;
        this.modCount=itr.modCount;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public double nextDouble(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final DoubleSnglLnkNode next;
        if((next=this.next)!=null){
          this.next=next.next;
          this.prev=this.curr;
          this.curr=next;
          return next.val;
        }
        throw new NoSuchElementException();
      }
      @Override void uncheckedForEachRemaining(final DoubleSnglLnkNode expectedNext,DoubleConsumer action){
        final int modCount=this.modCount;
        DoubleSnglLnkNode prev,curr,next;
        final CheckedQueue parent;
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
        final DoubleSnglLnkNode prev;
        if(this.curr!=(prev=this.prev)){
          final CheckedQueue parent;
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
  public static class UncheckedQueue extends DoubleSnglLnkSeq implements OmniQueue.OfDouble{
    private static final long serialVersionUID=1L;
    transient DoubleSnglLnkNode tail;
    public UncheckedQueue(){
      super();
    }
    UncheckedQueue(DoubleSnglLnkNode head,int size,DoubleSnglLnkNode tail){
      super(head,size);
      this.tail=tail;
    }
    @Override public boolean equals(Object val){
      //TODO
                  throw omni.util.NotYetImplementedException.getNYI();
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        DoubleSnglLnkNode curr;
        for(this.head=curr=new DoubleSnglLnkNode((double)in.readDouble());--size!=0;curr=curr.next=new DoubleSnglLnkNode((double)in.readDouble())){}
        this.tail=curr;
      }
    }
    @Override public void clear(){
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    @Override public Object clone(){
      DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        DoubleSnglLnkNode newHead=new DoubleSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new DoubleSnglLnkNode((head=head.next).val)){}
        return new UncheckedQueue(newHead,this.size,newTail);
      }
      return new UncheckedQueue();
    }
    @Override void push(double val){
      final var newNode=new DoubleSnglLnkNode(val);
      final DoubleSnglLnkNode tail;
      if((tail=this.tail)!=null){
        tail.next=newNode;
      }else{
        this.head=newNode;
      }
      this.tail=newNode;
      ++this.size;
    }
    @Override public double doubleElement(){
      return head.val;
    }
    @Override public boolean offer(double val){
      push(val);
      return true;
    }
    @Override public double removeDouble(){
      final DoubleSnglLnkNode head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public double pollDouble(){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public Double remove(){
      return removeDouble();
    }
    @Override public Double element(){
      return doubleElement();
    }
    @Override public boolean offer(Double val){
      push((double)val);
      return true;
    }
    @Override public Double poll(){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Double)(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    private int removeIfHelper(DoubleSnglLnkNode prev,DoubleSnglLnkNode tail,DoublePredicate filter){
      int numSurvivors=1;
      outer:for(DoubleSnglLnkNode next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(DoubleSnglLnkNode prev,DoubleSnglLnkNode curr,DoubleSnglLnkNode tail,DoublePredicate filter){
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
    @Override boolean uncheckedremoveIf(DoubleSnglLnkNode head,DoublePredicate filter){
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
          final DoubleSnglLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveValBits(DoubleSnglLnkNode head
    ,long bits
    ){
      {
        final var tail=this.tail;
        if(bits==Double.doubleToRawLongBits(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(bits!=Double.doubleToRawLongBits((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveVal0(DoubleSnglLnkNode head
    ){
      {
        final var tail=this.tail;
        if(0==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(0!=((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveValNaN(DoubleSnglLnkNode head
    ){
      {
        final var tail=this.tail;
        if(Double.isNaN(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(!Double.isNaN((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr();
    }
    private class Itr extends AbstractItr{
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
        final UncheckedQueue parent;
        --(parent=UncheckedQueue.this).size;
        final DoubleSnglLnkNode prev;
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
  private abstract static class AbstractItr
      extends AbstractDoubleItr
  {
    transient DoubleSnglLnkNode prev;
    transient DoubleSnglLnkNode curr;
    transient DoubleSnglLnkNode next;
    AbstractItr(AbstractItr itr){
      this.prev=itr.prev;
      this.curr=itr.curr;
      this.next=itr.next;
    }
    AbstractItr(DoubleSnglLnkNode next){
      this.next=next; 
    }
    @Override public double nextDouble(){
      final DoubleSnglLnkNode next;
      this.next=(next=this.next).next;
      this.prev=this.curr;
      this.curr=next;
      return next.val;
    }
    @Override public boolean hasNext(){
      return next!=null;
    }
    void uncheckedForEachRemaining(DoubleSnglLnkNode next,DoubleConsumer action){
      DoubleSnglLnkNode prev,curr=this.curr;
      do{
        action.accept(next.val);
        prev=curr;
      }while((next=(curr=next).next)!=null);
      this.prev=prev;
      this.curr=curr;
      this.next=null;
    }
    @Override public void forEachRemaining(DoubleConsumer action){
      final DoubleSnglLnkNode next;
      if((next=this.next)!=null){
        uncheckedForEachRemaining(next,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Double> action){
      final DoubleSnglLnkNode next;
      if((next=this.next)!=null){
        uncheckedForEachRemaining(next,action::accept);
      }
    }
  }
}
