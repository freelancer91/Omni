package omni.impl;
import omni.api.OmniIterator;
public abstract class AbstractByteItr implements OmniIterator.OfByte
{
  @Override public abstract Object clone();
  @Override
  public Byte next()
  {
    return (Byte)(nextByte());
  }
  @Override
  public double nextDouble()
  {
    return (double)(nextByte());
  }
  @Override
  public float nextFloat()
  {
    return (float)(nextByte());
  }
  @Override
  public long nextLong()
  {
    return (long)(nextByte());
  }
  @Override
  public int nextInt()
  {
    return (int)(nextByte());
  }
  @Override
  public short nextShort()
  {
    return (short)(nextByte());
  }
}
