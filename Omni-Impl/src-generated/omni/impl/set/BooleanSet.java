package omni.impl.set;
import omni.api.OmniSet;
import omni.function.BooleanPredicate;
import omni.function.BooleanConsumer;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.util.OmniArray;
import java.util.function.IntFunction;
import omni.api.OmniIterator;
public class BooleanSet implements OmniSet.OfBoolean
{
  transient int state;
  BooleanSet()
  {
  }
  BooleanSet(int state)
  {
    this.state=state;
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
  public void clear()
  {
    this.state=0b00;
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO
    return false;
  }
  @Override
  public OmniIterator.OfBoolean iterator()
  {
    //TODO
    return null;
  }
  @Override
  public String toString()
  {
    switch(this.state)
    {
    case 0b01:
      return "[false]";
    case 0b10:
      return "[true]";
    case 0b11:
      return "[false, true]";
    default:
    }
    return "[]";
  }
  @Override
  public int hashCode()
  {
    switch(this.state)
    {
    case 0b01:
      return 1237;
    case 0b10:
      return 1231;
    case 0b11:
      return 1231+1237;
    default:
      return 0;
    }
  }
  @Override
  public <T> T[] toArray(T[] dst)
  {
    switch(this.state)
    {
    case 0b01:
      (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
      break;
    case 0b10:
      (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
      break;
    case 0b11:
      (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.FALSE;
      dst[1]=(T)Boolean.TRUE;
      break;
    default:
      if(dst.length!=0)
      {
        dst[0]=null;
      }
    }
    return dst;
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    final T[] dst;
    switch(this.state)
    {
    case 0b01:
      (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
      break;
    case 0b10:
      (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
      break;
    case 0b11:
      (dst=arrConstructor.apply(2))[1]=(T)Boolean.TRUE;
      dst[0]=(T)Boolean.FALSE;
      break;
    default:
      dst=arrConstructor.apply(0);
    }
    return dst;
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
  public char[] toCharArray()
  {
    switch(this.state)
    {
    case 0b01:
      return new char[]{(char)0};
    case 0b10:
      return new char[]{(char)1};
    case 0b11:
      return new char[]{(char)0,(char)1};
    default:
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override
  public boolean removeIf(Predicate<? super Boolean> filter)
  {
    return removeIf((BooleanPredicate)filter::test);
  }
  @Override
  public void forEach(Consumer<? super Boolean> action)
  {
    forEach((BooleanConsumer)action::accept);
  }
  @Override
  public boolean removeIf(BooleanPredicate filter)
  {
    returnTrue:for(;;)
    {
      setEmpty:for(;;)
      {
        returnFalse:switch(this.state)
        {
        case 0b01:
          if(filter.test(false))
          {
            break setEmpty;
          }
          break returnFalse;
        case 0b10:
          if(filter.test(true))
          {
            break setEmpty;
          }
          break returnFalse;
        case 0b11:
          if(filter.test(false))
          {
            if(filter.test(true))
            {
              break setEmpty;
            }
            this.state=0b10;
            break returnTrue;
          }
          if(filter.test(true))
          {
            this.state=0b01;
            break returnTrue;
          }
        default:
        }
        return false;
      }
      this.state=0b00;
      break;
    }
    return true;
  }
  @Override
  public void forEach(BooleanConsumer action)
  {
    switch(this.state)
    {
    case 0b01:
      action.accept(false);
      return;
    case 0b11:
      action.accept(false);
    case 0b10:
      action.accept(true);
    default:
    }
  }
  @Override
  public boolean add(Boolean val)
  {
    return add((boolean)(val));
  }
  @Override
  public boolean add(boolean val)
  {
    returnTrue:for(;;)
    {
      setFull:for(;;)
      {
        switch(this.state)
        {
        default:
          this.state=val?0b10:0b01;
          break returnTrue;
        case 0b01:
          if(val)
          {
            break setFull;
          }
          break;
        case 0b10:
          if(!val)
          {
            break setFull;
          }
        case 0b11:
        }
        return false;
      }
      this.state=0b11;
      break;
    }
    return true; 
  }
  @Override
  public boolean contains(Object val)
  {
    return val instanceof Boolean && contains((boolean)val);
  }
  @Override
  public boolean remove(Object val)
  {
    return val instanceof Boolean && removeVal((boolean)val);
  }
}
