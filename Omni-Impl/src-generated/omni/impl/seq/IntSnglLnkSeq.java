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
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractIntItr;
import omni.api.OmniStack;
import omni.util.IntSnglLnkNode;
public abstract class IntSnglLnkSeq implements OmniCollection.OfInt,Cloneable{
  transient int size;
  transient IntSnglLnkNode head;
  private IntSnglLnkSeq(){
  }
  private IntSnglLnkSeq(int size,IntSnglLnkNode head){
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
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      return IntSnglLnkNode.uncheckedHashCode(head);
    }
    return 1;
  }
  @Override public String toString(){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/13)){(buffer=new byte[size*13])
        [size=IntSnglLnkNode.uncheckedToString(head,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        IntSnglLnkNode.uncheckedToString(head,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(IntConsumer action){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      IntSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Integer> action){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      IntSnglLnkNode.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      IntSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(int val);
  @Override public boolean add(int val)
  {
    push((val));
    return true;
  }
  public void push(Integer val){
    push((int)val);
  }
  @Override public boolean add(Integer val)
  {
    push((int)(val));
    return true;
  }
  @Override public boolean add(boolean val)
  {
    push((int)(int)TypeUtil.castToByte(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      IntSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public int[] toIntArray(){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      final int[] dst;
      IntSnglLnkNode.uncheckedCopyInto(head,dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public Integer[] toArray(){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      final Integer[] dst;
      IntSnglLnkNode.uncheckedCopyInto(head,dst=new Integer[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      final double[] dst;
      IntSnglLnkNode.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      final float[] dst;
      IntSnglLnkNode.uncheckedCopyInto(head,dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      final long[] dst;
      IntSnglLnkNode.uncheckedCopyInto(head,dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return IntSnglLnkNode.uncheckedcontains(head,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return IntSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((v=(int)val)==val)
            {
              return IntSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return IntSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return IntSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return IntSnglLnkNode.uncheckedcontains(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return IntSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return IntSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final IntSnglLnkNode head;
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
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((v=(int)val)==val)
            {
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
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
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if(val==(v=(int)val))
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
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final IntSnglLnkNode head;
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
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveVal(IntSnglLnkNode head,int val);
  @Override public boolean removeIf(IntPredicate filter){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Integer> filter){
    final IntSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(IntSnglLnkNode head,IntPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,IntSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(int val){
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
      IntSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        IntSnglLnkNode newHead;
        for(clone=new CheckedStack(this.size,newHead=new IntSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new IntSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public int popInt(){
      IntSnglLnkNode head;
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
    @Override public void forEach(IntConsumer action){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          IntSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Integer> action){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          IntSnglLnkNode.uncheckedForEach(head,action::accept);
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
    @Override boolean uncheckedremoveIf(IntSnglLnkNode head,IntPredicate filter){
      final int modCount=this.modCount;
      try
      {
        int numLeft=this.size-1;
        if(filter.test(head.val)){
          while((head=head.next)!=null){
            --numLeft;
            if(!filter.test(head.val)){
              //TODO
              //this.size=IntSnglLnkNode.retainSurvivors(head,filter,new ModCountChecker(modCount),numLeft);
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
          IntSnglLnkNode prev;
          for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
            --numLeft;
            if(filter.test(head.val)){
              //TODO
              //this.size=numSurvivors+IntSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter,new ModCountChecker(modCount),numLeft);
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
    @Override public int pollInt(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public Integer poll(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Integer)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final IntSnglLnkNode head;
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
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(IntSnglLnkNode head
      ,int val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          IntSnglLnkNode prev;
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
    @Override public OmniIterator.OfInt iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractIntItr
    {
      transient final CheckedStack parent;
      transient int modCount;
      transient IntSnglLnkNode prev;
      transient IntSnglLnkNode curr;
      transient IntSnglLnkNode next;
      Itr(CheckedStack parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public int nextInt(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final IntSnglLnkNode next;
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
      private void uncheckedForEachRemaining(IntSnglLnkNode next,IntConsumer action){
        final int modCount=this.modCount;
        IntSnglLnkNode prev,curr;
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
      @Override public void forEachRemaining(IntConsumer action){
        final IntSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final IntSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final IntSnglLnkNode prev;
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
  public static class UncheckedStack extends IntSnglLnkSeq implements OmniStack.OfInt{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,IntSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(int val){
      this.head=new IntSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      IntSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        IntSnglLnkNode newHead;
        for(clone=new UncheckedStack(this.size,newHead=new IntSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new IntSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public int popInt(){
      IntSnglLnkNode head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(IntSnglLnkNode head,IntPredicate filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=IntSnglLnkNode.retainSurvivors(head,filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        IntSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+IntSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override public Integer pop(){
      return popInt();
    }
    @Override public int peekInt(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollInt(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public Integer peek(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        return (Integer)(head.val);
      }
      return null;
    }
    @Override public Integer poll(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Integer)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double peekDouble(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double pollDouble(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float pollFloat(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        return (long)(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLong(){
      final IntSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(IntSnglLnkNode head
      ,int val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          IntSnglLnkNode prev;
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
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return IntSnglLnkNode.uncheckedsearch(head,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return IntSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((v=(int)val)==val)
            {
              return IntSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return IntSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return IntSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final IntSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return IntSnglLnkNode.uncheckedsearch(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public OmniIterator.OfInt iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractIntItr
    {
      transient final IntSnglLnkSeq parent;
      transient IntSnglLnkNode prev;
      transient IntSnglLnkNode curr;
      transient IntSnglLnkNode next;
      Itr(IntSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public int nextInt(){
        final IntSnglLnkNode next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(IntSnglLnkNode next,IntConsumer action){
        IntSnglLnkNode prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(IntConsumer action){
        final IntSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final IntSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final IntSnglLnkSeq parent;
        --(parent=this.parent).size;
        final IntSnglLnkNode prev;
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
