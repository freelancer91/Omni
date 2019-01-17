package omni.impl.seq;
import omni.util.TypeUtil;
import omni.api.OmniCollection;
abstract class AbstractFloatList extends AbstractSeq.OfFloat implements OmniCollection.OfFloat
{
  protected AbstractFloatList()
  {
    super();
  }
  protected AbstractFloatList(int size)
  {
    super(size);
  }
  @Override
  public final boolean add(int val)
  {
    return add((float)val);
  }
  @Override
  public final boolean add(char val)
  {
    return add((float)val);
  }
  @Override
  public final boolean add(short val)
  {
    return add((float)val);
  }
  @Override
  public final boolean add(long val)
  {
   return add((float)val);
  }
  @Override
  public final boolean add(boolean val)
  {
    return add(TypeUtil.castToFloat(val));
  } 
  @Override
  public final boolean add(Float val)
  {
    return add((float)val);
  }
  protected abstract void add(int index,float val);
  protected abstract float getFloat(int index);
  protected abstract float removeFloatAt(int index);
  protected abstract float set(int index,float val);
  public final Float remove(int index)
  {
    return removeFloatAt(index);
  }
  public final Float set(int index,Float val)
  {
    return set(index,(float)val);
  }
  public final void add(int index,Float val)
  {
    add(index,(float)val);
  }
  public final Float get(int index)
  {
    return getFloat(index);
  }
}
