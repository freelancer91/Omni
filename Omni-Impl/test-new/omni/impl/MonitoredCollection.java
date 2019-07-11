package omni.impl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import omni.impl.QueryVal.QueryValModification;
public interface MonitoredCollection<COL extends OmniCollection<?>>{
    MonitoredIterator<? extends OmniIterator<?>,COL> getMonitoredIterator(IteratorType itrType);
    CheckedType getCheckedType();
    COL getCollection();
    DataType getDataType();
    MonitoredIterator<? extends OmniIterator<?>,COL> getMonitoredIterator();
    StructType getStructType();
    default boolean isEmpty() {
        return size()==0;
    }
    void modCollection();
    void modParent();
    void modRoot();
    int size();
    void updateClearState();
    default void verifyClear(){
        try{
            int size=size();
            getCollection().clear();
            if(size != 0){
                updateClearState();
            }
        }finally{
            verifyCollectionState();
        }

    }
    void updateCollectionState();
    void verifyCollectionState();
    void verifyClone(Object clone);

    boolean verifyAdd(Object inputVal,DataType inputType,FunctionCallType functionCallType);
    boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryVal.QueryValModification modification);
    default void verifyToString(String string){
        getDataType().verifyToString(string,getCollection());
    }
    void verifyMASSIVEToString(String string,String testName);
    void verifyHashCode(int hashCode);
    void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter);
    default void verifyArrayIsCopy(Object arr){
        verifyArrayIsCopy(arr,true);
    }
    void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame);

    default boolean verifyThrowingContains(MonitoredObjectGen monitoredObjectGen){
        try{
            return QueryCastType.ToObject.callcontains(getCollection(),monitoredObjectGen.getMonitoredObject(this),
                    DataType.REF);
        }finally{
            verifyCollectionState();
        }
    }
    default boolean verifyThrowingRemoveVal(MonitoredObjectGen monitoredObjectGen){
        try{
            return QueryCastType.ToObject.callremoveVal(getCollection(),monitoredObjectGen.getMonitoredObject(this),
                    DataType.REF);
        }finally{
            verifyCollectionState();
        }
    }
    default boolean verifyContains(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryValModification modification){
        try{
            return queryCastType.callcontains(getCollection(),queryVal.getInputVal(inputType,modification),inputType);
        }finally{
            verifyCollectionState();
        }
    }


    @SuppressWarnings("unchecked") default boolean add(int val) {
        COL collection=getCollection();
        DataType dataType=getDataType();
        boolean result;
        switch(dataType) {
        case BOOLEAN:
            result=((OmniCollection.BooleanInput<?>)collection).add((val&1)!=0);
            break;
        case BYTE:
            result=((OmniCollection.ByteInput<?>)collection).add((byte)val);
            break;
        case CHAR:
            result=((OmniCollection.CharInput<?>)collection).add((char)val);
            break;
        case SHORT:
            result=((OmniCollection.ShortInput<?>)collection).add((short)val);
            break;
        case INT:
        case LONG:
        case FLOAT:
        case DOUBLE:
            result=((OmniCollection.IntInput<?>)collection).add(val);
            break;
        case REF:
            result=((OmniCollection.OfRef<Object>)collection).add(val);
            break;
        default:
            throw dataType.invalid();
        }
        updateCollectionState();
        return result;
    }
    default boolean add(MonitoredObject monitoredObject) {
        @SuppressWarnings("unchecked") boolean result=((OmniCollection.OfRef<Object> )getCollection()).add(monitoredObject);
        updateCollectionState();
        return result;
    }
    default void illegalMod(IllegalModification modType){
        switch(modType){
        case ModCollection:
            modCollection();
            return;
        case ModParent:
            modParent();
            return;
        case ModRoot:
            modRoot();
            return;
        case NoMod:
            return;
        case ModItr:
        }
        throw modType.invalid();
    }

    default Object verifyClone(){
        final Object clone;
        try{
            clone=getCollection().clone();
        }finally{
            verifyCollectionState();
        }
        verifyClone(clone);
        return clone;
    }
    @SuppressWarnings("unchecked")
    default void verifyForEach(MonitoredFunctionGen functionGen,
            FunctionCallType functionCallType,long randSeed){
        final COL collection=getCollection();
        final MonitoredFunction monitoredFunction=functionGen.getMonitoredFunction(this,randSeed);
        final DataType dataType=getDataType();
        try{
            switch(dataType){
            case BOOLEAN:{
                final var cast=(OmniCollection.OfBoolean)collection;
                if(functionCallType == FunctionCallType.Boxed){
                    cast.forEach((Consumer<? super Boolean>)monitoredFunction);
                }else{
                    cast.forEach((BooleanConsumer)monitoredFunction);
                }
                break;
            }
            case BYTE:{
                final var cast=(OmniCollection.OfByte)collection;
                if(functionCallType == FunctionCallType.Boxed){
                    cast.forEach((Consumer<? super Byte>)monitoredFunction);
                }else{
                    cast.forEach((ByteConsumer)monitoredFunction);
                }
                break;
            }
            case CHAR:{
                final var cast=(OmniCollection.OfChar)collection;
                if(functionCallType == FunctionCallType.Boxed){
                    cast.forEach((Consumer<? super Character>)monitoredFunction);
                }else{
                    cast.forEach((CharConsumer)monitoredFunction);
                }
                break;
            }
            case DOUBLE:{
                final var cast=(OmniCollection.OfDouble)collection;
                if(functionCallType == FunctionCallType.Boxed){
                    cast.forEach((Consumer<? super Double>)monitoredFunction);
                }else{
                    cast.forEach((DoubleConsumer)monitoredFunction);
                }
                break;
            }
            case FLOAT:{
                final var cast=(OmniCollection.OfFloat)collection;
                if(functionCallType == FunctionCallType.Boxed){
                    cast.forEach((Consumer<? super Float>)monitoredFunction);
                }else{
                    cast.forEach((FloatConsumer)monitoredFunction);
                }
                break;
            }
            case INT:{
                final var cast=(OmniCollection.OfInt)collection;
                if(functionCallType == FunctionCallType.Boxed){
                    cast.forEach((Consumer<? super Integer>)monitoredFunction);
                }else{
                    cast.forEach((IntConsumer)monitoredFunction);
                }
                break;
            }
            case LONG:{
                final var cast=(OmniCollection.OfLong)collection;
                if(functionCallType == FunctionCallType.Boxed){
                    cast.forEach((Consumer<? super Long>)monitoredFunction);
                }else{
                    cast.forEach((LongConsumer)monitoredFunction);
                }
                break;
            }
            case REF:{
                final var cast=(OmniCollection.OfRef<?>)collection;
                if(functionCallType == FunctionCallType.Boxed){
                    throw DataType.cannotBeBoxed();
                }else{
                    cast.forEach(monitoredFunction);
                }
                break;
            }
            case SHORT:{
                final var cast=(OmniCollection.OfShort)collection;
                if(functionCallType == FunctionCallType.Boxed){
                    cast.forEach((Consumer<? super Short>)monitoredFunction);
                }else{
                    cast.forEach((ShortConsumer)monitoredFunction);
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
        }finally{
            verifyCollectionState();
        }
        Assertions.assertEquals(monitoredFunction.size(),size());
        var itr=collection.iterator();
        var funcItr=monitoredFunction.iterator();
        if(dataType == DataType.REF){
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertSame(funcItr.next(),itr.next());
            }
        }else{
            while(itr.hasNext()){
                Assertions.assertTrue(funcItr.hasNext());
                Assertions.assertEquals(funcItr.next(),itr.next());
            }
        }
    }
    default boolean verifyIsEmpty(){
        final boolean expectedResult=isEmpty();
        final boolean actualResult;
        try{
            actualResult=getCollection().isEmpty();
        }finally{
            verifyCollectionState();
        }
        Assertions.assertEquals(expectedResult,actualResult);
        return actualResult;
    }
    default int verifySize(){
        final int expectedSize=size();
        final int actualSize;
        try{
            actualSize=getCollection().size();
        }finally{
            verifyCollectionState();
        }
        Assertions.assertEquals(expectedSize,actualSize);
        return actualSize;
    }
    default String verifyMASSIVEToString(String testName){
        final String result;
        try{
            result=getCollection().toString();
        }finally{
            verifyCollectionState();
        }
        verifyMASSIVEToString(result,testName);
        return result;
    }
    default String verifyToString() {
        final String result;
        try{
            result=getCollection().toString();
        }finally{
            verifyCollectionState();
        }
        verifyToString(result);
        return result;
    }
    default int verifyHashCode() {
        COL collection=getCollection();
        final int result;
        try{
            result=collection.hashCode();
        }finally{
            verifyCollectionState();
        }
        verifyHashCode(result);
        return result;
    }
    void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException;
    default void verifyReadAndWriteClone(COL readCol){
        verifyClone(readCol);
    }
    @SuppressWarnings("unchecked") default void verifyReadAndWrite(MonitoredFunctionGen monitoredFunctionGen) throws ClassNotFoundException, IOException { 
        COL readCol=null;
        try{
            
            if(monitoredFunctionGen.expectedException==null) {
                var baos=new ByteArrayOutputStream();
                try(var oos=new ObjectOutputStream(baos);){
                    oos.writeObject(getCollection());
                }
                try(var ois=monitoredFunctionGen.getMonitoredObjectInputStream(this,new ByteArrayInputStream(baos.toByteArray()));){
                    readCol=(COL)ois.readObject();
                }
            }else {
                try(var oos=monitoredFunctionGen.getMonitoredObjectOutputStream(this,OutputStream.nullOutputStream())){
                    writeObjectImpl(oos);
                }
            }
            
        }finally{
            verifyCollectionState();
        }
        if(getDataType() == DataType.REF){
            verifyReadAndWriteClone(readCol);
        }else{
            verifyClone(readCol);
        }
        
    }
    @SuppressWarnings("unchecked")
    default boolean verifyRemoveIf(MonitoredRemoveIfPredicate filter,FunctionCallType functionCallType){
        COL collection=getCollection();
        DataType dataType=getDataType();
        boolean result;
        try{
            switch(dataType) {
            case BOOLEAN:
            {
                var cast=(OmniCollection.OfBoolean)collection;
                if(functionCallType==FunctionCallType.Boxed) {
                    result=cast.removeIf((Predicate<? super Boolean>)filter);
                }else {
                    result=cast.removeIf((BooleanPredicate)filter);
                }
                break;
            }
            case BYTE:
            {
                var cast=(OmniCollection.OfByte)collection;
                if(functionCallType==FunctionCallType.Boxed) {
                    result=cast.removeIf((Predicate<? super Byte>)filter);
                }else {
                    result=cast.removeIf((BytePredicate)filter);
                }
                break;
            }
            case CHAR:
            {
                var cast=(OmniCollection.OfChar)collection;
                if(functionCallType==FunctionCallType.Boxed) {
                    result=cast.removeIf((Predicate<? super Character>)filter);
                }else {
                    result=cast.removeIf((CharPredicate)filter);
                }
                break;
            }
            case DOUBLE:
            {
                var cast=(OmniCollection.OfDouble)collection;
                if(functionCallType==FunctionCallType.Boxed) {
                    result=cast.removeIf((Predicate<? super Double>)filter);
                }else {
                    result=cast.removeIf((DoublePredicate)filter);
                }
                break;
            }
            case FLOAT:
            {
                var cast=(OmniCollection.OfFloat)collection;
                if(functionCallType==FunctionCallType.Boxed) {
                    result=cast.removeIf((Predicate<? super Float>)filter);
                }else {
                    result=cast.removeIf((FloatPredicate)filter);
                }
                break;
            }
            case INT:
            {
                var cast=(OmniCollection.OfInt)collection;
                if(functionCallType==FunctionCallType.Boxed) {
                    result=cast.removeIf((Predicate<? super Integer>)filter);
                }else {
                    result=cast.removeIf((IntPredicate)filter);
                }
                break;
            }
            case LONG:
            {
                var cast=(OmniCollection.OfLong)collection;
                if(functionCallType==FunctionCallType.Boxed) {
                    result=cast.removeIf((Predicate<? super Long>)filter);
                }else {
                    result=cast.removeIf((LongPredicate)filter);
                }
                break;
            }
            case REF:
            {
                var cast=(OmniCollection.OfRef<?>)collection;
                if(functionCallType==FunctionCallType.Boxed) {
                    throw DataType.cannotBeBoxed();
                }else {
                    result=cast.removeIf(filter);
                }
                break;
            }
            case SHORT:
            {
                var cast=(OmniCollection.OfShort)collection;
                if(functionCallType==FunctionCallType.Boxed) {
                    result=cast.removeIf((Predicate<? super Short>)filter);
                }else {
                    result=cast.removeIf((ShortPredicate)filter);
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            verifyRemoveIf(result,filter);
        }finally{
            verifyCollectionState();
        }
        return result;
    }
    default Object verifyToArray(DataType outputType) {
        return outputType.verifyToArray(this);
    }
    @SuppressWarnings("unchecked") default <T> T[] verifyToArray(T[] arr) {
        COL collection=getCollection();
        int size=collection.size();
        int arrLength=arr.length;
        for(int i=0;i<arrLength;++i) {
            arr[i]=(T)Integer.valueOf(i);
        }
        T[] result;
        try{
            result=collection.toArray(arr);
        }finally{
            verifyCollectionState();
        }
        verifyArrayIsCopy(result,false);
        switch(Integer.signum(size-arrLength)) {
        case -1:
            Assertions.assertNull(result[size]);
            for(int i=size+1;i<arrLength;++i) {
                Assertions.assertEquals(Integer.valueOf(i),result[i]);
            }
        case 0:
            Assertions.assertSame(arr,result);
            break;
        default:
            Assertions.assertNotSame(arr,result);
            Assertions.assertEquals(size,result.length);
            for(int i=0;i<arrLength;++i) {
                Assertions.assertEquals(Integer.valueOf(i),arr[i]);
            }
        }
        var itr=collection.iterator();
        DataType dataType=getDataType();
        if(dataType==DataType.REF) {
            for(int i=0;i<size;++i) {
                Assertions.assertSame(itr.next(),result[i]);
            }
        }else {
            for(int i=0;i<size;++i) {
                Assertions.assertEquals(itr.next(),result[i]);
            }
        }
        Assertions.assertFalse(itr.hasNext());
        return result;
    }
    default Object[] verifyToArray(MonitoredFunctionGen monitoredFunctionGen) {
        COL collection=getCollection();
        int size=collection.size();
        MonitoredArrayConstructor<?> monitoredArrayConstructor=monitoredFunctionGen.getMonitoredArrayConstructor(this);
        Object[] result;
        try {
            result=collection.toArray(monitoredArrayConstructor);
        }finally {
            Assertions.assertEquals(1,monitoredArrayConstructor.numCalls);
            verifyCollectionState();
        }
        verifyArrayIsCopy(result,false);
        Assertions.assertEquals(size,result.length);
        var iterator=collection.iterator();
        if(getDataType()==DataType.REF) {
            for(int i=0;i<size;++i) {
                Assertions.assertSame(iterator.next(),result[i]);
            }
        }else {
            for(int i=0;i<size;++i) {
                Assertions.assertEquals(iterator.next(),result[i]);
            }
        }
        Assertions.assertFalse(iterator.hasNext());
        return result;
    }
    interface MonitoredIterator<ITR extends OmniIterator<?>,COL extends OmniCollection<?>>{
        boolean nextWasJustCalled();
        default void modItr(){
            if(!hasNext()){
                throw new UnsupportedOperationException("Cannot modify an iterator in a depleted state");
            }
            iterateForward();
        }
        void updateItrNextState();
        void verifyNextResult(DataType outputType,Object result);
        default Object verifyNext(DataType outputType){
            final Object result;
            try{
                result=outputType.callIteratorNext(getIterator());
                updateItrNextState();
                verifyNextResult(outputType,result);
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }

            return result;
        }
        void updateItrRemoveState();
        default void verifyRemove(){
            try{
                getIterator().remove();
                updateItrRemoveState();
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }
        }
        ITR getIterator();
        IteratorType getIteratorType();
        MonitoredCollection<COL> getMonitoredCollection();
        boolean hasNext();
        int getNumLeft();
        void verifyForEachRemaining(MonitoredFunction function);
        // void updateIteratorState();
        abstract void verifyCloneHelper(Object clone);
        default void verifyClone(Object clone){
            Assertions.assertNotSame(getIterator(),clone);
            verifyCloneHelper(clone);
        }
        default void verifyIteratorState(){
            verifyCloneHelper(getIterator());
        }
        default boolean verifyHasNext() {
            ITR iterator=getIterator();
            boolean expectedResult=hasNext();
            boolean actualResult;
            try{
                actualResult=iterator.hasNext();
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }
            Assertions.assertEquals(expectedResult,actualResult);
            return actualResult;
        }
        default void illegalMod(IllegalModification modType){
            switch(modType){
            case ModItr:
                modItr();
                return;
            case ModCollection:
                getMonitoredCollection().modCollection();
                return;
            case ModParent:
                getMonitoredCollection().modParent();
                return;
            case ModRoot:
                getMonitoredCollection().modRoot();
            case NoMod:
                return;
            }
            throw modType.invalid();
        }
        default void iterateForward() {
            ITR iterator=getIterator();
            iterator.next();
            updateItrNextState();
        }
        default void remove() {
            ITR iterator=getIterator();
            iterator.remove();
            updateItrRemoveState();
        }
        default Object verifyClone(){
            final ITR itr=getIterator();
            final Object clone;
            try{
                clone=itr.clone();
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }
            Assertions.assertNotSame(itr,clone);
            verifyClone(clone);
            return clone;
        }
        @SuppressWarnings({"rawtypes","unchecked"}) default void verifyForEachRemaining(MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed) {
            ITR iterator=getIterator();
            int numLeft=getNumLeft();
            MonitoredFunction function=functionGen.getMonitoredFunction(this,randSeed);
            MonitoredCollection<?> monitoredCollection=getMonitoredCollection();
            DataType dataType=monitoredCollection.getDataType();
            try{
                if(functionCallType==FunctionCallType.Boxed) {
                    if(dataType==DataType.REF) {
                        throw DataType.cannotBeBoxed();
                    }
                    iterator.forEachRemaining((Consumer)function);
                }else {
                    switch(dataType) {
                    case BOOLEAN:
                        ((OmniIterator.OfBoolean)iterator).forEachRemaining((BooleanConsumer)function);
                        break;
                    case BYTE:
                        ((OmniIterator.OfByte)iterator).forEachRemaining((ByteConsumer)function);
                        break;
                    case CHAR:
                        ((OmniIterator.OfChar)iterator).forEachRemaining((CharConsumer)function);
                        break;
                    case DOUBLE:
                        ((OmniIterator.OfDouble)iterator).forEachRemaining((DoubleConsumer)function);
                        break;
                    case FLOAT:
                        ((OmniIterator.OfFloat)iterator).forEachRemaining((FloatConsumer)function);
                        break;
                    case INT:
                        ((OmniIterator.OfInt)iterator).forEachRemaining((IntConsumer)function);
                        break;
                    case LONG:
                        ((OmniIterator.OfLong)iterator).forEachRemaining((LongConsumer)function);
                        break;
                    case REF:
                        ((OmniIterator.OfRef<?>)iterator).forEachRemaining(function);
                        break;
                    case SHORT:
                        ((OmniIterator.OfShort)iterator).forEachRemaining((ShortConsumer)function);
                        break;
                    default:
                        throw dataType.invalid();
                    }
                }
                Assertions.assertEquals(numLeft,function.size());
                verifyForEachRemaining(function);
            }finally{
                monitoredCollection.verifyCollectionState();
                verifyIteratorState();
            }


        }
    }
}
