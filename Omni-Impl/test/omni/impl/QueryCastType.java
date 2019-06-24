package omni.impl;

import java.util.Set;
import omni.api.OmniCollection;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.api.OmniStack;
public enum QueryCastType{
    Unboxed{
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.removeVal((boolean)inputVal);
            case BYTE:
                return collection.removeVal((byte)inputVal);
            case CHAR:
                return collection.removeVal((char)inputVal);
            case DOUBLE:
                return collection.removeVal((double)inputVal);
            case FLOAT:
                return collection.removeVal((float)inputVal);
            case INT:
                return collection.removeVal((int)inputVal);
            case LONG:
                return collection.removeVal((long)inputVal);
            case SHORT:
                return collection.removeVal((short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.contains((boolean)inputVal);
            case BYTE:
                return collection.contains((byte)inputVal);
            case CHAR:
                return collection.contains((char)inputVal);
            case DOUBLE:
                return collection.contains((double)inputVal);
            case FLOAT:
                return collection.contains((float)inputVal);
            case INT:
                return collection.contains((int)inputVal);
            case LONG:
                return collection.contains((long)inputVal);
            case SHORT:
                return collection.contains((short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.removeFirstOccurrence((boolean)inputVal);
            case BYTE:
                return collection.removeFirstOccurrence((byte)inputVal);
            case CHAR:
                return collection.removeFirstOccurrence((char)inputVal);
            case DOUBLE:
                return collection.removeFirstOccurrence((double)inputVal);
            case FLOAT:
                return collection.removeFirstOccurrence((float)inputVal);
            case INT:
                return collection.removeFirstOccurrence((int)inputVal);
            case LONG:
                return collection.removeFirstOccurrence((long)inputVal);
            case SHORT:
                return collection.removeFirstOccurrence((short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.removeLastOccurrence((boolean)inputVal);
            case BYTE:
                return collection.removeLastOccurrence((byte)inputVal);
            case CHAR:
                return collection.removeLastOccurrence((char)inputVal);
            case DOUBLE:
                return collection.removeLastOccurrence((double)inputVal);
            case FLOAT:
                return collection.removeLastOccurrence((float)inputVal);
            case INT:
                return collection.removeLastOccurrence((int)inputVal);
            case LONG:
                return collection.removeLastOccurrence((long)inputVal);
            case SHORT:
                return collection.removeLastOccurrence((short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.search((boolean)inputVal);
            case BYTE:
                return collection.search((byte)inputVal);
            case CHAR:
                return collection.search((char)inputVal);
            case DOUBLE:
                return collection.search((double)inputVal);
            case FLOAT:
                return collection.search((float)inputVal);
            case INT:
                return collection.search((int)inputVal);
            case LONG:
                return collection.search((long)inputVal);
            case SHORT:
                return collection.search((short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.indexOf((boolean)inputVal);
            case BYTE:
                return collection.indexOf((byte)inputVal);
            case CHAR:
                return collection.indexOf((char)inputVal);
            case DOUBLE:
                return collection.indexOf((double)inputVal);
            case FLOAT:
                return collection.indexOf((float)inputVal);
            case INT:
                return collection.indexOf((int)inputVal);
            case LONG:
                return collection.indexOf((long)inputVal);
            case SHORT:
                return collection.indexOf((short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.lastIndexOf((boolean)inputVal);
            case BYTE:
                return collection.lastIndexOf((byte)inputVal);
            case CHAR:
                return collection.lastIndexOf((char)inputVal);
            case DOUBLE:
                return collection.lastIndexOf((double)inputVal);
            case FLOAT:
                return collection.lastIndexOf((float)inputVal);
            case INT:
                return collection.lastIndexOf((int)inputVal);
            case LONG:
                return collection.lastIndexOf((long)inputVal);
            case SHORT:
                return collection.lastIndexOf((short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
    },
    ToBoxed{
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.removeVal((Boolean)inputVal);
            case BYTE:
                return collection.removeVal((Byte)inputVal);
            case CHAR:
                return collection.removeVal((Character)inputVal);
            case DOUBLE:
                return collection.removeVal((Double)inputVal);
            case FLOAT:
                return collection.removeVal((Float)inputVal);
            case INT:
                return collection.removeVal((Integer)inputVal);
            case LONG:
                return collection.removeVal((Long)inputVal);
            case SHORT:
                return collection.removeVal((Short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.contains((Boolean)inputVal);
            case BYTE:
                return collection.contains((Byte)inputVal);
            case CHAR:
                return collection.contains((Character)inputVal);
            case DOUBLE:
                return collection.contains((Double)inputVal);
            case FLOAT:
                return collection.contains((Float)inputVal);
            case INT:
                return collection.contains((Integer)inputVal);
            case LONG:
                return collection.contains((Long)inputVal);
            case SHORT:
                return collection.contains((Short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.removeFirstOccurrence((Boolean)inputVal);
            case BYTE:
                return collection.removeFirstOccurrence((Byte)inputVal);
            case CHAR:
                return collection.removeFirstOccurrence((Character)inputVal);
            case DOUBLE:
                return collection.removeFirstOccurrence((Double)inputVal);
            case FLOAT:
                return collection.removeFirstOccurrence((Float)inputVal);
            case INT:
                return collection.removeFirstOccurrence((Integer)inputVal);
            case LONG:
                return collection.removeFirstOccurrence((Long)inputVal);
            case SHORT:
                return collection.removeFirstOccurrence((Short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.removeLastOccurrence((Boolean)inputVal);
            case BYTE:
                return collection.removeLastOccurrence((Byte)inputVal);
            case CHAR:
                return collection.removeLastOccurrence((Character)inputVal);
            case DOUBLE:
                return collection.removeLastOccurrence((Double)inputVal);
            case FLOAT:
                return collection.removeLastOccurrence((Float)inputVal);
            case INT:
                return collection.removeLastOccurrence((Integer)inputVal);
            case LONG:
                return collection.removeLastOccurrence((Long)inputVal);
            case SHORT:
                return collection.removeLastOccurrence((Short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.search((Boolean)inputVal);
            case BYTE:
                return collection.search((Byte)inputVal);
            case CHAR:
                return collection.search((Character)inputVal);
            case DOUBLE:
                return collection.search((Double)inputVal);
            case FLOAT:
                return collection.search((Float)inputVal);
            case INT:
                return collection.search((Integer)inputVal);
            case LONG:
                return collection.search((Long)inputVal);
            case SHORT:
                return collection.search((Short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.indexOf((Boolean)inputVal);
            case BYTE:
                return collection.indexOf((Byte)inputVal);
            case CHAR:
                return collection.indexOf((Character)inputVal);
            case DOUBLE:
                return collection.indexOf((Double)inputVal);
            case FLOAT:
                return collection.indexOf((Float)inputVal);
            case INT:
                return collection.indexOf((Integer)inputVal);
            case LONG:
                return collection.indexOf((Long)inputVal);
            case SHORT:
                return collection.indexOf((Short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
                return collection.lastIndexOf((Boolean)inputVal);
            case BYTE:
                return collection.lastIndexOf((Byte)inputVal);
            case CHAR:
                return collection.lastIndexOf((Character)inputVal);
            case DOUBLE:
                return collection.lastIndexOf((Double)inputVal);
            case FLOAT:
                return collection.lastIndexOf((Float)inputVal);
            case INT:
                return collection.lastIndexOf((Integer)inputVal);
            case LONG:
                return collection.lastIndexOf((Long)inputVal);
            case SHORT:
                return collection.lastIndexOf((Short)inputVal);
            case REF:
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
    },
    ToObject{
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
            case REF:
                return collection.remove(inputVal);
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
            case REF:
                return collection.removeFirstOccurrence(inputVal);
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
            case REF:
                return collection.removeLastOccurrence(inputVal);
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
            case REF:
                return collection.indexOf(inputVal);
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
            case REF:
                return collection.lastIndexOf(inputVal);
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
            case REF:
                return collection.search(inputVal);
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,DataType inputType){
            switch(inputType){
            case BOOLEAN:
            case BYTE:
            case CHAR:
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:
            case SHORT:
            case REF:
                return collection.contains(inputVal);
            }
            throw new UnsupportedOperationException("Unknown inputType " + inputType);
        }
    };
    public final Set<DataType> validDataTypes;
    QueryCastType(){
        this.validDataTypes=initValidDataTypes(this);
    }
    public abstract int callsearch(OmniStack<?> collection,Object inputVal,DataType inputType);
    public abstract int calllastIndexOf(OmniList<?> collection,Object inputVal,DataType inputType);
    public abstract int callindexOf(OmniList<?> collection,Object inputVal,DataType inputType);
    public abstract boolean callremoveVal(OmniCollection<?> collection,Object inputVal,DataType inputType);
    public abstract boolean callcontains(OmniCollection<?> collection,Object inputVal,DataType inputType);
    public abstract boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,DataType inputType);
    public abstract boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,DataType inputType);
    public static final Set<QueryCastType> AllTypes=Set.of(QueryCastType.Unboxed,QueryCastType.ToBoxed,
            QueryCastType.ToObject);
    public static final Set<QueryCastType> NotPrimitive=Set.of(QueryCastType.ToBoxed,QueryCastType.ToObject);
    private static Set<DataType> initValidDataTypes(QueryCastType queryCastType){
        switch(queryCastType){
        case ToObject:
            return DataType.getDataTypeSet(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,
                    DataType.LONG,DataType.FLOAT,DataType.DOUBLE,DataType.REF);
        case ToBoxed:
        case Unboxed:
            return DataType.getDataTypeSet(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,
                    DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        }
        throw new UnsupportedOperationException("Unknown queryCastType " + queryCastType);
    }
}
