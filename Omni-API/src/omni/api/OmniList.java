package omni.api;
import java.util.Comparator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;
import omni.function.BooleanComparator;
import omni.function.BooleanPredicate;
import omni.function.ByteComparator;
import omni.function.ByteUnaryOperator;
import omni.function.CharComparator;
import omni.function.CharUnaryOperator;
import omni.function.DoubleComparator;
import omni.function.FloatComparator;
import omni.function.FloatUnaryOperator;
import omni.function.LongComparator;
import omni.function.ShortComparator;
import omni.function.ShortUnaryOperator;
import omni.util.TypeUtil;
public interface OmniList extends OmniCollection{
    int indexOf(boolean val);
    int indexOf(Boolean val);
    int indexOf(byte val);
    int indexOf(Byte val);
    int indexOf(char val);
    int indexOf(Character val);
    int indexOf(double val);
    int indexOf(Double val);
    int indexOf(float val);
    int indexOf(Float val);
    int indexOf(int val);
    int indexOf(Integer val);
    int indexOf(long val);
    int indexOf(Long val);
    int indexOf(Object val);
    int indexOf(short val);
    int indexOf(Short val);
    int lastIndexOf(boolean val);
    int lastIndexOf(Boolean val);
    int lastIndexOf(byte val);
    int lastIndexOf(Byte val);
    int lastIndexOf(char val);
    int lastIndexOf(Character val);
    int lastIndexOf(double val);
    int lastIndexOf(Double val);
    int lastIndexOf(float val);
    int lastIndexOf(Float val);
    int lastIndexOf(int val);
    int lastIndexOf(Integer val);
    int lastIndexOf(long val);
    int lastIndexOf(Long val);
    int lastIndexOf(Object val);
    int lastIndexOf(short val);
    int lastIndexOf(Short val);
    void reverseSort();
    void sort();
    interface OfBoolean extends OfPrimitive,OmniCollection.OfBoolean{
        void add(int index,boolean val);
        void add(int index,Boolean val);
        Boolean get(int index);
        boolean getBoolean(int index);
        default byte getByte(int index){
            return TypeUtil.castToByte(getBoolean(index));
        }
        default char getChar(int index){
            return TypeUtil.castToChar(getBoolean(index));
        }
        default double getDouble(int index){
            return TypeUtil.castToDouble(getBoolean(index));
        }
        default float getFloat(int index){
            return TypeUtil.castToFloat(getBoolean(index));
        }
        default int getInt(int index){
            return TypeUtil.castToByte(getBoolean(index));
        }
        default long getLong(int index){
            return TypeUtil.castToLong(getBoolean(index));
        }
        default short getShort(int index){
            return TypeUtil.castToByte(getBoolean(index));
        }
        @Override
        default int indexOf(byte val){
            return indexOf((int)val);
        }
        @Override
        default int indexOf(char val){
            return indexOf((int)val);
        }
        @Override
        default int indexOf(short val){
            return indexOf((int)val);
        }
        @Override
        default int lastIndexOf(byte val){
            return lastIndexOf((int)val);
        }
        @Override
        default int lastIndexOf(char val){
            return lastIndexOf((int)val);
        }
        @Override
        default int lastIndexOf(short val){
            return lastIndexOf((int)val);
        }
        OmniListIterator.OfBoolean listIterator();
        OmniListIterator.OfBoolean listIterator(int index);
        void put(int index,boolean val);
        default void put(int index,Boolean val){
            put(index,val.booleanValue());
        }
        Boolean remove(int index);
        boolean removeBooleanAt(int index);
        default byte removeByteAt(int index){
            return TypeUtil.castToByte(removeBooleanAt(index));
        }
        default char removeCharAt(int index){
            return TypeUtil.castToChar(removeBooleanAt(index));
        }
        default double removeDoubleAt(int index){
            return TypeUtil.castToDouble(removeBooleanAt(index));
        }
        default float removeFloatAt(int index){
            return TypeUtil.castToFloat(removeBooleanAt(index));
        }
        default int removeIntAt(int index){
            return TypeUtil.castToByte(removeBooleanAt(index));
        }
        default long removeLongAt(int index){
            return TypeUtil.castToLong(removeBooleanAt(index));
        }
        default short removeShortAt(int index){
            return TypeUtil.castToByte(removeBooleanAt(index));
        }
        void replaceAll(BooleanPredicate operator);
        void replaceAll(UnaryOperator<Boolean> operator);
        boolean set(int index,boolean val);
        Boolean set(int index,Boolean val);
        void sort(BooleanComparator sorter);
        void sort(Comparator<? super Boolean> sorter);
        OmniList.OfBoolean subList(int fromIndex,int toIndex);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
    interface OfByte extends OfPrimitive,OmniCollection.OfByte{
        default void add(int index,boolean val){
            add(index,TypeUtil.castToByte(val));
        }
        default void add(int index,Boolean val){
            add(index,TypeUtil.castToByte(val.booleanValue()));
        }
        void add(int index,byte val);
        void add(int index,Byte val);
        Byte get(int index);
        byte getByte(int index);
        default double getDouble(int index){
            return getByte(index);
        }
        default float getFloat(int index){
            return getByte(index);
        }
        default int getInt(int index){
            return getByte(index);
        }
        default long getLong(int index){
            return getByte(index);
        }
        default short getShort(int index){
            return getByte(index);
        }
        @Override
        default int indexOf(short val){
            return indexOf((int)val);
        }
        @Override
        default int lastIndexOf(short val){
            return lastIndexOf(val);
        }
        OmniListIterator.OfByte listIterator();
        OmniListIterator.OfByte listIterator(int index);
        default void put(int index,boolean val){
            put(index,TypeUtil.castToByte(val));
        }
        default void put(int index,Boolean val){
            put(index,TypeUtil.castToByte(val.booleanValue()));
        }
        void put(int index,byte val);
        default void put(int index,Byte val){
            put(index,val.byteValue());
        }
        Byte remove(int index);
        byte removeByteAt(int index);
        default double removeDoubleAt(int index){
            return removeByteAt(index);
        }
        default float removeFloatAt(int index){
            return removeByteAt(index);
        }
        default int removeIntAt(int index){
            return removeByteAt(index);
        }
        default long removeLongAt(int index){
            return removeByteAt(index);
        }
        default short removeShortAt(int index){
            return removeByteAt(index);
        }
        void replaceAll(ByteUnaryOperator operator);
        void replaceAll(UnaryOperator<Byte> operator);
        byte set(int index,byte val);
        Byte set(int index,Byte val);
        void sort(ByteComparator sorter);
        void sort(Comparator<? super Byte> sorter);
        OmniList.OfByte subList(int fromIndex,int toIndex);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
    interface OfChar extends OfPrimitive,OmniCollection.OfChar{
        default void add(int index,boolean val){
            add(index,TypeUtil.castToChar(val));
        }
        default void add(int index,Boolean val){
            add(index,TypeUtil.castToChar(val.booleanValue()));
        }
        void add(int index,char val);
        void add(int index,Character val);
        Character get(int index);
        char getChar(int index);
        default double getDouble(int index){
            return getChar(index);
        }
        default float getFloat(int index){
            return getChar(index);
        }
        default int getInt(int index){
            return getChar(index);
        }
        default long getLong(int index){
            return getChar(index);
        }
        @Override
        default int indexOf(byte val){
            return indexOf((short)val);
        }
        @Override
        default int lastIndexOf(byte val){
            return lastIndexOf((short)val);
        }
        OmniListIterator.OfChar listIterator();
        OmniListIterator.OfChar listIterator(int index);
        default void put(int index,boolean val){
            put(index,TypeUtil.castToChar(val));
        }
        default void put(int index,Boolean val){
            put(index,TypeUtil.castToChar(val.booleanValue()));
        }
        void put(int index,char val);
        default void put(int index,Character val){
            put(index,val.charValue());
        }
        Character remove(int index);
        char removeCharAt(int index);
        default double removeDoubleAt(int index){
            return removeCharAt(index);
        }
        default float removeFloatAt(int index){
            return removeCharAt(index);
        }
        default int removeIntAt(int index){
            return removeCharAt(index);
        }
        default long removeLongAt(int index){
            return removeCharAt(index);
        }
        void replaceAll(CharUnaryOperator operator);
        void replaceAll(UnaryOperator<Character> operator);
        char set(int index,char val);
        Character set(int index,Character val);
        void sort(CharComparator sorter);
        void sort(Comparator<? super Character> sorter);
        OmniList.OfChar subList(int fromIndex,int toIndex);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
    interface OfDouble extends OfPrimitive,OmniCollection.OfDouble{
        default void add(int index,boolean val){
            add(index,TypeUtil.castToDouble(val));
        }
        default void add(int index,Boolean val){
            add(index,TypeUtil.castToDouble(val.booleanValue()));
        }
        default void add(int index,byte val){
            add(index,(double)val);
        }
        default void add(int index,Byte val){
            add(index,val.doubleValue());
        }
        default void add(int index,char val){
            add(index,(double)val);
        }
        default void add(int index,Character val){
            add(index,(double)val.charValue());
        }
        void add(int index,double val);
        void add(int index,Double val);
        default void add(int index,float val){
            add(index,(double)val);
        }
        default void add(int index,Float val){
            add(index,val.doubleValue());
        }
        default void add(int index,int val){
            add(index,(double)val);
        }
        default void add(int index,Integer val){
            add(index,val.doubleValue());
        }
        default void add(int index,long val){
            add(index,(double)val);
        }
        default void add(int index,Long val){
            add(index,val.doubleValue());
        }
        default void add(int index,short val){
            add(index,(double)val);
        }
        default void add(int index,Short val){
            add(index,val.doubleValue());
        }
        Double get(int index);
        double getDouble(int index);
        @Override
        default int indexOf(byte val){
            return indexOf((int)val);
        }
        @Override
        default int indexOf(char val){
            return indexOf((int)val);
        }
        @Override
        default int indexOf(short val){
            return indexOf((int)val);
        }
        @Override
        default int lastIndexOf(byte val){
            return lastIndexOf((int)val);
        }
        @Override
        default int lastIndexOf(char val){
            return lastIndexOf((int)val);
        }
        @Override
        default int lastIndexOf(short val){
            return lastIndexOf((int)val);
        }
        OmniListIterator.OfDouble listIterator();
        OmniListIterator.OfDouble listIterator(int index);
        default void put(int index,boolean val){
            put(index,TypeUtil.castToDouble(val));
        }
        default void put(int index,Boolean val){
            put(index,TypeUtil.castToDouble(val.booleanValue()));
        }
        default void put(int index,byte val){
            put(index,(double)val);
        }
        default void put(int index,Byte val){
            put(index,val.doubleValue());
        }
        default void put(int index,char val){
            put(index,(double)val);
        }
        default void put(int index,Character val){
            put(index,(double)val.charValue());
        }
        void put(int index,double val);
        default void put(int index,Double val){
            put(index,val.doubleValue());
        }
        default void put(int index,float val){
            put(index,(double)val);
        }
        default void put(int index,Float val){
            put(index,val.doubleValue());
        }
        default void put(int index,int val){
            put(index,(double)val);
        }
        default void put(int index,Integer val){
            put(index,val.doubleValue());
        }
        default void put(int index,long val){
            put(index,(double)val);
        }
        default void put(int index,Long val){
            put(index,val.doubleValue());
        }
        default void put(int index,short val){
            put(index,(double)val);
        }
        default void put(int index,Short val){
            put(index,val.doubleValue());
        }
        Double remove(int index);
        double removeDoubleAt(int index);
        void replaceAll(DoubleUnaryOperator operator);
        void replaceAll(UnaryOperator<Double> operator);
        double set(int index,double val);
        Double set(int index,Double val);
        void sort(Comparator<? super Double> sorter);
        void sort(DoubleComparator sorter);
        OmniList.OfDouble subList(int fromIndex,int toIndex);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
    interface OfFloat extends OfPrimitive,OmniCollection.OfFloat{
        default void add(int index,boolean val){
            add(index,TypeUtil.castToFloat(val));
        }
        default void add(int index,Boolean val){
            add(index,TypeUtil.castToFloat(val.booleanValue()));
        }
        default void add(int index,byte val){
            add(index,(float)val);
        }
        default void add(int index,Byte val){
            add(index,val.floatValue());
        }
        default void add(int index,char val){
            add(index,(float)val);
        }
        default void add(int index,Character val){
            add(index,val.charValue());
        }
        void add(int index,float val);
        void add(int index,Float val);
        default void add(int index,int val){
            add(index,(float)val);
        }
        default void add(int index,Integer val){
            add(index,val.floatValue());
        }
        default void add(int index,long val){
            add(index,(float)val);
        }
        default void add(int index,Long val){
            add(index,val.floatValue());
        }
        default void add(int index,short val){
            add(index,(float)val);
        }
        default void add(int index,Short val){
            add(index,val.floatValue());
        }
        Float get(int index);
        default double getDouble(int index){
            return getFloat(index);
        }
        float getFloat(int index);
        @Override
        default int indexOf(byte val){
            return indexOf((short)val);
        }
        @Override
        default int lastIndexOf(byte val){
            return lastIndexOf((short)val);
        }
        OmniListIterator.OfFloat listIterator();
        OmniListIterator.OfFloat listIterator(int index);
        default void put(int index,boolean val){
            put(index,TypeUtil.castToFloat(val));
        }
        default void put(int index,Boolean val){
            put(index,TypeUtil.castToFloat(val.booleanValue()));
        }
        default void put(int index,byte val){
            put(index,(float)val);
        }
        default void put(int index,Byte val){
            put(index,val.floatValue());
        }
        default void put(int index,char val){
            put(index,(float)val);
        }
        default void put(int index,Character val){
            put(index,(float)val.charValue());
        }
        void put(int index,float val);
        default void put(int index,Float val){
            put(index,(float)val);
        }
        default void put(int index,int val){
            put(index,(float)val);
        }
        default void put(int index,Integer val){
            put(index,val.floatValue());
        }
        default void put(int index,long val){
            put(index,(float)val);
        }
        default void put(int index,Long val){
            put(index,val.floatValue());
        }
        default void put(int index,short val){
            put(index,(float)val);
        }
        default void put(int index,Short val){
            put(index,val.floatValue());
        }
        Float remove(int index);
        default double removeDoubleAt(int index){
            return removeFloatAt(index);
        }
        float removeFloatAt(int index);
        void replaceAll(FloatUnaryOperator operator);
        void replaceAll(UnaryOperator<Float> operator);
        float set(int index,float val);
        Float set(int index,Float val);
        void sort(Comparator<? super Float> sorter);
        void sort(FloatComparator sorter);
        OmniList.OfFloat subList(int fromIndex,int toIndex);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
    interface OfInt extends OfPrimitive,OmniCollection.OfInt{
        default void add(int index,boolean val){
            add(index,(int)TypeUtil.castToByte(val));
        }
        default void add(int index,Boolean val){
            add(index,(int)TypeUtil.castToByte(val.booleanValue()));
        }
        default void add(int index,byte val){
            add(index,(int)val);
        }
        default void add(int index,Byte val){
            add(index,val.intValue());
        }
        default void add(int index,char val){
            add(index,(int)val);
        }
        default void add(int index,Character val){
            add(index,(int)val.charValue());
        }
        void add(int index,int val);
        void add(int index,Integer val);
        default void add(int index,short val){
            add(index,(int)val);
        }
        default void add(int index,Short val){
            add(index,val.intValue());
        }
        Integer get(int index);
        default double getDouble(int index){
            return getInt(index);
        }
        default float getFloat(int index){
            return getInt(index);
        }
        int getInt(int index);
        default long getLong(int index){
            return getInt(index);
        }
        @Override
        default int indexOf(byte val){
            return indexOf((int)val);
        }
        @Override
        default int indexOf(char val){
            return indexOf((int)val);
        }
        @Override
        default int indexOf(short val){
            return indexOf((int)val);
        }
        @Override
        default int lastIndexOf(byte val){
            return lastIndexOf((int)val);
        }
        @Override
        default int lastIndexOf(char val){
            return lastIndexOf((int)val);
        }
        @Override
        default int lastIndexOf(short val){
            return lastIndexOf((int)val);
        }
        OmniListIterator.OfInt listIterator();
        OmniListIterator.OfInt listIterator(int index);
        default void put(int index,boolean val){
            put(index,(int)TypeUtil.castToByte(val));
        }
        default void put(int index,Boolean val){
            put(index,(int)TypeUtil.castToByte(val.booleanValue()));
        }
        default void put(int index,byte val){
            put(index,(int)val);
        }
        default void put(int index,Byte val){
            put(index,val.intValue());
        }
        default void put(int index,char val){
            put(index,(int)val);
        }
        default void put(int index,Character val){
            put(index,(int)val.charValue());
        }
        void put(int index,int val);
        default void put(int index,Integer val){
            put(index,val.intValue());
        }
        default void put(int index,short val){
            put(index,(int)val);
        }
        default void put(int index,Short val){
            put(index,val.intValue());
        }
        Integer remove(int index);
        default double removeDoubleAt(int index){
            return removeIntAt(index);
        }
        default float removeFloatAt(int index){
            return removeIntAt(index);
        }
        int removeIntAt(int index);
        default long removeLongAt(int index){
            return removeIntAt(index);
        }
        void replaceAll(IntUnaryOperator operator);
        void replaceAll(UnaryOperator<Integer> operator);
        int set(int index,int val);
        Integer set(int index,Integer val);
        void sort(Comparator<? super Integer> sorter);
        void sort(IntBinaryOperator sorter);
        OmniList.OfInt subList(int fromIndex,int toIndex);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
    interface OfLong extends OfPrimitive,OmniCollection.OfLong{
        default void add(int index,boolean val){
            add(index,TypeUtil.castToLong(val));
        }
        default void add(int index,Boolean val){
            add(index,TypeUtil.castToLong(val.booleanValue()));
        }
        default void add(int index,byte val){
            add(index,(long)val);
        }
        default void add(int index,Byte val){
            add(index,val.longValue());
        }
        default void add(int index,char val){
            add(index,(long)val);
        }
        default void add(int index,Character val){
            add(index,(long)val.charValue());
        }
        default void add(int index,int val){
            add(index,(long)val);
        }
        default void add(int index,Integer val){
            add(index,val.longValue());
        }
        void add(int index,long val);
        void add(int index,Long val);
        default void add(int index,short val){
            add(index,(long)val);
        }
        default void add(int index,Short val){
            add(index,val.longValue());
        }
        Long get(int index);
        default double getDouble(int index){
            return getLong(index);
        }
        default float getFloat(int index){
            return getLong(index);
        }
        long getLong(int index);
        @Override
        default int indexOf(byte val){
            return indexOf((int)val);
        }
        @Override
        default int indexOf(char val){
            return indexOf((int)val);
        }
        @Override
        default int indexOf(short val){
            return indexOf((int)val);
        }
        @Override
        default int lastIndexOf(byte val){
            return lastIndexOf((int)val);
        }
        @Override
        default int lastIndexOf(char val){
            return lastIndexOf((int)val);
        }
        @Override
        default int lastIndexOf(short val){
            return lastIndexOf((int)val);
        }
        OmniListIterator.OfLong listIterator();
        OmniListIterator.OfLong listIterator(int index);
        default void put(int index,boolean val){
            put(index,TypeUtil.castToLong(val));
        }
        default void put(int index,Boolean val){
            put(index,TypeUtil.castToLong(val.booleanValue()));
        }
        default void put(int index,byte val){
            put(index,(long)val);
        }
        default void put(int index,Byte val){
            put(index,val.longValue());
        }
        default void put(int index,char val){
            put(index,(long)val);
        }
        default void put(int index,Character val){
            put(index,(long)val.charValue());
        }
        default void put(int index,int val){
            put(index,(long)val);
        }
        default void put(int index,Integer val){
            put(index,val.longValue());
        }
        void put(int index,long val);
        default void put(int index,Long val){
            put(index,val.longValue());
        }
        default void put(int index,short val){
            put(index,(long)val);
        }
        default void put(int index,Short val){
            put(index,val.longValue());
        }
        Long remove(int index);
        default double removeDoubleAt(int index){
            return removeLongAt(index);
        }
        default float removeFloatAt(int index){
            return removeLongAt(index);
        }
        long removeLongAt(int index);
        void replaceAll(LongUnaryOperator operator);
        void replaceAll(UnaryOperator<Long> operator);
        long set(int index,long val);
        Long set(int index,Long val);
        void sort(Comparator<? super Long> sorter);
        void sort(LongComparator sorter);
        OmniList.OfLong subList(int fromIndex,int toIndex);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
    interface OfPrimitive extends OmniList,OmniCollection.OfPrimitive{
        @Override
        default int indexOf(Boolean val){
            if(val!=null){
                return indexOf(val.booleanValue());
            }
            return -1;
        }
        @Override
        default int indexOf(Byte val){
            if(val!=null){
                return indexOf(val.byteValue());
            }
            return -1;
        }
        @Override
        default int indexOf(Character val){
            if(val!=null){
                return indexOf(val.charValue());
            }
            return -1;
        }
        @Override
        default int indexOf(Double val){
            if(val!=null){
                return indexOf(val.doubleValue());
            }
            return -1;
        }
        @Override
        default int indexOf(Float val){
            if(val!=null){
                return indexOf(val.floatValue());
            }
            return -1;
        }
        @Override
        default int indexOf(Integer val){
            if(val!=null){
                return indexOf(val.intValue());
            }
            return -1;
        }
        @Override
        default int indexOf(Long val){
            if(val!=null){
                return indexOf(val.longValue());
            }
            return -1;
        }
        @Override
        default int indexOf(Short val){
            if(val!=null){
                return indexOf(val.shortValue());
            }
            return -1;
        }
        @Override
        default int lastIndexOf(Boolean val){
            if(val!=null){
                return lastIndexOf(val.booleanValue());
            }
            return -1;
        }
        @Override
        default int lastIndexOf(Byte val){
            if(val!=null){
                return lastIndexOf(val.byteValue());
            }
            return -1;
        }
        @Override
        default int lastIndexOf(Character val){
            if(val!=null){
                return lastIndexOf(val.charValue());
            }
            return -1;
        }
        @Override
        default int lastIndexOf(Double val){
            if(val!=null){
                return lastIndexOf(val.doubleValue());
            }
            return -1;
        }
        @Override
        default int lastIndexOf(Float val){
            if(val!=null){
                return lastIndexOf(val.floatValue());
            }
            return -1;
        }
        @Override
        default int lastIndexOf(Integer val){
            if(val!=null){
                return lastIndexOf(val.intValue());
            }
            return -1;
        }
        @Override
        default int lastIndexOf(Long val){
            if(val!=null){
                return lastIndexOf(val.longValue());
            }
            return -1;
        }
        @Override
        default int lastIndexOf(Short val){
            if(val!=null){
                return lastIndexOf(val.shortValue());
            }
            return -1;
        }
    }
    interface OfRef<E>extends OmniList,OmniCollection.OfRef<E>{
        void add(int index,E val);
        E get(int index);
        OmniListIterator.OfRef<E> listIterator();
        OmniListIterator.OfRef<E> listIterator(int index);
        void put(int index,E val);
        E remove(int index);
        void replaceAll(UnaryOperator<E> operator);
        E set(int index,E val);
        void sort(Comparator<? super E> sorter);
        OmniList.OfRef<E> subList(int fromIndex,int toIndex);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
    interface OfShort extends OfPrimitive,OmniCollection.OfShort{
        default void add(int index,boolean val){
            add(index,(short)TypeUtil.castToByte(val));
        }
        default void add(int index,Boolean val){
            add(index,(short)TypeUtil.castToByte(val.booleanValue()));
        }
        default void add(int index,byte val){
            add(index,(short)val);
        }
        default void add(int index,Byte val){
            add(index,val.shortValue());
        }
        void add(int index,short val);
        void add(int index,Short val);
        Short get(int index);
        default double getDouble(int index){
            return getShort(index);
        }
        default float getFloat(int index){
            return getShort(index);
        }
        default int getInt(int index){
            return getShort(index);
        }
        default long getLong(int index){
            return getShort(index);
        }
        short getShort(int index);
        @Override
        default int indexOf(byte val){
            return indexOf((short)val);
        }
        @Override
        default int lastIndexOf(byte val){
            return lastIndexOf((short)val);
        }
        OmniListIterator.OfShort listIterator();
        OmniListIterator.OfShort listIterator(int index);
        default void put(int index,boolean val){
            put(index,(short)TypeUtil.castToByte(val));
        }
        default void put(int index,Boolean val){
            put(index,(short)TypeUtil.castToByte(val.booleanValue()));
        }
        default void put(int index,byte val){
            put(index,(short)val);
        }
        default void put(int index,Byte val){
            put(index,val.shortValue());
        }
        void put(int index,short val);
        default void put(int index,Short val){
            put(index,val.shortValue());
        }
        Short remove(int index);
        default double removeDoubleAt(int index){
            return removeShortAt(index);
        }
        default float removeFloatAt(int index){
            return removeShortAt(index);
        }
        default int removeIntAt(int index){
            return removeShortAt(index);
        }
        default long removeLongAt(int index){
            return removeShortAt(index);
        }
        short removeShortAt(int index);
        void replaceAll(ShortUnaryOperator operator);
        void replaceAll(UnaryOperator<Short> operator);
        short set(int index,short val);
        Short set(int index,Short val);
        void sort(Comparator<? super Short> sorter);
        void sort(ShortComparator sorter);
        OmniList.OfShort subList(int fromIndex,int toIndex);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
}
