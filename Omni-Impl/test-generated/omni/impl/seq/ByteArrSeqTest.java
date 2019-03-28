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
import omni.function.ByteConsumer;
//ENDIF
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class ByteArrSeqTest{
  private static void verifyAscendingSpanbyte(byte[] arr,int offset,int bound,int loVal)
    {
      for(int i=offset;i<bound;++i,++loVal)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(loVal),arr[i]);
      }
    }
//IF OfBoolean,OfRef
  private static void verifyAscendingSpanbyteboolean(byte[] arr,int offset,int bound,int loVal)
    {
      for(int i=offset;i<bound;++i,++loVal)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(loVal),arr[i]);
      }
    }
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testUncheckedListConstructor_happyPath()
  {
    var seq=new ByteArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testUncheckedListConstructor_int_bytearray_happyPath()
  {
    int size=5;
    byte[] arr=new byte[10];
    var seq=new ByteArrSeq.UncheckedList(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testUncheckedListConstructor_int_happyPath(int capacity)
  {
    var seq=new ByteArrSeq.UncheckedList(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.arr);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
  }
//IF OfRef
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.UncheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
  }
  //ENDIF
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testUncheckedStackConstructor_happyPath()
  {
    var seq=new ByteArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testUncheckedStackConstructor_int_bytearray_happyPath()
  {
    int size=5;
    byte[] arr=new byte[10];
    var seq=new ByteArrSeq.UncheckedStack(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testUncheckedStackConstructor_int_happyPath(int capacity)
  {
    var seq=new ByteArrSeq.UncheckedStack(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.arr);
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
  public void testUncheckedStackpush_byte_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new ByteArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
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
    var seq=new ByteArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
  }
//IF OfRef
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
    var seq=new ByteArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
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
    var seq=new ByteArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
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
    var seq=new ByteArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
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
    var seq=new ByteArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
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
    var seq=new ByteArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
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
    var seq=new ByteArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
  }
  //ENDIF
//ENDIF
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
  }
//IF OfRef
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.UncheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.UncheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
  }
  //ENDIF
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testCheckedListConstructor_happyPath()
  {
    var seq=new ByteArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testCheckedListConstructor_int_bytearray_happyPath()
  {
    int size=5;
    byte[] arr=new byte[10];
    var seq=new ByteArrSeq.CheckedList(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testCheckedListConstructor_int_happyPath(int capacity)
  {
    var seq=new ByteArrSeq.CheckedList(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.arr);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
  }
//IF OfRef
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new ByteArrSeq.CheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
  }
  //ENDIF
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testCheckedStackConstructor_happyPath()
  {
    var seq=new ByteArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testCheckedStackConstructor_int_bytearray_happyPath()
  {
    int size=5;
    byte[] arr=new byte[10];
    var seq=new ByteArrSeq.CheckedStack(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testCheckedStackConstructor_int_happyPath(int capacity)
  {
    var seq=new ByteArrSeq.CheckedStack(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.arr);
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
  public void testCheckedStackpush_byte_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new ByteArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
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
    var seq=new ByteArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
  }
//IF OfRef
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
    var seq=new ByteArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
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
    var seq=new ByteArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
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
    var seq=new ByteArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
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
    var seq=new ByteArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
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
    var seq=new ByteArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
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
    var seq=new ByteArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
  }
  //ENDIF
//ENDIF
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTobyte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTobyte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTobyte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTobyte(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
  }
//IF OfRef
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToByte(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(--val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToByte(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyte(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
    }
    verifyAscendingSpanbyte(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpanbyte(root.arr,0,rootSize+100,-preAllocSpan);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToByte(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToByte(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyte(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyte(root.arr,preAllocSpan+51,rootSize+101,50);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(val),root.arr[i]);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToboolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousByte();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
        seqItr.previousByte();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(--val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,100);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
    verifyAscendingSpanbyteboolean(root.arr,i,rootSize+100,i);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpanbyteboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToBoolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      ByteArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new ByteArrSeq.CheckedList();
      }
      else
      {
        byte[] arr=new byte[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpanbyteboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanbyteboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    ByteArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new ByteArrSeq.CheckedList();
    }
    else
    {
      byte[] arr=new byte[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new ByteArrSeq.CheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(val),root.arr[i]);
    }
  }
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
  private static void initAscendingArray(byte[] arr,int offset,int lo,int hi)
  {
    int bound=offset+(hi-lo);
    for(int i=offset;i<bound;++i,++lo)
    {
      arr[i]=TypeConversionUtil.convertTobyte(lo);
    }
  }
  private static void assertIteratorStateIntegrity(Object itr,int expectedCursor,int expectedLastRet,int expectedItrModCount,Object expectedParent,Object expectedRoot)
  {
    int actualCursor;
    Object actualParent;
    if(expectedParent==expectedRoot)
    {
      if(expectedRoot instanceof OmniStack.OfByte)
      {
        if(expectedRoot instanceof ByteArrSeq.CheckedStack)
        {
          actualCursor=FieldAccessor.ByteArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.ByteArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.ByteArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet,FieldAccessor.ByteArrSeq.CheckedStack.Itr.lastRet(itr));
        }
        else
        {
          actualCursor=FieldAccessor.ByteArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.ByteArrSeq.UncheckedStack.Itr.parent(itr);
        }
      }
      else
      {
        if(expectedRoot instanceof ByteArrSeq.CheckedList)
        {
          actualCursor=FieldAccessor.ByteArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.ByteArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.ByteArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet,FieldAccessor.ByteArrSeq.CheckedList.Itr.lastRet(itr));
        }
        else
        {
          actualCursor=FieldAccessor.ByteArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.ByteArrSeq.UncheckedList.Itr.parent(itr);
          //skip the lastRet check since the unchecked iterator does not guarantee its state
          //if(itr instanceof OmniListIterator.OfByte)
          //{
          //  Assertions.assertEquals(expectedLastRet,FieldAccessor.ByteArrSeq.UncheckedList.ListItr.lastRet(itr));
          //}
        }
      }
    }
    else
    {
      if(expectedRoot instanceof ByteArrSeq.CheckedList)
      {
        actualCursor=FieldAccessor.ByteArrSeq.CheckedSubList.Itr.cursor(itr);
        actualParent=FieldAccessor.ByteArrSeq.CheckedSubList.Itr.parent(itr);
        Assertions.assertEquals(expectedItrModCount,FieldAccessor.ByteArrSeq.CheckedSubList.Itr.modCount(itr));
        Assertions.assertEquals(expectedLastRet,FieldAccessor.ByteArrSeq.CheckedSubList.Itr.lastRet(itr));
      }
      else
      {
        actualCursor=FieldAccessor.ByteArrSeq.UncheckedSubList.Itr.cursor(itr);
        actualParent=FieldAccessor.ByteArrSeq.UncheckedSubList.Itr.parent(itr);
        //skip the lastRet check since the unchecked iterator does not guarantee its state
        //if(itr instanceof OmniListIterator.OfByte)
        //{
        //  Assertions.assertEquals(expectedLastRet,FieldAccessor.ByteArrSeq.UncheckedSubList.ListItr.lastRet(itr));
        //}
      }
    }
    Assertions.assertEquals(expectedCursor,actualCursor);
    Assertions.assertSame(expectedParent,actualParent);
  }
  private static ByteArrSeq assertStructuralIntegrity(Object seq,int expectedSeqSize,int expectedSeqModCount,Object expectedParent,int expectedParentSize,int expectedParentModCount,ByteArrSeq expectedRoot,int expectedRootSize,int expectedRootModCount)
  {
    if(seq==expectedRoot)
    {
      if(seq instanceof ByteArrSeq.CheckedList)
      {
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.ByteArrSeq.CheckedList.modCount(seq));
      }
      else if(seq instanceof ByteArrSeq.CheckedStack)
      {
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.ByteArrSeq.CheckedStack.modCount(seq));
      }
    }
    else
    {
      OmniList.OfByte actualSeqParent;
      Object actualSeqRoot;
      OmniList.OfByte actualParentParent;
      Object actualParentRoot;
      int actualParentSize;
      int actualSeqSize;
      if(expectedRoot instanceof ByteArrSeq.CheckedList)
      {
        actualSeqParent=FieldAccessor.ByteArrSeq.CheckedSubList.parent(seq);
        actualSeqRoot=FieldAccessor.ByteArrSeq.CheckedSubList.root(seq);
        actualParentParent=FieldAccessor.ByteArrSeq.CheckedSubList.parent(expectedParent);
        actualParentRoot=FieldAccessor.ByteArrSeq.CheckedSubList.root(expectedParent);
        actualSeqSize=FieldAccessor.ByteArrSeq.CheckedSubList.size(seq);
        actualParentSize=FieldAccessor.ByteArrSeq.CheckedSubList.size(expectedParent);
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.ByteArrSeq.CheckedSubList.modCount(seq));
        Assertions.assertEquals(expectedParentModCount,FieldAccessor.ByteArrSeq.CheckedSubList.modCount(expectedParent));
        Assertions.assertEquals(expectedRootModCount,FieldAccessor.ByteArrSeq.CheckedList.modCount(expectedRoot));
      }
      else
      {
        actualSeqParent=FieldAccessor.ByteArrSeq.UncheckedSubList.parent(seq);
        actualSeqRoot=FieldAccessor.ByteArrSeq.UncheckedSubList.root(seq);
        actualParentParent=FieldAccessor.ByteArrSeq.UncheckedSubList.parent(expectedParent);
        actualParentRoot=FieldAccessor.ByteArrSeq.UncheckedSubList.root(expectedParent);
        actualSeqSize=FieldAccessor.ByteArrSeq.UncheckedSubList.size(seq);
        actualParentSize=FieldAccessor.ByteArrSeq.UncheckedSubList.size(expectedParent);
      }
      Assertions.assertSame(expectedRoot,actualSeqRoot);
      Assertions.assertSame(expectedRoot,actualParentRoot);
      Assertions.assertSame(expectedParent,actualSeqParent);
      Assertions.assertNull(actualParentParent);
      Assertions.assertEquals(expectedSeqSize,actualSeqSize);
      Assertions.assertEquals(expectedParentSize,actualParentSize);
    }
    Assertions.assertEquals(expectedRootSize,FieldAccessor.ByteArrSeq.size(expectedRoot));
    return expectedRoot;
  }
}
