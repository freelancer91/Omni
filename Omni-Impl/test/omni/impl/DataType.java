package omni.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniCollection;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.api.OmniQueue;
import omni.api.OmniStack;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.function.ByteComparator;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.function.ByteUnaryOperator;
import omni.function.CharComparator;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.function.CharUnaryOperator;
import omni.function.DoubleComparator;
import omni.function.FloatComparator;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.function.FloatUnaryOperator;
import omni.function.IntComparator;
import omni.function.LongComparator;
import omni.function.ShortComparator;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import omni.function.ShortUnaryOperator;
import omni.util.OmniArray;
import omni.util.PeekAndPollIfc;
import omni.util.TestExecutorService;
import omni.util.ToStringUtil;
import omni.util.TypeUtil;
public enum DataType{
    BOOLEAN("BOOLEAN","BOOLEAN","BOOLEAN,BYTE,CHAR,SHORT,INT,LONG,FLOAT,DOUBLE,REF",true,false,false,Boolean.class,
            boolean.class,Boolean[].class,boolean[].class,"Boolean","Boolean",
            BooleanPredicate.class,BooleanConsumer.class,BooleanComparator.class,BooleanPredicate.class,Boolean.FALSE,
            "removeBooleanAt",boolean.class,"test","compare",
            boolean.class,"booleanElement",OmniArray.MAX_ARR_SIZE / 7,OmniArray.OfBoolean.DEFAULT_ARR){
        @Override
        public void callAddLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.BooleanInput<?>)collection;
            if(functionCallType.boxed){
                cast.addLast((Boolean)inputVal);
            }else {
                cast.addLast((boolean)inputVal);
            }
        }
        @Override public int arrayLength(Object arr) {
            return ((boolean[])arr).length;
        }
        @Override
        public void callAddFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.BooleanInput<?>)collection;
            if(functionCallType.boxed){
                cast.addFirst((Boolean)inputVal);
            }else {
                cast.addFirst((boolean)inputVal);
            }
        }
        @Override
        public boolean callOffer(Object inputVal,OmniQueue<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniQueue.BooleanInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offer((Boolean)inputVal);
            }else {
                result=cast.offer((boolean)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.BooleanInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerFirst((Boolean)inputVal);
            }else {
                result=cast.offerFirst((boolean)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.BooleanInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerLast((Boolean)inputVal);
            }else {
                result=cast.offerLast((boolean)inputVal);
            }
            return result;
        }
      @Override public Object callPop(OmniStack<?> collection) {
        return ((OmniStack.OfBoolean)collection).popBoolean();
        }
      @Override
      public void callPush(Object inputVal,OmniStack<?> collection,
              FunctionCallType functionCallType){
        var cast=(OmniStack.BooleanInput<?>)collection;
        if(functionCallType.boxed){
            cast.push((Boolean)inputVal);
        }else {
            cast.push((boolean)inputVal);
        }
      }
        @Override public Object verifyToArray(MonitoredCollection<?> monitoredCollection){
            var cast=(OmniCollection.OfBoolean)monitoredCollection.getCollection();
            boolean[] result;
            try{
                result=cast.toBooleanArray();
            }finally{
                monitoredCollection.verifyCollectionState();
            }
            monitoredCollection.verifyArrayIsCopy(result);
            var itr=cast.iterator();
            for(int i=0,bound=result.length;i<bound;++i) {
                Assertions.assertEquals(itr.nextBoolean(),result[i]);
            }
            Assertions.assertFalse(itr.hasNext());
            if(result.length==0) {
                Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,result);
            }
            return result;
        }
        @Override public Object callNext(OmniIterator<?> itr){
            return ((OmniIterator.OfBoolean)itr).nextBoolean();
        }

        @Override public Object callPrevious(OmniListIterator<?> itr){
            return ((OmniListIterator.OfBoolean)itr).previousBoolean();
        }
        @Override
        public boolean callAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniCollection.BooleanInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.add((Boolean)inputVal);
            }else {
                result=cast.add((boolean)inputVal);
            }
            return result;
        }
        @Override
        public void callAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.BooleanInput<?>)collection).add(index,(Boolean)inputVal);
            }else{
                ((OmniList.BooleanInput<?>)collection).add(index,(boolean)inputVal);
            }
        }
        @Override
        public void callAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.BooleanInput<?>)itr).add((Boolean)inputVal);
            }else{
                ((OmniListIterator.BooleanInput<?>)itr).add((boolean)inputVal);
            }
        }
        @Override
        public void callSet(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.BooleanInput<?>)itr).set((Boolean)inputVal);
            }else{
                ((OmniListIterator.BooleanInput<?>)itr).set((boolean)inputVal);
            }
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.contains((Boolean)inputVal);
            }else {
                return collection.contains((boolean)inputVal);
            }
        }
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeVal((Boolean)inputVal);
            }else {
                return collection.removeVal((boolean)inputVal);
            }
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeFirstOccurrence((Boolean)inputVal);
            }else {
                return collection.removeFirstOccurrence((boolean)inputVal);
            }
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeLastOccurrence((Boolean)inputVal);
            }else {
                return collection.removeLastOccurrence((boolean)inputVal);
            }
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.indexOf((Boolean)inputVal);
            }else {
                return collection.indexOf((boolean)inputVal);
            }
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.lastIndexOf((Boolean)inputVal);
            }else {
                return collection.lastIndexOf((boolean)inputVal);
            }
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.search((Boolean)inputVal);
            }else {
                return collection.search((boolean)inputVal);
            }
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            if(itr.hasNext()) {
                var castItr=(OmniIterator.OfBoolean)itr;
                char[] buffer=new char[5];
                for(;;) {
                    for(int i=0,valLength=ToStringUtil.getStringBoolean(castItr.nextBoolean(),buffer,0);i<valLength;++i) {
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()) {
                        break;
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset+1,result.length());
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection,String testName){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            int size=collection.size();
            System.out.print("Running test " + testName + " 0%");
            int nextPercent=1;
            int threshold=(int)Math.ceil(size / 100.0);
            int numCompleted=0;
            if(itr.hasNext()){
                var castItr=(OmniIterator.OfBoolean)itr;
                char[] buffer=new char[5];
                for(;;){
                    for(int i=0,valLength=ToStringUtil.getStringBoolean(castItr.nextBoolean(),buffer,
                            0);i < valLength;++i){
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()){
                        break;
                    }
                    if(++numCompleted >= threshold){
                        incrementProgressBar(nextPercent);
                        threshold=(int)Math.ceil(size / 100.0 * ++nextPercent);
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            finalizeProgressBar(nextPercent);
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset + 1,result.length());
        }
        @Override
        public void verifyMASSIVEToString(String result,int seqSize,String testName){
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            Assertions.assertEquals('t',result.charAt(++offset));
            Assertions.assertEquals('r',result.charAt(++offset));
            Assertions.assertEquals('u',result.charAt(++offset));
            Assertions.assertEquals('e',result.charAt(++offset));
            ++offset;
            int numBatches=TestExecutorService.getNumWorkers();
            int batchSize=(seqSize - 1) / numBatches * 6;
            for(int batchCount=0;batchCount < numBatches;++batchCount){
                final int batchOffset=offset;
                final int batchBound=batchCount == numBatches?result.length() - 1:offset + batchSize;
                TestExecutorService.submitTest(()->{
                    for(int i=batchOffset;i < batchBound;++i){
                        Assertions.assertEquals(',',result.charAt(i));
                        Assertions.assertEquals(' ',result.charAt(++i));
                        Assertions.assertEquals('t',result.charAt(++i));
                        Assertions.assertEquals('r',result.charAt(++i));
                        Assertions.assertEquals('u',result.charAt(++i));
                        Assertions.assertEquals('e',result.charAt(++i));
                    }
                });
                offset=batchBound;
            }
            Assertions.assertEquals(']',result.charAt(result.length() - 1));
            TestExecutorService.completeAllTests(testName);
        }
        @Override
        public int getMaxInt(){
            return 1;
        }
        @Override
        public int getMinInt(){
            return 0;
        }
        @Override
        public long getMaxLong(){
            return 1;
        }
        @Override
        public long getMinLong(){
            return 0;
        }

        @Override
        public Object convertVal(boolean val){
            return val;
        }
        @Override
        public Object convertVal(byte val){
            switch(val){
            case 0:
                return false;
            case 1:
                return true;
            default:
            }
            throw super.cannotBeConverted(val,BYTE);
        }
        @Override
        public Object convertVal(char val){
            switch(val){
            case 0:
                return false;
            case 1:
                return true;
            default:
            }
            throw super.cannotBeConverted(val,CHAR);
        }
        @Override
        public Object convertVal(short val){
            switch(val){
            case 0:
                return false;
            case 1:
                return true;
            default:
            }
            throw super.cannotBeConverted(val,SHORT);
        }
        @Override
        public Object convertVal(int val){
            switch(val){
            case 0:
                return false;
            case 1:
                return true;
            default:
            }
            throw super.cannotBeConverted(val,INT);
        }
        @Override
        public Object convertValUnchecked(int val){
            return (val & 1) != 0;
        }
        @Override
        public Object convertVal(long val){
            if(val == 0){
                return false;
            }
            if(val == 1){
                return true;
            }
            throw super.cannotBeConverted(val,LONG);
        }
        @Override
        public Object convertValUnchecked(long val){
            return (val & 1) != 0;
        }
        @Override
        public Object convertVal(float val){
            switch(Float.floatToRawIntBits(val)){
            case 0:
            case Integer.MIN_VALUE:
                return false;
            case TypeUtil.FLT_TRUE_BITS:
                return true;
            default:
            }
            throw super.cannotBeConverted(val,FLOAT);
        }

        @Override
        public Object convertVal(double val){
            long bits;
            if(((bits=Double.doubleToRawLongBits(val)) & Long.MAX_VALUE) == 0){
                return false;
            }
            if(bits == TypeUtil.DBL_TRUE_BITS){
                return true;
            }
            throw super.cannotBeConverted(val,DOUBLE);
        }
        @Override
        public Object callGet(int index,OmniList<?> collection){
            return ((OmniList.OfBoolean)collection).getBoolean(index);
            }
        @Override
        public void callPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.BooleanInput<?>)collection).put(index,(Boolean)inputVal);
            }else{
                ((OmniList.BooleanInput<?>)collection).put(index,(boolean)inputVal);
            }
        }
        @Override
        public Object callSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfBoolean)collection).set(index,(Boolean)inputVal);
            }else{
                return ((OmniList.OfBoolean)collection).set(index,(boolean)inputVal);
            }
        }
        @Override
        public Object callRemoveAt(int index,OmniList<?> collection){
            return ((OmniList.OfBoolean)collection).removeBooleanAt(index);
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callStableSort(MonitoredComparator sorter,OmniList<?> collection,FunctionCallType functionCallType){
            if(sorter==null) {
                if(functionCallType.boxed){
                    ((OmniList.OfBoolean)collection).sort((Comparator<? super Boolean>)sorter);
                }else{
                    ((OmniList.OfBoolean)collection).sort((BooleanComparator)sorter);
                }
            }else {
                if(functionCallType.boxed){
                    ((OmniList.OfBoolean)collection).sort((Comparator<? super Boolean>)sorter::compare);
                }else{
                    ((OmniList.OfBoolean)collection).sort((BooleanComparator)sorter::compare);
                }
            }
        }
        @Override
        public void callUnstableSort(MonitoredComparator sorter,OmniList<?> collection){
            throw new UnsupportedOperationException();
        }
        @Override public Object callPeek(PeekAndPollIfc<?> collection){
          return ((PeekAndPollIfc.BooleanOutput<?>)collection).peekBoolean();
        }
        @Override public Object callPoll(PeekAndPollIfc<?> collection){
          return ((PeekAndPollIfc.BooleanOutput<?>)collection).pollBoolean();
        }
        @Override
        public Object callElement(OmniQueue<?> collection){
            return ((OmniQueue.OfBoolean)collection).booleanElement();
        }
        @Override
        public Object callRemove(OmniQueue<?> collection){
            return ((OmniQueue.OfBoolean)collection).removeBoolean();
        }
        @Override
        public Object callPeekFirst(OmniDeque<?> collection){
            return ((OmniDeque.OfBoolean)collection).peekFirstBoolean();
        }
        @Override
        public Object callPeekLast(OmniDeque<?> collection){
            return ((OmniDeque.OfBoolean)collection).peekLastBoolean();
        }
        @Override
        public Object callPollFirst(OmniDeque<?> collection){
            return ((OmniDeque.OfBoolean)collection).pollFirstBoolean();
        }
        @Override
        public Object callPollLast(OmniDeque<?> collection){
            return ((OmniDeque.OfBoolean)collection).pollLastBoolean();
        }
        @Override
        public Object callGetFirst(OmniDeque<?> collection){
            return ((OmniDeque.OfBoolean)collection).getFirstBoolean();
        }
        @Override
        public Object callGetLast(OmniDeque<?> collection){
            return ((OmniDeque.OfBoolean)collection).getLastBoolean();
        }
        @Override
        public Object callRemoveFirst(OmniDeque<?> collection){
            return ((OmniDeque.OfBoolean)collection).removeFirstBoolean();
        }
        @Override
        public Object callRemoveLast(OmniDeque<?> collection){
            return ((OmniDeque.OfBoolean)collection).removeLastBoolean();
        }
        @Override
        public void storyInArray(int index,Object arrayOfThisType,Object inputValOfThisType){
            ((boolean[])arrayOfThisType)[index]=(boolean)inputValOfThisType;
        }
        @Override
        public Object newArray(int length){
            return new boolean[length];
        }
        @Override
        public Object getFromArray(int index,Object arrayOfThisType){
            return ((boolean[])arrayOfThisType)[index];
        }
        @Override
        public IntPredicate getArrayIndexSearcher(Object arr,Object inputVal,DataType inputType){
            boolean inputCast;
            switch(inputType){
            case BOOLEAN:
              inputCast=(boolean)inputVal;
              break;
            case BYTE:
              inputCast=(byte)inputVal == 1;
              break;
            case CHAR:
              inputCast=(char)inputVal == 1;
              break;
            case SHORT:
              inputCast=(short)inputVal == 1;
              break;
            case INT:
              inputCast=(int)inputVal == 1;
              break;
            case LONG:
              inputCast=(long)inputVal == 1L;
              break;
            case FLOAT:
              inputCast=(float)inputVal == 1F;
              break;
            case DOUBLE:
              inputCast=(double)inputVal == 1D;
              break;
            default:
              throw inputType.invalid();
            }
            final var arrCast=(boolean[])arr;
            return index->arrCast[index]==inputCast;
        }
       
    },
    BYTE("BYTE","BOOLEAN,BYTE","BYTE,SHORT,INT,LONG,FLOAT,DOUBLE,REF",true,false,true,Byte.class,byte.class,
            Byte[].class,byte[].class,"Byte","Byte",BytePredicate.class,
            ByteConsumer.class,ByteComparator.class,ByteUnaryOperator.class,Byte.MIN_VALUE,"removeByteAt",int.class,"applyAsByte","compare",byte.class,"byteElement",
            OmniArray.MAX_ARR_SIZE / 6,OmniArray.OfByte.DEFAULT_ARR){
        @Override
        public Object getFromArray(int index,Object arrayOfThisType){
            return ((byte[])arrayOfThisType)[index];
        }
        @Override
        public Object newArray(int length){
            return new byte[length];
        }
        @Override public int arrayLength(Object arr) {
            return ((byte[])arr).length;
        }
        @Override
        public void callAddLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.ByteInput<?>)collection;
            if(functionCallType.boxed){
                cast.addLast((Byte)inputVal);
            }else {
                cast.addLast((byte)inputVal);
            }
        }
        @Override
        public void callAddFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.ByteInput<?>)collection;
            if(functionCallType.boxed){
                cast.addFirst((Byte)inputVal);
            }else {
                cast.addFirst((byte)inputVal);
            }
        }
        @Override
        public boolean callOffer(Object inputVal,OmniQueue<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniQueue.ByteInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offer((Byte)inputVal);
            }else {
                result=cast.offer((byte)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.ByteInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerFirst((Byte)inputVal);
            }else {
                result=cast.offerFirst((byte)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.ByteInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerLast((Byte)inputVal);
            }else {
                result=cast.offerLast((byte)inputVal);
            }
            return result;
        }
      @Override public Object callPop(OmniStack<?> collection) {
        return ((OmniStack.ByteOutput<?>)collection).popByte();
        }
      @Override public Object callPeek(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.ByteOutput<?>)collection).peekByte();
      }
      @Override public Object callPoll(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.ByteOutput<?>)collection).pollByte();
      }
      @Override
      public void callPush(Object inputVal,OmniStack<?> collection,
              FunctionCallType functionCallType){
        var cast=(OmniStack.ByteInput<?>)collection;
        if(functionCallType.boxed){
            cast.push((Byte)inputVal);
        }else {
            cast.push((byte)inputVal);
        }
      }
        @Override
        public Object convertVal(boolean val){
            return TypeUtil.castToByte(val);
        }
        @Override
        public Object convertVal(byte val){
            return val;
        }
        @Override
        public Object convertVal(char val){
            if(val <= Byte.MAX_VALUE){
                return (byte)val;
            }
            throw super.cannotBeConverted(val,CHAR);
        }
        @Override
        public Object convertVal(short val){
            byte b;
            if(val == (b=(byte)val)){
                return b;
            }
            throw super.cannotBeConverted(val,SHORT);
        }
        @Override
        public Object convertVal(int val){
            byte b;
            if(val == (b=(byte)val)){
                return b;
            }
            throw super.cannotBeConverted(val,INT);
        }
        @Override
        public Object convertValUnchecked(int val){
            return (byte)val;
        }
        @Override
        public Object convertVal(long val){
            byte b;
            if(val == (b=(byte)val)){
                return b;
            }
            throw super.cannotBeConverted(val,LONG);
        }
        @Override
        public Object convertValUnchecked(long val){
            return (byte)val;
        }
        @Override
        public Object convertVal(float val){
            byte b;
            if(val == (b=(byte)val)){
                return b;
            }
            throw super.cannotBeConverted(val,FLOAT);
        }
        @Override
        public Object convertVal(double val){
            byte b;
            if(val == (b=(byte)val)){
                return b;
            }
            throw super.cannotBeConverted(val,DOUBLE);
        }
        @Override public Object verifyToArray(MonitoredCollection<?> monitoredCollection){
            var cast=(OmniCollection.ByteOutput<?>)monitoredCollection.getCollection();
            byte[] result;
            try{
                result=cast.toByteArray();
            }finally{
                monitoredCollection.verifyCollectionState();
            }
            monitoredCollection.verifyArrayIsCopy(result);
            var itr=cast.iterator();
            for(int i=0,bound=result.length;i<bound;++i) {
                Assertions.assertEquals(itr.nextByte(),result[i]);
            }
            Assertions.assertFalse(itr.hasNext());
            if(result.length==0) {
                Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,result);
            }
            return result;
        }
        @Override public Object callNext(OmniIterator<?> itr){
            return ((OmniIterator.ByteOutput<?>)itr).nextByte();
        }
        @Override public Object callPrevious(OmniListIterator<?> itr){
            return ((OmniListIterator.ByteOutput<?>)itr).previousByte();
        }
        @Override
        public boolean callAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniCollection.ByteInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.add((Byte)inputVal);
            }else {
                result=cast.add((byte)inputVal);
            }
            return result;
        }
        @Override
        public void callAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.ByteInput<?>)collection).add(index,(Byte)inputVal);
            }else{
                ((OmniList.ByteInput<?>)collection).add(index,(byte)inputVal);
            }
        }
        @Override
        public void callAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.ByteInput<?>)itr).add((Byte)inputVal);
            }else{
                ((OmniListIterator.ByteInput<?>)itr).add((byte)inputVal);
            }
        }
        @Override
        public void callSet(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.ByteInput<?>)itr).set((Byte)inputVal);
            }else{
                ((OmniListIterator.ByteInput<?>)itr).set((byte)inputVal);
            }
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.contains((Byte)inputVal);
            }else {
                return collection.contains((byte)inputVal);
            }
        }
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeVal((Byte)inputVal);
            }else {
                return collection.removeVal((byte)inputVal);
            }
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeFirstOccurrence((Byte)inputVal);
            }else {
                return collection.removeFirstOccurrence((byte)inputVal);
            }
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeLastOccurrence((Byte)inputVal);
            }else {
                return collection.removeLastOccurrence((byte)inputVal);
            }
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.indexOf((Byte)inputVal);
            }else {
                return collection.indexOf((byte)inputVal);
            }
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.lastIndexOf((Byte)inputVal);
            }else {
                return collection.lastIndexOf((byte)inputVal);
            }
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.search((Byte)inputVal);
            }else {
                return collection.search((byte)inputVal);
            }
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            if(itr.hasNext()) {
                var castItr=(OmniIterator.OfByte)itr;
                char[] buffer=new char[4];
                for(;;) {
                    for(int i=0,valLength=ToStringUtil.getStringShort(castItr.nextByte(),buffer,0);i<valLength;++i) {
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()) {
                        break;
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset+1,result.length());
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection,String testName){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            int size=collection.size();
            System.out.print("Running test " + testName + " 0%");
            int nextPercent=1;
            int threshold=(int)Math.ceil(size / 100.0);
            int numCompleted=0;
            if(itr.hasNext()){
                var castItr=(OmniIterator.OfByte)itr;
                char[] buffer=new char[4];
                for(;;){
                    for(int i=0,valLength=ToStringUtil.getStringShort(castItr.nextByte(),buffer,0);i < valLength;++i){
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()){
                        break;
                    }
                    if(++numCompleted >= threshold){
                        incrementProgressBar(nextPercent);
                        threshold=(int)Math.ceil(size / 100.0 * ++nextPercent);
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            finalizeProgressBar(nextPercent);
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset + 1,result.length());
        }
        @Override
        public int getMaxInt(){
            return Byte.MAX_VALUE;
        }
        @Override
        public int getMinInt(){
            return Byte.MIN_VALUE;
        }
        @Override
        public long getMaxLong(){
            return Byte.MAX_VALUE;
        }
        @Override
        public long getMinLong(){
            return Byte.MIN_VALUE;
        }
        @Override
        public Object callGet(int index,OmniList<?> collection){
            return ((OmniList.ByteOutput<?>)collection).getByte(index);
            }
        @Override
        public void callPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.ByteInput<?>)collection).put(index,(Byte)inputVal);
            }else{
                ((OmniList.ByteInput<?>)collection).put(index,(byte)inputVal);
            }
        }
        @Override
        public Object callSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfByte)collection).set(index,(Byte)inputVal);
            }else{
                return ((OmniList.OfByte)collection).set(index,(byte)inputVal);
            }
        }
        @Override
        public Object callRemoveAt(int index,OmniList<?> collection){
            return ((OmniList.ByteOutput<?>)collection).removeByteAt(index);
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callStableSort(MonitoredComparator sorter,OmniList<?> collection,FunctionCallType functionCallType){
            if(sorter==null) {
                if(functionCallType.boxed){
                    ((OmniList.OfByte)collection).sort((Comparator<? super Byte>)sorter);
                }else{
                    ((OmniList.OfByte)collection).sort((ByteComparator)sorter);
                }
            }else {
                if(functionCallType.boxed){
                    ((OmniList.OfByte)collection).sort((Comparator<? super Byte>)sorter::compare);
                }else{
                    ((OmniList.OfByte)collection).sort((ByteComparator)sorter::compare);
                }
            }
            
        }
        @Override
        public void callUnstableSort(MonitoredComparator sorter,OmniList<?> collection){
            if(sorter==null) {
                ((OmniList.OfByte)collection).unstableSort((ByteComparator)sorter);
            }else {
                ((OmniList.OfByte)collection).unstableSort(sorter::compare);
            }
            
        }
        @Override
        public Object callElement(OmniQueue<?> collection){
            return ((OmniQueue.ByteOutput<?>)collection).byteElement();
        }
        @Override
        public Object callRemove(OmniQueue<?> collection){
            return ((OmniQueue.ByteOutput<?>)collection).removeByte();
        }
        @Override
        public Object callPeekFirst(OmniDeque<?> collection){
            return ((OmniDeque.ByteOutput<?>)collection).peekFirstByte();
        }
        @Override
        public Object callPeekLast(OmniDeque<?> collection){
            return ((OmniDeque.ByteOutput<?>)collection).peekLastByte();
                    }
        @Override
        public Object callPollFirst(OmniDeque<?> collection){
            return ((OmniDeque.ByteOutput<?>)collection).pollFirstByte();
                    }
        @Override
        public Object callPollLast(OmniDeque<?> collection){
            return ((OmniDeque.ByteOutput<?>)collection).pollLastByte();
                    }
        @Override
        public Object callGetFirst(OmniDeque<?> collection){
            return ((OmniDeque.ByteOutput<?>)collection).getFirstByte();
                    }
        @Override
        public Object callGetLast(OmniDeque<?> collection){
            return ((OmniDeque.ByteOutput<?>)collection).getLastByte();
                    }
        @Override
        public Object callRemoveFirst(OmniDeque<?> collection){
            return ((OmniDeque.ByteOutput<?>)collection).removeFirstByte();
                    }
        @Override
        public Object callRemoveLast(OmniDeque<?> collection){
            return ((OmniDeque.ByteOutput<?>)collection).removeLastByte();
                    }
        @Override
        public void storyInArray(int index,Object arrayOfThisType,Object inputValOfThisType){
            ((byte[])arrayOfThisType)[index]=(byte)inputValOfThisType;
        }
        @Override
        public IntPredicate getArrayIndexSearcher(Object arr,Object inputVal,DataType inputType){
            byte inputCast;
            switch(inputType){
            case BOOLEAN:
              inputCast=(boolean)inputVal?(byte)1:(byte)0;
              break;
            case BYTE:
              inputCast=(byte)inputVal;
              break;
            case CHAR:
              inputCast=(byte)(char)inputVal;
              break;
            case SHORT:
              inputCast=(byte)(short)inputVal;
              break;
            case INT:
              inputCast=(byte)(int)inputVal;
              break;
            case LONG:
              inputCast=(byte)(long)inputVal;
              break;
            case FLOAT:
              inputCast=(byte)(float)inputVal;
              break;
            case DOUBLE:
              inputCast=(byte)(double)inputVal;
              break;
            default:
              throw inputType.invalid();
            }
            final var arrCast=(byte[])arr;
            return index->arrCast[index]==inputCast;
        }
    },
    CHAR("CHAR","BOOLEAN,CHAR","CHAR,INT,LONG,FLOAT,DOUBLE,REF",true,false,false,Character.class,char.class,
            Character[].class,char[].class,"Char","Char",CharPredicate.class,CharConsumer.class,CharComparator.class,
            CharUnaryOperator.class,Character.MIN_VALUE,"removeCharAt",
            int.class,"applyAsChar","compare",char.class,"charElement",-1,OmniArray.OfChar.DEFAULT_ARR){
        @Override
        public Object getFromArray(int index,Object arrayOfThisType){
            return ((char[])arrayOfThisType)[index];
        }
        @Override
        public Object newArray(int length){
            return new char[length];
        }
        @Override public int arrayLength(Object arr) {
            return ((char[])arr).length;
        }
        @Override
        public void callAddLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.CharInput<?>)collection;
            if(functionCallType.boxed){
                cast.addLast((Character)inputVal);
            }else {
                cast.addLast((char)inputVal);
            }
        }
        @Override
        public void callAddFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.CharInput<?>)collection;
            if(functionCallType.boxed){
                cast.addFirst((Character)inputVal);
            }else {
                cast.addFirst((char)inputVal);
            }
        }
        @Override
        public boolean callOffer(Object inputVal,OmniQueue<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniQueue.CharInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offer((Character)inputVal);
            }else {
                result=cast.offer((char)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.CharInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerFirst((Character)inputVal);
            }else {
                result=cast.offerFirst((char)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.CharInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerLast((Character)inputVal);
            }else {
                result=cast.offerLast((char)inputVal);
            }
            return result;
        }
      @Override public Object callPop(OmniStack<?> collection) {
        return ((OmniStack.CharOutput<?>)collection).popChar();
        }
      @Override public Object callPeek(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.CharOutput<?>)collection).peekChar();
      }
      @Override public Object callPoll(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.CharOutput<?>)collection).pollChar();
      }
      @Override
      public void callPush(Object inputVal,OmniStack<?> collection,
              FunctionCallType functionCallType){
        var cast=(OmniStack.CharInput<?>)collection;
        if(functionCallType.boxed){
            cast.push((Character)inputVal);
        }else {
            cast.push((char)inputVal);
        }
      }
        @Override
        public Object convertVal(boolean val){
            return TypeUtil.castToChar(val);
        }
        @Override
        public Object convertVal(byte val){
            if(val >= 0){
                return (char)val;
            }
            throw super.cannotBeConverted(val,BYTE);
        }
        @Override
        public Object convertVal(char val){
            return val;
        }
        @Override
        public Object convertVal(short val){
            if(val >= 0){
                return (char)val;
            }
            throw super.cannotBeConverted(val,SHORT);
        }
        @Override
        public Object convertVal(int val){
            char c;
            if((c=(char)val) == val){
                return c;
            }
            throw super.cannotBeConverted(val,INT);
        }
        @Override
        public Object convertValUnchecked(int val){
            return (char)val;
        }
        @Override
        public Object convertVal(long val){
            char c;
            if((c=(char)val) == val){
                return c;
            }
            throw super.cannotBeConverted(val,LONG);
        }
        @Override
        public Object convertValUnchecked(long val){
            return (char)val;
        }
        @Override
        public Object convertVal(float val){
            char c;
            if((c=(char)val) == val){
                return c;
            }
            throw super.cannotBeConverted(val,FLOAT);
        }
        @Override
        public Object convertVal(double val){
            char c;
            if((c=(char)val) == val){
                return c;
            }
            throw super.cannotBeConverted(val,DOUBLE);
        }
        @Override public Object verifyToArray(MonitoredCollection<?> monitoredCollection){
            var cast=(OmniCollection.CharOutput<?>)monitoredCollection.getCollection();
            char[] result;
            try{
                result=cast.toCharArray();
            }finally{
                monitoredCollection.verifyCollectionState();
            }
            monitoredCollection.verifyArrayIsCopy(result);
            var itr=cast.iterator();
            for(int i=0,bound=result.length;i<bound;++i) {
                Assertions.assertEquals(itr.nextChar(),result[i]);
            }
            Assertions.assertFalse(itr.hasNext());
            if(result.length==0) {
                Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,result);
            }
            return result;
        }
        @Override public Object callNext(OmniIterator<?> itr){
            return ((OmniIterator.CharOutput<?>)itr).nextChar();
        }
        @Override public Object callPrevious(OmniListIterator<?> itr){
            return ((OmniListIterator.CharOutput<?>)itr).previousChar();
        }
        @Override
        public boolean callAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniCollection.CharInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.add((Character)inputVal);
            }else {
                result=cast.add((char)inputVal);
            }
            return result;
        }
        @Override
        public void callAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.CharInput<?>)collection).add(index,(Character)inputVal);
            }else{
                ((OmniList.CharInput<?>)collection).add(index,(char)inputVal);
            }
        }
        @Override
        public void callAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.CharInput<?>)itr).add((Character)inputVal);
            }else{
                ((OmniListIterator.CharInput<?>)itr).add((char)inputVal);
            }
        }
        @Override
        public void callSet(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.CharInput<?>)itr).set((Character)inputVal);
            }else{
                ((OmniListIterator.CharInput<?>)itr).set((char)inputVal);
            }
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.contains((Character)inputVal);
            }else {
                return collection.contains((char)inputVal);
            }
        }
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeVal((Character)inputVal);
            }else {
                return collection.removeVal((char)inputVal);
            }
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeFirstOccurrence((Character)inputVal);
            }else {
                return collection.removeFirstOccurrence((char)inputVal);
            }
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeLastOccurrence((Character)inputVal);
            }else {
                return collection.removeLastOccurrence((char)inputVal);
            }
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.indexOf((Character)inputVal);
            }else {
                return collection.indexOf((char)inputVal);
            }
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.lastIndexOf((Character)inputVal);
            }else {
                return collection.lastIndexOf((char)inputVal);
            }
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.search((Character)inputVal);
            }else {
                return collection.search((char)inputVal);
            }
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            if(itr.hasNext()) {
                var castItr=(OmniIterator.OfChar)itr;
                for(;;) {
                    Assertions.assertEquals(castItr.nextChar(),result.charAt(++offset));
                    if(!itr.hasNext()) {
                        break;
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset+1,result.length());
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection,String testName){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            int size=collection.size();
            System.out.print("Running test " + testName + " 0%");
            int nextPercent=1;
            int threshold=(int)Math.ceil(size / 100.0);
            int numCompleted=0;
            if(itr.hasNext()){
                var castItr=(OmniIterator.OfChar)itr;
                for(;;){
                    Assertions.assertEquals(castItr.nextChar(),result.charAt(++offset));
                    if(!itr.hasNext()){
                        break;
                    }
                    if(!itr.hasNext()){
                        break;
                    }
                    if(++numCompleted >= threshold){
                        incrementProgressBar(nextPercent);
                        threshold=(int)Math.ceil(size / 100.0 * ++nextPercent);
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            finalizeProgressBar(nextPercent);
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset + 1,result.length());
        }
        @Override
        public int getMaxInt(){
            return Character.MAX_VALUE;
        }
        @Override
        public int getMinInt(){
            return 0;
        }
        @Override
        public long getMaxLong(){
            return Character.MAX_VALUE;
        }
        @Override
        public long getMinLong(){
            return 0;
        }
        @Override
        public Object callGet(int index,OmniList<?> collection){
            return ((OmniList.CharOutput<?>)collection).getChar(index);
            }
        @Override
        public void callPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.CharInput<?>)collection).put(index,(Character)inputVal);
            }else{
                ((OmniList.CharInput<?>)collection).put(index,(char)inputVal);
            }
        }
        @Override
        public Object callSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfChar)collection).set(index,(Character)inputVal);
            }else{
                return ((OmniList.OfChar)collection).set(index,(char)inputVal);
            }
        }
        @Override
        public void verifyMASSIVEToString(String result,int seqSize,String testName){
            throw new UnsupportedOperationException();
        }
        @Override
        public Object callRemoveAt(int index,OmniList<?> collection){
            return ((OmniList.CharOutput<?>)collection).removeCharAt(index);
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callStableSort(MonitoredComparator sorter,OmniList<?> collection,FunctionCallType functionCallType){
            if(sorter==null) {
                if(functionCallType.boxed){
                    ((OmniList.OfChar)collection).sort((Comparator<? super Character>)sorter);
                }else{
                    ((OmniList.OfChar)collection).sort((CharComparator)sorter);
                }
            }else {
                if(functionCallType.boxed){
                    ((OmniList.OfChar)collection).sort((Comparator<? super Character>)sorter::compare);
                }else{
                    ((OmniList.OfChar)collection).sort((CharComparator)sorter::compare);
                }
            }
        }
        @Override
        public void callUnstableSort(MonitoredComparator sorter,OmniList<?> collection){
            if(sorter==null) {
                ((OmniList.OfChar)collection).unstableSort((CharComparator)sorter);
            }else {
                ((OmniList.OfChar)collection).unstableSort(sorter::compare);
            }
            
        }
        @Override
        public Object callElement(OmniQueue<?> collection){
            return ((OmniQueue.CharOutput<?>)collection).charElement();
        }
        @Override
        public Object callRemove(OmniQueue<?> collection){
            return ((OmniQueue.CharOutput<?>)collection).removeChar();
            }
        @Override
        public Object callPeekFirst(OmniDeque<?> collection){
            return ((OmniDeque.CharOutput<?>)collection).peekFirstChar();
            }
        @Override
        public Object callPeekLast(OmniDeque<?> collection){
            return ((OmniDeque.CharOutput<?>)collection).peekLastChar();
            }
        @Override
        public Object callPollFirst(OmniDeque<?> collection){
            return ((OmniDeque.CharOutput<?>)collection).pollFirstChar();
            }
        @Override
        public Object callPollLast(OmniDeque<?> collection){
            return ((OmniDeque.CharOutput<?>)collection).pollLastChar();
            }
        @Override
        public Object callGetFirst(OmniDeque<?> collection){
            return ((OmniDeque.CharOutput<?>)collection).getFirstChar();
            }
        @Override
        public Object callGetLast(OmniDeque<?> collection){
            return ((OmniDeque.CharOutput<?>)collection).getLastChar();
            }
        @Override
        public Object callRemoveFirst(OmniDeque<?> collection){
            return ((OmniDeque.CharOutput<?>)collection).removeFirstChar();
            }
        @Override
        public Object callRemoveLast(OmniDeque<?> collection){
            return ((OmniDeque.CharOutput<?>)collection).removeLastChar();
            }
        @Override
        public void storyInArray(int index,Object arrayOfThisType,Object inputValOfThisType){
            ((char[])arrayOfThisType)[index]=(char)inputValOfThisType;
        }
        @Override
        public IntPredicate getArrayIndexSearcher(Object arr,Object inputVal,DataType inputType){
            char inputCast;
            switch(inputType){
            case BOOLEAN:
              inputCast=(boolean)inputVal?(char)1:(char)0;
              break;
            case BYTE:
              inputCast=(char)(byte)inputVal;
              break;
            case CHAR:
              inputCast=(char)inputVal;
              break;
            case SHORT:
              inputCast=(char)(short)inputVal;
              break;
            case INT:
              inputCast=(char)(int)inputVal;
              break;
            case LONG:
              inputCast=(char)(long)inputVal;
              break;
            case FLOAT:
              inputCast=(char)(float)inputVal;
              break;
            case DOUBLE:
              inputCast=(char)(double)inputVal;
              break;
            default:
              throw inputType.invalid();
            }
            final var arrCast=(char[])arr;
            return index->arrCast[index]==inputCast;
        }
    },
    SHORT("SHORT","BOOLEAN,BYTE,SHORT","SHORT,INT,LONG,FLOAT,DOUBLE,REF",true,false,true,Short.class,short.class,
            Short[].class,short[].class,"Short","Short",ShortPredicate.class,ShortConsumer.class,ShortComparator.class,
            ShortUnaryOperator.class,Short.MIN_VALUE,"removeShortAt",
            int.class,"applyAsShort","compare",short.class,"shortElement",OmniArray.MAX_ARR_SIZE / 8,OmniArray.OfShort.DEFAULT_ARR){
        @Override
        public Object getFromArray(int index,Object arrayOfThisType){
            return ((short[])arrayOfThisType)[index];
        }
        @Override
        public Object newArray(int length){
            return new short[length];
        }
        @Override public int arrayLength(Object arr) {
            return ((short[])arr).length;
        }
        @Override
        public void callAddLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.ShortInput<?>)collection;
            if(functionCallType.boxed){
                cast.addLast((Short)inputVal);
            }else {
                cast.addLast((short)inputVal);
            }
        }
        @Override
        public void callAddFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.ShortInput<?>)collection;
            if(functionCallType.boxed){
                cast.addFirst((Short)inputVal);
            }else {
                cast.addFirst((short)inputVal);
            }
        }
        @Override
        public boolean callOffer(Object inputVal,OmniQueue<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniQueue.ShortInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offer((Short)inputVal);
            }else {
                result=cast.offer((short)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.ShortInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerFirst((Short)inputVal);
            }else {
                result=cast.offerFirst((short)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.ShortInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerLast((Short)inputVal);
            }else {
                result=cast.offerLast((short)inputVal);
            }
            return result;
        }
      @Override public Object callPop(OmniStack<?> collection) {
        return ((OmniStack.ShortOutput<?>)collection).popShort();
        }
      @Override public Object callPeek(PeekAndPollIfc<?> collection){
       
        return ((PeekAndPollIfc.ShortOutput<?>)collection).peekShort();
      }
      @Override public Object callPoll(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.ShortOutput<?>)collection).pollShort();
      }
      @Override
      public void callPush(Object inputVal,OmniStack<?> collection,
              FunctionCallType functionCallType){
        var cast=(OmniStack.ShortInput<?>)collection;
        if(functionCallType.boxed){
            cast.push((Short)inputVal);
        }else {
            cast.push((short)inputVal);
        }
      }
        @Override
        public Object convertVal(boolean val){
            return (short)TypeUtil.castToByte(val);
        }
        @Override
        public Object convertVal(byte val){
            return (short)val;
        }
        @Override
        public Object convertVal(char val){
            if(val <= Short.MAX_VALUE){
                return (short)val;
            }
            throw super.cannotBeConverted(val,CHAR);
        }
        @Override
        public Object convertVal(short val){
            return (short)val;
        }
        @Override
        public Object convertVal(int val){
            short s;
            if(val == (s=(short)val)){
                return s;
            }
            throw super.cannotBeConverted(val,INT);
        }
        @Override
        public Object convertValUnchecked(int val){
            return (short)val;
        }
        @Override
        public Object convertVal(long val){
            short v;
            if((v=(short)val) == val){
                return v;
            }
            throw super.cannotBeConverted(val,LONG);
        }
        @Override
        public Object convertValUnchecked(long val){
            return (short)val;
        }
        @Override
        public Object convertVal(float val){
            short v;
            if((v=(short)val) == val){
                return v;
            }
            throw super.cannotBeConverted(val,FLOAT);
        }
        @Override
        public Object convertVal(double val){
            short v;
            if((v=(short)val) == val){
                return v;
            }
            throw super.cannotBeConverted(val,DOUBLE);
        }
        @Override public Object verifyToArray(MonitoredCollection<?> monitoredCollection){
            var cast=(OmniCollection.ShortOutput<?>)monitoredCollection.getCollection();
            short[] result;
            try{
                result=cast.toShortArray();
            }finally{
                monitoredCollection.verifyCollectionState();
            }
            monitoredCollection.verifyArrayIsCopy(result);
            var itr=cast.iterator();
            for(int i=0,bound=result.length;i<bound;++i) {
                Assertions.assertEquals(itr.nextShort(),result[i]);
            }
            Assertions.assertFalse(itr.hasNext());
            if(result.length==0) {
                Assertions.assertSame(OmniArray.OfShort.DEFAULT_ARR,result);
            }
            return result;
        }
        @Override public Object callNext(OmniIterator<?> itr){
            return ((OmniIterator.ShortOutput<?>)itr).nextShort();
        }
        @Override public Object callPrevious(OmniListIterator<?> itr){
            return ((OmniListIterator.ShortOutput<?>)itr).previousShort();
        }
        @Override
        public boolean callAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniCollection.ShortInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.add((Short)inputVal);
            }else {
                result=cast.add((short)inputVal);
            }
            return result;
        }
        @Override
        public void callAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.ShortInput<?>)collection).add(index,(Short)inputVal);
            }else{
                ((OmniList.ShortInput<?>)collection).add(index,(short)inputVal);
            }
        }
        @Override
        public void callAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.ShortInput<?>)itr).add((Short)inputVal);
            }else{
                ((OmniListIterator.ShortInput<?>)itr).add((short)inputVal);
            }
        }
        @Override
        public void callSet(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.ShortInput<?>)itr).set((Short)inputVal);
            }else{
                ((OmniListIterator.ShortInput<?>)itr).set((short)inputVal);
            }
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.contains((Short)inputVal);
            }else {
                return collection.contains((short)inputVal);
            }
        }
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeVal((Short)inputVal);
            }else {
                return collection.removeVal((short)inputVal);
            }
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeFirstOccurrence((Short)inputVal);
            }else {
                return collection.removeFirstOccurrence((short)inputVal);
            }
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeLastOccurrence((Short)inputVal);
            }else {
                return collection.removeLastOccurrence((short)inputVal);
            }
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.indexOf((Short)inputVal);
            }else {
                return collection.indexOf((short)inputVal);
            }
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.lastIndexOf((Short)inputVal);
            }else {
                return collection.lastIndexOf((short)inputVal);
            }
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.search((Short)inputVal);
            }else {
                return collection.search((short)inputVal);
            }
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            if(itr.hasNext()) {
                var castItr=(OmniIterator.OfShort)itr;
                char[] buffer=new char[6];
                for(;;) {
                    for(int i=0,valLength=ToStringUtil.getStringShort(castItr.nextShort(),buffer,0);i<valLength;++i) {
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()) {
                        break;
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset+1,result.length());
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection,String testName){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            int size=collection.size();
            System.out.print("Running test " + testName + " 0%");
            int nextPercent=1;
            int threshold=(int)Math.ceil(size / 100.0);
            int numCompleted=0;
            if(itr.hasNext()){
                var castItr=(OmniIterator.OfShort)itr;
                char[] buffer=new char[6];
                for(;;){
                    for(int i=0,valLength=ToStringUtil.getStringShort(castItr.nextShort(),buffer,0);i < valLength;++i){
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()){
                        break;
                    }
                    if(++numCompleted >= threshold){
                        incrementProgressBar(nextPercent);
                        threshold=(int)Math.ceil(size / 100.0 * ++nextPercent);
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            finalizeProgressBar(nextPercent);
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset + 1,result.length());
        }
        @Override
        public int getMaxInt(){
            return Short.MAX_VALUE;
        }
        @Override
        public int getMinInt(){
            return Short.MIN_VALUE;
        }
        @Override
        public long getMaxLong(){
            return Short.MAX_VALUE;
        }
        @Override
        public long getMinLong(){
            return Short.MIN_VALUE;
        }
        @Override
        public Object callGet(int index,OmniList<?> collection){
            return ((OmniList.ShortOutput<?>)collection).getShort(index);
            }
        @Override
        public void callPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.ShortInput<?>)collection).put(index,(Short)inputVal);
            }else{
                ((OmniList.ShortInput<?>)collection).put(index,(short)inputVal);
            }
        }
        @Override
        public Object callSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfShort)collection).set(index,(Short)inputVal);
            }else{
                return ((OmniList.OfShort)collection).set(index,(short)inputVal);
            }
        }
        @Override
        public Object callRemoveAt(int index,OmniList<?> collection){
            return ((OmniList.ShortOutput<?>)collection).removeShortAt(index);
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callStableSort(MonitoredComparator sorter,OmniList<?> collection,FunctionCallType functionCallType){
            if(sorter==null) {
                if(functionCallType.boxed){
                    ((OmniList.OfShort)collection).sort((Comparator<? super Short>)sorter);
                }else{
                    ((OmniList.OfShort)collection).sort((ShortComparator)sorter);
                }
            }else {
                if(functionCallType.boxed){
                    ((OmniList.OfShort)collection).sort((Comparator<? super Short>)sorter::compare);
                }else{
                    ((OmniList.OfShort)collection).sort((ShortComparator)sorter::compare);
                }
            }
           
        }
        @Override
        public void callUnstableSort(MonitoredComparator sorter,OmniList<?> collection){
            if(sorter==null) {
                ((OmniList.OfShort)collection).unstableSort((ShortComparator)sorter);
            }else {
                ((OmniList.OfShort)collection).unstableSort(sorter::compare);
            }        }
        @Override
        public Object callElement(OmniQueue<?> collection){
            return ((OmniQueue.ShortOutput<?>)collection).shortElement();
        }
        @Override
        public Object callRemove(OmniQueue<?> collection){
            return ((OmniQueue.ShortOutput<?>)collection).removeShort();
            }
        @Override
        public Object callPeekFirst(OmniDeque<?> collection){
            return ((OmniDeque.ShortOutput<?>)collection).peekFirstShort();
            }
        @Override
        public Object callPeekLast(OmniDeque<?> collection){
            return ((OmniDeque.ShortOutput<?>)collection).peekLastShort();
            }
        @Override
        public Object callPollFirst(OmniDeque<?> collection){
            return ((OmniDeque.ShortOutput<?>)collection).pollFirstShort();
            }
        @Override
        public Object callPollLast(OmniDeque<?> collection){
            return ((OmniDeque.ShortOutput<?>)collection).pollLastShort();
            }
        @Override
        public Object callGetFirst(OmniDeque<?> collection){
            return ((OmniDeque.ShortOutput<?>)collection).getFirstShort();
            }
        @Override
        public Object callGetLast(OmniDeque<?> collection){
            return ((OmniDeque.ShortOutput<?>)collection).getLastShort();
            }
        @Override
        public Object callRemoveFirst(OmniDeque<?> collection){
            return ((OmniDeque.ShortOutput<?>)collection).removeFirstShort();
            }
        @Override
        public Object callRemoveLast(OmniDeque<?> collection){
            return ((OmniDeque.ShortOutput<?>)collection).removeLastShort();
            }
        @Override
        public void storyInArray(int index,Object arrayOfThisType,Object inputValOfThisType){
            ((short[])arrayOfThisType)[index]=(short)inputValOfThisType;
        }
        @Override
        public IntPredicate getArrayIndexSearcher(Object arr,Object inputVal,DataType inputType){
            short inputCast;
            switch(inputType){
            case BOOLEAN:
              inputCast=(boolean)inputVal?(short)1:(short)0;
              break;
            case BYTE:
              inputCast=(byte)inputVal;
              break;
            case CHAR:
              inputCast=(short)(char)inputVal;
              break;
            case SHORT:
              inputCast=(short)inputVal;
              break;
            case INT:
              inputCast=(short)(int)inputVal;
              break;
            case LONG:
              inputCast=(short)(long)inputVal;
              break;
            case FLOAT:
              inputCast=(short)(float)inputVal;
              break;
            case DOUBLE:
              inputCast=(short)(double)inputVal;
              break;
            default:
              throw inputType.invalid();
            }
            final var arrCast=(short[])arr;
            return index->arrCast[index]==inputCast;
        }
    },
    INT("INT","BOOLEAN,BYTE,CHAR,SHORT,INT","INT,LONG,FLOAT,DOUBLE,REF",true,false,true,Integer.class,int.class,
            Integer[].class,int[].class,"Int","Int",IntPredicate.class,
            IntConsumer.class,IntBinaryOperator.class,IntUnaryOperator.class,Integer.MIN_VALUE,"removeIntAt",int.class,"applyAsInt","applyAsInt",int.class,"intElement",
            OmniArray.MAX_ARR_SIZE / 13,OmniArray.OfInt.DEFAULT_ARR){
        @Override
        public Object getFromArray(int index,Object arrayOfThisType){
            return ((int[])arrayOfThisType)[index];
        }
        @Override public int arrayLength(Object arr) {
            return ((int[])arr).length;
        }
        @Override
        public Object newArray(int length){
            return new int[length];
        }
        @Override
        public void callAddLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.IntInput<?>)collection;
            if(functionCallType.boxed){
                cast.addLast((Integer)inputVal);
            }else {
                cast.addLast((int)inputVal);
            }
        }
        @Override
        public void callAddFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.IntInput<?>)collection;
            if(functionCallType.boxed){
                cast.addFirst((Integer)inputVal);
            }else {
                cast.addFirst((int)inputVal);
            }
        }
        @Override
        public boolean callOffer(Object inputVal,OmniQueue<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniQueue.IntInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offer((Integer)inputVal);
            }else {
                result=cast.offer((int)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.IntInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerFirst((Integer)inputVal);
            }else {
                result=cast.offerFirst((int)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.IntInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerLast((Integer)inputVal);
            }else {
                result=cast.offerLast((int)inputVal);
            }
            return result;
        }
      @Override public Object callPop(OmniStack<?> collection) {
        return ((OmniStack.IntOutput<?>)collection).popInt();
        }
      @Override public Object callPeek(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.IntOutput<?>)collection).peekInt();
      }
      @Override public Object callPoll(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.IntOutput<?>)collection).pollInt();
      }
      @Override
      public void callPush(Object inputVal,OmniStack<?> collection,
              FunctionCallType functionCallType){
        var cast=(OmniStack.IntInput<?>)collection;
        if(functionCallType.boxed){
            cast.push((Integer)inputVal);
        }else {
            cast.push((int)inputVal);
        }
      }
        @Override
        public Object convertVal(boolean val){
            return (int)TypeUtil.castToByte(val);
        }
        @Override
        public Object convertVal(byte val){
            return (int)val;
        }
        @Override
        public Object convertVal(char val){
            return (int)val;
        }
        @Override
        public Object convertVal(short val){
            return (int)val;
        }
        @Override
        public Object convertVal(int val){
            return (int)val;
        }
        @Override
        public Object convertVal(long val){
            int v;
            if((v=(int)val) == val){
                return v;
            }
            throw super.cannotBeConverted(val,LONG);
        }
        @Override
        public Object convertValUnchecked(long val){
            return (int)val;
        }
        @Override
        public Object convertVal(float val){
            int v;
            if((double)(v=(int)val) == (double)val){
                return v;
            }
            throw super.cannotBeConverted(val,FLOAT);
        }
        @Override
        public Object convertVal(double val){
            int v;
            if((v=(int)val) == val){
                return v;
            }
            throw super.cannotBeConverted(val,DOUBLE);
        }
        @Override public Object verifyToArray(MonitoredCollection<?> monitoredCollection){
            var cast=(OmniCollection.IntOutput<?>)monitoredCollection.getCollection();
            int[] result;
            try{
                result=cast.toIntArray();
            }finally{
                monitoredCollection.verifyCollectionState();
            }
            monitoredCollection.verifyArrayIsCopy(result);
            var itr=cast.iterator();
            for(int i=0,bound=result.length;i<bound;++i) {
                Assertions.assertEquals(itr.nextInt(),result[i]);
            }
            Assertions.assertFalse(itr.hasNext());
            if(result.length==0) {
                Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,result);
            }
            return result;
        }
        @Override public Object callNext(OmniIterator<?> itr){
            return ((OmniIterator.IntOutput<?>)itr).nextInt();
        }
        @Override public Object callPrevious(OmniListIterator<?> itr){
            return ((OmniListIterator.IntOutput<?>)itr).previousInt();
        }
        @Override
        public boolean callAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniCollection.IntInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.add((Integer)inputVal);
            }else {
                result=cast.add((int)inputVal);
            }
            return result;
        }
        @Override
        public void callAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.IntInput<?>)collection).add(index,(Integer)inputVal);
            }else{
                ((OmniList.IntInput<?>)collection).add(index,(int)inputVal);
            }
        }
        @Override
        public void callAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.IntInput<?>)itr).add((Integer)inputVal);
            }else{
                ((OmniListIterator.IntInput<?>)itr).add((int)inputVal);
            }
        }
        @Override
        public void callSet(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.IntInput<?>)itr).set((Integer)inputVal);
            }else{
                ((OmniListIterator.IntInput<?>)itr).set((int)inputVal);
            }
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.contains((Integer)inputVal);
            }else {
                return collection.contains((int)inputVal);
            }
        }
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeVal((Integer)inputVal);
            }else {
                return collection.removeVal((int)inputVal);
            }
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeFirstOccurrence((Integer)inputVal);
            }else {
                return collection.removeFirstOccurrence((int)inputVal);
            }
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeLastOccurrence((Integer)inputVal);
            }else {
                return collection.removeLastOccurrence((int)inputVal);
            }
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.indexOf((Integer)inputVal);
            }else {
                return collection.indexOf((int)inputVal);
            }
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.lastIndexOf((Integer)inputVal);
            }else {
                return collection.lastIndexOf((int)inputVal);
            }
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.search((Integer)inputVal);
            }else {
                return collection.search((int)inputVal);
            }
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            if(itr.hasNext()) {
                var castItr=(OmniIterator.OfInt)itr;
                char[] buffer=new char[11];
                for(;;) {
                    for(int i=0,valLength=ToStringUtil.getStringInt(castItr.nextInt(),buffer,0);i<valLength;++i) {
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()) {
                        break;
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset+1,result.length());
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection,String testName){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            int size=collection.size();
            System.out.print("Running test " + testName + " 0%");
            int nextPercent=1;
            int threshold=(int)Math.ceil(size / 100.0);
            int numCompleted=0;
            if(itr.hasNext()){
                var castItr=(OmniIterator.OfInt)itr;
                char[] buffer=new char[11];
                for(;;){
                    for(int i=0,valLength=ToStringUtil.getStringInt(castItr.nextInt(),buffer,0);i < valLength;++i){
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()){
                        break;
                    }
                    if(++numCompleted >= threshold){
                        incrementProgressBar(nextPercent);
                        threshold=(int)Math.ceil(size / 100.0 * ++nextPercent);
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            finalizeProgressBar(nextPercent);
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset + 1,result.length());
        }
        @Override
        public int getMaxInt(){
            return Integer.MAX_VALUE;
        }
        @Override
        public int getMinInt(){
            return Integer.MIN_VALUE;
        }
        @Override
        public long getMaxLong(){
            return Integer.MAX_VALUE;
        }
        @Override
        public long getMinLong(){
            return Integer.MIN_VALUE;
        }
        @Override
        public Object callGet(int index,OmniList<?> collection){
            return ((OmniList.IntOutput<?>)collection).getInt(index);
            }
        @Override
        public void callPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.IntInput<?>)collection).put(index,(Integer)inputVal);
            }else{
                ((OmniList.IntInput<?>)collection).put(index,(int)inputVal);
            }
        }
        @Override
        public Object callSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfInt)collection).set(index,(Integer)inputVal);
            }else{
                return ((OmniList.OfInt)collection).set(index,(int)inputVal);
            }
        }
        @Override
        public Object callRemoveAt(int index,OmniList<?> collection){
            return ((OmniList.IntOutput<?>)collection).removeIntAt(index);
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callStableSort(MonitoredComparator sorter,OmniList<?> collection,FunctionCallType functionCallType){
            if(sorter==null) {
                if(functionCallType.boxed){
                    ((OmniList.OfInt)collection).sort((Comparator<? super Integer>)sorter);
                }else{
                    ((OmniList.OfInt)collection).sort((IntComparator)sorter);
                }
            }else {
                if(functionCallType.boxed){
                    ((OmniList.OfInt)collection).sort((Comparator<? super Integer>)sorter::compare);
                }else{
                    ((OmniList.OfInt)collection).sort((IntComparator)sorter::compare);
                }
            }
            
        }
        @Override
        public void callUnstableSort(MonitoredComparator sorter,OmniList<?> collection){
            if(sorter==null) {
                ((OmniList.OfInt)collection).unstableSort((IntComparator)sorter);
            }else {
                ((OmniList.OfInt)collection).unstableSort(sorter::compare);
            }
        }
        @Override
        public Object callElement(OmniQueue<?> collection){
            return ((OmniQueue.IntOutput<?>)collection).intElement();
        }
        @Override
        public Object callRemove(OmniQueue<?> collection){
            return ((OmniQueue.IntOutput<?>)collection).removeInt();
            }
        @Override
        public Object callPeekFirst(OmniDeque<?> collection){
            return ((OmniDeque.IntOutput<?>)collection).peekFirstInt();
            }
        @Override
        public Object callPeekLast(OmniDeque<?> collection){
            return ((OmniDeque.IntOutput<?>)collection).peekLastInt();
            }
        @Override
        public Object callPollFirst(OmniDeque<?> collection){
            return ((OmniDeque.IntOutput<?>)collection).pollFirstInt();
            }
        @Override
        public Object callPollLast(OmniDeque<?> collection){
            return ((OmniDeque.IntOutput<?>)collection).pollLastInt();
            }
        @Override
        public Object callGetFirst(OmniDeque<?> collection){
            return ((OmniDeque.IntOutput<?>)collection).getFirstInt();
            }
        @Override
        public Object callGetLast(OmniDeque<?> collection){
            return ((OmniDeque.IntOutput<?>)collection).getLastInt();
            }
        @Override
        public Object callRemoveFirst(OmniDeque<?> collection){
            return ((OmniDeque.IntOutput<?>)collection).removeFirstInt();
            }
        @Override
        public Object callRemoveLast(OmniDeque<?> collection){
            return ((OmniDeque.IntOutput<?>)collection).removeLastInt();
            }
        @Override
        public void storyInArray(int index,Object arrayOfThisType,Object inputValOfThisType){
            ((int[])arrayOfThisType)[index]=(int)inputValOfThisType;
        }
        @Override
        public IntPredicate getArrayIndexSearcher(Object arr,Object inputVal,DataType inputType){
            int inputCast;
            switch(inputType){
            case BOOLEAN:
              inputCast=(boolean)inputVal?1:0;
              break;
            case BYTE:
              inputCast=(byte)inputVal;
              break;
            case CHAR:
              inputCast=(char)inputVal;
              break;
            case SHORT:
              inputCast=(short)inputVal;
              break;
            case INT:
              inputCast=(int)inputVal;
              break;
            case LONG:
              inputCast=(int)(long)inputVal;
              break;
            case FLOAT:
              inputCast=(int)(float)inputVal;
              break;
            case DOUBLE:
              inputCast=(int)(double)inputVal;
              break;
            default:
              throw inputType.invalid();
            }
            final var arrCast=(int[])arr;
            return index->arrCast[index]==inputCast;
        }
    },
    LONG("LONG","BOOLEAN,BYTE,CHAR,SHORT,INT,LONG","LONG,FLOAT,DOUBLE,REF",true,false,true,Long.class,long.class,
            Long[].class,long[].class,"Long","Long",LongPredicate.class,
            LongConsumer.class,LongComparator.class,LongUnaryOperator.class,Long.MIN_VALUE,"removeLongAt",long.class,"applyAsLong","compare",long.class,"longElement",
            OmniArray.MAX_ARR_SIZE / 22,OmniArray.OfLong.DEFAULT_ARR){
        @Override
        public Object getFromArray(int index,Object arrayOfThisType){
            return ((long[])arrayOfThisType)[index];
        }
        @Override
        public Object newArray(int length){
            return new long[length];
        }
        @Override public int arrayLength(Object arr) {
            return ((long[])arr).length;
        }
        @Override
        public void callAddLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.LongInput<?>)collection;
            if(functionCallType.boxed){
                cast.addLast((Long)inputVal);
            }else {
                cast.addLast((long)inputVal);
            }
        }
        @Override
        public void callAddFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.LongInput<?>)collection;
            if(functionCallType.boxed){
                cast.addFirst((Long)inputVal);
            }else {
                cast.addFirst((long)inputVal);
            }
        }
        @Override
        public boolean callOffer(Object inputVal,OmniQueue<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniQueue.LongInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offer((Long)inputVal);
            }else {
                result=cast.offer((long)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.LongInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerFirst((Long)inputVal);
            }else {
                result=cast.offerFirst((long)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.LongInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerLast((Long)inputVal);
            }else {
                result=cast.offerLast((long)inputVal);
            }
            return result;
        }
      @Override public Object callPop(OmniStack<?> collection) {
        return ((OmniStack.LongOutput<?>)collection).popLong();
        }
      @Override public Object callPeek(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.LongOutput<?>)collection).peekLong();
      }
      @Override public Object callPoll(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.LongOutput<?>)collection).pollLong();
      }
      @Override
      public void callPush(Object inputVal,OmniStack<?> collection,
              FunctionCallType functionCallType){
        var cast=(OmniStack.LongInput<?>)collection;
        if(functionCallType.boxed){
            cast.push((Long)inputVal);
        }else {
            cast.push((long)inputVal);
        }
      }
        @Override
        public Object convertVal(boolean val){
            return TypeUtil.castToLong(val);
        }
        @Override
        public Object convertVal(byte val){
            return (long)val;
        }
        @Override
        public Object convertVal(char val){
            return (long)val;
        }
        @Override
        public Object convertVal(short val){
            return (long)val;
        }
        @Override
        public Object convertVal(int val){
            return (long)val;
        }
        @Override
        public Object convertVal(long val){
            return (long)val;
        }
        @Override
        public Object convertVal(float val){
            long v;
            if(TypeUtil.floatEquals(val,v=(long)val)){
                return v;
            }
            throw super.cannotBeConverted(val,FLOAT);
        }
        @Override
        public Object convertVal(double val){
            long v;
            if(TypeUtil.doubleEquals(val,v=(long)val)){
                return v;
            }
            throw super.cannotBeConverted(val,DOUBLE);
        }
        @Override public Object verifyToArray(MonitoredCollection<?> monitoredCollection){
            var cast=(OmniCollection.LongOutput<?>)monitoredCollection.getCollection();
            long[] result;
            try{
                result=cast.toLongArray();
            }finally{
                monitoredCollection.verifyCollectionState();
            }
            monitoredCollection.verifyArrayIsCopy(result);
            var itr=cast.iterator();
            for(int i=0,bound=result.length;i<bound;++i) {
                Assertions.assertEquals(itr.nextLong(),result[i]);
            }
            Assertions.assertFalse(itr.hasNext());
            if(result.length==0) {
                Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,result);
            }
            return result;
        }
        @Override public Object callNext(OmniIterator<?> itr){
            return ((OmniIterator.LongOutput<?>)itr).nextLong();
        }
        @Override public Object callPrevious(OmniListIterator<?> itr){
            return ((OmniListIterator.LongOutput<?>)itr).previousLong();
        }
        @Override
        public boolean callAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniCollection.LongInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.add((Long)inputVal);
            }else {
                result=cast.add((long)inputVal);
            }
            return result;
        }
        @Override
        public void callAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.LongInput<?>)collection).add(index,(Long)inputVal);
            }else{
                ((OmniList.LongInput<?>)collection).add(index,(long)inputVal);
            }
        }
        @Override
        public void callAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.LongInput<?>)itr).add((Long)inputVal);
            }else{
                ((OmniListIterator.LongInput<?>)itr).add((long)inputVal);
            }
        }
        @Override
        public void callSet(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.LongInput<?>)itr).set((Long)inputVal);
            }else{
                ((OmniListIterator.LongInput<?>)itr).set((long)inputVal);
            }
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.contains((Long)inputVal);
            }else {
                return collection.contains((long)inputVal);
            }
        }
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeVal((Long)inputVal);
            }else {
                return collection.removeVal((long)inputVal);
            }
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeFirstOccurrence((Long)inputVal);
            }else {
                return collection.removeFirstOccurrence((long)inputVal);
            }
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeLastOccurrence((Long)inputVal);
            }else {
                return collection.removeLastOccurrence((long)inputVal);
            }
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.indexOf((Long)inputVal);
            }else {
                return collection.indexOf((long)inputVal);
            }
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.lastIndexOf((Long)inputVal);
            }else {
                return collection.lastIndexOf((long)inputVal);
            }
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.search((Long)inputVal);
            }else {
                return collection.search((long)inputVal);
            }
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            if(itr.hasNext()) {
                var castItr=(OmniIterator.OfLong)itr;
                char[] buffer=new char[20];
                for(;;) {
                    for(int i=0,valLength=ToStringUtil.getStringLong(castItr.nextLong(),buffer,0);i<valLength;++i) {
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()) {
                        break;
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset+1,result.length());
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection,String testName){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            int size=collection.size();
            System.out.print("Running test " + testName + " 0%");
            int nextPercent=1;
            int threshold=(int)Math.ceil(size / 100.0);
            int numCompleted=0;
            if(itr.hasNext()){
                var castItr=(OmniIterator.OfLong)itr;
                char[] buffer=new char[20];
                for(;;){
                    for(int i=0,valLength=ToStringUtil.getStringLong(castItr.nextLong(),buffer,0);i < valLength;++i){
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()){
                        break;
                    }
                    if(++numCompleted >= threshold){
                        incrementProgressBar(nextPercent);
                        threshold=(int)Math.ceil(size / 100.0 * ++nextPercent);
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            finalizeProgressBar(nextPercent);
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset + 1,result.length());
        }
        @Override
        public int getMaxInt(){
            return Integer.MAX_VALUE;
        }
        @Override
        public int getMinInt(){
            return Integer.MIN_VALUE;
        }
        @Override
        public long getMaxLong(){
            return Long.MAX_VALUE;
        }
        @Override
        public long getMinLong(){
            return Long.MIN_VALUE;
        }
        @Override
        public Object callGet(int index,OmniList<?> collection){
            return ((OmniList.LongOutput<?>)collection).getLong(index);
            }
        @Override
        public void callPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.LongInput<?>)collection).put(index,(Long)inputVal);
            }else{
                ((OmniList.LongInput<?>)collection).put(index,(long)inputVal);
            }
        }
        @Override
        public Object callSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfLong)collection).set(index,(Long)inputVal);
            }else{
                return ((OmniList.OfLong)collection).set(index,(long)inputVal);
            }
        }
        @Override
        public Object callRemoveAt(int index,OmniList<?> collection){
            return ((OmniList.LongOutput<?>)collection).removeLongAt(index);
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callStableSort(MonitoredComparator sorter,OmniList<?> collection,FunctionCallType functionCallType){
            if(sorter==null) {
                if(functionCallType.boxed){
                    ((OmniList.OfLong)collection).sort((Comparator<? super Long>)sorter);
                }else{
                    ((OmniList.OfLong)collection).sort((LongComparator)sorter);
                }
            }else {
                if(functionCallType.boxed){
                    ((OmniList.OfLong)collection).sort((Comparator<? super Long>)sorter::compare);
                }else{
                    ((OmniList.OfLong)collection).sort((LongComparator)sorter::compare);
                }
            }
            
        }
        @Override
        public void callUnstableSort(MonitoredComparator sorter,OmniList<?> collection){
            if(sorter==null) {
                ((OmniList.OfLong)collection).unstableSort((LongComparator)sorter);
            }else {
                ((OmniList.OfLong)collection).unstableSort(sorter::compare);
            }
        }
        @Override
        public Object callElement(OmniQueue<?> collection){
            return ((OmniQueue.LongOutput<?>)collection).longElement();
            }
        @Override
        public Object callRemove(OmniQueue<?> collection){
            return ((OmniQueue.LongOutput<?>)collection).removeLong();
            }
        @Override
        public Object callPeekFirst(OmniDeque<?> collection){
            return ((OmniDeque.LongOutput<?>)collection).peekFirstLong();
            }
        @Override
        public Object callPeekLast(OmniDeque<?> collection){
            return ((OmniDeque.LongOutput<?>)collection).peekLastLong();
            }
        @Override
        public Object callPollFirst(OmniDeque<?> collection){
            return ((OmniDeque.LongOutput<?>)collection).pollFirstLong();
            }
        @Override
        public Object callPollLast(OmniDeque<?> collection){
            return ((OmniDeque.LongOutput<?>)collection).pollLastLong();
            }
        @Override
        public Object callGetFirst(OmniDeque<?> collection){
            return ((OmniDeque.LongOutput<?>)collection).getFirstLong();
            }
        @Override
        public Object callGetLast(OmniDeque<?> collection){
            return ((OmniDeque.LongOutput<?>)collection).getLastLong();
            }
        @Override
        public Object callRemoveFirst(OmniDeque<?> collection){
            return ((OmniDeque.LongOutput<?>)collection).removeFirstLong();
            }
        @Override
        public Object callRemoveLast(OmniDeque<?> collection){
            return ((OmniDeque.LongOutput<?>)collection).removeLastLong();
            }
        @Override
        public void storyInArray(int index,Object arrayOfThisType,Object inputValOfThisType){
            ((long[])arrayOfThisType)[index]=(long)inputValOfThisType;
        }
        @Override
        public IntPredicate getArrayIndexSearcher(Object arr,Object inputVal,DataType inputType){
             long inputCast;
                switch(inputType){
                case BOOLEAN:
                  inputCast=(boolean)inputVal?1L:0L;
                  break;
                case BYTE:
                  inputCast=(byte)inputVal;
                  break;
                case CHAR:
                  inputCast=(char)inputVal;
                  break;
                case SHORT:
                  inputCast=(short)inputVal;
                  break;
                case INT:
                  inputCast=(int)inputVal;
                  break;
                case LONG:
                  inputCast=(long)inputVal;
                  break;
                case FLOAT:
                  inputCast=(long)(float)inputVal;
                  break;
                case DOUBLE:
                  inputCast=(long)(double)inputVal;
                  break;
                default:
                  throw inputType.invalid();
                }
                final var arrCast=(long[])arr;
                return index->arrCast[index]==inputCast;
        }
    },
    FLOAT("FLOAT","BOOLEAN,BYTE,CHAR,SHORT,INT,LONG,FLOAT","FLOAT,DOUBLE,REF",false,true,true,Float.class,float.class,
            Float[].class,float[].class,"Float","Float",FloatPredicate.class,
            FloatConsumer.class,FloatComparator.class,FloatUnaryOperator.class,Float.NaN,"removeFloatAt",int.class,"applyAsFloat","compare",float.class,"floatElement",
            OmniArray.MAX_ARR_SIZE / 17,OmniArray.OfFloat.DEFAULT_ARR){
        @Override
        public Object getFromArray(int index,Object arrayOfThisType){
            return ((float[])arrayOfThisType)[index];
        }
        @Override
        public Object newArray(int length){
            return new float[length];
        }
        @Override public int arrayLength(Object arr) {
            return ((float[])arr).length;
        }
        @Override
        public void callAddLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.FloatInput<?>)collection;
            if(functionCallType.boxed){
                cast.addLast((Float)inputVal);
            }else {
                cast.addLast((float)inputVal);
            }
        }
        @Override
        public void callAddFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.FloatInput<?>)collection;
            if(functionCallType.boxed){
                cast.addFirst((Float)inputVal);
            }else {
                cast.addFirst((float)inputVal);
            }
        }
        @Override
        public boolean callOffer(Object inputVal,OmniQueue<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniQueue.FloatInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offer((Float)inputVal);
            }else {
                result=cast.offer((float)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.FloatInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerFirst((Float)inputVal);
            }else {
                result=cast.offerFirst((float)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.FloatInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.offerLast((Float)inputVal);
            }else {
                result=cast.offerLast((float)inputVal);
            }
            return result;
        }
      @Override public Object callPop(OmniStack<?> collection) {
        return ((OmniStack.FloatOutput<?>)collection).popFloat();
        }
      @Override public Object callPeek(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.FloatOutput<?>)collection).peekFloat();
      }
      @Override public Object callPoll(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.FloatOutput<?>)collection).pollFloat();
      }
      @Override
      public void callPush(Object inputVal,OmniStack<?> collection,
              FunctionCallType functionCallType){
        var cast=(OmniStack.FloatInput<?>)collection;
        if(functionCallType.boxed){
            cast.push((Float)inputVal);
        }else {
            cast.push((float)inputVal);
        }
      }
        @Override
        public void verifyMASSIVEToString(String result,int seqSize,String testName){
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            Assertions.assertEquals('0',result.charAt(++offset));
            Assertions.assertEquals('.',result.charAt(++offset));
            Assertions.assertEquals('0',result.charAt(++offset));
            ++offset;
            int numBatches=TestExecutorService.getNumWorkers();
            int batchSize=(seqSize - 1) / numBatches * 5;
            for(int batchCount=0;batchCount < numBatches;++batchCount){
                final int batchOffset=offset;
                final int batchBound=batchCount == numBatches?result.length() - 1:offset + batchSize;
                TestExecutorService.submitTest(()->{
                    for(int i=batchOffset;i < batchBound;++i){
                        Assertions.assertEquals(',',result.charAt(i));
                        Assertions.assertEquals(' ',result.charAt(++i));
                        Assertions.assertEquals('0',result.charAt(++i));
                        Assertions.assertEquals('.',result.charAt(++i));
                        Assertions.assertEquals('0',result.charAt(++i));
                    }
                });
                offset=batchBound;
            }
            Assertions.assertEquals(']',result.charAt(result.length() - 1));
            TestExecutorService.completeAllTests(testName);
        }
        @Override
        public Object convertVal(boolean val){
            return TypeUtil.castToFloat(val);
        }
        @Override
        public Object convertVal(byte val){
            return (float)val;
        }
        @Override
        public Object convertVal(char val){
            return (float)val;
        }
        @Override
        public Object convertVal(short val){
            return (float)val;
        }
        @Override
        public Object convertVal(int val){
            if(TypeUtil.checkCastToFloat(val)){
                return (float)val;
            }
            throw super.cannotBeConverted(val,INT);
        }
        @Override
        public Object convertValUnchecked(int val){
            return (float)val;
        }
        @Override
        public Object convertVal(long val){
            if(TypeUtil.checkCastToFloat(val)){
                return (float)val;
            }
            throw super.cannotBeConverted(val,LONG);
        }
        @Override
        public Object convertValUnchecked(long val){
            return (float)val;
        }
        @Override
        public Object convertVal(float val){
            return val;
        }
        @Override
        public Object convertVal(double val){
            float v;
            if((v=(float)val) == val || v != v){
                return v;
            }
            throw super.cannotBeConverted(val,DOUBLE);
        }
        @Override public Object verifyToArray(MonitoredCollection<?> monitoredCollection){
            var cast=(OmniCollection.FloatOutput<?>)monitoredCollection.getCollection();
            float[] result;
            try{
                result=cast.toFloatArray();
            }finally{
                monitoredCollection.verifyCollectionState();
            }
            monitoredCollection.verifyArrayIsCopy(result);
            var itr=cast.iterator();
            for(int i=0,bound=result.length;i<bound;++i) {
                Assertions.assertEquals(itr.nextFloat(),result[i]);
            }
            Assertions.assertFalse(itr.hasNext());
            if(result.length==0) {
                Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,result);
            }
            return result;
        }
        @Override public Object callNext(OmniIterator<?> itr){
            return ((OmniIterator.FloatOutput<?>)itr).nextFloat();
        }
        @Override public Object callPrevious(OmniListIterator<?> itr){
            return ((OmniListIterator.FloatOutput<?>)itr).previousFloat();
        }
        @Override
        public boolean callAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniCollection.FloatInput<?>)collection;
            if(functionCallType.boxed){
                result=cast.add((Float)inputVal);
            }else {
                result=cast.add((float)inputVal);
            }
            return result;
        }
        @Override
        public void callAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.FloatInput<?>)collection).add(index,(Float)inputVal);
            }else{
                ((OmniList.FloatInput<?>)collection).add(index,(float)inputVal);
            }
        }
        @Override
        public void callAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.FloatInput<?>)itr).add((Float)inputVal);
            }else{
                ((OmniListIterator.FloatInput<?>)itr).add((float)inputVal);
            }
        }
        @Override
        public void callSet(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.FloatInput<?>)itr).set((Float)inputVal);
            }else{
                ((OmniListIterator.FloatInput<?>)itr).set((float)inputVal);
            }
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.contains((Float)inputVal);
            }else {
                return collection.contains((float)inputVal);
            }
        }
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeVal((Float)inputVal);
            }else {
                return collection.removeVal((float)inputVal);
            }
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeFirstOccurrence((Float)inputVal);
            }else {
                return collection.removeFirstOccurrence((float)inputVal);
            }
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeLastOccurrence((Float)inputVal);
            }else {
                return collection.removeLastOccurrence((float)inputVal);
            }
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.indexOf((Float)inputVal);
            }else {
                return collection.indexOf((float)inputVal);
            }
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.lastIndexOf((Float)inputVal);
            }else {
                return collection.lastIndexOf((float)inputVal);
            }
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.search((Float)inputVal);
            }else {
                return collection.search((float)inputVal);
            }
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            if(itr.hasNext()) {
                var castItr=(OmniIterator.OfFloat)itr;
                char[] buffer=new char[15];
                for(;;) {
                    for(int i=0,valLength=ToStringUtil.getStringFloat(castItr.nextFloat(),buffer,0);i<valLength;++i) {
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()) {
                        break;
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset+1,result.length());
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection,String testName){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            int size=collection.size();
            System.out.print("Running test " + testName + " 0%");
            int nextPercent=1;
            int threshold=(int)Math.ceil(size / 100.0);
            int numCompleted=0;
            if(itr.hasNext()){
                var castItr=(OmniIterator.OfFloat)itr;
                char[] buffer=new char[15];
                for(;;){
                    for(int i=0,valLength=ToStringUtil.getStringFloat(castItr.nextFloat(),buffer,0);i < valLength;++i){
                        Assertions.assertEquals(buffer[i],result.charAt(++offset));
                    }
                    if(!itr.hasNext()){
                        break;
                    }
                    if(++numCompleted >= threshold){
                        incrementProgressBar(nextPercent);
                        threshold=(int)Math.ceil(size / 100.0 * ++nextPercent);
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            finalizeProgressBar(nextPercent);
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset + 1,result.length());
        }
        @Override
        public Object callGet(int index,OmniList<?> collection){
            return ((OmniList.FloatOutput<?>)collection).getFloat(index);
            }
        @Override
        public void callPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.FloatInput<?>)collection).put(index,(Float)inputVal);
            }else{
                ((OmniList.FloatInput<?>)collection).put(index,(float)inputVal);
            }
        }
        @Override
        public Object callSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfFloat)collection).set(index,(Float)inputVal);
            }else{
                return ((OmniList.OfFloat)collection).set(index,(float)inputVal);
            }
        }
        @Override
        public Object callRemoveAt(int index,OmniList<?> collection){
            return ((OmniList.FloatOutput<?>)collection).removeFloatAt(index);
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callStableSort(MonitoredComparator sorter,OmniList<?> collection,FunctionCallType functionCallType){
            if(sorter==null) {
                if(functionCallType.boxed){
                    ((OmniList.OfFloat)collection).sort((Comparator<? super Float>)sorter);
                }else{
                    ((OmniList.OfFloat)collection).sort((FloatComparator)sorter);
                }
            }else {
                if(functionCallType.boxed){
                    ((OmniList.OfFloat)collection).sort((Comparator<? super Float>)sorter::compare);
                }else{
                    ((OmniList.OfFloat)collection).sort((FloatComparator)sorter::compare);
                }
            }
            
        }
        @Override
        public void callUnstableSort(MonitoredComparator sorter,OmniList<?> collection){
            if(sorter==null) {
                ((OmniList.OfFloat)collection).unstableSort((FloatComparator)sorter);
            }else {
                ((OmniList.OfFloat)collection).unstableSort(sorter::compare);
            }
        }
        @Override
        public Object callElement(OmniQueue<?> collection){
            return ((OmniQueue.FloatOutput<?>)collection).floatElement();
            }
        @Override
        public Object callRemove(OmniQueue<?> collection){
            return ((OmniQueue.FloatOutput<?>)collection).removeFloat();
            }
        @Override
        public Object callPeekFirst(OmniDeque<?> collection){
            return ((OmniDeque.FloatOutput<?>)collection).peekFirstFloat();
            }
        @Override
        public Object callPeekLast(OmniDeque<?> collection){
            return ((OmniDeque.FloatOutput<?>)collection).peekLastFloat();
            }
        @Override
        public Object callPollFirst(OmniDeque<?> collection){
            return ((OmniDeque.FloatOutput<?>)collection).pollFirstFloat();
            }
        @Override
        public Object callPollLast(OmniDeque<?> collection){
            return ((OmniDeque.FloatOutput<?>)collection).pollLastFloat();
            }
        @Override
        public Object callGetFirst(OmniDeque<?> collection){
            return ((OmniDeque.FloatOutput<?>)collection).getFirstFloat();
            }
        @Override
        public Object callGetLast(OmniDeque<?> collection){
            return ((OmniDeque.FloatOutput<?>)collection).getLastFloat();
            }
        @Override
        public Object callRemoveFirst(OmniDeque<?> collection){
            return ((OmniDeque.FloatOutput<?>)collection).removeFirstFloat();
            }
        @Override
        public Object callRemoveLast(OmniDeque<?> collection){
            return ((OmniDeque.FloatOutput<?>)collection).removeLastFloat();
            }
        @Override
        public void storyInArray(int index,Object arrayOfThisType,Object inputValOfThisType){
            ((float[])arrayOfThisType)[index]=(float)inputValOfThisType;
        }
        @Override
        public IntPredicate getArrayIndexSearcher(Object arr,Object inputVal,DataType inputType){
            float inputCast;
                switch(inputType){
                case BOOLEAN:
                  inputCast=(boolean)inputVal?1.0f:0.0f;
                  break;
                case BYTE:
                  inputCast=(byte)inputVal;
                  break;
                case CHAR:
                  inputCast=(char)inputVal;
                  break;
                case SHORT:
                  inputCast=(short)inputVal;
                  break;
                case INT:
                  inputCast=(int)inputVal;
                  break;
                case LONG:
                  inputCast=(long)inputVal;
                  break;
                case FLOAT:
                  inputCast=(float)inputVal;
                  break;
                case DOUBLE:
                  inputCast=(float)(double)inputVal;
                  break;
                default:
                  throw inputType.invalid();
                }
                final var arrCast=(float[])arr;
                if(inputCast == inputCast){
                    return index->arrCast[index]==inputCast;
                }else {
                    return index->{
                        float v;
                        return (v=arrCast[index])!=v;
                    };
                }
        }
    },
    DOUBLE("DOUBLE","BOOLEAN,BYTE,CHAR,SHORT,INT,LONG,FLOAT,DOUBLE","DOUBLE,REF",false,true,true,Double.class,
            double.class,Double[].class,double[].class,"Double","Double",
            DoublePredicate.class,DoubleConsumer.class,DoubleComparator.class,DoubleUnaryOperator.class,Double.NaN,
            "removeDoubleAt",long.class,"applyAsDouble","compare",
            double.class,"doubleElement",-1,OmniArray.OfDouble.DEFAULT_ARR){
        @Override
        public Object getFromArray(int index,Object arrayOfThisType){
            return ((double[])arrayOfThisType)[index];
        }
        @Override
        public Object newArray(int length){
            return new double[length];
        }
        @Override public int arrayLength(Object arr) {
            return ((double[])arr).length;
        }
      @Override public Object callPeek(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.DoubleOutput<?>)collection).peekDouble();
      }
      @Override public Object callPoll(PeekAndPollIfc<?> collection){
        return ((PeekAndPollIfc.DoubleOutput<?>)collection).pollDouble();
      }
        @Override public Object verifyToArray(MonitoredCollection<?> monitoredCollection){
            var cast=(OmniCollection.DoubleOutput<?>)monitoredCollection.getCollection();
            double[] result;
            try{
                result=cast.toDoubleArray();
            }finally{
                monitoredCollection.verifyCollectionState();
            }
            monitoredCollection.verifyArrayIsCopy(result);
            var itr=cast.iterator();
            for(int i=0,bound=result.length;i<bound;++i) {
                Assertions.assertEquals(itr.nextDouble(),result[i]);
            }
            Assertions.assertFalse(itr.hasNext());
            if(result.length==0) {
                Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,result);
            }
            return result;
        }
        @Override public Object callNext(OmniIterator<?> itr){
            return ((OmniIterator.DoubleOutput<?>)itr).nextDouble();
        }
        @Override public Object callPrevious(OmniListIterator<?> itr){
            return ((OmniListIterator.DoubleOutput<?>)itr).previousDouble();
        }
        @Override
        public boolean callAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniCollection.OfDouble)collection;
            if(functionCallType.boxed){
                result=cast.add((Double)inputVal);
            }else {
                result=cast.add((double)inputVal);
            }
            return result;
        }
        @Override
        public void callPush(Object inputVal,OmniStack<?> collection,
                FunctionCallType functionCallType){
          var cast=(OmniStack.OfDouble)collection;
          if(functionCallType.boxed){
              cast.push((Double)inputVal);
          }else {
              cast.push((double)inputVal);
          }
        }
        @Override
        public void callAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.OfDouble)collection).add(index,(Double)inputVal);
            }else{
                ((OmniList.OfDouble)collection).add(index,(double)inputVal);
            }
        }
        @Override
        public void callAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.OfDouble)itr).add((Double)inputVal);
            }else{
                ((OmniListIterator.OfDouble)itr).add((double)inputVal);
            }
        }
        @Override
        public void callSet(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.OfDouble)itr).set((Double)inputVal);
            }else{
                ((OmniListIterator.OfDouble)itr).set((double)inputVal);
            }
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.contains((Double)inputVal);
            }else {
                return collection.contains((double)inputVal);
            }
        }
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeVal((Double)inputVal);
            }else {
                return collection.removeVal((double)inputVal);
            }
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeFirstOccurrence((Double)inputVal);
            }else {
                return collection.removeFirstOccurrence((double)inputVal);
            }
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.removeLastOccurrence((Double)inputVal);
            }else {
                return collection.removeLastOccurrence((double)inputVal);
            }
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.indexOf((Double)inputVal);
            }else {
                return collection.indexOf((double)inputVal);
            }
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.lastIndexOf((Double)inputVal);
            }else {
                return collection.lastIndexOf((double)inputVal);
            }
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return collection.search((Double)inputVal);
            }else {
                return collection.search((double)inputVal);
            }
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            if(itr.hasNext()) {
                var castItr=(OmniIterator.OfDouble)itr;
                for(;;) {
                    String strVal;
                    for(int i=0,valLength=(strVal=Double.toString(castItr.nextDouble())).length();i<valLength;++i) {
                        Assertions.assertEquals(strVal.charAt(i),result.charAt(++offset));
                    }
                    if(!itr.hasNext()) {
                        break;
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset+1,result.length());
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection,String testName){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            int size=collection.size();
            System.out.print("Running test " + testName + " 0%");
            int nextPercent=1;
            int threshold=(int)Math.ceil(size / 100.0);
            int numCompleted=0;
            if(itr.hasNext()){
                var castItr=(OmniIterator.OfDouble)itr;
                for(;;){
                    String strVal;
                    for(int i=0,valLength=(strVal=Double.toString(castItr.nextDouble())).length();i < valLength;++i){
                        Assertions.assertEquals(strVal.charAt(i),result.charAt(++offset));
                    }
                    if(!itr.hasNext()){
                        break;
                    }
                    if(++numCompleted >= threshold){
                        incrementProgressBar(nextPercent);
                        threshold=(int)Math.ceil(size / 100.0 * ++nextPercent);
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            finalizeProgressBar(nextPercent);
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset + 1,result.length());
        }
        @Override
        public Object convertVal(boolean val){
            return TypeUtil.castToDouble(val);
        }
        @Override
        public Object convertVal(byte val){
            return (double)val;
        }
        @Override
        public Object convertVal(char val){
            return (double)val;
        }
        @Override
        public Object convertVal(short val){
            return (double)val;
        }
        @Override
        public Object convertVal(int val){
            return (double)val;
        }
        @Override
        public Object convertVal(long val){
            if(TypeUtil.checkCastToDouble(val)){
                return (double)val;
            }
            throw super.cannotBeConverted(val,LONG);
        }
        @Override
        public Object convertValUnchecked(long val){
            return (double)val;
        }
        @Override
        public Object convertVal(float val){
            return (double)val;
        }
        @Override
        public Object convertVal(double val){
            return val;
        }
        @Override
        public Object callGet(int index,OmniList<?> collection){
            return ((OmniList.DoubleOutput<?>)collection).getDouble(index);
        }
        @Override public Object callPop(OmniStack<?> collection) {
          return ((OmniStack.DoubleOutput<?>)collection).popDouble();
          }
        @Override
        public void callPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.OfDouble)collection).put(index,(Double)inputVal);
            }else{
                ((OmniList.OfDouble)collection).put(index,(double)inputVal);
            }
        }
        @Override
        public Object callSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfDouble)collection).set(index,(Double)inputVal);
            }else{
                return ((OmniList.OfDouble)collection).set(index,(double)inputVal);
            }
        }
        @Override
        public void verifyMASSIVEToString(String result,int seqSize,String testName){
            throw new UnsupportedOperationException();
        }
        @Override
        public Object callRemoveAt(int index,OmniList<?> collection){
            return ((OmniList.DoubleOutput<?>)collection).removeDoubleAt(index);
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callStableSort(MonitoredComparator sorter,OmniList<?> collection,FunctionCallType functionCallType){
            if(sorter==null) {
                if(functionCallType.boxed){
                    ((OmniList.OfDouble)collection).sort((Comparator<? super Double>)sorter);
                }else{
                    ((OmniList.OfDouble)collection).sort((DoubleComparator)sorter);
                }
            }else {
                if(functionCallType.boxed){
                    ((OmniList.OfDouble)collection).sort((Comparator<? super Double>)sorter::compare);
                }else{
                    ((OmniList.OfDouble)collection).sort((DoubleComparator)sorter::compare);
                }
            }
            
        }
        @Override
        public void callUnstableSort(MonitoredComparator sorter,OmniList<?> collection){
            if(sorter==null) {
                ((OmniList.OfDouble)collection).unstableSort((DoubleComparator)sorter);
            }else {
                ((OmniList.OfDouble)collection).unstableSort(sorter::compare);
            }
        }
        @Override
        public void callAddLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.OfDouble)collection;
            if(functionCallType.boxed){
                cast.addLast((Double)inputVal);
            }else {
                cast.addLast((double)inputVal);
            }
        }
        @Override
        public void callAddFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            var cast=(OmniDeque.OfDouble)collection;
            if(functionCallType.boxed){
                cast.addFirst((Double)inputVal);
            }else {
                cast.addFirst((double)inputVal);
            }
        }
        @Override
        public boolean callOffer(Object inputVal,OmniQueue<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniQueue.OfDouble)collection;
            if(functionCallType.boxed){
                result=cast.offer((Double)inputVal);
            }else {
                result=cast.offer((double)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.OfDouble)collection;
            if(functionCallType.boxed){
                result=cast.offerFirst((Double)inputVal);
            }else {
                result=cast.offerFirst((double)inputVal);
            }
            return result;
        }
        @Override
        public boolean callOfferLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            boolean result;
            var cast=(OmniDeque.OfDouble)collection;
            if(functionCallType.boxed){
                result=cast.offerLast((Double)inputVal);
            }else {
                result=cast.offerLast((double)inputVal);
            }
            return result;
        }
        @Override
        public Object callElement(OmniQueue<?> collection){
            return ((OmniQueue.DoubleOutput<?>)collection).doubleElement();
            }
        @Override
        public Object callRemove(OmniQueue<?> collection){
            return ((OmniQueue.DoubleOutput<?>)collection).removeDouble();
            }
        @Override
        public Object callPeekFirst(OmniDeque<?> collection){
            return ((OmniDeque.DoubleOutput<?>)collection).peekFirstDouble();
            }
        @Override
        public Object callPeekLast(OmniDeque<?> collection){
            return ((OmniDeque.DoubleOutput<?>)collection).peekLastDouble();
            }
        @Override
        public Object callPollFirst(OmniDeque<?> collection){
            return ((OmniDeque.DoubleOutput<?>)collection).pollFirstDouble();
            }
        @Override
        public Object callPollLast(OmniDeque<?> collection){
            return ((OmniDeque.DoubleOutput<?>)collection).pollLastDouble();
            }
        @Override
        public Object callGetFirst(OmniDeque<?> collection){
            return ((OmniDeque.DoubleOutput<?>)collection).getFirstDouble();
            }
        @Override
        public Object callGetLast(OmniDeque<?> collection){
            return ((OmniDeque.DoubleOutput<?>)collection).getLastDouble();
            }
        @Override
        public Object callRemoveFirst(OmniDeque<?> collection){
            return ((OmniDeque.DoubleOutput<?>)collection).removeFirstDouble();
            }
        @Override
        public Object callRemoveLast(OmniDeque<?> collection){
            return ((OmniDeque.DoubleOutput<?>)collection).removeLastDouble();
        }
        @Override
        public void storyInArray(int index,Object arrayOfThisType,Object inputValOfThisType){
            ((double[])arrayOfThisType)[index]=(double)inputValOfThisType;
        }
        @Override
        public IntPredicate getArrayIndexSearcher(Object arr,Object inputVal,DataType inputType){
            double inputCast;
            switch(inputType){
            case BOOLEAN:
              inputCast=(boolean)inputVal?1.0:0.0;
              break;
            case BYTE:
              inputCast=(byte)inputVal;
              break;
            case CHAR:
              inputCast=(char)inputVal;
              break;
            case SHORT:
              inputCast=(short)inputVal;
              break;
            case INT:
              inputCast=(int)inputVal;
              break;
            case LONG:
              inputCast=(long)inputVal;
              break;
            case FLOAT:
              inputCast=(float)inputVal;
              break;
            case DOUBLE:
              inputCast=(double)inputVal;
              break;
            default:
              throw inputType.invalid();
            }
            final var arrCast=(double[])arr;
            if(inputCast == inputCast){
                return index->arrCast[index]==inputCast;
            }else {
                return index->{
                    double v;
                    return (v=arrCast[index])!=v;
                };
            }
        }
    },
    REF("REF","REF","REF",false,false,false,null,Object.class,null,Object[].class,"Ref","",null,null,null,null,null,
            "remove",Object.class,"apply","compare",Comparable.class,"element",-1,OmniArray.OfRef.DEFAULT_ARR){
        @Override
        public Object getFromArray(int index,Object arrayOfThisType){
            return ((Object[])arrayOfThisType)[index];
        }
        @Override
        public Object newArray(int length){
            return new Object[length];
        }
        @Override public int arrayLength(Object arr) {
            return ((Object[])arr).length;
        }
      @Override public Object callPeek(PeekAndPollIfc<?> collection){
        return collection.peek();
      }
      @Override public Object callPoll(PeekAndPollIfc<?> collection){
        return collection.poll();
      }
      @Override public Object callPop(OmniStack<?> collection) {
        return collection.pop();
    }
        @Override public Object verifyToArray(MonitoredCollection<?> monitoredCollection){
            var collection=monitoredCollection.getCollection();
            Object[] result;
            try{
                result=collection.toArray();
            }finally{
                monitoredCollection.verifyCollectionState();
            }
            monitoredCollection.verifyArrayIsCopy(result);
            var itr=collection.iterator();
            DataType dataType=monitoredCollection.getDataType();
            if(dataType==DataType.REF) {
                for(int i=0,bound=result.length;i<bound;++i) {
                    Assertions.assertSame(itr.next(),result[i]);
                }
            }else {
                for(int i=0,bound=result.length;i<bound;++i) {
                    Assertions.assertEquals(itr.next(),result[i]);
                }
            }
            Assertions.assertFalse(itr.hasNext());
            if(result.length==0) {
                Object defaultArr;
                switch(dataType) {
                case BOOLEAN:
                    defaultArr=OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
                    break;
                case BYTE:
                    defaultArr=OmniArray.OfByte.DEFAULT_BOXED_ARR;
                    break;
                case CHAR:
                    defaultArr=OmniArray.OfChar.DEFAULT_BOXED_ARR;
                    break;
                case DOUBLE:
                    defaultArr=OmniArray.OfDouble.DEFAULT_BOXED_ARR;
                    break;
                case FLOAT:
                    defaultArr=OmniArray.OfFloat.DEFAULT_BOXED_ARR;
                    break;
                case INT:
                    defaultArr=OmniArray.OfInt.DEFAULT_BOXED_ARR;
                    break;
                case LONG:
                    defaultArr=OmniArray.OfLong.DEFAULT_BOXED_ARR;
                    break;
                case REF:
                    defaultArr=OmniArray.OfRef.DEFAULT_ARR;
                    break;
                case SHORT:
                    defaultArr=OmniArray.OfShort.DEFAULT_BOXED_ARR;
                    break;
                default:
                    throw dataType.invalid();
                }
                Assertions.assertSame(defaultArr,result);
            }
            return result;
        }
        @Override public Object callNext(OmniIterator<?> itr){
            return itr.next();
        }
        @Override public Object callPrevious(OmniListIterator<?> itr){
            return itr.previous();
        }
        @SuppressWarnings({"unchecked"})
        @Override
        public boolean callAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return ((OmniCollection.OfRef<Object>)collection).add(inputVal);
        }
        @SuppressWarnings({"unchecked"})
        @Override
        public void callPush(Object inputVal,OmniStack<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            ((OmniStack.OfRef<Object>)collection).push(inputVal);
        }
        @SuppressWarnings({"unchecked"})
        @Override
        public void callAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            ((OmniList.OfRef<Object>)collection).add(index,inputVal);
        }
        @SuppressWarnings({"unchecked"})
        @Override
        public void callAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            ((OmniListIterator.OfRef<Object>)itr).add(inputVal);
        }
        @SuppressWarnings({"unchecked"})
        @Override
        public void callSet(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            ((OmniListIterator.OfRef<Object>)itr).set(inputVal);
        }
        @Override
        public Object callGet(int index,OmniList<?> collection){
            return collection.get(index);
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return collection.contains(inputVal);
        }
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return collection.remove(inputVal);
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return collection.removeFirstOccurrence(inputVal);
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return collection.removeLastOccurrence(inputVal);
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return collection.indexOf(inputVal);
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return collection.lastIndexOf(inputVal);
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return collection.search(inputVal);
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            if(itr.hasNext()){
                for(;;){
                    String strVal;
                    for(int i=0,valLength=(strVal=String.valueOf(itr.next())).length();i < valLength;++i){
                        Assertions.assertEquals(strVal.charAt(i),result.charAt(++offset));
                    }
                    if(!itr.hasNext()){
                        break;
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset + 1,result.length());
        }
        @Override
        public void verifyToString(String result,OmniCollection<?> collection,String testName){
            var itr=collection.iterator();
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            int size=collection.size();
            System.out.print("Running test " + testName + " 0%");
            int nextPercent=1;
            int threshold=(int)Math.ceil(size / 100.0);
            int numCompleted=0;
            if(itr.hasNext()){
                for(;;){
                    String strVal;
                    for(int i=0,valLength=(strVal=String.valueOf(itr.next())).length();i < valLength;++i){
                        Assertions.assertEquals(strVal.charAt(i),result.charAt(++offset));
                    }
                    if(!itr.hasNext()){
                        break;
                    }
                    if(++numCompleted >= threshold){
                        incrementProgressBar(nextPercent);
                        threshold=(int)Math.ceil(size / 100.0 * ++nextPercent);
                    }
                    Assertions.assertEquals(',',result.charAt(++offset));
                    Assertions.assertEquals(' ',result.charAt(++offset));
                }
            }
            finalizeProgressBar(nextPercent);
            Assertions.assertEquals(']',result.charAt(++offset));
            Assertions.assertEquals(offset + 1,result.length());
        }
        @Override
        public Object convertVal(boolean val){
            return val;
        }
        @Override
        public Object convertVal(byte val){
            return val;
        }
        @Override
        public Object convertVal(char val){
            return val;
        }
        @Override
        public Object convertVal(short val){
            return val;
        }
        @Override
        public Object convertVal(int val){
            return val;
        }
        @Override
        public Object convertVal(long val){
            return val;
        }
        @Override
        public Object convertVal(float val){
            return val;
        }
        @Override
        public Object convertVal(double val){
            return val;
        }
        @Override
        public boolean isValidQueryVal(QueryVal queryVal){
            return true;
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw DataType.cannotBeBoxed();
            }
            ((OmniList.OfRef<Object>)collection).put(index,inputVal);
        }
        @SuppressWarnings("unchecked")
        @Override
        public Object callSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw DataType.cannotBeBoxed();
            }
            return ((OmniList.OfRef<Object>)collection).set(index,inputVal);
        }
        @Override
        public void verifyMASSIVEToString(String result,int seqSize,String testName){
            throw new UnsupportedOperationException();
        }
        @Override
        public Object callRemoveAt(int index,OmniList<?> collection){
            return collection.remove(index);
        }
        @Override
        @SuppressWarnings("unchecked")
        public void callStableSort(MonitoredComparator sorter,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw DataType.cannotBeBoxed();
            }
            if(sorter==null) {
                ((OmniList.OfRef<Object>)collection).sort((Comparator<Object>)sorter);
            }else {
                ((OmniList.OfRef<Object>)collection).sort(sorter::compare);
            }
            
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callUnstableSort(MonitoredComparator sorter,OmniList<?> collection){
            if(sorter==null) {
                ((OmniList.OfRef<Object>)collection).unstableSort((Comparator<Object>)sorter);
            }else {
                ((OmniList.OfRef<Object>)collection).unstableSort(sorter::compare);
            }
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callAddLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            ((OmniDeque.OfRef<Object>)collection).addLast(inputVal);
        }
        @SuppressWarnings("unchecked")
        @Override
        public void callAddFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            ((OmniDeque.OfRef<Object>)collection).addFirst(inputVal);
        }
        @SuppressWarnings("unchecked")
        @Override
        public boolean callOffer(Object inputVal,OmniQueue<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return ((OmniQueue.OfRef<Object>)collection).offer(inputVal);
        }
        @SuppressWarnings("unchecked")
        @Override
        public boolean callOfferFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return ((OmniDeque.OfRef<Object>)collection).offerFirst(inputVal);
        }
        @SuppressWarnings("unchecked")
        @Override
        public boolean callOfferLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return ((OmniDeque.OfRef<Object>)collection).offerLast(inputVal);
        }
        @Override
        public Object callElement(OmniQueue<?> collection){
            return collection.element();
            }
        @Override
        public Object callRemove(OmniQueue<?> collection){
            return collection.remove();
        }
        @Override
        public Object callPeekFirst(OmniDeque<?> collection){
            return collection.peekFirst();
        }
        @Override
        public Object callPeekLast(OmniDeque<?> collection){
            return collection.peekLast();
            }
        @Override
        public Object callPollFirst(OmniDeque<?> collection){
            return collection.pollFirst();
            }
        @Override
        public Object callPollLast(OmniDeque<?> collection){
            return collection.pollLast();
            }
        @Override
        public Object callGetFirst(OmniDeque<?> collection){
            return collection.getFirst();
            }
        @Override
        public Object callGetLast(OmniDeque<?> collection){
            return collection.getLast();
            }
        @Override
        public Object callRemoveFirst(OmniDeque<?> collection){
            return collection.removeFirst();
            }
        @Override
        public Object callRemoveLast(OmniDeque<?> collection){
            return collection.removeLast();
            }
        @Override
        public void storyInArray(int index,Object arrayOfThisType,Object inputValOfThisType){
            ((Object[])arrayOfThisType)[index]=inputValOfThisType;
        }
        @Override
        public IntPredicate getArrayIndexSearcher(Object arr,Object inputVal,DataType inputType){
            final var arrCast=(Object[])arr;
            if(inputVal==null) {
                return index->arrCast[index]==null;
            }else {
                return index->inputVal.equals(arrCast[index]);
            }
        }

    };
    private static void incrementProgressBar(int nextPercent){
        if(nextPercent % 10 == 0){
            System.out.print(nextPercent + "%");
        }else{
            System.out.print('.');
        }
    }
    private static void finalizeProgressBar(int nextPercent){
        while(nextPercent <= 100){
            incrementProgressBar(nextPercent++);
        }
        System.out.println(" Finished!");
    }
    public static UnsupportedOperationException cannotBeBoxed(){
        return new UnsupportedOperationException(DataType.REF + " cannot be boxed");
    }
    public final String name;
    private final String mayBeAddedTo;
    private final String validOutputTypes;
    
    public final boolean isIntegral;
    public final boolean isFloatingPoint;
    public final boolean isSigned;
    public final Class<?> boxedClass;
    public final Class<?> primitiveClass;
    public final Class<?> boxedArrayClass;
    public final Class<?> primitiveArrayClass;
    public final String classPrefix;
    public final String typeNameModifier;
    public final Class<?> predicateClass;
    public final Class<?> consumerClass;
    public final Class<?> comparatorClass;
    public final Class<?> unaryOperatorClass;
    public final Object defaultVal;
    public final String removeAtIndexMethodName;
    public final Class<?> queryParameterType;
    public final String applyMethodName;
    public final String compareMethodName;
    public final Class<?> comparableType;
    public final String elementMethodName;
    public final int massiveToStringThreshold;
    public final Object defaultArr;
    public final EnumSet<FunctionCallType> validFunctionCalls;
    @SuppressWarnings("rawtypes")
    public final Set<Class<? extends Collection>> validCollectionConstructorClasses;
    
    
    DataType(String name,String mayBeAddedTo,String validOutputTypes,boolean isIntegral,boolean isFloatingPoint,
            boolean isSigned,Class<?> boxedClass,Class<?> primitiveClass,
            Class<?> boxedArrayClass,Class<?> primitiveArrayClass,String classPrefix,String typeNameModifier,
            Class<?> predicateClass,Class<?> consumerClass,Class<?> comparatorClass,Class<?> unaryOperatorClass,
            Object defaultVal,String removeAtIndexMethodName,
            Class<?> queryParameterType,String applyMethodName,String compareMethodName,Class<?> comparableType,
            String elementMethodName,int massiveToStringThreshold,Object defaultArr){
        this.name=name;
        this.mayBeAddedTo=mayBeAddedTo;
        this.validOutputTypes=validOutputTypes;
        this.isIntegral=isIntegral;
        this.isFloatingPoint=isFloatingPoint;
        this.isSigned=isSigned;
        this.boxedClass=boxedClass;
        this.primitiveClass=primitiveClass;
        this.boxedArrayClass=boxedArrayClass;
        this.primitiveArrayClass=primitiveArrayClass;
        this.classPrefix=classPrefix;
        this.typeNameModifier=typeNameModifier;
        this.predicateClass=predicateClass;
        this.consumerClass=consumerClass;
        this.comparatorClass=comparatorClass;
        this.unaryOperatorClass=unaryOperatorClass;
        this.defaultVal=defaultVal;
        this.removeAtIndexMethodName=removeAtIndexMethodName;
        this.queryParameterType=queryParameterType;
        this.applyMethodName=applyMethodName;
        this.compareMethodName=compareMethodName;
        this.comparableType=comparableType;
        this.elementMethodName=elementMethodName;
        this.massiveToStringThreshold=massiveToStringThreshold;
        this.defaultArr=defaultArr;
        if("REF".equals(name)) {
            this.validFunctionCalls=EnumSet.of(FunctionCallType.Unboxed);
        }else {
            this.validFunctionCalls=EnumSet.allOf(FunctionCallType.class);
        }
        switch(name) {
        case "BOOLEAN":
            validCollectionConstructorClasses=Set.of(Collection.class,OmniCollection.OfRef.class,OmniCollection.OfBoolean.class);
            break;
        case "BYTE":
            validCollectionConstructorClasses=Set.of(Collection.class,OmniCollection.OfRef.class,OmniCollection.OfBoolean.class,
                    OmniCollection.OfByte.class,OmniCollection.ByteOutput.class);
            break;
        case "CHAR":
            validCollectionConstructorClasses=Set.of(Collection.class,OmniCollection.OfRef.class,OmniCollection.OfBoolean.class,
                    OmniCollection.OfChar.class,OmniCollection.CharOutput.class);
            break;
        case "SHORT":
            validCollectionConstructorClasses=Set.of(Collection.class,OmniCollection.OfRef.class,OmniCollection.OfBoolean.class,
                    OmniCollection.OfByte.class,
                    OmniCollection.OfShort.class,OmniCollection.ShortOutput.class);
            break;
        case "INT":
            validCollectionConstructorClasses=Set.of(Collection.class,OmniCollection.OfRef.class,OmniCollection.OfBoolean.class,
                    OmniCollection.OfByte.class,
                    OmniCollection.OfChar.class,
                    OmniCollection.OfShort.class,
                    OmniCollection.OfInt.class,OmniCollection.IntOutput.class);
            break;
        case "LONG":
            validCollectionConstructorClasses=Set.of(Collection.class,OmniCollection.OfRef.class,OmniCollection.OfBoolean.class,
                    OmniCollection.OfByte.class,
                    OmniCollection.OfChar.class,
                    OmniCollection.OfShort.class,
                    OmniCollection.OfInt.class,
                    OmniCollection.OfLong.class,OmniCollection.LongOutput.class);
            break;
        case "FLOAT":
            validCollectionConstructorClasses=Set.of(Collection.class,OmniCollection.OfRef.class,OmniCollection.OfBoolean.class,
                    OmniCollection.OfByte.class,
                    OmniCollection.OfChar.class,
                    OmniCollection.OfShort.class,
                    OmniCollection.OfInt.class,
                    OmniCollection.OfLong.class,
                    OmniCollection.OfFloat.class,OmniCollection.FloatOutput.class);
            break;
        case "DOUBLE":
            validCollectionConstructorClasses=Set.of(Collection.class,OmniCollection.OfRef.class,OmniCollection.OfBoolean.class,
                    OmniCollection.OfByte.class,
                    OmniCollection.OfChar.class,
                    OmniCollection.OfShort.class,
                    OmniCollection.OfInt.class,
                    OmniCollection.OfLong.class,
                    OmniCollection.OfFloat.class,
                    OmniCollection.OfDouble.class,OmniCollection.DoubleOutput.class);
            break;
        case "REF":
            validCollectionConstructorClasses=Set.of(Collection.class,OmniCollection.OfRef.class);
            break;
        default:
            throw this.invalid();
        }
    }
    public boolean isValidQueryVal(QueryVal queryVal){
        return queryVal != QueryVal.NonNull;
    }
    public abstract Object convertVal(boolean val);
    public abstract Object convertVal(byte val);
    public abstract Object convertVal(char val);
    public abstract Object convertVal(short val);
    public abstract Object convertVal(int val);
    public Object convertValUnchecked(int val){
        return convertVal(val);
    }
    public abstract Object convertVal(long val);
    public Object convertValUnchecked(long val){
        return convertVal(val);
    }
    public abstract Object convertVal(float val);
    public abstract Object convertVal(double val);
    public final Object convertVal(Object val){
        if(val instanceof Integer){
            return convertVal((int)val);
        }else if(val instanceof Long){
            return convertVal((long)val);
        }else if(val instanceof Double){
            return convertVal((double)val);
        }else if(val instanceof Float){
            return convertVal((float)val);
        }else if(val instanceof Boolean){
            return convertVal((boolean)val);
        }else if(val instanceof Byte){
            return convertVal((byte)val);
        }else if(val instanceof Character){
            return convertVal((char)val);
        }else if(val instanceof Short){
            return convertVal((short)val);
        }
        return val;
    }
    public final Object convertValUnchecked(DataType inputType,Object val){
        switch(inputType){
        case BOOLEAN:
            return convertVal((boolean)val);
        case BYTE:
            return convertVal((byte)val);
        case CHAR:
            return convertVal((char)val);
        case SHORT:
            return convertVal((short)val);
        case INT:
            return convertValUnchecked((int)val);
        case LONG:
            return convertValUnchecked((long)val);
        case FLOAT:
            return convertVal((float)val);
        case DOUBLE:
            return convertVal((double)val);
        case REF:
            return val;
        }
        throw inputType.invalid();
    }
    public abstract Object getFromArray(int index,Object arrayOfThisType);
    public abstract void storyInArray(int index,Object arrayOfThisType,Object valOfThisType);
    public final void convertValAndStoreInArray(int index,Object arrayOfThisType,Object inputVal,DataType inputType) {
        storyInArray(index,arrayOfThisType,this.convertVal(inputType,inputVal));
    }
    public abstract Object newArray(int length);
    public abstract int arrayLength(Object arr);
    public final Object convertVal(DataType inputType,Object val){
        switch(inputType){
        case BOOLEAN:
            return convertVal((boolean)val);
        case BYTE:
            return convertVal((byte)val);
        case CHAR:
            return convertVal((char)val);
        case SHORT:
            return convertVal((short)val);
        case INT:
            return convertVal((int)val);
        case LONG:
            return convertVal((long)val);
        case FLOAT:
            return convertVal((float)val);
        case DOUBLE:
            return convertVal((double)val);
        case REF:
            return val;
        }
        throw inputType.invalid();
    }
    public void verifyMASSIVEToString(String result,int seqSize,String testName){
        int offset;
        Assertions.assertEquals('[',result.charAt(offset=0));
        Assertions.assertEquals('0',result.charAt(++offset));
        ++offset;
        int numBatches=TestExecutorService.getNumWorkers();
        int batchSize=(seqSize - 1) / numBatches * 3;
        for(int batchCount=0;batchCount < numBatches;++batchCount){
            final int batchOffset=offset;
            final int batchBound=batchCount == numBatches?result.length() - 1:offset + batchSize;
            TestExecutorService.submitTest(()->{
                for(int i=batchOffset;i < batchBound;++i){
                    Assertions.assertEquals(',',result.charAt(i));
                    Assertions.assertEquals(' ',result.charAt(++i));
                    Assertions.assertEquals('0',result.charAt(++i));
                }
            });
            offset=batchBound;
        }
        Assertions.assertEquals(']',result.charAt(result.length() - 1));
        TestExecutorService.completeAllTests(testName);
    }
    public abstract void verifyToString(String result,OmniCollection<?> collection,String testName);
    public abstract void verifyToString(String result,OmniCollection<?> collection);
    public abstract boolean callcontains(OmniCollection<?> collection,Object inputVal,
            FunctionCallType functionCallType);
    public abstract boolean callremoveVal(OmniCollection<?> collection,Object inputVal,
            FunctionCallType functionCallType);
    public abstract int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType);
    public abstract int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType);
    public abstract boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
            FunctionCallType functionCallType);
    public abstract boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
            FunctionCallType functionCallType);
    public abstract int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType);
    public abstract Object verifyToArray(MonitoredCollection<?> monitoredCollection);
    public abstract Object callNext(OmniIterator<?> itr);
    public abstract Object callPrevious(OmniListIterator<?> itr);
    public abstract void callSet(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType);
    public abstract Object callRemoveAt(int index,OmniList<?> collection);
    public abstract void callAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType);
    public abstract boolean callAdd(Object inputVal,OmniCollection<?> collection,
            FunctionCallType functionCallType);
    public abstract void callPush(Object inputVal,OmniStack<?> collection,FunctionCallType functionCallType);
    public abstract void callAdd(int index,Object inputVal,OmniList<?> collection,
            FunctionCallType functionCallType);
    public abstract Object callElement(OmniQueue<?> collection);
    public abstract Object callRemove(OmniQueue<?> collection);
    public abstract Object callPeekFirst(OmniDeque<?> collection);
    public abstract Object callPeekLast(OmniDeque<?> collection);
    public abstract Object callPollFirst(OmniDeque<?> collection);
    public abstract Object callPollLast(OmniDeque<?> collection);
    public abstract Object callGetFirst(OmniDeque<?> collection);
    public abstract Object callGetLast(OmniDeque<?> collection);
    public abstract Object callRemoveFirst(OmniDeque<?> collection);
    public abstract Object callRemoveLast(OmniDeque<?> collection);
    public abstract Object callGet(int index,OmniList<?> collection);
    public abstract void callPut(int index,Object inputVal,OmniList<?> collection,
            FunctionCallType functionCallType);
    public abstract Object callSet(int index,Object inputVal,OmniList<?> collection,
            FunctionCallType functionCallType);
    public abstract void callStableSort(MonitoredComparator sorter,OmniList<?> collection,
            FunctionCallType functionCallType);
    public abstract void callUnstableSort(MonitoredComparator sorter,OmniList<?> collection);
    public abstract Object callPeek(PeekAndPollIfc<?> collection);
    public abstract Object callPoll(PeekAndPollIfc<?> collection);
    public abstract Object callPop(OmniStack<?> collection);
    public abstract void callAddLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType);
    public abstract void callAddFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType);
    public abstract boolean callOffer(Object inputVal,OmniQueue<?> collection,FunctionCallType functionCallType);
    public abstract boolean callOfferFirst(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType);
    public abstract boolean callOfferLast(Object inputVal,OmniDeque<?> collection,FunctionCallType functionCallType);
    public int getMaxInt(){
        throw new UnsupportedOperationException();
    }
    public int getMinInt(){
        throw new UnsupportedOperationException();
    }
    public long getMaxLong(){
        throw new UnsupportedOperationException();
    }
    public long getMinLong(){
        throw new UnsupportedOperationException();
    }
    public final UnsupportedOperationException invalid(){
        return new UnsupportedOperationException("Invalid DataType " + this);
    }
    public final EnumSet<DataType> mayBeAddedTo(){
        return getDataTypeSet(this.mayBeAddedTo);
    }
    public final EnumSet<DataType> validOutputTypes(){
        return getDataTypeSet(this.validOutputTypes);
    }
    private final UnsupportedOperationException cannotBeConverted(Object val,DataType type){
        return new UnsupportedOperationException(
                "The value \"" + val + "\" of type " + type + " cannot be converted to " + this);
    }
    public synchronized static EnumSet<DataType> getDataTypeSet(String dataTypes){
        return SETS.computeIfAbsent(dataTypes,dataTypeStr->{
            String[] tokens=dataTypes.split(",");
            for(int i=tokens.length;--i >= 0;){
                tokens[i]=tokens[i].trim().toUpperCase();
            }
            switch(tokens.length){
            case 0:
                return EnumSet.noneOf(DataType.class);
            case 1:
                return EnumSet.of(DataType.valueOf(tokens[0]));
            default:
                DataType[] rest=new DataType[tokens.length - 1];
                for(int i=rest.length;--i >= 0;){
                    rest[i]=DataType.valueOf(tokens[i + 1]);
                }
                return EnumSet.of(DataType.valueOf(tokens[0]),rest);
            }
        });
    }

    public abstract IntPredicate getArrayIndexSearcher(Object arr,Object inputVal,DataType inputType);
    
    private static final HashMap<String,EnumSet<DataType>> SETS=new HashMap<>();
}
