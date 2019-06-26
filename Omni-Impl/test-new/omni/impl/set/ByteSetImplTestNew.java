package omni.impl.set;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.IteratorType;
import omni.impl.StructType;
import omni.util.TestExecutorService;
public class ByteSetImplTestNew{
    private static final long[] WORDSTATES=new long[]{0x0000000000000000L,0x0000000000000001L,0x0000000000000002L,
            0x0000000000000004L,0x7fffffffffffffffL,0x8000000000000000L,0xfffffffffffffffeL,0xffffffffffffffffL,};
    @org.junit.jupiter.api.AfterEach
    public void verifyAllExecuted(){
        int numTestsRemaining;
        if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
            System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
        }
        TestExecutorService.reset();
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_void(){
        for(var checkedType:CheckedType.values()){
            TestExecutorService.submitTest(()->new ByteSetImplMonitor(checkedType).verifyCollectionState());
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testConstructor_longlonglonglong(){
        for(var checkedType:CheckedType.values()){
            for(long word0:WORDSTATES){
                for(long word1:WORDSTATES){
                    for(long word2:WORDSTATES){
                        for(long word3:WORDSTATES){
                            TestExecutorService
                            .submitTest(()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3)
                                    .verifyCollectionState());
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testiterator_void(){
        for(var checkedType:CheckedType.values()){
            for(long word0:WORDSTATES){
                for(long word1:WORDSTATES){
                    for(long word2:WORDSTATES){
                        for(long word3:WORDSTATES){
                            TestExecutorService.submitTest(
                                    ()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3)
                                    .getMonitoredIterator().verifyIteratorState());
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testItrnext_void(){
        for(var outputType:DataType.BYTE.validOutputTypes()){
            for(var checkedType:CheckedType.values()){
                for(var preMod:IteratorType.AscendingItr.validPreMods){
                    if(checkedType.checked || preMod.expectedException == null){
                        for(long word0:WORDSTATES){
                            for(long word1:WORDSTATES){
                                for(long word2:WORDSTATES){
                                    for(long word3:WORDSTATES){
                                        TestExecutorService.submitTest(()->{
                                            var setMonitor=new ByteSetImplMonitor(checkedType,word0,word1,word2,word3);
                                            var itrMonitor=setMonitor.getMonitoredIterator();
                                            itrMonitor.illegalMod(preMod);
                                            if(preMod.expectedException == null){
                                                while(itrMonitor.hasNext()){
                                                    itrMonitor.verifyNext(outputType);
                                                }
                                                Assertions.assertFalse(itrMonitor.getIterator().hasNext());
                                                if(checkedType.checked){
                                                    Assertions.assertThrows(NoSuchElementException.class,
                                                            ()->itrMonitor.verifyNext(outputType));
                                                }
                                            }else{
                                                Assertions.assertThrows(preMod.expectedException,
                                                        ()->itrMonitor.verifyNext(outputType));
                                            }
                                            itrMonitor.verifyIteratorState();
                                            setMonitor.verifyCollectionState();
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testclone_void(){
        for(var checkedType:CheckedType.values()){
            for(long word0:WORDSTATES){
                for(long word1:WORDSTATES){
                    for(long word2:WORDSTATES){
                        for(long word3:WORDSTATES){
                            TestExecutorService.submitTest(
                                    ()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3).verifyClone());
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    //    @org.junit.jupiter.api.Test
    //    public void testtoString_void(){
    //        for(var checkedType:CheckedType.values()){
    //            for(long word0:WORDSTATES){
    //                for(long word1:WORDSTATES){
    //                    for(long word2:WORDSTATES){
    //                        for(long word3:WORDSTATES){
    //                            TestExecutorService.submitTest(
    //                                    ()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3).verifyToString());
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //        TestExecutorService.completeAllTests();
    //    }
    //    @org.junit.jupiter.api.Test
    //    public void testhashCode_void(){
    //        for(var checkedType:CheckedType.values()){
    //            for(long word0:WORDSTATES){
    //                for(long word1:WORDSTATES){
    //                    for(long word2:WORDSTATES){
    //                        for(long word3:WORDSTATES){
    //                            TestExecutorService.submitTest(
    //                                    ()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3).verifyHashCode());
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //        TestExecutorService.completeAllTests();
    //    }
    @org.junit.jupiter.api.Test
    public void testReadAndWrite(){
        for(var functionGen:StructType.ByteSetImpl.validMonitoredFunctionGens){
            for(var checkedType:CheckedType.values()){
                if(checkedType.checked || functionGen.expectedException == null){
                    for(long word0:WORDSTATES){
                        for(long word1:WORDSTATES){
                            for(long word2:WORDSTATES){
                                for(long word3:WORDSTATES){
                                    TestExecutorService
                                    .submitTest(()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3)
                                            .verifyReadAndWrite(functionGen));
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    //    @org.junit.jupiter.api.Test
    //    public void testclear_void(){
    //        for(var checkedType:CheckedType.values()){
    //            for(long word0:WORDSTATES){
    //                for(long word1:WORDSTATES){
    //                    for(long word2:WORDSTATES){
    //                        for(long word3:WORDSTATES){
    //                            TestExecutorService.submitTest(
    //                                    ()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3).verifyClear());
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //        TestExecutorService.completeAllTests();
    //    }
    @org.junit.jupiter.api.Test
    public void testsize_void(){
        for(var checkedType:CheckedType.values()){
            for(long word0:WORDSTATES){
                for(long word1:WORDSTATES){
                    for(long word2:WORDSTATES){
                        for(long word3:WORDSTATES){
                            TestExecutorService.submitTest(
                                    ()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3).verifySize());
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    @org.junit.jupiter.api.Test
    public void testisEmpty_void(){
        for(var checkedType:CheckedType.values()){
            for(long word0:WORDSTATES){
                for(long word1:WORDSTATES){
                    for(long word2:WORDSTATES){
                        for(long word3:WORDSTATES){
                            TestExecutorService.submitTest(
                                    ()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3).verifyIsEmpty());
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests();
    }
    //    @org.junit.jupiter.api.Test
    //    public void testtoArray_IntFunction(){
    //        for(var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
    //            for(var checkedType:CheckedType.values()){
    //                if(checkedType.checked || functionGen.expectedException == null){
    //                    for(long word0:WORDSTATES){
    //                        for(long word1:WORDSTATES){
    //                            for(long word2:WORDSTATES){
    //                                for(long word3:WORDSTATES){
    //                                    TestExecutorService.submitTest(()->{
    //                                        final var monitor=new ByteSetImplMonitor(checkedType,word0,word1,word2,word3);
    //                                        if(functionGen.expectedException == null){
    //                                            monitor.verifyToArray(functionGen);
    //                                        }else{
    //                                            Assertions.assertThrows(functionGen.expectedException,
    //                                                    ()->monitor.verifyToArray(functionGen));
    //                                            monitor.verifyCollectionState();
    //                                        }
    //                                    });
    //                                }
    //                            }
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //        TestExecutorService.completeAllTests();
    //    }
    //    @org.junit.jupiter.api.Test
    //    public void testtoArray_ObjectArray(){
    //        for(var checkedType:CheckedType.values()){
    //            for(long word0:WORDSTATES){
    //                for(long word1:WORDSTATES){
    //                    for(long word2:WORDSTATES){
    //                        for(long word3:WORDSTATES){
    //                            int size=getSize();
    //                            for(int arrSize=0,increment=Math.max(1,size / 10),bound=size + increment
    //                                    + 2;arrSize <= bound;arrSize+=increment){
    //                                Object[] paramArr=new Object[arrSize];
    //                                TestExecutorService
    //                                .submitTest(()->new ByteSetImplMonitor(checkedType,word0,word1,word2,word3)
    //                                        .verifyToArray(paramArr));
    //                            }
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //        TestExecutorService.completeAllTests();
    //    }
    //    @org.junit.jupiter.api.Test
    //    public void testadd_val(){
    //        for(var inputType:DataType.BYTE.mayBeAddedTo()){
    //            int min=inputType.getMinInt();
    //            int max=inputType.getMaxInt();
    //            for(var checkedType:CheckedType.values()){
    //                for(long word0:WORDSTATES){
    //                    for(long word1:WORDSTATES){
    //                        for(long word2:WORDSTATES){
    //                            for(long word3:WORDSTATES){
    //                                for(var functionCallType:FunctionCallType.values()){
    //                                    TestExecutorService.submitTest(()->{
    //                                        var monitor=new ByteSetImplMonitor(checkedType,word0,word1,word2,word3);
    //                                        for(int i=min;i <= max;++i){
    //                                            Assertions.assertEquals(
    //                                                    monitor.set.contains((byte)DataType.BYTE.convertVal(i)),
    //                                                    monitor.verifyAdd(inputType.convertVal(i),inputType,
    //                                                            functionCallType));
    //                                        }
    //                                    });
    //                                }
    //                            }
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //        TestExecutorService.completeAllTests();
    //    }
    private static int getSize(long...words){
        int size=0;
        for(var word:words){
            size+=Long.bitCount(word);
        }
        return size;
    }
}
