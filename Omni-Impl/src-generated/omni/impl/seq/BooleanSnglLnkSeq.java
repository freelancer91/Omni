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
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractBooleanItr;
import omni.api.OmniStack;
import omni.util.BooleanSnglLnkNode;
public abstract class BooleanSnglLnkSeq implements OmniCollection.OfBoolean,Cloneable{
  transient int size;
  transient BooleanSnglLnkNode head;
  private BooleanSnglLnkSeq(){
  }
  private BooleanSnglLnkSeq(int size,BooleanSnglLnkNode head){
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
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return BooleanSnglLnkNode.uncheckedHashCode(head);
    }
    return 1;
  }
  @Override public String toString(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
        [size=BooleanSnglLnkNode.uncheckedToString(head,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        BooleanSnglLnkNode.uncheckedToString(head,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(BooleanConsumer action){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      BooleanSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      BooleanSnglLnkNode.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      BooleanSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(boolean val);
  @Override public boolean add(boolean val)
  {
    push((val));
    return true;
  }
  public void push(Boolean val){
    push((boolean)val);
  }
  @Override public boolean add(Boolean val)
  {
    push((boolean)(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      BooleanSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public boolean[] toBooleanArray(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      final boolean[] dst;
      BooleanSnglLnkNode.uncheckedCopyInto(head,dst=new boolean[this.size]);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override public Boolean[] toArray(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      final Boolean[] dst;
      BooleanSnglLnkNode.uncheckedCopyInto(head,dst=new Boolean[this.size]);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      final double[] dst;
      BooleanSnglLnkNode.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      final float[] dst;
      BooleanSnglLnkNode.uncheckedCopyInto(head,dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      final long[] dst;
      BooleanSnglLnkNode.uncheckedCopyInto(head,dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      final int[] dst;
      BooleanSnglLnkNode.uncheckedCopyInto(head,dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      final short[] dst;
      BooleanSnglLnkNode.uncheckedCopyInto(head,dst=new short[this.size]);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public byte[] toByteArray(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      final byte[] dst;
      BooleanSnglLnkNode.uncheckedCopyInto(head,dst=new byte[this.size]);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public char[] toCharArray(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      final char[] dst;
      BooleanSnglLnkNode.uncheckedCopyInto(head,dst=new char[this.size]);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return BooleanSnglLnkNode.uncheckedcontains(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              switch(val)
              {
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return BooleanSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              if(val==0L)
              {
                v=false;
              }else if(val==1L)
              {
                v=true;
              }
              else
              {
                break returnFalse;
              }
              return BooleanSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              switch(Float.floatToRawIntBits(val))
              {
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return BooleanSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
              {
                v=false;
              }
              else if(bits==TypeUtil.DBL_TRUE_BITS)
              {
                v=true;
              }
              else
              {
                break returnFalse;
              }
              return BooleanSnglLnkNode.uncheckedcontains(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return BooleanSnglLnkNode.uncheckedcontains(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return uncheckedremoveVal(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              switch(val)
              {
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return uncheckedremoveVal(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              if(val==0L)
              {
                v=false;
              }else if(val==1L)
              {
                v=true;
              }
              else
              {
                break returnFalse;
              }
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
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              switch(Float.floatToRawIntBits(val))
              {
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
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
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
              {
                v=false;
              }
              else if(bits==TypeUtil.DBL_TRUE_BITS)
              {
                v=true;
              }
              else
              {
                break returnFalse;
              }
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
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return uncheckedremoveVal(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveVal(BooleanSnglLnkNode head,boolean val);
  @Override public boolean removeIf(BooleanPredicate filter){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Boolean> filter){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(BooleanSnglLnkNode head,BooleanPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,BooleanSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(boolean val){
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
      BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        BooleanSnglLnkNode newHead;
        for(clone=new CheckedStack(this.size,newHead=new BooleanSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new BooleanSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public boolean popBoolean(){
      BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        var ret=head.val;
        this.head=head.next;
        --this.size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public void forEach(BooleanConsumer action){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          BooleanSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          BooleanSnglLnkNode.uncheckedForEach(head,action::accept);
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
    @Override boolean uncheckedremoveIf(BooleanSnglLnkNode head,BooleanPredicate filter){
      final int modCount=this.modCount;
      try
      {
        boolean firstVal;
        if(filter.test(firstVal=head.val)){
          while((head=head.next)!=null){
            if(head.val^firstVal){
              if(filter.test(firstVal=!firstVal)){
                break;
              }
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
              this.head=head;
              this.size=BooleanSnglLnkNode.retainSurvivors(head,firstVal);
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=null;
          this.size=0;
          return true;
        }else{
          BooleanSnglLnkNode prev;
          for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
            if(head.val^firstVal){
              if(filter.test(!firstVal)){
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.modCount=modCount+1;
                this.size=numSurvivors+BooleanSnglLnkNode.retainTrailingSurvivors(prev,head.next,firstVal);
                return true;
              }
              break;
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
    @Override public boolean pollBoolean(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return false;
    }
    @Override public Boolean poll(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Boolean)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToDouble(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToFloat(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToLong(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)TypeUtil.castToByte(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short pollShort(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(short)TypeUtil.castToByte(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte pollByte(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToByte(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToChar(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(BooleanSnglLnkNode head
      ,boolean val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          BooleanSnglLnkNode prev;
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
    @Override public OmniIterator.OfBoolean iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractBooleanItr
    {
      transient final CheckedStack parent;
      transient int modCount;
      transient BooleanSnglLnkNode prev;
      transient BooleanSnglLnkNode curr;
      transient BooleanSnglLnkNode next;
      Itr(CheckedStack parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public boolean nextBoolean(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final BooleanSnglLnkNode next;
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
      private void uncheckedForEachRemaining(BooleanSnglLnkNode next,BooleanConsumer action){
        final int modCount=this.modCount;
        BooleanSnglLnkNode prev,curr;
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
      @Override public void forEachRemaining(BooleanConsumer action){
        final BooleanSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final BooleanSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final BooleanSnglLnkNode prev;
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
  public static class UncheckedStack extends BooleanSnglLnkSeq implements OmniStack.OfBoolean{
    public UncheckedStack(){
    }
    private UncheckedStack(int size,BooleanSnglLnkNode head){
      super(size,head);
    }
    @Override public void push(boolean val){
      this.head=new BooleanSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        BooleanSnglLnkNode newHead;
        for(clone=new UncheckedStack(this.size,newHead=new BooleanSnglLnkNode(head.val));(head=head.next)!=null;newHead=newHead.next=new BooleanSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public boolean popBoolean(){
      BooleanSnglLnkNode head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(BooleanSnglLnkNode head,BooleanPredicate filter){
      boolean firstVal;
      if(filter.test(firstVal=head.val)){
        while((head=head.next)!=null){
          if(head.val^firstVal){
            if(filter.test(firstVal=!firstVal)){
              break;
            }
            this.head=head;
            this.size=BooleanSnglLnkNode.retainSurvivors(head,firstVal);
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        BooleanSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(head.val^firstVal){
            if(filter.test(!firstVal)){
              this.size=numSurvivors+BooleanSnglLnkNode.retainTrailingSurvivors(prev,head.next,firstVal);
              return true;
            }
            break;
          }
        }
        return false;
      }
    }
    @Override public Boolean pop(){
      return popBoolean();
    }
    @Override public boolean peekBoolean(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return false;
    }
    @Override public boolean pollBoolean(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return false;
    }
    @Override public Boolean peek(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        return (Boolean)(head.val);
      }
      return null;
    }
    @Override public Boolean poll(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Boolean)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double peekDouble(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToDouble(head.val);
      }
      return Double.NaN;
    }
    @Override public double pollDouble(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToDouble(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToFloat(head.val);
      }
      return Float.NaN;
    }
    @Override public float pollFloat(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToFloat(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToLong(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLong(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToLong(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        return (int)TypeUtil.castToByte(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollInt(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)TypeUtil.castToByte(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short peekShort(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        return (short)TypeUtil.castToByte(head.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollShort(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(short)TypeUtil.castToByte(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte peekByte(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToByte(head.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollByte(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToByte(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char peekChar(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        return TypeUtil.castToChar(head.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollChar(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToChar(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(BooleanSnglLnkNode head
      ,boolean val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          BooleanSnglLnkNode prev;
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
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            return BooleanSnglLnkNode.uncheckedsearch(head,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              switch(val)
              {
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return BooleanSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              if(val==0L)
              {
                v=false;
              }else if(val==1L)
              {
                v=true;
              }
              else
              {
                break returnFalse;
              }
              return BooleanSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              switch(Float.floatToRawIntBits(val))
              {
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return BooleanSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            returnFalse:for(;;)
            {
              final boolean v;
              long bits;
              if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
              {
                v=false;
              }
              else if(bits==TypeUtil.DBL_TRUE_BITS)
              {
                v=true;
              }
              else
              {
                break returnFalse;
              }
              return BooleanSnglLnkNode.uncheckedsearch(head,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final BooleanSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final boolean b;
              if(val instanceof Boolean){
                b=(boolean)val;
              }else if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                switch(((Number)val).intValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                  default:
                    break returnFalse;
                  case 0:
                  case Integer.MIN_VALUE:
                    b=false;
                    break;
                  case TypeUtil.FLT_TRUE_BITS:
                    b=true;
                }
              }else if(val instanceof Double){
                final long bits;
                if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE){
                  b=false;
                }else if(bits==TypeUtil.DBL_TRUE_BITS){
                  b=true;
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long v;
                if((v=(long)val)==0L){
                  b=false;
                }else if(v==1L){
                  b=true;
                }else{
                 break returnFalse;
                }
              }else if(val instanceof Character){
                switch(((Character)val).charValue()){
                  default:
                    break returnFalse;
                  case 0:
                    b=false;
                    break;
                  case 1:
                    b=true;
                }
              }else{
                break returnFalse;
              }
              return BooleanSnglLnkNode.uncheckedsearch(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new Itr(this);
    }
    private static class Itr
      extends AbstractBooleanItr
    {
      transient final BooleanSnglLnkSeq parent;
      transient BooleanSnglLnkNode prev;
      transient BooleanSnglLnkNode curr;
      transient BooleanSnglLnkNode next;
      Itr(BooleanSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public boolean nextBoolean(){
        final BooleanSnglLnkNode next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(BooleanSnglLnkNode next,BooleanConsumer action){
        BooleanSnglLnkNode prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final BooleanSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final BooleanSnglLnkNode next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final BooleanSnglLnkSeq parent;
        --(parent=this.parent).size;
        final BooleanSnglLnkNode prev;
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
