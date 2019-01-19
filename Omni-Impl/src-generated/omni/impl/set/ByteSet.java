package omni.impl.set;
import omni.api.OmniSet;
import omni.function.BytePredicate;
import java.util.function.Predicate;
import omni.util.OmniArray;
import java.util.function.IntFunction;
public class ByteSet implements OmniSet.OfByte
{
  transient long word0;
  private
  boolean word0add(int val)
  {
    long word;
    if((word=this.word0)!=(word|=1L<<val))
    {
      this.word0=word;
      return true;
    }
    return false;
  }
  private
  boolean word0remove(int val)
  {
    long word;
    if((word=this.word0)!=(word&=(~(1L<<val))))
    {
      this.word0=word;
      return true;
    }
    return false;
  }
  boolean word0removeIf(BytePredicate filter)
  {
    long originalWord,marker=1L,word=originalWord=this.word0;
    int srcOffset=(Byte.MIN_VALUE);
    for(;;)
    {
      if((word&marker)!=0 && filter.test((byte)srcOffset))
      {
        word&=(~(marker));
      }
      ++srcOffset;
      if((marker<<=1)==0)
      {
        if(originalWord!=word)
        {
          this.word0=word;
          return true;
        }
        return false;
      }
    }
  }
  transient long word1;
  private
  boolean word1add(int val)
  {
    long word;
    if((word=this.word1)!=(word|=1L<<val))
    {
      this.word1=word;
      return true;
    }
    return false;
  }
  private
  boolean word1remove(int val)
  {
    long word;
    if((word=this.word1)!=(word&=(~(1L<<val))))
    {
      this.word1=word;
      return true;
    }
    return false;
  }
  boolean word1removeIf(BytePredicate filter)
  {
    long originalWord,marker=1L,word=originalWord=this.word1;
    int srcOffset=(Byte.MIN_VALUE+64);
    for(;;)
    {
      if((word&marker)!=0 && filter.test((byte)srcOffset))
      {
        word&=(~(marker));
      }
      ++srcOffset;
      if((marker<<=1)==0)
      {
        if(originalWord!=word)
        {
          this.word1=word;
          return true;
        }
        return false;
      }
    }
  }
  transient long word2;
  private
  boolean word2add(int val)
  {
    long word;
    if((word=this.word2)!=(word|=1L<<val))
    {
      this.word2=word;
      return true;
    }
    return false;
  }
  private
  boolean word2remove(int val)
  {
    long word;
    if((word=this.word2)!=(word&=(~(1L<<val))))
    {
      this.word2=word;
      return true;
    }
    return false;
  }
  boolean word2removeIf(BytePredicate filter)
  {
    long originalWord,marker=1L,word=originalWord=this.word2;
    int srcOffset=(Byte.MIN_VALUE+128);
    for(;;)
    {
      if((word&marker)!=0 && filter.test((byte)srcOffset))
      {
        word&=(~(marker));
      }
      ++srcOffset;
      if((marker<<=1)==0)
      {
        if(originalWord!=word)
        {
          this.word2=word;
          return true;
        }
        return false;
      }
    }
  }
  transient long word3;
  private
  boolean word3add(int val)
  {
    long word;
    if((word=this.word3)!=(word|=1L<<val))
    {
      this.word3=word;
      return true;
    }
    return false;
  }
  private
  boolean word3remove(int val)
  {
    long word;
    if((word=this.word3)!=(word&=(~(1L<<val))))
    {
      this.word3=word;
      return true;
    }
    return false;
  }
  boolean word3removeIf(BytePredicate filter)
  {
    long originalWord,marker=1L,word=originalWord=this.word3;
    int srcOffset=(Byte.MIN_VALUE+192);
    for(;;)
    {
      if((word&marker)!=0 && filter.test((byte)srcOffset))
      {
        word&=(~(marker));
      }
      ++srcOffset;
      if((marker<<=1)==0)
      {
        if(originalWord!=word)
        {
          this.word3=word;
          return true;
        }
        return false;
      }
    }
  }
  ByteSet()
  {
    super();
  }
  ByteSet(long word0,long word1,long word2,long word3
    )
  {
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
  }
  @Override
  public boolean add(boolean val)
  {
    long word;
    if((word=this.
      word2
      )!=(word
      |=(val?(0b10L):
      (0b01L))))
    {
       this.word2=word;
      return true;
    }
    return false;
  }
  @Override
  public boolean add(Boolean val)
  {
    return add((boolean)(val));
  }
  @Override
  public boolean add(byte val)
  {
    switch(val>>6)
    {
      case -2:
        return word0add(val);
      case -1:
        return word1add(val);
      case 0:
        return word2add(val);
      default:
        return word3add(val);
    }
  }
  @Override
  public boolean add(Byte val)
  {
    return add((byte)(val));
  }
  @Override
  public boolean removeVal(boolean val)
  {
    long word;
    if((word=this.
      word2
      )!=(word
      &=(val?~(0b10L):~
      (0b01L))))
    {
       this.word2=word;
      return true;
    }
    return false;
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
  public boolean removeIf(Predicate<? super Byte> filter)
  {
    return removeIf((BytePredicate)filter::test);
  }
  @Override
  public boolean removeIf(BytePredicate filter)
  {
    return word0removeIf(filter)
        |word1removeIf(filter)
        |word2removeIf(filter)
        |word3removeIf(filter);
  }
  @Override
  public byte[] toByteArray()
  {
    int size;
    long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      byte[] dst=new byte[size];
      int srcOffset=Byte.MIN_VALUE;
      outer:for(int dstOffset=0;;)
      {
        for(long marker=1L,word=this.word0;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(byte)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word1;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(byte)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word2;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(byte)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word3;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(byte)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        break;
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override
  public Byte[] toArray()
  {
    int size;
    long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      Byte[] dst=new Byte[size];
      int srcOffset=Byte.MIN_VALUE;
      outer:for(int dstOffset=0;;)
      {
        for(long marker=1L,word=this.word0;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(byte)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word1;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(byte)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word2;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(byte)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word3;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(byte)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        break;
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
  }
  @Override
  public double[] toDoubleArray()
  {
    int size;
    long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      double[] dst=new double[size];
      int srcOffset=Byte.MIN_VALUE;
      outer:for(int dstOffset=0;;)
      {
        for(long marker=1L,word=this.word0;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(double)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word1;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(double)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word2;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(double)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word3;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(double)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        break;
      }
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override
  public float[] toFloatArray()
  {
    int size;
    long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      float[] dst=new float[size];
      int srcOffset=Byte.MIN_VALUE;
      outer:for(int dstOffset=0;;)
      {
        for(long marker=1L,word=this.word0;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(float)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word1;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(float)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word2;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(float)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word3;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(float)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        break;
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override
  public long[] toLongArray()
  {
    int size;
    long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      long[] dst=new long[size];
      long srcOffset=Byte.MIN_VALUE;
      outer:for(int dstOffset=0;;)
      {
        for(long marker=1L,word=this.word0;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(long)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word1;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(long)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word2;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(long)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word3;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(long)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        break;
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override
  public int[] toIntArray()
  {
    int size;
    long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      int[] dst=new int[size];
      int srcOffset=Byte.MIN_VALUE;
      outer:for(int dstOffset=0;;)
      {
        for(long marker=1L,word=this.word0;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(int)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word1;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(int)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word2;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(int)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word3;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(int)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        break;
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override
  public short[] toShortArray()
  {
    int size;
    long word0,word1,word2,word3;
    if((size=Long.bitCount(word0=this.word0)+Long.bitCount(word1=this.word1)+Long.bitCount(word2=this.word2)+Long.bitCount(word3=this.word3))!=0)
    {
      short[] dst=new short[size];
      int srcOffset=Byte.MIN_VALUE;
      outer:for(int dstOffset=0;;)
      {
        for(long marker=1L,word=this.word0;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(short)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word1;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(short)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word2;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(short)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        for(long marker=1L,word=this.word3;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(short)(srcOffset);
            if(--size==0)
            {
              break outer;
            }
            ++dstOffset;
          }
          if((marker<<=1)==0)
          {
            break;
          }
        }
        break;
      }
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override
  public boolean remove(Object val)
  {
    return val instanceof Byte && removeVal((byte)val);
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    //TODO
    return null;
  }
  @Override
  public <T> T[] toArray(T[] arr)
  {
    //TODO
    return arr;
  }
}
