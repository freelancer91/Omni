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
import omni.function.CharConsumer;
//ENDIF
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class CharArrSeqTest{
  private static void verifyAscendingSpanchar(char[] arr,int offset,int bound,int loVal)
    {
      for(int i=offset;i<bound;++i,++loVal)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTochar(loVal),arr[i]);
      }
    }
//IF OfBoolean,OfRef
  private static void verifyAscendingSpancharboolean(char[] arr,int offset,int bound,int loVal)
    {
      for(int i=offset;i<bound;++i,++loVal)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(loVal),arr[i]);
      }
    }
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testUncheckedListConstructor_happyPath()
  {
    var seq=new CharArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testUncheckedListConstructor_int_chararray_happyPath()
  {
    int size=5;
    char[] arr=new char[10];
    var seq=new CharArrSeq.UncheckedList(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testUncheckedListConstructor_int_happyPath(int capacity)
  {
    var seq=new CharArrSeq.UncheckedList(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
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
  public void testUncheckedListListItradd_char_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_char_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_char_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_char_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_char_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_char_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_char_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Character_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Character_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListListItradd_Character_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Character_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Character_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_int_Character_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedListadd_Character_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
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
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
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
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    var seq=new CharArrSeq.UncheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
  }
  //ENDIF
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testUncheckedStackConstructor_happyPath()
  {
    var seq=new CharArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testUncheckedStackConstructor_int_chararray_happyPath()
  {
    int size=5;
    char[] arr=new char[10];
    var seq=new CharArrSeq.UncheckedStack(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testUncheckedStackConstructor_int_happyPath(int capacity)
  {
    var seq=new CharArrSeq.UncheckedStack(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
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
  public void testUncheckedStackpush_char_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_char_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackpush_Character_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testUncheckedStackadd_Character_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.UncheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
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
    var seq=new CharArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
    var seq=new CharArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
    var seq=new CharArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
    var seq=new CharArrSeq.UncheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
  }
  //ENDIF
//ENDIF
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_char_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_char_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_char_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_char_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_char_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_char_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_char_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Character_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Character_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListListItradd_Character_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Character_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Character_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_int_Character_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testUncheckedSubListadd_Character_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
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
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
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
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    CharArrSeq.UncheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.UncheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.UncheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
  }
  //ENDIF
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testCheckedListConstructor_happyPath()
  {
    var seq=new CharArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testCheckedListConstructor_int_chararray_happyPath()
  {
    int size=5;
    char[] arr=new char[10];
    var seq=new CharArrSeq.CheckedList(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testCheckedListConstructor_int_happyPath(int capacity)
  {
    var seq=new CharArrSeq.CheckedList(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
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
  public void testCheckedListListItradd_char_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_char_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_char_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_char_throwCME(int initialCapacity){
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTochar(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTochar(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertTochar(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTochar(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTochar(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTochar(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTochar(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTochar(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertTochar(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_char_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_char_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_char_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_char_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertTochar(0)));
      seq.add(TypeConversionUtil.convertTochar(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_char_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Character_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Character_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Character_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListListItradd_Character_throwCME(int initialCapacity){
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToCharacter(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToCharacter(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToCharacter(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToCharacter(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToCharacter(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToCharacter(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToCharacter(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
      var parent=seq;
      var root=seq;
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToCharacter(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToCharacter(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Character_happyPathInsertBegin(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Character_happyPathInsertEnd(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Character_happyPathInsertMidPoint(int initialCapacity){
  //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF STRUCT==List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_int_Character_throwIOBE(int initialCapacity){
    //ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToCharacter(0)));
      seq.add(TypeConversionUtil.convertToCharacter(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedListadd_Character_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
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
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
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
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,rootSize+101,-preAllocSpan);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
      var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    var seq=new CharArrSeq.CheckedList(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
  }
  //ENDIF
//ENDIF
//IF STRUCT==Stack,List
  @Test
  public void testCheckedStackConstructor_happyPath()
  {
    var seq=new CharArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @Test
  public void testCheckedStackConstructor_int_chararray_happyPath()
  {
    int size=5;
    char[] arr=new char[10];
    var seq=new CharArrSeq.CheckedStack(size,arr);
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
    assertStructuralIntegrity(seq,0,0,seq,0,0,seq,0,0);
  }
  @ParameterizedTest
  @ValueSource(ints={0,5,OmniArray.DEFAULT_ARR_SEQ_CAP,15})
  public void testCheckedStackConstructor_int_happyPath(int capacity)
  {
    var seq=new CharArrSeq.CheckedStack(capacity);
    Assertions.assertEquals(0,seq.size);
    switch(capacity)
    {
      case 0:
      Assertions.assertNull(seq.arr);
      break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
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
  public void testCheckedStackpush_char_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_char_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==Stack
  @ParameterizedTest
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackpush_Character_happyPath(int initialCapacity){
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
  }
//ENDIF
  @ParameterizedTest
//IF STRUCT==Stack,List
  @ValueSource(ints={0,5,10,15})
  public void testCheckedStackadd_Character_happyPath(int initialCapacity){
//ENDIF
//IF 
    int rootPreAlloc=0;
    int parentPreAlloc=0;
    int parentPostAlloc=0;
    int rootPostAlloc=0;
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    var seq=new CharArrSeq.CheckedStack(initialCapacity);
    var parent=seq;
    var root=seq;
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
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
    var seq=new CharArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
    var seq=new CharArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
    var seq=new CharArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
    var seq=new CharArrSeq.CheckedStack(initialCapacity);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
  }
  //ENDIF
//ENDIF
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_char_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_char_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_char_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTochar(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_char_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTochar(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTochar(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertTochar(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTochar(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTochar(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTochar(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTochar(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertTochar(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertTochar(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertTochar(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_char_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_char_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_char_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertTochar(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_char_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertTochar(0)));
      seq.add(TypeConversionUtil.convertTochar(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_char_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTochar(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTochar(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertTochar(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTochar(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTochar(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTochar(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTochar(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTochar(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertTochar(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTochar(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTochar(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertTochar(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_char_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertTochar(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTochar(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertTochar(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertTochar(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertTochar(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertTochar(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertTochar(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTochar(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertTochar(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTochar(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertTochar(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTochar(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertTochar(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertTochar(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertTochar(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertTochar(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertTochar(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertTochar(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertTochar(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertTochar(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertTochar(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertTochar(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertTochar(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertTochar(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertTochar(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertTochar(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_char_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
  }
//IF OfRef
//IF STRUCT==List,SubList
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Character_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Character_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+i+1,-1,i+1,parent,root);
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Character_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToCharacter(i));
      assertIteratorStateIntegrity(seqItr,((rootSize+i)/2)+1,-1,i+1,parent,root);
      if((i&1)==0)
      {
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListListItradd_Character_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToCharacter(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToCharacter(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      //illegally modify the sequence
      seq.add(0,TypeConversionUtil.convertToCharacter(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan,-1,0,parent,root);
      assertStructuralIntegrity(seq,1,1,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToCharacter(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToCharacter(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToCharacter(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToCharacter(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      var itr=seq.listIterator();
      for(int i=0;i<100;++i)
      {
        itr.add(TypeConversionUtil.convertToCharacter(i));
      }
      //illegally modify the seq
      seq.add(50,TypeConversionUtil.convertToCharacter(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->itr.add(TypeConversionUtil.convertToCharacter(0)));
      assertIteratorStateIntegrity(itr,preAllocSpan+100,-1,100,parent,root);
      assertStructuralIntegrity(seq,101,101,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  //ENDIF
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Character_happyPathInsertBegin(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(--val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,100);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Character_happyPathInsertEnd(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
  @ParameterizedTest
  //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Character_happyPathInsertMidPoint(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
  //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToCharacter(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
    }
    verifyAscendingSpanchar(root.arr,i,rootSize+100,i);
  }
  //IF CHECKED==Checked
  @ParameterizedTest
    //IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Character_throwIOBE(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    //ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(0)));
      //too hi
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(finalIndex+1,TypeConversionUtil.convertToCharacter(0)));
      seq.add(TypeConversionUtil.convertToCharacter(i));
    }
    //when the method throws, verify that no changes occurred
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpanchar(root.arr,0,rootSize+100,-preAllocSpan);
  }
    //IF STRUCT==SubList
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Character_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToCharacter(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToCharacter(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToCharacter(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToCharacter(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToCharacter(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToCharacter(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToCharacter(rootSize+100));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToCharacter(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(TypeConversionUtil.convertToCharacter(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToCharacter(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToCharacter(50));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToCharacter(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
  @ParameterizedTest
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_int_Character_throwCME(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the root
      root.add(preAllocSpan,TypeConversionUtil.convertToCharacter(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToCharacter(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToCharacter(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToCharacter(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToCharacter(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+1,TypeConversionUtil.convertToCharacter(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize,0,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToCharacter(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToCharacter(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(1,TypeConversionUtil.convertToCharacter(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToCharacter(i));
      }
      //illegally modify the root
      root.add(TypeConversionUtil.convertToCharacter(rootSize+100));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToCharacter(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToCharacter(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToCharacter(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToCharacter(0)));
      //attempt the same tests on the parent
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(0,TypeConversionUtil.convertToCharacter(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentPreAlloc+50,TypeConversionUtil.convertToCharacter(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+100,TypeConversionUtil.convertToCharacter(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(-1,TypeConversionUtil.convertToCharacter(0)));
      Assertions.assertThrows(ConcurrentModificationException.class,()->parent.add(parentSize+101,TypeConversionUtil.convertToCharacter(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+100,100,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      for(int i=0;i<100;++i)
      {
        seq.add(TypeConversionUtil.convertToCharacter(i));
      }
      //illegally modify the parent
      parent.add(parentPreAlloc+50,TypeConversionUtil.convertToCharacter(50));
      //attempt an insertion at the beginning
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(0,TypeConversionUtil.convertToCharacter(0)));
      //attempt an insertion at the midpoint
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(50,TypeConversionUtil.convertToCharacter(0)));
      //attempt an insertion at the end
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(100,TypeConversionUtil.convertToCharacter(0)));
      //an insertion out of bounds should also throw a CME
      //too low
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(0)));
      //too hi
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(101,TypeConversionUtil.convertToCharacter(0)));
      assertStructuralIntegrity(seq,100,100,parent,parentSize+101,101,root,rootSize+101,101);
      verifyAscendingSpanchar(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpanchar(root.arr,preAllocSpan+51,rootSize+101,50);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @ParameterizedTest
//IF 
  @MethodSource("getSubListConstructionArgs")
  public void testCheckedSubListadd_Character_happyPath(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc){
//ENDIF
//IF STRUCT==SubList
    int parentSize=parentPreAlloc+parentPostAlloc;
    int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
    int preAllocSpan=rootPreAlloc+parentPreAlloc;
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    for(int i=0,val=-preAllocSpan;i<100;++i,++val)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),root.arr[i]);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToboolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
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
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToboolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToboolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToboolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    var seqItr=seq.listIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertToBoolean(i));
      assertIteratorStateIntegrity(seqItr,preAllocSpan+1,-1,i+1,parent,root);
      seqItr.previousChar();
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
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
        seqItr.previousChar();
      }
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
    }
    {
      //try on an non-empty sublist, inserting at the end
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=100,bound=i+100;i<bound;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(--val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,100);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size(),TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
    }
    var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
    var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size()/2,TypeConversionUtil.convertToBoolean(i));
    }
    assertStructuralIntegrity(seq,100,100,parent,100+parentSize,100,root,100+rootSize,100);
    verifyAscendingSpancharboolean(root.arr,0,preAllocSpan,-preAllocSpan);
    int i=preAllocSpan;
    for(int val=1,bound=i+50;i<bound;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    for(int val=98,bound=i+50;i<bound;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
    }
    verifyAscendingSpancharboolean(root.arr,i,rootSize+100,i);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
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
    verifyAscendingSpancharboolean(root.arr,0,rootSize+100,-preAllocSpan);
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
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
      }
      var parent=root.subList(rootPreAlloc,preAllocSpan+parentPostAlloc);
      var seq=parent.subList(parentPreAlloc,parentPreAlloc);
//ENDIF
      //illegally modify the parent
      parent.add(parentPreAlloc,TypeConversionUtil.convertToBoolean(0));
      //attempt an insertion
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(TypeConversionUtil.convertToBoolean(0)));
      assertStructuralIntegrity(seq,0,0,parent,parentSize+1,1,root,rootSize+1,1);
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+1,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+1,rootSize+1,100);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,rootSize+101,-preAllocSpan);
    }
    {
      //try on an non-empty sublist
//IF STRUCT==SubList
      int parentSize=parentPreAlloc+parentPostAlloc;
      int rootSize=rootPreAlloc+parentSize+rootPostAlloc;
      int preAllocSpan=rootPreAlloc+parentPreAlloc;
      CharArrSeq.CheckedList root;
      if(rootSize==0)
      {
        root=new CharArrSeq.CheckedList();
      }
      else
      {
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-preAllocSpan,0);
        initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
        root=new CharArrSeq.CheckedList(rootSize,arr);
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
      verifyAscendingSpancharboolean(root.arr,0,preAllocSpan+51,-preAllocSpan);
      verifyAscendingSpancharboolean(root.arr,preAllocSpan+51,rootSize+101,50);
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
    CharArrSeq.CheckedList root;
    if(rootSize==0)
    {
      root=new CharArrSeq.CheckedList();
    }
    else
    {
      char[] arr=new char[rootSize];
      initAscendingArray(arr,0,-preAllocSpan,0);
      initAscendingArray(arr,preAllocSpan,100,100+parentPostAlloc+rootPostAlloc);
      root=new CharArrSeq.CheckedList(rootSize,arr);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),root.arr[i]);
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
  private static void initAscendingArray(char[] arr,int offset,int lo,int hi)
  {
    int bound=offset+(hi-lo);
    for(int i=offset;i<bound;++i,++lo)
    {
      arr[i]=TypeConversionUtil.convertTochar(lo);
    }
  }
  private static void assertIteratorStateIntegrity(Object itr,int expectedCursor,int expectedLastRet,int expectedItrModCount,Object expectedParent,Object expectedRoot)
  {
    int actualCursor;
    Object actualParent;
    if(expectedParent==expectedRoot)
    {
      if(expectedRoot instanceof OmniStack.OfChar)
      {
        if(expectedRoot instanceof CharArrSeq.CheckedStack)
        {
          actualCursor=FieldAccessor.CharArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.CharArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet,FieldAccessor.CharArrSeq.CheckedStack.Itr.lastRet(itr));
        }
        else
        {
          actualCursor=FieldAccessor.CharArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.UncheckedStack.Itr.parent(itr);
        }
      }
      else
      {
        if(expectedRoot instanceof CharArrSeq.CheckedList)
        {
          actualCursor=FieldAccessor.CharArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.CharArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet,FieldAccessor.CharArrSeq.CheckedList.Itr.lastRet(itr));
        }
        else
        {
          actualCursor=FieldAccessor.CharArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.UncheckedList.Itr.parent(itr);
          //skip the lastRet check since the unchecked iterator does not guarantee its state
          //if(itr instanceof OmniListIterator.OfChar)
          //{
          //  Assertions.assertEquals(expectedLastRet,FieldAccessor.CharArrSeq.UncheckedList.ListItr.lastRet(itr));
          //}
        }
      }
    }
    else
    {
      if(expectedRoot instanceof CharArrSeq.CheckedList)
      {
        actualCursor=FieldAccessor.CharArrSeq.CheckedSubList.Itr.cursor(itr);
        actualParent=FieldAccessor.CharArrSeq.CheckedSubList.Itr.parent(itr);
        Assertions.assertEquals(expectedItrModCount,FieldAccessor.CharArrSeq.CheckedSubList.Itr.modCount(itr));
        Assertions.assertEquals(expectedLastRet,FieldAccessor.CharArrSeq.CheckedSubList.Itr.lastRet(itr));
      }
      else
      {
        actualCursor=FieldAccessor.CharArrSeq.UncheckedSubList.Itr.cursor(itr);
        actualParent=FieldAccessor.CharArrSeq.UncheckedSubList.Itr.parent(itr);
        //skip the lastRet check since the unchecked iterator does not guarantee its state
        //if(itr instanceof OmniListIterator.OfChar)
        //{
        //  Assertions.assertEquals(expectedLastRet,FieldAccessor.CharArrSeq.UncheckedSubList.ListItr.lastRet(itr));
        //}
      }
    }
    Assertions.assertEquals(expectedCursor,actualCursor);
    Assertions.assertSame(expectedParent,actualParent);
  }
  private static CharArrSeq assertStructuralIntegrity(Object seq,int expectedSeqSize,int expectedSeqModCount,Object expectedParent,int expectedParentSize,int expectedParentModCount,CharArrSeq expectedRoot,int expectedRootSize,int expectedRootModCount)
  {
    if(seq==expectedRoot)
    {
      if(seq instanceof CharArrSeq.CheckedList)
      {
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.CharArrSeq.CheckedList.modCount(seq));
      }
      else if(seq instanceof CharArrSeq.CheckedStack)
      {
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.CharArrSeq.CheckedStack.modCount(seq));
      }
    }
    else
    {
      OmniList.OfChar actualSeqParent;
      Object actualSeqRoot;
      OmniList.OfChar actualParentParent;
      Object actualParentRoot;
      int actualParentSize;
      int actualSeqSize;
      if(expectedRoot instanceof CharArrSeq.CheckedList)
      {
        actualSeqParent=FieldAccessor.CharArrSeq.CheckedSubList.parent(seq);
        actualSeqRoot=FieldAccessor.CharArrSeq.CheckedSubList.root(seq);
        actualParentParent=FieldAccessor.CharArrSeq.CheckedSubList.parent(expectedParent);
        actualParentRoot=FieldAccessor.CharArrSeq.CheckedSubList.root(expectedParent);
        actualSeqSize=FieldAccessor.CharArrSeq.CheckedSubList.size(seq);
        actualParentSize=FieldAccessor.CharArrSeq.CheckedSubList.size(expectedParent);
        Assertions.assertEquals(expectedSeqModCount,FieldAccessor.CharArrSeq.CheckedSubList.modCount(seq));
        Assertions.assertEquals(expectedParentModCount,FieldAccessor.CharArrSeq.CheckedSubList.modCount(expectedParent));
        Assertions.assertEquals(expectedRootModCount,FieldAccessor.CharArrSeq.CheckedList.modCount(expectedRoot));
      }
      else
      {
        actualSeqParent=FieldAccessor.CharArrSeq.UncheckedSubList.parent(seq);
        actualSeqRoot=FieldAccessor.CharArrSeq.UncheckedSubList.root(seq);
        actualParentParent=FieldAccessor.CharArrSeq.UncheckedSubList.parent(expectedParent);
        actualParentRoot=FieldAccessor.CharArrSeq.UncheckedSubList.root(expectedParent);
        actualSeqSize=FieldAccessor.CharArrSeq.UncheckedSubList.size(seq);
        actualParentSize=FieldAccessor.CharArrSeq.UncheckedSubList.size(expectedParent);
      }
      Assertions.assertSame(expectedRoot,actualSeqRoot);
      Assertions.assertSame(expectedRoot,actualParentRoot);
      Assertions.assertSame(expectedParent,actualSeqParent);
      Assertions.assertNull(actualParentParent);
      Assertions.assertEquals(expectedSeqSize,actualSeqSize);
      Assertions.assertEquals(expectedParentSize,actualParentSize);
    }
    Assertions.assertEquals(expectedRootSize,FieldAccessor.CharArrSeq.size(expectedRoot));
    return expectedRoot;
  }
}
