package omni.impl;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
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
        @Override
        public Object getInputVal(DataType inputType,QueryValModification queryValModification){
            return null;
        }
    },
    Pos0{

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
            switch(collectionType){
            case REF:
            case DOUBLE:
                return true;
            case FLOAT:
                return modification != QueryValModification.PlusDoubleEpsilon;
            case LONG:
            case INT:
            case SHORT:
            case BYTE:
                switch(modification){
                case None:
                case Plus1:
                    return true;
                case PlusFloatEpsilon:
                case PlusDoubleEpsilon:
                    return false;
                }
                break;
            case BOOLEAN:
            case CHAR:
                return modification == QueryValModification.None;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                return inputType != DataType.REF;
            case Plus1:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                case SHORT:
                case BYTE:
                    return true;
                case BOOLEAN:
                case CHAR:
                case REF:
                    return false;
                }
                break;
            case PlusFloatEpsilon:
                return inputType.isFloatingPoint;
            case PlusDoubleEpsilon:
                return inputType == DataType.DOUBLE;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        int getPlus1Direction(){
            return -1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
        }
    },
    Neg0{
        @Override
        public boolean getBooleanVal(){
            return false;
        }
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
        public double getDoubleValPlusFloatEpsilon(){
            return Math.nextAfter(getFloatVal(),getPlusFloatEpsilonDirection());
        }
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
            switch(collectionType){
            case REF:
            case DOUBLE:
                return true;
            case FLOAT:
                return modification != QueryValModification.PlusDoubleEpsilon;
            case LONG:
            case INT:
            case SHORT:
            case CHAR:
            case BYTE:
                switch(modification){
                case None:
                case Plus1:
                    return true;
                case PlusFloatEpsilon:
                case PlusDoubleEpsilon:
                    return false;
                }
                break;
            case BOOLEAN:
                return modification == QueryValModification.None;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                return inputType != DataType.REF;
            case Plus1:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                case SHORT:
                case CHAR:
                case BYTE:
                    return true;
                case BOOLEAN:
                case REF:
                    return false;
                }
                break;
            case PlusFloatEpsilon:
                return inputType.isFloatingPoint;
            case PlusDoubleEpsilon:
                return inputType == DataType.DOUBLE;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        int getPlus1Direction(){
            return 1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
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
            switch(collectionType){
            case REF:
            case DOUBLE:
                return true;
            case FLOAT:
                return modification != QueryValModification.PlusDoubleEpsilon;
            case LONG:
            case INT:
            case SHORT:
            case CHAR:
                switch(modification){
                case None:
                case Plus1:
                    return true;
                case PlusFloatEpsilon:
                case PlusDoubleEpsilon:
                    return false;
                }
                break;
            case BYTE:
                return modification == QueryValModification.None;
            case BOOLEAN:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                case SHORT:
                case BYTE:
                case CHAR:
                    return true;
                case REF:
                case BOOLEAN:
                    return false;
                }
                break;
            case Plus1:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                case SHORT:
                case CHAR:
                    return true;
                case BYTE:
                case BOOLEAN:
                case REF:
                    return false;
                }
                break;
            case PlusFloatEpsilon:
                return inputType.isFloatingPoint;
            case PlusDoubleEpsilon:
                return inputType == DataType.DOUBLE;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        int getPlus1Direction(){
            return 1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
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
        int getPlus1Direction(){
            return -1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
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
            switch(collectionType){
            case REF:
            case DOUBLE:
                return true;
            case FLOAT:
                return modification != QueryValModification.PlusDoubleEpsilon;
            case LONG:
            case INT:
            case SHORT:
                switch(modification){
                case None:
                case Plus1:
                    return true;
                case PlusFloatEpsilon:
                case PlusDoubleEpsilon:
                    return false;
                }
                break;
            case BYTE:
                return modification == QueryValModification.None;
            case CHAR:
            case BOOLEAN:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                case SHORT:
                case BYTE:
                    return true;
                case CHAR:
                case REF:
                case BOOLEAN:
                    return false;
                }
                break;
            case Plus1:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                case SHORT:
                    return true;
                case CHAR:
                case BYTE:
                case BOOLEAN:
                case REF:
                    return false;
                }
                break;
            case PlusFloatEpsilon:
                return inputType.isFloatingPoint;
            case PlusDoubleEpsilon:
                return inputType == DataType.DOUBLE;
            }
            throw unknownCombo(this,inputType,modification,castType);
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
        int getPlus1Direction(){
            return 1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
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
            switch(collectionType){
            case REF:
            case DOUBLE:
                return true;
            case FLOAT:
                return modification != QueryValModification.PlusDoubleEpsilon;
            case LONG:
            case INT:
                switch(modification){
                case None:
                case Plus1:
                    return true;
                case PlusDoubleEpsilon:
                case PlusFloatEpsilon:
                    return false;
                }
                break;
            case CHAR:
                return modification == QueryValModification.None;
            case SHORT:
            case BYTE:
            case BOOLEAN:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                case CHAR:
                    return true;
                case REF:
                case SHORT:
                case BYTE:
                case BOOLEAN:
                    return false;
                }
                break;
            case Plus1:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                    return true;
                case REF:
                case CHAR:
                case SHORT:
                case BYTE:
                case BOOLEAN:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
                return inputType == DataType.DOUBLE;
            case PlusFloatEpsilon:
                return inputType.isFloatingPoint;
            }
            throw unknownCombo(this,inputType,modification,castType);
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
        int getPlus1Direction(){
            return 1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
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
            switch(collectionType){
            case REF:
            case DOUBLE:
                return true;
            case FLOAT:
                return modification != QueryValModification.PlusDoubleEpsilon;
            case LONG:
            case INT:
            case CHAR:
                switch(modification){
                case None:
                case Plus1:
                    return true;
                case PlusDoubleEpsilon:
                case PlusFloatEpsilon:
                    return false;
                }
                break;
            case SHORT:
                return modification == QueryValModification.None;
            case BYTE:
            case BOOLEAN:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                case SHORT:
                case CHAR:
                    return true;
                case BOOLEAN:
                case BYTE:
                case REF:
                    return false;
                }
                break;
            case Plus1:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                case CHAR:
                    return true;
                case BOOLEAN:
                case BYTE:
                case REF:
                case SHORT:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
                return inputType == DataType.DOUBLE;
            case PlusFloatEpsilon:
                return inputType.isFloatingPoint;
            }
            throw unknownCombo(this,inputType,modification,castType);
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
        int getPlus1Direction(){
            return -1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
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
            switch(collectionType){
            case REF:
            case DOUBLE:
                return true;
            case FLOAT:
                return modification != QueryValModification.PlusDoubleEpsilon;
            case LONG:
            case INT:
                switch(modification){
                case None:
                case Plus1:
                    return true;
                case PlusDoubleEpsilon:
                case PlusFloatEpsilon:
                    return false;
                }
                break;
            case SHORT:
                return modification == QueryValModification.None;
            case CHAR:
            case BYTE:
            case BOOLEAN:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType,collectionType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(modification){
            case None:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                case SHORT:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case REF:
                    return false;
                }
                break;
            case Plus1:
                switch(inputType){
                case DOUBLE:
                case FLOAT:
                case LONG:
                case INT:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case REF:
                case SHORT:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
                return inputType == DataType.DOUBLE;
            case PlusFloatEpsilon:
                return inputType.isFloatingPoint;
            }
            throw unknownCombo(this,inputType,modification,castType);
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
        int getPlus1Direction(){
            return 1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
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
            switch(modification){
            case None:
                switch(collectionType){
                case LONG:
                case DOUBLE:
                case INT:
                case FLOAT:
                case REF:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case SHORT:
                    return false;
                }
                break;
            case Plus1:
                switch(collectionType){
                case LONG:
                case DOUBLE:
                case INT:
                case REF:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case SHORT:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
            case PlusFloatEpsilon:
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
                case FLOAT:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case REF:
                case SHORT:
                    return false;
                }
                break;
            case Plus1:
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
                break;
            case PlusDoubleEpsilon:
            case PlusFloatEpsilon:
                return inputType == DataType.DOUBLE;
            }
            throw unknownCombo(this,inputType,modification,castType);
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
        int getPlus1Direction(){
            return -1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
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
            switch(modification){
            case None:
                switch(collectionType){
                case LONG:
                case DOUBLE:
                case INT:
                case FLOAT:
                case REF:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case SHORT:
                    return false;
                }
                break;
            case Plus1:
                switch(collectionType){
                case LONG:
                case DOUBLE:
                case INT:
                case REF:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case FLOAT:
                case SHORT:
                    return false;
                }
                break;
            case PlusDoubleEpsilon:
            case PlusFloatEpsilon:
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
                case FLOAT:
                    return true;
                case BOOLEAN:
                case BYTE:
                case CHAR:
                case REF:
                case SHORT:
                    return false;
                }
                break;
            case Plus1:
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
                break;
            case PlusDoubleEpsilon:
            case PlusFloatEpsilon:
                return inputType == DataType.DOUBLE;
            }
            throw unknownCombo(this,inputType,modification,castType);
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
            switch(collectionType){
            case REF:
            case DOUBLE:
                return true;
            case LONG:
                switch(modification){
                case None:
                case Plus1:
                    return true;
                case PlusFloatEpsilon:
                case PlusDoubleEpsilon:
                    return false;
                }
                break;
            case INT:
                return modification == QueryValModification.None;
            case FLOAT:
            case SHORT:
            case CHAR:
            case BYTE:
            case BOOLEAN:
                return false;
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
            case PlusFloatEpsilon:
                return inputType == DataType.DOUBLE;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        int getPlus1Direction(){
            return 1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
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
            switch(collectionType){
            case REF:
            case DOUBLE:
                return true;
            case LONG:
                switch(modification){
                case None:
                case Plus1:
                    return true;
                case PlusFloatEpsilon:
                case PlusDoubleEpsilon:
                    return false;
                }
                break;
            case INT:
                return modification == QueryValModification.None;
            case FLOAT:
            case SHORT:
            case CHAR:
            case BYTE:
            case BOOLEAN:
                return false;
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
            case PlusFloatEpsilon:
                return inputType == DataType.DOUBLE;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        int getPlus1Direction(){
            return -1;
        }
        @Override
        double getPlusFloatEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
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
            case PlusFloatEpsilon:
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
                    return false;
                }
                break;
            case Plus1:
                return inputType == DataType.LONG;
            case PlusDoubleEpsilon:
            case PlusFloatEpsilon:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        int getPlus1Direction(){
            return 1;
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
            case PlusFloatEpsilon:
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
                    return false;
                }
                break;
            case Plus1:
                return inputType == DataType.LONG;
            case PlusDoubleEpsilon:
            case PlusFloatEpsilon:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        int getPlus1Direction(){
            return -1;
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
            case PlusFloatEpsilon:
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
            case PlusFloatEpsilon:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        int getPlus1Direction(){
            return 1;
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.POSITIVE_INFINITY;
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
            case PlusFloatEpsilon:
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
            case PlusFloatEpsilon:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        double getPlusDoubleEpsilonDirection(){
            return Double.NEGATIVE_INFINITY;
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
    public final SortedMap<QueryVal.QueryValModification,SortedMap<QueryCastType,Set<DataType>>> validQueryCombos;
    QueryVal(){
        TreeMap<QueryVal.QueryValModification,SortedMap<QueryCastType,Set<DataType>>> tmpValidCombos=new TreeMap<>();
        for(var modification:QueryValModification.values()){
            for(var castType:QueryCastType.values()){
                for(var dataType:DataType.values()){
                    if(isValidQuery(modification,castType,dataType)){
                        tmpValidCombos.compute(modification,(keyModification,existingVal1)->{
                            if(existingVal1 == null){
                                existingVal1=new TreeMap<>();
                            }
                            existingVal1.compute(castType,(keyCastType,existingVal2)->{
                                if(existingVal2 == null){
                                    existingVal2=new TreeSet<>();
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
        tmpValidCombos.forEach((modification,mapped)->{
            mapped.replaceAll((castType,dataTypeSet)->{
                return EnumSet.copyOf(dataTypeSet);
            });
        });
        tmpValidCombos.replaceAll((modification,mapped)->{
            return Collections.unmodifiableSortedMap(mapped);
        });
        this.validQueryCombos=Collections.unmodifiableSortedMap(tmpValidCombos);
    }
    public abstract boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,
            DataType inputType,
            DataType collectionType);
    abstract boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType);
    public boolean getBooleanVal(QueryValModification modification){
        if(modification != QueryValModification.None){
            throw new UnsupportedOperationException("Invalid dataType/QueryValModification combo. dataType="
                    + DataType.BOOLEAN + "; modification=" + modification);
        }
        return getBooleanVal();
    }
    public byte getByteVal(QueryValModification modification){
        switch(modification){
        case None:
            return getByteVal();
        case Plus1:
            return getByteValPlus1();
        case PlusFloatEpsilon:
        case PlusDoubleEpsilon:
        }
        throw new UnsupportedOperationException("Invalid dataType/QueryValModification combo. dataType=" + DataType.BYTE
                + "; modification=" + modification);
    }
    public char getCharVal(QueryValModification modification){
        switch(modification){
        case None:
            return getCharVal();
        case Plus1:
            return getCharValPlus1();
        case PlusFloatEpsilon:
        case PlusDoubleEpsilon:
        }
        throw new UnsupportedOperationException("Invalid dataType/QueryValModification combo. dataType=" + DataType.CHAR
                + "; modification=" + modification);
    }
    public short getShortVal(QueryValModification modification){
        switch(modification){
        case None:
            return getShortVal();
        case Plus1:
            return getShortValPlus1();
        case PlusFloatEpsilon:
        case PlusDoubleEpsilon:
        }
        throw new UnsupportedOperationException("Invalid dataType/QueryValModification combo. dataType="
                + DataType.SHORT + "; modification=" + modification);
    }
    public int getIntVal(QueryValModification modification){
        switch(modification){
        case None:
            return getIntVal();
        case Plus1:
            return getIntValPlus1();
        case PlusFloatEpsilon:
        case PlusDoubleEpsilon:
        }
        throw new UnsupportedOperationException("Invalid dataType/QueryValModification combo. dataType=" + DataType.INT
                + "; modification=" + modification);
    }
    public long getLongVal(QueryValModification modification){
        switch(modification){
        case None:
            return getLongVal();
        case Plus1:
            return getLongValPlus1();
        case PlusFloatEpsilon:
        case PlusDoubleEpsilon:
        }
        throw new UnsupportedOperationException("Invalid dataType/QueryValModification combo. dataType=" + DataType.LONG
                + "; modification=" + modification);
    }
    public float getFloatVal(QueryValModification modification){
        switch(modification){
        case None:
            return getFloatVal();
        case Plus1:
            return getFloatValPlus1();
        case PlusFloatEpsilon:
            return getFloatValPlusFloatEpsilon();
        case PlusDoubleEpsilon:
        }
        throw new UnsupportedOperationException("Invalid dataType/QueryValModification combo. dataType="
                + DataType.FLOAT + "; modification=" + modification);
    }
    public double getDoubleVal(QueryValModification modification){
        switch(modification){
        case None:
            return getDoubleVal();
        case Plus1:
            return getDoubleValPlus1();
        case PlusFloatEpsilon:
            return getDoubleValPlusFloatEpsilon();
        case PlusDoubleEpsilon:
            return getDoubleValPlusDoubleEpsilon();
        }
        throw new UnsupportedOperationException("Invalid dataType/QueryValModification combo. dataType="
                + DataType.DOUBLE + "; modification=" + modification);
    }
    public Object getRefVal(QueryValModification modification){
        if(modification != QueryValModification.None){
            throw new UnsupportedOperationException("Invalid dataType/QueryValModification combo. dataType="
                    + DataType.REF + "; modification=" + modification);
        }
        return getRefVal();
    }
    int getPlus1Direction(){
        throw new UnsupportedOperationException();
    }
    double getPlusFloatEpsilonDirection(){
        throw new UnsupportedOperationException();
    }
    double getPlusDoubleEpsilonDirection(){
        throw new UnsupportedOperationException();
    }
    public final byte getByteValPlus1(){
        int v=getByteVal() + getPlus1Direction();
        if(v < Byte.MIN_VALUE || v > Byte.MAX_VALUE){
            throw new ArithmeticException("out of range v=" + v);
        }
        return (byte)v;
    }
    public final char getCharValPlus1(){
        int v=getCharVal() + getPlus1Direction();
        if(v < Character.MIN_VALUE || v > Character.MAX_VALUE){
            throw new ArithmeticException("out of range v=" + v);
        }
        return (char)v;
    }
    public final short getShortValPlus1(){
        int v=getShortVal() + getPlus1Direction();
        if(v < Short.MIN_VALUE || v > Short.MAX_VALUE){
            throw new ArithmeticException("out of range v=" + v);
        }
        return (short)v;
    }
    public final int getIntValPlus1(){
        return Math.addExact(getIntVal(),getPlus1Direction());
    }
    public final long getLongValPlus1(){
        return Math.addExact(getLongVal(),getPlus1Direction());
    }
    public final float getFloatValPlus1(){
        return getFloatVal() + getPlus1Direction();
    }
    public final float getFloatValPlusFloatEpsilon(){
        return Math.nextAfter(getFloatVal(),getPlusFloatEpsilonDirection());
    }
    public final double getDoubleValPlus1(){
        return getDoubleVal() + getPlus1Direction();
    }
    public double getDoubleValPlusFloatEpsilon(){
        double v=getDoubleVal();
        if(getPlusFloatEpsilonDirection() < 0){
            return v - Float.MIN_VALUE;
        }else{
            return v + Float.MIN_VALUE;
        }
    }
    public final double getDoubleValPlusDoubleEpsilon(){
        return Math.nextAfter(getDoubleVal(),getPlusDoubleEpsilonDirection());
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
        case PlusFloatEpsilon:
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
    public enum QueryValModification{
        None,Plus1,PlusFloatEpsilon,PlusDoubleEpsilon;
    }
}
