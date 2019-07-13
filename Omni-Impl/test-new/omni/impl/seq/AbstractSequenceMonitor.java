package omni.impl.seq;


import java.util.ConcurrentModificationException;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniList;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredSequence;
import omni.util.OmniArray;

public abstract class AbstractSequenceMonitor<SEQ extends AbstractSeq<?>> implements MonitoredSequence<SEQ>{
    final DataType dataType;
    final CheckedType checkedType;
    final SEQ seq;
    int expectedSize;
    int expectedCapacity;
    int expectedModCount;
    Object expectedArr;
    AbstractSequenceMonitor(CheckedType checkedType,DataType dataType){
        this.dataType=dataType;
        this.checkedType=checkedType;
        this.seq=initSeq();
        updateCollectionState();
    }
    AbstractSequenceMonitor(CheckedType checkedType,DataType dataType,int initCap){
        this.dataType=dataType;
        this.checkedType=checkedType;
        this.seq=initSeq(initCap);
        updateCollectionState();
    }
    public void updateReplaceAllState(MonitoredFunction function){
        updateReplaceAllState(function,0);
    }
    public void updateReplaceAllState(MonitoredFunction function,int index){
        if(function.getMonitoredFunctionGen().expectedException != ConcurrentModificationException.class
                && !function.isEmpty()){
            ++expectedModCount;
        }
        switch(dataType){
        case BOOLEAN:{
            final var expectedArr=(boolean[])this.expectedArr;
            for(final var oldVal:function){
                expectedArr[index]=!(boolean)oldVal;
                ++index;
            }
            break;
        }
        case BYTE:{
            final var expectedArr=(byte[])this.expectedArr;
            for(final var oldVal:function){
                expectedArr[index]=(byte)((byte)oldVal + Byte.MAX_VALUE);
                ++index;
            }
            break;
        }
        case CHAR:{
            final var expectedArr=(char[])this.expectedArr;
            for(final var oldVal:function){
                expectedArr[index]=(char)((char)oldVal + Character.MAX_VALUE);
                ++index;
            }
            break;
        }
        case DOUBLE:{
            final var expectedArr=(double[])this.expectedArr;
            for(final var oldVal:function){
                expectedArr[index]=-(double)oldVal;
                ++index;
            }
            break;
        }
        case FLOAT:{
            final var expectedArr=(float[])this.expectedArr;
            for(final var oldVal:function){
                expectedArr[index]=-(float)oldVal;
                ++index;
            }
            break;
        }
        case INT:{
            final var expectedArr=(int[])this.expectedArr;
            for(final var oldVal:function){
                expectedArr[index]=(int)oldVal + Integer.MAX_VALUE;
                ++index;
            }
            break;
        }
        case LONG:{
            final var expectedArr=(long[])this.expectedArr;
            for(final var oldVal:function){
                expectedArr[index]=(long)oldVal + Long.MAX_VALUE;
                ++index;
            }
            break;
        }
        case REF:{
            final var expectedArr=(Object[])this.expectedArr;
            for(final var oldVal:function){
                expectedArr[index]=(int)((int)oldVal + Integer.MAX_VALUE);
                ++index;
            }
            break;
        }
        case SHORT:{
            final var expectedArr=(short[])this.expectedArr;
            for(final var oldVal:function){
                expectedArr[index]=(short)((short)oldVal + Short.MAX_VALUE);
                ++index;
            }
            break;
        }
        default:
            throw dataType.invalid();
        }
    }
    public abstract void copyListContents();
    @Override
    public CheckedType getCheckedType(){
        return checkedType;
    }
    @Override
    public SEQ getCollection(){
        return seq;
    }
    @Override
    public DataType getDataType(){
        return dataType;
    }
    public void incrementModCount(){
        ++expectedModCount;
    }
    @Override
    public void modParent(){
        throw new UnsupportedOperationException();
    }
    @Override
    public void modRoot(){
        throw new UnsupportedOperationException();
    }
    @Override
    public int size(){
        return expectedSize;
    }
    public void verifyPutResult(int index,Object input,DataType inputType){
        final Object expectedVal=dataType.convertValUnchecked(inputType,input);
        switch(dataType){
        case BOOLEAN:
            Assertions.assertEquals((boolean)expectedVal,((OmniList.OfBoolean)seq).getBoolean(index));
            ((boolean[])expectedArr)[index]=(boolean)expectedVal;
            break;
        case BYTE:
            Assertions.assertEquals((byte)expectedVal,((OmniList.OfByte)seq).getByte(index));
            ((byte[])expectedArr)[index]=(byte)expectedVal;
            break;
        case CHAR:
            Assertions.assertEquals((char)expectedVal,((OmniList.OfChar)seq).getChar(index));
            ((char[])expectedArr)[index]=(char)expectedVal;
            break;
        case SHORT:
            Assertions.assertEquals((short)expectedVal,((OmniList.OfShort)seq).getShort(index));
            ((short[])expectedArr)[index]=(short)expectedVal;
            break;
        case INT:
            Assertions.assertEquals((int)expectedVal,((OmniList.OfInt)seq).getInt(index));
            ((int[])expectedArr)[index]=(int)expectedVal;
            break;
        case LONG:
            Assertions.assertEquals((long)expectedVal,((OmniList.OfLong)seq).getLong(index));
            ((long[])expectedArr)[index]=(long)expectedVal;
            break;
        case FLOAT:
            Assertions.assertEquals((float)expectedVal,((OmniList.OfFloat)seq).getFloat(index));
            ((float[])expectedArr)[index]=(float)expectedVal;
            break;
        case DOUBLE:
            Assertions.assertEquals((double)expectedVal,((OmniList.OfDouble)seq).getDouble(index));
            ((double[])expectedArr)[index]=(double)expectedVal;
            break;
        case REF:
            Assertions.assertEquals(expectedVal,((OmniList.OfRef<?>)seq).get(index));
            ((Object[])expectedArr)[index]=expectedVal;
            break;
        default:
            throw dataType.invalid();
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
                    final var newArr=new boolean[OmniArray.growBy50Pct(expectedCapacity)];
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
                    final var newArr=new byte[OmniArray.growBy50Pct(expectedCapacity)];
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
                    final var newArr=new char[OmniArray.growBy50Pct(expectedCapacity)];
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
                    final var newArr=new short[OmniArray.growBy50Pct(expectedCapacity)];
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
                    final var newArr=new int[OmniArray.growBy50Pct(expectedCapacity)];
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
                    final var newArr=new long[OmniArray.growBy50Pct(expectedCapacity)];
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
                    final var newArr=new float[OmniArray.growBy50Pct(expectedCapacity)];
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
                    final var newArr=new double[OmniArray.growBy50Pct(expectedCapacity)];
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
                    final var newArr=new Object[OmniArray.growBy50Pct(expectedCapacity)];
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
    @Override
    public void updateClearState(){
        
        ++expectedModCount;
        if(dataType == DataType.REF){
            final var arr=(Object[])expectedArr;
            for(int i=expectedSize;--i >= 0;){
                arr[i]=null;
            }
        }
        expectedSize=0;
    }
    @Override
    public void updateCollectionState(){
        
        if(checkedType.checked){
            updateModCount();
        }
        copyListContents();
        this.expectedSize=((AbstractSeq<?>)seq).size;
    }
    @Override
    public void updateRemoveIndexState(int index){
        System.arraycopy(expectedArr,index + 1,expectedArr,index,(--expectedSize) - index);
        if(dataType == DataType.REF){
            ((Object[])expectedArr)[expectedSize]=null;
        }
        ++expectedModCount;
    }
    @Override
    public void updateRemoveValState(Object inputVal,DataType inputType){
        final int index=findRemoveValIndex(inputVal,inputType,0,expectedSize);
        updateRemoveIndexState(index);
    }


    @Override
    public void verifyGetResult(int expectedCursor,Object output,DataType outputType){
        switch(outputType){
        case BOOLEAN:
            Assertions.assertEquals(((boolean[])expectedArr)[expectedCursor],(boolean)output);
            break;
        case BYTE:{
            final var v=(byte)output;
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
            final var v=(char)output;
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
            final var v=(short)output;
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
            final var v=(int)output;
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
            final var v=(long)output;
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
            final var v=(float)output;
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
            final var v=(double)output;
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
  

    abstract int findRemoveValIndex(Object inputVal,DataType inputType,int fromIndex,int toIndex);
    abstract SEQ initSeq();
    abstract SEQ initSeq(int initCap);
    abstract void updateModCount();
}
