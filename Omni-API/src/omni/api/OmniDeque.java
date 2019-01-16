package omni.api;
import omni.util.PeekAndPollIfc;
import omni.util.TypeUtil;
public interface OmniDeque extends OmniCollection,OmniQueue,OmniStack{
  default boolean removeFirstOccurrence(boolean val){
    return removeVal(val);
  }
  boolean removeFirstOccurrence(Boolean val);
  boolean removeFirstOccurrence(byte val);
  boolean removeFirstOccurrence(Byte val);
  boolean removeFirstOccurrence(char val);
  boolean removeFirstOccurrence(Character val);
  default boolean removeFirstOccurrence(double val){
    return removeVal(val);
  }
  boolean removeFirstOccurrence(Double val);
  default boolean removeFirstOccurrence(float val){
    return removeVal(val);
  }
  boolean removeFirstOccurrence(Float val);
  default boolean removeFirstOccurrence(int val){
    return removeVal(val);
  }
  boolean removeFirstOccurrence(Integer val);
  default boolean removeFirstOccurrence(long val){
    return removeVal(val);
  }
  boolean removeFirstOccurrence(Long val);
  boolean removeFirstOccurrence(Object val);
  boolean removeFirstOccurrence(short val);
  boolean removeFirstOccurrence(Short val);
  boolean removeLastOccurrence(boolean val);
  boolean removeLastOccurrence(Boolean val);
  boolean removeLastOccurrence(byte val);
  boolean removeLastOccurrence(Byte val);
  boolean removeLastOccurrence(char val);
  boolean removeLastOccurrence(Character val);
  boolean removeLastOccurrence(double val);
  boolean removeLastOccurrence(Double val);
  boolean removeLastOccurrence(float val);
  boolean removeLastOccurrence(Float val);
  boolean removeLastOccurrence(int val);
  boolean removeLastOccurrence(Integer val);
  boolean removeLastOccurrence(long val);
  boolean removeLastOccurrence(Long val);
  boolean removeLastOccurrence(Object val);
  boolean removeLastOccurrence(short val);
  boolean removeLastOccurrence(Short val);
  interface OfBoolean
      extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfBoolean,PeekAndPollIfc<Boolean>,
      PeekAndPollIfc.DoubleInput,PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,PeekAndPollIfc.IntInput,
      PeekAndPollIfc.ShortInput,PeekAndPollIfc.CharInput,PeekAndPollIfc.ByteInput,PeekAndPollIfc.BooleanInput,OmniStack,
      OmniStack.OfPrimitive,OmniStack.OfBoolean,OmniQueue,OmniQueue.OfBoolean,OmniDeque,OfPrimitive{
    default void addFirst(boolean val){
      push(val);
    }
    void addFirst(Boolean val);
    void addLast(boolean val);
    void addLast(Boolean val);
    OmniIterator.OfBoolean descendingIterator();
    Boolean getFirst();
    default boolean getFirstBoolean(){
      return booleanElement();
    }
    default byte getFirstByte(){
      return TypeUtil.castToByte(booleanElement());
    }
    default char getFirstChar(){
      return TypeUtil.castToChar(booleanElement());
    }
    default double getFirstDouble(){
      return TypeUtil.castToDouble(booleanElement());
    }
    default float getFirstFloat(){
      return TypeUtil.castToFloat(booleanElement());
    }
    default int getFirstInt(){
      return TypeUtil.castToByte(booleanElement());
    }
    default long getFirstLong(){
      return TypeUtil.castToLong(booleanElement());
    }
    default short getFirstShort(){
      return TypeUtil.castToByte(booleanElement());
    }
    Boolean getLast();
    boolean getLastBoolean();
    default byte getLastByte(){
      return TypeUtil.castToByte(getLastBoolean());
    }
    default char getLastChar(){
      return TypeUtil.castToChar(getLastBoolean());
    }
    default double getLastDouble(){
      return TypeUtil.castToDouble(getLastBoolean());
    }
    default float getLastFloat(){
      return TypeUtil.castToFloat(getLastBoolean());
    }
    default int getLastInt(){
      return TypeUtil.castToByte(getLastBoolean());
    }
    default long getLastLong(){
      return TypeUtil.castToLong(getLastBoolean());
    }
    default short getLastShort(){
      return TypeUtil.castToByte(getLastBoolean());
    }
    boolean offerFirst(boolean val);
    boolean offerFirst(Boolean val);
    boolean offerLast(boolean val);
    boolean offerLast(Boolean val);
    Boolean peekFirst();
    default boolean peekFirstBoolean(){
      return peekBoolean();
    }
    default byte peekFirstByte(){
      return peekByte();
    }
    default char peekFirstChar(){
      return peekChar();
    }
    default double peekFirstDouble(){
      return peekDouble();
    }
    default float peekFirstFloat(){
      return peekFloat();
    }
    default int peekFirstInt(){
      return peekInt();
    }
    default long peekFirstLong(){
      return peekLong();
    }
    default short peekFirstShort(){
      return peekShort();
    }
    Boolean peekLast();
    boolean peekLastBoolean();
    byte peekLastByte();
    char peekLastChar();
    double peekLastDouble();
    float peekLastFloat();
    int peekLastInt();
    long peekLastLong();
    short peekLastShort();
    Boolean pollFirst();
    default boolean pollFirstBoolean(){
      return pollBoolean();
    }
    default byte pollFirstByte(){
      return pollByte();
    }
    default char pollFirstChar(){
      return pollChar();
    }
    default double pollFirstDouble(){
      return pollDouble();
    }
    default float pollFirstFloat(){
      return pollFloat();
    }
    default int pollFirstInt(){
      return pollInt();
    }
    default long pollFirstLong(){
      return pollLong();
    }
    default short pollFirstShort(){
      return pollShort();
    }
    Boolean pollLast();
    boolean pollLastBoolean();
    byte pollLastByte();
    char pollLastChar();
    double pollLastDouble();
    float pollLastFloat();
    int pollLastInt();
    long pollLastLong();
    short pollLastShort();
    @Override default boolean removeBoolean(){
      return popBoolean();
    }
    Boolean removeFirst();
    default boolean removeFirstBoolean(){
      return popBoolean();
    }
    default byte removeFirstByte(){
      return TypeUtil.castToByte(popBoolean());
    }
    default char removeFirstChar(){
      return TypeUtil.castToChar(popBoolean());
    }
    default double removeFirstDouble(){
      return TypeUtil.castToDouble(popBoolean());
    }
    default float removeFirstFloat(){
      return TypeUtil.castToFloat(popBoolean());
    }
    default int removeFirstInt(){
      return TypeUtil.castToByte(popBoolean());
    }
    default long removeFirstLong(){
      return TypeUtil.castToLong(popBoolean());
    }
    @Override default boolean removeFirstOccurrence(byte val){
      return removeVal((int)val);
    }
    @Override default boolean removeFirstOccurrence(char val){
      return removeVal((int)val);
    }
    @Override default boolean removeFirstOccurrence(short val){
      return removeVal((int)val);
    }
    default short removeFirstShort(){
      return TypeUtil.castToByte(popBoolean());
    }
    Boolean removeLast();
    boolean removeLastBoolean();
    default byte removeLastByte(){
      return TypeUtil.castToByte(removeLastBoolean());
    }
    default char removeLastChar(){
      return TypeUtil.castToChar(removeLastBoolean());
    }
    default double removeLastDouble(){
      return TypeUtil.castToDouble(removeLastBoolean());
    }
    default float removeLastFloat(){
      return TypeUtil.castToFloat(removeLastBoolean());
    }
    default int removeLastInt(){
      return TypeUtil.castToByte(removeLastBoolean());
    }
    default long removeLastLong(){
      return TypeUtil.castToLong(removeLastBoolean());
    }
    @Override default boolean removeLastOccurrence(byte val){
      return removeLastOccurrence((int)val);
    }
    @Override default boolean removeLastOccurrence(char val){
      return removeLastOccurrence((int)val);
    }
    @Override default boolean removeLastOccurrence(short val){
      return removeLastOccurrence((int)val);
    }
    default short removeLastShort(){
      return TypeUtil.castToByte(removeLastBoolean());
    }
  }
  interface OfByte extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfByte,PeekAndPollIfc<Byte>,
      PeekAndPollIfc.DoubleInput,PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,PeekAndPollIfc.IntInput,
      PeekAndPollIfc.ShortInput,PeekAndPollIfc.ByteInput,OmniStack,OmniStack.OfPrimitive,OmniStack.OfByte,OmniQueue,
      OmniQueue.OfByte,OmniDeque,OfPrimitive{
    default void addFirst(boolean val){
      push(TypeUtil.castToByte(val));
    }
    default void addFirst(Boolean val){
      push(TypeUtil.castToByte(val.booleanValue()));
    }
    default void addFirst(byte val){
      push(val);
    }
    void addFirst(Byte val);
    default void addLast(boolean val){
      addLast(TypeUtil.castToByte(val));
    }
    default void addLast(Boolean val){
      addLast(TypeUtil.castToByte(val.booleanValue()));
    }
    void addLast(byte val);
    void addLast(Byte val);
    OmniIterator.OfByte descendingIterator();
    Byte getFirst();
    default byte getFirstByte(){
      return byteElement();
    }
    default double getFirstDouble(){
      return byteElement();
    }
    default float getFirstFloat(){
      return byteElement();
    }
    default int getFirstInt(){
      return byteElement();
    }
    default long getFirstLong(){
      return byteElement();
    }
    default short getFirstShort(){
      return byteElement();
    }
    Byte getLast();
    byte getLastByte();
    default double getLastDouble(){
      return getLastByte();
    }
    default float getLastFloat(){
      return getLastByte();
    }
    default int getLastInt(){
      return getLastByte();
    }
    default long getLastLong(){
      return getLastByte();
    }
    default short getLastShort(){
      return getLastByte();
    }
    default boolean offerFirst(boolean val){
      return offerFirst(TypeUtil.castToByte(val));
    }
    default boolean offerFirst(Boolean val){
      return offerFirst(TypeUtil.castToByte(val.booleanValue()));
    }
    boolean offerFirst(byte val);
    boolean offerFirst(Byte val);
    default boolean offerLast(boolean val){
      return offerLast(TypeUtil.castToByte(val));
    }
    default boolean offerLast(Boolean val){
      return offerLast(TypeUtil.castToByte(val.booleanValue()));
    }
    boolean offerLast(byte val);
    boolean offerLast(Byte val);
    Byte peekFirst();
    default byte peekFirstByte(){
      return peekByte();
    }
    default double peekFirstDouble(){
      return peekDouble();
    }
    default float peekFirstFloat(){
      return peekFloat();
    }
    default int peekFirstInt(){
      return peekInt();
    }
    default long peekFirstLong(){
      return peekLong();
    }
    default short peekFirstShort(){
      return peekShort();
    }
    Byte peekLast();
    byte peekLastByte();
    double peekLastDouble();
    float peekLastFloat();
    int peekLastInt();
    long peekLastLong();
    short peekLastShort();
    Byte pollFirst();
    default byte pollFirstByte(){
      return pollByte();
    }
    default double pollFirstDouble(){
      return pollDouble();
    }
    default float pollFirstFloat(){
      return pollFloat();
    }
    default int pollFirstInt(){
      return pollInt();
    }
    default long pollFirstLong(){
      return pollLong();
    }
    default short pollFirstShort(){
      return pollShort();
    }
    Byte pollLast();
    byte pollLastByte();
    double pollLastDouble();
    float pollLastFloat();
    int pollLastInt();
    long pollLastLong();
    short pollLastShort();
    @Override default byte removeByte(){
      return popByte();
    }
    Byte removeFirst();
    default byte removeFirstByte(){
      return popByte();
    }
    default double removeFirstDouble(){
      return popByte();
    }
    default float removeFirstFloat(){
      return popByte();
    }
    default int removeFirstInt(){
      return popByte();
    }
    default long removeFirstLong(){
      return popByte();
    }
    @Override default boolean removeFirstOccurrence(byte val){
      return removeVal((int)val);
    }
    @Override default boolean removeFirstOccurrence(char val){
      return removeVal((int)val);
    }
    @Override default boolean removeFirstOccurrence(short val){
      return removeVal((int)val);
    }
    default short removeFirstShort(){
      return popByte();
    }
    Byte removeLast();
    byte removeLastByte();
    default double removeLastDouble(){
      return removeLastByte();
    }
    default float removeLastFloat(){
      return removeLastByte();
    }
    default int removeLastInt(){
      return removeLastByte();
    }
    default long removeLastLong(){
      return removeLastByte();
    }
    @Override default boolean removeLastOccurrence(short val){
      return removeLastOccurrence((int)val);
    }
    default short removeLastShort(){
      return removeLastByte();
    }
  }
  interface OfChar extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfChar,PeekAndPollIfc<Character>,
      PeekAndPollIfc.DoubleInput,PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,PeekAndPollIfc.IntInput,
      PeekAndPollIfc.CharInput,OmniStack,OmniStack.OfPrimitive,OmniStack.OfChar,OmniQueue,OmniQueue.OfChar,OmniDeque,
      OfPrimitive{
    default void addFirst(boolean val){
      push(TypeUtil.castToChar(val));
    }
    default void addFirst(Boolean val){
      push(TypeUtil.castToChar(val.booleanValue()));
    }
    default void addFirst(char val){
      push(val);
    }
    void addFirst(Character val);
    default void addLast(boolean val){
      addLast(TypeUtil.castToChar(val));
    }
    default void addLast(Boolean val){
      addLast(TypeUtil.castToChar(val.booleanValue()));
    }
    void addLast(char val);
    void addLast(Character val);
    OmniIterator.OfChar descendingIterator();
    Character getFirst();
    default char getFirstChar(){
      return charElement();
    }
    default double getFirstDouble(){
      return charElement();
    }
    default float getFirstFloat(){
      return charElement();
    }
    default int getFirstInt(){
      return charElement();
    }
    default long getFirstLong(){
      return charElement();
    }
    Character getLast();
    char getLastChar();
    default double getLastDouble(){
      return getLastChar();
    }
    default float getLastFloat(){
      return getLastChar();
    }
    default int getLastInt(){
      return getLastChar();
    }
    default long getLastLong(){
      return getLastChar();
    }
    default boolean offerFirst(boolean val){
      return offerFirst(TypeUtil.castToChar(val));
    }
    default boolean offerFirst(Boolean val){
      return offerFirst(TypeUtil.castToChar(val.booleanValue()));
    }
    boolean offerFirst(char val);
    boolean offerFirst(Character val);
    default boolean offerLast(boolean val){
      return offerLast(TypeUtil.castToChar(val));
    }
    default boolean offerLast(Boolean val){
      return offerLast(TypeUtil.castToChar(val.booleanValue()));
    }
    boolean offerLast(char val);
    boolean offerLast(Character val);
    Character peekFirst();
    default char peekFirstChar(){
      return peekChar();
    }
    default double peekFirstDouble(){
      return peekDouble();
    }
    default float peekFirstFloat(){
      return peekFloat();
    }
    default int peekFirstInt(){
      return peekInt();
    }
    default long peekFirstLong(){
      return peekLong();
    }
    Character peekLast();
    char peekLastChar();
    double peekLastDouble();
    float peekLastFloat();
    int peekLastInt();
    long peekLastLong();
    Character pollFirst();
    default char pollFirstChar(){
      return pollChar();
    }
    default double pollFirstDouble(){
      return pollDouble();
    }
    default float pollFirstFloat(){
      return pollFloat();
    }
    default int pollFirstInt(){
      return pollInt();
    }
    default long pollFirstLong(){
      return pollLong();
    }
    Character pollLast();
    char pollLastChar();
    double pollLastDouble();
    float pollLastFloat();
    int pollLastInt();
    long pollLastLong();
    @Override default char removeChar(){
      return popChar();
    }
    Character removeFirst();
    default char removeFirstChar(){
      return popChar();
    }
    default double removeFirstDouble(){
      return popChar();
    }
    default float removeFirstFloat(){
      return popChar();
    }
    default int removeFirstInt(){
      return popChar();
    }
    default long removeFirstLong(){
      return popChar();
    }
    @Override default boolean removeFirstOccurrence(byte val){
      return removeVal((short)val);
    }
    @Override default boolean removeFirstOccurrence(char val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(short val){
      return removeVal(val);
    }
    Character removeLast();
    char removeLastChar();
    default double removeLastDouble(){
      return removeLastChar();
    }
    default float removeLastFloat(){
      return removeLastChar();
    }
    default int removeLastInt(){
      return removeLastChar();
    }
    default long removeLastLong(){
      return removeLastChar();
    }
    @Override default boolean removeLastOccurrence(byte val){
      return removeLastOccurrence((short)val);
    }
  }
  interface OfDouble extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfDouble,PeekAndPollIfc<Double>,
      PeekAndPollIfc.DoubleInput,OmniStack,OmniStack.OfPrimitive,OmniStack.OfDouble,OmniQueue,OmniQueue.OfDouble,
      OmniDeque,OfPrimitive{
    default void addFirst(boolean val){
      push(TypeUtil.castToDouble(val));
    }
    default void addFirst(Boolean val){
      push(TypeUtil.castToDouble(val.booleanValue()));
    }
    default void addFirst(byte val){
      push((double)val);
    }
    default void addFirst(Byte val){
      push(val.doubleValue());
    }
    default void addFirst(char val){
      push((double)val);
    }
    default void addFirst(Character val){
      push((double)val.charValue());
    }
    default void addFirst(double val){
      push(val);
    }
    void addFirst(Double val);
    default void addFirst(float val){
      push((double)val);
    }
    default void addFirst(Float val){
      push(val.doubleValue());
    }
    default void addFirst(int val){
      push((double)val);
    }
    default void addFirst(Integer val){
      push(val.doubleValue());
    }
    default void addFirst(long val){
      push((double)val);
    }
    default void addFirst(Long val){
      push(val.doubleValue());
    }
    default void addFirst(short val){
      push((double)val);
    }
    default void addFirst(Short val){
      push(val.doubleValue());
    }
    default void addLast(boolean val){
      addLast(TypeUtil.castToDouble(val));
    }
    default void addLast(Boolean val){
      addLast(TypeUtil.castToDouble(val.booleanValue()));
    }
    default void addLast(byte val){
      addLast((double)val);
    }
    default void addLast(Byte val){
      addLast(val.doubleValue());
    }
    default void addLast(char val){
      addLast((double)val);
    }
    default void addLast(Character val){
      addLast((double)val.charValue());
    }
    void addLast(double val);
    void addLast(Double val);
    default void addLast(float val){
      addLast((double)val);
    }
    default void addLast(Float val){
      addLast(val.doubleValue());
    }
    default void addLast(int val){
      addLast((double)val);
    }
    default void addLast(Integer val){
      addLast(val.doubleValue());
    }
    default void addLast(long val){
      addLast((double)val);
    }
    default void addLast(Long val){
      addLast(val.doubleValue());
    }
    default void addLast(short val){
      addLast((double)val);
    }
    default void addLast(Short val){
      addLast(val.doubleValue());
    }
    OmniIterator.OfDouble descendingIterator();
    Double getFirst();
    default double getFirstDouble(){
      return doubleElement();
    }
    Double getLast();
    double getLastDouble();
    default boolean offerFirst(boolean val){
      return offerFirst(TypeUtil.castToDouble(val));
    }
    default boolean offerFirst(Boolean val){
      return offerFirst(TypeUtil.castToDouble(val.booleanValue()));
    }
    default boolean offerFirst(byte val){
      return offerFirst((double)val);
    }
    default boolean offerFirst(Byte val){
      return offerFirst(val.doubleValue());
    }
    default boolean offerFirst(char val){
      return offerFirst((double)val);
    }
    default boolean offerFirst(Character val){
      return offerFirst((double)val.charValue());
    }
    boolean offerFirst(double val);
    boolean offerFirst(Double val);
    default boolean offerFirst(float val){
      return offerFirst((double)val);
    }
    default boolean offerFirst(Float val){
      return offerFirst(val.doubleValue());
    }
    default boolean offerFirst(int val){
      return offerFirst((double)val);
    }
    default boolean offerFirst(Integer val){
      return offerFirst(val.doubleValue());
    }
    default boolean offerFirst(long val){
      return offerFirst((double)val);
    }
    default boolean offerFirst(Long val){
      return offerFirst(val.doubleValue());
    }
    default boolean offerFirst(short val){
      return offerFirst((double)val);
    }
    default boolean offerFirst(Short val){
      return offerFirst(val.doubleValue());
    }
    default boolean offerLast(boolean val){
      return offerLast(TypeUtil.castToDouble(val));
    }
    default boolean offerLast(Boolean val){
      return offerLast(TypeUtil.castToDouble(val.booleanValue()));
    }
    default boolean offerLast(byte val){
      return offerLast((double)val);
    }
    default boolean offerLast(Byte val){
      return offerLast(val.doubleValue());
    }
    default boolean offerLast(char val){
      return offerLast((double)val);
    }
    default boolean offerLast(Character val){
      return offerLast((double)val.charValue());
    }
    boolean offerLast(double val);
    boolean offerLast(Double val);
    default boolean offerLast(float val){
      return offerLast((double)val);
    }
    default boolean offerLast(Float val){
      return offerLast(val.doubleValue());
    }
    default boolean offerLast(int val){
      return offerLast((double)val);
    }
    default boolean offerLast(Integer val){
      return offerLast(val.doubleValue());
    }
    default boolean offerLast(long val){
      return offerLast((double)val);
    }
    default boolean offerLast(Long val){
      return offerLast(val.doubleValue());
    }
    default boolean offerLast(short val){
      return offerLast((double)val);
    }
    default boolean offerLast(Short val){
      return offerLast(val.doubleValue());
    }
    Double peekFirst();
    default double peekFirstDouble(){
      return peekDouble();
    }
    Double peekLast();
    double peekLastDouble();
    Double pollFirst();
    default double pollFirstDouble(){
      return pollDouble();
    }
    Double pollLast();
    double pollLastDouble();
    @Override default double removeDouble(){
      return popDouble();
    }
    Double removeFirst();
    default double removeFirstDouble(){
      return popDouble();
    }
    @Override default boolean removeFirstOccurrence(byte val){
      return removeVal((int)val);
    }
    @Override default boolean removeFirstOccurrence(char val){
      return removeVal((int)val);
    }
    @Override default boolean removeFirstOccurrence(short val){
      return removeVal((int)val);
    }
    Double removeLast();
    double removeLastDouble();
    @Override default boolean removeLastOccurrence(byte val){
      return removeLastOccurrence((int)val);
    }
    @Override default boolean removeLastOccurrence(char val){
      return removeLastOccurrence((int)val);
    }
    @Override default boolean removeLastOccurrence(short val){
      return removeLastOccurrence((int)val);
    }
  }
  interface OfFloat extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfFloat,PeekAndPollIfc<Float>,
      PeekAndPollIfc.DoubleInput,PeekAndPollIfc.FloatInput,OmniStack,OmniStack.OfPrimitive,OmniStack.OfFloat,OmniQueue,
      OmniQueue.OfFloat,OmniDeque,OfPrimitive{
    default void addFirst(boolean val){
      push(TypeUtil.castToFloat(val));
    }
    default void addFirst(Boolean val){
      push(TypeUtil.castToFloat(val.booleanValue()));
    }
    default void addFirst(byte val){
      push((float)val);
    }
    default void addFirst(Byte val){
      push(val.floatValue());
    }
    default void addFirst(char val){
      push((float)val);
    }
    default void addFirst(Character val){
      push((float)val.charValue());
    }
    default void addFirst(float val){
      push(val);
    }
    void addFirst(Float val);
    default void addFirst(int val){
      push((float)val);
    }
    default void addFirst(Integer val){
      push(val.floatValue());
    }
    default void addFirst(long val){
      push((float)val);
    }
    default void addFirst(Long val){
      push(val.floatValue());
    }
    default void addFirst(short val){
      push((float)val);
    }
    default void addFirst(Short val){
      push(val.floatValue());
    }
    default void addLast(boolean val){
      addLast(TypeUtil.castToFloat(val));
    }
    default void addLast(Boolean val){
      addLast(TypeUtil.castToFloat(val.booleanValue()));
    }
    default void addLast(byte val){
      addLast((float)val);
    }
    default void addLast(Byte val){
      addLast(val.floatValue());
    }
    default void addLast(char val){
      addLast((float)val);
    }
    default void addLast(Character val){
      addLast((float)val.charValue());
    }
    void addLast(float val);
    void addLast(Float val);
    default void addLast(int val){
      addLast((float)val);
    }
    default void addLast(Integer val){
      addLast(val.floatValue());
    }
    default void addLast(long val){
      addLast((float)val);
    }
    default void addLast(Long val){
      addLast(val.floatValue());
    }
    default void addLast(short val){
      addLast((float)val);
    }
    default void addLast(Short val){
      addLast(val.floatValue());
    }
    OmniIterator.OfFloat descendingIterator();
    Float getFirst();
    default double getFirstDouble(){
      return floatElement();
    }
    default float getFirstFloat(){
      return floatElement();
    }
    Float getLast();
    default double getLastDouble(){
      return getLastFloat();
    }
    float getLastFloat();
    default boolean offerFirst(boolean val){
      return offerFirst(TypeUtil.castToFloat(val));
    }
    default boolean offerFirst(Boolean val){
      return offerFirst(TypeUtil.castToFloat(val.booleanValue()));
    }
    default boolean offerFirst(byte val){
      return offerFirst((float)val);
    }
    default boolean offerFirst(Byte val){
      return offerFirst(val.floatValue());
    }
    default boolean offerFirst(char val){
      return offerFirst((float)val);
    }
    default boolean offerFirst(Character val){
      return offerFirst((float)val.charValue());
    }
    boolean offerFirst(float val);
    boolean offerFirst(Float val);
    default boolean offerFirst(int val){
      return offerFirst((float)val);
    }
    default boolean offerFirst(Integer val){
      return offerFirst(val.floatValue());
    }
    default boolean offerFirst(long val){
      return offerFirst((float)val);
    }
    default boolean offerFirst(Long val){
      return offerFirst(val.floatValue());
    }
    default boolean offerFirst(short val){
      return offerFirst((float)val);
    }
    default boolean offerFirst(Short val){
      return offerFirst(val.floatValue());
    }
    default boolean offerLast(boolean val){
      return offerLast(TypeUtil.castToFloat(val));
    }
    default boolean offerLast(Boolean val){
      return offerLast(TypeUtil.castToFloat(val.booleanValue()));
    }
    default boolean offerLast(byte val){
      return offerLast((float)val);
    }
    default boolean offerLast(Byte val){
      return offerLast(val.floatValue());
    }
    default boolean offerLast(char val){
      return offerLast((float)val);
    }
    default boolean offerLast(Character val){
      return offerLast((float)val.charValue());
    }
    boolean offerLast(float val);
    boolean offerLast(Float val);
    default boolean offerLast(int val){
      return offerLast((float)val);
    }
    default boolean offerLast(Integer val){
      return offerLast(val.floatValue());
    }
    default boolean offerLast(long val){
      return offerLast((float)val);
    }
    default boolean offerLast(Long val){
      return offerLast(val.floatValue());
    }
    default boolean offerLast(short val){
      return offerLast((float)val);
    }
    default boolean offerLast(Short val){
      return offerLast(val.floatValue());
    }
    Float peekFirst();
    default double peekFirstDouble(){
      return peekDouble();
    }
    default float peekFirstFloat(){
      return peekFloat();
    }
    Float peekLast();
    double peekLastDouble();
    float peekLastFloat();
    Float pollFirst();
    default double pollFirstDouble(){
      return pollDouble();
    }
    default float pollFirstFloat(){
      return pollFloat();
    }
    Float pollLast();
    double pollLastDouble();
    float pollLastFloat();
    Float removeFirst();
    default double removeFirstDouble(){
      return popFloat();
    }
    default float removeFirstFloat(){
      return popFloat();
    }
    @Override default boolean removeFirstOccurrence(byte val){
      return removeVal((short)val);
    }
    @Override default boolean removeFirstOccurrence(char val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(short val){
      return removeVal(val);
    }
    @Override default float removeFloat(){
      return popFloat();
    }
    Float removeLast();
    default double removeLastDouble(){
      return removeLastFloat();
    }
    float removeLastFloat();
    @Override default boolean removeLastOccurrence(byte val){
      return removeLastOccurrence((short)val);
    }
  }
  interface OfInt extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfInt,PeekAndPollIfc<Integer>,
      PeekAndPollIfc.DoubleInput,PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,PeekAndPollIfc.IntInput,OmniStack,
      OmniStack.OfPrimitive,OmniStack.OfInt,OmniQueue,OmniQueue.OfInt,OmniDeque,OfPrimitive{
    default void addFirst(boolean val){
      push((int)TypeUtil.castToByte(val));
    }
    default void addFirst(Boolean val){
      push((int)TypeUtil.castToByte(val.booleanValue()));
    }
    default void addFirst(byte val){
      push((int)val);
    }
    default void addFirst(Byte val){
      push(val.intValue());
    }
    default void addFirst(char val){
      push((int)val);
    }
    default void addFirst(Character val){
      push((int)val.charValue());
    }
    default void addFirst(int val){
      push(val);
    }
    void addFirst(Integer val);
    default void addFirst(short val){
      push((int)val);
    }
    default void addFirst(Short val){
      push(val.intValue());
    }
    default void addLast(boolean val){
      addLast((int)TypeUtil.castToByte(val));
    }
    default void addLast(Boolean val){
      addLast((int)TypeUtil.castToByte(val.booleanValue()));
    }
    default void addLast(byte val){
      addLast((int)val);
    }
    default void addLast(Byte val){
      addLast(val.intValue());
    }
    default void addLast(char val){
      addLast((int)val);
    }
    default void addLast(Character val){
      addLast((int)val.charValue());
    }
    void addLast(int val);
    void addLast(Integer val);
    default void addLast(short val){
      addLast((int)val);
    }
    default void addLast(Short val){
      addLast(val.intValue());
    }
    OmniIterator.OfInt descendingIterator();
    Integer getFirst();
    default double getFirstDouble(){
      return intElement();
    }
    default float getFirstFloat(){
      return intElement();
    }
    default int getFirstInt(){
      return intElement();
    }
    default long getFirstLong(){
      return intElement();
    }
    Integer getLast();
    default double getLastDouble(){
      return getLastInt();
    }
    default float getLastFloat(){
      return getLastInt();
    }
    int getLastInt();
    default long getLastLong(){
      return getLastInt();
    }
    default boolean offerFirst(boolean val){
      return offerFirst((int)TypeUtil.castToByte(val));
    }
    default boolean offerFirst(Boolean val){
      return offerFirst((int)TypeUtil.castToByte(val.booleanValue()));
    }
    default boolean offerFirst(byte val){
      return offerFirst((int)val);
    }
    default boolean offerFirst(Byte val){
      return offerFirst(val.intValue());
    }
    default boolean offerFirst(char val){
      return offerFirst((int)val);
    }
    default boolean offerFirst(Character val){
      return offerFirst((int)val.charValue());
    }
    boolean offerFirst(int val);
    boolean offerFirst(Integer val);
    default boolean offerFirst(short val){
      return offerFirst((int)val);
    }
    default boolean offerFirst(Short val){
      return offerFirst(val.intValue());
    }
    default boolean offerLast(boolean val){
      return offerLast((int)TypeUtil.castToByte(val));
    }
    default boolean offerLast(Boolean val){
      return offerLast((int)TypeUtil.castToByte(val.booleanValue()));
    }
    default boolean offerLast(byte val){
      return offerLast((int)val);
    }
    default boolean offerLast(Byte val){
      return offerLast(val.intValue());
    }
    default boolean offerLast(char val){
      return offerLast((int)val);
    }
    default boolean offerLast(Character val){
      return offerLast((int)val.charValue());
    }
    boolean offerLast(int val);
    boolean offerLast(Integer val);
    default boolean offerLast(short val){
      return offerLast((int)val);
    }
    default boolean offerLast(Short val){
      return offerLast(val.intValue());
    }
    Integer peekFirst();
    default double peekFirstDouble(){
      return peekDouble();
    }
    default float peekFirstFloat(){
      return peekFloat();
    }
    default int peekFirstInt(){
      return peekInt();
    }
    default long peekFirstLong(){
      return peekLong();
    }
    Integer peekLast();
    double peekLastDouble();
    float peekLastFloat();
    int peekLastInt();
    long peekLastLong();
    Integer pollFirst();
    default double pollFirstDouble(){
      return pollDouble();
    }
    default float pollFirstFloat(){
      return pollFloat();
    }
    default int pollFirstInt(){
      return pollInt();
    }
    default long pollFirstLong(){
      return pollLong();
    }
    Integer pollLast();
    double pollLastDouble();
    float pollLastFloat();
    int pollLastInt();
    long pollLastLong();
    Integer removeFirst();
    default double removeFirstDouble(){
      return popInt();
    }
    default float removeFirstFloat(){
      return popInt();
    }
    default int removeFirstInt(){
      return popInt();
    }
    default long removeFirstLong(){
      return popInt();
    }
    @Override default boolean removeFirstOccurrence(byte val){
      return removeVal((int)val);
    }
    @Override default boolean removeFirstOccurrence(char val){
      return removeVal((int)val);
    }
    @Override default boolean removeFirstOccurrence(short val){
      return removeVal((int)val);
    }
    @Override default int removeInt(){
      return popInt();
    }
    Integer removeLast();
    default double removeLastDouble(){
      return removeLastInt();
    }
    default float removeLastFloat(){
      return removeLastInt();
    }
    int removeLastInt();
    default long removeLastLong(){
      return removeLastInt();
    }
    @Override default boolean removeLastOccurrence(byte val){
      return removeLastOccurrence((int)val);
    }
    @Override default boolean removeLastOccurrence(char val){
      return removeLastOccurrence((int)val);
    }
    @Override default boolean removeLastOccurrence(short val){
      return removeLastOccurrence((int)val);
    }
  }
  interface OfLong extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfLong,PeekAndPollIfc<Long>,
      PeekAndPollIfc.DoubleInput,PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,OmniStack,OmniStack.OfPrimitive,
      OmniStack.OfLong,OmniQueue,OmniQueue.OfLong,OmniDeque,OfPrimitive{
    default void addFirst(boolean val){
      push(TypeUtil.castToLong(val));
    }
    default void addFirst(Boolean val){
      push(TypeUtil.castToLong(val.booleanValue()));
    }
    default void addFirst(byte val){
      push((long)val);
    }
    default void addFirst(Byte val){
      push(val.longValue());
    }
    default void addFirst(char val){
      push((long)val);
    }
    default void addFirst(Character val){
      push((long)val.charValue());
    }
    default void addFirst(int val){
      push((long)val);
    }
    default void addFirst(Integer val){
      push(val.longValue());
    }
    default void addFirst(long val){
      push(val);
    }
    void addFirst(Long val);
    default void addFirst(short val){
      push((long)val);
    }
    default void addFirst(Short val){
      push(val.longValue());
    }
    default void addLast(boolean val){
      addLast(TypeUtil.castToLong(val));
    }
    default void addLast(Boolean val){
      addLast(TypeUtil.castToLong(val.booleanValue()));
    }
    default void addLast(byte val){
      addLast((long)val);
    }
    default void addLast(Byte val){
      addLast(val.longValue());
    }
    default void addLast(char val){
      addLast((long)val);
    }
    default void addLast(Character val){
      addLast((long)val.charValue());
    }
    default void addLast(int val){
      addLast((long)val);
    }
    default void addLast(Integer val){
      addLast(val.longValue());
    }
    void addLast(long val);
    void addLast(Long val);
    default void addLast(short val){
      addLast((long)val);
    }
    default void addLast(Short val){
      addLast(val.longValue());
    }
    OmniIterator.OfLong descendingIterator();
    Long getFirst();
    default double getFirstDouble(){
      return longElement();
    }
    default float getFirstFloat(){
      return longElement();
    }
    default long getFirstLong(){
      return longElement();
    }
    Long getLast();
    default double getLastDouble(){
      return getLastLong();
    }
    default float getLastFloat(){
      return getLastLong();
    }
    long getLastLong();
    default boolean offerFirst(boolean val){
      return offerFirst(TypeUtil.castToLong(val));
    }
    default boolean offerFirst(Boolean val){
      return offerFirst(TypeUtil.castToLong(val.booleanValue()));
    }
    default boolean offerFirst(byte val){
      return offerFirst((long)val);
    }
    default boolean offerFirst(Byte val){
      return offerFirst(val.longValue());
    }
    default boolean offerFirst(char val){
      return offerFirst((long)val);
    }
    default boolean offerFirst(Character val){
      return offerFirst((long)val.charValue());
    }
    default boolean offerFirst(int val){
      return offerFirst((long)val);
    }
    default boolean offerFirst(Integer val){
      return offerFirst(val.longValue());
    }
    boolean offerFirst(long val);
    boolean offerFirst(Long val);
    default boolean offerFirst(short val){
      return offerFirst((long)val);
    }
    default boolean offerFirst(Short val){
      return offerFirst(val.longValue());
    }
    default boolean offerLast(boolean val){
      return offerLast(TypeUtil.castToLong(val));
    }
    default boolean offerLast(Boolean val){
      return offerLast(TypeUtil.castToLong(val.booleanValue()));
    }
    default boolean offerLast(byte val){
      return offerLast((long)val);
    }
    default boolean offerLast(Byte val){
      return offerLast((long)val);
    }
    default boolean offerLast(char val){
      return offerLast((long)val);
    }
    default boolean offerLast(Character val){
      return offerLast((long)val.charValue());
    }
    default boolean offerLast(int val){
      return offerLast((long)val);
    }
    default boolean offerLast(Integer val){
      return offerLast(val.longValue());
    }
    boolean offerLast(long val);
    boolean offerLast(Long val);
    default boolean offerLast(short val){
      return offerLast((long)val);
    }
    default boolean offerLast(Short val){
      return offerLast(val.longValue());
    }
    Long peekFirst();
    default double peekFirstDouble(){
      return peekDouble();
    }
    default float peekFirstFloat(){
      return peekFloat();
    }
    default long peekFirstLong(){
      return peekLong();
    }
    Long peekLast();
    double peekLastDouble();
    float peekLastFloat();
    long peekLastLong();
    Long pollFirst();
    default double pollFirstDouble(){
      return pollDouble();
    }
    default float pollFirstFloat(){
      return pollFloat();
    }
    default long pollFirstLong(){
      return pollLong();
    }
    Long pollLast();
    double pollLastDouble();
    float pollLastFloat();
    long pollLastLong();
    Long removeFirst();
    default double removeFirstDouble(){
      return popLong();
    }
    default float removeFirstFloat(){
      return popLong();
    }
    default long removeFirstLong(){
      return popLong();
    }
    @Override default boolean removeFirstOccurrence(byte val){
      return removeVal((int)val);
    }
    @Override default boolean removeFirstOccurrence(char val){
      return removeVal((int)val);
    }
    @Override default boolean removeFirstOccurrence(short val){
      return removeVal((int)val);
    }
    Long removeLast();
    default double removeLastDouble(){
      return removeLastLong();
    }
    default float removeLastFloat(){
      return removeLastLong();
    }
    long removeLastLong();
    @Override default boolean removeLastOccurrence(byte val){
      return removeLastOccurrence((int)val);
    }
    @Override default boolean removeLastOccurrence(char val){
      return removeLastOccurrence((int)val);
    }
    @Override default boolean removeLastOccurrence(short val){
      return removeLastOccurrence((int)val);
    }
    @Override default long removeLong(){
      return popLong();
    }
  }
  interface OfPrimitive
      extends OmniCollection,OmniCollection.OfPrimitive,OmniStack,OmniQueue,OmniStack.OfPrimitive,OmniDeque{
    @Override default boolean removeFirstOccurrence(Boolean val){
      return val!=null&&removeVal(val.booleanValue());
    }
    @Override default boolean removeFirstOccurrence(Byte val){
      return val!=null&&removeVal(val.byteValue());
    }
    @Override default boolean removeFirstOccurrence(Character val){
      return val!=null&&removeVal(val.charValue());
    }
    @Override default boolean removeFirstOccurrence(Double val){
      return val!=null&&removeVal(val.doubleValue());
    }
    @Override default boolean removeFirstOccurrence(Float val){
      return val!=null&&removeVal(val.floatValue());
    }
    @Override default boolean removeFirstOccurrence(Integer val){
      return val!=null&&removeVal(val.intValue());
    }
    @Override default boolean removeFirstOccurrence(Long val){
      return val!=null&&removeVal(val.longValue());
    }
    @Override default boolean removeFirstOccurrence(Short val){
      return val!=null&&removeVal(val.shortValue());
    }
    @Override default boolean removeLastOccurrence(Boolean val){
      return val!=null&&removeLastOccurrence(val.booleanValue());
    }
    @Override default boolean removeLastOccurrence(Byte val){
      return val!=null&&removeLastOccurrence(val.byteValue());
    }
    @Override default boolean removeLastOccurrence(Character val){
      return val!=null&&removeLastOccurrence(val.charValue());
    }
    @Override default boolean removeLastOccurrence(Double val){
      return val!=null&&removeLastOccurrence(val.doubleValue());
    }
    @Override default boolean removeLastOccurrence(Float val){
      return val!=null&&removeLastOccurrence(val.floatValue());
    }
    @Override default boolean removeLastOccurrence(Integer val){
      return val!=null&&removeLastOccurrence(val.intValue());
    }
    @Override default boolean removeLastOccurrence(Long val){
      return val!=null&&removeLastOccurrence(val.longValue());
    }
    @Override default boolean removeLastOccurrence(Short val){
      return val!=null&&removeLastOccurrence(val.shortValue());
    }
  }
  interface OfRef<E>extends OmniCollection,OmniCollection.OfRef<E>,PeekAndPollIfc<E>,OmniStack,OmniStack.OfRef<E>,
      OmniQueue,OmniQueue.OfRef<E>,OmniDeque{
    void addFirst(E val);
    void addLast(E val);
    OmniIterator.OfRef<E> descendingIterator();
    E getFirst();
    E getLast();
    boolean offerFirst(E val);
    boolean offerLast(E val);
    E peekFirst();
    E peekLast();
    E pollFirst();
    E pollLast();
    E removeFirst();
    @Override default boolean removeFirstOccurrence(Boolean val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(byte val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(Byte val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(char val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(Character val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(Double val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(Float val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(Integer val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(Long val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(short val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(Short val){
      return removeVal(val);
    }
    E removeLast();
  }
  interface OfShort extends OmniCollection,OmniCollection.OfPrimitive,OmniCollection.OfShort,PeekAndPollIfc<Short>,
      PeekAndPollIfc.DoubleInput,PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,PeekAndPollIfc.IntInput,
      PeekAndPollIfc.ShortInput,OmniStack,OmniStack.OfPrimitive,OmniStack.OfShort,OmniQueue,OmniQueue.OfShort,OmniDeque,
      OfPrimitive{
    default void addFirst(boolean val){
      push((short)TypeUtil.castToByte(val));
    }
    default void addFirst(Boolean val){
      push((short)TypeUtil.castToByte(val.booleanValue()));
    }
    default void addFirst(byte val){
      push((short)val);
    }
    default void addFirst(Byte val){
      push(val.shortValue());
    }
    default void addFirst(short val){
      push(val);
    }
    void addFirst(Short val);
    default void addLast(boolean val){
      addLast((short)TypeUtil.castToByte(val));
    }
    default void addLast(Boolean val){
      addLast((short)TypeUtil.castToByte(val.booleanValue()));
    }
    default void addLast(byte val){
      addLast((short)val);
    }
    default void addLast(Byte val){
      addLast(val.shortValue());
    }
    void addLast(short val);
    void addLast(Short val);
    OmniIterator.OfShort descendingIterator();
    Short getFirst();
    default double getFirstDouble(){
      return shortElement();
    }
    default float getFirstFloat(){
      return shortElement();
    }
    default int getFirstInt(){
      return shortElement();
    }
    default long getFirstLong(){
      return shortElement();
    }
    default short getFirstShort(){
      return shortElement();
    }
    Short getLast();
    default double getLastDouble(){
      return getLastShort();
    }
    default float getLastFloat(){
      return getLastShort();
    }
    default int getLastInt(){
      return getLastShort();
    }
    default long getLastLong(){
      return getLastShort();
    }
    short getLastShort();
    default boolean offerFirst(boolean val){
      return offerFirst((short)TypeUtil.castToByte(val));
    }
    default boolean offerFirst(Boolean val){
      return offerFirst((short)TypeUtil.castToByte(val.booleanValue()));
    }
    default boolean offerFirst(byte val){
      return offerFirst((short)val);
    }
    default boolean offerFirst(Byte val){
      return offerFirst(val.shortValue());
    }
    boolean offerFirst(short val);
    boolean offerFirst(Short val);
    default boolean offerLast(boolean val){
      return offerLast((short)TypeUtil.castToByte(val));
    }
    default boolean offerLast(Boolean val){
      return offerLast((short)TypeUtil.castToByte(val.booleanValue()));
    }
    default boolean offerLast(byte val){
      return offerLast((short)val);
    }
    default boolean offerLast(Byte val){
      return offerLast(val.shortValue());
    }
    boolean offerLast(short val);
    boolean offerLast(Short val);
    Short peekFirst();
    default double peekFirstDouble(){
      return peekDouble();
    }
    default float peekFirstFloat(){
      return peekFloat();
    }
    default int peekFirstInt(){
      return peekInt();
    }
    default long peekFirstLong(){
      return peekLong();
    }
    default short peekFirstShort(){
      return peekShort();
    }
    Short peekLast();
    double peekLastDouble();
    float peekLastFloat();
    int peekLastInt();
    long peekLastLong();
    short peekLastShort();
    Short pollFirst();
    default double pollFirstDouble(){
      return pollDouble();
    }
    default float pollFirstFloat(){
      return pollFloat();
    }
    default int pollFirstInt(){
      return pollInt();
    }
    default long pollFirstLong(){
      return pollLong();
    }
    default short pollFirstShort(){
      return pollShort();
    }
    Short pollLast();
    double pollLastDouble();
    float pollLastFloat();
    int pollLastInt();
    long pollLastLong();
    short pollLastShort();
    Short removeFirst();
    default double removeFirstDouble(){
      return popShort();
    }
    default float removeFirstFloat(){
      return popShort();
    }
    default int removeFirstInt(){
      return popShort();
    }
    default long removeFirstLong(){
      return popShort();
    }
    @Override default boolean removeFirstOccurrence(byte val){
      return removeVal((short)val);
    }
    @Override default boolean removeFirstOccurrence(char val){
      return removeVal(val);
    }
    @Override default boolean removeFirstOccurrence(short val){
      return removeVal(val);
    }
    default short removeFirstShort(){
      return popShort();
    }
    Short removeLast();
    default double removeLastDouble(){
      return removeLastShort();
    }
    default float removeLastFloat(){
      return removeLastShort();
    }
    default int removeLastInt(){
      return removeLastShort();
    }
    default long removeLastLong(){
      return removeLastShort();
    }
    @Override default boolean removeLastOccurrence(byte val){
      return removeLastOccurrence((short)val);
    }
    short removeLastShort();
    @Override default short removeShort(){
      return popShort();
    }
  }
}
