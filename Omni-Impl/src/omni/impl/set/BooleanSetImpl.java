package omni.impl.set;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.AbstractBooleanItr;
import omni.util.OmniArray;

public class BooleanSetImpl extends AbstractBooleanSet implements OmniNavigableSet.OfBoolean{
  transient int state;
  public BooleanSetImpl(){
    super();
  }
  public BooleanSetImpl(BooleanSetImpl that) {
    super();
    this.state=that.state;
  }
  public BooleanSetImpl(Collection<? extends Boolean> that) {
    super();
    //TODO optimize
    this.addAll(that);
  }
  public BooleanSetImpl(OmniCollection.OfRef<? extends Boolean> that) {
    super();
    //TODO optimize
    this.addAll(that);
  }
  public BooleanSetImpl(OmniCollection.OfBoolean that) {
    super();
    //TODO optimize
    this.addAll(that);
  }
  BooleanSetImpl(int state){
    this.state=state;
  }
  @Override public void clear(){
    state=0;
  }
  @Override public boolean isEmpty(){
    return state==0;
  }
  boolean addTrue() {
    final int state;
    if(((state=this.state)&0b10)!=0) {
      this.state=state|0b10;
      return true;
    }
    return false;
  }
  boolean addFalse() {
    final int state;
    if(((state=this.state)&0b01)!=0) {
      this.state=state|0b01;
      return true;
    }
    return false;
  }
  @Override public int size(){
    switch(state) {
    case 0b00:
      return 0;
    case 0b01:
    case 0b10:
      return 1;
    default:
      return 2;
    }
  }
  @SuppressWarnings("unchecked") @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final T[] arr;
    switch(state) {
    case 0b01:
      (arr=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
      break;
    case 0b10:
      (arr=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
      break;
    case 0b11:
      (arr=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
      arr[1]=(T)Boolean.TRUE;
      break;
    default:
      return arrConstructor.apply(0);
    }
    return arr;
  }
  @SuppressWarnings("unchecked") @Override public <T> T[] toArray(T[] dst){
    switch(state) {
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
      if(dst.length!=0) {
        dst[0]=null;
      }
    }
    return dst;
  }
  @Override public void forEach(BooleanConsumer action){
    switch(state) {
    case 0b01:
      action.accept(false);
      break;
    case 0b11:
      action.accept(false);
    case 0b10:
      action.accept(true);
    default:
    }
  }
  @Override public boolean removeIf(BooleanPredicate filter){
    switch(state) {
    case 0b01:
      if(filter.test(false)) {
       break;
      }
      return false;
    case 0b10:
      if(filter.test(true)) {
        break;
      }
      return false;
    case 0b11:
      if(filter.test(false)) {
        if(filter.test(true)) {
          break;
        }else {
          this.state=0b10;
          return true;
        }
      }else if(filter.test(true)) {
        this.state=0b01;
        return true;
      }
    default:
      return false;
    }
    this.state=0b00;
    return true;
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    switch(state) {
    case 0b01:
      action.accept(Boolean.FALSE);
      break;
    case 0b11:
      action.accept(Boolean.FALSE);
    case 0b10:
      action.accept(Boolean.TRUE);
    default:
    }
  }
  @Override public boolean removeIf(Predicate<? super Boolean> filter){
    switch(state) {
    case 0b01:
      if(filter.test(Boolean.FALSE)) {
       break;
      }
      return false;
    case 0b10:
      if(filter.test(Boolean.TRUE)) {
        break;
      }
      return false;
    case 0b11:
      if(filter.test(Boolean.FALSE)) {
        if(filter.test(Boolean.TRUE)) {
          break;
        }else {
          this.state=0b10;
          return true;
        }
      }else if(filter.test(Boolean.TRUE)) {
        this.state=0b01;
        return true;
      }
    default:
      return false;
    }
    this.state=0b00;
    return true;
  }
  @Override public OmniIterator.OfBoolean iterator(){
    return new UncheckedAscendingItr(this);
  }
  @Override public Boolean[] toArray(){
    switch(state) {
    case 0b01:
      return new Boolean[] {Boolean.FALSE};
    case 0b10:
      return new Boolean[] {Boolean.TRUE};
    case 0b11:
      return new Boolean[] {Boolean.FALSE,Boolean.TRUE};
    default:
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
    }
  }
  @Override public boolean[] toBooleanArray(){
    switch(state) {
    case 0b01:
      return new boolean[] {false};
    case 0b10:
      return new boolean[] {true};
    case 0b11:
      return new boolean[] {false,true};
    default:
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
  }
  @Override public double[] toDoubleArray(){
    switch(state) {
    case 0b01:
      return new double[] {0D};
    case 0b10:
      return new double[] {1D};
    case 0b11:
      return new double[] {0D,1D};
    default:
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
  }
  @Override public float[] toFloatArray(){
    switch(state) {
    case 0b01:
      return new float[] {0F};
    case 0b10:
      return new float[] {1F};
    case 0b11:
      return new float[] {0F,1F};
    default:
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
  }
  @Override public long[] toLongArray(){
    switch(state) {
    case 0b01:
      return new long[] {0L};
    case 0b10:
      return new long[] {1L};
    case 0b11:
      return new long[] {0L,1L};
    default:
      return OmniArray.OfLong.DEFAULT_ARR;
    }
  }
  @Override public int[] toIntArray(){
    switch(state) {
    case 0b01:
      return new int[] {0};
    case 0b10:
      return new int[] {1};
    case 0b11:
      return new int[] {0,1};
    default:
      return OmniArray.OfInt.DEFAULT_ARR;
    }
  }
  @Override public short[] toShortArray(){
    switch(state) {
    case 0b01:
      return new short[] {0};
    case 0b10:
      return new short[] {1};
    case 0b11:
      return new short[] {0,1};
    default:
      return OmniArray.OfShort.DEFAULT_ARR;
    }
  }
  @Override public byte[] toByteArray(){
    switch(state) {
    case 0b01:
      return new byte[] {0};
    case 0b10:
      return new byte[] {1};
    case 0b11:
      return new byte[] {0,1};
    default:
      return OmniArray.OfByte.DEFAULT_ARR;
    }
  }
  @Override public char[] toCharArray(){
    switch(state) {
    case 0b01:
      return new char[] {0};
    case 0b10:
      return new char[] {1};
    case 0b11:
      return new char[] {0,1};
    default:
      return OmniArray.OfChar.DEFAULT_ARR;
    }
  }
  @Override public Object clone(){
    return new BooleanSetImpl(this.state);
  }
  @Override boolean containsTrue(){
    return (this.state&0b10)!=0;
  }
  @Override boolean containsFalse(){
    return (this.state&0b01)!=0;
  }
  @Override boolean removeTrue(){
    final int state;
    if(((state=this.state)&0b10)!=0){
      this.state=state&0b01;
      return true;
    }
    return false;
  }
  @Override boolean removeFalse(){
    final int state;
    if(((state=this.state)&0b01)!=0){
      this.state=state&0b10;
      return true;
    }
    return false;
  }
  private static class UncheckedAscendingItr extends AbstractBooleanItr{
    transient final BooleanSetImpl root;
    transient int itrState;
    UncheckedAscendingItr(BooleanSetImpl root){
      this.root=root;
      this.itrState=root.state;
    }
    private UncheckedAscendingItr(UncheckedAscendingItr itr){
      this.root=itr.root;
      this.itrState=itr.itrState;
    }
    @Override public Object clone() {
      return new UncheckedAscendingItr(this);
    }
    @Override public void remove() {
      final BooleanSetImpl root;
      switch(this.itrState&((root=this.root).state<<2)) {
      case 0b1100:
        root.state=0b01;
        break;
      case 0b1110:
        root.state=0b10;
        break;
      default:
        root.state=0b00;
      }
    }
    @Override public boolean hasNext() {
      return this.itrState!=0;
    }
    @Override public boolean nextBoolean() {
      switch(itrState) {
      case 0b11:
        this.itrState=0b10;
        return false;
      case 0b10:
        this.itrState=0b00;
        return true;
      default:
        this.itrState=0b00;
        return false;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action) {
      switch(itrState) {
      case 0b11:
        action.accept(Boolean.FALSE);
      case 0b10:
        action.accept(Boolean.TRUE);
        break;
      case 0b01:
        action.accept(Boolean.FALSE);
      default:
      }
      this.itrState=0b00;
    }
    @Override public void forEachRemaining(BooleanConsumer action) {
      switch(itrState) {
      case 0b11:
        action.accept(false);
      case 0b10:
        action.accept(true);
        break;
      case 0b01:
        action.accept(false);
      default:
      }
      this.itrState=0b00;
    }
  }
  @Override public byte pollFirstByte(){
    switch(state) {
    case 0b11:
      this.state=0b10;
      return 0;
    case 0b10:
      this.state=0b00;
      return 1;
    case 0b01:
      this.state=0b00;
      return 0;
    default:
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte pollLastByte(){
    switch(state) {
    case 0b11:
      this.state=0b01;
      return 1;
    case 0b10:
      this.state=0b00;
      return 0;
    case 0b01:
      this.state=0b00;
      return 1;
    default:
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte lowerByte(byte val){
    switch(state) {
    case 0b11:
      //TODO
      return 0;
    case 0b10:
      if(val>1) {
        return 1;
      }
      break;
    case 0b01:
      if(val>0) {
        return 0;
      }
    default:
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte higherByte(byte val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public byte byteCeiling(byte val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public byte byteFloor(byte val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public short pollFirstShort(){
    switch(state) {
    case 0b11:
      this.state=0b10;
      return 0;
    case 0b10:
      this.state=0b00;
      return 1;
    case 0b01:
      this.state=0b00;
      return 0;
    default:
    }
    return Short.MIN_VALUE;
  }
  @Override public short pollLastShort(){
    switch(state) {
    case 0b11:
      this.state=0b01;
      return 1;
    case 0b10:
      this.state=0b00;
      return 0;
    case 0b01:
      this.state=0b00;
      return 1;
    default:
    }
    return Short.MIN_VALUE;
  }
  @Override public short lowerShort(short val){
    switch(state) {
    case 0b11:
    //TODO
      return 0;
    case 0b10:
      if(val>1) {
        return 1;
      }
      break;
    case 0b01:
      if(val>0) {
        return 0;
      }
    default:
    }
    return Short.MIN_VALUE;
  }
  @Override public short higherShort(short val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public short shortCeiling(short val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public short shortFloor(short val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public int pollFirstInt(){
    switch(state) {
    case 0b11:
      this.state=0b10;
      return 0;
    case 0b10:
      this.state=0b00;
      return 1;
    case 0b01:
      this.state=0b00;
      return 0;
    default:
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollLastInt(){
    switch(state) {
    case 0b11:
      this.state=0b01;
      return 1;
    case 0b10:
      this.state=0b00;
      return 0;
    case 0b01:
      this.state=0b00;
      return 1;
    default:
    }
    return Integer.MIN_VALUE;
  }
  @Override public int lowerInt(int val){
    switch(state) {
    case 0b11:
    //TODO
      return 0;
    case 0b10:
      if(val>1) {
        return 1;
      }
      break;
    case 0b01:
      if(val>0) {
        return 0;
      }
    default:
    }
    return Integer.MIN_VALUE;
  }
  @Override public int higherInt(int val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public int intCeiling(int val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public int intFloor(int val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public long pollFirstLong(){
    switch(state) {
    case 0b11:
      this.state=0b10;
      return 0;
    case 0b10:
      this.state=0b00;
      return 1;
    case 0b01:
      this.state=0b00;
      return 0;
    default:
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollLastLong(){
    switch(state) {
    case 0b11:
      this.state=0b01;
      return 1;
    case 0b10:
      this.state=0b00;
      return 0;
    case 0b01:
      this.state=0b00;
      return 1;
    default:
    }
    return Long.MIN_VALUE;
  }
  @Override public long lowerLong(long val){
    switch(state) {
    case 0b11:
    //TODO
      return 0;
    case 0b10:
      if(val>1L) {
        return 1L;
      }
      break;
    case 0b01:
      if(val>0L) {
        return 0L;
      }
    default:
    }
    return Long.MIN_VALUE;
  }
  @Override public long higherLong(long val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public long longCeiling(long val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public long longFloor(long val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public float pollFirstFloat(){
    switch(state) {
    case 0b11:
      this.state=0b10;
      return 0;
    case 0b10:
      this.state=0b00;
      return 1;
    case 0b01:
      this.state=0b00;
      return 0;
    default:
    }
    return Float.NaN;
  }
  @Override public float pollLastFloat(){
    switch(state) {
    case 0b11:
      this.state=0b01;
      return 1;
    case 0b10:
      this.state=0b00;
      return 0;
    case 0b01:
      this.state=0b00;
      return 1;
    default:
    }
    return Float.NaN;
  }
  @Override public float lowerFloat(float val){
    switch(state) {
    case 0b11:
    //TODO
      return 0;
    case 0b10:
      if(val>1L) {
        return 1L;
      }
      break;
    case 0b01:
      if(val>0L) {
        return 0L;
      }
    default:
    }
    return Float.NaN;
  }
  @Override public float higherFloat(float val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public float floatCeiling(float val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public float floatFloor(float val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public double pollFirstDouble(){
    switch(state) {
    case 0b11:
      this.state=0b10;
      return 0;
    case 0b10:
      this.state=0b00;
      return 1;
    case 0b01:
      this.state=0b00;
      return 0;
    default:
    }
    return Double.NaN;
  }
  @Override public double pollLastDouble(){
    switch(state) {
    case 0b11:
      this.state=0b01;
      return 1;
    case 0b10:
      this.state=0b00;
      return 0;
    case 0b01:
      this.state=0b00;
      return 1;
    default:
    }
    return Double.NaN;
  }
  @Override public double lowerDouble(double val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public double higherDouble(double val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public double doubleCeiling(double val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public double doubleFloor(double val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Boolean pollFirst(){
    switch(state) {
    case 0b11:
      this.state=0b10;
      return Boolean.FALSE;
    case 0b10:
      this.state=0b00;
      return Boolean.TRUE;
    case 0b01:
      this.state=0b00;
      return Boolean.FALSE;
    default:
    }
    return null;
  }
  @Override public Boolean pollLast(){
    switch(state) {
    case 0b11:
      this.state=0b01;
      return Boolean.TRUE;
    case 0b10:
      this.state=0b00;
      return Boolean.FALSE;
    case 0b01:
      this.state=0b00;
      return Boolean.TRUE;
    default:
    }
    return null;
  }
  @Override public char pollFirstChar(){
    switch(state) {
    case 0b11:
      this.state=0b10;
      return 0;
    case 0b10:
      this.state=0b00;
      return 1;
    case 0b01:
      this.state=0b00;
      return 0;
    default:
    }
    return Character.MIN_VALUE;
  }
  @Override public char pollLastChar(){
    switch(state) {
    case 0b11:
      this.state=0b01;
      return 1;
    case 0b10:
      this.state=0b00;
      return 0;
    case 0b01:
      this.state=0b00;
      return 1;
    default:
    }
    return Character.MIN_VALUE;
  }
  @Override public char lowerChar(char val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public char higherChar(char val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public char charCeiling(char val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public char charFloor(char val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public BooleanComparator comparator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean firstBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean lastBoolean(){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public OmniIterator.OfBoolean descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniNavigableSet.OfBoolean descendingSet(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive, boolean toElement,boolean toInclusive){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean pollFirstBoolean(){
    switch(state) {
    case 0b11:
      this.state=0b10;
      break;
    case 0b10:
      this.state=0b00;
      return true;
    case 0b01:
      this.state=0b00;
    default:
    }
    return false;
  }
  @Override public boolean pollLastBoolean(){
    switch(state) {
    case 0b10:
      this.state=0b00;
    default:
      return false;
    case 0b11:
      this.state=0b01;
      break;
    case 0b01:
      this.state=0b00;
    }
    return true;
  }
  @Override public Boolean lower(boolean val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Boolean floor(boolean val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Boolean ceiling(boolean val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public Boolean higher(boolean val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean lowerBoolean(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean booleanFloor(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean booleanCeiling(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public boolean higherBoolean(boolean val){
    // TODO Auto-generated method stub
    return false;
  }
}