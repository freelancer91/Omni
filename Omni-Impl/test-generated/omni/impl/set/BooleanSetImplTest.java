package omni.impl.set;
import omni.util.TestExecutorService;
import omni.impl.CheckedType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import omni.impl.set.AbstractBooleanSetMonitor.MonitoredFunctionGen;
import omni.impl.set.AbstractBooleanSetMonitor.MonitoredRemoveIfPredicateGen;
import java.util.NoSuchElementException;
import java.nio.file.Files;
import java.io.File;
import omni.api.OmniCollection;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import omni.impl.FunctionCallType;
import omni.impl.BooleanInputTestArgType;
import omni.impl.BooleanOutputTestArgType;
import java.util.function.Predicate;
import omni.function.BooleanPredicate;
import java.util.function.Consumer;
import omni.function.BooleanConsumer;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("SetTest")
public class BooleanSetImplTest{
  @org.junit.jupiter.api.AfterEach
  public void verifyAllExecuted(){
    int numTestsRemaining;
    if((numTestsRemaining=TestExecutorService.getNumRemainingTasks())!=0)
    {
      System.err.println("Warning: there were "+numTestsRemaining+" tests that were not completed");
    }
    TestExecutorService.reset();
  }
  private static class SetMonitor extends AbstractBooleanSetMonitor<BooleanSetImpl>{
    int expectedState;
    SetMonitor(CheckedType checkedType){
      super(checkedType);
      if(checkedType.checked){
        this.set=new BooleanSetImpl.Checked();
      }else{
        this.set=new BooleanSetImpl();
      }
    }
    SetMonitor(CheckedType checkedType,int state){
      super(checkedType);
      this.expectedState=state;
      if(checkedType.checked){
        this.set=new BooleanSetImpl.Checked(state);
      }else{
        this.set=new BooleanSetImpl(state);
      }
    }
    void verifyAdd(BooleanInputTestArgType inputArgType,int val){
      boolean expectedResult;
      int expectedState;
      switch(expectedState=this.expectedState){
        case 0b00:
          expectedResult=true;
          if((val&1)==0){
            expectedState=0b01;
          }else{
            expectedState=0b10;
          }
          break;
        case 0b01:
          if((val&1)==1){
            expectedResult=true;
            expectedState=0b11;
          }else{
            expectedResult=false;
            expectedState=0b01;
          }
          break;
        case 0b10:
          if((val&1)==0){
            expectedResult=true;
            expectedState=0b11;
          }else{
            expectedResult=false;
            expectedState=0b10;
          }
          break;
        default:
          expectedResult=false;
          expectedState=0b11;
      }
      Assertions.assertEquals(expectedResult,inputArgType.callCollectionAdd(set,val));
      this.expectedState=expectedState;
      verifyStructuralIntegrity();
    }
    void verifyRemoveIf(MonitoredRemoveIfPredicate predicate,FunctionCallType functionCallType,int numExpectedCalls){
      int state=this.expectedState;
      boolean result;
      if(functionCallType==FunctionCallType.Boxed){
        result=set.removeIf((Predicate)predicate);
      }else{
        result=set.removeIf((BooleanPredicate)predicate);
      }
      Assertions.assertEquals(numExpectedCalls,predicate.callCounter);
      if(result){
        switch(state){
          case 0b00:
            Assertions.assertTrue(predicate.removedVals.isEmpty());
            Assertions.assertTrue(predicate.retainedVals.isEmpty());
            break;
          case 0b01:
            if(predicate.removedVals.contains(false)){
              expectedState=0b00;
              Assertions.assertFalse(predicate.retainedVals.contains(false));
            }else{
              Assertions.assertTrue(predicate.retainedVals.contains(false));
            }
            Assertions.assertFalse(predicate.removedVals.contains(true));
            Assertions.assertFalse(predicate.retainedVals.contains(true));
            break;
          case 0b10:
            if(predicate.removedVals.contains(true)){
              expectedState=0b00;
              Assertions.assertFalse(predicate.retainedVals.contains(true));
            }else{
              Assertions.assertTrue(predicate.retainedVals.contains(true));
            }
            Assertions.assertFalse(predicate.removedVals.contains(false));
            Assertions.assertFalse(predicate.retainedVals.contains(false));
            break;
          default:
            if(predicate.removedVals.contains(true)){
              if(predicate.removedVals.contains(false)){
                expectedState=0b00;
                Assertions.assertFalse(predicate.retainedVals.contains(true));
                Assertions.assertFalse(predicate.retainedVals.contains(false));
              }else{
                expectedState=0b01;
                Assertions.assertFalse(predicate.retainedVals.contains(true));
                Assertions.assertTrue(predicate.retainedVals.contains(false));
              }
            }else{
              expectedState=0b10;
              Assertions.assertTrue(predicate.removedVals.contains(false));
              Assertions.assertTrue(predicate.retainedVals.contains(true));
              Assertions.assertFalse(predicate.retainedVals.contains(false));
            }
        }
      }else{
        Assertions.assertTrue(predicate.removedVals.isEmpty());
        switch(state){
          case 0b00:
            Assertions.assertFalse(predicate.retainedVals.contains(false));
            Assertions.assertFalse(predicate.retainedVals.contains(true));
            break;
          case 0b01:
            Assertions.assertTrue(predicate.retainedVals.contains(false));
            Assertions.assertFalse(predicate.retainedVals.contains(true));
            break;
          case 0b10:
            Assertions.assertFalse(predicate.retainedVals.contains(false));
            Assertions.assertTrue(predicate.retainedVals.contains(true));
            break;
          default:
            Assertions.assertTrue(predicate.retainedVals.contains(false));
            Assertions.assertTrue(predicate.retainedVals.contains(true));
        }
      }
    }
    void clear(){
      set.clear();
      expectedState=0b00;
    }
    void writeObject(ObjectOutputStream oos) throws IOException{
      if(checkedType.checked){
        FieldAndMethodAccessor.BooleanSetImpl.Checked.writeObject(set,oos);
      }else{
        FieldAndMethodAccessor.BooleanSetImpl.writeObject(set,oos);
      }
    }
    AbstractItrMonitor getItrMonitor(){
      if(checkedType.checked){
        return new CheckedItrMonitor();
      }else{
        return new UncheckedItrMonitor();
      }
    }
    void verifyStructuralIntegrity(){
      Assertions.assertEquals(expectedState,set.state);
    }
    void illegalMod(PreModScenario preModScenario){
      if(preModScenario==PreModScenario.ModSet){
        switch(expectedState){
          default:
            //clear all
            set.state=0b00;
            expectedState=0b00;
            break;
          case 0b00:
          case 0b10:
            set.state|=0b01;
            expectedState|=0b01;
            break;
          case 0b01:
            set.state=0b11;
            expectedState=0b11;
        }
      }
    }
    class CheckedItrMonitor extends UncheckedItrMonitor{
      CheckedItrMonitor(){
        super();
      }
      void verifyIteratorState(){
        Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.itrState(itr));
        Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.root(itr));
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else{
          itr.forEachRemaining((BooleanConsumer)action);
        }
        switch(expectedItrState){
          case 0b0001:
            expectedItrState=0b0101;
            break;
          case 0b0010:
            expectedItrState=0b1010;
            break;
          case 0b0011:
            expectedItrState=0b1111;
            break;
          case 0b0110:
            expectedItrState=0b1110;
            break;
          case 0b0111:
            expectedItrState=0b1111;
          default:
        }
      }
      void moveExpectedIteratorStateForward(){
        switch(expectedItrState){
          case 0b0001:
            this.expectedItrState=0b0101;
            break;
          case 0b0010:
            this.expectedItrState=0b1010;
            break;
          case 0b0011:
            this.expectedItrState=0b0111;
            break;
          case 0b0110:
            this.expectedItrState=0b1110;
            break;
          default:
            this.expectedItrState=0b1111;
        }
      }
      void remove(){
        itr.remove();
        switch(expectedItrState){
        case 0b0101:
          expectedState=0b00;
          expectedItrState=0b0100;
          return;
        case 0b0111:
          expectedState=0b10;
          expectedItrState=0b0110;
          return;
        case 0b1010:
          expectedState=0b00;
          expectedItrState=0b1000;
          return;
        case 0b1110:
          expectedState=0b00;
          expectedItrState=0b1100;
          return;
        default:
          expectedState=0b01;
          expectedItrState=0b1101;
        }
      }
    }
    class UncheckedItrMonitor extends AbstractItrMonitor{
      int expectedItrState;
      UncheckedItrMonitor(){
        super(set.iterator());
        expectedItrState=set.state;
      }
      AbstractBooleanSetMonitor getSetMonitor(){
        return SetMonitor.this;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Itr.itrState(itr));
        Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Itr.root(itr));
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else{
          itr.forEachRemaining((BooleanConsumer)action);
        }
        expectedItrState=0b00;
      }
      void moveExpectedIteratorStateForward(){
        switch(expectedItrState){
          default:
            this.expectedItrState=0b10;
            break;
          case 0b01:
          case 0b10:
            this.expectedItrState=0b00;
        }
      }
      void remove(){
        itr.remove();
        if(expectedItrState == 0b00){
          if(expectedState == 0b11){
            expectedState=0b01;
          }else{
            expectedState=0b00;
          }
        }else{
          expectedState=0b10;
        }
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    for(var checkedType:CheckedType.values()){
      TestExecutorService.submitTest(()->new SetMonitor(checkedType).verifyStructuralIntegrity());
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_int(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        TestExecutorService.submitTest(()->new SetMonitor(checkedType,state).verifyStructuralIntegrity());
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testReadAndWrite(){
    for(var checkedType:CheckedType.values()){
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if(!monitoredFunctionGen.appliesToItr && (checkedType.checked || monitoredFunctionGen.expectedException==null)){
          for(var tmpState=0b00;tmpState<=0b11;++tmpState){
            final var state=tmpState;
            TestExecutorService.submitTest(()->{
              var setMonitor=new SetMonitor(checkedType,state);
              final File file;
              try{
                file=Files.createTempFile(null,null).toFile();
              }catch(Exception e){
                Assertions.fail(e);
                return;
              }
              if(monitoredFunctionGen.expectedException==null){
                try(var oos=new ObjectOutputStream(new FileOutputStream(file));){
                  oos.writeObject(setMonitor.set);
                }catch(Exception e){
                  Assertions.fail(e);
                }
                OmniCollection.OfBoolean readCol=null;
                try(var ois=new ObjectInputStream(new FileInputStream(file));){
                  readCol=(OmniCollection.OfBoolean)ois.readObject();
                }catch(Exception e){
                  Assertions.fail(e);
                  return;
                }
                if(checkedType.checked){
                  Assertions.assertTrue(readCol instanceof BooleanSetImpl.Checked);
                }else{
                  Assertions.assertTrue(readCol instanceof BooleanSetImpl);
                  Assertions.assertFalse(readCol instanceof BooleanSetImpl.Checked);
                }
                var itr=readCol.iterator();
                switch(state){
                  case 0b01:
                    Assertions.assertFalse(itr.nextBoolean());
                    break;
                  case 0b10:
                    Assertions.assertTrue(itr.nextBoolean());
                    break;
                  default:
                    Assertions.assertFalse(itr.nextBoolean());
                    Assertions.assertTrue(itr.nextBoolean());
                  case 0b00:
                }
                Assertions.assertFalse(itr.hasNext());
              }else{
                Assertions.assertThrows(monitoredFunctionGen.expectedException,()->{
                  try(var moos=monitoredFunctionGen.getMonitoredObjectOutputStream(file,setMonitor);){
                    setMonitor.writeObject(moos);
                  }
                });
              }
              setMonitor.verifyStructuralIntegrity();
            });
          }
        } 
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testclear_void(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        TestExecutorService.submitTest(()->{
          var setMonitor=new SetMonitor(checkedType,state);
          setMonitor.clear();
          setMonitor.verifyStructuralIntegrity();
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        TestExecutorService.submitTest(()->{
          var setMonitor=new SetMonitor(checkedType,state);
          var clone=setMonitor.set.clone();
          setMonitor.verifyStructuralIntegrity();
          if(checkedType.checked){
            Assertions.assertTrue(clone instanceof BooleanSetImpl.Checked);
          }else{
            Assertions.assertFalse(clone instanceof BooleanSetImpl.Checked);
            Assertions.assertTrue(clone instanceof BooleanSetImpl);
          }
          Assertions.assertEquals(state,((BooleanSetImpl)clone).state);
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoString_void(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        TestExecutorService.submitTest(()->{
          var setMonitor=new SetMonitor(checkedType,state);
          var result=setMonitor.set.toString();
          setMonitor.verifyStructuralIntegrity();
          String expectedString;
          switch(state){
            case 0b00:
              expectedString="[]";
              break;
            case 0b01:
              expectedString="[false]";
              break;
            case 0b10:
              expectedString="[true]";
              break;
            default:
              expectedString="[false, true]";
              break;
          }
          Assertions.assertEquals(expectedString,result);
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testhashCode_void(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        TestExecutorService.submitTest(()->{
          var setMonitor=new SetMonitor(checkedType,state);
          var result=setMonitor.set.hashCode();
          setMonitor.verifyStructuralIntegrity();
          int expectedHash;
          switch(state){
            case 0b00:
              expectedHash=0;
              break;
            case 0b01:
              expectedHash=Boolean.hashCode(false);
              break;
            case 0b10:
              expectedHash=Boolean.hashCode(true);
              break;
            default:
              expectedHash=Boolean.hashCode(false)+Boolean.hashCode(true);
              break;
          }
          Assertions.assertEquals(expectedHash,result);
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testisEmpty_void(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        TestExecutorService.submitTest(()->{
          var setMonitor=new SetMonitor(checkedType,state);
          var result=setMonitor.set.isEmpty();
          setMonitor.verifyStructuralIntegrity();
          Assertions.assertEquals(result,state==0b00);
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testsize_void(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        TestExecutorService.submitTest(()->{
          var setMonitor=new SetMonitor(checkedType,state);
          var result=setMonitor.set.size();
          setMonitor.verifyStructuralIntegrity();
          Assertions.assertEquals(Integer.bitCount(state),result);
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testToArray_IntFunction(){
    for(var checkedType:CheckedType.values()){
      for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
        if(!monitoredFunctionGen.appliesToItr && (checkedType.checked || monitoredFunctionGen.expectedException==null)){
          for(var tmpState=0b00;tmpState<=0b11;++tmpState){
            final var state=tmpState;
            TestExecutorService.submitTest(()->{
              var setMonitor=new SetMonitor(checkedType,state);
              var monitoredArrConstructor=monitoredFunctionGen.getMonitoredArrayConstructor(setMonitor);
              if(monitoredFunctionGen.expectedException==null){
                var result=setMonitor.set.toArray(monitoredArrConstructor);
                switch(state){
                  case 0b00:
                    Assertions.assertEquals(0,result.length);
                    break;
                  case 0b01:
                    Assertions.assertEquals(1,result.length);
                    Assertions.assertEquals(Boolean.FALSE,result[0]);
                    break;
                  case 0b10:
                    Assertions.assertEquals(1,result.length);
                    Assertions.assertEquals(Boolean.TRUE,result[0]);
                    break;
                  default:
                    Assertions.assertEquals(2,result.length);
                    Assertions.assertEquals(Boolean.FALSE,result[0]);
                    Assertions.assertEquals(Boolean.TRUE,result[1]);
                }
              }else{
                Assertions.assertThrows(monitoredFunctionGen.expectedException,()->setMonitor.set.toArray(monitoredArrConstructor));
              }
              Assertions.assertEquals(1,monitoredArrConstructor.numCalls);
              setMonitor.verifyStructuralIntegrity();
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testToArray_ObjectArray(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        for(var tmpArrSize=0;tmpArrSize<=4;++tmpArrSize){
          final var arrSize=tmpArrSize;
          TestExecutorService.submitTest(()->{
            var setMonitor=new SetMonitor(checkedType,state);
            var paramArr=new Object[arrSize];
            for(int i=0;i<arrSize;++i){
              paramArr[i]=Integer.valueOf(i);
            }
            var resultArr=setMonitor.set.toArray(paramArr);
            setMonitor.verifyStructuralIntegrity();
            int resultSize;
            switch(state){
              case 0b00:
                resultSize=0;
                break;
              case 0b01:
                resultSize=1;
                Assertions.assertEquals(Boolean.FALSE,resultArr[0]);
                break;
              case 0b10:
                resultSize=1;
                Assertions.assertEquals(Boolean.TRUE,resultArr[0]);
                break;
              default:
                resultSize=2;
                Assertions.assertEquals(Boolean.FALSE,resultArr[0]);
                Assertions.assertEquals(Boolean.TRUE,resultArr[1]);
            }
            if(arrSize>=resultSize){
              Assertions.assertSame(paramArr,resultArr);
              if(arrSize>resultSize){
                Assertions.assertNull(resultArr[resultSize]);
                for(int i=resultSize+1;i<arrSize;++i){
                  Assertions.assertEquals(Integer.valueOf(i),resultArr[i]);
                  Assertions.assertSame(paramArr[i],resultArr[i]);
                }
              }
            }else{
              Assertions.assertNotSame(paramArr,resultArr);
              Assertions.assertEquals(resultArr.length,resultSize);
            }
          });
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        for(var tmpAddedVal=0;tmpAddedVal<=1;++tmpAddedVal){
          final var addedVal=tmpAddedVal;
          for(var inputArgType:BooleanInputTestArgType.values()){
            TestExecutorService.submitTest(()->new SetMonitor(checkedType,state).verifyAdd(inputArgType,addedVal));
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testforEach_Consumer(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
          if(!monitoredFunctionGen.appliesToItr && (checkedType.checked || monitoredFunctionGen.expectedException==null || tmpState==0b00)){
            final int state=tmpState;
            for(var functionCallType:FunctionCallType.values()){
              TestExecutorService.submitTest(()->{
                var setMonitor=new SetMonitor(checkedType,state);
                var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(setMonitor);
                if(monitoredFunctionGen.expectedException==null || state==0b00){
                  setMonitor.forEach(monitoredConsumer,functionCallType);
                  setMonitor.verifyStructuralIntegrity();
                  switch(state){
                    case 0b00:
                      Assertions.assertTrue(monitoredConsumer.encounteredValues.isEmpty());
                      break;
                    case 0b01:
                      Assertions.assertEquals(1,monitoredConsumer.encounteredValues.size());
                      Assertions.assertEquals(Boolean.FALSE,monitoredConsumer.encounteredValues.get(0));
                      break;
                    case 0b10:
                      Assertions.assertEquals(1,monitoredConsumer.encounteredValues.size());
                      Assertions.assertEquals(Boolean.TRUE,monitoredConsumer.encounteredValues.get(0));
                      break;
                    default:
                      Assertions.assertEquals(2,monitoredConsumer.encounteredValues.size());
                      Assertions.assertEquals(Boolean.FALSE,monitoredConsumer.encounteredValues.get(0));
                      Assertions.assertEquals(Boolean.TRUE,monitoredConsumer.encounteredValues.get(1));
                      break;
                  }
                }else{
                  Assertions.assertThrows(monitoredFunctionGen.expectedException,()->setMonitor.forEach(monitoredConsumer,functionCallType));
                  setMonitor.verifyStructuralIntegrity();
                }
              });
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testremoveIf_Predicate(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        for(var monitoredRemoveIfPredicateGen:MonitoredRemoveIfPredicateGen.values()){
          if(checkedType.checked || monitoredRemoveIfPredicateGen.expectedException==null || tmpState==0b00){
            final int state=tmpState;
            for(var functionCallType:FunctionCallType.values()){
              long randSeedBound;
              if(state==0 || !monitoredRemoveIfPredicateGen.isRandomized || functionCallType==FunctionCallType.Boxed){
                randSeedBound=0;
              }else{
                randSeedBound=100;
              }
              for(long tmpRandSeed=0;tmpRandSeed<=randSeedBound;++tmpRandSeed){
                final long randSeed=tmpRandSeed;
                TestExecutorService.submitTest(()->{
                  var setMonitor=new SetMonitor(checkedType,state);
                  final int numExpectedCalls;
                  switch(state){
                    case 0b00:
                      numExpectedCalls=0;
                      break;
                    case 0b01:
                    case 0b10:
                      numExpectedCalls=1;
                      break;
                    default:
                      numExpectedCalls=2;
                  }
                  var predicate=monitoredRemoveIfPredicateGen.getMonitoredRemoveIfPredicate(setMonitor,randSeed,numExpectedCalls,0.5);
                  if(monitoredRemoveIfPredicateGen.expectedException==null || numExpectedCalls==0){
                    setMonitor.verifyRemoveIf(predicate,functionCallType,numExpectedCalls);
                  }else{
                    Assertions.assertThrows(monitoredRemoveIfPredicateGen.expectedException,()->setMonitor.verifyRemoveIf(predicate,functionCallType,numExpectedCalls));
                    Assertions.assertTrue(predicate.callCounter>0);
                  }
                  setMonitor.verifyStructuralIntegrity();
                }); 
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testtoArray_void(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        for(var outputArgType:BooleanOutputTestArgType.values()){
          TestExecutorService.submitTest(()->{
            var setMonitor=new SetMonitor(checkedType,state);
            outputArgType.verifyToArray(setMonitor.set,Integer.bitCount(state));
            setMonitor.verifyStructuralIntegrity();
          });
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testiterator_void(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        TestExecutorService.submitTest(()->{
          var setMonitor=new SetMonitor(checkedType,state);
          var itrMonitor=setMonitor.getItrMonitor();
          setMonitor.verifyStructuralIntegrity();
          itrMonitor.verifyIteratorState();
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testItrremove_void(){
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(checkedType.checked || preModScenario.expectedException==null){
          for(var itrRemoveScenario:ItrRemoveScenario.values()){
            if(checkedType.checked || itrRemoveScenario.expectedException==null){
              for(var tmpState=0b00;tmpState<=0b11;++tmpState){
                int setSize=Integer.bitCount(tmpState);
                if(setSize!=0 || itrRemoveScenario.validForEmptySet){
                  final var state=tmpState;
                  int tmpItrCount;
                  if(itrRemoveScenario==ItrRemoveScenario.PostInit){
                    setSize=0;
                    tmpItrCount=0;
                  }else{
                    tmpItrCount=1;
                  }
                  for(;tmpItrCount<=setSize;++tmpItrCount){
                    final var itrCount=tmpItrCount;
                    TestExecutorService.submitTest(()->{
                      var setMonitor=new SetMonitor(checkedType,state);
                      var itrMonitor=setMonitor.getItrMonitor();
                      for(int i=0;i<itrCount;++i){
                        itrMonitor.iterateForward();
                      }
                      if(itrRemoveScenario==ItrRemoveScenario.PostRemove){
                        itrMonitor.remove();
                      }
                      setMonitor.illegalMod(preModScenario);
                      Class<? extends Throwable> expectedException=(itrRemoveScenario.expectedException==null)?preModScenario.expectedException:itrRemoveScenario.expectedException;
                      if(expectedException==null){
                        for(;;){
                          itrMonitor.remove();
                          if(!itrMonitor.itr.hasNext()){
                            break;
                          }
                          itrMonitor.verifyIteratorState();
                          setMonitor.verifyStructuralIntegrity();
                          itrMonitor.iterateForward();
                        }
                      }else{
                        Assertions.assertThrows(expectedException,()->itrMonitor.remove());
                      }
                      itrMonitor.verifyIteratorState();
                      setMonitor.verifyStructuralIntegrity();
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
  public void testItrforEachRemaining_Consumer(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        for(var monitoredFunctionGen:MonitoredFunctionGen.values()){
          if(checkedType.checked || monitoredFunctionGen.expectedException==null || tmpState==0b00){
            for(var preModScenario:PreModScenario.values()){
              if(checkedType.checked || preModScenario.expectedException==null || tmpState==0b00){
                final int state=tmpState;
                int itrScenarioMax=0;
                if(state==0b11){
                  itrScenarioMax=2;
                }
                for(var tmpItrScenario=0;tmpItrScenario<=itrScenarioMax;++tmpItrScenario){
                  var itrScenario=tmpItrScenario;
                  for(var functionCallType:FunctionCallType.values()){
                    TestExecutorService.submitTest(()->{
                      var setMonitor=new SetMonitor(checkedType,state);
                      var itrMonitor=setMonitor.getItrMonitor();
                      var monitoredConsumer=monitoredFunctionGen.getMonitoredConsumer(itrMonitor);
                      int adjustedState=state;
                      switch(itrScenario){
                      case 1:
                        itrMonitor.iterateForward();
                        adjustedState=0b10;
                        break;
                      case 2:
                        itrMonitor.iterateForward();
                        itrMonitor.remove();
                        adjustedState=0b10;
                      default:
                      }
                      setMonitor.illegalMod(preModScenario);
                      if(preModScenario.expectedException!=null){
                        if(monitoredFunctionGen==MonitoredFunctionGen.ModSet && adjustedState==0b11){
                          setMonitor.illegalMod(preModScenario);
                        }
                      }
                      if(preModScenario.expectedException==null || adjustedState==0b00){
                        if(monitoredFunctionGen.expectedException==null || adjustedState==0b00){
                          itrMonitor.forEachRemaining(monitoredConsumer,functionCallType);
                          switch(adjustedState){
                            case 0b00:
                              Assertions.assertTrue(monitoredConsumer.encounteredValues.isEmpty());
                              break;
                            case 0b01:
                              Assertions.assertEquals(1,monitoredConsumer.encounteredValues.size());
                              Assertions.assertEquals(Boolean.FALSE,monitoredConsumer.encounteredValues.get(0));
                              break;
                            case 0b10:
                              Assertions.assertEquals(1,monitoredConsumer.encounteredValues.size());
                              Assertions.assertEquals(Boolean.TRUE,monitoredConsumer.encounteredValues.get(0));
                              break;
                            default:
                              Assertions.assertEquals(2,monitoredConsumer.encounteredValues.size());
                              Assertions.assertEquals(Boolean.FALSE,monitoredConsumer.encounteredValues.get(0));
                              Assertions.assertEquals(Boolean.TRUE,monitoredConsumer.encounteredValues.get(1));
                              break;
                          }
                        }else{
                          Assertions.assertThrows(monitoredFunctionGen.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
                        }
                      }else{
                        Assertions.assertThrows(preModScenario.expectedException,()->itrMonitor.forEachRemaining(monitoredConsumer,functionCallType));
                      }
                      itrMonitor.verifyIteratorState();
                      setMonitor.verifyStructuralIntegrity();
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
  public void testItrnext_void(){
    for(var checkedType:CheckedType.values()){
      for(var preModScenario:PreModScenario.values()){
        if(checkedType.checked || preModScenario.expectedException==null){
          for(var tmpState=0b00;tmpState<=0b11;++tmpState){
            final var state=tmpState;
            for(var outputArgType:BooleanOutputTestArgType.values()){
              TestExecutorService.submitTest(()->{
                var setMonitor=new SetMonitor(checkedType,state);
                var itrMonitor=setMonitor.getItrMonitor();
                setMonitor.illegalMod(preModScenario);
                if(preModScenario.expectedException==null){
                  switch(state){
                    case 0b00:
                      break;
                    case 0b01:
                      outputArgType.verifyItrNext(itrMonitor.itr,0);
                      itrMonitor.moveExpectedIteratorStateForward();
                      itrMonitor.verifyIteratorState();
                      setMonitor.verifyStructuralIntegrity();
                      break;
                    case 0b10:
                      outputArgType.verifyItrNext(itrMonitor.itr,1);
                      itrMonitor.moveExpectedIteratorStateForward();
                      itrMonitor.verifyIteratorState();
                      setMonitor.verifyStructuralIntegrity();
                      break;
                    default:
                      outputArgType.verifyItrNext(itrMonitor.itr,0);
                      itrMonitor.moveExpectedIteratorStateForward();
                      itrMonitor.verifyIteratorState();
                      setMonitor.verifyStructuralIntegrity();
                      outputArgType.verifyItrNext(itrMonitor.itr,1);
                      itrMonitor.moveExpectedIteratorStateForward();
                      itrMonitor.verifyIteratorState();
                      setMonitor.verifyStructuralIntegrity();
                  }
                  if(checkedType.checked){
                    Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyItrNext(itrMonitor.itr,0));
                    itrMonitor.verifyIteratorState();
                    setMonitor.verifyStructuralIntegrity();
                  }
                }else{
                  Assertions.assertThrows(preModScenario.expectedException,()->outputArgType.verifyItrNext(itrMonitor.itr,0));
                  itrMonitor.verifyIteratorState();
                  setMonitor.verifyStructuralIntegrity();
                }
              });
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testItrhasNext_void(){
    for(var checkedType:CheckedType.values()){
      for(var tmpState=0b00;tmpState<=0b11;++tmpState){
        final var state=tmpState;
        TestExecutorService.submitTest(()->{
          var setMonitor=new SetMonitor(checkedType,state);
          var itrMonitor=setMonitor.getItrMonitor();
          var setSize=Integer.bitCount(state);
          for(int i=0;i<setSize;++i){
            Assertions.assertTrue(itrMonitor.itr.hasNext());
            itrMonitor.verifyIteratorState();
            setMonitor.verifyStructuralIntegrity();
            itrMonitor.iterateForward();
          }
          Assertions.assertFalse(itrMonitor.itr.hasNext());
          itrMonitor.verifyIteratorState();
          setMonitor.verifyStructuralIntegrity();
        });
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testcontains_val(){
    for(var checkedType:CheckedType.values()){
      for(var queryVal:QueryVal.values()){
        for(var queryInputType:QueryInputType.values()){
          for(var queryCastType:QueryCastType.values()){
            if(isValidQuery(queryVal,queryInputType,queryCastType)){
              for(var tmpState=0b00;tmpState<=0b11;++tmpState){
                final var state=tmpState;
                TestExecutorService.submitTest(()->{
                  var setMonitor=new SetMonitor(checkedType,state);
                  boolean expectedResult;
                  switch(queryVal){
                    case TRUE:
                      expectedResult=(state&0b10)!=0;
                      break;
                    case NEG0:
                    case FALSE:
                      expectedResult=(state&0b01)!=0;
                      break;
                    default:
                      expectedResult=false;
                  }
                  Assertions.assertEquals(expectedResult,callcontains(setMonitor.set,queryVal,queryInputType,queryCastType));
                  setMonitor.verifyStructuralIntegrity();
                });
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    for(var checkedType:CheckedType.values()){
      for(var queryVal:QueryVal.values()){
        for(var queryInputType:QueryInputType.values()){
          for(var queryCastType:QueryCastType.values()){
            if(isValidQuery(queryVal,queryInputType,queryCastType)){
              for(var tmpState=0b00;tmpState<=0b11;++tmpState){
                final var state=tmpState;
                TestExecutorService.submitTest(()->{
                  var setMonitor=new SetMonitor(checkedType,state);
                  int newState;
                  switch(queryVal){
                    case TRUE:
                      switch(state){
                        case 0b00:
                        case 0b01:
                          newState=state;
                          break;
                        case 0b10:
                          newState=0b00;
                          break;
                        default:
                          newState=0b01;
                      }
                      break;
                    case NEG0:
                    case FALSE:
                      switch(state){
                        case 0b00:
                        case 0b10:
                          newState=state;
                          break;
                        case 0b01:
                          newState=0b00;
                          break;
                        default:
                          newState=0b10;
                      }
                      break;
                    default:
                      newState=state;
                  }
                  boolean result=callremoveVal(setMonitor.set,queryVal,queryInputType,queryCastType);
                  setMonitor.expectedState=newState;
                  Assertions.assertEquals(newState!=state,result);
                  setMonitor.verifyStructuralIntegrity();
                });
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests();
  }
  static enum QueryVal{
    TRUE,
    FALSE,
    NEG0,
    OUTOFRANGE,
    NULL;
  }
  static enum QueryInputType{
    BOOLEAN,
    BYTE,
    CHAR,
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    REF;
  }
  static enum QueryCastType{
    ToObject,
    ToBoxed,
    ToPrimitive;
  }
  static boolean isValidQuery(QueryVal queryVal,QueryInputType queryInputType,QueryCastType queryCastType){
    switch(queryCastType){
      case ToObject:
        switch(queryVal){
          case TRUE:
          case FALSE:
            return queryInputType!=QueryInputType.REF;
          case NEG0:
            return queryInputType==QueryInputType.DOUBLE || queryInputType==QueryInputType.FLOAT;
          case NULL:
            return queryInputType==QueryInputType.REF;
          case OUTOFRANGE:
            return queryInputType!=QueryInputType.BOOLEAN;
        }
        break;
      case ToBoxed:
        switch(queryVal){
          case OUTOFRANGE:
            return queryInputType!=QueryInputType.BOOLEAN && queryInputType!=QueryInputType.REF;
          case TRUE:
          case FALSE:
          case NULL:
            return queryInputType!=QueryInputType.REF;
          case NEG0:
            return queryInputType==QueryInputType.DOUBLE || queryInputType==QueryInputType.FLOAT;
        }
        break;
      case ToPrimitive:
        switch(queryVal){
          case TRUE:
          case FALSE:
            return queryInputType!=QueryInputType.REF;
          case NEG0:
            return queryInputType==QueryInputType.DOUBLE || queryInputType==QueryInputType.FLOAT;
          case OUTOFRANGE:
            return queryInputType!=QueryInputType.BOOLEAN && queryInputType!=QueryInputType.REF;
          case NULL:
            return false;
        }
    }
    throw new UnsupportedOperationException("queryVal="+queryVal+"; queryInputType="+queryInputType+"; queryCastType="+queryCastType);
  }
  static boolean callremoveVal(OmniCollection.OfBoolean set,QueryVal queryVal,QueryInputType queryInputType,QueryCastType queryCastType){
    switch(queryCastType){
      case ToObject:
        switch(queryVal){
          case NEG0:
            switch(queryInputType){
              case DOUBLE:
                return set.remove((Object)Double.valueOf(-0.0d));
              case FLOAT:
                return set.remove((Object)Float.valueOf(-0.0f));
              case BOOLEAN:
              case BYTE:
              case CHAR:
              case SHORT:
              case INT:
              case LONG:
              case REF:
            }
            break;
          case TRUE:
            switch(queryInputType){
              case BOOLEAN:
                return set.remove((Object)Boolean.TRUE);
              case BYTE:
                return set.remove((Object)Byte.valueOf((byte)1));
              case CHAR:
                return set.remove((Object)Character.valueOf((char)1));
              case SHORT:
                return set.remove((Object)Short.valueOf((short)1));
              case INT:
                return set.remove((Object)Integer.valueOf(1));
              case LONG:
                return set.remove((Object)Long.valueOf(1));
              case FLOAT:
                return set.remove((Object)Float.valueOf(1));
              case DOUBLE:
                return set.remove((Object)Double.valueOf(1));
              case REF:
            }
            break;
          case FALSE:
            switch(queryInputType){
              case BOOLEAN:
                return set.remove((Object)Boolean.FALSE);
              case BYTE:
                return set.remove((Object)Byte.valueOf((byte)0));
              case CHAR:
                return set.remove((Object)Character.valueOf((char)0));
              case SHORT:
                return set.remove((Object)Short.valueOf((short)0));
              case INT:
                return set.remove((Object)Integer.valueOf(0));
              case LONG:
                return set.remove((Object)Long.valueOf(0));
              case FLOAT:
                return set.remove((Object)Float.valueOf(0));
              case DOUBLE:
                return set.remove((Object)Double.valueOf(0));
              case REF:
            }
            break;
          case NULL:
            return set.remove((Object)null);
          case OUTOFRANGE:
            switch(queryInputType){
              case BYTE:
                return set.remove((Object)Byte.valueOf((byte)2));
              case CHAR:
                return set.remove((Object)Character.valueOf((char)2));
              case SHORT:
                return set.remove((Object)Short.valueOf((short)2));
              case INT:
                return set.remove((Object)Integer.valueOf(2));
              case LONG:
                return set.remove((Object)Long.valueOf(2));
              case FLOAT:
                return set.remove((Object)Float.valueOf(2));
              case DOUBLE:
                return set.remove((Object)Double.valueOf(2));
              case REF:
                return set.remove(new Object());
              case BOOLEAN:
            }
            break;
        }
        break;
      case ToBoxed:
        switch(queryVal){
          case NEG0:
            switch(queryInputType){
              case DOUBLE:
                return set.removeVal(Double.valueOf(-0.0d));
              case FLOAT:
                return set.removeVal(Float.valueOf(-0.0f));
              case BOOLEAN:
              case BYTE:
              case CHAR:
              case SHORT:
              case INT:
              case LONG:
              case REF:
            }
            break;
          case OUTOFRANGE:
            switch(queryInputType){
              case BYTE:
                return set.removeVal(Byte.valueOf((byte)2));
              case CHAR:
                return set.removeVal(Character.valueOf((char)2));
              case SHORT:
                return set.removeVal(Short.valueOf((short)2));
              case INT:
                return set.removeVal(Integer.valueOf(2));
              case LONG:
                return set.removeVal(Long.valueOf(2));
              case FLOAT:
                return set.removeVal(Float.valueOf(2));
              case DOUBLE:
                return set.removeVal(Double.valueOf(2));
              case REF:
              case BOOLEAN:
            }
            break;
          case TRUE:
            switch(queryInputType){
              case BOOLEAN:
                return set.removeVal(Boolean.TRUE);
              case BYTE:
                return set.removeVal(Byte.valueOf((byte)1));
              case CHAR:
                return set.removeVal(Character.valueOf((char)1));
              case SHORT:
                return set.removeVal(Short.valueOf((short)1));
              case INT:
                return set.removeVal(Integer.valueOf(1));
              case LONG:
                return set.removeVal(Long.valueOf(1));
              case FLOAT:
                return set.removeVal(Float.valueOf(1));
              case DOUBLE:
                return set.removeVal(Double.valueOf(1));
              case REF:
            }
            break;
          case FALSE:
            switch(queryInputType){
              case BOOLEAN:
                return set.removeVal(Boolean.FALSE);
              case BYTE:
                return set.removeVal(Byte.valueOf((byte)0));
              case CHAR:
                return set.removeVal(Character.valueOf((char)0));
              case SHORT:
                return set.removeVal(Short.valueOf((short)0));
              case INT:
                return set.removeVal(Integer.valueOf(0));
              case LONG:
                return set.removeVal(Long.valueOf(0));
              case FLOAT:
                return set.removeVal(Float.valueOf(0));
              case DOUBLE:
                return set.removeVal(Double.valueOf(0));
              case REF:
            }
            break;
          case NULL:
            switch(queryInputType){
              case BOOLEAN:
                return set.removeVal((Boolean)null);
              case BYTE:
                return set.removeVal((Byte)null);
              case CHAR:
                return set.removeVal((Character)null);
              case SHORT:
                return set.removeVal((Short)null);
              case INT:
                return set.removeVal((Integer)null);
              case LONG:
                return set.removeVal((Long)null);
              case FLOAT:
                return set.removeVal((Float)null);
              case DOUBLE:
                return set.removeVal((Double)null);
              case REF:
            }
            break;
        }
        break;
      case ToPrimitive:
        switch(queryVal){
          case NEG0:
            switch(queryInputType){
              case DOUBLE:
                return set.removeVal(-0.0d);
              case FLOAT:
                return set.removeVal(-0.0f);
              case BOOLEAN:
              case BYTE:
              case CHAR:
              case SHORT:
              case INT:
              case LONG:
              case REF:
            }
            break;
          case TRUE:
             switch(queryInputType){
              case BOOLEAN:
                return set.removeVal(true);
              case BYTE:
                return set.removeVal((byte)1);
              case CHAR:
                return set.removeVal((char)1);
              case SHORT:
                return set.removeVal((short)1);
              case INT:
                return set.removeVal((int)1);
              case LONG:
                return set.removeVal((long)1);
              case FLOAT:
                return set.removeVal((float)1);
              case DOUBLE:
                return set.removeVal((double)1);
              case REF:
            }
            break;
          case FALSE:
            switch(queryInputType){
              case BOOLEAN:
                return set.removeVal(false);
              case BYTE:
                return set.removeVal((byte)0);
              case CHAR:
                return set.removeVal((char)0);
              case SHORT:
                return set.removeVal((short)0);
              case INT:
                return set.removeVal((int)0);
              case LONG:
                return set.removeVal((long)0);
              case FLOAT:
                return set.removeVal((float)0);
              case DOUBLE:
                return set.removeVal((double)0);
              case REF:
            }
            break;
          case OUTOFRANGE:
            switch(queryInputType){
              case BYTE:
                return set.removeVal((byte)2);
              case CHAR:
                return set.removeVal((char)2);
              case SHORT:
                return set.removeVal((short)2);
              case INT:
                return set.removeVal((int)2);
              case LONG:
                return set.removeVal((long)2);
              case FLOAT:
                return set.removeVal((float)2);
              case DOUBLE:
                return set.removeVal((double)2);
              case BOOLEAN:
              case REF:
            }
            break;
          case NULL:
        }
    }
    throw new UnsupportedOperationException("queryVal="+queryVal+"; queryInputType="+queryInputType+"; queryCastType="+queryCastType);
  }
  static boolean callcontains(OmniCollection.OfBoolean set,QueryVal queryVal,QueryInputType queryInputType,QueryCastType queryCastType){
    switch(queryCastType){
      case ToObject:
        switch(queryVal){
          case NEG0:
            switch(queryInputType){
              case DOUBLE:
                return set.contains((Object)Double.valueOf(-0.0d));
              case FLOAT:
                return set.contains((Object)Float.valueOf(-0.0f));
              case BOOLEAN:
              case BYTE:
              case CHAR:
              case SHORT:
              case INT:
              case LONG:
              case REF:
            }
            break;
          case TRUE:
            switch(queryInputType){
              case BOOLEAN:
                return set.contains((Object)Boolean.TRUE);
              case BYTE:
                return set.contains((Object)Byte.valueOf((byte)1));
              case CHAR:
                return set.contains((Object)Character.valueOf((char)1));
              case SHORT:
                return set.contains((Object)Short.valueOf((short)1));
              case INT:
                return set.contains((Object)Integer.valueOf(1));
              case LONG:
                return set.contains((Object)Long.valueOf(1));
              case FLOAT:
                return set.contains((Object)Float.valueOf(1));
              case DOUBLE:
                return set.contains((Object)Double.valueOf(1));
              case REF:
            }
            break;
          case FALSE:
            switch(queryInputType){
              case BOOLEAN:
                return set.contains((Object)Boolean.FALSE);
              case BYTE:
                return set.contains((Object)Byte.valueOf((byte)0));
              case CHAR:
                return set.contains((Object)Character.valueOf((char)0));
              case SHORT:
                return set.contains((Object)Short.valueOf((short)0));
              case INT:
                return set.contains((Object)Integer.valueOf(0));
              case LONG:
                return set.contains((Object)Long.valueOf(0));
              case FLOAT:
                return set.contains((Object)Float.valueOf(0));
              case DOUBLE:
                return set.contains((Object)Double.valueOf(0));
              case REF:
            }
            break;
          case NULL:
            return set.contains((Object)null);
          case OUTOFRANGE:
            switch(queryInputType){
              case BYTE:
                return set.contains((Object)Byte.valueOf((byte)2));
              case CHAR:
                return set.contains((Object)Character.valueOf((char)2));
              case SHORT:
                return set.contains((Object)Short.valueOf((short)2));
              case INT:
                return set.contains((Object)Integer.valueOf(2));
              case LONG:
                return set.contains((Object)Long.valueOf(2));
              case FLOAT:
                return set.contains((Object)Float.valueOf(2));
              case DOUBLE:
                return set.contains((Object)Double.valueOf(2));
              case REF:
                return set.contains(new Object());
              case BOOLEAN:
            }
            break;
        }
        break;
      case ToBoxed:
        switch(queryVal){
          case NEG0:
            switch(queryInputType){
              case DOUBLE:
                return set.contains(Double.valueOf(-0.0d));
              case FLOAT:
                return set.contains(Float.valueOf(-0.0f));
              case BOOLEAN:
              case BYTE:
              case CHAR:
              case SHORT:
              case INT:
              case LONG:
              case REF:
            }
            break;
          case OUTOFRANGE:
            switch(queryInputType){
              case BYTE:
                return set.contains(Byte.valueOf((byte)2));
              case CHAR:
                return set.contains(Character.valueOf((char)2));
              case SHORT:
                return set.contains(Short.valueOf((short)2));
              case INT:
                return set.contains(Integer.valueOf(2));
              case LONG:
                return set.contains(Long.valueOf(2));
              case FLOAT:
                return set.contains(Float.valueOf(2));
              case DOUBLE:
                return set.contains(Double.valueOf(2));
              case REF:
              case BOOLEAN:
            }
            break;
          case TRUE:
            switch(queryInputType){
              case BOOLEAN:
                return set.contains(Boolean.TRUE);
              case BYTE:
                return set.contains(Byte.valueOf((byte)1));
              case CHAR:
                return set.contains(Character.valueOf((char)1));
              case SHORT:
                return set.contains(Short.valueOf((short)1));
              case INT:
                return set.contains(Integer.valueOf(1));
              case LONG:
                return set.contains(Long.valueOf(1));
              case FLOAT:
                return set.contains(Float.valueOf(1));
              case DOUBLE:
                return set.contains(Double.valueOf(1));
              case REF:
            }
            break;
          case FALSE:
            switch(queryInputType){
              case BOOLEAN:
                return set.contains(Boolean.FALSE);
              case BYTE:
                return set.contains(Byte.valueOf((byte)0));
              case CHAR:
                return set.contains(Character.valueOf((char)0));
              case SHORT:
                return set.contains(Short.valueOf((short)0));
              case INT:
                return set.contains(Integer.valueOf(0));
              case LONG:
                return set.contains(Long.valueOf(0));
              case FLOAT:
                return set.contains(Float.valueOf(0));
              case DOUBLE:
                return set.contains(Double.valueOf(0));
              case REF:
            }
            break;
          case NULL:
            switch(queryInputType){
              case BOOLEAN:
                return set.contains((Boolean)null);
              case BYTE:
                return set.contains((Byte)null);
              case CHAR:
                return set.contains((Character)null);
              case SHORT:
                return set.contains((Short)null);
              case INT:
                return set.contains((Integer)null);
              case LONG:
                return set.contains((Long)null);
              case FLOAT:
                return set.contains((Float)null);
              case DOUBLE:
                return set.contains((Double)null);
              case REF:
            }
            break;
        }
        break;
      case ToPrimitive:
        switch(queryVal){
          case NEG0:
            switch(queryInputType){
              case DOUBLE:
                return set.contains(-0.0d);
              case FLOAT:
                return set.contains(-0.0f);
              case BOOLEAN:
              case BYTE:
              case CHAR:
              case SHORT:
              case INT:
              case LONG:
              case REF:
            }
            break;
          case TRUE:
             switch(queryInputType){
              case BOOLEAN:
                return set.contains(true);
              case BYTE:
                return set.contains((byte)1);
              case CHAR:
                return set.contains((char)1);
              case SHORT:
                return set.contains((short)1);
              case INT:
                return set.contains((int)1);
              case LONG:
                return set.contains((long)1);
              case FLOAT:
                return set.contains((float)1);
              case DOUBLE:
                return set.contains((double)1);
              case REF:
            }
            break;
          case FALSE:
            switch(queryInputType){
              case BOOLEAN:
                return set.contains(false);
              case BYTE:
                return set.contains((byte)0);
              case CHAR:
                return set.contains((char)0);
              case SHORT:
                return set.contains((short)0);
              case INT:
                return set.contains((int)0);
              case LONG:
                return set.contains((long)0);
              case FLOAT:
                return set.contains((float)0);
              case DOUBLE:
                return set.contains((double)0);
              case REF:
            }
            break;
          case OUTOFRANGE:
            switch(queryInputType){
              case BYTE:
                return set.contains((byte)2);
              case CHAR:
                return set.contains((char)2);
              case SHORT:
                return set.contains((short)2);
              case INT:
                return set.contains((int)2);
              case LONG:
                return set.contains((long)2);
              case FLOAT:
                return set.contains((float)2);
              case DOUBLE:
                return set.contains((double)2);
              case BOOLEAN:
              case REF:
            }
            break;
          case NULL:
        }
    }
    throw new UnsupportedOperationException("queryVal="+queryVal+"; queryInputType="+queryInputType+"; queryCastType="+queryCastType);
  }
}
