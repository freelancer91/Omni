package omni.impl.set;
import omni.api.OmniSet;
import omni.function.BooleanPredicate;
import java.util.function.Predicate;
import omni.util.OmniArray;
import java.util.function.IntFunction;
public class BooleanSet implements OmniSet.OfBoolean
{
  transient int state;
  BooleanSet()
  {
    super();
  }
  BooleanSet(int state)
  {
    super();
    this.state=state;
  }
  @Override
  public boolean add(boolean val)
  {
    switch(this.state)
    {
      case 0b01:
        if(val)
        {
          break;
        }
        return false;
      case 0b10:
        if(!val)
        {
          break;
        }
      case 0b11:
        return false;
      default:
        this.state=val?0b10:0b01;
        return true;
    }
    this.state=0b11;
    return true;
  }
  @Override
  public boolean add(Boolean val)
  {
    return add((boolean)(val));
  }
  @Override
  public boolean removeVal(boolean val)
  {
    switch(this.state)
    {
      case 0b01:
        if(!val)
        {
          break;
        }
        return false;
      case 0b10:
        if(val)
        {
          break;
        }
      default:
        return false;
      case 0b11:
        this.state=val?0b01:0b10;
        return true;
    }
    this.state=0b00;
    return true;
  }
  @Override
  public int size()
  {
    switch(this.state)
    {
      case 0b01:
      case 0b10:
        return 1;
      case 0b11:
        return 2;
      default:
        return 0;
    }
  }
  @Override
  public boolean isEmpty()
  {
    return this.state==0b00;
  }
  @Override
  public boolean removeIf(Predicate<? super Boolean> filter)
  {
    return removeIf((BooleanPredicate)filter::test);
  }
  @Override
  public boolean removeIf(BooleanPredicate filter)
  {
    switch(this.state)
    {
      case 0b01:
        if(filter.test(false))
        {
          break;
        }
        return false;
      case 0b10:
        if(filter.test(true))
        {
          break;
        }
        return false;
      case 0b11:
        if(filter.test(true))
        {
          this.state=filter.test(false)?0b00:0b01;
          return true;
        }
        if(filter.test(false))
        {
          this.state=0b10;
          return true;
        }
      default:
        return false;
    }
    this.state=0b00;
    return true;
  }
  @Override
  public boolean[] toBooleanArray()
  {
    switch(this.state)
    {
      case 0b01:
        return new boolean[]{false};
      case 0b10:
        return new boolean[]{true};
      case 0b11:
        return new boolean[]{false,true};
      default:
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override
  public Boolean[] toArray()
  {
    switch(this.state)
    {
      case 0b01:
        return new Boolean[]{Boolean.FALSE};
      case 0b10:
        return new Boolean[]{Boolean.TRUE};
      case 0b11:
        return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
      default:
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override
  public double[] toDoubleArray()
  {
    switch(this.state)
    {
      case 0b01:
        return new double[]{0D};
      case 0b10:
        return new double[]{1D};
      case 0b11:
        return new double[]{0D,1D};
      default:
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override
  public float[] toFloatArray()
  {
    switch(this.state)
    {
      case 0b01:
        return new float[]{0F};
      case 0b10:
        return new float[]{1F};
      case 0b11:
        return new float[]{0F,1F};
      default:
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override
  public long[] toLongArray()
  {
    switch(this.state)
    {
      case 0b01:
        return new long[]{0L};
      case 0b10:
        return new long[]{1L};
      case 0b11:
        return new long[]{0L,1L};
      default:
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override
  public int[] toIntArray()
  {
    switch(this.state)
    {
      case 0b01:
        return new int[]{0};
      case 0b10:
        return new int[]{1};
      case 0b11:
        return new int[]{0,1};
      default:
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override
  public short[] toShortArray()
  {
    switch(this.state)
    {
      case 0b01:
        return new short[]{(short)0};
      case 0b10:
        return new short[]{(short)1};
      case 0b11:
        return new short[]{(short)0,(short)1};
      default:
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override
  public byte[] toByteArray()
  {
    switch(this.state)
    {
      case 0b01:
        return new byte[]{(byte)0};
      case 0b10:
        return new byte[]{(byte)1};
      case 0b11:
        return new byte[]{(byte)0,(byte)1};
      default:
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override
  public boolean remove(Object val)
  {
    return val instanceof Boolean && removeVal((boolean)val);
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    //TODO
    return null;
  }
  @Override
  public <T> T[] toArray(T[] arr)
  {
    //TODO
    return arr;
  }
}
