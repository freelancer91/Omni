package omni.api;
import java.util.Comparator;
import java.util.function.DoubleUnaryOperator;
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
import omni.function.IntComparator;
import omni.function.LongComparator;
import omni.function.ShortComparator;
import omni.function.ShortUnaryOperator;
import omni.util.TypeUtil;
import java.util.List;
import java.util.Collection;
public abstract interface OmniList<E> extends OmniCollection<E>,List<E>
{
  public abstract Object clone();
  @Override
  default boolean containsAll(Collection<?> that){
    //TODO implement in lower classes and remove this
    return OmniCollection.super.containsAll(that);
  }
  @Override
  default boolean removeAll(Collection<?> that){
    //TODO implement in lower classes and remove this
    return OmniCollection.super.removeAll(that);
  }
  @Override
  default boolean retainAll(Collection<?> that){
    //TODO implement in lower classes and remove this
    return OmniCollection.super.retainAll(that);
  }
  @Override
  default boolean addAll(Collection<? extends E> that){
    //TODO implement in lower classes and remove this
    return OmniCollection.super.addAll(that);
  }
  @SuppressWarnings("unchecked")
  @Override
  default boolean addAll(int index,Collection<? extends E> that){
    //TODO implement in lower classes and remove this
    final var thisItr=this.listIterator(index);
    final int size;
    final Object[] thatArr;
    if((size=(thatArr=that.toArray()).length)!=0){
      var i=0;
      do{
        thisItr.add((E)thatArr[i]);
      }while(++i!=size);
      return true;
    }
    return false;
  }
  public abstract OmniListIterator<E> listIterator();
  public abstract OmniListIterator<E> listIterator(int index);
  public abstract OmniList<E> subList(int fromIndex,int toIndex);
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
  //TODO add the rest of the methods
  public abstract E get(int index);
  public abstract E remove(int index);
  //public abstract E set(int index,E val);
  //public abstract boolean add(int index,E val);
  //public abstract void put(int index,E val);
  //public abstract Object clone();
  public abstract interface OfPrimitive<E> extends OmniList<E>,OmniCollection.OfPrimitive<E>
  {
     public abstract boolean addAll(int index,OmniCollection.OfBoolean that);
     public abstract void add(int index,boolean val);
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
  public abstract interface DoubleOutput<E> extends OmniList<E>,OmniCollection.DoubleOutput<E>
  {
    public abstract OmniListIterator.DoubleOutput<E> listIterator();
    public abstract OmniListIterator.DoubleOutput<E> listIterator(int index);
    public abstract OmniList.DoubleOutput<E> subList(int fromIndex,int toIndex);
    public abstract double getDouble(int index);
    public abstract double removeDoubleAt(int index);
  }
  public abstract interface FloatOutput<E> extends DoubleOutput<E>,OmniCollection.FloatOutput<E>
  {
    public abstract OmniListIterator.FloatOutput<E> listIterator();
    public abstract OmniListIterator.FloatOutput<E> listIterator(int index);
    public abstract OmniList.FloatOutput<E> subList(int fromIndex,int toIndex);
    public abstract float getFloat(int index);
    public abstract float removeFloatAt(int index);
  }
  public abstract interface LongOutput<E> extends FloatOutput<E>,OmniCollection.LongOutput<E>
  {
    public abstract OmniListIterator.LongOutput<E> listIterator();
    public abstract OmniListIterator.LongOutput<E> listIterator(int index);
    public abstract OmniList.LongOutput<E> subList(int fromIndex,int toIndex);
    public abstract long getLong(int index);
    public abstract long removeLongAt(int index);
  }
  public abstract interface IntOutput<E> extends LongOutput<E>,OmniCollection.IntOutput<E>
  {
    public abstract OmniListIterator.IntOutput<E> listIterator();
    public abstract OmniListIterator.IntOutput<E> listIterator(int index);
    public abstract OmniList.IntOutput<E> subList(int fromIndex,int toIndex);
    public abstract int getInt(int index);
    public abstract int removeIntAt(int index);
  }
  public abstract interface ShortOutput<E> extends IntOutput<E>,OmniCollection.ShortOutput<E>
  {
    public abstract OmniListIterator.ShortOutput<E> listIterator();
    public abstract OmniListIterator.ShortOutput<E> listIterator(int index);
    public abstract OmniList.ShortOutput<E> subList(int fromIndex,int toIndex);
    public abstract short getShort(int index);
    public abstract short removeShortAt(int index);
  }
  public abstract interface CharOutput<E> extends IntOutput<E>,OmniCollection.CharOutput<E>
  {
    public abstract OmniListIterator.CharOutput<E> listIterator();
    public abstract OmniListIterator.CharOutput<E> listIterator(int index);
    public abstract OmniList.CharOutput<E> subList(int fromIndex,int toIndex);
    public abstract char getChar(int index);
    public abstract char removeCharAt(int index);
  }
  public abstract interface ByteOutput<E> extends ShortOutput<E>,OmniCollection.ByteOutput<E>
  {
    public abstract OmniListIterator.ByteOutput<E> listIterator();
    public abstract OmniListIterator.ByteOutput<E> listIterator(int index);
    public abstract OmniList.ByteOutput<E> subList(int fromIndex,int toIndex);
    public abstract byte getByte(int index);
    public abstract byte removeByteAt(int index);
  }
  public abstract interface BooleanInput<E> extends OmniList<E>,OmniCollection.BooleanInput<E>
  {
    public abstract boolean addAll(int index,OmniCollection.OfBoolean that);
    public abstract void add(int index,boolean val);
    public abstract void add(int index,Boolean val);
    public abstract void put(int index,boolean val);
    public abstract void put(int index,Boolean val);
    public abstract OmniListIterator.BooleanInput<E> listIterator();
    public abstract OmniListIterator.BooleanInput<E> listIterator(int index);
    public abstract OmniList.BooleanInput<E> subList(int fromIndex,int toIndex);
  }
  public abstract interface ByteInput<E> extends BooleanInput<E>,OmniCollection.ByteInput<E>
  {
    public abstract boolean addAll(int index,OmniCollection.OfByte that);
    public abstract void add(int index,byte val);
    public abstract void add(int index,Byte val);
    public abstract void put(int index,byte val);
    public abstract void put(int index,Byte val);
    public abstract OmniListIterator.ByteInput<E> listIterator();
    public abstract OmniListIterator.ByteInput<E> listIterator(int index);
    public abstract OmniList.ByteInput<E> subList(int fromIndex,int toIndex);
  }
  public abstract interface CharInput<E> extends BooleanInput<E>,OmniCollection.CharInput<E>
  {
    public abstract boolean addAll(int index,OmniCollection.OfChar that);
    public abstract void add(int index,char val);
    public abstract void add(int index,Character val);
    public abstract void put(int index,char val);
    public abstract void put(int index,Character val);
    public abstract OmniListIterator.CharInput<E> listIterator();
    public abstract OmniListIterator.CharInput<E> listIterator(int index);
    public abstract OmniList.CharInput<E> subList(int fromIndex,int toIndex);
  }
  public abstract interface ShortInput<E> extends ByteInput<E>,OmniCollection.ShortInput<E>
  {
    public abstract boolean addAll(int index,OmniCollection.OfShort that);
    public abstract void add(int index,short val);
    public abstract void add(int index,Short val);
    public abstract void put(int index,short val);
    public abstract void put(int index,Short val);
    public abstract OmniListIterator.ShortInput<E> listIterator();
    public abstract OmniListIterator.ShortInput<E> listIterator(int index);
    public abstract OmniList.ShortInput<E> subList(int fromIndex,int toIndex);
  }
  public abstract interface IntInput<E> extends ShortInput<E>,CharInput<E>,OmniCollection.IntInput<E>
  {
    public abstract boolean addAll(int index,OmniCollection.OfInt that);
    public abstract void add(int index,int val);
    public abstract void add(int index,Integer val);
    public abstract void put(int index,int val);
    public abstract void put(int index,Integer val);
    public abstract OmniListIterator.IntInput<E> listIterator();
    public abstract OmniListIterator.IntInput<E> listIterator(int index);
    public abstract OmniList.IntInput<E> subList(int fromIndex,int toIndex);
  }
  public abstract interface LongInput<E> extends IntInput<E>,OmniCollection.LongInput<E>
  {
    public abstract boolean addAll(int index,OmniCollection.OfLong that);
    public abstract void add(int index,long val);
    public abstract void add(int index,Long val);
    public abstract void put(int index,long val);
    public abstract void put(int index,Long val);
    public abstract OmniListIterator.LongInput<E> listIterator();
    public abstract OmniListIterator.LongInput<E> listIterator(int index);
    public abstract OmniList.LongInput<E> subList(int fromIndex,int toIndex);
  }
  public abstract interface FloatInput<E> extends LongInput<E>,OmniCollection.FloatInput<E>
  {
    public abstract boolean addAll(int index,OmniCollection.OfFloat that);
    public abstract void add(int index,float val);
    public abstract void add(int index,Float val);
    public abstract void put(int index,float val);
    public abstract void put(int index,Float val);
    public abstract OmniListIterator.FloatInput<E> listIterator();
    public abstract OmniListIterator.FloatInput<E> listIterator(int index);
    public abstract OmniList.FloatInput<E> subList(int fromIndex,int toIndex);
  }
    public abstract interface OfBoolean extends OfPrimitive<Boolean>,OmniCollection.OfBoolean
    ,BooleanInput<Boolean>,ByteOutput<Boolean>,CharOutput<Boolean>
    {
      public default boolean addAll(int index,OmniCollection.OfRef<? extends Boolean> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final Boolean[] thatArr;
        if((size=(thatArr=that.toArray(Boolean[]::new)).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
          return true;
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfBoolean that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final boolean[] thatArr;
        if((size=(thatArr=that.toBooleanArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public abstract Boolean get(int index);
      public abstract boolean getBoolean(int index);
      @Override public abstract void add(int index,boolean val);
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
    public abstract interface OfByte extends OfPrimitive<Byte>,OmniCollection.OfByte
    ,ByteInput<Byte>,ByteOutput<Byte>
    {
      public default boolean addAll(int index,OmniCollection.OfRef<? extends Byte> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final Byte[] thatArr;
        if((size=(thatArr=that.toArray(Byte[]::new)).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
          return true;
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfBoolean that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final byte[] thatArr;
        if((size=(thatArr=that.toByteArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.ByteOutput<?> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final byte[] thatArr;
        if((size=(thatArr=that.toByteArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfByte that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.ByteOutput<?>)that);
      }
      public abstract Byte get(int index);
      public abstract byte getByte(int index);
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
      public abstract void add(int index,byte val);
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
    public abstract interface OfChar extends OfPrimitive<Character>,OmniCollection.OfChar
    ,CharInput<Character>,CharOutput<Character>
    {
      public default boolean addAll(int index,OmniCollection.OfRef<? extends Character> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final Character[] thatArr;
        if((size=(thatArr=that.toArray(Character[]::new)).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
          return true;
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfBoolean that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final char[] thatArr;
        if((size=(thatArr=that.toCharArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.CharOutput<?> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final char[] thatArr;
        if((size=(thatArr=that.toCharArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfChar that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.CharOutput<?>)that);
      }
      public abstract Character get(int index);
      public abstract char getChar(int index);
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
      public abstract void add(int index,char val);
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
    public abstract interface OfShort extends OfPrimitive<Short>,OmniCollection.OfShort
    ,ShortInput<Short>,ShortOutput<Short>
    {
      public default boolean addAll(int index,OmniCollection.OfRef<? extends Short> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final Short[] thatArr;
        if((size=(thatArr=that.toArray(Short[]::new)).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
          return true;
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfBoolean that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final short[] thatArr;
        if((size=(thatArr=that.toShortArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.ShortOutput<?> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final short[] thatArr;
        if((size=(thatArr=that.toShortArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfByte that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.ShortOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfShort that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.ShortOutput<?>)that);
      }
      public abstract Short get(int index);
      public abstract short getShort(int index);
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
      public abstract void add(int index,short val);
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
    public abstract interface OfInt extends OfPrimitive<Integer>,OmniCollection.OfInt
    ,IntInput<Integer>,IntOutput<Integer>
    {
      public default boolean addAll(int index,OmniCollection.OfRef<? extends Integer> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final Integer[] thatArr;
        if((size=(thatArr=that.toArray(Integer[]::new)).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
          return true;
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfBoolean that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final int[] thatArr;
        if((size=(thatArr=that.toIntArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.IntOutput<?> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final int[] thatArr;
        if((size=(thatArr=that.toIntArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfByte that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.IntOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfShort that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.IntOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfInt that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.IntOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfChar that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.IntOutput<?>)that);
      }
      public abstract Integer get(int index);
      public abstract int getInt(int index);
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
      public abstract void add(int index,int val);
      public abstract void replaceAll(IntUnaryOperator operator);
      public abstract void replaceAll(UnaryOperator<Integer> operator);
      public abstract int set(int index,int val);
      public abstract Integer set(int index,Integer val);
      public abstract void sort(IntComparator sorter);
      public abstract void unstableSort(IntComparator sorter);  
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
      public default void add(int index,char val)
      {
        add(index,(int)val);
      }
      public default void add(int index,Character val)
      {
        add(index,(int)val.charValue());
      }
      public default void put(int index,char val)
      {
        put(index,(int)val);
      }
      public default void put(int index,Character val)
      {
        put(index,(int)val.charValue());
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
    public abstract interface OfLong extends OfPrimitive<Long>,OmniCollection.OfLong
    ,LongInput<Long>,LongOutput<Long>
    {
      public default boolean addAll(int index,OmniCollection.OfRef<? extends Long> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final Long[] thatArr;
        if((size=(thatArr=that.toArray(Long[]::new)).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
          return true;
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfBoolean that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final long[] thatArr;
        if((size=(thatArr=that.toLongArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.LongOutput<?> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final long[] thatArr;
        if((size=(thatArr=that.toLongArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfByte that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.LongOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfShort that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.LongOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfInt that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.LongOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfLong that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.LongOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfChar that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.LongOutput<?>)that);
      }
      public abstract Long get(int index);
      public abstract long getLong(int index);
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
      public abstract void add(int index,long val);
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
      public default void add(int index,char val)
      {
        add(index,(long)val);
      }
      public default void add(int index,Character val)
      {
        add(index,(long)val.charValue());
      }
      public default void put(int index,char val)
      {
        put(index,(long)val);
      }
      public default void put(int index,Character val)
      {
        put(index,(long)val.charValue());
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
    public abstract interface OfFloat extends OfPrimitive<Float>,OmniCollection.OfFloat
    ,FloatInput<Float>,FloatOutput<Float>
    {
      public default boolean addAll(int index,OmniCollection.OfRef<? extends Float> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final Float[] thatArr;
        if((size=(thatArr=that.toArray(Float[]::new)).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
          return true;
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfBoolean that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final float[] thatArr;
        if((size=(thatArr=that.toFloatArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.FloatOutput<?> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final float[] thatArr;
        if((size=(thatArr=that.toFloatArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfByte that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.FloatOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfShort that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.FloatOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfInt that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.FloatOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfLong that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.FloatOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfFloat that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.FloatOutput<?>)that);
      }       
      public default boolean addAll(int index,OmniCollection.OfChar that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.FloatOutput<?>)that);
      }
      public abstract Float get(int index);
      public abstract float getFloat(int index);
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
      public abstract void add(int index,float val);
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
      public default void add(int index,char val)
      {
        add(index,(float)val);
      }
      public default void add(int index,Character val)
      {
        add(index,(float)val.charValue());
      }
      public default void put(int index,char val)
      {
        put(index,(float)val);
      }
      public default void put(int index,Character val)
      {
        put(index,(float)val.charValue());
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
    public abstract interface OfDouble extends OfPrimitive<Double>,OmniCollection.OfDouble
    ,FloatInput<Double>,DoubleOutput<Double>
    {
      public default boolean addAll(int index,OmniCollection.OfRef<? extends Double> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final Double[] thatArr;
        if((size=(thatArr=that.toArray(Double[]::new)).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
          return true;
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfBoolean that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final double[] thatArr;
        if((size=(thatArr=that.toDoubleArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.DoubleOutput<?> that){
        //TODO implement in lower classes and remove this
        final var thisItr=this.listIterator(index);
        final int size;
        final double[] thatArr;
        if((size=(thatArr=that.toDoubleArray()).length)!=0){
          var i=0;
          do{
            thisItr.add(thatArr[i]);
          }while(++i!=size);
        }
        return false;
      }
      public default boolean addAll(int index,OmniCollection.OfByte that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.DoubleOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfShort that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.DoubleOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfInt that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.DoubleOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfLong that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.DoubleOutput<?>)that);
      }
      public default boolean addAll(int index,OmniCollection.OfFloat that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.DoubleOutput<?>)that);
      }       
      public default boolean addAll(int index,OmniCollection.OfDouble that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.DoubleOutput<?>)that);
      }  
      public default boolean addAll(int index,OmniCollection.OfChar that){
        //TODO implement in lower classes and remove this
        return addAll(index,(OmniCollection.DoubleOutput<?>)that);
      }
      public abstract Double get(int index);
      public abstract double getDouble(int index);
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
      public abstract void add(int index,double val);
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
      public default void add(int index,char val)
      {
        add(index,(double)val);
      }
      public default void add(int index,Character val)
      {
        add(index,(double)val.charValue());
      }
      public default void put(int index,char val)
      {
        put(index,(double)val);
      }
      public default void put(int index,Character val)
      {
        put(index,(double)val.charValue());
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
  public abstract interface OfRef<E> extends OmniList<E>,OmniCollection.OfRef<E>{
    @SuppressWarnings("unchecked")
    public default boolean addAll(int index,OmniCollection.OfRef<? extends E> that){
    //TODO implement in lower classes and remove this
      final var thisItr=this.listIterator(index);
      final int size;
      final Object[] thatArr;
      if((size=(thatArr=that.toArray()).length)!=0){
        var i=0;
        do{
          thisItr.add((E)thatArr[i]);
        }while(++i!=size);
        return true;
      }
      return false;
    }
    public abstract void add(int index,E val);
    public abstract E get(int index);
    public abstract OmniListIterator.OfRef<E> listIterator();
    public abstract OmniListIterator.OfRef<E> listIterator(int index);
    public abstract void put(int index,E val);
    public abstract E remove(int index);
    public abstract void replaceAll(UnaryOperator<E> operator);
    public abstract E set(int index,E val);
    public abstract void sort(Comparator<? super E> sorter);
    public abstract void unstableSort(Comparator<? super E> sorter);
    public abstract void unstableAscendingSort();
    public abstract void unstableDescendingSort();
    public abstract OmniList.OfRef<E> subList(int fromIndex,int toIndex);
  }
}
