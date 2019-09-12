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
import omni.impl.AbstractOmniCollection;
import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import omni.util.HashUtil;
public abstract class FloatSnglLnkSeq extends 
AbstractOmniCollection<Float>
 implements OmniCollection.OfFloat,Externalizable{
  static final class Node{
    transient float val;
    transient Node next;
    Node(float val){
      this.val=val;
    }
    Node(float val,Node next){
      this.val=val;
      this.next=next;
    }
    public static  int uncheckedToString(Node curr,byte[] buffer){
      int bufferOffset=1;
      for(;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
        bufferOffset=ToStringUtil.getStringFloat(curr.val,buffer,bufferOffset);
        if((curr=curr.next)==null){
          return bufferOffset;
        }
      }
    }
    public static  void uncheckedToString(Node curr,ToStringUtil.OmniStringBuilderByte builder){
      for(;;builder.uncheckedAppendCommaAndSpace()){
        builder.uncheckedAppendFloat(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  int uncheckedHashCode(Node curr){
      int hash=31+HashUtil.hashFloat(curr.val);
      for(;(curr=curr.next)!=null;hash=(hash*31)+HashUtil.hashFloat(curr.val)){}
      return hash;
    }
    public static  void uncheckedForEach(Node curr,Node tail,FloatConsumer action){
      for(action.accept(curr.val);curr!=tail;action.accept((curr=curr.next).val)){}
    }
    public static  void uncheckedForEach(Node curr,FloatConsumer action){
      do{
        action.accept(curr.val);
      }while((curr=curr.next)!=null);
    }
    public static  boolean uncheckedcontainsBits(Node curr
    ,int bits
    ){
      for(;bits!=Float.floatToRawIntBits(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    public static  int uncheckedsearchBits(Node curr
    ,int bits
    ){
      int index=1;
      for(;bits!=Float.floatToRawIntBits(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    public static  boolean uncheckedcontains0(Node curr
    ){
      for(;0!=(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    public static  int uncheckedsearch0(Node curr
    ){
      int index=1;
      for(;0!=(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    public static  boolean uncheckedcontainsNaN(Node curr
    ){
      for(;!Float.isNaN(curr.val);){if((curr=curr.next)==null){return false;}}
      return true;
    }
    public static  int uncheckedsearchNaN(Node curr
    ){
      int index=1;
      for(;!Float.isNaN(curr.val);++index){if((curr=curr.next)==null){return -1;}}
      return index;
    }
    public static  void uncheckedCopyInto(Node curr,float[] dst){
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
    public static  void uncheckedCopyInto(Node curr,Float[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    public static  void uncheckedCopyInto(Node curr,double[] dst){
      for(int dstOffset=0;;++dstOffset){
        dst[dstOffset]=(double)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
  }
  private static final long serialVersionUID=1L;
  transient Node head;
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
  private FloatSnglLnkSeq(Node head,int size){
    super(size);
    this.head=head;
  }
  private static  void pullSurvivorsDown(Node prev,FloatPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
  private static  int markSurvivors(Node curr,FloatPredicate filter,long[] survivorSet){
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
  private static  void pullSurvivorsDown(Node prev,long word,int numSurvivors,int numRemoved){
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
  private static  long markSurvivors(Node curr,FloatPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test(curr.val)){
        word|=marker;
      }
      if((curr=curr.next)==null){
        return word;
      }
    }
  }
  private static  int retainSurvivors(Node prev, final FloatPredicate filter){
    int numSurvivors=1;
    outer:for(Node next;(next=prev.next)!=null;++numSurvivors,prev=next){
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
  private static  int retainTrailingSurvivors(Node prev,Node curr,final FloatPredicate filter){
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
    final Node head;
    if((head=this.head)!=null){
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
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
  @Override public void forEach(FloatConsumer action){
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedForEach(head,action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
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
    final Node head;
    if((head=this.head)!=null){
      Node.uncheckedCopyInto(head,arr=OmniArray.uncheckedArrResize(size,arr));
    }else if(arr.length!=0){
      arr[0]=null;
    }
    return arr;
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
  @Override public Float[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Float[] dst;
      Node.uncheckedCopyInto(head,dst=new Float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
    @Override public boolean contains(boolean val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val){
              return Node.uncheckedcontainsBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return Node.uncheckedcontains0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedcontains0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedcontains0(head);
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
            if(val==val){
              return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedcontainsNaN(head);
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
            final float v;
            if(val==(v=(float)val)){
              return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return Node.uncheckedcontainsNaN(head);
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(f));
                }
                return Node.uncheckedcontainsNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return Node.uncheckedcontainsNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedcontains0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(l));
                }
                return Node.uncheckedcontains0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedcontains0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedcontains0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return Node.uncheckedcontainsBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return Node.uncheckedcontains0(head);
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
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedcontains0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return Node.uncheckedcontainsBits(head,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedcontains0(head);
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
          final Node head;
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
  abstract boolean uncheckedremoveValBits(Node head,int bits);
  abstract boolean uncheckedremoveVal0(Node head);
  abstract boolean uncheckedremoveValNaN(Node head);
  @Override public boolean removeIf(FloatPredicate filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter);
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super Float> filter){
    final Node head;
    if((head=this.head)!=null){
      return uncheckedremoveIf(head,filter::test);
    }
    return false;
  }
  public float peekFloat(){
    final Node head;
    if((head=this.head)!=null){
      return (float)(head.val);
    }
    return Float.NaN;
  }
  public Float peek(){
    final Node head;
    if((head=this.head)!=null){
      return (Float)(head.val);
    }
    return null;
  }
  public double peekDouble(){
    final Node head;
    if((head=this.head)!=null){
      return (double)(head.val);
    }
    return Double.NaN;
  }
  abstract boolean uncheckedremoveIf(Node head,FloatPredicate filter);
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
      Node head;
      if((head=this.head)!=null){
        final CheckedStack clone;
        Node newHead;
        for(clone=new CheckedStack(newHead=new Node(head.val),this.size);(head=head.next)!=null;newHead=newHead.next=new Node(head.val)){}
        return clone;
      }
      return new CheckedStack();
    }
    @Override public float popFloat(){
      Node head;
      if((head=this.head)!=null){
        ++this.modCount;
        this.head=head.next;
        --this.size;
        return head.val;
      }
      throw new NoSuchElementException();
    }
    @Override public void forEach(FloatConsumer action){
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
    @Override public void forEach(Consumer<? super Float> action){
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
    private int removeIfHelper(Node prev,FloatPredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(Node head,FloatPredicate filter){
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
      final Node head;
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
      final Node head;
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
      final Node head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        ++this.modCount;
        return ret;
      }
      return Double.NaN;
    }
    @Override boolean uncheckedremoveValBits(Node head
      ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
    @Override boolean uncheckedremoveVal0(Node head
    ){
      {
        if(0==(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
    @Override boolean uncheckedremoveValNaN(Node head
    ){
      {
        if(Float.isNaN(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
        final Node next;
        if((next=this.next)!=null){
          this.next=next.next;
          this.prev=this.curr;
          this.curr=next;
          return next.val;
        }
        throw new NoSuchElementException();
      }
      @Override void uncheckedForEachRemaining(final Node expectedNext,FloatConsumer action){
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
    UncheckedStack(Node head,int size){
      super(head,size);
    }
    @Override public void readExternal(ObjectInput in) throws IOException
    {
      int size;
      this.size=size=in.readInt();
      if(size!=0){
        Node curr;
        for(this.head=curr=new Node((float)in.readFloat());--size!=0;curr=curr.next=new Node((float)in.readFloat())){}
      }
    }
    @Override public void push(float val){
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
    @Override public float popFloat(){
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
            if(val){
              return Node.uncheckedsearchBits(head,TypeUtil.FLT_TRUE_BITS);
            }
            return Node.uncheckedsearch0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val))
              {
                return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedsearch0(head);
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
            if(val!=0){
              if(TypeUtil.checkCastToFloat(val)){
                return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
              }
            }else{
              return Node.uncheckedsearch0(head);
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
            if(val==val){
              return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedsearchNaN(head);
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
            final float v;
            if(val==(v=(float)val)){
              return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return Node.uncheckedsearchNaN(head);
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(f));
                }
                return Node.uncheckedsearchNaN(head);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return Node.uncheckedsearchNaN(head);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedsearch0(head);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(l));
                }
                return Node.uncheckedsearch0(head);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedsearch0(head);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(i));
                }
                return Node.uncheckedsearch0(head);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return Node.uncheckedsearchBits(head,TypeUtil.FLT_TRUE_BITS);
                }
                return Node.uncheckedsearch0(head);
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
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final Node head;
          if((head=this.head)!=null)
          {
            if(val!=0){
              return Node.uncheckedsearchBits(head,Float.floatToRawIntBits(val));
            }
            return Node.uncheckedsearch0(head);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public float pollFloat(){
      final Node head;
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
      final Node head;
      if((head=this.head)!=null){
        final var ret=(Float)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      final Node head;
      if((head=this.head)!=null){
        final var ret=(double)(head.val);
        this.head=head.next;
        --this.size;
        return ret;
      }
      return Double.NaN;
    }
    @Override boolean uncheckedremoveIf(Node head,FloatPredicate filter){
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
        Node prev;
        for(int numSurvivors=1;(head=(prev=head).next)!=null;++numSurvivors){
          if(filter.test(head.val)){
            this.size=numSurvivors+retainTrailingSurvivors(prev,head.next,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveValBits(Node head
      ,int bits
    ){
      {
        if(bits==Float.floatToRawIntBits(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
    @Override boolean uncheckedremoveVal0(Node head
    ){
      {
        if(0==(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
    @Override boolean uncheckedremoveValNaN(Node head
    ){
      {
        if(Float.isNaN(head.val)){
          this.head=head.next;
        }else{
          Node prev;
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
    CheckedQueue(Node head,int size,Node tail){
      super(head,size,tail);
    }
    @Override public float floatElement(){
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
    @Override public void forEach(Consumer<? super Float> action){
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
    @Override void push(float val){
      ++this.modCount;
      super.push(val);
    }
    @Override public float removeFloat(){
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
    @Override public float pollFloat(){
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
      return Float.NaN;
    }
    @Override public Float poll(){
      final Node head;
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
      final Node head;
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
    private void pullSurvivorsDown(Node prev,FloatPredicate filter,long[] survivorSet,int numSurvivors,int numRemoved){
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
    private void pullSurvivorsDown(Node prev,long word,int numSurvivors,int numRemoved){
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
    private int removeIfHelper(Node prev,FloatPredicate filter,int numLeft,int modCount){
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
    @Override boolean uncheckedremoveIf(Node head,FloatPredicate filter){
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
    @Override boolean uncheckedremoveValBits(Node head
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
          Node prev;
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
    @Override boolean uncheckedremoveVal0(Node head
    ){
      {
        final var tail=this.tail;
        if(0==(head.val)){
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
    @Override boolean uncheckedremoveValNaN(Node head
    ){
      {
        final var tail=this.tail;
        if(Float.isNaN(head.val)){
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
        final Node next;
        if((next=this.next)!=null){
          this.next=next.next;
          this.prev=this.curr;
          this.curr=next;
          return next.val;
        }
        throw new NoSuchElementException();
      }
      @Override void uncheckedForEachRemaining(final Node expectedNext,FloatConsumer action){
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
  public static class UncheckedQueue extends FloatSnglLnkSeq implements OmniQueue.OfFloat{
    private static final long serialVersionUID=1L;
    transient Node tail;
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
        for(this.head=curr=new Node((float)in.readFloat());--size!=0;curr=curr.next=new Node((float)in.readFloat())){}
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
    @Override void push(float val){
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
    @Override public float floatElement(){
      return head.val;
    }
    @Override public boolean offer(float val){
      push(val);
      return true;
    }
    @Override public float removeFloat(){
      final Node head;
      if((head=this.head)==tail){
        this.tail=null;
      }
      this.head=head.next;
      --this.size;
      return head.val;
    }
    @Override public float pollFloat(){
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
      final Node head;
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
      final Node head;
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
    private int removeIfHelper(Node prev,Node tail,FloatPredicate filter){
      int numSurvivors=1;
      outer:for(Node next;prev!=tail;++numSurvivors,prev=next){
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
    private int removeIfHelper(Node prev,Node curr,Node tail,FloatPredicate filter){
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
    @Override boolean uncheckedremoveIf(Node head,FloatPredicate filter){
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
          final Node prev;
          if(filter.test((head=(prev=head).next).val)){
            this.size=numSurvivors+removeIfHelper(prev,head,tail,filter);
            return true;
          }
        }
        return false;
      }
    }
    @Override boolean uncheckedremoveValBits(Node head
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
          Node prev;
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
    @Override boolean uncheckedremoveVal0(Node head
    ){
      {
        final var tail=this.tail;
        if(0==(head.val)){
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
    @Override boolean uncheckedremoveValNaN(Node head
    ){
      {
        final var tail=this.tail;
        if(Float.isNaN(head.val)){
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
      extends AbstractFloatItr
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
    @Override public float nextFloat(){
      final Node next;
      this.next=(next=this.next).next;
      this.prev=this.curr;
      this.curr=next;
      return next.val;
    }
    @Override public boolean hasNext(){
      return next!=null;
    }
    void uncheckedForEachRemaining(Node next,FloatConsumer action){
      Node prev,curr=this.curr;
      do{
        action.accept(next.val);
        prev=curr;
      }while((next=(curr=next).next)!=null);
      this.prev=prev;
      this.curr=curr;
      this.next=null;
    }
    @Override public void forEachRemaining(FloatConsumer action){
      final Node next;
      if((next=this.next)!=null){
        uncheckedForEachRemaining(next,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Float> action){
      final Node next;
      if((next=this.next)!=null){
        uncheckedForEachRemaining(next,action::accept);
      }
    }
  }
}
