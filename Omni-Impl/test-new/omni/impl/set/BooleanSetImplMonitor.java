package omni.impl.set;

import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredObjectInputStream;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredSet;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;

public class BooleanSetImplMonitor implements MonitoredSet<BooleanSetImpl>{
  final CheckedType checkedType;
  final BooleanSetImpl set;
  int expectedState;
  
  BooleanSetImplMonitor(CheckedType checkedType,int expectedState){
    this.checkedType=checkedType;
    this.expectedState=expectedState;
    if(checkedType.checked) {
      this.set=new BooleanSetImpl.Checked(expectedState);
    }else {
      this.set=new BooleanSetImpl(expectedState);
    }
  }
  
  @Override public void updateAddState(Object inputVal,DataType inputType,boolean boxed,boolean result){
    boolean v=(boolean)inputVal;
    expectedState|=(v?2:1);
  }

  @Override public CheckedType getCheckedType(){
    return this.checkedType;
  }

  @Override public BooleanSetImpl getCollection(){
    return set;
  }

  @Override public DataType getDataType(){
    return DataType.BOOLEAN;
  }

  @Override public MonitoredIterator<OmniIterator.OfBoolean,BooleanSetImpl> getMonitoredIterator(){
    if(checkedType.checked) {
      return new CheckedMonitoredItr();
    }
    return new UncheckedMonitoredItr();
  }
  
  @Override public StructType getStructType(){
    return StructType.BooleanSetImpl;
  }

  @Override public boolean isEmpty(){
    return expectedState==0;
  }

  @Override public void modCollection(){
    set.state=expectedState=(expectedState+1)&0b11;
  }

  @Override public int size(){
    return Integer.bitCount(expectedState);
  }

  @Override public void updateCollectionState(){
    expectedState=set.state;
  }

  @Override public void verifyCollectionState(){
    Assertions.assertEquals(expectedState,set.state);
  }

  @Override public void verifyClone(Object clone){
    var cast=(BooleanSetImpl)clone;
    Assertions.assertEquals(checkedType.checked,cast instanceof BooleanSetImpl.Checked);
    Assertions.assertEquals(set.state,cast.state);
  }

  @Override public void verifyForEach(MonitoredFunction monitoredFunction){
    switch(expectedState) {
    case 0b00:
      Assertions.assertTrue(monitoredFunction.isEmpty());
      break;
    case 0b01:
      Assertions.assertEquals(1,monitoredFunction.size());
      Assertions.assertEquals(Boolean.FALSE,monitoredFunction.get(0));
      break;
    case 0b10:
      Assertions.assertEquals(1,monitoredFunction.size());
      Assertions.assertEquals(Boolean.TRUE,monitoredFunction.get(0));
      break;
    default:
      Assertions.assertEquals(2,monitoredFunction.size());
      Assertions.assertEquals(Boolean.FALSE,monitoredFunction.get(0));
      Assertions.assertEquals(Boolean.TRUE,monitoredFunction.get(1));
      break;
    }
  }

  @Override public void removeFromExpectedState(QueryVal queryVal,QueryValModification modification) {
    
    switch(expectedState) {
    case 0b01:
    case 0b10:
      expectedState=0b00;
      break;
    default:
      boolean v=(boolean)queryVal.getInputVal(DataType.BOOLEAN,modification);
      if(v) {
        expectedState=0b01;
      }else {
        expectedState=0b10;
      }
    }
  }
  
  @Override public void verifyWriteObject(MonitoredObjectOutputStream monitoredObjectOutputStream){
    Assertions.assertEquals(1,monitoredObjectOutputStream.numwriteIntalls);
  }

  @Override public void verifyReadObject(BooleanSetImpl collection,
      MonitoredObjectInputStream monitoredObjectInputStream){
    Assertions.assertEquals(checkedType.checked,collection instanceof BooleanSetImpl.Checked);
    Assertions.assertEquals(1,monitoredObjectInputStream.numreadIntCalls);
    Assertions.assertEquals(set.state,collection.state);
  }

  @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
    switch(expectedState) {
    case 0b00:
      Assertions.assertEquals(0,filter.numCalls);
      Assertions.assertEquals(0,filter.numRemoved);
      Assertions.assertEquals(0,filter.numRetained);
      Assertions.assertTrue(filter.removedVals.isEmpty());
      Assertions.assertTrue(filter.retainedVals.isEmpty());
      Assertions.assertFalse(result);
      Assertions.assertEquals(0b00,set.state);
      break;
    case 0b01:
      Assertions.assertEquals(1,filter.numCalls);
      if(result) {
        Assertions.assertEquals(1,filter.numRemoved);
        Assertions.assertEquals(0,filter.numRetained);
        Assertions.assertTrue(filter.removedVals.contains(Boolean.FALSE));
        Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
        Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
        Assertions.assertNotEquals(expectedState,set.state);
        Assertions.assertEquals(0b00,set.state);
      }else {
        Assertions.assertEquals(0,filter.numRemoved);
        Assertions.assertEquals(1,filter.numRetained);
        Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
        Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
        Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
        Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
        Assertions.assertEquals(0b01,set.state);
      }
      break;
    case 0b10:
      Assertions.assertEquals(1,filter.numCalls);
      if(result) {
        Assertions.assertEquals(1,filter.numRemoved);
        Assertions.assertEquals(0,filter.numRetained);
        Assertions.assertTrue(filter.removedVals.contains(Boolean.TRUE));
        Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
        Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
        Assertions.assertEquals(0b00,set.state);
      }else {
        Assertions.assertEquals(0,filter.numRemoved);
        Assertions.assertEquals(1,filter.numRetained);
        Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
        Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
        Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
        Assertions.assertEquals(0b10,set.state);
      }
      break;
    default:
      Assertions.assertEquals(2,filter.numCalls);
      if(result) {
        if(filter.removedVals.contains(Boolean.TRUE)) {
          if(filter.removedVals.contains(Boolean.FALSE)) {
            Assertions.assertEquals(0b00,set.state);
            Assertions.assertTrue(filter.retainedVals.isEmpty());
            Assertions.assertEquals(2,filter.removedVals.size());
          }else{
            Assertions.assertEquals(0b01,set.state);
            Assertions.assertEquals(1,filter.removedVals.size());
            Assertions.assertEquals(1,filter.retainedVals.size());
            Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
            Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
          }
        }else {
          Assertions.assertEquals(0b10,set.state);
          Assertions.assertEquals(1,filter.removedVals.size());
          Assertions.assertEquals(1,filter.retainedVals.size());
          Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
          Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
        }
      }else {
        Assertions.assertEquals(2,filter.numRetained);
        Assertions.assertEquals(0,filter.numRemoved);
        Assertions.assertTrue(filter.removedVals.isEmpty());
        Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
        Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
        Assertions.assertEquals(2,filter.retainedVals.size());
      }
    }
    expectedState=set.state;
  }

  @Override public void verifyArrayIsCopy(Object arr){
    //nothing to do
  }
  
  abstract class AbstractMonitoredItr implements MonitoredSet.MonitoredSetIterator<OmniIterator.OfBoolean,BooleanSetImpl>{

    final OmniIterator.OfBoolean itr;
    int expectedItrState;
    AbstractMonitoredItr(){
      itr=set.iterator();
      expectedItrState=expectedState;
    }
    
    @Override public OmniIterator.OfBoolean getIterator(){
      return itr;
    }

    @Override public MonitoredCollection<BooleanSetImpl> getMonitoredCollection(){
      return BooleanSetImplMonitor.this;
    }
  }

  class UncheckedMonitoredItr extends AbstractMonitoredItr{

    @Override public boolean hasNext(){
      return expectedItrState!=0b00;
    }

    @Override public int getNumLeft(){
      return Integer.bitCount(expectedItrState);
    }

    @Override public void verifyForEachRemaining(MonitoredFunction function){
      switch(expectedItrState) {
      case 0b00:
        Assertions.assertTrue(function.isEmpty());
        break;
      case 0b01:
        Assertions.assertEquals(1,function.size());
        Assertions.assertEquals(Boolean.FALSE,function.get(0));
        break;
      case 0b10:
        Assertions.assertEquals(1,function.size());
        Assertions.assertEquals(Boolean.TRUE,function.get(0));
        break;
      default:
        Assertions.assertEquals(2,function.size());
        Assertions.assertEquals(Boolean.FALSE,function.get(0));
        Assertions.assertEquals(Boolean.TRUE,function.get(1));
      }
      expectedItrState=0b00;
    }

    @Override public void updateIteratorState(){
      expectedState=FieldAndMethodAccessor.BooleanSetImpl.Itr.itrState(itr);
    }


    @Override public void verifyRemove(){
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
      Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Itr.root(itr));
      Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Itr.itrState(itr));
      Assertions.assertEquals(expectedState,set.state);
    }

    @Override public void verifyIteratorState(){
      Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Itr.root(itr));
      Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Itr.itrState(itr));
    }

    @Override public void verifyClone(Object clone){
      Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Itr.root(clone));
      Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Itr.itrState(clone));
    }

    @Override public Object verifyNext(DataType outputType){
      Object result=outputType.callIteratorNext(itr);
      if(expectedItrState==0b11) {
        expectedItrState=0b10;
      }else {
        expectedItrState=0b00;
      }
      Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Itr.root(itr));
      Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Itr.itrState(itr));
      Assertions.assertEquals(expectedState,set.state);
      return result;
    }
    
  }
  
  class CheckedMonitoredItr extends AbstractMonitoredItr{

    @Override public boolean hasNext(){
      switch(expectedItrState){
      case 0b0001:
      case 0b0010:
      case 0b0011:
      case 0b0110:
      case 0b0111:
        return true;
      default:
        return false;
      }
    }

    @Override public int getNumLeft(){
      switch(expectedItrState) {
      case 0b0011:
        return 2;
      case 0b0001:
      case 0b0010:
      case 0b0110:
      case 0b0111:
        return 1;
      default:
        return 0;
      }
    }

    @Override public void verifyForEachRemaining(MonitoredFunction function){
      switch(expectedItrState){
      case 0b0001:
        Assertions.assertEquals(1,function.size());
        Assertions.assertEquals(Boolean.FALSE,function.get(0));
        expectedItrState=0b0101;
        break;
      case 0b0010:
        Assertions.assertEquals(1,function.size());
        Assertions.assertEquals(Boolean.TRUE,function.get(0));
        expectedItrState=0b1010;
        break;
      case 0b0011:
        Assertions.assertEquals(2,function.size());
        Assertions.assertEquals(Boolean.FALSE,function.get(0));
        Assertions.assertEquals(Boolean.TRUE,function.get(1));
        expectedItrState=0b1111;
        break;
      case 0b0110:
        Assertions.assertEquals(1,function.size());
        Assertions.assertEquals(Boolean.TRUE,function.get(0));
        expectedItrState=0b1110;
        break;
      case 0b0111:
        Assertions.assertEquals(1,function.size());
        Assertions.assertEquals(Boolean.TRUE,function.get(0));
        expectedItrState=0b1111;
        break;
      default:
        Assertions.assertTrue(function.isEmpty());
      }
    }

    @Override public void updateIteratorState(){
      
      expectedState=FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.itrState(itr);
      }

    

    @Override public void verifyRemove(){
      itr.remove();
      switch(expectedItrState){
        default:
          expectedState=0b00;
          expectedItrState=0b0100;
          break;
        case 0b0111:
          expectedState=0b10;
          expectedItrState=0b0110;
          break;
        case 0b1010:
          expectedState=0b00;
          expectedItrState=0b1000;
          break;
        case 0b1110:
          expectedState=0b00;
          expectedItrState=0b1100;
          break;
        case 0b1111:
          expectedState=0b01;
          expectedItrState=0b1101;
      }
      Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.root(itr));
      Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.itrState(itr));
      Assertions.assertEquals(expectedState,set.state);
    }

    @Override public void verifyIteratorState(){
      Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.root(itr));
      Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.itrState(itr));
    }

    @Override public void verifyClone(Object clone){
      Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.root(clone));
      Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.itrState(clone));
    }

    @Override public Object verifyNext(DataType outputType){
      Object result=outputType.callIteratorNext(itr);
      switch(expectedItrState){
      default:
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
      case 0b0111:
        this.expectedItrState=0b1111;
        break;
      }
      Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Itr.root(itr));
      Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Itr.itrState(itr));
      Assertions.assertEquals(expectedState,set.state);
      return result;
    
    }
    
  }

}