package omni.impl.set;
import static omni.impl.set.FieldAndMethodAccessor.RefOpenAddressHashSet.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorRemoveScenario;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredObjectGen;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredRemoveIfPredicateGen.PredicateGenCallType;
import omni.impl.MonitoredSet;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;
import omni.util.NotYetImplementedException;
import omni.util.OmniArray;
import omni.util.TestExecutorService;

public class OpenAddressHashSetTest{
    private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
    private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
    private static final EnumMap<DataType,EnumSet<SetInitialization>> VALID_INIT_SEQS;
    static{
        VALID_INIT_SEQS=new EnumMap<>(DataType.class);
        final var tmp=EnumSet.of(SetInitialization.Empty,SetInitialization.AddTrue,
                SetInitialization.AddFalse,SetInitialization.AddTrueAndFalse,
                SetInitialization.AddPrime,SetInitialization.AddFibSeq,
                SetInitialization.AddMinByte,SetInitialization.FillWord0,
                SetInitialization.FillWord1,SetInitialization.FillWord2,
                SetInitialization.FillWord3,SetInitialization.Add200RemoveThenAdd100More);
        for(var dataType:StructType.OpenAddressHashSet.validDataTypes){
            if(dataType == DataType.REF){
                VALID_INIT_SEQS.put(DataType.REF,EnumSet.of(SetInitialization.Empty,
                        SetInitialization.AddTrue,SetInitialization.AddFalse,
                        SetInitialization.AddTrueAndFalse,SetInitialization.AddPrime,
                        SetInitialization.AddFibSeq,SetInitialization.AddMinByte,
                        SetInitialization.FillWord0,SetInitialization.FillWord1,
                        SetInitialization.FillWord2,SetInitialization.FillWord3,
                        SetInitialization.Add200RemoveThenAdd100More,SetInitialization.AddNull));
            }else{
                VALID_INIT_SEQS.put(dataType,tmp);
            }
        }
    }
    private static final int[] CONSTRUCTOR_INITIAL_CAPACITIES=new int[5 + 29 * 3 + 2];
    private static final float[] LOAD_FACTORS
    =new float[]{0.1f,0.25f,0.5f,0.75f,0.9f,0.95f,1.0f,1.1f,2.0f,0.0f,-1f,-.75f,-.5f,Float.NaN};
    private static final int[] GENERAL_PURPOSE_INITIAL_CAPACITIES
    =new int[]{0,1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192};
    static{
        CONSTRUCTOR_INITIAL_CAPACITIES[0]=0;
        CONSTRUCTOR_INITIAL_CAPACITIES[1]=1;
        CONSTRUCTOR_INITIAL_CAPACITIES[2]=2;
        int i=2;
        for(int pow=2;pow <= 30;++pow){
            CONSTRUCTOR_INITIAL_CAPACITIES[++i]=(1 << pow) - 1;
            CONSTRUCTOR_INITIAL_CAPACITIES[++i]=1 << pow;
            CONSTRUCTOR_INITIAL_CAPACITIES[++i]=(1 << pow) + 1;
        }
        CONSTRUCTOR_INITIAL_CAPACITIES[++i]=OmniArray.MAX_ARR_SIZE;
        CONSTRUCTOR_INITIAL_CAPACITIES[++i]=Integer.MAX_VALUE;
        CONSTRUCTOR_INITIAL_CAPACITIES[++i]=-1;
        CONSTRUCTOR_INITIAL_CAPACITIES[++i]=Integer.MIN_VALUE;
    }
    
    @Test public void testadd_val(){
        for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            for(var inputType:collectionType.mayBeAddedTo()) {
                for(var functionCallType:inputType.validFunctionCalls) {
                    for(final float loadFactor:LOAD_FACTORS){
                        if(loadFactor > 0.f && loadFactor <= 1.0f){
                            
                                for(final var checkedType:CheckedType.values()){
                                    for(final int initialCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                        for(var objGen:StructType.OpenAddressHashSet.validMonitoredObjectGens){
                                            if(objGen.expectedException == null || checkedType.checked
                                                    || inputType != DataType.REF){
                                                TestExecutorService.submitTest(()->{
                                                    final var monitor=new OpenAddressHashSetMonitor(collectionType,
                                                            checkedType,initialCapacity,loadFactor);
                                                    switch(inputType){
                                                    case BOOLEAN:
                                                        Assertions.assertNotEquals(false,
                                                                monitor.verifyAdd(false,inputType,functionCallType));
                                                        Assertions.assertNotEquals(false,
                                                                monitor.verifyAdd(true,inputType,functionCallType));
                                                        Assertions.assertNotEquals(true,
                                                                monitor.verifyAdd(false,inputType,functionCallType));
                                                        Assertions.assertNotEquals(true,
                                                                monitor.verifyAdd(true,inputType,functionCallType));
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        Assertions.assertNotEquals(false,
                                                                monitor.verifyAdd(true,inputType,functionCallType));
                                                        Assertions.assertNotEquals(false,
                                                                monitor.verifyAdd(false,inputType,functionCallType));
                                                        Assertions.assertNotEquals(true,
                                                                monitor.verifyAdd(true,inputType,functionCallType));
                                                        Assertions.assertNotEquals(true,
                                                                monitor.verifyAdd(false,inputType,functionCallType));
                                                        break;
                                                    case BYTE:
                                                        for(int i=Byte.MIN_VALUE;i <= Byte.MAX_VALUE;++i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((byte)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        for(int i=Byte.MAX_VALUE;i >= Byte.MIN_VALUE;--i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((byte)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        break;
                                                    case CHAR:
                                                        for(int i=0;i <= 500;++i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((char)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        for(int i=500;i >= 0;--i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((char)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        break;
                                                    case SHORT:
                                                        for(int i=-250;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((short)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        for(int i=250;i >= -250;--i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((short)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        break;
                                                    case INT:
                                                        for(int i=-250;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(i,inputType,functionCallType));
                                                        }
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        for(int i=250;i >= -250;--i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(i,inputType,functionCallType));
                                                        }
                                                        break;
                                                    case LONG:
                                                        for(long i=-250;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(i,inputType,functionCallType));
                                                        }
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        for(long i=250;i >= -250;--i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(i,inputType,functionCallType));
                                                        }
                                                        break;
                                                    case FLOAT:{
                                                        float f=0.0f;
                                                        for(int i=0;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(f,inputType,functionCallType));
                                                            f=Math.nextAfter(f,Double.POSITIVE_INFINITY);
                                                        }
                                                    }{
                                                        float f=-0.0f;
                                                        for(int i=0;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(f,inputType,functionCallType));
                                                            f=Math.nextAfter(f,Double.NEGATIVE_INFINITY);
                                                        }
                                                    }
                                                    Assertions.assertNotEquals(false,monitor.verifyAdd(Float.NaN,
                                                            inputType,functionCallType));
                                                    monitor.set.clear();
                                                    monitor.updateCollectionState();
                                                    Assertions.assertNotEquals(false,monitor.verifyAdd(Float.NaN,
                                                            inputType,functionCallType));{
                                                                float f=-0.0f;
                                                                for(int i=0;i <= 250;++i){
                                                                    Assertions.assertNotEquals(false,
                                                                            monitor.verifyAdd(f,inputType,functionCallType));
                                                                    f=Math.nextAfter(f,Double.NEGATIVE_INFINITY);
                                                                }
                                                            }{
                                                                float f=0.0f;
                                                                for(int i=0;i <= 250;++i){
                                                                    Assertions.assertNotEquals(false,
                                                                            monitor.verifyAdd(f,inputType,functionCallType));
                                                                    f=Math.nextAfter(f,Double.POSITIVE_INFINITY);
                                                                }
                                                            }
                                                            break;
                                                    case DOUBLE:{
                                                        double d=0.0d;
                                                        for(int i=0;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(d,inputType,functionCallType));
                                                            d=Math.nextAfter(d,Double.POSITIVE_INFINITY);
                                                        }
                                                    }{
                                                        double d=-0.0d;
                                                        for(int i=0;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(d,inputType,functionCallType));
                                                            d=Math.nextAfter(d,Double.NEGATIVE_INFINITY);
                                                        }
                                                    }
                                                    Assertions.assertNotEquals(false,monitor.verifyAdd(Double.NaN,
                                                            inputType,functionCallType));
                                                    monitor.set.clear();
                                                    monitor.updateCollectionState();
                                                    Assertions.assertNotEquals(false,monitor.verifyAdd(Double.NaN,
                                                            inputType,functionCallType));{
                                                                double d=-0.0d;
                                                                for(int i=0;i <= 250;++i){
                                                                    Assertions.assertNotEquals(false,
                                                                            monitor.verifyAdd(d,inputType,functionCallType));
                                                                    d=Math.nextAfter(d,Double.NEGATIVE_INFINITY);
                                                                }
                                                            }{
                                                                double d=0.0d;
                                                                for(int i=0;i <= 250;++i){
                                                                    Assertions.assertNotEquals(false,
                                                                            monitor.verifyAdd(d,inputType,functionCallType));
                                                                    d=Math.nextAfter(d,Double.POSITIVE_INFINITY);
                                                                }
                                                            }
                                                            break;
                                                    case REF:
                                                        if(objGen.expectedException == null){
                                                            for(int i=-250;i <= 250;++i){
                                                                Assertions.assertNotEquals(false,
                                                                        monitor.verifyAdd(
                                                                                objGen.getMonitoredObject(monitor,i),
                                                                                inputType,functionCallType));
                                                            }
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(null,inputType,functionCallType));
                                                            monitor.set.clear();
                                                            monitor.updateCollectionState();
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(null,inputType,functionCallType));
                                                            for(int i=250;i >= -250;--i){
                                                                Assertions.assertNotEquals(false,
                                                                        monitor.verifyAdd(
                                                                                objGen.getMonitoredObject(monitor,i),
                                                                                inputType,functionCallType));
                                                            }
                                                        }else{
                                                            for(int tmp=-500;tmp <= 500;++tmp){
                                                                final int i=tmp;
                                                                Assertions.assertThrows(objGen.expectedException,
                                                                        ()->monitor.verifyAdd(
                                                                                objGen.getMonitoredObject(monitor,i),
                                                                                inputType,functionCallType));
                                                            }
                                                        }
                                                        break;
                                                    default:
                                                        throw inputType.invalid();
                                                    }
                                                });
                                            }
                                            if(inputType != DataType.REF){
                                                break;
                                            }

                                        }
                                    }
                                }
                            
                        }
                    }
                }
            }
            
            for(final var functionCallType:FunctionCallType.values()){
                if(collectionType != DataType.REF || functionCallType != FunctionCallType.Boxed){
                    for(final float loadFactor:LOAD_FACTORS){
                        if(loadFactor > 0.f && loadFactor <= 1.0f){
                            for(final var inputType:collectionType.mayBeAddedTo()){
                                for(final var checkedType:CheckedType.values()){
                                    for(final int initialCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                        for(var objGen:StructType.OpenAddressHashSet.validMonitoredObjectGens){
                                            if(objGen.expectedException == null || checkedType.checked
                                                    || inputType != DataType.REF){
                                                TestExecutorService.submitTest(()->{
                                                    final var monitor=new OpenAddressHashSetMonitor(collectionType,
                                                            checkedType,initialCapacity,loadFactor);
                                                    switch(inputType){
                                                    case BOOLEAN:
                                                        Assertions.assertNotEquals(false,
                                                                monitor.verifyAdd(false,inputType,functionCallType));
                                                        Assertions.assertNotEquals(false,
                                                                monitor.verifyAdd(true,inputType,functionCallType));
                                                        Assertions.assertNotEquals(true,
                                                                monitor.verifyAdd(false,inputType,functionCallType));
                                                        Assertions.assertNotEquals(true,
                                                                monitor.verifyAdd(true,inputType,functionCallType));
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        Assertions.assertNotEquals(false,
                                                                monitor.verifyAdd(true,inputType,functionCallType));
                                                        Assertions.assertNotEquals(false,
                                                                monitor.verifyAdd(false,inputType,functionCallType));
                                                        Assertions.assertNotEquals(true,
                                                                monitor.verifyAdd(true,inputType,functionCallType));
                                                        Assertions.assertNotEquals(true,
                                                                monitor.verifyAdd(false,inputType,functionCallType));
                                                        break;
                                                    case BYTE:
                                                        for(int i=Byte.MIN_VALUE;i <= Byte.MAX_VALUE;++i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((byte)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        for(int i=Byte.MAX_VALUE;i >= Byte.MIN_VALUE;--i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((byte)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        break;
                                                    case CHAR:
                                                        for(int i=0;i <= 500;++i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((char)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        for(int i=500;i >= 0;--i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((char)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        break;
                                                    case SHORT:
                                                        for(int i=-250;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((short)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        for(int i=250;i >= -250;--i){
                                                            Assertions.assertNotEquals(false,monitor.verifyAdd((short)i,
                                                                    inputType,functionCallType));
                                                        }
                                                        break;
                                                    case INT:
                                                        for(int i=-250;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(i,inputType,functionCallType));
                                                        }
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        for(int i=250;i >= -250;--i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(i,inputType,functionCallType));
                                                        }
                                                        break;
                                                    case LONG:
                                                        for(long i=-250;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(i,inputType,functionCallType));
                                                        }
                                                        monitor.set.clear();
                                                        monitor.updateCollectionState();
                                                        for(long i=250;i >= -250;--i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(i,inputType,functionCallType));
                                                        }
                                                        break;
                                                    case FLOAT:{
                                                        float f=0.0f;
                                                        for(int i=0;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(f,inputType,functionCallType));
                                                            f=Math.nextAfter(f,Double.POSITIVE_INFINITY);
                                                        }
                                                    }{
                                                        float f=-0.0f;
                                                        for(int i=0;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(f,inputType,functionCallType));
                                                            f=Math.nextAfter(f,Double.NEGATIVE_INFINITY);
                                                        }
                                                    }
                                                    Assertions.assertNotEquals(false,monitor.verifyAdd(Float.NaN,
                                                            inputType,functionCallType));
                                                    monitor.set.clear();
                                                    monitor.updateCollectionState();
                                                    Assertions.assertNotEquals(false,monitor.verifyAdd(Float.NaN,
                                                            inputType,functionCallType));{
                                                                float f=-0.0f;
                                                                for(int i=0;i <= 250;++i){
                                                                    Assertions.assertNotEquals(false,
                                                                            monitor.verifyAdd(f,inputType,functionCallType));
                                                                    f=Math.nextAfter(f,Double.NEGATIVE_INFINITY);
                                                                }
                                                            }{
                                                                float f=0.0f;
                                                                for(int i=0;i <= 250;++i){
                                                                    Assertions.assertNotEquals(false,
                                                                            monitor.verifyAdd(f,inputType,functionCallType));
                                                                    f=Math.nextAfter(f,Double.POSITIVE_INFINITY);
                                                                }
                                                            }
                                                            break;
                                                    case DOUBLE:{
                                                        double d=0.0d;
                                                        for(int i=0;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(d,inputType,functionCallType));
                                                            d=Math.nextAfter(d,Double.POSITIVE_INFINITY);
                                                        }
                                                    }{
                                                        double d=-0.0d;
                                                        for(int i=0;i <= 250;++i){
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(d,inputType,functionCallType));
                                                            d=Math.nextAfter(d,Double.NEGATIVE_INFINITY);
                                                        }
                                                    }
                                                    Assertions.assertNotEquals(false,monitor.verifyAdd(Double.NaN,
                                                            inputType,functionCallType));
                                                    monitor.set.clear();
                                                    monitor.updateCollectionState();
                                                    Assertions.assertNotEquals(false,monitor.verifyAdd(Double.NaN,
                                                            inputType,functionCallType));{
                                                                double d=-0.0d;
                                                                for(int i=0;i <= 250;++i){
                                                                    Assertions.assertNotEquals(false,
                                                                            monitor.verifyAdd(d,inputType,functionCallType));
                                                                    d=Math.nextAfter(d,Double.NEGATIVE_INFINITY);
                                                                }
                                                            }{
                                                                double d=0.0d;
                                                                for(int i=0;i <= 250;++i){
                                                                    Assertions.assertNotEquals(false,
                                                                            monitor.verifyAdd(d,inputType,functionCallType));
                                                                    d=Math.nextAfter(d,Double.POSITIVE_INFINITY);
                                                                }
                                                            }
                                                            break;
                                                    case REF:
                                                        if(objGen.expectedException == null){
                                                            for(int i=-250;i <= 250;++i){
                                                                Assertions.assertNotEquals(false,
                                                                        monitor.verifyAdd(
                                                                                objGen.getMonitoredObject(monitor,i),
                                                                                inputType,functionCallType));
                                                            }
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(null,inputType,functionCallType));
                                                            monitor.set.clear();
                                                            monitor.updateCollectionState();
                                                            Assertions.assertNotEquals(false,
                                                                    monitor.verifyAdd(null,inputType,functionCallType));
                                                            for(int i=250;i >= -250;--i){
                                                                Assertions.assertNotEquals(false,
                                                                        monitor.verifyAdd(
                                                                                objGen.getMonitoredObject(monitor,i),
                                                                                inputType,functionCallType));
                                                            }
                                                        }else{
                                                            for(int tmp=-500;tmp <= 500;++tmp){
                                                                final int i=tmp;
                                                                Assertions.assertThrows(objGen.expectedException,
                                                                        ()->monitor.verifyAdd(
                                                                                objGen.getMonitoredObject(monitor,i),
                                                                                inputType,functionCallType));
                                                            }
                                                        }
                                                        break;
                                                    default:
                                                        throw inputType.invalid();
                                                    }
                                                });
                                            }
                                            if(inputType != DataType.REF){
                                                break;
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testadd_val");
    }
    
    @Test public void testclear_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .verifyClear();
        };
        test.runAllTests("OpenAddressHashSetTest.testclear_void");
    }
    
    @Test
    public void testequals_Object(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            try {
            Assertions.assertFalse(initSet.initialize( new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
                    .getCollection().equals(null));
            }catch(NotYetImplementedException e) {
                //do nothing
            }
        };
           
        test.runAllTests("OpenAddressHashSetTest.testequals_Object");
    }
    
    @Test public void testclone_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .verifyClone();
        };
        test.runAllTests("OpenAddressHashSetTest.testclone_void");
    }
    
    @Test public void testConstructor_float(){

        for(final var checkedType:CheckedType.values()){
            for(final float loadFactor:LOAD_FACTORS){
                if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                        TestExecutorService.submitTest(()->{
                            if(loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                                new OpenAddressHashSetMonitor(collectionType,checkedType,loadFactor)
                                .verifyCollectionState();
                            }else{
                                Assertions.assertThrows(IllegalArgumentException.class,
                                        ()->new OpenAddressHashSetMonitor(collectionType,checkedType,loadFactor));
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testConstructor_float");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void testConstructor_Collection(){
        for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            for(final var checkedType:CheckedType.values()){
                for(var collectionClass:collectionType.validCollectionConstructorClasses) {
                    TestExecutorService.submitTest(()->{
                        Collection<?> collectionParam=MonitoredCollection.getConstructorCollectionParam(collectionType,(Class<? extends Collection<?>>)collectionClass);
                        new OpenAddressHashSetMonitor(collectionType,checkedType,collectionParam,(Class<? extends Collection<?>>)collectionClass).verifyCollectionState();
                    });
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testConstructor_Collection");
    }
    @SuppressWarnings("unchecked")
    @Test
    public void testConstructor_floatCollection(){
        for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            for(final var checkedType:CheckedType.values()){
                for(var collectionClass:collectionType.validCollectionConstructorClasses) {
                    for(final float loadFactor:LOAD_FACTORS){
                        if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                            TestExecutorService.submitTest(()->{
                                Collection<?> collectionParam=MonitoredCollection.getConstructorCollectionParam(collectionType,(Class<? extends Collection<?>>)collectionClass);
                                if(loadFactor == loadFactor && loadFactor <= 1.0f
                                        && loadFactor > 0){
                                    new OpenAddressHashSetMonitor(collectionType,checkedType,
                                            loadFactor,collectionParam,(Class<? extends Collection<?>>)collectionClass).verifyCollectionState();
                                }else{
                                    Assertions.assertThrows(IllegalArgumentException.class,
                                            ()->new OpenAddressHashSetMonitor(collectionType,checkedType,
                                                    loadFactor,collectionParam,(Class<? extends Collection<?>>)collectionClass));
                                }
                            });
                        }
                    }
                    
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testConstructor_floatCollection");
    }
    
    @Test public void testConstructor_int(){

        for(final var checkedType:CheckedType.values()){
            for(final int initialCapacity:CONSTRUCTOR_INITIAL_CAPACITIES){
                if(checkedType.checked || initialCapacity >= 0){
                    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                        TestExecutorService.submitTest(()->{
                            if(initialCapacity >= 0){
                                new OpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity)
                                .verifyCollectionState();
                            }else{
                                Assertions.assertThrows(IllegalArgumentException.class,
                                        ()->new OpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity));
                            }
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testConstructor_int");
    }
    
    @Test public void testConstructor_intfloat(){

        for(final var checkedType:CheckedType.values()){
            for(final int initialCapacity:CONSTRUCTOR_INITIAL_CAPACITIES){
                if(checkedType.checked || initialCapacity >= 0){
                    for(final float loadFactor:LOAD_FACTORS){
                        if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                            for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                                TestExecutorService.submitTest(()->{
                                    if(initialCapacity >= 0 && loadFactor == loadFactor && loadFactor <= 1.0f
                                            && loadFactor > 0){
                                        new OpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity,
                                                loadFactor).verifyCollectionState();
                                    }else{
                                        Assertions.assertThrows(IllegalArgumentException.class,
                                                ()->new OpenAddressHashSetMonitor(collectionType,checkedType,
                                                        initialCapacity,loadFactor));
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testConstructor_intfloat");
    }
    
    @Test public void testConstructor_void(){
        for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            for(final var checkedType:CheckedType.values()){
                TestExecutorService
                .submitTest(()->new OpenAddressHashSetMonitor(collectionType,checkedType).verifyCollectionState());
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testConstructor_void");
    }
    
    @Test public void testcontains_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen)->{
            if(monitoredObjectGen == null){
                return monitor.verifyContains(queryVal,inputType,castType,modification);
            }else{
                return monitor.verifyThrowingContains(monitoredObjectGen);
            }
        };
        test.runAllTests("OpenAddressHashSetTest.testcontains_val");
    }
    
    @Test public void testforEach_Consumer(){
        for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            for(final var functionCallType:collectionType.validFunctionCalls){
                
                for(final var functionGen:StructType.OpenAddressHashSet.validMonitoredFunctionGens){
                    for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                        final int size
                        =initSet.initialize(new OpenAddressHashSetMonitor(collectionType,CheckedType.UNCHECKED)).size();
                        final long randSeedBound=functionGen.randomized && size > 1 && !functionCallType.boxed?100:0;
                        for(final var checkedType:CheckedType.values()){
                            if(checkedType.checked || functionGen.expectedException == null || size == 0){
                                LongStream.rangeClosed(0,randSeedBound)
                                .forEach(randSeed->TestExecutorService.submitTest(()->{
                                    final var monitor=initSet.initialize(
                                            new OpenAddressHashSetMonitor(collectionType,checkedType));
                                    if(functionGen.expectedException == null || monitor.size() == 0){
                                        monitor.verifyForEach(functionGen,functionCallType,randSeed);
                                    }else{
                                        Assertions.assertThrows(functionGen.expectedException,()->monitor
                                                .verifyForEach(functionGen,functionCallType,randSeed));
                                        monitor.verifyCollectionState();
                                    }
                                }));
                            }
                        }
                    }
                }
                
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testforEach_Consumer");
    }
    
    @Test public void testhashCode_void(){
        for(final var loadFactor:LOAD_FACTORS){
            if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                for(final var initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                        for(final var checkedType:CheckedType.values()){
                            for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                                TestExecutorService
                                .submitTest(()->initSet.initialize(new OpenAddressHashSetMonitor(collectionType,
                                        checkedType,initCapacity,loadFactor)).verifyHashCode());
                            }
                        }
                    }
                    for(var monitoredObjectGen:StructType.OpenAddressHashSet.validMonitoredObjectGens){
                        if(monitoredObjectGen.expectedException != null){
                            TestExecutorService.submitTest(()->{
                                var monitor=new OpenAddressHashSetMonitor(DataType.REF,CheckedType.CHECKED,initCapacity,
                                        loadFactor);
                                MonitoredObjectGen.ThrowSwitch throwSwitch=new MonitoredObjectGen.ThrowSwitch(false);
                                @SuppressWarnings("unchecked")
                                var set=(OmniSet.OfRef<Object>)monitor.set;
                                for(int i=0;i < 10;++i){
                                    set.add(monitoredObjectGen.getMonitoredObject(monitor,throwSwitch));
                                }
                                monitor.updateCollectionState();
                                throwSwitch.doThrow=true;
                                Assertions.assertThrows(monitoredObjectGen.expectedException,()->set.hashCode());
                                monitor.verifyCollectionState();
                            });
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testhashCode_void");
    }
    
    @Test public void testisEmpty_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .verifyIsEmpty();
        };
        test.runAllTests("OpenAddressHashSetTest.testisEmpty_void");
    }
    
    @Test public void testiterator_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .getMonitoredIterator().verifyIteratorState();
        };
        test.runAllTests("OpenAddressHashSetTest.testiterator_void");
    }
    
    @Test public void testItrclone_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->
        initSet
                .initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
                .getMonitoredIterator().verifyClone();
        
        test.runAllTests("OpenAddressHashSetTest.testItrclone_void");
    }
    
    @Test public void testItrforEachRemaining_Consumer(){
        for(final var loadFactor:LOAD_FACTORS){
            if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){

                for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                    for(final var functionCallType:collectionType.validFunctionCalls){
                        
                            for(final var checkedType:CheckedType.values()){
                                for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                                    int sizeScenario;
                                    switch(initSet){
                                    case Empty:
                                        sizeScenario=0;
                                        break;
                                    case AddPrime:
                                    case AddTrue:
                                    case AddFalse:
                                    case AddMinByte:
                                    case AddNull:
                                        sizeScenario=1;
                                        break;
                                    case AddTrueAndFalse:
                                    case FillWord0:
                                    case FillWord1:
                                    case FillWord2:
                                    case FillWord3:
                                        sizeScenario=2;
                                        break;
                                    case AddFibSeq:
                                    case Add200RemoveThenAdd100More:
                                        sizeScenario=3;
                                        break;
                                    default:
                                        throw initSet.invalid();
                                    }
                                    for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                                        if(checkedType.checked || functionGen.expectedException == null || sizeScenario == 0){
                                            for(final var preMod:IteratorType.AscendingItr.validPreMods){
                                                if(checkedType.checked || preMod.expectedException == null || sizeScenario == 0){
                                                    int itrScenarioMax=0;
                                                    if(sizeScenario > 1){
                                                        if(sizeScenario > 2){
                                                            itrScenarioMax=3;
                                                        }else{
                                                            itrScenarioMax=2;
                                                        }
                                                    }
                                                    for(int tmpItrScenario=0;tmpItrScenario<=itrScenarioMax;++tmpItrScenario) {
                                                        final int itrScenario=tmpItrScenario;
                                                        for(long tmpRandSeed=0,randSeedBound=preMod.expectedException == null && functionGen.randomized
                                                                && sizeScenario > 1 && itrScenario == 0?100:0;tmpRandSeed<=randSeedBound;++tmpRandSeed) {
                                                            final long randSeed=tmpRandSeed;
                                                                    for(final var initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                                                        TestExecutorService.submitTest(()->{
                                                                            final var setMonitor=initSet
                                                                                    .initialize(
                                                                                            new OpenAddressHashSetMonitor(
                                                                                                    collectionType,
                                                                                                    checkedType,
                                                                                                    initCapacity,
                                                                                                    loadFactor));
                                                                            final var itrMonitor=setMonitor
                                                                                    .getMonitoredIterator();
                                                                            switch(itrScenario){
                                                                            case 1:
                                                                                itrMonitor.iterateForward();
                                                                                break;
                                                                            case 2:
                                                                                itrMonitor.iterateForward();
                                                                                itrMonitor.remove();
                                                                                break;
                                                                            case 3:
                                                                                for(int i=0;i < 13 && itrMonitor
                                                                                        .hasNext();++i){
                                                                                    itrMonitor.iterateForward();
                                                                                }
                                                                                break;
                                                                            default:
                                                                            }
                                                                            final int numLeft=itrMonitor
                                                                                    .getNumLeft();
                                                                            itrMonitor.illegalMod(preMod);
                                                                            final Class<? extends Throwable> expectedException=numLeft == 0
                                                                                    ?null
                                                                                            :preMod.expectedException == null
                                                                                            ?functionGen.expectedException
                                                                                                    :preMod.expectedException;
                                                                            if(expectedException == null){
                                                                                itrMonitor
                                                                                .verifyForEachRemaining(
                                                                                        functionGen,
                                                                                        functionCallType,
                                                                                        randSeed);
                                                                            }else{
                                                                                Assertions.assertThrows(
                                                                                        expectedException,
                                                                                        ()->itrMonitor
                                                                                        .verifyForEachRemaining(
                                                                                                functionGen,
                                                                                                functionCallType,
                                                                                                randSeed));
                                                                                itrMonitor
                                                                                .verifyIteratorState();
                                                                                setMonitor
                                                                                .verifyCollectionState();
                                                                            }
                                                                        });
                                                                    }

                                                                }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        
                    }

                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testItrforEachRemaining_Consumer");
    }
    
    @Test public void testItrhasNext_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            final var setMonitor
            =initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor));
            final var itrMonitor=setMonitor.getMonitoredIterator();
            final var setSize=setMonitor.size();
            for(int i=0;i < setSize;++i){
                Assertions.assertTrue(itrMonitor.verifyHasNext());
                itrMonitor.iterateForward();
            }
            Assertions.assertFalse(itrMonitor.verifyHasNext());
        };
        test.runAllTests("OpenAddressHashSetTest.testItrhasNext_void");
    }
    
    @Test public void testItrnext_void(){
        for(final var loadFactor:LOAD_FACTORS){
            if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){

                for(final var checkedType:CheckedType.values()){
                    for(final var preMod:IteratorType.AscendingItr.validPreMods){
                        if(checkedType.checked || preMod.expectedException == null){
                            for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){

                                for(final var outputType:collectionType.validOutputTypes()){

                                    for(final var initialCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                        for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                                            TestExecutorService.submitTest(()->{
                                                final var setMonitor=initSet.initialize(new OpenAddressHashSetMonitor(
                                                        collectionType,checkedType,initialCapacity,loadFactor));
                                                final var itrMonitor=setMonitor.getMonitoredIterator();
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
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testItrnext_void");
    }
    
    @Test public void testItrremove_void(){
        for(final float loadFactor:LOAD_FACTORS){
            if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                    for(final var checkedType:CheckedType.values()){
                        for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                            for(final var itrRemoveScenario:IteratorType.AscendingItr.validItrRemoveScenarios){
                                if((!initSet.isEmpty || itrRemoveScenario == IteratorRemoveScenario.PostInit)
                                        && (checkedType.checked || itrRemoveScenario.expectedException == null)){
                                    for(final var preMod:IteratorType.AscendingItr.validPreMods){
                                        if(checkedType.checked || preMod.expectedException == null){
                                            final var monitor=initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType));
                                            final int setSize=monitor.size();
                                            int itrOffset,itrBound;
                                            if(itrRemoveScenario == IteratorRemoveScenario.PostInit){
                                                itrOffset=itrBound=0;
                                            }else{
                                                itrOffset=1;
                                                itrBound=setSize;
                                            }
                                            IntStream.rangeClosed(itrOffset,itrBound).forEach(itrCount->{

                                                for(final int initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                                    TestExecutorService.submitTest(()->{
                                                        var setMonitor=initSet.initialize(new OpenAddressHashSetMonitor(
                                                                collectionType,checkedType,initCapacity,loadFactor));
                                                        final var itrMonitor=setMonitor.getMonitoredIterator();
                                                        for(int i=0;i < itrCount;++i){
                                                            itrMonitor.iterateForward();
                                                        }
                                                        itrRemoveScenario.initialize(itrMonitor);
                                                        itrMonitor.illegalMod(preMod);
                                                        final Class<? extends Throwable> expectedException=itrRemoveScenario.expectedException == null
                                                                ?preMod.expectedException
                                                                        :itrRemoveScenario.expectedException;
                                                        if(expectedException == null){
                                                            for(;;){
                                                                itrMonitor.verifyRemove();
                                                                if(!itrMonitor.hasNext()){
                                                                    break;
                                                                }
                                                                itrMonitor.iterateForward();
                                                            }
                                                        }else{
                                                            Assertions.assertThrows(expectedException,
                                                                    ()->itrMonitor.verifyRemove());
                                                            itrMonitor.verifyIteratorState();
                                                            setMonitor.verifyCollectionState();
                                                        }
                                                    });
                                                }

                                            });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testItrremove_void");
    }
    @Test public void testMASSIVEtoString(){
        int numToAdd;
        var set=new FloatOpenAddressHashSet((numToAdd=DataType.FLOAT.massiveToStringThreshold + 1) + 1);
        for(int i=0;i <= numToAdd;++i){
            set.add(Float.intBitsToFloat(i));
        }
        DataType.FLOAT.verifyToString(set.toString(),set,
                "OpenAddressHashSetTest." + DataType.FLOAT + ".testMASSIVEtoString");
    }
    
    @Test public void testReadAndWrite(){
        final MonitoredFunctionGenTest test=(collectionType,functionGen,checkedType,initSet)->{
            var monitor=initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType));
            if(functionGen.expectedException==null) {
                Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
            }else {
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
            }   
        };
        test.runAllTests("OpenAddressHashSetTest.testReadAndWrite");
    }
    
    @Test public void testremoveIf_Predicate(){
        for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            for(final var functionCallType:collectionType.validFunctionCalls){
                for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                    final int setSize
                    =initSet.initialize(new OpenAddressHashSetMonitor(collectionType,CheckedType.UNCHECKED)).size();
                    for(final var filterGen:StructType.OpenAddressHashSet.validMonitoredRemoveIfPredicateGens){
                        final long randSeedBound;
                        final double[] thresholdArr;
                        if(filterGen.predicateGenCallType==PredicateGenCallType.Randomized && setSize > 1 && !functionCallType.boxed){
                            randSeedBound=100;
                            thresholdArr=RANDOM_THRESHOLDS;
                        }else{
                            randSeedBound=0;
                            thresholdArr=NON_RANDOM_THRESHOLD;
                        }
                        for(final var checkedType:CheckedType.values()){
                            if(checkedType.checked || filterGen.expectedException == null || setSize == 0){
                                for(long tmpRandSeed=0;tmpRandSeed<=randSeedBound;++tmpRandSeed) {
                                    final long randSeed=tmpRandSeed;
                                    for(var threshold:thresholdArr) {
                                        TestExecutorService.submitTest(()->{
                                            final var monitor=initSet.initialize(
                                                    new OpenAddressHashSetMonitor(collectionType,
                                                            checkedType));
                                            final var filter=filterGen.getMonitoredRemoveIfPredicate(
                                                    monitor,threshold,new Random(randSeed));
                                            final int sizeBefore=monitor.size();
                                            if(filterGen.expectedException == null || sizeBefore == 0){
                                                final boolean result=monitor.verifyRemoveIf(filter,
                                                        functionCallType);
                                                if(sizeBefore == 0b00){
                                                    Assertions.assertFalse(result);
                                                }else{
                                                    switch(filterGen){
                                                    case Random:
                                                        Assertions.assertEquals(filter.numRemoved != 0,
                                                        result);
                                                        break;
                                                    case RemoveAll:
                                                        Assertions.assertTrue(monitor.set.isEmpty());
                                                        Assertions.assertTrue(result);
                                                        break;
                                                    case RemoveFalse:
                                                        Assertions.assertFalse(monitor.set.contains(false));
                                                        break;
                                                    case RemoveNone:
                                                        Assertions.assertFalse(result);
                                                        Assertions.assertFalse(monitor.set.isEmpty());
                                                        break;
                                                    case RemoveTrue:
                                                        Assertions.assertFalse(monitor.set.contains(true));
                                                        break;
                                                    default:
                                                        throw filterGen.invalid();
                                                    }
                                                }
                                            }else{
                                                Assertions.assertThrows(filterGen.expectedException,
                                                        ()->monitor.verifyRemoveIf(filter,
                                                                functionCallType));
                                                monitor.verifyCollectionState();
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
                
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testremoveIf_Predicate");
    }
    
    @Test public void testremoveVal_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification,monitoredObjectGen)->{
            if(monitoredObjectGen == null){
                return monitor.verifyRemoveVal(queryVal,inputType,castType,modification);
            }else{
                return monitor.verifyThrowingRemoveVal(monitoredObjectGen);
            }
        };
        test.runAllTests("OpenAddressHashSetTest.testremoveVal_val");
    }
    
    @Test public void testsetLoadFactor_float(){

        for(final var checkedType:CheckedType.values()){

            for(final float loadFactor:LOAD_FACTORS){
                if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                        for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                            TestExecutorService
                            .submitTest(()->initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType))
                                    .verifySetLoadFactor(loadFactor));
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testsetLoadFactor_float");
    }
    
    @Test public void testsize_void(){
        final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSet)->{
            initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
            .verifySize();
        };
        test.runAllTests("OpenAddressHashSetTest.testsize_void");
    }
    
    @Test public void testtoArray_IntFunction(){
        final MonitoredFunctionGenTest test=(collectionType,functionGen,checkedType,initSet)->{
            final var monitor=initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType));
            if(functionGen.expectedException == null){
                monitor.verifyToArray(functionGen);
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
                monitor.verifyCollectionState();
            }
        };
        test.runAllTests("OpenAddressHashSetTest.testToArray_IntFunction");
    }
    
    @Test public void testtoArray_ObjectArray(){
        for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                final int size=initSet.initialize(new OpenAddressHashSetMonitor(collectionType,CheckedType.UNCHECKED)).size();
                for(final var checkedType:CheckedType.values()){
                    for(int arrSize=0,increment=Math.max(1,size / 10),bound=size + increment + 2;arrSize <= bound;
                            arrSize+=increment){
                        final Object[] paramArr=new Object[arrSize];
                        TestExecutorService.submitTest(()->initSet
                                .initialize(new OpenAddressHashSetMonitor(collectionType,checkedType)).verifyToArray(paramArr));
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testtoArray_ObjectArray");
    }
    
    @Test public void testtoArray_void(){
        for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            for(final var outputType:collectionType.validOutputTypes()){
                for(final var checkedType:CheckedType.values()){
                    for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                        TestExecutorService.submitTest(()->{
                            outputType.verifyToArray(initSet.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType)));
                        });
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testtoArray_void");
    }
    
    @Test public void testtoString_void(){
        for(final var loadFactor:LOAD_FACTORS){
            if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                for(final var initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                        for(final var checkedType:CheckedType.values()){
                            for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                                TestExecutorService
                                .submitTest(()->initSet.initialize(new OpenAddressHashSetMonitor(collectionType,
                                        checkedType,initCapacity,loadFactor)).verifyToString());
                            }
                        }
                    }
                    for(var monitoredObjectGen:StructType.OpenAddressHashSet.validMonitoredObjectGens){
                        if(monitoredObjectGen.expectedException != null){
                            TestExecutorService.submitTest(()->{
                                var monitor=new OpenAddressHashSetMonitor(DataType.REF,CheckedType.CHECKED,initCapacity,
                                        loadFactor);
                                MonitoredObjectGen.ThrowSwitch throwSwitch=new MonitoredObjectGen.ThrowSwitch(false);
                                @SuppressWarnings("unchecked")
                                var set=(OmniSet.OfRef<Object>)monitor.set;
                                for(int i=0;i < 10;++i){
                                    set.add(monitoredObjectGen.getMonitoredObject(monitor,throwSwitch));
                                }
                                monitor.updateCollectionState();
                                throwSwitch.doThrow=true;
                                Assertions.assertThrows(monitoredObjectGen.expectedException,()->set.toString());
                                monitor.verifyCollectionState();
                            });
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("OpenAddressHashSetTest.testtoString_void");
    }
    private interface BasicTest{
        void runTest(float loadFactor,int initCapacity,DataType collectionType,CheckedType checkedType,
                SetInitialization initSet);
        private void runAllTests(String testName){
            for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                for(final var checkedType:CheckedType.values()){
                    for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                        for(final var loadFactor:LOAD_FACTORS){
                            if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                                for(final var initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                    TestExecutorService
                                    .submitTest(()->runTest(loadFactor,initCapacity,collectionType,checkedType,initSet));
                                }
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private interface MonitoredFunctionGenTest{
        void runTest(DataType collectionType,MonitoredFunctionGen functionGen,CheckedType checkedType,
                SetInitialization initSet);
        private void runAllTests(String testName){
            for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                for(final var functionGen:StructType.OpenAddressHashSet.validMonitoredFunctionGens){
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || functionGen.expectedException == null){
                            for(final var initSet:VALID_INIT_SEQS.get(collectionType)){
                                TestExecutorService.submitTest(()->runTest(collectionType,functionGen,checkedType,initSet));
                            }
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
    }
    private static class OpenAddressHashSetMonitor implements MonitoredSet<AbstractOpenAddressHashSet<?>>{
        @Override public MonitoredIterator<? extends OmniIterator<?>,AbstractOpenAddressHashSet<?>> getMonitoredIterator(IteratorType itrType){
            if(itrType!=IteratorType.AscendingItr) {
                throw itrType.invalid();
            }
            return getMonitoredIterator();
        }
        @Override public MonitoredIterator<? extends OmniIterator<?>,AbstractOpenAddressHashSet<?>> getMonitoredIterator(int index,IteratorType itrType){
            var itrMonitor=getMonitoredIterator(itrType);
            while(--index>=0 && itrMonitor.hasNext()) {
                itrMonitor.iterateForward();
            }
            return itrMonitor;
        }
        private static int getTableHash(int bits){
            return bits ^ bits >>> 16;
        }
        private static int getTableHash(long bits){
            return getTableHash((int)(bits ^ bits >>> 32));
        }
        private static int getTableHash(Object v){
            return getTableHash(v.hashCode());
        }
        private static long getTableVal(double v){
            long bits;
            if(v == v){
                bits=Double.doubleToRawLongBits(v);
                if(bits == 0){
                    bits=0xfffc000000000000L;
                }
            }else{
                bits=0x7ff8000000000000L;
            }
            return bits;
        }
        private static int getTableVal(float v){
            int bits;
            if(v == v){
                bits=Float.floatToRawIntBits(v);
                if(bits == 0){
                    bits=0xffe00000;
                }
            }else{
                bits=0x7fc00000;
            }
            return bits;
        }
        private static Object getTableVal(Object v){
            if(v == null){
                v=NULL;
            }
            return v;
        }
        private static void quickInsert(int[] table,int val){
            int tableLength;
            int hash;
            for(hash=getTableHash(val) & (tableLength=table.length - 1);;){
                if(table[hash] == 0){
                    table[hash]=val;
                    return;
                }
                hash=hash + 1 & tableLength;
            }
        }
        private static void quickInsert(long[] table,long val){
            int tableLength;
            int hash;
            for(hash=getTableHash(val) & (tableLength=table.length - 1);;){
                if(table[hash] == 0){
                    table[hash]=val;
                    return;
                }
                hash=hash + 1 & tableLength;
            }
        }
        private static void quickInsert(Object[] table,Object val){
            int tableLength;
            int hash;
            for(hash=getTableHash(val) & (tableLength=table.length - 1);;){
                if(table[hash] == null){
                    table[hash]=val;
                    return;
                }
                hash=hash + 1 & tableLength;
            }
        }
        final AbstractOpenAddressHashSet<?> set;
        final CheckedType checkedType;
        final DataType dataType;
        int expectedSize;
        int expectedMaxTableSize;
        float expectedLoadFactor;
        int expectedModCount;
        int expectedTableLength;
        Object expectedTable;
        OpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType){
            switch(dataType){
            case FLOAT:
                if(checkedType.checked){
                    set=new FloatOpenAddressHashSet.Checked();
                }else{
                    set=new FloatOpenAddressHashSet();
                }
                break;
            case DOUBLE:
                if(checkedType.checked){
                    set=new DoubleOpenAddressHashSet.Checked();
                }else{
                    set=new DoubleOpenAddressHashSet();
                }
                break;
            case REF:
                if(checkedType.checked){
                    set=new RefOpenAddressHashSet.Checked<>();
                }else{
                    set=new RefOpenAddressHashSet<>();
                }
                break;
            default:
                throw dataType.invalid();
            }
            this.checkedType=checkedType;
            this.dataType=dataType;
            updateCollectionState();
        }
        OpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,float loadFactor){
            switch(dataType){
            case FLOAT:
                if(checkedType.checked){
                    set=new FloatOpenAddressHashSet.Checked(loadFactor);
                }else{
                    set=new FloatOpenAddressHashSet(loadFactor);
                }
                break;
            case DOUBLE:
                if(checkedType.checked){
                    set=new DoubleOpenAddressHashSet.Checked(loadFactor);
                }else{
                    set=new DoubleOpenAddressHashSet(loadFactor);
                }
                break;
            case REF:
                if(checkedType.checked){
                    set=new RefOpenAddressHashSet.Checked<>(loadFactor);
                }else{
                    set=new RefOpenAddressHashSet<>(loadFactor);
                }
                break;
            default:
                throw dataType.invalid();
            }
            this.checkedType=checkedType;
            this.dataType=dataType;
            updateCollectionState();
        }
        OpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,float loadFactor,Collection<?> collection,Class<? extends Collection<?>> collectionClass){
            @SuppressWarnings("rawtypes")
            Class<? extends AbstractOpenAddressHashSet> clazz;
            switch(dataType){
            case FLOAT:
                if(checkedType.checked){
                    clazz= FloatOpenAddressHashSet.Checked.class;
                }else{
                    clazz= FloatOpenAddressHashSet.class;
                }
                break;
            case DOUBLE:
                if(checkedType.checked){
                    clazz= DoubleOpenAddressHashSet.Checked.class;
                }else{
                    clazz= DoubleOpenAddressHashSet.class;
                }
                break;
            case REF:
                if(checkedType.checked){
                    clazz= RefOpenAddressHashSet.Checked.class;
                }else{
                    clazz= RefOpenAddressHashSet.class;
                }
                break;
            default:
                throw dataType.invalid();
            }
            this.checkedType=checkedType;
            this.dataType=dataType;
            try{
                this.set=clazz.getDeclaredConstructor(float.class,collectionClass).newInstance(loadFactor,collection);
            }catch(InvocationTargetException e) {
                var cause=e.getCause();
                if(cause instanceof RuntimeException) {
                    throw (RuntimeException)cause;
                }
                if(cause instanceof Error) {
                    throw (Error)cause;
                }
                throw new Error(cause);
            }catch(InstantiationException | IllegalAccessException
                    | NoSuchMethodException | SecurityException e){
                throw new Error(e);
            }
            updateCollectionState();
        }
        OpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,Collection<?> collection,Class<? extends Collection<?>> collectionClass){
            @SuppressWarnings("rawtypes")
            Class<? extends AbstractOpenAddressHashSet> clazz;
            switch(dataType){
            case FLOAT:
                if(checkedType.checked){
                    clazz= FloatOpenAddressHashSet.Checked.class;
                }else{
                    clazz= FloatOpenAddressHashSet.class;
                }
                break;
            case DOUBLE:
                if(checkedType.checked){
                    clazz= DoubleOpenAddressHashSet.Checked.class;
                }else{
                    clazz= DoubleOpenAddressHashSet.class;
                }
                break;
            case REF:
                if(checkedType.checked){
                    clazz= RefOpenAddressHashSet.Checked.class;
                }else{
                    clazz= RefOpenAddressHashSet.class;
                }
                break;
            default:
                throw dataType.invalid();
            }
            this.checkedType=checkedType;
            this.dataType=dataType;
            try{
                this.set=clazz.getDeclaredConstructor(collectionClass).newInstance(collection);
            }catch(InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e){
                throw new Error(e);
            }
            updateCollectionState();
        }
        OpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,int initialCapacity){
            switch(dataType){
            case FLOAT:
                if(checkedType.checked){
                    set=new FloatOpenAddressHashSet.Checked(initialCapacity);
                }else{
                    set=new FloatOpenAddressHashSet(initialCapacity);
                }
                break;
            case DOUBLE:
                if(checkedType.checked){
                    set=new DoubleOpenAddressHashSet.Checked(initialCapacity);
                }else{
                    set=new DoubleOpenAddressHashSet(initialCapacity);
                }
                break;
            case REF:
                if(checkedType.checked){
                    set=new RefOpenAddressHashSet.Checked<>(initialCapacity);
                }else{
                    set=new RefOpenAddressHashSet<>(initialCapacity);
                }
                break;
            default:
                throw dataType.invalid();
            }
            this.checkedType=checkedType;
            this.dataType=dataType;
            updateCollectionState();
        }
        OpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,int initialCapacity,float loadFactor){
            switch(dataType){
            case FLOAT:
                if(checkedType.checked){
                    set=new FloatOpenAddressHashSet.Checked(initialCapacity,loadFactor);
                }else{
                    set=new FloatOpenAddressHashSet(initialCapacity,loadFactor);
                }
                break;
            case DOUBLE:
                if(checkedType.checked){
                    set=new DoubleOpenAddressHashSet.Checked(initialCapacity,loadFactor);
                }else{
                    set=new DoubleOpenAddressHashSet(initialCapacity,loadFactor);
                }
                break;
            case REF:
                if(checkedType.checked){
                    set=new RefOpenAddressHashSet.Checked<>(initialCapacity,loadFactor);
                }else{
                    set=new RefOpenAddressHashSet<>(initialCapacity,loadFactor);
                }
                break;
            default:
                throw dataType.invalid();
            }
            this.checkedType=checkedType;
            this.dataType=dataType;
            updateCollectionState();
        }
        @Override
        public Object get(int iterationIndex,DataType outputType) {
            switch(dataType) {
            case FLOAT:{
                var itr=(OmniIterator.OfFloat)set.iterator();
                while(iterationIndex>0) {
                    itr.nextFloat();
                }
                return outputType.convertVal(itr.nextFloat());
            }
            case DOUBLE:{
                var itr=(OmniIterator.OfDouble)set.iterator();
                while(iterationIndex>0) {
                    itr.nextDouble();
                }
                return outputType.convertVal(itr.nextDouble());
            }
            case REF:{
                var itr=(OmniIterator.OfRef<?>)set.iterator();
                while(iterationIndex>0) {
                    itr.next();
                }
                return outputType.convertVal(itr.next());
            }
            default:
                throw dataType.invalid();
            }
            
        }
        @Override public CheckedType getCheckedType(){
            return checkedType;
        }
        @Override public AbstractOpenAddressHashSet<?> getCollection(){
            return set;
        }
        @Override public DataType getDataType(){
            return dataType;
        }
        @Override public MonitoredIterator<? extends OmniIterator<?>,AbstractOpenAddressHashSet<?>> getMonitoredIterator(){
            int expectedOffset;
            int numLeft;
            if((numLeft=expectedSize) != 0){
                outer:for(;;){
                    switch(dataType){
                    case FLOAT:{
                        final int[] table=(int[])expectedTable;
                        for(expectedOffset=table.length;;){
                            switch(table[--expectedOffset]){
                            default:
                                break outer;
                            case 0:
                            case 0x7fe00000:
                            }
                        }
                    }
                    case DOUBLE:{
                        final long[] table=(long[])expectedTable;
                        for(expectedOffset=table.length;;){
                            long tableVal;
                            if((tableVal=table[--expectedOffset]) != 0 && tableVal != 0x7ffc000000000000L){
                                break outer;
                            }
                        }
                    }
                    case REF:{
                        final Object[] table=(Object[])expectedTable;
                        for(expectedOffset=table.length;;){
                            Object tableVal;
                            if((tableVal=table[--expectedOffset]) != null && tableVal != DELETED){
                                break outer;
                            }
                        }
                    }
                    default:
                        throw dataType.invalid();
                    }
                }
            }else{
                expectedOffset=-1;
            }
            final var itr=set.iterator();
            if(checkedType.checked){ return new CheckedItrMonitor(itr,expectedOffset,numLeft,expectedModCount); }
            return new UncheckedItrMonitor(itr,expectedOffset,numLeft);
        }
        @Override public StructType getStructType(){
            return StructType.OpenAddressHashSet;
        }
        @SuppressWarnings("unchecked") @Override public void modCollection(){
            outer:for(;;){
                switch(dataType){
                case FLOAT:{
                    final var cast=(FloatOpenAddressHashSet)set;
                    if(cast.add(Float.NaN)){
                        break outer;
                    }
                    if(cast.add(-0.0f)){
                        break outer;
                    }
                    if(cast.add(Float.NEGATIVE_INFINITY)){
                        break outer;
                    }
                    if(cast.add(Float.POSITIVE_INFINITY)){
                        break outer;
                    }
                    float f=-Float.MAX_VALUE;
                    for(;;){
                        if(cast.add(f)){
                            break outer;
                        }
                        f=Math.nextUp(f);
                        if(f == Float.POSITIVE_INFINITY){
                            break;
                        }
                    }
                    break;
                }
                case DOUBLE:{
                    final var cast=(DoubleOpenAddressHashSet)set;
                    if(cast.add(Double.NaN)){
                        break outer;
                    }
                    if(cast.add(-0.0d)){
                        break outer;
                    }
                    if(cast.add(Double.NEGATIVE_INFINITY)){
                        break outer;
                    }
                    if(cast.add(Double.POSITIVE_INFINITY)){
                        break outer;
                    }
                    double d=-Double.MAX_VALUE;
                    for(;;){
                        if(cast.add(d)){
                            break outer;
                        }
                        d=Math.nextUp(d);
                        if(d == Double.POSITIVE_INFINITY){
                            break;
                        }
                    }
                    break;
                }
                case REF:{
                    ((RefOpenAddressHashSet<Object>)set).add(new Object());
                    break outer;
                }
                default:
                    throw dataType.invalid();
                }
                set.clear();
                break;
            }
        updateCollectionState();
        }
        @Override public int size(){
            return expectedSize;
        }
        @Override public void updateAddState(Object inputVal,DataType inputType,boolean result){
            if(result){
                switch(dataType){
                case FLOAT:{
                    float v;
                    switch(inputType){
                    case BOOLEAN:
                        v=(boolean)inputVal?1.0f:0.0f;
                        break;
                    case BYTE:
                        v=(byte)inputVal;
                        break;
                    case CHAR:
                        v=(char)inputVal;
                        break;
                    case SHORT:
                        v=(short)inputVal;
                        break;
                    case INT:
                        v=(int)inputVal;
                        break;
                    case LONG:
                        v=(long)inputVal;
                        break;
                    case FLOAT:
                        v=(float)inputVal;
                        break;
                    default:
                        throw inputType.invalid();
                    }
                    insertInTable(v);
                    break;
                }
                case DOUBLE:{
                    double v;
                    switch(inputType){
                    case BOOLEAN:
                        v=(boolean)inputVal?1.0d:0.0d;
                        break;
                    case BYTE:
                        v=(byte)inputVal;
                        break;
                    case CHAR:
                        v=(char)inputVal;
                        break;
                    case SHORT:
                        v=(short)inputVal;
                        break;
                    case INT:
                        v=(int)inputVal;
                        break;
                    case LONG:
                        v=(long)inputVal;
                        break;
                    case FLOAT:
                        v=(float)inputVal;
                        break;
                    case DOUBLE:
                        v=(double)inputVal;
                        break;
                    default:
                        throw inputType.invalid();
                    }
                    insertInTable(v);
                    break;
                }
                case REF:{
                    insertInTable(inputVal);
                    break;
                }
                default:
                    throw dataType.invalid();
                }
                ++expectedModCount;
            }
        }
        @Override public void updateClearState() {
            switch(dataType){
            case FLOAT:{
                final var table=(int[])expectedTable;
                for(int i=table.length;--i >= 0;){
                    table[i]=0;
                }
                break;
            }
            case DOUBLE:{
                final var table=(long[])expectedTable;
                for(int i=table.length;--i >= 0;){
                    table[i]=0;
                }
                break;
            }
            case REF:{
                final var table=(Object[])expectedTable;
                for(int i=table.length;--i >= 0;){
                    table[i]=null;
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            expectedSize=0;
            ++expectedModCount;
        }
        @Override public void updateCollectionState(){
            AbstractOpenAddressHashSet<?> set;
            expectedSize=(set=this.set).size;
            expectedLoadFactor=set.loadFactor;
            expectedMaxTableSize=set.maxTableSize;
            switch(dataType){
            case FLOAT:{
                final var castSet=(FloatOpenAddressHashSet)set;
                if(checkedType.checked){
                    expectedModCount=((FloatOpenAddressHashSet.Checked)castSet).modCount;
                }
                int[] table;
                if((table=castSet.table) == null){
                    expectedTable=null;
                    expectedTableLength=0;
                }else{
                    int expectedTableLength,tableLength;
                    if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                        Object newExpectedTable;
                        System.arraycopy(table,0,newExpectedTable=new int[tableLength],0,tableLength);
                        expectedTable=newExpectedTable;
                        this.expectedTableLength=tableLength;
                    }else{
                        System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                    }
                }
                break;
            }
            case DOUBLE:{
                final var castSet=(DoubleOpenAddressHashSet)set;
                if(checkedType.checked){
                    expectedModCount=((DoubleOpenAddressHashSet.Checked)castSet).modCount;
                }
                long[] table;
                if((table=castSet.table) == null){
                    expectedTable=null;
                    expectedTableLength=0;
                }else{
                    int expectedTableLength,tableLength;
                    if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                        Object newExpectedTable;
                        System.arraycopy(table,0,newExpectedTable=new long[tableLength],0,tableLength);
                        expectedTable=newExpectedTable;
                        this.expectedTableLength=tableLength;
                    }else{
                        System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                    }
                }
                break;
            }
            case REF:{
                final var castSet=(RefOpenAddressHashSet<?>)set;
                if(checkedType.checked){
                    expectedModCount=((RefOpenAddressHashSet.Checked<?>)castSet).modCount;
                }
                Object[] table;
                if((table=castSet.table) == null){
                    expectedTable=null;
                    expectedTableLength=0;
                }else{
                    int expectedTableLength,tableLength;
                    if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
                        Object newExpectedTable;
                        System.arraycopy(table,0,newExpectedTable=new Object[tableLength],0,tableLength);
                        expectedTable=newExpectedTable;
                        this.expectedTableLength=tableLength;
                    }else{
                        System.arraycopy(table,0,expectedTable,0,expectedTableLength);
                    }
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
        }
        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            switch(dataType){
            case FLOAT:
                Assertions.assertNotSame(((FloatOpenAddressHashSet)set).table,arr);
                break;
            case DOUBLE:
                Assertions.assertNotSame(((DoubleOpenAddressHashSet)set).table,arr);
                break;
            case REF:
                Assertions.assertNotSame(((RefOpenAddressHashSet<?>)set).table,arr);
                break;
            default:
                throw dataType.invalid();
            }
        }
        @Override public void verifyClone(Object clone){
            AbstractOpenAddressHashSet<?> cloneSet;
            Assertions.assertEquals(expectedSize,(cloneSet=(AbstractOpenAddressHashSet<?>)clone).size);
            Assertions.assertEquals(expectedLoadFactor,cloneSet.loadFactor);
            int expectedTableLength=this.expectedTableLength;
            switch(dataType){
            case FLOAT:
                if(checkedType.checked){
                    Assertions.assertEquals(0,((FloatOpenAddressHashSet.Checked)cloneSet).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((FloatOpenAddressHashSet)cloneSet).table);
                }else{
                    final var expectedTable=(int[])this.expectedTable;
                    while(--expectedTableLength >= 0){
                        int v;
                        switch(v=expectedTable[expectedTableLength]){
                        case 0xffe00000: // pos0
                            v=0;
                        default:
                            Assertions.assertTrue(cloneSet.contains(Float.intBitsToFloat(v)));
                        case 0: // empty
                        case 0x7fe00000: // deleted
                        }
                    }
                }
                break;
            case DOUBLE:
                if(checkedType.checked){
                    Assertions.assertEquals(0,((DoubleOpenAddressHashSet.Checked)cloneSet).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((DoubleOpenAddressHashSet)cloneSet).table);
                }else{
                    final var expectedTable=(long[])this.expectedTable;
                    while(--expectedTableLength >= 0){
                        long v;
                        if((v=expectedTable[expectedTableLength]) != 0L && v != 0x7ffc000000000000L){
                            if(v == 0xfffc000000000000L){
                                v=0L;
                            }
                            Assertions.assertTrue(cloneSet.contains(Double.longBitsToDouble(v)));
                        }
                    }
                }
                break;
            case REF:
                if(checkedType.checked){
                    Assertions.assertEquals(0,((RefOpenAddressHashSet.Checked<?>)cloneSet).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((RefOpenAddressHashSet<?>)cloneSet).table);
                }else{
                    final var expectedTable=(Object[])this.expectedTable;
                    while(--expectedTableLength >= 0){
                        Object v;
                        if((v=expectedTable[expectedTableLength]) != null && v != DELETED){
                            if(v == NULL){
                                v=null;
                            }
                            Assertions.assertTrue(cloneSet.contains(v));
                        }
                    }
                }
                break;
            default:
                throw dataType.invalid();
            }
            verifyStructuralIntegrity(cloneSet);
        }
        @Override public void verifyCollectionState(){
            AbstractOpenAddressHashSet<?> set;
            Assertions.assertEquals(expectedSize,(set=this.set).size);
            Assertions.assertEquals(expectedMaxTableSize,set.maxTableSize);
            Assertions.assertEquals(expectedLoadFactor,set.loadFactor);
            switch(dataType){
            case FLOAT:
                if(checkedType.checked){
                    Assertions.assertEquals(expectedModCount,((FloatOpenAddressHashSet.Checked)set).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((FloatOpenAddressHashSet)set).table);
                }else{
                    Assertions.assertArrayEquals((int[])expectedTable,((FloatOpenAddressHashSet)set).table);
                }
                break;
            case DOUBLE:
                if(checkedType.checked){
                    Assertions.assertEquals(expectedModCount,((DoubleOpenAddressHashSet.Checked)set).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((DoubleOpenAddressHashSet)set).table);
                }else{
                    Assertions.assertArrayEquals((long[])expectedTable,((DoubleOpenAddressHashSet)set).table);
                }
                break;
            case REF:
                if(checkedType.checked){
                    Assertions.assertEquals(expectedModCount,((RefOpenAddressHashSet.Checked<?>)set).modCount);
                }
                if(expectedTableLength == 0){
                    Assertions.assertNull(((RefOpenAddressHashSet<?>)set).table);
                }else{
                    Assertions.assertArrayEquals((Object[])expectedTable,((RefOpenAddressHashSet<?>)set).table);
                }
                break;
            default:
                throw dataType.invalid();
            }
            verifyStructuralIntegrity(set);
        }
        @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            Assertions.assertEquals(expectedSize,filter.numCalls);
            Assertions.assertEquals(filter.numRemoved,filter.removedVals.size());
            Assertions.assertEquals(filter.numRetained,filter.retainedVals.size());
            if(result){
                ++expectedModCount;
                final var removedValsItr=filter.removedVals.iterator();
                Assertions.assertTrue(removedValsItr.hasNext());
                final var retainedValsItr=filter.retainedVals.iterator();
                switch(dataType){
                case FLOAT:{
                    do{
                        final var v=(float)removedValsItr.next();
                        Assertions.assertFalse(set.contains(v));
                        removeVal(v);
                    }while(removedValsItr.hasNext());
                    while(retainedValsItr.hasNext()){
                        final var v=(float)retainedValsItr.next();
                        Assertions.assertTrue(set.contains(v));
                    }
                    break;
                }
                case DOUBLE:{
                    do{
                        final var v=(double)removedValsItr.next();
                        Assertions.assertFalse(set.contains(v));
                        removeVal(v);
                    }while(removedValsItr.hasNext());
                    while(retainedValsItr.hasNext()){
                        final var v=(double)retainedValsItr.next();
                        Assertions.assertTrue(set.contains(v));
                    }
                    break;
                }
                case REF:{
                    do{
                        final var v=removedValsItr.next();
                        Assertions.assertFalse(set.contains(v));
                        removeVal(v);
                    }while(removedValsItr.hasNext());
                    while(retainedValsItr.hasNext()){
                        final var v=retainedValsItr.next();
                        Assertions.assertTrue(set.contains(v));
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
            }
        }
        public void verifySetLoadFactor(float newLoadFactor){
            if(newLoadFactor <= 0.0f || newLoadFactor > 1.0f || newLoadFactor != newLoadFactor){
                Assertions.assertThrows(IllegalArgumentException.class,()->set.setLoadFactor(newLoadFactor));
            }else{
                set.setLoadFactor(newLoadFactor);
                expectedLoadFactor=newLoadFactor;
                if(expectedTable != null){
                    int tableLength;
                    switch(dataType){
                    case FLOAT:
                        tableLength=((int[])expectedTable).length;
                        break;
                    case DOUBLE:
                        tableLength=((long[])expectedTable).length;
                        break;
                    case REF:
                        tableLength=((Object[])expectedTable).length;
                        break;
                    default:
                        throw dataType.invalid();
                    }
                    expectedMaxTableSize=(int)(tableLength * newLoadFactor);
                }
            }
            verifyCollectionState();
        }
        @Override public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            set.writeExternal(oos);
        }
        private void insert(int[] table,int hash,int val){
            int tableSize;
            if((tableSize=++expectedSize) >= expectedMaxTableSize){
                expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
                int[] newTable;
                expectedTable=newTable=new int[expectedTableLength=hash];
                if(tableSize != 1){
                    for(int i=0;;++i){
                        int tableVal;
                        switch(tableVal=table[i]){
                        case 0: // empty
                        case 0x7fe00000: // deleted
                            continue;
                        default:
                        }
                        quickInsert(newTable,tableVal);
                        if(--tableSize == 1){
                            break;
                        }
                    }
                }
                quickInsert(newTable,val);
            }else{
                table[hash]=val;
            }
        }
        private void insert(long[] table,int hash,long val){
            int tableSize;
            if((tableSize=++expectedSize) >= expectedMaxTableSize){
                expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
                long[] newTable;
                expectedTable=newTable=new long[expectedTableLength=hash];
                if(tableSize != 1){
                    for(int i=0;;++i){
                        long tableVal;
                        if((tableVal=table[i]) != 0 && tableVal != 0x7ffc000000000000L){
                            quickInsert(newTable,tableVal);
                            if(--tableSize == 1){
                                break;
                            }
                        }
                    }
                }
                quickInsert(newTable,val);
            }else{
                table[hash]=val;
            }
        }
        private void insert(Object[] table,int hash,Object val){
            int tableSize;
            if((tableSize=++expectedSize) >= expectedMaxTableSize){
                expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
                Object[] newTable;
                expectedTable=newTable=new Object[expectedTableLength=hash];
                if(tableSize != 1){
                    for(int i=0;;++i){
                        Object tableVal;
                        if((tableVal=table[i]) != null && tableVal != DELETED){
                            quickInsert(newTable,tableVal);
                            if(--tableSize == 1){
                                break;
                            }
                        }
                    }
                }
                quickInsert(newTable,val);
            }else{
                table[hash]=val;
            }
        }
        private void insertInTable(double v){
            long tableVal;
            int hash=getTableHash(tableVal=getTableVal(v));
            long[] t;
            if((t=(long[])expectedTable) == null){
                int expectedTableLength;
                expectedTable=t=new long[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
                t[hash & expectedTableLength - 1]=tableVal;
                expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
                expectedSize=1;
            }else{
                int insertHere=-1;
                int expectedTableLength;
                insertInTable:for(final int initialHash=hash&=expectedTableLength=this.expectedTableLength - 1;;){
                    long existingVal;
                    if((existingVal=t[hash]) == 0){
                        if(insertHere == -1){
                            insertHere=hash;
                        }
                        break insertInTable;
                    }else{
                        if(existingVal == 0x7ffc000000000000L){
                            insertHere=hash;
                        }
                        if((hash=hash + 1 & expectedTableLength) == initialHash){
                            break insertInTable;
                        }
                    }
                }
                insert(t,insertHere,tableVal);
            }
        }
        private void insertInTable(float v){
            int tableVal;
            int hash=getTableHash(tableVal=getTableVal(v));
            int[] t;
            if((t=(int[])expectedTable) == null){
                int expectedTableLength;
                expectedTable=t=new int[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
                t[hash & expectedTableLength - 1]=tableVal;
                expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
                expectedSize=1;
            }else{
                int insertHere=-1;
                int expectedTableLength;
                insertInTable:for(final int initialHash=hash&=expectedTableLength=this.expectedTableLength - 1;;){
                    switch(t[hash]){
                    case 0:
                        if(insertHere == -1){
                            insertHere=hash;
                        }
                        break insertInTable;
                    case 0x7fe00000:
                        insertHere=hash;
                    default:
                        if((hash=hash + 1 & expectedTableLength) == initialHash){
                            break insertInTable;
                        }
                    }
                }
                insert(t,insertHere,tableVal);
            }
        }
        private void insertInTable(Object v){
            int hash=getTableHash(v=getTableVal(v));
            Object[] t;
            if((t=(Object[])expectedTable) == null){
                int expectedTableLength;
                expectedTable=t=new Object[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
                t[hash & expectedTableLength - 1]=v;
                expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
                expectedSize=1;
            }else{
                int insertHere=-1;
                int expectedTableLength;
                insertInTable:for(final int initialHash=hash&=expectedTableLength=this.expectedTableLength - 1;;){
                    Object existingVal;
                    if((existingVal=t[hash]) == null){
                        if(insertHere == -1){
                            insertHere=hash;
                        }
                        break insertInTable;
                    }else{
                        if(existingVal == DELETED){
                            insertHere=hash;
                        }
                        if((hash=hash + 1 & expectedTableLength) == initialHash){
                            break insertInTable;
                        }
                    }
                }
                insert(t,insertHere,v);
            }
        }
        @Override
        public void removeFromExpectedState(DataType inputType,QueryVal queryVal,QueryValModification modification){
            final Object inputVal=queryVal.getInputVal(inputType,modification);
            switch(dataType){
            case FLOAT:{
                removeVal((float)inputVal);
                break;
            }
            case DOUBLE:{
                removeVal((double)inputVal);
                break;
            }
            case REF:{
                removeVal(inputVal);
                break;
            }
            default:
                throw dataType.invalid();
            }
            ++expectedModCount;
        }
        private void removeVal(double v){
            final long[] expectedTable;
            int expectedTableLength;
            long tableVal;
            int hash=getTableHash(tableVal=getTableVal(v))
                    & (expectedTableLength=(expectedTable=(long[])this.expectedTable).length - 1);
            for(;;){
                if(expectedTable[hash] == tableVal){
                    expectedTable[hash]=0x7ffc000000000000L; // deleted
                    break;
                }
                hash=hash + 1 & expectedTableLength;
            }
            --expectedSize;
        }
        private void removeVal(float v){
            final int[] expectedTable;
            int expectedTableLength;
            int tableVal;
            int hash=getTableHash(tableVal=getTableVal(v))
                    & (expectedTableLength=(expectedTable=(int[])this.expectedTable).length - 1);
            for(;;){
                if(expectedTable[hash] == tableVal){
                    expectedTable[hash]=0x7fe00000; // deleted
                    break;
                }
                hash=hash + 1 & expectedTableLength;
            }
            --expectedSize;
        }
        private void removeVal(Object v){
            final Object[] expectedTable;
            int expectedTableLength;
            int hash=getTableHash(v=getTableVal(v))
                    & (expectedTableLength=(expectedTable=(Object[])this.expectedTable).length - 1);
            for(;;){
                if(v.equals(expectedTable[hash])){
                    expectedTable[hash]=DELETED; // deleted
                    break;
                }
                hash=hash + 1 & expectedTableLength;
            }
            --expectedSize;
        }
        private void verifyStructuralIntegrity(AbstractOpenAddressHashSet<?> set){
            final HashSet<Object> encounteredVals=new HashSet<>(set.size);
            switch(dataType){
            case FLOAT:{
                int[] table;
                if((table=((FloatOpenAddressHashSet)set).table) != null){
                    int i=table.length;
                    // check that it is a power of 2
                    Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                    Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                    while(--i >= 0){
                        int v;
                        switch(v=table[i]){
                        case 0: // empty
                        case 0x7fe00000: // deleted
                            break;
                        default:
                            Assertions.assertTrue(encounteredVals.add(v));
                        }
                    }
                }
                break;
            }
            case DOUBLE:{
                long[] table;
                if((table=((DoubleOpenAddressHashSet)set).table) != null){
                    int i=table.length;
                    // check that it is a power of 2
                    Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                    Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                    while(--i >= 0){
                        long v;
                        if((v=table[i]) != 0 && v != 0x7ffc000000000000L){
                            Assertions.assertTrue(encounteredVals.add(v));
                        }
                    }
                }
                break;
            }
            case REF:{
                Object[] table;
                if((table=((RefOpenAddressHashSet<?>)set).table) != null){
                    int i=table.length;
                    // check that it is a power of 2
                    Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
                    Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
                    while(--i >= 0){
                        Object v;
                        if((v=table[i]) != null && v != DELETED){
                            Assertions.assertTrue(encounteredVals.add(v));
                        }
                    }
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            Assertions.assertEquals(encounteredVals.size(),set.size);
        }
        abstract class AbstractItrMonitor
        implements MonitoredSet.MonitoredSetIterator<OmniIterator<?>,AbstractOpenAddressHashSet<?>>{
            final OmniIterator<?> itr;
            int expectedOffset;
            int expectedNumLeft;
            AbstractItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft){
                this.itr=itr;
                this.expectedOffset=expectedOffset;
                this.expectedNumLeft=expectedNumLeft;
            }
            @Override public OmniIterator<?> getIterator(){
                return itr;
            }
            @Override public MonitoredCollection<AbstractOpenAddressHashSet<?>> getMonitoredCollection(){
                return OpenAddressHashSetMonitor.this;
            }
            @Override public int getNumLeft(){
                return expectedNumLeft;
            }
            @Override public boolean hasNext(){
                return expectedOffset != -1;
            }
            @Override public void updateItrNextState(){
                int expectedOffset=this.expectedOffset;
                switch(dataType){
                case FLOAT:{
                    final int[] table=(int[])expectedTable;
                    setOffset:for(;;){
                        if(--expectedOffset == -1){
                            break;
                        }
                        switch(table[expectedOffset]){
                        default:
                            break setOffset;
                        case 0x7fe00000:
                        case 0:
                        }
                    }
                    break;
                }
                case DOUBLE:{
                    final long[] table=(long[])expectedTable;
                    for(;;){
                        if(--expectedOffset == -1){
                            break;
                        }
                        long tableVal;
                        if((tableVal=table[expectedOffset]) != 0 && tableVal != 0x7ffc000000000000L){
                            break;
                        }
                    }
                    break;
                }
                case REF:{
                    final Object[] table=(Object[])expectedTable;
                    for(;;){
                        if(--expectedOffset == -1){
                            break;
                        }
                        Object tableVal;
                        if((tableVal=table[expectedOffset]) != null && tableVal != DELETED){
                            break;
                        }
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
                this.expectedOffset=expectedOffset;
                --expectedNumLeft;
            }
            @Override public void verifyNextResult(DataType outputType,Object result){
                Object expectedResult;
                switch(dataType){
                case FLOAT:{
                    int bits;
                    if((bits=((int[])expectedTable)[expectedOffset]) == 0xffe00000){
                        bits=0;
                    }
                    expectedResult=outputType.convertVal(Float.intBitsToFloat(bits));
                    break;
                }
                case DOUBLE:{
                    long bits;
                    if((bits=((long[])expectedTable)[expectedOffset]) == 0xfffc000000000000L){
                        bits=0;
                    }
                    expectedResult=Double.longBitsToDouble(bits);
                    break;
                }
                case REF:{
                    if((expectedResult=((Object[])expectedTable)[expectedOffset]) == NULL){
                        expectedResult=null;
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
                Assertions.assertEquals(expectedResult,result);
            }
            private int verifyForEachRemainingHelper(MonitoredFunction function,int expectedLastRet){
                final var monitoredFunctionItr=function.iterator();
                int expectedOffset;
                if((expectedOffset=this.expectedOffset) != -1){
                    switch(dataType){
                    case FLOAT:{
                        final int[] table=(int[])expectedTable;
                        for(;;--expectedOffset){
                            int tableVal;
                            switch(tableVal=table[expectedOffset]){
                            case 0:
                            case 0x7fe00000:
                                continue;
                            case 0xffe00000:
                                tableVal=0;
                            default:
                                final var v=(Float)monitoredFunctionItr.next();
                                Assertions.assertEquals(v.floatValue(),Float.intBitsToFloat(tableVal));
                                expectedLastRet=expectedOffset;
                                if(--expectedNumLeft != 0){
                                    continue;
                                }
                            }
                            break;
                        }
                        break;
                    }
                    case DOUBLE:{
                        final long[] table=(long[])expectedTable;
                        for(;;--expectedOffset){
                            long tableVal;
                            if((tableVal=table[expectedOffset]) != 0 && tableVal != 0x7ffc000000000000L){
                                if(tableVal == 0xfffc000000000000L){
                                    tableVal=0;
                                }
                                final var v=(Double)monitoredFunctionItr.next();
                                Assertions.assertEquals(v.doubleValue(),Double.longBitsToDouble(tableVal));
                                expectedLastRet=expectedOffset;
                                if(--expectedNumLeft == 0){
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    case REF:{
                        final Object[] table=(Object[])expectedTable;
                        for(;;--expectedOffset){
                            Object tableVal;
                            if((tableVal=table[expectedOffset]) != null && tableVal != DELETED){
                                if(tableVal == NULL){
                                    tableVal=null;
                                }
                                final var v=monitoredFunctionItr.next();
                                Assertions.assertEquals(v,tableVal);
                                expectedLastRet=expectedOffset;
                                if(--expectedNumLeft == 0){
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    default:
                        throw dataType.invalid();
                    }
                }
                Assertions.assertFalse(monitoredFunctionItr.hasNext());
                this.expectedOffset=-1;
                return expectedLastRet;
            }
        }
        private class CheckedItrMonitor extends AbstractItrMonitor{
            int expectedItrModCount;
            int expectedItrLastRet;
            CheckedItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft,int expectedItrModCount){
                super(itr,expectedOffset,expectedNumLeft);
                this.expectedItrModCount=expectedItrModCount;
                expectedItrLastRet=-1;
            }
            @Override public void updateItrNextState(){
                expectedItrLastRet=expectedOffset;
                super.updateItrNextState();
            }
            @Override public void updateItrRemoveState(){
                ++expectedModCount;
                ++expectedItrModCount;
                --expectedSize;
                switch(dataType){
                case FLOAT:
                    ((int[])expectedTable)[expectedItrLastRet]=0x7fe00000;
                    break;
                case DOUBLE:
                    ((long[])expectedTable)[expectedItrLastRet]=0x7ffc000000000000L;
                    break;
                case REF:
                    ((Object[])expectedTable)[expectedItrLastRet]=DELETED;
                    break;
                default:
                    throw dataType.invalid();
                }
                expectedItrLastRet=-1;
            }
            @Override public void verifyForEachRemaining(MonitoredFunction function){
                expectedItrLastRet=super.verifyForEachRemainingHelper(function,expectedItrLastRet);
            }
            @Override
            public void verifyCloneHelper(Object clone){
                switch(dataType){
                case FLOAT:
                    Assertions.assertSame(set,FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.offset(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.modCount(clone));
                    Assertions.assertEquals(expectedItrLastRet,
                            FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.lastRet(clone));
                    break;
                case DOUBLE:
                    Assertions.assertSame(set,FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.offset(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.modCount(clone));
                    Assertions.assertEquals(expectedItrLastRet,
                            FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.lastRet(clone));
                    break;
                case REF:
                    Assertions.assertSame(set,FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.offset(clone));
                    Assertions.assertEquals(expectedItrModCount,
                            FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.modCount(clone));
                    Assertions.assertEquals(expectedItrLastRet,
                            FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.lastRet(clone));
                    break;
                default:
                    throw dataType.invalid();
                }
            }
            @Override
            public boolean nextWasJustCalled(){
                return expectedItrLastRet != -1;
            }
        }
        private class UncheckedItrMonitor extends AbstractItrMonitor{
            UncheckedItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft){
                super(itr,expectedOffset,expectedNumLeft);
            }
            @Override
            public boolean nextWasJustCalled(){
                switch(dataType){
                case FLOAT:{
                    final int[] table=(int[])expectedTable;
                    for(int offset=expectedOffset;;){
                        switch(table[++offset]){
                        default:
                            return true;
                        case 0:
                        case 0x7fe00000:
                        }
                        if(offset == table.length - 1){
                            break;
                        }
                    }
                    break;
                }
                case DOUBLE:{
                    final long[] table=(long[])expectedTable;
                    for(int offset=expectedOffset;;){
                        long tableVal;
                        if((tableVal=table[++offset]) != 0 && tableVal != 0x7ffc000000000000L){
                            return true;
                        }
                        if(offset == table.length - 1){
                            break;
                        }
                    }
                    break;
                }
                case REF:{
                    final Object[] table=(Object[])expectedTable;
                    for(int offset=expectedOffset;;){
                        Object tableVal;
                        if((tableVal=table[++offset]) != null && tableVal != DELETED){
                            return true;
                        }
                        if(offset == table.length - 1){
                            break;
                        }
                    }
                    break;
                }
                default:
                    throw dataType.invalid();
                }
                return false;
            }
            @Override public void updateItrRemoveState(){
                --expectedSize;
                switch(dataType){
                case FLOAT:{
                    final int[] table=(int[])expectedTable;
                    for(int offset=expectedOffset;;){
                        switch(table[++offset]){
                        default:
                            table[offset]=0x7fe00000;
                            return;
                        case 0:
                        case 0x7fe00000:
                        }
                    }
                }
                case DOUBLE:{
                    final long[] table=(long[])expectedTable;
                    for(int offset=expectedOffset;;){
                        long tableVal;
                        if((tableVal=table[++offset]) != 0 && tableVal != 0x7ffc000000000000L){
                            table[offset]=0x7ffc000000000000L;
                            return;
                        }
                    }
                }
                case REF:{
                    final Object[] table=(Object[])expectedTable;
                    for(int offset=expectedOffset;;){
                        Object tableVal;
                        if((tableVal=table[++offset]) != null && tableVal != DELETED){
                            table[offset]=DELETED;
                            return;
                        }
                    }
                }
                default:
                    throw dataType.invalid();
                }
            }
            @Override public void verifyForEachRemaining(MonitoredFunction function){
                super.verifyForEachRemainingHelper(function,-1);
            }
            @Override
            public void verifyCloneHelper(Object clone){
                switch(dataType){
                case FLOAT:
                    Assertions.assertSame(set,FieldAndMethodAccessor.FloatOpenAddressHashSet.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.FloatOpenAddressHashSet.Itr.offset(clone));
                    break;
                case DOUBLE:
                    Assertions.assertSame(set,FieldAndMethodAccessor.DoubleOpenAddressHashSet.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.DoubleOpenAddressHashSet.Itr.offset(clone));
                    break;
                case REF:
                    Assertions.assertSame(set,FieldAndMethodAccessor.RefOpenAddressHashSet.Itr.root(clone));
                    Assertions.assertEquals(expectedOffset,
                            FieldAndMethodAccessor.RefOpenAddressHashSet.Itr.offset(clone));
                    break;
                default:
                    throw dataType.invalid();
                }
            }
        }
    }
    private interface QueryTest{
        static final int[] sizes=new int[]{0,1,2,3,4,5,8,13,16,21,32,34,55,64,89,128,129,144,233,256,257,377,512,610,987};
        static final double[] positions=new double[]{-1,0,0.25,0.5,0.75,1};
        boolean callMethod(OpenAddressHashSetMonitor monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification,MonitoredObjectGen monitoredObjectGen);
        private void runAllTests(String testName){
            for(final var loadFactor:LOAD_FACTORS){
                if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                        for(final var queryVal:QueryVal.values()){
                            if(collectionType.isValidQueryVal(queryVal)){
                                queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                                    castTypesToInputTypes.forEach((castType,inputTypes)->{
                                        inputTypes.forEach(inputType->{
                
                                            if(queryVal == QueryVal.NonNull){
                                                for(var monitoredObjectGen:StructType.OpenAddressHashSet.validMonitoredObjectGens){
                                                    if(monitoredObjectGen.expectedException != null){
                                                        for(final var size:sizes){
                                                            if(size > 0){
                                                                TestExecutorService.submitTest(()->
                                                                Assertions.assertThrows(
                                                                        monitoredObjectGen.expectedException,
                                                                        ()->runTest(collectionType,queryVal,
                                                                                modification,inputType,castType,
                                                                                CheckedType.CHECKED,size,
                                                                                loadFactor,-1,
                                                                                monitoredObjectGen))
                                                                )
                                                                ;
                                                            }
                                                        }
                                                    }
                                                }
                                            }else{
                                                final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(
                                                        modification,castType,inputType,collectionType);
                                                for(final var checkedType:CheckedType.values()){
                                                    for(final var size:sizes){
                                                        for(final var position:positions){
                                                            if(position >= 0){
                                                                if(!queryCanReturnTrue){
                                                                    continue;
                                                                }
                                                                switch(size){
                                                                case 3:
                                                                    if(position == 0.5d){
                                                                        break;
                                                                    }
                                                                case 2:
                                                                    if(position == 1.0d){
                                                                        break;
                                                                    }
                                                                case 1:
                                                                    if(position == 0.0d){
                                                                        break;
                                                                    }
                                                                case 0:
                                                                    continue;
                                                                case 4:
                                                                    if(position != 0.5d){
                                                                        break;
                                                                    }
                                                                default:
                                                                    continue;
                                                                }
                                                            }
                                                            TestExecutorService.submitTest(()->

                                                            runTest(collectionType,
                                                                    queryVal,modification,inputType,castType,
                                                                    checkedType,size,loadFactor,position,null)
                                                            )
                                                            ;
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                    });
                                });
                            }

                        }
                    }

                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        @SuppressWarnings("unchecked")
        private void runTest(DataType collectionType,QueryVal queryVal,
                QueryVal.QueryValModification modification,DataType inputType,QueryCastType castType,CheckedType checkedType,
                int setSize,float loadFactor,double position,MonitoredObjectGen monitoredObjectGen){
            final var monitor=new OpenAddressHashSetMonitor(collectionType,checkedType,0,loadFactor);
            boolean expectedResult;
            if(position < 0){
                expectedResult=false;
                switch(collectionType){
                case FLOAT:
                    queryVal.initDoesNotContain((OmniSet.OfFloat)monitor.set,setSize,0,modification);
                    break;
                case DOUBLE:
                    queryVal.initDoesNotContain((OmniSet.OfDouble)monitor.set,setSize,0,modification);
                    break;
                case REF:
                    queryVal.initDoesNotContain((OmniSet.OfRef<Object>)monitor.set,setSize,0,modification);
                    break;
                default:
                    throw collectionType.invalid();
                }
            }else{
                expectedResult=true;
                switch(collectionType){
                case FLOAT:
                    queryVal.initContains((OmniSet.OfFloat)monitor.set,setSize,0,position,modification);
                    break;
                case DOUBLE:
                    queryVal.initContains((OmniSet.OfDouble)monitor.set,setSize,0,position,modification);
                    break;
                case REF:
                    queryVal.initContains((MonitoredSet<? extends OmniSet.OfRef<Object>>)monitor,setSize,0,position,modification,inputType,monitoredObjectGen);
                    break;
                default:
                    throw collectionType.invalid();
                }
            }
            monitor.updateCollectionState();
            final boolean actualResult=callMethod(monitor,queryVal,inputType,castType,modification,monitoredObjectGen);
            Assertions.assertEquals(expectedResult,actualResult);
        }
    }
}
