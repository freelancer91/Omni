package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractShortList extends AbstractSeq.Of16BitPrimitive
{
  protected AbstractShortList()
  {
    super();
  }
  protected AbstractShortList(int size)
  {
    super(size);
  }
  protected abstract boolean add(short val);
  public final boolean add(boolean val)
  {
    return add((short)TypeUtil.castToByte(val));
  } 
  public final boolean add(Short val)
  {
    return add((short)val);
  }
  public final Short remove(int index)
  {
    return removeShortAt(index);
  }
  public final Short set(int index,Short val)
  {
    return set(index,(short)val);
  }
  protected abstract void add(int index,short val);
  protected abstract short getShort(int index);
  protected abstract short removeShortAt(int index);
  protected abstract short set(int index,short val);
  public final void add(int index,Short val)
  {
    add(index,(short)val);
  }
  public final Short get(int index)
  {
    return getShort(index);
  }
  protected abstract int uncheckedlastIndexOf(int size,int val);
  public int lastIndexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Short)
      {
         return uncheckedlastIndexOf(size,(short)val);
      }
    }
    return -1;
  }
  public int lastIndexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short v;
      if(val==(v=(short)val))
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
      final short v;
      if(val==(v=(short)val))
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
      final short v;
      if(val==(v=(short)val))
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
      if(val==(short)val)
      {
	    return uncheckedlastIndexOf(size,(val));
	  }
    }
    return -1;
  }
  public int lastIndexOf(short val)
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
  public int lastIndexOf(char val)
  {
    if(val<=Short.MAX_VALUE)
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
      return uncheckedlastIndexOf(size,(short)TypeUtil.castToByte(val));
    }
    return -1;
  }
}
