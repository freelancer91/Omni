package omni.api;
import java.util.NavigableSet;
public abstract interface OmniNavigableSet<E> extends OmniSortedSet<E>,NavigableSet<E>{
  OmniIterator<E> iterator();
  OmniIterator<E> descendingIterator();
  OmniNavigableSet<E> descendingSet();
  OmniNavigableSet<E> subSet(E fromElement,boolean fromInclusive,E toElement,boolean toInclusive);
  OmniNavigableSet<E> headSet(E toElement,boolean inclusive);
  OmniNavigableSet<E> tailSet(E fromElement,boolean inclusive);
  OmniNavigableSet<E> subSet(E fromElement,E toElement);
  OmniNavigableSet<E> headSet(E toElement);
  OmniNavigableSet<E> tailSet(E fromElement);
  public abstract interface OfRef<E> extends OmniNavigableSet<E>,OmniSortedSet.OfRef<E>{
    OmniIterator.OfRef<E> iterator();
    OmniIterator.OfRef<E> descendingIterator();
    OmniNavigableSet.OfRef<E> descendingSet();
    OmniNavigableSet.OfRef<E> subSet(E fromElement,boolean fromInclusive,E toElement,boolean toInclusive);
    OmniNavigableSet.OfRef<E> headSet(E toElement,boolean inclusive);
    OmniNavigableSet.OfRef<E> tailSet(E fromElement,boolean inclusive);
    OmniNavigableSet.OfRef<E> subSet(E fromElement,E toElement);
    OmniNavigableSet.OfRef<E> headSet(E toElement);
    OmniNavigableSet.OfRef<E> tailSet(E fromElement);
  }
  public abstract interface DoubleOutput<E> extends OmniNavigableSet<E>,OmniSet.DoubleOutput<E>{
    OmniIterator.DoubleOutput<E> iterator();
    OmniIterator.DoubleOutput<E> descendingIterator();
    OmniNavigableSet.DoubleOutput<E> descendingSet();
    OmniNavigableSet.DoubleOutput<E> subSet(E fromElement,boolean fromInclusive,E toElement,boolean toInclusive);
    OmniNavigableSet.DoubleOutput<E> headSet(E toElement,boolean inclusive);
    OmniNavigableSet.DoubleOutput<E> tailSet(E fromElement,boolean inclusive);
    OmniNavigableSet.DoubleOutput<E> subSet(E fromElement,E toElement);
    OmniNavigableSet.DoubleOutput<E> headSet(E toElement);
    OmniNavigableSet.DoubleOutput<E> tailSet(E fromElement);
    double pollFirstDouble();
    double pollLastDouble();
    double lowerDouble(double val);
    double higherDouble(double val);
    double doubleCeiling(double val);
    double doubleFloor(double val);
  }
  public abstract interface FloatOutput<E> extends OmniNavigableSet.DoubleOutput<E>,OmniSet.FloatOutput<E>{
    OmniIterator.FloatOutput<E> iterator();
    OmniIterator.FloatOutput<E> descendingIterator();
    OmniNavigableSet.FloatOutput<E> descendingSet();
    OmniNavigableSet.FloatOutput<E> subSet(E fromElement,boolean fromInclusive,E toElement,boolean toInclusive);
    OmniNavigableSet.FloatOutput<E> headSet(E toElement,boolean inclusive);
    OmniNavigableSet.FloatOutput<E> tailSet(E fromElement,boolean inclusive);
    OmniNavigableSet.FloatOutput<E> subSet(E fromElement,E toElement);
    OmniNavigableSet.FloatOutput<E> headSet(E toElement);
    OmniNavigableSet.FloatOutput<E> tailSet(E fromElement);
    float pollFirstFloat();
    float pollLastFloat();
    float lowerFloat(float val);
    float higherFloat(float val);
    float floatCeiling(float val);
    float floatFloor(float val);
  }
  public abstract interface LongOutput<E> extends OmniNavigableSet.FloatOutput<E>,OmniSet.LongOutput<E>{
    OmniIterator.LongOutput<E> iterator();
    OmniIterator.LongOutput<E> descendingIterator();
    OmniNavigableSet.LongOutput<E> descendingSet();
    OmniNavigableSet.LongOutput<E> subSet(E fromElement,boolean fromInclusive,E toElement,boolean toInclusive);
    OmniNavigableSet.LongOutput<E> headSet(E toElement,boolean inclusive);
    OmniNavigableSet.LongOutput<E> tailSet(E fromElement,boolean inclusive);
    OmniNavigableSet.LongOutput<E> subSet(E fromElement,E toElement);
    OmniNavigableSet.LongOutput<E> headSet(E toElement);
    OmniNavigableSet.LongOutput<E> tailSet(E fromElement);
    long pollFirstLong();
    long pollLastLong();
    long lowerLong(long val);
    long higherLong(long val);
    long longCeiling(long val);
    long longFloor(long val);
  }
  public abstract interface IntOutput<E> extends OmniNavigableSet.LongOutput<E>,OmniSet.IntOutput<E>{
    OmniIterator.IntOutput<E> iterator();
    OmniIterator.IntOutput<E> descendingIterator();
    OmniNavigableSet.IntOutput<E> descendingSet();
    OmniNavigableSet.IntOutput<E> subSet(E fromElement,boolean fromInclusive,E toElement,boolean toInclusive);
    OmniNavigableSet.IntOutput<E> headSet(E toElement,boolean inclusive);
    OmniNavigableSet.IntOutput<E> tailSet(E fromElement,boolean inclusive);
    OmniNavigableSet.IntOutput<E> subSet(E fromElement,E toElement);
    OmniNavigableSet.IntOutput<E> headSet(E toElement);
    OmniNavigableSet.IntOutput<E> tailSet(E fromElement);
    int pollFirstInt();
    int pollLastInt();
    int lowerInt(int val);
    int higherInt(int val);
    int intCeiling(int val);
    int intFloor(int val);
  }
  public abstract interface ShortOutput<E> extends OmniNavigableSet.IntOutput<E>,OmniSet.ShortOutput<E>{
    OmniIterator.ShortOutput<E> iterator();
    OmniIterator.ShortOutput<E> descendingIterator();
    OmniNavigableSet.ShortOutput<E> descendingSet();
    OmniNavigableSet.ShortOutput<E> subSet(E fromElement,boolean fromInclusive,E toElement,boolean toInclusive);
    OmniNavigableSet.ShortOutput<E> headSet(E toElement,boolean inclusive);
    OmniNavigableSet.ShortOutput<E> tailSet(E fromElement,boolean inclusive);
    OmniNavigableSet.ShortOutput<E> subSet(E fromElement,E toElement);
    OmniNavigableSet.ShortOutput<E> headSet(E toElement);
    OmniNavigableSet.ShortOutput<E> tailSet(E fromElement);
    short pollFirstShort();
    short pollLastShort();
    short lowerShort(short val);
    short higherShort(short val);
    short shortCeiling(short val);
    short shortFloor(short val);
  }
  public abstract interface CharOutput<E> extends OmniNavigableSet.IntOutput<E>,OmniSet.CharOutput<E>{
    OmniIterator.CharOutput<E> iterator();
    OmniIterator.CharOutput<E> descendingIterator();
    OmniNavigableSet.CharOutput<E> descendingSet();
    OmniNavigableSet.CharOutput<E> subSet(E fromElement,boolean fromInclusive,E toElement,boolean toInclusive);
    OmniNavigableSet.CharOutput<E> headSet(E toElement,boolean inclusive);
    OmniNavigableSet.CharOutput<E> tailSet(E fromElement,boolean inclusive);
    OmniNavigableSet.CharOutput<E> subSet(E fromElement,E toElement);
    OmniNavigableSet.CharOutput<E> headSet(E toElement);
    OmniNavigableSet.CharOutput<E> tailSet(E fromElement);
    char pollFirstChar();
    char pollLastChar();
    char lowerChar(char val);
    char higherChar(char val);
    char charCeiling(char val);
    char charFloor(char val);
  }
  public abstract interface ByteOutput<E> extends OmniNavigableSet.ShortOutput<E>,OmniSet.ByteOutput<E>{
    OmniIterator.ByteOutput<E> iterator();
    OmniIterator.ByteOutput<E> descendingIterator();
    OmniNavigableSet.ByteOutput<E> descendingSet();
    OmniNavigableSet.ByteOutput<E> subSet(E fromElement,boolean fromInclusive,E toElement,boolean toInclusive);
    OmniNavigableSet.ByteOutput<E> headSet(E toElement,boolean inclusive);
    OmniNavigableSet.ByteOutput<E> tailSet(E fromElement,boolean inclusive);
    OmniNavigableSet.ByteOutput<E> subSet(E fromElement,E toElement);
    OmniNavigableSet.ByteOutput<E> headSet(E toElement);
    OmniNavigableSet.ByteOutput<E> tailSet(E fromElement);
    byte pollFirstByte();
    byte pollLastByte();
    byte lowerByte(byte val);
    byte higherByte(byte val);
    byte byteCeiling(byte val);
    byte byteFloor(byte val);
  }
  public abstract interface OfBoolean
    extends OmniNavigableSet.ByteOutput<Boolean>,OmniNavigableSet.CharOutput<Boolean>,OmniSortedSet.OfBoolean{
    OmniIterator.OfBoolean iterator();
    OmniIterator.OfBoolean descendingIterator();
    OmniNavigableSet.OfBoolean descendingSet();
    OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive);
    OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive);
    OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive);
    OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement);
    OmniNavigableSet.OfBoolean headSet(boolean toElement);
    OmniNavigableSet.OfBoolean tailSet(boolean fromElement);
    boolean pollFirstBoolean();
    boolean pollLastBoolean();
    Boolean lower(boolean val);
    Boolean floor(boolean val);
    Boolean ceiling(boolean val);
    Boolean higher(boolean val);
    boolean lowerBoolean(boolean val);
    boolean booleanFloor(boolean val);
    boolean booleanCeiling(boolean val);
    boolean higherBoolean(boolean val);
    default OmniNavigableSet.OfBoolean subSet(Boolean fromElement,boolean fromInclusive,Boolean toElement,boolean toInclusive){
      return this.subSet((boolean)fromElement,fromInclusive,(boolean)toElement,toInclusive);
    }
    default OmniNavigableSet.OfBoolean headSet(Boolean toElement,boolean inclusive){
      return this.headSet((boolean)toElement,inclusive);
    }
    default OmniNavigableSet.OfBoolean tailSet(Boolean fromElement,boolean inclusive){
      return this.tailSet((boolean)fromElement,inclusive);
    }
    default OmniNavigableSet.OfBoolean subSet(Boolean fromElement,Boolean toElement){
      return this.subSet((boolean)fromElement,(boolean)toElement);
    }
    default OmniNavigableSet.OfBoolean headSet(Boolean toElement){
      return this.headSet((boolean)toElement);
    }
    default OmniNavigableSet.OfBoolean tailSet(Boolean fromElement){
      return this.tailSet((boolean)fromElement);
    }
    default Boolean lower(Boolean val){
      return lower((boolean)val);
    }
    default Boolean higher(Boolean val){
      return higher((boolean)val);
    }
    default Boolean floor(Boolean val){
      return floor((boolean)val);
    }
    default Boolean ceiling(Boolean val){
      return ceiling((boolean)val);
    }
  }
  public abstract interface OfByte
    extends OmniNavigableSet.ByteOutput<Byte>,OmniSortedSet.OfByte{
    OmniIterator.OfByte iterator();
    OmniIterator.OfByte descendingIterator();
    OmniNavigableSet.OfByte descendingSet();
    OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive);
    OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive);
    OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive);
    OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement);
    OmniNavigableSet.OfByte headSet(byte toElement);
    OmniNavigableSet.OfByte tailSet(byte fromElement);
    byte pollFirstByte();
    byte pollLastByte();
    Byte lower(byte val);
    Byte floor(byte val);
    Byte ceiling(byte val);
    Byte higher(byte val);
    byte lowerByte(byte val);
    byte byteFloor(byte val);
    byte byteCeiling(byte val);
    byte higherByte(byte val);
    default OmniNavigableSet.OfByte subSet(Byte fromElement,boolean fromInclusive,Byte toElement,boolean toInclusive){
      return this.subSet((byte)fromElement,fromInclusive,(byte)toElement,toInclusive);
    }
    default OmniNavigableSet.OfByte headSet(Byte toElement,boolean inclusive){
      return this.headSet((byte)toElement,inclusive);
    }
    default OmniNavigableSet.OfByte tailSet(Byte fromElement,boolean inclusive){
      return this.tailSet((byte)fromElement,inclusive);
    }
    default OmniNavigableSet.OfByte subSet(Byte fromElement,Byte toElement){
      return this.subSet((byte)fromElement,(byte)toElement);
    }
    default OmniNavigableSet.OfByte headSet(Byte toElement){
      return this.headSet((byte)toElement);
    }
    default OmniNavigableSet.OfByte tailSet(Byte fromElement){
      return this.tailSet((byte)fromElement);
    }
    default Byte lower(Byte val){
      return lower((byte)val);
    }
    default Byte higher(Byte val){
      return higher((byte)val);
    }
    default Byte floor(Byte val){
      return floor((byte)val);
    }
    default Byte ceiling(Byte val){
      return ceiling((byte)val);
    }
  }
  public abstract interface OfChar
    extends OmniNavigableSet.CharOutput<Character>,OmniSortedSet.OfChar{
    OmniIterator.OfChar iterator();
    OmniIterator.OfChar descendingIterator();
    OmniNavigableSet.OfChar descendingSet();
    OmniNavigableSet.OfChar subSet(char fromElement,boolean fromInclusive,char toElement,boolean toInclusive);
    OmniNavigableSet.OfChar headSet(char toElement,boolean inclusive);
    OmniNavigableSet.OfChar tailSet(char fromElement,boolean inclusive);
    OmniNavigableSet.OfChar subSet(char fromElement,char toElement);
    OmniNavigableSet.OfChar headSet(char toElement);
    OmniNavigableSet.OfChar tailSet(char fromElement);
    char pollFirstChar();
    char pollLastChar();
    Character lower(char val);
    Character floor(char val);
    Character ceiling(char val);
    Character higher(char val);
    char lowerChar(char val);
    char charFloor(char val);
    char charCeiling(char val);
    char higherChar(char val);
    default OmniNavigableSet.OfChar subSet(Character fromElement,boolean fromInclusive,Character toElement,boolean toInclusive){
      return this.subSet((char)fromElement,fromInclusive,(char)toElement,toInclusive);
    }
    default OmniNavigableSet.OfChar headSet(Character toElement,boolean inclusive){
      return this.headSet((char)toElement,inclusive);
    }
    default OmniNavigableSet.OfChar tailSet(Character fromElement,boolean inclusive){
      return this.tailSet((char)fromElement,inclusive);
    }
    default OmniNavigableSet.OfChar subSet(Character fromElement,Character toElement){
      return this.subSet((char)fromElement,(char)toElement);
    }
    default OmniNavigableSet.OfChar headSet(Character toElement){
      return this.headSet((char)toElement);
    }
    default OmniNavigableSet.OfChar tailSet(Character fromElement){
      return this.tailSet((char)fromElement);
    }
    default Character lower(Character val){
      return lower((char)val);
    }
    default Character higher(Character val){
      return higher((char)val);
    }
    default Character floor(Character val){
      return floor((char)val);
    }
    default Character ceiling(Character val){
      return ceiling((char)val);
    }
  }
  public abstract interface OfShort
    extends OmniNavigableSet.ShortOutput<Short>,OmniSortedSet.OfShort{
    OmniIterator.OfShort iterator();
    OmniIterator.OfShort descendingIterator();
    OmniNavigableSet.OfShort descendingSet();
    OmniNavigableSet.OfShort subSet(short fromElement,boolean fromInclusive,short toElement,boolean toInclusive);
    OmniNavigableSet.OfShort headSet(short toElement,boolean inclusive);
    OmniNavigableSet.OfShort tailSet(short fromElement,boolean inclusive);
    OmniNavigableSet.OfShort subSet(short fromElement,short toElement);
    OmniNavigableSet.OfShort headSet(short toElement);
    OmniNavigableSet.OfShort tailSet(short fromElement);
    short pollFirstShort();
    short pollLastShort();
    Short lower(short val);
    Short floor(short val);
    Short ceiling(short val);
    Short higher(short val);
    short lowerShort(short val);
    short shortFloor(short val);
    short shortCeiling(short val);
    short higherShort(short val);
    default OmniNavigableSet.OfShort subSet(Short fromElement,boolean fromInclusive,Short toElement,boolean toInclusive){
      return this.subSet((short)fromElement,fromInclusive,(short)toElement,toInclusive);
    }
    default OmniNavigableSet.OfShort headSet(Short toElement,boolean inclusive){
      return this.headSet((short)toElement,inclusive);
    }
    default OmniNavigableSet.OfShort tailSet(Short fromElement,boolean inclusive){
      return this.tailSet((short)fromElement,inclusive);
    }
    default OmniNavigableSet.OfShort subSet(Short fromElement,Short toElement){
      return this.subSet((short)fromElement,(short)toElement);
    }
    default OmniNavigableSet.OfShort headSet(Short toElement){
      return this.headSet((short)toElement);
    }
    default OmniNavigableSet.OfShort tailSet(Short fromElement){
      return this.tailSet((short)fromElement);
    }
    default Short lower(Short val){
      return lower((short)val);
    }
    default Short higher(Short val){
      return higher((short)val);
    }
    default Short floor(Short val){
      return floor((short)val);
    }
    default Short ceiling(Short val){
      return ceiling((short)val);
    }
  }
  public abstract interface OfInt
    extends OmniNavigableSet.IntOutput<Integer>,OmniSortedSet.OfInt{
    OmniIterator.OfInt iterator();
    OmniIterator.OfInt descendingIterator();
    OmniNavigableSet.OfInt descendingSet();
    OmniNavigableSet.OfInt subSet(int fromElement,boolean fromInclusive,int toElement,boolean toInclusive);
    OmniNavigableSet.OfInt headSet(int toElement,boolean inclusive);
    OmniNavigableSet.OfInt tailSet(int fromElement,boolean inclusive);
    OmniNavigableSet.OfInt subSet(int fromElement,int toElement);
    OmniNavigableSet.OfInt headSet(int toElement);
    OmniNavigableSet.OfInt tailSet(int fromElement);
    int pollFirstInt();
    int pollLastInt();
    Integer lower(int val);
    Integer floor(int val);
    Integer ceiling(int val);
    Integer higher(int val);
    int lowerInt(int val);
    int intFloor(int val);
    int intCeiling(int val);
    int higherInt(int val);
    default OmniNavigableSet.OfInt subSet(Integer fromElement,boolean fromInclusive,Integer toElement,boolean toInclusive){
      return this.subSet((int)fromElement,fromInclusive,(int)toElement,toInclusive);
    }
    default OmniNavigableSet.OfInt headSet(Integer toElement,boolean inclusive){
      return this.headSet((int)toElement,inclusive);
    }
    default OmniNavigableSet.OfInt tailSet(Integer fromElement,boolean inclusive){
      return this.tailSet((int)fromElement,inclusive);
    }
    default OmniNavigableSet.OfInt subSet(Integer fromElement,Integer toElement){
      return this.subSet((int)fromElement,(int)toElement);
    }
    default OmniNavigableSet.OfInt headSet(Integer toElement){
      return this.headSet((int)toElement);
    }
    default OmniNavigableSet.OfInt tailSet(Integer fromElement){
      return this.tailSet((int)fromElement);
    }
    default Integer lower(Integer val){
      return lower((int)val);
    }
    default Integer higher(Integer val){
      return higher((int)val);
    }
    default Integer floor(Integer val){
      return floor((int)val);
    }
    default Integer ceiling(Integer val){
      return ceiling((int)val);
    }
  }
  public abstract interface OfLong
    extends OmniNavigableSet.LongOutput<Long>,OmniSortedSet.OfLong{
    OmniIterator.OfLong iterator();
    OmniIterator.OfLong descendingIterator();
    OmniNavigableSet.OfLong descendingSet();
    OmniNavigableSet.OfLong subSet(long fromElement,boolean fromInclusive,long toElement,boolean toInclusive);
    OmniNavigableSet.OfLong headSet(long toElement,boolean inclusive);
    OmniNavigableSet.OfLong tailSet(long fromElement,boolean inclusive);
    OmniNavigableSet.OfLong subSet(long fromElement,long toElement);
    OmniNavigableSet.OfLong headSet(long toElement);
    OmniNavigableSet.OfLong tailSet(long fromElement);
    long pollFirstLong();
    long pollLastLong();
    Long lower(long val);
    Long floor(long val);
    Long ceiling(long val);
    Long higher(long val);
    long lowerLong(long val);
    long longFloor(long val);
    long longCeiling(long val);
    long higherLong(long val);
    default OmniNavigableSet.OfLong subSet(Long fromElement,boolean fromInclusive,Long toElement,boolean toInclusive){
      return this.subSet((long)fromElement,fromInclusive,(long)toElement,toInclusive);
    }
    default OmniNavigableSet.OfLong headSet(Long toElement,boolean inclusive){
      return this.headSet((long)toElement,inclusive);
    }
    default OmniNavigableSet.OfLong tailSet(Long fromElement,boolean inclusive){
      return this.tailSet((long)fromElement,inclusive);
    }
    default OmniNavigableSet.OfLong subSet(Long fromElement,Long toElement){
      return this.subSet((long)fromElement,(long)toElement);
    }
    default OmniNavigableSet.OfLong headSet(Long toElement){
      return this.headSet((long)toElement);
    }
    default OmniNavigableSet.OfLong tailSet(Long fromElement){
      return this.tailSet((long)fromElement);
    }
    default Long lower(Long val){
      return lower((long)val);
    }
    default Long higher(Long val){
      return higher((long)val);
    }
    default Long floor(Long val){
      return floor((long)val);
    }
    default Long ceiling(Long val){
      return ceiling((long)val);
    }
  }
  public abstract interface OfFloat
    extends OmniNavigableSet.FloatOutput<Float>,OmniSortedSet.OfFloat{
    OmniIterator.OfFloat iterator();
    OmniIterator.OfFloat descendingIterator();
    OmniNavigableSet.OfFloat descendingSet();
    OmniNavigableSet.OfFloat subSet(float fromElement,boolean fromInclusive,float toElement,boolean toInclusive);
    OmniNavigableSet.OfFloat headSet(float toElement,boolean inclusive);
    OmniNavigableSet.OfFloat tailSet(float fromElement,boolean inclusive);
    OmniNavigableSet.OfFloat subSet(float fromElement,float toElement);
    OmniNavigableSet.OfFloat headSet(float toElement);
    OmniNavigableSet.OfFloat tailSet(float fromElement);
    float pollFirstFloat();
    float pollLastFloat();
    Float lower(float val);
    Float floor(float val);
    Float ceiling(float val);
    Float higher(float val);
    float lowerFloat(float val);
    float floatFloor(float val);
    float floatCeiling(float val);
    float higherFloat(float val);
    default OmniNavigableSet.OfFloat subSet(Float fromElement,boolean fromInclusive,Float toElement,boolean toInclusive){
      return this.subSet((float)fromElement,fromInclusive,(float)toElement,toInclusive);
    }
    default OmniNavigableSet.OfFloat headSet(Float toElement,boolean inclusive){
      return this.headSet((float)toElement,inclusive);
    }
    default OmniNavigableSet.OfFloat tailSet(Float fromElement,boolean inclusive){
      return this.tailSet((float)fromElement,inclusive);
    }
    default OmniNavigableSet.OfFloat subSet(Float fromElement,Float toElement){
      return this.subSet((float)fromElement,(float)toElement);
    }
    default OmniNavigableSet.OfFloat headSet(Float toElement){
      return this.headSet((float)toElement);
    }
    default OmniNavigableSet.OfFloat tailSet(Float fromElement){
      return this.tailSet((float)fromElement);
    }
    default Float lower(Float val){
      return lower((float)val);
    }
    default Float higher(Float val){
      return higher((float)val);
    }
    default Float floor(Float val){
      return floor((float)val);
    }
    default Float ceiling(Float val){
      return ceiling((float)val);
    }
  }
  public abstract interface OfDouble
    extends OmniNavigableSet.DoubleOutput<Double>,OmniSortedSet.OfDouble{
    OmniIterator.OfDouble iterator();
    OmniIterator.OfDouble descendingIterator();
    OmniNavigableSet.OfDouble descendingSet();
    OmniNavigableSet.OfDouble subSet(double fromElement,boolean fromInclusive,double toElement,boolean toInclusive);
    OmniNavigableSet.OfDouble headSet(double toElement,boolean inclusive);
    OmniNavigableSet.OfDouble tailSet(double fromElement,boolean inclusive);
    OmniNavigableSet.OfDouble subSet(double fromElement,double toElement);
    OmniNavigableSet.OfDouble headSet(double toElement);
    OmniNavigableSet.OfDouble tailSet(double fromElement);
    double pollFirstDouble();
    double pollLastDouble();
    Double lower(double val);
    Double floor(double val);
    Double ceiling(double val);
    Double higher(double val);
    double lowerDouble(double val);
    double doubleFloor(double val);
    double doubleCeiling(double val);
    double higherDouble(double val);
    default OmniNavigableSet.OfDouble subSet(Double fromElement,boolean fromInclusive,Double toElement,boolean toInclusive){
      return this.subSet((double)fromElement,fromInclusive,(double)toElement,toInclusive);
    }
    default OmniNavigableSet.OfDouble headSet(Double toElement,boolean inclusive){
      return this.headSet((double)toElement,inclusive);
    }
    default OmniNavigableSet.OfDouble tailSet(Double fromElement,boolean inclusive){
      return this.tailSet((double)fromElement,inclusive);
    }
    default OmniNavigableSet.OfDouble subSet(Double fromElement,Double toElement){
      return this.subSet((double)fromElement,(double)toElement);
    }
    default OmniNavigableSet.OfDouble headSet(Double toElement){
      return this.headSet((double)toElement);
    }
    default OmniNavigableSet.OfDouble tailSet(Double fromElement){
      return this.tailSet((double)fromElement);
    }
    default Double lower(Double val){
      return lower((double)val);
    }
    default Double higher(Double val){
      return higher((double)val);
    }
    default Double floor(Double val){
      return floor((double)val);
    }
    default Double ceiling(Double val){
      return ceiling((double)val);
    }
  }
}
