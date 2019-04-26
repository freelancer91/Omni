package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import omni.impl.FunctionCallType;
import omni.impl.LongOutputTestArgType;
import omni.impl.LongInputTestArgType;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import omni.impl.MonitoredObjectInputStream;
import omni.impl.MonitoredObjectOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import omni.function.LongComparator;
import java.util.function.LongPredicate;
import java.util.function.UnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.LongConsumer;
import java.util.ArrayList;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.HashSet;
import omni.api.OmniCollection;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.TypeUtil;
import omni.impl.QueryCastType;
import omni.util.TypeConversionUtil;
import omni.api.OmniStack;
import omni.api.OmniQueue;
import omni.api.OmniList;
import omni.api.OmniDeque;
@SuppressWarnings({"rawtypes","unchecked"})
abstract class AbstractLongSeqMonitor<SEQ extends OmniCollection.OfLong>
{
  final CheckedType checkedType;
  SEQ seq;
  int expectedSeqSize;
  int expectedSeqModCount;
  AbstractLongSeqMonitor(CheckedType checkedType)
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
  abstract void clear();
  abstract void verifyRemoveIf(MonitoredRemoveIfPredicate pred,FunctionCallType functionCallType,int expectedNumRemoved,OmniCollection.OfLong clone);
  abstract void writeObject(ObjectOutputStream oos) throws IOException;
  abstract Object readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException;
  abstract void verifyRemoval();
  abstract void verifyStructuralIntegrity();
  abstract void verifyFunctionalModification();
  abstract AbstractItrMonitor getItrMonitor();
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
  AbstractItrMonitor getItrMonitor(SequenceLocation seqLocation,ItrType itrType){
    switch(seqLocation){
      case BEGINNING:
        switch(itrType){
          case ListItr:
            return getListItrMonitor();
          case Itr:
            return getItrMonitor();
          case DescendingItr:
            return getDescendingItrMonitor();
          default:
            throw new Error("Unknown itr type "+itrType);
        }
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
  boolean addVal(long obj){
    boolean ret=seq.add(obj);
    if(ret)
    {
      verifyAddition();
    }
    return ret;
  }
  void add(int index,int val,LongInputTestArgType inputArgType){
    inputArgType.callListAdd(seq,index,val);
    verifyAddition();
  }
  void add(int index,int val){
    add(index,val,LongInputTestArgType.ARRAY_TYPE);
  }
  void verifySet(int index,int val,int expectedRet,FunctionCallType functionCallType){
    if(functionCallType==FunctionCallType.Boxed){
      Assertions.assertEquals(TypeConversionUtil.convertToLong(expectedRet),((OmniList.OfLong)seq).set(index,TypeConversionUtil.convertToLong(val)));
    }
    else
    {
      Assertions.assertEquals(TypeConversionUtil.convertTolong(expectedRet),((OmniList.OfLong)seq).set(index,TypeConversionUtil.convertTolong(val)));
    }
  }
  void pop(int expectedVal,LongOutputTestArgType outputType){
    if(seq instanceof OmniDeque)
    {
      throw new Error("Not implemented yet");
    }
    else if(seq instanceof OmniQueue)
    {
      outputType.verifyQueueRemove(seq,expectedVal);
    }
    else if(seq instanceof OmniStack)
    {
      outputType.verifyStackPop(seq,expectedVal);
    }
    else
    {
      throw new Error("Unknown nested type for "+seq);
    }
    verifyRemoval();
  }
  void poll(int expectedVal,LongOutputTestArgType outputType){
    outputType.verifyPoll(seq,expectedSeqSize,expectedVal);
    if(expectedSeqSize!=0){
      verifyRemoval();
    }
  }
  boolean offer(int val,LongInputTestArgType inputArgType){
    boolean ret=inputArgType.callQueueOffer(seq,val);
    if(ret){
      verifyAddition();
    }
    return ret;
  }
  boolean add(int val,LongInputTestArgType inputArgType){
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
    return add(val,LongInputTestArgType.ARRAY_TYPE);
  }
  void push(int val,LongInputTestArgType inputArgType){
    inputArgType.callStackPush(seq,val);
    verifyAddition();
  }
  void push(int val){
    push(val,LongInputTestArgType.ARRAY_TYPE);
  }
  void put(int index,int val,LongInputTestArgType inputArgType){
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
      seq.forEach((LongConsumer)action);
    }
  }
  void unstableSort(MonitoredComparator sorter){
    int seqSize=expectedSeqSize;
    ((OmniList.OfLong)seq).unstableSort((LongComparator)sorter);
    if(seqSize>1){
      verifyFunctionalModification();
    }
  }
  void replaceAll(MonitoredUnaryOperator operator,FunctionCallType functionCallType){
    int seqSize=expectedSeqSize;
    if(functionCallType==FunctionCallType.Boxed){
      ((OmniList.OfLong)seq).replaceAll((UnaryOperator)operator);
    }else
    {
      ((OmniList.OfLong)seq).replaceAll((LongUnaryOperator)operator);
    }
    if(seqSize!=0){
     verifyFunctionalModification();
    }
  }
  void sort(MonitoredComparator sorter,FunctionCallType functionCallType){
    int seqSize=expectedSeqSize;
    if(functionCallType==FunctionCallType.Boxed){
      ((OmniList.OfLong)seq).sort((Comparator)sorter);
    }else
    {
      ((OmniList.OfLong)seq).sort((LongComparator)sorter);
    }
    if(seqSize>1){
      verifyFunctionalModification();
    }
  }
  void stableAscendingSort(){
    int seqSize=expectedSeqSize;
    ((OmniList.OfLong)seq).stableAscendingSort();
    if(seqSize>1){
      verifyFunctionalModification();
    }
  }
  void stableDescendingSort(){
    int seqSize=expectedSeqSize;
    ((OmniList.OfLong)seq).stableDescendingSort();
    if(seqSize>1){
     verifyFunctionalModification();
    }
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
  boolean removeVal(Boolean obj)
  {
    boolean ret=seq.removeVal(obj);
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
  boolean removeVal(Character obj)
  {
    boolean ret=seq.removeVal(obj);
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
  boolean removeVal(Integer obj)
  {
    boolean ret=seq.removeVal(obj);
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
  boolean removeVal(Float obj)
  {
    boolean ret=seq.removeVal(obj);
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
  boolean removeVal(boolean obj)
  {
    boolean ret=seq.removeVal(obj);
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
  boolean removeVal(char obj)
  {
    boolean ret=seq.removeVal(obj);
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
  boolean removeVal(int obj)
  {
    boolean ret=seq.removeVal(obj);
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
  boolean removeVal(float obj)
  {
    boolean ret=seq.removeVal(obj);
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
  void removeAt(int expectedVal,LongOutputTestArgType outputType,int index){
    outputType.verifyListRemoveAt(seq,index,expectedVal);
    verifyRemoval();
  }
  void get(int expectedVal,LongOutputTestArgType outputType,int index){
    outputType.verifyListGet(seq,index,expectedVal);
  }
  private static int verifySingleStrElement(String str,int strOffset){
    Assertions.assertTrue(str.charAt(strOffset)=='1',"String fails at index "+strOffset);
    return strOffset;
  }
  private static void verifyLargeStr(String str,int offset,int bound,SequenceVerificationItr verifyItr){
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
  String callToString(){
    return seq.toString();
  }
  void verifyMASSIVEString(){
    String string=callToString();
    verifyStructuralIntegrity();
    var verifyItr=verifyPreAlloc(1);
    Assertions.assertEquals('[',string.charAt(0));
    Assertions.assertEquals(']',string.charAt(string.length()-1));
    int numThreads=Runtime.getRuntime().availableProcessors();
    int seqSize=getExpectedSeqSize();
    int threadOffset=0;
    int threadSpan=seqSize/numThreads;
    Thread[] threads=new Thread[numThreads-1];
    for(int i=0;i<numThreads-1;++i){
      final int thisThreadOffset=threadOffset;
      final int thisThreadBound=thisThreadOffset+threadSpan;
      final var thisThreadVerifyItr=verifyItr.getPositiveOffset(thisThreadOffset);
      threadOffset=thisThreadBound;
      (threads[i]=new Thread(()->verifyLargeStr(string,thisThreadOffset,thisThreadBound,thisThreadVerifyItr))).start();
    }
    verifyLargeStr(string,threadOffset,threadOffset+threadSpan,verifyItr.getPositiveOffset(threadOffset));
    try{
      for(int i=0;i<numThreads-1;++i){
        threads[i].join();
      }
    }catch(InterruptedException e){
      Assertions.fail(e);
    }
    verifyItr.skip(seqSize).verifyPostAlloc(1);
  }
  void verifyThrowCondition(int numToAdd,PreModScenario preModScenario
  ){
     var verifyItr=verifyPreAlloc();
    {
      verifyItr.verifyAscending(numToAdd).verifyPostAlloc(preModScenario);
    }
  }
  abstract static class AbstractItrMonitor<ITR extends OmniIterator.OfLong>
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
    void verifyPrevious(int expectedVal,LongOutputTestArgType outputType){
      throw new UnsupportedOperationException();
    }
    void add(int v,LongInputTestArgType inputArgType){
      throw new UnsupportedOperationException();
    }
    abstract void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType);
    abstract AbstractLongSeqMonitor getSeqMonitor();
    abstract void verifyNext(int expectedVal,LongOutputTestArgType outputType);
    abstract void iterateForward();
    abstract void remove();
    abstract void verifyIteratorState();
    void set(int v,LongInputTestArgType inputArgType){
       inputArgType.callListItrSet((OmniListIterator.OfLong)itr,v);
    }
    void set(int v){
      set(v,LongInputTestArgType.ARRAY_TYPE);
    }
    void add(int v){
      add(v,LongInputTestArgType.ARRAY_TYPE);
    }
    boolean hasNext(){
      return itr.hasNext();
    }
    boolean hasPrevious(){
      return ((OmniListIterator.OfLong)itr).hasPrevious();
    }
    int nextIndex(){
      return ((OmniListIterator.OfLong)itr).nextIndex();
    }
    int previousIndex(){
      return ((OmniListIterator.OfLong)itr).previousIndex();
    }
  }
  abstract static class SequenceVerificationItr{
    abstract void verifyLiteralIndexAndIterate(long val);
    abstract void verifyIndexAndIterate(LongInputTestArgType inputArgType,int val);
    abstract SequenceVerificationItr getPositiveOffset(int i);
    abstract SequenceVerificationItr skip(int i);
    abstract SequenceVerificationItr verifyPostAlloc(int expectedVal);
    abstract SequenceVerificationItr verifyParentPostAlloc();
    abstract SequenceVerificationItr verifyRootPostAlloc();
    abstract SequenceVerificationItr verifyNaturalAscending(int v,LongInputTestArgType inputArgType,int length);
    public abstract boolean equals(Object val);
    SequenceVerificationItr verifyAscending(int v,LongInputTestArgType inputArgType,int length){
      for(int i=0;i<length;++i,++v){
        verifyIndexAndIterate(inputArgType,v);
      }
      return this;
    }
    void verifyIndexAndIterate(int val){
      verifyIndexAndIterate(LongInputTestArgType.ARRAY_TYPE,val);
    }
    SequenceVerificationItr verifyNaturalAscending(int length)
    {
       return verifyNaturalAscending(0,LongInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyNaturalAscending(int v,int length)
    {
       return verifyNaturalAscending(v,LongInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyNaturalAscending(LongInputTestArgType inputArgType,int length){
      return verifyNaturalAscending(0,inputArgType,length);
    }
    SequenceVerificationItr verifyAscending(int length){
      return verifyAscending(0,LongInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyAscending(int v,int length){
      return verifyAscending(v,LongInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyAscending(LongInputTestArgType inputArgType,int length){
      return verifyAscending(0,inputArgType,length);
    }
    SequenceVerificationItr verifyDescending(int length){
      return verifyDescending(length,LongInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyDescending(int v,int length)
    {
       return verifyDescending(v,LongInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyDescending(int v,LongInputTestArgType inputArgType,int length)
    {
      for(int i=0;i<length;++i){
        verifyIndexAndIterate(inputArgType,--v);
      }
      return this;
    }
    SequenceVerificationItr verifyDescending(LongInputTestArgType inputArgType,int length){
      return verifyDescending(length,inputArgType,length);
    }
    SequenceVerificationItr verifyNearBeginningInsertion(int length){
      return verifyNearBeginningInsertion(LongInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyNearBeginningInsertion(LongInputTestArgType inputArgType,final int length){
      int v=3;
      for(int i=0,bound=(length/4);i<bound;++i)
      {
        verifyIndexAndIterate(inputArgType,v);
        v+=4;
      }
      v-=4;
      for(int i=0,bound=(length-(length/4));i<bound;++i)
      {
        if((i%3)==0)
        {
          --v;
        }
        verifyIndexAndIterate(inputArgType,v);
        --v;
      }
      return this;
    }
    SequenceVerificationItr verifyNearEndInsertion(int length){
      return verifyNearEndInsertion(LongInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyNearEndInsertion(LongInputTestArgType inputArgType,final int length){
      int v=0;
      for(int i=0,bound=(length/4)*3;i<bound;)
      {
        verifyIndexAndIterate(inputArgType,v);
        ++i;
        if((i%3)==0)
        {
          ++v;
        }
        ++v;
      }
      --v;
      for(int i=0,bound=(length-((length/4)*3));i<bound;++i)
      {
        verifyIndexAndIterate(inputArgType,v);
        v-=4;
      }
      return this;
    }
    SequenceVerificationItr verifyMidPointInsertion(int length){
      return verifyMidPointInsertion(LongInputTestArgType.ARRAY_TYPE,length);
    }
    SequenceVerificationItr verifyMidPointInsertion(LongInputTestArgType inputArgType,final int length){
      int i=0;
      for(int v=1,halfLength=length/2;i<halfLength;++i,v+=2){
        verifyIndexAndIterate(inputArgType,v);
      }
      for(int v=length-2;i<length;++i,v-=2){
        verifyIndexAndIterate(inputArgType,v);
      }
      return this;
    }
    SequenceVerificationItr verifyIllegalAdd(){
      verifyIndexAndIterate(LongInputTestArgType.ARRAY_TYPE,0);
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
  static enum SequenceContentsScenario{
    EMPTY(false),
    NONEMPTY(true);
    final boolean nonEmpty;
    SequenceContentsScenario(boolean nonEmpty){
      this.nonEmpty=nonEmpty;
    }
  }
  static enum MonitoredRemoveIfPredicateGen
  {
    RemoveAll(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          @Override boolean testImpl(long val){
            return true;
          }
        };
      }
    },
    RemoveNone(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          @Override boolean testImpl(long val){
            return false;
          }
        };
      }
    },
    Random(null,true,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(long val){
            return rand.nextDouble()>=threshold;
          }
        };
      }
    },
    Throw(IndexOutOfBoundsException.class,true,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(long val){
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
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(long val){
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
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(long val){
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
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(long val){
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
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(long val){
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
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(long val){
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
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(long val){
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
    abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractLongSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold);
  }
  static enum MonitoredFunctionGen{
    NoThrow(null,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator();
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor();
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file);
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file);
      }
    },
    Throw(IndexOutOfBoundsException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractLongSeqMonitor seqMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public long applyAsLong(long val){
            super.applyAsLong(val);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Long[] apply(int arrSize){
            ++numCalls;
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
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
          public void accept(long val){
            super.accept(val);
            itrMonitor.iterateForward();
            itrMonitor.remove();
          }
        };
      }
    },
    ModSeq(ConcurrentModificationException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public long applyAsLong(long val){
            var ret=super.applyAsLong(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Long[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return new Long[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
    },
    ModParent(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public long applyAsLong(long val){
            var ret=super.applyAsLong(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Long[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return new Long[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
    },
    ModRoot(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public long applyAsLong(long val){
            var ret=super.applyAsLong(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Long[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return new Long[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
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
          public void accept(long val){
            super.accept(val);
            itrMonitor.iterateForward();
            itrMonitor.remove();
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModSeq(ConcurrentModificationException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public long applyAsLong(long val){
            super.applyAsLong(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Long[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModParent(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public long applyAsLong(long val){
            super.applyAsLong(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Long[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModRoot(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(long val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public long applyAsLong(long val){
            super.applyAsLong(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Long[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
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
    MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractLongSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredConsumer getMonitoredConsumer(AbstractLongSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    abstract MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor);
    MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractLongSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
      throw new UnsupportedOperationException();
    }
    MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractLongSeqMonitor seqMonitor) throws IOException{
      throw new UnsupportedOperationException();
    }
  }
  static abstract class MonitoredComparator implements LongComparator
   ,Comparator
  {
    public abstract int compare(long val1, long val2);
    public int compare(Object val1,Object val2){
      return compare((long)val1,(long)val2);
    }
  }
  static class MonitoredUnaryOperator implements LongUnaryOperator
    ,UnaryOperator
  {
    ArrayList encounteredValues=new ArrayList();
    public long applyAsLong(long val){
      encounteredValues.add(val);
      return (long)(val+1);
    }
    public Object apply(Object val){
      return applyAsLong((long)val);
    }
  }
  static class MonitoredArrayConstructor
    implements IntFunction<Long[]>
  {
    int numCalls;
    @Override public Long[] apply(int arrSize){
      ++numCalls;
      return new Long[arrSize];
    }
  }
  static class MonitoredConsumer implements LongConsumer
    ,Consumer
  {
    ArrayList encounteredValues=new ArrayList();
    public void accept(long val){
      encounteredValues.add(val);
    }
    public void accept(Object val){
      accept((long)val);
    }
  }
  public static class ThrowingMonitoredConsumer extends MonitoredConsumer{
    public void accept(long val){
      super.accept(val);
      throw new IndexOutOfBoundsException();
    }
  }
  static abstract class MonitoredRemoveIfPredicate implements LongPredicate
    ,Predicate<Long>
  {
    final HashSet removedVals=new HashSet();
    int callCounter;
    int numRemoved;
    abstract boolean testImpl(long val);
    @Override public MonitoredRemoveIfPredicate negate()
    {
      //not worth implementing but must declare
      return null;
    }
    @Override public boolean test(long val)
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
    @Override public boolean test(Long val)
    {
      return test((long)val);
    }
  }
  static enum MonitoredComparatorGen{
    NoThrowAscending(null,true,true,false,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            return Long.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NoThrowDescending(null,true,false,true,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            return -Long.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NullComparator(null,true,true,false,true){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void initReverseHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ThrowAIOB(IllegalArgumentException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ThrowIOB(IndexOutOfBoundsException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModSeqAscending(ConcurrentModificationException.class,true,true,false,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return Long.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
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
    },
    ModSeqDescending(ConcurrentModificationException.class,true,false,true,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return -Long.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
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
    },
    ModParentAscending(ConcurrentModificationException.class,false,true,false,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return Long.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
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
    },
    ModParentDescending(ConcurrentModificationException.class,false,false,true,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return -Long.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
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
    },
    ModRootAscending(ConcurrentModificationException.class,false,true,false,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return Long.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModRootDescending(ConcurrentModificationException.class,false,false,true,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return -Long.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModSeqThrowAIOB(ConcurrentModificationException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
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
    },
    ModSeqThrowIOB(ConcurrentModificationException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
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
    },
    ModParentThrowAIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
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
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
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
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModRootThrowIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(long val1,long val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
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
    abstract void initHelper(AbstractLongSeqMonitor seqMonitor);
    void initReverseHelper(AbstractLongSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void init(AbstractLongSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      if(seqContentsScenario.nonEmpty){
        initHelper(seqMonitor);
      }else{
        seqMonitor.add(1);
      }
      seqMonitor.illegalAdd(preModScenario);
    }
    void initReverse(AbstractLongSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      if(seqContentsScenario.nonEmpty){
        initReverseHelper(seqMonitor);
      }else{
        seqMonitor.add(1);
      }
      seqMonitor.illegalAdd(preModScenario);
    }
    void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
      throw new UnsupportedOperationException();
    }
    abstract void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario);
    void assertSorted(AbstractLongSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      if(seqContentsScenario.nonEmpty){
        assertSortedHelper(verifyItr,preModScenario);
      }else{
        verifyItr.verifyIndexAndIterate(1);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    }
    void assertReverseSorted(AbstractLongSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      if(seqContentsScenario.nonEmpty){
        assertReverseSortedHelper(verifyItr,preModScenario);
      }else{
        verifyItr.verifyIndexAndIterate(1);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    }
    abstract MonitoredComparator getMonitoredComparator(AbstractLongSeqMonitor seqMonitor);
  }
  static enum QueryTester
  {
    Booleannull(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Boolean)(null));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Boolean)(null));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(Boolean)(null));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(Boolean)(null));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(Boolean)(null));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(Boolean)(null));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Boolean)(Boolean)(null));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Boolean)(Boolean)(null));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Boolean)(Boolean)(null));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Boolean)(Boolean)(null));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Boolean)(null));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Boolean)(null));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Boolean)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Boolean)(null));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Bytenull(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Byte)(null));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Byte)(null));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(Byte)(null));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(Byte)(null));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(Byte)(null));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(Byte)(null));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(Byte)(null));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Byte)(Byte)(null));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Byte)(Byte)(null));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Byte)(Byte)(null));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(null));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Byte)(null));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Byte)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Byte)(null));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Characternull(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Character)(null));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Character)(null));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(Character)(null));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(Character)(null));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(Character)(null));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(Character)(null));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(Character)(null));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Character)(Character)(null));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Character)(Character)(null));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Character)(Character)(null));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(null));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Character)(null));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Character)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Character)(null));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Shortnull(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Short)(null));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Short)(null));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(Short)(null));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(Short)(null));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(Short)(null));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(Short)(null));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(Short)(null));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Short)(Short)(null));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Short)(Short)(null));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Short)(Short)(null));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(null));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Short)(null));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Short)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Short)(null));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Integernull(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Integer)(null));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Integer)(null));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(Integer)(null));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(Integer)(null));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(Integer)(null));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(Integer)(null));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(Integer)(null));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(Integer)(null));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(Integer)(null));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(Integer)(null));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(null));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(null));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(null));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Longnull(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Long)(null));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Long)(null));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(Long)(null));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(Long)(null));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(Long)(null));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(Long)(null));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(Long)(null));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(Long)(null));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(Long)(null));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(Long)(null));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(null));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(null));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(null));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Floatnull(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Float)(null));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Float)(null));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(Float)(null));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(Float)(null));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(Float)(null));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(Float)(null));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(Float)(null));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(Float)(null));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(Float)(null));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(Float)(null));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(null));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(null));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(null));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Doublenull(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Double)(null));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Double)(null));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(Double)(null));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(Double)(null));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(Double)(null));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(Double)(null));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(Double)(null));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(Double)(null));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(Double)(null));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(Double)(null));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(null));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(null));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(null));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Objectnull(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(Object)(null));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(Object)(null));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(Object)(null));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(Object)(null));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.remove((Object)(Object)(null));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(Object)(null));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(Object)(null));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(Object)(null));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(null));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.remove((Object)(null));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(null));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(null));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(null));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        throw new UnsupportedOperationException();
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Booleanfalse(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(false));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(boolean)(false));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(boolean)(false));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(boolean)(false));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(boolean)(false));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(false));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Boolean)(boolean)(false));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Boolean)(boolean)(false));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Boolean)(boolean)(false));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Boolean)(boolean)(false));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(false));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((boolean)(false));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((boolean)(false));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((boolean)(false));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((boolean)(false));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)TypeUtil.castToLong(false));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Booleantrue(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(true));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(boolean)(true));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(boolean)(true));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(boolean)(true));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(boolean)(true));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(true));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Boolean)(boolean)(true));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Boolean)(boolean)(true));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Boolean)(boolean)(true));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Boolean)(boolean)(true));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(true));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((boolean)(true));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((boolean)(true));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((boolean)(true));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((boolean)(true));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)TypeUtil.castToLong(true));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Byte0(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(0));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(byte)(0));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(byte)(0));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(byte)(0));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(byte)(0));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(0));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(byte)(0));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Byte)(byte)(0));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Byte)(byte)(0));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Byte)(byte)(0));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(0));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((byte)(0));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((byte)(0));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((byte)(0));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((byte)(0));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(0));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Bytepos1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(byte)(1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(byte)(1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(byte)(1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(byte)(1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(byte)(1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Byte)(byte)(1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Byte)(byte)(1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Byte)(byte)(1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((byte)(1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((byte)(1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((byte)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((byte)(1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Bytepos2(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(2));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(byte)(2));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(byte)(2));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(byte)(2));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(byte)(2));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(2));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(byte)(2));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Byte)(byte)(2));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Byte)(byte)(2));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Byte)(byte)(2));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((byte)(2));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((byte)(2));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((byte)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((byte)(2));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(2));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Byteneg1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(byte)(-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(byte)(-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(byte)(-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(byte)(-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Byte)(byte)(-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Byte)(byte)(-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Byte)(byte)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Byte)(byte)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((byte)(-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((byte)(-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((byte)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((byte)(-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Character0(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(0));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(char)(0));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(char)(0));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(char)(0));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(char)(0));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(0));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(char)(0));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Character)(char)(0));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Character)(char)(0));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Character)(char)(0));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(0));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((char)(0));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((char)(0));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((char)(0));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((char)(0));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(0));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Characterpos1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(char)(1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(char)(1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(char)(1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(char)(1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(char)(1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Character)(char)(1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Character)(char)(1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Character)(char)(1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((char)(1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((char)(1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((char)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((char)(1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Characterpos2(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(2));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(char)(2));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(char)(2));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(char)(2));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(char)(2));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(2));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(char)(2));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Character)(char)(2));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Character)(char)(2));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Character)(char)(2));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((char)(2));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((char)(2));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((char)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((char)(2));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(2));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    CharacterMAX_BYTE_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(char)(((char)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Character)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((char)(((char)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((char)(((char)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((char)(((char)Byte.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((char)Byte.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    CharacterMAX_SHORT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Character)(char)(((char)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Character)(char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((char)(((char)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((char)(((char)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((char)(((char)Short.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((char)Short.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Short0(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(0));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(0));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(short)(0));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(short)(0));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(short)(0));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(0));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(0));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Short)(short)(0));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Short)(short)(0));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Short)(short)(0));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(0));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(0));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((short)(0));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((short)(0));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((short)(0));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(0));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Shortpos1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(short)(1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(short)(1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(short)(1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Short)(short)(1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Short)(short)(1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Short)(short)(1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((short)(1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((short)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((short)(1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Shortpos2(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(2));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(2));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(short)(2));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(short)(2));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(short)(2));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(2));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(2));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Short)(short)(2));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Short)(short)(2));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Short)(short)(2));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(2));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((short)(2));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((short)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((short)(2));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(2));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Shortneg1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(short)(-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(short)(-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(short)(-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Short)(short)(-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Short)(short)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Short)(short)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((short)(-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((short)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((short)(-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    ShortMAX_BYTE_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(((short)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Short)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(((short)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((short)(((short)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((short)(((short)Byte.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((short)Byte.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    ShortMIN_BYTE_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Short)(short)(((short)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Short)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((short)(((short)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((short)(((short)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((short)(((short)Byte.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((short)Byte.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Integer0(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(0));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(0));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(0));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(0));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(0));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(0));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(0));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(0));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(0));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(0));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(0));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(0));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(0));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(0));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(0));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(0));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Integerpos1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Integerpos2(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(2));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(2));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(2));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(2));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(2));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(2));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(2));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(2));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(2));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(2));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(2));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(2));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(2));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(2));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Integerneg1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMAX_BYTE_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(((int)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(((int)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(((int)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(((int)Byte.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((int)Byte.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMIN_BYTE_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(((int)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(((int)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(((int)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(((int)Byte.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((int)Byte.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMAX_SHORT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(((int)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(((int)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(((int)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(((int)Short.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((int)Short.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMIN_SHORT_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(((int)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(((int)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(((int)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(((int)Short.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((int)Short.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMAX_CHAR_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(((int)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(((int)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(((int)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(((int)Character.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((int)Character.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMAX_SAFE_INT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(TypeUtil.MAX_SAFE_INT+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(TypeUtil.MAX_SAFE_INT+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    IntegerMIN_SAFE_INT_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((int)(TypeUtil.MIN_SAFE_INT-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(TypeUtil.MIN_SAFE_INT-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Long0(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(0));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(0));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(0));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(0));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(0));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(0));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(0));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(0));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(0));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(0));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(0));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(0));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(0));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(0));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(0));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(0));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Longpos1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Longpos2(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(2));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(2));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(2));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(2));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(2));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(2));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(2));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(2));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(2));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(2));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(2));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(2));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(2));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(2));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Longneg1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_BYTE_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)Byte.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)Byte.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMIN_BYTE_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)Byte.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)Byte.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_SHORT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)Short.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)Short.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMIN_SHORT_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)Short.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)Short.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_CHAR_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)Character.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)Character.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_SAFE_INT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMIN_SAFE_INT_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_INT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)Integer.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)Integer.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMIN_INT_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)Integer.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)Integer.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMAX_SAFE_LONG_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    LongMIN_SAFE_LONG_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Floatpos0(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(0.0F));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(0.0F));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(0.0F));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(0.0F));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(0.0F));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(0.0F));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(0.0F));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(0.0F));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(0.0F));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(0.0F));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(0.0F));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(0.0F));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(0.0F));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(0.0F));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(0.0F));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(0.0F));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Floatneg0(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-0.0F));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(-0.0F));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(-0.0F));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(-0.0F));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(-0.0F));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-0.0F));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(-0.0F));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(-0.0F));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(-0.0F));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(-0.0F));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-0.0F));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(-0.0F));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(-0.0F));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(-0.0F));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(-0.0F));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(-0.0F));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Floatpos1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Floatpos2(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(2));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(2));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(2));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(2));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(2));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(2));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(2));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(2));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(2));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(2));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(2));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(2));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(2));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(2));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Floatneg1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_BYTE_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(((float)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(((float)Byte.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((float)Byte.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMIN_BYTE_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(((float)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(((float)Byte.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((float)Byte.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_SHORT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(((float)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(((float)Short.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((float)Short.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMIN_SHORT_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(((float)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(((float)Short.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((float)Short.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_CHAR_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(((float)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(((float)Character.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((float)Character.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_INT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(((float)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(((float)Integer.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((float)Integer.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMIN_INT_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(((float)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(((float)Integer.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((float)Integer.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_LONG_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Long.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Long.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(((float)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(((float)Long.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((float)Long.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMIN_LONG_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(((float)Long.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(((float)Long.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(((float)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(((float)Long.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((float)Long.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMIN_FLOAT_VALUE(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(Float.MIN_VALUE));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(Float.MIN_VALUE));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(Float.MIN_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(Float.MIN_VALUE));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(Float.MIN_VALUE));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(Float.MIN_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(Float.MIN_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(Float.MIN_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(Float.MIN_VALUE));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(Float.MIN_VALUE));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatMAX_FLOAT_VALUE(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(Float.MAX_VALUE));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(Float.MAX_VALUE));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(Float.MAX_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(Float.MAX_VALUE));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(Float.MAX_VALUE));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(Float.MAX_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(Float.MAX_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(Float.MAX_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(Float.MAX_VALUE));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(Float.MAX_VALUE));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    FloatNaN(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.NaN));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(float)(Float.NaN));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(float)(Float.NaN));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(float)(Float.NaN));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(float)(Float.NaN));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.NaN));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Float)(float)(Float.NaN));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Float)(float)(Float.NaN));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Float)(float)(Float.NaN));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Float)(float)(Float.NaN));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.NaN));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((float)(Float.NaN));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((float)(Float.NaN));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((float)(Float.NaN));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((float)(Float.NaN));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(Float.NaN));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Doublepos0(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(0.0D));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(0.0D));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(0.0D));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(0.0D));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(0.0D));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(0.0D));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(0.0D));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(0.0D));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(0.0D));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(0.0D));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(0.0D));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(0.0D));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(0.0D));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(0.0D));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(0.0D));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(0.0D));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Doubleneg0(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-0.0D));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(-0.0D));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(-0.0D));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(-0.0D));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(-0.0D));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-0.0D));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(-0.0D));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(-0.0D));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(-0.0D));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(-0.0D));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-0.0D));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(-0.0D));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(-0.0D));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(-0.0D));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(-0.0D));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(-0.0D));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)true);
      }
    },
    Doublepos1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Doublepos2(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(2));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(2));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(2));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(2));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(2));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(2));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(2));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(2));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(2));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(2));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(2));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(2));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(2));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(2));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(2));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(2));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    Doubleneg1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_BYTE_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Byte.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)Byte.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)Byte.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)Byte.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_BYTE_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Byte.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)Byte.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)Byte.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)Byte.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_SHORT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Short.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)Short.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)Short.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)Short.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_SHORT_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Short.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)Short.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)Short.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)Short.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_CHAR_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Character.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Character.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)Character.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)Character.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)Character.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_SAFE_INT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)TypeUtil.MAX_SAFE_INT)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_SAFE_INT_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)TypeUtil.MIN_SAFE_INT)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_INT_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Integer.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)Integer.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)Integer.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)Integer.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_INT_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Integer.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)Integer.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)Integer.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)Integer.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_LONG_PLUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Long.MAX_VALUE)+1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MAX_VALUE)+1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Long.MAX_VALUE)+1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)Long.MAX_VALUE)+1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)Long.MAX_VALUE)+1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)Long.MAX_VALUE)+1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_LONG_MINUS1(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(((double)Long.MIN_VALUE)-1));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MIN_VALUE)-1));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(((double)Long.MIN_VALUE)-1));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(((double)Long.MIN_VALUE)-1));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(((double)Long.MIN_VALUE)-1));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(((double)Long.MIN_VALUE)-1));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_FLOAT_VALUE(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(Float.MIN_VALUE));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(Float.MIN_VALUE));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(Float.MIN_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(Float.MIN_VALUE));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(Float.MIN_VALUE));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(Float.MIN_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MIN_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(Float.MIN_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(Float.MIN_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(Float.MIN_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(Float.MIN_VALUE));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(Float.MIN_VALUE));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_FLOAT_VALUE(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(Float.MAX_VALUE));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(Float.MAX_VALUE));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(Float.MAX_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(Float.MAX_VALUE));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(Float.MAX_VALUE));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(Float.MAX_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MAX_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(Float.MAX_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(Float.MAX_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(Float.MAX_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(Float.MAX_VALUE));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(Float.MAX_VALUE));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMIN_DOUBLE_VALUE(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(Double.MIN_VALUE));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(Double.MIN_VALUE));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(Double.MIN_VALUE));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(Double.MIN_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(Double.MIN_VALUE));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(Double.MIN_VALUE));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(Double.MIN_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(Double.MIN_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MIN_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(Double.MIN_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(Double.MIN_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(Double.MIN_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(Double.MIN_VALUE));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(Double.MIN_VALUE));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleMAX_DOUBLE_VALUE(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(Double.MAX_VALUE));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(Double.MAX_VALUE));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(Double.MAX_VALUE));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(Double.MAX_VALUE));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(Double.MAX_VALUE));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(Double.MAX_VALUE));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(Double.MAX_VALUE));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(Double.MAX_VALUE));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MAX_VALUE));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(Double.MAX_VALUE));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(Double.MAX_VALUE));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(Double.MAX_VALUE));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(Double.MAX_VALUE));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(Double.MAX_VALUE));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    DoubleNaN(false){
      @Override boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.NaN));}
      @Override boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor){return seqMonitor.remove((Object)(double)(Double.NaN));}
      @Override int invokesearchObject(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Object)(double)(Double.NaN));}
      @Override int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Object)(double)(Double.NaN));}
      @Override int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Object)(double)(Double.NaN));}
      @Override boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.NaN));}
      @Override boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((Double)(double)(Double.NaN));
      }
      @Override int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((Double)(double)(Double.NaN));}
      @Override int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((Double)(double)(Double.NaN));}
      @Override int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((Double)(double)(Double.NaN));}
      @Override boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.NaN));}
      @Override boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor){
        return seqMonitor.removeVal((double)(Double.NaN));
      }
      @Override int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniStack.OfLong)seqMonitor.seq).search((double)(Double.NaN));}
      @Override int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).indexOf((double)(Double.NaN));}
      @Override int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor){return ((OmniList.OfLong)seqMonitor.seq).lastIndexOf((double)(Double.NaN));}
      void addEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((long)(Double.NaN));
      }
      void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor){
        seqMonitor.addVal((boolean)false);
      }
    },
    ;
      final boolean isObjectNonNull;
      QueryTester(boolean isObjectNonNull){
        this.isObjectNonNull=isObjectNonNull;
      }
      boolean invokecontains(AbstractLongSeqMonitor seqMonitor,QueryCastType queryCastType){
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
      boolean invokeremoveVal(AbstractLongSeqMonitor seqMonitor,QueryCastType queryCastType){
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
      int invokesearch(AbstractLongSeqMonitor seqMonitor,QueryCastType queryCastType){
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
      int invokelastIndexOf(AbstractLongSeqMonitor seqMonitor,QueryCastType queryCastType){
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
      int invokeindexOf(AbstractLongSeqMonitor seqMonitor,QueryCastType queryCastType){
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
      abstract boolean invokecontainsObject(AbstractLongSeqMonitor seqMonitor);
      abstract boolean invokecontainsBoxed(AbstractLongSeqMonitor seqMonitor);
      abstract boolean invokecontainsUnboxed(AbstractLongSeqMonitor seqMonitor);
      abstract boolean invokeremoveValObject(AbstractLongSeqMonitor seqMonitor);
      abstract boolean invokeremoveValBoxed(AbstractLongSeqMonitor seqMonitor);
      abstract boolean invokeremoveValUnboxed(AbstractLongSeqMonitor seqMonitor);
      abstract int invokeindexOfObject(AbstractLongSeqMonitor seqMonitor);
      abstract int invokeindexOfBoxed(AbstractLongSeqMonitor seqMonitor);
      abstract int invokeindexOfUnboxed(AbstractLongSeqMonitor seqMonitor);
      abstract int invokelastIndexOfObject(AbstractLongSeqMonitor seqMonitor);
      abstract int invokelastIndexOfBoxed(AbstractLongSeqMonitor seqMonitor);
      abstract int invokelastIndexOfUnboxed(AbstractLongSeqMonitor seqMonitor);
      abstract int invokesearchObject(AbstractLongSeqMonitor seqMonitor);
      abstract int invokesearchBoxed(AbstractLongSeqMonitor seqMonitor);
      abstract int invokesearchUnboxed(AbstractLongSeqMonitor seqMonitor);
      abstract void addEqualsVal(AbstractLongSeqMonitor seqMonitor);
      abstract void addNotEqualsVal(AbstractLongSeqMonitor seqMonitor);
      void initDoesNotContain(AbstractLongSeqMonitor seqMonitor,int seqSize){
        for(int i=0;i<seqSize;++i){
          addNotEqualsVal(seqMonitor);
        }
      }
      int initContainsEnd(AbstractLongSeqMonitor seqMonitor,int seqSize,boolean forwardIteration){
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
      int initContainsMiddle(AbstractLongSeqMonitor seqMonitor,int seqSize,boolean forwardIteration){
        Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
        for(int i=0,bound=seqSize/2;i<bound;++i){
          addNotEqualsVal(seqMonitor);
        }
        addEqualsVal(seqMonitor);
        for(int i=(seqSize/2)+1;i<seqSize;++i){
          addNotEqualsVal(seqMonitor);
        }
        if(forwardIteration)
        {
          return seqMonitor.expectedSeqSize/2+1;
        }
        else
        {
          return (seqMonitor.expectedSeqSize/2);
        }
      }
      int initContainsNearBeginning(AbstractLongSeqMonitor seqMonitor,int seqSize,boolean forwardIteration){
        Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
        for(int i=0,bound=seqSize/4;i<bound;++i){
          addNotEqualsVal(seqMonitor);
        }
        addEqualsVal(seqMonitor);
        for(int i=(seqSize/4)+1;i<seqSize;++i){
          addNotEqualsVal(seqMonitor);
        }
        if(forwardIteration)
        {
          return (seqMonitor.expectedSeqSize/4)+1;
        }
        else
        {
          return ((seqMonitor.expectedSeqSize/4)*3);
        }
      }
      int initContainsNearEnd(AbstractLongSeqMonitor seqMonitor,int seqSize,boolean forwardIteration){
        Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
        for(int i=0,bound=(seqSize/4)*3;i<bound;++i){
          addNotEqualsVal(seqMonitor);
        }
        addEqualsVal(seqMonitor);
        for(int i=((seqSize/4)*3)+1;i<seqSize;++i){
          addNotEqualsVal(seqMonitor);
        }
        if(forwardIteration)
        {
          return ((seqMonitor.expectedSeqSize/4)*3)+1;
        }
        else
        {
          return (seqMonitor.expectedSeqSize/4);
        }
      }
      int initContainsBeginning(AbstractLongSeqMonitor seqMonitor,int seqSize,boolean forwardIteration){
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
