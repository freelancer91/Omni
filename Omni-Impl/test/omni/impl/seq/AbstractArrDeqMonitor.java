package omni.impl.seq;

import java.io.Externalizable;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.function.IntConsumer;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredDeque;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;

public abstract class AbstractArrDeqMonitor<DEQ extends OmniDeque<E>,E> implements MonitoredDeque<DEQ>{
  final CheckedType checkedType;
  final DataType dataType;
  final DEQ seq;
  int expectedHead;
  int expectedTail;
  int expectedSize;
  int expectedModCount;
  int expectedCapacity;
  Object expectedArr;
  @Override
public void repairModCount() {
      //nothing to do
  }
  
  abstract static class AbstractItrMonitor<MONITOR extends AbstractArrDeqMonitor<DEQ,E>,DEQ extends OmniDeque<E>,E>
    implements MonitoredCollection.MonitoredIterator<OmniIterator<?>,DEQ>{
    final MONITOR root;
    final OmniIterator<?> itr;
    int expectedCursor;
    int expectedLastRet;
    int expectedItrModCount;
    int numLeft;
    AbstractItrMonitor(MONITOR root,OmniIterator<?> itr,int expectedCursor,int numLeft){
      this.root=root;
      this.itr=itr;
      this.expectedCursor=expectedCursor;
      expectedLastRet=-1;
      expectedItrModCount=root.expectedModCount;
      this.numLeft=numLeft;
    }
    @Override public OmniIterator<?> getIterator(){
      return itr;
    }
    @Override public MonitoredCollection<DEQ> getMonitoredCollection(){
      return root;
    }
    @Override public int getNumLeft(){
      return numLeft;
    }
    @Override public boolean hasNext(){
      return numLeft > 0;
    }
    @Override public boolean nextWasJustCalled(){
      return expectedLastRet != -1;
    }
    @Override public void verifyNextResult(DataType outputType,Object result){
      root.verifyGetResult(expectedCursor,result,outputType);
    }
    abstract IntConsumer getForEachRemainingVerifier(MonitoredFunction function);
    
  }
  abstract DEQ initDeq();
  abstract DEQ initDeq(int capacity);
  abstract DEQ initDeq(Collection<?> collection,Class<? extends Collection<?>> collectionClass);
  AbstractArrDeqMonitor(CheckedType checkedType,DataType dataType,Collection<?> collection,Class<? extends Collection<?>> collectionClass){
      this.checkedType=checkedType;
      this.dataType=dataType;
      this.seq=initDeq(collection,collectionClass);
  }
  AbstractArrDeqMonitor(CheckedType checkedType,DataType dataType){
    this.checkedType=checkedType;
    this.dataType=dataType;
    this.seq=initDeq();
  }
  AbstractArrDeqMonitor(CheckedType checkedType,DataType dataType,int capacity){
    this.checkedType=checkedType;
    this.dataType=dataType;
    this.seq=initDeq(capacity);
    
  }
  @SuppressWarnings("unchecked") @Override public boolean add(int val){
    Object inputVal;
    switch(dataType){
    case BOOLEAN:{
      boolean v;
      ((OmniDeque.OfBoolean)seq).addLast(v=(val & 1) != 0);
      inputVal=v;
      break;
    }
    case BYTE:{
      byte v;
      ((OmniDeque.OfByte)seq).addLast(v=(byte)val);
      inputVal=v;
      break;
    }
    case CHAR:{
      char v;
      ((OmniDeque.OfChar)seq).addLast(v=(char)val);
      inputVal=v;
      break;
    }
    case SHORT:{
      short v;
      ((OmniDeque.OfShort)seq).addLast(v=(short)val);
      inputVal=v;
      break;
    }
    case INT:{
      int v;
      ((OmniDeque.OfInt)seq).addLast(v=val);
      inputVal=v;
      break;
    }
    case LONG:{
      long v;
      ((OmniDeque.OfLong)seq).addLast(v=val);
      inputVal=v;
      break;
    }
    case FLOAT:{
      float v;
      ((OmniDeque.OfFloat)seq).addLast(v=val);
      inputVal=v;
      break;
    }
    case DOUBLE:{
      double v;
      ((OmniDeque.OfDouble)seq).addLast(v=val);
      inputVal=v;
      break;
    }
    case REF:{
      ((OmniDeque.OfRef<Object>)seq).addLast(inputVal=val);
      break;
    }
    default:
      throw dataType.invalid();
    }
    updateAddState(inputVal,dataType);
    return true;
  }
  @Override public boolean verifyAdd(Object inputVal,DataType inputType,FunctionCallType functionCallType){
    Assertions.assertTrue(inputType.callAdd(inputVal,seq,functionCallType));
    updateAddState(inputVal,inputType);
    verifyCollectionState();
    return true;
  }
  @Override public void verifyHashCode(int hashCode){
    int expectedHash=1;
    switch(dataType){
    case BOOLEAN:{
      final var itr=(OmniIterator.OfBoolean)seq.iterator();
      while(itr.hasNext()){
        expectedHash=expectedHash * 31 + Boolean.hashCode(itr.nextBoolean());
      }
      break;
    }
    case BYTE:{
      final var itr=(OmniIterator.OfByte)seq.iterator();
      while(itr.hasNext()){
        expectedHash=expectedHash * 31 + Byte.hashCode(itr.nextByte());
      }
      break;
    }
    case CHAR:{
      final var itr=(OmniIterator.OfChar)seq.iterator();
      while(itr.hasNext()){
        expectedHash=expectedHash * 31 + Character.hashCode(itr.nextChar());
      }
      break;
    }
    case DOUBLE:{
      final var itr=(OmniIterator.OfDouble)seq.iterator();
      while(itr.hasNext()){
        expectedHash=expectedHash * 31 + Double.hashCode(itr.nextDouble());
      }
      break;
    }
    case FLOAT:{
      final var itr=(OmniIterator.OfFloat)seq.iterator();
      while(itr.hasNext()){
        expectedHash=expectedHash * 31 + Float.hashCode(itr.nextFloat());
      }
      break;
    }
    case INT:{
      final var itr=(OmniIterator.OfInt)seq.iterator();
      while(itr.hasNext()){
        expectedHash=expectedHash * 31 + Integer.hashCode(itr.nextInt());
      }
      break;
    }
    case LONG:{
      final var itr=(OmniIterator.OfLong)seq.iterator();
      while(itr.hasNext()){
        expectedHash=expectedHash * 31 + Long.hashCode(itr.nextLong());
      }
      break;
    }
    case REF:{
      final var itr=(OmniIterator.OfRef<?>)seq.iterator();
      while(itr.hasNext()){
        expectedHash=expectedHash * 31 + Objects.hashCode(itr.next());
      }
      break;
    }
    case SHORT:{
      final var itr=(OmniIterator.OfShort)seq.iterator();
      while(itr.hasNext()){
        expectedHash=expectedHash * 31 + Short.hashCode(itr.nextShort());
      }
      break;
    }
    default:
      throw dataType.invalid();
    }
    Assertions.assertEquals(expectedHash,hashCode);
  }
  @Override public boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
      QueryValModification modification){
    final Object inputVal=queryVal.getInputVal(inputType,modification);
    final boolean result=queryCastType.callremoveVal(seq,inputVal,inputType);
    if(result){
      updateRemoveValState(inputVal,inputType);
    }
    verifyCollectionState();
    return result;
  }
  @Override public Object removeFirst(){
    Object result=seq.pop();
    if(dataType==DataType.REF) {
      ((Object[])expectedArr)[expectedHead]=null;
    }
    if(expectedHead == expectedTail){
      expectedTail=-1;
    }else if(++expectedHead == expectedCapacity){
      expectedHead=0;
    }
    --expectedSize;
    ++expectedModCount;
    return result;
  }
  public void removeLast(){
    switch(dataType){
    case BOOLEAN:
      ((OmniDeque.OfBoolean)seq).removeLastBoolean();
      break;
    case BYTE:
      ((OmniDeque.OfByte)seq).removeLastByte();
      break;
    case CHAR:
      ((OmniDeque.OfChar)seq).removeLastChar();
      break;
    case DOUBLE:
      ((OmniDeque.OfDouble)seq).removeLastDouble();
      break;
    case FLOAT:
      ((OmniDeque.OfFloat)seq).removeLastFloat();
      break;
    case INT:
      ((OmniDeque.OfInt)seq).removeLastInt();
      break;
    case LONG:
      ((OmniDeque.OfLong)seq).removeLastLong();
      break;
    case REF:
      ((OmniDeque.OfRef<?>)seq).removeLast();
      ((Object[])expectedArr)[expectedTail]=null;
      break;
    case SHORT:
      ((OmniDeque.OfShort)seq).removeLastShort();
      break;
    default:
      throw dataType.invalid();
    }
    if(expectedHead == expectedTail){
      expectedTail=-1;
    }else if(--expectedTail == -1){
      expectedTail=expectedCapacity - 1;
    }
    --expectedSize;
    ++expectedModCount;
  }
  abstract void rotate(int numToRotate);
  @Override public void updateRemoveIndexState(int index){
    throw new UnsupportedOperationException();
  }
  @Override public MonitoredIterator<?,DEQ> getMonitoredIterator(int index,
      IteratorType itrType){
    final var itrMonitor=getMonitoredIterator(itrType);
    while(--index >= 0 && itrMonitor.hasNext()){
      itrMonitor.iterateForward();
    }
    return itrMonitor;
  }
  @Override public MonitoredIterator<?,DEQ>
      getMonitoredIterator(IteratorType itrType){
    switch(itrType){
    case AscendingItr:
      return getMonitoredIterator();
    case DescendingItr:
      return getMonitoredDescendingIterator();
    default:
      throw itrType.invalid();
    }
  }
  @Override public CheckedType getCheckedType(){
    return checkedType;
  }
  @Override public DEQ getCollection(){
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
    if(expectedSize != 0){
      if(dataType == DataType.REF){
        final var expectedArr=(Object[])this.expectedArr;
        int tail,head;
        if((tail=expectedTail) < (head=expectedHead)){
          final int bound=expectedCapacity;
          do{
            expectedArr[head]=null;
          }while(++head != bound);
          head=0;
        }
        do{
          expectedArr[head]=null;
        }while(++head <= tail);
      }
      expectedTail=-1;
      expectedSize=0;
      ++expectedModCount;
    }
  }
  @Override public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
    ((Externalizable)seq).writeExternal(oos);
    }
  @Override public void verifyGetFirstResult(Object result,DataType outputType){
    verifyGetResult(expectedHead,result,outputType);
  }
  @Override public void verifyGetLastResult(Object result,DataType outputType){
    verifyGetResult(expectedTail,result,outputType);
  }
  
  @Override public void updateRemoveFirstState(){
    if(dataType == DataType.REF){
      ((Object[])expectedArr)[expectedHead]=null;
    }
    if(expectedHead == expectedTail){
      expectedTail=-1;
    }else if(++expectedHead == expectedCapacity){
      expectedHead=0;
    }
    --expectedSize;
    ++expectedModCount;
  }
  @Override public void updateRemoveLastState(){
    if(dataType == DataType.REF){
      ((Object[])expectedArr)[expectedTail]=null;
    }
    if(expectedHead == expectedTail){
      expectedTail=-1;
    }else if(--expectedTail == -1){
      expectedTail=expectedCapacity - 1;
    }
    --expectedSize;
    ++expectedModCount;
  }
}
