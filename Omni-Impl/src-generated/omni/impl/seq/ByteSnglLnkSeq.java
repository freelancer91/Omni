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
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractByteItr;
import omni.api.OmniStack;
import omni.util.ByteSnglLnkNode;
public abstract class ByteSnglLnkSeq implements OmniCollection.OfByte,Cloneable{
  transient int size;
  transient ByteSnglLnkNode head;
  private ByteSnglLnkSeq(){
  }
  private ByteSnglLnkSeq(int size,ByteSnglLnkNode head){
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
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      return ByteSnglLnkNode.uncheckedHashCode(head);
    }
    return 1;
  }
  @Override public String toString(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/6)){(buffer=new byte[size*6])
        [size=ByteSnglLnkNode.uncheckedToString(head,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        ByteSnglLnkNode.uncheckedToString(head,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(ByteConsumer action){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      ByteSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Byte> action){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      ByteSnglLnkNode.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      ByteSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(byte val);
  @Override public boolean add(byte val)
  {
    push((val));
    return true;
  }
  public void push(Byte val){
    push((byte)val);
  }
  @Override public boolean add(Byte val)
  {
    push((byte)(val));
    return true;
  }
  @Override public boolean add(boolean val)
  {
    push((byte)TypeUtil.castToByte(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      ByteSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public byte[] toByteArray(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      final byte[] dst;
      ByteSnglLnkNode.uncheckedCopyInto(head,dst=new byte[this.size]);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public Byte[] toArray(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      final Byte[] dst;
      ByteSnglLnkNode.uncheckedCopyInto(head,dst=new Byte[this.size]);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      final double[] dst;
      ByteSnglLnkNode.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      final float[] dst;
      ByteSnglLnkNode.uncheckedCopyInto(head,dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      final long[] dst;
      ByteSnglLnkNode.uncheckedCopyInto(head,dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      final int[] dst;
      ByteSnglLnkNode.uncheckedCopyInto(head,dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      final short[] dst;
      ByteSnglLnkNode.uncheckedCopyInto(head,dst=new short[this.size]);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteSnglLnkNode.uncheckedcontains(head,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      if(val==(byte)val)
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if((v=(byte)val)==val)
            {
              return ByteSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ByteSnglLnkNode.uncheckedcontains(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      if(val<=Byte.MAX_VALUE)
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(byte)val)
      {
        {
          final ByteSnglLnkNode head;
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
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if((v=(byte)val)==val)
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
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
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
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
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
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
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
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      if(val<=Byte.MAX_VALUE)
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveVal(ByteSnglLnkNode head,int val);
  @Override public boolean removeIf(BytePredicate filter){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Byte> filter){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(ByteSnglLnkNode head,BytePredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,ByteSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(byte val){
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
      ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        ByteSnglLnkNode newHead;
        for(clone=new CheckedStack(this.size,newHead=new ByteSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new ByteSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public byte popByte(){
      ByteSnglLnkNode head;
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
    @Override public void forEach(ByteConsumer action){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ByteSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ByteSnglLnkNode.uncheckedForEach(head,action::accept);
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
    @Override boolean uncheckedremoveIf(ByteSnglLnkNode head,BytePredicate filter){
      final int modCount=this.modCount;
      try
      {
        int numLeft=this.size-1;
        if(filter.test(head.val)){
          while((head=head.next)!=null){
            --numLeft;
            if(!filter.test(head.val)){
              //TODO
              //this.size=ByteSnglLnkNode.retainSurvivors(head,filter,new ModCountChecker(modCount),numLeft);
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
          ByteSnglLnkNode prev;
          for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
            --numLeft;
            if(filter.test(head.val)){
              //TODO
              //this.size=numSurvivors+ByteSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter,new ModCountChecker(modCount),numLeft);
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
    @Override public byte pollByte(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public Byte poll(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Byte)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final ByteSnglLnkNode head;
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
      final ByteSnglLnkNode head;
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short pollShort(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(short)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(ByteSnglLnkNode head
      ,int val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          ByteSnglLnkNode prev;
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
    @Override public OmniIterator.OfByte iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractByteItr
    {
      transient final CheckedStack parent;
      transient int modCount;
      transient ByteSnglLnkNode prev;
      transient ByteSnglLnkNode curr;
      transient ByteSnglLnkNode next;
      Itr(CheckedStack parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public byte nextByte(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final ByteSnglLnkNode next;
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
      private void uncheckedForEachRemaining(ByteSnglLnkNode next,ByteConsumer action){
        final int modCount=this.modCount;
        ByteSnglLnkNode prev,curr;
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
      @Override public void forEachRemaining(ByteConsumer action){
        final ByteSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final ByteSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final ByteSnglLnkNode prev;
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
  public static class UncheckedStack extends ByteSnglLnkSeq implements OmniStack.OfByte{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,ByteSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(byte val){
      this.head=new ByteSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        ByteSnglLnkNode newHead;
        for(clone=new UncheckedStack(this.size,newHead=new ByteSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new ByteSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public byte popByte(){
      ByteSnglLnkNode head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(ByteSnglLnkNode head,BytePredicate filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=ByteSnglLnkNode.retainSurvivors(head,filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        ByteSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+ByteSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override public Byte pop(){
      return popByte();
    }
    @Override public byte peekByte(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollByte(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public Byte peek(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        return (Byte)(head.val);
      }
      return null;
    }
    @Override public Byte poll(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Byte)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double peekDouble(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double pollDouble(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float pollFloat(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        return (long)(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLong(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        return (int)(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollInt(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short peekShort(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        return (short)(head.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollShort(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(short)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(ByteSnglLnkNode head
      ,int val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          ByteSnglLnkNode prev;
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
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteSnglLnkNode.uncheckedsearch(head,TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(byte)val)
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if((v=(byte)val)==val)
            {
              return ByteSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final byte v;
            if(val==(v=(byte)val))
            {
              return ByteSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Byte){
                i=(byte)val;
              }else if(val instanceof Integer||val instanceof Short){
                if((i=((Number)val).intValue())!=(byte)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(byte)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(byte)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(byte)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Byte.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ByteSnglLnkNode.uncheckedsearch(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      if(val<=Byte.MAX_VALUE)
      {
        {
          final ByteSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ByteSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public OmniIterator.OfByte iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractByteItr
    {
      transient final ByteSnglLnkSeq parent;
      transient ByteSnglLnkNode prev;
      transient ByteSnglLnkNode curr;
      transient ByteSnglLnkNode next;
      Itr(ByteSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public byte nextByte(){
        final ByteSnglLnkNode next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(ByteSnglLnkNode next,ByteConsumer action){
        ByteSnglLnkNode prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(ByteConsumer action){
        final ByteSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        final ByteSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final ByteSnglLnkSeq parent;
        --(parent=this.parent).size;
        final ByteSnglLnkNode prev;
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
