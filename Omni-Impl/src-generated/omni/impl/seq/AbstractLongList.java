package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractLongList extends AbstractSeq.OfSignedIntegralPrimitive
{
  protected AbstractLongList()
  {
    super();
  }
  protected AbstractLongList(int size)
  {
    super(size);
  }
  protected abstract boolean add(long val);
  public final boolean add(int val)
  {
    return add((long)val);
  }
  public final boolean add(boolean val)
  {
    return add(TypeUtil.castToLong(val));
  } 
  public final boolean add(Long val)
  {
    return add((long)val);
  }
  public final Long remove(int index)
  {
    return removeLongAt(index);
  }
  public final Long set(int index,Long val)
  {
    return set(index,(long)val);
  }
  protected abstract void add(int index,long val);
  protected abstract long getLong(int index);
  protected abstract long removeLongAt(int index);
  protected abstract long set(int index,long val);
  public final void add(int index,Long val)
  {
    add(index,(long)val);
  }
  public final Long get(int index)
  {
    return getLong(index);
  }
  protected abstract int uncheckedlastIndexOf(int size,long val);
  public int lastIndexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Long)
      {
         return uncheckedlastIndexOf(size,(long)val);
      }
    }
    return -1;
  }
  public int lastIndexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(TypeUtil.doubleEquals(val,v=(long)val))
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
      final long v;
      if(TypeUtil.floatEquals(val,v=(long)val))
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
      return uncheckedlastIndexOf(size,(val));
    }
    return -1;
  }
  public int lastIndexOf(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
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
      return uncheckedlastIndexOf(size,TypeUtil.castToLong(val));
    }
    return -1;
  }
}
