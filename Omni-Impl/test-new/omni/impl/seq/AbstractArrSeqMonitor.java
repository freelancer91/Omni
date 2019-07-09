package omni.impl.seq;

import java.io.Externalizable;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniCollection;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredSequence;
import omni.util.OmniArray;

public abstract class AbstractArrSeqMonitor<SEQ extends OmniCollection<?>> implements MonitoredSequence<SEQ>{
    final DataType dataType;
    final CheckedType checkedType;
    final SEQ seq;
    int expectedSize;
    int expectedCapacity;
    int expectedModCount;
    Object expectedArr;
    abstract SEQ initSeq();
    abstract SEQ initSeq(int initCap);
    AbstractArrSeqMonitor(CheckedType checkedType,DataType dataType){
        this.dataType=dataType;
        this.checkedType=checkedType;
        this.seq=initSeq();
        updateCollectionState();
    }
    AbstractArrSeqMonitor(CheckedType checkedType,DataType dataType,int initCap){
        this.dataType=dataType;
        this.checkedType=checkedType;
        this.seq=initSeq(initCap);
        updateCollectionState();
    }
    @Override public CheckedType getCheckedType(){
        return checkedType;
    }
    @Override public SEQ getCollection(){
        return seq;
    }
    @Override public DataType getDataType(){
        return dataType;
    }
    @Override public void modParent(){
        throw new UnsupportedOperationException();
    }
    @Override public void modRoot(){
        throw new UnsupportedOperationException();
    }
    @Override public int size(){
        return expectedSize;
    }
    @Override public void updateClearState(){
        expectedSize=0;
        ++expectedModCount;
        if(dataType==DataType.REF) {
            var arr=(Object[])expectedArr;
            for(int i=expectedCapacity;--i>=0;) {
                arr[i]=null;
            }
        }
    }
    @Override public void updateCollectionState(){
        this.expectedSize=((AbstractSeq<?>)seq).size;
        if(checkedType.checked) {
            updateModCount();
        }
        switch(dataType) {
        case BOOLEAN:{
            var castSeq=(BooleanArrSeq)seq;
            var actualArr=castSeq.arr;
            var expectedArr=(boolean[])this.expectedArr;
            if(actualArr==null || actualArr==OmniArray.OfBoolean.DEFAULT_ARR) {
                this.expectedArr=actualArr;
                this.expectedCapacity=0;
            }else {
                if(expectedArr==null) {
                    System.arraycopy(actualArr,0,this.expectedArr=new boolean[actualArr.length],0,expectedSize);
                }else {
                    if(expectedArr.length!=actualArr.length) {
                        this.expectedArr=expectedArr=new boolean[actualArr.length];
                    }
                    System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
                }
                this.expectedCapacity=actualArr.length;
            }
            break;
        }
        case BYTE:{
            var castSeq=(ByteArrSeq)seq;
            var actualArr=castSeq.arr;
            var expectedArr=(byte[])this.expectedArr;
            if(actualArr==null || actualArr==OmniArray.OfByte.DEFAULT_ARR) {
                this.expectedArr=actualArr;
                this.expectedCapacity=0;
            }else {
                if(expectedArr==null) {
                    System.arraycopy(actualArr,0,this.expectedArr=new byte[actualArr.length],0,expectedSize);
                }else {
                    if(expectedArr.length!=actualArr.length) {
                        this.expectedArr=expectedArr=new byte[actualArr.length];
                    }
                    System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
                }
                this.expectedCapacity=actualArr.length;
            }
            break;
        }
        case CHAR:{
            var castSeq=(CharArrSeq)seq;
            var actualArr=castSeq.arr;
            var expectedArr=(char[])this.expectedArr;
            if(actualArr==null || actualArr==OmniArray.OfChar.DEFAULT_ARR) {
                this.expectedArr=actualArr;
                this.expectedCapacity=0;
            }else {
                if(expectedArr==null) {
                    System.arraycopy(actualArr,0,this.expectedArr=new char[actualArr.length],0,expectedSize);
                }else {
                    if(expectedArr.length!=actualArr.length) {
                        this.expectedArr=expectedArr=new char[actualArr.length];
                    }
                    System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
                }
                this.expectedCapacity=actualArr.length;
            }
            break;
        }
        case SHORT:{
            var castSeq=(ShortArrSeq)seq;
            var actualArr=castSeq.arr;
            var expectedArr=(short[])this.expectedArr;
            if(actualArr==null || actualArr==OmniArray.OfShort.DEFAULT_ARR) {
                this.expectedArr=actualArr;
                this.expectedCapacity=0;
            }else {
                if(expectedArr==null) {
                    System.arraycopy(actualArr,0,this.expectedArr=new short[actualArr.length],0,expectedSize);
                }else {
                    if(expectedArr.length!=actualArr.length) {
                        this.expectedArr=expectedArr=new short[actualArr.length];
                    }
                    System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
                }
                this.expectedCapacity=actualArr.length;
            }
            break;
        }
        case INT:{
            var castSeq=(IntArrSeq)seq;
            var actualArr=castSeq.arr;
            var expectedArr=(int[])this.expectedArr;
            if(actualArr==null || actualArr==OmniArray.OfInt.DEFAULT_ARR) {
                this.expectedArr=actualArr;
                this.expectedCapacity=0;
            }else {
                if(expectedArr==null) {
                    System.arraycopy(actualArr,0,this.expectedArr=new int[actualArr.length],0,expectedSize);
                }else {
                    if(expectedArr.length!=actualArr.length) {
                        this.expectedArr=expectedArr=new int[actualArr.length];
                    }
                    System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
                }
                this.expectedCapacity=actualArr.length;
            }
            break;
        }
        case LONG:{
            var castSeq=(LongArrSeq)seq;
            var actualArr=castSeq.arr;
            var expectedArr=(long[])this.expectedArr;
            if(actualArr==null || actualArr==OmniArray.OfLong.DEFAULT_ARR) {
                this.expectedArr=actualArr;
                this.expectedCapacity=0;
            }else {
                if(expectedArr==null) {
                    System.arraycopy(actualArr,0,this.expectedArr=new long[actualArr.length],0,expectedSize);
                }else {
                    if(expectedArr.length!=actualArr.length) {
                        this.expectedArr=expectedArr=new long[actualArr.length];
                    }
                    System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
                }
                this.expectedCapacity=actualArr.length;
            }
            break;
        }
        case FLOAT:{
            var castSeq=(FloatArrSeq)seq;
            var actualArr=castSeq.arr;
            var expectedArr=(float[])this.expectedArr;
            if(actualArr==null || actualArr==OmniArray.OfFloat.DEFAULT_ARR) {
                this.expectedArr=actualArr;
                this.expectedCapacity=0;
            }else {
                if(expectedArr==null) {
                    System.arraycopy(actualArr,0,this.expectedArr=new float[actualArr.length],0,expectedSize);
                }else {
                    if(expectedArr.length!=actualArr.length) {
                        this.expectedArr=expectedArr=new float[actualArr.length];
                    }
                    System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
                }
                this.expectedCapacity=actualArr.length;
            }
            break;
        }
        case DOUBLE:{
            var castSeq=(DoubleArrSeq)seq;
            var actualArr=castSeq.arr;
            var expectedArr=(double[])this.expectedArr;
            if(actualArr==null || actualArr==OmniArray.OfDouble.DEFAULT_ARR) {
                this.expectedArr=actualArr;
                this.expectedCapacity=0;
            }else {
                if(expectedArr==null) {
                    System.arraycopy(actualArr,0,this.expectedArr=new double[actualArr.length],0,expectedSize);
                }else {
                    if(expectedArr.length!=actualArr.length) {
                        this.expectedArr=expectedArr=new double[actualArr.length];
                    }
                    System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
                }
                this.expectedCapacity=actualArr.length;
            }
            break;
        }
        case REF:{
            var castSeq=(RefArrSeq<?>)seq;
            var actualArr=castSeq.arr;
            var expectedArr=(Object[])this.expectedArr;
            if(actualArr==null || actualArr==OmniArray.OfRef.DEFAULT_ARR) {
                this.expectedArr=actualArr;
                this.expectedCapacity=0;
            }else {
                if(expectedArr==null) {
                    System.arraycopy(actualArr,0,this.expectedArr=new Object[actualArr.length],0,expectedSize);
                }else {
                    if(expectedArr.length!=actualArr.length) {
                        this.expectedArr=expectedArr=new Object[actualArr.length];
                    }
                    System.arraycopy(actualArr,0,expectedArr,0,expectedSize);
                }
                this.expectedCapacity=actualArr.length;
            }
            break;
        }
        default:
            throw dataType.invalid();
        }
    }
    @Override public void verifyCollectionState(){
        int expectedSize;
        Assertions.assertEquals(expectedSize=this.expectedSize,((AbstractSeq<?>)seq).size);
        if(checkedType.checked) {
            verifyModCount();
        }
        switch(dataType) {
        case BOOLEAN:{
            var actualArr=((BooleanArrSeq)seq).arr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)==0) {
                Assertions.assertSame(expectedArr,actualArr);
            }else {
                Assertions.assertEquals(expectedCapacity,actualArr.length);
                var expectedArr=(boolean[])this.expectedArr;
                while(expectedSize!=0) {
                    Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
                }
            }
            break;
        }
        case BYTE:{
            var actualArr=((ByteArrSeq)seq).arr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)==0) {
                Assertions.assertSame(expectedArr,actualArr);
            }else {
                Assertions.assertEquals(expectedCapacity,actualArr.length);
                var expectedArr=(byte[])this.expectedArr;
                while(expectedSize!=0) {
                    Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
                }
            }
            break;
        }
        case CHAR:{
            var actualArr=((CharArrSeq)seq).arr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)==0) {
                Assertions.assertSame(expectedArr,actualArr);
            }else {
                Assertions.assertEquals(expectedCapacity,actualArr.length);
                var expectedArr=(char[])this.expectedArr;
                while(expectedSize!=0) {
                    Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
                }
            }
            break;
        }
        case DOUBLE:{
            var actualArr=((DoubleArrSeq)seq).arr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)==0) {
                Assertions.assertSame(expectedArr,actualArr);
            }else {
                Assertions.assertEquals(expectedCapacity,actualArr.length);
                var expectedArr=(double[])this.expectedArr;
                while(expectedSize!=0) {
                    Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
                }
            }
            break;
        }
        case FLOAT:{
            var actualArr=((FloatArrSeq)seq).arr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)==0) {
                Assertions.assertSame(expectedArr,actualArr);
            }else {
                Assertions.assertEquals(expectedCapacity,actualArr.length);
                var expectedArr=(float[])this.expectedArr;
                while(expectedSize!=0) {
                    Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
                }
            }
            break;
        }
        case INT:{
            var actualArr=((IntArrSeq)seq).arr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)==0) {
                Assertions.assertSame(expectedArr,actualArr);
            }else {
                Assertions.assertEquals(expectedCapacity,actualArr.length);
                var expectedArr=(int[])this.expectedArr;
                while(expectedSize!=0) {
                    Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
                }
            }
            break;
        }
        case LONG:{
            var actualArr=((LongArrSeq)seq).arr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)==0) {
                Assertions.assertSame(expectedArr,actualArr);
            }else {
                Assertions.assertEquals(expectedCapacity,actualArr.length);
                var expectedArr=(long[])this.expectedArr;
                while(expectedSize!=0) {
                    Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
                }
            }
            break;
        }
        case REF:{
            var actualArr=((RefArrSeq<?>)seq).arr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)==0) {
                Assertions.assertSame(expectedArr,actualArr);
            }else {
                var expectedArr=(Object[])this.expectedArr;
                int i=0;
                while(i!=expectedSize) {
                    Assertions.assertSame(expectedArr[i],actualArr[i++]);
                }
                while(i!=expectedCapacity) {
                    Assertions.assertNull(actualArr[i++]);
                }
            }
            break;
        }
        case SHORT:{
            var actualArr=((ShortArrSeq)seq).arr;
            int expectedCapacity;
            if((expectedCapacity=this.expectedCapacity)==0) {
                Assertions.assertSame(expectedArr,actualArr);
            }else {
                Assertions.assertEquals(expectedCapacity,actualArr.length);
                var expectedArr=(short[])this.expectedArr;
                while(expectedSize!=0) {
                    Assertions.assertEquals(expectedArr[--expectedSize],actualArr[expectedSize]);
                }
            }
            break;
        }
        default:
            throw dataType.invalid();
        }
    }
    @Override
    public void verifyReadAndWriteClone(SEQ readCol){
        verifyCloneTypeAndModCount(readCol);
        Assertions.assertNotSame(readCol,seq);
        int size;
        Assertions.assertEquals(size=((AbstractSeq<?>)seq).size,((AbstractSeq<?>)readCol).size);
        var origArr=((RefArrSeq<?>)seq).arr;
        var cloneArr=((RefArrSeq<?>)readCol).arr;
        if(origArr == OmniArray.OfRef.DEFAULT_ARR){
            Assertions.assertSame(origArr,cloneArr);
        }else{
            Assertions.assertNotSame(origArr,cloneArr);
            while(size != 0){
                Assertions.assertEquals(origArr[--size],cloneArr[size]);
            }
        }
    }
    @Override public void verifyClone(Object clone){
        verifyCloneTypeAndModCount(clone);
        Assertions.assertNotSame(clone,seq);
        int size;
        Assertions.assertEquals(size=((AbstractSeq<?>)seq).size,((AbstractSeq<?>)clone).size);
        switch(dataType) {
        case BOOLEAN:{
            var origArr=((BooleanArrSeq)seq).arr;
            var cloneArr=((BooleanArrSeq)clone).arr;
            if(origArr==OmniArray.OfBoolean.DEFAULT_ARR) {
                Assertions.assertSame(origArr,cloneArr);
            }else {
                Assertions.assertNotSame(origArr,cloneArr);
                while(size!=0) {
                    Assertions.assertEquals(origArr[--size],cloneArr[size]);
                }
            }
            break;
        }
        case BYTE:{
            var origArr=((ByteArrSeq)seq).arr;
            var cloneArr=((ByteArrSeq)clone).arr;
            if(origArr==OmniArray.OfByte.DEFAULT_ARR) {
                Assertions.assertSame(origArr,cloneArr);
            }else {
                Assertions.assertNotSame(origArr,cloneArr);
                while(size!=0) {
                    Assertions.assertEquals(origArr[--size],cloneArr[size]);
                }
            }
            break;
        }
        case CHAR:{
            var origArr=((CharArrSeq)seq).arr;
            var cloneArr=((CharArrSeq)clone).arr;
            if(origArr==OmniArray.OfChar.DEFAULT_ARR) {
                Assertions.assertSame(origArr,cloneArr);
            }else {
                Assertions.assertNotSame(origArr,cloneArr);
                while(size!=0) {
                    Assertions.assertEquals(origArr[--size],cloneArr[size]);
                }
            }
            break;
        }
        case SHORT:{
            var origArr=((ShortArrSeq)seq).arr;
            var cloneArr=((ShortArrSeq)clone).arr;
            if(origArr==OmniArray.OfShort.DEFAULT_ARR) {
                Assertions.assertSame(origArr,cloneArr);
            }else {
                Assertions.assertNotSame(origArr,cloneArr);
                while(size!=0) {
                    Assertions.assertEquals(origArr[--size],cloneArr[size]);
                }
            }
            break;
        }
        case INT:{
            var origArr=((IntArrSeq)seq).arr;
            var cloneArr=((IntArrSeq)clone).arr;
            if(origArr==OmniArray.OfInt.DEFAULT_ARR) {
                Assertions.assertSame(origArr,cloneArr);
            }else {
                Assertions.assertNotSame(origArr,cloneArr);
                while(size!=0) {
                    Assertions.assertEquals(origArr[--size],cloneArr[size]);
                }
            }
            break;
        }
        case LONG:{
            var origArr=((LongArrSeq)seq).arr;
            var cloneArr=((LongArrSeq)clone).arr;
            if(origArr==OmniArray.OfLong.DEFAULT_ARR) {
                Assertions.assertSame(origArr,cloneArr);
            }else {
                Assertions.assertNotSame(origArr,cloneArr);
                while(size!=0) {
                    Assertions.assertEquals(origArr[--size],cloneArr[size]);
                }
            }
            break;
        }
        case FLOAT:{
            var origArr=((FloatArrSeq)seq).arr;
            var cloneArr=((FloatArrSeq)clone).arr;
            if(origArr==OmniArray.OfFloat.DEFAULT_ARR) {
                Assertions.assertSame(origArr,cloneArr);
            }else {
                Assertions.assertNotSame(origArr,cloneArr);
                while(size!=0) {
                    Assertions.assertEquals(origArr[--size],cloneArr[size]);
                }
            }
            break;
        }
        case DOUBLE:{
            var origArr=((DoubleArrSeq)seq).arr;
            var cloneArr=((DoubleArrSeq)clone).arr;
            if(origArr==OmniArray.OfDouble.DEFAULT_ARR) {
                Assertions.assertSame(origArr,cloneArr);
            }else {
                Assertions.assertNotSame(origArr,cloneArr);
                while(size!=0) {
                    Assertions.assertEquals(origArr[--size],cloneArr[size]);
                }
            }
            break;
        }
        case REF:{
            var origArr=((RefArrSeq<?>)seq).arr;
            var cloneArr=((RefArrSeq<?>)clone).arr;
            if(origArr==OmniArray.OfRef.DEFAULT_ARR) {
                Assertions.assertSame(origArr,cloneArr);
            }else {
                Assertions.assertNotSame(origArr,cloneArr);
                while(size!=0) {
                    Assertions.assertSame(origArr[--size],cloneArr[size]);
                }
            }
            break;
        }
        default:
            throw dataType.invalid();
        }
    }
    @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
        if(dataType==DataType.BOOLEAN) {
            if(result) {
                var expectedArr=(boolean[])this.expectedArr;
                ++expectedModCount;
                if(filter.removedVals.contains(Boolean.TRUE)) {
                    if(filter.removedVals.contains(Boolean.FALSE)) {
                        Assertions.assertTrue(filter.retainedVals.isEmpty());
                        Assertions.assertEquals(0,filter.numRetained);
                        int expectedSize;
                        boolean firstVal=expectedArr[expectedSize=this.expectedSize-1];
                        for(;;) {
                            if(expectedArr[--expectedSize]^firstVal) {
                                //we are expecting this condition to be met before expectedSize==-1. Otherwise, something is wrong.
                                break;
                            }
                        }
                        this.expectedSize=0;
                    }else {
                        int i=0;
                        for(int expectedSize=this.expectedSize;;++i) {
                            if(expectedArr[i]) {
                                for(int j=i+1;j<expectedSize;++j) {
                                    if(!expectedArr[j]) {
                                        expectedArr[i++]=false;
                                    }
                                }
                                break;
                            }
                        }
                        this.expectedSize=i;
                    }
                }else {
                    int i=0;
                    for(int expectedSize=this.expectedSize;;++i) {
                        if(!expectedArr[i]) {
                            for(int j=i+1;j<expectedSize;++j) {
                                if(expectedArr[j]) {
                                    expectedArr[i++]=true;
                                }
                            }
                            break;
                        }
                    }
                    this.expectedSize=i;
                }
            }else {
                var expectedArr=((BooleanArrSeq)seq).arr;
                Assertions.assertTrue(filter.removedVals.isEmpty());
                if(filter.retainedVals.contains(Boolean.TRUE)) {
                    if(filter.retainedVals.contains(Boolean.FALSE)) {
                        int expectedSize;
                        boolean firstVal=expectedArr[expectedSize=this.expectedSize-1];
                        for(;;) {
                            if(expectedArr[--expectedSize]^firstVal) {
                                //we are expecting this condition to be met before expectedSize==-1. Otherwise, something is wrong.
                                break;
                            }
                        }
                    }else {
                        for(int i=this.expectedSize;--i>=0;) {
                            Assertions.assertTrue(expectedArr[i]);
                        }
                    }
                }else {
                    if(filter.retainedVals.contains(Boolean.FALSE)) {
                        for(int i=this.expectedSize;--i>=0;) {
                            Assertions.assertFalse(expectedArr[i]);
                        }
                    }else {
                        Assertions.assertEquals(0,expectedSize);
                    }
                }
            }
        }else {
            Assertions.assertEquals(expectedSize,filter.numCalls);
            if(result) {
                int i=0;
                switch(dataType) {
                case BYTE:{
                    var arr=(byte[])expectedArr;
                    for(int bound=this.expectedSize;;++i) {
                        var val=arr[i];
                        if(filter.removedVals.contains(val)) {
                            Assertions.assertFalse(filter.retainedVals.contains(val));
                            for(int j=i+1;j<bound;++j) {
                                if(filter.retainedVals.contains(val=arr[j])) {
                                    arr[i++]=val;
                                    Assertions.assertFalse(filter.removedVals.contains(val));
                                }else {
                                    Assertions.assertTrue(filter.removedVals.contains(val));
                                }
                            }
                            break;
                        }else {
                            Assertions.assertTrue(filter.retainedVals.contains(val));
                        }
                    }
                    break;
                }
                case CHAR:{
                    var arr=(char[])expectedArr;
                    for(int bound=this.expectedSize;;++i) {
                        var val=arr[i];
                        if(filter.removedVals.contains(val)) {
                            Assertions.assertFalse(filter.retainedVals.contains(val));
                            for(int j=i+1;j<bound;++j) {
                                if(filter.retainedVals.contains(val=arr[j])) {
                                    arr[i++]=val;
                                    Assertions.assertFalse(filter.removedVals.contains(val));
                                }else {
                                    Assertions.assertTrue(filter.removedVals.contains(val));
                                }
                            }
                            break;
                        }else {
                            Assertions.assertTrue(filter.retainedVals.contains(val));
                        }
                    }
                    break;
                }
                case DOUBLE:{
                    var arr=(double[])expectedArr;
                    for(int bound=this.expectedSize;;++i) {
                        var val=arr[i];
                        if(filter.removedVals.contains(val)) {
                            Assertions.assertFalse(filter.retainedVals.contains(val));
                            for(int j=i+1;j<bound;++j) {
                                if(filter.retainedVals.contains(val=arr[j])) {
                                    arr[i++]=val;
                                    Assertions.assertFalse(filter.removedVals.contains(val));
                                }else {
                                    Assertions.assertTrue(filter.removedVals.contains(val));
                                }
                            }
                            break;
                        }else {
                            Assertions.assertTrue(filter.retainedVals.contains(val));
                        }
                    }
                    break;
                }
                case FLOAT:{
                    var arr=(float[])expectedArr;
                    for(int bound=this.expectedSize;;++i) {
                        var val=arr[i];
                        if(filter.removedVals.contains(val)) {
                            Assertions.assertFalse(filter.retainedVals.contains(val));
                            for(int j=i+1;j<bound;++j) {
                                if(filter.retainedVals.contains(val=arr[j])) {
                                    arr[i++]=val;
                                    Assertions.assertFalse(filter.removedVals.contains(val));
                                }else {
                                    Assertions.assertTrue(filter.removedVals.contains(val));
                                }
                            }
                            break;
                        }else {
                            Assertions.assertTrue(filter.retainedVals.contains(val));
                        }
                    }
                    break;
                }
                case INT:{
                    var arr=(int[])expectedArr;
                    for(int bound=this.expectedSize;;++i) {
                        var val=arr[i];
                        if(filter.removedVals.contains(val)) {
                            Assertions.assertFalse(filter.retainedVals.contains(val));
                            for(int j=i+1;j<bound;++j) {
                                if(filter.retainedVals.contains(val=arr[j])) {
                                    arr[i++]=val;
                                    Assertions.assertFalse(filter.removedVals.contains(val));
                                }else {
                                    Assertions.assertTrue(filter.removedVals.contains(val));
                                }
                            }
                            break;
                        }else {
                            Assertions.assertTrue(filter.retainedVals.contains(val));
                        }
                    }
                    break;
                }
                case LONG:{
                    var arr=(long[])expectedArr;
                    for(int bound=this.expectedSize;;++i) {
                        var val=arr[i];
                        if(filter.removedVals.contains(val)) {
                            Assertions.assertFalse(filter.retainedVals.contains(val));
                            for(int j=i+1;j<bound;++j) {
                                if(filter.retainedVals.contains(val=arr[j])) {
                                    arr[i++]=val;
                                    Assertions.assertFalse(filter.removedVals.contains(val));
                                }else {
                                    Assertions.assertTrue(filter.removedVals.contains(val));
                                }
                            }
                            break;
                        }else {
                            Assertions.assertTrue(filter.retainedVals.contains(val));
                        }
                    }
                    break;
                }
                case REF:{
                    var arr=(Object[])expectedArr;
                    for(int bound=this.expectedSize;;++i) {
                        var val=arr[i];
                        if(filter.removedVals.contains(val)) {
                            Assertions.assertFalse(filter.retainedVals.contains(val));
                            for(int j=i+1;j<bound;++j) {
                                if(filter.retainedVals.contains(val=arr[j])) {
                                    arr[i++]=val;
                                    Assertions.assertFalse(filter.removedVals.contains(val));
                                }else {
                                    Assertions.assertTrue(filter.removedVals.contains(val));
                                }
                            }
                            break;
                        }else {
                            Assertions.assertTrue(filter.retainedVals.contains(val));
                        }
                    }
                    for(int expectedCapacity=this.expectedCapacity,j=i;++j<expectedCapacity;) {
                        arr[j]=null;
                    }
                    break;
                }
                case SHORT:{
                    var arr=(short[])expectedArr;
                    for(int bound=this.expectedSize;;++i) {
                        var val=arr[i];
                        if(filter.removedVals.contains(val)) {
                            Assertions.assertFalse(filter.retainedVals.contains(val));
                            for(int j=i+1;j<bound;++j) {
                                if(filter.retainedVals.contains(val=arr[j])) {
                                    arr[i++]=val;
                                    Assertions.assertFalse(filter.removedVals.contains(val));
                                }else {
                                    Assertions.assertTrue(filter.removedVals.contains(val));
                                }
                            }
                            break;
                        }else {
                            Assertions.assertTrue(filter.retainedVals.contains(val));
                        }
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
                Assertions.assertEquals(filter.numRemoved,expectedSize-i);
                Assertions.assertEquals(filter.numRetained,i);
                this.expectedSize=i;
            }else {
                Assertions.assertTrue(filter.removedVals.isEmpty());
                Assertions.assertEquals(0,filter.numRemoved);
                Assertions.assertEquals(expectedSize,filter.numRetained);
                var itr=seq.iterator();
                while(itr.hasNext()) {
                    Assertions.assertTrue(filter.retainedVals.contains(itr.next()));
                }
            }
        }
    }
    @Override
    public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
        if(expectedArr != null){
            if(expectedCapacity == 0 && emptyArrayMayBeSame){
                outer:for(;;){
                    switch(dataType){
                    case BOOLEAN:
                        if(arr instanceof boolean[]){
                            break;
                        }
                        break outer;
                    case BYTE:
                        if(arr instanceof byte[]){
                            break;
                        }
                        break outer;
                    case CHAR:
                        if(arr instanceof char[]){
                            break;
                        }
                        break outer;
                    case SHORT:
                        if(arr instanceof short[]){
                            break;
                        }
                        break outer;
                    case INT:
                        if(arr instanceof int[]){
                            break;
                        }
                        break outer;
                    case LONG:
                        if(arr instanceof long[]){
                            break;
                        }
                        break outer;
                    case FLOAT:
                        if(arr instanceof float[]){
                            break;
                        }
                        break outer;
                    case DOUBLE:
                        if(arr instanceof double[]){
                            break;
                        }
                        break outer;
                    case REF:
                        if(arr instanceof Object[]){
                            break;
                        }
                        break outer;
                    }
                    Assertions.assertSame(expectedArr,arr);
                    return;
                }
            }
            Assertions.assertNotSame(expectedArr,arr);
        }

    }
    @Override public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
        ((Externalizable)seq).writeExternal(oos);
    }
    public void verifyGetResult(int expectedCursor,Object output,DataType outputType){
        switch(outputType){
        case BOOLEAN:
            Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor],(boolean)output);
            break;
        case BYTE:{
            var v=(byte)output;
            switch(dataType){
            case BOOLEAN:
                Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?(byte)1:(byte)0,v);
                break;
            case BYTE:
                Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
                break;
            default:
                throw dataType.invalid();
            }
            break;
        }
        case CHAR:{
            var v=(char)output;
            switch(dataType){
            case BOOLEAN:
                Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?(char)1:(char)0,v);
                break;
            case CHAR:
                Assertions.assertEquals(((char[])expectedArr)[expectedCursor],v);
                break;
            default:
                throw dataType.invalid();
            }
            break;
        }
        case SHORT:{
            var v=(short)output;
            switch(dataType){
            case BOOLEAN:
                Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?(short)1:(short)0,v);
                break;
            case BYTE:
                Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
                break;
            case SHORT:
                Assertions.assertEquals(((short[])expectedArr)[expectedCursor],v);
                break;
            default:
                throw dataType.invalid();
            }
            break;
        }
        case INT:{
            var v=(int)output;
            switch(dataType){
            case BOOLEAN:
                Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?1:0,v);
                break;
            case BYTE:
                Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
                break;
            case CHAR:
                Assertions.assertEquals(((char[])expectedArr)[expectedCursor],v);
                break;
            case SHORT:
                Assertions.assertEquals(((short[])expectedArr)[expectedCursor],v);
                break;
            case INT:
                Assertions.assertEquals(((int[])expectedArr)[expectedCursor],v);
                break;
            default:
                throw dataType.invalid();
            }
            break;
        }
        case LONG:{
            var v=(long)output;
            switch(dataType){
            case BOOLEAN:
                Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?1L:0L,v);
                break;
            case BYTE:
                Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
                break;
            case CHAR:
                Assertions.assertEquals(((char[])expectedArr)[expectedCursor],v);
                break;
            case SHORT:
                Assertions.assertEquals(((short[])expectedArr)[expectedCursor],v);
                break;
            case INT:
                Assertions.assertEquals(((int[])expectedArr)[expectedCursor],v);
                break;
            case LONG:
                Assertions.assertEquals(((long[])expectedArr)[expectedCursor],v);
                break;
            default:
                throw dataType.invalid();
            }
            break;
        }
        case FLOAT:{
            var v=(float)output;
            switch(dataType){
            case BOOLEAN:
                Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?1F:0F,v);
                break;
            case BYTE:
                Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
                break;
            case CHAR:
                Assertions.assertEquals(((char[])expectedArr)[expectedCursor],v);
                break;
            case SHORT:
                Assertions.assertEquals(((short[])expectedArr)[expectedCursor],v);
                break;
            case INT:
                Assertions.assertEquals(((int[])expectedArr)[expectedCursor],v);
                break;
            case LONG:
                Assertions.assertEquals(((long[])expectedArr)[expectedCursor],v);
                break;
            case FLOAT:
                Assertions.assertEquals(((float[])expectedArr)[expectedCursor],v);
                break;
            default:
                throw dataType.invalid();
            }
            break;
        }
        case DOUBLE:{
            var v=(double)output;
            switch(dataType){
            case BOOLEAN:
                Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor]?1D:0D,v);
                break;
            case BYTE:
                Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],v);
                break;
            case CHAR:
                Assertions.assertEquals(((char[])expectedArr)[expectedCursor],v);
                break;
            case SHORT:
                Assertions.assertEquals(((short[])expectedArr)[expectedCursor],v);
                break;
            case INT:
                Assertions.assertEquals(((int[])expectedArr)[expectedCursor],v);
                break;
            case LONG:
                Assertions.assertEquals(((long[])expectedArr)[expectedCursor],v);
                break;
            case FLOAT:
                Assertions.assertEquals(((float[])expectedArr)[expectedCursor],v);
                break;
            case DOUBLE:
                Assertions.assertEquals(((double[])expectedArr)[expectedCursor],v);
                break;
            default:
                throw dataType.invalid();
            }
            break;
        }
        case REF:{
            switch(dataType){
            case BOOLEAN:
                Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor],output);
                break;
            case BYTE:
                Assertions.assertEquals(((byte[])expectedArr)[expectedCursor],output);
                break;
            case CHAR:
                Assertions.assertEquals(((char[])expectedArr)[expectedCursor],output);
                break;
            case SHORT:
                Assertions.assertEquals(((short[])expectedArr)[expectedCursor],output);
                break;
            case INT:
                Assertions.assertEquals(((int[])expectedArr)[expectedCursor],output);
                break;
            case LONG:
                Assertions.assertEquals(((long[])expectedArr)[expectedCursor],output);
                break;
            case FLOAT:
                Assertions.assertEquals(((float[])expectedArr)[expectedCursor],output);
                break;
            case DOUBLE:
                Assertions.assertEquals(((double[])expectedArr)[expectedCursor],output);
                break;
            case REF:
                Assertions.assertSame(((Object[])expectedArr)[expectedCursor],output);
                break;
            default:
                throw dataType.invalid();
            }
            break;
        }
        }
    }
    public void updateAddState(int index,Object inputVal,DataType inputType){
        switch(dataType){
        case BOOLEAN:{
            var expectedArr=(boolean[])this.expectedArr;
            if(expectedSize == expectedCapacity){
                if(expectedArr == OmniArray.OfBoolean.DEFAULT_ARR){
                    this.expectedArr=expectedArr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
                }else if(expectedArr == null){
                    this.expectedArr=expectedArr=new boolean[1];
                }else{
                    var newArr=new boolean[OmniArray.growBy50Pct(expectedCapacity)];
                    System.arraycopy(expectedArr,0,newArr,0,index);
                    System.arraycopy(expectedArr,index,newArr,index + 1,expectedSize - index);
                    this.expectedArr=expectedArr=newArr;
                }
                expectedCapacity=expectedArr.length;
            }else{
                System.arraycopy(expectedArr,index,expectedArr,index + 1,expectedSize - index);
            }
            expectedArr[index]=(boolean)inputVal;
            break;
        }
        case BYTE:{
            byte inputCast;
            switch(inputType){
            case BOOLEAN:
                inputCast=(boolean)inputVal?(byte)1:(byte)0;
                break;
            case BYTE:
                inputCast=(byte)inputVal;
                break;
            default:
                throw inputType.invalid();
            }
            var expectedArr=(byte[])this.expectedArr;
            if(expectedSize == expectedCapacity){
                if(expectedArr == OmniArray.OfByte.DEFAULT_ARR){
                    this.expectedArr=expectedArr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
                }else if(expectedArr == null){
                    this.expectedArr=expectedArr=new byte[1];
                }else{
                    var newArr=new byte[OmniArray.growBy50Pct(expectedCapacity)];
                    System.arraycopy(expectedArr,0,newArr,0,index);
                    System.arraycopy(expectedArr,index,newArr,index + 1,expectedSize - index);
                    this.expectedArr=expectedArr=newArr;
                }
                expectedCapacity=expectedArr.length;
            }else{
                System.arraycopy(expectedArr,index,expectedArr,index + 1,expectedSize - index);
            }
            expectedArr[index]=inputCast;
            break;
        }
        case CHAR:{
            char inputCast;
            switch(inputType){
            case BOOLEAN:
                inputCast=(boolean)inputVal?(char)1:(char)0;
                break;
            case CHAR:
                inputCast=(char)inputVal;
                break;
            default:
                throw inputType.invalid();
            }
            var expectedArr=(char[])this.expectedArr;
            if(expectedSize == expectedCapacity){
                if(expectedArr == OmniArray.OfChar.DEFAULT_ARR){
                    this.expectedArr=expectedArr=new char[OmniArray.DEFAULT_ARR_SEQ_CAP];
                }else if(expectedArr == null){
                    this.expectedArr=expectedArr=new char[1];
                }else{
                    var newArr=new char[OmniArray.growBy50Pct(expectedCapacity)];
                    System.arraycopy(expectedArr,0,newArr,0,index);
                    System.arraycopy(expectedArr,index,newArr,index + 1,expectedSize - index);
                    this.expectedArr=expectedArr=newArr;
                }
                expectedCapacity=expectedArr.length;
            }else{
                System.arraycopy(expectedArr,index,expectedArr,index + 1,expectedSize - index);
            }
            expectedArr[index]=inputCast;
            break;
        }
        case SHORT:{
            short inputCast;
            switch(inputType){
            case BOOLEAN:
                inputCast=(boolean)inputVal?(short)1:(short)0;
                break;
            case BYTE:
                inputCast=(byte)inputVal;
                break;
            case SHORT:
                inputCast=(short)inputVal;
                break;
            default:
                throw inputType.invalid();
            }
            var expectedArr=(short[])this.expectedArr;
            if(expectedSize == expectedCapacity){
                if(expectedArr == OmniArray.OfShort.DEFAULT_ARR){
                    this.expectedArr=expectedArr=new short[OmniArray.DEFAULT_ARR_SEQ_CAP];
                }else if(expectedArr == null){
                    this.expectedArr=expectedArr=new short[1];
                }else{
                    var newArr=new short[OmniArray.growBy50Pct(expectedCapacity)];
                    System.arraycopy(expectedArr,0,newArr,0,index);
                    System.arraycopy(expectedArr,index,newArr,index + 1,expectedSize - index);
                    this.expectedArr=expectedArr=newArr;
                }
                expectedCapacity=expectedArr.length;
            }else{
                System.arraycopy(expectedArr,index,expectedArr,index + 1,expectedSize - index);
            }
            expectedArr[index]=inputCast;
            break;
        }
        case INT:{
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
            default:
                throw inputType.invalid();
            }
            var expectedArr=(int[])this.expectedArr;
            if(expectedSize == expectedCapacity){
                if(expectedArr == OmniArray.OfInt.DEFAULT_ARR){
                    this.expectedArr=expectedArr=new int[OmniArray.DEFAULT_ARR_SEQ_CAP];
                }else if(expectedArr == null){
                    this.expectedArr=expectedArr=new int[1];
                }else{
                    var newArr=new int[OmniArray.growBy50Pct(expectedCapacity)];
                    System.arraycopy(expectedArr,0,newArr,0,index);
                    System.arraycopy(expectedArr,index,newArr,index + 1,expectedSize - index);
                    this.expectedArr=expectedArr=newArr;
                }
                expectedCapacity=expectedArr.length;
            }else{
                System.arraycopy(expectedArr,index,expectedArr,index + 1,expectedSize - index);
            }
            expectedArr[index]=inputCast;
            break;
        }
        case LONG:{
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
            default:
                throw inputType.invalid();
            }
            var expectedArr=(long[])this.expectedArr;
            if(expectedSize == expectedCapacity){
                if(expectedArr == OmniArray.OfLong.DEFAULT_ARR){
                    this.expectedArr=expectedArr=new long[OmniArray.DEFAULT_ARR_SEQ_CAP];
                }else if(expectedArr == null){
                    this.expectedArr=expectedArr=new long[1];
                }else{
                    var newArr=new long[OmniArray.growBy50Pct(expectedCapacity)];
                    System.arraycopy(expectedArr,0,newArr,0,index);
                    System.arraycopy(expectedArr,index,newArr,index + 1,expectedSize - index);
                    this.expectedArr=expectedArr=newArr;
                }
                expectedCapacity=expectedArr.length;
            }else{
                System.arraycopy(expectedArr,index,expectedArr,index + 1,expectedSize - index);
            }
            expectedArr[index]=inputCast;
            break;
        }
        case FLOAT:{
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
            default:
                throw inputType.invalid();
            }
            var expectedArr=(float[])this.expectedArr;
            if(expectedSize == expectedCapacity){
                if(expectedArr == OmniArray.OfFloat.DEFAULT_ARR){
                    this.expectedArr=expectedArr=new float[OmniArray.DEFAULT_ARR_SEQ_CAP];
                }else if(expectedArr == null){
                    this.expectedArr=expectedArr=new float[1];
                }else{
                    var newArr=new float[OmniArray.growBy50Pct(expectedCapacity)];
                    System.arraycopy(expectedArr,0,newArr,0,index);
                    System.arraycopy(expectedArr,index,newArr,index + 1,expectedSize - index);
                    this.expectedArr=expectedArr=newArr;
                }
                expectedCapacity=expectedArr.length;
            }else{
                System.arraycopy(expectedArr,index,expectedArr,index + 1,expectedSize - index);
            }
            expectedArr[index]=inputCast;
            break;
        }
        case DOUBLE:{
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
            var expectedArr=(double[])this.expectedArr;
            if(expectedSize == expectedCapacity){
                if(expectedArr == OmniArray.OfDouble.DEFAULT_ARR){
                    this.expectedArr=expectedArr=new double[OmniArray.DEFAULT_ARR_SEQ_CAP];
                }else if(expectedArr == null){
                    this.expectedArr=expectedArr=new double[1];
                }else{
                    var newArr=new double[OmniArray.growBy50Pct(expectedCapacity)];
                    System.arraycopy(expectedArr,0,newArr,0,index);
                    System.arraycopy(expectedArr,index,newArr,index + 1,expectedSize - index);
                    this.expectedArr=expectedArr=newArr;
                }
                expectedCapacity=expectedArr.length;
            }else{
                System.arraycopy(expectedArr,index,expectedArr,index + 1,expectedSize - index);
            }
            expectedArr[index]=inputCast;
            break;
        }
        case REF:{
            var expectedArr=(Object[])this.expectedArr;
            if(expectedSize == expectedCapacity){
                if(expectedArr == OmniArray.OfRef.DEFAULT_ARR){
                    this.expectedArr=expectedArr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
                }else if(expectedArr == null){
                    this.expectedArr=expectedArr=new Object[1];
                }else{
                    var newArr=new Object[OmniArray.growBy50Pct(expectedCapacity)];
                    System.arraycopy(expectedArr,0,newArr,0,index);
                    System.arraycopy(expectedArr,index,newArr,index + 1,expectedSize - index);
                    this.expectedArr=expectedArr=newArr;
                }
                expectedCapacity=expectedArr.length;
            }else{
                System.arraycopy(expectedArr,index,expectedArr,index + 1,expectedSize - index);
            }
            expectedArr[index]=inputVal;
            break;
        }
        default:
            throw dataType.invalid();
        }
        ++expectedModCount;
        ++expectedSize;
    }
    @Override
    public void updateAddState(Object inputVal,DataType inputType){
        updateAddState(expectedSize,inputVal,inputType);
    }
    abstract int findRemoveValIndex(Object inputVal,DataType inputType);
    @Override
    public void updateRemoveValState(Object inputVal,DataType inputType){
        int index=findRemoveValIndex(inputVal,inputType);
        updateRemoveIndexState(index);
    }
    public void updateRemoveIndexState(int index){
        System.arraycopy(expectedArr,index + 1,expectedArr,index,(--expectedSize) - index);
        if(dataType == DataType.REF){
            ((Object[])expectedArr)[expectedSize]=null;
        }
        ++expectedModCount;
    }
    abstract void updateModCount();
    abstract void verifyModCount();
    abstract void verifyCloneTypeAndModCount(Object clone);
}
