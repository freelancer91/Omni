package omni.impl;
import omni.api.OmniSet;
import omni.api.OmniIterator;
import omni.function.ByteConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.function.BytePredicate;
import java.util.function.IntFunction;
import omni.util.OmniArray;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
public class ByteSet implements OmniSet.OfByte
{
  transient long word0;
  private
  int
  word0copy(Object[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE);
    int srcOffset=(Byte.MIN_VALUE);
    for(long word=this.word0;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word0copy(Byte[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE);
    int srcOffset=(Byte.MIN_VALUE);
    for(long word=this.word0;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(byte)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word0copy(double[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE);
    int srcOffset=(Byte.MIN_VALUE);
    for(long word=this.word0;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word0copy(float[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE);
    int srcOffset=(Byte.MIN_VALUE);
    for(long word=this.word0;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word0copy(long[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE);
    long srcOffset=(Byte.MIN_VALUE);
    for(long word=this.word0;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word0copy(int[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE);
    int srcOffset=(Byte.MIN_VALUE);
    for(long word=this.word0;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word0copy(short[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE);
    int srcOffset=(Byte.MIN_VALUE);
    for(long word=this.word0;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(short)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word0copy(byte[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE);
    int srcOffset=(Byte.MIN_VALUE);
    for(long word=this.word0;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(byte)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private boolean word0contains(int val)
  {
    return (this.word0&(1L<<val))!=0;
  }
  private boolean word0add(int val)
  {
    long word;
    if((word=this.word0)!=(word|=1L<<val))
    {
      this.word0=word;
      return true;
    }
    return false;
  }
  private boolean word0remove(int val)
  {
    long word;
    if((word=this.word0)!=(word&=(~(1L<<val))))
    {
      this.word0=word;
      return true;
    }
    return false;
  }
  private int word0hash()
  {
    long marker,word;
    int srcOffset=(Byte.MIN_VALUE);
    int hash=((marker=1L<<(Byte.MIN_VALUE))&(word=this.word0))!=0?srcOffset:0;
    while((marker<<=1)!=0)
    {
      ++srcOffset;
      if((marker&word)!=0)
      {
        hash+=srcOffset;
      }
    }
    return hash;
  }
  void word0consume(ByteConsumer action)
  {
    consumeWord(word0,action,(Byte.MIN_VALUE));
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
  int
  word1copy(Object[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+64);
    int srcOffset=(Byte.MIN_VALUE+64);
    for(long word=this.word1;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word1copy(Byte[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+64);
    int srcOffset=(Byte.MIN_VALUE+64);
    for(long word=this.word1;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(byte)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word1copy(double[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+64);
    int srcOffset=(Byte.MIN_VALUE+64);
    for(long word=this.word1;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word1copy(float[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+64);
    int srcOffset=(Byte.MIN_VALUE+64);
    for(long word=this.word1;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word1copy(long[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+64);
    long srcOffset=(Byte.MIN_VALUE+64);
    for(long word=this.word1;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word1copy(int[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+64);
    int srcOffset=(Byte.MIN_VALUE+64);
    for(long word=this.word1;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word1copy(short[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+64);
    int srcOffset=(Byte.MIN_VALUE+64);
    for(long word=this.word1;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(short)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word1copy(byte[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+64);
    int srcOffset=(Byte.MIN_VALUE+64);
    for(long word=this.word1;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(byte)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private boolean word1contains(int val)
  {
    return (this.word1&(1L<<val))!=0;
  }
  private boolean word1add(int val)
  {
    long word;
    if((word=this.word1)!=(word|=1L<<val))
    {
      this.word1=word;
      return true;
    }
    return false;
  }
  private boolean word1remove(int val)
  {
    long word;
    if((word=this.word1)!=(word&=(~(1L<<val))))
    {
      this.word1=word;
      return true;
    }
    return false;
  }
  private int word1hash()
  {
    long marker,word;
    int srcOffset=(Byte.MIN_VALUE+64);
    int hash=((marker=1L<<(Byte.MIN_VALUE+64))&(word=this.word1))!=0?srcOffset:0;
    while((marker<<=1)!=0)
    {
      ++srcOffset;
      if((marker&word)!=0)
      {
        hash+=srcOffset;
      }
    }
    return hash;
  }
  void word1consume(ByteConsumer action)
  {
    consumeWord(word1,action,(Byte.MIN_VALUE+64));
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
  int
  word2copy(Object[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+128);
    int srcOffset=(Byte.MIN_VALUE+128);
    for(long word=this.word2;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word2copy(Byte[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+128);
    int srcOffset=(Byte.MIN_VALUE+128);
    for(long word=this.word2;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(byte)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word2copy(double[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+128);
    int srcOffset=(Byte.MIN_VALUE+128);
    for(long word=this.word2;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word2copy(float[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+128);
    int srcOffset=(Byte.MIN_VALUE+128);
    for(long word=this.word2;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word2copy(long[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+128);
    long srcOffset=(Byte.MIN_VALUE+128);
    for(long word=this.word2;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word2copy(int[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+128);
    int srcOffset=(Byte.MIN_VALUE+128);
    for(long word=this.word2;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word2copy(short[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+128);
    int srcOffset=(Byte.MIN_VALUE+128);
    for(long word=this.word2;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(short)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private
  int
  word2copy(byte[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+128);
    int srcOffset=(Byte.MIN_VALUE+128);
    for(long word=this.word2;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(byte)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return dstOffset;
      }
    }
  }
  private boolean word2contains(int val)
  {
    return (this.word2&(1L<<val))!=0;
  }
  private boolean word2add(int val)
  {
    long word;
    if((word=this.word2)!=(word|=1L<<val))
    {
      this.word2=word;
      return true;
    }
    return false;
  }
  private boolean word2remove(int val)
  {
    long word;
    if((word=this.word2)!=(word&=(~(1L<<val))))
    {
      this.word2=word;
      return true;
    }
    return false;
  }
  private int word2hash()
  {
    long marker,word;
    int srcOffset=(Byte.MIN_VALUE+128);
    int hash=((marker=1L<<(Byte.MIN_VALUE+128))&(word=this.word2))!=0?srcOffset:0;
    while((marker<<=1)!=0)
    {
      ++srcOffset;
      if((marker&word)!=0)
      {
        hash+=srcOffset;
      }
    }
    return hash;
  }
  void word2consume(ByteConsumer action)
  {
    consumeWord(word2,action,(Byte.MIN_VALUE+128));
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
  void
  word3copy(Object[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+192);
    int srcOffset=(Byte.MIN_VALUE+192);
    for(long word=this.word3;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return;
      }
    }
  }
  private
  void
  word3copy(Byte[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+192);
    int srcOffset=(Byte.MIN_VALUE+192);
    for(long word=this.word3;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(byte)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return;
      }
    }
  }
  private
  void
  word3copy(double[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+192);
    int srcOffset=(Byte.MIN_VALUE+192);
    for(long word=this.word3;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return;
      }
    }
  }
  private
  void
  word3copy(float[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+192);
    int srcOffset=(Byte.MIN_VALUE+192);
    for(long word=this.word3;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return;
      }
    }
  }
  private
  void
  word3copy(long[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+192);
    long srcOffset=(Byte.MIN_VALUE+192);
    for(long word=this.word3;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return;
      }
    }
  }
  private
  void
  word3copy(int[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+192);
    int srcOffset=(Byte.MIN_VALUE+192);
    for(long word=this.word3;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return;
      }
    }
  }
  private
  void
  word3copy(short[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+192);
    int srcOffset=(Byte.MIN_VALUE+192);
    for(long word=this.word3;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(short)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return;
      }
    }
  }
  private
  void
  word3copy(byte[] dst,int dstOffset)
  {
    long marker=1L<<(Byte.MIN_VALUE+192);
    int srcOffset=(Byte.MIN_VALUE+192);
    for(long word=this.word3;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        dst[dstOffset++]=(byte)(srcOffset);
      }
      if((marker<<=1)==0)
      {
        return;
      }
    }
  }
  private boolean word3contains(int val)
  {
    return (this.word3&(1L<<val))!=0;
  }
  private boolean word3add(int val)
  {
    long word;
    if((word=this.word3)!=(word|=1L<<val))
    {
      this.word3=word;
      return true;
    }
    return false;
  }
  private boolean word3remove(int val)
  {
    long word;
    if((word=this.word3)!=(word&=(~(1L<<val))))
    {
      this.word3=word;
      return true;
    }
    return false;
  }
  private int word3hash()
  {
    long marker,word;
    int srcOffset=(Byte.MIN_VALUE+192);
    int hash=((marker=1L<<(Byte.MIN_VALUE+192))&(word=this.word3))!=0?srcOffset:0;
    while((marker<<=1)!=0)
    {
      ++srcOffset;
      if((marker&word)!=0)
      {
        hash+=srcOffset;
      }
    }
    return hash;
  }
  void word3consume(ByteConsumer action)
  {
    consumeWord(word3,action,(Byte.MIN_VALUE+192));
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
  ByteSet(long word0,long word1,long word2, long word3)
  {
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
  }
  ByteSet()
  {
  }
  @Override
  public boolean add(boolean val)
  {
    long word;
    if((word=this.word2)!=
      (word|=(val?0b10:0b01))
    )
    {
      this.word2=word;
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
    return add((byte)val);
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
  public Object clone()
  {
    return new ByteSet(word0,word1,word2,word3);
  }
  @Override
  public boolean contains(boolean val)
  {
    return (this.word2&(val?0b10L:0b01L))!=0;
  }
  @Override
  public boolean contains(Boolean val)
  {
    return val!=null && contains((boolean)(val));
  }
  @Override
  public boolean contains(byte val)
  {
    switch(val>>6)
    {
      case -2:
        return word0contains(val);
      case -1:
        return word1contains(val);
      case 0:
        return word2contains(val);
      default:
        return word3contains(val);
    }
  }
  @Override
  public boolean contains(Byte val)
  {
    return val!=null && contains((byte)(val));
  }
  @Override
  public boolean contains(char val)
  {
    switch(val>>6)
    {
      case 0:
        return word2contains(val);
      case 1:
        return word3contains(val);
      default:
        return false;
    }
  }
  @Override
  public boolean contains(Character val)
  {
    return val!=null && contains((char)(val));
  }
  @Override
  public boolean contains(double val)
  {
    final byte v;
    return val==(v=(byte)val)&&contains(v);
  }
  @Override
  public boolean contains(Double val)
  {
    return val!=null && contains((double)(val));
  }
  @Override
  public boolean contains(float val)
  {
    final byte v;
    return val==(v=(byte)val)&&contains(v);
  }
  @Override
  public boolean contains(Float val)
  {
    return val!=null && contains((float)(val));
  }
  @Override
  public boolean contains(int val)
  {
    switch(val>>6)
    {
      case -2:
        return word0contains(val);
      case -1:
        return word1contains(val);
      case 0:
        return word2contains(val);
      case 1:
        return word3contains(val);
      default:
        return false;
    }
  }
  @Override
  public boolean contains(Integer val)
  {
    return val!=null && contains((int)(val));
  }
  @Override
  public boolean contains(long val)
  {
    final byte v;
    return val==(v=(byte)val)&&contains(v);
  }
  @Override
  public boolean contains(Long val)
  {
    return val!=null && contains((long)(val));
  }
  @Override
  public boolean contains(Object val)
  {
    return val instanceof Byte && contains((byte)val);
  }
  @Override
  public boolean contains(short val)
  {
    return contains((int)val);
  }
  @Override
  public boolean contains(Short val)
  {
    return val!=null && contains((int)(val));
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO implement equals method
    return false;
  }
  @Override
  public void forEach(ByteConsumer action)
  {
    word0consume(action);
    word1consume(action);
    word2consume(action);
    word3consume(action);
  }
  @Override
  public void forEach(Consumer<? super Byte> action)
  {
    word0consume(action::accept);
    word1consume(action::accept);
    word2consume(action::accept);
    word3consume(action::accept);
  }
  @Override
  public int hashCode()
  {
    return word0hash()+word1hash()+word2hash()+word3hash();
  }
  @Override
  public boolean isEmpty()
  {
    return word0==0&&word1==0&&word2==0&&word3==0;
  }
  @Override
  public OmniIterator.OfByte iterator()
  {
    return new Itr(this);
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
  public boolean removeIf(Predicate<? super Byte> filter)
  {
    return word0removeIf(filter::test)
          |word1removeIf(filter::test)
          |word2removeIf(filter::test)
          |word3removeIf(filter::test);
  }
  private static class Itr extends AbstractByteItr
  {
    private transient final ByteSet root;
    private transient int srcOffset;
    private transient byte lastRet;
    private Itr(ByteSet root)
    {
      this.root=root;
      int srcOffset=Byte.MIN_VALUE;
      long currWord=root.word0,marker=1L;
      outer: for(;;)
      {
        for(;;)
        {
          if((marker&currWord)!=0)
          {
            break outer;
          }
          ++srcOffset;
          if((marker<<=1)==0)
          {
            break;
          }
        }
        currWord=root.word1;
        marker=1L;
        for(;;)
        {
          if((marker&currWord)!=0)
          {
            break outer;
          }
          ++srcOffset;
          if((marker<<=1)==0)
          {
            break;
          }
        }
        currWord=root.word2;
        marker=1L;
        for(;;)
        {
          if((marker&currWord)!=0)
          {
            break outer;
          }
          ++srcOffset;
          if((marker<<=1)==0)
          {
            break;
          }
        }
        currWord=root.word3;
        marker=1L;
        for(;;)
        {
          if((marker&currWord)!=0)
          {
            break outer;
          }
          ++srcOffset;
          if((marker<<=1)==0)
          {
            break;
          }
        }
      }
      this.srcOffset=srcOffset;
    }
    @Override
    public boolean hasNext()
    {
      return this.srcOffset<=Byte.MAX_VALUE;
    }
    @Override
    public void remove()
    {
      byte lastRet;
      switch((lastRet=this.lastRet)>>6)
      {
        case -2:
          root.word0&=(~(1L<<lastRet));
          return;
        case -1:
          root.word1&=(~(1L<<lastRet));
          return;
        case 0:
          root.word2&=(~(1L<<lastRet));
          return;
        default:
          root.word3&=(~(1L<<lastRet));
      }
    }
    @Override
    public byte nextByte()
    {
      int srcOffset;
      final byte lastRet;
      this.lastRet=lastRet=(byte)(srcOffset=this.srcOffset);
      final ByteSet root=this.root;
      long currWord,marker;
      outerfinal: switch(srcOffset>>6)
      {
      case -2:
        currWord=root.word0;
        marker=1L<<srcOffset;
        for(;;)
        {
          ++srcOffset;
          if((marker<<=1)==0)
          {
            break;
          }
          if((marker&currWord)!=0)
          {
            break outerfinal;
          }
        }
      case -1:
        currWord=root.word1;
        marker=1L<<srcOffset;
        for(;;)
        {
          ++srcOffset;
          if((marker<<=1)==0)
          {
            break;
          }
          if((marker&currWord)!=0)
          {
            break outerfinal;
          }
        }
      case 0:
        currWord=root.word2;
        marker=1L<<srcOffset;
        for(;;)
        {
          ++srcOffset;
          if((marker<<=1)==0)
          {
            break;
          }
          if((marker&currWord)!=0)
          {
            break outerfinal;
          }
        }
      default:
        currWord=root.word3;
        marker=1L<<srcOffset;
        for(;;)
        {
          ++srcOffset;
          if((marker<<=1)==0)
          {
            break;
          }
          if((marker&currWord)!=0)
          {
            break outerfinal;
          }
        }
      }
      this.srcOffset=srcOffset;
      return lastRet;
    }
    @Override
    public void forEachRemaining(ByteConsumer action)
    {
      var root=this.root;
      int srcOffset;
      byte lastRet;
      action.accept(lastRet=(byte)(srcOffset=this.srcOffset));
      switch(srcOffset>>6)
      {
        case -2:
          long word=root.word0;
          long marker=1L<<srcOffset;
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((word&marker)!=0)
            {
              action.accept(lastRet=(byte)srcOffset);
            }
          }
        case -1:
          word=root.word1;
          marker=1L<<srcOffset;
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((word&marker)!=0)
            {
              action.accept(lastRet=(byte)srcOffset);
            }
          }
        case 0:
          word=root.word2;
          marker=1L<<srcOffset;
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((word&marker)!=0)
            {
              action.accept(lastRet=(byte)srcOffset);
            }
          }
        case 1:
          word=root.word3;
          marker=1L<<srcOffset;
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((word&marker)!=0)
            {
              action.accept(lastRet=(byte)srcOffset);
            }
          }
          break;
        default:
          return;
      }
      this.lastRet=lastRet;
      this.srcOffset=srcOffset;
    }
    @Override
    public void forEachRemaining(Consumer<? super Byte> action)
    {
      var root=this.root;
      int srcOffset;
      byte lastRet;
      action.accept(lastRet=(byte)(srcOffset=this.srcOffset));
      switch(srcOffset>>6)
      {
        case -2:
          long word=root.word0;
          long marker=1L<<srcOffset;
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((word&marker)!=0)
            {
              action.accept(lastRet=(byte)srcOffset);
            }
          }
        case -1:
          word=root.word1;
          marker=1L<<srcOffset;
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((word&marker)!=0)
            {
              action.accept(lastRet=(byte)srcOffset);
            }
          }
        case 0:
          word=root.word2;
          marker=1L<<srcOffset;
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((word&marker)!=0)
            {
              action.accept(lastRet=(byte)srcOffset);
            }
          }
        case 1:
          word=root.word3;
          marker=1L<<srcOffset;
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((word&marker)!=0)
            {
              action.accept(lastRet=(byte)srcOffset);
            }
          }
          break;
        default:
          return;
      }
      this.lastRet=lastRet;
      this.srcOffset=srcOffset;
    }
  }
  @Override
  public boolean remove(Object val)
  {
    return val instanceof Byte && removeVal((byte)val);
  }
  @Override
  public boolean removeVal(boolean val)
  {
    long word;
    if((word=this.word2)!=
      (word&=(val?~(0b10):~(0b01)))
    )
    {
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override
  public boolean removeVal(Boolean val)
  {
    return val!=null && removeVal((boolean)(val));
  }
  @Override
  public boolean removeVal(byte val)
  {
    switch(val>>6)
    {
      case -2:
        return word0remove(val);
      case -1:
        return word1remove(val);
      case 0:
        return word2remove(val);
      default:
        return word3remove(val);
    }
  }
  @Override
  public boolean removeVal(Byte val)
  {
    return val!=null && removeVal((byte)(val));
  }
  @Override
  public boolean removeVal(char val)
  {
    switch(val>>6)
    {
      case 0:
        return word2remove(val);
      case 1:
        return word3remove(val);
      default:
        return false;
    }
  }
  @Override
  public boolean removeVal(Character val)
  {
    return val!=null && removeVal((char)(val));
  }
  @Override
  public boolean removeVal(double val)
  {
    final byte v;
    return val==(v=(byte)val)&&removeVal(v);
  }
  @Override
  public boolean removeVal(Double val)
  {
    return val!=null && removeVal((double)(val));
  }
  @Override
  public boolean removeVal(float val)
  {
    final byte v;
    return val==(v=(byte)val)&&removeVal(v);
  }
  @Override
  public boolean removeVal(Float val)
  {
    return val!=null && removeVal((float)(val));
  }
  @Override
  public boolean removeVal(int val)
  {
    switch(val>>6)
    {
      case -2:
        return word0remove(val);
      case -1:
        return word1remove(val);
      case 0:
        return word2remove(val);
      case 1:
        return word3remove(val);
      default:
        return false;
    }
  }
  @Override
  public boolean removeVal(Integer val)
  {
    return val!=null && removeVal((int)(val));
  }
  @Override
  public boolean removeVal(long val)
  {
    final byte v;
    return val==(v=(byte)val)&&removeVal(v);
  }
  @Override
  public boolean removeVal(Long val)
  {
    return val!=null && removeVal((long)(val));
  }
  @Override
  public boolean removeVal(short val)
  {
    return removeVal((int)val);
  }
  @Override
  public boolean removeVal(Short val)
  {
    return val!=null && removeVal((int)(val));
  }
  @Override
  public int size()
  {
    return Long.bitCount(word0)
      +Long.bitCount(word1)
      +Long.bitCount(word2)
      +Long.bitCount(word3);
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    final int size;
    if((size=Long.bitCount(word0)
      +Long.bitCount(word1)
      +Long.bitCount(word2)
      +Long.bitCount(word3))!=0)
    {
      T[] arr;
      word3copy(arr=arrConstructor.apply(size),word2copy(arr,word1copy(arr,word0copy(arr,0))));
      return arr;
    }
    else
    {
    return arrConstructor.apply(0);
    }
  }
  @Override
  public <T> T[] toArray(T[] dst)
  {
    final int size;
    if((size=Long.bitCount(word0)+Long.bitCount(word1)+Long.bitCount(word2)+Long.bitCount(word3))!=0)
    {
      word3copy(dst=OmniArray.uncheckedArrResize(size,dst),word2copy(dst,word1copy(dst,word0copy(dst,0))));
    }
    else if(dst.length!=0)
    {
      dst[0]=null;
    }
    return dst;
  }
  @Override
  public Byte[] toArray()
  {
    final int size;
    if((size=Long.bitCount(word0)
      +Long.bitCount(word1)
      +Long.bitCount(word2)
      +Long.bitCount(word3))!=0)
    {
      final Byte[] arr;
      word3copy(arr=new Byte[size],word2copy(arr,word1copy(arr,word0copy(arr,0))));
      return arr;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
  }
  @Override
  public byte[] toByteArray()
  {
    final int size;
    if((size=Long.bitCount(word0)
      +Long.bitCount(word1)
      +Long.bitCount(word2)
      +Long.bitCount(word3))!=0)
    {
      final byte[] arr;
      word3copy(arr=new byte[size],word2copy(arr,word1copy(arr,word0copy(arr,0))));
      return arr;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override
  public short[] toShortArray()
  {
    final int size;
    if((size=Long.bitCount(word0)
      +Long.bitCount(word1)
      +Long.bitCount(word2)
      +Long.bitCount(word3))!=0)
    {
      final short[] arr;
      word3copy(arr=new short[size],word2copy(arr,word1copy(arr,word0copy(arr,0))));
      return arr;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override
  public int[] toIntArray()
  {
    final int size;
    if((size=Long.bitCount(word0)
      +Long.bitCount(word1)
      +Long.bitCount(word2)
      +Long.bitCount(word3))!=0)
    {
      final int[] arr;
      word3copy(arr=new int[size],word2copy(arr,word1copy(arr,word0copy(arr,0))));
      return arr;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override
  public long[] toLongArray()
  {
    final int size;
    if((size=Long.bitCount(word0)
      +Long.bitCount(word1)
      +Long.bitCount(word2)
      +Long.bitCount(word3))!=0)
    {
      final long[] arr;
      word3copy(arr=new long[size],word2copy(arr,word1copy(arr,word0copy(arr,0))));
      return arr;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override
  public float[] toFloatArray()
  {
    final int size;
    if((size=Long.bitCount(word0)
      +Long.bitCount(word1)
      +Long.bitCount(word2)
      +Long.bitCount(word3))!=0)
    {
      final float[] arr;
      word3copy(arr=new float[size],word2copy(arr,word1copy(arr,word0copy(arr,0))));
      return arr;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override
  public double[] toDoubleArray()
  {
    final int size;
    if((size=Long.bitCount(word0)
      +Long.bitCount(word1)
      +Long.bitCount(word2)
      +Long.bitCount(word3))!=0)
    {
      final double[] arr;
      word3copy(arr=new double[size],word2copy(arr,word1copy(arr,word0copy(arr,0))));
      return arr;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override
  public String toString()
  {
    long word,marker;
    final StringBuilder builder;
    int srcOffset=Byte.MIN_VALUE;
    outerword3: for(;;)
    {
      outerword2: for(;;)
      {
        outerword1: for(;;)
        {
          outerword0: for(;;)
          {
            for(word=this.word0,marker=1L;;++srcOffset)
            {
              if((word&marker)!=0)
              {
                builder=new StringBuilder("[").append(srcOffset);
                break outerword0;
              }
              if((marker<<=1)==0)
              {
                break;
              }
            }
            for(word=this.word1,marker=1L;;++srcOffset)
            {
              if((word&marker)!=0)
              {
                builder=new StringBuilder("[").append(srcOffset);
                break outerword1;
              }
              if((marker<<=1)==0)
              {
                break;
              }
            }
            for(word=this.word2,marker=1L;;++srcOffset)
            {
              if((word&marker)!=0)
              {
                builder=new StringBuilder("[").append(srcOffset);
                break outerword2;
              }
              if((marker<<=1)==0)
              {
                break;
              }
            }
            for(word=this.word3,marker=1L;;++srcOffset)
            {
              if((word&marker)!=0)
              {
                builder=new StringBuilder("[").append(srcOffset);
                break outerword3;
              }
              if((marker<<=1)==0)
              {
                break;
              }
            }
            return "[]";
          }
          for(;;)
          {
            if((marker<<=1)==0)
            {
              word=this.word1;
              break outerword1;
            }
            ++srcOffset;
            if((word&marker)!=0)
            {
              builder.append(',').append(' ').append(srcOffset);
            }
          }
        }
        for(;;)
        {
          if((marker<<=1)==0)
          {
            word=this.word2;
            break outerword2;
          }
          ++srcOffset;
          if((word&marker)!=0)
          {
            builder.append(',').append(' ').append(srcOffset);
          }
        }
      }
      for(;;)
      {
        if((marker<<=1)==0)
        {
          word=this.word3;
          break outerword3;
        }
        ++srcOffset;
        if((word&marker)!=0)
        {
          builder.append(',').append(' ').append(srcOffset);
        }
      }
    }
    while((marker<<=1)!=0)
    {
      ++srcOffset;
      if((word&marker)!=0)
      {
        builder.append(',').append(' ').append(srcOffset);
      }
    }
    return builder.append(']').toString();
  }
  static class Checked extends ByteSet
  {
    private static void checkMod(long expectedWord,long actualWord)
    {
      if(expectedWord!=actualWord)
      {
        throw new ConcurrentModificationException();
      }
    }
    Checked(long word0,long word1,long word2,long word3)
    {
      super(word0,word1,word2,word3);
    }
    Checked()
    {
      super();
    }
    @Override
    public Object clone()
    {
      return new Checked(word0,word1,word2,word3);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals method
      return false;
    }
    @Override
    public OmniIterator.OfByte iterator()
    {
      return new CheckedItr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      final long word0,word1,word2,word3;
      if((size=Long.bitCount(word0=this.word0)
        +Long.bitCount(word1=this.word1)
        +Long.bitCount(word2=this.word2)
        +Long.bitCount(word3=this.word3))!=0)
      {
        T[] arr=arrConstructor.apply(size);
        checkMod(this.word0,word0);
        checkMod(this.word1,word1);
        checkMod(this.word2,word2);
        checkMod(this.word3,word3);
        super.word3copy(arr,super.word2copy(arr,super.word1copy(arr,super.word0copy(arr,0))));
        return arr;
      }
      else
      {
      T[] arr=arrConstructor.apply(0);
      checkMod(this.word0,word0);
      checkMod(this.word1,word1);
      checkMod(this.word2,word2);
      checkMod(this.word3,word3);
      return arr;
      }
    }
    private static class CheckedItr extends AbstractByteItr
    {
      private transient final Checked root;
      private transient int srcOffset;
      private transient byte lastRet;
      private CheckedItr(Checked root)
      {
        this.root=root;
        int srcOffset=Byte.MIN_VALUE;
        long currWord=root.word0,marker=1L;
        outer: for(;;)
        {
          for(;;)
          {
            if((marker&currWord)!=0)
            {
              break outer;
            }
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
          }
          currWord=root.word1;
          marker=1L;
          for(;;)
          {
            if((marker&currWord)!=0)
            {
              break outer;
            }
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
          }
          currWord=root.word2;
          marker=1L;
          for(;;)
          {
            if((marker&currWord)!=0)
            {
              break outer;
            }
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
          }
          currWord=root.word3;
          marker=1L;
          for(;;)
          {
            if((marker&currWord)!=0)
            {
              break outer;
            }
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
          }
        }
        this.srcOffset=srcOffset;
      }
      @Override
      public boolean hasNext()
      {
        return this.srcOffset<=Byte.MAX_VALUE;
      }
      @Override
      public void remove()
      {
        byte lastRet;
        switch((lastRet=this.lastRet)>>6)
        {
          case -2:
            root.word0&=(~(1L<<lastRet));
            return;
          case -1:
            root.word1&=(~(1L<<lastRet));
            return;
          case 0:
            root.word2&=(~(1L<<lastRet));
            return;
          default:
            root.word3&=(~(1L<<lastRet));
        }
      }
      @Override
      public byte nextByte()
      {
        int srcOffset;
        final byte lastRet=(byte)(srcOffset=this.srcOffset);
        final ByteSet root=this.root;
        long currWord,marker=1L<<srcOffset;
        outerfinal: for(;;)
        {
          outerword3: for(;;)
          {
            outerword2: for(;;)
            {
              outerword1: for(;;)
              {
                outerword0: switch(srcOffset>>6)
                {
                  case -2:
                    checkMod((currWord=root.word0)&marker,marker);
                    break outerword0;
                  case -1:
                    checkMod((currWord=root.word1)&marker,marker);
                    break outerword1;
                  case 0:
                    checkMod((currWord=root.word2)&marker,marker);
                    break outerword2;
                  case 1:
                    checkMod((currWord=root.word3)&marker,marker);
                    break outerword3;
                  default:
                    throw new NoSuchElementException();
                }
                for(;;)
                {
                  ++srcOffset;
                  if((marker<<=1)==0)
                  {
                    break;
                  }
                  if((marker&currWord)!=0)
                  {
                    break outerfinal;
                  }
                }
                currWord=root.word1;
                marker=1L;
                break;
              }
              for(;;)
              {
                ++srcOffset;
                if((marker<<=1)==0)
                {
                  break;
                }
                if((marker&currWord)!=0)
                {
                  break outerfinal;
                }
              }
              currWord=root.word2;
              marker=1L;
              break;
            }
            for(;;)
            {
              ++srcOffset;
              if((marker<<=1)==0)
              {
                break;
              }
              if((marker&currWord)!=0)
              {
                break outerfinal;
              }
            }
            currWord=root.word3;
            marker=1L;
            break;
          }
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((marker&currWord)!=0)
            {
              break outerfinal;
            }
          }
          break;
        }
        this.srcOffset=srcOffset;
        this.lastRet=lastRet;
        return lastRet;
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
      {
        var root=this.root;
        int srcOffset;
        long word,marker=1L<<(srcOffset=this.srcOffset);
        byte lastRet;
        outerword3: for(;;)
        {
          outerword2: for(;;)
          {
            outerword1: for(;;)
            {
              outerword0: switch(srcOffset>>6)
              {
              case -2:
                checkMod((word=root.word0)&marker,marker);
                action.accept(lastRet=(byte)srcOffset);
                break outerword0;
              case -1:
                checkMod((word=root.word1)&marker,marker);
                action.accept(lastRet=(byte)srcOffset);
                break outerword1;
              case 0:
                checkMod((word=root.word2)&marker,marker);
                action.accept(lastRet=(byte)srcOffset);
                break outerword2;
              case 1:
                checkMod((word=root.word3)&marker,marker);
                action.accept(lastRet=(byte)srcOffset);
                break outerword3;
              default:
                return;
              }
              for(;;)
              {
                ++srcOffset;
                if((marker<<=1)==0)
                {
                  break;
                }
                if((word&marker)!=0)
                {
                  action.accept(lastRet=(byte)srcOffset);
                }
              }
              checkMod(root.word0,word);
              marker=1L;
              word=root.word1;
              break outerword1;
            }
            for(;;)
            {
              ++srcOffset;
              if((marker<<=1)==0)
              {
                break;
              }
              if((word&marker)!=0)
              {
                action.accept(lastRet=(byte)srcOffset);
              }
            }
            checkMod(root.word1,word);
            marker=1L;
            word=root.word2;
            break outerword2;
          }
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((word&marker)!=0)
            {
              action.accept(lastRet=(byte)srcOffset);
            }
          }
          checkMod(root.word2,word);
          marker=1L;
          word=root.word3;
          break outerword3;
        }
        for(;;)
        {
          ++srcOffset;
          if((marker<<=1)==0)
          {
            break;
          }
          if((word&marker)!=0)
          {
            action.accept(lastRet=(byte)srcOffset);
          }
        }
        checkMod(root.word3,word);
        this.lastRet=lastRet;
        this.srcOffset=srcOffset;
      }
      @Override
      public void forEachRemaining(Consumer<? super Byte> action)
      {
        var root=this.root;
        int srcOffset;
        long word,marker=1L<<(srcOffset=this.srcOffset);
        byte lastRet;
        outerword3: for(;;)
        {
          outerword2: for(;;)
          {
            outerword1: for(;;)
            {
              outerword0: switch(srcOffset>>6)
              {
              case -2:
                checkMod((word=root.word0)&marker,marker);
                action.accept(lastRet=(byte)srcOffset);
                break outerword0;
              case -1:
                checkMod((word=root.word1)&marker,marker);
                action.accept(lastRet=(byte)srcOffset);
                break outerword1;
              case 0:
                checkMod((word=root.word2)&marker,marker);
                action.accept(lastRet=(byte)srcOffset);
                break outerword2;
              case 1:
                checkMod((word=root.word3)&marker,marker);
                action.accept(lastRet=(byte)srcOffset);
                break outerword3;
              default:
                return;
              }
              for(;;)
              {
                ++srcOffset;
                if((marker<<=1)==0)
                {
                  break;
                }
                if((word&marker)!=0)
                {
                  action.accept(lastRet=(byte)srcOffset);
                }
              }
              checkMod(root.word0,word);
              marker=1L;
              word=root.word1;
              break outerword1;
            }
            for(;;)
            {
              ++srcOffset;
              if((marker<<=1)==0)
              {
                break;
              }
              if((word&marker)!=0)
              {
                action.accept(lastRet=(byte)srcOffset);
              }
            }
            checkMod(root.word1,word);
            marker=1L;
            word=root.word2;
            break outerword2;
          }
          for(;;)
          {
            ++srcOffset;
            if((marker<<=1)==0)
            {
              break;
            }
            if((word&marker)!=0)
            {
              action.accept(lastRet=(byte)srcOffset);
            }
          }
          checkMod(root.word2,word);
          marker=1L;
          word=root.word3;
          break outerword3;
        }
        for(;;)
        {
          ++srcOffset;
          if((marker<<=1)==0)
          {
            break;
          }
          if((word&marker)!=0)
          {
            action.accept(lastRet=(byte)srcOffset);
          }
        }
        checkMod(root.word3,word);
        this.lastRet=lastRet;
        this.srcOffset=srcOffset;
      }
    }
  }
  private static void consumeWord(long word,ByteConsumer action,int srcOffset)
  {
    for(long marker=1L<<srcOffset;;++srcOffset)
    {
      if((word&marker)!=0)
      {
        action.accept((byte)srcOffset);
      }
      if((marker<<=1)==0)
      {
        return;
      }
    }
  }
}
