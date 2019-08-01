package omni.impl;

import java.util.ConcurrentModificationException;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;

public enum MonitoredComparatorGen{
    NullComparatorThrowAIOB(false,ComparatorType.NaturalOrder,IllegalArgumentException.class,true,0){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NullComparatorThrowIOB(false,ComparatorType.NaturalOrder,IndexOutOfBoundsException.class,true,0){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NullComparatorModCollection(false,ComparatorType.NaturalOrder,ConcurrentModificationException.class,true,0){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NullComparatorModParent(false,ComparatorType.NaturalOrder,ConcurrentModificationException.class,true,2){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NullComparatorModRoot(false,ComparatorType.NaturalOrder,ConcurrentModificationException.class,true,1){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NullComparatorModCollectionThrowAIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,
            true,0){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NullComparatorModCollectionThrowIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,
            true,0){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NullComparatorModParentThrowAIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,true,2){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NullComparatorModParentThrowIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,true,2){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NullComparatorModRootThrowAIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,true,1){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NullComparatorModRootThrowIOB(false,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,true,1){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
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
    NoThrowAscending(true,ComparatorType.Ascending,null,false,0){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator();
        }
    },
    NoThrowDescending(true,ComparatorType.Descending,null,false,0){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
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
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
            for(int i=0;i < listSize;++i){
                listMonitor.add(i);
            }
        }
        @SuppressWarnings({"unchecked","rawtypes"})
        @Override
        public void assertReverseSorted(MonitoredList<?> listMonitor){
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
                break;
            }
            case BYTE:{
                var itr=(OmniIterator.OfByte)uncastItr;
                for(byte next,prev=itr.nextByte();itr.hasNext();prev=next){
                    Assertions.assertTrue(Byte.compare(prev,next=itr.nextByte()) <= 0);
                }
                break;
            }
            case CHAR:{
                var itr=(OmniIterator.OfChar)uncastItr;
                for(char next,prev=itr.nextChar();itr.hasNext();prev=next){
                    Assertions.assertTrue(Character.compare(prev,next=itr.nextChar()) <= 0);
                }
                break;
            }
            case DOUBLE:{
                var itr=(OmniIterator.OfDouble)uncastItr;
                for(double next,prev=itr.nextDouble();itr.hasNext();prev=next){
                    Assertions.assertTrue(Double.compare(prev,next=itr.nextDouble()) <= 0);
                }
                break;
            }
            case FLOAT:{
                var itr=(OmniIterator.OfFloat)uncastItr;
                for(float next,prev=itr.nextFloat();itr.hasNext();prev=next){
                    Assertions.assertTrue(Float.compare(prev,next=itr.nextFloat()) <= 0);
                }
                break;
            }
            case INT:{
                var itr=(OmniIterator.OfInt)uncastItr;
                for(int next,prev=itr.nextInt();itr.hasNext();prev=next){
                    Assertions.assertTrue(Integer.compare(prev,next=itr.nextInt()) <= 0);
                }
                break;
            }
            case LONG:{
                var itr=(OmniIterator.OfLong)uncastItr;
                for(long next,prev=itr.nextLong();itr.hasNext();prev=next){
                    Assertions.assertTrue(Long.compare(prev,next=itr.nextLong()) <= 0);
                }
                break;
            }
            case REF:{
                var itr=(OmniIterator.OfRef)uncastItr;
                for(Comparable next,prev=(Comparable)itr.next();itr.hasNext();prev=next){
                    Assertions.assertTrue(prev.compareTo(next=(Comparable)itr.next()) <= 0);
                }
                break;
            }
            case SHORT:{
                var itr=(OmniIterator.OfShort)uncastItr;
                for(short next,prev=itr.nextShort();itr.hasNext();prev=next){
                    Assertions.assertTrue(Short.compare(prev,next=itr.nextShort()) <= 0);
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            if(listMonitor.size() > 1){
                listMonitor.incrementModCount();
                listMonitor.copyListContents();
            }

        }
        @SuppressWarnings({"unchecked","rawtypes"})
        @Override
        public void assertSorted(MonitoredList<?> listMonitor){
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
                break;
            }
            case BYTE:{
                var itr=(OmniIterator.OfByte)uncastItr;
                for(byte next,prev=itr.nextByte();itr.hasNext();prev=next){
                    Assertions.assertTrue(Byte.compare(prev,next=itr.nextByte()) >= 0);
                }
                break;
            }
            case CHAR:{
                var itr=(OmniIterator.OfChar)uncastItr;
                for(char next,prev=itr.nextChar();itr.hasNext();prev=next){
                    Assertions.assertTrue(Character.compare(prev,next=itr.nextChar()) >= 0);
                }
                break;
            }
            case DOUBLE:{
                var itr=(OmniIterator.OfDouble)uncastItr;
                for(double next,prev=itr.nextDouble();itr.hasNext();prev=next){
                    Assertions.assertTrue(Double.compare(prev,next=itr.nextDouble()) >= 0);
                }
                break;
            }
            case FLOAT:{
                var itr=(OmniIterator.OfFloat)uncastItr;
                for(float next,prev=itr.nextFloat();itr.hasNext();prev=next){
                    Assertions.assertTrue(Float.compare(prev,next=itr.nextFloat()) >= 0);
                }
                break;
            }
            case INT:{
                var itr=(OmniIterator.OfInt)uncastItr;
                for(int next,prev=itr.nextInt();itr.hasNext();prev=next){
                    Assertions.assertTrue(Integer.compare(prev,next=itr.nextInt()) >= 0);
                }
                break;
            }
            case LONG:{
                var itr=(OmniIterator.OfLong)uncastItr;
                for(long next,prev=itr.nextLong();itr.hasNext();prev=next){
                    Assertions.assertTrue(Long.compare(prev,next=itr.nextLong()) >= 0);
                }
                break;
            }
            case REF:{
                var itr=(OmniIterator.OfRef)uncastItr;
                for(Comparable next,prev=(Comparable)itr.next();itr.hasNext();prev=next){
                    Assertions.assertTrue(prev.compareTo(next=(Comparable)itr.next()) >= 0);
                }
                break;
            }
            case SHORT:{
                var itr=(OmniIterator.OfShort)uncastItr;
                for(short next,prev=itr.nextShort();itr.hasNext();prev=next){
                    Assertions.assertTrue(Short.compare(prev,next=itr.nextShort()) >= 0);
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            if(listMonitor.size() > 1){
                listMonitor.incrementModCount();
                listMonitor.copyListContents();
            }

        }
    },
    NullComparator(true,ComparatorType.NaturalOrder,null,true,0){
        @Override
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
            if(listMonitor.getDataType() == DataType.REF){
                for(int i=listSize;--i >= 0;){
                    listMonitor.add(new MonitoredObject(i));
                }
            }else{
                super.initUnsortedHelper(listMonitor,listSize);
            }
        }
    },
    ThrowAIOB(true,ComparatorType.ImmediatelyThrows,IllegalArgumentException.class,false,0){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    throw new ArrayIndexOutOfBoundsException();
                }
            };
        }
    },
    ThrowIOB(true,ComparatorType.ImmediatelyThrows,IndexOutOfBoundsException.class,false,0){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    throw new IndexOutOfBoundsException();
                }
            };
        }
    },
    ModCollectionAscending(true,ComparatorType.Ascending,ConcurrentModificationException.class,false,0){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModCollection);
                }
            };
        }
    },
    ModCollectionDescending(true,ComparatorType.Descending,ConcurrentModificationException.class,false,0){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
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
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
            for(int i=0;i < listSize;++i){
                listMonitor.add(i);
            }
        }
    },
    ModParentAscending(true,ComparatorType.Ascending,ConcurrentModificationException.class,false,2){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModParent);
                }
            };
        }
    },
    ModParentDescending(true,ComparatorType.Descending,ConcurrentModificationException.class,false,2){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
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
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
            for(int i=0;i < listSize;++i){
                listMonitor.add(i);
            }
        }
    },
    ModRootAscending(true,ComparatorType.Ascending,ConcurrentModificationException.class,false,1){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModRoot);
                }
            };
        }
    },
    ModRootDescending(true,ComparatorType.Descending,ConcurrentModificationException.class,false,1){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
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
        void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
            for(int i=0;i < listSize;++i){
                listMonitor.add(i);
            }
        }
    },
    ModCollectionThrowAIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,false,0){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModCollection);
                    throw new ArrayIndexOutOfBoundsException();
                }
            };
        }
    },
    ModCollectionThrowIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,false,0){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModCollection);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
    },
    ModParentThrowAIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,false,2){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModParent);
                    throw new ArrayIndexOutOfBoundsException();
                }
            };
        }
    },
    ModParentThrowIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,false,2){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModParent);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
    },
    ModRootThrowAIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,false,1){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
            return new MonitoredComparator(){
                @Override
                protected void throwingCall(){
                    listMonitor.illegalMod(IllegalModification.ModRoot);
                    throw new ArrayIndexOutOfBoundsException();
                }
            };
        }
    },
    ModRootThrowIOB(true,ComparatorType.ImmediatelyThrows,ConcurrentModificationException.class,false,1){
        @Override
        public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
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
    public final boolean validWithNoComparator;
    public final int minDepth;
    MonitoredComparatorGen(boolean validWithPrimitive,ComparatorType comparatorType,
            Class<? extends Throwable> expectedException,boolean validWithNoComparator,int minDepth){
        this.validWithPrimitive=validWithPrimitive;
        this.comparatorType=comparatorType;
        this.expectedException=expectedException;
        this.validWithNoComparator=validWithNoComparator;
        this.minDepth=minDepth;
    }
    public enum ComparatorType{
        Ascending,Descending,NaturalOrder,ImmediatelyThrows;
    }
    public MonitoredComparator getMonitoredComparator(MonitoredList<?> listMonitor){
        return null;
    }
    @SuppressWarnings({"unchecked","rawtypes"})
    public void assertReverseSorted(MonitoredList<?> listMonitor){
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
            break;
        }
        case BYTE:{
            var itr=(OmniIterator.OfByte)uncastItr;
            for(byte next,prev=itr.nextByte();itr.hasNext();prev=next){
                Assertions.assertTrue(Byte.compare(prev,next=itr.nextByte()) >= 0);
            }
            break;
        }
        case CHAR:{
            var itr=(OmniIterator.OfChar)uncastItr;
            for(char next,prev=itr.nextChar();itr.hasNext();prev=next){
                Assertions.assertTrue(Character.compare(prev,next=itr.nextChar()) >= 0);
            }
            break;
        }
        case DOUBLE:{
            var itr=(OmniIterator.OfDouble)uncastItr;
            for(double next,prev=itr.nextDouble();itr.hasNext();prev=next){
                Assertions.assertTrue(Double.compare(prev,next=itr.nextDouble()) >= 0);
            }
            break;
        }
        case FLOAT:{
            var itr=(OmniIterator.OfFloat)uncastItr;
            for(float next,prev=itr.nextFloat();itr.hasNext();prev=next){
                Assertions.assertTrue(Float.compare(prev,next=itr.nextFloat()) >= 0);
            }
            break;
        }
        case INT:{
            var itr=(OmniIterator.OfInt)uncastItr;
            for(int next,prev=itr.nextInt();itr.hasNext();prev=next){
                Assertions.assertTrue(Integer.compare(prev,next=itr.nextInt()) >= 0);
            }
            break;
        }
        case LONG:{
            var itr=(OmniIterator.OfLong)uncastItr;
            for(long next,prev=itr.nextLong();itr.hasNext();prev=next){
                Assertions.assertTrue(Long.compare(prev,next=itr.nextLong()) >= 0);
            }
            break;
        }
        case REF:{
            var itr=(OmniIterator.OfRef)uncastItr;
            for(
            Comparable next,prev=(Comparable)itr.next();itr.hasNext();prev=next){
                Assertions.assertTrue(prev.compareTo(next=(Comparable)itr.next()) >= 0);
            }
            break;
        }
        case SHORT:{
            var itr=(OmniIterator.OfShort)uncastItr;
            for(short next,prev=itr.nextShort();itr.hasNext();prev=next){
                Assertions.assertTrue(Short.compare(prev,next=itr.nextShort()) >= 0);
            }
            break;
        }
        default:
            throw dataType.invalid();
        }
        if(listMonitor.size() > 1){
            listMonitor.incrementModCount();
            listMonitor.copyListContents();
        }
    }
    @SuppressWarnings({"unchecked","rawtypes"})
    public void assertSorted(MonitoredList<?> listMonitor){
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
            break;
        }
        case BYTE:{
            var itr=(OmniIterator.OfByte)uncastItr;
            for(byte next,prev=itr.nextByte();itr.hasNext();prev=next){
                Assertions.assertTrue(Byte.compare(prev,next=itr.nextByte()) <= 0);
            }
            break;
        }
        case CHAR:{
            var itr=(OmniIterator.OfChar)uncastItr;
            for(char next,prev=itr.nextChar();itr.hasNext();prev=next){
                Assertions.assertTrue(Character.compare(prev,next=itr.nextChar()) <= 0);
            }
            break;
        }
        case DOUBLE:{
            var itr=(OmniIterator.OfDouble)uncastItr;
            for(double next,prev=itr.nextDouble();itr.hasNext();prev=next){
                Assertions.assertTrue(Double.compare(prev,next=itr.nextDouble()) <= 0);
            }
            break;
        }
        case FLOAT:{
            var itr=(OmniIterator.OfFloat)uncastItr;
            for(float next,prev=itr.nextFloat();itr.hasNext();prev=next){
                Assertions.assertTrue(Float.compare(prev,next=itr.nextFloat()) <= 0);
            }
            break;
        }
        case INT:{
            var itr=(OmniIterator.OfInt)uncastItr;
            for(int next,prev=itr.nextInt();itr.hasNext();prev=next){
                Assertions.assertTrue(Integer.compare(prev,next=itr.nextInt()) <= 0);
            }
            break;
        }
        case LONG:{
            var itr=(OmniIterator.OfLong)uncastItr;
            for(long next,prev=itr.nextLong();itr.hasNext();prev=next){
                Assertions.assertTrue(Long.compare(prev,next=itr.nextLong()) <= 0);
            }
            break;
        }
        case REF:{
            var itr=(OmniIterator.OfRef)uncastItr;
            for(Comparable next,prev=(Comparable)itr.next();itr.hasNext();prev=next){
                Assertions.assertTrue(prev.compareTo(next=(Comparable)itr.next()) <= 0);
            }
            break;
        }
        case SHORT:{
            var itr=(OmniIterator.OfShort)uncastItr;
            for(short next,prev=itr.nextShort();itr.hasNext();prev=next){
                Assertions.assertTrue(Short.compare(prev,next=itr.nextShort()) <= 0);
            }
            break;
        }
        default:
            throw dataType.invalid();
        }
        if(listMonitor.size() > 1){
            listMonitor.incrementModCount();
            listMonitor.copyListContents();
        }

    }
    void initUnsortedHelper(MonitoredList<?> listMonitor,int listSize){
        for(int i=listSize;--i >= 0;){
            listMonitor.add(i);
        }
    }

    public void initUnsorted(MonitoredList<?> listMonitor,int listSize){
        if(listSize != 0){
            if(listSize > 1){
                initUnsortedHelper(listMonitor,listSize);
            }else{
                listMonitor.add(0);
            }
        }

    }

}
