package omni.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
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
    boolean isEmpty();
    void modCollection();
    void modParent();
    void modRoot();
    int size();
    void verifyClear();
    void updateCollectionState();
    void verifyCollectionState();
    void verifyClone(Object clone);
    boolean verifyAdd(Object inputVal,DataType inputType,FunctionCallType functionCallType);
    boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,QueryVal.QueryValModification modification);
    void verifyToString(String string);
    void verifyHashCode(int hashCode);
    void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter);
    void verifyArrayIsCopy(Object arr);

    default boolean verifyContains(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,QueryValModification modification){
        Object inputVal=queryVal.getInputVal(inputType,modification);
        boolean result=queryCastType.callcontains(getCollection(),inputVal,inputType);
        verifyCollectionState();
        return result;
    }
    default Object convertInitialVal(Object initialVal){
        final var dataType=this.getDataType();
        switch(dataType){
        case BOOLEAN:{
            if(initialVal instanceof Boolean){
                return (int)(((Boolean)initialVal).booleanValue()?1:0);
            }else if(initialVal instanceof Number){
                return (int)((Number)initialVal).intValue();
            }else if(initialVal instanceof Character){ return (int)((Character)initialVal).charValue(); }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        case BYTE:
        case INT:
        case SHORT:{
            if(initialVal instanceof Number){
                return (int)((Number)initialVal).intValue();
            }else if(initialVal instanceof Boolean){
                return (int)(((Boolean)initialVal).booleanValue()?1:0);
            }else if(initialVal instanceof Character){ return (int)((Character)initialVal).charValue(); }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        case CHAR:{
            if(initialVal instanceof Character){
                return (int)((Character)initialVal).charValue();
            }else if(initialVal instanceof Number){
                return (int)((Number)initialVal).intValue();
            }else if(initialVal instanceof Boolean){ return (int)(((Boolean)initialVal).booleanValue()?1:0); }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        case DOUBLE:{
            if(initialVal instanceof Number){
                return (double)((Number)initialVal).doubleValue();
            }else if(initialVal instanceof Boolean){
                return (double)(((Boolean)initialVal).booleanValue()?1d:0d);
            }else if(initialVal instanceof Character){ return (double)((Character)initialVal).charValue(); }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        case FLOAT:{
            if(initialVal instanceof Number){
                return (float)((Number)initialVal).floatValue();
            }else if(initialVal instanceof Boolean){
                return (float)(((Boolean)initialVal).booleanValue()?1f:0f);
            }else if(initialVal instanceof Character){ return (float)((Character)initialVal).charValue(); }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        case LONG:
        case REF:{
            if(initialVal instanceof Number){
                return (long)((Number)initialVal).longValue();
            }else if(initialVal instanceof Boolean){
                return (long)(((Boolean)initialVal).booleanValue()?1L:0L);
            }else if(initialVal instanceof Character){ return (long)((Character)initialVal).charValue(); }
            throw new UnsupportedOperationException("Unsupported initialVal " + initialVal);
        }
        }
        throw DataType.invalidDataType(dataType);
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
            throw DataType.invalidDataType(dataType);
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
        throw new UnsupportedOperationException("Unknown modType " + modType);
    }
    @SuppressWarnings("unchecked") default void initContains(QueryVal queryVal,int setSize,double containsPosition,
            Object initialVal){
        final COL collection=getCollection();
        final DataType dataType=getDataType();
        initialVal=convertInitialVal(initialVal);
        switch(dataType){
        case BOOLEAN:
            queryVal.initContains((OmniCollection.OfBoolean)collection,setSize,(int)initialVal,containsPosition);
            break;
        case BYTE:
            queryVal.initContains((OmniCollection.OfByte)collection,setSize,(int)initialVal,containsPosition);
            break;
        case CHAR:
            queryVal.initContains((OmniCollection.OfChar)collection,setSize,(int)initialVal,containsPosition);
            break;
        case DOUBLE:
            queryVal.initContains((OmniCollection.OfDouble)collection,setSize,(double)initialVal,containsPosition);
            break;
        case FLOAT:
            queryVal.initContains((OmniCollection.OfFloat)collection,setSize,(float)initialVal,containsPosition);
            break;
        case INT:
            queryVal.initContains((OmniCollection.OfInt)collection,setSize,(int)initialVal,containsPosition);
            break;
        case LONG:
            queryVal.initContains((OmniCollection.OfLong)collection,setSize,(long)initialVal,containsPosition);
            break;
        case REF:
            queryVal.initContains((OmniCollection.OfRef<Object>)collection,setSize,(long)initialVal,containsPosition);
            break;
        case SHORT:
            queryVal.initContains((OmniCollection.OfShort)collection,setSize,(int)initialVal,containsPosition);
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
        updateCollectionState();
    }
    @SuppressWarnings("unchecked") default void initDoesNotContain(QueryVal queryVal,int setSize,Object initialVal){
        final COL collection=getCollection();
        final DataType dataType=getDataType();
        initialVal=convertInitialVal(initialVal);
        switch(dataType){
        case BOOLEAN:
            queryVal.initDoesNotContain((OmniCollection.OfBoolean)collection,setSize,(int)initialVal);
            break;
        case BYTE:
            queryVal.initDoesNotContain((OmniCollection.OfByte)collection,setSize,(int)initialVal);
            break;
        case CHAR:
            queryVal.initDoesNotContain((OmniCollection.OfChar)collection,setSize,(int)initialVal);
            break;
        case DOUBLE:
            queryVal.initDoesNotContain((OmniCollection.OfDouble)collection,setSize,(double)initialVal);
            break;
        case FLOAT:
            queryVal.initDoesNotContain((OmniCollection.OfFloat)collection,setSize,(float)initialVal);
            break;
        case INT:
            queryVal.initDoesNotContain((OmniCollection.OfInt)collection,setSize,(int)initialVal);
            break;
        case LONG:
            queryVal.initDoesNotContain((OmniCollection.OfLong)collection,setSize,(long)initialVal);
            break;
        case REF:
            queryVal.initDoesNotContain((OmniCollection.OfRef<Object>)collection,setSize,(long)initialVal);
            break;
        case SHORT:
            queryVal.initDoesNotContain((OmniCollection.OfShort)collection,setSize,(int)initialVal);
            break;
        default:
            throw DataType.invalidDataType(dataType);
        }
        updateCollectionState();
    }
    default Object verifyClone(){
        final COL collection=getCollection();
        final Object clone=collection.clone();
        verifyCollectionState();
        Assertions.assertNotSame(collection,clone);
        verifyClone(clone);
        return clone;
    }
    @SuppressWarnings("unchecked")
    default void verifyForEach(MonitoredFunctionGen functionGen,
            FunctionCallType functionCallType,long randSeed){
        final COL collection=getCollection();
        final MonitoredFunction monitoredFunction=functionGen.getMonitoredFunction(this,randSeed);
        final DataType dataType=getDataType();
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
                throw new UnsupportedOperationException("Boxed is not suppported for Ref");
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
            throw DataType.invalidDataType(dataType);
        }
        verifyCollectionState();
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
        final COL collection=getCollection();
        final boolean expectedResult=isEmpty();
        final boolean actualResult=collection.isEmpty();
        verifyCollectionState();
        Assertions.assertEquals(expectedResult,actualResult);
        return actualResult;
    }
    default int verifySize(){
        final COL collection=getCollection();
        final int expectedSize=size();
        final int actualSize=collection.size();
        verifyCollectionState();
        Assertions.assertEquals(expectedSize,actualSize);
        return actualSize;
    }
    default String verifyToString() {
        COL collection=getCollection();
        String result=collection.toString();
        verifyCollectionState();
        verifyToString(result);
        return result;
    }
    default int verifyHashCode() {
        COL collection=getCollection();
        int result=collection.hashCode();
        verifyCollectionState();
        verifyHashCode(result);
        return result;
    }
    @SuppressWarnings("unchecked") default void verifyReadAndWrite(MonitoredFunctionGen monitoredFunctionGen) {
        COL collection=getCollection();
        final File file;
        try{
            file=Files.createTempFile(null,null).toFile();
        }catch(Exception e){
            Assertions.fail(e);
            return;
        }
        try(var oos=monitoredFunctionGen.getMonitoredObjectOutputStream(this,new FileOutputStream(file));){
            oos.writeObject(collection);
            verifyCollectionState();
        }catch(Exception e){
            Assertions.fail(e);
        }
        COL readCol=null;
        try(var ois=monitoredFunctionGen.getMonitoredObjectInputStream(this,new FileInputStream(file));){
            readCol=(COL)ois.readObject();
            verifyCollectionState();

        }catch(Exception e){
            Assertions.fail(e);
            return;
        }
        verifyClone(readCol);
    }
    @SuppressWarnings("unchecked") default boolean verifyRemoveIf(MonitoredRemoveIfPredicateGen filterGen,FunctionCallType functionCallType,double threshold,long randSeed) {
        COL collection=getCollection();
        DataType dataType=getDataType();
        var filter=filterGen.getMonitoredRemoveIfPredicate(this,threshold,randSeed);
        boolean result;
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
                throw new UnsupportedOperationException("Ref cannot be boxed");
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
            throw DataType.invalidDataType(dataType);
        }
        verifyRemoveIf(result,filter);
        verifyCollectionState();
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
        T[] result=collection.toArray(arr);
        verifyCollectionState();
        verifyArrayIsCopy(result);
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
        }
        verifyCollectionState();
        verifyArrayIsCopy(result);
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

        ITR getIterator();
        IteratorType getIteratorType();
        MonitoredCollection<COL> getMonitoredCollection();
        boolean hasNext();
        int getNumLeft();
        void verifyForEachRemaining(MonitoredFunction function);
        void updateIteratorState();
        void modItr();
        void verifyRemove();
        void verifyClone(Object clone);

        void verifyIteratorState();
        Object verifyNext(DataType outputType);
        default boolean verifyHasNext() {
            ITR iterator=getIterator();
            boolean expectedResult=hasNext();
            boolean actual=iterator.hasNext();
            Assertions.assertEquals(expectedResult,actual);
            verifyIteratorState();
            getMonitoredCollection().verifyCollectionState();
            return actual;
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
            throw new UnsupportedOperationException("Unknown modType " + modType);
        }
        default void iterateForward() {
            ITR iterator=getIterator();
            iterator.next();
            updateIteratorState();
        }
        default void remove() {
            ITR iterator=getIterator();
            MonitoredCollection<COL> collection=getMonitoredCollection();
            iterator.remove();
            collection.updateCollectionState();
            updateIteratorState();
        }
        default Object verifyClone(){
            final ITR itr=getIterator();
            final Object clone=itr.clone();
            verifyIteratorState();
            getMonitoredCollection().verifyCollectionState();
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
            if(functionCallType==FunctionCallType.Boxed) {
                if(dataType==DataType.REF) {
                    throw new UnsupportedOperationException("Ref cannot be boxed");
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
                    throw DataType.invalidDataType(dataType);
                }
            }
            monitoredCollection.verifyCollectionState();
            Assertions.assertEquals(numLeft,function.size());
            verifyForEachRemaining(function);
            verifyIteratorState();
        }
    }
}
