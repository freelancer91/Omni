package omni.impl;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class BooleanSet implements OmniSet.OfBoolean
{
  static class Checked extends BooleanSet
  {
    Checked(int state)
    {
      super(state);
    }
    Checked()
    {
      super();
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals method
      return false;
    }
    private void checkMod(int expectedState)
    {
      if(this.state!=expectedState)
      {
        throw new ConcurrentModificationException();
      }
    }
    @Override
    public Object clone(){
        return new Checked(this.state);
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      T[] dst;
      switch(state)
      {
        case 0b01:
          (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
          this.checkMod(0b01);
          break;
        case 0b10:
          (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
          this.checkMod(0b10);
          break;
        case 0b11:
          (dst=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
          this.checkMod(0b11);
          dst[1]=(T)Boolean.TRUE;
          break;
        default:
          dst=arrConstructor.apply(0);
          this.checkMod(0b00);
      }
      return dst;
    }
    @Override
    public void forEach(BooleanConsumer action)
    {
      switch(state)
      {
        case 0b11:
          action.accept(false);
          action.accept(true);
          this.checkMod(0b11);
          return;
        case 0b10:
          action.accept(true);
          this.checkMod(0b10);
          return;
        case 0b01:
          action.accept(false);
          this.checkMod(0b01);
        default:
      }
    }
    @Override
    public void forEach(Consumer<? super Boolean> action)
    {
      switch(state)
      {
        case 0b11:
          action.accept(Boolean.FALSE);
          action.accept(Boolean.TRUE);
          this.checkMod(0b11);
          return;
        case 0b10:
          action.accept(Boolean.TRUE);
          this.checkMod(0b10);
          return;
        case 0b01:
          action.accept(Boolean.FALSE);
          this.checkMod(0b01);
        default:
      }
    }
    @Override
    public OmniIterator.OfBoolean iterator(){
        return new CheckedItr(this);
    }
    @Override
    public boolean removeIf(BooleanPredicate filter)
    {
      switch(state)
      {
        case 0b01:
          try
          {
            if(filter.test(false)){break;}return false;
          }
          finally
          {
            this.checkMod(0b01);
          }
        case 0b10:
          try
          {
            if(filter.test(true)){break;}return false;
          }
          finally
          {
            this.checkMod(0b10);
          }
        case 0b11:
          if(filter.test(true))
          {
            if(filter.test(false))
            {
              this.checkMod(0b11);
              this.state=0b00;
              return true;
            }
            this.checkMod(0b11);
            this.state=0b01;
            return true;
          }
          try
          {
            if(!filter.test(false)){return false;}
          }
          finally
          {
            this.checkMod(0b11);
          }
          this.state=0b10;
          return true;
        default:
          return false;
      }
      this.state=0b00;
      return true;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      switch(state)
      {
        case 0b01:
          try
          {
            if(filter.test(Boolean.FALSE)){break;}return false;
          }
          finally
          {
            this.checkMod(0b01);
          }
        case 0b10:
          try
          {
            if(filter.test(Boolean.TRUE)){break;}return false;
          }
          finally
          {
            this.checkMod(0b10);
          }
        case 0b11:
          if(filter.test(Boolean.TRUE))
          {
            if(filter.test(Boolean.FALSE))
            {
              this.checkMod(0b11);
              this.state=0b00;
              return true;
            }
            this.checkMod(0b11);
            this.state=0b01;
            return true;
          }
          try
          {
            if(!filter.test(Boolean.FALSE)){return false;}
          }
          finally
          {
            this.checkMod(0b11);
          }
          this.state=0b10;
          return true;
        default:
          return false;
      }
      this.state=0b00;
      return true;
    }
    private static class CheckedItr extends AbstractBooleanItr
    {
      private transient final Checked root;
      private transient int itrState;
      private CheckedItr(Checked root){
          this.root=root;
          this.itrState=root.state;
      }
      @Override
      public boolean hasNext()
      {
        switch(this.itrState)
        {
          case 0b001:
          case 0b010:
          case 0b011:
          case 0b111:
            return true;
          default:
            return false;
        }
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        switch(this.itrState){
        case 0b001:
            action.accept(true);
            root.checkMod(0b01);
            this.itrState=0b100;
            return;
        case 0b010:
            action.accept(true);
            root.checkMod(0b10);
            this.itrState=0b101;
            return;
        case 0b011:
            action.accept(false);
        case 0b111:
            action.accept(true);
            root.checkMod(0b11);
            this.itrState=0b110;
        default:
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        switch(this.itrState){
        case 0b001:
            action.accept(Boolean.TRUE);
            root.checkMod(0b01);
            this.itrState=0b100;
            return;
        case 0b010:
            action.accept(Boolean.TRUE);
            root.checkMod(0b10);
            this.itrState=0b101;
            return;
        case 0b011:
            action.accept(Boolean.FALSE);
        case 0b111:
            action.accept(Boolean.TRUE);
            root.checkMod(0b11);
            this.itrState=0b110;
        default:
        }
      }
      @Override
      public boolean nextBoolean(){
          final var root=this.root;
          switch(itrState){
          case 0b001:
              root.checkMod(0b01);
              this.itrState=0b100;
              return false;
          case 0b010:
              root.checkMod(0b10);
              this.itrState=0b101;
              return true;
          case 0b011:
              root.checkMod(0b11);
              this.itrState=0b111;
              return false;
          case 0b111:
              root.checkMod(0b11);
              this.itrState=0b110;
              return true;
          default:
              throw new NoSuchElementException();
          }
      }
      @Override
      public void remove(){
        final var root=this.root;
        switch(this.itrState){
        case 0b100:
            root.checkMod(0b01);
            root.state=0b00;
            this.itrState=0b000;
            return;
        case 0b101:
            root.checkMod(0b10);
            root.state=0b00;
            this.itrState=0b000;
            return;
        case 0b110:
            root.checkMod(0b11);
            root.state=0b01;
            this.itrState=0b000;
            return;
        case 0b111:
            root.checkMod(0b11);
            root.state=0b10;
            this.itrState=0b010;
            return;
        default:
            throw new IllegalStateException();
        }
      }
    }
  }
  transient int state;
  BooleanSet(int state){
    super();
    this.state=state;
  }
  BooleanSet(){
    super();
  }
  @Override
  public void clear()
  {
    this.state=0;
  }
  @Override
  public Object clone(){
      return new BooleanSet(this.state);
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO implement equals method
    return false;
  }
  @Override
  public int hashCode()
  {
    switch(this.state)
    {
      case 0b01:
        return 1231;
      case 0b10:
        return 1237;
      case 0b11:
        return 1231+1237;
      default:
        return 0;
    }
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
        return "[]";
    }
  }
  @Override
  public boolean contains(boolean val)
  {
    switch(this.state)
    {
      case 0b01:
        return !val;
      case 0b10:
        return val;
      case 0b11:
        return true;
      default:
        return false;
    }
  }
  @Override
  public boolean contains(Boolean val)
  {
    return val!=null && contains((boolean)val);
  }
  @Override
  public boolean contains(byte val){
      return contains((int)val);
  }
  @Override
  public boolean contains(Byte val){
      return val!=null&&contains((int)val);
  }
  @Override
  public boolean contains(char val){
      return contains((int)val);
  }
  @Override
  public boolean contains(Character val){
      return val!=null&&contains((int)val);
  }
  @Override
  public boolean contains(double val){
      long bits;
      if((bits=Double.doubleToRawLongBits(val))==0L||bits==Long.MIN_VALUE){
          return (state&0b01)!=0;
      }
      return bits==TypeUtil.DBL_TRUE_BITS&&(state&0b10)!=0;
  }
  @Override
  public boolean contains(Double val){
      return val!=null&&contains((double)val);
  }
  @Override
  public boolean contains(float val){
      switch(Float.floatToRawIntBits(val)){
      case 0:
      case Integer.MIN_VALUE:
          return (state&0b01)!=0;
      case TypeUtil.FLT_TRUE_BITS:
          return (state&0b10)!=0;
      default:
          return false;
      }
  }
  @Override
  public boolean contains(Float val){
      return val!=null&&contains((float)val);
  }
  @Override
  public boolean contains(int val){
      switch(val){
      case 0:
          return (state&0b01)!=0;
      case 1:
          return (state&0b10)!=0;
      default:
          return false;
      }
  }
  @Override
  public boolean contains(Integer val){
      return val!=null&&contains((int)val);
  }
  @Override
  public boolean contains(long val){
      if(val==0){
          return (state&0b01)!=0;
      }
      return val==1&&(state&0b10)!=0;
  }
  @Override
  public boolean contains(Long val){
      return val!=null&&contains((long)val);
  }
  @Override
  public boolean contains(Object val)
  {
    switch(this.state)
    {
      case 0b01:
        return TypeUtil.refEqualsFalse(val);
      case 0b10:
        return TypeUtil.refEqualsTrue(val);
      case 0b11:
        return val instanceof Boolean;
      default:
        return false;
    }
  }
  @Override
  public boolean contains(short val){
      return contains((int)val);
  }
  @Override
  public boolean contains(Short val){
      return val!=null&&contains((int)val);
  }
  @Override
  public boolean isEmpty(){
      return state==0;
  }
  @Override
  public boolean remove(Object val)
  {
    switch(this.state)
    {
      case 0b01:
        if(TypeUtil.refEqualsFalse(val))
        {
          break;
        }
        return false;
      case 0b10:
        if(TypeUtil.refEqualsTrue(val))
        {
          break;
        }
        return false;
      case 0b11:
        if(val instanceof Boolean)
        {
          this.state=((boolean)val)?0b01:0b10;
          return true;
        }
      default:
        return false;
    }
    this.state=0b00;
    return true;
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
        return false;
      case 0b11:
        {
          this.state=((boolean)val)?0b01:0b10;
          return true;
        }
      default:
        return false;
    }
    this.state=0b00;
    return true;
  }
  @Override
  public boolean removeVal(Boolean val){
      return val!=null&&removeVal((boolean)val);
  }
  @Override
  public boolean removeVal(byte val){
      return removeVal((int)val);
  }
  @Override
  public boolean removeVal(Byte val){
      return val!=null&&removeVal((int)val);
  }
  @Override
  public boolean removeVal(char val){
      return removeVal((int)val);
  }
  @Override
  public boolean removeVal(Character val){
      return val!=null&&removeVal((int)val);
  }
  @Override
  public boolean removeVal(double val){
      long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
          int state;
          if(((state=this.state)&0b01)!=0){
              this.state=state&0b10;
              return true;
          }
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
          int state;
          if(((state=this.state)&0b10)!=0){
              this.state=state&0b01;
              return true;
          }
      }
      return false;
  }
  @Override
  public boolean removeVal(Double val){
      return val!=null&&removeVal((double)val);
  }
  @Override
  public boolean removeVal(float val){
      switch(Float.floatToRawIntBits(val)){
      case 0:
      case Integer.MIN_VALUE:
          int state;
          if(((state=this.state)&0b01)!=0){
              this.state=state&0b10;
              return true;
          }
          break;
      case TypeUtil.FLT_TRUE_BITS:
          if(((state=this.state)&0b10)!=0){
              this.state=state&0b10;
              return true;
          }
      default:
      }
      return false;
  }
  @Override
  public boolean removeVal(Float val){
      return val!=null&&removeVal((float)val);
  }
  @Override
  public boolean removeVal(int val){
      switch(val){
      case 0:
          int state;
          if(((state=this.state)&0b01)!=0){
              this.state=state&0b10;
              return true;
          }
          break;
      case 1:
          if(((state=this.state)&0b10)!=0){
              this.state=state&0b10;
              return true;
          }
      default:
      }
      return false;
  }
  @Override
  public boolean removeVal(Integer val){
      return val!=null&&removeVal((int)val);
  }
  @Override
  public boolean removeVal(long val){
      if(val==0){
          int state;
          if(((state=this.state)&0b01)!=0){
              this.state=state&0b10;
              return true;
          }
      }else if(val==1){
          int state;
          if(((state=this.state)&0b10)!=0){
              this.state=state&0b10;
              return true;
          }
      }
      return false;
  }
  @Override
  public boolean removeVal(Long val){
      return val!=null&&removeVal((long)val);
  }
  @Override
  public boolean removeVal(short val){
      return removeVal((int)val);
  }
  @Override
  public boolean removeVal(Short val){
      return val!=null&&removeVal((int)val);
  }
  @Override
  public int size(){
      switch(state){
      case 0b01:
      case 0b10:
          return 1;
      case 0b11:
          return 2;
      default:
          return 0;
      }
  }
  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    T[] dst;
    switch(state)
    {
      case 0b01:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        break;
      case 0b11:
        (dst=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
        dst[1]=(T)Boolean.TRUE;
        break;
      default:
        dst=arrConstructor.apply(0);
    }
    return dst;
  }
  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] toArray(T[] dst){
      switch(state){
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
          if(dst.length!=0){
              dst[0]=null;
          }
      }
      return dst;
  }
  @Override
  public boolean add(boolean val){
      switch(state){
      case 0b01:
          if(!val){
              return false;
          }
          break;
      case 0b10:
          if(val){
              return false;
          }
          break;
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
  public boolean add(Boolean val){
      return add((boolean)val);
  }
  @Override
  public void forEach(BooleanConsumer action)
  {
    switch(state)
    {
      case 0b11:
        action.accept(false);
        action.accept(true);
        return;
      case 0b10:
        action.accept(true);
        return;
      case 0b01:
        action.accept(false);
      default:
    }
  }
  @Override
  public void forEach(Consumer<? super Boolean> action)
  {
    switch(state)
    {
      case 0b11:
        action.accept(Boolean.FALSE);
        action.accept(Boolean.TRUE);
        return;
      case 0b10:
        action.accept(Boolean.TRUE);
        return;
      case 0b01:
        action.accept(Boolean.FALSE);
      default:
    }
  }
  @Override
  public OmniIterator.OfBoolean iterator(){
      return new Itr(this);
  }
  private static class Itr extends AbstractBooleanItr
  {
    private transient final BooleanSet root;
    private transient int itrState;
    private Itr(BooleanSet root)
    {
      this.root=root;
      this.itrState=root.state;
    }
    @Override
    public void remove(){
        root.state=itrState;
    }
    @Override
    public void forEachRemaining(BooleanConsumer action)
    {
      switch(this.itrState){
      case 0b11:
          action.accept(false);
      case 0b10:
          action.accept(true);
          break;
      case 0b01:
          action.accept(false);
          break;
      case 0b00:
          return;
      }
      this.itrState=0b00;
    }
    @Override
    public void forEachRemaining(Consumer<? super Boolean> action)
    {
      switch(this.itrState){
      case 0b11:
          action.accept(Boolean.FALSE);
      case 0b10:
          action.accept(Boolean.TRUE);
          break;
      case 0b01:
          action.accept(Boolean.FALSE);
          break;
      case 0b00:
          return;
      }
      this.itrState=0b00;
    }
    @Override
    public boolean nextBoolean(){
        switch(itrState){
        case 0b01:
            this.itrState=0b00;
            break;
        case 0b10:
            this.itrState=0b00;
            return true;
        default:
            this.itrState=0b10;
        }
        return false;
    }
    @Override
    public boolean hasNext(){
        return itrState!=0;
    }
  }
  @Override
  public boolean removeIf(BooleanPredicate filter)
  {
    switch(state)
    {
      case 0b01:
        if(filter.test(false)){break;}return false;
      case 0b10:
        if(filter.test(true)){break;}return false;
      case 0b11:
        if(filter.test(true))
        {
          if(filter.test(false))
          {
            this.state=0b00;
            return true;
          }
          this.state=0b01;
          return true;
        }
        if(!filter.test(false)){return false;}
        this.state=0b10;
        return true;
      default:
        return false;
    }
    this.state=0b00;
    return true;
  }
  @Override
  public boolean removeIf(Predicate<? super Boolean> filter)
  {
    switch(state)
    {
      case 0b01:
        if(filter.test(Boolean.FALSE)){break;}return false;
      case 0b10:
        if(filter.test(Boolean.TRUE)){break;}return false;
      case 0b11:
        if(filter.test(Boolean.TRUE))
        {
          if(filter.test(Boolean.FALSE))
          {
            this.state=0b00;
            return true;
          }
          this.state=0b01;
          return true;
        }
        if(!filter.test(Boolean.FALSE)){return false;}
        this.state=0b10;
        return true;
      default:
        return false;
    }
    this.state=0b00;
    return true;
  }
  @Override
  public Boolean[] toArray()
  {
    switch(state)
    {
      case 0b01:
        return new Boolean[]{Boolean.FALSE};
      case 0b10:
        return new Boolean[]{Boolean.TRUE};
      case 0b11:
        return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
      default:
        return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
    }
  }
  @Override
  public boolean[] toBooleanArray()
  {
    switch(state)
    {
      case 0b01:
        return new boolean[]{false};
      case 0b10:
        return new boolean[]{true};
      case 0b11:
        return new boolean[]{false,true};
      default:
        return OmniArray.OfBoolean.DEFAULT_ARR;
    }
  }
  @Override
  public byte[] toByteArray()
  {
    switch(state)
    {
      case 0b01:
        return new byte[]{0};
      case 0b10:
        return new byte[]{1};
      case 0b11:
        return new byte[]{0,1};
      default:
        return OmniArray.OfByte.DEFAULT_ARR;
    }
  }
  @Override
  public char[] toCharArray()
  {
    switch(state)
    {
      case 0b01:
        return new char[]{0};
      case 0b10:
        return new char[]{1};
      case 0b11:
        return new char[]{0,1};
      default:
        return OmniArray.OfChar.DEFAULT_ARR;
    }
  }
  @Override
  public short[] toShortArray()
  {
    switch(state)
    {
      case 0b01:
        return new short[]{0};
      case 0b10:
        return new short[]{1};
      case 0b11:
        return new short[]{0,1};
      default:
        return OmniArray.OfShort.DEFAULT_ARR;
    }
  }
  @Override
  public int[] toIntArray()
  {
    switch(state)
    {
      case 0b01:
        return new int[]{0};
      case 0b10:
        return new int[]{1};
      case 0b11:
        return new int[]{0,1};
      default:
        return OmniArray.OfInt.DEFAULT_ARR;
    }
  }
  @Override
  public long[] toLongArray()
  {
    switch(state)
    {
      case 0b01:
        return new long[]{0};
      case 0b10:
        return new long[]{1};
      case 0b11:
        return new long[]{0,1};
      default:
        return OmniArray.OfLong.DEFAULT_ARR;
    }
  }
  @Override
  public float[] toFloatArray()
  {
    switch(state)
    {
      case 0b01:
        return new float[]{0};
      case 0b10:
        return new float[]{1};
      case 0b11:
        return new float[]{0,1};
      default:
        return OmniArray.OfFloat.DEFAULT_ARR;
    }
  }
  @Override
  public double[] toDoubleArray()
  {
    switch(state)
    {
      case 0b01:
        return new double[]{0};
      case 0b10:
        return new double[]{1};
      case 0b11:
        return new double[]{0,1};
      default:
        return OmniArray.OfDouble.DEFAULT_ARR;
    }
  }
}
