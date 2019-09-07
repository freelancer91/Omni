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
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.util.TypeUtil;
import omni.impl.AbstractFloatItr;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.impl.FloatSnglLnkNode;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
public abstract class FloatSnglLnkSeq extends 
AbstractSeq<Float>
 implements OmniCollection.OfFloat,Externalizable{
  private static final long serialVersionUID=1L;
  transient FloatSnglLnkNode head;
  private FloatSnglLnkSeq(Collection<? extends Float> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatSnglLnkSeq(OmniCollection.OfRef<? extends Float> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatSnglLnkSeq(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatSnglLnkSeq(OmniCollection.FloatOutput<?> that){
    super();
    //TODO optimize;
    this.addAll(that);
  }
  private FloatSnglLnkSeq(OmniCollection.OfByte that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatSnglLnkSeq(OmniCollection.OfShort that){
    super();
    //TODO optimize
    this.addAll(that);
  }  
  private FloatSnglLnkSeq(OmniCollection.OfInt that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatSnglLnkSeq(OmniCollection.OfLong that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatSnglLnkSeq(OmniCollection.OfFloat that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatSnglLnkSeq(OmniCollection.OfChar that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatSnglLnkSeq(){
  }
  private FloatSnglLnkSeq(FloatSnglLnkNode head,int size){
    super(size);
    this.head=head;
  }
  private static  void pullSurvivorsDown(FloatSnglLnkNode prev,FloatPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
  private static  int markSurvivors(FloatSnglLnkNode curr,FloatPredicate filter,long[] survivorSet){
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
  private static  void pullSurvivorsDown(FloatSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
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
  private static  long markSurvivors(FloatSnglLnkNode curr,FloatPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  private static  int retainSurvivors(FloatSnglLnkNode prev, final FloatPredicate filter){
    int numSurvivors=1;
    outer:for(FloatSnglLnkNode next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  private static  int retainTrailingSurvivors(FloatSnglLnkNode prev,FloatSnglLnkNode curr,final FloatPredicate filter){
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
        out.writeFloat(curr.val);
      }
      while((curr=curr.next)!=null);
    }
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
  }
  @Override public String toString(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
        [size=FloatSnglLnkNode.uncheckedToString(head,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        FloatSnglLnkNode.uncheckedToString(head,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(FloatConsumer action){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      FloatSnglLnkNode.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      FloatSnglLnkNode.uncheckedForEach(head,action::accept);
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] arr=arrConstructor.apply(size=this.size);
    if(size!=0){
      FloatSnglLnkNode.uncheckedCopyInto(head,arr);
    }
    return arr;
  }
  abstract void push(float val);
  @Override public boolean add(float val){
    push((val));
    return true;
  }
  public void push(Float val){
    push((float)val);
  }
  @Override public boolean add(Float val){
    push((float)(val));
    return true;
  }
  @Override public boolean add(boolean val){
    push((float)TypeUtil.castToFloat(val));
    return true;
  }
  @Override public boolean add(int val){
    push((float)(val));
    return true;
  }
  @Override public boolean add(char val){
    push((float)(val));
    return true;
  }
  @Override public boolean add(short val){
    push((float)(val));
    return true;
  }
  @Override public boolean add(long val){
    push((float)(val));
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      FloatSnglLnkNode.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else if(arr.length!=0){
      arr[0]=null;
    }
    return arr;
  }
  @Override public float[] toFloatArray(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      final float[] dst;
      FloatSnglLnkNode.uncheckedCopyInto(head,dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public Float[] toArray(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      final Float[] dst;
      FloatSnglLnkNode.uncheckedCopyInto(head,dst=new Float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      final double[] dst;
      FloatSnglLnkNode.uncheckedCopyInto(head,dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
    @Override public boolean contains(boolean val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return FloatSnglLnkNode.uncheckedcontainsBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return FloatSnglLnkNode.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatSnglLnkNode.uncheckedcontains0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatSnglLnkNode.uncheckedcontains0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedcontainsNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return FloatSnglLnkNode.uncheckedcontainsNaN(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(f));
                }
                return FloatSnglLnkNode.uncheckedcontainsNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return FloatSnglLnkNode.uncheckedcontainsNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(l));
                }
                return FloatSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedcontains0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return FloatSnglLnkNode.uncheckedcontainsBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return FloatSnglLnkNode.uncheckedcontains0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatSnglLnkNode.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return uncheckedremoveVal0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveValNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return uncheckedremoveValNaN(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }
                return uncheckedremoveValNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return uncheckedremoveValNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(l));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return uncheckedremoveValBits(head,Float.floatToRawIntBits(i));
                }
                return uncheckedremoveVal0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return uncheckedremoveValBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return uncheckedremoveVal0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return uncheckedremoveValBits(head,Float.floatToRawIntBits(val));
            }
            return uncheckedremoveVal0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
  abstract boolean uncheckedremoveValBits(FloatSnglLnkNode head,int bits);
  abstract boolean uncheckedremoveVal0(FloatSnglLnkNode head);
  abstract boolean uncheckedremoveValNaN(FloatSnglLnkNode head);
  @Override public boolean removeIf(FloatPredicate filter){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Float> filter){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  public float peekFloat(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      return (float)(head.val);
    }
    return Float.NaN;
  }
  public Float peek(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      return (Float)(head.val);
    }
    return null;
  }
  public double peekDouble(){
    final FloatSnglLnkNode head;
    if((head=this.head)!=null){
      return (double)(head.val);
    }
    return Double.NaN;
  }
  abstract boolean uncheckedremoveIf(FloatSnglLnkNode head,FloatPredicate filter);
  public static class CheckedStack extends UncheckedStack{
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(Collection<? extends Float> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfRef<? extends Float> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedStack(OmniCollection.FloatOutput<?> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfByte that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfShort that){
      super(that);
    }  
    public CheckedStack(OmniCollection.OfInt that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfLong that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfFloat that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfChar that){
      super(that);
    }
    public CheckedStack(){
    }
    CheckedStack(FloatSnglLnkNode head,int size){
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
    @Override public void push(float val){
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
      FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        FloatSnglLnkNode newHead;
        for(clone=new CheckedStack(newHead=new FloatSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new FloatSnglLnkNode(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public float popFloat(){
      FloatSnglLnkNode head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void forEach(FloatConsumer action){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          FloatSnglLnkNode.uncheckedForEach(head,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Float> action){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          FloatSnglLnkNode.uncheckedForEach(head,action::accept);
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
    private int removeIfHelper(FloatSnglLnkNode prev,FloatPredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(FloatSnglLnkNode head,FloatPredicate filter){
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
    @Override public float pollFloat(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Float.NaN;
    }
    @Override public Float poll(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Float)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Double.NaN;
    }
    @Override boolean uncheckedremoveValBits(FloatSnglLnkNode head
      ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(bits!=Float.floatToRawIntBits(head.val));
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveVal0(FloatSnglLnkNode head
    ){
      {
        if(0==(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(0!=(head.val));
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveValNaN(FloatSnglLnkNode head
    ){
      {
        if(Float.isNaN(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(!Float.isNaN(head.val));
          prev.next=head.next;
        }
      }
      this.modCount=modCount+1;
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfFloat iterator(){
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
      @Override public float nextFloat(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final FloatSnglLnkNode next;
        if((next=this.next)!=null){
          this.next=next.next;
          this.prev=this.curr;
          this.curr=next;
          return next.val;
        }
        throw new NoSuchElementException();
      }
      @Override void uncheckedForEachRemaining(final FloatSnglLnkNode expectedNext,FloatConsumer action){
        final int modCount=this.modCount;
        FloatSnglLnkNode prev,curr,next;
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
        final FloatSnglLnkNode prev;
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
  public static class UncheckedStack extends FloatSnglLnkSeq implements OmniStack.OfFloat{
    private static final long serialVersionUID=1L;
    public UncheckedStack(Collection<? extends Float> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfRef<? extends Float> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedStack(OmniCollection.FloatOutput<?> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfByte that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfShort that){
      super(that);
    }  
    public UncheckedStack(OmniCollection.OfInt that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfLong that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfFloat that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfChar that){
      super(that);
    }
    public UncheckedStack(){
    }
    UncheckedStack(FloatSnglLnkNode head,int size){
      super(head,size);
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        FloatSnglLnkNode curr;
        for(this.head=curr=new FloatSnglLnkNode((float)in.readFloat());--size!=0;curr=curr.next=new FloatSnglLnkNode((float)in.readFloat())){}
      }
    }
    @Override public void push(float val){
      this.head=new FloatSnglLnkNode(val,this.head);
      ++this.size;
    }
    @Override public Object clone(){
      FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final UncheckedStack clone;
        FloatSnglLnkNode newHead;
        for(clone=new UncheckedStack(newHead=new FloatSnglLnkNode(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new FloatSnglLnkNode(head.val)){}
        return clone;
      }
      return new UncheckedStack();
    }
    @Override public float popFloat(){
      FloatSnglLnkNode head;
      this.head=(head=this.head).next;
      --this.size;
      return head.val;
    }
    @Override public int search(boolean val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val){
              return FloatSnglLnkNode.uncheckedsearchBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return FloatSnglLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatSnglLnkNode.uncheckedsearch0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return FloatSnglLnkNode.uncheckedsearch0(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val==val){
              return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedsearchNaN(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            final float v;
            if(val==(v=(float)val)){
              return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return FloatSnglLnkNode.uncheckedsearchNaN(head);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(f));
                }
                return FloatSnglLnkNode.uncheckedsearchNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return FloatSnglLnkNode.uncheckedsearchNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(l));
                }
                return FloatSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return FloatSnglLnkNode.uncheckedsearch0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return FloatSnglLnkNode.uncheckedsearchBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return FloatSnglLnkNode.uncheckedsearch0(head);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final FloatSnglLnkNode head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return FloatSnglLnkNode.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return FloatSnglLnkNode.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public float pollFloat(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public Float pop(){
      return popFloat();
    }
    @Override public Float poll(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override boolean uncheckedremoveIf(FloatSnglLnkNode head,FloatPredicate filter){
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
        FloatSnglLnkNode prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveValBits(FloatSnglLnkNode head
      ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(bits!=Float.floatToRawIntBits(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveVal0(FloatSnglLnkNode head
    ){
      {
        if(0==(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(0!=(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveValNaN(FloatSnglLnkNode head
    ){
      {
        if(Float.isNaN(head.val)){
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if((head=(prev=head).next)==null){
              return false;
            }
          }while(!Float.isNaN(head.val));
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfFloat iterator(){
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
        final FloatSnglLnkNode prev;
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
    public CheckedQueue(Collection<? extends Float> that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfRef<? extends Float> that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedQueue(OmniCollection.FloatOutput<?> that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfByte that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfShort that){
      super(that);
    }  
    public CheckedQueue(OmniCollection.OfInt that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfLong that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfFloat that){
      super(that);
    }
    public CheckedQueue(OmniCollection.OfChar that){
      super(that);
    }
    public CheckedQueue(){
      super();
    }
    CheckedQueue(FloatSnglLnkNode head,int size,FloatSnglLnkNode tail){
      super(head,size,tail);
    }
    @Override public float floatElement(){
      final FloatSnglLnkNode head;
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
          FloatSnglLnkNode curr;
          for(curr=this.head;;curr=curr.next){
            out.writeFloat(curr.val);
            if(curr==tail){
              return;
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void forEach(FloatConsumer action){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          FloatSnglLnkNode.uncheckedForEach(head,tail,action);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Float> action){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final int modCount=this.modCount;
        try{
          FloatSnglLnkNode.uncheckedForEach(head,tail,action::accept);
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
      FloatSnglLnkNode head;
      if((head=this.head)!=null){
        FloatSnglLnkNode newHead=new FloatSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new FloatSnglLnkNode((head=head.next).val)){}
        return new CheckedQueue(newHead,this.size,newTail);
      }
      return new CheckedQueue();
    }
    @Override void push(float val){
      ++this.modCount;
      super.push(val);
    }
    @Override public float removeFloat(){
      final FloatSnglLnkNode head;
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
    @Override public float pollFloat(){
      final FloatSnglLnkNode head;
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
      return Float.NaN;
    }
    @Override public Float poll(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Float)(head.val);
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
      final FloatSnglLnkNode head;
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
    private void pullSurvivorsDown(FloatSnglLnkNode prev,FloatPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(FloatSnglLnkNode prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(FloatSnglLnkNode prev,FloatPredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(FloatSnglLnkNode head,FloatPredicate filter){
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
    @Override boolean uncheckedremoveValBits(FloatSnglLnkNode head
    ,int bits
    ){
      {
        final var tail=this.tail;
        if(bits==Float.floatToRawIntBits(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(bits!=Float.floatToRawIntBits((head=(prev=head).next).val));
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
    @Override boolean uncheckedremoveVal0(FloatSnglLnkNode head
    ){
      {
        final var tail=this.tail;
        if(0==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(0!=((head=(prev=head).next).val));
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
    @Override boolean uncheckedremoveValNaN(FloatSnglLnkNode head
    ){
      {
        final var tail=this.tail;
        if(Float.isNaN(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(!Float.isNaN((head=(prev=head).next).val));
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
    @Override public OmniIterator.OfFloat iterator(){
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
      @Override public float nextFloat(){
        CheckedCollection.checkModCount(modCount,parent.modCount);
        final FloatSnglLnkNode next;
        if((next=this.next)!=null){
          this.next=next.next;
          this.prev=this.curr;
          this.curr=next;
          return next.val;
        }
        throw new NoSuchElementException();
      }
      @Override void uncheckedForEachRemaining(final FloatSnglLnkNode expectedNext,FloatConsumer action){
        final int modCount=this.modCount;
        FloatSnglLnkNode prev,curr,next;
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
        final FloatSnglLnkNode prev;
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
  public static class UncheckedQueue extends FloatSnglLnkSeq implements OmniQueue.OfFloat{
    private static final long serialVersionUID=1L;
    transient FloatSnglLnkNode tail;
    public UncheckedQueue(Collection<? extends Float> that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfRef<? extends Float> that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.FloatOutput<?> that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfByte that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfShort that){
      super(that);
    }  
    public UncheckedQueue(OmniCollection.OfInt that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfLong that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfFloat that){
      super(that);
    }
    public UncheckedQueue(OmniCollection.OfChar that){
      super(that);
    }
    public UncheckedQueue(){
      super();
    }
    UncheckedQueue(FloatSnglLnkNode head,int size,FloatSnglLnkNode tail){
      super(head,size);
      this.tail=tail;
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        FloatSnglLnkNode curr;
        for(this.head=curr=new FloatSnglLnkNode((float)in.readFloat());--size!=0;curr=curr.next=new FloatSnglLnkNode((float)in.readFloat())){}
        this.tail=curr;
      }
    }
    @Override public void clear(){
      this.head=null;
      this.tail=null;
      this.size=0;
    }
    @Override public Object clone(){
      FloatSnglLnkNode head;
      if((head=this.head)!=null){
        FloatSnglLnkNode newHead=new FloatSnglLnkNode(head.val),newTail=newHead;
        for(final var tail=this.tail;head!=tail;newTail=newTail.next=new FloatSnglLnkNode((head=head.next).val)){}
        return new UncheckedQueue(newHead,this.size,newTail);
      }
      return new UncheckedQueue();
    }
    @Override void push(float val){
      final var newNode=new FloatSnglLnkNode(val);
      final FloatSnglLnkNode tail;
      if((tail=this.tail)!=null){
        tail.next=newNode;
      }else{
        this.head=newNode;
      }
      this.tail=newNode;
      ++this.size;
    }
    @Override public float floatElement(){
      return head.val;
    }
    @Override public boolean offer(float val){
      push(val);
      return true;
    }
    @Override public float removeFloat(){
      final FloatSnglLnkNode head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public float pollFloat(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(head.val);
        if(head==this.tail){
          this.tail=null;
        }
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public Float remove(){
      return removeFloat();
    }
    @Override public Float element(){
      return floatElement();
    }
    @Override public boolean offer(Float val){
      push((float)val);
      return true;
    }
    @Override public Float poll(){
      final FloatSnglLnkNode head;
      if((head=this.head)!=null){
        final var ret=(Float)(head.val);
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
      final FloatSnglLnkNode head;
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
    private int removeIfHelper(FloatSnglLnkNode prev,FloatSnglLnkNode tail,FloatPredicate filter){
      int numSurvivors=1;
      outer:for(FloatSnglLnkNode next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(FloatSnglLnkNode prev,FloatSnglLnkNode curr,FloatSnglLnkNode tail,FloatPredicate filter){
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
    @Override boolean uncheckedremoveIf(FloatSnglLnkNode head,FloatPredicate filter){
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
          final FloatSnglLnkNode prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveValBits(FloatSnglLnkNode head
    ,int bits
    ){
      {
        final var tail=this.tail;
        if(bits==Float.floatToRawIntBits(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(bits!=Float.floatToRawIntBits((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveVal0(FloatSnglLnkNode head
    ){
      {
        final var tail=this.tail;
        if(0==(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(0!=((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override boolean uncheckedremoveValNaN(FloatSnglLnkNode head
    ){
      {
        final var tail=this.tail;
        if(Float.isNaN(head.val)){
          if(head==tail){
            this.tail=null;
          }
          this.head=head.next;
        }else{
          FloatSnglLnkNode prev;
          do{
            if(head==tail){
              return false;
            }
          }while(!Float.isNaN((head=(prev=head).next).val));
          if(head==tail){
            this.tail=prev;
          }
          prev.next=head.next;
        }
      }
      --this.size;
      return true;
    }
    @Override public OmniIterator.OfFloat iterator(){
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
        final FloatSnglLnkNode prev;
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
      extends AbstractFloatItr
  {
    transient FloatSnglLnkNode prev;
    transient FloatSnglLnkNode curr;
    transient FloatSnglLnkNode next;
    AbstractItr(AbstractItr itr){
      this.prev=itr.prev;
      this.curr=itr.curr;
      this.next=itr.next;
    }
    AbstractItr(FloatSnglLnkNode next){
      this.next=next; 
    }
    @Override public float nextFloat(){
      final FloatSnglLnkNode next;
      this.next=(next=this.next).next;
      this.prev=this.curr;
      this.curr=next;
      return next.val;
    }
    @Override public boolean hasNext(){
      return next!=null;
    }
    void uncheckedForEachRemaining(FloatSnglLnkNode next,FloatConsumer action){
      FloatSnglLnkNode prev,curr=this.curr;
      do{
        action.accept(next.val);
        prev=curr;
      }while((next=(curr=next).next)!=null);
      this.prev=prev;
      this.curr=curr;
      this.next=null;
    }
    @Override public void forEachRemaining(FloatConsumer action){
      final FloatSnglLnkNode next;
      if((next=this.next)!=null){
        uncheckedForEachRemaining(next,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Float> action){
      final FloatSnglLnkNode next;
      if((next=this.next)!=null){
        uncheckedForEachRemaining(next,action::accept);
      }
    }
  }
}
