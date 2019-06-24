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

public class ByteSetImplMonitor implements MonitoredSet<ByteSetImpl>{
  final CheckedType checkedType;
  final ByteSetImpl set;
  final long[] expectedWords;
  int expectedSize;
  int expectedModCount;
  
  private int getExpectedSize() {
    int size=0;
    for(var word:expectedWords) {
      size+=Long.bitCount(word);
    }
    return size;
  }
  ByteSetImplMonitor(CheckedType checkedType,ByteSetImpl set){
    this.checkedType=checkedType;
    this.set=set;
    this.expectedWords=new long[4];
    expectedWords[0]=set.word0;
    expectedWords[1]=set.word1;
    expectedWords[2]=set.word2;
    expectedWords[3]=set.word3;
    this.expectedSize=getExpectedSize();
    if(checkedType.checked) {
      expectedModCount=((ByteSetImpl.Checked)set).modCount;
    }
  }
  @Override public CheckedType getCheckedType(){
    return this.checkedType;
  }
  @Override public ByteSetImpl getCollection(){
    return set;
  }
  @Override public DataType getDataType(){
    return DataType.BYTE;
  }
  
  abstract class AbstractMonitoredItr implements MonitoredSet.MonitoredSetIterator<OmniIterator.OfByte,ByteSetImpl>{
    final OmniIterator.OfByte itr;
    int expectedValOffset;
    AbstractMonitoredItr(OmniIterator.OfByte itr,int expectedValOffset){
      this.itr=itr;
      this.expectedValOffset=expectedValOffset;
    }
    @Override public OmniIterator.OfByte getIterator(){
      return itr;
    }
    @Override public MonitoredCollection<ByteSetImpl> getMonitoredCollection(){
      return ByteSetImplMonitor.this;
    }
    @Override public boolean hasNext(){
      return expectedValOffset!=128;
    }
    @Override public int getNumLeft(){
      int numLeft=0;
      int expectedValOffset=this.expectedValOffset;
      for(int i=(expectedValOffset>>6)+2;i<4;++i) {
        numLeft+=Long.bitCount(expectedWords[i]>>expectedValOffset);
        expectedValOffset=0;
      }
      return numLeft;
    }
  }
  class UncheckedMonitoredItr extends AbstractMonitoredItr{

    UncheckedMonitoredItr(OmniIterator.OfByte itr,int expectedValOffset){
      super(itr,expectedValOffset);
    }

    @Override public void verifyForEachRemaining(MonitoredFunction function){
      // TODO Auto-generated method stub
      
    }

    @Override public void updateIteratorState(){
      // TODO Auto-generated method stub
      
    }

    @Override public void verifyRemove(){
      // TODO Auto-generated method stub
      
    }

    @Override public void verifyIteratorState(){
      // TODO Auto-generated method stub
      
    }

    @Override public void verifyClone(Object clone){
      // TODO Auto-generated method stub
      
    }

    @Override public Object verifyNext(DataType outputType){
      // TODO Auto-generated method stub
      return null;
    }
    
  }
  
  class CheckedMonitoredItr extends AbstractMonitoredItr{
    int expectedItrModCount;
    int expectedItrLastRet;
    CheckedMonitoredItr(OmniIterator.OfByte itr,int expectedValOffset,int expectedItrModCount,int expectedItrLastRet){
      super(itr,expectedValOffset);
      this.expectedItrModCount=expectedItrModCount;
      this.expectedItrLastRet=expectedItrLastRet;
    }
    @Override public void verifyForEachRemaining(MonitoredFunction function){
      // TODO Auto-generated method stub
      
    }
    @Override public void updateIteratorState(){
      // TODO Auto-generated method stub
      
    }
    @Override public void verifyRemove(){
      // TODO Auto-generated method stub
      
    }
    @Override public void verifyIteratorState(){
      // TODO Auto-generated method stub
      
    }
    @Override public void verifyClone(Object clone){
      // TODO Auto-generated method stub
      
    }
    @Override public Object verifyNext(DataType outputType){
      // TODO Auto-generated method stub
      return null;
    }
    
    
    
  }
  
  @Override public MonitoredIterator<? extends OmniIterator<?>,ByteSetImpl> getMonitoredIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public StructType getStructType(){
    return StructType.ByteSetImpl;
  }
  @Override public boolean isEmpty(){
    return expectedSize==0;
  }
  @Override public void modCollection(){
    int wordIndex=0;
    int bitIndex=-1;
    while(wordIndex<4) {
      long word=expectedWords[wordIndex];
      if(word!=-1) {
        bitIndex=Long.numberOfTrailingZeros(~word);
        break;
      }
      ++wordIndex;
    }
    if(bitIndex==-1) {
      set.clear();
      for(int i=0;i<4;++i) {
        expectedWords[i]=0;
      }
      expectedSize=0;
      ++expectedModCount;
      return;
    }
    byte inputVal=(byte)((wordIndex<<6)+bitIndex);
    set.add(inputVal);
    expectedWords[wordIndex]|=(1L<<bitIndex);
    ++expectedSize;
    ++expectedModCount;
  }
  @Override public int size(){
    return expectedSize;
  }
  @Override public void updateCollectionState(){
    if(checkedType.checked) {
      expectedModCount=((ByteSetImpl.Checked)set).modCount;
    }
    expectedWords[0]=set.word0;
    expectedWords[1]=set.word1;
    expectedWords[2]=set.word2;
    expectedWords[3]=set.word3;
    expectedSize=getExpectedSize();
    
  }
  @Override public void verifyCollectionState(){
    if(checkedType.checked) {
      ByteSetImpl.Checked cast;
      Assertions.assertEquals(expectedModCount,(cast=(ByteSetImpl.Checked)set).modCount);
      Assertions.assertEquals(expectedSize,cast.size);
    }
    Assertions.assertEquals(expectedWords[0],set.word0);
    Assertions.assertEquals(expectedWords[1],set.word1);
    Assertions.assertEquals(expectedWords[2],set.word2);
    Assertions.assertEquals(expectedWords[3],set.word3);
  }
  @Override public void verifyClone(Object clone){
    ByteSetImpl cast;
    Assertions.assertEquals(expectedWords[0],(cast=(ByteSetImpl)clone).word0);
    Assertions.assertEquals(expectedWords[1],cast.word1);
    Assertions.assertEquals(expectedWords[2],cast.word2);
    Assertions.assertEquals(expectedWords[3],cast.word3);
    if(checkedType.checked) {
      ByteSetImpl.Checked checked;
      Assertions.assertEquals(0,(checked=(ByteSetImpl.Checked)cast).modCount);
      Assertions.assertEquals(expectedSize,checked.size);
    }
  }
  @Override public void verifyForEach(MonitoredFunction monitoredFunction){
    Assertions.assertEquals(expectedSize,monitoredFunction.size());
    int monitoredFunctionIndex=0;
    for(int i=Byte.MIN_VALUE;i<=Byte.MAX_VALUE;++i) {
      if(((expectedWords[(i>>6)+2])&(1L<<i))!=0) {
        Assertions.assertEquals(Byte.valueOf((byte)i),monitoredFunction.get(monitoredFunctionIndex));
        ++monitoredFunctionIndex;
      }
    }
  }
  
  @Override public void verifyWriteObject(MonitoredObjectOutputStream monitoredObjectOutputStream){
    Assertions.assertEquals(4,monitoredObjectOutputStream.numwriteLongCalls);
  }
  @Override public void verifyReadObject(ByteSetImpl collection,MonitoredObjectInputStream monitoredObjectInputStream){
    Assertions.assertEquals(4,monitoredObjectInputStream.numreadLongCalls);
    verifyClone(collection);
  }
  @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
    Assertions.assertEquals(expectedSize,filter.numCalls);
    if(result) {
      ++expectedModCount;
    }
    Assertions.assertEquals(!result,filter.removedVals.isEmpty());
    for(var removedVal:filter.removedVals) {
      byte v=(byte)removedVal;
      expectedWords[(v>>6)+2]&=~(1L<<v);
      --expectedSize;
    }
    Assertions.assertEquals(expectedSize,filter.retainedVals.size());
  }
  @Override public void verifyArrayIsCopy(Object arr){
    //nothing to do
  }
  @Override public void updateAddState(Object inputVal,DataType inputType,boolean boxed,boolean result){
    byte v;
    switch(inputType) {
    case BOOLEAN:
      v=((boolean)inputVal)?(byte)1:(byte)0;
      break;
    case BYTE:
      v=(byte)inputVal;
      break;
    default:
      throw new UnsupportedOperationException("Unknown inputType "+inputType);
    }
    expectedWords[(v>>6)+2]|=(1L<<v);
    if(result) {
      ++expectedSize;
      ++expectedModCount;
    }
  }
  @Override public void removeFromExpectedState(QueryVal queryVal,QueryValModification modification){
    byte v=(byte)queryVal.getInputVal(DataType.BYTE,modification);
    expectedWords[(v>>6)+2]&=~(1L<<v);
    --expectedSize;
    ++expectedModCount;
  }
  
}
