package omni.impl.seq;
import omni.util.TypeUtil;
import omni.api.OmniCollection;
abstract class AbstractCharList extends AbstractSeq.Of16BitPrimitive implements OmniCollection.OfChar
{
  protected AbstractCharList()
  {
    super();
  }
  protected AbstractCharList(int size)
  {
    super(size);
  }
  @Override
  public final boolean add(boolean val)
  {
    return add(TypeUtil.castToChar(val));
  } 
  @Override
  public final boolean add(Character val)
  {
    return add((char)val);
  }
  protected abstract void add(int index,char val);
  protected abstract char getChar(int index);
  protected abstract char removeCharAt(int index);
  protected abstract char set(int index,char val);
  public final Character remove(int index)
  {
    return removeCharAt(index);
  }
  public final Character set(int index,Character val)
  {
    return set(index,(char)val);
  }
  public final void add(int index,Character val)
  {
    add(index,(char)val);
  }
  public final Character get(int index)
  {
    return getChar(index);
  }
}
