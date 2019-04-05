package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.ShortSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import java.util.ConcurrentModificationException;
import omni.function.ShortUnaryOperator;
import omni.function.ShortComparator;
import omni.function.ShortPredicate;
import omni.function.ShortConsumer;
import omni.util.ToStringUtil;
import omni.util.BitSetUtil;
import omni.impl.AbstractShortItr;
public abstract class ShortArrSeq implements OmniCollection.OfShort,Cloneable
{
  transient int size;
  transient short[] arr;
  private ShortArrSeq()
  {
    super();
    this.arr=OmniArray.OfShort.DEFAULT_ARR;
  }
  private ShortArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new short[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfShort.DEFAULT_ARR;
    case 0:
    }
  }
  private ShortArrSeq(int size,short[] arr)
  {
    super();
    this.size=size;
    this.arr=arr;
  }
  @Override
  public abstract Object clone();
  @Override
  public int size()
  {
    return this.size;
  }
  @Override
  public boolean isEmpty()
  {
    return this.size==0;
  }
  @Override
  public void clear()
  {
    this.size=0;
  }
  @Override
  public int hashCode()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedHashCode(size);
    }
    return 1;
  }
  @Override
  public String toString()
  {
    int size;
    if((size=this.size)!=0)
    {
      final byte[] buffer;
      if(size<=(OmniArray.MAX_ARR_SIZE>>3)){(buffer=new byte[size<<3])
        [size=uncheckedToString(size,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  abstract int uncheckedHashCode(int size);
  abstract int uncheckedToString(int size,byte[] buffer);
  abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder);
  @Override
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(short)TypeUtil.castToByte(val));
    }
    return false;
  }
  @Override
  public boolean contains(int val)
  {
    if(val==(short)val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short v;
      if((v=(short)val)==val)
      {
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short v;
      if(val==(v=(short)val))
      {
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short v;
      if(val==(v=(short)val))
      {
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(Object val)
  {
    final int size;
    if((size=this.size)!=0){
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
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,i);
      }
  //#ELSE
  //    if(val instanceof Short)
  //    {
  //  #IF OfDouble,OfFloat
  //      final short v;
  //      if((v=(short)val)==v)
  //      {
  //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
  //      }
  //      #MACRO ReturnUncheckedQueryNaN()
  //  #ELSE
  //      #MACRO ReturnUncheckedQuery((short)(val))
  //  #ENDIF
  //    }
  //#ENDIF
    }
    return false;
  }
  @Override
  public boolean contains(byte val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(char val)
  {
    if(val<=Short.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,(short)TypeUtil.castToByte(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(int val)
  {
    if(val==(short)val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short v;
      if((v=(short)val)==val)
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short v;
      if(val==(v=(short)val))
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short v;
      if(val==(v=(short)val))
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean remove(Object val)
  {
    final int size;
    if((size=this.size)!=0){
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
        return this.uncheckedremoveVal(size,i);
      }
  //#ELSE
  //    if(val instanceof Short)
  //    {
  //  #IF OfDouble,OfFloat
  //      final short v;
  //      if((v=(short)val)==v)
  //      {
  //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
  //      }
  //      #MACRO ReturnUncheckedQueryNaN()
  //  #ELSE
  //      #MACRO ReturnUncheckedQuery((short)(val))
  //  #ENDIF
  //    }
  //#ENDIF
    }
    return false;
  }
  @Override
  public boolean removeVal(byte val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(char val)
  {
    if(val<=Short.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  abstract boolean uncheckedremoveVal(int size,int val);
  abstract void uncheckedForEach(int size,ShortConsumer action);
  @Override
  public void forEach(ShortConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Short> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  private void uncheckedAppend(int size,short val)
  {
    short[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new short[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(short val)
  {
    short[] arr;
    if((arr=this.arr)==null)
    {
      this.arr=new short[]{val};
    }
    else
    {
      if(arr==OmniArray.OfShort.DEFAULT_ARR)
      {
        this.arr=arr=new short[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  public void push(short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      uncheckedInit(val);
    }
  }
  @Override
  public boolean add(short val)
  {
    push(val);
    return true;
  }
  @Override
  public boolean add(Short val)
  {
    push((short)val);
    return true;
  }
  @Override
  public boolean add(boolean val)
  {
    push((short)(short)TypeUtil.castToByte(val));
    return true;
  }
  abstract void uncheckedCopyInto(short[] dst,int length);
  @Override
  public <T> T[] toArray(T[] arr)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr),size);
    }
    else if(arr.length!=0)
    {
      arr[0]=null;
    }
    return arr;
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    final int size;
    T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0)
    {
      uncheckedCopyInto(dst,size);
    }
    return dst;
  }
  @Override
  public short[] toShortArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short[] dst;
      uncheckedCopyInto(dst=new short[size],size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(Short[] dst,int length);
  @Override
  public Short[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Short[] dst;
      uncheckedCopyInto(dst=new Short[size],size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_BOXED_ARR;
  }
  abstract void uncheckedCopyInto(double[] dst,int length);
  @Override
  public double[] toDoubleArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final double[] dst;
      uncheckedCopyInto(dst=new double[size],size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(float[] dst,int length);
  @Override
  public float[] toFloatArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float[] dst;
      uncheckedCopyInto(dst=new float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(long[] dst,int length);
  @Override
  public long[] toLongArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long[] dst;
      uncheckedCopyInto(dst=new long[size],size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(int[] dst,int length);
  @Override
  public int[] toIntArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int[] dst;
      uncheckedCopyInto(dst=new int[size],size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  boolean uncheckedRemoveIf(int size,ShortPredicate filter)
  {
    if(size!=(size-=uncheckedRemoveIfImpl(this.arr,0,size,filter))){
      this.size=size;
      return true;
    }
    return false;
  }
  @Override
  public boolean removeIf(ShortPredicate filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  @Override
  public boolean removeIf(Predicate<? super Short> filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
  }
  private static  int pullSurvivorsDown(short[] arr,int srcOffset,int srcBound,int dstOffset,ShortPredicate filter)
  {
    while(++srcOffset!=srcBound)
    {
      final short v;
      if(!filter.test((short)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    return srcBound-dstOffset;
  }
  private static  int uncheckedRemoveIfImpl(short[] arr,int srcOffset,int srcBound,ShortPredicate filter)
  {
    do{
      if(filter.test((short)arr[srcOffset])){
        return pullSurvivorsDown(arr,srcOffset,srcBound,srcOffset,filter);
      }
    }while(++srcOffset!=srcBound);
    return 0;
  }
  private static  int uncheckedRemoveIfImpl(short[] arr,int srcOffset,int srcBound,ShortPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker)
  {
    do
    {
      if(filter.test((short)arr[srcOffset]))
      {
        int dstOffset=srcOffset;
        outer:for(;;)
        {
          if(++srcOffset==srcBound)
          {
            modCountChecker.checkModCount();
            break outer;
          }
          short before;
          if(!filter.test((short)(before=arr[srcOffset])))
          {
            for(int i=srcBound-1;;--i)
            {
              if(i==srcOffset)
              {
                modCountChecker.checkModCount();
                arr[dstOffset++]=before;
                break outer;
              }
              short after;
              if(!filter.test((short)(after=arr[i])))
              {
                int n;
                if((n=i-(++srcOffset))!=0)
                {
                  if(n>64)
                  {
                    long[] survivorSet;
                    int numSurvivors=BitSetUtil.markSurvivors(arr,srcOffset,i,filter,survivorSet=BitSetUtil.getBitSet(n));
                    modCountChecker.checkModCount();
                    if(numSurvivors!=0)
                    {
                      if(numSurvivors==n)
                      {
                        ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset-1,numSurvivors+=2);
                        dstOffset+=numSurvivors;
                      }
                      else
                      {
                        arr[dstOffset]=before;
                        BitSetUtil.pullSurvivorsDown(arr,srcOffset,++dstOffset,dstOffset+=numSurvivors,survivorSet);
                        arr[dstOffset++]=after;
                      }
                      break outer;
                    }
                  }
                  else
                  {
                    long survivorWord=BitSetUtil.markSurvivors(arr,srcOffset,i,filter);
                    modCountChecker.checkModCount();
                    int numSurvivors;
                    if((numSurvivors=Long.bitCount(survivorWord))!=0)
                    {
                      if(numSurvivors==n)
                      {
                        ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset-1,numSurvivors+=2);
                        dstOffset+=numSurvivors;
                      }
                      else
                      {
                        arr[dstOffset]=before;
                        BitSetUtil.pullSurvivorsDown(arr,srcOffset,++dstOffset,dstOffset+=numSurvivors,survivorWord);
                        arr[dstOffset++]=after;
                      }
                      break outer;
                    }
                  }
                }
                else
                {
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
      extends ShortArrSeq
      implements OmniStack.OfShort,Cloneable
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    UncheckedStack(int size,short[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedStack
      return false;
    }
    @Override
    public Object clone()
    {
      final short[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new short[size],0,size);
      }
      else
      {
        copy=OmniArray.OfShort.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    int uncheckedToString(int size,byte[] buffer)
    {
      return OmniArray.OfShort.descendingToString(this.arr,0,size-1,buffer,1);
    }
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder)
    {
      OmniArray.OfShort.descendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfShort.descendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int search(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfShort.uncheckedsearch(this.arr,size,(short)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public int search(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedsearch(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int search(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          return OmniArray.OfShort.uncheckedsearch(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int search(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedsearch(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int search(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedsearch(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int search(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          return OmniArray.OfShort.uncheckedsearch(this.arr,size,i);
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return -1;
    }
    @Override
    public int search(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedsearch(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int search(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedsearch(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        val==arr[index]
        )
        {
          OmniArray.OfShort.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }
        if(index==0)
        {
          return false;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,ShortConsumer action)
    {
      {
        OmniArray.OfShort.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractShortItr
    {
      transient final UncheckedStack parent;
      transient int cursor;
      private Itr(UncheckedStack parent)
      {
        this.parent=parent;
        this.cursor=parent.size;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>0;
      }
      @Override
      public short nextShort()
      {
        return (short)parent.arr[--cursor];
      }
      @Override
      public void remove()
      {
        final UncheckedStack root;
        OmniArray.OfShort.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(ShortConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)>0)
        {
          OmniArray.OfShort.descendingForEach(parent.arr,0,cursor-1,action);
          this.cursor=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Short> action)
      {
        final int cursor;
        if((cursor=this.cursor)>0)
        {
          OmniArray.OfShort.descendingForEach(parent.arr,0,cursor-1,action::accept);
          this.cursor=0;
        }
      }
    }
    @Override
    public OmniIterator.OfShort iterator()
    {
      return new Itr(this);
    }
    @Override
    public void push(Short val)
    {
      push((short)val);
    }
    @Override
    void uncheckedCopyInto(short[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Short[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(int[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    public Short pop()
    {
      return popShort();
    }
    @Override
    public short popShort()
    {
      return (short)arr[--this.size];
    }
    @Override
    public short pollShort()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=(short)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override
    public short peekShort()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (short)(arr[size-1]);
      }
      return Short.MIN_VALUE;
    }
    @Override
    public Short poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=(Short)(arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    public Short peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Short)(arr[size-1]);
      }
      return null;
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=(double)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)(arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=(float)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)(arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public long pollLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=(long)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)(arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=(int)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)(arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
  }
  public
    static class UncheckedList
      extends ShortArrSeq
      implements ShortListDefault,Cloneable
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    UncheckedList(int size,short[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedList
      return false;
    }
    @Override
    public Object clone()
    {
      final short[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new short[size],0,size);
      }
      else
      {
        copy=OmniArray.OfShort.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    int uncheckedToString(int size,byte[] buffer)
    {
      return OmniArray.OfShort.ascendingToString(this.arr,0,size-1,buffer,1);
    }
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder)
    {
      OmniArray.OfShort.ascendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfShort.ascendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfShort.uncheckedindexOf(this.arr,size,(short)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedindexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          return OmniArray.OfShort.uncheckedindexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedindexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedindexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          return OmniArray.OfShort.uncheckedindexOf(this.arr,size,i);
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return -1;
    }
    @Override
    public int indexOf(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedindexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedindexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfShort.uncheckedlastIndexOf(this.arr,size,(short)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          return OmniArray.OfShort.uncheckedlastIndexOf(this.arr,size,i);
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return -1;
    }
    @Override
    public int lastIndexOf(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        val==arr[index]
        )
        {
          OmniArray.OfShort.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }
        if(++index==size)
        {
          return false;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,ShortConsumer action)
    {
      {
        OmniArray.OfShort.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractShortItr
    {
      transient final UncheckedList parent;
      transient int cursor;
      private Itr(UncheckedList parent)
      {
        this.parent=parent;
        this.cursor=0;
      }
      private Itr(UncheckedList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @Override
      public short nextShort()
      {
        return (short)parent.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        OmniArray.OfShort.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(ShortConsumer action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfShort.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Short> action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfShort.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfShort iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfShort
    {
      transient int lastRet;
      private ListItr(UncheckedList parent)
      {
        super(parent);
        this.lastRet=-1;
      }
      private ListItr(UncheckedList parent,int cursor)
      {
        super(parent,cursor);
        this.lastRet=-1;
      }
      @Override
      public boolean hasPrevious()
      {
        return this.cursor>0;
      }
      @Override
      public int nextIndex()
      {
        return this.cursor;
      }
      @Override
      public int previousIndex()
      {
        return this.cursor-1;
      }
      @Override
      public short nextShort()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (short)parent.arr[lastRet];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfShort.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override
      public void forEachRemaining(ShortConsumer action)
      {
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfShort.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Short> action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfShort.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public short previousShort()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (short)parent.arr[lastRet];
      }
      @Override
      public void set(short val)
      {
        parent.arr[this.lastRet]=val;
      }
      @Override
      public void add(short val)
      {
        final UncheckedList root;
        final int rootSize;
        if((rootSize=(root=this.parent).size)!=0)
        {
          ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((ShortArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfShort listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfShort listIterator(int index)
    {
      return new ListItr(this,index);
    }
      private void uncheckedInsert(int index,int size,short val)
      {
        final int tailDist;
        if((tailDist=size-index)==0)
        {
          super.uncheckedAppend(size,val);
        }
        else
        {
          short[] arr;
          if((arr=this.arr).length==size)
          {
            final short[] tmp;
            ArrCopy.semicheckedCopy(arr,0,tmp=new short[OmniArray.growBy50Pct(size)],0,index);
            ArrCopy.uncheckedCopy(arr,index,tmp,index+1,tailDist);
            this.arr=arr=tmp;
          }
          else
          {
            ArrCopy.uncheckedCopy(arr,index,arr,index+1,tailDist);
          }
          arr[index]=val;
          this.size=size+1;
        }
      }
    @Override
    public void add(int index,short val)
    {
      final int size;
      if((size=this.size)!=0){
        ((UncheckedList)this).uncheckedInsert(index,size,val);
      }else{
        ((ShortArrSeq)this).uncheckedInit(val);
      }
    }
    @Override
    void uncheckedCopyInto(short[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Short[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(int[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    public short getShort(int index)
    {
      return (short)this.arr[index];
    }
    @Override
    public void put(int index,short val)
    {
      this.arr[index]=val;
    }
    @Override
    public short set(int index,short val)
    {
      final short[] arr;
      final var ret=(short)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public short removeShortAt(int index)
    {
      final short[] arr;
      final var ret=(short)(arr=this.arr)[index];
      OmniArray.OfShort.removeIndexAndPullDown(arr,index,--size);
      return ret;
    }
    @Override
    public void replaceAll(ShortUnaryOperator operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        OmniArray.OfShort.uncheckedReplaceAll(this.arr,0,size,operator);
      }
    }
    @Override
    public void sort(ShortComparator sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ShortSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          ShortSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        ShortSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        ShortSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Short> operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        OmniArray.OfShort.uncheckedReplaceAll(this.arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(Comparator<? super Short> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ShortSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          ShortSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
        }
      }
    }
    @Override
    public void unstableSort(ShortComparator sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ShortSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          ShortSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public OmniList.OfShort subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class UncheckedSubList
      implements ShortSubListDefault,Cloneable
  {
    transient final int rootOffset;
    transient int size;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    private UncheckedSubList(UncheckedList root,int rootOffset,int size)
    {
      super();
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,int size)
    {
      super();
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedSubList
      return false;
    }
    @Override
    public Object clone()
    {
      final short[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new short[size],0,size);
      }
      else
      {
        copy=OmniArray.OfShort.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override
    public String toString()
    {
      int size;
      if((size=this.size)!=0)
      {
          final int rootOffset;
          final byte[] buffer;
          if(size<=(OmniArray.MAX_ARR_SIZE>>3)){(buffer=new byte[size<<3])
            [size=OmniArray.OfShort.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer,1)]=(byte)']';
            buffer[0]=(byte)'[';
            return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
          }else{
            final ToStringUtil.OmniStringBuilderByte builder;
            OmniArray.OfShort.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
            builder.uncheckedAppendChar((byte)']');
            (buffer=builder.buffer)[0]=(byte)'[';
            return new String(buffer,0,size=builder.size,ToStringUtil.IOS8859CharSet);
          }
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        return OmniArray.OfShort.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return 1;
    }
    @Override
    public int size()
    {
      return this.size;
    }
    @Override
    public boolean isEmpty()
    {
      return this.size==0;
    }
    @Override
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        for(var curr=parent;curr!=null;curr.size-=size,curr=curr.parent){}
        final UncheckedList root;
        (root=this.root).size=OmniArray.OfShort.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override
    public boolean contains(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(short)TypeUtil.castToByte(val));
      }
      return false;
    }
    @Override
    public boolean contains(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          final int rootOffset;
          return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
        }
      }
      return false;
    }
    @Override
    public boolean contains(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          final int rootOffset;
          return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
        }
      }
      return false;
    }
    @Override
    public boolean contains(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          final int rootOffset;
          return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
        }
      }
      return false;
    }
    @Override
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          final int rootOffset;
          return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,i);
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return false;
    }
    @Override
    public boolean contains(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,(short)TypeUtil.castToByte(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public boolean remove(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          return this.uncheckedremoveVal(size,i);
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return false;
    }
    @Override
    public boolean removeVal(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
      }
      return false;
    }
    @Override
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,(short)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,i);
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return -1;
    }
    @Override
    public int indexOf(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(short)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,i);
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return -1;
    }
    @Override
    public int lastIndexOf(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    private boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        val==arr[index]
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfShort.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    @Override
    public void forEach(ShortConsumer action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfShort.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Short> action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfShort.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
    }
    private static class Itr
      extends AbstractShortItr
    {
      transient final UncheckedSubList parent;
      transient int cursor;
      private Itr(UncheckedSubList parent)
      {
        this.parent=parent;
        this.cursor=parent.rootOffset;
      }
      private Itr(UncheckedSubList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        final UncheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override
      public short nextShort()
      {
        return (short)parent.root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        OmniArray.OfShort.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(ShortConsumer action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfShort.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Short> action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfShort.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfShort iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfShort
    {
      transient int lastRet;
      private ListItr(UncheckedSubList parent)
      {
        super(parent);
        this.lastRet=-1;
      }
      private ListItr(UncheckedSubList parent,int cursor)
      {
        super(parent,cursor);
        this.lastRet=-1;
      }
      @Override
      public boolean hasPrevious()
      {
        return this.cursor>parent.rootOffset;
      }
      @Override
      public int nextIndex()
      {
        return this.cursor-parent.rootOffset;
      }
      @Override
      public int previousIndex()
      {
        return this.cursor-parent.rootOffset-1;
      }
      @Override
      public short nextShort()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (short)parent.root.arr[lastRet];
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfShort.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(ShortConsumer action)
      {
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfShort.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Short> action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfShort.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public short previousShort()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (short)parent.root.arr[lastRet];
      }
      @Override
      public void set(short val)
      {
        parent.root.arr[this.lastRet]=val;
      }
      @Override
      public void add(short val)
      {
        final UncheckedList root;
        final int rootSize;
        UncheckedSubList parent;
        if((rootSize=(root=(parent=this.parent).root).size)!=0)
        {
          ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((ShortArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
        do{
          ++parent.size;
        }while((parent=parent.parent)!=null);
      }
    }
    @Override
    public OmniListIterator.OfShort listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfShort listIterator(int index)
    {
      return new ListItr(this,index+this.rootOffset);
    }
    @Override
    public boolean add(short val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        ((ShortArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,short val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+index,rootSize,val);
      }else{
        ((ShortArrSeq)root).uncheckedInit(val);
      }
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
      }
      else if(arr.length!=0)
      {
        arr[0]=null;
      }
      return arr;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      final T[] dst;
      {
        dst=arrConstructor.apply(size=this.size);
      }
      if(size!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
      }
      return dst;
    }
    @Override
    public short[] toShortArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new short[size],0,size);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override
    public Short[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Short[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Short[size],0,size);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_BOXED_ARR;
    }
    @Override
    public double[] toDoubleArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public float[] toFloatArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public long[] toLongArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final long[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public short getShort(int index)
    {
      return (short)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,short val)
    {
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public short set(int index,short val)
    {
      final short[] arr;
      final var ret=(short)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public short removeShortAt(int index)
    {
      final short[] arr;
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      final UncheckedList root;
      final var ret=(short)(arr=(root=this.root).arr)[index+=this.rootOffset];
      OmniArray.OfShort.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override
    public boolean removeIf(ShortPredicate filter)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          final short[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfShort.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Short> filter)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          final short[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfShort.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override
    public void replaceAll(ShortUnaryOperator operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        OmniArray.OfShort.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
    }
    @Override
    public void sort(ShortComparator sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        final int rootOffset;
        if(sorter==null)
        {
          ShortSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        else
        {
          ShortSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        ShortSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        ShortSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Short> operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        OmniArray.OfShort.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }
    }
    @Override
    public void sort(Comparator<? super Short> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        final int rootOffset;
        if(sorter==null)
        {
          ShortSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        else
        {
          ShortSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
        }
      }
    }
    @Override
    public void unstableSort(ShortComparator sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        final int rootOffset;
        if(sorter==null)
        {
          ShortSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        else
        {
          ShortSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override
    public OmniList.OfShort subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,this.rootOffset+fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class CheckedStack
      extends UncheckedStack
  {
    transient int modCount;
    public CheckedStack()
    {
      super();
    }
    public CheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    CheckedStack(int size,short[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedStack
      return false;
    }
    @Override
    public Object clone()
    {
      final short[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new short[size],0,size);
      }
      else
      {
        copy=OmniArray.OfShort.DEFAULT_ARR;
      }
      return new CheckedStack(size,copy);
    }
    @Override
    public void clear()
    {
      if(this.size!=0)
      {
        ++this.modCount;
        this.size=0;
      }
    }
    @Override
    boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        val==arr[index]
        )
        {
          ++this.modCount;
          OmniArray.OfShort.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }
        if(index==0)
        {
          return false;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,ShortConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        OmniArray.OfShort.descendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractShortItr
    {
      transient final CheckedStack parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedStack parent)
      {
        this.parent=parent;
        this.cursor=parent.size;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>0;
      }
      @Override
      public short nextShort()
      {
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        int cursor;
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
            return (short)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          int modCount;
          final CheckedStack root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfShort.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(ShortConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)>0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfShort.descendingForEach(parent.arr,0,cursor-1,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Short> action)
      {
        final int cursor;
        if((cursor=this.cursor)>0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfShort.descendingForEach(parent.arr,0,cursor-1,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
    }
    @Override
    public OmniIterator.OfShort iterator()
    {
      return new Itr(this);
    }
    @Override
    public void push(short val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try
        {
          return arrConstructor.apply(arrSize);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override
    public short popShort()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(short)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public short pollShort()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(short)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override
    public Short poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(Short)(arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(double)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(float)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public long pollLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(long)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(int)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    boolean uncheckedRemoveIf(int size,ShortPredicate filter)
    {
      final int modCount=this.modCount;
      try
      {
        if(size!=(size-=uncheckedRemoveIfImpl(this.arr
          ,0,size,filter,new CheckedCollection.AbstractModCountChecker(modCount){
            @Override protected int getActualModCount(){
            return CheckedStack.this.modCount;
            }
          }))
          ){
          this.modCount=modCount+1;
          this.size=size;
          return true;
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
  }
  public
    static class CheckedList
      extends UncheckedList
  {
    transient int modCount;
    public CheckedList()
    {
      super();
    }
    public CheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    CheckedList(int size,short[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedList
      return false;
    }
    @Override
    public Object clone()
    {
      final short[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new short[size],0,size);
      }
      else
      {
        copy=OmniArray.OfShort.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override
    public void clear()
    {
      if(this.size!=0)
      {
        ++this.modCount;
        this.size=0;
      }
    }
    @Override
    boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        val==arr[index]
        )
        {
          ++this.modCount;
          OmniArray.OfShort.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }
        if(++index==size)
        {
          return false;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,ShortConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        OmniArray.OfShort.ascendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractShortItr
    {
      transient final CheckedList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedList parent)
      {
        this.parent=parent;
        this.cursor=0;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @Override
      public short nextShort()
      {
        final CheckedList root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)<root.size)
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
            return (short)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          int modCount;
          final CheckedList root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfShort.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(ShortConsumer action)
      {
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfShort.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Short> action)
      {
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfShort.ascendingForEach(parent.arr,cursor,cursor=bound-1,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
    }
    @Override
    public OmniIterator.OfShort iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfShort
    {
      private ListItr(CheckedList parent)
      {
        super(parent);
      }
      private ListItr(CheckedList parent,int cursor)
      {
        super(parent,cursor);
      }
      @Override
      public boolean hasPrevious()
      {
        return this.cursor>0;
      }
      @Override
      public int nextIndex()
      {
        return this.cursor;
      }
      @Override
      public int previousIndex()
      {
        return this.cursor-1;
      }
      @Override
      public short previousShort()
      {
        final CheckedList root;
        int cursor;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (short)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(short val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void add(short val)
      {
        int modCount;
        final CheckedList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        this.lastRet=-1;
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((ShortArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfShort listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfShort listIterator(int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index);
    }
    @Override
    public void push(short val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void add(int index,short val)
    {
      final int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      if(size!=0){
        ((UncheckedList)this).uncheckedInsert(index,size,val);
      }else{
        ((ShortArrSeq)this).uncheckedInit(val);
      }
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try
        {
          return arrConstructor.apply(arrSize);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override
    public short getShort(int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (short)this.arr[index];
    }
    @Override
    public void put(int index,short val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      this.arr[index]=val;
    }
    @Override
    public short set(int index,short val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final short[] arr;
      final var ret=(short)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public short removeShortAt(int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final short[] arr;
      ++this.modCount;
      final var ret=(short)(arr=this.arr)[index];
      OmniArray.OfShort.removeIndexAndPullDown(arr,index,--size);
      this.size=size;
      return ret;
    }
    @Override
    boolean uncheckedRemoveIf(int size,ShortPredicate filter)
    {
      final int modCount=this.modCount;
      try
      {
        if(size!=(size-=uncheckedRemoveIfImpl(this.arr
          ,0,size,filter,new CheckedCollection.AbstractModCountChecker(modCount){
            @Override protected int getActualModCount(){
            return CheckedList.this.modCount;
            }
          }))
          ){
          this.modCount=modCount+1;
          this.size=size;
          return true;
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
    @Override
    public void replaceAll(ShortUnaryOperator operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          OmniArray.OfShort.uncheckedReplaceAll(this.arr,0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(ShortComparator sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ShortSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          final int modCount=this.modCount;
          try
          {
            ShortSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
          }
          catch(ArrayIndexOutOfBoundsException e)
          {
            throw CheckedCollection.checkModCount(modCount,this.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
          }
          catch(RuntimeException e)
          {
            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        ShortSortUtil.uncheckedAscendingSort(this.arr,0,size);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        ShortSortUtil.uncheckedDescendingSort(this.arr,0,size);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Short> operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          OmniArray.OfShort.uncheckedReplaceAll(this.arr,0,size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(Comparator<? super Short> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ShortSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          final int modCount=this.modCount;
          try
          {
            ShortSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
          }
          catch(ArrayIndexOutOfBoundsException e)
          {
            throw CheckedCollection.checkModCount(modCount,this.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
          }
          catch(RuntimeException e)
          {
            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void unstableSort(ShortComparator sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ShortSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          final int modCount=this.modCount;
          try
          {
            ShortSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
          }
          catch(ArrayIndexOutOfBoundsException e)
          {
            throw CheckedCollection.checkModCount(modCount,this.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
          }
          catch(RuntimeException e)
          {
            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public OmniList.OfShort subList(int fromIndex,int toIndex)
    {
      return new CheckedSubList(this,fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
  private
    static class CheckedSubList
      implements ShortSubListDefault,Cloneable
  {
    transient int modCount;
    transient final int rootOffset;
    transient int size;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    private CheckedSubList(CheckedList root,int rootOffset,int size)
    {
      super();
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int rootOffset,int size)
    {
      super();
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
      this.modCount=parent.modCount;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedSubList
      return false;
    }
    @Override
    public Object clone()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      final short[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new short[size],0,size);
      }
      else
      {
        copy=OmniArray.OfShort.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override
    public String toString()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0)
      {
          final int rootOffset;
          final byte[] buffer;
          if(size<=(OmniArray.MAX_ARR_SIZE>>3)){(buffer=new byte[size<<3])
            [size=OmniArray.OfShort.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer,1)]=(byte)']';
            buffer[0]=(byte)'[';
            return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
          }else{
            final ToStringUtil.OmniStringBuilderByte builder;
            OmniArray.OfShort.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
            builder.uncheckedAppendChar((byte)']');
            (buffer=builder.buffer)[0]=(byte)'[';
            return new String(buffer,0,size=builder.size,ToStringUtil.IOS8859CharSet);
          }
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        return OmniArray.OfShort.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return 1;
    }
    @Override
    public int size()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override
    public boolean isEmpty()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override
    public void clear()
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=size,curr=curr.parent){}
        root.size=OmniArray.OfShort.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override
    public boolean contains(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(short)TypeUtil.castToByte(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,i);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfShort.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,(short)TypeUtil.castToByte(val));
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean remove(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          return this.uncheckedremoveVal(size,i);
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,(short)TypeUtil.castToByte(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,i);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(short)TypeUtil.castToByte(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      if(val==(short)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if((v=(short)val)==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short v;
        if(val==(v=(short)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
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
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,i);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
    //#ELSE
    //    if(val instanceof Short)
    //    {
    //  #IF OfDouble,OfFloat
    //      final short v;
    //      if((v=(short)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery((short)(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(char val)
    {
      if(val<=Short.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfShort.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    private boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        val==arr[index]
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfShort.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    @Override
    public void forEach(ShortConsumer action)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfShort.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void forEach(Consumer<? super Short> action)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfShort.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private static class Itr
      extends AbstractShortItr
    {
      transient final CheckedSubList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedSubList parent)
      {
        this.parent=parent;
        this.cursor=parent.rootOffset;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedSubList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean hasNext()
      {
        final CheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override
      public short nextShort()
      {
        final CheckedList root;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)<(parent.rootOffset+parent.size))
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
            return (short)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
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
          OmniArray.OfShort.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(ShortConsumer action)
      {
        int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final int modCount=this.modCount;
          final var root=parent.root;
          try
          {
            OmniArray.OfShort.ascendingForEach(root.arr,cursor,cursor=bound-1,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Short> action)
      {
        int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final int modCount=this.modCount;
          final var root=parent.root;
          try
          {
            OmniArray.OfShort.ascendingForEach(root.arr,cursor,cursor=bound-1,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
    }
    @Override
    public OmniIterator.OfShort iterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfShort
    {
      private ListItr(CheckedSubList parent)
      {
        super(parent);
      }
      private ListItr(CheckedSubList parent,int cursor)
      {
        super(parent,cursor);
      }
      @Override
      public boolean hasPrevious()
      {
        return this.cursor>parent.rootOffset;
      }
      @Override
      public int nextIndex()
      {
        return this.cursor-parent.rootOffset;
      }
      @Override
      public int previousIndex()
      {
        return this.cursor-parent.rootOffset-1;
      }
      @Override
      public short previousShort()
      {
        final CheckedList root;
        int cursor;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        if((cursor=this.cursor)>parent.rootOffset)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (short)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(short val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void add(short val)
      {
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
          ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((ShortArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfShort listIterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfShort listIterator(int index)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index+this.rootOffset);
    }
    @Override
    public boolean add(short val)
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      if((modCount=root.size)!=0){
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+(this.size++),modCount,val);
      }else{
        ((ShortArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,short val)
    {
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
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+index,modCount,val);
      }else{
        ((ShortArrSeq)root).uncheckedInit(val);
      }
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
      }
      else if(arr.length!=0)
      {
        arr[0]=null;
      }
      return arr;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      final T[] dst;
      final CheckedList root;
      int modCount=this.modCount;
      try
      {
        dst=arrConstructor.apply(size=this.size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      if(size!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
      }
      return dst;
    }
    @Override
    public short[] toShortArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final short[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new short[size],0,size);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override
    public Short[] toArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final Short[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Short[size],0,size);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_BOXED_ARR;
    }
    @Override
    public double[] toDoubleArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public float[] toFloatArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public long[] toLongArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final long[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public int[] toIntArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final int[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public short getShort(int index)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (short)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,short val)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public short set(int index,short val)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final short[] arr;
      final var ret=(short)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public short removeShortAt(int index)
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final short[] arr;
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
      final var ret=(short)(arr=root.arr)[index+=this.rootOffset];
      OmniArray.OfShort.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override
    public boolean removeIf(ShortPredicate filter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0)
      {
        try
        {
          final short[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter,new CheckedCollection.AbstractModCountChecker(modCount){
            @Override protected int getActualModCount(){
              return root.modCount;
            }
          }))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfShort.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
        catch(ConcurrentModificationException e)
        {
          throw e;
        }
        catch(RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Short> filter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0)
      {
        try
        {
          final short[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test,new CheckedCollection.AbstractModCountChecker(modCount){
            @Override protected int getActualModCount(){
              return root.modCount;
            }
          }))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfShort.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
        catch(ConcurrentModificationException e)
        {
          throw e;
        }
        catch(RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public void replaceAll(ShortUnaryOperator operator)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)==0)
        {
          return;
        }
        final int rootOffset;
        OmniArray.OfShort.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void sort(ShortComparator sorter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)<2)
        {
          return;
        }
        {
          final int rootOffset;
          if(sorter==null)
          {
            ShortSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            try
            {
              ShortSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
              throw new IllegalArgumentException("Comparison method violates its general contract!",e);
            }
          }
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void stableAscendingSort()
    {
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int size;
        if((size=this.size)<2){
          return;
        }
        final int rootOffset;
        ShortSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
    }
    @Override
    public void stableDescendingSort()
    {
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int size;
        if((size=this.size)<2){
          return;
        }
        final int rootOffset;
        ShortSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
    }
    @Override
    public void replaceAll(UnaryOperator<Short> operator)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)==0)
        {
          return;
        }
        final int rootOffset;
        OmniArray.OfShort.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void sort(Comparator<? super Short> sorter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)<2)
        {
          return;
        }
        {
          final int rootOffset;
          if(sorter==null)
          {
            ShortSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            try
            {
              ShortSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
              throw new IllegalArgumentException("Comparison method violates its general contract!",e);
            }
          }
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void unstableSort(ShortComparator sorter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)<2)
        {
          return;
        }
        {
          final int rootOffset;
          if(sorter==null)
          {
            ShortSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            try
            {
              ShortSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
              throw new IllegalArgumentException("Comparison method violates its general contract!",e);
            }
          }
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public OmniList.OfShort subList(int fromIndex,int toIndex)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new CheckedSubList(this,this.rootOffset+fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
}
