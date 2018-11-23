package omni.api;
import omni.util.PeekAndPollIfc;
import omni.util.TypeUtil;
public interface OmniDeque extends OmniCollection,OmniQueue,OmniStack{
    boolean removeFirstOccurrence(boolean val);
    boolean removeFirstOccurrence(Boolean val);
    boolean removeFirstOccurrence(byte val);
    boolean removeFirstOccurrence(Byte val);
    boolean removeFirstOccurrence(char val);
    boolean removeFirstOccurrence(Character val);
    boolean removeFirstOccurrence(double val);
    boolean removeFirstOccurrence(Double val);
    boolean removeFirstOccurrence(float val);
    boolean removeFirstOccurrence(Float val);
    boolean removeFirstOccurrence(int val);
    boolean removeFirstOccurrence(Integer val);
    boolean removeFirstOccurrence(long val);
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
            extends
            OmniCollection,
            OmniCollection.OfPrimitive,
            OmniCollection.OfBoolean,
            PeekAndPollIfc<Boolean>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            PeekAndPollIfc.IntInput,
            PeekAndPollIfc.ShortInput,
            PeekAndPollIfc.CharInput,
            PeekAndPollIfc.ByteInput,
            PeekAndPollIfc.BooleanInput,
            OmniStack,
            OmniStack.OfPrimitive,
            OmniStack.OfBoolean,
            OmniQueue,
            OmniQueue.OfBoolean,
            OmniDeque,
            OfPrimitive{
        void addFirst(boolean val);
        void addFirst(Boolean val);
        void addLast(boolean val);
        void addLast(Boolean val);
        OmniIterator.OfBoolean descendingIterator();
        Boolean getFirst();
        boolean getFirstBoolean();
        default byte getFirstByte(){
            return TypeUtil.castToByte(getFirstBoolean());
        }
        default char getFirstChar(){
            return TypeUtil.castToChar(getFirstBoolean());
        }
        default double getFirstDouble(){
            return TypeUtil.castToDouble(getFirstBoolean());
        }
        default float getFirstFloat(){
            return TypeUtil.castToFloat(getFirstBoolean());
        }
        default int getFirstInt(){
            return TypeUtil.castToByte(getFirstBoolean());
        }
        default long getFirstLong(){
            return TypeUtil.castToLong(getFirstBoolean());
        }
        default short getFirstShort(){
            return TypeUtil.castToByte(getFirstBoolean());
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
        boolean peekFirstBoolean();
        byte peekFirstByte();
        char peekFirstChar();
        double peekFirstDouble();
        float peekFirstFloat();
        int peekFirstInt();
        long peekFirstLong();
        short peekFirstShort();
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
        boolean pollFirstBoolean();
        byte pollFirstByte();
        char pollFirstChar();
        double pollFirstDouble();
        float pollFirstFloat();
        int pollFirstInt();
        long pollFirstLong();
        short pollFirstShort();
        Boolean pollLast();
        boolean pollLastBoolean();
        byte pollLastByte();
        char pollLastChar();
        double pollLastDouble();
        float pollLastFloat();
        int pollLastInt();
        long pollLastLong();
        short pollLastShort();
        Boolean removeFirst();
        boolean removeFirstBoolean();
        default byte removeFirstByte(){
            return TypeUtil.castToByte(removeFirstBoolean());
        }
        default char removeFirstChar(){
            return TypeUtil.castToChar(removeFirstBoolean());
        }
        default double removeFirstDouble(){
            return TypeUtil.castToDouble(removeFirstBoolean());
        }
        default float removeFirstFloat(){
            return TypeUtil.castToFloat(removeFirstBoolean());
        }
        default int removeFirstInt(){
            return TypeUtil.castToByte(removeFirstBoolean());
        }
        default long removeFirstLong(){
            return TypeUtil.castToLong(removeFirstBoolean());
        }
        default short removeFirstShort(){
            return TypeUtil.castToByte(removeFirstBoolean());
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
        default short removeLastShort(){
            return TypeUtil.castToByte(removeLastBoolean());
        }
        @Override default boolean removeFirstOccurrence(byte val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeFirstOccurrence(char val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeFirstOccurrence(short val){
            return removeFirstOccurrence((int)val);
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
    interface OfByte
            extends
            OmniCollection,
            OmniCollection.OfPrimitive,
            OmniCollection.OfByte,
            PeekAndPollIfc<Byte>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            PeekAndPollIfc.IntInput,
            PeekAndPollIfc.ShortInput,
            PeekAndPollIfc.ByteInput,
            OmniStack,
            OmniStack.OfPrimitive,
            OmniStack.OfByte,
            OmniQueue,
            OmniQueue.OfByte,
            OmniDeque,
            OfPrimitive{
        @Override default boolean removeFirstOccurrence(short val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeLastOccurrence(short val){
            return removeLastOccurrence((int)val);
        }
        default void addFirst(boolean val){
            addFirst(TypeUtil.castToByte(val));
        }
        default void addFirst(Boolean val){
            addFirst(TypeUtil.castToByte(val.booleanValue()));
        }
        void addFirst(byte val);
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
        byte getFirstByte();
        default double getFirstDouble(){
            return getFirstByte();
        }
        default float getFirstFloat(){
            return getFirstByte();
        }
        default int getFirstInt(){
            return getFirstByte();
        }
        default long getFirstLong(){
            return getFirstByte();
        }
        default short getFirstShort(){
            return getFirstByte();
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
        byte peekFirstByte();
        double peekFirstDouble();
        float peekFirstFloat();
        int peekFirstInt();
        long peekFirstLong();
        short peekFirstShort();
        Byte peekLast();
        byte peekLastByte();
        double peekLastDouble();
        float peekLastFloat();
        int peekLastInt();
        long peekLastLong();
        short peekLastShort();
        Byte pollFirst();
        byte pollFirstByte();
        double pollFirstDouble();
        float pollFirstFloat();
        int pollFirstInt();
        long pollFirstLong();
        short pollFirstShort();
        Byte pollLast();
        byte pollLastByte();
        double pollLastDouble();
        float pollLastFloat();
        int pollLastInt();
        long pollLastLong();
        short pollLastShort();
        Byte removeFirst();
        byte removeFirstByte();
        default double removeFirstDouble(){
            return removeFirstByte();
        }
        default float removeFirstFloat(){
            return removeFirstByte();
        }
        default int removeFirstInt(){
            return removeFirstByte();
        }
        default long removeFirstLong(){
            return removeFirstByte();
        }
        default short removeFirstShort(){
            return removeFirstByte();
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
        default short removeLastShort(){
            return removeLastByte();
        }
    }
    interface OfChar
            extends
            OmniCollection,
            OmniCollection.OfPrimitive,
            OmniCollection.OfChar,
            PeekAndPollIfc<Character>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            PeekAndPollIfc.IntInput,
            PeekAndPollIfc.CharInput,
            OmniStack,
            OmniStack.OfPrimitive,
            OmniStack.OfChar,
            OmniQueue,
            OmniQueue.OfChar,
            OmniDeque,
            OfPrimitive{
        @Override default boolean removeFirstOccurrence(byte val){
            return removeFirstOccurrence((short)val);
        }
        @Override default boolean removeLastOccurrence(byte val){
            return removeLastOccurrence((short)val);
        }
        default void addFirst(boolean val){
            addFirst(TypeUtil.castToChar(val));
        }
        default void addFirst(Boolean val){
            addFirst(TypeUtil.castToChar(val.booleanValue()));
        }
        void addFirst(char val);
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
        char getFirstChar();
        default double getFirstDouble(){
            return getFirstChar();
        }
        default float getFirstFloat(){
            return getFirstChar();
        }
        default int getFirstInt(){
            return getFirstChar();
        }
        default long getFirstLong(){
            return getFirstChar();
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
        char peekFirstChar();
        double peekFirstDouble();
        float peekFirstFloat();
        int peekFirstInt();
        long peekFirstLong();
        Character peekLast();
        char peekLastChar();
        double peekLastDouble();
        float peekLastFloat();
        int peekLastInt();
        long peekLastLong();
        Character pollFirst();
        char pollFirstChar();
        double pollFirstDouble();
        float pollFirstFloat();
        int pollFirstInt();
        long pollFirstLong();
        Character pollLast();
        char pollLastChar();
        double pollLastDouble();
        float pollLastFloat();
        int pollLastInt();
        long pollLastLong();
        Character removeFirst();
        char removeFirstChar();
        default double removeFirstDouble(){
            return removeFirstChar();
        }
        default float removeFirstFloat(){
            return removeFirstChar();
        }
        default int removeFirstInt(){
            return removeFirstChar();
        }
        default long removeFirstLong(){
            return removeFirstChar();
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
    }
    interface OfDouble
            extends
            OmniCollection,
            OmniCollection.OfPrimitive,
            OmniCollection.OfDouble,
            PeekAndPollIfc<Double>,
            PeekAndPollIfc.DoubleInput,
            OmniStack,
            OmniStack.OfPrimitive,
            OmniStack.OfDouble,
            OmniQueue,
            OmniQueue.OfDouble,
            OmniDeque,
            OfPrimitive{
        @Override default boolean removeFirstOccurrence(byte val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeLastOccurrence(byte val){
            return removeLastOccurrence((int)val);
        }
        @Override default boolean removeFirstOccurrence(char val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeLastOccurrence(char val){
            return removeLastOccurrence((int)val);
        }
        @Override default boolean removeFirstOccurrence(short val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeLastOccurrence(short val){
            return removeLastOccurrence((int)val);
        }
        default void addFirst(boolean val){
            addFirst(TypeUtil.castToDouble(val));
        }
        default void addFirst(Boolean val){
            addFirst(TypeUtil.castToDouble(val.booleanValue()));
        }
        default void addFirst(byte val){
            addFirst((double)val);
        }
        default void addFirst(Byte val){
            addFirst(val.doubleValue());
        }
        default void addFirst(char val){
            addFirst((double)val);
        }
        default void addFirst(Character val){
            addFirst((double)val.charValue());
        }
        void addFirst(double val);
        void addFirst(Double val);
        default void addFirst(float val){
            addFirst((double)val);
        }
        default void addFirst(Float val){
            addFirst(val.doubleValue());
        }
        default void addFirst(int val){
            addFirst((double)val);
        }
        default void addFirst(Integer val){
            addFirst(val.doubleValue());
        }
        default void addFirst(long val){
            addFirst((double)val);
        }
        default void addFirst(Long val){
            addFirst(val.doubleValue());
        }
        default void addFirst(short val){
            addFirst((double)val);
        }
        default void addFirst(Short val){
            addFirst(val.doubleValue());
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
        double getFirstDouble();
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
        double peekFirstDouble();
        Double peekLast();
        double peekLastDouble();
        Double pollFirst();
        double pollFirstDouble();
        Double pollLast();
        double pollLastDouble();
        Double removeFirst();
        double removeFirstDouble();
        Double removeLast();
        double removeLastDouble();
    }
    interface OfFloat
            extends
            OmniCollection,
            OmniCollection.OfPrimitive,
            OmniCollection.OfFloat,
            PeekAndPollIfc<Float>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            OmniStack,
            OmniStack.OfPrimitive,
            OmniStack.OfFloat,
            OmniQueue,
            OmniQueue.OfFloat,
            OmniDeque,
            OfPrimitive{
        @Override default boolean removeFirstOccurrence(byte val){
            return removeFirstOccurrence((short)val);
        }
        @Override default boolean removeLastOccurrence(byte val){
            return removeLastOccurrence((short)val);
        }
        default void addFirst(boolean val){
            addFirst(TypeUtil.castToFloat(val));
        }
        default void addFirst(Boolean val){
            addFirst(TypeUtil.castToFloat(val.booleanValue()));
        }
        default void addFirst(byte val){
            addFirst((float)val);
        }
        default void addFirst(Byte val){
            addFirst(val.floatValue());
        }
        default void addFirst(char val){
            addFirst((float)val);
        }
        default void addFirst(Character val){
            addFirst((float)val.charValue());
        }
        void addFirst(float val);
        void addFirst(Float val);
        default void addFirst(int val){
            addFirst((float)val);
        }
        default void addFirst(Integer val){
            addFirst(val.floatValue());
        }
        default void addFirst(long val){
            addFirst((float)val);
        }
        default void addFirst(Long val){
            addFirst(val.floatValue());
        }
        default void addFirst(short val){
            addFirst((float)val);
        }
        default void addFirst(Short val){
            addFirst(val.floatValue());
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
            return getFirstFloat();
        }
        float getFirstFloat();
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
        double peekFirstDouble();
        float peekFirstFloat();
        Float peekLast();
        double peekLastDouble();
        float peekLastFloat();
        Float pollFirst();
        double pollFirstDouble();
        float pollFirstFloat();
        Float pollLast();
        double pollLastDouble();
        float pollLastFloat();
        Float removeFirst();
        default double removeFirstDouble(){
            return removeFirstFloat();
        }
        float removeFirstFloat();
        Float removeLast();
        default double removeLastDouble(){
            return removeLastFloat();
        }
        float removeLastFloat();
    }
    interface OfInt
            extends
            OmniCollection,
            OmniCollection.OfPrimitive,
            OmniCollection.OfInt,
            PeekAndPollIfc<Integer>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            PeekAndPollIfc.IntInput,
            OmniStack,
            OmniStack.OfPrimitive,
            OmniStack.OfInt,
            OmniQueue,
            OmniQueue.OfInt,
            OmniDeque,
            OfPrimitive{
        @Override default boolean removeFirstOccurrence(byte val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeLastOccurrence(byte val){
            return removeLastOccurrence((int)val);
        }
        @Override default boolean removeFirstOccurrence(char val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeLastOccurrence(char val){
            return removeLastOccurrence((int)val);
        }
        @Override default boolean removeFirstOccurrence(short val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeLastOccurrence(short val){
            return removeLastOccurrence((int)val);
        }
        default void addFirst(boolean val){
            addFirst((int)TypeUtil.castToByte(val));
        }
        default void addFirst(Boolean val){
            addFirst((int)TypeUtil.castToByte(val.booleanValue()));
        }
        default void addFirst(byte val){
            addFirst((int)val);
        }
        default void addFirst(Byte val){
            addFirst(val.intValue());
        }
        default void addFirst(char val){
            addFirst((int)val);
        }
        default void addFirst(Character val){
            addFirst((int)val.charValue());
        }
        void addFirst(int val);
        void addFirst(Integer val);
        default void addFirst(short val){
            addFirst((int)val);
        }
        default void addFirst(Short val){
            addFirst(val.intValue());
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
            return getFirstInt();
        }
        default float getFirstFloat(){
            return getFirstInt();
        }
        int getFirstInt();
        default long getFirstLong(){
            return getFirstInt();
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
        double peekFirstDouble();
        float peekFirstFloat();
        int peekFirstInt();
        long peekFirstLong();
        Integer peekLast();
        double peekLastDouble();
        float peekLastFloat();
        int peekLastInt();
        long peekLastLong();
        Integer pollFirst();
        double pollFirstDouble();
        float pollFirstFloat();
        int pollFirstInt();
        long pollFirstLong();
        Integer pollLast();
        double pollLastDouble();
        float pollLastFloat();
        int pollLastInt();
        long pollLastLong();
        Integer removeFirst();
        default double removeFirstDouble(){
            return removeFirstInt();
        }
        default float removeFirstFloat(){
            return removeFirstInt();
        }
        int removeFirstInt();
        default long removeFirstLong(){
            return removeFirstInt();
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
    }
    interface OfLong
            extends
            OmniCollection,
            OmniCollection.OfPrimitive,
            OmniCollection.OfLong,
            PeekAndPollIfc<Long>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            OmniStack,
            OmniStack.OfPrimitive,
            OmniStack.OfLong,
            OmniQueue,
            OmniQueue.OfLong,
            OmniDeque,
            OfPrimitive{
        @Override default boolean removeFirstOccurrence(byte val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeLastOccurrence(byte val){
            return removeLastOccurrence((int)val);
        }
        @Override default boolean removeFirstOccurrence(char val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeLastOccurrence(char val){
            return removeLastOccurrence((int)val);
        }
        @Override default boolean removeFirstOccurrence(short val){
            return removeFirstOccurrence((int)val);
        }
        @Override default boolean removeLastOccurrence(short val){
            return removeLastOccurrence((int)val);
        }
        default void addFirst(boolean val){
            addFirst(TypeUtil.castToLong(val));
        }
        default void addFirst(Boolean val){
            addFirst(TypeUtil.castToLong(val.booleanValue()));
        }
        default void addFirst(byte val){
            addFirst((long)val);
        }
        default void addFirst(Byte val){
            addFirst(val.longValue());
        }
        default void addFirst(char val){
            addFirst((long)val);
        }
        default void addFirst(Character val){
            addFirst((long)val.charValue());
        }
        default void addFirst(int val){
            addFirst((long)val);
        }
        default void addFirst(Integer val){
            addFirst(val.longValue());
        }
        void addFirst(long val);
        void addFirst(Long val);
        default void addFirst(short val){
            addFirst((long)val);
        }
        default void addFirst(Short val){
            addFirst(val.longValue());
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
            return getFirstLong();
        }
        default float getFirstFloat(){
            return getFirstLong();
        }
        long getFirstLong();
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
        double peekFirstDouble();
        float peekFirstFloat();
        long peekFirstLong();
        Long peekLast();
        double peekLastDouble();
        float peekLastFloat();
        long peekLastLong();
        Long pollFirst();
        double pollFirstDouble();
        float pollFirstFloat();
        long pollFirstLong();
        Long pollLast();
        double pollLastDouble();
        float pollLastFloat();
        long pollLastLong();
        Long removeFirst();
        default double removeFirstDouble(){
            return removeFirstLong();
        }
        default float removeFirstFloat(){
            return removeFirstLong();
        }
        long removeFirstLong();
        Long removeLast();
        default double removeLastDouble(){
            return removeLastLong();
        }
        default float removeLastFloat(){
            return removeLastLong();
        }
        long removeLastLong();
    }
    interface OfPrimitive
            extends
            OmniCollection,
            OmniCollection.OfPrimitive,
            OmniStack,
            OmniQueue,
            OmniStack.OfPrimitive,
            OmniDeque{
        @Override default boolean removeFirstOccurrence(Boolean val){
            return val!=null&&removeFirstOccurrence(val.booleanValue());
        }
        @Override default boolean removeFirstOccurrence(Byte val){
            return val!=null&&removeFirstOccurrence(val.byteValue());
        }
        @Override default boolean removeFirstOccurrence(Character val){
            return val!=null&&removeFirstOccurrence(val.charValue());
        }
        @Override default boolean removeFirstOccurrence(Double val){
            return val!=null&&removeFirstOccurrence(val.doubleValue());
        }
        @Override default boolean removeFirstOccurrence(Float val){
            return val!=null&&removeFirstOccurrence(val.floatValue());
        }
        @Override default boolean removeFirstOccurrence(Integer val){
            return val!=null&&removeFirstOccurrence(val.intValue());
        }
        @Override default boolean removeFirstOccurrence(Long val){
            return val!=null&&removeFirstOccurrence(val.longValue());
        }
        @Override default boolean removeFirstOccurrence(Short val){
            return val!=null&&removeFirstOccurrence(val.shortValue());
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
    interface OfRef<E>
            extends
            OmniCollection,
            OmniCollection.OfRef<E>,
            PeekAndPollIfc<E>,
            OmniStack,
            OmniStack.OfRef<E>,
            OmniQueue,
            OmniQueue.OfRef<E>,
            OmniDeque{
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
        E removeLast();
    }
    interface OfShort
            extends
            OmniCollection,
            OmniCollection.OfPrimitive,
            OmniCollection.OfShort,
            PeekAndPollIfc<Short>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            PeekAndPollIfc.IntInput,
            PeekAndPollIfc.ShortInput,
            OmniStack,
            OmniStack.OfPrimitive,
            OmniStack.OfShort,
            OmniQueue,
            OmniQueue.OfShort,
            OmniDeque,
            OfPrimitive{
        @Override default boolean removeFirstOccurrence(byte val){
            return removeFirstOccurrence((short)val);
        }
        @Override default boolean removeLastOccurrence(byte val){
            return removeLastOccurrence((short)val);
        }
        default void addFirst(boolean val){
            addFirst((short)TypeUtil.castToByte(val));
        }
        default void addFirst(Boolean val){
            addFirst((short)TypeUtil.castToByte(val.booleanValue()));
        }
        default void addFirst(byte val){
            addFirst((short)val);
        }
        default void addFirst(Byte val){
            addFirst(val.shortValue());
        }
        void addFirst(short val);
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
            return getFirstShort();
        }
        default float getFirstFloat(){
            return getFirstShort();
        }
        default int getFirstInt(){
            return getFirstShort();
        }
        default long getFirstLong(){
            return getFirstShort();
        }
        short getFirstShort();
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
        double peekFirstDouble();
        float peekFirstFloat();
        int peekFirstInt();
        long peekFirstLong();
        short peekFirstShort();
        Short peekLast();
        double peekLastDouble();
        float peekLastFloat();
        int peekLastInt();
        long peekLastLong();
        short peekLastShort();
        Short pollFirst();
        double pollFirstDouble();
        float pollFirstFloat();
        int pollFirstInt();
        long pollFirstLong();
        short pollFirstShort();
        Short pollLast();
        double pollLastDouble();
        float pollLastFloat();
        int pollLastInt();
        long pollLastLong();
        short pollLastShort();
        Short removeFirst();
        default double removeFirstDouble(){
            return removeFirstShort();
        }
        default float removeFirstFloat(){
            return removeFirstShort();
        }
        default int removeFirstInt(){
            return removeFirstShort();
        }
        default long removeFirstLong(){
            return removeFirstShort();
        }
        short removeFirstShort();
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
        short removeLastShort();
    }
}
