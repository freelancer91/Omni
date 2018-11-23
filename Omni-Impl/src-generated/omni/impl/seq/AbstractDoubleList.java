package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractDoubleList extends AbstractSeq.OfDouble
{
  protected AbstractDoubleList()
  {
    super();
  }
  protected AbstractDoubleList(int size)
  {
    super(size);
  }
  protected abstract boolean add(double val);
  public final boolean add(int val)
  {
    return add((double)val);
  }
  public final boolean add(float val)
  {
    return add((double)val);
  }
  public final boolean add(char val)
  {
    return add((double)val);
  }
  public final boolean add(short val)
  {
    return add((double)val);
  }
  public final boolean add(long val)
  {
   return add((double)val);
  }
  public final boolean add(boolean val)
  {
    return add(TypeUtil.castToDouble(val));
  } 
  public final boolean add(Double val)
  {
    return add((double)val);
  }
  public final Double remove(int index)
  {
    return removeDoubleAt(index);
  }
  public final Double set(int index,Double val)
  {
    return set(index,(double)val);
  }
  protected abstract void add(int index,double val);
  protected abstract double getDouble(int index);
  protected abstract double removeDoubleAt(int index);
  protected abstract double set(int index,double val);
  public final void add(int index,Double val)
  {
    add(index,(double)val);
  }
  public final Double get(int index)
  {
    return getDouble(index);
  }
  protected abstract int uncheckedlastIndexOf0(int size);
  protected abstract int uncheckedlastIndexOfBits(int size,long bits);
  protected abstract int uncheckedlastIndexOfNaN(int size);
  private int uncheckedlastIndexOf(int size,double val)
  {
    if(val==val)
    {
      return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
    }
    return uncheckedlastIndexOfNaN(size);
  }
  public int lastIndexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Double)
      {
         return uncheckedlastIndexOf(size,(double)val);
      }
    }
    return -1;
  }
  public int lastIndexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
     return uncheckedlastIndexOf(size,(val));
    }
    return -1;
  }
  public int lastIndexOf(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==val)
      {
        return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
      }
      return uncheckedlastIndexOfNaN(size);
    }
    return -1;
  }
  public int lastIndexOf(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(!TypeUtil.checkCastToDouble(val))
        {
          return -1;
        }
        return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
      }
      return uncheckedlastIndexOf0(size);
    }
    return -1;
  }
  public int lastIndexOf(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
      }
      return uncheckedlastIndexOf0(size);
    }
    return -1;
  }
  public int lastIndexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val)
      {
        return uncheckedlastIndexOfBits(size,TypeUtil.DBL_TRUE_BITS);
      }
      return uncheckedlastIndexOf0(size);
    }
    return -1;
  }
}
