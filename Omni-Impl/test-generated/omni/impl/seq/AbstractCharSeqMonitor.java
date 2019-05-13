package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import omni.impl.FunctionCallType;
import omni.impl.CharOutputTestArgType;
import omni.impl.CharInputTestArgType;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import omni.impl.MonitoredObjectInputStream;
import omni.impl.MonitoredObjectOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import omni.function.CharComparator;
import omni.function.CharPredicate;
import java.util.function.UnaryOperator;
import omni.function.CharUnaryOperator;
import omni.function.CharConsumer;
import java.util.ArrayList;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.HashSet;
import omni.api.OmniCollection;
import java.io.ObjectOutputStream;
import java.util.Random;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.TypeUtil;
import omni.impl.QueryCastType;
import omni.util.TypeConversionUtil;
import omni.api.OmniStack;
import omni.api.OmniList;
import omni.api.OmniDeque;
@SuppressWarnings({"rawtypes","unchecked"})
abstract class AbstractCharSeqMonitor<SEQ extends OmniCollection.OfChar>{
  static final int[] FIB_SEQ=new int[12];
  static{
    FIB_SEQ[0]=0;
    FIB_SEQ[1]=1;
    FIB_SEQ[2]=2;
    for(int i=3;i<FIB_SEQ.length;++i){
      FIB_SEQ[i]=FIB_SEQ[i-1]+FIB_SEQ[i-2];
    }
  }
  final CheckedType checkedType;
  SEQ seq;
  int expectedSeqSize;
  int expectedSeqModCount;
  AbstractCharSeqMonitor(CheckedType checkedType)
  {
    this.checkedType=checkedType;
  }
  AbstractItrMonitor getDescendingItrMonitor(){
    throw new UnsupportedOperationException();
  }
  AbstractItrMonitor getListItrMonitor(){
    throw new UnsupportedOperationException();
  }
  AbstractItrMonitor getListItrMonitor(int index){
    throw new UnsupportedOperationException();
  }
  abstract SequenceVerificationItr verifyPreAlloc(int expectedVal);
  abstract SequenceVerificationItr verifyPreAlloc();
  abstract void illegalAdd(PreModScenario preModScenario);
  abstract void verifyAddition();
  abstract void writeObject(ObjectOutputStream oos) throws IOException;
  abstract void verifyRemoval();
  abstract void verifyStructuralIntegrity();
  abstract void verifyFunctionalModification();
  abstract AbstractItrMonitor getItrMonitor();
  abstract void verifyBatchRemove(int numRemoved);
  AbstractItrMonitor getItrMonitor(ItrType itrType){
    switch(itrType){
      case Itr:
        return getItrMonitor();
      case ListItr:
        return getListItrMonitor();
      case DescendingItr:
        return getDescendingItrMonitor();
      default:
        throw new Error("unknown itr type "+itrType);
    }
  }
  //TODO clean this up
  void verifyNearBeginningRemoveAt(CharOutputTestArgType outputArgType){
    switch(expectedSeqSize&3)
    {
      case 0:
      for(int seqSize=expectedSeqSize,expectedLo=(seqSize>>2),expectedHi=(seqSize>>2)-1;seqSize>0;--seqSize){
        switch(seqSize&3){
          case 0:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          case 1:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          case 2:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          default:
            removeAt(--expectedLo,outputArgType,seqSize>>2);
            break;
        }
        verifyStructuralIntegrity();
      }
      break;
      case 1:
      for(int seqSize=expectedSeqSize,expectedLo=(seqSize>>2),expectedHi=(seqSize>>2)-1;seqSize>0;--seqSize){
        switch(seqSize&3){
          case 0:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          case 1:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          case 2:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          default:
            removeAt(--expectedLo,outputArgType,seqSize>>2);
            break;
        }
        verifyStructuralIntegrity();
      }
      break;
      case 2:
      for(int seqSize=expectedSeqSize,expectedLo=(seqSize>>2),expectedHi=(seqSize>>2)-1;seqSize>0;--seqSize){
        switch(seqSize&3){
          case 0:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          case 1:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          case 2:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          default:
            removeAt(--expectedLo,outputArgType,seqSize>>2);
            break;
        }
        verifyStructuralIntegrity();
      }
      break;
      case 3:
      for(int seqSize=expectedSeqSize,expectedLo=(seqSize>>2)+1,expectedHi=(seqSize>>2);seqSize>0;--seqSize){
        switch(seqSize&3){
          case 0:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          case 1:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          case 2:
            removeAt(++expectedHi,outputArgType,seqSize>>2);
            break;
          default:
            removeAt(--expectedLo,outputArgType,seqSize>>2);
            break;
        }
        verifyStructuralIntegrity();
      }
      break;
    }
  }
  //TODO clean this up
  void verifyMiddleRemoveAt(CharOutputTestArgType outputArgType){
    if((expectedSeqSize&1)==0)
    {
      for(int seqSize=expectedSeqSize,expectedLo=(seqSize>>1),expectedHi=(seqSize>>1)-1;seqSize>0;--seqSize){
        if((seqSize&1)==0){
          removeAt(++expectedHi,outputArgType,seqSize>>1);
        }else{
          removeAt(--expectedLo,outputArgType,seqSize>>1);
        }
      }
    }
    else
    {
      for(int seqSize=expectedSeqSize,expectedLo=(seqSize>>1)+1,expectedHi=(seqSize>>1);seqSize>0;--seqSize){
        if((seqSize&1)==0){
          removeAt(++expectedHi,outputArgType,seqSize>>1);
        }else{
          removeAt(--expectedLo,outputArgType,seqSize>>1);
        }
      }
    }
  }
  //TODO clean this up
  void verifyNearEndRemoveAt(CharOutputTestArgType outputArgType){
    switch(expectedSeqSize&3)
    {
      case 0:
      for(int seqSize=expectedSeqSize,expectedLo=(seqSize-(seqSize>>2)),expectedHi=(seqSize-(seqSize>>2)-1);seqSize>0;--seqSize){
        switch(seqSize&3){
           case 0:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          case 1:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          case 2:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          default:
            removeAt(++expectedHi,outputArgType,seqSize-(seqSize>>2)-1);
            break;
        }
        verifyStructuralIntegrity();
      }
      break;
      case 1:
      for(int seqSize=expectedSeqSize,expectedLo=(seqSize-(seqSize>>2)),expectedHi=(seqSize-(seqSize>>2)-1);seqSize>0;--seqSize){
        switch(seqSize&3){
          case 0:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          case 1:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          case 2:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          default:
            removeAt(++expectedHi,outputArgType,seqSize-(seqSize>>2)-1);
            break;
        }
        verifyStructuralIntegrity();
      }
      break;
      case 2:
      for(int seqSize=expectedSeqSize,expectedLo=(seqSize-(seqSize>>2)),expectedHi=(seqSize-(seqSize>>2)-1);seqSize>0;--seqSize){
        switch(seqSize&3){
          case 0:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          case 1:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          case 2:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          default:
            removeAt(++expectedHi,outputArgType,seqSize-(seqSize>>2)-1);
            break;
        }
        verifyStructuralIntegrity();
      }
      break;
      case 3:
      for(int seqSize=expectedSeqSize,expectedLo=(seqSize-(seqSize>>2)-1),expectedHi=(seqSize-(seqSize>>2)-2);seqSize>0;--seqSize){
        switch(seqSize&3){
          case 0:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          case 1:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          case 2:
            removeAt(--expectedLo,outputArgType,seqSize-(seqSize>>2)-1);
            break;
          default:
            removeAt(++expectedHi,outputArgType,seqSize-(seqSize>>2)-1);
            break;
        }
        verifyStructuralIntegrity();
      }
      break;
    }
  }
  void verifyBeginningRemoveAt(CharOutputTestArgType outputArgType){
    for(int expected=0,seqSize=expectedSeqSize;expected<seqSize;++expected){
      removeAt(expected,outputArgType,0);
      verifyStructuralIntegrity();
    }
  }
  void verifyEndRemoveAt(CharOutputTestArgType outputArgType){
    for(int seqSize=expectedSeqSize;--seqSize>=0;){
      removeAt(seqSize,outputArgType,seqSize);
      verifyStructuralIntegrity();
    }
  }
  void clear(){
    int seqSize=expectedSeqSize;
    seq.clear();
    if(seqSize!=0){
      verifyBatchRemove(seqSize);
      verifyFunctionalModification();
    }
  }
  void verifyRemoveIf(MonitoredRemoveIfPredicate pred,FunctionCallType functionCallType,int expectedNumRemoved,OmniCollection.OfChar clone){
    boolean retVal;
    if(functionCallType==FunctionCallType.Boxed){
      retVal=seq.removeIf((Predicate)pred);
    }
    else
    {
      retVal=seq.removeIf((CharPredicate)pred);
    }
    if(retVal){
      verifyFunctionalModification();
      int numRemoved;
      numRemoved=pred.numRemoved;
      for(var removedVal:pred.removedVals){
        Assertions.assertFalse(seq.contains(removedVal));
      }
      verifyBatchRemove(numRemoved);
      if(expectedNumRemoved!=-1){
        Assertions.assertEquals(expectedNumRemoved,numRemoved);
      }
    }else{
      Assertions.assertEquals(expectedSeqSize,clone.size());
      var seqItr=seq.iterator();
      var cloneItr=clone.iterator();
      for(int i=0;i<expectedSeqSize;++i){
        Assertions.assertEquals(seqItr.nextChar(),cloneItr.nextChar());
      }
    }
  }
  AbstractItrMonitor getItrMonitor(SequenceLocation seqLocation,ItrType itrType){
    int offset;
    switch(seqLocation){
      case BEGINNING:
        offset=0;
        break;
      case NEARBEGINNING:
        offset=getExpectedSeqSize()>>2;
        break;
      case MIDDLE:
        offset=getExpectedSeqSize()>>1;
        break;
      case NEAREND:
        offset=(getExpectedSeqSize()>>2)*3;
        break;
      case END:
        offset=getExpectedSeqSize();
        break;
      default:
        throw new Error("Unknown seqLocation "+seqLocation);
    }
    AbstractItrMonitor itrMonitor;
    switch (itrType){
      case ListItr:
        return getListItrMonitor(offset);
      case Itr:
        itrMonitor=getItrMonitor();
        break;
      case DescendingItr:
        itrMonitor=getDescendingItrMonitor();
        break;
      default:
        throw new Error("Unknown itrType "+itrType);
    }
    for(int i=0;i<offset;++i)
    {
      itrMonitor.iterateForward();
    }
    return itrMonitor;
  }
  AbstractItrMonitor getListItrMonitor(SequenceLocation seqLocation){
    switch(seqLocation){
      case BEGINNING:
        return getListItrMonitor();
      case NEARBEGINNING:
        return getListItrMonitor(getExpectedSeqSize()/4);
      case MIDDLE:
        return getListItrMonitor(getExpectedSeqSize()/2);
      case NEAREND:
        return getListItrMonitor((getExpectedSeqSize()/4)*3);
      case END:
        return getListItrMonitor(getExpectedSeqSize());
      default:
        throw new Error("Unknown sequence location "+seqLocation);
    }
  }
  int getExpectedSeqSize(){
    return this.expectedSeqSize;
  }
  boolean addVal(char obj){
    boolean ret=seq.add(obj);
    if(ret)
    {
      verifyAddition();
    }
    return ret;
  }
  void add(int index,int val,CharInputTestArgType inputArgType){
    inputArgType.callListAdd(seq,index,val);
    verifyAddition();
  }
  void add(int index,int val){
    add(index,val,CharInputTestArgType.ARRAY_TYPE);
  }
  void verifySet(int index,int val,int expectedRet,FunctionCallType functionCallType){
    if(functionCallType==FunctionCallType.Boxed){
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(expectedRet),((OmniList.OfChar)seq).set(index,TypeConversionUtil.convertToCharacter(val)));
    }
    else
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(expectedRet),((OmniList.OfChar)seq).set(index,TypeConversionUtil.convertTochar(val)));
    }
  }
  void queueRemove(int expectedVal,CharOutputTestArgType outputType){
    outputType.verifyQueueRemove(seq,expectedVal);
    verifyRemoval();
  }
  void removeFirst(int expectedVal,CharOutputTestArgType outputType){
    outputType.verifyDequeRemoveFirst(seq,expectedVal);
    verifyRemoval();
  }
  void removeLast(int expectedVal,CharOutputTestArgType outputType){
    outputType.verifyDequeRemoveLast(seq,expectedVal);
    verifyRemoval();
  }
  void pop(int expectedVal,CharOutputTestArgType outputType){
    outputType.verifyStackPop(seq,expectedVal);
    verifyRemoval();
  }
  void pollFirst(int expectedVal,CharOutputTestArgType outputType){
    outputType.verifyDequePollFirst(seq,expectedSeqSize,expectedVal);
    if(expectedSeqSize!=0){
      verifyRemoval();
    }
  }
  void pollLast(int expectedVal,CharOutputTestArgType outputType){
    outputType.verifyDequePollLast(seq,expectedSeqSize,expectedVal);
    if(expectedSeqSize!=0){
      verifyRemoval();
    }
  }
  void poll(int expectedVal,CharOutputTestArgType outputType){
    outputType.verifyPoll(seq,expectedSeqSize,expectedVal);
    if(expectedSeqSize!=0){
      verifyRemoval();
    }
  }
  void addLast(int val,CharInputTestArgType inputArgType){
    inputArgType.callDequeAddLast(seq,val);
    verifyAddition();
  }
  void addFirst(int val,CharInputTestArgType inputArgType){
    inputArgType.callDequeAddFirst(seq,val);
    verifyAddition();
  }
  boolean offerLast(int val,CharInputTestArgType inputArgType){
    boolean ret=inputArgType.callDequeOfferLast(seq,val);
    if(ret){
      verifyAddition();
    }
    return ret;
  }
  boolean offerFirst(int val,CharInputTestArgType inputArgType){
    boolean ret=inputArgType.callDequeOfferFirst(seq,val);
    if(ret){
      verifyAddition();
    }
    return ret;
  }
  boolean offer(int val,CharInputTestArgType inputArgType){
    boolean ret=inputArgType.callQueueOffer(seq,val);
    if(ret){
      verifyAddition();
    }
    return ret;
  }
  boolean add(int val,CharInputTestArgType inputArgType){
    boolean ret=inputArgType.callCollectionAdd(seq,val);
    if(ret)
    {
      verifyAddition();
    }
    return ret;
  }
  boolean addVal(boolean val)
  {
    boolean ret=seq.add(val);
    if(ret){
      verifyAddition();
    }
    return ret;
  }
  boolean add(int val){
    return add(val,CharInputTestArgType.ARRAY_TYPE);
  }
  void push(int val,CharInputTestArgType inputArgType){
    inputArgType.callStackPush(seq,val);
    verifyAddition();
  }
  void push(int val){
    push(val,CharInputTestArgType.ARRAY_TYPE);
  }
  void put(int index,int val,CharInputTestArgType inputArgType){
    inputArgType.callListPut(seq,index,val);
  }
  boolean isEmpty(){
    return seq.isEmpty();
  }
  void forEach(MonitoredConsumer action,FunctionCallType functionCallType){
    if(functionCallType==FunctionCallType.Boxed){
      seq.forEach((Consumer)action);
    }else
    {
      seq.forEach((CharConsumer)action);
    }
  }
  void unstableSort(MonitoredComparator sorter){
    int seqSize=expectedSeqSize;
    try
    {
      ((OmniList.OfChar)seq).unstableSort((CharComparator)sorter);
    }
    catch(ConcurrentModificationException e)
    {
      throw e;
    }
    catch(RuntimeException e)
    {
      if(seqSize>1){
       verifyFunctionalModification();
      }
      throw e;
    }
    if(seqSize>1){
     verifyFunctionalModification();
    }
  }
  void replaceAll(MonitoredUnaryOperator operator,FunctionCallType functionCallType){
    int seqSize=expectedSeqSize;
    try
    {
      if(functionCallType==FunctionCallType.Boxed){
      ((OmniList.OfChar)seq).replaceAll((UnaryOperator)operator);
      }else
      {
        ((OmniList.OfChar)seq).replaceAll((CharUnaryOperator)operator);
      }
    }
    catch(ConcurrentModificationException e)
    {
      throw e;
    }
    catch(RuntimeException e)
    {
      if(seqSize!=0){
       verifyFunctionalModification();
      }
      throw e;
    }
    if(seqSize!=0){
     verifyFunctionalModification();
    }
  }
  void sort(MonitoredComparator sorter,FunctionCallType functionCallType){
    int seqSize=expectedSeqSize;
    try
    {
      if(functionCallType==FunctionCallType.Boxed){
        ((OmniList.OfChar)seq).sort((Comparator)sorter);
      }else
      {
        ((OmniList.OfChar)seq).sort((CharComparator)sorter);
      }
    }
    catch(ConcurrentModificationException e)
    {
      throw e;
    }
    catch(RuntimeException e)
    {
      if(seqSize>1){
       verifyFunctionalModification();
      }
      throw e;
    }
    if(seqSize>1){
     verifyFunctionalModification();
    }
  }
  void stableAscendingSort(){
    int seqSize=expectedSeqSize;
    try
    {
      ((OmniList.OfChar)seq).stableAscendingSort();
    }
    catch(ConcurrentModificationException e)
    {
      throw e;
    }
    catch(RuntimeException e)
    {
      if(seqSize>1){
       verifyFunctionalModification();
      }
      throw e;
    }
    if(seqSize>1){
     verifyFunctionalModification();
    }
  }
  void stableDescendingSort(){
    int seqSize=expectedSeqSize;
    try
    {
      ((OmniList.OfChar)seq).stableDescendingSort();
    }
    catch(ConcurrentModificationException e)
    {
      throw e;
    }
    catch(RuntimeException e)
    {
      if(seqSize>1){
       verifyFunctionalModification();
      }
      throw e;
    }
    if(seqSize>1){
     verifyFunctionalModification();
    }
  }
  boolean removeFirstOccurrence(Object obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(Object obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean remove(Object obj)
  {
    boolean ret=seq.remove(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(Boolean obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(Boolean obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(Boolean obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(Byte obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(Byte obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(Byte obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(Character obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(Character obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(Character obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(Short obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(Short obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(Short obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(Integer obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(Integer obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(Integer obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(Long obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(Long obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(Long obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(Float obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(Float obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(Float obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(Double obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(Double obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(Double obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(boolean obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(boolean obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(boolean obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(byte obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(byte obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(byte obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(char obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(char obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(char obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(short obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(short obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(short obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(int obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(int obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(int obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(long obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(long obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(long obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(float obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(float obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(float obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeFirstOccurrence(double obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeFirstOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeLastOccurrence(double obj)
  {
    boolean ret=((OmniDeque.OfChar)seq).removeLastOccurrence(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  boolean removeVal(double obj)
  {
    boolean ret=seq.removeVal(obj);
    if(ret)
    {
      verifyRemoval();
    }
    return ret;
  }
  void removeAt(int expectedVal,CharOutputTestArgType outputType,int index){
    outputType.verifyListRemoveAt(seq,index,expectedVal);
    verifyRemoval();
  }
  void get(int expectedVal,CharOutputTestArgType outputType,int index){
    outputType.verifyListGet(seq,index,expectedVal);
  }
  private static int verifySingleStrElement(String str,int strOffset){
    Assertions.assertTrue(str.charAt(strOffset)=='1',"String fails at index "+strOffset);
    return strOffset;
  }
  static void verifyLargeStr(String str,int offset,int bound,SequenceVerificationItr verifyItr){
    if(offset>=bound){
      return;
    }
    int strOffset;
    if(offset==0){
      strOffset=verifySingleStrElement(str,1);
    }else{
      strOffset=(3*offset)-2;
    }
    for(;;){
      verifyItr.verifyIndexAndIterate(1);
      if(++offset==bound){
        return;
      }
      if(str.charAt(++strOffset)!=',' || str.charAt(++strOffset)!=' '){
        break;
      }
      strOffset=verifySingleStrElement(str,++strOffset);
    }
    Assertions.fail("string fails at index "+strOffset);
  }
  void verifyThrowCondition(int numToAdd,PreModScenario preModScenario
  ){
     var verifyItr=verifyPreAlloc();
    {
      verifyItr.verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
    }
  }
  abstract static class AbstractItrMonitor<ITR extends OmniIterator.OfChar>
  {
    final ItrType itrType;
    final ITR itr;
    int expectedItrModCount;
    AbstractItrMonitor(ItrType itrType,ITR itr,int expectedItrModCount)
    {
      this.itrType=itrType;
      this.itr=itr;
      this.expectedItrModCount=expectedItrModCount;
    }
    void iterateReverse(){
      throw new UnsupportedOperationException();
    }
    void verifyPrevious(int expectedVal,CharOutputTestArgType outputType){
      throw new UnsupportedOperationException();
    }
    void add(int v,CharInputTestArgType inputArgType){
      throw new UnsupportedOperationException();
    }
    abstract void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType);
    abstract AbstractCharSeqMonitor getSeqMonitor();
    abstract void verifyNext(int expectedVal,CharOutputTestArgType outputType);
    abstract void iterateForward();
    abstract void remove();
    abstract void verifyIteratorState();
    void set(int v,CharInputTestArgType inputArgType){
       inputArgType.callListItrSet((OmniListIterator.OfChar)itr,v);
    }
    void set(int v){
      set(v,CharInputTestArgType.ARRAY_TYPE);
    }
    void add(int v){
      add(v,CharInputTestArgType.ARRAY_TYPE);
    }
    boolean hasNext(){
      return itr.hasNext();
    }
    boolean hasPrevious(){
      return ((OmniListIterator.OfChar)itr).hasPrevious();
    }
    int nextIndex(){
      return ((OmniListIterator.OfChar)itr).nextIndex();
    }
    int previousIndex(){
      return ((OmniListIterator.OfChar)itr).previousIndex();
    }
  }
  abstract static class SequenceVerificationItr{
    abstract void verifyLiteralIndexAndIterate(char val);
    abstract void verifyIndexAndIterate(CharInputTestArgType inputArgType,int val);
    abstract SequenceVerificationItr getOffset(int i);
    abstract SequenceVerificationItr skip(int i);
    abstract SequenceVerificationItr verifyPostAlloc(int expectedVal);
    abstract SequenceVerificationItr verifyParentPostAlloc();
    abstract SequenceVerificationItr verifyRootPostAlloc();
    abstract SequenceVerificationItr verifyNaturalAscending(int v,CharInputTestArgType inputArgType,int length);
    public abstract boolean equals(Object val);
    SequenceVerificationItr verifyAscending(int v,CharInputTestArgType inputArgType,int length){
      for(int i=0;i<length;++i,++v){
        verifyIndexAndIterate(inputArgType,v);
      }
      return this;
    }
    void reverseAndVerifyIndex(CharInputTestArgType inputArgType,int val){
      throw new UnsupportedOperationException();
    }
    void verifyIndexAndIterate(int val){
      verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,val);
    }
    SequenceVerificationItr verifyNaturalAscending(int length)
    {
       return verifyNaturalAscending(0,CharInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyNaturalAscending(int v,int length)
    {
       return verifyNaturalAscending(v,CharInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyNaturalAscending(CharInputTestArgType inputArgType,int length){
      return verifyNaturalAscending(0,inputArgType,length);
    }
    SequenceVerificationItr verifyAscending(int length){
      return verifyAscending(0,CharInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyAscending(int v,int length){
      return verifyAscending(v,CharInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyAscending(CharInputTestArgType inputArgType,int length){
      return verifyAscending(0,inputArgType,length);
    }
    SequenceVerificationItr verifyDescending(int length){
      return verifyDescending(length,CharInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyDescending(int v,int length)
    {
       return verifyDescending(v,CharInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyDescending(int v,CharInputTestArgType inputArgType,int length)
    {
      for(int i=0;i<length;++i){
        verifyIndexAndIterate(inputArgType,--v);
      }
      return this;
    }
    SequenceVerificationItr verifyDescending(CharInputTestArgType inputArgType,int length){
      return verifyDescending(length,inputArgType,length);
    }
    SequenceVerificationItr verifyNearBeginningInsertion(int length){
      return verifyNearBeginningInsertion(CharInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyNearBeginningInsertion(CharInputTestArgType inputArgType,final int length){
      switch(length&3)
      {
        case 0:
        {
          skip(length>>2);
          SequenceVerificationItr loItr=getOffset(0);
          for(int lo=length-1;lo>=0;lo-=4)
          {
            loItr.reverseAndVerifyIndex(inputArgType,lo);
          }
          for(int hi=length-2,i=0;hi>=0;--hi){
            verifyIndexAndIterate(inputArgType,hi);
            if(++i==3)
            {
              i=0;
              --hi;
            }
          }
          break;
        }  
        case 1:
        {
          skip(length>>2);
          SequenceVerificationItr loItr=getOffset(0);
          for(int lo=length-2;lo>=0;lo-=4)
          {
            loItr.reverseAndVerifyIndex(inputArgType,lo);
          }
          for(int hi=length-1,i=2;hi>=0;--hi){
            verifyIndexAndIterate(inputArgType,hi);
            if(++i==3)
            {
              i=0;
              --hi;
            }
          }
          break;
        }  
        case 2:
        {
          skip(length>>2);
          SequenceVerificationItr loItr=getOffset(0);
          for(int lo=length-3;lo>=0;lo-=4)
          {
            loItr.reverseAndVerifyIndex(inputArgType,lo);
          }
          for(int hi=length-1,i=1;hi>=0;--hi){
            verifyIndexAndIterate(inputArgType,hi);
            if(++i==3)
            {
              i=0;
              --hi;
            }
          }
          break;
        }  
        default:
        {
          skip(length>>2);
          SequenceVerificationItr loItr=getOffset(0);
          for(int lo=length-4;lo>=0;lo-=4)
          {
            loItr.reverseAndVerifyIndex(inputArgType,lo);
          }
          for(int hi=length-1,i=0;hi>=0;--hi){
            verifyIndexAndIterate(inputArgType,hi);
            if(++i==3)
            {
              i=0;
              --hi;
            }
          }
          break;
        }  
      }
      return this;
    }
    SequenceVerificationItr verifyNearEndInsertion(int length){
      return verifyNearEndInsertion(CharInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyNearEndInsertion(CharInputTestArgType inputArgType,final int length){
      switch(length&3)
      {
        case 0:
        {
          skip(length-(length>>2));
          SequenceVerificationItr loItr=getOffset(0);
          for(int lo=length-2,i=0;lo>=0;--lo)
          {
            loItr.reverseAndVerifyIndex(inputArgType,lo);
            if(++i==3)
            {
              i=0;
              --lo;
            }
          }
          for(int hi=length-1;hi>=0;hi-=4){
            verifyIndexAndIterate(inputArgType,hi);
          }
          break;
        }  
        case 1:
        {
          skip(length-(length>>2));
          SequenceVerificationItr loItr=getOffset(0);
          for(int lo=length-1,i=2;lo>=0;--lo)
          {
            loItr.reverseAndVerifyIndex(inputArgType,lo);
            if(++i==3)
            {
              i=0;
              --lo;
            }
          }
          for(int hi=length-2;hi>=0;hi-=4){
            verifyIndexAndIterate(inputArgType,hi);
          }
          break;
        }  
        case 2:
        {
          skip(length-(length>>2));
          SequenceVerificationItr loItr=getOffset(0);
          for(int lo=length-1,i=1;lo>=0;--lo)
          {
            loItr.reverseAndVerifyIndex(inputArgType,lo);
            if(++i==3)
            {
              i=0;
              --lo;
            }
          }
          for(int hi=length-3;hi>=0;hi-=4){
            verifyIndexAndIterate(inputArgType,hi);
          }
          break;
        }  
        default:
        {
          skip(length-(length>>2));
          SequenceVerificationItr loItr=getOffset(0);
          for(int lo=length-1,i=0;lo>=0;--lo)
          {
            loItr.reverseAndVerifyIndex(inputArgType,lo);
            if(++i==3)
            {
              i=0;
              --lo;
            }
          }
          for(int hi=length-4;hi>=0;hi-=4){
            verifyIndexAndIterate(inputArgType,hi);
          }
          break;
        }  
      }
      return this;
    }
    SequenceVerificationItr verifyMidPointInsertion(int length){
      return verifyMidPointInsertion(CharInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyMidPointInsertion(CharInputTestArgType inputArgType,final int length){
      if((length&1)==0)
      {
        skip((length>>1));
        SequenceVerificationItr loItr=getOffset(0);
        for(int lo=length-1;lo>=0;lo-=2)
        {
          loItr.reverseAndVerifyIndex(inputArgType,lo);
        }
        for(int hi=length-2;hi>=0;hi-=2){
          verifyIndexAndIterate(inputArgType,hi);
        }
      }
      else
      {
        skip((length>>1));
        SequenceVerificationItr loItr=getOffset(0);
        for(int lo=length-2;lo>=0;lo-=2)
        {
          loItr.reverseAndVerifyIndex(inputArgType,lo);
        }
        for(int hi=length-1;hi>=0;hi-=2){
          verifyIndexAndIterate(inputArgType,hi);
        }
      }
      return this;
    }
    SequenceVerificationItr verifyIllegalAdd(){
      verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,0);
      return this;
    }
    SequenceVerificationItr verifyPostAlloc(){
      return verifyPostAlloc(PreModScenario.NoMod);
    }
    SequenceVerificationItr verifyPostAlloc(PreModScenario preModScenario){
      if(preModScenario==PreModScenario.ModSeq){verifyIllegalAdd();}
      verifyParentPostAlloc();
      if(preModScenario==PreModScenario.ModParent){verifyIllegalAdd();}
      verifyRootPostAlloc();
      if(preModScenario==PreModScenario.ModRoot){verifyIllegalAdd();}
      return this;
    }
  }
  static enum PreModScenario{
    ModSeq(ConcurrentModificationException.class,false,true),
    ModParent(ConcurrentModificationException.class,true,false),
    ModRoot(ConcurrentModificationException.class,true,false),
    NoMod(null,true,true);
    final Class<? extends Throwable> expectedException;
    final boolean appliesToSubList;
    final boolean appliesToRootItr;
    PreModScenario(Class<? extends Throwable> expectedException,boolean appliesToSubList,boolean appliesToRootItr){
      this.expectedException=expectedException;
      this.appliesToSubList=appliesToSubList;
      this.appliesToRootItr=appliesToRootItr;
    }
  }
  static enum ItrType{
    Itr,
    ListItr,
    DescendingItr;
  }
  static enum SequenceLocation{
    BEGINNING(null,true),
    NEARBEGINNING(null,false),
    MIDDLE(null,false),
    NEAREND(null,false),
    END(null,false),
    IOBLO(IndexOutOfBoundsException.class,true),
    IOBHI(IndexOutOfBoundsException.class,true);
    final Class<? extends Throwable> expectedException;
    final boolean validForEmpty;
    SequenceLocation(Class<? extends Throwable> expectedException,boolean validForEmpty){
      this.expectedException=expectedException;
      this.validForEmpty=validForEmpty;
    }
  }
  static enum ListItrSetScenario{
    HappyPath(null,PreModScenario.NoMod),
    ModSeq(ConcurrentModificationException.class,PreModScenario.ModSeq),
    ModParent(ConcurrentModificationException.class,PreModScenario.ModParent),
    ModRoot(ConcurrentModificationException.class,PreModScenario.ModRoot),
    ThrowISE(IllegalStateException.class,PreModScenario.NoMod),
    PostAddThrowISE(IllegalStateException.class,PreModScenario.NoMod),
    PostRemoveThrowISE(IllegalStateException.class,PreModScenario.NoMod),
    ThrowISESupercedesModRootCME(IllegalStateException.class,PreModScenario.ModRoot),
    ThrowISESupercedesModParentCME(IllegalStateException.class,PreModScenario.ModParent),
    ThrowISESupercedesModSeqCME(IllegalStateException.class,PreModScenario.ModSeq),
    PostAddThrowISESupercedesModRootCME(IllegalStateException.class,PreModScenario.ModRoot),
    PostAddThrowISESupercedesModParentCME(IllegalStateException.class,PreModScenario.ModParent),
    PostAddThrowISESupercedesModSeqCME(IllegalStateException.class,PreModScenario.ModSeq),
    PostRemoveThrowISESupercedesModRootCME(IllegalStateException.class,PreModScenario.ModRoot),
    PostRemoveThrowISESupercedesModParentCME(IllegalStateException.class,PreModScenario.ModParent),
    PostRemoveThrowISESupercedesModSeqCME(IllegalStateException.class,PreModScenario.ModSeq);
    final Class<? extends Throwable> expectedException;
    final PreModScenario preModScenario;
    ListItrSetScenario(Class<? extends Throwable> expectedException,PreModScenario preModScenario){
      this.expectedException=expectedException;
      this.preModScenario=preModScenario;
    }
  }
  static enum ItrRemoveScenario{
    PostNext(null,false,true),
    PostPrevious(null,false,false),
    PostInit(IllegalStateException.class,true,true),
    PostAdd(IllegalStateException.class,true,false),
    PostRemove(IllegalStateException.class,false,true);
    final Class<? extends Throwable> expectedException;
    final boolean validWithEmptySeq;
    final boolean validWithForwardItr;
    ItrRemoveScenario(Class<? extends Throwable> expectedException,boolean validWithEmptySeq,boolean validWithForwardItr){
      this.expectedException=expectedException;
      this.validWithEmptySeq=validWithEmptySeq;
      this.validWithForwardItr=validWithForwardItr;
    }
  }
  static enum IterationScenario{
    NoMod(NoSuchElementException.class,PreModScenario.NoMod,false),
    ModSeq(ConcurrentModificationException.class,PreModScenario.ModSeq,false),
    ModParent(ConcurrentModificationException.class,PreModScenario.ModParent,false),
    ModRoot(ConcurrentModificationException.class,PreModScenario.ModRoot,false),
    ModSeqSupercedesThrowNSEE(ConcurrentModificationException.class,PreModScenario.ModSeq,true),
    ModParentSupercedesThrowNSEE(ConcurrentModificationException.class,PreModScenario.ModParent,true),
    ModRootSupercedesThrowNSEE(ConcurrentModificationException.class,PreModScenario.ModRoot,true);
    final Class<? extends Throwable> expectedException;
    final PreModScenario preModScenario;
    final boolean validWithEmptySeq;
    IterationScenario(Class<? extends Throwable> expectedException,PreModScenario preModScenario,boolean validWithEmptySeq){
      this.expectedException=expectedException;
      this.preModScenario=preModScenario;
      this.validWithEmptySeq=validWithEmptySeq;
    }
  }
  static enum CheckedType{
    CHECKED(true),
    UNCHECKED(false);
    final boolean checked;
    CheckedType(boolean checked){
      this.checked=checked;
    }
  }
  static enum MonitoredRemoveIfPredicateGen
  {
    RemoveFirst(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          private final char matchVal=TypeConversionUtil.convertTochar(0);
          @Override boolean testImpl(char val){
            return matchVal==val;
          }
        };
      }
    },
    RemoveLast(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          private final char matchVal=TypeConversionUtil.convertTochar(numExpectedCalls-1);
          @Override boolean testImpl(char val){
            return matchVal==val;
          }
        };
      }
    },
    RemoveFirstAndLast(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          private final char matchVal1=TypeConversionUtil.convertTochar(0);
          private final char matchVal2=TypeConversionUtil.convertTochar(numExpectedCalls-1);
          @Override boolean testImpl(char val){
            return matchVal1==val || matchVal2==val;
          }
        };
      }
    },
    RemoveAllButFirst(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          private final char matchVal=TypeConversionUtil.convertTochar(0);
          @Override boolean testImpl(char val){
            return matchVal!=val;
          }
        };
      }
    },
    RemoveAllButLast(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          private final char matchVal=TypeConversionUtil.convertTochar(numExpectedCalls-1);
          @Override boolean testImpl(char val){
            return matchVal!=val;
          }
        };
      }
    },
    RemoveAllButFirstAndLast(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          private final char matchVal1=TypeConversionUtil.convertTochar(0);
          private final char matchVal2=TypeConversionUtil.convertTochar(numExpectedCalls-1);
          @Override boolean testImpl(char val){
            return matchVal1!=val && matchVal2!=val;
          }
        };
      }
    },
    RemoveAll(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          @Override boolean testImpl(char val){
            return true;
          }
        };
      }
    },
    RemoveNone(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          @Override boolean testImpl(char val){
            return false;
          }
        };
      }
    },
    //TODO come up with a quicker way to test than using random sequences
    Random(null,true,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(char val){
            return rand.nextDouble()>=threshold;
          }
        };
      }
    },
    Throw(IndexOutOfBoundsException.class,true,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(char val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              throw new IndexOutOfBoundsException();
            }
            return rand.nextDouble()>=threshold;
          }
        };
      }
    },
    ModSeq(ConcurrentModificationException.class,true,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(char val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModSeq);
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ModParent(ConcurrentModificationException.class,false,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(char val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModParent);
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ModRoot(ConcurrentModificationException.class,false,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(char val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModRoot);
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ThrowModSeq(ConcurrentModificationException.class,true,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(char val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModSeq);
              throw new IndexOutOfBoundsException();
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ThrowModParent(ConcurrentModificationException.class,false,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(char val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModParent);
              throw new IndexOutOfBoundsException();
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ThrowModRoot(ConcurrentModificationException.class,false,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(char val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModRoot);
              throw new IndexOutOfBoundsException();
            }
            return rand.nextBoolean();
          }
        };
      }
    };
    final Class<? extends Throwable> expectedException;
    final boolean appliesToRoot;
    final boolean isRandomized;
    MonitoredRemoveIfPredicateGen(Class<? extends Throwable> expectedException,boolean appliesToRoot,boolean isRandomized){
      this.expectedException=expectedException;
      this.appliesToRoot=appliesToRoot;
      this.isRandomized=isRandomized;
    }
    abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractCharSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold);
  }
  static enum MonitoredFunctionGen{
    NoThrow(null,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator();
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor();
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file);
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file);
      }
    },
    Throw(IndexOutOfBoundsException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractCharSeqMonitor seqMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            super.applyAsChar(val);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ModItr(ConcurrentModificationException.class,false,false,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.iterateForward();
            itrMonitor.remove();
          }
        };
      }
    },
    ModSeq(ConcurrentModificationException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            var ret=super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return new Character[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
    },
    ModParent(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            var ret=super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return new Character[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
    },
    ModRoot(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            var ret=super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return new Character[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
    },
    ThrowModItr(ConcurrentModificationException.class,false,false,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.iterateForward();
            itrMonitor.remove();
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModSeq(ConcurrentModificationException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModParent(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModRoot(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    };
    final Class<? extends Throwable> expectedException;
    final boolean appliesToRoot;
    final boolean appliesToSubList;
    final boolean appliesToRootItr;
    MonitoredFunctionGen(Class<? extends Throwable> expectedException,boolean appliesToRoot,boolean appliesToSubList,boolean appliesToRootItr){
      this.expectedException=expectedException;
      this.appliesToRoot=appliesToRoot;
      this.appliesToSubList=appliesToSubList;
      this.appliesToRootItr=appliesToRootItr;
    }
    MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractCharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredConsumer getMonitoredConsumer(AbstractCharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    abstract MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor);
    MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractCharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
      throw new UnsupportedOperationException();
    }
    MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractCharSeqMonitor seqMonitor) throws IOException{
      throw new UnsupportedOperationException();
    }
  }
  static abstract class MonitoredComparator implements CharComparator
   ,Comparator
  {
    public abstract int compare(char val1, char val2);
    public int compare(Object val1,Object val2){
      return compare((char)val1,(char)val2);
    }
  }
  static class MonitoredUnaryOperator implements CharUnaryOperator
    ,UnaryOperator
  {
    ArrayList encounteredValues=new ArrayList();
    public char applyAsChar(char val){
      encounteredValues.add(val);
      return (char)(val+1);
    }
    public Object apply(Object val){
      return applyAsChar((char)val);
    }
  }
  static class MonitoredArrayConstructor
    implements IntFunction<Character[]>
  {
    int numCalls;
    @Override public Character[] apply(int arrSize){
      ++numCalls;
      return new Character[arrSize];
    }
  }
  static class MonitoredConsumer implements CharConsumer
    ,Consumer
  {
    ArrayList encounteredValues=new ArrayList();
    public void accept(char val){
      encounteredValues.add(val);
    }
    public void accept(Object val){
      accept((char)val);
    }
  }
  public static class ThrowingMonitoredConsumer extends MonitoredConsumer{
    public void accept(char val){
      super.accept(val);
      throw new IndexOutOfBoundsException();
    }
  }
  static abstract class MonitoredRemoveIfPredicate implements CharPredicate
    ,Predicate<Character>
  {
    final HashSet removedVals=new HashSet();
    int callCounter;
    int numRemoved;
    abstract boolean testImpl(char val);
    @Override public MonitoredRemoveIfPredicate negate()
    {
      //not worth implementing but must declare
      return null;
    }
    @Override public boolean test(char val)
    {
      ++callCounter;
      if(removedVals.contains(val))
      {
        ++numRemoved;
        return true;
      }
      if(testImpl(val))
      {
        ++numRemoved;
        removedVals.add(val);
        return true;
      }
      return false;
    }
    @Override public boolean test(Character val)
    {
      return test((char)val);
    }
  }
  static enum MonitoredComparatorGen{
    NoThrowAscending(null,true,true,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            return Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      /*
      @Override void assertReverseUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      */
    },
    NoThrowDescending(null,true,false,true,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            return -Character.compare(val1,val2);
          }
        };
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NullComparator(null,true,true,false,true){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void initReverseHelper(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertReverseUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      /*
      @Override void initReverseHelper(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      */
    },
    ThrowAIOB(IllegalArgumentException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      /*
      @Override void assertReverseUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      */
    },
    ThrowIOB(IndexOutOfBoundsException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      /*
      @Override void assertReverseUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      */
    },
    ModSeqAscending(ConcurrentModificationException.class,true,true,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      /*
      @Override void assertReverseUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      */
    },
    ModSeqDescending(ConcurrentModificationException.class,true,false,true,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return -Character.compare(val1,val2);
          }
        };
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      /*
      @Override void assertReverseUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      */
    },
    ModParentAscending(ConcurrentModificationException.class,false,true,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
      /*
      @Override void assertReverseUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
      */
    },
    ModParentDescending(ConcurrentModificationException.class,false,false,true,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return -Character.compare(val1,val2);
          }
        };
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModRootAscending(ConcurrentModificationException.class,false,true,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
      //@Override void assertReverseUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
      //  verifyItr.verifyIndexAndIterate(2);
      //  verifyItr.verifyIndexAndIterate(3);
      //  verifyItr.verifyPostAlloc(preModScenario);
      //}
    },
    ModRootDescending(ConcurrentModificationException.class,false,false,true,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return -Character.compare(val1,val2);
          }
        };
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModSeqThrowAIOB(ConcurrentModificationException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case ModParent:
          case ModRoot:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case ModParent:
          case ModRoot:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModSeqThrowIOB(ConcurrentModificationException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case ModParent:
          case ModRoot:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case ModParent:
          case ModRoot:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModParentThrowAIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModParentThrowIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModRootThrowAIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModRootThrowIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    };
    final Class<? extends Throwable> expectedException;
    final boolean appliesToRoot;
    final boolean ascending;
    final boolean descending;
    final boolean nullComparator;
    MonitoredComparatorGen(Class<? extends Throwable> expectedException,final boolean appliesToRoot,final boolean ascending,final boolean descending,boolean nullComparator){
      this.expectedException=expectedException;
      this.appliesToRoot=appliesToRoot;
      this.ascending=ascending;
      this.descending=descending;
      this.nullComparator=nullComparator;
    }
    abstract void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario);
    abstract MonitoredComparator getMonitoredComparator(AbstractCharSeqMonitor seqMonitor);
    void initReverseHelper(AbstractCharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
      throw new UnsupportedOperationException();
    }
    void assertUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
      throw new UnsupportedOperationException();
    }
    void assertReverseUnmodifiedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
      throw new UnsupportedOperationException();
    }
    void initHelper(AbstractCharSeqMonitor seqMonitor){
      seqMonitor.add(2);
      seqMonitor.add(3);
    }
    void init(AbstractCharSeqMonitor seqMonitor,int seqSize,PreModScenario preModScenario){
      if(seqSize>1){
        initHelper(seqMonitor);
      }else{
        seqMonitor.add(1);
      }
      seqMonitor.illegalAdd(preModScenario);
    }
    void initReverse(AbstractCharSeqMonitor seqMonitor,int seqSize,PreModScenario preModScenario){
      if(seqSize>1){
        initReverseHelper(seqMonitor);
      }else{
        seqMonitor.add(1);
      }
      seqMonitor.illegalAdd(preModScenario);
    }
    void assertUnmodified(AbstractCharSeqMonitor seqMonitor,int seqSize,PreModScenario preModScenario){
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      if(seqSize>1){
        assertUnmodifiedHelper(verifyItr,preModScenario);
      }else{
        verifyItr.verifyIndexAndIterate(1);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    }
    void assertReverseUnmodified(AbstractCharSeqMonitor seqMonitor,int seqSize,PreModScenario preModScenario){
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      if(seqSize>1){
        assertReverseUnmodifiedHelper(verifyItr,preModScenario);
      }else{
        verifyItr.verifyIndexAndIterate(1);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    }
    void assertSorted(AbstractCharSeqMonitor seqMonitor,int seqSize,PreModScenario preModScenario){
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      if(seqSize>1){
        assertSortedHelper(verifyItr,preModScenario);
      }else{
        verifyItr.verifyIndexAndIterate(1);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    }
    void assertReverseSorted(AbstractCharSeqMonitor seqMonitor,int seqSize,PreModScenario preModScenario){
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      if(seqSize>1){
        assertReverseSortedHelper(verifyItr,preModScenario);
      }else{
        verifyItr.verifyIndexAndIterate(1);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    }
  }
  static enum QueryTester
  {
    Booleannull(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(Boolean)(null));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(Boolean)(null));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Boolean)(null));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Boolean)(null));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Boolean)(null));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Boolean)(null));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Boolean)(null));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Boolean)(Boolean)(null));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Boolean)(Boolean)(null));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(Boolean)(null));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Boolean)(Boolean)(null));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Boolean)(Boolean)(null));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Boolean)(Boolean)(null));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Boolean)(Boolean)(null));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Boolean)(null));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Boolean)(null));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Boolean)(null));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Boolean)(null));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Boolean)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Boolean)(null));}
      @Override void setEqualsVal(char[] arr,int index){
        throw new UnsupportedOperationException();
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Bytenull(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(Byte)(null));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(Byte)(null));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Byte)(null));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Byte)(null));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Byte)(null));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Byte)(null));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Byte)(null));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Byte)(Byte)(null));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Byte)(Byte)(null));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(Byte)(null));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(Byte)(null));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(Byte)(null));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(Byte)(null));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(Byte)(null));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Byte)(null));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Byte)(null));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(null));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(null));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(null));}
      @Override void setEqualsVal(char[] arr,int index){
        throw new UnsupportedOperationException();
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Characternull(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(Character)(null));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(Character)(null));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Character)(null));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Character)(null));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Character)(null));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Character)(null));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Character)(null));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Character)(Character)(null));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Character)(Character)(null));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(Character)(null));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(Character)(null));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(Character)(null));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(Character)(null));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(Character)(null));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Character)(null));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Character)(null));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(null));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(null));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(null));}
      @Override void setEqualsVal(char[] arr,int index){
        throw new UnsupportedOperationException();
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Shortnull(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(Short)(null));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(Short)(null));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Short)(null));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Short)(null));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Short)(null));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Short)(null));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Short)(null));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Short)(Short)(null));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Short)(Short)(null));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(Short)(null));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(Short)(null));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(Short)(null));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(Short)(null));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(Short)(null));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Short)(null));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Short)(null));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(null));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(null));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(null));}
      @Override void setEqualsVal(char[] arr,int index){
        throw new UnsupportedOperationException();
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Integernull(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(Integer)(null));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(Integer)(null));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Integer)(null));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Integer)(null));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Integer)(null));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Integer)(null));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Integer)(null));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(Integer)(null));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(Integer)(null));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(Integer)(null));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(Integer)(null));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(Integer)(null));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(Integer)(null));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(Integer)(null));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(null));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(null));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(null));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(null));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(null));}
      @Override void setEqualsVal(char[] arr,int index){
        throw new UnsupportedOperationException();
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Longnull(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(Long)(null));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(Long)(null));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Long)(null));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Long)(null));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Long)(null));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Long)(null));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Long)(null));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(Long)(null));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(Long)(null));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(Long)(null));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(Long)(null));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(Long)(null));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(Long)(null));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(Long)(null));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(null));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(null));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(null));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(null));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(null));}
      @Override void setEqualsVal(char[] arr,int index){
        throw new UnsupportedOperationException();
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Floatnull(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(Float)(null));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(Float)(null));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Float)(null));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Float)(null));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Float)(null));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Float)(null));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Float)(null));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(Float)(null));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(Float)(null));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(Float)(null));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(Float)(null));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(Float)(null));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(Float)(null));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(Float)(null));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(null));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(null));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(null));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(null));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(null));}
      @Override void setEqualsVal(char[] arr,int index){
        throw new UnsupportedOperationException();
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Doublenull(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(Double)(null));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(Double)(null));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Double)(null));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Double)(null));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Double)(null));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Double)(null));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Double)(null));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(Double)(null));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(Double)(null));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(Double)(null));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(Double)(null));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(Double)(null));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(Double)(null));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(Double)(null));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(null));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(null));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(null));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(null));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(null));}
      @Override void setEqualsVal(char[] arr,int index){
        throw new UnsupportedOperationException();
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Objectnull(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(Object)(null));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(Object)(null));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Object)(null));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Object)(null));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Object)(null));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Object)(null));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(Object)(null));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(Object)(null));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.remove((Object)(Object)(null));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Object)(null));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Object)(null));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Object)(null));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(null));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(null));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.remove((Object)(null));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(null));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(null));}
      @Override void setEqualsVal(char[] arr,int index){
        throw new UnsupportedOperationException();
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Booleanfalse(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(boolean)(false));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(boolean)(false));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(false));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(boolean)(false));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(boolean)(false));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(boolean)(false));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(boolean)(false));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Boolean)(boolean)(false));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Boolean)(boolean)(false));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(false));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Boolean)(boolean)(false));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Boolean)(boolean)(false));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Boolean)(boolean)(false));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Boolean)(boolean)(false));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((boolean)(false));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((boolean)(false));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(false));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((boolean)(false));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((boolean)(false));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((boolean)(false));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((boolean)(false));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)TypeUtil.castToChar(false));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)true);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)TypeUtil.castToChar(false));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Booleantrue(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(boolean)(true));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(boolean)(true));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(true));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(boolean)(true));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(boolean)(true));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(boolean)(true));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(boolean)(true));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Boolean)(boolean)(true));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Boolean)(boolean)(true));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(true));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Boolean)(boolean)(true));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Boolean)(boolean)(true));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Boolean)(boolean)(true));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Boolean)(boolean)(true));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((boolean)(true));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((boolean)(true));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(true));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((boolean)(true));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((boolean)(true));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((boolean)(true));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((boolean)(true));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)TypeUtil.castToChar(true));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)TypeUtil.castToChar(true));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Byte0(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(byte)(0));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(byte)(0));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(0));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(byte)(0));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(byte)(0));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(byte)(0));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(byte)(0));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Byte)(byte)(0));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Byte)(byte)(0));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(0));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(byte)(0));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(byte)(0));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(byte)(0));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(byte)(0));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((byte)(0));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((byte)(0));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(0));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((byte)(0));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((byte)(0));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((byte)(0));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((byte)(0));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(0));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)true);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(0));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Bytepos1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(byte)(1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(byte)(1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(byte)(1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(byte)(1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(byte)(1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(byte)(1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Byte)(byte)(1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Byte)(byte)(1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(byte)(1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(byte)(1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(byte)(1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(byte)(1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((byte)(1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((byte)(1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((byte)(1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((byte)(1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((byte)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((byte)(1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Bytepos2(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(byte)(2));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(byte)(2));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(2));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(byte)(2));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(byte)(2));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(byte)(2));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(byte)(2));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Byte)(byte)(2));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Byte)(byte)(2));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(2));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(byte)(2));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(byte)(2));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(byte)(2));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(byte)(2));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((byte)(2));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((byte)(2));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((byte)(2));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((byte)(2));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((byte)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((byte)(2));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(2));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(2));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Byteneg1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(byte)(-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(byte)(-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(byte)(-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(byte)(-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(byte)(-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(byte)(-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Byte)(byte)(-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Byte)(byte)(-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(byte)(-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(byte)(-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(byte)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(byte)(-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((byte)(-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((byte)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((byte)(-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((byte)(-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((byte)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((byte)(-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Character0(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(char)(0));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(char)(0));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(0));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(char)(0));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(char)(0));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(char)(0));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(char)(0));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Character)(char)(0));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Character)(char)(0));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(0));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(char)(0));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(char)(0));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(char)(0));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(char)(0));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((char)(0));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((char)(0));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(0));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((char)(0));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((char)(0));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((char)(0));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((char)(0));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(0));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)true);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(0));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Characterpos1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(char)(1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(char)(1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(char)(1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(char)(1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(char)(1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(char)(1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Character)(char)(1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Character)(char)(1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(char)(1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(char)(1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(char)(1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(char)(1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((char)(1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((char)(1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((char)(1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((char)(1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((char)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((char)(1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Characterpos2(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(char)(2));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(char)(2));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(2));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(char)(2));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(char)(2));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(char)(2));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(char)(2));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Character)(char)(2));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Character)(char)(2));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(2));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(char)(2));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(char)(2));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(char)(2));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(char)(2));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((char)(2));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((char)(2));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((char)(2));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((char)(2));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((char)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((char)(2));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(2));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(2));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    CharacterMAX_BYTE_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Character)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Character)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(char)(((char)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((char)(((char)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((char)(((char)Byte.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((char)Byte.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((char)Byte.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    CharacterMAX_SHORT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Character)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Character)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(char)(((char)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((char)(((char)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((char)(((char)Short.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((char)Short.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((char)Short.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Short0(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(short)(0));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(short)(0));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(0));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(0));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(0));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(0));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(0));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Short)(short)(0));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Short)(short)(0));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(0));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(0));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(0));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(0));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(0));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((short)(0));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((short)(0));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(0));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(0));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(0));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(0));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(0));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(0));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)true);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(0));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Shortpos1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(short)(1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(short)(1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Short)(short)(1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Short)(short)(1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((short)(1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((short)(1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Shortpos2(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(short)(2));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(short)(2));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(2));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(2));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(2));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(2));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(2));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Short)(short)(2));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Short)(short)(2));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(2));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(2));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(2));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(2));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(2));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((short)(2));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((short)(2));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(2));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(2));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(2));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(2));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(2));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Shortneg1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(short)(-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(short)(-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Short)(short)(-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Short)(short)(-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((short)(-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((short)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    ShortMAX_BYTE_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Short)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Short)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(((short)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(((short)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(((short)Byte.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((short)Byte.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((short)Byte.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    ShortMIN_BYTE_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Short)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Short)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(((short)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(((short)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(((short)Byte.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((short)Byte.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((short)Byte.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Integer0(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(0));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(0));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(0));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(0));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(0));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(0));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(0));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(0));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(0));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(0));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(0));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(0));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(0));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(0));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(0));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(0));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(0));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(0));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(0));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(0));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(0));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(0));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)true);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(0));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Integerpos1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Integerpos2(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(2));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(2));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(2));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(2));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(2));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(2));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(2));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(2));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(2));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(2));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(2));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(2));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(2));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(2));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(2));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(2));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(2));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(2));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(2));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(2));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(2));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Integerneg1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMAX_BYTE_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(((int)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(((int)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(((int)Byte.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((int)Byte.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((int)Byte.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMIN_BYTE_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(((int)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(((int)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(((int)Byte.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((int)Byte.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((int)Byte.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMAX_SHORT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(((int)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(((int)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(((int)Short.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((int)Short.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((int)Short.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMIN_SHORT_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(((int)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(((int)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(((int)Short.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((int)Short.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((int)Short.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMAX_CHAR_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(((int)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(((int)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(((int)Character.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((int)Character.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((int)Character.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMAX_SAFE_INT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(TypeUtil.MAX_SAFE_INT+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(TypeUtil.MAX_SAFE_INT+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(TypeUtil.MAX_SAFE_INT+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMIN_SAFE_INT_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(TypeUtil.MIN_SAFE_INT-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(TypeUtil.MIN_SAFE_INT-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(TypeUtil.MIN_SAFE_INT-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Long0(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(0));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(0));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(0));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(0));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(0));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(0));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(0));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(0));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(0));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(0));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(0));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(0));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(0));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(0));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(0));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(0));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(0));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(0));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(0));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(0));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(0));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(0));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)true);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(0));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Longpos1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Longpos2(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(2));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(2));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(2));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(2));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(2));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(2));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(2));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(2));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(2));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(2));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(2));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(2));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(2));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(2));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(2));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(2));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(2));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(2));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(2));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(2));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(2));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Longneg1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_BYTE_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Byte.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)Byte.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)Byte.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMIN_BYTE_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Byte.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)Byte.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)Byte.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_SHORT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Short.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)Short.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)Short.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMIN_SHORT_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Short.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)Short.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)Short.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_CHAR_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Character.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)Character.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)Character.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_SAFE_INT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMIN_SAFE_INT_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_INT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Integer.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)Integer.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)Integer.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMIN_INT_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Integer.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)Integer.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)Integer.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_SAFE_LONG_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMIN_SAFE_LONG_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Floatpos0(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(0.0F));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(0.0F));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(0.0F));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(0.0F));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(0.0F));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(0.0F));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(0.0F));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(0.0F));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(0.0F));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(0.0F));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(0.0F));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(0.0F));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(0.0F));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(0.0F));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(0.0F));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(0.0F));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(0.0F));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(0.0F));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(0.0F));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(0.0F));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(0.0F));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(0.0F));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)true);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(0.0F));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Floatneg0(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(-0.0F));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(-0.0F));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-0.0F));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(-0.0F));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(-0.0F));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(-0.0F));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(-0.0F));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(-0.0F));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(-0.0F));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-0.0F));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(-0.0F));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(-0.0F));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(-0.0F));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(-0.0F));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(-0.0F));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(-0.0F));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-0.0F));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(-0.0F));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(-0.0F));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(-0.0F));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(-0.0F));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(-0.0F));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)true);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(-0.0F));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Floatpos1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Floatpos2(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(2));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(2));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(2));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(2));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(2));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(2));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(2));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(2));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(2));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(2));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(2));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(2));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(2));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(2));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(2));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(2));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(2));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(2));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(2));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(2));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(2));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Floatneg1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_BYTE_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Byte.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((float)Byte.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((float)Byte.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMIN_BYTE_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Byte.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((float)Byte.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((float)Byte.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_SHORT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Short.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((float)Short.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((float)Short.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMIN_SHORT_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Short.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((float)Short.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((float)Short.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_CHAR_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Character.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((float)Character.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((float)Character.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_INT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Integer.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((float)Integer.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((float)Integer.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMIN_INT_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Integer.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((float)Integer.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((float)Integer.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_LONG_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Long.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Long.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Long.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((float)Long.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((float)Long.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMIN_LONG_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Long.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Long.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Long.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((float)Long.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((float)Long.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMIN_FLOAT_VALUE(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(Float.MIN_VALUE));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(Float.MIN_VALUE));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(Float.MIN_VALUE));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(Float.MIN_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(Float.MIN_VALUE));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(Float.MIN_VALUE));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(Float.MIN_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(Float.MIN_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(Float.MIN_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(Float.MIN_VALUE));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(Float.MIN_VALUE));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(Float.MIN_VALUE));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_FLOAT_VALUE(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(Float.MAX_VALUE));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(Float.MAX_VALUE));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(Float.MAX_VALUE));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(Float.MAX_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(Float.MAX_VALUE));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(Float.MAX_VALUE));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(Float.MAX_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(Float.MAX_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(Float.MAX_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(Float.MAX_VALUE));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(Float.MAX_VALUE));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(Float.MAX_VALUE));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatNaN(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(float)(Float.NaN));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(float)(Float.NaN));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.NaN));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(Float.NaN));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(Float.NaN));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(Float.NaN));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(Float.NaN));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Float)(float)(Float.NaN));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Float)(float)(Float.NaN));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.NaN));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(Float.NaN));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(Float.NaN));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(Float.NaN));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(Float.NaN));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((float)(Float.NaN));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((float)(Float.NaN));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.NaN));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(Float.NaN));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(Float.NaN));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(Float.NaN));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(Float.NaN));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(Float.NaN));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(Float.NaN));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Doublepos0(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(0.0D));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(0.0D));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(0.0D));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(0.0D));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(0.0D));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(0.0D));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(0.0D));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(0.0D));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(0.0D));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(0.0D));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(0.0D));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(0.0D));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(0.0D));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(0.0D));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(0.0D));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(0.0D));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(0.0D));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(0.0D));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(0.0D));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(0.0D));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(0.0D));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(0.0D));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)true);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(0.0D));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Doubleneg0(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(-0.0D));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(-0.0D));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-0.0D));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(-0.0D));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(-0.0D));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(-0.0D));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(-0.0D));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(-0.0D));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(-0.0D));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-0.0D));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(-0.0D));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(-0.0D));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(-0.0D));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(-0.0D));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(-0.0D));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(-0.0D));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-0.0D));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(-0.0D));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(-0.0D));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(-0.0D));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(-0.0D));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(-0.0D));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)true);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(-0.0D));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Doublepos1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Doublepos2(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(2));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(2));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(2));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(2));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(2));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(2));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(2));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(2));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(2));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(2));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(2));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(2));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(2));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(2));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(2));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(2));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(2));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(2));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(2));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(2));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(2));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Doubleneg1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_BYTE_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Byte.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)Byte.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)Byte.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_BYTE_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Byte.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)Byte.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)Byte.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_SHORT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Short.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)Short.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)Short.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_SHORT_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Short.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)Short.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)Short.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_CHAR_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Character.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)Character.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)Character.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_SAFE_INT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_SAFE_INT_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_INT_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Integer.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)Integer.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)Integer.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_INT_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Integer.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)Integer.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)Integer.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_LONG_PLUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Long.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Long.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Long.MAX_VALUE)+1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)Long.MAX_VALUE)+1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)Long.MAX_VALUE)+1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_LONG_MINUS1(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Long.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Long.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Long.MIN_VALUE)-1));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(((double)Long.MIN_VALUE)-1));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(((double)Long.MIN_VALUE)-1));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_FLOAT_VALUE(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(Float.MIN_VALUE));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(Float.MIN_VALUE));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(Float.MIN_VALUE));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(Float.MIN_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(Float.MIN_VALUE));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(Float.MIN_VALUE));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(Float.MIN_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(Float.MIN_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(Float.MIN_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(Float.MIN_VALUE));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(Float.MIN_VALUE));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(Float.MIN_VALUE));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_FLOAT_VALUE(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(Float.MAX_VALUE));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(Float.MAX_VALUE));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(Float.MAX_VALUE));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(Float.MAX_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(Float.MAX_VALUE));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(Float.MAX_VALUE));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(Float.MAX_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(Float.MAX_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(Float.MAX_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(Float.MAX_VALUE));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(Float.MAX_VALUE));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(Float.MAX_VALUE));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_DOUBLE_VALUE(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(Double.MIN_VALUE));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(Double.MIN_VALUE));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(Double.MIN_VALUE));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(Double.MIN_VALUE));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(Double.MIN_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(Double.MIN_VALUE));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(Double.MIN_VALUE));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(Double.MIN_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(Double.MIN_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(Double.MIN_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(Double.MIN_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(Double.MIN_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(Double.MIN_VALUE));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(Double.MIN_VALUE));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(Double.MIN_VALUE));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_DOUBLE_VALUE(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(Double.MAX_VALUE));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(Double.MAX_VALUE));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(Double.MAX_VALUE));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(Double.MAX_VALUE));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(Double.MAX_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(Double.MAX_VALUE));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(Double.MAX_VALUE));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(Double.MAX_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(Double.MAX_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(Double.MAX_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(Double.MAX_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(Double.MAX_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(Double.MAX_VALUE));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(Double.MAX_VALUE));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(Double.MAX_VALUE));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleNaN(false){
      @Override boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Object)(double)(Double.NaN));}
      @Override boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Object)(double)(Double.NaN));}
      @Override boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.NaN));}
      @Override boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(Double.NaN));}
      @Override int invokesearchObject(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(Double.NaN));}
      @Override int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(Double.NaN));}
      @Override int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(Double.NaN));}
      @Override boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((Double)(double)(Double.NaN));}
      @Override boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((Double)(double)(Double.NaN));}
      @Override boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.NaN));}
      @Override boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(Double.NaN));
      }
      @Override int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(Double.NaN));}
      @Override int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(Double.NaN));}
      @Override int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(Double.NaN));}
      @Override boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeFirstOccurrence((double)(Double.NaN));}
      @Override boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.removeLastOccurrence((double)(Double.NaN));}
      @Override boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.NaN));}
      @Override boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(Double.NaN));
      }
      @Override int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(Double.NaN));}
      @Override int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(Double.NaN));}
      @Override int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(Double.NaN));}
      @Override void setEqualsVal(char[] arr,int index){
        arr[index]=((char)(Double.NaN));
      }
      @Override void setNotEqualsVal(char[] arr,int index){
        arr[index]=TypeUtil.castToChar((boolean)false);
      }
      @Override void addEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((char)(Double.NaN));
      }
      @Override void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    ;
      final boolean isObjectNonNull;
      QueryTester(boolean isObjectNonNull){
        this.isObjectNonNull=isObjectNonNull;
      }
      boolean invokeremoveFirstOccurrence(AbstractCharSeqMonitor seqMonitor,QueryCastType queryCastType){
        switch(queryCastType){
          case Unboxed:
            return invokeremoveFirstOccurrenceUnboxed(seqMonitor);
          case ToBoxed:
            return invokeremoveFirstOccurrenceBoxed(seqMonitor);
          case ToObject:
            return invokeremoveFirstOccurrenceObject(seqMonitor);
          default:
            throw new Error("Unknown queryCastType "+queryCastType);
        }
      }
      boolean invokeremoveLastOccurrence(AbstractCharSeqMonitor seqMonitor,QueryCastType queryCastType){
        switch(queryCastType){
          case Unboxed:
            return invokeremoveLastOccurrenceUnboxed(seqMonitor);
          case ToBoxed:
            return invokeremoveLastOccurrenceBoxed(seqMonitor);
          case ToObject:
            return invokeremoveLastOccurrenceObject(seqMonitor);
          default:
            throw new Error("Unknown queryCastType "+queryCastType);
        }
      }
      boolean invokecontains(AbstractCharSeqMonitor seqMonitor,QueryCastType queryCastType){
        switch(queryCastType){
          case Unboxed:
            return invokecontainsUnboxed(seqMonitor);
          case ToBoxed:
            return invokecontainsBoxed(seqMonitor);
          case ToObject:
            return invokecontainsObject(seqMonitor);
          default:
            throw new Error("Unknown queryCastType "+queryCastType);
        }
      }
      boolean invokeremoveVal(AbstractCharSeqMonitor seqMonitor,QueryCastType queryCastType){
        switch(queryCastType){
          case Unboxed:
            return invokeremoveValUnboxed(seqMonitor);
          case ToBoxed:
            return invokeremoveValBoxed(seqMonitor);
          case ToObject:
            return invokeremoveValObject(seqMonitor);
          default:
            throw new Error("Unknown queryCastType "+queryCastType);
        }
      }
      int invokesearch(AbstractCharSeqMonitor seqMonitor,QueryCastType queryCastType){
        switch(queryCastType){
          case Unboxed:
            return invokesearchUnboxed(seqMonitor);
          case ToBoxed:
            return invokesearchBoxed(seqMonitor);
          case ToObject:
            return invokesearchObject(seqMonitor);
          default:
            throw new Error("Unknown queryCastType "+queryCastType);
        }
      }
      int invokelastIndexOf(AbstractCharSeqMonitor seqMonitor,QueryCastType queryCastType){
        switch(queryCastType){
          case Unboxed:
            return invokelastIndexOfUnboxed(seqMonitor);
          case ToBoxed:
            return invokelastIndexOfBoxed(seqMonitor);
          case ToObject:
            return invokelastIndexOfObject(seqMonitor);
          default:
            throw new Error("Unknown queryCastType "+queryCastType);
        }
      }
      int invokeindexOf(AbstractCharSeqMonitor seqMonitor,QueryCastType queryCastType){
        switch(queryCastType){
          case Unboxed:
            return invokeindexOfUnboxed(seqMonitor);
          case ToBoxed:
            return invokeindexOfBoxed(seqMonitor);
          case ToObject:
            return invokeindexOfObject(seqMonitor);
          default:
            throw new Error("Unknown queryCastType "+queryCastType);
        }
      }
      abstract boolean invokeremoveFirstOccurrenceObject(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokeremoveFirstOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokeremoveFirstOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokeremoveLastOccurrenceObject(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokeremoveLastOccurrenceBoxed(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokeremoveLastOccurrenceUnboxed(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokecontainsObject(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokecontainsBoxed(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokecontainsUnboxed(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokeremoveValObject(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokeremoveValBoxed(AbstractCharSeqMonitor seqMonitor);
      abstract boolean invokeremoveValUnboxed(AbstractCharSeqMonitor seqMonitor);
      abstract int invokeindexOfObject(AbstractCharSeqMonitor seqMonitor);
      abstract int invokeindexOfBoxed(AbstractCharSeqMonitor seqMonitor);
      abstract int invokeindexOfUnboxed(AbstractCharSeqMonitor seqMonitor);
      abstract int invokelastIndexOfObject(AbstractCharSeqMonitor seqMonitor);
      abstract int invokelastIndexOfBoxed(AbstractCharSeqMonitor seqMonitor);
      abstract int invokelastIndexOfUnboxed(AbstractCharSeqMonitor seqMonitor);
      abstract int invokesearchObject(AbstractCharSeqMonitor seqMonitor);
      abstract int invokesearchBoxed(AbstractCharSeqMonitor seqMonitor);
      abstract int invokesearchUnboxed(AbstractCharSeqMonitor seqMonitor);
      abstract void addEqualsVal(AbstractCharSeqMonitor seqMonitor);
      abstract void addNotEqualsVal(AbstractCharSeqMonitor seqMonitor);
      abstract void setEqualsVal(char[] arr,int index);
      abstract void setNotEqualsVal(char[] arr,int index);
      void initDoesNotContain(AbstractCharSeqMonitor seqMonitor,int seqSize){
        for(int i=0;i<seqSize;++i){
          addNotEqualsVal(seqMonitor);
        }
      }
      int initContainsEnd(AbstractCharSeqMonitor seqMonitor,int seqSize,boolean forwardIteration){
        Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
        for(int i=0;i<seqSize-1;++i){
          addNotEqualsVal(seqMonitor);
        }
        addEqualsVal(seqMonitor);
        if(forwardIteration)
        {
          return seqMonitor.expectedSeqSize;
        }
        else
        {
          return 1;
        }
      }
      int initContainsMiddle(AbstractCharSeqMonitor seqMonitor,int seqSize,boolean forwardIteration){
        Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
        for(int i=0,bound=seqSize>>1;i<bound;++i){
          addNotEqualsVal(seqMonitor);
        }
        addEqualsVal(seqMonitor);
        for(int i=(seqSize>>1)+1;i<seqSize;++i){
          addNotEqualsVal(seqMonitor);
        }
        if(forwardIteration)
        {
          return (seqSize>>1)+1;
        }
        else
        {
          return seqMonitor.expectedSeqSize-(seqSize>>1);
        }
      }
      int initContainsNearBeginning(AbstractCharSeqMonitor seqMonitor,int seqSize,boolean forwardIteration){
        Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
        for(int i=0,bound=seqSize/4;i<bound;++i){
          addNotEqualsVal(seqMonitor);
        }
        addEqualsVal(seqMonitor);
        for(int i=(seqSize>>2)+1;i<seqSize;++i){
          addNotEqualsVal(seqMonitor);
        }
        if(forwardIteration)
        {
          return (seqSize>>2)+1;
        }
        else
        {
          return seqMonitor.expectedSeqSize-((seqSize>>2));
        }
      }
      int initContainsNearEnd(AbstractCharSeqMonitor seqMonitor,int seqSize,boolean forwardIteration){
        Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
        for(int i=0,bound=(seqSize>>2)*3;i<bound;++i){
          addNotEqualsVal(seqMonitor);
        }
        addEqualsVal(seqMonitor);
        for(int i=((seqSize>>2)*3)+1;i<seqSize;++i){
          addNotEqualsVal(seqMonitor);
        }
        if(forwardIteration)
        {
          return ((seqSize>>2)*3)+1;
        }
        else
        {
          return (seqMonitor.expectedSeqSize)-(seqSize>>2)*3;
        }
      }
      int initContainsBeginning(AbstractCharSeqMonitor seqMonitor,int seqSize,boolean forwardIteration){
        addEqualsVal(seqMonitor);
        for(int i=1;i<seqSize;++i){
          addNotEqualsVal(seqMonitor);
        }
        if(forwardIteration)
        {
          return 1;
        }
        else
        {
          return seqMonitor.expectedSeqSize;
        }
      }
  }
}
