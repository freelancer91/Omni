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
//IF OfRef
import omni.function.FloatConsumer;
//ENDIF
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class FloatArrSeqTest{
  private static void verifyAscendingSpanfloat(float[] arr,int offset,int bound,int loVal)
    {
      for(int i=offset;i<bound;++i,++loVal)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(loVal),arr[i]);
      }
    }
//IF OfBoolean,OfRef
  private static void verifyAscendingSpanfloatboolean(float[] arr,int offset,int bound,int loVal)
    {
      for(int i=offset;i<bound;++i,++loVal)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(loVal),arr[i]);
      }
    }
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testUncheckedListConstructor_happyPath()
  {
    var seq=new FloatArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testUncheckedListConstructor_int_floatarray_happyPath()
  {
    int size=5;
    float[] arr=new float[10];
    var seq=new FloatArrSeq.UncheckedList(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testUncheckedListConstructor_int_happyPath(int capacity)
  {
    var seq=new FloatArrSeq.UncheckedList(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.arr);
      break;
      default:
      Assertions.assertEquals(capacity,seq.arr.length);
    }
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
//ENDIF
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_float_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_float_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_float_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_float_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_float_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_float_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_float_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTofloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Float_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Float_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Float_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Float_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Float_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Float_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_Float_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToFloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
  //IF OfBoolean
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_boolean_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_boolean_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_boolean_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_boolean_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_boolean_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_boolean_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_boolean_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Boolean_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Boolean_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Boolean_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Boolean_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Boolean_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Boolean_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_Boolean_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
    //IF OfByte,OfChar
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_byte_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_byte_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_byte_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_byte_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_byte_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_byte_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_byte_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Byte_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Byte_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Byte_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Byte_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Byte_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Byte_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_Byte_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
      //IF OfShort
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_short_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_short_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_short_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_short_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_short_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_short_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_short_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Short_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Short_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Short_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Short_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Short_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Short_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_Short_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
        //IF OfInt
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_int_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_int_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_int_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_int_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_int_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_int_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Integer_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Integer_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Integer_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Integer_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Integer_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Integer_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_Integer_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //IF OfLong
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_long_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_long_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_long_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_long_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_long_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_long_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_long_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Long_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Long_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Long_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Long_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Long_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Long_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_Long_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToLong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //ENDIF
        //ENDIF
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testUncheckedStackConstructor_happyPath()
  {
    var seq=new FloatArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testUncheckedStackConstructor_int_floatarray_happyPath()
  {
    int size=5;
    float[] arr=new float[10];
    var seq=new FloatArrSeq.UncheckedStack(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testUncheckedStackConstructor_int_happyPath(int capacity)
  {
    var seq=new FloatArrSeq.UncheckedStack(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.arr);
      break;
      default:
      Assertions.assertEquals(capacity,seq.arr.length);
    }
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
//ENDIF
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_float_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTofloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_float_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTofloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_Float_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToFloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_Float_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToFloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
  //IF OfBoolean
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_boolean_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_boolean_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_Boolean_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_Boolean_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
    //IF OfByte,OfChar
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_byte_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_byte_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_Byte_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_Byte_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
      //IF OfShort
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_short_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_short_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_Short_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_Short_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
        //IF OfInt
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_int_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_int_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_Integer_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_Integer_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //IF OfLong
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_long_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_long_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_Long_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToLong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_Long_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToLong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //ENDIF
        //ENDIF
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_float_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_float_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_float_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_float_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_float_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_float_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_float_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTofloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Float_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Float_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Float_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Float_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Float_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Float_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_Float_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToFloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
  //IF OfBoolean
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_boolean_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_boolean_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_boolean_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_boolean_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_boolean_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_boolean_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_boolean_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Boolean_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Boolean_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Boolean_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Boolean_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Boolean_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Boolean_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_Boolean_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
    //IF OfByte,OfChar
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_byte_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_byte_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_byte_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_byte_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_byte_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_byte_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_byte_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Byte_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Byte_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Byte_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Byte_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Byte_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Byte_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_Byte_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
      //IF OfShort
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_short_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_short_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_short_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_short_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_short_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_short_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_short_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Short_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Short_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Short_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Short_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Short_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Short_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_Short_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
        //IF OfInt
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_int_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_int_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_int_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_int_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_int_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_int_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Integer_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Integer_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Integer_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Integer_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Integer_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Integer_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_Integer_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //IF OfLong
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_long_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_long_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_long_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_long_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_long_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_long_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_long_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Long_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Long_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Long_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Long_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Long_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Long_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_Long_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.UncheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToLong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //ENDIF
        //ENDIF
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testCheckedListConstructor_happyPath()
  {
    var seq=new FloatArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testCheckedListConstructor_int_floatarray_happyPath()
  {
    int size=5;
    float[] arr=new float[10];
    var seq=new FloatArrSeq.CheckedList(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testCheckedListConstructor_int_happyPath(int capacity)
  {
    var seq=new FloatArrSeq.CheckedList(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.arr);
      break;
      default:
      Assertions.assertEquals(capacity,seq.arr.length);
    }
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
//ENDIF
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_float_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_float_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_float_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_float_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTofloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTofloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertTofloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTofloat(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTofloat(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTofloat(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTofloat(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTofloat(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertTofloat(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_float_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_float_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_float_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_float_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertTofloat(0)));
      seq.add(TypeConversionUtil.convertTofloat(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_float_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTofloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Float_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Float_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Float_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Float_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToFloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToFloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToFloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToFloat(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToFloat(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToFloat(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToFloat(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToFloat(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToFloat(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Float_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Float_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Float_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Float_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToFloat(0)));
      seq.add(TypeConversionUtil.convertToFloat(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_Float_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToFloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
  //IF OfBoolean
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_boolean_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_boolean_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_boolean_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_boolean_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToboolean(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToboolean(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToboolean(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToboolean(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToboolean(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToboolean(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_boolean_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_boolean_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_boolean_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_boolean_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToboolean(0)));
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_boolean_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Boolean_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Boolean_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Boolean_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Boolean_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToBoolean(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToBoolean(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToBoolean(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToBoolean(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToBoolean(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToBoolean(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Boolean_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Boolean_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Boolean_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Boolean_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToBoolean(0)));
      seq.add(TypeConversionUtil.convertToBoolean(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_Boolean_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
    //IF OfByte,OfChar
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_byte_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_byte_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_byte_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_byte_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTobyte(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTobyte(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTobyte(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTobyte(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTobyte(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertTobyte(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_byte_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_byte_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_byte_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_byte_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertTobyte(0)));
      seq.add(TypeConversionUtil.convertTobyte(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_byte_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Byte_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Byte_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Byte_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Byte_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToByte(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToByte(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToByte(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToByte(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToByte(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToByte(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Byte_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Byte_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Byte_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Byte_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToByte(0)));
      seq.add(TypeConversionUtil.convertToByte(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_Byte_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
      //IF OfShort
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_short_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_short_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_short_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_short_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToshort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToshort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToshort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToshort(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToshort(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToshort(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToshort(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToshort(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToshort(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_short_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_short_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_short_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_short_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToshort(0)));
      seq.add(TypeConversionUtil.convertToshort(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_short_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Short_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Short_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Short_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Short_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToShort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToShort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToShort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToShort(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToShort(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToShort(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToShort(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToShort(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToShort(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Short_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Short_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Short_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Short_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToShort(0)));
      seq.add(TypeConversionUtil.convertToShort(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_Short_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
        //IF OfInt
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_int_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_int_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_int_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_int_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToint(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToint(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToint(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToint(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToint(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToint(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToint(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToint(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToint(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_int_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_int_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_int_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_int_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToint(0)));
      seq.add(TypeConversionUtil.convertToint(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Integer_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Integer_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Integer_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Integer_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToInteger(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToInteger(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToInteger(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToInteger(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToInteger(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToInteger(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToInteger(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToInteger(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToInteger(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Integer_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Integer_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Integer_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Integer_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToInteger(0)));
      seq.add(TypeConversionUtil.convertToInteger(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_Integer_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //IF OfLong
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_long_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_long_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_long_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_long_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTolong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTolong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertTolong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTolong(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTolong(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTolong(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTolong(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTolong(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertTolong(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_long_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_long_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_long_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_long_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertTolong(0)));
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_long_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Long_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Long_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Long_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Long_throwCME(int initialCapacity){
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToLong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToLong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToLong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToLong(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToLong(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToLong(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToLong(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new FloatArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToLong(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToLong(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Long_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Long_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Long_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Long_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToLong(0)));
      seq.add(TypeConversionUtil.convertToLong(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_Long_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToLong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //ENDIF
        //ENDIF
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testCheckedStackConstructor_happyPath()
  {
    var seq=new FloatArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testCheckedStackConstructor_int_floatarray_happyPath()
  {
    int size=5;
    float[] arr=new float[10];
    var seq=new FloatArrSeq.CheckedStack(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testCheckedStackConstructor_int_happyPath(int capacity)
  {
    var seq=new FloatArrSeq.CheckedStack(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.arr);
      break;
      default:
      Assertions.assertEquals(capacity,seq.arr.length);
    }
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
//ENDIF
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_float_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTofloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_float_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTofloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_Float_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToFloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_Float_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToFloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
  //IF OfBoolean
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_boolean_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_boolean_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_Boolean_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_Boolean_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
    //IF OfByte,OfChar
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_byte_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_byte_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_Byte_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_Byte_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
      //IF OfShort
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_short_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_short_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_Short_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_Short_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
        //IF OfInt
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_int_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_int_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_Integer_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_Integer_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //IF OfLong
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_long_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_long_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_Long_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToLong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_Long_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new FloatArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToLong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //ENDIF
        //ENDIF
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_float_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_float_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_float_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_float_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTofloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTofloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertTofloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTofloat(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTofloat(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTofloat(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTofloat(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTofloat(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertTofloat(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTofloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_float_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_float_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_float_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_float_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertTofloat(0)));
      seq.add(TypeConversionUtil.convertTofloat(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_float_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTofloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTofloat(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertTofloat(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTofloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTofloat(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTofloat(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTofloat(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTofloat(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertTofloat(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTofloat(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTofloat(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTofloat(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_float_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTofloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTofloat(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertTofloat(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertTofloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertTofloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertTofloat(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTofloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTofloat(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertTofloat(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTofloat(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTofloat(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTofloat(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertTofloat(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertTofloat(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertTofloat(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertTofloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertTofloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertTofloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertTofloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertTofloat(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTofloat(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTofloat(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTofloat(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertTofloat(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertTofloat(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertTofloat(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_float_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTofloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Float_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Float_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Float_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToFloat(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Float_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToFloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToFloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToFloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToFloat(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToFloat(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToFloat(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToFloat(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToFloat(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToFloat(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToFloat(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Float_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Float_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Float_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToFloat(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Float_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToFloat(0)));
      seq.add(TypeConversionUtil.convertToFloat(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Float_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToFloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToFloat(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToFloat(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToFloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToFloat(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToFloat(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToFloat(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToFloat(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToFloat(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToFloat(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToFloat(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToFloat(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Float_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToFloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToFloat(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToFloat(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToFloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToFloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToFloat(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToFloat(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToFloat(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToFloat(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToFloat(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToFloat(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToFloat(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToFloat(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToFloat(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToFloat(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToFloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToFloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToFloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToFloat(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToFloat(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToFloat(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToFloat(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToFloat(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToFloat(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToFloat(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToFloat(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Float_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToFloat(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
  //IF OfBoolean
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_boolean_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_boolean_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_boolean_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_boolean_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToboolean(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToboolean(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToboolean(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToboolean(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToboolean(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToboolean(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToboolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_boolean_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_boolean_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_boolean_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_boolean_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToboolean(0)));
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_boolean_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToboolean(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToboolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToboolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToboolean(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToboolean(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToboolean(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToboolean(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToboolean(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToboolean(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToboolean(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_boolean_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToboolean(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToboolean(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToboolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToboolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToboolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToboolean(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToboolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToboolean(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToboolean(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToboolean(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToboolean(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToboolean(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToboolean(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToboolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToboolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToboolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToboolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToboolean(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToboolean(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToboolean(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToboolean(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToboolean(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToboolean(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToboolean(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_boolean_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Boolean_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Boolean_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Boolean_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Boolean_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToBoolean(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToBoolean(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToBoolean(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToBoolean(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToBoolean(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToBoolean(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToBoolean(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Boolean_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Boolean_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Boolean_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
    verifyAscendingSpanfloatboolean(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Boolean_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToBoolean(0)));
      seq.add(TypeConversionUtil.convertToBoolean(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloatboolean(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Boolean_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToBoolean(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToBoolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToBoolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToBoolean(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToBoolean(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToBoolean(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToBoolean(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToBoolean(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToBoolean(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToBoolean(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Boolean_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToBoolean(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToBoolean(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToBoolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToBoolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToBoolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToBoolean(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToBoolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToBoolean(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToBoolean(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToBoolean(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToBoolean(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToBoolean(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToBoolean(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToBoolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToBoolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToBoolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToBoolean(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToBoolean(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToBoolean(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToBoolean(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToBoolean(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToBoolean(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToBoolean(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToBoolean(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloatboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloatboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Boolean_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(val),root.arr[i]);
    }
  }
    //IF OfByte,OfChar
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_byte_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_byte_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_byte_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_byte_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTobyte(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTobyte(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTobyte(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTobyte(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTobyte(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertTobyte(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTobyte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_byte_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_byte_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_byte_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_byte_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertTobyte(0)));
      seq.add(TypeConversionUtil.convertTobyte(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_byte_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTobyte(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertTobyte(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTobyte(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTobyte(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTobyte(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTobyte(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertTobyte(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTobyte(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTobyte(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTobyte(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_byte_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTobyte(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertTobyte(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertTobyte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertTobyte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertTobyte(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTobyte(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertTobyte(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTobyte(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTobyte(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTobyte(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertTobyte(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertTobyte(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertTobyte(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertTobyte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertTobyte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertTobyte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertTobyte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertTobyte(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTobyte(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTobyte(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTobyte(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertTobyte(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertTobyte(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertTobyte(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_byte_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Byte_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Byte_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Byte_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Byte_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToByte(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToByte(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToByte(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToByte(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToByte(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToByte(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToByte(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Byte_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Byte_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Byte_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Byte_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToByte(0)));
      seq.add(TypeConversionUtil.convertToByte(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Byte_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToByte(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToByte(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToByte(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToByte(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToByte(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToByte(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToByte(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToByte(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToByte(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToByte(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Byte_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToByte(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToByte(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToByte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToByte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToByte(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToByte(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToByte(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToByte(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToByte(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToByte(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToByte(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToByte(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToByte(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToByte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToByte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToByte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToByte(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToByte(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToByte(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToByte(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToByte(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToByte(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToByte(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToByte(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Byte_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
      //IF OfShort
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_short_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_short_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_short_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToshort(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_short_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToshort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToshort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToshort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToshort(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToshort(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToshort(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToshort(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToshort(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToshort(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToshort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_short_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_short_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_short_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToshort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_short_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToshort(0)));
      seq.add(TypeConversionUtil.convertToshort(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_short_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToshort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToshort(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToshort(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToshort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToshort(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToshort(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToshort(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToshort(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToshort(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToshort(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToshort(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToshort(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_short_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToshort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToshort(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToshort(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToshort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToshort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToshort(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToshort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToshort(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToshort(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToshort(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToshort(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToshort(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToshort(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToshort(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToshort(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToshort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToshort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToshort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToshort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToshort(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToshort(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToshort(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToshort(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToshort(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToshort(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToshort(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_short_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Short_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Short_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Short_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToShort(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Short_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToShort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToShort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToShort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToShort(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToShort(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToShort(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToShort(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToShort(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToShort(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToShort(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Short_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Short_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Short_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToShort(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Short_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToShort(0)));
      seq.add(TypeConversionUtil.convertToShort(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Short_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToShort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToShort(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToShort(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToShort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToShort(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToShort(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToShort(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToShort(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToShort(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToShort(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToShort(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToShort(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Short_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToShort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToShort(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToShort(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToShort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToShort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToShort(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToShort(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToShort(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToShort(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToShort(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToShort(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToShort(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToShort(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToShort(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToShort(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToShort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToShort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToShort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToShort(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToShort(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToShort(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToShort(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToShort(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToShort(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToShort(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToShort(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Short_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
        //IF OfInt
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_int_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_int_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_int_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToint(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_int_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToint(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToint(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToint(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToint(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToint(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToint(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToint(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToint(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToint(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToint(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_int_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_int_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_int_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToint(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_int_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToint(0)));
      seq.add(TypeConversionUtil.convertToint(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToint(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToint(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToint(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToint(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToint(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToint(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToint(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToint(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToint(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToint(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToint(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToint(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_int_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToint(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToint(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToint(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToint(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToint(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToint(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToint(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToint(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToint(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToint(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToint(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToint(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToint(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToint(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToint(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToint(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToint(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToint(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToint(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToint(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToint(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToint(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToint(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToint(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToint(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToint(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToint(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToint(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToint(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToint(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Integer_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Integer_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Integer_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToInteger(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Integer_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToInteger(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToInteger(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToInteger(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToInteger(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToInteger(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToInteger(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToInteger(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToInteger(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToInteger(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToInteger(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Integer_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Integer_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Integer_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToInteger(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Integer_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToInteger(0)));
      seq.add(TypeConversionUtil.convertToInteger(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Integer_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToInteger(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToInteger(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToInteger(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToInteger(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToInteger(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToInteger(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToInteger(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToInteger(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToInteger(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToInteger(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToInteger(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToInteger(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Integer_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToInteger(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToInteger(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToInteger(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToInteger(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToInteger(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToInteger(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToInteger(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToInteger(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToInteger(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToInteger(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToInteger(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToInteger(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToInteger(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToInteger(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToInteger(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToInteger(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToInteger(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToInteger(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToInteger(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToInteger(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToInteger(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToInteger(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToInteger(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToInteger(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToInteger(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToInteger(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Integer_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //IF OfLong
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_long_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_long_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_long_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTolong(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_long_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTolong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTolong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertTolong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTolong(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTolong(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTolong(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTolong(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTolong(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertTolong(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTolong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_long_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_long_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_long_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTolong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_long_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertTolong(0)));
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_long_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTolong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTolong(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertTolong(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTolong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTolong(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTolong(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTolong(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertTolong(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTolong(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTolong(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_long_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTolong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTolong(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertTolong(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertTolong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertTolong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertTolong(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTolong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTolong(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertTolong(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTolong(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTolong(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertTolong(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertTolong(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertTolong(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertTolong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertTolong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertTolong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertTolong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertTolong(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTolong(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTolong(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertTolong(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertTolong(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertTolong(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_long_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Long_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousFloat();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Long_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Long_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToLong(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Long_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToLong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToLong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToLong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToLong(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToLong(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToLong(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToLong(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToLong(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToLong(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToLong(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Long_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(--val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Long_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Long_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToLong(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
    verifyAscendingSpanfloat(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Long_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToLong(0)));
      seq.add(TypeConversionUtil.convertToLong(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanfloat(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Long_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToLong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToLong(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToLong(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToLong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToLong(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToLong(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToLong(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToLong(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToLong(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToLong(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToLong(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToLong(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Long_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToLong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToLong(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToLong(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToLong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToLong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToLong(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToLong(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToLong(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToLong(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToLong(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToLong(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToLong(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToLong(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToLong(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToLong(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToLong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToLong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToLong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToLong(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToLong(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      FloatArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new FloatArrSeq.CheckedList();
      }
      else
      {
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new FloatArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToLong(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToLong(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToLong(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToLong(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToLong(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToLong(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanfloat(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanfloat(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Long_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    FloatArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new FloatArrSeq.CheckedList();
    }
    else
    {
      float[] arr=new float[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new FloatArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToLong(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(val),root.arr[i]);
    }
  }
          //ENDIF
        //ENDIF
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
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
  private static void initAscendingArray(float[] arr,int offset,int lo,int hi)
  {
    int bound=offset+(hi-lo);
    for(int i=offset;i<bound;++i,++lo)
    {
      arr[i]=TypeConversionUtil.convertTofloat(lo);
    }
  }
  private static void assertIteratorStateIntegrity(Object itr,int expectedCursor,int expectedLastRet,int expectedItrModCount,Object expectedParent,Object expectedRoot)
  {
    int actualCursor;
    Object actualParent;
    if(expectedParent==expectedRoot)
    {
      if(expectedRoot instanceof OmniStack.OfFloat)
      {
        if(expectedRoot instanceof FloatArrSeq.CheckedStack)
        {
          actualCursor=FieldAccessor.FloatArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.FloatArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.FloatArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet,FieldAccessor.FloatArrSeq.CheckedStack.Itr.lastRet(itr));
        }
        else
        {
          actualCursor=FieldAccessor.FloatArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.FloatArrSeq.UncheckedStack.Itr.parent(itr);
        }
      }
      else
      {
        if(expectedRoot instanceof FloatArrSeq.CheckedList)
        {
          actualCursor=FieldAccessor.FloatArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.FloatArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.FloatArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet,FieldAccessor.FloatArrSeq.CheckedList.Itr.lastRet(itr));
        }
        else
        {
          actualCursor=FieldAccessor.FloatArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.FloatArrSeq.UncheckedList.Itr.parent(itr);
          //skip the lastRet check since the unchecked iterator does not guarantee its state
          //if(itr instanceof OmniListIterator.OfFloat)
          //{
          //  Assertions.assertEquals(expectedLastRet,FieldAccessor.FloatArrSeq.UncheckedList.ListItr.lastRet(itr));
          //}
        }
      }
    }
    else
    {
      if(expectedRoot instanceof FloatArrSeq.CheckedList)
      {
        actualCursor=FieldAccessor.FloatArrSeq.CheckedSubList.Itr.cursor(itr);
        actualParent=FieldAccessor.FloatArrSeq.CheckedSubList.Itr.parent(itr);
        Assertions.assertEquals(expectedItrModCount,FieldAccessor.FloatArrSeq.CheckedSubList.Itr.modCount(itr));
        Assertions.assertEquals(expectedLastRet,FieldAccessor.FloatArrSeq.CheckedSubList.Itr.lastRet(itr));
      }
      else
      {
        actualCursor=FieldAccessor.FloatArrSeq.UncheckedSubList.Itr.cursor(itr);
        actualParent=FieldAccessor.FloatArrSeq.UncheckedSubList.Itr.parent(itr);
        //skip the lastRet check since the unchecked iterator does not guarantee its state
        //if(itr instanceof OmniListIterator.OfFloat)
        //{
        //  Assertions.assertEquals(expectedLastRet,FieldAccessor.FloatArrSeq.UncheckedSubList.ListItr.lastRet(itr));
        //}
      }
    }
    Assertions.assertEquals(expectedCursor,actualCursor);
    Assertions.assertSame(expectedParent,actualParent);
  }
  private static FloatArrSeq assertStructuralIntegrity(Object seq,int expectedSeqSize,int expectedSeqModCount,Object expectedParent,int expectedParentSize,int expectedParentModCount,FloatArrSeq expectedRoot,int expectedRootSize,int expectedRootModCount)
  {
    if(seq==expectedRoot)
    {
      if(seq instanceof FloatArrSeq.CheckedList)
      {
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.FloatArrSeq.CheckedList.modCount(seq));
      }
      else if(seq instanceof FloatArrSeq.CheckedStack)
      {
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.FloatArrSeq.CheckedStack.modCount(seq));
      }
    }
    else
    {
      OmniList.OfFloat actualSeqParent;
      Object actualSeqRoot;
      OmniList.OfFloat actualParentParent;
      Object actualParentRoot;
      int actualParentSize;
      int actualSeqSize;
      if(expectedRoot instanceof FloatArrSeq.CheckedList)
      {
        actualSeqParent=FieldAccessor.FloatArrSeq.CheckedSubList.parent(seq);
        actualSeqRoot=FieldAccessor.FloatArrSeq.CheckedSubList.root(seq);
        actualParentParent=FieldAccessor.FloatArrSeq.CheckedSubList.parent(expectedParent);
        actualParentRoot=FieldAccessor.FloatArrSeq.CheckedSubList.root(expectedParent);
        actualSeqSize=FieldAccessor.FloatArrSeq.CheckedSubList.size(seq);
        actualParentSize=FieldAccessor.FloatArrSeq.CheckedSubList.size(expectedParent);
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.FloatArrSeq.CheckedSubList.modCount(seq));
        Assertions.assertEquals(expectedParentModCount,FieldAccessor.FloatArrSeq.CheckedSubList.modCount(expectedParent));
        Assertions.assertEquals(expectedRootModCount,FieldAccessor.FloatArrSeq.CheckedList.modCount(expectedRoot));
      }
      else
      {
        actualSeqParent=FieldAccessor.FloatArrSeq.UncheckedSubList.parent(seq);
        actualSeqRoot=FieldAccessor.FloatArrSeq.UncheckedSubList.root(seq);
        actualParentParent=FieldAccessor.FloatArrSeq.UncheckedSubList.parent(expectedParent);
        actualParentRoot=FieldAccessor.FloatArrSeq.UncheckedSubList.root(expectedParent);
        actualSeqSize=FieldAccessor.FloatArrSeq.UncheckedSubList.size(seq);
        actualParentSize=FieldAccessor.FloatArrSeq.UncheckedSubList.size(expectedParent);
      }
      Assertions.assertSame(expectedRoot,actualSeqRoot);
      Assertions.assertSame(expectedRoot,actualParentRoot);
      Assertions.assertSame(expectedParent,actualSeqParent);
      Assertions.assertNull(actualParentParent);
      Assertions.assertEquals(expectedSeqSize,actualSeqSize);
      Assertions.assertEquals(expectedParentSize,actualParentSize);
    }
    Assertions.assertEquals(expectedRootSize,FieldAccessor.FloatArrSeq.size(expectedRoot));
    return expectedRoot;
  }
}
