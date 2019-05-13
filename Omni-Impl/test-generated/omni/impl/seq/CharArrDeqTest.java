package omni.impl.seq;
import omni.function.CharConsumer;
import java.util.Arrays;
import omni.util.ArrCopy;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.CharInputTestArgType;
import omni.impl.CharOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import omni.util.OmniArray;
import omni.impl.CharDblLnkNode;
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
import omni.impl.seq.AbstractCharSeqMonitor.CheckedType;
import omni.impl.seq.AbstractCharSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractCharSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractCharSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractCharSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractCharSeqMonitor.ListItrSetScenario;
import omni.impl.seq.AbstractCharSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractCharSeqMonitor.MonitoredRemoveIfPredicateGen;
import omni.impl.seq.AbstractCharSeqMonitor.QueryTester;
import omni.impl.seq.AbstractCharSeqMonitor.ItrType;
import omni.impl.seq.AbstractCharSeqMonitor.MonitoredComparatorGen;
import java.nio.file.Files;
import omni.impl.seq.AbstractCharSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import java.util.ArrayList;
import omni.api.OmniDeque;
import omni.api.OmniList;
import java.util.Comparator;
import omni.function.CharComparator;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("ArrDeqTest")
@Execution(ExecutionMode.CONCURRENT)
public class CharArrDeqTest{
  @org.junit.jupiter.api.Test
  public void testcontains_val(){
    runQueryTests(false,(checkedType,argType,queryCastType,seqLocation,seqSize
    )->{
      if(seqSize==0)
      {
        testcontains_valHelper(checkedType,seqSize,0,seqSize,seqLocation,argType,queryCastType
        );
      }
      else
      {
        IntStream.range(0,seqSize)
        .forEach(head->{
          testcontains_valHelper(checkedType,seqSize,head,seqSize,seqLocation,argType,queryCastType
          );
        });
      }
    });
  }
  private static void testcontains_valHelper(CheckedType checkedType,int capacity,int head,int numToAdd,SequenceLocation seqLocation,QueryTester argType,QueryCastType queryCastType
  ){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,capacity,head,numToAdd);
    if(numToAdd!=0){
      initializeArrayForQuery(true,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation);
    }
    Assertions.assertEquals(seqLocation!=SequenceLocation.IOBHI,argType.invokecontains(seqMonitor,queryCastType));
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testsearch_val(){
    runQueryTests(false,(checkedType,argType,queryCastType,seqLocation,seqSize
    )->{
      if(seqSize==0)
      {
        testsearch_valHelper(checkedType,seqSize,0,seqSize,seqLocation,argType,queryCastType
        );
      }
      else
      {
        IntStream.range(0,seqSize)
        .forEach(head->{
          testsearch_valHelper(checkedType,seqSize,head,seqSize,seqLocation,argType,queryCastType
          );
        });
      }
    });
  }
  private static void testsearch_valHelper(CheckedType checkedType,int capacity,int head,int numToAdd,SequenceLocation seqLocation,QueryTester argType,QueryCastType queryCastType
  ){
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,capacity,head,numToAdd);
    int expectedIndex;
    if(numToAdd!=0){
      expectedIndex=initializeArrayForQuery(true,seqMonitor.seq.arr,head,numToAdd,argType,seqLocation);
    }else{
      expectedIndex=-1;
    }
    if(seqLocation==SequenceLocation.IOBHI){
      expectedIndex=-1;
    }
    Assertions.assertEquals(expectedIndex,argType.invokesearch(seqMonitor,queryCastType));
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testhashCode_void(){
    runToStringOrHashCodeTests(false,CharArrDeqTest::testhashCode_voidHelper);
  }
  private static void testhashCode_voidHelper(CheckedType checkedType,int size,int head
  )
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,size,head,size);
    {
      initializeAscending(seqMonitor.seq.arr,head,size);
    }
    {
      int resultHash=seqMonitor.seq.hashCode();
      seqMonitor.verifyPreAlloc().verifyAscending(size);
      int expectedHash=1;
      if(size!=0)
      {
        char[] arr;
        for(int i=0,j=seqMonitor.seq.head,bound=(arr=seqMonitor.seq.arr).length;i<size;++i){
          expectedHash=(expectedHash*31)+(arr[j]);
          if(++j==bound){
            j=0;
          }
        }
      }
      Assertions.assertEquals(expectedHash,resultHash);
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testtoString_void(){
    runToStringOrHashCodeTests(false,CharArrDeqTest::testtoString_voidHelper);
  }
  private static void testtoString_voidHelper(CheckedType checkedType,int size,int head
  )
  {
    SeqMonitor seqMonitor=new SeqMonitor(checkedType,size,head,size);
    {
      initializeAscending(seqMonitor.seq.arr,head,size);
    }
    {
      var resultStr=seqMonitor.seq.toString();
      seqMonitor.verifyPreAlloc().verifyAscending(size);
      var arrList=new ArrayList<Object>();
      if(size!=0){
        for(int i=0,j=seqMonitor.seq.head,bound=seqMonitor.seq.arr.length;i<size;++i)
        {
          arrList.add(seqMonitor.seq.arr[j]);
          if(++j==bound)
          {
            j=0;
          }
        }
      }
      Assertions.assertEquals(arrList.toString(),resultStr);
    }
    seqMonitor.verifyStructuralIntegrity();
  }
  @org.junit.jupiter.api.Test
  public void testelement_void(){
    runOutputTests(false,true,CharArrDeqTest::testelement_voidHelper);
  }
  private static void testelement_voidHelper(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType outputArgType)
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
    runOutputTests(false,true,CharArrDeqTest::testgetFirst_voidHelper);
  }
  private static void testgetFirst_voidHelper(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType outputArgType)
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
    runOutputTests(false,true,CharArrDeqTest::testgetLast_voidHelper);
  }
  private static void testgetLast_voidHelper(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType outputArgType)
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
    runOutputTests(false,true,CharArrDeqTest::testremoveLast_voidHelper);
  }
  private static void testremoveLast_voidHelper(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType outputArgType)
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
    runOutputTests(false,true,CharArrDeqTest::testremoveFirst_voidHelper);
  }
  private static void testremoveFirst_voidHelper(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType outputArgType)
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
    runOutputTests(false,true,CharArrDeqTest::testremove_voidHelper);
  }
  private static void testremove_voidHelper(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType outputArgType)
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
    runOutputTests(false,true,CharArrDeqTest::testpop_voidHelper);
  }
  private static void testpop_voidHelper(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType outputArgType)
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
      .forEach(seqSize->{
        if(seqSize==0)
        {
          testclone_voidHelper(new SeqMonitor(checkedType,0,0,0));
        }
        else
        {
          IntStream.range(0,seqSize)
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
    var clone=(CharArrDeq)seqMonitor.seq.clone();
    if(seqMonitor.checkedType.checked){
      Assertions.assertEquals(0,((CharArrDeq.Checked)clone).modCount);
    }
    int seqSize;
    if((seqSize=seqMonitor.expectedSeqSize)==0)
    {
      Assertions.assertEquals(-1,clone.tail);
      Assertions.assertEquals(0,clone.head);
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,clone.arr);
    }
    else
    {
      Assertions.assertEquals(0,clone.head);
      Assertions.assertEquals(seqSize-1,clone.tail);
      char[] origArr,cloneArr=clone.arr;
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
    runOutputTests(false,true,CharArrDeqTest::testpoll_voidHelper);
  }
  private static void testpoll_voidHelper(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType outputArgType)
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
    runOutputTests(false,true,CharArrDeqTest::testpollFirst_voidHelper);
  }
  private static void testpollFirst_voidHelper(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType outputArgType)
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
    runOutputTests(false,true,CharArrDeqTest::testpollLast_voidHelper);
  }
  private static void testpollLast_voidHelper(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType outputArgType)
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
    runQueryTests(false,(checkedType,argType,queryCastType,seqLocation,seqSize
    )->{
      if(seqSize==0)
      {
        testremoveVal_valHelper(checkedType,seqSize,0,seqSize,seqLocation,argType,queryCastType
        );
      }
      else
      {
        IntStream.range(0,seqSize)
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
    runQueryTests(false,(checkedType,argType,queryCastType,seqLocation,seqSize
    )->{
      if(seqSize==0)
      {
        testremoveLastOccurrence_valHelper(checkedType,seqSize,0,seqSize,seqLocation,argType,queryCastType
        );
      }
      else
      {
        IntStream.range(0,seqSize)
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
    runInputTests(false,CharArrDeqTest::testadd_valHelper);
  }
  private static void testadd_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,CharInputTestArgType inputArgType)
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
    runInputTests(false,CharArrDeqTest::testaddLast_valHelper);
  }
  private static void testaddLast_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,CharInputTestArgType inputArgType)
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
    runInputTests(false,CharArrDeqTest::testpush_valHelper);
  }
  private static void testpush_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,CharInputTestArgType inputArgType)
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
    runInputTests(false,CharArrDeqTest::testaddFirst_valHelper);
  }
  private static void testaddFirst_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,CharInputTestArgType inputArgType)
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
    runInputTests(false,CharArrDeqTest::testofferFirst_valHelper);
  }
  private static void testofferFirst_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,CharInputTestArgType inputArgType)
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
    runInputTests(false,CharArrDeqTest::testoffer_valHelper);
  }
  private static void testoffer_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,CharInputTestArgType inputArgType)
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
  public void testofferLast_val(){
    runInputTests(false,CharArrDeqTest::testofferLast_valHelper);
  }
  private static void testofferLast_valHelper(CheckedType checkedType,int initialCapacity,int head,int initialSize,CharInputTestArgType inputArgType)
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
      var deq=new CharArrDeq.Checked();
      Assertions.assertEquals(0,deq.modCount);
      Assertions.assertEquals(-1,deq.tail);
      Assertions.assertEquals(0,deq.head);
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,deq.arr);
    }
    {
      var deq=new CharArrDeq();
      Assertions.assertEquals(-1,deq.tail);
      Assertions.assertEquals(0,deq.head);
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,deq.arr);
    }
  }
  @org.junit.jupiter.api.Test
  public void testConstructor_int(){
    for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5)
    {
      {
        var deq=new CharArrDeq.Checked(initialCapacity);
        Assertions.assertEquals(0,deq.modCount);
        Assertions.assertEquals(-1,deq.tail);
        Assertions.assertEquals(0,deq.head);
        switch(initialCapacity){
          case 0:
            Assertions.assertNull(deq.arr);
            break;
          case OmniArray.DEFAULT_ARR_SEQ_CAP:
            Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,deq.arr);
            break;
          default:
            Assertions.assertEquals(initialCapacity,deq.arr.length);
        }
      }
      {
        var deq=new CharArrDeq.Checked(initialCapacity);
        Assertions.assertEquals(-1,deq.tail);
        Assertions.assertEquals(0,deq.head);
        switch(initialCapacity){
          case 0:
            Assertions.assertNull(deq.arr);
            break;
          case OmniArray.DEFAULT_ARR_SEQ_CAP:
            Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,deq.arr);
            break;
          default:
            Assertions.assertEquals(initialCapacity,deq.arr.length);
        }
      }
    }
  }
  interface ToStringAndHashCodeTest{
    void runTest(CheckedType checkedType,int size,int head
    );
  }
  static void runToStringOrHashCodeTests(boolean parallel,ToStringAndHashCodeTest tester){
    for(var checkedType:CheckedType.values()){
      var seqSizeStream=IntStream.rangeClosed(0,10);
      if(parallel){
        seqSizeStream=seqSizeStream.parallel();
      }
      seqSizeStream.forEach(seqSize->{
        if(seqSize==0)
        {
          tester.runTest(checkedType,seqSize,0);
        }
        else
        {
          var headStream=IntStream.range(0,seqSize);
          if(parallel){
            headStream=headStream.parallel();
          }
          headStream.forEach(head->{
            tester.runTest(checkedType,seqSize,head);
          });
        }
      });
    }
  }
  interface OutputTester{
    void runTest(CheckedType checkedType,int head,int initialSize,CharOutputTestArgType inputType);
  }
  static void runOutputTests(boolean parallel,boolean allowEmpty,OutputTester outputTest){
    for(final var checkedType:CheckedType.values()){
      var seqSizeStream=IntStream.rangeClosed(0,10).filter(seqSize->allowEmpty||seqSize!=0||checkedType.checked);
      if(parallel){
        seqSizeStream=seqSizeStream.parallel();
      }
      seqSizeStream.forEach(seqSize->{
        var outputArgTypeStream=Stream.of(CharOutputTestArgType.values());
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
    void runTest(CheckedType checkedType,int initialCapacity,int head,int initialSize,CharInputTestArgType inputType);
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
          var inputArgTypeStream=Stream.of(CharInputTestArgType.values());
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
  private static void initializeAscending(char[] arr,int head,int size)
  {
    if(size!=0)
    {
      for(int i=0,arrLength=arr.length;i<size;++i)
      {
        arr[head]=TypeConversionUtil.convertTochar(i);
        if(++head==arrLength)
        {
          head=0;
        }
      }
    }
  }
  private static int initializeArrayForQuery(boolean forwardIteration,char[] arr,int head,int size,QueryTester argType,SequenceLocation seqLocation
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
  private static class SeqMonitor extends AbstractCharSeqMonitor<CharArrDeq>{
    //private int expectedTail;
    //private int expectedHead;
    SeqMonitor(CheckedType checkedType,int capacity,int head,int size)
    {
      super(checkedType);
      if(size==0)
      {
        this.seq=checkedType.checked?new CharArrDeq.Checked(capacity):new CharArrDeq(capacity);
      }
      else
      {
        Assertions.assertTrue(size<=capacity,"The expression (size="+size+")<=(capacity="+capacity+") evaluates to false");
        int tail=head+size-1;
        char[] arr=new char[capacity];
        if(tail>=capacity)
        {
          tail-=capacity;
        }
        this.seq=checkedType.checked?new CharArrDeq.Checked(head,arr,tail):new CharArrDeq(head,arr,tail);
        this.expectedSeqSize=size;
      }
    }
    SeqMonitor(CheckedType checkedType,int initialCapacity){
      super(checkedType);
      this.seq=checkedType.checked?new CharArrDeq.Checked(initialCapacity):new CharArrDeq(initialCapacity);
      //this.expectedTail=-1;
    }
    SeqMonitor(CheckedType checkedType){
      super(checkedType);
      this.seq=checkedType.checked?new CharArrDeq.Checked():new CharArrDeq();
      //this.expectedTail=-1;
    }
    AbstractItrMonitor getItrMonitor(){
      if(checkedType.checked){
        return new CheckedAscendingItrMonitor();
      }
      return new UncheckedAscendingItrMonitor();
    }
    class UncheckedAscendingItrMonitor extends AbstractCharSeqMonitor.AbstractItrMonitor{
      int expectedCursor;
      UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfChar itr,int cursor){
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
          itr.forEachRemaining((CharConsumer)action);
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
      void verifyNext(int expectedVal,CharOutputTestArgType outputType){
        int newCursor=getNewCursor();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCursor=newCursor;
      }
      void iterateForward(){
        int newCursor=getNewCursor();
        itr.nextChar();
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
        Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrDeq.AscendingItr.cursor(itr));
        Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrDeq.AscendingItr.root(itr));
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
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfChar itr,int cursor){
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
          itr.forEachRemaining((CharConsumer)action);
        }
        if(tail!=-1){
          this.expectedLastRet=tail;
          this.expectedCursor=-1;
        }
      }
      void verifyNext(int expectedVal,CharOutputTestArgType outputType){
        int newCursor=getNewCursor();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCursor;
        expectedCursor=newCursor;
      }
      void iterateForward(){
        int newCursor=getNewCursor();
        itr.nextChar();
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
        Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.CharArrDeq.Checked.AscendingItr.modCount(itr));
        Assertions.assertEquals(expectedCursor,FieldAndMethodAccessor.CharArrDeq.Checked.AscendingItr.cursor(itr));
        Assertions.assertEquals(expectedLastRet,FieldAndMethodAccessor.CharArrDeq.Checked.AscendingItr.lastRet(itr));
        Assertions.assertSame(seq,FieldAndMethodAccessor.CharArrDeq.Checked.AscendingItr.root(itr));
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
          itr.forEachRemaining((CharConsumer)action);
        }
        if(tail!=-1){
          this.expectedLastRet=head;
          this.expectedCursor=-1;
        }
      }
      int getNewCursor(){
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
      SequenceVerificationItr verifyNaturalAscending(int v,CharInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(char val){
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
      @Override void reverseAndVerifyIndex(CharInputTestArgType inputArgType,int val){
        int currIndex;
        inputArgType.verifyVal(val,seqMonitor.seq.arr[(currIndex=getReverseIndex())]);
        this.currIndex=currIndex;
      }
      @Override void verifyIndexAndIterate(CharInputTestArgType inputArgType,int val){ 
        inputArgType.verifyVal(val,seqMonitor.seq.arr[currIndex]);
        if(++currIndex==seqMonitor.seq.arr.length){
          currIndex=0;
        }
      }
      @Override SequenceVerificationItr getOffset(int i){
        int index=currIndex+1;
        int arrLength;
        if(index>=(arrLength=seqMonitor.seq.arr.length)){
          index-=arrLength;
        }else if(index<0){
          index+=arrLength;
        }
        return new ArrDeqVerificationItr(index,seqMonitor);
      }
      @Override SequenceVerificationItr skip(int i){
        currIndex+=i;
        int arrLength;
        if(currIndex>=(arrLength=seqMonitor.seq.arr.length)){
          currIndex-=arrLength;
        }else if(currIndex<0){
          currIndex+=arrLength;
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
          CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
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
      return new StringBuilder("CharArrDeq").append(checkedType.checked?".Checked{":"{").append(seq.head).append(',').append(expectedSeqSize).append(',').append(seq.tail).append('}').toString();
    }
    void verifyStructuralIntegrity(){
      if(checkedType.checked){
        Assertions.assertEquals(expectedSeqModCount,((CharArrDeq.Checked)seq).modCount);
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
        FieldAndMethodAccessor.CharArrDeq.Checked.writeObject(seq,oos);
      }else{
        FieldAndMethodAccessor.CharArrDeq.writeObject(seq,oos);
      }
    }
    void verifyRemoval(){
      --expectedSeqSize;
      ++expectedSeqModCount;
    }
  }
}
