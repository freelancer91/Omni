package omni.impl.seq;
import java.util.function.LongConsumer;
import java.util.Arrays;
import omni.util.ArrCopy;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.LongInputTestArgType;
import omni.impl.LongOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import omni.util.OmniArray;
import omni.impl.LongDblLnkNode;
import omni.api.OmniIterator;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import java.io.File;
import org.junit.jupiter.api.Tag;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import omni.impl.seq.AbstractLongSeqMonitor.CheckedType;
import omni.impl.seq.AbstractLongSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractLongSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractLongSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractLongSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractLongSeqMonitor.ListItrSetScenario;
import omni.impl.seq.AbstractLongSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractLongSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractLongSeqMonitor.QueryTester;
import omni.impl.seq.AbstractLongSeqMonitor.ItrType;
import omni.impl.seq.AbstractLongSeqMonitor.MonitoredComparatorGen;
import java.nio.file.Files;
import omni.impl.seq.AbstractLongSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import java.util.ArrayList;
import omni.api.OmniDeque;
import omni.api.OmniList;
import java.util.Comparator;
import omni.function.LongComparator;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("ArrDeqTest")
@Execution(ExecutionMode.CONCURRENT)
public class LongArrDeqTest{
  @org.junit.jupiter.api.Test
  public void testelement_void(){
    runOutputTests(true,true,LongArrDeqTest::testelement_voidHelper);
  }
  private static void testelement_voidHelper(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<initialSize;++i){
      outputArgType.verifyQueueElement(seqMonitor.seq,i);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.removeFirst(i,outputArgType);
    }
    if(seqMonitor.checkedType.checked){
      Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyQueueElement(seqMonitor.seq,0));
      seqMonitor.verifyStructuralIntegrity();
    }
  }
  @org.junit.jupiter.api.Test
  public void testgetFirst_void(){
    runOutputTests(true,true,LongArrDeqTest::testgetFirst_voidHelper);
  }
  private static void testgetFirst_voidHelper(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<initialSize;++i){
      outputArgType.verifyDequeGetFirst(seqMonitor.seq,i);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.removeFirst(i,outputArgType);
    }
    if(seqMonitor.checkedType.checked){
      Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyDequeGetFirst(seqMonitor.seq,0));
      seqMonitor.verifyStructuralIntegrity();
    }
  }
  @org.junit.jupiter.api.Test
  public void testgetLast_void(){
    runOutputTests(true,true,LongArrDeqTest::testgetLast_voidHelper);
  }
  private static void testgetLast_voidHelper(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=initialSize;--i>=0;){
      outputArgType.verifyDequeGetLast(seqMonitor.seq,i);
      seqMonitor.verifyStructuralIntegrity();
      seqMonitor.removeLast(i,outputArgType);
    }
    if(seqMonitor.checkedType.checked){
      Assertions.assertThrows(NoSuchElementException.class,()->outputArgType.verifyDequeGetLast(seqMonitor.seq,0));
      seqMonitor.verifyStructuralIntegrity();
    }
  }
  @org.junit.jupiter.api.Test
  public void testremoveLast_void(){
    runOutputTests(true,true,LongArrDeqTest::testremoveLast_voidHelper);
  }
  private static void testremoveLast_voidHelper(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=initialSize;--i>=0;)
    {
      seqMonitor.removeLast(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    if(checkedType.checked)
    {
      Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.removeLast(0,outputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
  }
  @org.junit.jupiter.api.Test
  public void testremoveFirst_void(){
    runOutputTests(true,true,LongArrDeqTest::testremoveFirst_voidHelper);
  }
  private static void testremoveFirst_voidHelper(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<initialSize;++i)
    {
      seqMonitor.removeFirst(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    if(checkedType.checked)
    {
      Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.removeFirst(0,outputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
  }
  @org.junit.jupiter.api.Test
  public void testremove_void(){
    runOutputTests(true,true,LongArrDeqTest::testremove_voidHelper);
  }
  private static void testremove_voidHelper(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<initialSize;++i)
    {
      seqMonitor.queueRemove(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    if(checkedType.checked)
    {
      Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.queueRemove(0,outputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
  }
  @org.junit.jupiter.api.Test
  public void testpop_void(){
    runOutputTests(true,true,LongArrDeqTest::testpop_voidHelper);
  }
  private static void testpop_voidHelper(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<initialSize;++i)
    {
      seqMonitor.pop(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    if(checkedType.checked)
    {
      Assertions.assertThrows(NoSuchElementException.class,()->seqMonitor.pop(0,outputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
  }
  @org.junit.jupiter.api.Test
  public void testclone_void(){
    for(var checkedType:CheckedType.values()){
      IntStream.range(0,10)
      .parallel()
      .forEach(seqSize->{
        if(seqSize==0)
        {
          var seqMonitor=new SeqMonitor(checkedType,0,0,0);
          testclone_voidHelper(new SeqMonitor(checkedType,0,0,0));
        }
        else
        {
          IntStream.range(0,seqSize)
          .parallel()
          .forEach(head->{
            var seqMonitor=new SeqMonitor(checkedType,seqSize,head,seqSize);
            initializeAscending(seqMonitor.seq.arr,head,seqSize);
            testclone_voidHelper(seqMonitor);
          });
        }
      });
    }
  }
  private static void testclone_voidHelper(SeqMonitor seqMonitor){
    var clone=(LongArrDeq)seqMonitor.seq.clone();
    if(seqMonitor.checkedType.checked){
      Assertions.assertEquals(0,((LongArrDeq.Checked)clone).modCount);
    }
    int seqSize;
    if((seqSize=seqMonitor.expectedSeqSize)==0)
    {
      Assertions.assertEquals(-1,clone.tail);
      Assertions.assertEquals(0,clone.head);
      Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,clone.arr);
    }
    else
    {
      Assertions.assertEquals(0,clone.head);
      Assertions.assertEquals(seqSize-1,clone.tail);
      long[] origArr,cloneArr=clone.arr;
      for(int i=0,j=seqMonitor.seq.head,arrLength=(origArr=seqMonitor.seq.arr).length;i<seqSize;++i)
      {
        Assertions.assertEquals(origArr[j],cloneArr[i]);
        if(++j==arrLength)
        {
          j=0;
        }
      }
    }
  }
  @org.junit.jupiter.api.Test
  public void testpoll_void(){
    runOutputTests(true,true,LongArrDeqTest::testpoll_voidHelper);
  }
  private static void testpoll_voidHelper(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<initialSize;++i)
    {
      seqMonitor.poll(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.poll(0,outputArgType);
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testpollFirst_void(){
    runOutputTests(true,true,LongArrDeqTest::testpollFirst_voidHelper);
  }
  private static void testpollFirst_voidHelper(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<initialSize;++i)
    {
      seqMonitor.pollFirst(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.pollFirst(0,outputArgType);
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testpollLast_void(){
    runOutputTests(true,true,LongArrDeqTest::testpollLast_voidHelper);
  }
  private static void testpollLast_voidHelper(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType outputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialSize,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=initialSize;--i>=0;)
    {
      seqMonitor.pollLast(i,outputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    seqMonitor.pollLast(0,outputArgType);
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testremoveVal_val(){
    runQueryTests(true,(checkedType,argType,queryCastType,seqLocation,seqSize
    )->{
      if(seqSize==0)
      {
        testremoveVal_valHelper(checkedType,seqSize,0,seqSize,seqLocation,argType,queryCastType
        );
      }
      else
      {
        IntStream.range(0,seqSize)
        .parallel()
        .forEach(head->{
          testremoveVal_valHelper(checkedType,seqSize,head,seqSize,seqLocation,argType,queryCastType
          );
        });
      }
    });
  }
  private static void testremoveVal_valHelper(CheckedType checkedType,int capacity,int head,int numToAdd,SequenceLocation seqLocation,QueryTester argType,QueryCastType queryCastType
  ){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,capacity,head,numToAdd);
    if(numToAdd!=0){
      initializeArrayForQuery(true,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation);
    }
    Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveVal(seqMonitor,queryCastType));
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testremoveLastOccurrence_val(){
    runQueryTests(true,(checkedType,argType,queryCastType,seqLocation,seqSize
    )->{
      if(seqSize==0)
      {
        testremoveLastOccurrence_valHelper(checkedType,seqSize,0,seqSize,seqLocation,argType,queryCastType
        );
      }
      else
      {
        IntStream.range(0,seqSize)
        .parallel()
        .forEach(head->{
          testremoveLastOccurrence_valHelper(checkedType,seqSize,head,seqSize,seqLocation,argType,queryCastType
          );
        });
      }
    });
  }
  private static void testremoveLastOccurrence_valHelper(CheckedType checkedType,int capacity,int head,int numToAdd,SequenceLocation seqLocation,QueryTester argType,QueryCastType queryCastType
  ){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,capacity,head,numToAdd);
    if(numToAdd!=0){
      initializeArrayForQuery(false,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation);
    }
    Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokeremoveLastOccurrence(seqMonitor,queryCastType));
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testadd_val(){
    runInputTests(true,LongArrDeqTest::testadd_valHelper);
  }
  private static void testadd_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,LongInputTestArgType inputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seqMonitor.add(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(initialSize);
    verifyItr.verifyAscending(inputArgType,100);
  }
  @org.junit.jupiter.api.Test
  public void testaddLast_val(){
    runInputTests(true,LongArrDeqTest::testaddLast_valHelper);
  }
  private static void testaddLast_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,LongInputTestArgType inputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<100;++i)
    {
      seqMonitor.addLast(i,inputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(initialSize);
    verifyItr.verifyAscending(inputArgType,100);
  }
  @org.junit.jupiter.api.Test
  public void testpush_val(){
    runInputTests(true,LongArrDeqTest::testpush_valHelper);
  }
  private static void testpush_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,LongInputTestArgType inputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<100;++i)
    {
      seqMonitor.push(i,inputArgType);
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      verifyItr.verifyDescending(inputArgType,i+1);
      verifyItr.verifyAscending(initialSize);
    }
    var verifyItr=seqMonitor.verifyPreAlloc();
    verifyItr.verifyDescending(inputArgType,100);
    verifyItr.verifyAscending(initialSize);
  }
  @org.junit.jupiter.api.Test
  public void testaddFirst_val(){
    runInputTests(true,LongArrDeqTest::testaddFirst_valHelper);
  }
  private static void testaddFirst_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,LongInputTestArgType inputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<100;++i)
    {
      seqMonitor.addFirst(i,inputArgType);
      seqMonitor.verifyStructuralIntegrity();
    }
    var verifyItr=seqMonitor.verifyPreAlloc();
    verifyItr.verifyDescending(inputArgType,100);
    verifyItr.verifyAscending(initialSize);
  }
  @org.junit.jupiter.api.Test
  public void testofferFirst_val(){
    runInputTests(true,LongArrDeqTest::testofferFirst_valHelper);
  }
  private static void testofferFirst_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,LongInputTestArgType inputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seqMonitor.offerFirst(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    var verifyItr=seqMonitor.verifyPreAlloc();
    verifyItr.verifyDescending(inputArgType,100);
    verifyItr.verifyAscending(initialSize);
  }
  @org.junit.jupiter.api.Test
  public void testoffer_val(){
    runInputTests(true,LongArrDeqTest::testoffer_valHelper);
  }
  private static void testoffer_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,LongInputTestArgType inputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seqMonitor.offer(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(initialSize);
    verifyItr.verifyAscending(inputArgType,100);
  }
  @org.junit.jupiter.api.Test
  public void tesetofferLast_val(){
    runInputTests(true,LongArrDeqTest::tesetofferLast_valHelper);
  }
  private static void tesetofferLast_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,LongInputTestArgType inputArgType)
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,initialCapacity,head,initialSize);
    initializeAscending(seqMonitor.seq.arr,head,initialSize);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seqMonitor.offerLast(i,inputArgType));
      seqMonitor.verifyStructuralIntegrity();
    }
    var verifyItr=seqMonitor.verifyPreAlloc().verifyAscending(initialSize);
    verifyItr.verifyAscending(inputArgType,100);
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_void(){
    {
      var deq=new LongArrDeq.Checked();
      Assertions.assertEquals(0,deq.modCount);
      Assertions.assertEquals(-1,deq.tail);
      Assertions.assertEquals(0,deq.head);
      Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,deq.arr);
    }
    {
      var deq=new LongArrDeq();
      Assertions.assertEquals(-1,deq.tail);
      Assertions.assertEquals(0,deq.head);
      Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,deq.arr);
    }
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_int(){
    for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5)
    {
      {
        var deq=new LongArrDeq.Checked(initialCapacity);
        Assertions.assertEquals(0,deq.modCount);
        Assertions.assertEquals(-1,deq.tail);
        Assertions.assertEquals(0,deq.head);
        switch(initialCapacity){
          case 0:
            Assertions.assertNull(deq.arr);
            break;
          case OmniArray.DEFAULT_ARR_SEQ_CAP:
            Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,deq.arr);
            break;
          default:
            Assertions.assertEquals(initialCapacity,deq.arr.length);
        }
      }
      {
        var deq=new LongArrDeq.Checked(initialCapacity);
        Assertions.assertEquals(-1,deq.tail);
        Assertions.assertEquals(0,deq.head);
        switch(initialCapacity){
          case 0:
            Assertions.assertNull(deq.arr);
            break;
          case OmniArray.DEFAULT_ARR_SEQ_CAP:
            Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,deq.arr);
            break;
          default:
            Assertions.assertEquals(initialCapacity,deq.arr.length);
        }
      }
    }
  }
  interface OutputTester{
    void runTest(CheckedType checkedType,int head,int initialSize,LongOutputTestArgType inputType);
  }
  static void runOutputTests(boolean parallel,boolean allowEmpty,OutputTester outputTest){
    for(final var checkedType:CheckedType.values()){
      var seqSizeStream=IntStream.rangeClosed(0,10).filter(seqSize->allowEmpty||seqSize!=0||checkedType.checked);
      if(parallel){
        seqSizeStream=seqSizeStream.parallel();
      }
      seqSizeStream.forEach(seqSize->{
        var outputArgTypeStream=Stream.of(LongOutputTestArgType.values());
        if(parallel){
          outputArgTypeStream=outputArgTypeStream.parallel();
        }
        outputArgTypeStream.forEach(outputType->{
          if(seqSize==0){
            outputTest.runTest(checkedType,0,seqSize,outputType);
          }else{
            var headStream=IntStream.range(0,seqSize);
            if(parallel){
              headStream=headStream.parallel();
            }
            headStream.forEach(head->outputTest.runTest(checkedType,head,seqSize,outputType));
          }
        });
      });
    }
  }
  interface InputTester{
    void runTest(CheckedType checkedType,int initialCapacity,int head,int initialSize,LongInputTestArgType inputType);
  }
  static void runInputTests(boolean parallel,InputTester inputTester)
  {
    for(var checkedType:CheckedType.values()){
      var capacityStream=IntStream.rangeClosed(0,15);
      if(parallel){
        capacityStream=capacityStream.parallel();
      }
      capacityStream.forEach(capacity->{
        var seqSizeStream=IntStream.rangeClosed(0,capacity);
        if(parallel)
        {
          seqSizeStream=seqSizeStream.parallel();
        }
        seqSizeStream.forEach(seqSize->{
          var inputArgTypeStream=Stream.of(LongInputTestArgType.values());
          if(parallel)
          {
            inputArgTypeStream=inputArgTypeStream.parallel();
          }
          inputArgTypeStream.forEach(inputType->{
             if(seqSize==0)
             {
               inputTester.runTest(checkedType,capacity,0,seqSize,inputType);
             }
             else
             {
               var headStream=IntStream.range(0,capacity);
               if(parallel){
                 headStream=headStream.parallel();
               }
               headStream.forEach(head->{
                 inputTester.runTest(checkedType,capacity,head,seqSize,inputType);
               });
             }
          });
        });
      });
    }
  }
  interface QueryTest
  {
    void runTest(CheckedType checkedType,QueryTester argType,QueryCastType queryCastType,SequenceLocation seqLocation,int seqSize
    );
  }
  static void runQueryTests(boolean parallel,QueryTest queryTest){
    for(var checkedType:CheckedType.values()){
      var seqLocationStream=Stream.of(SequenceLocation.values()).filter(seqLocation->seqLocation!=SequenceLocation.IOBLO);
      if(parallel){
        seqLocationStream=seqLocationStream.parallel();
      }
      seqLocationStream.forEach(seqLocation->{
        for(int tmpSeqSize=0;tmpSeqSize<=10;++tmpSeqSize){
          final int seqSize=tmpSeqSize;
          if(seqLocation==SequenceLocation.IOBHI || seqSize!=0){
            for(var queryCastType:QueryCastType.values()){
              var argTypeStream=Stream.of(QueryTester.values());
              if(parallel){
                argTypeStream=argTypeStream.parallel();
              }
              argTypeStream.forEach(argType->{
                switch(argType){
                  case Booleannull:
                  case Bytenull:
                  case Characternull:
                  case Shortnull:
                  case Integernull:
                  case Longnull:
                  case Floatnull:
                  case Doublenull:
                    if(queryCastType!=QueryCastType.ToBoxed || (seqSize!=0 && seqLocation.expectedException==null)){
                      return;
                    }
                    break;
                  case Objectnull:
                    if(queryCastType!=QueryCastType.ToObject || (seqSize!=0 && seqLocation.expectedException==null)){
                      return;
                    }
                    break;
                  case Booleanfalse:
                  case Byte0:
                  case Character0:
                  case Short0:
                  case Integer0:
                  case Long0:
                  case Floatpos0:
                  case Floatneg0:
                  case Doublepos0:
                  case Doubleneg0:
                  case Booleantrue:
                  case Bytepos1:
                  case Characterpos1:
                  case Shortpos1:
                  case Integerpos1:
                  case Longpos1:
                  case Floatpos1:
                  case Doublepos1:
                  //values beyond the range of boolean
                  case Bytepos2:
                  case Characterpos2:
                  case Shortpos2:
                  case Integerpos2:
                  case Longpos2:
                  case Floatpos2:
                  case Doublepos2:
                  //negative values beyond the range of char
                  case Byteneg1:
                  case Shortneg1:
                  case Integerneg1:
                  case Longneg1:
                  case Floatneg1:
                  case Doubleneg1:
                  //negative values beyond the range of byte
                  case ShortMIN_BYTE_MINUS1:
                  case IntegerMIN_BYTE_MINUS1:
                  case LongMIN_BYTE_MINUS1:
                  case FloatMIN_BYTE_MINUS1:
                  case DoubleMIN_BYTE_MINUS1:
                  //negative values beyond the range of short
                  case IntegerMIN_SHORT_MINUS1:
                  case LongMIN_SHORT_MINUS1:
                  case FloatMIN_SHORT_MINUS1:
                  case DoubleMIN_SHORT_MINUS1:
                  //negative values beyond the range of int
                  case FloatMIN_INT_MINUS1:
                  //negative values beyond the range of int and beyond the precision of float
                  case LongMIN_INT_MINUS1:
                  case DoubleMIN_INT_MINUS1:
                  //negative values beyond MIN_SAFE_INT that are beyond the precision of float
                  case IntegerMIN_SAFE_INT_MINUS1:
                  case LongMIN_SAFE_INT_MINUS1:
                  case DoubleMIN_SAFE_INT_MINUS1:
                  //negative values beyond the range of int that are beyond the precision of float and double
                  case LongMIN_SAFE_LONG_MINUS1:
                  //positive values out of the range of byte
                  case CharacterMAX_BYTE_PLUS1:
                  case ShortMAX_BYTE_PLUS1:
                  case IntegerMAX_BYTE_PLUS1:
                  case LongMAX_BYTE_PLUS1:
                  case FloatMAX_BYTE_PLUS1:
                  case DoubleMAX_BYTE_PLUS1:
                  //positive values out of the range of short
                  case CharacterMAX_SHORT_PLUS1:
                  case IntegerMAX_SHORT_PLUS1:
                  case LongMAX_SHORT_PLUS1:
                  case FloatMAX_SHORT_PLUS1:
                  case DoubleMAX_SHORT_PLUS1:
                  //positive values out of the range of char
                  case IntegerMAX_CHAR_PLUS1:
                  case LongMAX_CHAR_PLUS1:
                  case FloatMAX_CHAR_PLUS1:
                  case DoubleMAX_CHAR_PLUS1:
                  //positive values out of the range of int
                  case LongMAX_INT_PLUS1:
                  case FloatMAX_INT_PLUS1:
                  case DoubleMAX_INT_PLUS1:
                  //positive values beyond MAX_SAFE_INT that are beyond the precision of float
                  case IntegerMAX_SAFE_INT_PLUS1:
                  case LongMAX_SAFE_INT_PLUS1:
                  case DoubleMAX_SAFE_INT_PLUS1:
                  //positive values beyond the range of int that are beyond the precision of float and double
                  case LongMAX_SAFE_LONG_PLUS1:
                  //these input values cannot potentially return true
                  break;
                  default:
                  if(seqSize!=0 && seqLocation.expectedException==null){
                    return;
                  }
                  //these values must necessarily return false
                }
                queryTest.runTest(checkedType,argType,queryCastType,seqLocation,seqSize);
              });
            }
          }
        }
      });
    }
  }
  private static void initializeAscending(long[] arr,int head,int size)
  {
    if(size!=0)
    {
      for(int i=0,arrLength=arr.length;i<size;++i)
      {
        arr[head]=TypeConversionUtil.convertTolong(i);
        if(++head==arrLength)
        {
          head=0;
        }
      }
    }
  }
  private static int initializeArrayForQuery(boolean forwardIteration,long[] arr,int head,int size,QueryTester argType,SequenceLocation seqLocation
  ){
    int capacity=arr.length;
    switch(seqLocation){
      case IOBHI:
        {
          for(int i=0,dst=head;i<size;++i)
          {
            argType.setNotEqualsVal(arr,dst);
            if(++dst==capacity)
            {
              dst=0;
            }
          }
        }
        return size;
      case BEGINNING:
        {
          int dst;
          argType.setEqualsVal(arr,dst=head);
          for(int i=1;i<size;++i)
          {
            if(++dst==capacity){
              dst=0;
            }
            argType.setNotEqualsVal(arr,dst);
          }
        }
        if(forwardIteration)
        {
          return 1;
        }
        else
        {
          return size;
        }
      case NEARBEGINNING:
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=size>>2;i<bound;++i)
          {
            argType.setNotEqualsVal(arr,dst);
            if(++dst==capacity)
            {
              dst=0;
            }
          }
          argType.setEqualsVal(arr,dst);
          for(;++i<size;)
          {
            if(++dst==capacity)
            {
              dst=0;
            }
            argType.setNotEqualsVal(arr,dst);
          }
        }
        if(forwardIteration)
        {
          return (size>>2)+1;
        }
        else
        {
          return size-((size>>2));
        }
      case MIDDLE:
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=size>>1;i<bound;++i)
          {
            argType.setNotEqualsVal(arr,dst);
            if(++dst==capacity)
            {
              dst=0;
            }
          }
          argType.setEqualsVal(arr,dst);
          for(;++i<size;)
          {
            if(++dst==capacity)
            {
              dst=0;
            }
            argType.setNotEqualsVal(arr,dst);
          }
        }
        if(forwardIteration)
        {
          return (size>>1)+1;
        }
        else
        {
          return size-(size>>1);
        }
      case NEAREND:
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=(size>>2)*3;i<bound;++i)
          {
            argType.setNotEqualsVal(arr,dst);
            if(++dst==capacity)
            {
              dst=0;
            }
          }
          argType.setEqualsVal(arr,dst);
          for(;++i<size;)
          {
            if(++dst==capacity)
            {
              dst=0;
            }
            argType.setNotEqualsVal(arr,dst);
          }
        }
        if(forwardIteration)
        {
          return ((size>>2)*3)+1;
        }
        else
        {
          return (size)-(size>>2)*3;
        }
      case END:
        {
          int i,dst,bound;
          for(i=0,dst=head,bound=size-1;i<bound;++i)
          {
            argType.setNotEqualsVal(arr,dst);
            if(++dst==capacity){
              dst=0;
            }
          }
          argType.setEqualsVal(arr,dst);
        }
        if(forwardIteration)
        {
          return size;
        }
        else
        {
          return 1;
        }
      default:
        throw new Error("Unknown seqLocation "+seqLocation);
    }
  }
  static enum NestedType{
    DEQUE;
    final boolean rootType=true;
    final boolean forwardIteration=true;
  }
  private static class SeqMonitor extends AbstractLongSeqMonitor<LongArrDeq>{
    private final NestedType nestedType=NestedType.DEQUE;
    //private int expectedTail;
    //private int expectedHead;
    SeqMonitor(CheckedType checkedType,int head,long[] arr,int tail){
      super(checkedType);
      this.seq=checkedType.checked?new LongArrDeq.Checked(head,arr,tail):new LongArrDeq(head,arr,tail);
      //this.expectedHead=head;
      //this.expectedTail=tail;
      if(tail==-1){
        this.expectedSeqSize=0;
      }else{
        this.expectedSeqSize=tail-head+1;
        if(expectedSeqSize<=0){
          this.expectedSeqSize+=arr.length;
        }
      }
    }
    SeqMonitor(CheckedType checkedType,int capacity,int head,int size)
    {
      super(checkedType);
      if(size==0)
      {
        this.seq=checkedType.checked?new LongArrDeq.Checked(capacity):new LongArrDeq(capacity);
      }
      else
      {
        Assertions.assertTrue(size<=capacity,"The expression (size="+size+")<=(capacity="+capacity+") evaluates to false");
        int tail=head+size-1;
        long[] arr=new long[capacity];
        if(tail>=capacity)
        {
          tail-=capacity;
        }
        this.seq=checkedType.checked?new LongArrDeq.Checked(head,arr,tail):new LongArrDeq(head,arr,tail);
        this.expectedSeqSize=size;
      }
    }
    SeqMonitor(CheckedType checkedType,int initialCapacity){
      super(checkedType);
      this.seq=checkedType.checked?new LongArrDeq.Checked(initialCapacity):new LongArrDeq(initialCapacity);
      //this.expectedTail=-1;
    }
    SeqMonitor(CheckedType checkedType){
      super(checkedType);
      this.seq=checkedType.checked?new LongArrDeq.Checked():new LongArrDeq();
      //this.expectedTail=-1;
    }
    AbstractItrMonitor getItrMonitor(){
      if(checkedType.checked){
        return new CheckedAscendingItrMonitor();
      }
      return new UncheckedAscendingItrMonitor();
    }
    class UncheckedAscendingItrMonitor extends AbstractLongSeqMonitor.AbstractItrMonitor{
      int expectedCursor;
      UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfLong itr,int cursor){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCursor=cursor;
      }
      UncheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),expectedSeqModCount);
        this.expectedCursor=seq.tail==-1?-1:seq.head;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int tail=seq.tail;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((LongConsumer)action);
        }
        if(tail!=-1){
          this.expectedCursor=-1;
        }
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      int getNewCursor(){
        return expectedCursor==seq.tail?-1:expectedCursor+1==seq.arr.length?0:expectedCursor+1;
      }
      void verifyNext(int expectedVal,LongOutputTestArgType outputType){
        int newCursor=getNewCursor();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCursor=newCursor;
      }
      void iterateForward(){
        int newCursor=getNewCursor();
        itr.nextLong();
        expectedCursor=newCursor;
      }
      int getRemoveCursor(){
        int newCursor;
        switch(newCursor=expectedCursor){
          case 0:
            int arrBound;
            if(seq.tail<(arrBound=seq.arr.length-1)-seq.head){
              newCursor=arrBound;
            }
            break;
          default:
            int tail,head;
            arrBound=newCursor-1;
            if((tail=seq.tail)<(head=seq.head)){
              int headDist;
              if((headDist=arrBound-head)>=0){
                if(headDist>seq.arr.length-arrBound+tail){
                  newCursor=arrBound;
                }
              }else if(tail-arrBound<=seq.arr.length-head+arrBound){
                newCursor=arrBound;
              }
            }else if(arrBound-head>tail-arrBound){
              newCursor=arrBound;
            }
          case -1:
        }
        return newCursor;
      }
      void remove(){
        int newCursor=getRemoveCursor();
        itr.remove();
        verifyRemoval();
        this.expectedCursor=newCursor;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrDeq.AscendingItr.cursor(itr));
        Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrDeq.AscendingItr.root(itr));
      }
    }
    class UncheckedDescendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,seq.descendingIterator(),seq.tail);
      }
      int getNewCursor(){
        return expectedCursor==seq.head?-1:expectedCursor==0?seq.arr.length-1:expectedCursor-1;
      }
      int getRemoveCursor(){
        int newCursor;
        if((newCursor=expectedCursor)!=-1){
          int head,tail;
          if((tail=seq.tail)<(head=seq.head)){
            int arrBound;
            if(expectedCursor==(arrBound=seq.arr.length-1)){
              if(tail>arrBound-head){
                newCursor=0;
              }
            }else{
              int headDist,tmp;
              if((headDist=(tmp=newCursor+1)-head)>=0){
                if(headDist<=arrBound-tmp){
                  newCursor=tmp;
                }
              }else if(tail-tmp>arrBound-head+tmp){
                newCursor=tmp;
              }
            }
          }else{
            int tmp;
            if(tail-(tmp=newCursor+1)>tmp-head){
              newCursor=tmp;
            }
          }
        }
        return newCursor;
      }
    }
    class CheckedAscendingItrMonitor extends UncheckedAscendingItrMonitor{
      int expectedLastRet;
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfLong itr,int cursor){
        super(itrType,itr,cursor);
        this.expectedLastRet=-1;
      }
      CheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),seq.tail==-1?-1:seq.head);
        this.expectedLastRet=-1;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int tail=seq.tail;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((LongConsumer)action);
        }
        if(tail!=-1){
          this.expectedLastRet=tail;
          this.expectedCursor=-1;
        }
      }
      void verifyNext(int expectedVal,LongOutputTestArgType outputType){
        int newCursor=getNewCursor();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCursor;
        expectedCursor=newCursor;
      }
      void iterateForward(){
        int newCursor=getNewCursor();
        itr.nextLong();
        expectedLastRet=expectedCursor;
        expectedCursor=newCursor;
      }
      int getRemoveCursor(){
        int newCursor=expectedCursor;
        int head,tail;
        switch(Integer.signum((tail=seq.tail)-(head=seq.head)))
        {
          case -1:
          {
            int headDist;
            if((headDist=expectedLastRet-head)>=0){
              if(headDist>seq.arr.length-expectedLastRet+tail){
                newCursor=expectedLastRet;
              }
            }else if(tail-expectedLastRet<=seq.arr.length-head+expectedLastRet){
              newCursor=expectedLastRet;
            }
            break;
          }
          default:
          {
            if(expectedLastRet-head>tail-expectedLastRet){
              newCursor=expectedLastRet;
            }
          }
          case 0:
        }
        return newCursor;
      }
      void remove(){
        int newCursor=getRemoveCursor();
        itr.remove();
        verifyRemoval();
        this.expectedCursor=newCursor;
        this.expectedLastRet=-1;
        ++this.expectedItrModCount;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.LongArrDeq.Checked.AscendingItr.modCount(itr));
        Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.LongArrDeq.Checked.AscendingItr.cursor(itr));
        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.LongArrDeq.Checked.AscendingItr.lastRet(itr));
        Assertions.assertSame(seq,FieldAndMethodAccessor.LongArrDeq.Checked.AscendingItr.root(itr));
      }
    }
    class CheckedDescendingItrMonitor extends CheckedAscendingItrMonitor{
      int expectedLastRet;
      int expectedCursor;
      CheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,seq.descendingIterator(),expectedSeqModCount);
        this.expectedCursor=seq.tail;
        this.expectedLastRet=-1;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int tail=seq.tail;
        int head=seq.head;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((LongConsumer)action);
        }
        if(tail!=-1){
          this.expectedLastRet=head;
          this.expectedCursor=-1;
        }
      }
      int newCursor(){
        return expectedCursor==seq.head?-1:expectedCursor==0?seq.arr.length-1:expectedCursor-1;
      }
      int getRemoveCursor(){
        int newCursor=expectedCursor;
        int head,tail;
        switch(Integer.signum((tail=seq.tail)-(head=seq.head)))
        {
          case -1:
          {
            int headDist;
            if((headDist=expectedLastRet-head)>=0){
              if(headDist<=seq.arr.length-expectedLastRet+tail){
                newCursor=expectedLastRet;
              }
            }else if(tail-expectedLastRet>seq.arr.length-head+expectedLastRet){
              newCursor=expectedLastRet;
            }
            break;
          }
          default:
          {
            if(expectedLastRet-head<tail-expectedLastRet){
              newCursor=expectedLastRet;
            }
          }
          case 0:
        }
        return newCursor;
      }
    }
    AbstractItrMonitor getDescendingItrMonitor(){
      if(checkedType.checked){
        return new CheckedDescendingItrMonitor();
      }
      return new UncheckedDescendingItrMonitor();
    }
    SequenceVerificationItr verifyPreAlloc(int expectedVal){
      return verifyPreAlloc();
    }
    SequenceVerificationItr verifyPreAlloc(){
      return new ArrDeqVerificationItr(seq.head,this);
    }
    private static class ArrDeqVerificationItr extends SequenceVerificationItr{
      int currIndex;
      final SeqMonitor seqMonitor;
      ArrDeqVerificationItr(int currIndex,SeqMonitor seqMonitor){
        this.currIndex=currIndex;
        this.seqMonitor=seqMonitor;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,LongInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(long val){
        Assertions.assertEquals(val,seqMonitor.seq.arr[currIndex]);
        if(++currIndex==seqMonitor.seq.arr.length){
          currIndex=0;
        }
      }
      private int getReverseIndex(){
        if(currIndex==0){
          return seqMonitor.seq.arr.length-1;
        }
        return currIndex-1;
      }
      @Override void reverseAndVerifyIndex(LongInputTestArgType inputArgType,int val){
        int currIndex;
        inputArgType.verifyVal(val,seqMonitor.seq.arr[(currIndex=getReverseIndex())]);
        this.currIndex=currIndex;
      }
      @Override void verifyIndexAndIterate(LongInputTestArgType inputArgType,int val){ 
        inputArgType.verifyVal(val,seqMonitor.seq.arr[currIndex]);
        if(++currIndex==seqMonitor.seq.arr.length){
          currIndex=0;
        }
      }
      @Override SequenceVerificationItr getOffset(int i){
        int index=currIndex+i;
        if(i>0){
          if(index<0){
            index+=seqMonitor.seq.arr.length;
          }
        }else{
          int arrLength;
          if(index>=(arrLength=seqMonitor.seq.arr.length)){
            index-=arrLength;
          }
        }
        return new ArrDeqVerificationItr(index,seqMonitor);
      }
      @Override SequenceVerificationItr skip(int i){
        currIndex+=i;
        if(i>0){
          if(currIndex<0){
            currIndex+=seqMonitor.seq.arr.length;
          }
        }else{
          int arrLength;
          if(currIndex>=(arrLength=seqMonitor.seq.arr.length)){
            currIndex-=arrLength;
          }
        }
        return this;
      }
      @Override public boolean equals(Object val){
        ArrDeqVerificationItr thatItr;
        return val==this || (val instanceof ArrDeqVerificationItr && (thatItr=(ArrDeqVerificationItr)val).currIndex==this.currIndex && thatItr.seqMonitor.seq==seqMonitor.seq);
      }
      @Override SequenceVerificationItr verifyRootPostAlloc(){
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        return this;
      }
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario){
        case ModSeq:
          LongInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          verifyAddition();
          break;
        case NoMod:
          break;
        default:
          throw new Error("Unknown preModScenario "+preModScenario);
      }
    }
    void verifyAddition(){
      ++expectedSeqSize;
      ++expectedSeqModCount;
    }
    public String toString(){
      return new StringBuilder("LongArrDeq").append(checkedType.checked?".Checked{":"{").append(seq.head).append(',').append(expectedSeqSize).append(',').append(seq.tail).append('}').toString();
    }
    void verifyStructuralIntegrity(){
      if(checkedType.checked){
        Assertions.assertEquals(expectedSeqModCount,((LongArrDeq.Checked)seq).modCount);
      }
      if(expectedSeqSize==0)
      {
        Assertions.assertEquals(-1,seq.tail);
      }
      else
      {
        int size;
        if((size=seq.tail-seq.head+1)<=0){
          size+=seq.arr.length;
        }else{
        }
        Assertions.assertEquals(expectedSeqSize,size);
      }
    }
    void verifyFunctionalModification(){
      ++expectedSeqModCount;
    }
    void verifyBatchRemove(int numRemoved){
      expectedSeqSize-=numRemoved;
    }
    void writeObject(ObjectOutputStream oos) throws IOException{
      if(checkedType.checked){
        FieldAndMethodAccessor.LongArrDeq.Checked.writeObject(seq,oos);
      }else{
        FieldAndMethodAccessor.LongArrDeq.writeObject(seq,oos);
      }
    }
    void verifyRemoval(){
      --expectedSeqSize;
      ++expectedSeqModCount;
    }
  }
}
