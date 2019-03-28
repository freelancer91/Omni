package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqTest{
//IF OfRef
  private static void verifyRangeIsNull(Object[] arr,int offset,int bound)
  {
    for(int i=offset;i<bound;++i)
    {
      Assertions.assertNull(arr[i]);
    }
  }
//ENDIF
  private static void verifyAscendingSpanObject(Object[] arr,int offset,int bound,int loVal)
    {
      for(int i=offset;i<bound;++i,++loVal)
      {
        Assertions.assertEquals(TypeConversionUtil.convertToObject(loVal),arr[i]);
      }
    }
//IF STRUCT==Stack,List
  @Test
  public void testUncheckedListConstructor_happyPath()
  {
    var seq=new RefArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testUncheckedListConstructor_int_Objectarray_happyPath()
  {
    int size=5;
    Object[] arr=new Object[10];
    var seq=new RefArrSeq.UncheckedList(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testUncheckedListConstructor_int_happyPath(int capacity)
  {
    var seq=new RefArrSeq.UncheckedList(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
      default:
      Assertions.assertEquals(capacity,seq.arr.length);
  //IF OfRef
      verifyRangeIsNull(seq.arr,0,capacity);
  //ENDIF
    }
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
//ENDIF
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Object_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previous();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(--val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Object_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Object_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previous();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Object_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(--val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Object_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Object_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_Object_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack,List
  @Test
  public void testUncheckedStackConstructor_happyPath()
  {
    var seq=new RefArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testUncheckedStackConstructor_int_Objectarray_happyPath()
  {
    int size=5;
    Object[] arr=new Object[10];
    var seq=new RefArrSeq.UncheckedStack(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testUncheckedStackConstructor_int_happyPath(int capacity)
  {
    var seq=new RefArrSeq.UncheckedStack(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
      default:
      Assertions.assertEquals(capacity,seq.arr.length);
  //IF OfRef
      verifyRangeIsNull(seq.arr,0,capacity);
  //ENDIF
    }
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
//ENDIF
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_Object_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_Object_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Object_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.UncheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previous();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(--val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Object_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.UncheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Object_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.UncheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previous();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Object_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.UncheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(--val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Object_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.UncheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Object_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.UncheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_Object_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.UncheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack,List
  @Test
  public void testCheckedListConstructor_happyPath()
  {
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testCheckedListConstructor_int_Objectarray_happyPath()
  {
    int size=5;
    Object[] arr=new Object[10];
    var seq=new RefArrSeq.CheckedList(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testCheckedListConstructor_int_happyPath(int capacity)
  {
    var seq=new RefArrSeq.CheckedList(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
      default:
      Assertions.assertEquals(capacity,seq.arr.length);
  //IF OfRef
      verifyRangeIsNull(seq.arr,0,capacity);
  //ENDIF
    }
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
//ENDIF
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Object_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previous();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(--val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Object_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Object_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previous();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Object_throwCME(int initialCapacity){
    //ENDIF
    {
      //try on an empty sublist
//IF 
      int rootPreAlloc=0;
      int parentPreAlloc=0;
      int parentPostAlloc=0;
      int rootPostAlloc=0;
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      var seq=new RefArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToObject(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF 
      int rootPreAlloc=0;
      int parentPreAlloc=0;
      int parentPostAlloc=0;
      int rootPostAlloc=0;
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      var seq=new RefArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToObject(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF 
      int rootPreAlloc=0;
      int parentPreAlloc=0;
      int parentPostAlloc=0;
      int rootPostAlloc=0;
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      var seq=new RefArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToObject(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF 
      int rootPreAlloc=0;
      int parentPreAlloc=0;
      int parentPostAlloc=0;
      int rootPostAlloc=0;
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      var seq=new RefArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToObject(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToObject(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanObject(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF 
      int rootPreAlloc=0;
      int parentPreAlloc=0;
      int parentPostAlloc=0;
      int rootPostAlloc=0;
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      var seq=new RefArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToObject(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToObject(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF 
      int rootPreAlloc=0;
      int parentPreAlloc=0;
      int parentPostAlloc=0;
      int rootPostAlloc=0;
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      var seq=new RefArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToObject(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToObject(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Object_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(--val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Object_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Object_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Object_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToObject(0)));
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_Object_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack,List
  @Test
  public void testCheckedStackConstructor_happyPath()
  {
    var seq=new RefArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testCheckedStackConstructor_int_Objectarray_happyPath()
  {
    int size=5;
    Object[] arr=new Object[10];
    var seq=new RefArrSeq.CheckedStack(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testCheckedStackConstructor_int_happyPath(int capacity)
  {
    var seq=new RefArrSeq.CheckedStack(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
      default:
      Assertions.assertEquals(capacity,seq.arr.length);
  //IF OfRef
      verifyRangeIsNull(seq.arr,0,capacity);
  //ENDIF
    }
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
//ENDIF
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_Object_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_Object_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new RefArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Object_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.CheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previous();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(--val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Object_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.CheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Object_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.CheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToObject(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previous();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Object_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToObject(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToObject(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToObject(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToObject(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToObject(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanObject(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToObject(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToObject(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToObject(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToObject(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToObject(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Object_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.CheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(--val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Object_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.CheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Object_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.CheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToObject(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
    verifyAscendingSpanObject(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Object_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.CheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToObject(0)));
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanObject(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Object_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToObject(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToObject(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToObject(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToObject(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToObject(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToObject(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToObject(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToObject(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToObject(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanObject(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToObject(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToObject(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToObject(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Object_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToObject(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToObject(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToObject(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToObject(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToObject(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToObject(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToObject(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToObject(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToObject(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToObject(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToObject(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToObject(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToObject(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToObject(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToObject(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToObject(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToObject(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToObject(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToObject(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToObject(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanObject(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      RefArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new RefArrSeq.CheckedList();
      }
      else
      {
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new RefArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToObject(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToObject(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToObject(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToObject(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToObject(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToObject(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanObject(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanObject(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Object_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    RefArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new RefArrSeq.CheckedList();
    }
    else
    {
      Object[] arr=new Object[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new RefArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),root.arr[i]);
    }
  }
  private static final Arguments[] SUB_LIST_CONSTRUCTION_ARGS;
  static
  {
    Arguments[] args=new Arguments[16];
    int dstOffset=0;
    for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5)
    {
      for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5)
      {
        for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5)
        {
          for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5,++dstOffset)
          {
            args[dstOffset]=Arguments.of(rootPreAlloc,rootPostAlloc,parentPreAlloc,parentPostAlloc);
          }
        }
      }
    }
    SUB_LIST_CONSTRUCTION_ARGS=args;
  }
  private static Stream<Arguments> getSubListConstructionArgs()
  {
    return Stream.of(SUB_LIST_CONSTRUCTION_ARGS);
  }
  private static void initAscendingArray(Object[] arr,int offset,int lo,int hi)
  {
    int bound=offset+(hi-lo);
    for(int i=offset;i<bound;++i,++lo)
    {
      arr[i]=TypeConversionUtil.convertToObject(lo);
    }
  }
  private static void assertIteratorStateIntegrity(Object itr,int expectedCursor,int expectedLastRet,int expectedItrModCount,Object expectedParent,Object expectedRoot)
  {
    int actualCursor;
    Object actualParent;
    if(expectedParent==expectedRoot)
    {
      if(expectedRoot instanceof OmniStack.OfRef)
      {
        if(expectedRoot instanceof RefArrSeq.CheckedStack)
        {
          actualCursor=FieldAccessor.RefArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.RefArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet,FieldAccessor.RefArrSeq.CheckedStack.Itr.lastRet(itr));
        }
        else
        {
          actualCursor=FieldAccessor.RefArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.UncheckedStack.Itr.parent(itr);
        }
      }
      else
      {
        if(expectedRoot instanceof RefArrSeq.CheckedList)
        {
          actualCursor=FieldAccessor.RefArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.RefArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet,FieldAccessor.RefArrSeq.CheckedList.Itr.lastRet(itr));
        }
        else
        {
          actualCursor=FieldAccessor.RefArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.UncheckedList.Itr.parent(itr);
          //skip the lastRet check since the unchecked iterator does not guarantee its state
          //if(itr instanceof OmniListIterator.OfRef)
          //{
          //  Assertions.assertEquals(expectedLastRet,FieldAccessor.RefArrSeq.UncheckedList.ListItr.lastRet(itr));
          //}
        }
      }
    }
    else
    {
      if(expectedRoot instanceof RefArrSeq.CheckedList)
      {
        actualCursor=FieldAccessor.RefArrSeq.CheckedSubList.Itr.cursor(itr);
        actualParent=FieldAccessor.RefArrSeq.CheckedSubList.Itr.parent(itr);
        Assertions.assertEquals(expectedItrModCount,FieldAccessor.RefArrSeq.CheckedSubList.Itr.modCount(itr));
        Assertions.assertEquals(expectedLastRet,FieldAccessor.RefArrSeq.CheckedSubList.Itr.lastRet(itr));
      }
      else
      {
        actualCursor=FieldAccessor.RefArrSeq.UncheckedSubList.Itr.cursor(itr);
        actualParent=FieldAccessor.RefArrSeq.UncheckedSubList.Itr.parent(itr);
        //skip the lastRet check since the unchecked iterator does not guarantee its state
        //if(itr instanceof OmniListIterator.OfRef)
        //{
        //  Assertions.assertEquals(expectedLastRet,FieldAccessor.RefArrSeq.UncheckedSubList.ListItr.lastRet(itr));
        //}
      }
    }
    Assertions.assertEquals(expectedCursor,actualCursor);
    Assertions.assertSame(expectedParent,actualParent);
  }
  private static RefArrSeq assertStructuralIntegrity(Object seq,int expectedSeqSize,int expectedSeqModCount,Object expectedParent,int expectedParentSize,int expectedParentModCount,RefArrSeq expectedRoot,int expectedRootSize,int expectedRootModCount)
  {
    if(seq==expectedRoot)
    {
      if(seq instanceof RefArrSeq.CheckedList)
      {
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.RefArrSeq.CheckedList.modCount(seq));
      }
      else if(seq instanceof RefArrSeq.CheckedStack)
      {
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.RefArrSeq.CheckedStack.modCount(seq));
      }
    }
    else
    {
      OmniList.OfRef actualSeqParent;
      Object actualSeqRoot;
      OmniList.OfRef actualParentParent;
      Object actualParentRoot;
      int actualParentSize;
      int actualSeqSize;
      if(expectedRoot instanceof RefArrSeq.CheckedList)
      {
        actualSeqParent=FieldAccessor.RefArrSeq.CheckedSubList.parent(seq);
        actualSeqRoot=FieldAccessor.RefArrSeq.CheckedSubList.root(seq);
        actualParentParent=FieldAccessor.RefArrSeq.CheckedSubList.parent(expectedParent);
        actualParentRoot=FieldAccessor.RefArrSeq.CheckedSubList.root(expectedParent);
        actualSeqSize=FieldAccessor.RefArrSeq.CheckedSubList.size(seq);
        actualParentSize=FieldAccessor.RefArrSeq.CheckedSubList.size(expectedParent);
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.RefArrSeq.CheckedSubList.modCount(seq));
        Assertions.assertEquals(expectedParentModCount,FieldAccessor.RefArrSeq.CheckedSubList.modCount(expectedParent));
        Assertions.assertEquals(expectedRootModCount,FieldAccessor.RefArrSeq.CheckedList.modCount(expectedRoot));
      }
      else
      {
        actualSeqParent=FieldAccessor.RefArrSeq.UncheckedSubList.parent(seq);
        actualSeqRoot=FieldAccessor.RefArrSeq.UncheckedSubList.root(seq);
        actualParentParent=FieldAccessor.RefArrSeq.UncheckedSubList.parent(expectedParent);
        actualParentRoot=FieldAccessor.RefArrSeq.UncheckedSubList.root(expectedParent);
        actualSeqSize=FieldAccessor.RefArrSeq.UncheckedSubList.size(seq);
        actualParentSize=FieldAccessor.RefArrSeq.UncheckedSubList.size(expectedParent);
      }
      Assertions.assertSame(expectedRoot,actualSeqRoot);
      Assertions.assertSame(expectedRoot,actualParentRoot);
      Assertions.assertSame(expectedParent,actualSeqParent);
      Assertions.assertNull(actualParentParent);
      Assertions.assertEquals(expectedSeqSize,actualSeqSize);
      Assertions.assertEquals(expectedParentSize,actualParentSize);
    }
    Assertions.assertEquals(expectedRootSize,FieldAccessor.RefArrSeq.size(expectedRoot));
    return expectedRoot;
  }
}
