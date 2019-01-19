package omni.impl.set;
import omni.api.OmniSet;
import omni.function.CharPredicate;
import java.util.function.Predicate;
import omni.util.OmniArray;
import java.util.function.IntFunction;
abstract class CharSet implements OmniSet.OfChar
{
  transient int size;
  transient long word0;
  boolean word0add(int val)
  {
    long word;
    if((word=this.word0)!=(word|=1L<<val))
    {
      ++this.size;
      this.word0=word;
      return true;
    }
    return false;
  }
  boolean word0remove(int val)
  {
    long word;
    if((word=this.word0)!=(word&=(~(1L<<val))))
    {
      --this.size;
      this.word0=word;
      return true;
    }
    return false;
  }
  transient long word1;
  boolean word1add(int val)
  {
    long word;
    if((word=this.word1)!=(word|=1L<<val))
    {
      ++this.size;
      this.word1=word;
      return true;
    }
    return false;
  }
  boolean word1remove(int val)
  {
    long word;
    if((word=this.word1)!=(word&=(~(1L<<val))))
    {
      --this.size;
      this.word1=word;
      return true;
    }
    return false;
  }
  transient long word2;
  boolean word2add(int val)
  {
    long word;
    if((word=this.word2)!=(word|=1L<<val))
    {
      ++this.size;
      this.word2=word;
      return true;
    }
    return false;
  }
  boolean word2remove(int val)
  {
    long word;
    if((word=this.word2)!=(word&=(~(1L<<val))))
    {
      --this.size;
      this.word2=word;
      return true;
    }
    return false;
  }
  transient long word3;
  boolean word3add(int val)
  {
    long word;
    if((word=this.word3)!=(word|=1L<<val))
    {
      ++this.size;
      this.word3=word;
      return true;
    }
    return false;
  }
  boolean word3remove(int val)
  {
    long word;
    if((word=this.word3)!=(word&=(~(1L<<val))))
    {
      --this.size;
      this.word3=word;
      return true;
    }
    return false;
  }
  CharSet()
  {
    super();
  }
  CharSet(long word0,long word1,long word2,long word3
    ,int size
    )
  {
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
    this.size=size;
  }
  @Override
  public boolean add(boolean val)
  {
    long word;
    if((word=this.
      word0
      )!=(word
      |=(val?(0b10L):
      (0b01L))))
    {
      ++this.size;
      this.word0=word;
      return true;
    }
    return false;
  }
  @Override
  public boolean add(Boolean val)
  {
    return add((boolean)(val));
  }
  abstract boolean addToTable(char val);
  @Override
  public boolean add(char val)
  {
    switch(val>>6)
    {
      default:
        return addToTable(val);
      case 0:
        return word0add(val);
      case 1:
        return word1add(val);
      case 2:
        return word2add(val);
      case 3:
        return word3add(val);
    }
  }
  @Override
  public boolean add(Character val)
  {
    return add((char)(val));
  }
  @Override
  public boolean removeVal(boolean val)
  {
    long word;
    if((word=this.
      word0
      )!=(word
      &=(val?~(0b10L):~
      (0b01L))))
    {
      --this.size;
      this.word0=word;
      return true;
    }
    return false;
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
  abstract boolean uncheckedRemoveIf(int numLeft,CharPredicate filter);
  @Override
  public boolean removeIf(Predicate<? super Character> filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
  }
  @Override
  public boolean removeIf(CharPredicate filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  abstract void copyTableToArray(int numLeft,char[] dst,int dstOffset);
  @Override
  public char[] toCharArray()
  {
    int size;
    if((size=this.size)!=0)
    {
      char[] dst=new char[size];
      int srcOffset=Character.MIN_VALUE;
      outer:for(int dstOffset=0;;)
      {
        for(long marker=1L,word=this.word0;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(char)(srcOffset);
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
            dst[dstOffset]=(char)(srcOffset);
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
            dst[dstOffset]=(char)(srcOffset);
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
            dst[dstOffset]=(char)(srcOffset);
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
        copyTableToArray(size,dst,dstOffset);
        break;
      }
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  abstract void copyTableToArray(int numLeft,Character[] dst,int dstOffset);
  @Override
  public Character[] toArray()
  {
    int size;
    if((size=this.size)!=0)
    {
      Character[] dst=new Character[size];
      int srcOffset=Character.MIN_VALUE;
      outer:for(int dstOffset=0;;)
      {
        for(long marker=1L,word=this.word0;;++srcOffset)
        {
          if((word&marker)!=0)
          {
            dst[dstOffset]=(char)(srcOffset);
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
            dst[dstOffset]=(char)(srcOffset);
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
            dst[dstOffset]=(char)(srcOffset);
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
            dst[dstOffset]=(char)(srcOffset);
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
        copyTableToArray(size,dst,dstOffset);
        break;
      }
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
  }
  abstract void copyTableToArray(int numLeft,double[] dst,int dstOffset);
  @Override
  public double[] toDoubleArray()
  {
    int size;
    if((size=this.size)!=0)
    {
      double[] dst=new double[size];
      int srcOffset=Character.MIN_VALUE;
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
        copyTableToArray(size,dst,dstOffset);
        break;
      }
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  abstract void copyTableToArray(int numLeft,float[] dst,int dstOffset);
  @Override
  public float[] toFloatArray()
  {
    int size;
    if((size=this.size)!=0)
    {
      float[] dst=new float[size];
      int srcOffset=Character.MIN_VALUE;
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
        copyTableToArray(size,dst,dstOffset);
        break;
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  abstract void copyTableToArray(int numLeft,long[] dst,int dstOffset);
  @Override
  public long[] toLongArray()
  {
    int size;
    if((size=this.size)!=0)
    {
      long[] dst=new long[size];
      long srcOffset=Character.MIN_VALUE;
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
        copyTableToArray(size,dst,dstOffset);
        break;
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  abstract void copyTableToArray(int numLeft,int[] dst,int dstOffset);
  @Override
  public int[] toIntArray()
  {
    int size;
    if((size=this.size)!=0)
    {
      int[] dst=new int[size];
      int srcOffset=Character.MIN_VALUE;
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
        copyTableToArray(size,dst,dstOffset);
        break;
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override
  public boolean remove(Object val)
  {
    return val instanceof Character && removeVal((char)val);
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
