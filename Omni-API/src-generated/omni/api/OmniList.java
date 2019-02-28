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
public abstract interface OmniList extends OmniCollection
{
  public abstract int indexOf(Object val);
  public abstract int indexOf(boolean val);
  public abstract int indexOf(byte val);
  public abstract int indexOf(char val);
  public abstract int indexOf(short val);
  public abstract int indexOf(int val);
  public abstract int indexOf(long val);
  public abstract int indexOf(float val);
  public abstract int indexOf(double val);
  public abstract int indexOf(Boolean val);
  public abstract int indexOf(Byte val);
  public abstract int indexOf(Character val);
  public abstract int indexOf(Short val);
  public abstract int indexOf(Integer val);
  public abstract int indexOf(Long val);
  public abstract int indexOf(Float val);
  public abstract int indexOf(Double val);
  public abstract int lastIndexOf(Object val);
  public abstract int lastIndexOf(boolean val);
  public abstract int lastIndexOf(byte val);
  public abstract int lastIndexOf(char val);
  public abstract int lastIndexOf(short val);
  public abstract int lastIndexOf(int val);
  public abstract int lastIndexOf(long val);
  public abstract int lastIndexOf(float val);
  public abstract int lastIndexOf(double val);
  public abstract int lastIndexOf(Boolean val);
  public abstract int lastIndexOf(Byte val);
  public abstract int lastIndexOf(Character val);
  public abstract int lastIndexOf(Short val);
  public abstract int lastIndexOf(Integer val);
  public abstract int lastIndexOf(Long val);
  public abstract int lastIndexOf(Float val);
  public abstract int lastIndexOf(Double val);
  public abstract void stableAscendingSort();
  public abstract void stableDescendingSort();
  public abstract interface OfPrimitive extends OmniList,OmniCollection.OfPrimitive
  {
     @Override
     public default int indexOf(Boolean val)
     {
       if(val!=null)
       {
         return indexOf(val.
         booleanValue
         ());
       }
       return -1;
     }
     @Override
     public default int indexOf(Byte val)
     {
       if(val!=null)
       {
         return indexOf(val.
         byteValue
         ());
       }
       return -1;
     }
     @Override
     public default int indexOf(Character val)
     {
       if(val!=null)
       {
         return indexOf(val.
         charValue
         ());
       }
       return -1;
     }
     @Override
     public default int indexOf(Short val)
     {
       if(val!=null)
       {
         return indexOf(val.
         shortValue
         ());
       }
       return -1;
     }
     @Override
     public default int indexOf(Integer val)
     {
       if(val!=null)
       {
         return indexOf(val.
         intValue
         ());
       }
       return -1;
     }
     @Override
     public default int indexOf(Long val)
     {
       if(val!=null)
       {
         return indexOf(val.
         longValue
         ());
       }
       return -1;
     }
     @Override
     public default int indexOf(Float val)
     {
       if(val!=null)
       {
         return indexOf(val.
         floatValue
         ());
       }
       return -1;
     }
     @Override
     public default int indexOf(Double val)
     {
       if(val!=null)
       {
         return indexOf(val.
         doubleValue
         ());
       }
       return -1;
     }
     @Override
     public default int lastIndexOf(Boolean val)
     {
       if(val!=null)
       {
         return lastIndexOf(val.
         booleanValue
         ());
       }
       return -1;
     }
     @Override
     public default int lastIndexOf(Byte val)
     {
       if(val!=null)
       {
         return lastIndexOf(val.
         byteValue
         ());
       }
       return -1;
     }
     @Override
     public default int lastIndexOf(Character val)
     {
       if(val!=null)
       {
         return lastIndexOf(val.
         charValue
         ());
       }
       return -1;
     }
     @Override
     public default int lastIndexOf(Short val)
     {
       if(val!=null)
       {
         return lastIndexOf(val.
         shortValue
         ());
       }
       return -1;
     }
     @Override
     public default int lastIndexOf(Integer val)
     {
       if(val!=null)
       {
         return lastIndexOf(val.
         intValue
         ());
       }
       return -1;
     }
     @Override
     public default int lastIndexOf(Long val)
     {
       if(val!=null)
       {
         return lastIndexOf(val.
         longValue
         ());
       }
       return -1;
     }
     @Override
     public default int lastIndexOf(Float val)
     {
       if(val!=null)
       {
         return lastIndexOf(val.
         floatValue
         ());
       }
       return -1;
     }
     @Override
     public default int lastIndexOf(Double val)
     {
       if(val!=null)
       {
         return lastIndexOf(val.
         doubleValue
         ());
       }
       return -1;
     }
  }
    public abstract interface OfBoolean extends OfPrimitive,OmniCollection.OfBoolean
    {
      public abstract Boolean get(int index);
      public abstract boolean getBoolean(int index);
      public abstract void add(int index,boolean val);
      public abstract void add(int index,Boolean val);
      public abstract OmniListIterator.OfBoolean listIterator();
      public abstract OmniListIterator.OfBoolean listIterator(int index);
      public abstract void put(int index,boolean val);
      public default void put(int index,Boolean val)
      {
        put(index,val.booleanValue());
      }
      public abstract Boolean remove(int index);
      public abstract boolean removeBooleanAt(int index);
      public abstract void replaceAll(BooleanPredicate operator);
      public abstract void replaceAll(UnaryOperator<Boolean> operator);
      public abstract boolean set(int index,boolean val);
      public abstract Boolean set(int index,Boolean val);
      public abstract void sort(BooleanComparator sorter);
      public abstract void sort(Comparator<? super Boolean> sorter);
      public abstract OmniList.OfBoolean subList(int fromIndex,int toIndex);
      public default byte getByte(int index){
        return TypeUtil.castToByte(getBoolean(index));
      }
      public default char getChar(int index){
        return TypeUtil.castToChar(getBoolean(index));
      }
      public default double getDouble(int index){
        return TypeUtil.castToDouble(getBoolean(index));
      }
      public default float getFloat(int index){
        return TypeUtil.castToFloat(getBoolean(index));
      }
      public default int getInt(int index){
        return TypeUtil.castToByte(getBoolean(index));
      }
      public default long getLong(int index){
        return TypeUtil.castToLong(getBoolean(index));
      }
      public default short getShort(int index){
        return TypeUtil.castToByte(getBoolean(index));
      }
      public default byte removeByteAt(int index){
        return TypeUtil.castToByte(removeBooleanAt(index));
      }
      public default char removeCharAt(int index){
        return TypeUtil.castToChar(removeBooleanAt(index));
      }
      public default double removeDoubleAt(int index){
        return TypeUtil.castToDouble(removeBooleanAt(index));
      }
      public default float removeFloatAt(int index){
        return TypeUtil.castToFloat(removeBooleanAt(index));
      }
      public default int removeIntAt(int index){
        return TypeUtil.castToByte(removeBooleanAt(index));
      }
      public default long removeLongAt(int index){
        return TypeUtil.castToLong(removeBooleanAt(index));
      }
      public default short removeShortAt(int index){
        return TypeUtil.castToByte(removeBooleanAt(index));
      }
      @Override 
      public default int indexOf(byte val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(byte val){
        return lastIndexOf((int)val);
      }
      @Override 
      public default int indexOf(char val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(char val){
        return lastIndexOf((int)val);
      }
      @Override 
      public default int indexOf(short val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(short val){
        return lastIndexOf((int)val);
      }
    }
    public abstract interface OfByte extends OfPrimitive,OmniCollection.OfByte
    {
      public abstract Byte get(int index);
      public abstract byte getByte(int index);
      public abstract void add(int index,byte val);
      public abstract void add(int index,Byte val);
      public abstract OmniListIterator.OfByte listIterator();
      public abstract OmniListIterator.OfByte listIterator(int index);
      public abstract void put(int index,byte val);
      public default void put(int index,Byte val)
      {
        put(index,val.byteValue());
      }
      public abstract Byte remove(int index);
      public abstract byte removeByteAt(int index);
      public abstract void replaceAll(ByteUnaryOperator operator);
      public abstract void replaceAll(UnaryOperator<Byte> operator);
      public abstract byte set(int index,byte val);
      public abstract Byte set(int index,Byte val);
      public abstract void sort(ByteComparator sorter);
      public abstract void unstableSort(ByteComparator sorter);  
      public abstract void sort(Comparator<? super Byte> sorter);
      public abstract OmniList.OfByte subList(int fromIndex,int toIndex);
      public default void add(int index,boolean val)
      {
        add(index,(byte)TypeUtil.
        castToByte
        (val));
      }
      public default void add(int index,Boolean val)
      {
        add(index,(byte)TypeUtil.
        castToByte
        (val.booleanValue()));
      }
      public default void put(int index,boolean val)
      {
        put(index,(byte)TypeUtil.
        castToByte
        (val));
      }
      public default void put(int index,Boolean val)
      {
        put(index,(byte)TypeUtil.
        castToByte
        (val.booleanValue()));
      }
      public default double getDouble(int index)
      {
        return getByte(index);
      }
      public default double removeDoubleAt(int index)
      {
        return removeByteAt(index);
      }
      public default float getFloat(int index)
      {
        return getByte(index);
      }
      public default float removeFloatAt(int index)
      {
        return removeByteAt(index);
      }
      public default long getLong(int index)
      {
        return getByte(index);
      }
      public default long removeLongAt(int index)
      {
        return removeByteAt(index);
      }
      public default int getInt(int index)
      {
        return getByte(index);
      }
      public default int removeIntAt(int index)
      {
        return removeByteAt(index);
      }
      public default short getShort(int index)
      {
        return getByte(index);
      }
      public default short removeShortAt(int index)
      {
        return removeByteAt(index);
      }
      @Override 
      public default int indexOf(short val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(short val){
        return lastIndexOf((int)val);
      }
    }
    public abstract interface OfChar extends OfPrimitive,OmniCollection.OfChar
    {
      public abstract Character get(int index);
      public abstract char getChar(int index);
      public abstract void add(int index,char val);
      public abstract void add(int index,Character val);
      public abstract OmniListIterator.OfChar listIterator();
      public abstract OmniListIterator.OfChar listIterator(int index);
      public abstract void put(int index,char val);
      public default void put(int index,Character val)
      {
        put(index,val.charValue());
      }
      public abstract Character remove(int index);
      public abstract char removeCharAt(int index);
      public abstract void replaceAll(CharUnaryOperator operator);
      public abstract void replaceAll(UnaryOperator<Character> operator);
      public abstract char set(int index,char val);
      public abstract Character set(int index,Character val);
      public abstract void sort(CharComparator sorter);
      public abstract void unstableSort(CharComparator sorter);  
      public abstract void sort(Comparator<? super Character> sorter);
      public abstract OmniList.OfChar subList(int fromIndex,int toIndex);
      public default void add(int index,boolean val)
      {
        add(index,(char)TypeUtil.
        castToChar
        (val));
      }
      public default void add(int index,Boolean val)
      {
        add(index,(char)TypeUtil.
        castToChar
        (val.booleanValue()));
      }
      public default void put(int index,boolean val)
      {
        put(index,(char)TypeUtil.
        castToChar
        (val));
      }
      public default void put(int index,Boolean val)
      {
        put(index,(char)TypeUtil.
        castToChar
        (val.booleanValue()));
      }
      public default double getDouble(int index)
      {
        return getChar(index);
      }
      public default double removeDoubleAt(int index)
      {
        return removeCharAt(index);
      }
      public default float getFloat(int index)
      {
        return getChar(index);
      }
      public default float removeFloatAt(int index)
      {
        return removeCharAt(index);
      }
      public default long getLong(int index)
      {
        return getChar(index);
      }
      public default long removeLongAt(int index)
      {
        return removeCharAt(index);
      }
      public default int getInt(int index)
      {
        return getChar(index);
      }
      public default int removeIntAt(int index)
      {
        return removeCharAt(index);
      }
      @Override 
      public default int indexOf(byte val){
        return indexOf((short)val);
      }
      @Override  
      public default int lastIndexOf(byte val){
        return lastIndexOf((short)val);
      }
    }
    public abstract interface OfShort extends OfPrimitive,OmniCollection.OfShort
    {
      public abstract Short get(int index);
      public abstract short getShort(int index);
      public abstract void add(int index,short val);
      public abstract void add(int index,Short val);
      public abstract OmniListIterator.OfShort listIterator();
      public abstract OmniListIterator.OfShort listIterator(int index);
      public abstract void put(int index,short val);
      public default void put(int index,Short val)
      {
        put(index,val.shortValue());
      }
      public abstract Short remove(int index);
      public abstract short removeShortAt(int index);
      public abstract void replaceAll(ShortUnaryOperator operator);
      public abstract void replaceAll(UnaryOperator<Short> operator);
      public abstract short set(int index,short val);
      public abstract Short set(int index,Short val);
      public abstract void sort(ShortComparator sorter);
      public abstract void unstableSort(ShortComparator sorter);  
      public abstract void sort(Comparator<? super Short> sorter);
      public abstract OmniList.OfShort subList(int fromIndex,int toIndex);
      public default void add(int index,boolean val)
      {
        add(index,(short)TypeUtil.
        castToByte
        (val));
      }
      public default void add(int index,Boolean val)
      {
        add(index,(short)TypeUtil.
        castToByte
        (val.booleanValue()));
      }
      public default void put(int index,boolean val)
      {
        put(index,(short)TypeUtil.
        castToByte
        (val));
      }
      public default void put(int index,Boolean val)
      {
        put(index,(short)TypeUtil.
        castToByte
        (val.booleanValue()));
      }
      public default double getDouble(int index)
      {
        return getShort(index);
      }
      public default double removeDoubleAt(int index)
      {
        return removeShortAt(index);
      }
      public default float getFloat(int index)
      {
        return getShort(index);
      }
      public default float removeFloatAt(int index)
      {
        return removeShortAt(index);
      }
      public default long getLong(int index)
      {
        return getShort(index);
      }
      public default long removeLongAt(int index)
      {
        return removeShortAt(index);
      }
      public default int getInt(int index)
      {
        return getShort(index);
      }
      public default int removeIntAt(int index)
      {
        return removeShortAt(index);
      }
      public default void add(int index,byte val)
      {
        add(index,(short)val);
      }
      public default void add(int index,Byte val)
      {
        add(index,(short)val.byteValue());
      }
      public default void put(int index,byte val)
      {
        put(index,(short)val);
      }
      public default void put(int index,Byte val)
      {
        put(index,(short)val.byteValue());
      }
      @Override 
      public default int indexOf(byte val){
        return indexOf((short)val);
      }
      @Override  
      public default int lastIndexOf(byte val){
        return lastIndexOf((short)val);
      }
    }
    public abstract interface OfInt extends OfPrimitive,OmniCollection.OfInt
    {
      public abstract Integer get(int index);
      public abstract int getInt(int index);
      public abstract void add(int index,int val);
      public abstract void add(int index,Integer val);
      public abstract OmniListIterator.OfInt listIterator();
      public abstract OmniListIterator.OfInt listIterator(int index);
      public abstract void put(int index,int val);
      public default void put(int index,Integer val)
      {
        put(index,val.intValue());
      }
      public abstract Integer remove(int index);
      public abstract int removeIntAt(int index);
      public abstract void replaceAll(IntUnaryOperator operator);
      public abstract void replaceAll(UnaryOperator<Integer> operator);
      public abstract int set(int index,int val);
      public abstract Integer set(int index,Integer val);
      public abstract void sort(IntBinaryOperator sorter);
      public abstract void unstableSort(IntBinaryOperator sorter);
      public abstract void sort(Comparator<? super Integer> sorter);
      public abstract OmniList.OfInt subList(int fromIndex,int toIndex);
      public default void add(int index,boolean val)
      {
        add(index,(int)TypeUtil.
        castToByte
        (val));
      }
      public default void add(int index,Boolean val)
      {
        add(index,(int)TypeUtil.
        castToByte
        (val.booleanValue()));
      }
      public default void put(int index,boolean val)
      {
        put(index,(int)TypeUtil.
        castToByte
        (val));
      }
      public default void put(int index,Boolean val)
      {
        put(index,(int)TypeUtil.
        castToByte
        (val.booleanValue()));
      }
      public default double getDouble(int index)
      {
        return getInt(index);
      }
      public default double removeDoubleAt(int index)
      {
        return removeIntAt(index);
      }
      public default float getFloat(int index)
      {
        return getInt(index);
      }
      public default float removeFloatAt(int index)
      {
        return removeIntAt(index);
      }
      public default long getLong(int index)
      {
        return getInt(index);
      }
      public default long removeLongAt(int index)
      {
        return removeIntAt(index);
      }
      public default void add(int index,byte val)
      {
        add(index,(int)val);
      }
      public default void add(int index,Byte val)
      {
        add(index,(int)val.byteValue());
      }
      public default void put(int index,byte val)
      {
        put(index,(int)val);
      }
      public default void put(int index,Byte val)
      {
        put(index,(int)val.byteValue());
      }
      public default void add(int index,short val)
      {
        add(index,(int)val);
      }
      public default void add(int index,Short val)
      {
        add(index,(int)val.shortValue());
      }
      public default void put(int index,short val)
      {
        put(index,(int)val);
      }
      public default void put(int index,Short val)
      {
        put(index,(int)val.shortValue());
      }
      @Override 
      public default int indexOf(byte val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(byte val){
        return lastIndexOf((int)val);
      }
      @Override 
      public default int indexOf(char val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(char val){
        return lastIndexOf((int)val);
      }
      @Override 
      public default int indexOf(short val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(short val){
        return lastIndexOf((int)val);
      }
    }
    public abstract interface OfLong extends OfPrimitive,OmniCollection.OfLong
    {
      public abstract Long get(int index);
      public abstract long getLong(int index);
      public abstract void add(int index,long val);
      public abstract void add(int index,Long val);
      public abstract OmniListIterator.OfLong listIterator();
      public abstract OmniListIterator.OfLong listIterator(int index);
      public abstract void put(int index,long val);
      public default void put(int index,Long val)
      {
        put(index,val.longValue());
      }
      public abstract Long remove(int index);
      public abstract long removeLongAt(int index);
      public abstract void replaceAll(LongUnaryOperator operator);
      public abstract void replaceAll(UnaryOperator<Long> operator);
      public abstract long set(int index,long val);
      public abstract Long set(int index,Long val);
      public abstract void sort(LongComparator sorter);
      public abstract void unstableSort(LongComparator sorter);  
      public abstract void sort(Comparator<? super Long> sorter);
      public abstract OmniList.OfLong subList(int fromIndex,int toIndex);
      public default void add(int index,boolean val)
      {
        add(index,(long)TypeUtil.
        castToLong
        (val));
      }
      public default void add(int index,Boolean val)
      {
        add(index,(long)TypeUtil.
        castToLong
        (val.booleanValue()));
      }
      public default void put(int index,boolean val)
      {
        put(index,(long)TypeUtil.
        castToLong
        (val));
      }
      public default void put(int index,Boolean val)
      {
        put(index,(long)TypeUtil.
        castToLong
        (val.booleanValue()));
      }
      public default double getDouble(int index)
      {
        return getLong(index);
      }
      public default double removeDoubleAt(int index)
      {
        return removeLongAt(index);
      }
      public default float getFloat(int index)
      {
        return getLong(index);
      }
      public default float removeFloatAt(int index)
      {
        return removeLongAt(index);
      }
      public default void add(int index,byte val)
      {
        add(index,(long)val);
      }
      public default void add(int index,Byte val)
      {
        add(index,(long)val.byteValue());
      }
      public default void put(int index,byte val)
      {
        put(index,(long)val);
      }
      public default void put(int index,Byte val)
      {
        put(index,(long)val.byteValue());
      }
      public default void add(int index,short val)
      {
        add(index,(long)val);
      }
      public default void add(int index,Short val)
      {
        add(index,(long)val.shortValue());
      }
      public default void put(int index,short val)
      {
        put(index,(long)val);
      }
      public default void put(int index,Short val)
      {
        put(index,(long)val.shortValue());
      }
      public default void add(int index,int val)
      {
        add(index,(long)val);
      }
      public default void add(int index,Integer val)
      {
        add(index,(long)val.intValue());
      }
      public default void put(int index,int val)
      {
        put(index,(long)val);
      }
      public default void put(int index,Integer val)
      {
        put(index,(long)val.intValue());
      }
      @Override 
      public default int indexOf(byte val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(byte val){
        return lastIndexOf((int)val);
      }
      @Override 
      public default int indexOf(char val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(char val){
        return lastIndexOf((int)val);
      }
      @Override 
      public default int indexOf(short val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(short val){
        return lastIndexOf((int)val);
      }
    }
    public abstract interface OfFloat extends OfPrimitive,OmniCollection.OfFloat
    {
      public abstract Float get(int index);
      public abstract float getFloat(int index);
      public abstract void add(int index,float val);
      public abstract void add(int index,Float val);
      public abstract OmniListIterator.OfFloat listIterator();
      public abstract OmniListIterator.OfFloat listIterator(int index);
      public abstract void put(int index,float val);
      public default void put(int index,Float val)
      {
        put(index,val.floatValue());
      }
      public abstract Float remove(int index);
      public abstract float removeFloatAt(int index);
      public abstract void replaceAll(FloatUnaryOperator operator);
      public abstract void replaceAll(UnaryOperator<Float> operator);
      public abstract float set(int index,float val);
      public abstract Float set(int index,Float val);
      public abstract void sort(FloatComparator sorter);
      public abstract void unstableSort(FloatComparator sorter);  
      public abstract void sort(Comparator<? super Float> sorter);
      public abstract OmniList.OfFloat subList(int fromIndex,int toIndex);
      public default void add(int index,boolean val)
      {
        add(index,(float)TypeUtil.
        castToFloat
        (val));
      }
      public default void add(int index,Boolean val)
      {
        add(index,(float)TypeUtil.
        castToFloat
        (val.booleanValue()));
      }
      public default void put(int index,boolean val)
      {
        put(index,(float)TypeUtil.
        castToFloat
        (val));
      }
      public default void put(int index,Boolean val)
      {
        put(index,(float)TypeUtil.
        castToFloat
        (val.booleanValue()));
      }
      public default double getDouble(int index)
      {
        return getFloat(index);
      }
      public default double removeDoubleAt(int index)
      {
        return removeFloatAt(index);
      }
      public default void add(int index,byte val)
      {
        add(index,(float)val);
      }
      public default void add(int index,Byte val)
      {
        add(index,(float)val.byteValue());
      }
      public default void put(int index,byte val)
      {
        put(index,(float)val);
      }
      public default void put(int index,Byte val)
      {
        put(index,(float)val.byteValue());
      }
      public default void add(int index,short val)
      {
        add(index,(float)val);
      }
      public default void add(int index,Short val)
      {
        add(index,(float)val.shortValue());
      }
      public default void put(int index,short val)
      {
        put(index,(float)val);
      }
      public default void put(int index,Short val)
      {
        put(index,(float)val.shortValue());
      }
      public default void add(int index,int val)
      {
        add(index,(float)val);
      }
      public default void add(int index,Integer val)
      {
        add(index,(float)val.intValue());
      }
      public default void put(int index,int val)
      {
        put(index,(float)val);
      }
      public default void put(int index,Integer val)
      {
        put(index,(float)val.intValue());
      }
      public default void add(int index,long val)
      {
        add(index,(float)val);
      }
      public default void add(int index,Long val)
      {
        add(index,(float)val.longValue());
      }
      public default void put(int index,long val)
      {
        put(index,(float)val);
      }
      public default void put(int index,Long val)
      {
        put(index,(float)val.longValue());
      }
      @Override 
      public default int indexOf(byte val){
        return indexOf((short)val);
      }
      @Override  
      public default int lastIndexOf(byte val){
        return lastIndexOf((short)val);
      }
    }
    public abstract interface OfDouble extends OfPrimitive,OmniCollection.OfDouble
    {
      public abstract Double get(int index);
      public abstract double getDouble(int index);
      public abstract void add(int index,double val);
      public abstract void add(int index,Double val);
      public abstract OmniListIterator.OfDouble listIterator();
      public abstract OmniListIterator.OfDouble listIterator(int index);
      public abstract void put(int index,double val);
      public default void put(int index,Double val)
      {
        put(index,val.doubleValue());
      }
      public abstract Double remove(int index);
      public abstract double removeDoubleAt(int index);
      public abstract void replaceAll(DoubleUnaryOperator operator);
      public abstract void replaceAll(UnaryOperator<Double> operator);
      public abstract double set(int index,double val);
      public abstract Double set(int index,Double val);
      public abstract void sort(DoubleComparator sorter);
      public abstract void unstableSort(DoubleComparator sorter);  
      public abstract void sort(Comparator<? super Double> sorter);
      public abstract OmniList.OfDouble subList(int fromIndex,int toIndex);
      public default void add(int index,boolean val)
      {
        add(index,(double)TypeUtil.
        castToDouble
        (val));
      }
      public default void add(int index,Boolean val)
      {
        add(index,(double)TypeUtil.
        castToDouble
        (val.booleanValue()));
      }
      public default void put(int index,boolean val)
      {
        put(index,(double)TypeUtil.
        castToDouble
        (val));
      }
      public default void put(int index,Boolean val)
      {
        put(index,(double)TypeUtil.
        castToDouble
        (val.booleanValue()));
      }
      public default void add(int index,byte val)
      {
        add(index,(double)val);
      }
      public default void add(int index,Byte val)
      {
        add(index,(double)val.byteValue());
      }
      public default void put(int index,byte val)
      {
        put(index,(double)val);
      }
      public default void put(int index,Byte val)
      {
        put(index,(double)val.byteValue());
      }
      public default void add(int index,short val)
      {
        add(index,(double)val);
      }
      public default void add(int index,Short val)
      {
        add(index,(double)val.shortValue());
      }
      public default void put(int index,short val)
      {
        put(index,(double)val);
      }
      public default void put(int index,Short val)
      {
        put(index,(double)val.shortValue());
      }
      public default void add(int index,int val)
      {
        add(index,(double)val);
      }
      public default void add(int index,Integer val)
      {
        add(index,(double)val.intValue());
      }
      public default void put(int index,int val)
      {
        put(index,(double)val);
      }
      public default void put(int index,Integer val)
      {
        put(index,(double)val.intValue());
      }
      public default void add(int index,long val)
      {
        add(index,(double)val);
      }
      public default void add(int index,Long val)
      {
        add(index,(double)val.longValue());
      }
      public default void put(int index,long val)
      {
        put(index,(double)val);
      }
      public default void put(int index,Long val)
      {
        put(index,(double)val.longValue());
      }
      public default void add(int index,float val)
      {
        add(index,(double)val);
      }
      public default void add(int index,Float val)
      {
        add(index,(double)val.floatValue());
      }
      public default void put(int index,float val)
      {
        put(index,(double)val);
      }
      public default void put(int index,Float val)
      {
        put(index,(double)val.floatValue());
      }
      @Override 
      public default int indexOf(byte val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(byte val){
        return lastIndexOf((int)val);
      }
      @Override 
      public default int indexOf(char val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(char val){
        return lastIndexOf((int)val);
      }
      @Override 
      public default int indexOf(short val){
        return indexOf((int)val);
      }
      @Override  
      public default int lastIndexOf(short val){
        return lastIndexOf((int)val);
      }
    }
  public abstract interface OfRef<E> extends OmniList,OmniCollection.OfRef<E>{
    public abstract void add(int index,E val);
    public abstract E get(int index);
    public abstract OmniListIterator.OfRef<E> listIterator();
    public abstract OmniListIterator.OfRef<E> listIterator(int index);
    public abstract void put(int index,E val);
    public abstract E remove(int index);
    public abstract void replaceAll(UnaryOperator<E> operator);
    public abstract E set(int index,E val);
    public abstract void sort(Comparator<? super E> sorter);
    public abstract void unstableAscendingSort();
    public abstract void unstableDescendingSort();
    public abstract OmniList.OfRef<E> subList(int fromIndex,int toIndex);
  }
}
