package omni.impl.seq;
import java.io.Externalizable;
import java.io.IOException;
import java.util.EnumSet;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.BooleanDblLnkNode;
import omni.impl.ByteDblLnkNode;
import omni.impl.CharDblLnkNode;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.DoubleDblLnkNode;
import omni.impl.FloatDblLnkNode;
import omni.impl.FunctionCallType;
import omni.impl.IntDblLnkNode;
import omni.impl.IteratorType;
import omni.impl.LongDblLnkNode;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredDeque;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredList;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.RefDblLnkNode;
import omni.impl.ShortDblLnkNode;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
public class DblLnkSeqTest{
  private static final EnumSet<StructType> ALL_STRUCTS=EnumSet.of(StructType.DblLnkList);
  
  
  private static class DblLnkSeqMonitor<LSTDEQ extends AbstractSeq<E>&OmniDeque<E>&OmniList<E>&Externalizable,E>
      extends AbstractSequenceMonitor<LSTDEQ> implements MonitoredDeque<LSTDEQ>,MonitoredList<LSTDEQ>{
    private static class SubListMonitor<SUBLIST extends AbstractSeq<E>&OmniList<E>,LSTDEQ extends AbstractSeq<E>&OmniDeque<E>&OmniList<E>&Externalizable,E> implements MonitoredList<SUBLIST>{
      final DblLnkSeqMonitor<LSTDEQ,E> expectedRoot;
      final SubListMonitor<SUBLIST,LSTDEQ,E> expectedParent;
      final SUBLIST seq;
      int expectedSize;
      int expectedModCount;
      //TODO add the other fields
      
      
      SubListMonitor(DblLnkSeqMonitor<LSTDEQ,E> expectedRoot,int fromIndex,int toIndex){
        //TODO
        throw new UnsupportedOperationException();
      }
      SubListMonitor(SubListMonitor<SUBLIST,LSTDEQ,E> expectedRoot,int fromIndex,int toIndex){
        //TODO
        throw new UnsupportedOperationException();
      }
      
      @Override public void updateRemoveIndexState(int index){
        //TODO
        throw new UnsupportedOperationException();
      }

      @Override public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(int index,
          IteratorType itrType){
        //TODO
        throw new UnsupportedOperationException();
      }

      @Override public void verifyGetResult(int index,Object result,DataType outputType){
        //TODO
        throw new UnsupportedOperationException();
      }

      @Override public void updateRemoveValState(Object inputVal,DataType inputType){
        //TODO
        throw new UnsupportedOperationException();
      }

      @Override public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(IteratorType itrType){
        //TODO
        throw new UnsupportedOperationException();
      }

      @Override public CheckedType getCheckedType(){
        return expectedRoot.checkedType;
      }

      @Override public SUBLIST getCollection(){
        return seq;
      }

      @Override public DataType getDataType(){
        return expectedRoot.dataType;
      }

      @Override public MonitoredIterator<? extends OmniIterator<?>,SUBLIST> getMonitoredIterator(){
        // TODO Auto-generated method stub
        return null;
      }

      @Override public StructType getStructType(){
        return StructType.DblLnkSubList;
      }

      @Override public void modCollection(){
        Consumer<SubListMonitor<SUBLIST,LSTDEQ,E>> modifier;
        var dataType=expectedRoot.dataType;
        switch(dataType) {
        case BOOLEAN:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case BYTE:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case CHAR:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.CharDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case DOUBLE:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case FLOAT:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.FloatDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case INT:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case LONG:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.LongDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case REF:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.RefDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        case SHORT:
          modifier=subList->subList.expectedModCount=FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.incrementModCount(subList.seq);
          break;
        default:
          throw dataType.invalid();
        }
        var curr=this;
        do {
          modifier.accept(curr);
        }while((curr=curr.expectedParent)!=null);
        expectedRoot.modCollection();
      }

      @Override public void modParent(){
        expectedParent.modCollection();
      }

      @Override public void modRoot(){
        expectedRoot.modCollection();
      }

      @Override public int size(){
        return expectedSize;
      }

      @Override public void updateClearState(){
        // TODO Auto-generated method stub
        
      }

      @Override public void updateCollectionState(){
        // TODO Auto-generated method stub
        
      }

      @Override public void verifyClone(Object clone){
        // TODO Auto-generated method stub
        
      }

      @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
        // TODO Auto-generated method stub
        
      }

      @Override public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
        // TODO Auto-generated method stub
        
      }

      @Override public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
        // TODO Auto-generated method stub
        
      }

      @Override public void updateAddState(int index,Object inputVal,DataType inputType){
        // TODO Auto-generated method stub
        
      }

      @Override public void verifyPutResult(int index,Object input,DataType inputType){
        // TODO Auto-generated method stub
        
      }

      @Override public MonitoredListIterator<? extends OmniListIterator<?>,SUBLIST> getMonitoredListIterator(){
        // TODO Auto-generated method stub
        return null;
      }

      @Override public MonitoredListIterator<? extends OmniListIterator<?>,SUBLIST> getMonitoredListIterator(int index){
        // TODO Auto-generated method stub
        return null;
      }

      @Override public void updateReplaceAllState(MonitoredFunction function){
        // TODO Auto-generated method stub
        
      }

      @Override public void verifyCollectionState(boolean refIsSame){
        // TODO Auto-generated method stub
        
      }

      @Override public void copyListContents(){
        // TODO Auto-generated method stub
        
      }

      @Override public void incrementModCount(){
        // TODO Auto-generated method stub
        
      }

      @Override public MonitoredList<?> getMonitoredSubList(int fromIndex,int toIndex){
        return new SubListMonitor<>(this,fromIndex,toIndex);
      }
      //TODO
    }
   
    @SuppressWarnings("unchecked") private Object next(Object node) {
      switch(dataType) {
      case BOOLEAN:
        return ((BooleanDblLnkNode)node).next;
      case BYTE:
        return ((ByteDblLnkNode)node).next;
      case CHAR:
        return ((CharDblLnkNode)node).next;
      case DOUBLE:
        return ((DoubleDblLnkNode)node).next;
      case FLOAT:
        return ((FloatDblLnkNode)node).next;
      case INT:
        return ((IntDblLnkNode)node).next;
      case LONG:
        return ((LongDblLnkNode)node).next;
      case REF:
        return ((RefDblLnkNode<E>)node).next;
      case SHORT:
        return ((ShortDblLnkNode)node).next;
      default:
        throw dataType.invalid();
      }
    }
    @SuppressWarnings("unchecked") private Object prev(Object node) {
      switch(dataType) {
      case BOOLEAN:
        return ((BooleanDblLnkNode)node).prev;
      case BYTE:
        return ((ByteDblLnkNode)node).prev;
      case CHAR:
        return ((CharDblLnkNode)node).prev;
      case DOUBLE:
        return ((DoubleDblLnkNode)node).prev;
      case FLOAT:
        return ((FloatDblLnkNode)node).prev;
      case INT:
        return ((IntDblLnkNode)node).prev;
      case LONG:
        return ((LongDblLnkNode)node).prev;
      case REF:
        return ((RefDblLnkNode<E>)node).prev;
      case SHORT:
        return ((ShortDblLnkNode)node).prev;
      default:
        throw dataType.invalid();
      }
    }

    private Object head(AbstractSeq<E> seq) {
      switch(dataType) {
      case BOOLEAN:
        return ((BooleanDblLnkSeq)seq).head;
      case BYTE:
        return ((ByteDblLnkSeq)seq).head;
      case CHAR:
        return ((CharDblLnkSeq)seq).head;
      case DOUBLE:
        return ((DoubleDblLnkSeq)seq).head;
      case FLOAT:
        return ((FloatDblLnkSeq)seq).head;
      case INT:
        return ((IntDblLnkSeq)seq).head;
      case LONG:
        return ((LongDblLnkSeq)seq).head;
      case REF:
        return ((RefDblLnkSeq<E>)seq).head;
      case SHORT:
        return ((ShortDblLnkSeq)seq).head;
      default:
        throw dataType.invalid();
      }
    }
    private Object tail(AbstractSeq<E> seq) {
      switch(dataType) {
      case BOOLEAN:
        return ((BooleanDblLnkSeq)seq).tail;
      case BYTE:
        return ((ByteDblLnkSeq)seq).tail;
      case CHAR:
        return ((CharDblLnkSeq)seq).tail;
      case DOUBLE:
        return ((DoubleDblLnkSeq)seq).tail;
      case FLOAT:
        return ((FloatDblLnkSeq)seq).tail;
      case INT:
        return ((IntDblLnkSeq)seq).tail;
      case LONG:
        return ((LongDblLnkSeq)seq).tail;
      case REF:
        return ((RefDblLnkSeq<E>)seq).tail;
      case SHORT:
        return ((ShortDblLnkSeq)seq).tail;
      default:
        throw dataType.invalid();
      }
    }
    private Object getNode(int index,AbstractSeq<E> seq,int expectedSize) {
      int tailDist;
      if((tailDist=expectedSize-index)<=index) {
        return getNodeIterateDown(seq,tailDist);
      }else {
        return getNodeIterateUp(index,seq);
      }
    }
    private Object getNodeIterateUp(int index,AbstractSeq<E> seq){
      switch(dataType) {
      case BOOLEAN:{
        var curr=((BooleanDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case BYTE:{
        var curr=((ByteDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case CHAR:{
        var curr=((CharDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case DOUBLE:{
        var curr=((DoubleDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case FLOAT:{
        var curr=((FloatDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case INT:{
        var curr=((IntDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case LONG:{
        var curr=((LongDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case REF:{
        var curr=((RefDblLnkSeq<E>)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      case SHORT:{
        var curr=((ShortDblLnkSeq)seq).head;
        while(index>0) {
          curr=curr.next;
          --index;
        }
        return curr;
      }
      default:
        throw dataType.invalid();
      }
    }
    private Object getNodeIterateDown(AbstractSeq<E> seq,int tailDist){
      switch(dataType) {
      case BOOLEAN:{
        var curr=((BooleanDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case BYTE:{
        var curr=((ByteDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case CHAR:{
        var curr=((CharDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case DOUBLE:{
        var curr=((DoubleDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case FLOAT:{
        var curr=((FloatDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case INT:{
        var curr=((IntDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case LONG:{
        var curr=((LongDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case REF:{
        var curr=((RefDblLnkSeq<E>)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      case SHORT:{
        var curr=((ShortDblLnkSeq)seq).tail;
        while(--tailDist>0) {
          curr=curr.prev;
        }
        return curr;
      }
      default:
        throw dataType.invalid();
      }
    }
  /*
    @SuppressWarnings("unchecked") private Object val(Object node) {
  switch(dataType) {
  case BOOLEAN:
    return ((BooleanDblLnkNode)node).val;
  case BYTE:
    return ((ByteDblLnkNode)node).val;
  case CHAR:
    return ((CharDblLnkNode)node).val;
  case DOUBLE:
    return ((DoubleDblLnkNode)node).val;
  case FLOAT:
    return ((FloatDblLnkNode)node).val;
  case INT:
    return ((IntDblLnkNode)node).val;
  case LONG:
    return ((LongDblLnkNode)node).val;
  case REF:
    return ((RefDblLnkNode<E>)node).val;
  case SHORT:
    return ((ShortDblLnkNode)node).val;
  default:
    throw dataType.invalid();
  }
}
    private Object iterateUp(Object node,int length) {
      switch(dataType) {
      case BOOLEAN:{
        var cast=(BooleanDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case BYTE:{
        var cast=(ByteDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case CHAR:{
        var cast=(CharDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case DOUBLE:{
        var cast=(DoubleDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case FLOAT:{
        var cast=(FloatDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case INT:{
        var cast=(IntDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case LONG:{
        var cast=(LongDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case REF:{
        @SuppressWarnings("unchecked") var cast=(RefDblLnkNode<E>)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      case SHORT:{
        var cast=(ShortDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.next;
        }
        return cast;
      }  
      default:
        throw dataType.invalid();
      }
    }
    private Object iterateDown(Object node,int length) {
      switch(dataType) {
      case BOOLEAN:{
        var cast=(BooleanDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case BYTE:{
        var cast=(ByteDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case CHAR:{
        var cast=(CharDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case DOUBLE:{
        var cast=(DoubleDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case FLOAT:{
        var cast=(FloatDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case INT:{
        var cast=(IntDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case LONG:{
        var cast=(LongDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case REF:{
        @SuppressWarnings("unchecked") var cast=(RefDblLnkNode<E>)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      case SHORT:{
        var cast=(ShortDblLnkNode)node;
        while(length>0) {
          --length;
          cast=cast.prev;
        }
        return cast;
      }  
      default:
        throw dataType.invalid();
      }
    }
    */
    private abstract class AbstractItrMonitor<ITR extends OmniIterator<E>> implements MonitoredIterator<ITR,LSTDEQ>{
      final ITR itr;
      Object expectedCurr;
      Object expectedLastRet;
      int expectedCurrIndex;
      int expectedItrModCount;
      int lastRetIndex;
      AbstractItrMonitor(ITR itr,int expectedCurrIndex,Object expectedCurr){
        this.itr=itr;
        this.expectedCurrIndex=expectedCurrIndex;
        this.expectedCurr=expectedCurr;
        this.expectedItrModCount=expectedModCount;
        this.lastRetIndex=-1;
      }
      @Override public boolean nextWasJustCalled(){
        return lastRetIndex!=0 && lastRetIndex<expectedCurrIndex;
      }
      @Override public void updateItrNextState(){
        expectedLastRet=expectedCurr;
        expectedCurr=next(expectedCurr);
        lastRetIndex=expectedCurrIndex++;
      }
      @Override public void verifyNextResult(DataType outputType,Object result){
        verifyGetResult(lastRetIndex,result,outputType);
      }
      @Override public void updateItrRemoveState(){
        int expectedCurrIndex=this.expectedCurrIndex;
        int lastRetIndex;
        updateRemoveIndexState(lastRetIndex=this.lastRetIndex);
        ++expectedItrModCount;
        if(expectedCurrIndex==lastRetIndex) {
          this.expectedCurr=next(expectedLastRet);
        }else {
          this.expectedCurrIndex=expectedCurrIndex-1;
        }
        this.lastRetIndex=-1;
        this.expectedLastRet=null;
      }
      @Override public ITR getIterator(){
        return itr;
      }
      @Override public MonitoredCollection<LSTDEQ> getMonitoredCollection(){
        return DblLnkSeqMonitor.this;
      }
      @Override public boolean hasNext(){
        return this.expectedCurrIndex<expectedSize;
      }
      @Override public int getNumLeft(){
        return expectedSize-expectedCurrIndex;
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        int lastRetIndex=this.lastRetIndex;
        int expectedCurrIndex=this.expectedCurrIndex;
        int expectedBound=expectedSize;
        final var functionItr=function.iterator();
        switch(dataType) {
        case BOOLEAN:{
          final var expectedArr=(boolean[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(BooleanDblLnkNode)this.expectedCurr;
          var expectedLastRet=(BooleanDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(boolean)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case BYTE:{
          final var expectedArr=(byte[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(ByteDblLnkNode)this.expectedCurr;
          var expectedLastRet=(ByteDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(byte)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case CHAR:{
          final var expectedArr=(char[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(CharDblLnkNode)this.expectedCurr;
          var expectedLastRet=(CharDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(char)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case DOUBLE:{
          final var expectedArr=(double[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(DoubleDblLnkNode)this.expectedCurr;
          var expectedLastRet=(DoubleDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(double)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case FLOAT:{
          final var expectedArr=(float[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(FloatDblLnkNode)this.expectedCurr;
          var expectedLastRet=(FloatDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(float)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case INT:{
          final var expectedArr=(int[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(IntDblLnkNode)this.expectedCurr;
          var expectedLastRet=(IntDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(int)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case LONG:{
          final var expectedArr=(long[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(LongDblLnkNode)this.expectedCurr;
          var expectedLastRet=(LongDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(long)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case REF:{
          final var expectedArr=(Object[])DblLnkSeqMonitor.this.expectedArr;
          @SuppressWarnings("unchecked") var expectedCurr=(RefDblLnkNode<E>)this.expectedCurr;
          @SuppressWarnings("unchecked") var expectedLastRet=(RefDblLnkNode<E>)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertSame(expectedArr[expectedCurrIndex],functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        case SHORT:{
          final var expectedArr=(short[])DblLnkSeqMonitor.this.expectedArr;
          var expectedCurr=(ShortDblLnkNode)this.expectedCurr;
          var expectedLastRet=(ShortDblLnkNode)this.expectedLastRet;
          while(expectedCurrIndex<expectedBound) {
            Assertions.assertEquals(expectedArr[expectedCurrIndex],(short)functionItr.next());
            lastRetIndex=expectedCurrIndex++;
            expectedLastRet=expectedCurr;
            expectedCurr=expectedCurr.next;
          }
          this.expectedCurr=expectedCurr;
          this.expectedLastRet=expectedLastRet;
          break;
        }
        default:
          throw dataType.invalid();
        }
        this.expectedCurrIndex=expectedCurrIndex;
        this.lastRetIndex=lastRetIndex;
      }
    }
    private class ItrMonitor extends AbstractItrMonitor<OmniIterator<E>>{
      ItrMonitor(){
        super(seq.iterator(),0,head(seq));
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.AscendingItr;
      }
      @Override public void verifyCloneHelper(Object clone){
        var checked=checkedType.checked;
        switch(dataType) {
        case BOOLEAN:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case BYTE:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case CHAR:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case DOUBLE:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case FLOAT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case INT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case LONG:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case REF:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        case SHORT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.AscendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.AscendingItr.curr(itr));
          }
          break;
        }
        default:
          throw dataType.invalid();
        }
      }
    }
    private class ListItrMonitor extends AbstractItrMonitor<OmniListIterator<E>> implements MonitoredListIterator<OmniListIterator<E>,LSTDEQ>{
      ListItrMonitor(){
        super(seq.listIterator(),0,head(seq));
      }
      ListItrMonitor(int index){
        super(seq.listIterator(index),index,getNode(index,seq,expectedSize));
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.BidirectionalItr;
      }
      @Override public void verifyCloneHelper(Object clone){
        var checked=checkedType.checked;
        switch(dataType) {
        case BOOLEAN:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case BYTE:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case CHAR:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case DOUBLE:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case FLOAT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case INT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case LONG:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case REF:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        case SHORT:{
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr));
          }
          break;
        }
        default:
          throw dataType.invalid();
        }
      }
      @Override public boolean previousWasJustCalled(){
        return this.lastRetIndex==this.expectedCurrIndex;
      }
      @Override public void updateItrPreviousState(){
        if(expectedCurr==null) {
          expectedCurr=tail(seq);
        }else {
          expectedCurr=prev(expectedCurr);
        }
        this.expectedLastRet=expectedCurr;
        this.lastRetIndex=--expectedCurrIndex;
      }
      @Override public void verifyPreviousResult(DataType outputType,Object result){
        verifyGetResult(lastRetIndex,result,outputType);
      }
      @Override public boolean hasPrevious(){
        return expectedCurrIndex>0;
      }
      @Override public int nextIndex(){
        return expectedCurrIndex;
      }
      @Override public int previousIndex(){
        return expectedCurrIndex-1;
      }
      @Override public void updateItrSetState(Object input,DataType inputType){
        verifyPutResult(lastRetIndex,input,inputType);
      }
      @Override public void updateItrAddState(Object input,DataType inputType){
        DblLnkSeqMonitor.this.updateAddState(expectedCurrIndex++,input,inputType);
        ++expectedItrModCount;
        this.lastRetIndex=-1;
        this.expectedLastRet=null;
      }
    }
    private class DescendingItrMonitor implements MonitoredIterator<OmniIterator<E>,LSTDEQ>{
      final OmniIterator<E> itr=seq.iterator();
      Object expectedCurr=tail(seq);
      Object expectedLastRet;
      int expectedCurrIndex=expectedSize;
      int expectedItrModCount=expectedModCount;
      int lastRetIndex=-1;
      @Override public boolean nextWasJustCalled(){
        return this.lastRetIndex!=-1;
      }
      @Override public void updateItrNextState(){
        this.expectedCurr=prev(this.expectedLastRet=this.expectedCurr);
        this.lastRetIndex=--expectedCurrIndex;
      }
      @Override public void verifyNextResult(DataType outputType,Object result){
        verifyGetResult(lastRetIndex,result,outputType);
      }
      @Override public void updateItrRemoveState(){
        updateRemoveIndexState(lastRetIndex);
        this.expectedLastRet=null;
        this.lastRetIndex=-1;
        ++expectedItrModCount;
      }
      @Override public OmniIterator<E> getIterator(){
        return itr;
      }
      @Override public IteratorType getIteratorType(){
        return IteratorType.DescendingItr;
      }
      @Override public MonitoredCollection<LSTDEQ> getMonitoredCollection(){
        return DblLnkSeqMonitor.this;
      }
      @Override public boolean hasNext(){
        return expectedCurrIndex>0;
      }
      @Override public int getNumLeft(){
        return expectedCurrIndex;
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        var itr=function.iterator();
        int expectedCurrIndex=this.expectedCurrIndex;
        int lastRetIndex=this.lastRetIndex;
        Object expectedLastRet=this.expectedLastRet;
        switch(dataType) {
        case BOOLEAN:{
          var arr=(boolean[])expectedArr;
          var castCurr=(BooleanDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(boolean)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case BYTE:{
          var arr=(byte[])expectedArr;
          var castCurr=(ByteDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(byte)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case CHAR:{
          var arr=(char[])expectedArr;
          var castCurr=(CharDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(char)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case DOUBLE:{
          var arr=(double[])expectedArr;
          var castCurr=(DoubleDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(double)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case FLOAT:{
          var arr=(float[])expectedArr;
          var castCurr=(FloatDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(float)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case INT:{
          var arr=(int[])expectedArr;
          var castCurr=(IntDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(int)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case LONG:{
          var arr=(long[])expectedArr;
          var castCurr=(LongDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(long)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case REF:{
          var arr=(Object[])expectedArr;
          @SuppressWarnings("unchecked") var castCurr=(RefDblLnkNode<E>)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertSame(arr[lastRetIndex=--expectedCurrIndex],itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        case SHORT:{
          var arr=(short[])expectedArr;
          var castCurr=(ShortDblLnkNode)expectedCurr;
          while(expectedCurrIndex>0) {
            Assertions.assertEquals(arr[lastRetIndex=--expectedCurrIndex],(short)itr.next());
            expectedLastRet=castCurr=castCurr.prev;
          }
          break;
        }
        default:
          throw dataType.invalid();
        }
        this.expectedLastRet=expectedLastRet;
        this.expectedCurr=null;
        this.lastRetIndex=lastRetIndex;
        this.expectedCurrIndex=expectedCurrIndex;
        
      }
      @Override public void verifyCloneHelper(Object clone){
        var checked=checkedType.checked;
        switch(dataType) {
        case BOOLEAN:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.BooleanDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.BooleanDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case BYTE:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case CHAR:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.CharDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.CharDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case DOUBLE:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.DoubleDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.DoubleDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case FLOAT:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.FloatDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.FloatDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case INT:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case LONG:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.LongDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case REF:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.RefDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        case SHORT:
          if(checked) {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.curr(itr));
            Assertions.assertSame(expectedLastRet,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.lastRet(itr));
            Assertions.assertEquals(expectedCurrIndex,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.currIndex(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.modCount(itr));
          }else {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.DescendingItr.parent(itr));
            Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.DescendingItr.curr(itr));
          }
          break;
        default:
          throw dataType.invalid();
        }
      }
    }
    
    
    DblLnkSeqMonitor(SequenceInitParams initParams,int initCapacity){
      super(initParams.checkedType,initParams.collectionType,initCapacity);
      updateCollectionState();
    }
    DblLnkSeqMonitor(CheckedType checkedType,DataType dataType){
      super(checkedType,dataType);
      updateCollectionState();
    }
    DblLnkSeqMonitor(CheckedType checkedType,DataType dataType,int capacity){
      super(checkedType,dataType,capacity);
      updateCollectionState();
    }
    
    @Override public MonitoredList<?> getMonitoredSubList(int fromIndex,int toIndex){
      return new SubListMonitor<>(this,fromIndex,toIndex);
    }
    @Override public MonitoredIterator<?,LSTDEQ> getMonitoredDescendingIterator(){
      return new DescendingItrMonitor();
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(){
      return new ItrMonitor();
    }
    @Override public MonitoredListIterator<? extends OmniListIterator<?>,LSTDEQ> getMonitoredListIterator(){
      return new ListItrMonitor();
    }
    @Override public MonitoredListIterator<? extends OmniListIterator<?>,LSTDEQ> getMonitoredListIterator(int index){
      return new ListItrMonitor(index);
    }
    @Override public void copyListContents(){
      final int expectedSize=seq.size;
      int oldExpectedSize=this.expectedSize;
      switch(dataType){
      case BOOLEAN:{
        final var cast=(BooleanDblLnkSeq)seq;
        final var expectedArr=(boolean[])this.expectedArr;
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case BYTE:{
        final var cast=(ByteDblLnkSeq)seq;
        final var expectedArr=(byte[])this.expectedArr;
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case CHAR:{
        final var cast=(CharDblLnkSeq)seq;
        final var expectedArr=(char[])this.expectedArr;
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case DOUBLE:{
        final var cast=(DoubleDblLnkSeq)seq;
        final var expectedArr=(double[])this.expectedArr;
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case FLOAT:{
        final var cast=(FloatDblLnkSeq)seq;
        final var expectedArr=(float[])this.expectedArr;
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case INT:{
        final var cast=(IntDblLnkSeq)seq;
        final var expectedArr=(int[])this.expectedArr;
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case LONG:{
        final var cast=(LongDblLnkSeq)seq;
        final var expectedArr=(long[])this.expectedArr;
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      case REF:{
        @SuppressWarnings("unchecked") final var cast=(RefDblLnkSeq<E>)seq;
        final var expectedArr=(Object[])this.expectedArr;
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        while(oldExpectedSize > expectedSize){
          expectedArr[--oldExpectedSize]=null;
        }
        break;
      }
      case SHORT:{
        final var cast=(ShortDblLnkSeq)seq;
        final var expectedArr=(short[])this.expectedArr;
        var currNode=cast.tail;
        for(int i=expectedSize;;){
          if(currNode == null){
            break;
          }
          expectedArr[--i]=currNode.val;
          currNode=currNode.prev;
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(int index,
        IteratorType itrType){
      MonitoredIterator<? extends OmniIterator<?>,LSTDEQ>  itr;
      switch(itrType) {
      case BidirectionalItr:
        return getMonitoredListIterator(index);
      default:
        throw itrType.invalid();
      case AscendingItr:
        itr=getMonitoredIterator();
        break;
      case DescendingItr:
        itr=getMonitoredDescendingIterator();
      }
      while(--index>=0 && itr.hasNext()) {
        itr.iterateForward();
      }
      return itr;
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,LSTDEQ> getMonitoredIterator(IteratorType itrType){
      switch(itrType) {
      case AscendingItr:
        return getMonitoredIterator();
      case BidirectionalItr:
        return getMonitoredListIterator();
      case DescendingItr:
        return getMonitoredDescendingIterator();
      default:
        throw itrType.invalid();
      }
    }
    @Override public StructType getStructType(){
      return StructType.DblLnkList;
    }
    @Override public void modCollection(){
      switch(dataType){
      case BOOLEAN:
        ++((BooleanDblLnkSeq.CheckedList)seq).modCount;
        break;
      case BYTE:
        ++((ByteDblLnkSeq.CheckedList)seq).modCount;
        break;
      case CHAR:
        ++((CharDblLnkSeq.CheckedList)seq).modCount;
        break;
      case DOUBLE:
        ++((DoubleDblLnkSeq.CheckedList)seq).modCount;
        break;
      case FLOAT:
        ++((FloatDblLnkSeq.CheckedList)seq).modCount;
        break;
      case INT:
        ++((IntDblLnkSeq.CheckedList)seq).modCount;
        break;
      case LONG:
        ++((LongDblLnkSeq.CheckedList)seq).modCount;
        break;
      case REF:
        ++((RefDblLnkSeq.CheckedList<?>)seq).modCount;
        break;
      case SHORT:
        ++((ShortDblLnkSeq.CheckedList)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
      ++expectedModCount;
    }
    @Override public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
      // nothing to do
    }
    @Override public void verifyClone(Object clone){
      Assertions.assertNotSame(clone,seq);
      int size;
      Assertions.assertEquals(size=seq.size,((AbstractSeq<?>)clone).size);
      switch(dataType){
      case BOOLEAN:{
        var cloneCast=(BooleanDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof BooleanDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((BooleanDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof BooleanDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(BooleanDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case BYTE:{
        var cloneCast=(ByteDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof ByteDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((ByteDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof ByteDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(ByteDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case CHAR:{
        var cloneCast=(CharDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof CharDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((CharDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof CharDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(CharDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case DOUBLE:{
        var cloneCast=(DoubleDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof DoubleDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((DoubleDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof DoubleDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(DoubleDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case FLOAT:{
        var cloneCast=(FloatDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof FloatDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((FloatDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof FloatDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(FloatDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case INT:{
        var cloneCast=(IntDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof IntDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((IntDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof IntDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(IntDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case LONG:{
        var cloneCast=(LongDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof LongDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((LongDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof LongDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(LongDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case REF:{
        @SuppressWarnings("unchecked") var cloneCast=(RefDblLnkSeq<E>)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof RefDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((RefDblLnkSeq.CheckedList<E>)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof RefDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          @SuppressWarnings("unchecked") var thisCast=(RefDblLnkSeq<E>)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertSame(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      case SHORT:{
        var cloneCast=(ShortDblLnkSeq)clone;
        Assertions.assertEquals(checkedType.checked,cloneCast instanceof ShortDblLnkSeq.CheckedList);
        if(checkedType.checked) {
          Assertions.assertEquals(0,((ShortDblLnkSeq.CheckedList)cloneCast).modCount);
        }else {
          Assertions.assertTrue(cloneCast instanceof ShortDblLnkSeq.UncheckedList);
        }
        var cloneHead=cloneCast.head;
        var cloneTail=cloneCast.tail;
        if(size==0) {
          Assertions.assertNull(cloneHead);
          Assertions.assertNull(cloneTail);
        }else {
          var thisCast=(ShortDblLnkSeq)seq;
          var thisHead=thisCast.head;
          var thisTail=thisCast.tail;
          var clonePrev=cloneHead.prev;
          Assertions.assertNull(clonePrev);
          for(int i=0;i < size;++i){
            Assertions.assertNotSame(cloneHead,thisHead);
            Assertions.assertEquals(cloneHead.val,thisHead.val);
            Assertions.assertSame(clonePrev,cloneHead.prev);
            clonePrev=cloneHead;
            cloneHead=cloneHead.next;
            thisHead=thisHead.next;
          }
          Assertions.assertSame(cloneTail,clonePrev);
          Assertions.assertNotSame(cloneTail,thisTail);
          Assertions.assertNull(cloneHead);
        }
        break;
      }
      default:{
        throw dataType.invalid();
      }
      }
     
    }
    @Override public void verifyCollectionState(boolean refIsSame){
      final int expectedSize;
      Assertions.assertEquals(expectedSize=this.expectedSize,((AbstractSeq<?>)seq).size);
      switch(dataType){
      case BOOLEAN:{
        final var cast=(BooleanDblLnkSeq)seq;
        final var expectedArr=(boolean[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((BooleanDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case BYTE:{
        final var cast=(ByteDblLnkSeq)seq;
        final var expectedArr=(byte[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((ByteDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case CHAR:{
        final var cast=(CharDblLnkSeq)seq;
        final var expectedArr=(char[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((CharDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case DOUBLE:{
        final var cast=(DoubleDblLnkSeq)seq;
        final var expectedArr=(double[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((DoubleDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case FLOAT:{
        final var cast=(FloatDblLnkSeq)seq;
        final var expectedArr=(float[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((FloatDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case INT:{
        final var cast=(IntDblLnkSeq)seq;
        final var expectedArr=(int[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((IntDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case LONG:{
        final var cast=(LongDblLnkSeq)seq;
        final var expectedArr=(long[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((LongDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,(long)expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case REF:{
        @SuppressWarnings("unchecked") final var cast=(RefDblLnkSeq<E>)seq;
        final var expectedArr=(Object[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((RefDblLnkSeq.CheckedList<E>)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          if(refIsSame){
            for(int i=0;i < expectedSize;++i){
              Assertions.assertSame(headNode.val,expectedArr[i]);
              Assertions.assertSame(prevNode,headNode.prev);
              prevNode=headNode;
              headNode=headNode.next;
            }
          }else{
            for(int i=0;i < expectedSize;++i){
              Assertions.assertEquals(headNode.val,expectedArr[i]);
              Assertions.assertSame(prevNode,headNode.prev);
              prevNode=headNode;
              headNode=headNode.next;
            }
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      case SHORT:{
        final var cast=(ShortDblLnkSeq)seq;
        final var expectedArr=(short[])this.expectedArr;
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((ShortDblLnkSeq.CheckedList)cast).modCount);
        }
        var headNode=cast.head;
        final var tailNode=cast.tail;
        if(expectedSize == 0){
          Assertions.assertNull(headNode);
          Assertions.assertNull(tailNode);
        }else{
          var prevNode=headNode.prev;
          Assertions.assertNull(prevNode);
          for(int i=0;i < expectedSize;++i){
            Assertions.assertEquals(headNode.val,expectedArr[i]);
            Assertions.assertSame(prevNode,headNode.prev);
            prevNode=headNode;
            headNode=headNode.next;
          }
          Assertions.assertSame(tailNode,prevNode);
          Assertions.assertNull(headNode);
        }
        break;
      }
      default:
        throw dataType.invalid();
      }
    }
    @SuppressWarnings("unchecked") @Override LSTDEQ initSeq(){
      switch(dataType){
      case BOOLEAN:
        if(checkedType.checked){
          return (LSTDEQ)new BooleanDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new BooleanDblLnkSeq.UncheckedList();
        }
      case BYTE:
        if(checkedType.checked){
          return (LSTDEQ)new ByteDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new ByteDblLnkSeq.UncheckedList();
        }
      case CHAR:
        if(checkedType.checked){
          return (LSTDEQ)new CharDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new CharDblLnkSeq.UncheckedList();
        }
      case SHORT:
        if(checkedType.checked){
          return (LSTDEQ)new ShortDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new ShortDblLnkSeq.UncheckedList();
        }
      case INT:
        if(checkedType.checked){
          return (LSTDEQ)new IntDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new IntDblLnkSeq.UncheckedList();
        }
      case LONG:
        if(checkedType.checked){
          return (LSTDEQ)new LongDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new LongDblLnkSeq.UncheckedList();
        }
      case FLOAT:
        if(checkedType.checked){
          return (LSTDEQ)new FloatDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new FloatDblLnkSeq.UncheckedList();
        }
      case DOUBLE:
        if(checkedType.checked){
          return (LSTDEQ)new DoubleDblLnkSeq.CheckedList();
        }else{
          return (LSTDEQ)new DoubleDblLnkSeq.UncheckedList();
        }
      case REF:
        if(checkedType.checked){
          return (LSTDEQ)new RefDblLnkSeq.CheckedList<>();
        }else{
          return (LSTDEQ)new RefDblLnkSeq.UncheckedList<>();
        }
      default:
        throw dataType.invalid();
      }
    }
    @Override LSTDEQ initSeq(int initCap){
      return initSeq();
    }
    @Override void updateModCount(){
      switch(dataType){
      case BOOLEAN:
        expectedModCount=((BooleanDblLnkSeq.CheckedList)seq).modCount;
        break;
      case BYTE:
        expectedModCount=((ByteDblLnkSeq.CheckedList)seq).modCount;
        break;
      case CHAR:
        expectedModCount=((CharDblLnkSeq.CheckedList)seq).modCount;
        break;
      case DOUBLE:
        expectedModCount=((DoubleDblLnkSeq.CheckedList)seq).modCount;
        break;
      case FLOAT:
        expectedModCount=((FloatDblLnkSeq.CheckedList)seq).modCount;
        break;
      case INT:
        expectedModCount=((IntDblLnkSeq.CheckedList)seq).modCount;
        break;
      case LONG:
        expectedModCount=((LongDblLnkSeq.CheckedList)seq).modCount;
        break;
      case REF:
        expectedModCount=((RefDblLnkSeq.CheckedList<?>)seq).modCount;
        break;
      case SHORT:
        expectedModCount=((ShortDblLnkSeq.CheckedList)seq).modCount;
        break;
      default:
        throw dataType.invalid();
      }
    }
  }
  private static class SequenceInitParams{
    final DataType collectionType;
    final StructType structType;
    final CheckedType checkedType;
    final int[] preAllocs;
    final int[] postAllocs;
    public SequenceInitParams(StructType structType,DataType collectionType,CheckedType checkedType,
        int[] preAllocs,int[] postAllocs){
      super();
      this.collectionType=collectionType;
      this.structType=structType;
      this.checkedType=checkedType;
      this.preAllocs=preAllocs;
      this.postAllocs=postAllocs;
    }
    
  }
  
  private static final SequenceInitParams[] INIT_PARAMS;
  static {
    Stream.Builder<SequenceInitParams> builder=Stream.builder();
    for(var checkedType:CheckedType.values()) {
      for(var collectionType:DataType.values()) {
        builder.accept(new SequenceInitParams(StructType.DblLnkList,collectionType,checkedType,OmniArray.OfInt.DEFAULT_ARR,OmniArray.OfInt.DEFAULT_ARR));
      }
    }
//    for(int pre0=0;pre0<=4;pre0+=2) {
//      for(int pre1=0;pre1<=4;pre1+=2) {
//        for(int pre2=0;pre2<=4;pre2+=2) {
//          final var preAllocs=new int[] {pre0,pre1,pre2};
//          for(int post0=0;post0<=4;post0+=2) {
//            for(int post1=0;post1<=4;post1+=2) {
//              for(int post2=0;post2<=4;post2+=2) {
//                final var postAllocs=new int[] {post0,post1,post2};
//                for(var checkedType:CheckedType.values()) {
//                  for(var collectionType:DataType.values()) {
//                    builder.accept(new SequenceInitParams(StructType.DblLnkSubList,collectionType,checkedType,preAllocs,postAllocs));
//                  }
//                }
//              }
//            }
//          }
//        }
//      }
//    }
    INIT_PARAMS=builder.build().toArray(SequenceInitParams[]::new);
  }
  
  private MonitoredList<?> getMonitoredList(SequenceInitParams initParams,int initialCapacity){
    var rootMonitor=new DblLnkSeqMonitor<>(initParams,getInitCapacity(initialCapacity,initParams.preAllocs,initParams.postAllocs));
    if(initParams.structType==StructType.DblLnkSubList) {
      //TODO
      throw new UnsupportedOperationException("Still need to implement sublist monitor");
    }
    return rootMonitor;
  }
  private static int getInitCapacity(int initCapacity,int[] preAllocs,int[] postAllocs) {
    for(int i=preAllocs.length;--i>=0;) {
      initCapacity+=preAllocs[i];
    }
    for(int i=postAllocs.length;--i>=0;) {
      initCapacity+=postAllocs[i];
    }
    return initCapacity;
  }

  @Test
  public void testConstructor_void() {
    for(var checkedType:CheckedType.values()) {
      for(var collectionType:DataType.values()) {
        TestExecutorService.submitTest(()->new DblLnkSeqMonitor<>(checkedType,collectionType).verifyCollectionState());
      }
    }
    TestExecutorService.completeAllTests("DblLnkSeqTest.testConstructor_void");
  }
  @Test
  public void testadd_val() {
    for(var initParams:INIT_PARAMS) {
      for(var illegalMod:initParams.structType.validPreMods) {
        if(initParams.checkedType.checked || illegalMod.expectedException==null) {
          for(var functionCallType:FunctionCallType.values()) {
            for(var inputType:initParams.collectionType.mayBeAddedTo()) {
              if(!functionCallType.boxed || inputType!=DataType.REF) {
                if(illegalMod.expectedException==null) {
                  TestExecutorService.submitTest(()->{
                    var monitor=getMonitoredList(initParams,100);
                    for(int i=0;i<100;++i) {
                      monitor.verifyAdd(inputType.convertValUnchecked(i),inputType,functionCallType);
                    }
                  });
                }else {
                  for(int tmpInitSize=0;tmpInitSize<100;++tmpInitSize) {
                    final int initSize=tmpInitSize;
                    TestExecutorService.submitTest(()->{
                        var monitor=SequenceInitialization.Ascending.initialize(getMonitoredList(initParams,initSize),initSize,0,0);
                        monitor.illegalMod(illegalMod);
                        Assertions.assertThrows(illegalMod.expectedException,()->monitor.verifyAdd(inputType.convertValUnchecked(initSize),inputType,functionCallType));
                    });
                  }
                }
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("DblLnkSeqTest.testadd_val");
  }
  @Test
  public void testadd_intval() {
    //TODO
  }
}
