package omni.impl;

import java.util.ConcurrentModificationException;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;

public enum MonitoredComparatorGen{
    NullComparatorThrowAIOB(false,ComparatorType.NaturalOrder,IllegalArgumentException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ThrowAIOB.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NullComparatorThrowIOB(false,ComparatorType.NaturalOrder,IndexOutOfBoundsException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ThrowIOB.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NullComparatorModCollection(false,ComparatorType.NaturalOrder,ConcurrentModificationException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ModCollection.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NullComparatorModParent(false,ComparatorType.NaturalOrder,ConcurrentModificationException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ModParent.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NullComparatorModRoot(false,ComparatorType.NaturalOrder,ConcurrentModificationException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ModRoot.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NullComparatorModCollectionThrowAIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ModCollectionThrowAIOB.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NullComparatorModCollectionThrowIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ModCollectionThrowIOB.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NullComparatorModParentThrowAIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ModParentThrowAIOB.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NullComparatorModParentThrowIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ModParentThrowIOB.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NullComparatorModRootThrowAIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ModRootThrowAIOB.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NullComparatorModRootThrowIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            int halfwayPoint=listSize / 2;
            for(int i=0;i < halfwayPoint;++i){
                listMonitor.add(new MonitoredObject(i));
            }
            listMonitor.add(MonitoredObjectGen.ModRootThrowIOB.getMonitoredObject(listMonitor,halfwayPoint));
            for(int i=halfwayPoint + 1;i < listSize;++i){
                listMonitor.add(new MonitoredObject(i));
            }
        }
    },
    NoThrowAscending(true,ComparatorType.Ascending,null){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator();
        }
    },
    NoThrowDescending(true,ComparatorType.Descending,null){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected int impl(double val1,double val2){
                    return -Double.compare(val1,val2);
                }
                @Override
                protected int impl(long val1,long val2){
                    return -Long.compare(val1,val2);
                }
                @SuppressWarnings({"unchecked","rawtypes"})
                @Override
                protected int impl(Object val1,Object val2){
                    return -((Comparable)val1).compareTo(val2);
                }
            };
        }
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            for(int i=0;i < listSize;++i){
                listMonitor.add(i);
            }
        }
        @SuppressWarnings({"unchecked","rawtypes"})
        @Override
        void assertSorted(MonitoredList<?,?> listMonitor){
            var uncastItr=listMonitor.getMonitoredIterator().getIterator();
            if(!uncastItr.hasNext()){
                return;
            }
            var dataType=listMonitor.getDataType();
            switch(dataType){
            case BOOLEAN:{
                var itr=(OmniIterator.OfBoolean)uncastItr;
                for(boolean next,prev=itr.nextBoolean();itr.hasNext();prev=next){
                    Assertions.assertTrue(Boolean.compare(prev,next=itr.nextBoolean()) >= 0);
                }
                return;
            }
            case BYTE:{
                var itr=(OmniIterator.OfByte)uncastItr;
                for(byte next,prev=itr.nextByte();itr.hasNext();prev=next){
                    Assertions.assertTrue(Byte.compare(prev,next=itr.nextByte()) >= 0);
                }
                return;
            }
            case CHAR:{
                var itr=(OmniIterator.OfChar)uncastItr;
                for(char next,prev=itr.nextChar();itr.hasNext();prev=next){
                    Assertions.assertTrue(Character.compare(prev,next=itr.nextChar()) >= 0);
                }
                return;
            }
            case DOUBLE:{
                var itr=(OmniIterator.OfDouble)uncastItr;
                for(double next,prev=itr.nextDouble();itr.hasNext();prev=next){
                    Assertions.assertTrue(Double.compare(prev,next=itr.nextDouble()) >= 0);
                }
                return;
            }
            case FLOAT:{
                var itr=(OmniIterator.OfFloat)uncastItr;
                for(float next,prev=itr.nextFloat();itr.hasNext();prev=next){
                    Assertions.assertTrue(Float.compare(prev,next=itr.nextFloat()) >= 0);
                }
                return;
            }
            case INT:{
                var itr=(OmniIterator.OfInt)uncastItr;
                for(int next,prev=itr.nextInt();itr.hasNext();prev=next){
                    Assertions.assertTrue(Integer.compare(prev,next=itr.nextInt()) >= 0);
                }
                return;
            }
            case LONG:{
                var itr=(OmniIterator.OfLong)uncastItr;
                for(long next,prev=itr.nextLong();itr.hasNext();prev=next){
                    Assertions.assertTrue(Long.compare(prev,next=itr.nextLong()) >= 0);
                }
                return;
            }
            case REF:{
                var itr=(OmniIterator.OfRef)uncastItr;
                for(Comparable next,prev=(Comparable)itr.next();itr.hasNext();prev=next){
                    Assertions.assertTrue(prev.compareTo(next=(Comparable)itr.next()) >= 0);
                }
                return;
            }
            case SHORT:{
                var itr=(OmniIterator.OfShort)uncastItr;
                for(short next,prev=itr.nextShort();itr.hasNext();prev=next){
                    Assertions.assertTrue(Short.compare(prev,next=itr.nextShort()) >= 0);
                }
                return;
            }
            }
            throw DataType.invalidDataType(dataType);
        }
    },
    NullComparator(true,ComparatorType.NaturalOrder,null){
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            if(listMonitor.getDataType() == DataType.REF){
                for(int i=listSize;--i >= 0;){
                    listMonitor.add(new MonitoredObject(i));
                }
            }else{
                super.initUnsortedHelper(listMonitor,listSize);
            }
        }
    },
    ThrowAIOB(true,ComparatorType.ImmediatelyThrows,IllegalArgumentException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    throw new ArrayIndexOutOfBoundsException();
                }
            };
        }
    },
    ThrowIOB(true,ComparatorType.ImmediatelyThrows,IndexOutOfBoundsException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    throw new IndexOutOfBoundsException();
                }
            };
        }
    },
    ModCollectionAscending(true,ComparatorType.Ascending,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModCollection);
                }
            };
        }
    },
    ModCollectionDescending(true,ComparatorType.Descending,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModCollection);
                }
                @Override
                protected int impl(double val1,double val2){
                    return -Double.compare(val1,val2);
                }
                @Override
                protected int impl(long val1,long val2){
                    return -Long.compare(val1,val2);
                }
                @SuppressWarnings({"unchecked","rawtypes"})
                @Override
                protected int impl(Object val1,Object val2){
                    return -((Comparable)val1).compareTo(val2);
                }
            };
        }
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            for(int i=0;i < listSize;++i){
                listMonitor.add(i);
            }
        }
    },
    ModParentAscending(true,ComparatorType.Ascending,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModParent);
                }
            };
        }
    },
    ModParentDescending(true,ComparatorType.Descending,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModParent);
                }
                @Override
                protected int impl(double val1,double val2){
                    return -Double.compare(val1,val2);
                }
                @Override
                protected int impl(long val1,long val2){
                    return -Long.compare(val1,val2);
                }
                @SuppressWarnings({"unchecked","rawtypes"})
                @Override
                protected int impl(Object val1,Object val2){
                    return -((Comparable)val1).compareTo(val2);
                }
            };
        }
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            for(int i=0;i < listSize;++i){
                listMonitor.add(i);
            }
        }
    },
    ModRootAscending(true,ComparatorType.Ascending,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModRoot);
                }
            };
        }
    },
    ModRootDescending(true,ComparatorType.Descending,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModRoot);
                }
                @Override
                protected int impl(double val1,double val2){
                    return -Double.compare(val1,val2);
                }
                @Override
                protected int impl(long val1,long val2){
                    return -Long.compare(val1,val2);
                }
                @SuppressWarnings({"unchecked","rawtypes"})
                @Override
                protected int impl(Object val1,Object val2){
                    return -((Comparable)val1).compareTo(val2);
                }
            };
        }
        @Override
        void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
            for(int i=0;i < listSize;++i){
                listMonitor.add(i);
            }
        }
    },
    ModCollectionThrowAIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModCollection);
                    throw new ArrayIndexOutOfBoundsException();
                }
            };
        }
    },
    ModCollectionThrowIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModCollection);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
    },
    ModParentThrowAIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModParent);
                    throw new ArrayIndexOutOfBoundsException();
                }
            };
        }
    },
    ModParentThrowIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModParent);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
    },
    ModRootThrowAIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModRoot);
                    throw new ArrayIndexOutOfBoundsException();
                }
            };
        }
    },
    ModRootThrowIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModRoot);
                    throw new IndexOutOfBoundsException();
                }
            };
        }

    },;
    public final boolean validWithPrimitive;
    public final ComparatorType comparatorType;
    public final Class<? extends Throwable> expectedException;
    MonitoredComparatorGen(boolean validWithPrimitive,ComparatorType comparatorType,
            Class<? extends Throwable> expectedException){
        this.validWithPrimitive=validWithPrimitive;
        this.comparatorType=comparatorType;
        this.expectedException=expectedException;
    }
    public enum ComparatorType{
        Ascending,Descending,NaturalOrder,ImmediatelyThrows;
    }
    public MonitoredComparator getMonitoredComparator(MonitoredList<?,?> listMonitor){
        throw new UnsupportedOperationException();
    }
    @SuppressWarnings({"unchecked","rawtypes"})
    void assertSorted(MonitoredList<?,?> listMonitor){
        var uncastItr=listMonitor.getMonitoredIterator().getIterator();
        if(!uncastItr.hasNext()){
            return;
        }
        var dataType=listMonitor.getDataType();
        switch(dataType){
        case BOOLEAN:{
            var itr=(OmniIterator.OfBoolean)uncastItr;
            for(boolean next,prev=itr.nextBoolean();itr.hasNext();prev=next){
                Assertions.assertTrue(Boolean.compare(prev,next=itr.nextBoolean()) <= 0);
            }
            return;
        }
        case BYTE:{
            var itr=(OmniIterator.OfByte)uncastItr;
            for(byte next,prev=itr.nextByte();itr.hasNext();prev=next){
                Assertions.assertTrue(Byte.compare(prev,next=itr.nextByte()) <= 0);
            }
            return;
        }
        case CHAR:{
            var itr=(OmniIterator.OfChar)uncastItr;
            for(char next,prev=itr.nextChar();itr.hasNext();prev=next){
                Assertions.assertTrue(Character.compare(prev,next=itr.nextChar()) <= 0);
            }
            return;
        }
        case DOUBLE:{
            var itr=(OmniIterator.OfDouble)uncastItr;
            for(double next,prev=itr.nextDouble();itr.hasNext();prev=next){
                Assertions.assertTrue(Double.compare(prev,next=itr.nextDouble()) <= 0);
            }
            return;
        }
        case FLOAT:{
            var itr=(OmniIterator.OfFloat)uncastItr;
            for(float next,prev=itr.nextFloat();itr.hasNext();prev=next){
                Assertions.assertTrue(Float.compare(prev,next=itr.nextFloat()) <= 0);
            }
            return;
        }
        case INT:{
            var itr=(OmniIterator.OfInt)uncastItr;
            for(int next,prev=itr.nextInt();itr.hasNext();prev=next){
                Assertions.assertTrue(Integer.compare(prev,next=itr.nextInt()) <= 0);
            }
            return;
        }
        case LONG:{
            var itr=(OmniIterator.OfLong)uncastItr;
            for(long next,prev=itr.nextLong();itr.hasNext();prev=next){
                Assertions.assertTrue(Long.compare(prev,next=itr.nextLong()) <= 0);
            }
            return;
        }
        case REF:{
            var itr=(OmniIterator.OfRef)uncastItr;
            for(Comparable next,prev=(Comparable)itr.next();itr.hasNext();prev=next){
                Assertions.assertTrue(prev.compareTo(next=(Comparable)itr.next()) <= 0);
            }
            return;
        }
        case SHORT:{
            var itr=(OmniIterator.OfShort)uncastItr;
            for(short next,prev=itr.nextShort();itr.hasNext();prev=next){
                Assertions.assertTrue(Short.compare(prev,next=itr.nextShort()) <= 0);
            }
            return;
        }
        }
        throw DataType.invalidDataType(dataType);
    }
    void initUnsortedHelper(MonitoredList<?,?> listMonitor,int listSize){
        for(int i=listSize;--i >= 0;){
            listMonitor.add(i);
        }
    }

    public void initUnsorted(MonitoredList<?,?> listMonitor,int listSize){
        if(listSize <= 1){
            throw new UnsupportedOperationException("List size must be > 1");
        }
        initUnsortedHelper(listMonitor,listSize);
    }

}
