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
public abstract class BooleanSnglLnkSeq implements OmniCollection.OfBoolean,Cloneable{
  static class Node{
    transient boolean val;
    transient Node next;
    Node(boolean val){
      this.val=val;
    }
    Node(boolean val,Node next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+Boolean.hashCode(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+Boolean.hashCode(curr.val);
      }
      return hash;
    }
    private int uncheckedToString(byte[] buffer){
      int bufferOffset=1;
      for(var curr=this;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
        bufferOffset=ToStringUtil.getStringBoolean(curr.val,buffer,bufferOffset);
        if((curr=curr.next)==null){
          return bufferOffset;
        }
      }
    }
    private void uncheckedToString(ToStringUtil.OmniStringBuilderByte builder){
      for(var curr=this;;builder.uncheckedAppendCommaAndSpace()){
        builder.uncheckedAppendBoolean(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(BooleanConsumer action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(boolean[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(Object[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(Boolean[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(double[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=TypeUtil.castToDouble(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(float[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=TypeUtil.castToFloat(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(long[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=TypeUtil.castToLong(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(int[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(int)TypeUtil.castToByte(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(short[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(short)TypeUtil.castToByte(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(byte[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=TypeUtil.castToByte(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(char[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=TypeUtil.castToChar(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private boolean uncheckedcontains(boolean val){
      for(var curr=this;val!=(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    private int uncheckedsearch(boolean val){
      int index=1;
      for(var curr=this;val!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    private int retainSurvivors(final boolean retainThis){
      int numSurvivors=1;
      Node prev,next;
      outer:for(next=(prev=this).next;next!=null;++numSurvivors,next=(prev=next).next){
        if(next.val^retainThis){
          do{
            if((next=next.next)==null){
              prev.next=null;
              break outer;
            }
          }while(next.val^retainThis);
          prev.next=next;
        }
      }
      return numSurvivors;
    }
    private int retainTrailingSurvivors(final boolean retainThis){
      int numSurvivors=0;
      Node prev,curr;
      outer:for(prev=this;;prev=curr){
        if((curr=prev.next)==null){
          prev.next=null;
          break;
        }
        if(curr.val==retainThis){
          prev.next=curr;
          do{
            ++numSurvivors;
            if((curr=(prev=curr).next)==null){
              break outer;
            }
          }
          while(curr.val==retainThis);
        }
      }
      return numSurvivors;
    }
  }
  transient int size;
  transient Node head;
  private BooleanSnglLnkSeq(){
  }
  private BooleanSnglLnkSeq(int size,Node head){
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
    final Node head;
    if((head=this.head)!=null){
      return head.uncheckedHashCode();
    }
    return 1;
  }
  @Override public String toString(){
    final Node head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
        [size=head.uncheckedToString(buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        head.uncheckedToString(builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(BooleanConsumer action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      head.uncheckedCopyInto(arr);
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
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public boolean[] toBooleanArray(){
    final Node head;
    if((head=this.head)!=null){
      final boolean[] dst;
      head.uncheckedCopyInto(dst=new boolean[this.size]);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override public Boolean[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Boolean[] dst;
      head.uncheckedCopyInto(dst=new Boolean[this.size]);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final Node head;
    if((head=this.head)!=null){
      final double[] dst;
      head.uncheckedCopyInto(dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final Node head;
    if((head=this.head)!=null){
      final float[] dst;
      head.uncheckedCopyInto(dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    final Node head;
    if((head=this.head)!=null){
      final long[] dst;
      head.uncheckedCopyInto(dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    final Node head;
    if((head=this.head)!=null){
      final int[] dst;
      head.uncheckedCopyInto(dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    final Node head;
    if((head=this.head)!=null){
      final short[] dst;
      head.uncheckedCopyInto(dst=new short[this.size]);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public byte[] toByteArray(){
    final Node head;
    if((head=this.head)!=null){
      final byte[] dst;
      head.uncheckedCopyInto(dst=new byte[this.size]);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public char[] toCharArray(){
    final Node head;
    if((head=this.head)!=null){
      final char[] dst;
      head.uncheckedCopyInto(dst=new char[this.size]);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            return head.uncheckedcontains((val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final Node head;
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
              return head.uncheckedcontains(v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final Node head;
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
              return head.uncheckedcontains(v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final Node head;
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
              return head.uncheckedcontains(v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final Node head;
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
              return head.uncheckedcontains(v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final Node head;
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
              return head.uncheckedcontains(b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
  abstract boolean uncheckedremoveVal(Node head,boolean val);
  @Override public boolean removeIf(BooleanPredicate filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Boolean> filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  abstract boolean uncheckedremoveIf(Node head,BooleanPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    transient int modCount;
    public CheckedStack(){
    }
    private CheckedStack(int size,Node head){
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
      Node head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        Node newHead;
        for(clone=new CheckedStack(this.size,newHead=new Node(head.val));(head=head.next)!=null;newHead=newHead.next=new Node(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public boolean popBoolean(){
      Node head;
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
      final Node head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          head.uncheckedForEach(action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      final Node head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          head.uncheckedForEach(action::accept);
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
    @Override boolean uncheckedremoveIf(Node head,BooleanPredicate filter){
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
              this.size=head.retainSurvivors(firstVal);
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=null;
          this.size=0;
          return true;
        }else{
          for(int numSurvivors=1;(head=head.next)!=null;++numSurvivors){
            if(head.val^firstVal){
              if(filter.test(!firstVal)){
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.modCount=modCount+1;
                this.size=numSurvivors+head.retainTrailingSurvivors(firstVal);
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
      final Node head;
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
      final Node head;
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
      final Node head;
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
      final Node head;
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
      final Node head;
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
      final Node head;
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
      final Node head;
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
      final Node head;
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
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToChar(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(Node head
      ,boolean val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
      transient Node prev;
      transient Node curr;
      transient Node next;
      Itr(CheckedStack parent){
        this.parent=parent;
        this.next=parent.head;
        this.modCount=parent.modCount;
      }
      @Override public boolean nextBoolean(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final Node next;
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
      private void uncheckedForEachRemaining(Node next,BooleanConsumer action){
        final int modCount=this.modCount;
        Node prev,curr;
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
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final Node prev;
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
    private UncheckedStack(int size,Node head){
      super(size,head);
    }
    @Override public void push(boolean val){
      this.head=new Node(val,this.head);
      ++this.size;
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public Object clone(){
      Node head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        Node newHead;
        for(clone=new UncheckedStack(this.size,newHead=new Node(head.val));(head=head.next)!=null;newHead=newHead.next=new Node(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public boolean popBoolean(){
      Node head;
      var ret=(head=this.head).val;
      this.head=head.next;
      --this.size;
      return ret;
    }
    @Override boolean uncheckedremoveIf(Node head,BooleanPredicate filter){
      boolean firstVal;
      if(filter.test(firstVal=head.val)){
        while((head=head.next)!=null){
          if(head.val^firstVal){
            if(filter.test(firstVal=!firstVal)){
              break;
            }
            this.head=head;
            this.size=head.retainSurvivors(firstVal);
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        for(int numSurvivors=1;(head=head.next)!=null;++numSurvivors){
          if(head.val^firstVal){
            if(filter.test(!firstVal)){
              this.size=numSurvivors+head.retainTrailingSurvivors(firstVal);
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
      final Node head;
      if((head=this.head)!=null){
        return (head.val);
      }
      return false;
    }
    @Override public boolean pollBoolean(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return false;
    }
    @Override public Boolean peek(){
      final Node head;
      if((head=this.head)!=null){
        return (Boolean)(head.val);
      }
      return null;
    }
    @Override public Boolean poll(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(Boolean)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double peekDouble(){
      final Node head;
      if((head=this.head)!=null){
        return TypeUtil.castToDouble(head.val);
      }
      return Double.NaN;
    }
    @Override public double pollDouble(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToDouble(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float peekFloat(){
      final Node head;
      if((head=this.head)!=null){
        return TypeUtil.castToFloat(head.val);
      }
      return Float.NaN;
    }
    @Override public float pollFloat(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToFloat(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long peekLong(){
      final Node head;
      if((head=this.head)!=null){
        return TypeUtil.castToLong(head.val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLong(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToLong(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int peekInt(){
      final Node head;
      if((head=this.head)!=null){
        return (int)TypeUtil.castToByte(head.val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollInt(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(int)TypeUtil.castToByte(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short peekShort(){
      final Node head;
      if((head=this.head)!=null){
        return (short)TypeUtil.castToByte(head.val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollShort(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(short)TypeUtil.castToByte(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte peekByte(){
      final Node head;
      if((head=this.head)!=null){
        return TypeUtil.castToByte(head.val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollByte(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToByte(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char peekChar(){
      final Node head;
      if((head=this.head)!=null){
        return TypeUtil.castToChar(head.val);
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollChar(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToChar(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override boolean uncheckedremoveVal(Node head
      ,boolean val
      ){
        if(val==(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
          final Node head;
          if((head=this.head)!=null)
          {
            return head.uncheckedsearch((val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final Node head;
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
              return head.uncheckedsearch(v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final Node head;
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
              return head.uncheckedsearch(v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final Node head;
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
              return head.uncheckedsearch(v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final Node head;
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
              return head.uncheckedsearch(v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final Node head;
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
              return head.uncheckedsearch(b);
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
      transient Node prev;
      transient Node curr;
      transient Node next;
      Itr(BooleanSnglLnkSeq parent){
        this.parent=parent;
        this.next=parent.head;
      }
      @Override public boolean nextBoolean(){
        final Node next;
        this.next=(next=this.next).next;
        this.prev=this.curr;
        this.curr=next;
        return next.val;
      }
      @Override public boolean hasNext(){
        return next!=null;
      }
      private void uncheckedForEachRemaining(Node next,BooleanConsumer action){
        Node prev,curr=this.curr;
        do{
          action.accept(next.val);
          prev=curr;
        }while((next=(curr=next).next)!=null);
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void forEachRemaining(BooleanConsumer action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action){
        final Node next;
        if((next=this.next)!=null){
          uncheckedForEachRemaining(next,action::accept);
        }
      }
      @Override public void remove(){
        final BooleanSnglLnkSeq parent;
        --(parent=this.parent).size;
        final Node prev;
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
