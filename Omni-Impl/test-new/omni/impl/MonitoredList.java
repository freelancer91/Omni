package omni.impl;

import java.util.function.DoubleUnaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.function.BooleanPredicate;
import omni.function.ByteUnaryOperator;
import omni.function.CharUnaryOperator;
import omni.function.FloatUnaryOperator;
import omni.function.ShortUnaryOperator;
import omni.impl.QueryVal.QueryValModification;
public interface MonitoredList<LST extends OmniList<?>>
extends
MonitoredSequence<LST>{
    default Object verifyRemoveAt(int index,DataType outputType){
        Object result;
        Object expected=null;
        var collection=getCollection();
        if(index >= 0 || index < size()){
            expected=outputType.callGet(index,collection);
        }
        try{
            result=outputType.callRemoveAt(index,collection);
            updateRemoveIndexState(index);
        }finally{
            verifyCollectionState();
        }
        if(outputType == DataType.REF && getDataType() == DataType.REF){
            Assertions.assertSame(expected,result);
        }else{
            Assertions.assertEquals(expected,result);
        }
        return result;
    }
    default void verifyAdd(int index,Object inputVal,DataType inputType,FunctionCallType functionCallType){
        var collection=getCollection();
        try{
            inputType.callAdd(index,inputVal,collection,functionCallType);
            updateAddState(index,inputVal,inputType);
        }finally{
            verifyCollectionState();
        }
    }
    @Override
    default void updateAddState(Object inputVal,DataType inputType){
        updateAddState(size(),inputVal,inputType);
    }
    void updateAddState(int index,Object inputVal,DataType inputType);
    default int verifyIndexOf(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryValModification modification){
        try{
            return queryCastType.callindexOf(getCollection(),queryVal.getInputVal(inputType,modification),inputType);
        }finally{
            verifyCollectionState();
        }
    }
    default int verifyLastIndexOf(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryValModification modification){
        try{
            return queryCastType.calllastIndexOf(getCollection(),queryVal.getInputVal(inputType,modification),
                    inputType);
        }finally{
            verifyCollectionState();
        }
    }
    default int verifyThrowingIndexOf(MonitoredObjectGen monitoredObjectGen){
        Object inputVal=monitoredObjectGen.getMonitoredObject(this);
        try{
            return QueryCastType.ToObject.callindexOf(getCollection(),inputVal,DataType.REF);
        }finally{
            verifyCollectionState();
        }
    }
    default int verifyThrowingLastIndexOf(MonitoredObjectGen monitoredObjectGen){
        Object inputVal=monitoredObjectGen.getMonitoredObject(this);
        try{
            return QueryCastType.ToObject.calllastIndexOf(getCollection(),inputVal,DataType.REF);
        }finally{
            verifyCollectionState();
        }
    }
    void verifyPutResult(int index,Object input,DataType inputType);
    
    default Object verifySet(int index,Object inputVal,FunctionCallType functionCallType){
        Object result;
        Object expected=null;
        var dataType=getDataType();
        if(index >= 0 || index < size()){
            expected=dataType.callGet(index,getCollection());
        }
        try{
            result=dataType.callSet(index,inputVal,getCollection(),functionCallType);
            verifyPutResult(index,inputVal,dataType);
        }finally{
            verifyCollectionState();
        }
        if(dataType == DataType.REF){
            Assertions.assertSame(expected,result);
        }else{
            Assertions.assertEquals(expected,result);
        }
        return result;
    }
    @SuppressWarnings("unchecked")
    default boolean add(MonitoredObject monitoredObject) {
        ((OmniCollection.OfRef<Object> )getCollection()).add(monitoredObject);
        updateAddState(monitoredObject,DataType.REF);
        return true;
    }
    default Object verifyGet(int index,DataType outputType) {
        Object result;
        try {
            result=outputType.callGet(index,getCollection());
        }finally{
            verifyCollectionState();
        }
        verifyGetResult(index,result,outputType);
        return result;
    }
    default void verifyPut(int index,Object inputVal,DataType inputType,FunctionCallType functionCallType){
        try{
            inputType.callPut(index,inputVal,getCollection(),functionCallType);
            verifyPutResult(index,inputVal,inputType);
        }finally{
            verifyCollectionState();
        }
    }
    interface MonitoredListIterator<LSTITR extends OmniListIterator<?>,LST extends OmniList<?>>
    extends
    MonitoredIterator<LSTITR,LST>{
        default void add(Object input,DataType inputType){
            inputType.callAdd(input,getIterator(),FunctionCallType.Unboxed);
            updateItrAddState(input,inputType);
        }
        boolean previousWasJustCalled();
        @Override
        default void modItr(){
            if(hasNext()){
                iterateForward();
            }else if(hasPrevious()){
                iterateReverse();
            }else{
                throw new UnsupportedOperationException("Cannot modify an iterator in a depleted state");
            }
        }
        default Object verifyPrevious(DataType outputType){
            final Object result;
            try{
                result=outputType.callPrevious(getIterator());
                updateItrPreviousState();
                verifyPreviousResult(outputType,result);
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }
            return result;
        }
        void updateItrPreviousState();
        void verifyPreviousResult(DataType outputType,Object result);
        boolean hasPrevious();
        default void iterateReverse(){
            LSTITR iterator=getIterator();
            iterator.previous();
            updateItrPreviousState();
        }
        default boolean verifyHasPrevious(){
            LSTITR iterator=getIterator();
            boolean expectedResult=hasPrevious();
            boolean actualResult;
            try{
                actualResult=iterator.hasPrevious();
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }
            Assertions.assertEquals(expectedResult,actualResult);
            return actualResult;
        }
        int nextIndex();
        int previousIndex();
        void updateItrSetState(Object input,DataType inputType);
        void updateItrAddState(Object input,DataType inputType);
        default void verifySet(Object input,DataType inputType,FunctionCallType functionCallType) {
            var itr=getIterator();
            try{
                inputType.callSet(input,itr,functionCallType);
                updateItrSetState(input,inputType);
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }
        }
        default void verifyAdd(Object input,DataType inputType,FunctionCallType functionCallType){
            var itr=getIterator();
            try{
                inputType.callAdd(input,itr,functionCallType);
                updateItrAddState(input,inputType);
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }
        }
        default int verifyNextIndex(){
            int expected=nextIndex();
            int actual;
            try{
                actual=getIterator().nextIndex();
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }
            Assertions.assertEquals(expected,actual);
            return actual;
        }
        default int verifyPreviousIndex(){
            int expected=previousIndex();
            int actual;
            try{
                actual=getIterator().previousIndex();
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }
            Assertions.assertEquals(expected,actual);
            return actual;
        }
    }
    
    MonitoredListIterator<? extends OmniListIterator<?>,LST> getMonitoredListIterator();
    MonitoredListIterator<? extends OmniListIterator<?>,LST> getMonitoredListIterator(int index);
    void updateReplaceAllState(MonitoredFunction function);
    void verifyCollectionState(boolean refIsSame);
    @Override
    default void verifyCollectionState(){
        verifyCollectionState(true);
    }
    @SuppressWarnings("unchecked")
    default void verifyReplaceAll(MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed){
        final var collection=getCollection();
        final MonitoredFunction monitoredFunction=functionGen.getMonitoredFunction(this,randSeed);
        final DataType dataType=getDataType();

        switch(dataType){
        case BOOLEAN:{
            var cast=(OmniList.OfBoolean)collection;
            if(functionCallType.boxed){
                cast.replaceAll((UnaryOperator<Boolean>)monitoredFunction);
            }else{
                cast.replaceAll((BooleanPredicate)monitoredFunction);
            }
            Assertions.assertEquals(monitoredFunction.size(),size());
            var itr=cast.iterator();
            var funcItr=monitoredFunction.iterator();
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertEquals((boolean)funcItr.next(),!itr.nextBoolean());
            }
            break;
        }
        case BYTE:{
            var cast=(OmniList.OfByte)collection;
            if(functionCallType.boxed){
                cast.replaceAll((UnaryOperator<Byte>)monitoredFunction);
            }else{
                cast.replaceAll((ByteUnaryOperator)monitoredFunction);
            }
            Assertions.assertEquals(monitoredFunction.size(),size());
            var itr=cast.iterator();
            var funcItr=monitoredFunction.iterator();
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertEquals((byte)funcItr.next(),(byte)(itr.nextByte() - Byte.MAX_VALUE));
            }
            break;
        }
        case CHAR:{
            var cast=(OmniList.OfChar)collection;
            if(functionCallType.boxed){
                cast.replaceAll((UnaryOperator<Character>)monitoredFunction);
            }else{
                cast.replaceAll((CharUnaryOperator)monitoredFunction);
            }
            Assertions.assertEquals(monitoredFunction.size(),size());
            var itr=cast.iterator();
            var funcItr=monitoredFunction.iterator();
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertEquals((char)funcItr.next(),(char)(itr.nextChar() - Character.MAX_VALUE));
            }
            break;
        }
        case DOUBLE:{
            var cast=(OmniList.OfDouble)collection;
            if(functionCallType.boxed){
                cast.replaceAll((UnaryOperator<Double>)monitoredFunction);
            }else{
                cast.replaceAll((DoubleUnaryOperator)monitoredFunction);
            }
            Assertions.assertEquals(monitoredFunction.size(),size());
            var itr=cast.iterator();
            var funcItr=monitoredFunction.iterator();
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertEquals((double)funcItr.next(),-itr.nextDouble());
            }
            break;
        }
        case FLOAT:{
            var cast=(OmniList.OfFloat)collection;
            if(functionCallType.boxed){
                cast.replaceAll((UnaryOperator<Float>)monitoredFunction);
            }else{
                cast.replaceAll((FloatUnaryOperator)monitoredFunction);
            }
            Assertions.assertEquals(monitoredFunction.size(),size());
            var itr=cast.iterator();
            var funcItr=monitoredFunction.iterator();
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertEquals((float)funcItr.next(),-itr.nextFloat());
            }
            break;
        }
        case INT:{
            var cast=(OmniList.OfInt)collection;
            if(functionCallType.boxed){
                cast.replaceAll((UnaryOperator<Integer>)monitoredFunction);
            }else{
                cast.replaceAll((IntUnaryOperator)monitoredFunction);
            }
            Assertions.assertEquals(monitoredFunction.size(),size());
            var itr=cast.iterator();
            var funcItr=monitoredFunction.iterator();
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertEquals((int)funcItr.next(),itr.nextInt() - Integer.MAX_VALUE);
            }
            break;
        }
        case LONG:{
            var cast=(OmniList.OfLong)collection;
            if(functionCallType.boxed){
                cast.replaceAll((UnaryOperator<Long>)monitoredFunction);
            }else{
                cast.replaceAll((LongUnaryOperator)monitoredFunction);
            }
            Assertions.assertEquals(monitoredFunction.size(),size());
            var itr=cast.iterator();
            var funcItr=monitoredFunction.iterator();
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertEquals((long)funcItr.next(),itr.nextLong() - Long.MAX_VALUE);
            }
            break;
        }
        case REF:{
            var cast=(OmniList.OfRef<Object>)collection;
            if(functionCallType.boxed){
                throw DataType.cannotBeBoxed();
            }else{
                cast.replaceAll(monitoredFunction);
            }
            Assertions.assertEquals(monitoredFunction.size(),size());
            var itr=cast.iterator();
            var funcItr=monitoredFunction.iterator();
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertEquals((int)funcItr.next(),(int)itr.next() - Integer.MAX_VALUE);
            }
            break;
        }
        case SHORT:{
            var cast=(OmniList.OfShort)collection;
            if(functionCallType.boxed){
                cast.replaceAll((UnaryOperator<Short>)monitoredFunction);
            }else{
                cast.replaceAll((ShortUnaryOperator)monitoredFunction);
            }
            Assertions.assertEquals(monitoredFunction.size(),size());
            var itr=cast.iterator();
            var funcItr=monitoredFunction.iterator();
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertEquals((short)funcItr.next(),(short)(itr.nextShort() - Short.MAX_VALUE));
            }
            break;
        }
        default:
            throw dataType.invalid();
        }

        updateReplaceAllState(monitoredFunction);
        verifyCollectionState(false);


    }
    void copyListContents();
    void incrementModCount();
    default void verifyStableSort(int size,MonitoredComparatorGen comparatorGen,FunctionCallType functionCallType){
        comparatorGen.initUnsorted(this,size);
        getDataType().callStableSort(comparatorGen.getMonitoredComparator(this),getCollection(),functionCallType);
        comparatorGen.assertSorted(this);
        verifyCollectionState();
    }
    default void verifyUnstableSort(int size,MonitoredComparatorGen comparatorGen){
        comparatorGen.initUnsorted(this,size);
        getDataType().callUnstableSort(comparatorGen.getMonitoredComparator(this),getCollection());
        comparatorGen.assertSorted(this);
        verifyCollectionState();
    }
    default void verifyAscendingStableSort(int size,MonitoredComparatorGen comparatorGen){
        comparatorGen.initUnsorted(this,size);
        getCollection().stableAscendingSort();
        comparatorGen.assertSorted(this);
        verifyCollectionState();
    }
    default void verifyDescendingStableSort(int size,MonitoredComparatorGen comparatorGen){
        comparatorGen.initUnsorted(this,size);
        getCollection().stableDescendingSort();
        comparatorGen.assertReverseSorted(this);
        verifyCollectionState();
    }
    @SuppressWarnings("unchecked")
    default void verifyAscendingUnstableSort(int size,MonitoredComparatorGen comparatorGen){
        comparatorGen.initUnsorted(this,size);
        ((OmniList.OfRef<Object>)getCollection()).unstableAscendingSort();
        comparatorGen.assertSorted(this);
        verifyCollectionState();
    }
    @SuppressWarnings("unchecked")
    default void verifyDescendingUnstableSort(int size,MonitoredComparatorGen comparatorGen){
        comparatorGen.initUnsorted(this,size);
        ((OmniList.OfRef<Object>)getCollection()).unstableDescendingSort();
        comparatorGen.assertReverseSorted(this);
        verifyCollectionState();
    }
    MonitoredList<?> getMonitoredSubList(int fromIndex,int toIndex);
}
