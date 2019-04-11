package omni.impl.seq;
import java.util.function.IntFunction;
import omni.api.OmniIterator;
import omni.api.OmniCollection;
import omni.impl.CheckedCollection;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import omni.util.ToStringUtil;
import omni.util.OmniArray;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractLongItr;
import omni.api.OmniStack;
import omni.util.LongSnglLnkNode;
public abstract class LongSnglLnkSeq implements OmniCollection.OfLong,Cloneable{
  transient int size;
  transient LongSnglLnkNode head;
  private LongSnglLnkSeq(){
  }
  private LongSnglLnkSeq(int size,LongSnglLnkNode head){
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
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      return LongSnglLnkNode.uncheckedHashCode(head);
    }
    return 1;
  }
  @Override public String toString(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/22)){(buffer=new byte[size*22])
        [size=LongSnglLnkNode.uncheckedToString(head,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        LongSnglLnkNode.uncheckedToString(head,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(LongConsumer action){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      LongSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Long> action){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      LongSnglLnkNode.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      LongSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(long val);
  @Override public boolean add(long val)
  {
    push((val));
    return true;
  }
  public void push(Long val){
    push((long)val);
  }
  @Override public boolean add(Long val)
  {
    push((long)(val));
    return true;
  }
  @Override public boolean add(boolean val)
  {
    push((long)TypeUtil.castToLong(val));
    return true;
  }
  @Override public boolean add(int val)
  {
    push((long)(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      LongSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public long[] toLongArray(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      final long[] dst;
      LongSnglLnkNode.uncheckedCopyInto(head,dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public Long[] toArray(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      final Long[] dst;
      LongSnglLnkNode.uncheckedCopyInto(head,dst=new Long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      final double[] dst;
      LongSnglLnkNode.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      final float[] dst;
      LongSnglLnkNode.uncheckedCopyInto(head,dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedcontains(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return LongSnglLnkNode.uncheckedcontains(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveVal(LongSnglLnkNode head,long val);
  @Override public boolean removeIf(LongPredicate filter){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Long> filter){
    final LongSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(LongSnglLnkNode head,LongPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,LongSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(long val){
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
      LongSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        LongSnglLnkNode newHead;
        for(clone=new CheckedStack(this.size,newHead=new LongSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new LongSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public long popLong(){
      LongSnglLnkNode head;
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
    @Override public void forEach(LongConsumer action){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Long> action){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          LongSnglLnkNode.uncheckedForEach(head,action::accept);
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
    @Override boolean uncheckedremoveIf(LongSnglLnkNode head,LongPredicate filter){
      final int modCount=this.modCount;
      try
      {
        int numLeft=this.size-1;
        if(filter.test(head.val)){
          while((head=head.next)!=null){
            --numLeft;
            if(!filter.test(head.val)){
              //TODO
              //this.size=LongSnglLnkNode.retainSurvivors(head,filter,new ModCountChecker(modCount),numLeft);
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
          LongSnglLnkNode prev;
          for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
            --numLeft;
            if(filter.test(head.val)){
              //TODO
              //this.size=numSurvivors+LongSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter,new ModCountChecker(modCount),numLeft);
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
    @Override public long pollLong(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public Long poll(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Long)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Float.NaN;
    }
    @Override boolean uncheckedremoveVal(LongSnglLnkNode head
      ,long val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          LongSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(val!=(head.val));
          }
          prev.next=head.next;
        }
        this.modCount=modCount+1;
        --this.size;
        return true;
      }
    @Override public OmniIterator.OfLong iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractLongItr
    {
      transient final CheckedStack parent;
      transient int modCount;
      transient LongSnglLnkNode prev;
      transient LongSnglLnkNode curr;
      transient LongSnglLnkNode next;
      Itr(CheckedStack parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public long nextLong(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final LongSnglLnkNode next;
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
      private void uncheckedForEachRemaining(LongSnglLnkNode next,LongConsumer action){
        final int modCount=this.modCount;
        LongSnglLnkNode prev,curr;
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
      @Override public void forEachRemaining(LongConsumer action){
        final LongSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final LongSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final LongSnglLnkNode prev;
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
  public static class UncheckedStack extends LongSnglLnkSeq implements OmniStack.OfLong{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,LongSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(long val){
      this.head=new LongSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      LongSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        LongSnglLnkNode newHead;
        for(clone=new UncheckedStack(this.size,newHead=new LongSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new LongSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public long popLong(){
      LongSnglLnkNode head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(LongSnglLnkNode head,LongPredicate filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=LongSnglLnkNode.retainSurvivors(head,filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        LongSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+LongSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override public Long pop(){
      return popLong();
    }
    @Override public long peekLong(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLong(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public Long peek(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        return (Long)(head.val);
      }
      return null;
    }
    @Override public Long poll(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Long)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double peekDouble(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double pollDouble(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float pollFloat(){
      final LongSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override boolean uncheckedremoveVal(LongSnglLnkNode head
      ,long val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          LongSnglLnkNode prev;
          {
            do{
              if((head=(prev=head).next)==null){
                return false;
              }
            }while(val!=(head.val));
          }
          prev.next=head.next;
        }
        --this.size;
        return true;
      }
    @Override public int search(boolean val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedsearch(head,TypeUtil.castToLong(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return LongSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.floatEquals(val,v=(long)val))
            {
              return LongSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final long v;
            if(TypeUtil.doubleEquals(val,v=(long)val))
            {
              return LongSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final LongSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final long l;
              if(val instanceof Long||val instanceof Integer||val instanceof Byte||val instanceof Short){
                l=((Number)val).longValue();
              }else if(val instanceof Float){
                final float f;
                if(!TypeUtil.floatEquals(f=(float)val,l=(long)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if(!TypeUtil.doubleEquals(d=(double)val,l=(long)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                l=(char)val;
              }else if(val instanceof Boolean){
                l=TypeUtil.castToLong((boolean)val);
              }else{
                break returnFalse;
              }
              return LongSnglLnkNode.uncheckedsearch(head,l);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public OmniIterator.OfLong iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractLongItr
    {
      transient final LongSnglLnkSeq parent;
      transient LongSnglLnkNode prev;
      transient LongSnglLnkNode curr;
      transient LongSnglLnkNode next;
      Itr(LongSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public long nextLong(){
        final LongSnglLnkNode next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(LongSnglLnkNode next,LongConsumer action){
        LongSnglLnkNode prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(LongConsumer action){
        final LongSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Long> action){
        final LongSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final LongSnglLnkSeq parent;
        --(parent=this.parent).size;
        final LongSnglLnkNode prev;
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
