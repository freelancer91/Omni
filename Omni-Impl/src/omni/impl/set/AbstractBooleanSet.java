package omni.impl.set;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.AbstractBooleanItr;
import omni.util.OmniArray;
import omni.util.TypeUtil;

abstract class AbstractBooleanSet implements OmniNavigableSet.OfBoolean{
  abstract boolean containsTrue();
  abstract boolean containsFalse();
  abstract boolean removeTrue();
  abstract boolean removeFalse();
  abstract boolean addTrue();
  abstract boolean addFalse();
  @Override public boolean contains(Object val){
    containsTrue:for(;;) {
      containsFalse:for(;;) {
        if(val instanceof Boolean) {
          if((boolean)val) {
            break containsTrue;
          }
          break containsFalse;
        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short) {
          switch(((Number)val).intValue()) {
          case 0:
            break containsFalse;
          case 1:
            break containsTrue;
          default:
          }
        }else if(val instanceof Long) {
          final long l;
          if((l=(long)val)==0L) {
            break containsFalse;
          }else if(l==1L) {
            break containsTrue;
          }
        }else if(val instanceof Float) {
          switch(Float.floatToRawIntBits((float)val)) {
          case 0:
          case Integer.MIN_VALUE:
            break containsFalse;
          case TypeUtil.FLT_TRUE_BITS:
            break containsTrue;
          }
        }else if(val instanceof Double) {
          final long bits;
          if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE) {
            break containsFalse;
          }else if(bits==TypeUtil.DBL_TRUE_BITS) {
            break containsTrue;
          }
        }else if(val instanceof Character) {
          switch(((Character)val).charValue()) {
          case 0:
            break containsFalse;
          case 1:
            break containsTrue;
          default:
          }
        }
        return false;
      }
      return containsFalse();
    }
    return containsTrue();
  }
  @Override public boolean contains(boolean val){
    if(val) {
      return containsTrue();
    }else {
      return containsFalse();
    }
  }
  @Override public boolean contains(int val){
    switch(val) {
    case 0:
      return containsFalse();
    case 1:
      return containsTrue();
    default:
      return false;
    }
  }
  @Override public boolean contains(long val){
    if(val==0L) {
      return containsFalse();
    }
    return val==1L && containsTrue();
  }
  @Override public boolean contains(float val){
    switch(Float.floatToRawIntBits(val)) {
    case 0:
    case Integer.MIN_VALUE:
      return containsFalse();
    case TypeUtil.FLT_TRUE_BITS:
      return containsTrue();
    default:
      return false;
    }
  }
  @Override public boolean contains(double val){
    final long bits;
    if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE) {
      return containsFalse();
    }
    return bits==TypeUtil.DBL_TRUE_BITS && containsTrue();
  }
  @Override public boolean remove(Object val){
    removeTrue:for(;;) {
      removeFalse:for(;;) {
        if(val instanceof Boolean) {
          if((boolean)val) {
            break removeTrue;
          }
          break removeFalse;
        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short) {
          switch(((Number)val).intValue()) {
          case 0:
            break removeFalse;
          case 1:
            break removeTrue;
          default:
          }
        }else if(val instanceof Long) {
          final long l;
          if((l=(long)val)==0L) {
            break removeFalse;
          }else if(l==1L) {
            break removeTrue;
          }
        }else if(val instanceof Float) {
          switch(Float.floatToRawIntBits((float)val)) {
          case 0:
          case Integer.MIN_VALUE:
            break removeFalse;
          case TypeUtil.FLT_TRUE_BITS:
            break removeTrue;
          }
        }else if(val instanceof Double) {
          final long bits;
          if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE) {
            break removeFalse;
          }else if(bits==TypeUtil.DBL_TRUE_BITS) {
            break removeTrue;
          }
        }else if(val instanceof Character) {
          switch(((Character)val).charValue()) {
          case 0:
            break removeFalse;
          case 1:
            break removeTrue;
          default:
          }
        }
        return false;
      }
      return removeFalse();
    }
    return removeTrue();
  }
  @Override public boolean removeVal(boolean val){
    if(val) {
      return removeTrue();
    }
    return removeFalse();
  }
  @Override public boolean removeVal(int val){
    switch(val) {
    case 0:
      return removeFalse();
    case 1:
      return removeTrue();
    default:
      return false;
    }
  }
  @Override public boolean removeVal(long val){
    if(val==0L) {
      return removeFalse();
    }
    return val==1L && removeTrue();
  }
  @Override public boolean removeVal(float val){
    switch(Float.floatToRawIntBits(val)) {
    case 0:
    case Integer.MIN_VALUE:
      return removeFalse();
    case TypeUtil.FLT_TRUE_BITS:
      return removeTrue();
    default:
      return false;
    }
  }
  @Override public boolean removeVal(double val){
    final long bits;
    if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE) {
      return removeFalse();
    }
    return bits==TypeUtil.DBL_TRUE_BITS && removeTrue();
  }
  @Override public boolean add(Boolean val){
    if(val) {
      return addTrue();
    }else {
      return addFalse();
    }
  }
  @Override public boolean add(boolean val){
    if(val) {
      return addTrue();
    }else {
      return addFalse();
    }
  }

  @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
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
  
  protected static final AbstractBooleanItr EMPTY_ITR=new AbstractBooleanItr(){
      @Override public Object clone(){
        return this;
      }
      @Override public boolean hasNext(){
        return false;
      }
      @Override public boolean nextBoolean() {
          throw new NoSuchElementException();
      }
      @Override public void remove() {
          throw new IllegalStateException();
      }
      @Override public void forEachRemaining(BooleanConsumer action) {
          //nothing to do
      }
      @Override public void forEachRemaining(Consumer<? super Boolean> action) {
          //nothing to do
      }
    };
    protected static final AscendingEmptyView CHECKED_EMPTY_ASCENDING_HEAD =new AscendingEmptyView(){


      @Override
      public OmniNavigableSet.OfBoolean descendingSet(){
        return CHECKED_EMPTY_DESCENDING_TAIL;  
      }

      @Override
      public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
          if(fromInclusive && !fromElement&& !toElement  && !toInclusive) {
              return this;
          }
          throw new IllegalArgumentException("out of bounds");
      }

      @Override
      public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
          if(!toElement&&!inclusive) {
              return this;
          }
          throw new IllegalArgumentException("out of bounds");
      }

      @Override
      public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
          if(inclusive && !fromElement) {
              return this;
          }
          throw new IllegalArgumentException("out of bounds");
      }

      @Override
      public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
          throw new IllegalArgumentException("out of bounds");
      }

      @Override
      public OmniNavigableSet.OfBoolean headSet(boolean toElement){
          throw new IllegalArgumentException("out of bounds");
      }

      @Override
      public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
          if(!fromElement) {
              return this;
          }
          throw new IllegalArgumentException("out of bounds");
      }

      
    };
    protected static final DescendingEmptyView CHECKED_EMPTY_DESCENDING_HEAD =new DescendingEmptyView(){

        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
          return CHECKED_EMPTY_ASCENDING_TAIL;  
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
            if(fromElement && toElement && fromInclusive && !toInclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            if(toElement && !inclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
            if(fromElement && inclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            if(fromElement) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

    };
    protected static final AscendingEmptyView CHECKED_EMPTY_ASCENDING_MIDDLE =new AscendingEmptyView(){

        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
          return CHECKED_EMPTY_DESCENDING_MIDDLE;  
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
            if(fromElement==fromInclusive && toElement^toInclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            if(toElement^inclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
            if(fromElement==inclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            if(fromElement && !toElement) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            if(!toElement) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            if(fromElement) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

    };
    protected static final DescendingEmptyView CHECKED_EMPTY_DESCENDING_MIDDLE =new DescendingEmptyView(){
        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
          return CHECKED_EMPTY_ASCENDING_MIDDLE;  
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
            if(fromElement^fromInclusive && toElement==toInclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            if(toElement == inclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
            if(fromElement^inclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            if(toElement && !fromElement) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            if(toElement) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            if(!fromElement) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

    };
    protected static final AscendingEmptyView CHECKED_EMPTY_ASCENDING_TAIL =new AscendingEmptyView(){
        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
          return CHECKED_EMPTY_DESCENDING_HEAD;  
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
            if(fromElement && toElement && toInclusive && !fromInclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            if(toElement && inclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
            if(fromElement && !inclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            if(toElement) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            throw new IllegalArgumentException("out of bounds");
        }

    };
    protected static final DescendingEmptyView CHECKED_EMPTY_DESCENDING_TAIL =new DescendingEmptyView(){

        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
          return CHECKED_EMPTY_ASCENDING_HEAD;  
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
            if(toInclusive && !fromElement && !toElement && !fromInclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            if(inclusive && !toElement) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
            if(!fromElement && !inclusive) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            if(!toElement) {
                return this;
            }
            throw new IllegalArgumentException("out of bounds");
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            throw new IllegalArgumentException("out of bounds");
        }

    };
    protected static final AscendingEmptyView UNCHECKED_EMPTY_ASCENDING =new AscendingEmptyView(){
        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
            return UNCHECKED_EMPTY_DESCENDING;
        }

    };
    protected static final DescendingEmptyView UNCHECKED_EMPTY_DESCENDING =new DescendingEmptyView(){
        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
            return UNCHECKED_EMPTY_ASCENDING;
        }

    };
    
  protected static abstract class AscendingEmptyView extends EmptyView{
    @Override public BooleanComparator comparator() {
      return Boolean::compare;
    }
  }
  protected static abstract class DescendingEmptyView extends EmptyView{
    @Override public BooleanComparator comparator() {
      return BooleanComparator::descendingCompare;
    }
  }
  protected static abstract class EmptyView implements OmniNavigableSet.OfBoolean,Cloneable{

      @Override
      public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
          return this;
      }
  
      @Override
      public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
          return this;
      }
  
      @Override
      public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
          return this;
      }
  
      @Override
      public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
          return this;
      }
  
      @Override
      public OmniNavigableSet.OfBoolean headSet(boolean toElement){
          return this;
      }
  
      @Override
      public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
          return this;
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        return EMPTY_ITR;
      }
      @Override public OmniIterator.OfBoolean iterator(){
        return EMPTY_ITR;
      }
      @Override public boolean contains(Object val){
        return false;
      }
      @Override public boolean contains(boolean val){
        return false;
      }
      @Override public boolean contains(int val){
        return false;
      }
      @Override public boolean contains(long val){
        return false;
      }
      @Override public boolean contains(float val){
        return false;
      }
      @Override public boolean contains(double val){
        return false;
      }
      @Override public boolean remove(Object val){
        return false;
      }
      @Override public boolean removeVal(boolean val){
        return false;
      }
      @Override public boolean removeVal(int val){
        return false;
      }
      @Override public boolean removeVal(long val){
        return false;
      }
      @Override public boolean removeVal(float val){
        return false;
      }
      @Override public boolean removeVal(double val){
        return false;
      }
      @Override public boolean add(Boolean val){
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public boolean add(boolean val){
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public boolean booleanCeiling(boolean val){
        return false;
      }
      @Override public boolean booleanFloor(boolean val){
        return false;
      }
      @Override public Boolean ceiling(boolean val){
        return null;
      }
      @Override public Boolean floor(boolean val){
        return null;
      }
      @Override public byte byteCeiling(byte val){
        return Byte.MIN_VALUE;
      }
      @Override public byte byteFloor(byte val){
        return Byte.MIN_VALUE;
      }
      @Override public char charCeiling(char val){
        return Character.MIN_VALUE;
      }
      @Override public char charFloor(char val){
        return Character.MIN_VALUE;
      }
      @Override public short shortCeiling(short val){
        return Short.MIN_VALUE;
      }
      @Override public short shortFloor(short val){
        return Short.MIN_VALUE;
      }
      @Override public int intCeiling(int val){
        return Integer.MIN_VALUE;
      }
      @Override public int intFloor(int val){
        return Integer.MIN_VALUE;
      }
      @Override public long longCeiling(long val){
        return Long.MIN_VALUE;
      }
      @Override public long longFloor(long val){
        return Long.MIN_VALUE;
      }
      @Override public float floatCeiling(float val){
        return Float.NaN;
      }
      @Override public float floatFloor(float val){
        return Float.NaN;
      }
      @Override public double doubleCeiling(double val){
        return Double.NaN;
      }
      @Override public double doubleFloor(double val){
        return Double.NaN;
      }
      @Override public Boolean[] toArray() {
          return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
      }
      @Override public boolean[] toBooleanArray() {
          return OmniArray.OfBoolean.DEFAULT_ARR;
      }
      @Override public byte[] toByteArray() {
          return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public char[] toCharArray() {
          return OmniArray.OfChar.DEFAULT_ARR;
      }
      @Override public short[] toShortArray() {
          return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray() {
          return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray() {
          return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray() {
          return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray() {
          return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public void clear() {
          //nothing to do
      }
      @Override public void forEach(BooleanConsumer action) {
          //nothing to do
      }
      @Override public void forEach(Consumer<? super Boolean> action) {
          //nothing to do
      }
      @Override public boolean removeIf(BooleanPredicate filter) {
          return false;
      }
      @Override public boolean removeIf(Predicate<? super Boolean> filter) {
          return false;
      }
      @Override public boolean isEmpty() {
          return true;
      }
      @Override public int size() {
          return 0;
      }
      @Override public <T> T[] toArray(T[] dst) {
          if(dst.length!=0) {
              dst[0]=null;
          }
          return dst;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor) {
          return arrConstructor.apply(0);
      }
      @Override public boolean firstBoolean() {
          throw new NoSuchElementException();
      }
      @Override public boolean lastBoolean() {
          throw new NoSuchElementException();
      }
      @Override public boolean higherBoolean(boolean val){
          return false;
        }
        @Override public boolean lowerBoolean(boolean val){
          return false;
        }
        @Override public Boolean higher(boolean val){
          return null;
        }
        @Override public Boolean lower(boolean val){
          return null;
        }
        @Override public byte higherByte(byte val){
          return Byte.MIN_VALUE;
        }
        @Override public byte lowerByte(byte val){
          return Byte.MIN_VALUE;
        }
        @Override public char higherChar(char val){
          return Character.MIN_VALUE;
        }
        @Override public char lowerChar(char val){
          return Character.MIN_VALUE;
        }
        @Override public short higherShort(short val){
          return Short.MIN_VALUE;
        }
        @Override public short lowerShort(short val){
          return Short.MIN_VALUE;
        }
        @Override public int higherInt(int val){
          return Integer.MIN_VALUE;
        }
        @Override public int lowerInt(int val){
          return Integer.MIN_VALUE;
        }
        @Override public long higherLong(long val){
          return Long.MIN_VALUE;
        }
        @Override public long lowerLong(long val){
          return Long.MIN_VALUE;
        }
        @Override public float higherFloat(float val){
          return Float.NaN;
        }
        @Override public float lowerFloat(float val){
          return Float.NaN;
        }
        @Override public double higherDouble(double val){
          return Double.NaN;
        }
        @Override public double lowerDouble(double val){
          return Double.NaN;
        }

        @Override public boolean pollLastBoolean(){
            return false;
          }
          @Override public boolean pollFirstBoolean(){
            return false;
          }
          @Override public Boolean pollLast(){
            return null;
          }
          @Override public Boolean pollFirst(){
            return null;
          }
          @Override public byte pollLastByte(){
            return Byte.MIN_VALUE;
          }
          @Override public byte pollFirstByte(){
            return Byte.MIN_VALUE;
          }
          @Override public char pollLastChar(){
            return Character.MIN_VALUE;
          }
          @Override public char pollFirstChar(){
            return Character.MIN_VALUE;
          }
          @Override public short pollLastShort(){
            return Short.MIN_VALUE;
          }
          @Override public short pollFirstShort(){
            return Short.MIN_VALUE;
          }
          @Override public int pollLastInt(){
            return Integer.MIN_VALUE;
          }
          @Override public int pollFirstInt(){
            return Integer.MIN_VALUE;
          }
          @Override public long pollLastLong(){
            return Long.MIN_VALUE;
          }
          @Override public long pollFirstLong(){
            return Long.MIN_VALUE;
          }
          @Override public float pollLastFloat(){
            return Float.NaN;
          }
          @Override public float pollFirstFloat(){
            return Float.NaN;
          }
          @Override public double pollLastDouble(){
            return Double.NaN;
          }
          @Override public double pollFirstDouble(){
            return Double.NaN;
          }
        @Override public String toString() {
          return "[]";
        }
        @Override public int hashCode() {
          return 0;
        }
        @Override public boolean equals(Object val) {
          if(val==this) {
            return true;
          }
          if(val instanceof Set) {
            return ((Set<?>)val).isEmpty();
          }
          return false;
        }
    @Override public Object clone() {
      return this;
    }
  }
}
