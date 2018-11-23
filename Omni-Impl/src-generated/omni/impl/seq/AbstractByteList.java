package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractByteList extends AbstractSeq
{
  protected AbstractByteList()
  {
    super();
  }
  protected AbstractByteList(int size)
  {
    super(size);
  }
  protected abstract boolean add(byte val);
  public final boolean add(boolean val)
  {
    return add(TypeUtil.castToByte(val));
  } 
  public final boolean add(Byte val)
  {
    return add((byte)val);
  }
  public int lastIndexOf(byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,(val));
    }
    return -1;
  }
  public final Byte remove(int index)
  {
    return removeByteAt(index);
  }
  public final Byte set(int index,Byte val)
  {
    return set(index,(byte)val);
  }
  protected abstract void add(int index,byte val);
  protected abstract byte getByte(int index);
  protected abstract byte removeByteAt(int index);
  protected abstract byte set(int index,byte val);
  public final void add(int index,Byte val)
  {
    add(index,(byte)val);
  }
  public final Byte get(int index)
  {
    return getByte(index);
  }
  protected abstract int uncheckedlastIndexOf(int size,int val);
  public int lastIndexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Byte)
      {
         return uncheckedlastIndexOf(size,(byte)val);
      }
    }
    return -1;
  }
  public int lastIndexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
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
      final byte v;
      if(val==(v=(byte)val))
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
      final byte v;
      if(val==(v=(byte)val))
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
      final byte v;
      if(val==(v=(byte)val))
      {
        return uncheckedlastIndexOf(size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(char val)
  {
    if(val<=Byte.MAX_VALUE)
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
      return uncheckedlastIndexOf(size,TypeUtil.castToByte(val));
    }
    return -1;
  }
}
