package omni.impl;
import omni.api.OmniIterator;
import omni.util.TypeUtil;
public abstract class AbstractBooleanItr implements OmniIterator.OfBoolean
{
  @Override
  public Boolean next()
  {
    return (Boolean)(nextBoolean());
  }
  @Override
  public double nextDouble()
  {
    return TypeUtil.castToDouble(nextBoolean());
  }
  @Override
  public float nextFloat()
  {
    return TypeUtil.castToFloat(nextBoolean());
  }
  @Override
  public long nextLong()
  {
    return TypeUtil.castToLong(nextBoolean());
  }
  @Override
  public int nextInt()
  {
    return (int)TypeUtil.castToByte(nextBoolean());
  }
  @Override
  public short nextShort()
  {
    return (short)TypeUtil.castToByte(nextBoolean());
  }
  @Override
  public byte nextByte()
  {
    return TypeUtil.castToByte(nextBoolean());
  }
  @Override
  public char nextChar()
  {
    return TypeUtil.castToChar(nextBoolean());
  }
}
