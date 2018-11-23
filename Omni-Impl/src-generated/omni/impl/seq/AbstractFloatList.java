package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractFloatList extends AbstractSeq.OfFloat
{
  protected AbstractFloatList()
  {
    super();
  }
  protected AbstractFloatList(int size)
  {
    super(size);
  }
  protected abstract boolean add(float val);
  public final boolean add(int val)
  {
    return add((float)val);
  }
  public final boolean add(char val)
  {
    return add((float)val);
  }
  public final boolean add(short val)
  {
    return add((float)val);
  }
  public final boolean add(long val)
  {
   return add((float)val);
  }
  public final boolean add(boolean val)
  {
    return add(TypeUtil.castToFloat(val));
  } 
  public final boolean add(Float val)
  {
    return add((float)val);
  }
  public final Float remove(int index)
  {
    return removeFloatAt(index);
  }
  public final Float set(int index,Float val)
  {
    return set(index,(float)val);
  }
  protected abstract void add(int index,float val);
  protected abstract float getFloat(int index);
  protected abstract float removeFloatAt(int index);
  protected abstract float set(int index,float val);
  public final void add(int index,Float val)
  {
    add(index,(float)val);
  }
  public final Float get(int index)
  {
    return getFloat(index);
  }
  protected abstract int uncheckedlastIndexOf0(int size);
  protected abstract int uncheckedlastIndexOfBits(int size,int bits);
  protected abstract int uncheckedlastIndexOfNaN(int size);
  private int uncheckedlastIndexOf(int size,float val)
  {
    if(val==val)
    {
      return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
    }
    return uncheckedlastIndexOfNaN(size);
  }
  private int uncheckedlastIndexOfRawInt(int size,int val)
  {
    if(val!=0)
    {
      return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
    }
    return uncheckedlastIndexOf0(size);
  }
  public int lastIndexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Float)
      {
         return uncheckedlastIndexOf(size,(float)val);
      }
    }
    return -1;
  }
  public int lastIndexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float v;
      if((v=(float)val)==val)
      {
        return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return uncheckedlastIndexOfNaN(size);
      }
    }
    return -1;
  }
  public int lastIndexOf(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedlastIndexOf(size,(val));
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
        if(!TypeUtil.checkCastToFloat(val))
        {
          return -1;
        }
        return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
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
        if(!TypeUtil.checkCastToFloat(val))
        {
          return -1;
        }
        return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
      }
      return uncheckedlastIndexOf0(size);
    }
    return -1;
  }
  public int lastIndexOf(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedlastIndexOfRawInt(size,val);
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
        return uncheckedlastIndexOfRawInt(size,val);
      }
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
        return uncheckedlastIndexOfBits(size,TypeUtil.FLT_TRUE_BITS);
      }
      return uncheckedlastIndexOf0(size);
    }
    return -1;
  }
}
