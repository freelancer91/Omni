package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractBooleanList extends AbstractSeq
{
  protected AbstractBooleanList()
  {
    super();
  }
  protected AbstractBooleanList(int size)
  {
    super(size);
  }
  protected abstract boolean add(boolean val);
  public final boolean add(Boolean val)
  {
    return add((boolean)val);
  }
  public final Boolean remove(int index)
  {
    return removeBooleanAt(index);
  }
  public final Boolean set(int index,Boolean val)
  {
    return set(index,(boolean)val);
  }
  protected abstract void add(int index,boolean val);
  protected abstract boolean getBoolean(int index);
  protected abstract boolean removeBooleanAt(int index);
  protected abstract boolean set(int index,boolean val);
  public final void add(int index,Boolean val)
  {
    add(index,(boolean)val);
  }
  public final Boolean get(int index)
  {
    return getBoolean(index);
  }
  protected abstract int uncheckedlastIndexOf(int size,boolean val);
  public int lastIndexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Boolean)
      {
         return uncheckedlastIndexOf(size,(boolean)val);
      }
    }
    return -1;
  }
  public int lastIndexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return -1;
      }
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
      final boolean v;
      switch(Float.floatToRawIntBits(val))
      {
      default:
        return -1;
      case 0:
      case Integer.MIN_VALUE:
        v=false;
        break;
      case TypeUtil.FLT_TRUE_BITS:
        v=true;
      }
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
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return -1;
      }
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
      final boolean v;
      switch(val)
      {
      default:
        return -1;
      case 0:
        v=false;
        break;
      case 1:
        v=true;
      }
      {
        return uncheckedlastIndexOf(size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,(val));
    }
    return -1;
  }
}
