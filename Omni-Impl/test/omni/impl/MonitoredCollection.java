package omni.impl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import omni.impl.seq.BooleanArrSeq;
import omni.impl.seq.ByteArrSeq;
import omni.impl.seq.CharArrSeq;
import omni.impl.seq.DoubleArrSeq;
import omni.impl.seq.FloatArrSeq;
import omni.impl.seq.IntArrSeq;
import omni.impl.seq.LongArrSeq;
import omni.impl.seq.RefArrSeq;
import omni.impl.seq.ShortArrSeq;
public interface MonitoredCollection<COL extends OmniCollection<?>>{
    
    @SuppressWarnings("unchecked")
    static Collection<?> getConstructorCollectionParam(DataType dataType,Class<? extends Collection<?>> collectionClass){
       
          if(collectionClass==Collection.class || collectionClass==OmniCollection.OfRef.class) {
              Collection<?> collectionParam;
              if(collectionClass==OmniCollection.OfRef.class) {
                  collectionParam=new RefArrSeq.UncheckedList<>();
              }else {
                  collectionParam=new ArrayList<>();
              }
            switch(dataType) {
            case BOOLEAN:
                Collections.addAll((Collection<Boolean>)collectionParam,Boolean.FALSE,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE,Boolean.FALSE,Boolean.TRUE);
                break;
            case BYTE:
                Collections.addAll((Collection<Byte>)collectionParam,(byte)0,(byte)1,(byte)2,(byte)3,(byte)4,(byte)5,(byte)6,(byte)7,(byte)8,(byte)9);
                break;
            case CHAR:
                Collections.addAll((Collection<Character>)collectionParam,(char)0,(char)1,(char)2,(char)3,(char)4,(char)5,(char)6,(char)7,(char)8,(char)9);
                break;
            case SHORT:
                Collections.addAll((Collection<Short>)collectionParam,(short)0,(short)1,(short)2,(short)3,(short)4,(short)5,(short)6,(short)7,(short)8,(short)9);
                break;
            case INT:
                Collections.addAll((Collection<Integer>)collectionParam,0,1,2,3,4,5,6,7,8,9);
                break;
            case LONG:
                Collections.addAll((Collection<Long>)collectionParam,(long)0,(long)1,(long)2,(long)3,(long)4,(long)5,(long)6,(long)7,(long)8,(long)9);
                break;
            case FLOAT:
                Collections.addAll((Collection<Float>)collectionParam,(float)0,(float)1,(float)2,(float)3,(float)4,(float)5,(float)6,(float)7,(float)8,(float)9);
                break;
            case DOUBLE:
            case REF:
                Collections.addAll((Collection<Double>)collectionParam,(double)0,(double)1,(double)2,(double)3,(double)4,(double)5,(double)6,(double)7,(double)8,(double)9);
                break;
            default:
                throw dataType.invalid();
            }
            return collectionParam;
          }else {
              switch(collectionClass.getSimpleName()) {
              case "OfBoolean":
              {
                  BooleanArrSeq.UncheckedList col=new BooleanArrSeq.UncheckedList();
                  for(int i=0;i<10;++i) {
                      col.add((i&1)!=0);
                  }
                  return col;
              }
              case "OfByte":
              case "ByteOutput":
              {
                  ByteArrSeq.UncheckedList col=new ByteArrSeq.UncheckedList();
                  for(byte i=0;i<10;++i) {
                      col.add(i);
                  }
                  return col;
              }
              case "OfChar":
              case "CharOutput":
              {
                  CharArrSeq.UncheckedList col=new CharArrSeq.UncheckedList();
                  for(char i=0;i<10;++i) {
                      col.add(i);
                  }
                  return col;
              }
              case "OfShort":
              case "ShortOutput":
              {
                  ShortArrSeq.UncheckedList col=new ShortArrSeq.UncheckedList();
                  for(short i=0;i<10;++i) {
                      col.add(i);
                  }
                  return col;
              }
              case "OfInt":
              case "IntOutput":
              {
                  IntArrSeq.UncheckedList col=new IntArrSeq.UncheckedList();
                  for(int i=0;i<10;++i) {
                      col.add(i);
                  }
                  return col;
              }
              case "OfLong":
              case "LongOutput":
              {
                  LongArrSeq.UncheckedList col=new LongArrSeq.UncheckedList();
                  for(long i=0;i<10;++i) {
                      col.add(i);
                  }
                  return col;
              }
              case "OfFloat":
              case "FloatOutput":
              {
                  FloatArrSeq.UncheckedList col=new FloatArrSeq.UncheckedList();
                  for(float i=0;i<10;++i) {
                      col.add(i);
                  }
                  return col;
              }
              case "OfDouble":
              case "DoubleOutput":
              {
                  DoubleArrSeq.UncheckedList col=new DoubleArrSeq.UncheckedList();
                  for(double i=0;i<10;++i) {
                      col.add(i);
                  }
                  return col;
              }
              default:
                  throw new UnsupportedOperationException("Unknown collectionClass "+collectionClass+" (simpleName="+collectionClass.getSimpleName()+", dataType="+dataType+")");
              }
          }

    }
    Object get(int iterationIndex,DataType outputType);
    default Object get(int iterationIndex) {
        return get(iterationIndex,getDataType());
    }
    void repairModCount();
    
    MonitoredIterator<? extends OmniIterator<?>,COL> getMonitoredIterator(IteratorType itrType);
    CheckedType getCheckedType();
    COL getCollection();
    DataType getDataType();
    MonitoredIterator<? extends OmniIterator<?>,COL> getMonitoredIterator();
    default MonitoredIterator<? extends OmniIterator<?>,COL> getMonitoredIterator(int index){
        var itrMonitor=getMonitoredIterator();
        while(--index>=0 && itrMonitor.hasNext()) {
            itrMonitor.iterateForward();
        }
        return itrMonitor;
    }
    MonitoredIterator<? extends OmniIterator<?>,COL> getMonitoredIterator(int index,IteratorType itrType);
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
    void verifyHashCode(int hashCode);
    void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter);
    default void verifyArrayIsCopy(Object arr){
        verifyArrayIsCopy(arr,true);
    }
    void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame);

    default boolean verifyThrowingContains(MonitoredObjectGen monitoredObjectGen){
        try{
            return QueryCastType.ToObject.callcontains(getCollection(),monitoredObjectGen.getMonitoredObject(this,0),
                    DataType.REF);
        }finally{
            verifyCollectionState();
        }
    }
    default boolean verifyThrowingRemoveVal(MonitoredObjectGen monitoredObjectGen){
        try{
            return QueryCastType.ToObject.callremoveVal(getCollection(),monitoredObjectGen.getMonitoredObject(this,0),
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


    boolean add(int val);
    
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
            	final int index;
                Assertions.assertSame(iterator.next(),result[index=i],()->"mismatch at index "+index);
            }
        }else {
            for(int i=0;i<size;++i) {
            	final int index;
                Assertions.assertEquals(iterator.next(),result[index=i],()->"mismatch at index "+index);
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
                result=outputType.callNext(getIterator());
                verifyNextResult(outputType,result);
                updateItrNextState();
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
        MonitoredCollection<? extends COL> getMonitoredCollection();
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
