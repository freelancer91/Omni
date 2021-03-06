package omni.impl.seq;
import java.util.List;
import java.util.Collection;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.IntSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.impl.AbstractOmniCollection;
import java.util.ListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import java.util.ConcurrentModificationException;
import java.util.function.IntUnaryOperator;
import omni.function.IntComparator;
import java.util.function.IntPredicate;
import java.util.function.IntConsumer;
import omni.util.ToStringUtil;
import omni.impl.AbstractIntItr;
import java.io.Externalizable;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.RandomAccess;
public abstract class IntArrSeq extends 
AbstractOmniCollection<Integer>
 implements OmniCollection.OfInt,Externalizable,RandomAccess{
  //TODO refactor the template and/or optimize code generation to make sure that the code generation doesn't take forever
  private static final long serialVersionUID=1L;
  transient int[] arr; 
  private IntArrSeq(){
    super();
    this.arr=OmniArray.OfInt.DEFAULT_ARR;
  }
  private IntArrSeq(Collection<? extends Integer> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private IntArrSeq(OmniCollection.OfRef<? extends Integer> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private IntArrSeq(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private IntArrSeq(OmniCollection.IntOutput<?> that){
    super();
    //TODO optimize;
    this.addAll(that);
  }
  private IntArrSeq(OmniCollection.OfByte that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private IntArrSeq(OmniCollection.OfShort that){
    super();
    //TODO optimize
    this.addAll(that);
  }  
  private IntArrSeq(OmniCollection.OfInt that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private IntArrSeq(OmniCollection.OfChar that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private IntArrSeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new int[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfInt.DEFAULT_ARR;
    case 0:
    }
  }
  private IntArrSeq(int size,int[] arr){
    super(size);
    this.arr=arr;
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException{
    int size;
    out.writeInt(size=this.size);
    if(size!=0)
    {
      OmniArray.OfInt.writeArray(arr,0,size-1,out);
    }
  }
  @Override public void readExternal(ObjectInput in) throws IOException
  {
    int size;
    this.size=size=in.readInt();
    if(size!=0){
      int[] arr;
      OmniArray.OfInt.readArray(arr=new int[size],0,size-1,in);
      this.arr=arr;
    }else{
      this.arr=OmniArray.OfInt.DEFAULT_ARR;
    }
  }
  public int popInt(){
    return (int)arr[--this.size];
  }
  static  long markSurvivors(int[] arr,int srcOffset,int srcBound,IntPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test((int)arr[srcOffset])){
        word|=marker;
      }
      if(++srcOffset==srcBound){
        return word;
      }
    }
  }
  static  int markSurvivors(int[] arr,int srcOffset,int srcBound,IntPredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test((int)arr[srcOffset])){
          word|=marker;
          ++numSurvivors;
        }
        if(++srcOffset==srcBound){
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
      }
      while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  static void pullSurvivorsDown(int[] arr,int srcOffset,int dstOffset,long word){
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  static void pullSurvivorsDown(int[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet){
    for(int wordOffset=0;;){
      long word=survivorSet[wordOffset];
      for(int s=srcOffset;;){
        int numTail0s;
        if((numTail0s=Long.numberOfTrailingZeros(word))==64){
          break;
        }
        ArrCopy.uncheckedSelfCopy(arr,dstOffset,s+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
        if((dstOffset+=numTail0s)>=dstBound){
          return;
        }else if(numTail0s==64){
          break;
        }
        s+=numTail0s;
        word>>>=numTail0s;
      }
      ++wordOffset;
      srcOffset+=64;
    }
  }
  @Override public void clear(){
    this.size=0;
  }
  @Override public String toString(){
    int size;
    if((size=this.size)!=0){
      final byte[] buffer;
      if(size<=(OmniArray.MAX_ARR_SIZE/13)){(buffer=new byte[size*13])
        [size=uncheckedToString(size,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  abstract int uncheckedToString(int size,byte[] buffer);
  abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder);
  @Override public boolean contains(boolean val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(int)TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int v;
          if((v=(int)val)==val){
            return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int v;
          if((double)val==(double)(v=(int)val))
          {
            return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int v;
          if(val==(v=(int)val))
          {
            return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              i=((Number)val).intValue();
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(int)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((double)(f=(float)val)!=(double)(i=(int)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(int)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(int)TypeUtil.castToByte(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(long val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int v;
          if((v=(int)val)==val){
            return this.uncheckedremoveVal(size,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(float val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int v;
          if((double)val==(double)(v=(int)val))
          {
            return this.uncheckedremoveVal(size,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(double val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int v;
          if(val==(v=(int)val))
          {
            return this.uncheckedremoveVal(size,v);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean remove(Object val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            final int i;
            if(val instanceof Integer||val instanceof Byte||val instanceof Short){
              i=((Number)val).intValue();
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(int)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((double)(f=(float)val)!=(double)(i=(int)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(int)d)){
                break returnFalse;
              }
            }else if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return this.uncheckedremoveVal(size,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(byte val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(char val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  abstract boolean uncheckedremoveVal(int size,int val);
  abstract void uncheckedForEach(int size,IntConsumer action);
  @Override public void forEach(IntConsumer action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action);
    }
  }
  @Override public void forEach(Consumer<? super Integer> action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action::accept);
    }
  }
  public void push(int val){
    final int size;
    if((size=this.size)!=0){
      uncheckedAppend(size,val);
    }else{
      uncheckedInit(val);
    }
  }
  @Override public boolean add(int val){
    push(val);
    return true;
  }
  @Override public boolean add(Integer val){
    push((int)val);
    return true;
  }
  @Override public boolean add(boolean val){
    push((int)(int)TypeUtil.castToByte(val));
    return true;
  }
  @Override public boolean add(char val){
    push((int)val);
    return true;
  }
  @Override public boolean add(byte val){
    push((int)val);
    return true;
  }
  abstract void uncheckedCopyInto(int[] dst,int length);
  @Override public <T> T[] toArray(T[] arr){
    final int size;
    if((size=this.size)!=0){
      uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr),size);
    }else if(arr.length!=0){
      arr[0]=null;
    }
    return arr;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      uncheckedCopyInto(dst,size);
    }
    return dst;
  }
  @Override public int[] toIntArray(){
    final int size;
    if((size=this.size)!=0){
      final int[] dst;
      uncheckedCopyInto(dst=new int[size],size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(Integer[] dst,int length);
  @Override public Integer[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Integer[] dst;
      uncheckedCopyInto(dst=new Integer[size],size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_BOXED_ARR;
  }
  abstract void uncheckedCopyInto(double[] dst,int length);
  @Override public double[] toDoubleArray(){
    final int size;
    if((size=this.size)!=0){
      final double[] dst;
      uncheckedCopyInto(dst=new double[size],size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(float[] dst,int length);
  @Override public float[] toFloatArray(){
    final int size;
    if((size=this.size)!=0){
      final float[] dst;
      uncheckedCopyInto(dst=new float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(long[] dst,int length);
  @Override public long[] toLongArray(){
    final int size;
    if((size=this.size)!=0){
      final long[] dst;
      uncheckedCopyInto(dst=new long[size],size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  boolean uncheckedRemoveIf(int size,IntPredicate filter){
    if(size!=(size-=uncheckedRemoveIfImpl(this.arr,0,size,filter))){
      this.size=size;
      return true;
    }
    return false;
  }
  @Override public boolean removeIf(IntPredicate filter){
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Integer> filter){
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
  }
  private
  void uncheckedAppend(int size,int val){
    int[] arr;
    if((arr=this.arr).length==size){
      ArrCopy.uncheckedCopy(arr,0,arr=new int[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  void uncheckedInit(int val){
    int[] arr;
    if((arr=this.arr)==null){
      this.arr=new int[]{val};
    }else{
      if(arr==OmniArray.OfInt.DEFAULT_ARR){
        this.arr=arr=new int[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  private static  int pullSurvivorsDown(int[] arr,int srcOffset,int srcBound,int dstOffset,IntPredicate filter){
    while(++srcOffset!=srcBound){
      final int v;
      if(!filter.test((int)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    return srcBound-dstOffset;
  }
  private static  int uncheckedRemoveIfImpl(int[] arr,int srcOffset,int srcBound,IntPredicate filter){
    do{
      if(filter.test((int)arr[srcOffset])){
        return pullSurvivorsDown(arr,srcOffset,srcBound,srcOffset,filter);
      }
    }while(++srcOffset!=srcBound);
    return 0;
  }
  private static  int uncheckedRemoveIfImpl(int[] arr,int srcOffset,int srcBound,IntPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker){
    do{
      if(filter.test((int)arr[srcOffset])){
        int dstOffset=srcOffset;
        outer:for(;;){
          if(++srcOffset==srcBound){
            modCountChecker.checkModCount();
            break outer;
          }
          int before;
          if(!filter.test((int)(before=arr[srcOffset]))){
            for(int i=srcBound-1;;--i){
              if(i==srcOffset){
                modCountChecker.checkModCount();
                arr[dstOffset++]=before;
                break outer;
              }
              int after;
              if(!filter.test((int)(after=arr[i]))){
                int n;
                if((n=i-(++srcOffset))!=0){
                  if(n>64){
                    long[] survivorSet;
                    int numSurvivors=markSurvivors(arr,srcOffset,i,filter,survivorSet=new long[(n-1>>6)+1]);
                    modCountChecker.checkModCount();
                    if(numSurvivors!=0){
                      if(numSurvivors==n){
                        System.arraycopy(arr,srcOffset-1,arr,dstOffset,numSurvivors+=2);
                        dstOffset+=numSurvivors;
                      }else{
                        arr[dstOffset]=before;
                        pullSurvivorsDown(arr,srcOffset,++dstOffset,dstOffset+=numSurvivors,survivorSet);
                        arr[dstOffset++]=after;
                      }
                      break outer;
                    }
                  }else{
                    long survivorWord=markSurvivors(arr,srcOffset,i,filter);
                    modCountChecker.checkModCount();
                    int numSurvivors;
                    if((numSurvivors=Long.bitCount(survivorWord))!=0){
                      if(numSurvivors==n){
                        ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset-1,numSurvivors+=2);
                        dstOffset+=numSurvivors;
                      }else{
                        arr[dstOffset]=before;
                        pullSurvivorsDown(arr,srcOffset,++dstOffset,survivorWord);
                        arr[dstOffset+=numSurvivors]=after;
                        ++dstOffset;
                      }
                      break outer;
                    }
                  }
                }else{
                  modCountChecker.checkModCount();
                }
                arr[dstOffset++]=before;
                arr[dstOffset++]=after;
                break outer;
              }
            }
          }
        }
        return srcBound-dstOffset;
      }
    }
    while(++srcOffset!=srcBound);
    return 0;
  }
  public
    static class UncheckedStack
      extends IntArrSeq
      implements OmniStack.OfInt,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    public UncheckedStack(Collection<? extends Integer> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfRef<? extends Integer> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedStack(OmniCollection.IntOutput<?> that){
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
    public UncheckedStack(OmniCollection.OfChar that){
      super(that);
    }
    public UncheckedStack(){
      super();
    }
    public UncheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedStack(int size,int[] arr){
      super(size,arr);
    }
    @Override public Object clone(){
      final int[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new int[size],0,size);
      }else{
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    int uncheckedToString(int size,byte[] buffer){
      return OmniArray.OfInt.descendingToString(this.arr,0,size-1,buffer,1);
    }
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder){
      OmniArray.OfInt.descendingToString(this.arr,0,size-1,builder);
    }
    @Override public int search(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfInt.uncheckedsearch(this.arr,size,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfInt.uncheckedsearch(this.arr,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
              return OmniArray.OfInt.uncheckedsearch(this.arr,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return OmniArray.OfInt.uncheckedsearch(this.arr,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return OmniArray.OfInt.uncheckedsearch(this.arr,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return OmniArray.OfInt.uncheckedsearch(this.arr,size,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveVal (int size
    ,int val
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        val==arr[index]
        )
        {
          OmniArray.OfInt.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,IntConsumer action){
      {
        OmniArray.OfInt.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractIntItr
    {
      transient final UncheckedStack parent;
      transient int cursor;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
      }
      private Itr(UncheckedStack parent){
        this.parent=parent;
        this.cursor=parent.size;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.cursor>0;
      }
      @Override public int nextInt(){
        return (int)parent.arr[--cursor];
      }
      @Override public void remove(){
        final UncheckedStack root;
        OmniArray.OfInt.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override public void forEachRemaining(IntConsumer action){
        final int cursor;
        if((cursor=this.cursor)>0){
          OmniArray.OfInt.descendingForEach(parent.arr,0,cursor-1,action);
          this.cursor=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final int cursor;
        if((cursor=this.cursor)>0){
          OmniArray.OfInt.descendingForEach(parent.arr,0,cursor-1,action::accept);
          this.cursor=0;
        }
      }
    }
    @Override public OmniIterator.OfInt iterator(){
      return new Itr(this);
    }
    @Override public void push(Integer val){
      push((int)val);
    }
    @Override void uncheckedCopyInto(int[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Integer[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(double[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(float[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(long[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override public Integer pop(){
      return popInt();
    }
    @Override public int pollInt(){
      int size;
      if((size=this.size)!=0){
        final var ret=(int)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int peekInt(){
      final int size;
      if((size=this.size)!=0){
        return (int)(arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
    @Override public Integer poll(){
      int size;
      if((size=this.size)!=0){
        final var ret=(Integer)(arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override public Integer peek(){
      final int size;
      if((size=this.size)!=0){
        return (Integer)(arr[size-1]);
      }
      return null;
    }
    @Override public double pollDouble(){
      int size;
      if((size=this.size)!=0){
        final var ret=(double)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public double peekDouble(){
      final int size;
      if((size=this.size)!=0){
        return (double)(arr[size-1]);
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      int size;
      if((size=this.size)!=0){
        final var ret=(float)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public float peekFloat(){
      final int size;
      if((size=this.size)!=0){
        return (float)(arr[size-1]);
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      int size;
      if((size=this.size)!=0){
        final var ret=(long)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLong(){
      final int size;
      if((size=this.size)!=0){
        return (long)(arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
  }
  public
    static class UncheckedList
      extends IntArrSeq
      implements IntListDefault,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    public UncheckedList(Collection<? extends Integer> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfRef<? extends Integer> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedList(OmniCollection.IntOutput<?> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfByte that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfShort that){
      super(that);
    }  
    public UncheckedList(OmniCollection.OfInt that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfChar that){
      super(that);
    }
    public UncheckedList(){
      super();
    }
    public UncheckedList(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedList(int size,int[] arr){
      super(size,arr);
    }
    boolean isEqualTo(ListIterator<?> itr,int thisOffset,int thisBound){
      Object val;
      if(itr.hasNext() && (val=itr.next()) instanceof Integer){
        for(final var arr=this.arr;(int)val==arr[thisOffset];){
          if(!itr.hasNext()){
            return thisOffset+1==thisBound;
          }
          if(++thisOffset>=thisBound || !((val=itr.next()) instanceof Integer)){
            break;
          }
        }
      }
      return false;
    }
    private boolean isEqualToHelper(int thisOffset,int thisBound,int[] thatArr,int thatOffset){
      for(final var thisArr=this.arr;thisArr[thisOffset]==thatArr[thatOffset];++thatOffset){
        if(++thisOffset==thisBound){
          return true;
        }  
      }
      return false;
    }
    private boolean isEqualTo(int rootOffset,int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> that;
        return size==(that=(RefArrSeq.UncheckedList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be RefDblLnkSeq
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    private boolean isEqualTo(int rootOffset,int size,OmniList.OfInt list){
      if(list instanceof IntArrSeq.UncheckedList){
        final IntArrSeq.UncheckedList uncheckedList;
        return (uncheckedList=(IntArrSeq.UncheckedList)list).size==size && ((uncheckedList==this) || this.isEqualToHelper(rootOffset,rootOffset+size,uncheckedList.arr,0));
      }else if(list instanceof IntArrSeq.UncheckedSubList){
        final IntArrSeq.UncheckedSubList subList;
        final int thatOffset=(subList=(IntArrSeq.UncheckedSubList)list).rootOffset;
        final IntArrSeq.UncheckedList thatRoot;
        return size==subList.size && (((thatRoot=subList.root)==this && thatOffset==rootOffset) || this.isEqualToHelper(rootOffset,rootOffset+size,thatRoot.arr,thatOffset));
      }else if(list instanceof IntArrSeq.CheckedSubList){
        final IntArrSeq.CheckedSubList subList;
        final IntArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount(((subList=(IntArrSeq.CheckedSubList)list)).modCount,(thatRoot=subList.root).modCount);
        return subList.size==size && this.isEqualToHelper(rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be IntDblLnkSeq
        final IntDblLnkSeq dls;
        if((dls=(IntDblLnkSeq)list) instanceof IntDblLnkSeq.CheckedSubList){
          final IntDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(IntDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    private boolean isEqualTo(int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> that;
        return size==(that=(RefArrSeq.UncheckedList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,that.arr,0);
      }else if(list instanceof RefDblLnkSeq){
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount,subList.root.modCount);
        }
        return dls.size==size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,dls.head);
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,subList.root.arr,subList.rootOffset);
      }else{
        //must be RefArrSeq.CheckedSubList
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,thatRoot.arr,subList.rootOffset);
      }
    }
    private boolean isEqualTo(int size,OmniList.OfInt list){
      if(list instanceof IntArrSeq.UncheckedList){
        final IntArrSeq.UncheckedList that;
        return size==(that=(IntArrSeq.UncheckedList)list).size && this.isEqualToHelper(0,size,that.arr,0);
      }else if(list instanceof IntDblLnkSeq){
        final IntDblLnkSeq dls;
        if((dls=(IntDblLnkSeq)list) instanceof IntDblLnkSeq.CheckedSubList){
          final IntDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(IntDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,dls.head);
      }else if(list instanceof IntArrSeq.UncheckedSubList){
        final IntArrSeq.UncheckedSubList subList;
        final IntArrSeq.UncheckedList thatRoot;
        return (subList=(IntArrSeq.UncheckedSubList)list).size==size && ((thatRoot=subList.root)==this || this.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset));
      }else{
        //must be IntArrSeq.CheckedSubList
        final IntArrSeq.CheckedSubList subList;
        final IntArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(IntArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && this.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset);
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractOmniCollection){
          if(list instanceof OmniList.OfInt){
            return this.isEqualTo(size,(OmniList.OfInt)list);
          }else if(list instanceof OmniList.OfRef){
            return this.isEqualTo(size,(OmniList.OfRef<?>)list);
          }
        }else{
          return this.isEqualTo(list.listIterator(),0,size);
        }
      }
      return false;
    }
    @Override public Object clone(){
      final int[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new int[size],0,size);
      }else{
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    int uncheckedToString(int size,byte[] buffer){
      return OmniArray.OfInt.ascendingToString(this.arr,0,size-1,buffer,1);
    }
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder){
      OmniArray.OfInt.ascendingToString(this.arr,0,size-1,builder);
    }
    @Override public int hashCode(){
      final int size;
      if((size=this.size)!=0){
        return OmniArray.OfInt.ascendingSeqHashCode(this.arr,0,size-1);
      }
      return 1;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfInt.uncheckedindexOf(this.arr,size,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfInt.uncheckedindexOf(this.arr,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
              return OmniArray.OfInt.uncheckedindexOf(this.arr,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return OmniArray.OfInt.uncheckedindexOf(this.arr,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return OmniArray.OfInt.uncheckedindexOf(this.arr,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return OmniArray.OfInt.uncheckedindexOf(this.arr,size,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfInt.uncheckedlastIndexOf(this.arr,size,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfInt.uncheckedlastIndexOf(this.arr,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
              return OmniArray.OfInt.uncheckedlastIndexOf(this.arr,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return OmniArray.OfInt.uncheckedlastIndexOf(this.arr,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return OmniArray.OfInt.uncheckedlastIndexOf(this.arr,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return OmniArray.OfInt.uncheckedlastIndexOf(this.arr,size,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveVal (int size
    ,int val
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        val==arr[index]
        )
        {
          OmniArray.OfInt.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,IntConsumer action){
      {
        OmniArray.OfInt.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractIntItr
    {
      transient final UncheckedList parent;
      transient int cursor;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
      }
      private Itr(UncheckedList parent){
        this.parent=parent;
        this.cursor=0;
      }
      private Itr(UncheckedList parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.cursor<parent.size;
      }
      @Override public int nextInt(){
        return (int)parent.arr[cursor++];
      }
      @Override public void remove(){
        final UncheckedList root;
        OmniArray.OfInt.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override public void forEachRemaining(IntConsumer action){
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfInt.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfInt.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override public OmniIterator.OfInt iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfInt{
      transient int lastRet;
      private ListItr(ListItr itr){
        super(itr);
        this.lastRet=itr.lastRet;
      }
      private ListItr(UncheckedList parent){
        super(parent);
      }
      private ListItr(UncheckedList parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr(this);
      }
      @Override public boolean hasPrevious(){
        return this.cursor>0;
      }
      @Override public int nextIndex(){
        return this.cursor;
      }
      @Override public int previousIndex(){
        return this.cursor-1;
      }
      @Override public int nextInt(){
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (int)parent.arr[lastRet];
      }
      @Override public void remove(){
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfInt.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override public void forEachRemaining(IntConsumer action){
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfInt.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfInt.ascendingForEach(parent.arr,cursor,cursor=bound-1,action::accept);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public int previousInt(){
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (int)parent.arr[lastRet];
      }
      @Override public void set(int val){
        parent.arr[this.lastRet]=val;
      }
      @Override public void add(int val){
        final UncheckedList root;
        final int rootSize;
        if((rootSize=(root=this.parent).size)!=0)
        {
          root.uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          root.uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfInt listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfInt listIterator(int index){
      return new ListItr(this,index);
    }
      void uncheckedInsert(int index,int size,int val){
        final int tailDist;
        if((tailDist=size-index)==0){
          super.uncheckedAppend(size,val);
        }else{
          int[] arr;
          if((arr=this.arr).length==size){
            final int[] tmp;
            ArrCopy.semicheckedCopy(arr,0,tmp=new int[OmniArray.growBy50Pct(size)],0,index);
            ArrCopy.uncheckedCopy(arr,index,tmp,index+1,tailDist);
            this.arr=arr=tmp;
          }else{
            ArrCopy.uncheckedCopy(arr,index,arr,index+1,tailDist);
          }
          arr[index]=val;
          this.size=size+1;
        }
      }
    @Override public void add(int index,int val){
      final int size;
      if((size=this.size)!=0){
        this
        .uncheckedInsert(index,size,val);
      }else{
        super.uncheckedInit(val);
      }
    }
    @Override void uncheckedCopyInto(int[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Integer[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(double[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(float[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(long[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override public void put(int index,int val){
      this.arr[index]=val;
    }
    @Override public int getInt(int index){
      return (int)this.arr[index];
    }
    @Override public int set(int index,int val){
      final int[] arr;
      final var ret=(int)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override public int removeIntAt(int index){
      final int[] arr;
      final var ret=(int)(arr=this.arr)[index];
      OmniArray.OfInt.removeIndexAndPullDown(arr,index,--size);
      return ret;
    }
    @Override public void replaceAll(IntUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        OmniArray.OfInt.uncheckedReplaceAll(this.arr,0,size,operator);
      }
    }
    @Override
    public void sort(IntComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          IntSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        IntSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override public void replaceAll(UnaryOperator<Integer> operator){
      final int size;
      if((size=this.size)!=0){
        OmniArray.OfInt.uncheckedReplaceAll(this.arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(Comparator<? super Integer> sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          IntSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
        }
      }
    }
    @Override
    public void unstableSort(IntComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          IntSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
  }
    static class UncheckedSubList
      extends AbstractOmniCollection<Integer>
      implements IntSubListDefault,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    transient final int rootOffset;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    private UncheckedSubList(UncheckedList root,int rootOffset,int size){
      super(size);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,int size){
      super(size);
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
    }
    @Override public boolean add(Integer val){
      return add((int)val);
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient int[] arr;
      private transient int size;
      private transient final int rootOffset;
      private SerializableSubList(int[] arr,int size,int rootOffset
      ){
        this.arr=arr;
        this.size=size;
        this.rootOffset=rootOffset;
      }
      private Object readResolve(){
        return new UncheckedList(size,arr);
      }
      private void readObject(ObjectInputStream ois) throws IOException
      {
        int size;
        this.size=size=ois.readInt();
        if(size!=0){
          int[] arr;
          OmniArray.OfInt.readArray(arr=new int[size],0,size-1,ois);
          this.arr=arr;
        }else{
          this.arr=OmniArray.OfInt.DEFAULT_ARR;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        {
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            final int rootOffset;
            OmniArray.OfInt.writeArray(arr,rootOffset=this.rootOffset,rootOffset+size-1,oos);
          }
        }
      }
    }
    private Object writeReplace(){
      return new SerializableSubList(root.arr,this.size,this.rootOffset);
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
         final int size;
         if((size=this.size)==0){
           return ((List<?>)val).isEmpty();
         }
         final List<?> list;
         if((list=(List<?>)val) instanceof AbstractOmniCollection){
            if(list instanceof OmniList.OfInt){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfInt)list);
            }else if(list instanceof OmniList.OfRef){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfRef<?>)list);
            }
         }else{
            final int rootOffset;
            return root.isEqualTo(list.listIterator(),rootOffset=this.rootOffset,size+rootOffset);
         }
      }
      return false;
    }
    @Override public Object clone(){
      final int[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new int[size],0,size);
      }else{
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override public String toString(){
      int size;
      if((size=this.size)!=0){
          final int rootOffset;
          final byte[] buffer;
          if(size<=(OmniArray.MAX_ARR_SIZE/13)){(buffer=new byte[size*13])
            [size=OmniArray.OfInt.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer,1)]=(byte)']';
            buffer[0]=(byte)'[';
            return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
          }else{
            final ToStringUtil.OmniStringBuilderByte builder;
            OmniArray.OfInt.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
            builder.uncheckedAppendChar((byte)']');
            (buffer=builder.buffer)[0]=(byte)'[';
            return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
          }
      }
      return "[]";
    }
  @Override public int hashCode(){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return OmniArray.OfInt.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    }
    return 1;
  }
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        for(var curr=parent;curr!=null;curr.size-=size,curr=curr.parent){}
        final UncheckedList root;
        (root=this.root).size=OmniArray.OfInt.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
              return this.uncheckedremoveVal(size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return this.uncheckedremoveVal(size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return this.uncheckedremoveVal(size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return this.uncheckedremoveVal(size,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
              final int rootOffset;
              return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              final int rootOffset;
              return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
              final int rootOffset;
              return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              final int rootOffset;
              return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
              return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
              return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    private boolean uncheckedremoveVal (int size
    ,int val
    ){
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        val==arr[index]
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfInt.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    @Override public void forEach(IntConsumer action){
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfInt.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
    }
    @Override public void forEach(Consumer<? super Integer> action){
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfInt.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
    }
    private static class Itr
      extends AbstractIntItr
    {
      transient final UncheckedSubList parent;
      transient int cursor;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
      }
      private Itr(UncheckedSubList parent){
        this.parent=parent;
        this.cursor=parent.rootOffset;
      }
      private Itr(UncheckedSubList parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        final UncheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override public int nextInt(){
        return (int)parent.root.arr[cursor++];
      }
      @Override public void remove(){
        UncheckedSubList parent;
        final UncheckedList root;
        OmniArray.OfInt.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override public void forEachRemaining(IntConsumer action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfInt.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfInt.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override public OmniIterator.OfInt iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfInt{
      transient int lastRet;
      private ListItr(ListItr itr){
        super(itr);
        this.lastRet=itr.lastRet;
      }
      private ListItr(UncheckedSubList parent){
        super(parent);
      }
      private ListItr(UncheckedSubList parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr(this);
      }
      @Override public boolean hasPrevious(){
        return this.cursor>parent.rootOffset;
      }
      @Override public int nextIndex(){
        return this.cursor-parent.rootOffset;
      }
      @Override public int previousIndex(){
        return this.cursor-parent.rootOffset-1;
      }
      @Override public int nextInt(){
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (int)parent.root.arr[lastRet];
      }
      @Override public void remove(){
        UncheckedSubList parent;
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfInt.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override public void forEachRemaining(IntConsumer action){
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfInt.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfInt.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action::accept);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public int previousInt(){
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (int)parent.root.arr[lastRet];
      }
      @Override public void set(int val){
        parent.root.arr[this.lastRet]=val;
      }
      @Override public void add(int val){
        final UncheckedList root;
        final int rootSize;
        UncheckedSubList parent;
        if((rootSize=(root=(parent=this.parent).root).size)!=0)
        {
          root.uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          root.uncheckedInit(val);
          ++this.cursor;
        }
        do{
          ++parent.size;
        }while((parent=parent.parent)!=null);
      }
    }
    @Override public OmniListIterator.OfInt listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfInt listIterator(int index){
      return new ListItr(this,index+this.rootOffset);
    }
    @Override public boolean add(int val){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        root.uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        root.uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override public void add(int index,int val){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        root.uncheckedInsert(this.rootOffset+index,rootSize,val);
      }else{
        root.uncheckedInit(val);
      }
    }
    @Override  public <T> T[] toArray(T[] arr){
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
      }else if(arr.length!=0){
        arr[0]=null;
      }
      return arr;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final int size;
      final T[] dst;
      {
        dst=arrConstructor.apply(size=this.size);
      }
      if(size!=0){
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
      }
      return dst;
    }
    @Override public int[] toIntArray(){
      final int size;
      if((size=this.size)!=0){
        final int[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public Integer[] toArray(){
      final int size;
      if((size=this.size)!=0){
        final Integer[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Integer[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      final int size;
      if((size=this.size)!=0){
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      final int size;
      if((size=this.size)!=0){
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      final int size;
      if((size=this.size)!=0){
        final long[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public void put(int index,int val){
      root.arr[index+this.rootOffset]=val;
    }
    @Override public int getInt(int index){
      return (int)root.arr[index+this.rootOffset];
    }
    @Override public int set(int index,int val){
      final int[] arr;
      final var ret=(int)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override public int removeIntAt(int index){
      final int[] arr;
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      final UncheckedList root;
      final var ret=(int)(arr=(root=this.root).arr)[index+=this.rootOffset];
      OmniArray.OfInt.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override public boolean removeIf(IntPredicate filter){
      final int size;
      if((size=this.size)!=0){
        {
          final int[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfInt.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Integer> filter){
      final int size;
      if((size=this.size)!=0){
        {
          final int[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfInt.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override public void replaceAll(IntUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        OmniArray.OfInt.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
    }
    @Override
    public void sort(IntComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          IntSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        IntSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override public void replaceAll(UnaryOperator<Integer> operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        OmniArray.OfInt.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }
    }
    @Override
    public void sort(Comparator<? super Integer> sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          IntSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
        }
      }
    }
    @Override
    public void unstableSort(IntComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          IntSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
      return new UncheckedSubList(this,this.rootOffset+fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class CheckedStack
      extends UncheckedStack
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(Collection<? extends Integer> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfRef<? extends Integer> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedStack(OmniCollection.IntOutput<?> that){
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
    public CheckedStack(OmniCollection.OfChar that){
      super(that);
    }
    public CheckedStack(){
      super();
    }
    public CheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    CheckedStack(int size,int[] arr){
      super(size,arr);
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return CheckedStack.this.modCount;
      }
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public Object clone(){
      final int[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new int[size],0,size);
      }else{
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new CheckedStack(size,copy);
    }
    @Override public void clear(){
      if(this.size!=0){
        ++this.modCount;
        this.size=0;
      }
    }
    @Override boolean uncheckedremoveVal (int size
    ,int val
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        val==arr[index]
        )
        {
          ++this.modCount;
          OmniArray.OfInt.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,IntConsumer action){
      int modCount=this.modCount;
      try
      {
        OmniArray.OfInt.descendingForEach(this.arr,0,size-1,action);
      }
      finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractIntItr
    {
      transient final CheckedStack parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
        this.lastRet=itr.lastRet;
        this.modCount=itr.modCount;
      }
      private Itr(CheckedStack parent){
        this.parent=parent;
        this.cursor=parent.size;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.cursor>0;
      }
      @Override public int nextInt(){
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        int cursor;
        if((cursor=this.cursor)>0)
        {  
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (int)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          int modCount;
          final CheckedStack root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfInt.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(IntConsumer action){
        final int cursor;
        if((cursor=this.cursor)>0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          try{
            OmniArray.OfInt.descendingForEach(parent.arr,0,cursor-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final int cursor;
        if((cursor=this.cursor)>0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          try{
            OmniArray.OfInt.descendingForEach(parent.arr,0,cursor-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
    }
    @Override public OmniIterator.OfInt iterator(){
      return new Itr(this);
    }
    @Override public void push(int val){
      ++this.modCount;
      super.push(val);
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public int popInt(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(int)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public int pollInt(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(int)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public Integer poll(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(Integer)(arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override public double pollDouble(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(double)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public float pollFloat(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(float)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public long pollLong(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(long)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override boolean uncheckedRemoveIf(int size,IntPredicate filter){
      final int modCount=this.modCount;
      try{
        if(size!=(size-=uncheckedRemoveIfImpl(this.arr
          ,0,size,filter,new ModCountChecker(modCount)))
          ){
          this.modCount=modCount+1;
          this.size=size;
          return true;
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
  }
  public
    static class CheckedList
      extends UncheckedList
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedList(Collection<? extends Integer> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfRef<? extends Integer> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedList(OmniCollection.IntOutput<?> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfByte that){
      super(that);
    }
    public CheckedList(OmniCollection.OfShort that){
      super(that);
    }  
    public CheckedList(OmniCollection.OfInt that){
      super(that);
    }
    public CheckedList(OmniCollection.OfChar that){
      super(that);
    }
    public CheckedList(){
      super();
    }
    public CheckedList(int initialCapacity){
      super(initialCapacity);
    }
    CheckedList(int size,int[] arr){
      super(size,arr);
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return CheckedList.this.modCount;
      }
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private boolean isEqualTo(int rootOffset,int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> that;
        return size==(that=(RefArrSeq.UncheckedList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else{
        //must be RefDblLnkSeq
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    private boolean isEqualTo(int rootOffset,int size,OmniList.OfInt list){
      if(list instanceof IntArrSeq.UncheckedList){
        final IntArrSeq.UncheckedList that;
        return size==(that=(IntArrSeq.UncheckedList)list).size && ((that==this)||(super.isEqualToHelper(rootOffset,rootOffset+size,that.arr,0)));
      }else if(list instanceof IntArrSeq.CheckedSubList){
        final IntArrSeq.CheckedSubList subList;
        final IntArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(IntArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return size==subList.size && (((thatOffset=subList.rootOffset)==rootOffset&&thatRoot==this)|| super.isEqualToHelper(rootOffset,rootOffset+size,thatRoot.arr,thatOffset));
      }else if(list instanceof IntArrSeq.UncheckedSubList){
        final IntArrSeq.UncheckedSubList subList;
        return size==(subList=(IntArrSeq.UncheckedSubList)list).size && super.isEqualToHelper(rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else{
        //must be IntDblLnkSeq
        final IntDblLnkSeq dls;
        if((dls=(IntDblLnkSeq)list) instanceof IntDblLnkSeq.CheckedSubList){
          final IntDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(IntDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    private boolean isEqualTo(int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> that;
        return size==(that=(RefArrSeq.UncheckedList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,that.arr,0);
      }else if(list instanceof RefDblLnkSeq){
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,dls.head);
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be RefArrSeq.UncheckedSubList
        final RefArrSeq.UncheckedSubList<?> subList;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,subList.root.arr,subList.rootOffset);
      }
    }
    private boolean isEqualTo(int size,OmniList.OfInt list){
      if(list instanceof IntArrSeq.UncheckedList){
        final IntArrSeq.UncheckedList that;
        return size==(that=(IntArrSeq.UncheckedList)list).size && super.isEqualToHelper(0,size,that.arr,0);
      }else if(list instanceof IntDblLnkSeq){
        final IntDblLnkSeq dls;
        if((dls=(IntDblLnkSeq)list) instanceof IntDblLnkSeq.CheckedSubList){
          final IntDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(IntDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,dls.head);
      }else if(list instanceof IntArrSeq.CheckedSubList){
        final IntArrSeq.CheckedSubList subList;
        final IntArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(IntArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && (thatRoot==this || super.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset));
      }else{
        //must be IntArrSeq.UncheckedSubList
        final IntArrSeq.UncheckedSubList subList;
        return size==(subList=(IntArrSeq.UncheckedSubList)list).size && super.isEqualToHelper(0,size,subList.root.arr,subList.rootOffset);
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
         final int size;
         if((size=this.size)==0){
           return ((List<?>)val).isEmpty();
         }
         final List<?> list;
         if((list=(List<?>)val) instanceof AbstractOmniCollection){
           if(list instanceof OmniList.OfInt){
             return this.isEqualTo(size,(OmniList.OfInt)list);
           }else if(list instanceof OmniList.OfRef){
             return this.isEqualTo(size,(OmniList.OfRef<?>)list);
           }
         }else{
           final int modCount=this.modCount;
           try{
             return super.isEqualTo(list.listIterator(),0,size);
           }finally{
             CheckedCollection.checkModCount(modCount,this.modCount);
           }
         }
      }
      return false;
    }
    @Override public Object clone(){
      final int[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new int[size],0,size);
      }else{
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override public void clear(){
      if(this.size!=0){
        ++this.modCount;
        this.size=0;
      }
    }
    @Override boolean uncheckedremoveVal (int size
    ,int val
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        val==arr[index]
        )
        {
          ++this.modCount;
          OmniArray.OfInt.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,IntConsumer action){
      int modCount=this.modCount;
      try
      {
        OmniArray.OfInt.ascendingForEach(this.arr,0,size-1,action);
      }
      finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractIntItr
    {
      transient final CheckedList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
        this.lastRet=itr.lastRet;
        this.modCount=itr.modCount;
      }
      private Itr(CheckedList parent){
        this.parent=parent;
        this.cursor=0;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedList parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.cursor<parent.size;
      }
      @Override public int nextInt(){
        final CheckedList root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)<root.size)
        {  
          this.lastRet=cursor;
          this.cursor=cursor+1;
          return (int)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          int modCount;
          final CheckedList root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfInt.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(IntConsumer action){
        final int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          final int lastRet;
          try{
            OmniArray.OfInt.ascendingForEach(parent.arr,cursor,lastRet=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          final int lastRet;
          try{
            OmniArray.OfInt.ascendingForEach(parent.arr,cursor,lastRet=bound-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
    }
    @Override public OmniIterator.OfInt iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfInt{
      private ListItr(ListItr itr){
        super(itr);
      }
      private ListItr(CheckedList parent){
        super(parent);
      }
      private ListItr(CheckedList parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr(this);
      }
      @Override public boolean hasPrevious(){
        return this.cursor>0;
      }
      @Override public int nextIndex(){
        return this.cursor;
      }
      @Override public int previousIndex(){
        return this.cursor-1;
      }
      @Override public int previousInt(){
        final CheckedList root;
        int cursor;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (int)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void set(int val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(int val){
        int modCount;
        final CheckedList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        this.lastRet=-1;
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          root.uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          root.uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfInt listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfInt listIterator(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index);
    }
    @Override public void push(int val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void add(int index,int val){
      final int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      if(size!=0){
        super
        .uncheckedInsert(index,size,val);
      }else{
        super.uncheckedInit(val);
      }
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public void put(int index,int val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      this.arr[index]=val;
    }
    @Override public int getInt(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (int)this.arr[index];
    }
    @Override public int set(int index,int val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final int[] arr;
      final var ret=(int)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override public int removeIntAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final int[] arr;
      ++this.modCount;
      final var ret=(int)(arr=this.arr)[index];
      OmniArray.OfInt.removeIndexAndPullDown(arr,index,--size);
      this.size=size;
      return ret;
    }
    @Override boolean uncheckedRemoveIf(int size,IntPredicate filter){
      final int modCount=this.modCount;
      try{
        if(size!=(size-=uncheckedRemoveIfImpl(this.arr
          ,0,size,filter,new ModCountChecker(modCount)))
          ){
          this.modCount=modCount+1;
          this.size=size;
          return true;
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override public void replaceAll(IntUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        int modCount=this.modCount;
        try{
          OmniArray.OfInt.uncheckedReplaceAll(this.arr,0,size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void sort(IntComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            IntSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        IntSortUtil.uncheckedDescendingSort(this.arr,0,size);
        this.modCount=modCount+1;
      }
    }
    @Override public void replaceAll(UnaryOperator<Integer> operator){
      final int size;
      if((size=this.size)!=0){
        int modCount=this.modCount;
        try{
          OmniArray.OfInt.uncheckedReplaceAll(this.arr,0,size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void sort(Comparator<? super Integer> sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            IntSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override
    public void unstableSort(IntComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            IntSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
      return new CheckedSubList(this,fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
    static class CheckedSubList
      extends AbstractOmniCollection<Integer>
      implements IntSubListDefault,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    transient final int rootOffset;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    private CheckedSubList(CheckedList root,int rootOffset,int size){
      super(size);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int rootOffset,int size){
      super(size);
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.modCount=parent.modCount;
    }
    @Override public boolean add(Integer val){
      return add((int)val);
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient int[] arr;
      private transient int size;
      private transient final int rootOffset;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(int[] arr,int size,int rootOffset
        ,CheckedList.ModCountChecker modCountChecker
      ){
        this.arr=arr;
        this.size=size;
        this.rootOffset=rootOffset;
        this.modCountChecker=modCountChecker;
      }
      private Object readResolve(){
        return new CheckedList(size,arr);
      }
      private void readObject(ObjectInputStream ois) throws IOException
      {
        int size;
        this.size=size=ois.readInt();
        if(size!=0){
          int[] arr;
          OmniArray.OfInt.readArray(arr=new int[size],0,size-1,ois);
          this.arr=arr;
        }else{
          this.arr=OmniArray.OfInt.DEFAULT_ARR;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try
        {
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            final int rootOffset;
            OmniArray.OfInt.writeArray(arr,rootOffset=this.rootOffset,rootOffset+size-1,oos);
          }
        }
        finally{
          modCountChecker.checkModCount();
        }
      }
    }
    private Object writeReplace(){
      final CheckedList root;
      return new SerializableSubList((root=this.root).arr,this.size,this.rootOffset,root.new ModCountChecker(this.modCount));
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int size;
          if((size=this.size)==0){
            return ((List<?>)val).isEmpty();
          }
          final List<?> list;
          if((list=(List<?>)val) instanceof AbstractOmniCollection){
            if(list instanceof OmniList.OfInt){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfInt)list);
            }else if(list instanceof OmniList.OfRef){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfRef<?>)list);
            }
          }else{
            final int rootOffset;
            return root.isEqualTo(list.listIterator(),rootOffset=this.rootOffset,rootOffset+size);
          }
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      return false;
    }
    @Override public Object clone(){
      final CheckedList root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      final int[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new int[size],0,size);
      }else{
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override public String toString(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
          final int rootOffset;
          final byte[] buffer;
          if(size<=(OmniArray.MAX_ARR_SIZE/13)){(buffer=new byte[size*13])
            [size=OmniArray.OfInt.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer,1)]=(byte)']';
            buffer[0]=(byte)'[';
            return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
          }else{
            final ToStringUtil.OmniStringBuilderByte builder;
            OmniArray.OfInt.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
            builder.uncheckedAppendChar((byte)']');
            (buffer=builder.buffer)[0]=(byte)'[';
            return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
          }
      }
      return "[]";
    }
  @Override public int hashCode(){
    final CheckedList root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return OmniArray.OfInt.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    }
    return 1;
  }
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override public void clear(){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=size,curr=curr.parent){}
        root.size=OmniArray.OfInt.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
              return this.uncheckedremoveVal(size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
              return this.uncheckedremoveVal(size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
              return this.uncheckedremoveVal(size,v);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return this.uncheckedremoveVal(size,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean contains(boolean val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
                final int rootOffset;
                return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
                final int rootOffset;
                return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
                final int rootOffset;
                return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
                final int rootOffset;
                return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,i);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfInt.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
                return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
                return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
                return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
                return OmniArray.OfInt.uncheckedindexOf(root.arr,this.rootOffset,size,i);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(int)TypeUtil.castToByte(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((v=(int)val)==val){
                return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if((double)val==(double)(v=(int)val))
            {
                return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int v;
            if(val==(v=(int)val))
            {
                return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              final int i;
              if(val instanceof Integer||val instanceof Byte||val instanceof Short){
                i=((Number)val).intValue();
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(int)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((double)(f=(float)val)!=(double)(i=(int)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(int)d)){
                  break returnFalse;
                }
              }else if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
                return OmniArray.OfInt.uncheckedlastIndexOf(root.arr,this.rootOffset,size,i);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    private boolean uncheckedremoveVal (int size
    ,int val
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        val==arr[index]
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfInt.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    @Override public void forEach(IntConsumer action){
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfInt.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void forEach(Consumer<? super Integer> action){
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfInt.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private static class Itr
      extends AbstractIntItr
    {
      transient final CheckedSubList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
        this.lastRet=itr.lastRet;
        this.modCount=itr.modCount;
      }
      private Itr(CheckedSubList parent){
        this.parent=parent;
        this.cursor=parent.rootOffset;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedSubList parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        final CheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override public int nextInt(){
        final CheckedList root;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)<(parent.rootOffset+parent.size))
        {  
          this.lastRet=cursor;
          this.cursor=cursor+1;
          return (int)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          int modCount;
          final CheckedList root;
          CheckedSubList parent;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          do{
            parent.modCount=modCount;
            --parent.size;
          }while((parent=parent.parent)!=null);
          this.modCount=modCount;
          OmniArray.OfInt.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(IntConsumer action){
        final int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          final int modCount=this.modCount;
          final var root=parent.root;
          final int lastRet;
          try{
            OmniArray.OfInt.ascendingForEach(root.arr,cursor,lastRet=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Integer> action){
        final int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          final int modCount=this.modCount;
          final var root=parent.root;
          final int lastRet;
          try{
            OmniArray.OfInt.ascendingForEach(root.arr,cursor,lastRet=bound-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
    }
    @Override public OmniIterator.OfInt iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfInt{
      private ListItr(ListItr itr){
        super(itr);
      }
      private ListItr(CheckedSubList parent){
        super(parent);
      }
      private ListItr(CheckedSubList parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr(this);
      }
      @Override public boolean hasPrevious(){
        return this.cursor>parent.rootOffset;
      }
      @Override public int nextIndex(){
        return this.cursor-parent.rootOffset;
      }
      @Override public int previousIndex(){
        return this.cursor-parent.rootOffset-1;
      }
      @Override public int previousInt(){
        final CheckedList root;
        int cursor;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        if((cursor=this.cursor)>parent.rootOffset)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (int)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void set(int val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(int val){
        int modCount;
        final CheckedList root;
        CheckedSubList parent;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
        root.modCount=++modCount;
        do{
          parent.modCount=modCount;
          ++parent.size;
        }while((parent=parent.parent)!=null);
        this.modCount=modCount;
        this.lastRet=-1;
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          root.uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          root.uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfInt listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfInt listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index+this.rootOffset);
    }
    @Override public boolean add(int val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      if((modCount=root.size)!=0){
        root.uncheckedInsert(this.rootOffset+(this.size++),modCount,val);
      }else{
        root.uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override public void add(int index,int val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      this.size=size+1;
      if((modCount=root.size)!=0){
        root.uncheckedInsert(this.rootOffset+index,modCount,val);
      }else{
        root.uncheckedInit(val);
      }
    }
    @Override  public <T> T[] toArray(T[] arr){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
      }else if(arr.length!=0){
        arr[0]=null;
      }
      return arr;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final int size;
      final T[] dst;
      final CheckedList root;
      int modCount=this.modCount;
      try
      {
        dst=arrConstructor.apply(size=this.size);
      }
      finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      if(size!=0){
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
      }
      return dst;
    }
    @Override public int[] toIntArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final int[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public Integer[] toArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final Integer[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Integer[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_BOXED_ARR;
    }
    @Override public double[] toDoubleArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final long[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public void put(int index,int val){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      root.arr[index+this.rootOffset]=val;
    }
    @Override public int getInt(int index){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (int)root.arr[index+this.rootOffset];
    }
    @Override public int set(int index,int val){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final int[] arr;
      final var ret=(int)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override public int removeIntAt(int index){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final int[] arr;
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
      final var ret=(int)(arr=root.arr)[index+=this.rootOffset];
      OmniArray.OfInt.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override public boolean removeIf(IntPredicate filter){
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0){
        try
        {
          final int[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter,root.new ModCountChecker(modCount)))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfInt.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
        catch(ConcurrentModificationException e){
          throw e;
        }catch(RuntimeException e){
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Integer> filter){
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0){
        try
        {
          final int[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test,root.new ModCountChecker(modCount)))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfInt.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
        catch(ConcurrentModificationException e){
          throw e;
        }catch(RuntimeException e){
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public void replaceAll(IntUnaryOperator operator){
      final int size;
      if((size=this.size)==0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        OmniArray.OfInt.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void sort(IntComparator sorter)
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final var root=this.root;
      int modCount=this.modCount;
      if(sorter==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        final int rootOffset;
        IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          IntSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        IntSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
      }
    }
    @Override public void replaceAll(UnaryOperator<Integer> operator){
      final int size;
      if((size=this.size)==0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        OmniArray.OfInt.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void sort(Comparator<? super Integer> sorter)
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final var root=this.root;
      int modCount=this.modCount;
      if(sorter==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        final int rootOffset;
        IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          IntSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
    }
    @Override
    public void unstableSort(IntComparator sorter)
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final var root=this.root;
      int modCount=this.modCount;
      if(sorter==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        final int rootOffset;
        IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          IntSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
    }
    @Override public OmniList.OfInt subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new CheckedSubList(this,this.rootOffset+fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
}
