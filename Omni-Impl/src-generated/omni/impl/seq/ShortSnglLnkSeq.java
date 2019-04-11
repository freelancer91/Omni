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
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractShortItr;
import omni.api.OmniStack;
import omni.util.ShortSnglLnkNode;
public abstract class ShortSnglLnkSeq implements OmniCollection.OfShort,Cloneable{
  transient int size;
  transient ShortSnglLnkNode head;
  private ShortSnglLnkSeq(){
  }
  private ShortSnglLnkSeq(int size,ShortSnglLnkNode head){
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
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      return ShortSnglLnkNode.uncheckedHashCode(head);
    }
    return 1;
  }
  @Override public String toString(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE>>3)){(buffer=new byte[size<<3])
        [size=ShortSnglLnkNode.uncheckedToString(head,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        ShortSnglLnkNode.uncheckedToString(head,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(ShortConsumer action){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      ShortSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Short> action){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      ShortSnglLnkNode.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      ShortSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(short val);
  @Override public boolean add(short val)
  {
    push((val));
    return true;
  }
  public void push(Short val){
    push((short)val);
  }
  @Override public boolean add(Short val)
  {
    push((short)(val));
    return true;
  }
  @Override public boolean add(boolean val)
  {
    push((short)(short)TypeUtil.castToByte(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      ShortSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public short[] toShortArray(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      final short[] dst;
      ShortSnglLnkNode.uncheckedCopyInto(head,dst=new short[this.size]);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public Short[] toArray(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      final Short[] dst;
      ShortSnglLnkNode.uncheckedCopyInto(head,dst=new Short[this.size]);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      final double[] dst;
      ShortSnglLnkNode.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      final float[] dst;
      ShortSnglLnkNode.uncheckedCopyInto(head,dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      final long[] dst;
      ShortSnglLnkNode.uncheckedCopyInto(head,dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      final int[] dst;
      ShortSnglLnkNode.uncheckedCopyInto(head,dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortSnglLnkNode.uncheckedcontains(head,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      if(val==(short)val)
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if((v=(short)val)==val)
            {
              return ShortSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ShortSnglLnkNode.uncheckedcontains(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      if(val<=Short.MAX_VALUE)
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(short)val)
      {
        {
          final ShortSnglLnkNode head;
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
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if((v=(short)val)==val)
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
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
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
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
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
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      if(val<=Short.MAX_VALUE)
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveVal(ShortSnglLnkNode head,int val);
  @Override public boolean removeIf(ShortPredicate filter){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Short> filter){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(ShortSnglLnkNode head,ShortPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,ShortSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(short val){
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
      ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        ShortSnglLnkNode newHead;
        for(clone=new CheckedStack(this.size,newHead=new ShortSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new ShortSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public short popShort(){
      ShortSnglLnkNode head;
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
    @Override public void forEach(ShortConsumer action){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ShortSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Short> action){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ShortSnglLnkNode.uncheckedForEach(head,action::accept);
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
    @Override boolean uncheckedremoveIf(ShortSnglLnkNode head,ShortPredicate filter){
      final int modCount=this.modCount;
      try
      {
        int numLeft=this.size-1;
        if(filter.test(head.val)){
          while((head=head.next)!=null){
            --numLeft;
            if(!filter.test(head.val)){
              //TODO
              //this.size=ShortSnglLnkNode.retainSurvivors(head,filter,new ModCountChecker(modCount),numLeft);
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
          ShortSnglLnkNode prev;
          for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
            --numLeft;
            if(filter.test(head.val)){
              //TODO
              //this.size=numSurvivors+ShortSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter,new ModCountChecker(modCount),numLeft);
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
    @Override public short pollShort(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public Short poll(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Short)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final ShortSnglLnkNode head;
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
      final ShortSnglLnkNode head;
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
      final ShortSnglLnkNode head;
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
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(ShortSnglLnkNode head
      ,int val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          ShortSnglLnkNode prev;
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
    @Override public OmniIterator.OfShort iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractShortItr
    {
      transient final CheckedStack parent;
      transient int modCount;
      transient ShortSnglLnkNode prev;
      transient ShortSnglLnkNode curr;
      transient ShortSnglLnkNode next;
      Itr(CheckedStack parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public short nextShort(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final ShortSnglLnkNode next;
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
      private void uncheckedForEachRemaining(ShortSnglLnkNode next,ShortConsumer action){
        final int modCount=this.modCount;
        ShortSnglLnkNode prev,curr;
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
      @Override public void forEachRemaining(ShortConsumer action){
        final ShortSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final ShortSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final ShortSnglLnkNode prev;
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
  public static class UncheckedStack extends ShortSnglLnkSeq implements OmniStack.OfShort{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,ShortSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(short val){
      this.head=new ShortSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        ShortSnglLnkNode newHead;
        for(clone=new UncheckedStack(this.size,newHead=new ShortSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new ShortSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public short popShort(){
      ShortSnglLnkNode head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(ShortSnglLnkNode head,ShortPredicate filter){
      if(filter.test(head.val)){
        while((head=head.next)!=null){
          if(!filter.test(head.val)){
            this.size=ShortSnglLnkNode.retainSurvivors(head,filter);
            this.head=head;
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        ShortSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+ShortSnglLnkNode.retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override public Short pop(){
      return popShort();
    }
    @Override public short peekShort(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollShort(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public Short peek(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        return (Short)(head.val);
      }
      return null;
    }
    @Override public Short poll(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Short)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double peekDouble(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        return (double)(head.val);
      }
      return Double.NaN;
    }
    @Override public double pollDouble(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        return (float)(head.val);
      }
      return Float.NaN;
    }
    @Override public float pollFloat(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        return (long)(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLong(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        return (int)(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollInt(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(ShortSnglLnkNode head
      ,int val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          ShortSnglLnkNode prev;
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
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortSnglLnkNode.uncheckedsearch(head,(short)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(short)val)
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if((v=(short)val)==val)
            {
              return ShortSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final short v;
            if(val==(v=(short)val))
            {
              return ShortSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Short||val instanceof Byte){
                i=((Number)val).shortValue();
              }else if(val instanceof Integer){
                if((i=(int)val)!=(short)i){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(short)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(short)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(short)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                if((i=(char)val)>Short.MAX_VALUE){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return ShortSnglLnkNode.uncheckedsearch(head,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      if(val<=Short.MAX_VALUE)
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final ShortSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return ShortSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public OmniIterator.OfShort iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractShortItr
    {
      transient final ShortSnglLnkSeq parent;
      transient ShortSnglLnkNode prev;
      transient ShortSnglLnkNode curr;
      transient ShortSnglLnkNode next;
      Itr(ShortSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public short nextShort(){
        final ShortSnglLnkNode next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(ShortSnglLnkNode next,ShortConsumer action){
        ShortSnglLnkNode prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(ShortConsumer action){
        final ShortSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Short> action){
        final ShortSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final ShortSnglLnkSeq parent;
        --(parent=this.parent).size;
        final ShortSnglLnkNode prev;
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
