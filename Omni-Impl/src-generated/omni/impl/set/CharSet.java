package omni.impl.set;
import omni.api.OmniSet;
import omni.function.CharPredicate;
import omni.function.CharConsumer;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.util.OmniArray;
import java.util.function.IntFunction;
import omni.api.OmniIterator;
abstract class CharSet implements OmniSet.OfChar
{
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  CharSet()
  {
  }
  CharSet(long word0,long word1,long word2,long word3)
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
  public OmniIterator.OfChar iterator()
  {
    //TODO
    return null;
  }
  @Override
  public int hashCode()
  {
    return wordHash(this.word0,Character.MIN_VALUE)
      +wordHash(this.word1,Character.MIN_VALUE+64)
      +wordHash(this.word2,Character.MIN_VALUE+128)
      +wordHash(this.word3,Character.MIN_VALUE+192);
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
  public boolean add(Character val)
  {
    return add((char)(val));
  }
  @Override
  public boolean add(boolean val)
  {
    long word;
    if((word=this.word2)!=(word|=(val?0b10L:0b01L)))
    {
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override
  public boolean contains(double val)
  {
    final char v;
    return val==(v=(char)val) && contains(v);
  }
  @Override
  public boolean contains(float val)
  {
    final char v;
    return val==(v=(char)val) && contains(v);
  }
  @Override
  public boolean contains(long val)
  {
    final char v;
    return val==(v=(char)val) && contains(v);
  }
  @Override
  public boolean removeVal(double val)
  {
    final char v;
    return val==(v=(char)val) && removeVal(v);
  }
  @Override
  public boolean removeVal(float val)
  {
    final char v;
    return val==(v=(char)val) && removeVal(v);
  }
  @Override
  public boolean removeVal(long val)
  {
    final char v;
    return val==(v=(char)val) && removeVal(v);
  }
  @Override
  public boolean contains(Object val)
  {
    return val instanceof Character && contains((char)val);
  }
  @Override
  public boolean remove(Object val)
  {
    return val instanceof Character && removeVal((char)val);
  }
}
