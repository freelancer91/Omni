package omni.codegen;
import java.util.ArrayList;
import java.util.List;
enum TypeDefinition{
    OfRef(getOfRef()),OfDouble(getOfDouble()),OfFloat(getOfFloat()),OfLong(getOfLong()),OfInt(getOfInt()),
    OfShort(getOfShort()),OfChar(getOfChar()),OfByte(getOfByte()),OfBoolean(getOfBoolean());
    final List<DefVar> definitionVars;
    private static List<DefVar> getOfBoolean(){
        final List<DefVar> defVars=new ArrayList<>();
        defVars.add(new DefVar("<E>",""));
        defVars.add(new DefVar("BoxedType","Boolean"));
        defVars.add(new DefVar("TypeNameModifier","Boolean"));
        defVars.add(new DefVar("ClassPrefix","Boolean"));
        defVars.add(new DefVar("exposedType","boolean"));
        defVars.add(new DefVar("removeAtIndexMethod","removeBooleanAt"));
        defVars.add(new DefVar("AbstractListextends",""));
        defVars.add(new DefVar("ArrayType","boolean"));
        defVars.add(new DefVar("<\\? super E>",""));
        defVars.add(new DefVar("queryParameterType","boolean"));
        defVars.add(new DefVar("castToByte","TypeUtil.castToByte"));
        defVars.add(new DefVar("castToChar","TypeUtil.castToChar"));
        defVars.add(new DefVar("castToShort","(short)TypeUtil.castToByte"));
        defVars.add(new DefVar("castToInt","(int)TypeUtil.castToByte"));
        defVars.add(new DefVar("castToLong","TypeUtil.castToLong"));
        defVars.add(new DefVar("castToFloat","TypeUtil.castToFloat"));
        defVars.add(new DefVar("castToDouble","TypeUtil.castToDouble"));
        defVars.add(new DefVar("queryCastBoolean",""));
        defVars.add(new DefVar("queryCastRef","(boolean)"));
        defVars.add(new DefVar("defaultVal","false"));
        defVars.add(new DefVar("hashCodeMethod","Boolean.hashCode"));
        defVars.add(new DefVar("elementMethod","booleanElement"));
        defVars.add(new DefVar("Comparator","BooleanComparator"));
        defVars.add(new DefVar("UnaryOperator","BooleanPredicate"));
        defVars.add(new DefVar("applyMethod","test"));
        defVars.add(new DefVar("compareMethod","compare"));
        return defVars;
    }
    private static List<DefVar> getOfByte(){
        final List<DefVar> defVars=new ArrayList<>();
        defVars.add(new DefVar("<E>",""));
        defVars.add(new DefVar("BoxedType","Byte"));
        defVars.add(new DefVar("TypeNameModifier","Byte"));
        defVars.add(new DefVar("ClassPrefix","Byte"));
        defVars.add(new DefVar("exposedType","byte"));
        defVars.add(new DefVar("removeAtIndexMethod","removeByteAt"));
        defVars.add(new DefVar("AbstractListextends",""));
        defVars.add(new DefVar("ArrayType","byte"));
        defVars.add(new DefVar("<\\? super E>",""));
        defVars.add(new DefVar("queryParameterType","int"));
        defVars.add(new DefVar("castToShort","(short)"));
        defVars.add(new DefVar("castToInt","(int)"));
        defVars.add(new DefVar("castToLong","(long)"));
        defVars.add(new DefVar("castToFloat","(float)"));
        defVars.add(new DefVar("castToDouble","(double)"));
        defVars.add(new DefVar("queryCastBoolean","TypeUtil.castToByte"));
        defVars.add(new DefVar("queryCastPrimitive",""));
        defVars.add(new DefVar("queryCastRef","(byte)"));
        defVars.add(new DefVar("defaultVal","Byte.MIN_VALUE"));
        defVars.add(new DefVar("hashCodeMethod",""));
        defVars.add(new DefVar("elementMethod","byteElement"));
        defVars.add(new DefVar("Comparator","ByteComparator"));
        defVars.add(new DefVar("UnaryOperator","ByteUnaryOperator"));
        defVars.add(new DefVar("applyMethod","applyAsByte"));
        defVars.add(new DefVar("compareMethod","compare"));
        return defVars;
    }
    private static List<DefVar> getOfChar(){
        final List<DefVar> defVars=new ArrayList<>();
        defVars.add(new DefVar("<E>",""));
        defVars.add(new DefVar("BoxedType","Character"));
        defVars.add(new DefVar("TypeNameModifier","Char"));
        defVars.add(new DefVar("ClassPrefix","Char"));
        defVars.add(new DefVar("exposedType","char"));
        defVars.add(new DefVar("removeAtIndexMethod","removeCharAt"));
        defVars.add(new DefVar("AbstractListextends",".Of16BitPrimitive"));
        defVars.add(new DefVar("ArrayType","char"));
        defVars.add(new DefVar("<\\? super E>",""));
        defVars.add(new DefVar("queryParameterType","int"));
        defVars.add(new DefVar("castToInt","(int)"));
        defVars.add(new DefVar("castToLong","(long)"));
        defVars.add(new DefVar("castToFloat","(float)"));
        defVars.add(new DefVar("castToDouble","(double)"));
        defVars.add(new DefVar("queryCastBoolean","TypeUtil.castToChar"));
        defVars.add(new DefVar("queryCastPrimitive",""));
        defVars.add(new DefVar("queryCastRef","(char)"));
        defVars.add(new DefVar("defaultVal","Character.MIN_VALUE"));
        defVars.add(new DefVar("hashCodeMethod",""));
        defVars.add(new DefVar("elementMethod","charElement"));
        defVars.add(new DefVar("Comparator","CharComparator"));
        defVars.add(new DefVar("UnaryOperator","CharUnaryOperator"));
        defVars.add(new DefVar("applyMethod","applyAsChar"));
        defVars.add(new DefVar("compareMethod","compare"));
        return defVars;
    }
    private static List<DefVar> getOfDouble(){
        final List<DefVar> defVars=new ArrayList<>();
        defVars.add(new DefVar("<E>",""));
        defVars.add(new DefVar("BoxedType","Double"));
        defVars.add(new DefVar("TypeNameModifier","Double"));
        defVars.add(new DefVar("ClassPrefix","Double"));
        defVars.add(new DefVar("exposedType","double"));
        defVars.add(new DefVar("removeAtIndexMethod","removeDoubleAt"));
        defVars.add(new DefVar("AbstractListextends",".OfDouble"));
        defVars.add(new DefVar("ArrayType","double"));
        defVars.add(new DefVar("<\\? super E>",""));
        defVars.add(new DefVar("queryParameterType","long"));
        defVars.add(new DefVar("queryCastBoolean","TypeUtil.castToDouble"));
        defVars.add(new DefVar("queryCastPrimitive",""));
        defVars.add(new DefVar("queryCastRef","(double)"));
        defVars.add(new DefVar("TRUE_BITS","TypeUtil.DBL_TRUE_BITS"));
        defVars.add(new DefVar("convertToBits","Double.doubleToRawLongBits"));
        defVars.add(new DefVar("defaultVal","Double.NaN"));
        defVars.add(new DefVar("hashCodeMethod","HashUtils.hashDouble"));
        defVars.add(new DefVar("elementMethod","doubleElement"));
        defVars.add(new DefVar("Comparator","DoubleComparator"));
        defVars.add(new DefVar("UnaryOperator","DoubleUnaryOperator"));
        defVars.add(new DefVar("applyMethod","applyAsDouble"));
        defVars.add(new DefVar("compareMethod","compare"));
        return defVars;
    }
    private static List<DefVar> getOfFloat(){
        final List<DefVar> defVars=new ArrayList<>();
        defVars.add(new DefVar("<E>",""));
        defVars.add(new DefVar("BoxedType","Float"));
        defVars.add(new DefVar("TypeNameModifier","Float"));
        defVars.add(new DefVar("ClassPrefix","Float"));
        defVars.add(new DefVar("exposedType","float"));
        defVars.add(new DefVar("removeAtIndexMethod","removeFloatAt"));
        defVars.add(new DefVar("AbstractListextends",".OfFloat"));
        defVars.add(new DefVar("ArrayType","float"));
        defVars.add(new DefVar("<\\? super E>",""));
        defVars.add(new DefVar("queryParameterType","int"));
        defVars.add(new DefVar("castToDouble","(double)"));
        defVars.add(new DefVar("queryCastBoolean","TypeUtil.castToFloat"));
        defVars.add(new DefVar("queryCastPrimitive",""));
        defVars.add(new DefVar("queryCastRef","(float)"));
        defVars.add(new DefVar("TRUE_BITS","TypeUtil.FLT_TRUE_BITS"));
        defVars.add(new DefVar("convertToBits","Float.floatToRawIntBits"));
        defVars.add(new DefVar("defaultVal","Float.NaN"));
        defVars.add(new DefVar("hashCodeMethod","HashUtils.hashFloat"));
        defVars.add(new DefVar("elementMethod","floatElement"));
        defVars.add(new DefVar("Comparator","FloatComparator"));
        defVars.add(new DefVar("UnaryOperator","FloatUnaryOperator"));
        defVars.add(new DefVar("applyMethod","applyAsFloat"));
        defVars.add(new DefVar("compareMethod","compare"));
        return defVars;
    }
    private static List<DefVar> getOfInt(){
        final List<DefVar> defVars=new ArrayList<>();
        defVars.add(new DefVar("<E>",""));
        defVars.add(new DefVar("BoxedType","Integer"));
        defVars.add(new DefVar("TypeNameModifier","Int"));
        defVars.add(new DefVar("ClassPrefix","Int"));
        defVars.add(new DefVar("exposedType","int"));
        defVars.add(new DefVar("removeAtIndexMethod","removeIntAt"));
        defVars.add(new DefVar("AbstractListextends",".OfSignedIntegralPrimitive"));
        defVars.add(new DefVar("ArrayType","int"));
        defVars.add(new DefVar("<\\? super E>",""));
        defVars.add(new DefVar("queryParameterType","int"));
        defVars.add(new DefVar("castToLong","(long)"));
        defVars.add(new DefVar("castToFloat","(float)"));
        defVars.add(new DefVar("castToDouble","(double)"));
        defVars.add(new DefVar("queryCastBoolean","(int)TypeUtil.castToByte"));
        defVars.add(new DefVar("queryCastPrimitive",""));
        defVars.add(new DefVar("queryCastRef","(int)"));
        defVars.add(new DefVar("defaultVal","Integer.MIN_VALUE"));
        defVars.add(new DefVar("hashCodeMethod",""));
        defVars.add(new DefVar("elementMethod","intElement"));
        defVars.add(new DefVar("Comparator","IntBinaryOperator"));
        defVars.add(new DefVar("UnaryOperator","IntUnaryOperator"));
        defVars.add(new DefVar("applyMethod","applyAsInt"));
        defVars.add(new DefVar("compareMethod","applyAsInt"));
        return defVars;
    }
    private static List<DefVar> getOfLong(){
        final List<DefVar> defVars=new ArrayList<>();
        defVars.add(new DefVar("<E>",""));
        defVars.add(new DefVar("BoxedType","Long"));
        defVars.add(new DefVar("TypeNameModifier","Long"));
        defVars.add(new DefVar("ClassPrefix","Long"));
        defVars.add(new DefVar("exposedType","long"));
        defVars.add(new DefVar("removeAtIndexMethod","removeLongAt"));
        defVars.add(new DefVar("AbstractListextends",".OfSignedIntegralPrimitive"));
        defVars.add(new DefVar("ArrayType","long"));
        defVars.add(new DefVar("<\\? super E>",""));
        defVars.add(new DefVar("queryParameterType","long"));
        defVars.add(new DefVar("castToFloat","(float)"));
        defVars.add(new DefVar("castToDouble","(double)"));
        defVars.add(new DefVar("queryCastBoolean","TypeUtil.castToLong"));
        defVars.add(new DefVar("queryCastPrimitive",""));
        defVars.add(new DefVar("queryCastRef","(long)"));
        defVars.add(new DefVar("defaultVal","Long.MIN_VALUE"));
        defVars.add(new DefVar("hashCodeMethod","Long.hashCode"));
        defVars.add(new DefVar("elementMethod","longElement"));
        defVars.add(new DefVar("Comparator","LongComparator"));
        defVars.add(new DefVar("UnaryOperator","LongUnaryOperator"));
        defVars.add(new DefVar("applyMethod","applyAsLong"));
        defVars.add(new DefVar("compareMethod","compare"));
        return defVars;
    }
    private static List<DefVar> getOfRef(){
        final List<DefVar> defVars=new ArrayList<>();
        defVars.add(new DefVar("<E>","<E>"));
        defVars.add(new DefVar("BoxedType","E"));
        defVars.add(new DefVar("TypeNameModifier",""));
        defVars.add(new DefVar("ClassPrefix","Ref"));
        defVars.add(new DefVar("exposedType","E"));
        defVars.add(new DefVar("removeAtIndexMethod","remove"));
        defVars.add(new DefVar("ArrayType","Object"));
        defVars.add(new DefVar("<\\? super E>","<? super E>"));
        defVars.add(new DefVar("queryCastBoolean","OmniPred.OfRef.getEqualsPred"));
        defVars.add(new DefVar("queryCastPrimitive","OmniPred.OfRef.getEqualsPred"));
        defVars.add(new DefVar("queryCastRef","OmniPred.OfRef.getEqualsPred"));
        defVars.add(new DefVar("defaultVal","null"));
        defVars.add(new DefVar("hashCodeMethod","Objects.hashCode"));
        defVars.add(new DefVar("elementMethod","element"));
        defVars.add(new DefVar("Comparator","Comparator"));
        defVars.add(new DefVar("UnaryOperator","UnaryOperator"));
        defVars.add(new DefVar("applyMethod","apply"));
        defVars.add(new DefVar("compareMethod","compare"));
        return defVars;
    }
    private static List<DefVar> getOfShort(){
        final List<DefVar> defVars=new ArrayList<>();
        defVars.add(new DefVar("<E>",""));
        defVars.add(new DefVar("BoxedType","Short"));
        defVars.add(new DefVar("TypeNameModifier","Short"));
        defVars.add(new DefVar("ClassPrefix","Short"));
        defVars.add(new DefVar("exposedType","short"));
        defVars.add(new DefVar("removeAtIndexMethod","removeShortAt"));
        defVars.add(new DefVar("AbstractListextends",".Of16BitPrimitive"));
        defVars.add(new DefVar("ArrayType","short"));
        defVars.add(new DefVar("<\\? super E>",""));
        defVars.add(new DefVar("queryParameterType","int"));
        defVars.add(new DefVar("castToInt","(int)"));
        defVars.add(new DefVar("castToLong","(long)"));
        defVars.add(new DefVar("castToFloat","(float)"));
        defVars.add(new DefVar("castToDouble","(double)"));
        defVars.add(new DefVar("queryCastBoolean","(short)TypeUtil.castToByte"));
        defVars.add(new DefVar("queryCastPrimitive",""));
        defVars.add(new DefVar("queryCastRef","(short)"));
        defVars.add(new DefVar("defaultVal","Short.MIN_VALUE"));
        defVars.add(new DefVar("hashCodeMethod",""));
        defVars.add(new DefVar("elementMethod","shortElement"));
        defVars.add(new DefVar("Comparator","ShortComparator"));
        defVars.add(new DefVar("UnaryOperator","ShortUnaryOperator"));
        defVars.add(new DefVar("applyMethod","applyAsShort"));
        defVars.add(new DefVar("compareMethod","compare"));
        return defVars;
    }
    boolean matchTypeDef(String...args){
        int i=0;
        final int numArgs=args.length;
        String name=this.name();
        do{
            if(name.equals(args[i])){
                return true;
            }
        }while(++i!=numArgs);
        return false;
    }
    private TypeDefinition(List<DefVar> definitionVars){
        this.definitionVars=definitionVars;
    }
    String parseLine(String line){
        for(final var defVar:definitionVars){
            line=defVar.replace(line);
        }
        return line;
    }
    static class DefVar{
        public final String key;
        public final String val;
        private int numUses;
        DefVar(String key,String val){
            this.key=key;
            this.val=val;
        }
        public int getNumUses(){
            return numUses;
        }
        public String replace(String line){
            final String after=line.replaceAll("\\$"+key+"\\$",val);
            if(!line.equals(after)){
                ++numUses;
            }
            return after;
        }
        @Override public String toString(){
            return "{key = "+key+"; val = "+val+"; numUses = "+numUses+"}";
        }
    }
}