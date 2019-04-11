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
import omni.util.HashUtil;
import omni.util.TypeUtil;
import omni.impl.AbstractDoubleItr;
import omni.api.OmniStack;
import omni.util.DoubleSnglLnkNode;
public abstract class DoubleSnglLnkSeq implements OmniCollection.OfDouble,Cloneable{
  transient int size;
  transient DoubleSnglLnkNode head;
  private DoubleSnglLnkSeq(){
  }
  private DoubleSnglLnkSeq(int size,DoubleSnglLnkNode head){
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
  @Override public boolean add(double val)
  {
    push((val));
    return true;
  }
  public void push(Double val){
    push((double)val);
  }
  @Override public boolean add(Double val)
  {
    push((double)(val));
    return true;
  }
  @Override public boolean add(boolean val)
  {
    push((double)TypeUtil.castToDouble(val));
    return true;
  }
  @Override public boolean add(int val)
  {
    push((double)(val));
    return true;
  }
  @Override public boolean add(char val)
  {
    push((double)(val));
    return true;
  }
  @Override public boolean add(short val)
  {
    push((double)(val));
    return true;
  }
  @Override public boolean add(long val)
  {
    push((double)(val));
    return true;
  }
  @Override public boolean add(float val)
  {
    push((double)(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final DoubleSnglLnkNode head;
    if((head=this.head)!=null){
      DoubleSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
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
            if(val)
            {
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
            if(val!=0)
            {
              {
                return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(val));
              }
            }
            else
            {
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
            if(val!=0)
            {
              if(TypeUtil.checkCastToDouble(val))
              {
                return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(val));
              }
            }
            else
            {
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
            if(val==val)
            {
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
            if(val==val)
            {
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
    @Override public boolean contains(byte val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleSnglLnkNode.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleSnglLnkNode.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return DoubleSnglLnkNode.uncheckedcontainsBits(head,Double.doubleToRawLongBits(val));
            }
            return DoubleSnglLnkNode.uncheckedcontains0(head);
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
            if(val)
            {
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
            if(val!=0)
            {
              {
                return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
              }
            }
            else
            {
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
            if(val!=0)
            {
              if(TypeUtil.checkCastToDouble(val))
              {
                return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
              }
            }
            else
            {
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
            if(val==val)
            {
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
            if(val==val)
            {
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
    @Override public boolean removeVal(byte val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0)
            {
              return uncheckedremoveValBits(head,Double.doubleToRawLongBits(val));
            }
            return uncheckedremoveVal0(head);
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
  abstract boolean uncheckedremoveIf(DoubleSnglLnkNode head,DoublePredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,DoubleSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(double val){
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
      DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        DoubleSnglLnkNode newHead;
        for(clone=new CheckedStack(this.size,newHead=new DoubleSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new DoubleSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public double popDouble(){
      DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        var ret=head.val;
        this.head=head.next;
        --this.size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return CheckedStack.this.modCount;
      }
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
    @Override boolean uncheckedremoveIf(DoubleSnglLnkNode head,DoublePredicate filter){
      final int modCount=this.modCount;
      try
      {
        int numLeft=this.size-1;
        if(filter.test(head.val)){
          while((head=head.next)!=null){
            --numLeft;
            if(!filter.test(head.val)){
              //TODO
              //this.size=DoubleSnglLnkNode.retainSurvivors(head,filter,new ModCountChecker(modCount),numLeft);
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
          DoubleSnglLnkNode prev;
          for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
            --numLeft;
            if(filter.test(head.val)){
              //TODO
              //this.size=numSurvivors+DoubleSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter,new ModCountChecker(modCount),numLeft);
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
        if(bits==Double.doubleToRawLongBits(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(bits!=Double.doubleToRawLongBits(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveVal0(DoubleSnglLnkNode head
      ){
        if(0==(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(0!=(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveValNaN(DoubleSnglLnkNode head
      ){
        if(Double.isNaN(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!Double.isNaN(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractDoubleItr
    {
      transient final CheckedStack parent;
      transient int modCount;
      transient DoubleSnglLnkNode prev;
      transient DoubleSnglLnkNode curr;
      transient DoubleSnglLnkNode next;
      Itr(CheckedStack parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
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
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(DoubleSnglLnkNode next,DoubleConsumer action){
        final int modCount=this.modCount;
        DoubleSnglLnkNode prev,curr;
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
      @Override public void remove(){
        final DoubleSnglLnkNode prev;
        if(this.curr!=(prev=this.prev)){
          final CheckedStack parent;
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
  public static class UncheckedStack extends DoubleSnglLnkSeq implements OmniStack.OfDouble{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,DoubleSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(double val){
      this.head=new DoubleSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        DoubleSnglLnkNode newHead;
        for(clone=new UncheckedStack(this.size,newHead=new DoubleSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new DoubleSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public double popDouble(){
      DoubleSnglLnkNode head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(DoubleSnglLnkNode head,DoublePredicate filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=DoubleSnglLnkNode.retainSurvivors(head,filter);
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
            this.size=numSurvivors+DoubleSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override public Double pop(){
      return popDouble();
    }
    @Override public double peekDouble(){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Double.NaN;
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
    @Override public Double peek(){
      final DoubleSnglLnkNode head;
      if((head=this.head)!=null){
        return (Double)(head.val);
      }
      return null;
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
    @Override boolean uncheckedremoveValBits(DoubleSnglLnkNode head
      ,long bits
      ){
        if(bits==Double.doubleToRawLongBits(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(bits!=Double.doubleToRawLongBits(head.val));
          }
          prev.next=head.next;
        }
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveVal0(DoubleSnglLnkNode head
      ){
        if(0==(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(0!=(head.val));
          }
          prev.next=head.next;
        }
        --this.size;
        return true;
      }
    @Override boolean uncheckedremoveValNaN(DoubleSnglLnkNode head
      ){
        if(Double.isNaN(head.val)){
          this.head=head.next;
        }else{
          DoubleSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(!Double.isNaN(head.val));
          }
          prev.next=head.next;
        }
        --this.size;
        return true;
      }
    @Override public int search(boolean val){
      {
        {
          final DoubleSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val)
            {
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
            if(val!=0)
            {
              {
                return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
              }
            }
            else
            {
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
            if(val!=0)
            {
              if(TypeUtil.checkCastToDouble(val))
              {
                return DoubleSnglLnkNode.uncheckedsearchBits(head,Double.doubleToRawLongBits(val));
              }
            }
            else
            {
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
            if(val==val)
            {
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
            if(val==val)
            {
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
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractDoubleItr
    {
      transient final DoubleSnglLnkSeq parent;
      transient DoubleSnglLnkNode prev;
      transient DoubleSnglLnkNode curr;
      transient DoubleSnglLnkNode next;
      Itr(DoubleSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
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
      private void uncheckedForEachRemaining(DoubleSnglLnkNode next,DoubleConsumer action){
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
      @Override public void remove(){
        final DoubleSnglLnkSeq parent;
        --(parent=this.parent).size;
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
}
