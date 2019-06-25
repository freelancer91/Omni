package omni.impl;

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
import omni.util.ToStringUtil;
public enum DataType{
    BOOLEAN(true,false,false,Boolean.class,boolean.class,Boolean[].class,boolean[].class,"Boolean","Boolean",
            BooleanPredicate.class,BooleanConsumer.class,BooleanComparator.class,BooleanPredicate.class,Boolean.FALSE,
            BooleanDblLnkNode.class,BooleanSnglLnkNode.class,"removeBooleanAt",boolean.class,"test","compare",
            boolean.class,"booleanElement") {
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.OfBoolean)collection.getCollection();
            var result=cast.toBooleanArray();
            collection.verifyCollectionState();
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
        @Override public void verifyToString(String result,OmniIterator<?> itr){
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


    },
    BYTE(true,false,true,Byte.class,byte.class,Byte[].class,byte[].class,"Byte","Byte",BytePredicate.class,
            ByteConsumer.class,ByteComparator.class,ByteUnaryOperator.class,Byte.MIN_VALUE,ByteDblLnkNode.class,
            ByteSnglLnkNode.class,"removeByteAt",int.class,"applyAsByte","compare",byte.class,"byteElement") {
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.ByteOutput<?>)collection.getCollection();
            var result=cast.toByteArray();
            collection.verifyCollectionState();
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
        @Override public void verifyToString(String result,OmniIterator<?> itr){
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
    },
    CHAR(true,false,false,Character.class,char.class,Character[].class,char[].class,"Char","Char",CharPredicate.class,
            CharConsumer.class,CharComparator.class,CharUnaryOperator.class,Character.MIN_VALUE,CharDblLnkNode.class,
            CharSnglLnkNode.class,"removeCharAt",int.class,"applyAsChar","compare",char.class,"charElement") {
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.CharOutput<?>)collection.getCollection();
            var result=cast.toCharArray();
            collection.verifyCollectionState();
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
        @Override public void verifyToString(String result,OmniIterator<?> itr){
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
    },
    SHORT(true,false,true,Short.class,short.class,Short[].class,short[].class,"Short","Short",ShortPredicate.class,
            ShortConsumer.class,ShortComparator.class,ShortUnaryOperator.class,Short.MIN_VALUE,ShortDblLnkNode.class,
            ShortSnglLnkNode.class,"removeShortAt",int.class,"applyAsShort","compare",short.class,"shortElement") {
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.ShortOutput<?>)collection.getCollection();
            var result=cast.toShortArray();
            collection.verifyCollectionState();
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
        @Override public void verifyToString(String result,OmniIterator<?> itr){
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
    },
    INT(true,false,true,Integer.class,int.class,Integer[].class,int[].class,"Int","Int",IntPredicate.class,
            IntConsumer.class,IntBinaryOperator.class,IntUnaryOperator.class,Integer.MIN_VALUE,IntDblLnkNode.class,
            IntSnglLnkNode.class,"removeIntAt",int.class,"applyAsInt","applyAsInt",int.class,"intElement") {
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.IntOutput<?>)collection.getCollection();
            var result=cast.toIntArray();
            collection.verifyCollectionState();
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
        @Override public void verifyToString(String result,OmniIterator<?> itr){
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
    },
    LONG(true,false,true,Long.class,long.class,Long[].class,long[].class,"Long","Long",LongPredicate.class,
            LongConsumer.class,LongComparator.class,LongUnaryOperator.class,Long.MIN_VALUE,LongDblLnkNode.class,
            LongSnglLnkNode.class,"removeLongAt",long.class,"applyAsLong","compare",long.class,"longElement") {
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.LongOutput<?>)collection.getCollection();
            var result=cast.toLongArray();
            collection.verifyCollectionState();
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
        @Override public void verifyToString(String result,OmniIterator<?> itr){
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
    },
    FLOAT(false,true,true,Float.class,float.class,Float[].class,float[].class,"Float","Float",FloatPredicate.class,
            FloatConsumer.class,FloatComparator.class,FloatUnaryOperator.class,Float.NaN,FloatDblLnkNode.class,
            FloatSnglLnkNode.class,"removeFloatAt",int.class,"applyAsFloat","compare",float.class,"floatElement") {
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.FloatOutput<?>)collection.getCollection();
            var result=cast.toFloatArray();
            collection.verifyCollectionState();
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
        @Override public void verifyToString(String result,OmniIterator<?> itr){
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
    },
    DOUBLE(false,true,true,Double.class,double.class,Double[].class,double[].class,"Double","Double",
            DoublePredicate.class,DoubleConsumer.class,DoubleComparator.class,DoubleUnaryOperator.class,Double.NaN,
            DoubleDblLnkNode.class,DoubleSnglLnkNode.class,"removeDoubleAt",long.class,"applyAsDouble","compare",
            double.class,"doubleElement") {
        @Override public Object verifyToArray(MonitoredCollection<?> collection){
            var cast=(OmniCollection.DoubleOutput<?>)collection.getCollection();
            var result=cast.toDoubleArray();
            collection.verifyCollectionState();
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
        @Override public void verifyToString(String result,OmniIterator<?> itr){
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
    },
    REF(false,false,false,null,Object.class,null,Object[].class,"Ref","",null,null,null,null,null,RefDblLnkNode.class,
            RefSnglLnkNode.class,"remove",Object.class,"apply","compare",Comparable.class,"element") {
        @Override public Object verifyToArray(MonitoredCollection<?> monitoredCollection){
            var collection=monitoredCollection.getCollection();
            var result=collection.toArray();
            monitoredCollection.verifyCollectionState();
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
                    throw new UnsupportedOperationException("Unknown dataType "+dataType);
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
        @SuppressWarnings({"unchecked","rawtypes"})
        @Override
        public boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw new UnsupportedOperationException("Ref cannot be boxed");
            }
            return ((OmniCollection.OfRef)collection).add(inputVal);
        }
        @Override
        public boolean callcontains(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw new UnsupportedOperationException("Ref cannot be boxed");
            }
            return collection.contains(inputVal);
        }
        @Override
        public boolean callremoveVal(OmniCollection<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw new UnsupportedOperationException("Ref cannot be boxed");
            }
            return collection.remove(inputVal);
        }
        @Override
        public boolean callremoveFirstOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw new UnsupportedOperationException("Ref cannot be boxed");
            }
            return collection.removeFirstOccurrence(inputVal);
        }
        @Override
        public boolean callremoveLastOccurrence(OmniDeque<?> collection,Object inputVal,
                FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw new UnsupportedOperationException("Ref cannot be boxed");
            }
            return collection.removeLastOccurrence(inputVal);
        }
        @Override
        public int callindexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw new UnsupportedOperationException("Ref cannot be boxed");
            }
            return collection.indexOf(inputVal);
        }
        @Override
        public int calllastIndexOf(OmniList<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw new UnsupportedOperationException("Ref cannot be boxed");
            }
            return collection.lastIndexOf(inputVal);
        }
        @Override
        public int callsearch(OmniStack<?> collection,Object inputVal,FunctionCallType functionCallType){
            if(functionCallType.boxed){
                throw new UnsupportedOperationException("Ref cannot be boxed");
            }
            return collection.search(inputVal);
        }


    };
    public final Set<DataType> mayBeAddedTo;
    public final Set<DataType> validOutputTypes;

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
    DataType(boolean isIntegral,boolean isFloatingPoint,boolean isSigned,Class<?> boxedClass,Class<?> primitiveClass,
            Class<?> boxedArrayClass,Class<?> primitiveArrayClass,String classPrefix,String typeNameModifier,
            Class<?> predicateClass,Class<?> consumerClass,Class<?> comparatorClass,Class<?> unaryOperatorClass,
            Object defaultVal,Class<?> dblLnkNodeClass,Class<?> snglLnkNodeClass,String removeAtIndexMethodName,
            Class<?> queryParameterType,String applyMethodName,String compareMethodName,Class<?> comparableType,
            String elementMethodName){
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
        this.mayBeAddedTo=initMayBeAddedTo(this);
        this.validOutputTypes=initValidOutputTypes(this);

    }

    private static Set<DataType> initValidOutputTypes(DataType dataType){
        switch(dataType){
        case BOOLEAN:
            return getDataTypeSet(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,
                    DataType.LONG,DataType.FLOAT,DataType.DOUBLE,DataType.REF);
        case BYTE:
            return getDataTypeSet(DataType.BYTE,DataType.SHORT,DataType.INT,DataType.LONG,DataType.FLOAT,
                    DataType.DOUBLE,DataType.REF);
        case CHAR:
            return getDataTypeSet(DataType.CHAR,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE,DataType.REF);
        case DOUBLE:
            return getDataTypeSet(DataType.DOUBLE,DataType.REF);
        case FLOAT:
            return getDataTypeSet(DataType.FLOAT,DataType.DOUBLE,DataType.REF);
        case INT:
            return getDataTypeSet(DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE,DataType.REF);
        case LONG:
            return getDataTypeSet(DataType.LONG,DataType.FLOAT,DataType.DOUBLE,DataType.REF);
        case REF:
            return getDataTypeSet(DataType.REF);
        case SHORT:
            return getDataTypeSet(DataType.SHORT,DataType.INT,DataType.LONG,DataType.FLOAT,DataType.DOUBLE,
                    DataType.REF);
        }
        throw invalidDataType(dataType);
    }
    public void verifyToString(String result,OmniIterator<?> itr) {
        int offset;
        Assertions.assertEquals('[',result.charAt(offset=0));
        if(itr.hasNext()) {
            for(;;) {
                String strVal;
                for(int i=0,valLength=(strVal=String.valueOf(itr.next())).length();i<valLength;++i) {
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
    public abstract boolean callCollectionAdd(Object inputVal,OmniCollection<?> collection,
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
    public static UnsupportedOperationException invalidDataType(DataType dataType){
        return new UnsupportedOperationException("Invalid dataType " + dataType);
    }
    private static Set<DataType> initMayBeAddedTo(DataType dataType){
        switch(dataType){
        case REF:
            return getDataTypeSet(DataType.REF);
        case BOOLEAN:
            return getDataTypeSet(DataType.BOOLEAN);
        case BYTE:
            return getDataTypeSet(DataType.BOOLEAN,DataType.BYTE);
        case CHAR:
            return getDataTypeSet(DataType.BOOLEAN,DataType.CHAR);
        case SHORT:
            return getDataTypeSet(DataType.BOOLEAN,DataType.BYTE,DataType.SHORT);
        case INT:
            return getDataTypeSet(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT);
        case LONG:
            return getDataTypeSet(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,
                    DataType.LONG);
        case FLOAT:
            return getDataTypeSet(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,
                    DataType.LONG,DataType.FLOAT);
        case DOUBLE:
            return getDataTypeSet(DataType.BOOLEAN,DataType.BYTE,DataType.CHAR,DataType.SHORT,DataType.INT,
                    DataType.LONG,DataType.FLOAT,DataType.DOUBLE);
        }
        throw invalidDataType(dataType);
    }

    private static final HashMap<Set<DataType>,Set<DataType>> SETS=new HashMap<>();
    public static synchronized Set<DataType> getDataTypeSet(DataType...dataTypes){
        Set<DataType> newSet;
        return SETS.putIfAbsent(newSet=Set.of(dataTypes),newSet);
    }


}
