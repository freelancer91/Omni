package omni.impl;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import omni.api.OmniCollection;
import omni.util.TypeUtil;
public enum QueryVal{
    // TODO add support for collection modifying non-null Objects
    Null{
        @Override
        boolean isBooleanValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isByteValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isLongValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isFloatValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isDoubleValSupported(QueryValModification modification){
            return false;
        }
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
        boolean isCharValSupported(QueryValModification modification){
            return modification == QueryValModification.None;
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
            return -0.0f;
        }
        @Override
        public double getDoubleVal(){
            return -0.0d;
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
        public double getDoubleValPlusFloatEpsilon(){
            return Math.nextAfter(getFloatVal(),getPlusFloatEpsilonDirection());
        }
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
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return Math.nextAfter(getFloatVal(),getPlusFloatEpsilonDirection());
        }
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
    TwoHundred{
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return Math.nextAfter(getFloatVal(),getPlusFloatEpsilonDirection());
        }
        @Override
        public char getCharVal(){
            return 200;
        }
        @Override
        public short getShortVal(){
            return 200;
        }
        @Override
        public int getIntVal(){
            return 200;
        }
        @Override
        public long getLongVal(){
            return 200;
        }
        @Override
        public float getFloatVal(){
            return 200;
        }
        @Override
        public double getDoubleVal(){
            return 200;
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(collectionType){
            case REF:
            case DOUBLE:
            case FLOAT:
            case LONG:
            case INT:
            case SHORT:
            case CHAR:
                return true;
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
                case BYTE:
                case REF:
                case BOOLEAN:
                    return false;
                }
                break;
            case Plus1:
            case PlusFloatEpsilon:
            case PlusDoubleEpsilon:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
    },
    MaxChar{
        @Override
        public double getDoubleValPlusFloatEpsilon(){
            return Math.nextAfter(getFloatVal(),getPlusFloatEpsilonDirection());
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return modification == QueryValModification.None;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
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
        public double getDoubleValPlusFloatEpsilon(){
            return Math.nextAfter(getFloatVal(),getPlusFloatEpsilonDirection());
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return modification == QueryValModification.None;
        }
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
        public double getDoubleValPlusFloatEpsilon(){
            return Math.nextAfter(getFloatVal(),getPlusFloatEpsilonDirection());
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return modification == QueryValModification.None;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
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
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
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
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
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
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return modification == QueryValModification.None;
        }
        @Override
        boolean isFloatValSupported(QueryValModification modification){
            return false;
        }
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
        boolean isFloatValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return modification == QueryValModification.None;
        }
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
        boolean isFloatValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public long getLongVal(){
            return TypeUtil.MAX_SAFE_LONG;
        }
        @Override
        public double getDoubleVal(){
            return TypeUtil.MAX_SAFE_LONG;
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
        boolean isFloatValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public long getLongVal(){
            return TypeUtil.MIN_SAFE_LONG;
        }
        @Override
        public double getDoubleVal(){
            return TypeUtil.MIN_SAFE_LONG;
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
    OneLShift54{
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public long getLongVal(){
            return 1L << 54;
        }
        @Override
        public float getFloatVal(){
            return 1L << 54;
        }
        @Override
        public double getDoubleVal(){
            return 1L << 54;
        }
        @Override
        public boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,DataType inputType,
                DataType collectionType){
            switch(collectionType){
            case LONG:
            case FLOAT:
            case DOUBLE:
            case REF:
                return true;
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case SHORT:
            case INT:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
        @Override
        boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType){
            switch(inputType){
            case LONG:
            case FLOAT:
            case DOUBLE:
                return modification == QueryValModification.None;
            case REF:
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case SHORT:
            case INT:
                return false;
            }
            throw unknownCombo(this,inputType,modification,castType);
        }
    },
    MaxLong{
        @Override
        boolean isFloatValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isDoubleValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public long getLongVal(){
            return Long.MAX_VALUE;
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
        boolean isFloatValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isDoubleValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public long getLongVal(){
            return Long.MIN_VALUE;
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
        boolean isLongValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public float getFloatVal(){
            return Float.MAX_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Float.MAX_VALUE;
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
        boolean isLongValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public float getFloatVal(){
            return Float.MIN_VALUE;
        }
        @Override
        public double getDoubleVal(){
            return Float.MIN_VALUE;
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
        boolean isFloatValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isLongValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public double getDoubleVal(){
            return Double.MAX_VALUE;
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
        boolean isFloatValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isLongValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public double getDoubleVal(){
            return Double.MIN_VALUE;
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
        boolean isLongValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public float getFloatVal(){
            return Float.POSITIVE_INFINITY;
        }
        @Override
        public double getDoubleVal(){
            return Double.POSITIVE_INFINITY;
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
        boolean isLongValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
        @Override
        public float getFloatVal(){
            return Float.NEGATIVE_INFINITY;
        }
        @Override
        public double getDoubleVal(){
            return Double.NEGATIVE_INFINITY;
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
        boolean isLongValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isIntValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isShortValSupported(QueryValModification modification){
            return false;
        }
        @Override
        boolean isCharValSupported(QueryValModification modification){
            return false;
        }
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
    QueryVal(){
        EnumMap<QueryVal.QueryValModification,Map<QueryCastType,Set<DataType>>> tmpValidCombos=new EnumMap<>(
                QueryVal.QueryValModification.class);
        for(var modification:QueryValModification.values()){
            for(var castType:QueryCastType.values()){
                for(var dataType:DataType.values()){
                    if(isValidQuery(modification,castType,dataType)){
                        tmpValidCombos.compute(modification,(keyModification,existingVal1)->{
                            if(existingVal1 == null){
                                existingVal1=new EnumMap<>(QueryCastType.class);
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
            return Collections.unmodifiableMap(mapped);
        });
        this.validQueryCombos=Collections.unmodifiableMap(tmpValidCombos);
    }
    public abstract boolean queryCanReturnTrue(QueryValModification modification,QueryCastType castType,
            DataType inputType,
            DataType collectionType);
    abstract boolean isValidQuery(QueryValModification modification,QueryCastType castType,DataType inputType);

    public final boolean getBooleanVal(QueryValModification modification){
        if(modification != QueryValModification.None){
            throw new UnsupportedOperationException("Invalid dataType/QueryValModification combo. dataType="
                    + DataType.BOOLEAN + "; modification=" + modification);
        }
        return getBooleanVal();
    }

    public final byte getByteVal(QueryValModification modification){
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

    public final char getCharVal(QueryValModification modification){
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
    public final short getShortVal(QueryValModification modification){
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
    public final int getIntVal(QueryValModification modification){
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
    public final long getLongVal(QueryValModification modification){
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
    boolean isFloatValSupported(QueryValModification modification){
        switch(modification){
        case None:
        case Plus1:
        case PlusFloatEpsilon:
            return true;
        case PlusDoubleEpsilon:
        }
        return false;
    }
    public final float getFloatVal(QueryValModification modification){
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
    boolean isDoubleValSupported(QueryValModification modification){
        switch(modification){
        case None:
        case Plus1:
        case PlusFloatEpsilon:
        case PlusDoubleEpsilon:
            return true;
        }
        return false;
    }
    public final double getDoubleVal(QueryValModification modification){
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
    public final Object getRefVal(QueryValModification modification){
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
    boolean isBooleanValSupported(QueryValModification modification){
        return modification == QueryValModification.None;
    }
    public final void initDoesNotContain(OmniCollection.OfBoolean collection,int setSize,int initVal,
            QueryValModification modification){
        if(setSize>0) {
            int i=0;
            if(isBooleanValSupported(modification)){
                var avoidThis=getBooleanVal();
                do {
                    var v=(initVal + i & 1) != 0;
                    if(v == avoidThis){
                        v=!v;
                    }
                    collection.add(v);
                }while(++i<setSize);
            }else {
                do {
                    var v=(initVal + i & 1) != 0;
                    collection.add(v);
                }while(++i<setSize);
            }


        }


    }
    boolean isByteValSupported(QueryValModification modification){
        switch(modification){
        case None:
        case Plus1:
            return true;
        default:
            return false;
        }
    }
    public final void initDoesNotContain(OmniCollection.OfByte collection,int setSize,int initVal,
            QueryValModification modification){
        if(setSize>0) {
            int i=0;
            if(isByteValSupported(modification)){
                var avoidThis=getByteVal(modification);
                do{
                    var v=(byte)(i + initVal);
                    if(v == avoidThis){
                        ++v;
                    }
                    collection.add(v);
                }while(++i < setSize);
            }else{
                do {
                    var v=(byte)(i + initVal);
                    collection.add(v);
                }while(++i<setSize);
            }
        }
    }
    boolean isCharValSupported(QueryValModification modification){
        switch(modification){
        case None:
        case Plus1:
            return true;
        default:
            return false;
        }
    }
    public final void initDoesNotContain(OmniCollection.OfChar collection,int setSize,int initVal,
            QueryValModification modification){
        if(setSize>0) {
            int i=0;
            if(isCharValSupported(modification)){
                var avoidThis=getCharVal(modification);
                do{
                    var v=(char)(i + initVal);
                    if(v == avoidThis){
                        ++v;
                    }
                    collection.add(v);
                }while(++i < setSize);
            }else{
                do {
                    var v=(char)(i + initVal);
                    collection.add(v);
                }while(++i<setSize);
            }
        }
    }
    boolean isShortValSupported(QueryValModification modification){
        switch(modification){
        case None:
        case Plus1:
            return true;
        default:
            return false;
        }
    }
    public final void initDoesNotContain(OmniCollection.OfShort collection,int setSize,int initVal,
            QueryValModification modification){
        if(setSize>0) {
            int i=0;
            if(isShortValSupported(modification)){
                var avoidThis=getShortVal(modification);
                do{
                    var v=(short)(i + initVal);
                    if(v == avoidThis){
                        ++v;
                    }
                    collection.add(v);
                }while(++i < setSize);
            }else{
                do {
                    var v=(short)(i + initVal);
                    collection.add(v);
                }while(++i<setSize);
            }
        }
    }
    boolean isIntValSupported(QueryValModification modification){
        switch(modification){
        case None:
        case Plus1:
            return true;
        default:
            return false;
        }
    }
    public final void initDoesNotContain(OmniCollection.OfInt collection,int setSize,int initVal,
            QueryValModification modification){
        if(setSize>0) {
            int i=0;
            if(isIntValSupported(modification)){
                var avoidThis=getIntVal(modification);
                do{
                    var v=i + initVal;
                    if(v == avoidThis){
                        ++v;
                    }
                    collection.add(v);
                }while(++i < setSize);
            }else{
                do {
                    var v=i + initVal;
                    collection.add(v);
                }while(++i<setSize);
            }
        }
    }
    boolean isLongValSupported(QueryValModification modification){
        switch(modification){
        case None:
        case Plus1:
            return true;
        default:
            return false;
        }
    }
    public final void initDoesNotContain(OmniCollection.OfLong collection,int setSize,long initVal,
            QueryValModification modification){
        if(setSize>0) {
            int i=0;
            if(isLongValSupported(modification)){
                var avoidThis=getLongVal(modification);
                do{
                    var v=i + initVal;
                    if(v == avoidThis){
                        ++v;
                    }
                    collection.add(v);
                }while(++i < setSize);
            }else{
                do {
                    var v=i + initVal;
                    collection.add(v);
                }while(++i<setSize);
            }
        }
    }
    public final void initDoesNotContain(OmniCollection.OfFloat collection,int setSize,float initVal,
            QueryValModification modification){
        if(setSize > 0){
            float valOffset=0;
            int i=0;
            if(isFloatValSupported(modification)){
                var avoidThis=getFloatVal(modification);
                do{
                    var v=valOffset + initVal;
                    if(v == avoidThis){
                        v=Math.nextAfter(v,Double.POSITIVE_INFINITY);
                    }
                    collection.add(v);
                    if(v == Float.POSITIVE_INFINITY){
                        valOffset=-Float.MAX_VALUE;
                    }
                }while(++i < setSize);
            }else{
                do{
                    var v=valOffset + initVal;
                    collection.add(v);
                    if(v == Float.POSITIVE_INFINITY){
                        valOffset=-Float.MAX_VALUE;
                    }
                }while(++i < setSize);
            }
        }
    }
    public final void initDoesNotContain(OmniCollection.OfDouble collection,int setSize,double initVal,
            QueryValModification modification){
        if(setSize > 0){
            double valOffset=0;
            int i=0;
            if(isDoubleValSupported(modification)){
                var avoidThis=getDoubleVal(modification);
                do{
                    var v=valOffset + initVal;
                    if(v == avoidThis){
                        v=Math.nextAfter(v,Double.POSITIVE_INFINITY);
                    }
                    collection.add(v);
                    if(v == Double.POSITIVE_INFINITY){
                        valOffset=-Double.MAX_VALUE;
                    }
                }while(++i < setSize);
            }else{
                do{
                    var v=valOffset + initVal;
                    collection.add(v);
                    if(v == Double.POSITIVE_INFINITY){
                        valOffset=-Double.MAX_VALUE;
                    }
                }while(++i < setSize);
            }
        }
    }
    public final void initDoesNotContain(OmniCollection.OfRef<Object> collection,int setSize,long initVal,
            QueryValModification modification){
        for(int i=0;i < setSize;++i){
            collection.add(new Object());
        }

    }
    public final void initContains(OmniCollection.OfBoolean collection,int setSize,int initVal,double containsPosition,
            QueryValModification modification){
        var val=getBooleanVal(modification);
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal,modification);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex,modification);
    }
    public final void initContains(OmniCollection.OfByte collection,int setSize,int initVal,double containsPosition,
            QueryValModification modification){
        var val=getByteVal(modification);
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal,modification);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex,modification);
    }
    public final void initContains(OmniCollection.OfChar collection,int setSize,int initVal,double containsPosition,
            QueryValModification modification){
        var val=getCharVal(modification);
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal,modification);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex,modification);
    }
    public final void initContains(OmniCollection.OfShort collection,int setSize,int initVal,double containsPosition,
            QueryValModification modification){
        var val=getShortVal(modification);
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal,modification);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex,modification);
    }
    public final void initContains(OmniCollection.OfInt collection,int setSize,int initVal,double containsPosition,
            QueryValModification modification){
        var val=getIntVal(modification);
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal,modification);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex,modification);
    }
    public final void initContains(OmniCollection.OfLong collection,int setSize,long initVal,double containsPosition,
            QueryValModification modification){
        var val=getLongVal(modification);
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal,modification);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex,modification);
    }
    public final void initContains(OmniCollection.OfFloat collection,int setSize,float initVal,double containsPosition,
            QueryValModification modification){
        var val=getFloatVal(modification);
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal,modification);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex,modification);
    }
    public final void initContains(OmniCollection.OfDouble collection,int setSize,double initVal,
            double containsPosition,
            QueryValModification modification){
        var val=getDoubleVal(modification);
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal,modification);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex,modification);
    }
    public final void initContains(OmniCollection.OfRef<Object> collection,int setSize,long initVal,
            double containsPosition,
            QueryValModification modification){
        var val=getRefVal(modification);
        int containsIndex=(int)Math.round(containsPosition * setSize);
        initDoesNotContain(collection,containsIndex,initVal,modification);
        collection.add(val);
        initDoesNotContain(collection,setSize - containsIndex - 1,initVal + containsIndex,modification);
    }
    public enum QueryValModification{
        None,Plus1,PlusFloatEpsilon,PlusDoubleEpsilon;
    }
}
