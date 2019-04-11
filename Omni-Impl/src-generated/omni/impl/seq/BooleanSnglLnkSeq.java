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
import omni.api.OmniQueue;
import omni.util.BooleanSnglLnkNode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
public abstract class BooleanSnglLnkSeq implements OmniCollection.OfBoolean,Cloneable,Externalizable{
  private static final long serialVersionUID=1L;
  transient int size;
  transient BooleanSnglLnkNode head;
  private BooleanSnglLnkSeq(){
  }
  private BooleanSnglLnkSeq(BooleanSnglLnkNode head,int size){
    this.size=size;
    this.head=head;
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException
  {
    int size;
    out.writeInt(size=this.size);
    if(size!=0)
    {
      var curr=this.head;
      for(int word=TypeUtil.castToByte(curr.val),marker=1;;)
      {
        if((curr=curr.next)==null)
        {
          out.writeByte(word);
          return;
        }
        if((marker<<=1)==(1<<8))
        {
          out.writeByte(word);
          word=0;
          marker=1;
        }
        if(curr.val)
        {
          word|=marker;
        }
      }
    }
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
  public boolean peekBoolean(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return (boolean)(head.val);
    }
    return false;
  }
  public Boolean peek(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return (Boolean)(head.val);
    }
    return null;
  }
  public double peekDouble(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return TypeUtil.castToDouble(head.val);
    }
    return Double.NaN;
  }
  public float peekFloat(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return TypeUtil.castToFloat(head.val);
    }
    return Float.NaN;
  }
  public long peekLong(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return TypeUtil.castToLong(head.val);
    }
    return Long.MIN_VALUE;
  }
  public int peekInt(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return (int)TypeUtil.castToByte(head.val);
    }
    return Integer.MIN_VALUE;
  }
  public short peekShort(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return (short)TypeUtil.castToByte(head.val);
    }
    return Short.MIN_VALUE;
  }
  public byte peekByte(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return TypeUtil.castToByte(head.val);
    }
    return Byte.MIN_VALUE;
  }
  public char peekChar(){
    final BooleanSnglLnkNode head;
    if((head=this.head)!=null){
      return TypeUtil.castToChar(head.val);
    }
    return Character.MIN_VALUE;
  }
  abstract boolean uncheckedremoveIf(BooleanSnglLnkNode head,BooleanPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(){
    }
    CheckedStack(BooleanSnglLnkNode head,int size){
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
        for(clone=new CheckedStack(newHead=new BooleanSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new BooleanSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public boolean popBoolean(){
      BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
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
      try{
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
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
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
    )
    {
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
    private static class Itr extends AbstractItr{
      final CheckedStack parent;
      transient int modCount;
      Itr(CheckedStack parent){
        super(parent.head);
        this.parent=parent;
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
      @Override void uncheckedForEachRemaining(BooleanSnglLnkNode next,BooleanConsumer action){
        final int modCount=this.modCount;
        BooleanSnglLnkNode prev,curr;
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
        final BooleanSnglLnkNode prev;
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
  public static class UncheckedStack extends BooleanSnglLnkSeq implements OmniStack.OfBoolean{
    private static final long serialVersionUID=1L;
    public UncheckedStack(){
    }
    UncheckedStack(BooleanSnglLnkNode head,int size){
      super(head,size);
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0)
      {
        BooleanSnglLnkNode curr;
        int word,marker;
        for(this.head=curr=new BooleanSnglLnkNode(((marker=1)&(word=in.readUnsignedByte()))!=0);--size!=0;curr=curr.next=new BooleanSnglLnkNode((word&marker)!=0))
        {
          if((marker<<=1)==(1<<8))
          {
            word=in.readUnsignedByte();
            marker=1;
          }
        }
      }
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
        for(clone=new UncheckedStack(newHead=new BooleanSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new BooleanSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public boolean popBoolean(){
      BooleanSnglLnkNode head;
      this.head=(head=this.head).next;
      --this.size;
      return head.val;
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
    @Override public Boolean pop(){
      return popBoolean();
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
    @Override boolean uncheckedremoveVal(BooleanSnglLnkNode head
      ,boolean val
    )
    {
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
    @Override public OmniIterator.OfBoolean iterator(){
      return new Itr();
    }
    private class Itr extends AbstractItr
    {
      Itr(){
        super(UncheckedStack.this.head);
      }
      @Override public void remove(){
        final UncheckedStack parent;
        --(parent=UncheckedStack.this).size;
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
  public static class CheckedQueue extends UncheckedQueue
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedQueue(){
      super();
    }
    CheckedQueue(BooleanSnglLnkNode head,int size,BooleanSnglLnkNode tail){
      super(head,size,tail);
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
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
    @Override public void clear(){
      if(size!=0)
      {
        ++this.modCount;
        this.head=null;
        this.tail=null;
      }
    }
    @Override public Object clone(){
      BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        BooleanSnglLnkNode newHead=new BooleanSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new BooleanSnglLnkNode((head=head.next).val)){}
        return new CheckedQueue(newHead,this.size,newTail);
      }
      return new CheckedQueue();
    }
    @Override void push(boolean val){
      ++this.modCount;
      super.push(val);
    }
    @Override public boolean removeBoolean(){
      final BooleanSnglLnkNode head;
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
    @Override public boolean pollBoolean(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
          this.tail=null;
        }
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
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    private int removeIfHelper(BooleanSnglLnkNode prev,BooleanSnglLnkNode tail,boolean retainThis){
      int numSurvivors=1;
      outer:for(BooleanSnglLnkNode next;prev!=tail;prev=next,++numSurvivors){
        if((next=prev.next).val^retainThis){
          do{
            if(next==tail){
              prev.next=null;
              this.tail=prev;
              break outer;
            }
          }while((next=next.next).val^retainThis);
          prev.next=next;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(BooleanSnglLnkNode prev,BooleanSnglLnkNode head,BooleanSnglLnkNode tail,boolean retainThis){
      int numSurvivors=0;
      outer:for(;;){
        if(head==tail){
          prev.next=null;
          this.tail=prev;
          break;
        }
        if((head=head.next).val==retainThis){
          prev.next=head;
          do{
            ++numSurvivors;
            if(head==tail){
              break outer;
            }
          }
          while((head=(prev=head).next).val==retainThis);
        }
      }
      return numSurvivors;
    }
    @Override boolean uncheckedremoveIf(BooleanSnglLnkNode head,BooleanPredicate filter){
      final int modCount=this.modCount;
      try{
        final var tail=this.tail;
        boolean firstVal;
        if(filter.test(firstVal=head.val)){
          while(head!=tail){
            if((head=head.next).val^firstVal){
              if(filter.test(firstVal=!firstVal)){
                break;
              }
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.modCount=modCount+1;
              this.head=head;
              this.size=removeIfHelper(head,tail,firstVal);
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
          for(int numSurvivors=1;head!=tail;++numSurvivors){
            BooleanSnglLnkNode prev;
            if((head=(prev=head).next).val^firstVal){
              if(filter.test(!firstVal)){
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.modCount=modCount+1;
                this.size=numSurvivors+removeIfHelper(prev,head,tail,firstVal);
                return true;
              }
              break;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return false;
    }
    @Override boolean uncheckedremoveVal(BooleanSnglLnkNode head
    ,boolean val
    ){
      if(val==(head.val))
      {
        if(head==tail)
        {
          this.tail=null;
        }
        this.head=head.next;
      }else{
        BooleanSnglLnkNode prev;
        {
          do{
            if(head==tail)
            {
              return false;
            }
            //if((head=(prev=head).next)==null){
            //  return false;
            //}
          }while(val!=((head=(prev=head).next).val));
        }
        if(head==tail)
        {
          this.tail=prev;
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
    private static class Itr extends AbstractItr
    {
      transient final CheckedQueue parent;
      transient int modCount;
      Itr(CheckedQueue parent){
        super(parent.head);
        this.parent=parent;
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
      @Override void uncheckedForEachRemaining(BooleanSnglLnkNode next,BooleanConsumer action){
        final int modCount=this.modCount;
        BooleanSnglLnkNode prev,curr;
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
        final BooleanSnglLnkNode prev;
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
          if(curr==parent.tail)
          {
            parent.tail=prev;
          }
          this.curr=prev;
          return;
        }
        throw new IllegalStateException();
      }
    }
  }
  public static class UncheckedQueue extends BooleanSnglLnkSeq implements OmniQueue.OfBoolean{
    private static final long serialVersionUID=1L;
    transient BooleanSnglLnkNode tail;
    public UncheckedQueue(){
      super();
    }
    UncheckedQueue(BooleanSnglLnkNode head,int size,BooleanSnglLnkNode tail){
      super(head,size);
      this.tail=tail;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0)
      {
        BooleanSnglLnkNode curr;
        int word,marker;
        for(this.head=curr=new BooleanSnglLnkNode(((marker=1)&(word=in.readUnsignedByte()))!=0);--size!=0;curr=curr.next=new BooleanSnglLnkNode((word&marker)!=0))
        {
          if((marker<<=1)==(1<<8))
          {
            word=in.readUnsignedByte();
            marker=1;
          }
        }
        this.tail=curr;
      }
    }
    @Override public void clear(){
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    @Override public Object clone(){
      BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        BooleanSnglLnkNode newHead=new BooleanSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new BooleanSnglLnkNode((head=head.next).val)){}
        return new UncheckedQueue(newHead,this.size,newTail);
      }
      return new UncheckedQueue();
    }
    @Override void push(boolean val){
      BooleanSnglLnkNode newNode;
      this.tail.next=(newNode=new BooleanSnglLnkNode(val));
      this.tail=newNode;
      ++this.size;
    }
    @Override public boolean booleanElement(){
      return head.val;
    }
    @Override public boolean offer(boolean val){
      push(val);
      return true;
    }
    @Override public boolean removeBoolean(){
      final BooleanSnglLnkNode head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public boolean pollBoolean(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return false;
    }
    @Override public Boolean remove(){
      return removeBoolean();
    }
    @Override public Boolean element(){
      return booleanElement();
    }
    @Override public boolean offer(Boolean val){
      push((boolean)val);
      return true;
    }
    @Override public Boolean poll(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Boolean)(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToDouble(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToFloat(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToLong(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)TypeUtil.castToByte(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short pollShort(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(short)TypeUtil.castToByte(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte pollByte(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToByte(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      final BooleanSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToChar(head.val);
        if(head==this.tail)
        {
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    private int removeIfHelper(BooleanSnglLnkNode prev,BooleanSnglLnkNode tail,boolean retainThis)
    {
      int numSurvivors=1;
      outer:for(BooleanSnglLnkNode next;prev!=tail;++numSurvivors,prev=next){
        if((next=prev.next).val^retainThis){
          do{
            if(next==tail){
              this.tail=prev;
              prev.next=null;
              break outer;
            }
          }
          while((next=next.next).val^retainThis);
          prev.next=next;
        }
      }
      return numSurvivors;
    }
    private int removeIfHelper(BooleanSnglLnkNode prev,BooleanSnglLnkNode curr,BooleanSnglLnkNode tail,boolean retainThis){
      int numSurvivors=0;
      while(curr!=tail) {
        if((curr=curr.next).val==retainThis){
          prev.next=curr;
          do{
            ++numSurvivors;
            if(curr==tail){
              return numSurvivors;
            }
          }while((curr=(prev=curr).next).val==retainThis);
        }
      }
      prev.next=null;
      this.tail=prev;
      return numSurvivors;
    }
    @Override boolean uncheckedremoveIf(BooleanSnglLnkNode head,BooleanPredicate filter){
      boolean firstVal;
      if(filter.test(firstVal=head.val)){
        for(var tail=this.tail;head!=tail;)
        {
          if((head=head.next).val^firstVal){
            if(filter.test(firstVal=!firstVal)){
              break;
            }
            this.head=head;
            this.size=removeIfHelper(head,tail,firstVal);
            return true;
          }
        }
        this.head=null;
        this.tail=null;
        this.size=0;
        return true;
      }else{
        int numSurvivors=1;
        for(final var tail=this.tail;head!=tail;++numSurvivors)
        {
          final BooleanSnglLnkNode prev;
          if((head=(prev=head).next).val^firstVal)
          {
            if(filter.test(!firstVal))
            {
              this.size=numSurvivors+removeIfHelper(prev,head,tail,firstVal);
              return true;
            }
            break;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveVal(BooleanSnglLnkNode head
    ,boolean val
    ){
      if(val==(head.val))
      {
        if(head==tail)
        {
          this.tail=null;
        }
        this.head=head.next;
      }else{
        BooleanSnglLnkNode prev;
        {
          do{
            if(head==tail)
            {
              return false;
            }
            //if((head=(prev=head).next)==null){
            //  return false;
            //}
          }while(val!=((head=(prev=head).next).val));
        }
        if(head==tail)
        {
          this.tail=prev;
        }
        prev.next=head.next;
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      return new Itr();
    }
    private class Itr extends AbstractItr
    {
      Itr(){
        super(UncheckedQueue.this.head);
      }
      @Override public void remove(){
        final UncheckedQueue parent;
        --(parent=UncheckedQueue.this).size;
        final BooleanSnglLnkNode prev;
        if((prev=this.prev)==null)
        {
          parent.head=next;
        }else{
          prev.next=null;
        }
        if(this.curr==parent.tail)
        {
          parent.tail=prev;
        }
        this.curr=prev;
      }
    }
  }
  private static class AbstractItr
      extends AbstractBooleanItr
  {
    transient BooleanSnglLnkNode prev;
    transient BooleanSnglLnkNode curr;
    transient BooleanSnglLnkNode next;
    AbstractItr(BooleanSnglLnkNode next)
    {
      this.next=next; 
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
    void uncheckedForEachRemaining(BooleanSnglLnkNode next,BooleanConsumer action){
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
  }
}
