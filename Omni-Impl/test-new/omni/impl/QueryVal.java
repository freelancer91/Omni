package omni.impl;
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

    };
    public final Set<DataType> validDataTypes;
    public final Set<DataType> validPlus1;
    public final Set<DataType> validPlusFloatEpsilon;
    public final Set<DataType> validPlusDoubleEpsilon;
    public final Set<QueryCastType> validQueryCasts;
    QueryVal(){
        this.validDataTypes=initValidDataTypes(this);
        this.validPlus1=initValidPlus1(this);
        this.validPlusFloatEpsilon=initValidPlusFloatEpsilon(this);
        this.validPlusDoubleEpsilon=initValidPlusDoubleEpsilon(this);
        this.validQueryCasts=initValidQueryCasts(this);
    }
    public byte getByteValPlus1(){
        throw getUOE(DataType.BYTE);
    }
    public char getCharValPlus1(){
        throw getUOE(DataType.CHAR);
    }
    public short getShortValPlus1(){
        throw getUOE(DataType.SHORT);
    }
    public int getIntValPlus1(){
        throw getUOE(DataType.INT);
    }
    public long getLongValPlus1(){
        throw getUOE(DataType.LONG);
    }
    public float getFloatValPlus1(){
        throw getUOE(DataType.FLOAT);
    }
    public float getFloatValPlusFloatEpsilon(){
        throw getUOE(DataType.FLOAT);
    }
    public double getDoubleValPlus1(){
        throw getUOE(DataType.DOUBLE);
    }
    public double getDoubleValPlusFloatEpsilon(){
        throw getUOE(DataType.DOUBLE);
    }
    public double getDoubleValPlusDoubleEpsilon(){
        throw getUOE(DataType.DOUBLE);
    }
    public Object getRefVal(){
        throw getUOE(DataType.REF);
    }
    public boolean getBooleanVal(){
        throw getUOE(DataType.BOOLEAN);
    }
    public byte getByteVal(){
        throw getUOE(DataType.BYTE);
    }
    public char getCharVal(){
        throw getUOE(DataType.CHAR);
    }
    public short getShortVal(){
        throw getUOE(DataType.SHORT);
    }
    public int getIntVal(){
        throw getUOE(DataType.INT);
    }
    public long getLongVal(){
        throw getUOE(DataType.LONG);
    }
    public float getFloatVal(){
        throw getUOE(DataType.FLOAT);
    }
    public double getDoubleVal(){
        throw getUOE(DataType.DOUBLE);
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
    public static UnsupportedOperationException getUOE(DataType dataType){
        return new UnsupportedOperationException("Invalid dataType " + dataType);
    }
    private static Set<DataType> initValidDataTypes(QueryVal queryVal){
        switch(queryVal){
        case Null:
            return DataType.getDataTypeSet(DataType.REF);
        case MaxLong:
        case MinLong:
            return DataType.getDataTypeSet(DataType.LONG);
        case MaxDouble:
        case MinDouble:
            return DataType.getDataTypeSet(DataType.DOUBLE);
        case MinSafeLong:
        case MaxSafeLong:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.LONG);
        case NaN:
        case Neg0:
        case NegInfinity:
        case PosInfinity:
        case MaxFloat:
        case MinFloat:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT);
        case MaxInt:
        case MinInt:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.LONG,DataType.INT);
        case MinSafeInt:
        case MaxSafeInt:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT);
        case MaxChar:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.CHAR);
        case MinShort:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT);
        case MinByte:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
                    DataType.BYTE);
        case MaxShort:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
                    DataType.CHAR);
        case MaxByte:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
                    DataType.CHAR,
                    DataType.BYTE);
        case MaxBoolean:
        case Pos0:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
                    DataType.CHAR,
                    DataType.BYTE,DataType.BOOLEAN);
        }
        throw new UnsupportedOperationException("Unknown queryVal " + queryVal);
    }
    private static Set<DataType> initValidPlus1(QueryVal queryVal){
        switch(queryVal){
        case Null:
        case MaxDouble:
        case MinDouble:
        case MinFloat:
        case MaxLong:
        case MinLong:
        case NaN:
        case Neg0:
        case NegInfinity:
        case PosInfinity:
            return Set.of();
        case MaxSafeLong:
        case MinSafeLong:
            return DataType.getDataTypeSet(DataType.LONG);
        case MaxFloat:
            return DataType.getDataTypeSet(DataType.DOUBLE);
        case MaxInt:
        case MinInt:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.LONG);
        case MaxSafeInt:
        case MinSafeInt:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.LONG,DataType.INT);
        case MinShort:
        case MaxChar:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT);
        case MaxShort:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.CHAR);
        case MinByte:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT);
        case Pos0:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
                    DataType.BYTE);
        case MaxByte:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
                    DataType.CHAR);
        case MaxBoolean:
            return DataType.getDataTypeSet(DataType.DOUBLE,DataType.FLOAT,DataType.LONG,DataType.INT,DataType.SHORT,
                    DataType.CHAR,DataType.BYTE);
        }
        throw new UnsupportedOperationException("Unknown queryVal " + queryVal);
    }
    private static Set<DataType> initValidPlusFloatEpsilon(QueryVal queryVal){
        switch(queryVal){
        case Null:
        case PosInfinity:
        case NegInfinity:
        case Neg0:
        case NaN:
        case MinLong:
        case MinSafeLong:
        case MaxLong:
        case MaxSafeLong:
        case MaxDouble:
        case MaxFloat:
        case MinDouble:
        case MinFloat:
            return DataType.getDataTypeSet();
        case MaxInt:
        case MaxSafeInt:
        case MinInt:
        case MinSafeInt:
            return DataType.getDataTypeSet(DataType.DOUBLE);
        case MaxBoolean:
        case MaxByte:
        case MaxChar:
        case MaxShort:
        case MinByte:
        case MinShort:
        case Pos0:
            return DataType.getDataTypeSet(DataType.FLOAT,DataType.DOUBLE);
        }
        throw new UnsupportedOperationException("Unknown queryVal " + queryVal);
    }
    private static Set<DataType> initValidPlusDoubleEpsilon(QueryVal queryVal){
        switch(queryVal){
        case Null:
        case PosInfinity:
        case NegInfinity:
        case Neg0:
        case NaN:
        case MinLong:
        case MinSafeLong:
        case MaxLong:
        case MaxSafeLong:
        case MaxDouble:
        case MinDouble:
            return DataType.getDataTypeSet();
        case MaxInt:
        case MaxSafeInt:
        case MinInt:
        case MinSafeInt:
        case MaxFloat:
        case MinFloat:
            return DataType.getDataTypeSet(DataType.DOUBLE);
        case MaxBoolean:
        case MaxByte:
        case MaxChar:
        case MaxShort:
        case MinByte:
        case MinShort:
        case Pos0:
            return DataType.getDataTypeSet(DataType.FLOAT,DataType.DOUBLE);
        }
        throw new UnsupportedOperationException("Unknown queryVal " + queryVal);
    }
    private static Set<QueryCastType> initValidQueryCasts(QueryVal queryVal){
        switch(queryVal){
        case MaxBoolean:
        case MaxByte:
        case MaxChar:
        case MaxDouble:
        case MaxFloat:
        case MaxInt:
        case MaxLong:
        case MaxSafeInt:
        case MaxSafeLong:
        case MaxShort:
        case MinByte:
        case MinDouble:
        case MinFloat:
        case MinInt:
        case MinLong:
        case MinSafeInt:
        case MinSafeLong:
        case MinShort:
        case NaN:
        case Neg0:
        case NegInfinity:
        case Pos0:
        case PosInfinity:
            return QueryCastType.AllTypes;
        case Null:
            return QueryCastType.NotPrimitive;
        }
        throw new UnsupportedOperationException("Unknown queryVal " + queryVal);
    }
    public enum QueryValModification{
        None,Plus1,PlusFloatEpisolon,PlusDoubleEpsilon;
    }
}
