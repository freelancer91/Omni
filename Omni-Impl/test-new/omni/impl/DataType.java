package omni.impl;

import java.util.EnumSet;
import java.util.HashMap;
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
import omni.function.LongComparator;
import omni.function.ShortComparator;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import omni.function.ShortUnaryOperator;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
import omni.util.ToStringUtil;
import omni.util.TypeUtil;
public enum DataType{
    BOOLEAN("BOOLEAN","BOOLEAN","BOOLEAN,BYTE,CHAR,SHORT,INT,LONG,FLOAT,DOUBLE,REF",true,false,false,Boolean.class,
            boolean.class,Boolean[].class,boolean[].class,"Boolean","Boolean",
            BooleanPredicate.class,BooleanConsumer.class,BooleanComparator.class,BooleanPredicate.class,Boolean.FALSE,
            BooleanDblLnkNode.class,BooleanSnglLnkNode.class,"removeBooleanAt",boolean.class,"test","compare",
            boolean.class,"booleanElement",OmniArray.MAX_ARR_SIZE / 7){
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.OfBoolean)collection.getCollection();
            boolean[] result;
            try{
                result=cast.toBooleanArray();
            }finally{
                collection.verifyCollectionState();
            }
            collection.verifyArrayIsCopy(result);
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
        @Override public Object callIteratorNext(OmniIterator<?> itr){
            return ((OmniIterator.OfBoolean)itr).nextBoolean();
        }

        @Override public Object callIteratorPrev(OmniListIterator<?> itr){
            return ((OmniListIterator.OfBoolean)itr).previousBoolean();
        }
        @Override
        public boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
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
        public void callListAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.BooleanInput<?>)collection).add(index,(Boolean)inputVal);
            }else{
                ((OmniList.BooleanInput<?>)collection).add(index,(boolean)inputVal);
            }
        }
        @Override
        public void callIteratorAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.BooleanInput<?>)itr).add((Boolean)inputVal);
            }else{
                ((OmniListIterator.BooleanInput<?>)itr).add((boolean)inputVal);
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
            int numBatches=TestExecutorService.numWorkers;
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
        public Object callListGet(int index,MonitoredList<?,?,?> monitoredList){
            return ((OmniList.OfBoolean)monitoredList.getCollection()).getBoolean(index);
        }
        @Override
        public void callListPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.BooleanInput<?>)collection).put(index,(Boolean)inputVal);
            }else{
                ((OmniList.BooleanInput<?>)collection).put(index,(boolean)inputVal);
            }
        }
        @Override
        public Object callListSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfBoolean)collection).set(index,(Boolean)inputVal);
            }else{
                return ((OmniList.OfBoolean)collection).set(index,(boolean)inputVal);
            }
        }


    },
    BYTE("BYTE","BOOLEAN,BYTE","BYTE,SHORT,INT,LONG,FLOAT,DOUBLE,REF",true,false,true,Byte.class,byte.class,
            Byte[].class,byte[].class,"Byte","Byte",BytePredicate.class,
            ByteConsumer.class,ByteComparator.class,ByteUnaryOperator.class,Byte.MIN_VALUE,ByteDblLnkNode.class,
            ByteSnglLnkNode.class,"removeByteAt",int.class,"applyAsByte","compare",byte.class,"byteElement",
            OmniArray.MAX_ARR_SIZE / 6){
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
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.ByteOutput<?>)collection.getCollection();
            byte[] result;
            try{
                result=cast.toByteArray();
            }finally{
                collection.verifyCollectionState();
            }
            collection.verifyArrayIsCopy(result);
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
        @Override public Object callIteratorNext(OmniIterator<?> itr){
            return ((OmniIterator.ByteOutput<?>)itr).nextByte();
        }
        @Override public Object callIteratorPrev(OmniListIterator<?> itr){
            return ((OmniListIterator.ByteOutput<?>)itr).previousByte();
        }
        @Override
        public boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
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
        public void callListAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.ByteInput<?>)collection).add(index,(Byte)inputVal);
            }else{
                ((OmniList.ByteInput<?>)collection).add(index,(byte)inputVal);
            }
        }
        @Override
        public void callIteratorAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.ByteInput<?>)itr).add((Byte)inputVal);
            }else{
                ((OmniListIterator.ByteInput<?>)itr).add((byte)inputVal);
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
        public Object callListGet(int index,MonitoredList<?,?,?> monitoredList){
            return ((OmniList.ByteOutput<?>)monitoredList.getCollection()).getByte(index);
        }
        @Override
        public void callListPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.ByteInput<?>)collection).put(index,(Byte)inputVal);
            }else{
                ((OmniList.ByteInput<?>)collection).put(index,(byte)inputVal);
            }
        }
        @Override
        public Object callListSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfByte)collection).set(index,(Byte)inputVal);
            }else{
                return ((OmniList.OfByte)collection).set(index,(byte)inputVal);
            }
        }

    },
    CHAR("CHAR","BOOLEAN,CHAR","CHAR,INT,LONG,FLOAT,DOUBLE,REF",true,false,false,Character.class,char.class,
            Character[].class,char[].class,"Char","Char",CharPredicate.class,CharConsumer.class,CharComparator.class,
            CharUnaryOperator.class,Character.MIN_VALUE,CharDblLnkNode.class,CharSnglLnkNode.class,"removeCharAt",
            int.class,"applyAsChar","compare",char.class,"charElement",-1){
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
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.CharOutput<?>)collection.getCollection();
            char[] result;
            try{
                result=cast.toCharArray();
            }finally{
                collection.verifyCollectionState();
            }
            collection.verifyArrayIsCopy(result);
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
        @Override public Object callIteratorNext(OmniIterator<?> itr){
            return ((OmniIterator.CharOutput<?>)itr).nextChar();
        }
        @Override public Object callIteratorPrev(OmniListIterator<?> itr){
            return ((OmniListIterator.CharOutput<?>)itr).previousChar();
        }
        @Override
        public boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
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
        public void callListAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.CharInput<?>)collection).add(index,(Character)inputVal);
            }else{
                ((OmniList.CharInput<?>)collection).add(index,(char)inputVal);
            }
        }
        @Override
        public void callIteratorAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.CharInput<?>)itr).add((Character)inputVal);
            }else{
                ((OmniListIterator.CharInput<?>)itr).add((char)inputVal);
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
        public Object callListGet(int index,MonitoredList<?,?,?> monitoredList){
            return ((OmniList.CharOutput<?>)monitoredList.getCollection()).getChar(index);
        }
        @Override
        public void callListPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.CharInput<?>)collection).put(index,(Character)inputVal);
            }else{
                ((OmniList.CharInput<?>)collection).put(index,(char)inputVal);
            }
        }
        @Override
        public Object callListSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
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
    },
    SHORT("SHORT","BOOLEAN,BYTE,SHORT","SHORT,INT,LONG,FLOAT,DOUBLE,REF",true,false,true,Short.class,short.class,
            Short[].class,short[].class,"Short","Short",ShortPredicate.class,ShortConsumer.class,ShortComparator.class,
            ShortUnaryOperator.class,Short.MIN_VALUE,ShortDblLnkNode.class,ShortSnglLnkNode.class,"removeShortAt",
            int.class,"applyAsShort","compare",short.class,"shortElement",OmniArray.MAX_ARR_SIZE / 8){
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
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.ShortOutput<?>)collection.getCollection();
            short[] result;
            try{
                result=cast.toShortArray();
            }finally{
                collection.verifyCollectionState();
            }
            collection.verifyArrayIsCopy(result);
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
        @Override public Object callIteratorNext(OmniIterator<?> itr){
            return ((OmniIterator.ShortOutput<?>)itr).nextShort();
        }
        @Override public Object callIteratorPrev(OmniListIterator<?> itr){
            return ((OmniListIterator.ShortOutput<?>)itr).previousShort();
        }
        @Override
        public boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
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
        public void callListAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.ShortInput<?>)collection).add(index,(Short)inputVal);
            }else{
                ((OmniList.ShortInput<?>)collection).add(index,(short)inputVal);
            }
        }
        @Override
        public void callIteratorAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.ShortInput<?>)itr).add((Short)inputVal);
            }else{
                ((OmniListIterator.ShortInput<?>)itr).add((short)inputVal);
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
        public Object callListGet(int index,MonitoredList<?,?,?> monitoredList){
            return ((OmniList.ShortOutput<?>)monitoredList.getCollection()).getShort(index);
        }
        @Override
        public void callListPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.ShortInput<?>)collection).put(index,(Short)inputVal);
            }else{
                ((OmniList.ShortInput<?>)collection).put(index,(short)inputVal);
            }
        }
        @Override
        public Object callListSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfShort)collection).set(index,(Short)inputVal);
            }else{
                return ((OmniList.OfShort)collection).set(index,(short)inputVal);
            }
        }

    },
    INT("INT","BOOLEAN,BYTE,CHAR,SHORT,INT","INT,LONG,FLOAT,DOUBLE,REF",true,false,true,Integer.class,int.class,
            Integer[].class,int[].class,"Int","Int",IntPredicate.class,
            IntConsumer.class,IntBinaryOperator.class,IntUnaryOperator.class,Integer.MIN_VALUE,IntDblLnkNode.class,
            IntSnglLnkNode.class,"removeIntAt",int.class,"applyAsInt","applyAsInt",int.class,"intElement",
            OmniArray.MAX_ARR_SIZE / 13){
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
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.IntOutput<?>)collection.getCollection();
            int[] result;
            try{
                result=cast.toIntArray();
            }finally{
                collection.verifyCollectionState();
            }
            collection.verifyArrayIsCopy(result);
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
        @Override public Object callIteratorNext(OmniIterator<?> itr){
            return ((OmniIterator.IntOutput<?>)itr).nextInt();
        }
        @Override public Object callIteratorPrev(OmniListIterator<?> itr){
            return ((OmniListIterator.IntOutput<?>)itr).previousInt();
        }
        @Override
        public boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
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
        public void callListAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.IntInput<?>)collection).add(index,(Integer)inputVal);
            }else{
                ((OmniList.IntInput<?>)collection).add(index,(int)inputVal);
            }
        }
        @Override
        public void callIteratorAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.IntInput<?>)itr).add((Integer)inputVal);
            }else{
                ((OmniListIterator.IntInput<?>)itr).add((int)inputVal);
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
        public Object callListGet(int index,MonitoredList<?,?,?> monitoredList){
            return ((OmniList.IntOutput<?>)monitoredList.getCollection()).getInt(index);
        }
        @Override
        public void callListPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.IntInput<?>)collection).put(index,(Integer)inputVal);
            }else{
                ((OmniList.IntInput<?>)collection).put(index,(int)inputVal);
            }
        }
        @Override
        public Object callListSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfInt)collection).set(index,(Integer)inputVal);
            }else{
                return ((OmniList.OfInt)collection).set(index,(int)inputVal);
            }
        }
    },
    LONG("LONG","BOOLEAN,BYTE,CHAR,SHORT,INT,LONG","LONG,FLOAT,DOUBLE,REF",true,false,true,Long.class,long.class,
            Long[].class,long[].class,"Long","Long",LongPredicate.class,
            LongConsumer.class,LongComparator.class,LongUnaryOperator.class,Long.MIN_VALUE,LongDblLnkNode.class,
            LongSnglLnkNode.class,"removeLongAt",long.class,"applyAsLong","compare",long.class,"longElement",
            OmniArray.MAX_ARR_SIZE / 22){
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
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.LongOutput<?>)collection.getCollection();
            long[] result;
            try{
                result=cast.toLongArray();
            }finally{
                collection.verifyCollectionState();
            }
            collection.verifyArrayIsCopy(result);
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
        @Override public Object callIteratorNext(OmniIterator<?> itr){
            return ((OmniIterator.LongOutput<?>)itr).nextLong();
        }
        @Override public Object callIteratorPrev(OmniListIterator<?> itr){
            return ((OmniListIterator.LongOutput<?>)itr).previousLong();
        }
        @Override
        public boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
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
        public void callListAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.LongInput<?>)collection).add(index,(Long)inputVal);
            }else{
                ((OmniList.LongInput<?>)collection).add(index,(long)inputVal);
            }
        }
        @Override
        public void callIteratorAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.LongInput<?>)itr).add((Long)inputVal);
            }else{
                ((OmniListIterator.LongInput<?>)itr).add((long)inputVal);
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
        public Object callListGet(int index,MonitoredList<?,?,?> monitoredList){
            return ((OmniList.LongOutput<?>)monitoredList.getCollection()).getLong(index);
        }
        @Override
        public void callListPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.LongInput<?>)collection).put(index,(Long)inputVal);
            }else{
                ((OmniList.LongInput<?>)collection).put(index,(long)inputVal);
            }
        }
        @Override
        public Object callListSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfLong)collection).set(index,(Long)inputVal);
            }else{
                return ((OmniList.OfLong)collection).set(index,(long)inputVal);
            }
        }

    },
    FLOAT("FLOAT","BOOLEAN,BYTE,CHAR,SHORT,INT,LONG,FLOAT","FLOAT,DOUBLE,REF",false,true,true,Float.class,float.class,
            Float[].class,float[].class,"Float","Float",FloatPredicate.class,
            FloatConsumer.class,FloatComparator.class,FloatUnaryOperator.class,Float.NaN,FloatDblLnkNode.class,
            FloatSnglLnkNode.class,"removeFloatAt",int.class,"applyAsFloat","compare",float.class,"floatElement",
            OmniArray.MAX_ARR_SIZE / 17){
        @Override
        public void verifyMASSIVEToString(String result,int seqSize,String testName){
            int offset;
            Assertions.assertEquals('[',result.charAt(offset=0));
            Assertions.assertEquals('0',result.charAt(++offset));
            Assertions.assertEquals('.',result.charAt(++offset));
            Assertions.assertEquals('0',result.charAt(++offset));
            ++offset;
            int numBatches=TestExecutorService.numWorkers;
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
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.FloatOutput<?>)collection.getCollection();
            float[] result;
            try{
                result=cast.toFloatArray();
            }finally{
                collection.verifyCollectionState();
            }
            collection.verifyArrayIsCopy(result);
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
        @Override public Object callIteratorNext(OmniIterator<?> itr){
            return ((OmniIterator.FloatOutput<?>)itr).nextFloat();
        }
        @Override public Object callIteratorPrev(OmniListIterator<?> itr){
            return ((OmniListIterator.FloatOutput<?>)itr).previousFloat();
        }
        @Override
        public boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
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
        public void callListAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.FloatInput<?>)collection).add(index,(Float)inputVal);
            }else{
                ((OmniList.FloatInput<?>)collection).add(index,(float)inputVal);
            }
        }
        @Override
        public void callIteratorAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.FloatInput<?>)itr).add((Float)inputVal);
            }else{
                ((OmniListIterator.FloatInput<?>)itr).add((float)inputVal);
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
        public Object callListGet(int index,MonitoredList<?,?,?> monitoredList){
            return ((OmniList.FloatOutput<?>)monitoredList.getCollection()).getFloat(index);
        }
        @Override
        public void callListPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.FloatInput<?>)collection).put(index,(Float)inputVal);
            }else{
                ((OmniList.FloatInput<?>)collection).put(index,(float)inputVal);
            }
        }
        @Override
        public Object callListSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                return ((OmniList.OfFloat)collection).set(index,(Float)inputVal);
            }else{
                return ((OmniList.OfFloat)collection).set(index,(float)inputVal);
            }
        }


    },
    DOUBLE("DOUBLE","BOOLEAN,BYTE,CHAR,SHORT,INT,LONG,FLOAT,DOUBLE","DOUBLE,REF",false,true,true,Double.class,
            double.class,Double[].class,double[].class,"Double","Double",
            DoublePredicate.class,DoubleConsumer.class,DoubleComparator.class,DoubleUnaryOperator.class,Double.NaN,
            DoubleDblLnkNode.class,DoubleSnglLnkNode.class,"removeDoubleAt",long.class,"applyAsDouble","compare",
            double.class,"doubleElement",-1){

        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.DoubleOutput<?>)collection.getCollection();
            double[] result;
            try{
                result=cast.toDoubleArray();
            }finally{
                collection.verifyCollectionState();
            }
            collection.verifyArrayIsCopy(result);
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
        @Override public Object callIteratorNext(OmniIterator<?> itr){
            return ((OmniIterator.DoubleOutput<?>)itr).nextDouble();
        }
        @Override public Object callIteratorPrev(OmniListIterator<?> itr){
            return ((OmniListIterator.DoubleOutput<?>)itr).previousDouble();
        }
        @Override
        public boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
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
        public void callListAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.OfDouble)collection).add(index,(Double)inputVal);
            }else{
                ((OmniList.OfDouble)collection).add(index,(double)inputVal);
            }
        }
        @Override
        public void callIteratorAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniListIterator.OfDouble)itr).add((Double)inputVal);
            }else{
                ((OmniListIterator.OfDouble)itr).add((double)inputVal);
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
        public Object callListGet(int index,MonitoredList<?,?,?> monitoredList){
            return ((OmniList.DoubleOutput<?>)monitoredList.getCollection()).getDouble(index);
        }
        @Override
        public void callListPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                ((OmniList.OfDouble)collection).put(index,(Double)inputVal);
            }else{
                ((OmniList.OfDouble)collection).put(index,(double)inputVal);
            }
        }
        @Override
        public Object callListSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
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

    },
    REF("REF","REF","REF",false,false,false,null,Object.class,null,Object[].class,"Ref","",null,null,null,null,null,
            RefDblLnkNode.class,
            RefSnglLnkNode.class,"remove",Object.class,"apply","compare",Comparable.class,"element",-1){
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
        @Override public Object callIteratorNext(OmniIterator<?> itr){
            return itr.next();
        }
        @Override public Object callIteratorPrev(OmniListIterator<?> itr){
            return itr.previous();
        }
        @SuppressWarnings({"unchecked"})
        @Override
        public boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            return ((OmniCollection.OfRef<Object>)collection).add(inputVal);
        }
        @SuppressWarnings({"unchecked"})
        @Override
        public void callListAdd(int index,Object inputVal,OmniList<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            ((OmniList.OfRef<Object>)collection).add(index,inputVal);
        }
        @SuppressWarnings({"unchecked"})
        @Override
        public void callIteratorAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw cannotBeBoxed();
            }
            ((OmniListIterator.OfRef<Object>)itr).add(inputVal);
        }
        @Override
        public Object callListGet(int index,MonitoredList<?,?,?> monitoredList){
            var dataType=monitoredList.getDataType();
            var list=monitoredList.getCollection();
            switch(dataType){
            case BOOLEAN:
                return ((OmniList.OfBoolean)list).get(index);
            case BYTE:
                return ((OmniList.OfByte)list).get(index);
            case CHAR:
                return ((OmniList.OfChar)list).get(index);
            case DOUBLE:
                return ((OmniList.OfDouble)list).get(index);
            case FLOAT:
                return ((OmniList.OfFloat)list).get(index);
            case INT:
                return ((OmniList.OfInt)list).get(index);
            case LONG:
                return ((OmniList.OfLong)list).get(index);
            case REF:
                return ((OmniList.OfRef<?>)list).get(index);
            case SHORT:
                return ((OmniList.OfShort)list).get(index);
            default:
                throw dataType.invalid();
            }
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
        public void callListPut(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw DataType.cannotBeBoxed();
            }
            ((OmniList.OfRef<Object>)collection).put(index,inputVal);
        }
        @SuppressWarnings("unchecked")
        @Override
        public Object callListSet(int index,Object inputVal,OmniList<?> collection,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw DataType.cannotBeBoxed();
            }
            return ((OmniList.OfRef<Object>)collection).set(index,inputVal);
        }
        @Override
        public void verifyMASSIVEToString(String result,int seqSize,String testName){
            throw new UnsupportedOperationException();
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
    public final Class<?> dblLnkNodeClass;
    public final Class<?> snglLnkNodeClass;
    public final String removeAtIndexMethodName;
    public final Class<?> queryParameterType;
    public final String applyMethodName;
    public final String compareMethodName;
    public final Class<?> comparableType;
    public final String elementMethodName;
    public final int massiveToStringThreshold;
    DataType(String name,String mayBeAddedTo,String validOutputTypes,boolean isIntegral,boolean isFloatingPoint,
            boolean isSigned,Class<?> boxedClass,Class<?> primitiveClass,
            Class<?> boxedArrayClass,Class<?> primitiveArrayClass,String classPrefix,String typeNameModifier,
            Class<?> predicateClass,Class<?> consumerClass,Class<?> comparatorClass,Class<?> unaryOperatorClass,
            Object defaultVal,Class<?> dblLnkNodeClass,Class<?> snglLnkNodeClass,String removeAtIndexMethodName,
            Class<?> queryParameterType,String applyMethodName,String compareMethodName,Class<?> comparableType,
            String elementMethodName,int massiveToStringThreshold){
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
        this.dblLnkNodeClass=dblLnkNodeClass;
        this.snglLnkNodeClass=snglLnkNodeClass;
        this.removeAtIndexMethodName=removeAtIndexMethodName;
        this.queryParameterType=queryParameterType;
        this.applyMethodName=applyMethodName;
        this.compareMethodName=compareMethodName;
        this.comparableType=comparableType;
        this.elementMethodName=elementMethodName;
        this.massiveToStringThreshold=massiveToStringThreshold;
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
        int numBatches=TestExecutorService.numWorkers;
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
    public abstract Object verifyToArray(MonitoredCollection<?> collection);
    public abstract Object callIteratorNext(OmniIterator<?> itr);
    public abstract Object callIteratorPrev(OmniListIterator<?> itr);
    public abstract void callIteratorAdd(Object inputVal,OmniListIterator<?> itr,FunctionCallType functionCallType);
    public abstract boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
            FunctionCallType functionCallType);
    public abstract void callListAdd(int index,Object inputVal,OmniList<?> collection,
            FunctionCallType functionCallType);
    public abstract Object callListGet(int index,MonitoredList<?,?,?> monitoredList);
    public abstract void callListPut(int index,Object inputVal,OmniList<?> collection,
            FunctionCallType functionCallType);
    public abstract Object callListSet(int index,Object inputVal,OmniList<?> collection,
            FunctionCallType functionCallType);
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

    private static final HashMap<String,EnumSet<DataType>> SETS=new HashMap<>();
}
