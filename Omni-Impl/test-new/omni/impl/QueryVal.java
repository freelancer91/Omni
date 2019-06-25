package omni.impl;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import omni.api.OmniCollection;
import omni.util.TypeUtil;
public enum QueryVal{
    // TODO add support for collection modifying non-null Objects
    Null{
        @Override
        public Object getRefVal(){
            return null;
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            return collectionType == DataType.REF;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            if(modification == QueryValModification.None){
                if(inputType == DataType.REF){
                    return castType == QueryCastType.ToObject;
                }
                return castType == QueryCastType.ToBoxed;
            }
            return false;
        }
    },
    Pos0{
        @Override
        public byte getByteValPlus1(){
            return -1;
        }
        @Override
        public short getShortValPlus1(){
            return -1;
        }
        @Override
        public int getIntValPlus1(){
            return -1;
        }
        @Override
        public long getLongValPlus1(){
            return -1;
        }
        @Override
        public float getFloatValPlus1(){
            return -1;
        }
        @Override
        public float getFloatValPlusFloatEpsilon(){
            return -Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return -1;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return -Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return -Double.MIN_VALUE;
        }
        @Override
        public boolean getBooleanVal(){
            return false;
        }
        @Override
        public byte getByteVal(){
            return 0;
        }
        @Override
        public char getCharVal(){
            return 0;
        }
        @Override
        public short getShortVal(){
            return 0;
        }
        @Override
        public int getIntVal(){
            return 0;
        }
        @Override
        public long getLongVal(){
            return 0;
        }
        @Override
        public float getFloatVal(){
            return 0;
        }
        @Override
        public double getDoubleVal(){
            return 0;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfBoolean collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                collection.add(true);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfByte collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                byte v=(byte)(i + initVal);
                if(v == 0){
                    v=(byte)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfChar collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                char v=(char)(i + initVal);
                if(v == 0){
                    v=(char)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfShort collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                short v=(short)(i + initVal);
                if(v == 0){
                    v=(short)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == 0){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == 0){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == 0){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == 0){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            // TODO Auto-generated method stub
            return false;
        }
    },
    Neg0{
        @Override
        public float getFloatVal(){
            return -0.0f;
        }
        @Override
        public double getDoubleVal(){
            return -0.0d;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfBoolean collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                collection.add(true);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfByte collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                byte v=(byte)(i + initVal);
                if(v == 0){
                    v=(byte)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfChar collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                char v=(char)(i + initVal);
                if(v == 0){
                    v=(char)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfShort collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                short v=(short)(i + initVal);
                if(v == 0){
                    v=(short)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == 0){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == 0){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == 0){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == 0){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            return true;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            return modification == QueryValModification.None && inputType.isFloatingPoint;
        }
    },
    MaxBoolean{
        @Override
        public boolean getBooleanVal(){
            return true;
        }
        @Override
        public byte getByteVal(){
            return 1;
        }
        @Override
        public char getCharVal(){
            return 1;
        }
        @Override
        public short getShortVal(){
            return 1;
        }
        @Override
        public int getIntVal(){
            return 1;
        }
        @Override
        public long getLongVal(){
            return 1;
        }
        @Override
        public float getFloatVal(){
            return 1;
        }
        @Override
        public double getDoubleVal(){
            return 1;
        }
        @Override
        public byte getByteValPlus1(){
            return 2;
        }
        @Override
        public char getCharValPlus1(){
            return 2;
        }
        @Override
        public short getShortValPlus1(){
            return 2;
        }
        @Override
        public int getIntValPlus1(){
            return 2;
        }
        @Override
        public long getLongValPlus1(){
            return 2;
        }
        @Override
        public float getFloatValPlusFloatEpsilon(){
            return 1 + Float.MIN_VALUE;
        }
        @Override
        public float getFloatValPlus1(){
            return 2;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return (double)1 + Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return 1 + Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return 2;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfBoolean collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                collection.add(false);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfByte collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                byte v=(byte)(i + initVal);
                if(v == 1){
                    v=(byte)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfChar collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                char v=(char)(i + initVal);
                if(v == 1){
                    v=(char)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfShort collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                short v=(short)(i + initVal);
                if(v == 1){
                    v=(short)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == 1){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == 1){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == 1){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == 1){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            // TODO Auto-generated method stub
            return false;
        }
    },
    MaxByte{
        @Override
        public byte getByteVal(){
            return Byte.MAX_VALUE;
        }
        @Override
        public char getCharVal(){
            return Byte.MAX_VALUE;
        }
        @Override
        public short getShortVal(){
            return Byte.MAX_VALUE;
        }
        @Override
        public int getIntVal(){
            return Byte.MAX_VALUE;
        }
        @Override
        public long getLongVal(){
            return Byte.MAX_VALUE;
        }
        @Override
        public float getFloatVal(){
            return Byte.MAX_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Byte.MAX_VALUE;
        }
        @Override
        public char getCharValPlus1(){
            return Byte.MAX_VALUE + 1;
        }
        @Override
        public short getShortValPlus1(){
            return Byte.MAX_VALUE + 1;
        }
        @Override
        public int getIntValPlus1(){
            return Byte.MAX_VALUE + 1;
        }
        @Override
        public long getLongValPlus1(){
            return Byte.MAX_VALUE + 1;
        }
        @Override
        public float getFloatValPlusFloatEpsilon(){
            return Byte.MAX_VALUE + Float.MIN_VALUE;
        }
        @Override
        public float getFloatValPlus1(){
            return (float)Byte.MAX_VALUE + 1;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return (double)Byte.MAX_VALUE + Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return Byte.MAX_VALUE + Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return (double)Byte.MAX_VALUE + 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfByte collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                byte v=(byte)(i + initVal);
                if(v == Byte.MAX_VALUE){
                    v=(byte)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfChar collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                char v=(char)(i + initVal);
                if(v == Byte.MAX_VALUE){
                    v=(char)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfShort collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                short v=(short)(i + initVal);
                if(v == Byte.MAX_VALUE){
                    v=(short)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == Byte.MAX_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == Byte.MAX_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == Byte.MAX_VALUE){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Byte.MAX_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            // TODO Auto-generated method stub
            return false;
        }
    },
    MinByte{
        @Override
        public byte getByteVal(){
            return Byte.MIN_VALUE;
        }
        @Override
        public short getShortVal(){
            return Byte.MIN_VALUE;
        }
        @Override
        public int getIntVal(){
            return Byte.MIN_VALUE;
        }
        @Override
        public long getLongVal(){
            return Byte.MIN_VALUE;
        }
        @Override
        public float getFloatVal(){
            return Byte.MIN_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Byte.MIN_VALUE;
        }
        @Override
        public short getShortValPlus1(){
            return Byte.MIN_VALUE - 1;
        }
        @Override
        public int getIntValPlus1(){
            return Byte.MIN_VALUE - 1;
        }
        @Override
        public long getLongValPlus1(){
            return Byte.MIN_VALUE - 1;
        }
        @Override
        public float getFloatValPlusFloatEpsilon(){
            return Byte.MIN_VALUE - Float.MIN_VALUE;
        }
        @Override
        public float getFloatValPlus1(){
            return (float)Byte.MIN_VALUE - 1;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return (double)Byte.MIN_VALUE - Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return Byte.MIN_VALUE - Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return (double)Byte.MIN_VALUE - 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfByte collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                byte v=(byte)(i + initVal);
                if(v == Byte.MIN_VALUE){
                    v=(byte)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfShort collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                short v=(short)(i + initVal);
                if(v == Byte.MIN_VALUE){
                    v=(short)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == Byte.MIN_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == Byte.MIN_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == Byte.MIN_VALUE){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Byte.MIN_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            // TODO Auto-generated method stub
            return false;
        }
    },
    MaxChar{
        @Override
        public char getCharVal(){
            return Character.MAX_VALUE;
        }
        @Override
        public int getIntVal(){
            return Character.MAX_VALUE;
        }
        @Override
        public long getLongVal(){
            return Character.MAX_VALUE;
        }
        @Override
        public float getFloatVal(){
            return Character.MAX_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Character.MAX_VALUE;
        }
        @Override
        public int getIntValPlus1(){
            return Character.MAX_VALUE + 1;
        }
        @Override
        public long getLongValPlus1(){
            return Character.MAX_VALUE + 1;
        }
        @Override
        public float getFloatValPlusFloatEpsilon(){
            return Character.MAX_VALUE + Float.MIN_VALUE;
        }
        @Override
        public float getFloatValPlus1(){
            return (float)Character.MAX_VALUE + 1;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return (double)Character.MAX_VALUE + Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return Character.MAX_VALUE + Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return (double)Character.MAX_VALUE + 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfChar collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                char v=(char)(i + initVal);
                if(v == Character.MAX_VALUE){
                    v=(char)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == Character.MAX_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == Character.MAX_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == Character.MAX_VALUE){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Character.MAX_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            // TODO Auto-generated method stub
            return false;
        }
    },
    MaxShort{
        @Override
        public char getCharVal(){
            return Short.MAX_VALUE;
        }
        @Override
        public short getShortVal(){
            return Short.MAX_VALUE;
        }
        @Override
        public int getIntVal(){
            return Short.MAX_VALUE;
        }
        @Override
        public long getLongVal(){
            return Short.MAX_VALUE;
        }
        @Override
        public float getFloatVal(){
            return Short.MAX_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Short.MAX_VALUE;
        }
        @Override
        public char getCharValPlus1(){
            return Short.MAX_VALUE + 1;
        }
        @Override
        public int getIntValPlus1(){
            return Short.MAX_VALUE + 1;
        }
        @Override
        public long getLongValPlus1(){
            return Short.MAX_VALUE + 1;
        }
        @Override
        public float getFloatValPlusFloatEpsilon(){
            return Short.MAX_VALUE + Float.MIN_VALUE;
        }
        @Override
        public float getFloatValPlus1(){
            return (float)Short.MAX_VALUE + 1;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return (double)Short.MAX_VALUE + Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return Short.MAX_VALUE + Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return (double)Short.MAX_VALUE + 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfChar collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                char v=(char)(i + initVal);
                if(v == Short.MAX_VALUE){
                    v=(char)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfShort collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                short v=(short)(i + initVal);
                if(v == Short.MAX_VALUE){
                    v=(short)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == Short.MAX_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == Short.MAX_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == Short.MAX_VALUE){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Short.MAX_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            // TODO Auto-generated method stub
            return false;
        }
    },
    MinShort{
        @Override
        public short getShortVal(){
            return Short.MIN_VALUE;
        }
        @Override
        public int getIntVal(){
            return Short.MIN_VALUE;
        }
        @Override
        public long getLongVal(){
            return Short.MIN_VALUE;
        }
        @Override
        public float getFloatVal(){
            return Short.MIN_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Short.MIN_VALUE;
        }
        @Override
        public int getIntValPlus1(){
            return Short.MIN_VALUE - 1;
        }
        @Override
        public long getLongValPlus1(){
            return Short.MIN_VALUE - 1;
        }
        @Override
        public float getFloatValPlusFloatEpsilon(){
            return Short.MIN_VALUE - Float.MIN_VALUE;
        }
        @Override
        public float getFloatValPlus1(){
            return (float)Short.MIN_VALUE - 1;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return (double)Short.MIN_VALUE - Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return Short.MIN_VALUE - Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return (double)Short.MIN_VALUE - 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfShort collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                short v=(short)(i + initVal);
                if(v == Short.MIN_VALUE){
                    v=(short)(i + (++initVal));
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == Short.MIN_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == Short.MIN_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == Short.MIN_VALUE){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Short.MIN_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            // TODO Auto-generated method stub
            return false;
        }
    },
    MaxSafeInt{
        @Override
        public int getIntVal(){
            return TypeUtil.MAX_SAFE_INT;
        }
        @Override
        public long getLongVal(){
            return TypeUtil.MAX_SAFE_INT;
        }
        @Override
        public float getFloatVal(){
            return TypeUtil.MAX_SAFE_INT;
        }
        @Override
        public double getDoubleVal(){
            return TypeUtil.MAX_SAFE_INT;
        }
        @Override
        public int getIntValPlus1(){
            return TypeUtil.MAX_SAFE_INT + 1;
        }
        @Override
        public long getLongValPlus1(){
            return TypeUtil.MAX_SAFE_INT + 1;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return (double)TypeUtil.MAX_SAFE_INT + Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return TypeUtil.MAX_SAFE_INT + Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return (double)TypeUtil.MAX_SAFE_INT + 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == TypeUtil.MAX_SAFE_INT){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == TypeUtil.MAX_SAFE_INT){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == TypeUtil.MAX_SAFE_INT){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == TypeUtil.MAX_SAFE_INT){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            // TODO Auto-generated method stub
            return false;
        }
    },
    MinSafeInt{
        @Override
        public int getIntVal(){
            return TypeUtil.MIN_SAFE_INT;
        }
        @Override
        public long getLongVal(){
            return TypeUtil.MIN_SAFE_INT;
        }
        @Override
        public float getFloatVal(){
            return TypeUtil.MIN_SAFE_INT;
        }
        @Override
        public double getDoubleVal(){
            return TypeUtil.MIN_SAFE_INT;
        }
        @Override
        public int getIntValPlus1(){
            return TypeUtil.MIN_SAFE_INT - 1;
        }
        @Override
        public long getLongValPlus1(){
            return TypeUtil.MIN_SAFE_INT - 1;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return (double)TypeUtil.MIN_SAFE_INT - Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return TypeUtil.MIN_SAFE_INT - Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return (double)TypeUtil.MIN_SAFE_INT - 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == TypeUtil.MIN_SAFE_INT){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == TypeUtil.MIN_SAFE_INT){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == TypeUtil.MIN_SAFE_INT){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == TypeUtil.MIN_SAFE_INT){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            // TODO Auto-generated method stub
            return false;
        }
    },
    MaxInt{
        @Override
        public int getIntVal(){
            return Integer.MAX_VALUE;
        }
        @Override
        public long getLongVal(){
            return Integer.MAX_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Integer.MAX_VALUE;
        }
        @Override
        public long getLongValPlus1(){
            return (long)Integer.MAX_VALUE + 1;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return (double)Integer.MAX_VALUE + Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return Integer.MAX_VALUE + Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return (double)Integer.MAX_VALUE + 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == Integer.MAX_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == Integer.MAX_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Integer.MAX_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(modification){
            case None:
                switch(inputType){
                case LONG:
                case DOUBLE:
                case REF:
                case INT:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case SHORT:
                    return false;
                }
                break;
            case Plus1:
                switch(inputType){
                case LONG:
                case DOUBLE:
                case REF:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case INT:
                case SHORT:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
            case PlusFloatEpisolon:
                switch(collectionType){
                case DOUBLE:
                case REF:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case INT:
                case LONG:
                case SHORT:
                    return false;
                }
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                switch(inputType){
                case LONG:
                case DOUBLE:
                case INT:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case REF:
                case SHORT:
                    return false;
                }
            case Plus1:
                switch(inputType){
                case LONG:
                case DOUBLE:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case INT:
                case REF:
                case SHORT:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
            case PlusFloatEpisolon:
                return inputType == DataType.DOUBLE;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
    },
    MinInt{
        @Override
        public int getIntVal(){
            return Integer.MIN_VALUE;
        }
        @Override
        public long getLongVal(){
            return Integer.MIN_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Integer.MIN_VALUE;
        }
        @Override
        public long getLongValPlus1(){
            return (long)Integer.MIN_VALUE - 1;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return (double)Integer.MIN_VALUE - Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return Integer.MIN_VALUE - Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return (double)Integer.MIN_VALUE - 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
            for(int i=0;i < setSize;++i){
                int v=i + initVal;
                if(v == Integer.MIN_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == Integer.MIN_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Integer.MIN_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(modification){
            case None:
                switch(inputType){
                case LONG:
                case DOUBLE:
                case REF:
                case INT:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case SHORT:
                    return false;
                }
                break;
            case Plus1:
                switch(inputType){
                case LONG:
                case DOUBLE:
                case REF:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case INT:
                case SHORT:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
            case PlusFloatEpisolon:
                switch(collectionType){
                case DOUBLE:
                case REF:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case INT:
                case LONG:
                case SHORT:
                    return false;
                }
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                switch(inputType){
                case LONG:
                case DOUBLE:
                case INT:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case REF:
                case SHORT:
                    return false;
                }
            case Plus1:
                switch(inputType){
                case LONG:
                case DOUBLE:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case INT:
                case REF:
                case SHORT:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
            case PlusFloatEpisolon:
                return inputType == DataType.DOUBLE;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
    },
    MaxSafeLong{
        @Override
        public long getLongVal(){
            return TypeUtil.MAX_SAFE_LONG;
        }
        @Override
        public double getDoubleVal(){
            return TypeUtil.MAX_SAFE_LONG;
        }
        @Override
        public long getLongValPlus1(){
            return TypeUtil.MAX_SAFE_LONG + 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == TypeUtil.MAX_SAFE_LONG){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == TypeUtil.MAX_SAFE_LONG){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(modification){
            case None:
                switch(collectionType){
                case REF:
                case LONG:
                case DOUBLE:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case INT:
                case SHORT:
                    return false;
                }
                break;
            case Plus1:
                switch(collectionType){
                case REF:
                case LONG:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case DOUBLE:
                case FLOAT:
                case INT:
                case SHORT:
                    return false;
                }
            case PlusDoubleEpsilon:
            case PlusFloatEpisolon:
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                switch(inputType){
                case DOUBLE:
                case LONG:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case INT:
                case REF:
                case SHORT:
                }
                break;
            case Plus1:
                return inputType == DataType.LONG;
            case PlusDoubleEpsilon:
            case PlusFloatEpisolon:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
    },
    MinSafeLong{
        @Override
        public long getLongVal(){
            return TypeUtil.MIN_SAFE_LONG;
        }
        @Override
        public double getDoubleVal(){
            return TypeUtil.MIN_SAFE_LONG;
        }
        @Override
        public long getLongValPlus1(){
            return TypeUtil.MIN_SAFE_LONG - 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == TypeUtil.MIN_SAFE_LONG){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == TypeUtil.MIN_SAFE_LONG){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(modification){
            case None:
                switch(collectionType){
                case REF:
                case LONG:
                case DOUBLE:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case INT:
                case SHORT:
                    return false;
                }
                break;
            case Plus1:
                switch(collectionType){
                case REF:
                case LONG:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case DOUBLE:
                case FLOAT:
                case INT:
                case SHORT:
                    return false;
                }
            case PlusDoubleEpsilon:
            case PlusFloatEpisolon:
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                switch(inputType){
                case DOUBLE:
                case LONG:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case INT:
                case REF:
                case SHORT:
                }
                break;
            case Plus1:
                return inputType == DataType.LONG;
            case PlusDoubleEpsilon:
            case PlusFloatEpisolon:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
    },
    MaxLong{
        @Override
        public long getLongVal(){
            return Long.MAX_VALUE;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == Long.MAX_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(collectionType){
            case REF:
            case LONG:
                return true;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case SHORT:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            return modification == QueryValModification.None && inputType == DataType.LONG;
        }
    },
    MinLong{
        @Override
        public long getLongVal(){
            return Long.MIN_VALUE;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
            for(int i=0;i < setSize;++i){
                long v=i + initVal;
                if(v == Long.MIN_VALUE){
                    v=i + (++initVal);
                }
                collection.add(v);
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(collectionType){
            case REF:
            case LONG:
                return true;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case SHORT:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            return modification == QueryValModification.None && inputType == DataType.LONG;
        }
    },
    MaxFloat{
        @Override
        public float getFloatVal(){
            return Float.MAX_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Float.MAX_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return Float.MAX_VALUE + Double.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlus1(){
            return (double)Float.MAX_VALUE + 1;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == Float.MAX_VALUE){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Float.MAX_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(modification){
            case None:
                switch(collectionType){
                case REF:
                case DOUBLE:
                case FLOAT:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case INT:
                case LONG:
                case SHORT:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
            case Plus1:
                switch(collectionType){
                case REF:
                case DOUBLE:
                    return true;
                case FLOAT:
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case INT:
                case LONG:
                case SHORT:
                    return false;
                }
            case PlusFloatEpisolon:
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                return inputType.isFloatingPoint;
            case PlusDoubleEpsilon:
            case Plus1:
                return inputType == DataType.DOUBLE;
            case PlusFloatEpisolon:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
    },
    MinFloat{
        @Override
        public float getFloatVal(){
            return Float.MIN_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Float.MIN_VALUE;
        }
        @Override
        public double getDoubleValPlusDoubleEpsilon(){
            return Float.MIN_VALUE - Double.MIN_VALUE;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == Float.MIN_VALUE){
                    v+=Float.MIN_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Float.MIN_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(modification){
            case None:
                switch(collectionType){
                case REF:
                case DOUBLE:
                case FLOAT:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case INT:
                case LONG:
                case SHORT:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
                switch(collectionType){
                case REF:
                case DOUBLE:
                    return true;
                case FLOAT:
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case INT:
                case LONG:
                case SHORT:
                    return false;
                }
            case Plus1:
            case PlusFloatEpisolon:
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                return inputType.isFloatingPoint;
            case PlusDoubleEpsilon:
                return inputType == DataType.DOUBLE;
            case Plus1:
            case PlusFloatEpisolon:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
    },
    MaxDouble{
        @Override
        public double getDoubleVal(){
            return Double.MAX_VALUE;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Double.MAX_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(collectionType){
            case DOUBLE:
            case REF:
                return true;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            return modification == QueryValModification.None && inputType == DataType.DOUBLE;
        }
    },
    MinDouble{
        @Override
        public double getDoubleVal(){
            return Double.MIN_VALUE;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Double.MIN_VALUE){
                    v+=Double.MIN_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(collectionType){
            case DOUBLE:
            case REF:
                return true;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            return modification == QueryValModification.None && inputType == DataType.DOUBLE;
        }
    },
    PosInfinity{
        @Override
        public float getFloatVal(){
            return Float.POSITIVE_INFINITY;
        }
        @Override
        public double getDoubleVal(){
            return Double.POSITIVE_INFINITY;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == Float.POSITIVE_INFINITY){
                    v=initVal=-Float.MAX_VALUE;
                }
                collection.add(v);
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Double.POSITIVE_INFINITY){
                    v=initVal=-Double.MAX_VALUE;
                }
                collection.add(v);
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(collectionType){
            case DOUBLE:
            case FLOAT:
            case REF:
                return true;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case INT:
            case LONG:
            case SHORT:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            return modification == QueryValModification.None && inputType.isFloatingPoint;
        }
    },
    NegInfinity{
        @Override
        public float getFloatVal(){
            return Float.NEGATIVE_INFINITY;
        }
        @Override
        public double getDoubleVal(){
            return Double.NEGATIVE_INFINITY;
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
            for(int i=0;i < setSize;++i){
                float v=i + initVal;
                if(v == Float.NEGATIVE_INFINITY){
                    v=initVal=-Float.MAX_VALUE;
                }
                collection.add(v);
                if(v == Float.POSITIVE_INFINITY){
                    initVal=-Float.MAX_VALUE;
                }
            }
        }
        @Override
        public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
            for(int i=0;i < setSize;++i){
                double v=i + initVal;
                if(v == Double.NEGATIVE_INFINITY){
                    v=initVal=-Double.MAX_VALUE;
                }
                collection.add(v);
                if(v == Double.POSITIVE_INFINITY){
                    initVal=-Double.MAX_VALUE;
                }
            }
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(collectionType){
            case DOUBLE:
            case FLOAT:
            case REF:
                return true;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case INT:
            case LONG:
            case SHORT:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            return modification == QueryValModification.None && inputType.isFloatingPoint;
        }
    },
    NaN{
        @Override
        public float getFloatVal(){
            return Float.NaN;
        }
        @Override
        public double getDoubleVal(){
            return Double.NaN;
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){

            switch(collectionType){
            case DOUBLE:
            case FLOAT:
            case REF:
                return true;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case INT:
            case LONG:
            case SHORT:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            return modification == QueryValModification.None && inputType.isFloatingPoint;
        }
    };
    private static UnsupportedOperationException unknownCombo(QueryVal queryVal,DataType inputType,
            QueryValModification modification,QueryCastType castType,DataType collectionType){
        return new UnsupportedOperationException("Unknown combo queryVal=" + queryVal + "; inputType=" + inputType
                + "; modification=" + modification + "; castType=" + castType + "; collectionType=" + collectionType);
    }
    private static UnsupportedOperationException unknownCombo(QueryVal queryVal,DataType inputType,
            QueryValModification modification,QueryCastType castType){
        return new UnsupportedOperationException("Unknown combo queryVal=" + queryVal + "; inputType=" + inputType
                + "; modification=" + modification + "; castType=" + castType);
    }
    public final Map<QueryVal.QueryValModification,Map<QueryCastType,Set<DataType>>> validQueryCombos;
    public final Map<QueryVal.QueryValModification,Map<QueryCastType,Map<DataType,Set<DataType>>>> queriesMapReturnTrue;
    QueryVal(){
        Map<QueryVal.QueryValModification,Map<QueryCastType,Set<DataType>>> tmpValidCombos=new HashMap<>();
        Map<QueryVal.QueryValModification,Map<QueryCastType,Map<DataType,Set<DataType>>>> tmpMayReturnTrue=new HashMap<>();
        for(var modification:QueryValModification.values()){
            for(var castType:QueryCastType.values()){
                for(var dataType:DataType.values()){
                    if(isValidQuery(modification,castType,dataType)){
                        for(var collectionType:DataType.values()){
                            if(queryCanReturnTrue(modification,castType,dataType,collectionType)){
                                tmpMayReturnTrue.compute(modification,(keyModification,existingVal1)->{
                                    if(existingVal1 == null){
                                        existingVal1=new HashMap<>();
                                    }
                                    existingVal1.compute(castType,(keyCastType,existingVal2)->{
                                        if(existingVal2 == null){
                                            existingVal2=new HashMap<>();
                                        }
                                        existingVal2.compute(dataType,(keyDataType,existingVal3)->{
                                            if(existingVal3 == null){
                                                existingVal3=new HashSet<>();
                                            }
                                            existingVal3.add(collectionType);
                                            return existingVal3;
                                        });
                                        return existingVal2;
                                    });
                                    return existingVal1;
                                });
                            }
                        }
                        tmpValidCombos.compute(modification,(keyModification,existingVal1)->{
                            if(existingVal1 == null){
                                existingVal1=new HashMap<>();
                            }
                            existingVal1.compute(castType,(keyCastType,existingVal2)->{
                                if(existingVal2 == null){
                                    existingVal2=new HashSet<>();
                                }
                                existingVal2.add(dataType);
                                return existingVal2;
                            });
                            return existingVal1;
                        });
                    }
                }
            }
        }
        tmpMayReturnTrue.forEach((modification,mapped1)->{
            mapped1.forEach((castType,mapped2)->{
                mapped2.replaceAll((dataType,dataTypeSet)->{
                    return Set.copyOf(dataTypeSet);
                });
            });
        });
        tmpMayReturnTrue.forEach((modification,mapped1)->{
            mapped1.replaceAll((castType,mapped2)->{
                return Map.copyOf(mapped2);
            });
        });
        tmpValidCombos.forEach((modification,mapped)->{
            mapped.replaceAll((castType,dataTypeSet)->{
                return Set.copyOf(dataTypeSet);
            });
        });
        tmpMayReturnTrue.replaceAll((modification,mapped)->{
            return Map.copyOf(mapped);
        });
        tmpValidCombos.replaceAll((modification,mapped)->{
            return Map.copyOf(mapped);
        });
        this.queriesMapReturnTrue=Map.copyOf(tmpMayReturnTrue);
        this.validQueryCombos=Map.copyOf(tmpValidCombos);
    }
    public abstract boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,
            DataType inputType,
            DataType collectionType);
    abstract boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType);
    public byte getByteValPlus1(){
        throw DataType.invalidDataType(DataType.BYTE);
    }
    public char getCharValPlus1(){
        throw DataType.invalidDataType(DataType.CHAR);
    }
    public short getShortValPlus1(){
        throw DataType.invalidDataType(DataType.SHORT);
    }
    public int getIntValPlus1(){
        throw DataType.invalidDataType(DataType.INT);
    }
    public long getLongValPlus1(){
        throw DataType.invalidDataType(DataType.LONG);
    }
    public float getFloatValPlus1(){
        throw DataType.invalidDataType(DataType.FLOAT);
    }
    public float getFloatValPlusFloatEpsilon(){
        throw DataType.invalidDataType(DataType.FLOAT);
    }
    public double getDoubleValPlus1(){
        throw DataType.invalidDataType(DataType.DOUBLE);
    }
    public double getDoubleValPlusFloatEpsilon(){
        throw DataType.invalidDataType(DataType.DOUBLE);
    }
    public double getDoubleValPlusDoubleEpsilon(){
        throw DataType.invalidDataType(DataType.DOUBLE);
    }
    public Object getRefVal(){
        throw DataType.invalidDataType(DataType.REF);
    }
    public boolean getBooleanVal(){
        throw DataType.invalidDataType(DataType.BOOLEAN);
    }
    public byte getByteVal(){
        throw DataType.invalidDataType(DataType.BYTE);
    }
    public char getCharVal(){
        throw DataType.invalidDataType(DataType.CHAR);
    }
    public short getShortVal(){
        throw DataType.invalidDataType(DataType.SHORT);
    }
    public int getIntVal(){
        throw DataType.invalidDataType(DataType.INT);
    }
    public long getLongVal(){
        throw DataType.invalidDataType(DataType.LONG);
    }
    public float getFloatVal(){
        throw DataType.invalidDataType(DataType.FLOAT);
    }
    public double getDoubleVal(){
        throw DataType.invalidDataType(DataType.DOUBLE);
    }
    public Object getInputVal(DataType inputType,QueryValModification queryValModification){
        switch(queryValModification){
        case None:
            switch(inputType){
            case BOOLEAN:
                return getBooleanVal();
            case BYTE:
                return getByteVal();
            case CHAR:
                return getCharVal();
            case DOUBLE:
                return getDoubleVal();
            case FLOAT:
                return getFloatVal();
            case INT:
                return getIntVal();
            case LONG:
                return getLongVal();
            case REF:
                return getRefVal();
            case SHORT:
                return getShortVal();
            }
            break;
        case Plus1:
            switch(inputType){
            case BYTE:
                return getByteValPlus1();
            case CHAR:
                return getCharValPlus1();
            case DOUBLE:
                return getDoubleValPlus1();
            case FLOAT:
                return getFloatValPlus1();
            case INT:
                return getIntValPlus1();
            case LONG:
                return getLongValPlus1();
            case SHORT:
                return getShortValPlus1();
            case BOOLEAN:
            case REF:
            }
            break;
        case PlusFloatEpisolon:
            switch(inputType){
            case DOUBLE:
                return getDoubleValPlusFloatEpsilon();
            case FLOAT:
                return getFloatValPlusFloatEpsilon();
            case INT:
            case LONG:
            case SHORT:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            }
            break;
        case PlusDoubleEpsilon:
            switch(inputType){
            case DOUBLE:
                return getDoubleValPlusDoubleEpsilon();
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case REF:
            }
            break;
        }
        throw new UnsupportedOperationException("Invalid inputType-queryValModificationCombo; inputType=" + inputType
                + "; queryValModification=" + queryValModification);
    }
    public void initDoesNotContain(OmniCollection.OfBoolean collection,int setSize,int initVal){
        for(int i=0;i < setSize;++i){
            collection.add((initVal + i & 1) != 0);
        }
    }
    public void initDoesNotContain(OmniCollection.OfByte collection,int setSize,int initVal){
        for(int i=0;i < setSize;++i){
            collection.add((byte)(i + initVal));
        }
    }
    public void initDoesNotContain(OmniCollection.OfChar collection,int setSize,int initVal){
        for(int i=0;i < setSize;++i){
            collection.add((char)(i + initVal));
        }
    }
    public void initDoesNotContain(OmniCollection.OfShort collection,int setSize,int initVal){
        for(int i=0;i < setSize;++i){
            collection.add((short)(i + initVal));
        }
    }
    public void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal){
        for(int i=0;i < setSize;++i){
            collection.add(i + initVal);
        }
    }
    public void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal){
        for(int i=0;i < setSize;++i){
            collection.add(i + initVal);
        }
    }
    public void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal){
        for(int i=0;i < setSize;++i){
            collection.add(i + initVal);
        }
    }
    public void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal){
        for(int i=0;i < setSize;++i){
            collection.add(i + initVal);
        }
    }
    public void initDoesNotContain(OmniCollection.OfRef<Object> collection,int setSize,long initVal){
        for(int i=0;i < setSize;++i){
            collection.add(new Object());
        }
    }
    public void initContains(OmniCollection.OfBoolean collection,int setSize,int initVal,double containsPosition){
        var val=getBooleanVal();
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex);
    }
    public void initContains(OmniCollection.OfByte collection,int setSize,int initVal,double containsPosition){
        var val=getByteVal();
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex);
    }
    public void initContains(OmniCollection.OfChar collection,int setSize,int initVal,double containsPosition){
        var val=getCharVal();
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex);
    }
    public void initContains(OmniCollection.OfShort collection,int setSize,int initVal,double containsPosition){
        var val=getShortVal();
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex);
    }
    public void initContains(OmniCollection.OfInt collection,int setSize,int initVal,double containsPosition){
        var val=getIntVal();
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex);
    }
    public void initContains(OmniCollection.OfLong collection,int setSize,long initVal,double containsPosition){
        var val=getLongVal();
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex);
    }
    public void initContains(OmniCollection.OfFloat collection,int setSize,float initVal,double containsPosition){
        var val=getFloatVal();
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex);
    }
    public void initContains(OmniCollection.OfDouble collection,int setSize,double initVal,double containsPosition){
        var val=getDoubleVal();
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex);
    }
    public void initContains(OmniCollection.OfRef<Object> collection,int setSize,long initVal,double containsPosition){
        var val=getRefVal();
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex);
    }
    //    private static Set<DataType> initValidDataTypes(QueryVal queryVal){
    //        switch(queryVal){
    //        case MaxInt:
    //        case MinInt:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.LONG,DataType.INT);
    //        case MinSafeInt:
    //        case MaxSafeInt:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT);
    //        case MaxChar:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.CHAR);
    //        case MinShort:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT);
    //        case MinByte:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
    //                    DataType.BYTE);
    //        case MaxShort:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
    //                    DataType.CHAR);
    //        case MaxByte:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
    //                    DataType.CHAR,
    //                    DataType.BYTE);
    //        case MaxBoolean:
    //        case Pos0:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
    //                    DataType.CHAR,
    //                    DataType.BYTE,DataType.BOOLEAN);
    //        }
    //        throw new UnsupportedOperationException("Unknown queryVal " + queryVal);
    //    }
    //    private static Set<DataType> initValidPlus1(QueryVal queryVal){
    //        switch(queryVal){
    //        case MaxInt:
    //        case MinInt:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.LONG);
    //        case MaxSafeInt:
    //        case MinSafeInt:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.LONG,DataType.INT);
    //        case MinShort:
    //        case MaxChar:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT);
    //        case MaxShort:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.CHAR);
    //        case MinByte:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT);
    //        case Pos0:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
    //                    DataType.BYTE);
    //        case MaxByte:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
    //                    DataType.CHAR);
    //        case MaxBoolean:
    //            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
    //                    DataType.CHAR,DataType.BYTE);
    //        }
    //        throw new UnsupportedOperationException("Unknown queryVal " + queryVal);
    //    }
    //    private static Set<DataType> initValidPlusFloatEpsilon(QueryVal queryVal){
    //        switch(queryVal){
    //        case MaxInt:
    //        case MaxSafeInt:
    //        case MinInt:
    //        case MinSafeInt:
    //            return DataType.getDataTypeSet(DataType.DOUBLE);
    //        case MaxBoolean:
    //        case MaxByte:
    //        case MaxChar:
    //        case MaxShort:
    //        case MinByte:
    //        case MinShort:
    //        case Pos0:
    //            return DataType.getDataTypeSet(DataType.FLOAT,DataType.DOUBLE);
    //        }
    //        throw new UnsupportedOperationException("Unknown queryVal " + queryVal);
    //    }
    //    private static Set<DataType> initValidPlusDoubleEpsilon(QueryVal queryVal){
    //        switch(queryVal){
    //        case MaxInt:
    //        case MaxSafeInt:
    //        case MinInt:
    //        case MinSafeInt:
    //            return DataType.getDataTypeSet(DataType.DOUBLE);
    //        case MaxBoolean:
    //        case MaxByte:
    //        case MaxChar:
    //        case MaxShort:
    //        case MinByte:
    //        case MinShort:
    //        case Pos0:
    //            return DataType.getDataTypeSet(DataType.FLOAT,DataType.DOUBLE);
    //        }
    //        throw new UnsupportedOperationException("Unknown queryVal " + queryVal);
    //    }
    public enum QueryValModification{
        None,Plus1,PlusFloatEpisolon,PlusDoubleEpsilon;
    }
}
