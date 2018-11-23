package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractCharList extends AbstractSeq.Of16BitPrimitive
{
  protected AbstractCharList()
  {
    super();
  }
  protected AbstractCharList(int size)
  {
    super(size);
  }
  protected abstract boolean add(char val);
  public final boolean add(boolean val)
  {
    return add(TypeUtil.castToChar(val));
  } 
  public final boolean add(Character val)
  {
    return add((char)val);
  }
  public final Character remove(int index)
  {
    return removeCharAt(index);
  }
  public final Character set(int index,Character val)
  {
    return set(index,(char)val);
  }
  protected abstract void add(int index,char val);
  protected abstract char getChar(int index);
  protected abstract char removeCharAt(int index);
  protected abstract char set(int index,char val);
  public final void add(int index,Character val)
  {
    add(index,(char)val);
  }
  public final Character get(int index)
  {
    return getChar(index);
  }
  protected abstract int uncheckedlastIndexOf(int size,int val);
  public int lastIndexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Character)
      {
         return uncheckedlastIndexOf(size,(char)val);
      }
    }
    return -1;
  }
  public int lastIndexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return uncheckedlastIndexOf(size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return uncheckedlastIndexOf(size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return uncheckedlastIndexOf(size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return uncheckedlastIndexOf(size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(short val)
  {
    if(val>=0)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedlastIndexOf(size,(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedlastIndexOf(size,(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,TypeUtil.castToChar(val));
    }
    return -1;
  }
}
