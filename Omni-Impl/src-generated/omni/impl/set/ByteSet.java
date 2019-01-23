package omni.impl.set;
import omni.api.OmniSet;
import omni.function.BytePredicate;
import omni.function.ByteConsumer;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.util.OmniArray;
import java.util.function.IntFunction;
import omni.api.OmniIterator;
public class ByteSet implements OmniSet.OfByte
{
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  ByteSet()
  {
  }
  ByteSet(long word0,long word1,long word2,long word3)
  {
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
  }
  @Override
  public int size()
  {
    return Long.bitCount(this.word0)+Long.bitCount(this.word1)+Long.bitCount(this.word2)+Long.bitCount(this.word3);
  }
  @Override
  public boolean isEmpty()
  {
    return this.word0==0 && this.word1==0 && this.word2==0 && this.word3==0;
  }
  @Override
  public void clear()
  {
    this.word0=0;
    this.word1=0;
    this.word2=0;
    this.word3=0;
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO
    return false;
  }
  @Override
  public OmniIterator.OfByte iterator()
  {
    //TODO
    return null;
  }
  @Override
  public String toString()
  {
    long word;
    StringBuilder builder;
    endtoString:for(;;)
    {
      appendword3:for(;;)
      {
        appendword2:for(;;)
        {
          appendword1:for(;;)
          {
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64)
            {
              builder=initializeStringBuilder(word>>>tail0s,Byte.MIN_VALUE+tail0s);
              break appendword1;
            }
            if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64)
            {
              builder=initializeStringBuilder(word>>>tail0s,Byte.MIN_VALUE+64+tail0s);
              break appendword2;
            }
            if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64)
            {
              builder=initializeStringBuilder(word>>>tail0s,Byte.MIN_VALUE+128+tail0s);
              break appendword3;
            }
            if((tail0s=Long.numberOfTrailingZeros(word=this.word3))!=64)
            {
              builder=initializeStringBuilder(word>>>tail0s,Byte.MIN_VALUE+192+tail0s);
              break endtoString;
            }
            return "[]";
          }
          appendWord(word1,Byte.MIN_VALUE+64,builder);
          break;
        }
        appendWord(word2,Byte.MIN_VALUE+128,builder);
        break;
      }
      appendWord(word3,Byte.MIN_VALUE+192,builder);
      break;
    }
    return builder.append(']').toString();
  }
  private static StringBuilder initializeStringBuilder(long src,int srcOffset)
  {
    int tail0s;
    for(final var builder=new StringBuilder("[").append(srcOffset++);;src>>>=tail0s,builder.append(',').append(' ').append(srcOffset+=tail0s))
    {
      for(src>>>=(tail0s=Long.numberOfTrailingZeros(~(src)));--tail0s!=0;)
      {
        builder.append(',').append(' ').append(srcOffset++);
      }
      if((tail0s=Long.numberOfTrailingZeros(src))==64)
      {
        return builder;
      }
    }
  }
  private static void appendWord(long src,int srcOffset,StringBuilder builder)
  {
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        builder.append(',').append(' ').append(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
  }
  @Override
  public int hashCode()
  {
    return wordHash(this.word0,Byte.MIN_VALUE)
      +wordHash(this.word1,Byte.MIN_VALUE+64)
      +wordHash(this.word2,Byte.MIN_VALUE+128)
      +wordHash(this.word3,Byte.MIN_VALUE+192);
  }
  private static int wordHash(long word,int offset)
  {
    int hash=0;
    for(long marker=1L;;++offset)
    {
      if((word&marker)!=0)
      {
        hash+=offset;
      }
      if((marker<<=1)==0)
      {
        return hash;
      }
    }
  }
  @Override
  public <T> T[] toArray(T[] dst)
  {
    final int size;
    final long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      wordcopy(word3,dst=OmniArray.uncheckedArrResize(size,dst),wordcopy(word2,Byte.MIN_VALUE+128,dst,wordcopy(word1,Byte.MIN_VALUE+64,dst,wordcopy(word0,Byte.MIN_VALUE,dst,0))));
    }
    else if(dst.length!=0)
    {
      dst[0]=null;
    }
    return dst;
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    final int size;
    final long word0,word1,word2,word3;
    final T[] dst=arrConstructor.apply(size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3));
    if(size!=0)
    {
      wordcopy(word3,dst,wordcopy(word2,Byte.MIN_VALUE+128,dst,wordcopy(word1,Byte.MIN_VALUE+64,dst,wordcopy(word0,Byte.MIN_VALUE,dst,0))));
    }
    return dst;
  }
  @Override
  public byte[] toByteArray()
  {
    final int size;
    final long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      final byte[] dst;
      wordcopy(word3,dst=new byte[size],wordcopy(word2,Byte.MIN_VALUE+128,dst,wordcopy(word1,Byte.MIN_VALUE+64,dst,wordcopy(word0,Byte.MIN_VALUE,dst,0))));
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override
  public Byte[] toArray()
  {
    final int size;
    final long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      final Byte[] dst;
      wordcopy(word3,dst=new Byte[size],wordcopy(word2,Byte.MIN_VALUE+128,dst,wordcopy(word1,Byte.MIN_VALUE+64,dst,wordcopy(word0,Byte.MIN_VALUE,dst,0))));
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
  }
  @Override
  public double[] toDoubleArray()
  {
    final int size;
    final long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      final double[] dst;
      wordcopy(word3,dst=new double[size],wordcopy(word2,Byte.MIN_VALUE+128,dst,wordcopy(word1,Byte.MIN_VALUE+64,dst,wordcopy(word0,Byte.MIN_VALUE,dst,0))));
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override
  public float[] toFloatArray()
  {
    final int size;
    final long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      final float[] dst;
      wordcopy(word3,dst=new float[size],wordcopy(word2,Byte.MIN_VALUE+128,dst,wordcopy(word1,Byte.MIN_VALUE+64,dst,wordcopy(word0,Byte.MIN_VALUE,dst,0))));
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override
  public long[] toLongArray()
  {
    final int size;
    final long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      final long[] dst;
      wordcopy(word3,dst=new long[size],wordcopy(word2,Byte.MIN_VALUE+128,dst,wordcopy(word1,Byte.MIN_VALUE+64,dst,wordcopy(word0,Byte.MIN_VALUE,dst,0))));
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override
  public int[] toIntArray()
  {
    final int size;
    final long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      final int[] dst;
      wordcopy(word3,dst=new int[size],wordcopy(word2,Byte.MIN_VALUE+128,dst,wordcopy(word1,Byte.MIN_VALUE+64,dst,wordcopy(word0,Byte.MIN_VALUE,dst,0))));
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override
  public short[] toShortArray()
  {
    final int size;
    final long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      final short[] dst;
      wordcopy(word3,dst=new short[size],wordcopy(word2,Byte.MIN_VALUE+128,dst,wordcopy(word1,Byte.MIN_VALUE+64,dst,wordcopy(word0,Byte.MIN_VALUE,dst,0))));
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  private static void wordcopy(long src,Object[] dst,int dstOffset)
  {
    int tail0s;
    for(int srcOffset=Byte.MIN_VALUE+192;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(byte)(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
  }
  static int wordcopy(long src,int srcOffset,Object[] dst,int dstOffset)
  {
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(byte)(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
    return dstOffset;
  }
  private static void wordcopy(long src,Byte[] dst,int dstOffset)
  {
    int tail0s;
    for(int srcOffset=Byte.MIN_VALUE+192;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(byte)(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
  }
  static int wordcopy(long src,int srcOffset,Byte[] dst,int dstOffset)
  {
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(byte)(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
    return dstOffset;
  }
  private static void wordcopy(long src,byte[] dst,int dstOffset)
  {
    int tail0s;
    for(int srcOffset=Byte.MIN_VALUE+192;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(byte)(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
  }
  static int wordcopy(long src,int srcOffset,byte[] dst,int dstOffset)
  {
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(byte)(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
    return dstOffset;
  }
  private static void wordcopy(long src,short[] dst,int dstOffset)
  {
    int tail0s;
    for(int srcOffset=Byte.MIN_VALUE+192;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(short)(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
  }
  static int wordcopy(long src,int srcOffset,short[] dst,int dstOffset)
  {
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(short)(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
    return dstOffset;
  }
  private static void wordcopy(long src,int[] dst,int dstOffset)
  {
    int tail0s;
    for(int srcOffset=Byte.MIN_VALUE+192;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
  }
  static int wordcopy(long src,int srcOffset,int[] dst,int dstOffset)
  {
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
    return dstOffset;
  }
  private static void wordcopy(long src,long[] dst,int dstOffset)
  {
    int tail0s;
    for(long srcOffset=Byte.MIN_VALUE+192;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
  }
  static int wordcopy(long src,long srcOffset,long[] dst,int dstOffset)
  {
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
    return dstOffset;
  }
  private static void wordcopy(long src,float[] dst,int dstOffset)
  {
    int tail0s;
    for(int srcOffset=Byte.MIN_VALUE+192;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
  }
  static int wordcopy(long src,int srcOffset,float[] dst,int dstOffset)
  {
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
    return dstOffset;
  }
  private static void wordcopy(long src,double[] dst,int dstOffset)
  {
    int tail0s;
    for(int srcOffset=Byte.MIN_VALUE+192;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
  }
  static int wordcopy(long src,int srcOffset,double[] dst,int dstOffset)
  {
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(src))!=64;)
    {
      for(srcOffset+=tail0s,src>>>=(tail0s=Long.numberOfTrailingZeros(~(src>>>=tail0s)));;)
      {
        dst[dstOffset++]=(srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
    return dstOffset;
  }
  @Override
  public boolean removeIf(Predicate<? super Byte> filter)
  {
    return removeIf((BytePredicate)filter::test);
  }
  @Override
  public void forEach(Consumer<? super Byte> action)
  {
    forEach((ByteConsumer)action::accept);
  }
  static long wordRemoveIf(long word,int srcOffset,BytePredicate filter)
  {
    int tail0s;
    for(long wordCopy=word;(tail0s=Long.numberOfTrailingZeros(wordCopy))!=64;)
    {
      for(srcOffset+=tail0s,wordCopy>>>=(tail0s=Long.numberOfTrailingZeros(~(wordCopy>>>=tail0s)));;)
      {
        if(filter.test((byte)srcOffset))
        {
          word&=(~(1L<<srcOffset));
        }
        ++srcOffset;
        if(--tail0s==0)
        {
          break;
        }
      }
    }
    return word;
  }
  static void wordForEach(long word,int srcOffset,ByteConsumer action)
  {
    for(int tail0s;(tail0s=Long.numberOfTrailingZeros(word))!=64;)
    {
      for(srcOffset+=tail0s,word>>>=(tail0s=Long.numberOfTrailingZeros(~(word>>>=tail0s)));;)
      {
        action.accept((byte)srcOffset++);
        if(--tail0s==0)
        {
          break;
        }
      }
    }
  }
  @Override
  public boolean removeIf(BytePredicate filter)
  {
    boolean dif=false;
    long word;
    if((word=this.word0)!=(word=wordRemoveIf(word,Byte.MIN_VALUE,filter)))
    {
      this.word0=word;
      dif=true;
    }
    if((word=this.word1)!=(word=wordRemoveIf(word,Byte.MIN_VALUE+64,filter)))
    {
      this.word1=word;
      dif=true;
    }
    if((word=this.word2)!=(word=wordRemoveIf(word,Byte.MIN_VALUE+128,filter)))
    {
      this.word2=word;
      dif=true;
    }
    if((word=this.word3)!=(word=wordRemoveIf(word,Byte.MIN_VALUE+192,filter)))
    {
      this.word3=word;
      return true;
    }
    return dif;
  }
  @Override
  public void forEach(ByteConsumer action)
  {
    wordForEach(this.word0,Byte.MIN_VALUE,action);
    wordForEach(this.word1,Byte.MIN_VALUE+64,action);
    wordForEach(this.word2,Byte.MIN_VALUE+128,action);
    wordForEach(this.word3,Byte.MIN_VALUE+192,action);
  }
  @Override
  public boolean add(Byte val)
  {
    return add((byte)(val));
  }
  private boolean addword0(int val)
  {
    long word;
    if((word=this.word0)!=(word|=(1L<<val)))
    {
      this.word0=word;
      return true;
    }
    return false;
  }
  private boolean addword1(int val)
  {
    long word;
    if((word=this.word1)!=(word|=(1L<<val)))
    {
      this.word1=word;
      return true;
    }
    return false;
  }
  private boolean addword2(int val)
  {
    long word;
    if((word=this.word2)!=(word|=(1L<<val)))
    {
      this.word2=word;
      return true;
    }
    return false;
  }
  private boolean addword3(int val)
  {
    long word;
    if((word=this.word3)!=(word|=(1L<<val)))
    {
      this.word3=word;
      return true;
    }
    return false;
  }
  @Override
  public boolean add(byte val)
  {
    switch(val>>6)
    {
    case -2:
      return addword0(val);
    case -1:
      return addword1(val);
    case 0:
      return addword2(val);
    default:
      return addword3(val);
    }
  }
  @Override
  public boolean add(boolean val)
  {
    long word;
    if((word=this.word0)!=(word|=(val?0b10L:0b01L)))
    {
      this.word0=word;
      return true;
    }
    return false;
  }
  @Override
  public boolean contains(double val)
  {
    final byte v;
    return val==(v=(byte)val) && contains(v);
  }
  @Override
  public boolean contains(float val)
  {
    final byte v;
    return val==(v=(byte)val) && contains(v);
  }
  @Override
  public boolean contains(long val)
  {
    final byte v;
    return val==(v=(byte)val) && contains(v);
  }
  @Override
  public boolean removeVal(double val)
  {
    final byte v;
    return val==(v=(byte)val) && removeVal(v);
  }
  @Override
  public boolean removeVal(float val)
  {
    final byte v;
    return val==(v=(byte)val) && removeVal(v);
  }
  @Override
  public boolean removeVal(long val)
  {
    final byte v;
    return val==(v=(byte)val) && removeVal(v);
  }
  @Override
  public boolean contains(Object val)
  {
    return val instanceof Byte && contains((byte)val);
  }
  @Override
  public boolean remove(Object val)
  {
    return val instanceof Byte && removeVal((byte)val);
  }
}
