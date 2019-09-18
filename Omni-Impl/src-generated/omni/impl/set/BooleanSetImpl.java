package omni.impl.set;
import omni.api.OmniNavigableSet;
import java.io.Serializable;
import java.util.Collection;
import omni.api.OmniCollection;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import java.util.function.Consumer;
import omni.function.BooleanPredicate;
import java.util.function.Predicate;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.function.IntFunction;
import omni.util.OmniArray;
import omni.api.OmniIterator;
import java.util.Set;
import omni.api.OmniSet;
import java.util.NoSuchElementException;
import omni.impl.AbstractBooleanItr;
import omni.api.OmniNavigableSet;
import java.util.ConcurrentModificationException;
public class BooleanSetImpl extends AbstractBooleanSet implements Serializable,Cloneable{
  //TODO reconsider the subset implementation
  transient int state;
  @Override public OmniNavigableSet.OfBoolean descendingSet(){
    return new Descending(this);
  }
  private static final long serialVersionUID=1L;
  public BooleanSetImpl(){
    super();
  }
  public BooleanSetImpl(BooleanSetImpl that){
    this.state=that.state;
  }
  public BooleanSetImpl(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public BooleanSetImpl(OmniCollection.OfRef<? extends Boolean> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public BooleanSetImpl(Collection<? extends Boolean> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  BooleanSetImpl(int state){
    super();
    this.state=state;
  }
  @Override public Object clone(){
    return new BooleanSetImpl(this.state);
  }
  @Override public BooleanComparator comparator(){
    return Boolean::compare;
  }
  @Override public String toString(){
    switch(this.state){
      case 0b00:
        return "[]";
      case 0b01:
        return "[false]";
      case 0b10:
        return "[true]";
      default:
        return "[false, true]";
    }
  }
  @Override public Boolean ceiling(boolean val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public Boolean floor(boolean val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public Boolean lower(boolean val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public Boolean higher(boolean val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public Boolean pollFirst(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public Boolean pollLast(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean booleanCeiling(boolean val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean booleanFloor(boolean val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean lowerBoolean(boolean val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean higherBoolean(boolean val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean pollFirstBoolean(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean pollLastBoolean(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public byte byteCeiling(byte val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public byte byteFloor(byte val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public byte lowerByte(byte val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public byte higherByte(byte val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public byte pollFirstByte(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public byte pollLastByte(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public char charCeiling(char val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public char charFloor(char val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public char lowerChar(char val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public char higherChar(char val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public char pollFirstChar(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public char pollLastChar(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public short shortCeiling(short val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public short shortFloor(short val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public short lowerShort(short val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public short higherShort(short val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public short pollFirstShort(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public short pollLastShort(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public int intCeiling(int val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public int intFloor(int val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public int lowerInt(int val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public int higherInt(int val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public int pollFirstInt(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public int pollLastInt(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public long longCeiling(long val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public long longFloor(long val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public long lowerLong(long val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public long higherLong(long val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public long pollFirstLong(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public long pollLastLong(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public float floatCeiling(float val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public float floatFloor(float val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public float lowerFloat(float val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public float higherFloat(float val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public float pollFirstFloat(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public float pollLastFloat(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public double doubleCeiling(double val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public double doubleFloor(double val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public double lowerDouble(double val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public double higherDouble(double val){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public double pollFirstDouble(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public double pollLastDouble(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public boolean equals(Object val){
  if(val==this){
      return true;
    }
    if(val instanceof Set){
      switch(this.state){
        case 0b00:
          return ((Set<?>)val).isEmpty();
        case 0b01:
          return equalsFalseState((Set<?>)val);
        case 0b10:
          return equalsTrueState((Set<?>)val);
        default:
          return equalsFullState((Set<?>)val);
      }
    }
    return false;
  }
  @Override public int size(){
    switch(this.state){
      case 0b00:
        return 0;
      case 0b01:
      case 0b10:
        return 1;
      default:
        return 2;
    }
  }
  @Override public void clear(){
    this.state=0b00;
  }
  @Override public int hashCode(){
    switch(this.state){
      case 0b00:
        return 0;
      case 0b01:
        return 1237;
      case 0b10:
        return 1231;
      default:
        return 1231+1237;
    }
  }
  @Override public boolean isEmpty(){
    return this.state==0b00;
  }
  @Override public void forEach(BooleanConsumer action){
    switch(this.state){
      case 0b11:
        action.accept(false);
      case 0b10:
        action.accept(true);
        break;
      case 0b01:
        action.accept(false);
      default:
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    switch(this.state){
      case 0b11:
        action.accept(Boolean.FALSE);
      case 0b10:
        action.accept(Boolean.TRUE);
        break;
      case 0b01:
        action.accept(Boolean.FALSE);
      default:
    }
  }
  @Override public boolean removeIf(BooleanPredicate filter){
    switch(this.state){
      case 0b11:
        if(filter.test(false)){
          if(filter.test(true)){
            break;
          }else{
            this.state=0b10;
            return true;
          }
        }else if(filter.test(true)){
          this.state=0b01;
          return true;
        }
        return false;
      case 0b10:
        if(filter.test(true)){
          break;
        }
        return false;
      case 0b01:
        if(filter.test(false)){
          break;
        }
      default:
        return false;
    }
    this.state=0b00;
    return true;
  }
  @Override public boolean removeIf(Predicate<? super Boolean> filter){
    switch(this.state){
      case 0b11:
        if(filter.test(Boolean.FALSE)){
          if(filter.test(Boolean.TRUE)){
            break;
          }else{
            this.state=0b10;
            return true;
          }
        }else if(filter.test(Boolean.TRUE)){
          this.state=0b01;
          return true;
        }
        return false;
      case 0b10:
        if(filter.test(Boolean.TRUE)){
          break;
        }
        return false;
      case 0b01:
        if(filter.test(Boolean.FALSE)){
          break;
        }
      default:
        return false;
    }
    this.state=0b00;
    return true;
  }
  @Override boolean addTrue(){
    final int state;
    if(((state=this.state)&0b10)==0){
      this.state=state+0b10;
      return true;
    }
    return false;
  }
  @Override boolean containsTrue(){
    return (this.state&0b10)!=0;
  }
  @Override boolean removeTrue(){
    final int state;
    if(((state=this.state)&0b10)!=0){
      this.state=state-0b10;
      return true;
    }
    return false;
  }
  @Override boolean addFalse(){
    final int state;
    if(((state=this.state)&0b01)==0){
      this.state=state+0b01;
      return true;
    }
    return false;
  }
  @Override boolean containsFalse(){
    return (this.state&0b01)!=0;
  }
  @Override boolean removeFalse(){
    final int state;
    if(((state=this.state)&0b01)!=0){
      this.state=state-0b01;
      return true;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public <T> T[] toArray(T[] dst){
    switch(this.state){
    case 0b11:
      (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.FALSE;
      dst[1]=(T)Boolean.TRUE;
      break;
    case 0b10:
      (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
      break;
    case 0b01:
      (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
      break;
    default:
      if(dst.length!=0){
        dst[0]=null;
      }
    }
    return dst;
  }
  @SuppressWarnings("unchecked")
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final T[] dst;
    switch(this.state){
    case 0b11:
      (dst=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
      dst[1]=(T)Boolean.TRUE;
      break;
    case 0b10:
      (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
      break;
    case 0b01:
      (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
      break;
    default:
      return arrConstructor.apply(0);
    }
    return dst;
  }
  @Override public Boolean[] toArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
      case 0b01:
        return new Boolean[]{Boolean.FALSE};
      case 0b10:
        return new Boolean[]{Boolean.TRUE};
      default:
        return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
    }
  }
  @Override public boolean[] toBooleanArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfBoolean.DEFAULT_ARR;
      case 0b01:
        return new boolean[]{false};
      case 0b10:
        return new boolean[]{true};
      default:
        return new boolean[]{false,true};
    }
  }
  @Override public byte[] toByteArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfByte.DEFAULT_ARR;
      case 0b01:
        return new byte[]{(byte)0};
      case 0b10:
        return new byte[]{(byte)1};
      default:
        return new byte[]{(byte)0,(byte)1};
    }
  }
  @Override public char[] toCharArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfChar.DEFAULT_ARR;
      case 0b01:
        return new char[]{(char)0};
      case 0b10:
        return new char[]{(char)1};
      default:
        return new char[]{(char)0,(char)1};
    }
  }
  @Override public short[] toShortArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfShort.DEFAULT_ARR;
      case 0b01:
        return new short[]{(short)0};
      case 0b10:
        return new short[]{(short)1};
      default:
        return new short[]{(short)0,(short)1};
    }
  }
  @Override public int[] toIntArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfInt.DEFAULT_ARR;
      case 0b01:
        return new int[]{0};
      case 0b10:
        return new int[]{1};
      default:
        return new int[]{0,1};
    }
  }
  @Override public long[] toLongArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfLong.DEFAULT_ARR;
      case 0b01:
        return new long[]{0L};
      case 0b10:
        return new long[]{1L};
      default:
        return new long[]{0L,1L};
    }
  }
  @Override public float[] toFloatArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfFloat.DEFAULT_ARR;
      case 0b01:
        return new float[]{0F};
      case 0b10:
        return new float[]{1F};
      default:
        return new float[]{0F,1F};
    }
  }
  @Override public double[] toDoubleArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfDouble.DEFAULT_ARR;
      case 0b01:
        return new double[]{0D};
      case 0b10:
        return new double[]{1D};
      default:
        return new double[]{0D,1D};
    }
  }
  @Override public OmniIterator.OfBoolean iterator(){
    final int state;
    if((state=this.state)==0b00){
      return EMPTY_ITR;
    }
    return new UncheckedAscendingFullItr(this,state);
  }
  @Override public OmniIterator.OfBoolean descendingIterator(){
    final int state;
    if((state=this.state)==0b00){
      return EMPTY_ITR;
    }
    return new UncheckedDescendingFullItr(this,state);
  }
  @Override public boolean firstBoolean(){
    if(this.state==0b10){
      return true;
    }
    return false;
  }
  @Override public boolean lastBoolean(){
    if(this.state==0b01){
      return false;
    }
    return true;
  }
  @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
    if(fromElement){
      if(toElement && fromInclusive && toInclusive){
        return new UncheckedTrueView(this);
      }
    }else{
      if(toElement){
        if(fromInclusive){
          if(toInclusive){
            return this;
          }else{
            return new UncheckedFalseView(this);
          }
        }else{
          if(toInclusive){
            return new UncheckedTrueView(this);
          }
        }
      }else{
        if(fromInclusive && toInclusive){
          return new UncheckedFalseView(this);
        }
      }
    }
    return EmptyView.UncheckedAscending;
  }
  public static class Checked extends BooleanSetImpl{
    private static final long serialVersionUID=1L;
    public Checked(){
      super();
    }
    public Checked(BooleanSetImpl that){
      super(that);
    }
    public Checked(OmniCollection.OfBoolean that){
      super(that);
    }
    public Checked(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public Checked(Collection<? extends Boolean> that){
      super(that);
    }
    Checked(int state){
      super(state);
    }
    @Override public Object clone(){
      return new Checked(this.state);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new Descending.Checked(this);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final int state;
      if((state=this.state)==0b00){
        return EMPTY_ITR;
      }
      return new CheckedAscendingFullItr(this,state);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final int state;
      if((state=this.state)==0b00){
        return EMPTY_ITR;
      }
      return new CheckedDescendingFullItr(this,state);
    }
    @Override public boolean firstBoolean(){
      switch(this.state){
        case 0b00:
          throw new NoSuchElementException();
        case 0b10:
          return true;
        default:
          return false;
      }
    }
    @Override public boolean lastBoolean(){
      switch(this.state){
        case 0b00:
          throw new NoSuchElementException();
        case 0b01:
          return false;
        default:
          return true;
      }
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      if(fromElement){
        if(toElement){
          if(fromInclusive){
            if(toInclusive){
              return new UncheckedTrueView.Checked(this);
            }else{
              return EmptyView.CheckedAscendingMiddle;
            }
          }else{
            if(toInclusive){
              return EmptyView.CheckedAscendingTail;
            }
          }
        }else{
          if(fromInclusive && toInclusive){
            return EmptyView.CheckedAscendingMiddle;
          }
        }
      }else{
        if(toElement){
          if(fromInclusive){
            if(toInclusive){
              return this;
            }else{
              return new UncheckedFalseView.Checked(this);
            }
          }else{
            if(toInclusive){
              return new UncheckedTrueView.Checked(this);
            }else{
              return EmptyView.CheckedAscendingMiddle;
            }
          }
        }else{
          if(fromInclusive){
            if(toInclusive){
              return new UncheckedFalseView.Checked(this);
            }else{
              return EmptyView.CheckedAscendingHead;
            }
          }else{
            if(toInclusive){
              return EmptyView.CheckedAscendingMiddle;
            }
          }
        }
      }
      throw new IllegalArgumentException("out of bounds");
    }
  }
  public static class Descending extends BooleanSetImpl{
    private static final long serialVersionUID=1L;
    public Descending(){
      super();
    }
    public Descending(BooleanSetImpl that){
      super(that);
    }
    public Descending(OmniCollection.OfBoolean that){
      super(that);
    }
    public Descending(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public Descending(Collection<? extends Boolean> that){
      super(that);
    }
    Descending(int state){
      super(state);
    }
    @Override public Object clone(){
      return new Descending(this.state);
    }
    @Override public BooleanComparator comparator(){
      return BooleanComparator::descendingCompare;
    }
    @Override public String toString(){
      switch(this.state){
        case 0b00:
          return "[]";
        case 0b01:
          return "[false]";
        case 0b10:
          return "[true]";
        default:
          return "[true, false]";
      }
    }
    @Override public void forEach(BooleanConsumer action){
      switch(this.state){
        case 0b11:
          action.accept(true);
        case 0b01:
          action.accept(false);
          break;
        case 0b10:
          action.accept(true);
        default:
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      switch(this.state){
        case 0b11:
          action.accept(Boolean.TRUE);
        case 0b01:
          action.accept(Boolean.FALSE);
          break;
        case 0b10:
          action.accept(Boolean.TRUE);
        default:
      }
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      switch(this.state){
      case 0b11:
        (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.TRUE;
        dst[1]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
        break;
      default:
        if(dst.length!=0){
          dst[0]=null;
        }
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final T[] dst;
      switch(this.state){
      case 0b11:
        (dst=arrConstructor.apply(2))[0]=(T)Boolean.TRUE;
        dst[1]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        break;
      default:
        return arrConstructor.apply(0);
      }
      return dst;
    }
    @Override public Boolean[] toArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        case 0b01:
          return new Boolean[]{Boolean.FALSE};
        case 0b10:
          return new Boolean[]{Boolean.TRUE};
        default:
          return new Boolean[]{Boolean.TRUE,Boolean.FALSE};
      }
    }
    @Override public boolean[] toBooleanArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_ARR;
        case 0b01:
          return new boolean[]{false};
        case 0b10:
          return new boolean[]{true};
        default:
          return new boolean[]{true,false};
      }
    }
    @Override public byte[] toByteArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfByte.DEFAULT_ARR;
        case 0b01:
          return new byte[]{(byte)0};
        case 0b10:
          return new byte[]{(byte)1};
        default:
          return new byte[]{(byte)1,(byte)0};
      }
    }
    @Override public char[] toCharArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfChar.DEFAULT_ARR;
        case 0b01:
          return new char[]{(char)0};
        case 0b10:
          return new char[]{(char)1};
        default:
          return new char[]{(char)1,(char)0};
      }
    }
    @Override public short[] toShortArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfShort.DEFAULT_ARR;
        case 0b01:
          return new short[]{(short)0};
        case 0b10:
          return new short[]{(short)1};
        default:
          return new short[]{(short)1,(short)0};
      }
    }
    @Override public int[] toIntArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfInt.DEFAULT_ARR;
        case 0b01:
          return new int[]{0};
        case 0b10:
          return new int[]{1};
        default:
          return new int[]{1,0};
      }
    }
    @Override public long[] toLongArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfLong.DEFAULT_ARR;
        case 0b01:
          return new long[]{0L};
        case 0b10:
          return new long[]{1L};
        default:
          return new long[]{1L,0L};
      }
    }
    @Override public float[] toFloatArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfFloat.DEFAULT_ARR;
        case 0b01:
          return new float[]{0F};
        case 0b10:
          return new float[]{1F};
        default:
          return new float[]{1F,0F};
      }
    }
    @Override public double[] toDoubleArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfDouble.DEFAULT_ARR;
        case 0b01:
          return new double[]{0D};
        case 0b10:
          return new double[]{1D};
        default:
          return new double[]{1D,0D};
      }
    }
    @Override public Boolean ceiling(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean floor(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean lower(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean higher(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollFirst(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollLast(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean booleanCeiling(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean booleanFloor(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lowerBoolean(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean higherBoolean(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollLastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte byteCeiling(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte byteFloor(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte lowerByte(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte higherByte(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte pollFirstByte(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte pollLastByte(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char charCeiling(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char charFloor(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char lowerChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char higherChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char pollFirstChar(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char pollLastChar(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short shortCeiling(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short shortFloor(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short lowerShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short higherShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short pollFirstShort(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short pollLastShort(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intCeiling(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intFloor(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int lowerInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int higherInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int pollFirstInt(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int pollLastInt(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longCeiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longFloor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long lowerLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long higherLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long pollFirstLong(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long pollLastLong(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatCeiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatFloor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float lowerFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float higherFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float pollFirstFloat(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float pollLastFloat(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleCeiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleFloor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double lowerDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double higherDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double pollFirstDouble(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double pollLastDouble(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final int state;
      if((state=this.state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedDescendingFullItr(this,state);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final int state;
      if((state=this.state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedAscendingFullItr(this,state);
    }
    @Override public boolean firstBoolean(){
      if(this.state==0b01){
        return false;
      }
      return true;
    }
    @Override public boolean lastBoolean(){
      if(this.state==0b10){
        return true;
      }
      return false;
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new BooleanSetImpl(this);
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      if(fromElement){
        if(toElement){
          if(fromInclusive && toInclusive){
            return new UncheckedTrueView.Descending(this);
          }
        }else{
          if(fromInclusive){
            if(toInclusive){
              return this;
            }else{
              return new UncheckedTrueView.Descending(this);
            }
          }else{
            if(toInclusive){
              return new UncheckedFalseView.Descending(this);
            }
          }
        }
      }else{
         if(!toElement && fromInclusive && toInclusive){
           return new UncheckedFalseView.Descending(this);
         }
      }
      return EmptyView.UncheckedDescending;
    }
    public static class Checked extends Descending{
      private static final long serialVersionUID=1L;
      public Checked(){
        super();
      }
      public Checked(BooleanSetImpl that){
        super(that);
      }
      public Checked(OmniCollection.OfBoolean that){
        super(that);
      }
      public Checked(OmniCollection.OfRef<? extends Boolean> that){
        super(that);
      }
      public Checked(Collection<? extends Boolean> that){
        super(that);
      }
      Checked(int state){
        super(state);
      }
      @Override public Object clone(){
        return new Checked(this.state);
      }
      @Override public OmniIterator.OfBoolean iterator(){
        final int state;
        if((state=this.state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedDescendingFullItr(this,state);
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return new BooleanSetImpl.Checked(this);
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        final int state;
        if((state=this.state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedAscendingFullItr(this,state);
      }
      @Override public boolean firstBoolean(){
        switch(this.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b01:
            return false;
          default:
            return true;
        }
      }
      @Override public boolean lastBoolean(){
        switch(this.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b10:
            return true;
          default:
            return false;
        }
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        if(fromElement){
          if(toElement){
            if(fromInclusive){
              if(toInclusive){
                return new UncheckedTrueView.Checked.Descending(this);
              }else{
                return EmptyView.CheckedDescendingHead;
              }
            }else{
              if(toInclusive){
                return EmptyView.CheckedDescendingMiddle;
              }
            }
          }else{
            if(fromInclusive){
              if(toInclusive){
                return this;
              }else{
                return new UncheckedTrueView.Checked.Descending(this);
              }
            }else{
              if(toInclusive){
                return new UncheckedFalseView.Checked.Descending(this);
              }else{
                return EmptyView.CheckedDescendingMiddle;
              }
            }
          }
        }else{
          if(toElement){
            if(fromInclusive && toInclusive){
              return EmptyView.CheckedDescendingMiddle;
            }
          }else{
            if(fromInclusive){
              if(toInclusive){
                return new UncheckedFalseView.Checked.Descending(this);
              }else{
                return EmptyView.CheckedDescendingMiddle;
              }
            }else{
              if(toInclusive){
                return EmptyView.CheckedDescendingTail;
              }
            }
          }
        }
        throw new IllegalArgumentException("out of bounds");  
      }
    }
  }
  private void writeObject(ObjectOutputStream oos) throws IOException{
    oos.writeByte(this.state);
  }
  private void readObject(ObjectInputStream ois) throws IOException{
    this.state=ois.readUnsignedByte();
  }
  private static boolean equalsFullState(Set<?> val){
    //TODO optimize
    if(val.size()==2){
      if(val instanceof OmniSet){
        if(val instanceof AbstractBooleanSet){
          return true;
        }else if(val instanceof OmniSet.OfRef){
          final OmniSet.OfRef<?> that;
          return (that=(OmniSet.OfRef<?>)val).contains(false) && that.contains(true);
        }
      }else{
        return val.contains(Boolean.FALSE) && val.contains(Boolean.TRUE);
      }
    }
    return false;
  }
  private static boolean equalsTrueState(Set<?> val){
    //TODO optimize
    if(val.size()==1){
      if(val instanceof OmniSet){
        if(val instanceof AbstractBooleanSet){
          return ((AbstractBooleanSet)val).containsTrue();
        }else if(val instanceof OmniSet.OfRef){
          return ((OmniSet.OfRef<?>)val).contains(true);
        }
      }else{
        return val.contains(Boolean.TRUE);
      }
    }
    return false;
  }
  private static boolean equalsFalseState(Set<?> val){
    //TODO optimize
    if(val.size()==1){
      if(val instanceof OmniSet){
        if(val instanceof AbstractBooleanSet){
          return ((AbstractBooleanSet)val).containsFalse();
        }else if(val instanceof OmniSet.OfRef){
          return ((OmniSet.OfRef<?>)val).contains(false);
        }
      }else{
        return val.contains(Boolean.FALSE);
      }
    }
    return false;
  }
  private static abstract class AbstractFullView extends AbstractBooleanSet implements Serializable,Cloneable{
    private static final long serialVersionUID=1L;
    transient final BooleanSetImpl root;
    private AbstractFullView(BooleanSetImpl root){
      this.root=root;
    }
    @Override boolean addTrue(){
      final int state;
      final BooleanSetImpl root;
      if(((state=(root=this.root).state)&0b10)==0){
        root.state=state+0b10;
        return true;
      }
      return false;
    }
    @Override boolean containsTrue(){
      return (root.state&0b10)!=0;
    }
    @Override boolean removeTrue(){
      final int state;
      final BooleanSetImpl root;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return true;
      }
      return false;
    }
    @Override boolean addFalse(){
      final int state;
      final BooleanSetImpl root;
      if(((state=(root=this.root).state)&0b01)==0){
        root.state=state+0b01;
        return true;
      }
      return false;
    }
    @Override boolean containsFalse(){
      return (root.state&0b01)!=0;
    }
    @Override boolean removeFalse(){
      final int state;
      final BooleanSetImpl root;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final BooleanSetImpl root;
      switch((root=this.root).state){
        case 0b11:
          if(filter.test(false)){
            if(filter.test(true)){
              break;
            }else{
              root.state=0b10;
              return true;
            }
          }else if(filter.test(true)){
            root.state=0b01;
            return true;
          }
          return false;
        case 0b10:
          if(filter.test(true)){
            break;
          }
          return false;
        case 0b01:
          if(filter.test(false)){
            break;
          }
        default:
          return false;
      }
      root.state=0b00;
      return true;
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final BooleanSetImpl root;
      switch((root=this.root).state){
        case 0b11:
          if(filter.test(Boolean.FALSE)){
            if(filter.test(Boolean.TRUE)){
              break;
            }else{
              root.state=0b10;
              return true;
            }
          }else if(filter.test(Boolean.TRUE)){
            root.state=0b01;
            return true;
          }
          return false;
        case 0b10:
          if(filter.test(Boolean.TRUE)){
            break;
          }
          return false;
        case 0b01:
          if(filter.test(Boolean.FALSE)){
            break;
          }
        default:
          return false;
      }
      root.state=0b00;
      return true;
    }
    @Override public int size(){
      switch(root.state){
        case 0b00:
          return 0;
        case 0b01:
        case 0b10:
          return 1;
        default:
          return 2;
      }
    }
    @Override public void clear(){
      root.state=0b00;
    }
    @Override public int hashCode(){
      switch(root.state){
        case 0b00:
          return 0;
        case 0b01:
          return 1237;
        case 0b10:
          return 1231;
        default:
          return 1231+1237;
      }
    }
    @Override public boolean isEmpty(){
      return root.state==0b00;
    }
    @Override public boolean equals(Object val){
    if(val==this||val==root){
        return true;
      }
      if(val instanceof Set){
        switch(root.state){
          case 0b00:
            return ((Set<?>)val).isEmpty();
          case 0b01:
            return equalsFalseState((Set<?>)val);
          case 0b10:
            return equalsTrueState((Set<?>)val);
          default:
            return equalsFullState((Set<?>)val);
        }
      }
      return false;
    }
  }
  private static class DescendingView extends AbstractFullView{
    private static final long serialVersionUID=1L;
    private DescendingView(BooleanSetImpl root){
      super(root);
    }
    @Override public Object clone(){
      return new Descending(root.state);
    }
    private Object writeReplace(){
      return new Descending(root.state);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return root;
    }
    @Override public BooleanComparator comparator(){
      return BooleanComparator::descendingCompare;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      switch(root.state){
      case 0b11:
        (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.TRUE;
        dst[1]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
        break;
      default:
        if(dst.length!=0){
          dst[0]=null;
        }
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final T[] dst;
      switch(root.state){
      case 0b11:
        (dst=arrConstructor.apply(2))[0]=(T)Boolean.TRUE;
        dst[1]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        break;
      default:
        return arrConstructor.apply(0);
      }
      return dst;
    }
    @Override public Boolean[] toArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        case 0b01:
          return new Boolean[]{Boolean.FALSE};
        case 0b10:
          return new Boolean[]{Boolean.TRUE};
        default:
          return new Boolean[]{Boolean.TRUE,Boolean.FALSE};
      }
    }
    @Override public boolean[] toBooleanArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_ARR;
        case 0b01:
          return new boolean[]{false};
        case 0b10:
          return new boolean[]{true};
        default:
          return new boolean[]{true,false};
      }
    }
    @Override public byte[] toByteArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfByte.DEFAULT_ARR;
        case 0b01:
          return new byte[]{(byte)0};
        case 0b10:
          return new byte[]{(byte)1};
        default:
          return new byte[]{(byte)1,(byte)0};
      }
    }
    @Override public char[] toCharArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfChar.DEFAULT_ARR;
        case 0b01:
          return new char[]{(char)0};
        case 0b10:
          return new char[]{(char)1};
        default:
          return new char[]{(char)1,(char)0};
      }
    }
    @Override public short[] toShortArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfShort.DEFAULT_ARR;
        case 0b01:
          return new short[]{(short)0};
        case 0b10:
          return new short[]{(short)1};
        default:
          return new short[]{(short)1,(short)0};
      }
    }
    @Override public int[] toIntArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfInt.DEFAULT_ARR;
        case 0b01:
          return new int[]{0};
        case 0b10:
          return new int[]{1};
        default:
          return new int[]{1,0};
      }
    }
    @Override public long[] toLongArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfLong.DEFAULT_ARR;
        case 0b01:
          return new long[]{0L};
        case 0b10:
          return new long[]{1L};
        default:
          return new long[]{1L,0L};
      }
    }
    @Override public float[] toFloatArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfFloat.DEFAULT_ARR;
        case 0b01:
          return new float[]{0F};
        case 0b10:
          return new float[]{1F};
        default:
          return new float[]{1F,0F};
      }
    }
    @Override public double[] toDoubleArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfDouble.DEFAULT_ARR;
        case 0b01:
          return new double[]{0D};
        case 0b10:
          return new double[]{1D};
        default:
          return new double[]{1D,0D};
      }
    }
    @Override public void forEach(BooleanConsumer action){
      switch(root.state){
        case 0b11:
          action.accept(true);
        case 0b01:
          action.accept(false);
          break;
        case 0b10:
          action.accept(true);
        default:
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      switch(root.state){
        case 0b11:
          action.accept(Boolean.TRUE);
        case 0b01:
          action.accept(Boolean.FALSE);
          break;
        case 0b10:
          action.accept(Boolean.TRUE);
        default:
      }
    }
    @Override public boolean firstBoolean(){
      if(root.state==0b01){
        return false;
      }
      return true;
    }
    @Override public boolean lastBoolean(){
      if(root.state==0b10){
        return true;
      }
      return false;
    }
    @Override public String toString(){
      switch(root.state){
        case 0b00:
          return "[]";
        case 0b01:
          return "[false]";
        case 0b10:
          return "[true]";
        default:
          return "[true, false]";
      }
    }
    @Override public Boolean ceiling(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean floor(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean lower(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean higher(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollFirst(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollLast(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean booleanCeiling(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean booleanFloor(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lowerBoolean(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean higherBoolean(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollLastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte byteCeiling(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte byteFloor(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte lowerByte(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte higherByte(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte pollFirstByte(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte pollLastByte(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char charCeiling(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char charFloor(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char lowerChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char higherChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char pollFirstChar(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char pollLastChar(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short shortCeiling(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short shortFloor(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short lowerShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short higherShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short pollFirstShort(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short pollLastShort(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intCeiling(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intFloor(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int lowerInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int higherInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int pollFirstInt(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int pollLastInt(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longCeiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longFloor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long lowerLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long higherLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long pollFirstLong(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long pollLastLong(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatCeiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatFloor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float lowerFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float higherFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float pollFirstFloat(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float pollLastFloat(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleCeiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleFloor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double lowerDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double higherDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double pollFirstDouble(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double pollLastDouble(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final BooleanSetImpl root;
      final int rootState;
      if((rootState=(root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedDescendingFullItr(root,rootState);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final BooleanSetImpl root;
      final int rootState;
      if((rootState=(root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedAscendingFullItr(root,rootState);
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      if(fromElement){
        if(toElement){
          if(fromInclusive && toInclusive){
            return new UncheckedTrueView.Descending(root);
          }
        }else{
          if(fromInclusive){
            if(toInclusive){
              return this;
            }else{
              return new UncheckedTrueView.Descending(root);
            }
          }else{
            if(toInclusive){
              return new UncheckedFalseView.Descending(root);
            }
          }
        }
      }else{
         if(!toElement && fromInclusive && toInclusive){
           return new UncheckedFalseView.Descending(root);
         }
      }
      return EmptyView.UncheckedDescending;
    }
    private static class Checked extends DescendingView{
      private static final long serialVersionUID=1L;
      private Checked(BooleanSetImpl root){
        super(root);
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return root;
      }
      @Override public Object clone(){
        return new Descending.Checked(root.state);
      }
      private Object writeReplace(){
        return new Descending.Checked(root.state);
      }
      @Override public boolean firstBoolean(){
        switch(root.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b01:
            return false;
          default:
            return true;
        }
      }
      @Override public boolean lastBoolean(){
        switch(root.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b10:
            return true;
          default:
            return false;
        }
      }
      @Override public OmniIterator.OfBoolean iterator(){
        final BooleanSetImpl root;
        final int rootState;
        if((rootState=(root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedDescendingFullItr(root,rootState);
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        final BooleanSetImpl root;
        final int rootState;
        if((rootState=(root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedAscendingFullItr(root,rootState);
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        if(fromElement){
          if(toElement){
            if(fromInclusive){
              if(toInclusive){
                return new UncheckedTrueView.Checked.Descending(root);
              }else{
                return EmptyView.CheckedDescendingHead;
              }
            }else{
              if(toInclusive){
                return EmptyView.CheckedDescendingMiddle;
              }
            }
          }else{
            if(fromInclusive){
              if(toInclusive){
                return this;
              }else{
                return new UncheckedTrueView.Checked.Descending(root);
              }
            }else{
              if(toInclusive){
                return new UncheckedFalseView.Checked.Descending(root);
              }else{
                return EmptyView.CheckedDescendingMiddle;
              }
            }
          }
        }else{
          if(toElement){
            if(fromInclusive && toInclusive){
              return EmptyView.CheckedDescendingMiddle;
            }
          }else{
            if(fromInclusive){
              if(toInclusive){
                return new UncheckedFalseView.Checked.Descending(root);
              }else{
                return EmptyView.CheckedDescendingMiddle;
              }
            }else{
              if(toInclusive){
                return EmptyView.CheckedDescendingTail;
              }
            }
          }
        }
        throw new IllegalArgumentException("out of bounds");  
      }
    }
  }
  private static class AscendingView extends AbstractFullView{
    private static final long serialVersionUID=1L;
    private AscendingView(BooleanSetImpl root){
      super(root);
    }
    @Override public Object clone(){
      return new BooleanSetImpl(root.state);
    }
    private Object writeReplace(){
      return new BooleanSetImpl(root.state);
    }
    @Override public BooleanComparator comparator(){
      return Boolean::compare;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      switch(root.state){
      case 0b11:
        (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.FALSE;
        dst[1]=(T)Boolean.TRUE;
        break;
      case 0b10:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
        break;
      default:
        if(dst.length!=0){
          dst[0]=null;
        }
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final T[] dst;
      switch(root.state){
      case 0b11:
        (dst=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
        dst[1]=(T)Boolean.TRUE;
        break;
      case 0b10:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        break;
      default:
        return arrConstructor.apply(0);
      }
      return dst;
    }
    @Override public Boolean[] toArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        case 0b01:
          return new Boolean[]{Boolean.FALSE};
        case 0b10:
          return new Boolean[]{Boolean.TRUE};
        default:
          return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
      }
    }
    @Override public boolean[] toBooleanArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_ARR;
        case 0b01:
          return new boolean[]{false};
        case 0b10:
          return new boolean[]{true};
        default:
          return new boolean[]{false,true};
      }
    }
    @Override public byte[] toByteArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfByte.DEFAULT_ARR;
        case 0b01:
          return new byte[]{(byte)0};
        case 0b10:
          return new byte[]{(byte)1};
        default:
          return new byte[]{(byte)0,(byte)1};
      }
    }
    @Override public char[] toCharArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfChar.DEFAULT_ARR;
        case 0b01:
          return new char[]{(char)0};
        case 0b10:
          return new char[]{(char)1};
        default:
          return new char[]{(char)0,(char)1};
      }
    }
    @Override public short[] toShortArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfShort.DEFAULT_ARR;
        case 0b01:
          return new short[]{(short)0};
        case 0b10:
          return new short[]{(short)1};
        default:
          return new short[]{(short)0,(short)1};
      }
    }
    @Override public int[] toIntArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfInt.DEFAULT_ARR;
        case 0b01:
          return new int[]{0};
        case 0b10:
          return new int[]{1};
        default:
          return new int[]{0,1};
      }
    }
    @Override public long[] toLongArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfLong.DEFAULT_ARR;
        case 0b01:
          return new long[]{0L};
        case 0b10:
          return new long[]{1L};
        default:
          return new long[]{0L,1L};
      }
    }
    @Override public float[] toFloatArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfFloat.DEFAULT_ARR;
        case 0b01:
          return new float[]{0F};
        case 0b10:
          return new float[]{1F};
        default:
          return new float[]{0F,1F};
      }
    }
    @Override public double[] toDoubleArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfDouble.DEFAULT_ARR;
        case 0b01:
          return new double[]{0D};
        case 0b10:
          return new double[]{1D};
        default:
          return new double[]{0D,1D};
      }
    }
    @Override public void forEach(BooleanConsumer action){
      switch(root.state){
        case 0b11:
          action.accept(false);
        case 0b10:
          action.accept(true);
          break;
        case 0b01:
          action.accept(false);
        default:
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      switch(root.state){
        case 0b11:
          action.accept(Boolean.FALSE);
        case 0b10:
          action.accept(Boolean.TRUE);
          break;
        case 0b01:
          action.accept(Boolean.FALSE);
        default:
      }
    }
    @Override public boolean firstBoolean(){
      if(root.state==0b10){
        return true;
      }
      return false;
    }
    @Override public boolean lastBoolean(){
      if(root.state==0b01){
        return false;
      }
      return true;
    }
    @Override public Boolean ceiling(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean floor(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean lower(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean higher(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollFirst(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollLast(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean booleanCeiling(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean booleanFloor(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lowerBoolean(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean higherBoolean(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollLastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte byteCeiling(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte byteFloor(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte lowerByte(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte higherByte(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte pollFirstByte(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte pollLastByte(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char charCeiling(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char charFloor(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char lowerChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char higherChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char pollFirstChar(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char pollLastChar(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short shortCeiling(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short shortFloor(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short lowerShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short higherShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short pollFirstShort(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short pollLastShort(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intCeiling(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intFloor(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int lowerInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int higherInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int pollFirstInt(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int pollLastInt(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longCeiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longFloor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long lowerLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long higherLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long pollFirstLong(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long pollLastLong(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatCeiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatFloor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float lowerFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float higherFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float pollFirstFloat(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float pollLastFloat(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleCeiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleFloor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double lowerDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double higherDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double pollFirstDouble(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double pollLastDouble(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public String toString(){
      switch(root.state){
        case 0b00:
          return "[]";
        case 0b01:
          return "[false]";
        case 0b10:
          return "[true]";
        default:
          return "[false, true]";
      }
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final BooleanSetImpl root;
      final int rootState;
      if((rootState=(root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedAscendingFullItr(root,rootState);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final BooleanSetImpl root;
      final int rootState;
      if((rootState=(root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedDescendingFullItr(root,rootState);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return root;
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      if(fromElement){
        if(toElement && fromInclusive && toInclusive){
          return new UncheckedTrueView(root);
        }
      }else{
        if(toElement){
          if(fromInclusive){
            if(toInclusive){
              return this;
            }else{
              return new UncheckedFalseView(root);
            }
          }else{
            if(toInclusive){
              return new UncheckedTrueView(root);
            }
          }
        }else{
          if(fromInclusive && toInclusive){
            return new UncheckedFalseView(root);
          }
        }
      }
      return EmptyView.UncheckedAscending;
    }
    private static class Checked extends AscendingView{
      private static final long serialVersionUID=1L;
      private Checked(BooleanSetImpl root){
        super(root);
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return root;
      }
      @Override public Object clone(){
        return new BooleanSetImpl.Checked(root.state);
      }
      private Object writeReplace(){
        return new BooleanSetImpl.Checked(root.state);
      }
      @Override public boolean firstBoolean(){
        switch(root.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b10:
            return true;
          default:
            return false;
        }
      }
      @Override public boolean lastBoolean(){
        switch(root.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b01:
            return false;
          default:
            return true;
        }
      }
      @Override public OmniIterator.OfBoolean iterator(){
        final BooleanSetImpl root;
        final int rootState;
        if((rootState=(root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedAscendingFullItr(root,rootState);
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        final BooleanSetImpl root;
        final int rootState;
        if((rootState=(root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedDescendingFullItr(root,rootState);
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        if(fromElement){
          if(toElement){
            if(fromInclusive){
              if(toInclusive){
                return new UncheckedTrueView.Checked(root);
              }else{
                return EmptyView.CheckedAscendingMiddle;
              }
            }else{
              if(toInclusive){
                return EmptyView.CheckedAscendingTail;
              }
            }
          }else{
            if(fromInclusive && toInclusive){
              return EmptyView.CheckedAscendingMiddle;
            }
          }
        }else{
          if(toElement){
            if(fromInclusive){
              if(toInclusive){
                return this;
              }else{
                return new UncheckedFalseView.Checked(root);
              }
            }else{
              if(toInclusive){
                return new UncheckedTrueView.Checked(root);
              }else{
                return EmptyView.CheckedAscendingMiddle;
              }
            }
          }else{
            if(fromInclusive){
              if(toInclusive){
                return new UncheckedFalseView.Checked(root);
              }else{
                return EmptyView.CheckedAscendingHead;
              }
            }else{
              if(toInclusive){
                return EmptyView.CheckedAscendingMiddle;
              }
            }
          }
        }
        throw new IllegalArgumentException("out of bounds");
      }
    }
  }
  private static abstract class AbstractSingleView extends AbstractBooleanSet{
    transient final BooleanSetImpl root;
    private AbstractSingleView(BooleanSetImpl root){
      this.root=root;
    }
    @Override public BooleanComparator comparator(){
      return Boolean::compare;
    }
  }
  private static class UncheckedTrueView extends AbstractSingleView{
    private UncheckedTrueView(BooleanSetImpl root){
      super(root);
    }
    @Override boolean addTrue(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&(10))==0){
        root.state=state+10;
        return true;
      }
      return false;
    }
    @Override boolean containsTrue(){
      return (root.state&0b10)!=0;
    }
    @Override boolean removeTrue(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&(10))!=0){
        root.state=state-10;
        return true;
      }
      return false;
    }
    @Override boolean addFalse(){
      throw new IllegalArgumentException("out of bounds");
    }
    @Override boolean containsFalse(){
      return false;
    }
    @Override boolean removeFalse(){
      return false;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0 && filter.test(true)){
        root.state=state-0b10;
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0 && filter.test(Boolean.TRUE)){
        root.state=state-0b10;
        return true;
      }
      return false;
    }
    @Override public int size(){
      return root.state>>>1;
    }
    @Override public void clear(){
      root.state&=0b01;
    }
    @Override public int hashCode(){
      if((root.state&0b10)==0){
        return 0;
      }
      return 1231;
    }
    @Override public boolean isEmpty(){
      return (root.state&0b10)==0;
    }
    @Override public boolean equals(Object val){
    if(val==this){
        return true;
      }
      if(val instanceof Set){
        if(((root.state)&0b01)==0){
          return ((Set<?>)val).isEmpty();
        }
        return equalsTrueState((Set<?>)val);
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      if((root.state&0b10)==0){
        if(dst.length!=0){
          dst[0]=null;
        }
      }else{
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      if((root.state&0b10)==0){
        return arrConstructor.apply(0);
      }else{
        final T[] dst;
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        return dst;
      }
    }
    @Override public Boolean[] toArray(){
      if((root.state&0b10)!=0){
        return new Boolean[]{Boolean.TRUE};
      }
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
    }
    @Override public boolean[] toBooleanArray(){
      if((root.state&0b10)!=0){
        return new boolean[]{true};
      }
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override public byte[] toByteArray(){
      if((root.state&0b10)!=0){
        return new byte[]{(byte)1};
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public char[] toCharArray(){
      if((root.state&0b10)!=0){
        return new char[]{(char)1};
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      if((root.state&0b10)!=0){
        return new short[]{(short)1};
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      if((root.state&0b10)!=0){
        return new int[]{1};
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      if((root.state&0b10)!=0){
        return new long[]{1L};
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      if((root.state&0b10)!=0){
        return new float[]{1F};
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      if((root.state&0b10)!=0){
        return new double[]{1D};
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public void forEach(BooleanConsumer action){
      if((root.state&0b10)!=0){
        action.accept(true);
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      if((root.state&0b10)!=0){
        action.accept(Boolean.TRUE);
      }
    }
    @Override public boolean firstBoolean(){
      return true;
    }
    @Override public boolean lastBoolean(){
      return true;
    }
    @Override public String toString(){
      if((root.state&0b10)!=0){
        return "[true]";
      }
      return "[]";
    }
    @Override public Boolean ceiling(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean floor(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean lower(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean higher(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollFirst(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollLast(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean booleanCeiling(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean booleanFloor(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lowerBoolean(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean higherBoolean(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollLastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte byteCeiling(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte byteFloor(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte lowerByte(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte higherByte(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte pollFirstByte(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte pollLastByte(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char charCeiling(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char charFloor(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char lowerChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char higherChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char pollFirstChar(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char pollLastChar(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short shortCeiling(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short shortFloor(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short lowerShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short higherShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short pollFirstShort(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short pollLastShort(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intCeiling(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intFloor(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int lowerInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int higherInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int pollFirstInt(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int pollLastInt(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longCeiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longFloor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long lowerLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long higherLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long pollFirstLong(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long pollLastLong(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatCeiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatFloor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float lowerFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float higherFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float pollFirstFloat(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float pollLastFloat(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleCeiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleFloor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double lowerDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double higherDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double pollFirstDouble(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double pollLastDouble(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final BooleanSetImpl root;
      if(((root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedTrueItr(root,0b1);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final BooleanSetImpl root;
      if(((root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedTrueItr(root,0b1);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedTrueView.Descending(root);
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        //TODO
          throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends UncheckedTrueView{
      private Descending(BooleanSetImpl root){
        super(root);
      }
      @Override public BooleanComparator comparator(){
        return BooleanComparator::descendingCompare;
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return new UncheckedTrueView(root);
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
          //TODO
            throw new omni.util.NotYetImplementedException();
      }
    }
    private static class Checked extends UncheckedTrueView{
      private Checked(BooleanSetImpl root){
        super(root);
      }
      @Override public boolean firstBoolean(){
        if((root.state&0b10)==0){
          throw new NoSuchElementException();
        }
        return true;
      }
      @Override public boolean lastBoolean(){
        if((root.state&0b10)==0){
          throw new NoSuchElementException();
        }
        return true;
      }
      @Override public OmniIterator.OfBoolean iterator(){
        final BooleanSetImpl root;
        if(((root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedTrueItr(root,0b10);
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return new UncheckedTrueView.Checked.Descending(root);
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
          //TODO
            throw new omni.util.NotYetImplementedException();
      }
      private static class Descending extends Checked{
        private Descending(BooleanSetImpl root){
          super(root);
        }
        @Override public BooleanComparator comparator(){
          return BooleanComparator::descendingCompare;
        }
        @Override public OmniNavigableSet.OfBoolean descendingSet(){
          return new UncheckedTrueView.Checked(root);
        }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
            //TODO
              throw new omni.util.NotYetImplementedException();
        }
      }
    }
  }
  private static class UncheckedFalseView extends AbstractSingleView{
    private UncheckedFalseView(BooleanSetImpl root){
      super(root);
    }
    @Override boolean addTrue(){
      throw new IllegalArgumentException("out of bounds");
    }
    @Override boolean containsTrue(){
      return false;
    }
    @Override boolean removeTrue(){
      return false;
    }
    @Override boolean addFalse(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&(01))==0){
        root.state=state+01;
        return true;
      }
      return false;
    }
    @Override boolean containsFalse(){
      return (root.state&0b01)!=0;
    }
    @Override boolean removeFalse(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&(01))!=0){
        root.state=state-01;
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0 && filter.test(false)){
        root.state=state-0b01;
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0 && filter.test(Boolean.FALSE)){
        root.state=state-0b01;
        return true;
      }
      return false;
    }
    @Override public int size(){
      return root.state&0b01;
    }
    @Override public void clear(){
      root.state&=0b10;
    }
    @Override public int hashCode(){
      if((root.state&0b01)==0){
        return 0;
      }
      return 1237;
    }
    @Override public boolean isEmpty(){
      return (root.state&0b01)==0;
    }
    @Override public boolean equals(Object val){
    if(val==this){
        return true;
      }
      if(val instanceof Set){
        if(((root.state)&0b01)==0){
          return ((Set<?>)val).isEmpty();
        }
        return equalsFalseState((Set<?>)val);
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      if((root.state&0b01)==0){
        if(dst.length!=0){
          dst[0]=null;
        }
      }else{
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      if((root.state&0b01)==0){
        return arrConstructor.apply(0);
      }else{
        final T[] dst;
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        return dst;
      }
    }
    @Override public Boolean[] toArray(){
      if((root.state&0b01)!=0){
        return new Boolean[]{Boolean.FALSE};
      }
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
    }
    @Override public boolean[] toBooleanArray(){
      if((root.state&0b01)!=0){
        return new boolean[]{false};
      }
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override public byte[] toByteArray(){
      if((root.state&0b01)!=0){
        return new byte[]{(byte)0};
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public char[] toCharArray(){
      if((root.state&0b01)!=0){
        return new char[]{(char)0};
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      if((root.state&0b01)!=0){
        return new short[]{(short)0};
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      if((root.state&0b01)!=0){
        return new int[]{0};
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      if((root.state&0b01)!=0){
        return new long[]{0L};
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      if((root.state&0b01)!=0){
        return new float[]{0F};
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      if((root.state&0b01)!=0){
        return new double[]{0D};
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public void forEach(BooleanConsumer action){
      if((root.state&0b01)!=0){
        action.accept(false);
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      if((root.state&0b01)!=0){
        action.accept(Boolean.FALSE);
      }
    }
    @Override public boolean firstBoolean(){
      return false;
    }
    @Override public boolean lastBoolean(){
      return false;
    }
    @Override public String toString(){
      if((root.state&0b01)!=0){
        return "[false]";
      }
      return "[]";
    }
    @Override public Boolean ceiling(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean floor(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean lower(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean higher(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollFirst(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollLast(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean booleanCeiling(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean booleanFloor(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lowerBoolean(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean higherBoolean(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollLastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte byteCeiling(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte byteFloor(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte lowerByte(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte higherByte(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte pollFirstByte(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte pollLastByte(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char charCeiling(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char charFloor(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char lowerChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char higherChar(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char pollFirstChar(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public char pollLastChar(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short shortCeiling(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short shortFloor(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short lowerShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short higherShort(short val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short pollFirstShort(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short pollLastShort(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intCeiling(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int intFloor(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int lowerInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int higherInt(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int pollFirstInt(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int pollLastInt(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longCeiling(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long longFloor(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long lowerLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long higherLong(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long pollFirstLong(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long pollLastLong(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatCeiling(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float floatFloor(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float lowerFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float higherFloat(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float pollFirstFloat(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float pollLastFloat(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleCeiling(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double doubleFloor(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double lowerDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double higherDouble(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double pollFirstDouble(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double pollLastDouble(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final BooleanSetImpl root;
      if(((root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedFalseItr(root,0b1);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final BooleanSetImpl root;
      if(((root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedFalseItr(root,0b1);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedFalseView.Descending(root);
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        //TODO
          throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends UncheckedFalseView{
      private Descending(BooleanSetImpl root){
        super(root);
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return new UncheckedFalseView(root);
      }
      @Override public BooleanComparator comparator(){
        return BooleanComparator::descendingCompare;
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
          //TODO
            throw new omni.util.NotYetImplementedException();
      }
    }
    private static class Checked extends UncheckedFalseView{
      private Checked(BooleanSetImpl root){
        super(root);
      }
      @Override public boolean firstBoolean(){
        if((root.state&0b01)==0){
          throw new NoSuchElementException();
        }
        return false;
      }
      @Override public boolean lastBoolean(){
        if((root.state&0b01)==0){
          throw new NoSuchElementException();
        }
        return false;
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
          return new UncheckedFalseView.Checked.Descending(root);
        }
      @Override public OmniIterator.OfBoolean iterator(){
        final BooleanSetImpl root;
        if(((root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedFalseItr(root,0b10);
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        final BooleanSetImpl root;
        if(((root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedFalseItr(root,0b10);
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
          //TODO
            throw new omni.util.NotYetImplementedException();
      }
      private static class Descending extends Checked{
        private Descending(BooleanSetImpl root){
          super(root);
        }
        @Override public BooleanComparator comparator(){
          return BooleanComparator::descendingCompare;
        }
        @Override public OmniNavigableSet.OfBoolean descendingSet(){
          return new UncheckedFalseView.Checked(root);
        }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
            //TODO
              throw new omni.util.NotYetImplementedException();
        }
      }
    }
  }
  private static class UncheckedAscendingFullItr extends AbstractBooleanItr{
    // valid itrStates
    // state | previously returned | next return | nextBoolean post-state | remove                                                  | forEachRemaining | expected root state
    // 0b00  | true OR false       | undefined   | undefined              | if root contains true, remove true, else remove false   | do nothing       | 0b01 OR 0b10 OR 0b11
    // 0b01  | undefined           | false       | 0b00                   | undefined                                               | false            | 0b01
    // 0b10  | assumed false       | true        | 0b00                   | remove false                                            | true             | 0b11
    // 0b11  | undefined           | false       | 0b10                   | undefined                                               | false,true       | 0b11
    transient final BooleanSetImpl root;
    transient int itrState;
    private UncheckedAscendingFullItr(BooleanSetImpl root,int itrState){
      this.root=root;
      this.itrState=itrState;
    }
    @Override public Object clone(){
      return new UncheckedAscendingFullItr(root,itrState);
    }
    @Override public boolean hasNext(){
      return itrState!=0;
    }
    @Override public boolean nextBoolean(){
      switch(this.itrState){
        case 0b11:
          this.itrState=0b10;
          break;
        case 0b10:
          this.itrState=0b00;
          return true;
        default:
          this.itrState=0b00;
      } 
      return false;
    }
    @Override public void remove(){
      if(itrState==0){
        final BooleanSetImpl root;
        final int rootState;
        (root=this.root).state=(rootState=root.state)-((rootState&0b10)==0?0b01:0b10);
      }else{
        root.state=0b10;
      }
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      switch(itrState){
        default:
          return;
        case 0b11:
          action.accept(false);
        case 0b10:
          action.accept(true);
          break;
        case 0b01:
          action.accept(false);
      }
      this.itrState=0b00;
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      switch(itrState){
        default:
          return;
        case 0b11:
          action.accept(Boolean.FALSE);
        case 0b10:
          action.accept(Boolean.TRUE);
          break;
        case 0b01:
          action.accept(Boolean.FALSE);
      }
      this.itrState=0b00;
    }
  }
  private static class CheckedAscendingFullItr extends UncheckedAscendingFullItr{
    // valid itrStates
    // state | previously returned | next return | nextBoolean post-state | remove       | remove post-state | forEachRemaining | forEachRemaining post-state | expected root state
    // 0b000 | false               | true        | 0b110                  | remove false | 0b010             | true             | 0b110                       | 0b11
    // 0b001 | undefined           | false       | 0b100                  | throw ISE    | 0b001             | false            | 0b100                       | 0b01
    // 0b010 | undefined           | true        | 0b110                  | throw ISE    | 0b010             | true             | 0b110                       | 0b10
    // 0b011 | undefined           | false       | 0b000                  | throw ISE    | 0b011             | false,true       | 0b110                       | 0b11
    // 0b100 | false               | throw NSE   | 0b100                  | remove false | 0b101             | do nothing       | 0b100                       | 0b01
    // 0b101 | undefined           | throw NSE   | 0b101                  | throw ISE    | 0b101             | do nothing       | 0b101                       | 0b00 OR 0b01
    // 0b110 | true                | throw NSE   | 0b110                  | remove true  | 0b101             | do nothing       | 0b110                       | 0b10 OR 0b11
    private CheckedAscendingFullItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new CheckedAscendingFullItr(root,itrState);
    }
    @Override public boolean hasNext(){
      return itrState<0b100;
    }
    @Override public boolean nextBoolean(){
      switch((this.itrState<<2)|(root.state)){
        case 0b10001:
        case 0b10100:
        case 0b10101:
        case 0b11010:
        case 0b11011:
          throw new NoSuchElementException();
        default:
          throw new ConcurrentModificationException();
        case 0b00011:
        case 0b01010:
          this.itrState=0b110;
          return true;
        case 0b00101:
          this.itrState=0b100;
          break;
        case 0b01111:
          this.itrState=0b000;
      }
      return false;
    }
    @Override public void remove(){
          final BooleanSetImpl root;
          switch((itrState<<2)|((root=this.root).state)){
          case 0b00000:
          case 0b00001:
          case 0b00010:
          case 0b10010:
          case 0b10011:
          case 0b11000:
          case 0b11001:
          case 0b10000:
            throw new ConcurrentModificationException();
          default:
            throw new IllegalStateException();
          case 0b00011:
            root.state=0b10;
            itrState=0b010;
            return;
          case 0b10001:
          case 0b11010:
            root.state=0b00;
            break;
          case 0b11011:
            root.state=0b01;
          }
          itrState=0b101;
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      switch((itrState<<2)|root.state){
      default:
        throw new ConcurrentModificationException();
      case 0b00101:
        action.accept(false);
        itrState=0b100;
        break;
      case 0b01111:
        action.accept(false);
      case 0b00011:
      case 0b01010:
        action.accept(true);
        itrState=0b110;
      case 0b10001:
      case 0b10100:
      case 0b10101:
      case 0b11010:
      case 0b11011:
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      switch((itrState<<2)|root.state){
      default:
        throw new ConcurrentModificationException();
      case 0b00101:
        action.accept(Boolean.FALSE);
        itrState=0b100;
        break;
      case 0b01111:
        action.accept(Boolean.FALSE);
      case 0b00011:
      case 0b01010:
        action.accept(Boolean.TRUE);
        itrState=0b110;
      case 0b10001:
      case 0b10100:
      case 0b10101:
      case 0b11010:
      case 0b11011:
      }
    }
  }
  private static class UncheckedDescendingFullItr extends UncheckedAscendingFullItr{
    // valid itrStates
    // state | previously returned | next return | nextBoolean post-state | remove                                                 | forEachRemaining | expected root state
    // 0b00  | true OR false       | undefined   | undefined              | if root contains false, remove false, else remove true | do nothing       | 0b01 OR 0b10 OR 0b11
    // 0b01  | assumed true        | false       | 0b00                   | remove true                                            | false            | 0b11
    // 0b10  | undefined           | true        | 0b00                   | undefined                                              | true             | 0b10
    // 0b11  | undefined           | true        | 0b01                   | undefined                                              | true,false       | 0b11
    private UncheckedDescendingFullItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new UncheckedDescendingFullItr(root,itrState);
    }
    @Override public boolean nextBoolean(){
      switch(this.itrState){
        case 0b11:
          this.itrState=0b01;
          break;
        case 0b01:
          this.itrState=0b00;
          return false;
        default:
          this.itrState=0b00;
          break;
      } 
      return true;
    }
    @Override public void remove(){
      if(itrState==0){
        final BooleanSetImpl root;
        final int rootState;
        (root=this.root).state=(rootState=root.state)-((rootState&0b01)==0?0b10:0b01);
      }else{
        root.state=0b01;
      }
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      switch(itrState){
        default:
          return;
        case 0b11:
          action.accept(true);
        case 0b01:
          action.accept(false);
          break;
        case 0b10:
          action.accept(true);
      }
      this.itrState=0b00;
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      switch(itrState){
        default:
          return;
        case 0b11:
          action.accept(Boolean.TRUE);
        case 0b01:
          action.accept(Boolean.FALSE);
          break;
        case 0b10:
          action.accept(Boolean.TRUE);
      }
      this.itrState=0b00;
    }
  }
  private static class CheckedDescendingFullItr extends CheckedAscendingFullItr{
    // valid itrStates
    // state | previously returned | next return | nextBoolean post-state | remove       | remove post-state | forEachRemaining | forEachRemaining post-state | expected root state
    // 0b000 | true                | false       | 0b100                  | remove true  | 0b001             | false            | 0b100                       | 0b11
    // 0b001 | undefined           | false       | 0b100                  | throw ISE    | 0b001             | false            | 0b100                       | 0b01
    // 0b010 | undefined           | true        | 0b110                  | throw ISE    | 0b010             | true             | 0b110                       | 0b10
    // 0b011 | undefined           | true        | 0b000                  | throw ISE    | 0b011             | true,false       | 0b100                       | 0b11
    // 0b100 | false               | throw NSE   | 0b100                  | remove false | 0b101             | do nothing       | 0b100                       | 0b01 OR 0b11
    // 0b101 | undefined           | throw NSE   | 0b101                  | throw ISE    | 0b101             | do nothing       | 0b101                       | 0b00 OR 0b10
    // 0b110 | true                | throw NSE   | 0b110                  | remove true  | 0b101             | do nothing       | 0b110                       | 0b10
    private CheckedDescendingFullItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new CheckedDescendingFullItr(root,itrState);
    }
    @Override public boolean nextBoolean(){
      switch((this.itrState<<2)|(root.state)){
        case 0b10001:
        case 0b10011:
        case 0b10100:
        case 0b10110:
        case 0b11010:
          throw new NoSuchElementException();
        default:
          throw new ConcurrentModificationException();
        case 0b00011:
        case 0b00101:
          this.itrState=0b100;
          return false;
        case 0b01010:
          this.itrState=0b110;
          break;
        case 0b01111:
          this.itrState=0b000;
      }
      return true;
    }
    @Override public void remove(){
    final BooleanSetImpl root;
          switch((itrState<<2)|((root=this.root).state)){
          case 0b00000:
          case 0b00001:
          case 0b00010:
          case 0b10000:
          case 0b10010:
          case 0b11000:
          case 0b11001:
          case 0b11011:
            throw new ConcurrentModificationException();
          default:
            throw new IllegalStateException();
          case 0b00011:
            root.state=0b01;
            itrState=0b001;
            return;
          case 0b10001:
          case 0b11010:
            root.state=0b00;
            break;
          case 0b10011:
            root.state=0b10;
          }
          itrState=0b101;
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      switch((itrState<<2)|root.state){
      default:
        throw new ConcurrentModificationException();
      case 0b01010:
        action.accept(true);
        itrState=0b110;
        break;
      case 0b01111:
        action.accept(true);
      case 0b00011:
      case 0b00101:
        action.accept(false);
        itrState=0b100;
      case 0b10001:
      case 0b10011:
      case 0b10100:
      case 0b10110:
      case 0b11010:
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      switch((itrState<<2)|root.state){
      default:
        throw new ConcurrentModificationException();
      case 0b01010:
        action.accept(Boolean.TRUE);
        itrState=0b110;
        break;
      case 0b01111:
        action.accept(Boolean.TRUE);
      case 0b00011:
      case 0b00101:
        action.accept(Boolean.FALSE);
        itrState=0b100;
      case 0b10001:
      case 0b10011:
      case 0b10100:
      case 0b10110:
      case 0b11010:
      }
    }
  }
  private static class UncheckedTrueItr extends UncheckedAscendingFullItr{
    // valid itrStates
    // state | previously returned | next return | nextBoolean post-state | remove      | forEachRemaining | expected root state
    // 0b0   | true                | undefined   | undefined              | remove true | do nothing       | 0b10 OR 0b01 OR 0b11 OR 0b00
    // 0b1   | undefined           | true        | 0b0                    | undefined   | true             | 0b10 OR 0b11
    private UncheckedTrueItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new UncheckedTrueItr(root,itrState);
    }
    @Override public boolean nextBoolean(){
      itrState=0b0;
      return true;
    }
    @Override public void remove(){
      root.state&=0b01;
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      if(itrState==0b1){
        action.accept(true);
        itrState=0b0;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      if(itrState==0b1){
        action.accept(Boolean.TRUE);
        itrState=0b0;
      }
    }
  }
  private static class UncheckedFalseItr extends UncheckedTrueItr{
    // valid itrStates
    // state | previously returned | next return | nextBoolean post-state | remove      | forEachRemaining | expected root state
    // 0b0   | false               | undefined   | undefined              | remove false| do nothing       | 0b10 OR 0b01 OR 0b11 OR 0b00
    // 0b1   | undefined           | false       | 0b0                    | undefined   | false            | 0b01 OR 0b11
    private UncheckedFalseItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new UncheckedFalseItr(root,itrState);
    }
    @Override public boolean nextBoolean(){
      itrState=0b0;
      return false;
    }
    @Override public void remove(){
      root.state&=0b10;
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      if(itrState==0b1){
        action.accept(false);
        itrState=0b0;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      if(itrState==0b1){
        action.accept(Boolean.FALSE);
        itrState=0b0;
      }
    }
  }
  private static class CheckedTrueItr extends UncheckedAscendingFullItr{
    // valid itrStates
    // state | previously returned | next return | nextBoolean post-state | remove      | remove post-state | forEachRemaining | forEachRemaining post-state | expected root state
    // 0b00  | undefined           | throw NSE   | 0b00                   | throw ISE   | 0b00              | do nothing       | 0b00                        | 0b00 OR 0b01
    // 0b01  | true                | throw NSE   | 0b01                   | remove true | 0b00              | do nothing       | 0b01                        | 0b10 OR 0b11
    // 0b10  | undefined           | return true | 0b01                   | throw ISE   | 0b10              | true             | 0b01                        | 0b10 OR 0b11
    private CheckedTrueItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new CheckedTrueItr(root,itrState);
    }
    @Override public boolean hasNext(){
      return itrState==0b10;
    }
    @Override public boolean nextBoolean(){
      switch((itrState<<1)|(root.state>>>1)){
      case 0b000:
      case 0b011:
        throw new NoSuchElementException();
      default:
        throw new ConcurrentModificationException();
      case 0b101:
      }
      itrState=0b01;
      return true;
    }
    @Override public void remove(){
      final BooleanSetImpl root;
      final int rootState;
      switch((itrState<<1)|((rootState=(root=this.root).state)>>>1)){
      default:
        throw new IllegalStateException();
      case 0b010:
        throw new ConcurrentModificationException();
      case 0b011:
        root.state=rootState-0b10;
        itrState=0b00;
      }
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      switch((itrState<<1)|(root.state>>>1)){
      default:
        throw new ConcurrentModificationException();
      case 0b101:
        action.accept(true);
        itrState=0b01;
      case 0b000:
      case 0b011:
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      switch((itrState<<1)|(root.state>>>1)){
      default:
        throw new ConcurrentModificationException();
      case 0b101:
        action.accept(Boolean.TRUE);
        itrState=0b01;
      case 0b000:
      case 0b011:
      }
    }
  }
  private static class CheckedFalseItr extends CheckedTrueItr{
    // valid itrStates
    // state | previously returned | next return  | nextBoolean post-state | remove       | remove post-state | forEachRemaining | forEachRemaining post-state | expected root state
    // 0b00  | undefined           | throw NSE    | 0b00                   | throw ISE    | 0b00              | do nothing       | 0b00                        | 0b00 OR 0b10
    // 0b01  | false               | throw NSE    | 0b01                   | remove false | 0b00              | do nothing       | 0b01                        | 0b01 OR 0b11
    // 0b10  | undefined           | return false | 0b01                   | throw ISE    | 0b10              | false            | 0b01                        | 0b01 OR 0b11
    private CheckedFalseItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new CheckedFalseItr(root,itrState);
    }
    @Override public boolean nextBoolean(){
      switch((itrState<<1)|(root.state&0b01)){
      case 0b000:
      case 0b011:
        throw new NoSuchElementException();
      default:
        throw new ConcurrentModificationException();
      case 0b101:
      }
      itrState=0b01;
      return false;
    }
    @Override public void remove(){
      final BooleanSetImpl root;
      final int rootState;
      switch((itrState<<1)|((rootState=(root=this.root).state)&1)){
      default:
        throw new IllegalStateException();
      case 0b010:
        throw new ConcurrentModificationException();
      case 0b011:
        root.state=rootState-0b01;
        itrState=0b00;
      }
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      switch((itrState<<1)|(root.state&0b01)){
      default:
        throw new ConcurrentModificationException();
      case 0b101:
        action.accept(false);
        itrState=0b01;
      case 0b000:
      case 0b011:
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      switch((itrState<<1)|(root.state&0b01)){
      default:
        throw new ConcurrentModificationException();
      case 0b101:
        action.accept(Boolean.FALSE);
        itrState=0b01;
      case 0b000:
      case 0b011:
      }
    }
  }
}
