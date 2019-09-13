package omni.api;
import java.util.SortedSet;
import omni.util.TypeUtil;
import omni.function.BooleanComparator;
import omni.function.ByteComparator;
import omni.function.CharComparator;
import omni.function.ShortComparator;
import omni.function.IntComparator;
import omni.function.LongComparator;
import omni.function.FloatComparator;
import omni.function.DoubleComparator;
public abstract interface OmniSortedSet<E> extends OmniSet<E>,SortedSet<E>{
  OmniSortedSet<E> subSet(E fromElement,E toElement);
  OmniSortedSet<E> headSet(E toElement);
  OmniSortedSet<E> tailSet(E fromElement);
  public abstract interface OfRef<E> extends OmniSortedSet<E>,OmniSet.OfRef<E>{
    OmniSortedSet.OfRef<E> subSet(E fromElement,E toElement);
    OmniSortedSet.OfRef<E> headSet(E toElement);
    OmniSortedSet.OfRef<E> tailSet(E fromElement);
  }
  public abstract interface DoubleOutput<E> extends OmniSortedSet<E>,OmniSet.DoubleOutput<E>{
    OmniSortedSet.DoubleOutput<E> subSet(E fromElement,E toElement);
    OmniSortedSet.DoubleOutput<E> headSet(E toElement);
    OmniSortedSet.DoubleOutput<E> tailSet(E fromElement);
    double firstDouble();
    double lastDouble();
  }
  public abstract interface FloatOutput<E> extends OmniSortedSet.DoubleOutput<E>,OmniSet.FloatOutput<E>{
    OmniSortedSet.FloatOutput<E> subSet(E fromElement,E toElement);
    OmniSortedSet.FloatOutput<E> headSet(E toElement);
    OmniSortedSet.FloatOutput<E> tailSet(E fromElement);
    float firstFloat();
    float lastFloat();
  }
  public abstract interface LongOutput<E> extends OmniSortedSet.FloatOutput<E>,OmniSet.LongOutput<E>{
    OmniSortedSet.LongOutput<E> subSet(E fromElement,E toElement);
    OmniSortedSet.LongOutput<E> headSet(E toElement);
    OmniSortedSet.LongOutput<E> tailSet(E fromElement);
    long firstLong();
    long lastLong();
  }
  public abstract interface IntOutput<E> extends OmniSortedSet.LongOutput<E>,OmniSet.IntOutput<E>{
    OmniSortedSet.IntOutput<E> subSet(E fromElement,E toElement);
    OmniSortedSet.IntOutput<E> headSet(E toElement);
    OmniSortedSet.IntOutput<E> tailSet(E fromElement);
    int firstInt();
    int lastInt();
  }
  public abstract interface ShortOutput<E> extends OmniSortedSet.IntOutput<E>,OmniSet.ShortOutput<E>{
    OmniSortedSet.ShortOutput<E> subSet(E fromElement,E toElement);
    OmniSortedSet.ShortOutput<E> headSet(E toElement);
    OmniSortedSet.ShortOutput<E> tailSet(E fromElement);
    short firstShort();
    short lastShort();
  }
  public abstract interface CharOutput<E> extends OmniSortedSet.IntOutput<E>,OmniSet.CharOutput<E>{
    OmniSortedSet.CharOutput<E> subSet(E fromElement,E toElement);
    OmniSortedSet.CharOutput<E> headSet(E toElement);
    OmniSortedSet.CharOutput<E> tailSet(E fromElement);
    char firstChar();
    char lastChar();
  }
  public abstract interface ByteOutput<E> extends OmniSortedSet.ShortOutput<E>,OmniSet.ByteOutput<E>{
    OmniSortedSet.ByteOutput<E> subSet(E fromElement,E toElement);
    OmniSortedSet.ByteOutput<E> headSet(E toElement);
    OmniSortedSet.ByteOutput<E> tailSet(E fromElement);
    byte firstByte();
    byte lastByte();
  }
  public abstract interface OfBoolean
    extends OmniSortedSet.ByteOutput<Boolean>,OmniSortedSet.CharOutput<Boolean>,OmniSet.OfBoolean{
    BooleanComparator comparator();
    OmniSortedSet.OfBoolean subSet(boolean fromElement,boolean toElement);
    OmniSortedSet.OfBoolean headSet(boolean toElement);
    OmniSortedSet.OfBoolean tailSet(boolean fromElement);
    boolean firstBoolean();
    boolean lastBoolean();
    OmniSortedSet.OfBoolean subSet(Boolean fromElement,Boolean toElement);
    OmniSortedSet.OfBoolean headSet(Boolean toElement);
    OmniSortedSet.OfBoolean tailSet(Boolean fromElement);
    default Boolean first(){
      return firstBoolean();
    }
    default Boolean last(){
      return lastBoolean();
    }
    default double firstDouble(){
      return TypeUtil.castToDouble(firstBoolean());
    }
    default double lastDouble(){
      return TypeUtil.castToDouble(lastBoolean());
    }
    default float firstFloat(){
      return TypeUtil.castToFloat(firstBoolean());
    }
    default float lastFloat(){
      return TypeUtil.castToFloat(lastBoolean());
    }
    default long firstLong(){
      return TypeUtil.castToLong(firstBoolean());
    }
    default long lastLong(){
      return TypeUtil.castToLong(lastBoolean());
    }
    default int firstInt(){
      return TypeUtil.castToByte(firstBoolean());
    }
    default int lastInt(){
      return TypeUtil.castToByte(lastBoolean());
    }
    default short firstShort(){
      return TypeUtil.castToByte(firstBoolean());
    }
    default short lastShort(){
      return TypeUtil.castToByte(lastBoolean());
    }
    default byte firstByte(){
      return TypeUtil.castToByte(firstBoolean());
    }
    default byte lastByte(){
      return TypeUtil.castToByte(lastBoolean());
    }
    default char firstChar(){
      return TypeUtil.castToChar(firstBoolean());
    }
    default char lastChar(){
      return TypeUtil.castToChar(lastBoolean());
    }
  }
  public abstract interface OfByte
    extends OmniSortedSet.ByteOutput<Byte>,OmniSet.OfByte{
    ByteComparator comparator();
    OmniSortedSet.OfByte subSet(byte fromElement,byte toElement);
    OmniSortedSet.OfByte headSet(byte toElement);
    OmniSortedSet.OfByte tailSet(byte fromElement);
    byte firstByte();
    byte lastByte();
    OmniSortedSet.OfByte subSet(Byte fromElement,Byte toElement);
    OmniSortedSet.OfByte headSet(Byte toElement);
    OmniSortedSet.OfByte tailSet(Byte fromElement);
    default Byte first(){
      return firstByte();
    }
    default Byte last(){
      return lastByte();
    }
    default double firstDouble(){
      return firstByte();
    }
    default double lastDouble(){
      return lastByte();
    }
    default float firstFloat(){
      return firstByte();
    }
    default float lastFloat(){
      return lastByte();
    }
    default long firstLong(){
      return firstByte();
    }
    default long lastLong(){
      return lastByte();
    }
    default int firstInt(){
      return firstByte();
    }
    default int lastInt(){
      return lastByte();
    }
    default short firstShort(){
      return firstByte();
    }
    default short lastShort(){
      return lastByte();
    }
  }
  public abstract interface OfChar
    extends OmniSortedSet.CharOutput<Character>,OmniSet.OfChar{
    CharComparator comparator();
    OmniSortedSet.OfChar subSet(char fromElement,char toElement);
    OmniSortedSet.OfChar headSet(char toElement);
    OmniSortedSet.OfChar tailSet(char fromElement);
    char firstChar();
    char lastChar();
    OmniSortedSet.OfChar subSet(Character fromElement,Character toElement);
    OmniSortedSet.OfChar headSet(Character toElement);
    OmniSortedSet.OfChar tailSet(Character fromElement);
    default Character first(){
      return firstChar();
    }
    default Character last(){
      return lastChar();
    }
    default double firstDouble(){
      return firstChar();
    }
    default double lastDouble(){
      return lastChar();
    }
    default float firstFloat(){
      return firstChar();
    }
    default float lastFloat(){
      return lastChar();
    }
    default long firstLong(){
      return firstChar();
    }
    default long lastLong(){
      return lastChar();
    }
    default int firstInt(){
      return firstChar();
    }
    default int lastInt(){
      return lastChar();
    }
  }
  public abstract interface OfShort
    extends OmniSortedSet.ShortOutput<Short>,OmniSet.OfShort{
    ShortComparator comparator();
    OmniSortedSet.OfShort subSet(short fromElement,short toElement);
    OmniSortedSet.OfShort headSet(short toElement);
    OmniSortedSet.OfShort tailSet(short fromElement);
    short firstShort();
    short lastShort();
    OmniSortedSet.OfShort subSet(Short fromElement,Short toElement);
    OmniSortedSet.OfShort headSet(Short toElement);
    OmniSortedSet.OfShort tailSet(Short fromElement);
    default Short first(){
      return firstShort();
    }
    default Short last(){
      return lastShort();
    }
    default double firstDouble(){
      return firstShort();
    }
    default double lastDouble(){
      return lastShort();
    }
    default float firstFloat(){
      return firstShort();
    }
    default float lastFloat(){
      return lastShort();
    }
    default long firstLong(){
      return firstShort();
    }
    default long lastLong(){
      return lastShort();
    }
    default int firstInt(){
      return firstShort();
    }
    default int lastInt(){
      return lastShort();
    }
  }
  public abstract interface OfInt
    extends OmniSortedSet.IntOutput<Integer>,OmniSet.OfInt{
    IntComparator comparator();
    OmniSortedSet.OfInt subSet(int fromElement,int toElement);
    OmniSortedSet.OfInt headSet(int toElement);
    OmniSortedSet.OfInt tailSet(int fromElement);
    int firstInt();
    int lastInt();
    OmniSortedSet.OfInt subSet(Integer fromElement,Integer toElement);
    OmniSortedSet.OfInt headSet(Integer toElement);
    OmniSortedSet.OfInt tailSet(Integer fromElement);
    default Integer first(){
      return firstInt();
    }
    default Integer last(){
      return lastInt();
    }
    default double firstDouble(){
      return firstInt();
    }
    default double lastDouble(){
      return lastInt();
    }
    default float firstFloat(){
      return firstInt();
    }
    default float lastFloat(){
      return lastInt();
    }
    default long firstLong(){
      return firstInt();
    }
    default long lastLong(){
      return lastInt();
    }
  }
  public abstract interface OfLong
    extends OmniSortedSet.LongOutput<Long>,OmniSet.OfLong{
    LongComparator comparator();
    OmniSortedSet.OfLong subSet(long fromElement,long toElement);
    OmniSortedSet.OfLong headSet(long toElement);
    OmniSortedSet.OfLong tailSet(long fromElement);
    long firstLong();
    long lastLong();
    OmniSortedSet.OfLong subSet(Long fromElement,Long toElement);
    OmniSortedSet.OfLong headSet(Long toElement);
    OmniSortedSet.OfLong tailSet(Long fromElement);
    default Long first(){
      return firstLong();
    }
    default Long last(){
      return lastLong();
    }
    default double firstDouble(){
      return firstLong();
    }
    default double lastDouble(){
      return lastLong();
    }
    default float firstFloat(){
      return firstLong();
    }
    default float lastFloat(){
      return lastLong();
    }
  }
  public abstract interface OfFloat
    extends OmniSortedSet.FloatOutput<Float>,OmniSet.OfFloat{
    FloatComparator comparator();
    OmniSortedSet.OfFloat subSet(float fromElement,float toElement);
    OmniSortedSet.OfFloat headSet(float toElement);
    OmniSortedSet.OfFloat tailSet(float fromElement);
    float firstFloat();
    float lastFloat();
    OmniSortedSet.OfFloat subSet(Float fromElement,Float toElement);
    OmniSortedSet.OfFloat headSet(Float toElement);
    OmniSortedSet.OfFloat tailSet(Float fromElement);
    default Float first(){
      return firstFloat();
    }
    default Float last(){
      return lastFloat();
    }
    default double firstDouble(){
      return firstFloat();
    }
    default double lastDouble(){
      return lastFloat();
    }
  }
  public abstract interface OfDouble
    extends OmniSortedSet.DoubleOutput<Double>,OmniSet.OfDouble{
    DoubleComparator comparator();
    OmniSortedSet.OfDouble subSet(double fromElement,double toElement);
    OmniSortedSet.OfDouble headSet(double toElement);
    OmniSortedSet.OfDouble tailSet(double fromElement);
    double firstDouble();
    double lastDouble();
    OmniSortedSet.OfDouble subSet(Double fromElement,Double toElement);
    OmniSortedSet.OfDouble headSet(Double toElement);
    OmniSortedSet.OfDouble tailSet(Double fromElement);
    default Double first(){
      return firstDouble();
    }
    default Double last(){
      return lastDouble();
    }
  }
}
