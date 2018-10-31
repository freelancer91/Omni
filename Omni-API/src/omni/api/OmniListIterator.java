package omni.api;
import java.util.ListIterator;
import omni.util.TypeUtil;
public interface OmniListIterator<E>extends OmniIterator<E>,ListIterator<E>{
  @Override boolean hasPrevious();
  @Override int nextIndex();
  @Override int previousIndex();
  interface OfBoolean extends OmniListIterator<Boolean>,OmniIterator.OfBoolean{
    void add(boolean val);
    @Override default void add(Boolean val){
      add(val.booleanValue());
    }
    @Override default Boolean next(){
      return nextBoolean();
    }
    @Override default Boolean previous(){
      return previousBoolean();
    }
    boolean previousBoolean();
    default byte previousByte(){
      return TypeUtil.castToByte(previousBoolean());
    }
    default char previousChar(){
      return TypeUtil.castToChar(previousBoolean());
    }
    default double previousDouble(){
      return TypeUtil.castToDouble(previousBoolean());
    }
    default float previousFloat(){
      return TypeUtil.castToFloat(previousBoolean());
    }
    default int previousInt(){
      return TypeUtil.castToByte(previousBoolean());
    }
    default long previousLong(){
      return TypeUtil.castToLong(previousBoolean());
    }
    default short previousShort(){
      return TypeUtil.castToByte(previousBoolean());
    }
    void set(boolean val);
    @Override default void set(Boolean val){
      set(val.booleanValue());
    }
  }
  interface OfByte extends OmniListIterator<Byte>,OmniIterator.OfByte{
    default void add(boolean val){
      add(TypeUtil.castToByte(val));
    }
    default void add(Boolean val){
      add(TypeUtil.castToByte(val.booleanValue()));
    }
    void add(byte val);
    @Override default void add(Byte val){
      add(val.byteValue());
    }
    @Override default Byte next(){
      return nextByte();
    }
    @Override default Byte previous(){
      return previousByte();
    }
    byte previousByte();
    default double previousDouble(){
      return previousByte();
    }
    default float previousFloat(){
      return previousByte();
    }
    default int previousInt(){
      return previousByte();
    }
    default long previousLong(){
      return previousByte();
    }
    default short previousShort(){
      return previousByte();
    }
    default void set(boolean val){
      set(TypeUtil.castToByte(val));
    }
    default void set(Boolean val){
      set(TypeUtil.castToByte(val.booleanValue()));
    }
    void set(byte val);
    @Override default void set(Byte val){
      set(val.byteValue());
    }
  }
  interface OfChar extends OmniListIterator<Character>,OmniIterator.OfChar{
    default void add(boolean val){
      add(TypeUtil.castToChar(val));
    }
    default void add(Boolean val){
      add(TypeUtil.castToChar(val.booleanValue()));
    }
    void add(char val);
    @Override default void add(Character val){
      add(val.charValue());
    }
    @Override default Character next(){
      return nextChar();
    }
    @Override default Character previous(){
      return previousChar();
    }
    char previousChar();
    default double previousDouble(){
      return previousChar();
    }
    default float previousFloat(){
      return previousChar();
    }
    default int previousInt(){
      return previousChar();
    }
    default long previousLong(){
      return previousChar();
    }
    default void set(boolean val){
      set(TypeUtil.castToChar(val));
    }
    default void set(Boolean val){
      set(TypeUtil.castToChar(val.booleanValue()));
    }
    void set(char val);
    @Override default void set(Character val){
      set(val.charValue());
    }
  }
  interface OfDouble extends OmniListIterator<Double>,OmniIterator.OfDouble{
    default void add(boolean val){
      add(TypeUtil.castToDouble(val));
    }
    default void add(Boolean val){
      add(TypeUtil.castToDouble(val.booleanValue()));
    }
    default void add(byte val){
      add((double)val);
    }
    default void add(Byte val){
      add(val.doubleValue());
    }
    default void add(char val){
      add((double)val);
    }
    default void add(Character val){
      add((double)val.charValue());
    }
    void add(double val);
    @Override default void add(Double val){
      add(val.doubleValue());
    }
    default void add(float val){
      add((double)val);
    }
    default void add(Float val){
      add(val.doubleValue());
    }
    default void add(int val){
      add((double)val);
    }
    default void add(Integer val){
      add(val.doubleValue());
    }
    default void add(long val){
      add((double)val);
    }
    default void add(Long val){
      add(val.doubleValue());
    }
    default void add(short val){
      add((double)val);
    }
    default void add(Short val){
      add(val.doubleValue());
    }
    @Override default Double next(){
      return nextDouble();
    }
    @Override default Double previous(){
      return previousDouble();
    }
    double previousDouble();
    default void set(boolean val){
      set(TypeUtil.castToDouble(val));
    }
    default void set(Boolean val){
      set(TypeUtil.castToDouble(val.booleanValue()));
    }
    default void set(byte val){
      set((double)val);
    }
    default void set(Byte val){
      set(val.doubleValue());
    }
    default void set(char val){
      set((double)val);
    }
    default void set(Character val){
      set((double)val.charValue());
    }
    void set(double val);
    @Override default void set(Double val){
      set(val.doubleValue());
    }
    default void set(float val){
      set((double)val);
    }
    default void set(Float val){
      set(val.doubleValue());
    }
    default void set(int val){
      set((double)val);
    }
    default void set(Integer val){
      set(val.doubleValue());
    }
    default void set(long val){
      set((double)val);
    }
    default void set(Long val){
      set(val.doubleValue());
    }
    default void set(short val){
      set((double)val);
    }
    default void set(Short val){
      set(val.doubleValue());
    }
  }
  interface OfFloat extends OmniListIterator<Float>,OmniIterator.OfFloat{
    default void add(boolean val){
      add(TypeUtil.castToFloat(val));
    }
    default void add(Boolean val){
      add(TypeUtil.castToFloat(val.booleanValue()));
    }
    default void add(byte val){
      add((float)val);
    }
    default void add(Byte val){
      add(val.floatValue());
    }
    default void add(char val){
      add((float)val);
    }
    default void add(Character val){
      add((float)val.charValue());
    }
    void add(float val);
    @Override default void add(Float val){
      add(val.floatValue());
    }
    default void add(int val){
      add((float)val);
    }
    default void add(Integer val){
      add(val.floatValue());
    }
    default void add(long val){
      add((float)val);
    }
    default void add(Long val){
      add(val.floatValue());
    }
    default void add(short val){
      add((float)val);
    }
    default void add(Short val){
      add(val.floatValue());
    }
    @Override default Float next(){
      return nextFloat();
    }
    @Override default Float previous(){
      return previousFloat();
    }
    default double previousDouble(){
      return previousFloat();
    }
    float previousFloat();
    default void set(boolean val){
      set(TypeUtil.castToFloat(val));
    }
    default void set(Boolean val){
      set(TypeUtil.castToFloat(val.booleanValue()));
    }
    default void set(byte val){
      set((float)val);
    }
    default void set(Byte val){
      set(val.floatValue());
    }
    default void set(char val){
      set((float)val);
    }
    default void set(Character val){
      set((float)val.charValue());
    }
    void set(float val);
    @Override default void set(Float val){
      set(val.floatValue());
    }
    default void set(int val){
      set((float)val);
    }
    default void set(Integer val){
      set(val.floatValue());
    }
    default void set(long val){
      set((float)val);
    }
    default void set(Long val){
      set(val.floatValue());
    }
    default void set(short val){
      set((float)val);
    }
    default void set(Short val){
      set(val.floatValue());
    }
  }
  interface OfInt extends OmniListIterator<Integer>,OmniIterator.OfInt{
    default void add(boolean val){
      add((int)TypeUtil.castToByte(val));
    }
    default void add(Boolean val){
      add((int)TypeUtil.castToByte(val.booleanValue()));
    }
    default void add(byte val){
      add((int)val);
    }
    default void add(Byte val){
      add(val.intValue());
    }
    default void add(char val){
      add((int)val);
    }
    default void add(Character val){
      add((int)val.charValue());
    }
    void add(int val);
    @Override default void add(Integer val){
      add(val.intValue());
    }
    default void add(short val){
      add((int)val);
    }
    default void add(Short val){
      add(val.intValue());
    }
    @Override default Integer next(){
      return nextInt();
    }
    @Override default Integer previous(){
      return previousInt();
    }
    default double previousDouble(){
      return previousInt();
    }
    default float previousFloat(){
      return previousInt();
    }
    int previousInt();
    default long previousLong(){
      return previousInt();
    }
    default void set(boolean val){
      set((int)TypeUtil.castToByte(val));
    }
    default void set(Boolean val){
      set((int)TypeUtil.castToByte(val.booleanValue()));
    }
    default void set(byte val){
      set((int)val);
    }
    default void set(Byte val){
      set(val.intValue());
    }
    default void set(char val){
      set((int)val);
    }
    default void set(Character val){
      set((int)val.charValue());
    }
    void set(int val);
    @Override default void set(Integer val){
      set(val.intValue());
    }
    default void set(short val){
      set((int)val);
    }
    default void set(Short val){
      set(val.intValue());
    }
  }
  interface OfLong extends OmniListIterator<Long>,OmniIterator.OfLong{
    default void add(boolean val){
      add(TypeUtil.castToLong(val));
    }
    default void add(Boolean val){
      add(TypeUtil.castToLong(val.booleanValue()));
    }
    default void add(byte val){
      add((long)val);
    }
    default void add(Byte val){
      add(val.longValue());
    }
    default void add(char val){
      add((long)val);
    }
    default void add(Character val){
      add((long)val.charValue());
    }
    default void add(int val){
      add((long)val);
    }
    default void add(Integer val){
      add(val.longValue());
    }
    void add(long val);
    @Override default void add(Long val){
      add(val.longValue());
    }
    default void add(short val){
      add((long)val);
    }
    default void add(Short val){
      add(val.longValue());
    }
    @Override default Long next(){
      return nextLong();
    }
    @Override default Long previous(){
      return previousLong();
    }
    default double previousDouble(){
      return previousLong();
    }
    default float previousFloat(){
      return previousLong();
    }
    long previousLong();
    default void set(boolean val){
      set(TypeUtil.castToLong(val));
    }
    default void set(Boolean val){
      set(TypeUtil.castToLong(val.booleanValue()));
    }
    default void set(byte val){
      set((long)val);
    }
    default void set(Byte val){
      set(val.longValue());
    }
    default void set(char val){
      set((long)val);
    }
    default void set(Character val){
      set((long)val.charValue());
    }
    default void set(int val){
      set((long)val);
    }
    default void set(Integer val){
      set(val.longValue());
    }
    void set(long val);
    @Override default void set(Long val){
      set(val.longValue());
    }
    default void set(short val){
      set((long)val);
    }
    default void set(Short val){
      set(val.longValue());
    }
  }
  interface OfRef<E>extends OmniListIterator<E>,OmniIterator.OfRef<E>{
    @Override void add(E val);
    @Override E previous();
    @Override void set(E val);
  }
  interface OfShort extends OmniListIterator<Short>,OmniIterator.OfShort{
    default void add(boolean val){
      add((short)TypeUtil.castToByte(val));
    }
    default void add(Boolean val){
      add((short)TypeUtil.castToByte(val.booleanValue()));
    }
    default void add(byte val){
      add((short)val);
    }
    default void add(Byte val){
      add(val.shortValue());
    }
    void add(short val);
    @Override default void add(Short val){
      add(val.shortValue());
    }
    @Override default Short next(){
      return nextShort();
    }
    @Override default Short previous(){
      return previousShort();
    }
    default double previousDouble(){
      return previousShort();
    }
    default float previousFloat(){
      return previousShort();
    }
    default int previousInt(){
      return previousShort();
    }
    default long previousLong(){
      return previousShort();
    }
    short previousShort();
    default void set(boolean val){
      set((short)TypeUtil.castToByte(val));
    }
    default void set(Boolean val){
      set((short)TypeUtil.castToByte(val.booleanValue()));
    }
    default void set(byte val){
      set((short)val);
    }
    default void set(Byte val){
      set(val.shortValue());
    }
    void set(short val);
    @Override default void set(Short val){
      set(val.shortValue());
    }
  }
}
