package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorType;
import omni.impl.MonitoredList;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
public class ArrListTest{
  private static final int[] INIT_CAPACITIES=new int[]{0,5,10,15};
  @Test public void testclone_void(){
    BasicTest test=(monitor)->monitor.verifyClone();
    test.runAllTests("ArrListTest.testclone_void");
  }
  @Test public void testisEmpty_void(){
    BasicTest test=(monitor)->monitor.verifyIsEmpty();
    test.runAllTests("ArrListTest.testisEmpty_void");
  }
  @Test public void testclear_void(){
    BasicTest test=(monitor)->monitor.verifyClear();
    test.runAllTests("ArrListTest.testclear_void");
  }
  @Test public void testConstructor_int(){
    for(final var dataType:DataType.values()){
      for(final var checkedType:CheckedType.values()){
        for(final var initCap:INIT_CAPACITIES){
          TestExecutorService.submitTest(()->{
            final var monitor=new ArrListMonitor(checkedType,dataType,initCap);
            switch(initCap){
            case 0:
              Assertions.assertNull(monitor.expectedArr);
              break;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
              Object expectedArr;
              switch(dataType){
              case BOOLEAN:
                expectedArr=OmniArray.OfBoolean.DEFAULT_ARR;
                break;
              case BYTE:
                expectedArr=OmniArray.OfByte.DEFAULT_ARR;
                break;
              case CHAR:
                expectedArr=OmniArray.OfChar.DEFAULT_ARR;
                break;
              case SHORT:
                expectedArr=OmniArray.OfShort.DEFAULT_ARR;
                break;
              case INT:
                expectedArr=OmniArray.OfInt.DEFAULT_ARR;
                break;
              case LONG:
                expectedArr=OmniArray.OfLong.DEFAULT_ARR;
                break;
              case FLOAT:
                expectedArr=OmniArray.OfFloat.DEFAULT_ARR;
                break;
              case DOUBLE:
                expectedArr=OmniArray.OfDouble.DEFAULT_ARR;
                break;
              case REF:
                expectedArr=OmniArray.OfRef.DEFAULT_ARR;
                break;
              default:
                throw dataType.invalid();
              }
              Assertions.assertSame(expectedArr,monitor.expectedArr);
              break;
            default:
              Assertions.assertEquals(initCap,monitor.expectedCapacity);
            }
            monitor.verifyCollectionState();
          });
        }
      }
    }
    TestExecutorService.completeAllTests("ArrListTest.testConstructor_int");
  }
  @Test public void testConstructor_void(){
    for(final var dataType:DataType.values()){
      for(final var checkedType:CheckedType.values()){
        TestExecutorService.submitTest(()->new ArrListMonitor(checkedType,dataType).verifyCollectionState());
      }
    }
    TestExecutorService.completeAllTests("ArrListTest.testConstructor_void");
  }
  @Test public void testadd_val() {
    for(var collectionType:DataType.values()) {
      for(var inputType:collectionType.mayBeAddedTo()) {
        for(var functionCallType:FunctionCallType.values()) {
          if(inputType!=DataType.REF || functionCallType!=FunctionCallType.Boxed) {
            for(var initCap:INIT_CAPACITIES) {
              for(var checkedType:CheckedType.values()) {
                TestExecutorService.submitTest(()->{
                  var monitor=new ArrListMonitor(checkedType,collectionType,initCap);
                  for(int i=0;i<1000;++i) {
                    monitor.verifyAdd(inputType.convertValUnchecked(i),inputType,functionCallType);
                  }
                });
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("ArrListTest.testadd_val");
  }
  @Test public void testsize_void() {
    BasicTest test=(monitor)->monitor.verifySize();
    test.runAllTests("ArrListTest.testsize_void");
  }
  private static interface BasicTest{
    
    void runTest(ArrListMonitor monitor);
    
    default void runAllTests(String testName) {
      for(int seqSize:FIB_SEQ) {
        if(seqSize>100) {
          break;
        }
        for(var dataType:DataType.values()) {
          for(var checkedType:CheckedType.values()) {
            for(var initCap:INIT_CAPACITIES) {
              TestExecutorService.submitTest(()->runTest(SequenceInitialization.Ascending.initialize(new ArrListMonitor(checkedType,dataType,initCap),seqSize,0)));
            }
          }
        }
      }
      TestExecutorService.completeAllTests("ArrListTest.testsize_void");
    }
  }
  private static class ArrListMonitor extends AbstractArrSeqMonitor<OmniList<?>>
      implements MonitoredList<OmniIterator<?>,OmniList<?>>{
    public ArrListMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
    }
    public ArrListMonitor(CheckedType checkedType,DataType dataType,int initCap){
      super(checkedType,dataType,initCap);
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,OmniList<?>> getMonitoredIterator(){
      // TODO Auto-generated method stub
      return null;
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,OmniList<?>>
        getMonitoredIterator(IteratorType itrType){
      // TODO Auto-generated method stub
      return null;
    }
    @Override public StructType getStructType(){
      return StructType.ArrList;
    }
    @Override public void modCollection(){
      switch(dataType){
      case BOOLEAN:
        ++((BooleanArrSeq.CheckedList)seq).modCount;
        break;
      case BYTE:
        ++((ByteArrSeq.CheckedList)seq).modCount;
        break;
      case CHAR:
        ++((CharArrSeq.CheckedList)seq).modCount;
        break;
      case DOUBLE:
        ++((DoubleArrSeq.CheckedList)seq).modCount;
        break;
      case FLOAT:
        ++((FloatArrSeq.CheckedList)seq).modCount;
        break;
      case INT:
        ++((IntArrSeq.CheckedList)seq).modCount;
        break;
      case LONG:
        ++((LongArrSeq.CheckedList)seq).modCount;
        break;
      case REF:
        ++((RefArrSeq.CheckedList<?>)seq).modCount;
        break;
      case SHORT:
        ++((ShortArrSeq.CheckedList)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
      ++expectedModCount;
    }
    @Override public void updateRemoveValState(Object inputVal,DataType inputType){
      final int expectedSize=this.expectedSize;
      switch(dataType){
      case BOOLEAN:{
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
        final var expectedArr=(boolean[])this.expectedArr;
        for(int i=0;;++i){
          if(expectedArr[i] == inputCast){
            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
            break;
          }
        }
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
        final var expectedArr=(byte[])this.expectedArr;
        for(int i=0;;++i){
          if(expectedArr[i] == inputCast){
            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
            break;
          }
        }
        break;
      }
      case CHAR:{
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
        final var expectedArr=(char[])this.expectedArr;
        for(int i=0;;++i){
          if(expectedArr[i] == inputCast){
            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
            break;
          }
        }
        break;
      }
      case SHORT:{
        short inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?(short)1:(short)0;
          break;
        case BYTE:
          inputCast=(short)(byte)inputVal;
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
        final var expectedArr=(short[])this.expectedArr;
        for(int i=0;;++i){
          if(expectedArr[i] == inputCast){
            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
            break;
          }
        }
        break;
      }
      case INT:{
        int inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?1:0;
          break;
        case BYTE:
          inputCast=(int)(byte)inputVal;
          break;
        case CHAR:
          inputCast=(int)(char)inputVal;
          break;
        case SHORT:
          inputCast=(int)(short)inputVal;
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
        final var expectedArr=(int[])this.expectedArr;
        for(int i=0;;++i){
          if(expectedArr[i] == inputCast){
            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
            break;
          }
        }
        break;
      }
      case LONG:{
        long inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?1L:0L;
          break;
        case BYTE:
          inputCast=(long)(byte)inputVal;
          break;
        case CHAR:
          inputCast=(long)(char)inputVal;
          break;
        case SHORT:
          inputCast=(long)(short)inputVal;
          break;
        case INT:
          inputCast=(long)(int)inputVal;
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
        final var expectedArr=(long[])this.expectedArr;
        for(int i=0;;++i){
          if(expectedArr[i] == inputCast){
            System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
            break;
          }
        }
        break;
      }
      case FLOAT:{
        float inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?1.0f:0.0f;
          break;
        case BYTE:
          inputCast=(float)(byte)inputVal;
          break;
        case CHAR:
          inputCast=(float)(char)inputVal;
          break;
        case SHORT:
          inputCast=(float)(short)inputVal;
          break;
        case INT:
          inputCast=(float)(int)inputVal;
          break;
        case LONG:
          inputCast=(float)(long)inputVal;
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
        final var expectedArr=(float[])this.expectedArr;
        if(inputCast == inputCast){
          for(int i=0;;++i){
            if(expectedArr[i] == inputCast){
              System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
              break;
            }
          }
        }else{
          for(int i=0;;++i){
            float v;
            if((v=expectedArr[i]) != v){
              System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
              break;
            }
          }
        }
        break;
      }
      case DOUBLE:{
        double inputCast;
        switch(inputType){
        case BOOLEAN:
          inputCast=(boolean)inputVal?1.0:0.0;
          break;
        case BYTE:
          inputCast=(double)(byte)inputVal;
          break;
        case CHAR:
          inputCast=(double)(char)inputVal;
          break;
        case SHORT:
          inputCast=(double)(short)inputVal;
          break;
        case INT:
          inputCast=(double)(int)inputVal;
          break;
        case LONG:
          inputCast=(double)(long)inputVal;
          break;
        case FLOAT:
          inputCast=(double)(float)inputVal;
          break;
        case DOUBLE:
          inputCast=(double)inputVal;
          break;
        default:
          throw inputType.invalid();
        }
        final var expectedArr=(double[])this.expectedArr;
        if(inputCast == inputCast){
          for(int i=0;;++i){
            if(expectedArr[i] == inputCast){
              System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
              break;
            }
          }
        }else{
          for(int i=0;;++i){
            double v;
            if((v=expectedArr[i]) != v){
              System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
              break;
            }
          }
        }
        break;
      }
      case REF:{
        final var expectedArr=(Object[])this.expectedArr;
        if(inputVal == null){
          for(int i=0;;++i){
            if(expectedArr[i] == null){
              System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
              break;
            }
          }
        }else{
          for(int i=0;;++i){
            if(inputVal.equals(expectedArr[i])){
              System.arraycopy(expectedArr,i + 1,expectedArr,i,expectedSize - i - 1);
              break;
            }
          }
        }
        expectedArr[expectedSize]=null;
        break;
      }
      default:
        throw dataType.invalid();
      }
      --this.expectedSize;
      ++expectedModCount;
    }
    @Override OmniList<?> initSeq(){
      switch(dataType){
      case BOOLEAN:
        if(checkedType.checked){
          return new BooleanArrSeq.CheckedList();
        }else{
          return new BooleanArrSeq.UncheckedList();
        }
      case BYTE:
        if(checkedType.checked){
          return new ByteArrSeq.CheckedList();
        }else{
          return new ByteArrSeq.UncheckedList();
        }
      case CHAR:
        if(checkedType.checked){
          return new CharArrSeq.CheckedList();
        }else{
          return new CharArrSeq.UncheckedList();
        }
      case DOUBLE:
        if(checkedType.checked){
          return new DoubleArrSeq.CheckedList();
        }else{
          return new DoubleArrSeq.UncheckedList();
        }
      case FLOAT:
        if(checkedType.checked){
          return new FloatArrSeq.CheckedList();
        }else{
          return new FloatArrSeq.UncheckedList();
        }
      case INT:
        if(checkedType.checked){
          return new IntArrSeq.CheckedList();
        }else{
          return new IntArrSeq.UncheckedList();
        }
      case LONG:
        if(checkedType.checked){
          return new LongArrSeq.CheckedList();
        }else{
          return new LongArrSeq.UncheckedList();
        }
      case REF:
        if(checkedType.checked){
          return new RefArrSeq.CheckedList<>();
        }else{
          return new RefArrSeq.UncheckedList<>();
        }
      case SHORT:
        if(checkedType.checked){
          return new ShortArrSeq.CheckedList();
        }else{
          return new ShortArrSeq.UncheckedList();
        }
      default:
        throw dataType.invalid();
      }
    }
    @Override OmniList<?> initSeq(int initCap){
      switch(dataType){
      case BOOLEAN:
        if(checkedType.checked){
          return new BooleanArrSeq.CheckedList(initCap);
        }else{
          return new BooleanArrSeq.UncheckedList(initCap);
        }
      case BYTE:
        if(checkedType.checked){
          return new ByteArrSeq.CheckedList(initCap);
        }else{
          return new ByteArrSeq.UncheckedList(initCap);
        }
      case CHAR:
        if(checkedType.checked){
          return new CharArrSeq.CheckedList(initCap);
        }else{
          return new CharArrSeq.UncheckedList(initCap);
        }
      case DOUBLE:
        if(checkedType.checked){
          return new DoubleArrSeq.CheckedList(initCap);
        }else{
          return new DoubleArrSeq.UncheckedList(initCap);
        }
      case FLOAT:
        if(checkedType.checked){
          return new FloatArrSeq.CheckedList(initCap);
        }else{
          return new FloatArrSeq.UncheckedList(initCap);
        }
      case INT:
        if(checkedType.checked){
          return new IntArrSeq.CheckedList(initCap);
        }else{
          return new IntArrSeq.UncheckedList(initCap);
        }
      case LONG:
        if(checkedType.checked){
          return new LongArrSeq.CheckedList(initCap);
        }else{
          return new LongArrSeq.UncheckedList(initCap);
        }
      case REF:
        if(checkedType.checked){
          return new RefArrSeq.CheckedList<>(initCap);
        }else{
          return new RefArrSeq.UncheckedList<>(initCap);
        }
      case SHORT:
        if(checkedType.checked){
          return new ShortArrSeq.CheckedList(initCap);
        }else{
          return new ShortArrSeq.UncheckedList(initCap);
        }
      default:
        throw dataType.invalid();
      }
    }
    @Override void updateModCount(){
      switch(dataType){
      case BOOLEAN:
        expectedModCount=((BooleanArrSeq.CheckedList)seq).modCount;
        break;
      case BYTE:
        expectedModCount=((ByteArrSeq.CheckedList)seq).modCount;
        break;
      case CHAR:
        expectedModCount=((CharArrSeq.CheckedList)seq).modCount;
        break;
      case DOUBLE:
        expectedModCount=((DoubleArrSeq.CheckedList)seq).modCount;
        break;
      case FLOAT:
        expectedModCount=((FloatArrSeq.CheckedList)seq).modCount;
        break;
      case INT:
        expectedModCount=((IntArrSeq.CheckedList)seq).modCount;
        break;
      case LONG:
        expectedModCount=((LongArrSeq.CheckedList)seq).modCount;
        break;
      case REF:
        expectedModCount=((RefArrSeq.CheckedList<?>)seq).modCount;
        break;
      case SHORT:
        expectedModCount=((ShortArrSeq.CheckedList)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
    }
    @Override void verifyCloneTypeAndModCount(Object clone){
      switch(dataType){
      case BOOLEAN:{
        if(checkedType.checked){
          Assertions.assertEquals(0,((BooleanArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof BooleanArrSeq.UncheckedList);
          Assertions.assertFalse(clone instanceof BooleanArrSeq.CheckedList);
        }
        break;
      }
      case BYTE:{
        if(checkedType.checked){
          Assertions.assertEquals(0,((ByteArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof ByteArrSeq.UncheckedList);
          Assertions.assertFalse(clone instanceof ByteArrSeq.CheckedList);
        }
        break;
      }
      case CHAR:{
        if(checkedType.checked){
          Assertions.assertEquals(0,((CharArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof CharArrSeq.UncheckedList);
          Assertions.assertFalse(clone instanceof CharArrSeq.CheckedList);
        }
        break;
      }
      case DOUBLE:{
        if(checkedType.checked){
          Assertions.assertEquals(0,((DoubleArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof DoubleArrSeq.UncheckedList);
          Assertions.assertFalse(clone instanceof DoubleArrSeq.CheckedList);
        }
        break;
      }
      case FLOAT:{
        if(checkedType.checked){
          Assertions.assertEquals(0,((FloatArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof FloatArrSeq.UncheckedList);
          Assertions.assertFalse(clone instanceof FloatArrSeq.CheckedList);
        }
        break;
      }
      case INT:{
        if(checkedType.checked){
          Assertions.assertEquals(0,((IntArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof IntArrSeq.UncheckedList);
          Assertions.assertFalse(clone instanceof IntArrSeq.CheckedList);
        }
        break;
      }
      case LONG:{
        if(checkedType.checked){
          Assertions.assertEquals(0,((LongArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof LongArrSeq.UncheckedList);
          Assertions.assertFalse(clone instanceof LongArrSeq.CheckedList);
        }
        break;
      }
      case REF:{
        if(checkedType.checked){
          Assertions.assertEquals(0,((RefArrSeq.CheckedList<?>)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof RefArrSeq.UncheckedList);
          Assertions.assertFalse(clone instanceof RefArrSeq.CheckedList);
        }
        break;
      }
      case SHORT:{
        if(checkedType.checked){
          Assertions.assertEquals(0,((ShortArrSeq.CheckedList)clone).modCount);
        }else{
          Assertions.assertTrue(clone instanceof ShortArrSeq.UncheckedList);
          Assertions.assertFalse(clone instanceof ShortArrSeq.CheckedList);
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override void verifyModCount(){
      int actualModCount;
      switch(dataType){
      case BOOLEAN:
        actualModCount=((BooleanArrSeq.CheckedList)seq).modCount;
        break;
      case BYTE:
        actualModCount=((ByteArrSeq.CheckedList)seq).modCount;
        break;
      case CHAR:
        actualModCount=((CharArrSeq.CheckedList)seq).modCount;
        break;
      case DOUBLE:
        actualModCount=((DoubleArrSeq.CheckedList)seq).modCount;
        break;
      case FLOAT:
        actualModCount=((FloatArrSeq.CheckedList)seq).modCount;
        break;
      case INT:
        actualModCount=((IntArrSeq.CheckedList)seq).modCount;
        break;
      case LONG:
        actualModCount=((LongArrSeq.CheckedList)seq).modCount;
        break;
      case REF:
        actualModCount=((RefArrSeq.CheckedList<?>)seq).modCount;
        break;
      case SHORT:
        actualModCount=((ShortArrSeq.CheckedList)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
      Assertions.assertEquals(expectedModCount,actualModCount);
    }
  }
  private static final int[] FIB_SEQ=new int[]{0,1,2,3,5,8,13,21,34,55,89,144,233,377,610,987,1597,2584,4181,6765,10946,
      17711,28657,46368,75025,121393,196418,317811,514229,832040,1346269,2178309,3524578,5702887,9227465,14930352,
      24157817,39088169,63245986,102334155,165580141,267914296,433494437,701408733,1134903170,1836311903};
}
