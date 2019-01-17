package omni.impl.seq;
import omni.util.TypeUtil;
import omni.api.OmniCollection;
abstract class AbstractDoubleList extends AbstractSeq.OfDouble implements OmniCollection.OfDouble
{
  protected AbstractDoubleList()
  {
    super();
  }
  protected AbstractDoubleList(int size)
  {
    super(size);
  }
  @Override
  public final boolean add(int val)
  {
    return add((double)val);
  }
  @Override
  public final boolean add(float val)
  {
    return add((double)val);
  }
  @Override
  public final boolean add(char val)
  {
    return add((double)val);
  }
  @Override
  public final boolean add(short val)
  {
    return add((double)val);
  }
  @Override
  public final boolean add(long val)
  {
   return add((double)val);
  }
  @Override
  public final boolean add(boolean val)
  {
    return add(TypeUtil.castToDouble(val));
  } 
  @Override
  public final boolean add(Double val)
  {
    return add((double)val);
  }
  protected abstract void add(int index,double val);
  protected abstract double getDouble(int index);
  protected abstract double removeDoubleAt(int index);
  protected abstract double set(int index,double val);
  public final Double remove(int index)
  {
    return removeDoubleAt(index);
  }
  public final Double set(int index,Double val)
  {
    return set(index,(double)val);
  }
  public final void add(int index,Double val)
  {
    add(index,(double)val);
  }
  public final Double get(int index)
  {
    return getDouble(index);
  }
}
