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
import java.util.ConcurrentModificationException;
public abstract class BooleanSetImpl extends AbstractBooleanSet implements OmniNavigableSet.OfBoolean,Serializable,Cloneable{
  private static final long serialVersionUID=1L;
  private static int reverseCompare(boolean val1,boolean val2){
    if(val1==val2){
      return 0;
    }
    if(val1){
      return -1;
    }
    return 1;
  }
  transient int state;
  public BooleanSetImpl(){
    super();
  }
  public BooleanSetImpl(BooleanSetImpl that){
    super();
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
  abstract String fullString();
  @Override public String toString(){
    switch(this.state){
      case 0b00:
        return "[]";
      case 0b01:
        return "[false]";
      case 0b10:
        return "[true]";
      default:
        return fullString();
    }
  }
  @Override public void clear(){
    this.state=0b00;
  }
  @Override public boolean isEmpty(){
    return this.state==0b00;
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
  @Override public int hashCode(){
    switch(this.state){
      case 0b00:
        return 0;
      case 0b01:
        return 1231;
      case 0b10:
        return 1237;
      default:
        return 1231+1237;
    }
  }
  private void writeObject(ObjectOutputStream oos) throws IOException{
    oos.writeByte(state);
  }
  private void readObject(ObjectInputStream ois) throws IOException{
    this.state=ois.readUnsignedByte();
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
  @Override public boolean equals(Object val){
    if(val==this){
      return true;
    }
    if(val instanceof Set){
      switch(state){
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
  @Override public boolean removeIf(BooleanPredicate filter){
    switch(this.state){
      case 0b11:
        if(filter.test(false)){
          if(filter.test(true)){
            break;
          }else{
            this.state=0b10;
          }
          return true;
        }else if(filter.test(true)){
          this.state=0b01;
          return true;
        }
        break;
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
          }
          return true;
        }else if(filter.test(Boolean.TRUE)){
          this.state=0b01;
          return true;
        }
        break;
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
  abstract Boolean[] fullRefArray();
  @SuppressWarnings("unchecked")@Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final T[] dst;
    switch(this.state){
      case 0b00:
        return arrConstructor.apply(0);
      case 0b01:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        break;
      default:
        copyFull(dst=arrConstructor.apply(2));
    }
    return dst;
  }
  @SuppressWarnings("unchecked")@Override public <T> T[] toArray(T[] dst){
    switch(this.state){
      case 0b00:
        if(dst.length!=0){
          dst[0]=null;
        }
        break;
      case 0b01:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
        break;
      default:
        copyFull(dst=OmniArray.uncheckedArrResize(2,dst));
    }
    return dst;
  }
  abstract void copyFull(Object[] dst);
  @Override public Boolean[] toArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
      case 0b01:
        return new Boolean[]{Boolean.FALSE};
      case 0b10:
        return new Boolean[]{Boolean.TRUE};
      default:
        return fullRefArray();
    }
  }
  abstract boolean[] fullBooleanArray();
  @Override public boolean[] toBooleanArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfBoolean.DEFAULT_ARR;
      case 0b01:
        return new boolean[]{false};
      case 0b10:
        return new boolean[]{true};
      default:
        return fullBooleanArray();
    }
  }
  abstract byte[] fullByteArray();
  @Override public byte[] toByteArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfByte.DEFAULT_ARR;
      case 0b01:
        return new byte[]{0};
      case 0b10:
        return new byte[]{1};
      default:
        return fullByteArray();
    }
  }
  abstract char[] fullCharArray();
  @Override public char[] toCharArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfChar.DEFAULT_ARR;
      case 0b01:
        return new char[]{0};
      case 0b10:
        return new char[]{1};
      default:
        return fullCharArray();
    }
  }
  abstract short[] fullShortArray();
  @Override public short[] toShortArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfShort.DEFAULT_ARR;
      case 0b01:
        return new short[]{0};
      case 0b10:
        return new short[]{1};
      default:
        return fullShortArray();
    }
  }
  abstract int[] fullIntArray();
  @Override public int[] toIntArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfInt.DEFAULT_ARR;
      case 0b01:
        return new int[]{0};
      case 0b10:
        return new int[]{1};
      default:
        return fullIntArray();
    }
  }
  abstract long[] fullLongArray();
  @Override public long[] toLongArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfLong.DEFAULT_ARR;
      case 0b01:
        return new long[]{0L};
      case 0b10:
        return new long[]{1L};
      default:
        return fullLongArray();
    }
  }
  abstract float[] fullFloatArray();
  @Override public float[] toFloatArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfFloat.DEFAULT_ARR;
      case 0b01:
        return new float[]{0F};
      case 0b10:
        return new float[]{1F};
      default:
        return fullFloatArray();
    }
  }
  abstract double[] fullDoubleArray();
  @Override public double[] toDoubleArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfDouble.DEFAULT_ARR;
      case 0b01:
        return new double[]{0D};
      case 0b10:
        return new double[]{1D};
      default:
        return fullDoubleArray();
    }
  }
  @Override boolean addTrue(){
    final int state;
    if(((state=this.state)&0b10)==0){
      this.state=state+0b10;
      return true;
    }
    return false;
  }
  @Override boolean removeTrue(){
    final int state;
    if(((state=this.state)&0b10)!=0){
      this.state=state-0b10;
      return true;
    }
    return false;
  }
  @Override boolean containsTrue(){
    return (this.state&0b10)!=0;
  }
  @Override boolean addFalse(){
    final int state;
    if(((state=this.state)&0b01)==0){
      this.state=state+0b01;
      return true;
    }
    return false;
  }
  @Override boolean removeFalse(){
    final int state;
    if(((state=this.state)&0b01)!=0){
      this.state=state-0b01;
      return true;
    }
    return false;
  }
  @Override boolean containsFalse(){
    return (this.state&0b01)!=0;
  }
  public static class UncheckedAscending extends BooleanSetImpl{
    private static final long serialVersionUID=1L;
    public UncheckedAscending(){
      super();
    }
    public UncheckedAscending(BooleanSetImpl that){
      super(that);
    }
    public UncheckedAscending(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedAscending(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public UncheckedAscending(Collection<? extends Boolean> that){
      super(that);
    }
    UncheckedAscending(int state){
      super(state);
    }
    @Override public Object clone(){
      return new UncheckedAscending(state);
    }
    @Override public BooleanComparator comparator(){
      return Boolean::compare;
    }
    @Override String fullString(){
      return "[false, true]";
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
    @Override void copyFull(Object[] dst){
      dst[0]=Boolean.FALSE;
      dst[1]=Boolean.TRUE;
    }
    @Override Boolean[] fullRefArray(){
      return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
    }
    @Override boolean[] fullBooleanArray(){
      return new boolean[]{false,true};
    }
    @Override byte[] fullByteArray(){
      return new byte[]{0,1};
    }
    @Override char[] fullCharArray(){
      return new char[]{0,1};
    }
    @Override short[] fullShortArray(){
      return new short[]{0,1};
    }
    @Override int[] fullIntArray(){
      return new int[]{0,1};
    }
    @Override long[] fullLongArray(){
      return new long[]{0L,1L};
    }
    @Override float[] fullFloatArray(){
      return new float[]{0F,1F};
    }
    @Override double[] fullDoubleArray(){
      return new double[]{0D,1D};
    }
    @Override public Boolean pollFirst(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return Boolean.TRUE;
        default:
          this.state=0b10;
          break;
        case 0b00:
          return null;
        case 0b01:
          this.state=0b00;
      }
      return Boolean.FALSE;
    }
    @Override public Boolean pollLast(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return Boolean.FALSE;
        default:
          return null;
        case 0b10:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b10;
      }
      return Boolean.TRUE;
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
    @Override public boolean firstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return true;
        default:
          this.state=0b10;
          break;
        case 0b01:
          this.state=0b00;
        case 0b00:
      }
      return false;
    }
    @Override public boolean pollLastBoolean(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
        default:
          return false;
        case 0b10:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b10;
      }
      return true;
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
    @Override public byte pollFirstByte(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 1;
        default:
          this.state=0b10;
          break;
        case 0b00:
          return Byte.MIN_VALUE;
        case 0b01:
          this.state=0b00;
      }
      return 0;
    }
    @Override public byte pollLastByte(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 0;
        default:
          return Byte.MIN_VALUE;
        case 0b10:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b10;
      }
      return 1;
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
    @Override public char pollFirstChar(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 1;
        default:
          this.state=0b10;
          break;
        case 0b01:
          this.state=0b00;
        case 0b00:
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
        default:
          return Character.MIN_VALUE;
        case 0b10:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b10;
      }
      return 1;
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
    @Override public short pollFirstShort(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 1;
        default:
          this.state=0b10;
          break;
        case 0b00:
          return Short.MIN_VALUE;
        case 0b01:
          this.state=0b00;
      }
      return 0;
    }
    @Override public short pollLastShort(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 0;
        default:
          return Short.MIN_VALUE;
        case 0b10:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b10;
      }
      return 1;
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
    @Override public int pollFirstInt(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 1;
        default:
          this.state=0b10;
          break;
        case 0b00:
          return Integer.MIN_VALUE;
        case 0b01:
          this.state=0b00;
      }
      return 0;
    }
    @Override public int pollLastInt(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 0;
        default:
          return Integer.MIN_VALUE;
        case 0b10:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b10;
      }
      return 1;
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
    @Override public long pollFirstLong(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 1L;
        default:
          this.state=0b10;
          break;
        case 0b00:
          return Long.MIN_VALUE;
        case 0b01:
          this.state=0b00;
      }
      return 0L;
    }
    @Override public long pollLastLong(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 0L;
        default:
          return Long.MIN_VALUE;
        case 0b10:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b10;
      }
      return 1L;
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
    @Override public float pollFirstFloat(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 1F;
        default:
          this.state=0b10;
          break;
        case 0b00:
          return Float.NaN;
        case 0b01:
          this.state=0b00;
      }
      return 0F;
    }
    @Override public float pollLastFloat(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 0F;
        default:
          return Float.NaN;
        case 0b10:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b10;
      }
      return 1F;
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
    @Override public double pollFirstDouble(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 1D;
        default:
          this.state=0b10;
          break;
        case 0b00:
          return Double.NaN;
        case 0b01:
          this.state=0b00;
      }
      return 0D;
    }
    @Override public double pollLastDouble(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 0D;
        default:
          return Double.NaN;
        case 0b10:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b10;
      }
      return 1D;
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
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedDescendingFullView(this);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  public static class UncheckedDescending extends BooleanSetImpl{
    private static final long serialVersionUID=1L;
    public UncheckedDescending(){
      super();
    }
    public UncheckedDescending(BooleanSetImpl that){
      super(that);
    }
    public UncheckedDescending(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedDescending(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public UncheckedDescending(Collection<? extends Boolean> that){
      super(that);
    }
    UncheckedDescending(int state){
      super(state);
    }
    @Override public Object clone(){
      return new UncheckedDescending(state);
    }
    @Override public BooleanComparator comparator(){
      return BooleanSetImpl::reverseCompare;
    }
    @Override String fullString(){
      return "[true, false]";
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
    @Override void copyFull(Object[] dst){
      dst[0]=Boolean.TRUE;
      dst[1]=Boolean.FALSE;
    }
    @Override Boolean[] fullRefArray(){
      return new Boolean[]{Boolean.TRUE,Boolean.FALSE};
    }
    @Override boolean[] fullBooleanArray(){
      return new boolean[]{true,false};
    }
    @Override byte[] fullByteArray(){
      return new byte[]{1,0};
    }
    @Override char[] fullCharArray(){
      return new char[]{1,0};
    }
    @Override short[] fullShortArray(){
      return new short[]{1,0};
    }
    @Override int[] fullIntArray(){
      return new int[]{1,0};
    }
    @Override long[] fullLongArray(){
      return new long[]{1L,0L};
    }
    @Override float[] fullFloatArray(){
      return new float[]{1F,0F};
    }
    @Override double[] fullDoubleArray(){
      return new double[]{1D,0D};
    }
    @Override public Boolean pollFirst(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return Boolean.TRUE;
        default:
          this.state=0b01;
          break;
        case 0b00:
          return null;
        case 0b10:
          this.state=0b00;
      }
      return Boolean.FALSE;
    }
    @Override public Boolean pollLast(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return Boolean.FALSE;
        default:
          return null;
        case 0b01:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b01;
      }
      return Boolean.TRUE;
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
    @Override public boolean firstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return true;
        default:
          this.state=0b01;
          break;
        case 0b10:
          this.state=0b00;
        case 0b00:
      }
      return false;
    }
    @Override public boolean pollLastBoolean(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
        default:
          return false;
        case 0b01:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b01;
      }
      return true;
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
    @Override public byte pollFirstByte(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 1;
        default:
          this.state=0b01;
          break;
        case 0b00:
          return Byte.MIN_VALUE;
        case 0b10:
          this.state=0b00;
      }
      return 0;
    }
    @Override public byte pollLastByte(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 0;
        default:
          return Byte.MIN_VALUE;
        case 0b01:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b01;
      }
      return 1;
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
    @Override public char pollFirstChar(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 1;
        default:
          this.state=0b01;
          break;
        case 0b10:
          this.state=0b00;
        case 0b00:
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
        default:
          return Character.MIN_VALUE;
        case 0b01:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b01;
      }
      return 1;
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
    @Override public short pollFirstShort(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 1;
        default:
          this.state=0b01;
          break;
        case 0b00:
          return Short.MIN_VALUE;
        case 0b10:
          this.state=0b00;
      }
      return 0;
    }
    @Override public short pollLastShort(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 0;
        default:
          return Short.MIN_VALUE;
        case 0b01:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b01;
      }
      return 1;
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
    @Override public int pollFirstInt(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 1;
        default:
          this.state=0b01;
          break;
        case 0b00:
          return Integer.MIN_VALUE;
        case 0b10:
          this.state=0b00;
      }
      return 0;
    }
    @Override public int pollLastInt(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 0;
        default:
          return Integer.MIN_VALUE;
        case 0b01:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b01;
      }
      return 1;
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
    @Override public long pollFirstLong(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 1L;
        default:
          this.state=0b01;
          break;
        case 0b00:
          return Long.MIN_VALUE;
        case 0b10:
          this.state=0b00;
      }
      return 0L;
    }
    @Override public long pollLastLong(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 0L;
        default:
          return Long.MIN_VALUE;
        case 0b01:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b01;
      }
      return 1L;
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
    @Override public float pollFirstFloat(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 1F;
        default:
          this.state=0b01;
          break;
        case 0b00:
          return Float.NaN;
        case 0b10:
          this.state=0b00;
      }
      return 0F;
    }
    @Override public float pollLastFloat(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 0F;
        default:
          return Float.NaN;
        case 0b01:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b01;
      }
      return 1F;
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
    @Override public double pollFirstDouble(){
      switch(this.state){
        case 0b01:
          this.state=0b00;
          return 1D;
        default:
          this.state=0b01;
          break;
        case 0b00:
          return Double.NaN;
        case 0b10:
          this.state=0b00;
      }
      return 0D;
    }
    @Override public double pollLastDouble(){
      switch(this.state){
        case 0b10:
          this.state=0b00;
          return 0D;
        default:
          return Double.NaN;
        case 0b01:
          this.state=0b00;
          break;
        case 0b11:
          this.state=0b01;
      }
      return 1D;
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
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedAscendingFullView(this);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  public static class CheckedAscending extends UncheckedAscending{
    private static final long serialVersionUID=1L;
    public CheckedAscending(){
      super();
    }
    public CheckedAscending(BooleanSetImpl that){
      super(that);
    }
    public CheckedAscending(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedAscending(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public CheckedAscending(Collection<? extends Boolean> that){
      super(that);
    }
    CheckedAscending(int state){
      super(state);
    }
    @Override public Object clone(){
      return new CheckedAscending(state);
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
    @Override public boolean firstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lastBoolean(){
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
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new CheckedDescendingFullView(this);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  public static class CheckedDescending extends UncheckedDescending{
    private static final long serialVersionUID=1L;
    public CheckedDescending(){
      super();
    }
    public CheckedDescending(BooleanSetImpl that){
      super(that);
    }
    public CheckedDescending(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedDescending(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public CheckedDescending(Collection<? extends Boolean> that){
      super(that);
    }
    CheckedDescending(int state){
      super(state);
    }
    @Override public Object clone(){
      return new CheckedDescending(state);
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
    @Override public boolean firstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lastBoolean(){
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
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new CheckedAscendingFullView(this);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class UncheckedAscendingFullView extends AbstractFullView implements Cloneable,Serializable{
    private UncheckedAscendingFullView(BooleanSetImpl root){
      super(root);
    }
    @Override public BooleanComparator comparator(){
      return Boolean::compare;
    }
    @Override String fullString(){
      return "[false, true]";
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
    @Override void copyFull(Object[] dst){
      dst[0]=Boolean.FALSE;
      dst[1]=Boolean.TRUE;
    }
    @Override Boolean[] fullRefArray(){
      return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
    }
    @Override boolean[] fullBooleanArray(){
      return new boolean[]{false,true};
    }
    @Override byte[] fullByteArray(){
      return new byte[]{0,1};
    }
    @Override char[] fullCharArray(){
      return new char[]{0,1};
    }
    @Override short[] fullShortArray(){
      return new short[]{0,1};
    }
    @Override int[] fullIntArray(){
      return new int[]{0,1};
    }
    @Override long[] fullLongArray(){
      return new long[]{0L,1L};
    }
    @Override float[] fullFloatArray(){
      return new float[]{0F,1F};
    }
    @Override double[] fullDoubleArray(){
      return new double[]{0D,1D};
    }
    @Override public Boolean pollFirst(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return Boolean.TRUE;
        default:
          root.state=0b10;
          break;
        case 0b00:
          return null;
        case 0b01:
          root.state=0b00;
      }
      return Boolean.FALSE;
    }
    @Override public Boolean pollLast(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return Boolean.FALSE;
        default:
          return null;
        case 0b10:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b10;
      }
      return Boolean.TRUE;
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
    @Override public boolean firstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return true;
        default:
          root.state=0b10;
          break;
        case 0b01:
          root.state=0b00;
        case 0b00:
      }
      return false;
    }
    @Override public boolean pollLastBoolean(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
        default:
          return false;
        case 0b10:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b10;
      }
      return true;
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
    @Override public byte pollFirstByte(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 1;
        default:
          root.state=0b10;
          break;
        case 0b00:
          return Byte.MIN_VALUE;
        case 0b01:
          root.state=0b00;
      }
      return 0;
    }
    @Override public byte pollLastByte(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 0;
        default:
          return Byte.MIN_VALUE;
        case 0b10:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b10;
      }
      return 1;
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
    @Override public char pollFirstChar(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 1;
        default:
          root.state=0b10;
          break;
        case 0b01:
          root.state=0b00;
        case 0b00:
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
        default:
          return Character.MIN_VALUE;
        case 0b10:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b10;
      }
      return 1;
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
    @Override public short pollFirstShort(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 1;
        default:
          root.state=0b10;
          break;
        case 0b00:
          return Short.MIN_VALUE;
        case 0b01:
          root.state=0b00;
      }
      return 0;
    }
    @Override public short pollLastShort(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 0;
        default:
          return Short.MIN_VALUE;
        case 0b10:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b10;
      }
      return 1;
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
    @Override public int pollFirstInt(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 1;
        default:
          root.state=0b10;
          break;
        case 0b00:
          return Integer.MIN_VALUE;
        case 0b01:
          root.state=0b00;
      }
      return 0;
    }
    @Override public int pollLastInt(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 0;
        default:
          return Integer.MIN_VALUE;
        case 0b10:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b10;
      }
      return 1;
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
    @Override public long pollFirstLong(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 1L;
        default:
          root.state=0b10;
          break;
        case 0b00:
          return Long.MIN_VALUE;
        case 0b01:
          root.state=0b00;
      }
      return 0L;
    }
    @Override public long pollLastLong(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 0L;
        default:
          return Long.MIN_VALUE;
        case 0b10:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b10;
      }
      return 1L;
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
    @Override public float pollFirstFloat(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 1F;
        default:
          root.state=0b10;
          break;
        case 0b00:
          return Float.NaN;
        case 0b01:
          root.state=0b00;
      }
      return 0F;
    }
    @Override public float pollLastFloat(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 0F;
        default:
          return Float.NaN;
        case 0b10:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b10;
      }
      return 1F;
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
    @Override public double pollFirstDouble(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 1D;
        default:
          root.state=0b10;
          break;
        case 0b00:
          return Double.NaN;
        case 0b01:
          root.state=0b00;
      }
      return 0D;
    }
    @Override public double pollLastDouble(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 0D;
        default:
          return Double.NaN;
        case 0b10:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b10;
      }
      return 1D;
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
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedDescendingFullView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class UncheckedDescendingFullView extends AbstractFullView implements Cloneable,Serializable{
    private UncheckedDescendingFullView(BooleanSetImpl root){
      super(root);
    }
    @Override public BooleanComparator comparator(){
      return BooleanSetImpl::reverseCompare;
    }
    @Override String fullString(){
      return "[true, false]";
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
    @Override void copyFull(Object[] dst){
      dst[0]=Boolean.TRUE;
      dst[1]=Boolean.FALSE;
    }
    @Override Boolean[] fullRefArray(){
      return new Boolean[]{Boolean.TRUE,Boolean.FALSE};
    }
    @Override boolean[] fullBooleanArray(){
      return new boolean[]{true,false};
    }
    @Override byte[] fullByteArray(){
      return new byte[]{1,0};
    }
    @Override char[] fullCharArray(){
      return new char[]{1,0};
    }
    @Override short[] fullShortArray(){
      return new short[]{1,0};
    }
    @Override int[] fullIntArray(){
      return new int[]{1,0};
    }
    @Override long[] fullLongArray(){
      return new long[]{1L,0L};
    }
    @Override float[] fullFloatArray(){
      return new float[]{1F,0F};
    }
    @Override double[] fullDoubleArray(){
      return new double[]{1D,0D};
    }
    @Override public Boolean pollFirst(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return Boolean.TRUE;
        default:
          root.state=0b01;
          break;
        case 0b00:
          return null;
        case 0b10:
          root.state=0b00;
      }
      return Boolean.FALSE;
    }
    @Override public Boolean pollLast(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return Boolean.FALSE;
        default:
          return null;
        case 0b01:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b01;
      }
      return Boolean.TRUE;
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
    @Override public boolean firstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return true;
        default:
          root.state=0b01;
          break;
        case 0b10:
          root.state=0b00;
        case 0b00:
      }
      return false;
    }
    @Override public boolean pollLastBoolean(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
        default:
          return false;
        case 0b01:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b01;
      }
      return true;
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
    @Override public byte pollFirstByte(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 1;
        default:
          root.state=0b01;
          break;
        case 0b00:
          return Byte.MIN_VALUE;
        case 0b10:
          root.state=0b00;
      }
      return 0;
    }
    @Override public byte pollLastByte(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 0;
        default:
          return Byte.MIN_VALUE;
        case 0b01:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b01;
      }
      return 1;
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
    @Override public char pollFirstChar(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 1;
        default:
          root.state=0b01;
          break;
        case 0b10:
          root.state=0b00;
        case 0b00:
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
        default:
          return Character.MIN_VALUE;
        case 0b01:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b01;
      }
      return 1;
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
    @Override public short pollFirstShort(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 1;
        default:
          root.state=0b01;
          break;
        case 0b00:
          return Short.MIN_VALUE;
        case 0b10:
          root.state=0b00;
      }
      return 0;
    }
    @Override public short pollLastShort(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 0;
        default:
          return Short.MIN_VALUE;
        case 0b01:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b01;
      }
      return 1;
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
    @Override public int pollFirstInt(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 1;
        default:
          root.state=0b01;
          break;
        case 0b00:
          return Integer.MIN_VALUE;
        case 0b10:
          root.state=0b00;
      }
      return 0;
    }
    @Override public int pollLastInt(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 0;
        default:
          return Integer.MIN_VALUE;
        case 0b01:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b01;
      }
      return 1;
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
    @Override public long pollFirstLong(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 1L;
        default:
          root.state=0b01;
          break;
        case 0b00:
          return Long.MIN_VALUE;
        case 0b10:
          root.state=0b00;
      }
      return 0L;
    }
    @Override public long pollLastLong(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 0L;
        default:
          return Long.MIN_VALUE;
        case 0b01:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b01;
      }
      return 1L;
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
    @Override public float pollFirstFloat(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 1F;
        default:
          root.state=0b01;
          break;
        case 0b00:
          return Float.NaN;
        case 0b10:
          root.state=0b00;
      }
      return 0F;
    }
    @Override public float pollLastFloat(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 0F;
        default:
          return Float.NaN;
        case 0b01:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b01;
      }
      return 1F;
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
    @Override public double pollFirstDouble(){
      switch(root.state){
        case 0b01:
          root.state=0b00;
          return 1D;
        default:
          root.state=0b01;
          break;
        case 0b00:
          return Double.NaN;
        case 0b10:
          root.state=0b00;
      }
      return 0D;
    }
    @Override public double pollLastDouble(){
      switch(root.state){
        case 0b10:
          root.state=0b00;
          return 0D;
        default:
          return Double.NaN;
        case 0b01:
          root.state=0b00;
          break;
        case 0b11:
          root.state=0b01;
      }
      return 1D;
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
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedAscendingFullView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  public static class CheckedAscendingFullView extends UncheckedAscendingFullView{
    private CheckedAscendingFullView(BooleanSetImpl root){
      super(root);
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
    @Override public boolean firstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lastBoolean(){
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
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new CheckedDescendingFullView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  public static class CheckedDescendingFullView extends UncheckedDescendingFullView{
    private CheckedDescendingFullView(BooleanSetImpl root){
      super(root);
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
    @Override public boolean firstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lastBoolean(){
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
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new CheckedAscendingFullView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static abstract class AbstractFullView extends AbstractBooleanSet implements OmniNavigableSet.OfBoolean{
    final BooleanSetImpl root;
    private AbstractFullView(BooleanSetImpl root){
      this.root=root;
    }
    abstract String fullString();
    @Override public String toString(){
      switch(root.state){
        case 0b00:
          return "[]";
        case 0b01:
          return "[false]";
        case 0b10:
          return "[true]";
        default:
          return fullString();
      }
    }
    @Override public void clear(){
      root.state=0b00;
    }
    @Override public boolean isEmpty(){
      return root.state==0b00;
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
    @Override public int hashCode(){
      switch(root.state){
        case 0b00:
          return 0;
        case 0b01:
          return 1231;
        case 0b10:
          return 1237;
        default:
          return 1231+1237;
      }
    }
    @Override public boolean equals(Object val){
      if(val==this || val==root){
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
    @Override public boolean removeIf(BooleanPredicate filter){
      switch(root.state){
        case 0b11:
          if(filter.test(false)){
            if(filter.test(true)){
              break;
            }else{
              root.state=0b10;
            }
            return true;
          }else if(filter.test(true)){
            root.state=0b01;
            return true;
          }
          break;
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
      switch(root.state){
        case 0b11:
          if(filter.test(Boolean.FALSE)){
            if(filter.test(Boolean.TRUE)){
              break;
            }else{
              root.state=0b10;
            }
            return true;
          }else if(filter.test(Boolean.TRUE)){
            root.state=0b01;
            return true;
          }
          break;
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
    abstract Boolean[] fullRefArray();
    @SuppressWarnings("unchecked")@Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final T[] dst;
      switch(root.state){
        case 0b00:
          return arrConstructor.apply(0);
        case 0b01:
          (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
          break;
        case 0b10:
          (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
          break;
        default:
          copyFull(dst=arrConstructor.apply(2));
      }
      return dst;
    }
    @SuppressWarnings("unchecked")@Override public <T> T[] toArray(T[] dst){
      switch(root.state){
        case 0b00:
          if(dst.length!=0){
            dst[0]=null;
          }
          break;
        case 0b01:
          (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
          break;
        case 0b10:
          (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
          break;
        default:
          copyFull(dst=OmniArray.uncheckedArrResize(2,dst));
      }
      return dst;
    }
    abstract void copyFull(Object[] dst);
    @Override public Boolean[] toArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        case 0b01:
          return new Boolean[]{Boolean.FALSE};
        case 0b10:
          return new Boolean[]{Boolean.TRUE};
        default:
          return fullRefArray();
      }
    }
    abstract boolean[] fullBooleanArray();
    @Override public boolean[] toBooleanArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_ARR;
        case 0b01:
          return new boolean[]{false};
        case 0b10:
          return new boolean[]{true};
        default:
          return fullBooleanArray();
      }
    }
    abstract byte[] fullByteArray();
    @Override public byte[] toByteArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfByte.DEFAULT_ARR;
        case 0b01:
          return new byte[]{0};
        case 0b10:
          return new byte[]{1};
        default:
          return fullByteArray();
      }
    }
    abstract char[] fullCharArray();
    @Override public char[] toCharArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfChar.DEFAULT_ARR;
        case 0b01:
          return new char[]{0};
        case 0b10:
          return new char[]{1};
        default:
          return fullCharArray();
      }
    }
    abstract short[] fullShortArray();
    @Override public short[] toShortArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfShort.DEFAULT_ARR;
        case 0b01:
          return new short[]{0};
        case 0b10:
          return new short[]{1};
        default:
          return fullShortArray();
      }
    }
    abstract int[] fullIntArray();
    @Override public int[] toIntArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfInt.DEFAULT_ARR;
        case 0b01:
          return new int[]{0};
        case 0b10:
          return new int[]{1};
        default:
          return fullIntArray();
      }
    }
    abstract long[] fullLongArray();
    @Override public long[] toLongArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfLong.DEFAULT_ARR;
        case 0b01:
          return new long[]{0L};
        case 0b10:
          return new long[]{1L};
        default:
          return fullLongArray();
      }
    }
    abstract float[] fullFloatArray();
    @Override public float[] toFloatArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfFloat.DEFAULT_ARR;
        case 0b01:
          return new float[]{0F};
        case 0b10:
          return new float[]{1F};
        default:
          return fullFloatArray();
      }
    }
    abstract double[] fullDoubleArray();
    @Override public double[] toDoubleArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfDouble.DEFAULT_ARR;
        case 0b01:
          return new double[]{0D};
        case 0b10:
          return new double[]{1D};
        default:
          return fullDoubleArray();
      }
    }
    @Override boolean addTrue(){
      final int state;
      if(((state=root.state)&0b10)==0){
        root.state=state+0b10;
        return true;
      }
      return false;
    }
    @Override boolean removeTrue(){
      final int state;
      if(((state=root.state)&0b10)!=0){
        root.state=state-0b10;
        return true;
      }
      return false;
    }
    @Override boolean containsTrue(){
      return (root.state&0b10)!=0;
    }
    @Override boolean addFalse(){
      final int state;
      if(((state=root.state)&0b01)==0){
        root.state=state+0b01;
        return true;
      }
      return false;
    }
    @Override boolean removeFalse(){
      final int state;
      if(((state=root.state)&0b01)!=0){
        root.state=state-0b01;
        return true;
      }
      return false;
    }
    @Override boolean containsFalse(){
      return (root.state&0b01)!=0;
    }
  }
  private static abstract class AbstractTrueView extends AbstractBooleanSet implements OmniNavigableSet.OfBoolean{
    final BooleanSetImpl root;
    private AbstractTrueView(BooleanSetImpl root){
      this.root=root;
    }
    @Override public Boolean pollFirst(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return Boolean.FALSE;
      }
      return null;
    }
    @Override public Boolean pollLast(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return Boolean.FALSE;
      }
      return null;
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
    @Override public boolean firstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return false;
      }
      return false;
    }
    @Override public boolean pollLastBoolean(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return false;
      }
      return false;
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
    @Override public byte pollFirstByte(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0;
      }
      return Byte.MIN_VALUE;
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
    @Override public char pollFirstChar(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0;
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0;
      }
      return Character.MIN_VALUE;
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
    @Override public short pollFirstShort(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0;
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0;
      }
      return Short.MIN_VALUE;
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
    @Override public int pollFirstInt(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0;
      }
      return Integer.MIN_VALUE;
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
    @Override public long pollFirstLong(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0L;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0L;
      }
      return Long.MIN_VALUE;
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
    @Override public float pollFirstFloat(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0F;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0F;
      }
      return Float.NaN;
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
    @Override public double pollFirstDouble(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0D;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10) !=0){
        root.state=state-0b10;
        return 0D;
      }
      return Double.NaN;
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
        return new byte[]{1};
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public char[] toCharArray(){
      if((root.state&0b10)!=0){
        return new char[]{1};
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      if((root.state&0b10)!=0){
        return new short[]{1};
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
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      if((root.state&0b10)==0){
        return arrConstructor.apply(0);
      }else{
        final T[] dst;
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        return dst; 
      }
    }
    @Override public void clear(){
      root.state&=0b01;
    }
    @Override public int size(){
      return root.state>>>1;
    }
    @Override public boolean isEmpty(){
      return (root.state&0b10)!=0;
    }
    @Override boolean addFalse(){
      throw new IllegalArgumentException("false is out of range for this subset");
    }
    @Override boolean addTrue(){
      final BooleanSetImpl root;
      final int state;
      return (state=(root=this.root).state)!=(root.state=state|0b10);
    }
    @Override boolean containsFalse(){
      return false;
    }
    @Override boolean containsTrue(){
      return (root.state&0b10)!=0;
    }
    @Override boolean removeFalse(){
      return false;
    }
    @Override boolean removeTrue(){
      final int state;
      final BooleanSetImpl root;
      return (state=(root=this.root).state)!=(root.state=state&0b01);
    }
  }
  private static abstract class AbstractFalseView extends AbstractBooleanSet implements OmniNavigableSet.OfBoolean{
    final BooleanSetImpl root;
    private AbstractFalseView(BooleanSetImpl root){
      this.root=root;
    }
    @Override public Boolean pollFirst(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return Boolean.FALSE;
      }
      return null;  
    }
    @Override public Boolean pollLast(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return Boolean.FALSE;
      }
      return null;  
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
    @Override public boolean firstBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean lastBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean pollFirstBoolean(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
      }
      return false;  
    }
    @Override public boolean pollLastBoolean(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
      }
      return false;  
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
    @Override public byte pollFirstByte(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0;
      }
      return Byte.MIN_VALUE;  
    }
    @Override public byte pollLastByte(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0;
      }
      return Byte.MIN_VALUE;  
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
    @Override public char pollFirstChar(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
      }
      return Character.MIN_VALUE;  
    }
    @Override public char pollLastChar(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
      }
      return Character.MIN_VALUE;  
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
    @Override public short pollFirstShort(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0;
      }
      return Short.MIN_VALUE;  
    }
    @Override public short pollLastShort(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0;
      }
      return Short.MIN_VALUE;  
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
    @Override public int pollFirstInt(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0;
      }
      return Integer.MIN_VALUE;  
    }
    @Override public int pollLastInt(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0;
      }
      return Integer.MIN_VALUE;  
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
    @Override public long pollFirstLong(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0L;
      }
      return Long.MIN_VALUE;  
    }
    @Override public long pollLastLong(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0L;
      }
      return Long.MIN_VALUE;  
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
    @Override public float pollFirstFloat(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0F;
      }
      return Float.NaN;  
    }
    @Override public float pollLastFloat(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0F;
      }
      return Float.NaN;  
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
    @Override public double pollFirstDouble(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0D;
      }
      return Double.NaN;  
    }
    @Override public double pollLastDouble(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01) !=0){
        root.state=state-0b01;
        return 0D;
      }
      return Double.NaN;  
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
        return new byte[]{0};
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public char[] toCharArray(){
      if((root.state&0b01)!=0){
        return new char[]{0};
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      if((root.state&0b01)!=0){
        return new short[]{0};
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
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      if((root.state&0b01)==0){
        return arrConstructor.apply(0);
      }else{
        final T[] dst;
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        return dst; 
      }
    }
    @Override public int size(){
      return root.state&0b01;
    }
    @Override public boolean isEmpty(){
      return (root.state&0b01)!=0;
    }
    @Override public void clear(){
      root.state&=0b01;
    }
    @Override boolean addTrue(){
      throw new IllegalArgumentException("true is out of range for this subset");
    }
    @Override boolean addFalse(){
      final BooleanSetImpl root;
      final int state;
      return (state=(root=this.root).state)!=(root.state=state|0b01);
    }
    @Override boolean containsTrue(){
      return false;
    }
    @Override boolean containsFalse(){
      return (root.state&0b01)!=0;
    }
    @Override boolean removeTrue(){
      return false;
    }
    @Override boolean removeFalse(){
      final int state;
      final BooleanSetImpl root;
      return (state=(root=this.root).state)!=(root.state=state&0b10);
    }
  }
  private static class UncheckedAscendingTrueView extends AbstractTrueView{
    private UncheckedAscendingTrueView(BooleanSetImpl root){
      super(root);
    }
    @Override public BooleanComparator comparator(){
      return Boolean::compare;
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedDescendingTrueView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class UncheckedAscendingFalseView extends AbstractFalseView{
    private UncheckedAscendingFalseView(BooleanSetImpl root){
      super(root);
    }
    @Override public BooleanComparator comparator(){
      return Boolean::compare;
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedDescendingFalseView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class CheckedAscendingTrueView extends UncheckedAscendingTrueView{
    private CheckedAscendingTrueView(BooleanSetImpl root){
      super(root);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new CheckedDescendingTrueView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class CheckedAscendingFalseView extends UncheckedAscendingFalseView{
    private CheckedAscendingFalseView(BooleanSetImpl root){
      super(root);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new CheckedDescendingFalseView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class UncheckedDescendingTrueView extends AbstractTrueView{
    private UncheckedDescendingTrueView(BooleanSetImpl root){
      super(root);
    }
    @Override public BooleanComparator comparator(){
      return BooleanSetImpl::reverseCompare;
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedAscendingTrueView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class UncheckedDescendingFalseView extends AbstractFalseView{
    private UncheckedDescendingFalseView(BooleanSetImpl root){
      super(root);
    }
    @Override public BooleanComparator comparator(){
      return BooleanSetImpl::reverseCompare;
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedAscendingFalseView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class CheckedDescendingTrueView extends UncheckedDescendingTrueView{
    private CheckedDescendingTrueView(BooleanSetImpl root){
      super(root);
    }
    @Override public BooleanComparator comparator(){
      return BooleanSetImpl::reverseCompare;
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new CheckedAscendingTrueView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class CheckedDescendingFalseView extends UncheckedDescendingFalseView{
    private CheckedDescendingFalseView(BooleanSetImpl root){
      super(root);
    }
    @Override public BooleanComparator comparator(){
      return BooleanSetImpl::reverseCompare;
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new CheckedAscendingFalseView(root);
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static abstract class AbstractEmptyView extends AbstractBooleanSet implements OmniNavigableSet.OfBoolean{
    private AbstractEmptyView(){
      super();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Boolean pollFirst(){
      return null;
    }
    @Override public Boolean pollLast(){
      return null;
    }
    @Override public Boolean ceiling(boolean val){
      return null:
    }
    @Override public Boolean floor(boolean val){
      return null:
    }
    @Override public Boolean lower(boolean val){
      return null:
    }
    @Override public Boolean higher(boolean val){
      return null:
    }
    @Override public boolean firstBoolean(){
      throw new NoSuchElementException();
    }
    @Override public boolean lastBoolean(){
      throw new NoSuchElementException();
    }
    @Override public boolean pollFirstBoolean(){
      return false;
    }
    @Override public boolean pollLastBoolean(){
      return false;
    }
    @Override public boolean booleanCeiling(boolean val){
      return false:
    }
    @Override public boolean booleanFloor(boolean val){
      return false:
    }
    @Override public boolean lowerBoolean(boolean val){
      return false:
    }
    @Override public boolean higherBoolean(boolean val){
      return false:
    }
    @Override public byte pollFirstByte(){
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      return Byte.MIN_VALUE;
    }
    @Override public byte byteCeiling(byte val){
      return Byte.MIN_VALUE:
    }
    @Override public byte byteFloor(byte val){
      return Byte.MIN_VALUE:
    }
    @Override public byte lowerByte(byte val){
      return Byte.MIN_VALUE:
    }
    @Override public byte higherByte(byte val){
      return Byte.MIN_VALUE:
    }
    @Override public char pollFirstChar(){
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      return Character.MIN_VALUE;
    }
    @Override public char charCeiling(char val){
      return Character.MIN_VALUE:
    }
    @Override public char charFloor(char val){
      return Character.MIN_VALUE:
    }
    @Override public char lowerChar(char val){
      return Character.MIN_VALUE:
    }
    @Override public char higherChar(char val){
      return Character.MIN_VALUE:
    }
    @Override public short pollFirstShort(){
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      return Short.MIN_VALUE;
    }
    @Override public short shortCeiling(short val){
      return Short.MIN_VALUE:
    }
    @Override public short shortFloor(short val){
      return Short.MIN_VALUE:
    }
    @Override public short lowerShort(short val){
      return Short.MIN_VALUE:
    }
    @Override public short higherShort(short val){
      return Short.MIN_VALUE:
    }
    @Override public int pollFirstInt(){
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      return Integer.MIN_VALUE;
    }
    @Override public int intCeiling(int val){
      return Integer.MIN_VALUE:
    }
    @Override public int intFloor(int val){
      return Integer.MIN_VALUE:
    }
    @Override public int lowerInt(int val){
      return Integer.MIN_VALUE:
    }
    @Override public int higherInt(int val){
      return Integer.MIN_VALUE:
    }
    @Override public long pollFirstLong(){
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      return Long.MIN_VALUE;
    }
    @Override public long longCeiling(long val){
      return Long.MIN_VALUE:
    }
    @Override public long longFloor(long val){
      return Long.MIN_VALUE:
    }
    @Override public long lowerLong(long val){
      return Long.MIN_VALUE:
    }
    @Override public long higherLong(long val){
      return Long.MIN_VALUE:
    }
    @Override public float pollFirstFloat(){
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      return Float.NaN;
    }
    @Override public float floatCeiling(float val){
      return Float.NaN:
    }
    @Override public float floatFloor(float val){
      return Float.NaN:
    }
    @Override public float lowerFloat(float val){
      return Float.NaN:
    }
    @Override public float higherFloat(float val){
      return Float.NaN:
    }
    @Override public double pollFirstDouble(){
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      return Double.NaN;
    }
    @Override public double doubleCeiling(double val){
      return Double.NaN:
    }
    @Override public double doubleFloor(double val){
      return Double.NaN:
    }
    @Override public double lowerDouble(double val){
      return Double.NaN:
    }
    @Override public double higherDouble(double val){
      return Double.NaN:
    }
    @Override public <T> T[] toArray(T[] dst){
      if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return arrConstructor.apply(0);
    }
    @Override public Boolean[] toArray(){
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
    }
    @Override public boolean[] toBooleanArray(){
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override public byte[] toByteArray(){
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public char[] toCharArray(){
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public void clear(){
      //nothing to do
    }
    @Override public boolean isEmpty(){
      return true;
    }
    @Override public int size(){
      return 0;
    }
    @Override public void forEach(BooleanConsumer action){
      //nothing to do
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      //nothing to do
    }
    @Override public boolean removeIf(BooleanPredicate action){
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Boolean> action){
      return false;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override boolean addFalse(){
      throw new IllegalArgumentException("false is out of range for this subset");
    }
    @Override boolean addTrue(){
      throw new IllegalArgumentException("true is out of range for this subset");
    }
    @Override boolean containsFalse(){
      return false;
    }
    @Override boolean containsTrue(){
      return false;
    }
    @Override boolean removeFalse(){
      return false;
    }
    @Override boolean removeTrue(){
      return false;
    }
  }
  private static final AbstractEmptyView ASCENDING_EMPTY=new AbstractEmptyView(){
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return DESCENDING_EMPTY;
    }
    @Override public BooleanComparator comparator(){
      return Boolean::compare;
    }
  };
  private static final AbstractEmptyView DESCENDING_EMPTY=new AbstractEmptyView(){
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return ASCENDING_EMPTY;
    }
    @Override public BooleanComparator comparator(){
      return BooleanSetImpl::reverseCompare;
    }
  };
  //TODO true itrs
  //TODO false itrs
  //TODO empty itrs
  private static abstract class AbstractUncheckedFullItr extends AbstractBooleanItr{
    transient final BooleanSetImpl root;
    transient int itrState;
    private AbstractUncheckedFullItr(BooleanSetImpl root){
      this.root=root;
      this.itrState=root.state;
    }
    private AbstractUncheckedFullItr(BooleanSetImpl root,int itrState){
      this.root=root;
      this.itrState=itrState;
    }
    @Override public boolean hasNext(){
      return itrState!=0;
    }
  }
  private static class UncheckedAscendingFullItr extends AbstractUncheckedFullItr{
    private UncheckedAscendingFullItr(BooleanSetImpl root){
      super(root);
    }
    private UncheckedAscendingFullItr(UncheckedAscendingFullItr itr){
      super(itr.root,itr.itrState);
    }
    @Override public Object clone(){
      return new UncheckedAscendingFullItr(this);
    }
    @Override public void remove(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean nextBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class UncheckedDescendingFullItr extends AbstractUncheckedFullItr{
    private UncheckedDescendingFullItr(BooleanSetImpl root){
      super(root);
    }
    private UncheckedDescendingFullItr(UncheckedDescendingFullItr itr){
      super(itr.root,itr.itrState);
    }
    @Override public Object clone(){
      return new UncheckedDescendingFullItr(this);
    }
    @Override public void remove(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean nextBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static abstract class AbstractCheckedFullItr extends AbstractBooleanItr{
    transient final BooleanSetImpl root;
    transient int itrState;
    private AbstractCheckedFullItr(BooleanSetImpl root){
      this.root=root;
      this.itrState=root.state;
    }
    private AbstractCheckedFullItr(BooleanSetImpl root,int itrState){
      this.root=root;
      this.itrState=itrState;
    }
    @Override public boolean hasNext(){
      return (itrState&0b11)!=0;
    }
  }
  private static class CheckedAscendingFullItr extends AbstractCheckedFullItr{
    private CheckedAscendingFullItr(BooleanSetImpl root){
      super(root);
    }
    private CheckedAscendingFullItr(CheckedAscendingFullItr itr){
      super(itr.root,itr.itrState);
    }
    @Override public Object clone(){
      return new CheckedAscendingFullItr(this);
    }
    @Override public void remove(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean nextBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
  private static class CheckedDescendingFullItr extends AbstractCheckedFullItr{
    private CheckedDescendingFullItr(BooleanSetImpl root){
      super(root);
    }
    private CheckedDescendingFullItr(CheckedDescendingFullItr itr){
      super(itr.root,itr.itrState);
    }
    @Override public Object clone(){
      return new CheckedDescendingFullItr(this);
    }
    @Override public void remove(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean nextBoolean(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
  }
}
