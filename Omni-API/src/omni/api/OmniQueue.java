package omni.api;
import omni.util.PeekAndPollIfc;
import omni.util.TypeUtil;
public interface OmniQueue extends OmniCollection{
    interface OfBoolean
            extends
            OmniQueue,
            OmniCollection.OfBoolean,
            PeekAndPollIfc<Boolean>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            PeekAndPollIfc.IntInput,
            PeekAndPollIfc.ShortInput,
            PeekAndPollIfc.CharInput,
            PeekAndPollIfc.ByteInput,
            PeekAndPollIfc.BooleanInput{
        boolean booleanElement();
        default byte byteElement(){
            return TypeUtil.castToByte(booleanElement());
        }
        default char charElement(){
            return TypeUtil.castToChar(booleanElement());
        }
        default double doubleElement(){
            return TypeUtil.castToDouble(booleanElement());
        }
        Boolean element();
        default float floatElement(){
            return TypeUtil.castToFloat(booleanElement());
        }
        default int intElement(){
            return TypeUtil.castToByte(booleanElement());
        }
        default long longElement(){
            return TypeUtil.castToLong(booleanElement());
        }
        boolean offer(boolean val);
        boolean offer(Boolean val);
        Boolean remove();
        boolean removeBoolean();
        default byte removeByte(){
            return TypeUtil.castToByte(removeBoolean());
        }
        default char removeChar(){
            return TypeUtil.castToChar(removeBoolean());
        }
        default double removeDouble(){
            return TypeUtil.castToDouble(removeBoolean());
        }
        default float removeFloat(){
            return TypeUtil.castToFloat(removeBoolean());
        }
        default int removeInt(){
            return TypeUtil.castToByte(removeBoolean());
        }
        default long removeLong(){
            return TypeUtil.castToLong(removeBoolean());
        }
        default short removeShort(){
            return TypeUtil.castToByte(removeBoolean());
        }
        default short shortElement(){
            return TypeUtil.castToByte(booleanElement());
        }
    }
    interface OfByte
            extends
            OmniQueue,
            OmniCollection.OfByte,
            PeekAndPollIfc<Byte>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            PeekAndPollIfc.IntInput,
            PeekAndPollIfc.ShortInput,
            PeekAndPollIfc.ByteInput{
        byte byteElement();
        default double doubleElement(){
            return byteElement();
        }
        Byte element();
        default float floatElement(){
            return byteElement();
        }
        default int intElement(){
            return byteElement();
        }
        default long longElement(){
            return byteElement();
        }
        default boolean offer(boolean val){
            return offer(TypeUtil.castToByte(val));
        }
        default boolean offer(Boolean val){
            return offer(TypeUtil.castToByte(val.booleanValue()));
        }
        boolean offer(byte val);
        boolean offer(Byte val);
        Byte remove();
        byte removeByte();
        default double removeDouble(){
            return removeByte();
        }
        default float removeFloat(){
            return removeByte();
        }
        default int removeInt(){
            return removeByte();
        }
        default long removeLong(){
            return removeByte();
        }
        default short removeShort(){
            return removeByte();
        }
        default short shortElement(){
            return byteElement();
        }
    }
    interface OfChar
            extends
            OmniQueue,
            OmniCollection.OfChar,
            PeekAndPollIfc<Character>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            PeekAndPollIfc.IntInput,
            PeekAndPollIfc.CharInput{
        char charElement();
        default double doubleElement(){
            return charElement();
        }
        Character element();
        default float floatElement(){
            return charElement();
        }
        default int intElement(){
            return charElement();
        }
        default long longElement(){
            return charElement();
        }
        default boolean offer(boolean val){
            return offer(TypeUtil.castToChar(val));
        }
        default boolean offer(Boolean val){
            return offer(TypeUtil.castToChar(val.booleanValue()));
        }
        boolean offer(char val);
        boolean offer(Character val);
        Character remove();
        char removeChar();
        default double removeDouble(){
            return removeChar();
        }
        default float removeFloat(){
            return removeChar();
        }
        default int removeInt(){
            return removeChar();
        }
        default long removeLong(){
            return removeChar();
        }
    }
    interface OfDouble extends OmniQueue,OmniCollection.OfDouble,PeekAndPollIfc<Double>,PeekAndPollIfc.DoubleInput{
        double doubleElement();
        Double element();
        default boolean offer(boolean val){
            return offer(TypeUtil.castToDouble(val));
        }
        default boolean offer(Boolean val){
            return offer(TypeUtil.castToDouble(val.booleanValue()));
        }
        default boolean offer(byte val){
            return offer((double)val);
        }
        default boolean offer(Byte val){
            return offer(val.doubleValue());
        }
        default boolean offer(char val){
            return offer((double)val);
        }
        default boolean offer(Character val){
            return offer((double)val.charValue());
        }
        boolean offer(double val);
        boolean offer(Double val);
        default boolean offer(float val){
            return offer((double)val);
        }
        default boolean offer(Float val){
            return offer(val.doubleValue());
        }
        default boolean offer(int val){
            return offer((double)val);
        }
        default boolean offer(Integer val){
            return offer(val.doubleValue());
        }
        default boolean offer(long val){
            return offer((double)val);
        }
        default boolean offer(Long val){
            return offer(val.doubleValue());
        }
        default boolean offer(short val){
            return offer((double)val);
        }
        default boolean offer(Short val){
            return offer(val.doubleValue());
        }
        Double remove();
        double removeDouble();
    }
    interface OfFloat
            extends
            OmniQueue,
            OmniCollection.OfFloat,
            PeekAndPollIfc<Float>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput{
        default double doubleElement(){
            return floatElement();
        }
        Float element();
        float floatElement();
        default boolean offer(boolean val){
            return offer(TypeUtil.castToFloat(val));
        }
        default boolean offer(Boolean val){
            return offer(TypeUtil.castToFloat(val.booleanValue()));
        }
        default boolean offer(byte val){
            return offer((float)val);
        }
        default boolean offer(Byte val){
            return offer(val.floatValue());
        }
        default boolean offer(char val){
            return offer((float)val);
        }
        default boolean offer(Character val){
            return offer((float)val.charValue());
        }
        boolean offer(float val);
        boolean offer(Float val);
        default boolean offer(int val){
            return offer((float)val);
        }
        default boolean offer(Integer val){
            return offer(val.floatValue());
        }
        default boolean offer(long val){
            return offer((float)val);
        }
        default boolean offer(Long val){
            return offer(val.floatValue());
        }
        default boolean offer(short val){
            return offer((float)val);
        }
        default boolean offer(Short val){
            return offer(val.floatValue());
        }
        Float remove();
        default double removeDouble(){
            return removeFloat();
        }
        float removeFloat();
    }
    interface OfInt
            extends
            OmniQueue,
            OmniCollection.OfInt,
            PeekAndPollIfc<Integer>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            PeekAndPollIfc.IntInput{
        default double doubleElement(){
            return intElement();
        }
        Integer element();
        default float floatElement(){
            return intElement();
        }
        int intElement();
        default long longElement(){
            return intElement();
        }
        default boolean offer(boolean val){
            return offer((int)TypeUtil.castToByte(val));
        }
        default boolean offer(Boolean val){
            return offer((int)TypeUtil.castToByte(val.booleanValue()));
        }
        default boolean offer(byte val){
            return offer((int)val);
        }
        default boolean offer(Byte val){
            return offer(val.intValue());
        }
        default boolean offer(char val){
            return offer((int)val);
        }
        default boolean offer(Character val){
            return offer((int)val.charValue());
        }
        boolean offer(int val);
        boolean offer(Integer val);
        default boolean offer(short val){
            return offer((int)val);
        }
        default boolean offer(Short val){
            return offer(val.intValue());
        }
        Integer remove();
        default double removeDouble(){
            return removeInt();
        }
        default float removeFloat(){
            return removeInt();
        }
        int removeInt();
        default long removeLong(){
            return removeInt();
        }
    }
    interface OfLong
            extends
            OmniQueue,
            OmniCollection.OfLong,
            PeekAndPollIfc<Long>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput{
        default double doubleElement(){
            return longElement();
        }
        Long element();
        default float floatElement(){
            return longElement();
        }
        long longElement();
        default boolean offer(boolean val){
            return offer(TypeUtil.castToLong(val));
        }
        default boolean offer(Boolean val){
            return offer(TypeUtil.castToLong(val.booleanValue()));
        }
        default boolean offer(byte val){
            return offer((long)val);
        }
        default boolean offer(Byte val){
            return offer(val.longValue());
        }
        default boolean offer(char val){
            return offer((long)val);
        }
        default boolean offer(Character val){
            return offer((long)val.charValue());
        }
        default boolean offer(int val){
            return offer((long)val);
        }
        default boolean offer(Integer val){
            return offer(val.longValue());
        }
        boolean offer(long val);
        boolean offer(Long val);
        default boolean offer(short val){
            return offer((long)val);
        }
        default boolean offer(Short val){
            return offer(val.longValue());
        }
        Long remove();
        default double removeDouble(){
            return removeLong();
        }
        default float removeFloat(){
            return removeLong();
        }
        long removeLong();
    }
    interface OfRef<E>extends OmniQueue,OmniCollection.OfRef<E>,PeekAndPollIfc<E>{
        E element();
        boolean offer(E val);
        E remove();
    }
    interface OfShort
            extends
            OmniQueue,
            OmniCollection.OfShort,
            PeekAndPollIfc<Short>,
            PeekAndPollIfc.DoubleInput,
            PeekAndPollIfc.FloatInput,
            PeekAndPollIfc.LongInput,
            PeekAndPollIfc.IntInput,
            PeekAndPollIfc.ShortInput{
        default double doubleElement(){
            return shortElement();
        }
        Short element();
        default float floatElement(){
            return shortElement();
        }
        default int intElement(){
            return shortElement();
        }
        default long longElement(){
            return shortElement();
        }
        default boolean offer(boolean val){
            return offer((short)TypeUtil.castToByte(val));
        }
        default boolean offer(Boolean val){
            return offer((short)TypeUtil.castToByte(val.booleanValue()));
        }
        default boolean offer(byte val){
            return offer((short)val);
        }
        default boolean offer(Byte val){
            return offer(val.shortValue());
        }
        boolean offer(short val);
        boolean offer(Short val);
        Short remove();
        default double removeDouble(){
            return removeShort();
        }
        default float removeFloat(){
            return removeShort();
        }
        default int removeInt(){
            return removeShort();
        }
        default long removeLong(){
            return removeShort();
        }
        short removeShort();
        short shortElement();
    }
}
