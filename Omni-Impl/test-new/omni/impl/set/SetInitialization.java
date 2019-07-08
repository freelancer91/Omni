package omni.impl.set;

import java.util.EnumSet;
import java.util.Random;
import omni.api.OmniSet;
import omni.impl.DataType;
import omni.impl.MonitoredSet;
public enum SetInitialization{
    Empty("Empty",true){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            return monitoredSet;
        }
    },
    AddTrue("AddTrue",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            if(monitoredSet.getDataType() == DataType.REF){
                @SuppressWarnings("unchecked")
                var set=(OmniSet.OfRef<Object>)monitoredSet.getCollection();
                set.add(Boolean.TRUE);
            }else{
                var set=(OmniSet.BooleanInput<?>)monitoredSet.getCollection();
                set.add(true);
            }
            monitoredSet.updateCollectionState();
            return monitoredSet;
        }
    },
    AddFalse("AddFalse",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            if(monitoredSet.getDataType() == DataType.REF){
                @SuppressWarnings("unchecked")
                var set=(OmniSet.OfRef<Object>)monitoredSet.getCollection();
                set.add(Boolean.FALSE);
            }else{
                var set=(OmniSet.BooleanInput<?>)monitoredSet.getCollection();
                set.add(false);
            }
            monitoredSet.updateCollectionState();
            return monitoredSet;
        }
    },
    AddTrueAndFalse("AddTrueAndFalse",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            if(monitoredSet.getDataType() == DataType.REF){
                @SuppressWarnings("unchecked")
                var set=(OmniSet.OfRef<Object>)monitoredSet.getCollection();
                set.add(Boolean.FALSE);
                set.add(Boolean.TRUE);
            }else{
                var set=(OmniSet.BooleanInput<?>)monitoredSet.getCollection();
                set.add(false);
                set.add(true);
            }
            monitoredSet.updateCollectionState();
            return monitoredSet;
        }

    },
    AddPrime("AddPrime",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            monitoredSet.add(1231);
            return monitoredSet;
        }
    },
    AddFibSeq("AddFibSeq",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            final var dataType=monitoredSet.getDataType();
            switch(dataType){
            case BYTE:{
                final var set=(OmniSet.OfByte)monitoredSet.getCollection();
                set.add((byte)0);
                set.add((byte)1);
                set.add((byte)2);
                long pPrev=1;
                long prev=2;
                for(int i=3;i < 100;++i){
                    final long curr=pPrev + prev;
                    set.add((byte)curr);
                    pPrev=prev;
                    prev=curr;
                }
                break;
            }
            case CHAR:{
                final var set=(OmniSet.OfChar)monitoredSet.getCollection();
                set.add((char)0);
                set.add((char)1);
                set.add((char)2);
                long pPrev=1;
                long prev=2;
                for(int i=3;i < 100;++i){
                    final long curr=pPrev + prev;
                    set.add((char)curr);
                    pPrev=prev;
                    prev=curr;
                }
                break;
            }
            case SHORT:{
                final var set=(OmniSet.OfShort)monitoredSet.getCollection();
                set.add((short)0);
                set.add((short)1);
                set.add((short)2);
                long pPrev=1;
                long prev=2;
                for(int i=3;i < 100;++i){
                    final long curr=pPrev + prev;
                    set.add((short)curr);
                    pPrev=prev;
                    prev=curr;
                }
                break;
            }
            case INT:{
                final var set=(OmniSet.OfInt)monitoredSet.getCollection();
                set.add(0);
                set.add(1);
                set.add(2);
                long pPrev=1;
                long prev=2;
                for(int i=3;i < 100;++i){
                    final long curr=pPrev + prev;
                    set.add((int)curr);
                    pPrev=prev;
                    prev=curr;
                }
                break;
            }
            case LONG:
            case FLOAT:
            case DOUBLE:{
                final var set=(OmniSet.LongInput<?>)monitoredSet.getCollection();
                set.add((long)0);
                set.add((long)1);
                set.add((long)2);
                long pPrev=1;
                long prev=2;
                for(int i=3;i < 100;++i){
                    final long curr=pPrev + prev;
                    set.add(curr);
                    pPrev=prev;
                    prev=curr;
                }
                break;
            }
            case REF:{
                @SuppressWarnings("unchecked")
                final var set=(OmniSet.OfRef<Object>)monitoredSet.getCollection();
                set.add((long)0);
                set.add((long)1);
                set.add((long)2);
                long pPrev=1;
                long prev=2;
                for(int i=3;i < 100;++i){
                    final long curr=pPrev + prev;
                    set.add(curr);
                    pPrev=prev;
                    prev=curr;
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            monitoredSet.updateCollectionState();
            return monitoredSet;
        }
    },
    AddMinByte("AddMinByte",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            monitoredSet.add(Byte.MIN_VALUE);
            return monitoredSet;
        }
    },
    FillWord0("FillWord0",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            final var dataType=monitoredSet.getDataType();
            switch(dataType){
            case BYTE:{
                var set=(OmniSet.OfByte)monitoredSet.getCollection();
                for(int i=-128;i < -64;++i){
                    set.add((byte)i);
                }
                break;
            }
            case CHAR:{
                var set=(OmniSet.OfChar)monitoredSet.getCollection();
                for(int i=0;i < 64;++i){
                    set.add((char)i);
                }
                break;
            }
            case SHORT:{
                var set=(OmniSet.OfShort)monitoredSet.getCollection();
                for(int i=-128;i < -64;++i){
                    set.add((short)i);
                }
                break;
            }
            case INT:{
                var set=(OmniSet.OfInt)monitoredSet.getCollection();
                for(int i=-128;i < -64;++i){
                    set.add(i);
                }
                break;
            }
            case LONG:
            case FLOAT:
            case DOUBLE:{
                var set=(OmniSet.LongInput<?>)monitoredSet.getCollection();
                for(long i=-128;i < -64;++i){
                    set.add(i);
                }
                break;
            }
            case REF:
                @SuppressWarnings("unchecked")
                var set=(OmniSet.OfRef<Object>)monitoredSet.getCollection();
                for(long i=-128;i < -64;++i){
                    set.add(i);
                }
                break;
            default:
                throw dataType.invalid();
            }
            monitoredSet.updateCollectionState();
            return monitoredSet;
        }
    },
    FillWord1("FillWord1",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            final var dataType=monitoredSet.getDataType();
            switch(dataType){
            case BYTE:{
                var set=(OmniSet.OfByte)monitoredSet.getCollection();
                for(int i=-64;i < 0;++i){
                    set.add((byte)i);
                }
                break;
            }
            case CHAR:{
                var set=(OmniSet.OfChar)monitoredSet.getCollection();
                for(int i=64;i < 128;++i){
                    set.add((char)i);
                }
                break;
            }
            case SHORT:{
                var set=(OmniSet.OfShort)monitoredSet.getCollection();
                for(int i=-64;i < 0;++i){
                    set.add((short)i);
                }
                break;
            }
            case INT:{
                var set=(OmniSet.OfInt)monitoredSet.getCollection();
                for(int i=-64;i < 0;++i){
                    set.add(i);
                }
                break;
            }
            case LONG:
            case FLOAT:
            case DOUBLE:{
                var set=(OmniSet.LongInput<?>)monitoredSet.getCollection();
                for(long i=-64;i < 0;++i){
                    set.add(i);
                }
                break;
            }
            case REF:
                @SuppressWarnings("unchecked")
                var set=(OmniSet.OfRef<Object>)monitoredSet.getCollection();
                for(long i=-64;i < 0;++i){
                    set.add(i);
                }
                break;
            default:
                throw dataType.invalid();
            }
            monitoredSet.updateCollectionState();
            return monitoredSet;
        }
    },
    FillWord2("FillWord2",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            final var dataType=monitoredSet.getDataType();
            switch(dataType){
            case BYTE:{
                var set=(OmniSet.OfByte)monitoredSet.getCollection();
                for(int i=0;i < 64;++i){
                    set.add((byte)i);
                }
                break;
            }
            case CHAR:{
                var set=(OmniSet.OfChar)monitoredSet.getCollection();
                for(int i=128;i < 192;++i){
                    set.add((char)i);
                }
                break;
            }
            case SHORT:{
                var set=(OmniSet.OfShort)monitoredSet.getCollection();
                for(int i=0;i < 64;++i){
                    set.add((short)i);
                }
                break;
            }
            case INT:{
                var set=(OmniSet.OfInt)monitoredSet.getCollection();
                for(int i=0;i < 64;++i){
                    set.add(i);
                }
                break;
            }
            case LONG:
            case FLOAT:
            case DOUBLE:{
                var set=(OmniSet.LongInput<?>)monitoredSet.getCollection();
                for(long i=0;i < 64;++i){
                    set.add(i);
                }
                break;
            }
            case REF:
                @SuppressWarnings("unchecked")
                var set=(OmniSet.OfRef<Object>)monitoredSet.getCollection();
                for(long i=0;i < 64;++i){
                    set.add(i);
                }
                break;
            default:
                throw dataType.invalid();
            }
            monitoredSet.updateCollectionState();
            return monitoredSet;
        }
    },
    FillWord3("FillWord3",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            final var dataType=monitoredSet.getDataType();
            switch(dataType){
            case BYTE:{
                var set=(OmniSet.OfByte)monitoredSet.getCollection();
                for(int i=64;i < 128;++i){
                    set.add((byte)i);
                }
                break;
            }
            case CHAR:{
                var set=(OmniSet.OfChar)monitoredSet.getCollection();
                for(int i=192;i < 256;++i){
                    set.add((char)i);
                }
                break;
            }
            case SHORT:{
                var set=(OmniSet.OfShort)monitoredSet.getCollection();
                for(int i=64;i < 128;++i){
                    set.add((short)i);
                }
                break;
            }
            case INT:{
                var set=(OmniSet.OfInt)monitoredSet.getCollection();
                for(int i=64;i < 128;++i){
                    set.add(i);
                }
                break;
            }
            case LONG:
            case FLOAT:
            case DOUBLE:{
                var set=(OmniSet.LongInput<?>)monitoredSet.getCollection();
                for(long i=64;i < 128;++i){
                    set.add(i);
                }
                break;
            }
            case REF:
                @SuppressWarnings("unchecked")
                var set=(OmniSet.OfRef<Object>)monitoredSet.getCollection();
                for(long i=64;i < 128;++i){
                    set.add(i);
                }
                break;
            default:
                throw dataType.invalid();
            }
            monitoredSet.updateCollectionState();
            return monitoredSet;
        }
    },
    AddNull("AddNull",false){
        @SuppressWarnings("unchecked")
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            ((OmniSet.OfRef<Object>)monitoredSet.getCollection()).add(null);
            monitoredSet.updateCollectionState();
            return monitoredSet;
        }
    },
    Add200RemoveThenAdd100More("Add200RemoveThenAdd100More",false){
        @Override
        public <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet){
            final var dataType=monitoredSet.getDataType();
            switch(dataType){
            case CHAR:{
                var set=(OmniSet.OfChar)monitoredSet.getCollection();
                for(int i=256;i < 456;++i){
                    set.add((char)i);
                }
                for(int i=256;i < 456;++i){
                    set.removeVal((char)i);
                }
                for(int i=456;i < 556;++i){
                    set.add((char)i);
                }
                break;
            }
            case SHORT:{
                var set=(OmniSet.OfShort)monitoredSet.getCollection();
                for(int i=128;i < 328;++i){
                    set.add((short)i);
                }
                for(int i=128;i < 328;++i){
                    set.removeVal((short)i);
                }
                for(int i=328;i < 428;++i){
                    set.add((short)i);
                }
                break;
            }
            case INT:{
                var set=(OmniSet.OfInt)monitoredSet.getCollection();
                for(int i=128;i < 328;++i){
                    set.add(i);
                }
                for(int i=128;i < 328;++i){
                    set.removeVal(i);
                }
                for(int i=328;i < 428;++i){
                    set.add(i);
                }
                break;
            }
            case LONG:{
                var set=(OmniSet.OfLong)monitoredSet.getCollection();
                for(long i=128;i < 328;++i){
                    set.add(i);
                }
                for(long i=128;i < 328;++i){
                    set.removeVal(i);
                }
                for(long i=328;i < 428;++i){
                    set.add(i);
                }
                break;
            }
            case FLOAT:{

                var set=(OmniSet.OfFloat)monitoredSet.getCollection();
                Random rand=new Random(0);
                {
                    for(int i=200;;){
                        if(set.add(rand.nextFloat()) && --i == 0){
                            break;
                        }
                    }
                }
                {
                    var itr=set.iterator();
                    while(itr.hasNext()){
                        itr.nextFloat();
                        itr.remove();
                    }
                }
                {
                    for(int i=100;;){
                        if(set.add(rand.nextFloat()) && --i == 0){
                            break;
                        }
                    }
                }
                break;
            }
            case DOUBLE:{
                var set=(OmniSet.OfDouble)monitoredSet.getCollection();
                Random rand=new Random(0);
                {
                    for(int i=200;;){
                        if(set.add(rand.nextDouble()) && --i == 0){
                            break;
                        }
                    }
                }
                {
                    var itr=set.iterator();
                    while(itr.hasNext()){
                        itr.nextDouble();
                        itr.remove();
                    }
                }
                {
                    for(int i=100;;){
                        if(set.add(rand.nextDouble()) && --i == 0){
                            break;
                        }
                    }
                }
                break;
            }
            case REF:
                @SuppressWarnings("unchecked")
                var set=(OmniSet.OfRef<Object>)monitoredSet.getCollection();
                Random rand=new Random(0);{
                    for(int i=200;;){
                        if(set.add(rand.nextDouble()) && --i == 0){
                            break;
                        }
                    }
                }
                set.add(null);
                {
                    var itr=set.iterator();
                    while(itr.hasNext()){
                        itr.next();
                        itr.remove();
                    }
                }
                {
                    for(int i=100;;){
                        if(set.add(rand.nextDouble()) && --i == 0){
                            break;
                        }
                    }
                }
                set.add(null);
                break;
            default:
                throw dataType.invalid();
            }
            monitoredSet.updateCollectionState();
            return monitoredSet;
        }
    };
    public final UnsupportedOperationException invalid(){
        throw new UnsupportedOperationException("Invalid SetInitializationSequence " + this);
    }
    public final String name;
    public final boolean isEmpty;
    public final EnumSet<DataType> validDataTypes;
    SetInitialization(String name,boolean isEmpty){
        this.name=name;
        this.isEmpty=isEmpty;
        this.validDataTypes=initValidDataTypes(this);
    }
    private static final EnumSet<DataType> initValidDataTypes(SetInitialization initSeq){
        switch(initSeq.name){
        case "Empty":
            return DataType.getDataTypeSet("BOOLEAN,BYTE,CHAR,SHORT,INT,LONG,FLOAT,DOUBLE,REF");
        case "AddTrue":
        case "AddFalse":
        case "AddTrueAndFalse":
            return DataType.getDataTypeSet("BOOLEAN,BYTE,CHAR,SHORT,INT,LONG,FLOAT,DOUBLE,REF");
        case "AddPrime":
        case "AddFibSeq":
        case "AddMinByte":
        case "FillWord0":
        case "FillWord1":
        case "FillWord2":
        case "FillWord3":
            return DataType.getDataTypeSet("BYTE,CHAR,SHORT,INT,LONG,FLOAT,DOUBLE,REF");
        case "Add200RemoveThenAdd100More":
            return DataType.getDataTypeSet("CHAR,SHORT,INT,LONG,FLOAT,DOUBLE,REF");
        case "AddNull":
            return DataType.getDataTypeSet("REF");
        }
        throw initSeq.invalid();
    }
    public abstract <SET extends MonitoredSet<?>> SET initialize(SET monitoredSet);
}
