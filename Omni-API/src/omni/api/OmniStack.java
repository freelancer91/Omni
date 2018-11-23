package omni.api;
import omni.util.PeekAndPollIfc;
import omni.util.TypeUtil;
public interface OmniStack extends OmniCollection{
    int search(boolean val);
    int search(Boolean val);
    int search(byte val);
    int search(Byte val);
    int search(char val);
    int search(Character val);
    int search(double val);
    int search(Double val);
    int search(float val);
    int search(Float val);
    int search(int val);
    int search(Integer val);
    int search(long val);
    int search(Long val);
    int search(Object val);
    int search(short val);
    int search(Short val);
    interface OfBoolean
            extends
            OmniCollection,
            OmniStack,
            OmniCollection.OfPrimitive,
            OfPrimitive,
            OmniCollection.OfBoolean,
            PeekAndPollIfc<Boolean>,
            PeekAndPollIfc.DoubleInput,
    PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,PeekAndPollIfc.IntInput,PeekAndPollIfc.ShortInput,
    PeekAndPollIfc.CharInput,PeekAndPollIfc.ByteInput,PeekAndPollIfc.BooleanInput{
        Boolean pop();
        boolean popBoolean();
        default byte popByte(){
            return TypeUtil.castToByte(popBoolean());
        }
        default char popChar(){
            return TypeUtil.castToChar(popBoolean());
        }
        default double popDouble(){
            return TypeUtil.castToDouble(popBoolean());
        }
        default float popFloat(){
            return TypeUtil.castToFloat(popBoolean());
        }
        default int popInt(){
            return TypeUtil.castToByte(popBoolean());
        }
        default long popLong(){
            return TypeUtil.castToLong(popBoolean());
        }
        default short popShort(){
            return TypeUtil.castToByte(popBoolean());
        }
        void push(boolean val);
        void push(Boolean val);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
        @Override default int search(byte val){
            return search((int)val);
        }
        @Override default int search(char val){
            return search((int)val);
        }
        @Override default int search(short val){
            return search((int)val);
        }
    }
    interface OfByte
            extends
            OmniCollection,
            OmniStack,
            OmniCollection.OfPrimitive,
            OfPrimitive,
            OmniCollection.OfByte,
            PeekAndPollIfc<Byte>,
            PeekAndPollIfc.DoubleInput,
    PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,PeekAndPollIfc.IntInput,PeekAndPollIfc.ShortInput,
    PeekAndPollIfc.ByteInput{
        Byte pop();
        byte popByte();
        default double popDouble(){
            return popByte();
        }
        default float popFloat(){
            return popByte();
        }
        default int popInt(){
            return popByte();
        }
        default long popLong(){
            return popByte();
        }
        default short popShort(){
            return popByte();
        }
        default void push(boolean val){
            push(TypeUtil.castToByte(val));
        }
        default void push(Boolean val){
            push(TypeUtil.castToByte(val.booleanValue()));
        }
        void push(byte val);
        void push(Byte val);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
        @Override default int search(short val){
            return search((int)val);
        }
    }
    interface OfChar
            extends
            OmniCollection,
            OmniStack,
            OmniCollection.OfPrimitive,
            OfPrimitive,
            OmniCollection.OfChar,
            PeekAndPollIfc<Character>,
            PeekAndPollIfc.DoubleInput,
    PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,PeekAndPollIfc.IntInput,PeekAndPollIfc.CharInput{
        Character pop();
        char popChar();
        default double popDouble(){
            return popChar();
        }
        default float popFloat(){
            return popChar();
        }
        default int popInt(){
            return popChar();
        }
        default long popLong(){
            return popChar();
        }
        default void push(boolean val){
            push(TypeUtil.castToChar(val));
        }
        default void push(Boolean val){
            push(TypeUtil.castToChar(val.booleanValue()));
        }
        void push(char val);
        void push(Character val);
        @Override default int search(byte val){
            return search((short)val);
        }
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
    interface OfDouble
            extends
            OmniCollection,
            OmniStack,
            OmniCollection.OfPrimitive,
            OfPrimitive,
            OmniCollection.OfDouble,
            PeekAndPollIfc<Double>,
            PeekAndPollIfc.DoubleInput{
        Double pop();
        double popDouble();
        default void push(boolean val){
            push(TypeUtil.castToDouble(val));
        }
        default void push(Boolean val){
            push(TypeUtil.castToDouble(val.booleanValue()));
        }
        default void push(byte val){
            push((double)val);
        }
        default void push(Byte val){
            push(val.doubleValue());
        }
        default void push(char val){
            push((double)val);
        }
        default void push(Character val){
            push((double)val.charValue());
        }
        void push(double val);
        void push(Double val);
        default void push(float val){
            push((double)val);
        }
        default void push(Float val){
            push(val.doubleValue());
        }
        default void push(int val){
            push((double)val);
        }
        default void push(Integer val){
            push(val.doubleValue());
        }
        default void push(long val){
            push((double)val);
        }
        default void push(Long val){
            push(val.doubleValue());
        }
        default void push(short val){
            push((double)val);
        }
        default void push(Short val){
            push(val.doubleValue());
        }
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
        @Override default int search(byte val){
            return search((int)val);
        }
        @Override default int search(char val){
            return search((int)val);
        }
        @Override default int search(short val){
            return search((int)val);
        }
    }
    interface OfFloat
            extends
            OmniCollection,
            OmniStack,
            OmniCollection.OfPrimitive,
            OfPrimitive,
            OmniCollection.OfFloat,
            PeekAndPollIfc<Float>,
            PeekAndPollIfc.DoubleInput,
    PeekAndPollIfc.FloatInput{
        Float pop();
        default double popDouble(){
            return popFloat();
        }
        float popFloat();
        default void push(boolean val){
            push(TypeUtil.castToFloat(val));
        }
        default void push(Boolean val){
            push(TypeUtil.castToFloat(val.booleanValue()));
        }
        default void push(byte val){
            push((float)val);
        }
        default void push(Byte val){
            push(val.floatValue());
        }
        default void push(char val){
            push((float)val);
        }
        default void push(Character val){
            push((float)val.charValue());
        }
        void push(float val);
        void push(Float val);
        default void push(int val){
            push((float)val);
        }
        default void push(Integer val){
            push(val.floatValue());
        }
        default void push(long val){
            push((float)val);
        }
        default void push(Long val){
            push(val.floatValue());
        }
        default void push(short val){
            push((float)val);
        }
        default void push(Short val){
            push(val.floatValue());
        }
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
        @Override default int search(byte val){
            return search((short)val);
        }
    }
    interface OfInt
            extends
            OmniCollection,
            OmniStack,
            OmniCollection.OfPrimitive,
            OfPrimitive,
            OmniCollection.OfInt,
            PeekAndPollIfc<Integer>,
            PeekAndPollIfc.DoubleInput,
    PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,PeekAndPollIfc.IntInput{
        Integer pop();
        default double popDouble(){
            return popInt();
        }
        default float popFloat(){
            return popInt();
        }
        int popInt();
        default long popLong(){
            return popInt();
        }
        default void push(boolean val){
            push((int)TypeUtil.castToByte(val));
        }
        default void push(Boolean val){
            push((int)TypeUtil.castToByte(val.booleanValue()));
        }
        default void push(byte val){
            push((int)val);
        }
        default void push(Byte val){
            push(val.intValue());
        }
        default void push(char val){
            push((int)val);
        }
        default void push(Character val){
            push((int)val.charValue());
        }
        void push(int val);
        void push(Integer val);
        default void push(short val){
            push((int)val);
        }
        default void push(Short val){
            push(val.intValue());
        }
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
        @Override default int search(byte val){
            return search((int)val);
        }
        @Override default int search(char val){
            return search((int)val);
        }
        @Override default int search(short val){
            return search((int)val);
        }
    }
    interface OfLong
            extends
            OmniCollection,
            OmniStack,
            OmniCollection.OfPrimitive,
            OfPrimitive,
            OmniCollection.OfLong,
            PeekAndPollIfc<Long>,
            PeekAndPollIfc.DoubleInput,
    PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput{
        Long pop();
        default double popDouble(){
            return popLong();
        }
        default float popFloat(){
            return popLong();
        }
        long popLong();
        default void push(boolean val){
            push(TypeUtil.castToLong(val));
        }
        default void push(Boolean val){
            push(TypeUtil.castToLong(val.booleanValue()));
        }
        default void push(byte val){
            push((long)val);
        }
        default void push(Byte val){
            push(val.longValue());
        }
        default void push(char val){
            push((long)val);
        }
        default void push(Character val){
            push((long)val.charValue());
        }
        default void push(int val){
            push((long)val);
        }
        default void push(Integer val){
            push(val.longValue());
        }
        void push(long val);
        void push(Long val);
        default void push(short val){
            push((long)val);
        }
        default void push(Short val){
            push(val.longValue());
        }
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
        @Override default int search(byte val){
            return search((int)val);
        }
        @Override default int search(char val){
            return search((int)val);
        }
        @Override default int search(short val){
            return search((int)val);
        }
    }
    interface OfPrimitive extends OmniCollection,OmniStack,OmniCollection.OfPrimitive{
        @Override default int search(Boolean val){
            if(val!=null){ return search(val.booleanValue()); }
            return -1;
        }
        @Override default int search(Byte val){
            if(val!=null){ return search(val.byteValue()); }
            return -1;
        }
        @Override default int search(Character val){
            if(val!=null){ return search(val.charValue()); }
            return -1;
        }
        @Override default int search(Double val){
            if(val!=null){ return search(val.doubleValue()); }
            return -1;
        }
        @Override default int search(Float val){
            if(val!=null){ return search(val.floatValue()); }
            return -1;
        }
        @Override default int search(Integer val){
            if(val!=null){ return search(val.intValue()); }
            return -1;
        }
        @Override default int search(Long val){
            if(val!=null){ return search(val.longValue()); }
            return -1;
        }
        @Override default int search(Short val){
            if(val!=null){ return search(val.shortValue()); }
            return -1;
        }
    }
    interface OfRef<E>extends OmniCollection,OmniStack,OmniCollection.OfRef<E>,PeekAndPollIfc<E>{
        E pop();
        void push(E val);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
    }
    interface OfShort
            extends
            OmniCollection,
            OmniStack,
            OmniCollection.OfPrimitive,
            OfPrimitive,
            OmniCollection.OfShort,
            PeekAndPollIfc<Short>,
            PeekAndPollIfc.DoubleInput,
    PeekAndPollIfc.FloatInput,PeekAndPollIfc.LongInput,PeekAndPollIfc.IntInput,PeekAndPollIfc.ShortInput{
        Short pop();
        default double popDouble(){
            return popShort();
        }
        default float popFloat(){
            return popShort();
        }
        default int popInt(){
            return popShort();
        }
        default long popLong(){
            return popShort();
        }
        short popShort();
        default void push(boolean val){
            push((short)TypeUtil.castToByte(val));
        }
        default void push(Boolean val){
            push((short)TypeUtil.castToByte(val.booleanValue()));
        }
        default void push(byte val){
            push((short)val);
        }
        default void push(Byte val){
            push(val.shortValue());
        }
        void push(short val);
        void push(Short val);
        // TODO spliterator,
        // TODO stream
        // TODO vanillaView
        @Override default int search(byte val){
            return search((short)val);
        }
    }
}
