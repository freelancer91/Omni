package omni.impl.set;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.api.OmniSet;
import omni.function.ByteComparator;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.impl.AbstractByteItr;
import omni.util.OmniArray;

abstract class AbstractByteSet implements OmniNavigableSet.OfByte{  
	

    
    static abstract class ComparatorlessImpl extends AbstractByteSet{
    
        
        
        @Override public ByteComparator comparator(){
          return Byte::compare;
        }
        
        @Override public byte firstByte() {
            return (byte)firstInt();
            
        }
        @Override public short firstShort() {
            return (short)firstInt();
            
        }
        @Override public long firstLong() {
            return firstInt();
            
        }
        @Override public float firstFloat() {
            return firstInt();
            
        }
        @Override public double firstDouble() {
            return firstInt();
            
        }
        
        @Override public byte lastByte() {
            return (byte)lastInt();
            
        }
        @Override public short lastShort() {
            return (short)lastInt();
            
        }
        @Override public long lastLong() {
            return lastInt();
            
        }
        @Override public float lastFloat() {
            return lastInt();
            
        }
        @Override public double lastDouble() {
            return lastInt();
            
        }
        
        
        @Override public Byte pollFirst(){
            final int v;
            if((v=pollFirstInt())==Integer.MIN_VALUE){
              return null;
            }
            return (byte)v;
          }
          @Override public byte pollFirstByte(){
            final int v;
            if((v=pollFirstInt())==Integer.MIN_VALUE){
              return Byte.MIN_VALUE;
            }
            return (byte)v;
          }
          @Override public short pollFirstShort(){
            final int v;
            if((v=pollFirstInt())==Integer.MIN_VALUE){
              return Short.MIN_VALUE;
            }
            return (short)v;
          }
          @Override public long pollFirstLong(){
            final int v;
            if((v=pollFirstInt())==Integer.MIN_VALUE){
              return Long.MIN_VALUE;
            }
            return v;
          }
          @Override public float pollFirstFloat(){
            final int v;
            if((v=pollFirstInt())==Integer.MIN_VALUE){
              return Float.NaN;
            }
            return v;
          }
          @Override public double pollFirstDouble(){
            final int v;
            if((v=pollFirstInt())==Integer.MIN_VALUE){
              return Double.NaN;
            }
            return v;
          }
          
          @Override public Byte pollLast(){
            final int v;
            if((v=pollLastInt())==Integer.MIN_VALUE){
              return null;
            }
            return (byte)v;
          }
          @Override public byte pollLastByte(){
            final int v;
            if((v=pollLastInt())==Integer.MIN_VALUE){
              return Byte.MIN_VALUE;
            }
            return (byte)v;
          }
          @Override public short pollLastShort(){
            final int v;
            if((v=pollLastInt())==Integer.MIN_VALUE){
              return Short.MIN_VALUE;
            }
            return (short)v;
          }
          @Override public long pollLastLong(){
            final int v;
            if((v=pollLastInt())==Integer.MIN_VALUE){
              return Long.MIN_VALUE;
            }
            return v;
          }
          @Override public float pollLastFloat(){
            final int v;
            if((v=pollLastInt())==Integer.MIN_VALUE){
              return Float.NaN;
            }
            return v;
          }
          @Override public double pollLastDouble(){
            final int v;
            if((v=pollLastInt())==Integer.MIN_VALUE){
              return Double.NaN;
            }
            return v;
          }
          
        
    }
    
   
    
    
    
    
    static abstract class EmptyView extends AbstractByteSet implements Cloneable{
        @Override public Object clone() {
            return this;
        }
        
        static final AbstractByteItr EMPTY_ITR=new AbstractByteItr() {
            @Override public Object clone() {
                return this;
            }
            @Override public void remove() {
                throw new IllegalStateException();
            }
            @Override public boolean hasNext() {
                return false;
            }
            @Override public void forEachRemaining(ByteConsumer action) {
                //nothing to do
            }
            @Override public void forEachRemaining(Consumer<? super Byte> action) {
                //nothing to do
            }
            @Override public byte nextByte() {
                throw new NoSuchElementException();
            }
        };
        static abstract class Checked extends EmptyView{
            transient final int after;
            
            Checked(int after) {
                this.after=after;
            }
            
            
            
            static class Ascending extends Checked{
                Ascending(int after) {
                    super(after);
                }
                @Override public ByteComparator comparator() {
                    return Byte::compare;
                }
                @Override public OmniNavigableSet.OfByte descendingSet(){
                    return new Descending(after);
                }
                @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
                    if(fromElement==after) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
                @Override public OmniNavigableSet.OfByte headSet(byte toElement){
                    if(toElement==after-1) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
                @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
                    final int after;
                    if(fromElement==(after=this.after) && toElement==after-1) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
                @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
                    if(after==(inclusive?fromElement:fromElement+1)) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
                @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
                    if(after==(inclusive?toElement+1:toElement)) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
                @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
                    final int after;
                    if((after=this.after)==(fromInclusive?fromElement:fromElement+1) && after==(toInclusive?toElement+1:toElement)) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
            }
            
            
            static class Descending extends Checked{
                Descending(int after) {
                    super(after);
                }
                @Override public OmniNavigableSet.OfByte descendingSet(){
                    return new Ascending(after);
                }
                @Override public ByteComparator comparator() {
                    return ByteComparator::descendingCompare;
                }
                
                
                @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
                    if(fromElement==after-1) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
                @Override public OmniNavigableSet.OfByte headSet(byte toElement){
                    if(toElement==after) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
                @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
                    final int after;
                    if(fromElement==(after=this.after)-1 && toElement==after) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
                @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
                    if(after==(inclusive?fromElement+1:fromElement)) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
                @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
                    if(after==(inclusive?toElement:toElement+1)) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
                @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
                    final int after;
                    if((after=this.after)==(fromInclusive?fromElement+1:fromElement) && after==(toInclusive?toElement:toElement+1)) {
                        return this;
                    }
                    throw new IllegalArgumentException("out of bounds");
                }
            }
        }
        
        static final EmptyView ASCENDING=new EmptyView(){
          @Override public OmniNavigableSet.OfByte descendingSet(){
              return DESCENDING;
          }
          
          @Override public ByteComparator comparator() {
              return Byte::compare;
          }
        };
        static final EmptyView DESCENDING=new EmptyView(){
            @Override public OmniNavigableSet.OfByte descendingSet(){
                return ASCENDING;
            }
            @Override public ByteComparator comparator() {
                return ByteComparator::descendingCompare;
            }
        };
        
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
            return this;
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement){
            return this;
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
            return this;
        }
        
        @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
            return this;
        }
        @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
            return this;
        }
        @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
            return this;
        }
        
        @Override public boolean add(boolean val) {
            throw new IllegalArgumentException("out of bounds");
        }
        @Override public boolean add(byte val) {
            throw new IllegalArgumentException("out of bounds");
        }
        
        @Override public <T> T[] toArray(T[] dst) {
            if(dst.length!=0) {
                dst[0]=null;
            }
            return dst;
        }
        @Override public int size() {
            return 0;
        }
        @Override public boolean isEmpty() {
            return true;
        }
        @Override public void clear() {
            //nothing to do
        }
        @Override public void forEach(ByteConsumer filter) {
            //nothing to do
        }
        @Override public void forEach(Consumer<? super Byte> filter) {
            //nothing to do
        }
        @Override public boolean removeIf(BytePredicate filter) {
            return false;
        }
        @Override public boolean removeIf(Predicate<? super Byte> filter) {
            return false;
        }
        @Override public Byte[] toArray() {
            return OmniArray.OfByte.DEFAULT_BOXED_ARR;
        }
        @Override public byte[] toByteArray() {
            return OmniArray.OfByte.DEFAULT_ARR;
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
        @Override public OmniIterator.OfByte iterator(){
            return EMPTY_ITR;
        }
        @Override public OmniIterator.OfByte descendingIterator(){
            return EMPTY_ITR;
        }
        @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor) {
            return arrConstructor.apply(0);
        }
        @Override public boolean contains(Object val) {
            return false;
        }
        @Override public boolean remove(Object val) {
            return false;
        }
        @Override public boolean contains(boolean val) {
            return false;
        }
        @Override public boolean contains(byte val) {
            return false;
        }
        @Override public boolean contains(char val) {
            return false;
        }
        @Override public boolean contains(int val) {
            return false;
        }
        @Override public boolean contains(long val) {
            return false;
        }
        @Override public boolean contains(float val) {
            return false;
        }
        @Override public boolean contains(double val) {
            return false;
        }
        @Override public boolean removeVal(boolean val) {
            return false;
        }
        @Override public boolean removeVal(byte val) {
            return false;
        }
        @Override public boolean removeVal(char val) {
            return false;
        }
        @Override public boolean removeVal(int val) {
            return false;
        }
        @Override public boolean removeVal(long val) {
            return false;
        }
        @Override public boolean removeVal(float val) {
            return false;
        }
        @Override public boolean removeVal(double val) {
            return false;
        }
        
        @Override public byte firstByte() {
            throw new NoSuchElementException();
        }
        @Override public byte lastByte() {
            throw new NoSuchElementException();
        }
        @Override public byte higherByte(byte val) {
            return Byte.MIN_VALUE;
        }
        @Override public byte lowerByte(byte val) {
            return Byte.MIN_VALUE;
        }
        @Override public byte byteFloor(byte val) {
            return Byte.MIN_VALUE;
        }
        @Override public byte byteCeiling(byte val) {
            return Byte.MIN_VALUE;
        }
        @Override public byte pollFirstByte() {
            return Byte.MIN_VALUE;
        }
        @Override public byte pollLastByte() {
            return Byte.MIN_VALUE;
        }
        
        
        @Override public short higherShort(short val) {
            return Short.MIN_VALUE;
        }
        @Override public short lowerShort(short val) {
            return Short.MIN_VALUE;
        }
        @Override public short shortFloor(short val) {
            return Short.MIN_VALUE;
        }
        @Override public short shortCeiling(short val) {
            return Short.MIN_VALUE;
        }
        @Override public short pollFirstShort() {
            return Short.MIN_VALUE;
        }
        @Override public short pollLastShort() {
            return Short.MIN_VALUE;
        }
        
        @Override public int higherInt(int val) {
            return Integer.MIN_VALUE;
        }
        @Override public int lowerInt(int val) {
            return Integer.MIN_VALUE;
        }
        @Override public int intFloor(int val) {
            return Integer.MIN_VALUE;
        }
        @Override public int intCeiling(int val) {
            return Integer.MIN_VALUE;
        }
        @Override public int pollFirstInt() {
            return Integer.MIN_VALUE;
        }
        @Override public int pollLastInt() {
            return Integer.MIN_VALUE;
        }
        
        @Override public long higherLong(long val) {
            return Long.MIN_VALUE;
        }
        @Override public long lowerLong(long val) {
            return Long.MIN_VALUE;
        }
        @Override public long longFloor(long val) {
            return Long.MIN_VALUE;
        }
        @Override public long longCeiling(long val) {
            return Long.MIN_VALUE;
        }
        @Override public long pollFirstLong() {
            return Long.MIN_VALUE;
        }
        @Override public long pollLastLong() {
            return Long.MIN_VALUE;
        }
        
        @Override public float higherFloat(float val) {
            return Float.NaN;
        }
        @Override public float lowerFloat(float val) {
            return Float.NaN;
        }
        @Override public float floatFloor(float val) {
            return Float.NaN;
        }
        @Override public float floatCeiling(float val) {
            return Float.NaN;
        }
        @Override public float pollFirstFloat() {
            return Float.NaN;
        }
        @Override public float pollLastFloat() {
            return Float.NaN;
        }
        
        @Override public double higherDouble(double val) {
            return Double.NaN;
        }
        @Override public double lowerDouble(double val) {
            return Double.NaN;
        }
        @Override public double doubleFloor(double val) {
            return Double.NaN;
        }
        @Override public double doubleCeiling(double val) {
            return Double.NaN;
        }
        @Override public double pollFirstDouble() {
            return Double.NaN;
        }
        @Override public double pollLastDouble() {
            return Double.NaN;
        }
        
        
        @Override public Byte higher(byte val) {
            return null;
        }
        @Override public Byte lower(byte val) {
            return null;
        }
        @Override public Byte floor(byte val) {
            return null;
        }
        @Override public Byte ceiling(byte val) {
            return null;
        }
        @Override public Byte pollFirst() {
            return null;
        }
        @Override public Byte pollLast() {
            return null;
        }
     }
     
    
   
    
    @Override public boolean add(Byte val) {
        return add((byte)val);
    }
    @Override public boolean equals(Object val){
        if(val==this){
          return true;
        }
        if(val instanceof Set){
          //TODO optimize
          final int size;
          if((size=this.size())==0){
            return ((Set<?>)val).isEmpty();
          }
          final Set<?> set;
          if(size==(set=(Set<?>)val).size()){
            if(val instanceof OmniSet){
              if(val instanceof OmniSet.OfByte){
                return ((OmniSet.OfByte)set).containsAll(this);
              }else if(val instanceof OmniSet.OfRef){
                return ((OmniSet.OfRef<?>)set).containsAll(this);
              }
            }else{
              return set.containsAll(this);
            }
          }
        }
        return false;
      }
    
}
