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
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractShortItr;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.impl.ShortSnglLnkNode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
public abstract class ShortSnglLnkSeq extends 
AbstractSeq<Short>
 implements OmniCollection.OfShort,Externalizable{
  private static final long serialVersionUID=1L;
  transient ShortSnglLnkNode head;
  private ShortSnglLnkSeq(Collection<? extends Short> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private ShortSnglLnkSeq(OmniCollection.OfRef<? extends Short> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private ShortSnglLnkSeq(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private ShortSnglLnkSeq(OmniCollection.ShortOutput<?> that){
    super();
    //TODO optimize;
    this.addAll(that);
  }
  private ShortSnglLnkSeq(OmniCollection.OfByte that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private ShortSnglLnkSeq(OmniCollection.OfShort that){
    super();
    //TODO optimize
    this.addAll(that);
  }  
  private ShortSnglLnkSeq(){
  }
  private ShortSnglLnkSeq(ShortSnglLnkNode head,int size){
    super(size);
    this.head=head;
  }
  private static  void pullSurvivorsDown(ShortSnglLnkNode prev,ShortPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
  private static  int markSurvivors(ShortSnglLnkNode curr,ShortPredicate filter,long[] survivorSet){
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
  private static  void pullSurvivorsDown(ShortSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
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
  private static  long markSurvivors(ShortSnglLnkNode curr,ShortPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  private static  int retainSurvivors(ShortSnglLnkNode prev, final ShortPredicate filter){
    int numSurvivors=1;
    outer:for(ShortSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  private static  int retainTrailingSurvivors(ShortSnglLnkNode prev,ShortSnglLnkNode curr,final ShortPredicate filter){
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
        out.writeShort(curr.val);
      }
      while((curr=curr.next)!=null);
    }
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
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
        (buffer=builder.buffer)[0]=(byte)'[';
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
  @Override public boolean add(short val){
    push((val));
    return true;
  }
  @Override public boolean add(byte val){
    push((short)(val));
    return true;
  }
  public void push(Short val){
    push((short)val);
  }
  @Override public boolean add(Short val){
    push((short)(val));
    return true;
  }
  @Override public boolean add(boolean val){
    push((short)(short)TypeUtil.castToByte(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      ShortSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else if(arr.length!=0){
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
            if((v=(short)val)==val){
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
            if((v=(short)val)==val){
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
  public short peekShort(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      return (short)(head.val);
    }
    return Short.MIN_VALUE;
  }
  public Short peek(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      return (Short)(head.val);
    }
    return null;
  }
  public double peekDouble(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      return (double)(head.val);
    }
    return Double.NaN;
  }
  public float peekFloat(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      return (float)(head.val);
    }
    return Float.NaN;
  }
  public long peekLong(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      return (long)(head.val);
    }
    return Long.MIN_VALUE;
  }
  public int peekInt(){
    final ShortSnglLnkNode head;
    if((head=this.head)!=null){
      return (int)(head.val);
    }
    return Integer.MIN_VALUE;
  }
  abstract boolean uncheckedremoveIf(ShortSnglLnkNode head,ShortPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(Collection<? extends Short> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfRef<? extends Short> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedStack(OmniCollection.ShortOutput<?> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfByte that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfShort that){
      super(that);
    }  
    public CheckedStack(){
    }
    CheckedStack(ShortSnglLnkNode head,int size){
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
    @Override public void push(short val){
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
      ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        ShortSnglLnkNode newHead;
        for(clone=new CheckedStack(newHead=new ShortSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new ShortSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public short popShort(){
      ShortSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
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
    private int removeIfHelper(ShortSnglLnkNode prev,ShortPredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(ShortSnglLnkNode head,ShortPredicate filter){
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
      {
        if(val==(head.val)){
          this.head=head.next;
        }else{
          ShortSnglLnkNode prev;
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
    @Override public OmniIterator.OfShort iterator(){
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
      @Override void uncheckedForEachRemaining(final ShortSnglLnkNode expectedNext,ShortConsumer action){
        final int modCount=this.modCount;
        ShortSnglLnkNode prev,curr,next;
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
        final ShortSnglLnkNode prev;
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
  public static class UncheckedStack extends ShortSnglLnkSeq implements OmniStack.OfShort{
    private static final long serialVersionUID=1L;
    public UncheckedStack(Collection<? extends Short> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfRef<? extends Short> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedStack(OmniCollection.ShortOutput<?> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfByte that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfShort that){
      super(that);
    }  
    public UncheckedStack(){
    }
    UncheckedStack(ShortSnglLnkNode head,int size){
      super(head,size);
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        ShortSnglLnkNode curr;
        for(this.head=curr=new ShortSnglLnkNode((short)in.readShort());--size!=0;curr=curr.next=new ShortSnglLnkNode((short)in.readShort())){}
      }
    }
    @Override public void push(short val){
      this.head=new ShortSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public Object clone(){
      ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        ShortSnglLnkNode newHead;
        for(clone=new UncheckedStack(newHead=new ShortSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new ShortSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public short popShort(){
      ShortSnglLnkNode head;
      this.head=(head=this.head).next;
      --this.size;
      return head.val;
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
            if((v=(short)val)==val){
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
    @Override public Short pop(){
      return popShort();
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
    @Override boolean uncheckedremoveIf(ShortSnglLnkNode head,ShortPredicate filter){
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
        ShortSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveVal(ShortSnglLnkNode head
      ,int val
    ){
      {
        if(val==(head.val)){
          this.head=head.next;
        }else{
          ShortSnglLnkNode prev;
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
    @Override public OmniIterator.OfShort iterator(){
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
  public static class CheckedQueue extends UncheckedQueue{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedQueue(Collection<? extends Short> that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfRef<? extends Short> that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedQueue(OmniCollection.ShortOutput<?> that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfByte that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfShort that){
      super(that);
    }  
    public CheckedQueue(){
      super();
    }
    CheckedQueue(ShortSnglLnkNode head,int size,ShortSnglLnkNode tail){
      super(head,size,tail);
    }
    @Override public short shortElement(){
      final ShortSnglLnkNode head;
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
          ShortSnglLnkNode curr;
          for(curr=this.head;;curr=curr.next){
            out.writeShort(curr.val);
            if(curr==tail){
              return;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void forEach(ShortConsumer action){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          ShortSnglLnkNode.uncheckedForEach(head,tail,action);
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
          ShortSnglLnkNode.uncheckedForEach(head,tail,action::accept);
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
      ShortSnglLnkNode head;
      if((head=this.head)!=null){
        ShortSnglLnkNode newHead=new ShortSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new ShortSnglLnkNode((head=head.next).val)){}
        return new CheckedQueue(newHead,this.size,newTail);
      }
      return new CheckedQueue();
    }
    @Override void push(short val){
      ++this.modCount;
      super.push(val);
    }
    @Override public short removeShort(){
      final ShortSnglLnkNode head;
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
    @Override public short pollShort(){
      final ShortSnglLnkNode head;
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
      return Short.MIN_VALUE;
    }
    @Override public Short poll(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Short)(head.val);
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
      final ShortSnglLnkNode head;
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
      final ShortSnglLnkNode head;
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
      final ShortSnglLnkNode head;
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
      final ShortSnglLnkNode head;
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
    private void pullSurvivorsDown(ShortSnglLnkNode prev,ShortPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(ShortSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(ShortSnglLnkNode prev,ShortPredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(ShortSnglLnkNode head,ShortPredicate filter){
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
    @Override boolean uncheckedremoveVal(ShortSnglLnkNode head
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
          ShortSnglLnkNode prev;
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
    @Override public OmniIterator.OfShort iterator(){
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
      @Override void uncheckedForEachRemaining(final ShortSnglLnkNode expectedNext,ShortConsumer action){
        final int modCount=this.modCount;
        ShortSnglLnkNode prev,curr,next;
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
        final ShortSnglLnkNode prev;
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
  public static class UncheckedQueue extends ShortSnglLnkSeq implements OmniQueue.OfShort{
    private static final long serialVersionUID=1L;
    transient ShortSnglLnkNode tail;
    public UncheckedQueue(Collection<? extends Short> that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfRef<? extends Short> that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.ShortOutput<?> that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfByte that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfShort that){
      super(that);
    }  
    public UncheckedQueue(){
      super();
    }
    UncheckedQueue(ShortSnglLnkNode head,int size,ShortSnglLnkNode tail){
      super(head,size);
      this.tail=tail;
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        ShortSnglLnkNode curr;
        for(this.head=curr=new ShortSnglLnkNode((short)in.readShort());--size!=0;curr=curr.next=new ShortSnglLnkNode((short)in.readShort())){}
        this.tail=curr;
      }
    }
    @Override public void clear(){
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    @Override public Object clone(){
      ShortSnglLnkNode head;
      if((head=this.head)!=null){
        ShortSnglLnkNode newHead=new ShortSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new ShortSnglLnkNode((head=head.next).val)){}
        return new UncheckedQueue(newHead,this.size,newTail);
      }
      return new UncheckedQueue();
    }
    @Override void push(short val){
      final var newNode=new ShortSnglLnkNode(val);
      final ShortSnglLnkNode tail;
      if((tail=this.tail)!=null){
        tail.next=newNode;
      }else{
        this.head=newNode;
      }
      this.tail=newNode;
      ++this.size;
    }
    @Override public short shortElement(){
      return head.val;
    }
    @Override public boolean offer(short val){
      push(val);
      return true;
    }
    @Override public short removeShort(){
      final ShortSnglLnkNode head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public short pollShort(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override public Short remove(){
      return removeShort();
    }
    @Override public Short element(){
      return shortElement();
    }
    @Override public boolean offer(Short val){
      push((short)val);
      return true;
    }
    @Override public Short poll(){
      final ShortSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Short)(head.val);
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
      final ShortSnglLnkNode head;
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
      final ShortSnglLnkNode head;
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
      final ShortSnglLnkNode head;
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
      final ShortSnglLnkNode head;
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
    private int removeIfHelper(ShortSnglLnkNode prev,ShortSnglLnkNode tail,ShortPredicate filter){
      int numSurvivors=1;
      outer:for(ShortSnglLnkNode next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(ShortSnglLnkNode prev,ShortSnglLnkNode curr,ShortSnglLnkNode tail,ShortPredicate filter){
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
    @Override boolean uncheckedremoveIf(ShortSnglLnkNode head,ShortPredicate filter){
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
          final ShortSnglLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveVal(ShortSnglLnkNode head
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
          ShortSnglLnkNode prev;
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
    @Override public OmniIterator.OfShort iterator(){
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
        final ShortSnglLnkNode prev;
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
      extends AbstractShortItr
  {
    transient ShortSnglLnkNode prev;
    transient ShortSnglLnkNode curr;
    transient ShortSnglLnkNode next;
    AbstractItr(AbstractItr itr){
      this.prev=itr.prev;
      this.curr=itr.curr;
      this.next=itr.next;
    }
    AbstractItr(ShortSnglLnkNode next){
      this.next=next; 
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
    void uncheckedForEachRemaining(ShortSnglLnkNode next,ShortConsumer action){
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
  }
}
