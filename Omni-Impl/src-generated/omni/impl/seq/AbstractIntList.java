package omni.impl.seq;
import omni.util.TypeUtil;
import omni.api.OmniCollection;
abstract class AbstractIntList extends AbstractSeq.OfSignedIntegralPrimitive implements OmniCollection.OfInt
{
  protected AbstractIntList()
  {
    super();
  }
  protected AbstractIntList(int size)
  {
    super(size);
  }
  @Override
  public final boolean add(boolean val)
  {
    return add((int)TypeUtil.castToByte(val));
  } 
  @Override
  public final boolean add(Integer val)
  {
    return add((int)val);
  }
  protected abstract void add(int index,int val);
  protected abstract int getInt(int index);
  protected abstract int removeIntAt(int index);
  protected abstract int set(int index,int val);
  public final Integer remove(int index)
  {
    return removeIntAt(index);
  }
  public final Integer set(int index,Integer val)
  {
    return set(index,(int)val);
  }
  public final void add(int index,Integer val)
  {
    add(index,(int)val);
  }
  public final Integer get(int index)
  {
    return getInt(index);
  }
}
