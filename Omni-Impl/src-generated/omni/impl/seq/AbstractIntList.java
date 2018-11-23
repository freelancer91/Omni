package omni.impl.seq;
import omni.util.TypeUtil;
public abstract class AbstractIntList extends AbstractSeq.OfSignedIntegralPrimitive
{
  protected AbstractIntList()
  {
    super();
  }
  protected AbstractIntList(int size)
  {
    super(size);
  }
  protected abstract boolean add(int val);
  public final boolean add(boolean val)
  {
    return add((int)TypeUtil.castToByte(val));
  } 
  public final boolean add(Integer val)
  {
    return add((int)val);
  }
  public final Integer remove(int index)
  {
    return removeIntAt(index);
  }
  public final Integer set(int index,Integer val)
  {
    return set(index,(int)val);
  }
  protected abstract void add(int index,int val);
  protected abstract int getInt(int index);
  protected abstract int removeIntAt(int index);
  protected abstract int set(int index,int val);
  public final void add(int index,Integer val)
  {
    add(index,(int)val);
  }
  public final Integer get(int index)
  {
    return getInt(index);
  }
  protected abstract int uncheckedlastIndexOf(int size,int val);
  public int lastIndexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Integer)
      {
         return uncheckedlastIndexOf(size,(int)val);
      }
    }
    return -1;
  }
  public int lastIndexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int v;
      if(val==(v=(int)val))
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
      final int v;
      if((double)val==(double)(v=(int)val))
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
      final int v;
      if(val==(v=(int)val))
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
      return uncheckedlastIndexOf(size,(int)TypeUtil.castToByte(val));
    }
    return -1;
  }
}
