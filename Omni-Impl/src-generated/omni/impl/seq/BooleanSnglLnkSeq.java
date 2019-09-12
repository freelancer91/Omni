package omni.impl.seq;
import java.util.Collection;
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
import omni.impl.AbstractOmniCollection;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
public abstract class BooleanSnglLnkSeq extends 
AbstractOmniCollection<Boolean>
 implements OmniCollection.OfBoolean,Externalizable{
  static final class Node{
    transient boolean val;
    transient Node next;
    Node(boolean val){
      this.val=val;
    }
    Node(boolean val,Node next){
      this.val=val;
      this.next=next;
    }
    public static  int uncheckedToString(Node curr,byte[] buffer){
      int bufferOffset=1;
      for(;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
        bufferOffset=ToStringUtil.getStringBoolean(curr.val,buffer,bufferOffset);
        if((curr=curr.next)==null){
          return bufferOffset;
        }
      }
    }
    public static  void uncheckedToString(Node curr,ToStringUtil.OmniStringBuilderByte builder){
      for(;;builder.uncheckedAppendCommaAndSpace()){
        builder.uncheckedAppendBoolean(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  int uncheckedHashCode(Node curr){
      int hash=31+Boolean.hashCode(curr.val);
      for(;(curr=curr.next)!=null;hash=(hash*31)+Boolean.hashCode(curr.val)){}
      return hash;
    }
    public static  void uncheckedForEach(Node curr,Node tail,BooleanConsumer action){
      for(action.accept(curr.val);curr!=tail;action.accept((curr=curr.next).val)){}
    }
    public static  void uncheckedForEach(Node curr,BooleanConsumer action){
      do{
        action.accept(curr.val);
      }while((curr=curr.next)!=null);
    }
    public static  boolean uncheckedcontains (Node curr
    ,boolean val
    ){
      for(;val!=(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    public static  int uncheckedsearch (Node curr
    ,boolean val
    ){
      int index=1;
      for(;val!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    public static  void uncheckedCopyInto(Node curr,boolean[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  void uncheckedCopyInto(Node curr,Object[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  void uncheckedCopyInto(Node curr,Boolean[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  void uncheckedCopyInto(Node curr,double[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=TypeUtil.castToDouble(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  void uncheckedCopyInto(Node curr,float[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=TypeUtil.castToFloat(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  void uncheckedCopyInto(Node curr,long[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=TypeUtil.castToLong(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  void uncheckedCopyInto(Node curr,int[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=(int)TypeUtil.castToByte(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  void uncheckedCopyInto(Node curr,short[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=(short)TypeUtil.castToByte(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  void uncheckedCopyInto(Node curr,byte[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=TypeUtil.castToByte(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  void uncheckedCopyInto(Node curr,char[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=TypeUtil.castToChar(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
  }
  private static final long serialVersionUID=1L;
  transient Node head;
  private BooleanSnglLnkSeq(Collection<? extends Boolean> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private BooleanSnglLnkSeq(OmniCollection.OfRef<? extends Boolean> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private BooleanSnglLnkSeq(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private BooleanSnglLnkSeq(){
  }
  private BooleanSnglLnkSeq(Node head,int size){
    super(size);
    this.head=head;
  }
  private static  int retainSurvivors(Node prev,final boolean retainThis){
    int numSurvivors=1;
    outer:for(Node next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  private static  int retainTrailingSurvivors(Node prev,Node curr,final boolean retainThis){
    int numSurvivors=0;
    outer:for(;;curr=curr.next){
      if(curr==null){
        prev.next=null;
        break;
      }else if(curr.val==retainThis){
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
  @Override public void writeExternal(ObjectOutput out) throws IOException{
    int size;
    out.writeInt(size=this.size);
    if(size!=0){
      var curr=this.head;
      for(int word=TypeUtil.castToByte(curr.val),marker=1;;){
        if((curr=curr.next)==null){
          out.writeByte(word);
          return;
        }else if((marker<<=1)==(1<<8)){
          out.writeByte(word);
          word=0;
          marker=1;
        }
        if(curr.val){
          word|=marker;
        }
      }
    }
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
  }
  @Override public String toString(){
    final Node head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
        [size=Node.uncheckedToString(head,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        Node.uncheckedToString(head,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(BooleanConsumer action){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      Node.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(boolean val);
  @Override public boolean add(boolean val){
    push((val));
    return true;
  }
  public void push(Boolean val){
    push((boolean)val);
  }
  @Override public boolean add(Boolean val){
    push((boolean)(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else if(arr.length!=0){
      arr[0]=null;
    }
    return arr;
  }
  @Override public boolean[] toBooleanArray(){
    final Node head;
    if((head=this.head)!=null){
      final boolean[] dst;
      Node.uncheckedCopyInto(head,dst=new boolean[this.size]);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override public Boolean[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Boolean[] dst;
      Node.uncheckedCopyInto(head,dst=new Boolean[this.size]);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final Node head;
    if((head=this.head)!=null){
      final double[] dst;
      Node.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    final Node head;
    if((head=this.head)!=null){
      final float[] dst;
      Node.uncheckedCopyInto(head,dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    final Node head;
    if((head=this.head)!=null){
      final long[] dst;
      Node.uncheckedCopyInto(head,dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    final Node head;
    if((head=this.head)!=null){
      final int[] dst;
      Node.uncheckedCopyInto(head,dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    final Node head;
    if((head=this.head)!=null){
      final short[] dst;
      Node.uncheckedCopyInto(head,dst=new short[this.size]);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public byte[] toByteArray(){
    final Node head;
    if((head=this.head)!=null){
      final byte[] dst;
      Node.uncheckedCopyInto(head,dst=new byte[this.size]);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public char[] toCharArray(){
    final Node head;
    if((head=this.head)!=null){
      final char[] dst;
      Node.uncheckedCopyInto(head,dst=new char[this.size]);
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
            return Node.uncheckedcontains(head,(val));
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
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return Node.uncheckedcontains(head,v);
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
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedcontains(head,v);
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
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return Node.uncheckedcontains(head,v);
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
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedcontains(head,v);
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
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
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
              return Node.uncheckedcontains(head,b);
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
            returnFalse:for(;;){
              final boolean v;
              switch(val){
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
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
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
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
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
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
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
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
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
  public boolean peekBoolean(){
    final Node head;
    if((head=this.head)!=null){
      return (boolean)(head.val);
    }
    return false;
  }
  public Boolean peek(){
    final Node head;
    if((head=this.head)!=null){
      return (Boolean)(head.val);
    }
    return null;
  }
  public double peekDouble(){
    final Node head;
    if((head=this.head)!=null){
      return TypeUtil.castToDouble(head.val);
    }
    return Double.NaN;
  }
  public float peekFloat(){
    final Node head;
    if((head=this.head)!=null){
      return TypeUtil.castToFloat(head.val);
    }
    return Float.NaN;
  }
  public long peekLong(){
    final Node head;
    if((head=this.head)!=null){
      return TypeUtil.castToLong(head.val);
    }
    return Long.MIN_VALUE;
  }
  public int peekInt(){
    final Node head;
    if((head=this.head)!=null){
      return (int)TypeUtil.castToByte(head.val);
    }
    return Integer.MIN_VALUE;
  }
  public short peekShort(){
    final Node head;
    if((head=this.head)!=null){
      return (short)TypeUtil.castToByte(head.val);
    }
    return Short.MIN_VALUE;
  }
  public byte peekByte(){
    final Node head;
    if((head=this.head)!=null){
      return TypeUtil.castToByte(head.val);
    }
    return Byte.MIN_VALUE;
  }
  public char peekChar(){
    final Node head;
    if((head=this.head)!=null){
      return TypeUtil.castToChar(head.val);
    }
    return Character.MIN_VALUE;
  }
  abstract boolean uncheckedremoveIf(Node head,BooleanPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(Collection<? extends Boolean> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedStack(){
    }
    CheckedStack(Node head,int size){
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
        for(clone=new CheckedStack(newHead=new Node(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new Node(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public boolean popBoolean(){
      Node head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void forEach(BooleanConsumer action){
      final Node head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          Node.uncheckedForEach(head,action);
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
          Node.uncheckedForEach(head,action::accept);
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
              this.size=retainSurvivors(head,firstVal);
              return true;
            }
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          this.head=null;
          this.size=0;
          return true;
        }else{
          Node prev;
          for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
            if(head.val^firstVal){
              if(filter.test(!firstVal)){
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.modCount=modCount+1;
                this.size=numSurvivors+retainTrailingSurvivors(prev,head.next,firstVal);
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
      {
        if(val==(head.val)){
          this.head=head.next;
        }else{
          Node prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(val!=(head.val));
          prev.next=head.next;
        }
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
      Itr(Itr itr){
        super(itr);
        this.parent=itr.parent;
        this.modCount=itr.modCount;
      }
      @Override public Object clone(){
        return new Itr(this);
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
      @Override void uncheckedForEachRemaining(final Node expectedNext,BooleanConsumer action){
        final int modCount=this.modCount;
        Node prev,curr,next;
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
        final Node prev;
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
    public UncheckedStack(Collection<? extends Boolean> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedStack(){
    }
    UncheckedStack(Node head,int size){
      super(head,size);
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        Node curr;
        int word,marker;
        for(this.head=curr=new Node(((marker=1)&(word=in.readUnsignedByte()))!=0);--size!=0;curr=curr.next=new Node((word&marker)!=0)){
          if((marker<<=1)==(1<<8)){
            word=in.readUnsignedByte();
            marker=1;
          }
        }
      }
    }
    @Override public void push(boolean val){
      this.head=new Node(val,this.head);
      ++this.size;
    }
    @Override public Object clone(){
      Node head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        Node newHead;
        for(clone=new UncheckedStack(newHead=new Node(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new Node(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public boolean popBoolean(){
      Node head;
      this.head=(head=this.head).next;
      --this.size;
      return head.val;
    }
    @Override public int search(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            return Node.uncheckedsearch(head,(val));
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
            returnFalse:for(;;){
              final boolean v;
              switch(val){
              default:
                break returnFalse;
              case 0:
                v=false;
                break;
              case 1:
                v=true;
              }
              return Node.uncheckedsearch(head,v);
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
            returnFalse:for(;;){
              final boolean v;
              if(val==0L){
                v=false;
              }else if(val==1L){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedsearch(head,v);
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
            returnFalse:for(;;){
              final boolean v;
              switch(Float.floatToRawIntBits(val)){
                default:
                  break returnFalse;
                case 0:
                case Integer.MIN_VALUE:
                  v=false;
                  break;
                case TypeUtil.FLT_TRUE_BITS:
                  v=true;
              }
              return Node.uncheckedsearch(head,v);
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
            returnFalse:for(;;){
              final boolean v;
              long bits;
              if(((bits=Double.doubleToRawLongBits(val))&(Long.MAX_VALUE))==0){
                v=false;
              }else if(bits==TypeUtil.DBL_TRUE_BITS){
                v=true;
              }else{
                break returnFalse;
              }
              return Node.uncheckedsearch(head,v);
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
                if(((bits=Double.doubleToRawLongBits((double)val))&(Long.MAX_VALUE))==0){
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
              return Node.uncheckedsearch(head,b);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
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
    @Override public Boolean pop(){
      return popBoolean();
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
    @Override boolean uncheckedremoveIf(Node head,BooleanPredicate filter){
      boolean firstVal;
      if(filter.test(firstVal=head.val)){
        while((head=head.next)!=null){
          if(head.val^firstVal){
            if(filter.test(firstVal=!firstVal)){
              break;
            }
            this.head=head;
            this.size=retainSurvivors(head,firstVal);
            return true;
          }
        }
        this.head=null;
        this.size=0;
        return true;
      }else{
        Node prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(head.val^firstVal){
            if(filter.test(!firstVal)){
              this.size=numSurvivors+retainTrailingSurvivors(prev,head.next,firstVal);
              return true;
            }
            break;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveVal(Node head
      ,boolean val
    ){
      {
        if(val==(head.val)){
          this.head=head.next;
        }else{
          Node prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(val!=(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfBoolean iterator(){
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
  public static class CheckedQueue extends UncheckedQueue{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedQueue(Collection<? extends Boolean> that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedQueue(){
      super();
    }
    CheckedQueue(Node head,int size,Node tail){
      super(head,size,tail);
    }
    @Override public boolean booleanElement(){
      final Node head;
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
          Node curr;
          for(int word=TypeUtil.castToByte((curr=this.head).val),marker=1;;){
            if(curr==tail){
              out.writeByte(word);
              return;
            }else if((marker<<=1)==(1<<8)){
              out.writeByte(word);
              word=0;
              marker=1;
            }
            if((curr=curr.next).val){
              word|=marker;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void forEach(BooleanConsumer action){
      final Node head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          Node.uncheckedForEach(head,tail,action);
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
          Node.uncheckedForEach(head,tail,action::accept);
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
      Node head;
      if((head=this.head)!=null){
        Node newHead=new Node(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new Node((head=head.next).val)){}
        return new CheckedQueue(newHead,this.size,newTail);
      }
      return new CheckedQueue();
    }
    @Override void push(boolean val){
      ++this.modCount;
      super.push(val);
    }
    @Override public boolean removeBoolean(){
      final Node head;
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
      final Node head;
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
      return false;
    }
    @Override public Boolean poll(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(Boolean)(head.val);
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
    @Override public double pollDouble(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToDouble(head.val);
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
    @Override public float pollFloat(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToFloat(head.val);
        if(head==this.tail){
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
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToLong(head.val);
        if(head==this.tail){
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
      final Node head;
      if((head=this.head)!=null){
        final var ret=(int)TypeUtil.castToByte(head.val);
        if(head==this.tail){
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
      final Node head;
      if((head=this.head)!=null){
        final var ret=(short)TypeUtil.castToByte(head.val);
        if(head==this.tail){
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
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToByte(head.val);
        if(head==this.tail){
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
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToChar(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    private int removeIfHelper(Node prev,Node tail,boolean retainThis){
      int numSurvivors=1;
      outer:for(Node next;prev!=tail;prev=next,++numSurvivors){
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
    private int removeIfHelper(Node prev,Node head,Node tail,boolean retainThis){
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
    @Override boolean uncheckedremoveIf(Node head,BooleanPredicate filter){
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
            Node prev;
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
    @Override boolean uncheckedremoveVal(Node head
    ,boolean val
    ){
      {
        final var tail=this.tail;
        if(val==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          Node prev;
          do{
            if(head==tail){
              return false;
            }
          }while(val!=((head=(prev=head).next).val));
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
    @Override public OmniIterator.OfBoolean iterator(){
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
      @Override void uncheckedForEachRemaining(final Node expectedNext,BooleanConsumer action){
        final int modCount=this.modCount;
        Node prev,curr,next;
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
        final Node prev;
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
  public static class UncheckedQueue extends BooleanSnglLnkSeq implements OmniQueue.OfBoolean{
    private static final long serialVersionUID=1L;
    transient Node tail;
    public UncheckedQueue(Collection<? extends Boolean> that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedQueue(){
      super();
    }
    UncheckedQueue(Node head,int size,Node tail){
      super(head,size);
      this.tail=tail;
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        Node curr;
        int word,marker;
        for(this.head=curr=new Node(((marker=1)&(word=in.readUnsignedByte()))!=0);--size!=0;curr=curr.next=new Node((word&marker)!=0)){
          if((marker<<=1)==(1<<8)){
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
      Node head;
      if((head=this.head)!=null){
        Node newHead=new Node(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new Node((head=head.next).val)){}
        return new UncheckedQueue(newHead,this.size,newTail);
      }
      return new UncheckedQueue();
    }
    @Override void push(boolean val){
      final var newNode=new Node(val);
      final Node tail;
      if((tail=this.tail)!=null){
        tail.next=newNode;
      }else{
        this.head=newNode;
      }
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
      final Node head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public boolean pollBoolean(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail){
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
      final Node head;
      if((head=this.head)!=null){
        final var ret=(Boolean)(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToDouble(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToFloat(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToLong(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(int)TypeUtil.castToByte(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public short pollShort(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(short)TypeUtil.castToByte(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public byte pollByte(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToByte(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char pollChar(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=TypeUtil.castToChar(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    private int removeIfHelper(Node prev,Node tail,boolean retainThis){
      int numSurvivors=1;
      outer:for(Node next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(Node prev,Node curr,Node tail,boolean retainThis){
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
    @Override boolean uncheckedremoveIf(Node head,BooleanPredicate filter){
      boolean firstVal;
      if(filter.test(firstVal=head.val)){
        for(var tail=this.tail;head!=tail;){
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
        for(final var tail=this.tail;head!=tail;++numSurvivors){
          final Node prev;
          if((head=(prev=head).next).val^firstVal){
            if(filter.test(!firstVal)){
              this.size=numSurvivors+removeIfHelper(prev,head,tail,firstVal);
              return true;
            }
            break;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveVal(Node head
    ,boolean val
    ){
      {
        final var tail=this.tail;
        if(val==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          Node prev;
          do{
            if(head==tail){
              return false;
            }
          }while(val!=((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfBoolean iterator(){
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
        final Node prev;
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
      extends AbstractBooleanItr
  {
    transient Node prev;
    transient Node curr;
    transient Node next;
    AbstractItr(AbstractItr itr){
      this.prev=itr.prev;
      this.curr=itr.curr;
      this.next=itr.next;
    }
    AbstractItr(Node next){
      this.next=next; 
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
    void uncheckedForEachRemaining(Node next,BooleanConsumer action){
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
  }
}
