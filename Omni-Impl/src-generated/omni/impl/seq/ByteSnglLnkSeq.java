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
import omni.api.OmniQueue;
import omni.impl.ByteSnglLnkNode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
public abstract class ByteSnglLnkSeq extends 
AbstractSeq<Byte>
 implements OmniCollection.OfByte,Externalizable{
  private static final long serialVersionUID=1L;
  transient ByteSnglLnkNode head;
  private ByteSnglLnkSeq(){
  }
  private ByteSnglLnkSeq(ByteSnglLnkNode head,int size){
    super(size);
    this.head=head;
  }
  private static  void pullSurvivorsDown(ByteSnglLnkNode prev,BytePredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
  private static  int markSurvivors(ByteSnglLnkNode curr,BytePredicate filter,long[] survivorSet){
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
  private static  void pullSurvivorsDown(ByteSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
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
  private static  long markSurvivors(ByteSnglLnkNode curr,BytePredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  private static  int retainSurvivors(ByteSnglLnkNode prev, final BytePredicate filter){
    int numSurvivors=1;
    outer:for(ByteSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  private static  int retainTrailingSurvivors(ByteSnglLnkNode prev,ByteSnglLnkNode curr,final BytePredicate filter){
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
        out.writeByte(curr.val);
      }
      while((curr=curr.next)!=null);
    }
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
        (buffer=builder.buffer)[0]=(byte)'[';
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
  @Override public boolean add(byte val){
    push((val));
    return true;
  }
  public void push(Byte val){
    push((byte)val);
  }
  @Override public boolean add(Byte val){
    push((byte)(val));
    return true;
  }
  @Override public boolean add(boolean val){
    push((byte)TypeUtil.castToByte(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      ByteSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else if(arr.length!=0){
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
            if((v=(byte)val)==val){
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
            if((v=(byte)val)==val){
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
  public byte peekByte(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      return (byte)(head.val);
    }
    return Byte.MIN_VALUE;
  }
  public Byte peek(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      return (Byte)(head.val);
    }
    return null;
  }
  public double peekDouble(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      return (double)(head.val);
    }
    return Double.NaN;
  }
  public float peekFloat(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      return (float)(head.val);
    }
    return Float.NaN;
  }
  public long peekLong(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      return (long)(head.val);
    }
    return Long.MIN_VALUE;
  }
  public int peekInt(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      return (int)(head.val);
    }
    return Integer.MIN_VALUE;
  }
  public short peekShort(){
    final ByteSnglLnkNode head;
    if((head=this.head)!=null){
      return (short)(head.val);
    }
    return Short.MIN_VALUE;
  }
  abstract boolean uncheckedremoveIf(ByteSnglLnkNode head,BytePredicate filter);
  public static class CheckedStack extends UncheckedStack{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(){
    }
    CheckedStack(ByteSnglLnkNode head,int size){
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
        for(clone=new CheckedStack(newHead=new ByteSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new ByteSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public byte popByte(){
      ByteSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
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
    private int removeIfHelper(ByteSnglLnkNode prev,BytePredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(ByteSnglLnkNode head,BytePredicate filter){
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
      {
        if(val==(head.val)){
          this.head=head.next;
        }else{
          ByteSnglLnkNode prev;
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
    @Override public OmniIterator.OfByte iterator(){
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
      @Override void uncheckedForEachRemaining(ByteSnglLnkNode next,ByteConsumer action){
        final int modCount=this.modCount;
        ByteSnglLnkNode prev,curr;
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
        final ByteSnglLnkNode prev;
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
  public static class UncheckedStack extends ByteSnglLnkSeq implements OmniStack.OfByte{
    private static final long serialVersionUID=1L;
    public UncheckedStack(){
    }
    UncheckedStack(ByteSnglLnkNode head,int size){
      super(head,size);
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        ByteSnglLnkNode curr;
        for(this.head=curr=new ByteSnglLnkNode((byte)in.readByte());--size!=0;curr=curr.next=new ByteSnglLnkNode((byte)in.readByte())){}
      }
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
        for(clone=new UncheckedStack(newHead=new ByteSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new ByteSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public byte popByte(){
      ByteSnglLnkNode head;
      this.head=(head=this.head).next;
      --this.size;
      return head.val;
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
            if((v=(byte)val)==val){
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
    @Override public Byte pop(){
      return popByte();
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
    @Override boolean uncheckedremoveIf(ByteSnglLnkNode head,BytePredicate filter){
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
        ByteSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveVal(ByteSnglLnkNode head
      ,int val
    ){
      {
        if(val==(head.val)){
          this.head=head.next;
        }else{
          ByteSnglLnkNode prev;
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
    @Override public OmniIterator.OfByte iterator(){
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
  public static class CheckedQueue extends UncheckedQueue{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedQueue(){
      super();
    }
    CheckedQueue(ByteSnglLnkNode head,int size,ByteSnglLnkNode tail){
      super(head,size,tail);
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public byte byteElement(){
      final ByteSnglLnkNode head;
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
          ByteSnglLnkNode curr;
          for(curr=this.head;;curr=curr.next){
            out.writeByte(curr.val);
            if(curr==tail){
              return;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void forEach(ByteConsumer action){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ByteSnglLnkNode.uncheckedForEach(head,tail,action);
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
          ByteSnglLnkNode.uncheckedForEach(head,tail,action::accept);
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
      ByteSnglLnkNode head;
      if((head=this.head)!=null){
        ByteSnglLnkNode newHead=new ByteSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new ByteSnglLnkNode((head=head.next).val)){}
        return new CheckedQueue(newHead,this.size,newTail);
      }
      return new CheckedQueue();
    }
    @Override void push(byte val){
      ++this.modCount;
      super.push(val);
    }
    @Override public byte removeByte(){
      final ByteSnglLnkNode head;
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
    @Override public byte pollByte(){
      final ByteSnglLnkNode head;
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
      return Byte.MIN_VALUE;
    }
    @Override public Byte poll(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Byte)(head.val);
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(short)(head.val);
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
    private void pullSurvivorsDown(ByteSnglLnkNode prev,BytePredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(ByteSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(ByteSnglLnkNode prev,BytePredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(ByteSnglLnkNode head,BytePredicate filter){
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
    @Override boolean uncheckedremoveVal(ByteSnglLnkNode head
    ,int val
    ){
      {
        final var tail=this.tail;
        if(val==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          ByteSnglLnkNode prev;
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
    @Override public OmniIterator.OfByte iterator(){
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
      @Override void uncheckedForEachRemaining(ByteSnglLnkNode next,ByteConsumer action){
        final int modCount=this.modCount;
        ByteSnglLnkNode prev,curr;
        final CheckedQueue parent;
        final var tail=(parent=this.parent).tail;
        try{
          for(curr=this.curr;;next=curr.next){
            action.accept(next.val);
            prev=curr;
            if((curr=next)==tail){
              break;
            }
          }
        }finally{
          CheckedCollection.checkModCount(modCount,parent.modCount);
        }
        this.prev=prev;
        this.curr=curr;
        this.next=null;
      }
      @Override public void remove(){
        final ByteSnglLnkNode prev;
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
  public static class UncheckedQueue extends ByteSnglLnkSeq implements OmniQueue.OfByte{
    private static final long serialVersionUID=1L;
    transient ByteSnglLnkNode tail;
    public UncheckedQueue(){
      super();
    }
    UncheckedQueue(ByteSnglLnkNode head,int size,ByteSnglLnkNode tail){
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
      if(size!=0){
        ByteSnglLnkNode curr;
        for(this.head=curr=new ByteSnglLnkNode((byte)in.readByte());--size!=0;curr=curr.next=new ByteSnglLnkNode((byte)in.readByte())){}
        this.tail=curr;
      }
    }
    @Override public void clear(){
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    @Override public Object clone(){
      ByteSnglLnkNode head;
      if((head=this.head)!=null){
        ByteSnglLnkNode newHead=new ByteSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new ByteSnglLnkNode((head=head.next).val)){}
        return new UncheckedQueue(newHead,this.size,newTail);
      }
      return new UncheckedQueue();
    }
    @Override void push(byte val){
      final var newNode=new ByteSnglLnkNode(val);
      final ByteSnglLnkNode tail;
      if((tail=this.tail)!=null){
        tail.next=newNode;
      }else{
        this.head=newNode;
      }
      this.tail=newNode;
      ++this.size;
    }
    @Override public byte byteElement(){
      return head.val;
    }
    @Override public boolean offer(byte val){
      push(val);
      return true;
    }
    @Override public byte removeByte(){
      final ByteSnglLnkNode head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public byte pollByte(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override public Byte remove(){
      return removeByte();
    }
    @Override public Byte element(){
      return byteElement();
    }
    @Override public boolean offer(Byte val){
      push((byte)val);
      return true;
    }
    @Override public Byte poll(){
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Byte)(head.val);
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(float)(head.val);
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(long)(head.val);
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(int)(head.val);
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
      final ByteSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(short)(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    private int removeIfHelper(ByteSnglLnkNode prev,ByteSnglLnkNode tail,BytePredicate filter){
      int numSurvivors=1;
      outer:for(ByteSnglLnkNode next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(ByteSnglLnkNode prev,ByteSnglLnkNode curr,ByteSnglLnkNode tail,BytePredicate filter){
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
    @Override boolean uncheckedremoveIf(ByteSnglLnkNode head,BytePredicate filter){
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
          final ByteSnglLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveVal(ByteSnglLnkNode head
    ,int val
    ){
      {
        final var tail=this.tail;
        if(val==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          ByteSnglLnkNode prev;
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
    @Override public OmniIterator.OfByte iterator(){
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
        final ByteSnglLnkNode prev;
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
      extends AbstractByteItr
  {
    transient ByteSnglLnkNode prev;
    transient ByteSnglLnkNode curr;
    transient ByteSnglLnkNode next;
    AbstractItr(AbstractItr itr){
      this.prev=itr.prev;
      this.curr=itr.curr;
      this.next=itr.next;
    }
    AbstractItr(ByteSnglLnkNode next){
      this.next=next; 
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
    void uncheckedForEachRemaining(ByteSnglLnkNode next,ByteConsumer action){
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
  }
}
