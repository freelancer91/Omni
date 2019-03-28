package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class IntArrSeqListTest
{
  @Test
  public void testUncheckedListput_intint_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intint_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intint_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF OfRef
  @Test
  public void testUncheckedListput_intInteger_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intInteger_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intInteger_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  //IF OfBoolean
  @Test
  public void testUncheckedListput_intboolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intboolean_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intboolean_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
    //IF OfByte,OfChar
  @Test
  public void testUncheckedListput_intbyte_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intbyte_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intbyte_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intByte_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intByte_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intByte_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
      //IF OfShort
  @Test
  public void testUncheckedListput_intchar_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intchar_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intchar_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intCharacter_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intCharacter_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intCharacter_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intshort_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intshort_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intshort_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intShort_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intShort_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intShort_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
  @Test
  public void testUncheckedListremoveIntAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToint(seq.size()-1),seq.removeIntAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveIntAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.removeIntAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveIntAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToint(((i&1)==0?i:0)+removeIndex),seq.removeIntAt(removeIndex));
    }
  }
  @Test
  public void testUncheckedListgetInt_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertToint(i);
      Assertions.assertEquals(expectedVal,seq.getInt(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
//IF OfRef
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(seq.size()-1),seq.remove(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(i),seq.remove(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(((i&1)==0?i:0)+removeIndex),seq.remove(removeIndex));
    }
  }
  @Test
  public void testUncheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertToInteger(i);
      Assertions.assertEquals(expectedVal,seq.get(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  //IF OfDouble
  @Test
  public void testUncheckedListremoveDoubleAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(seq.size()-1),seq.removeDoubleAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveDoubleAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(i),seq.removeDoubleAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveDoubleAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(((i&1)==0?i:0)+removeIndex),seq.removeDoubleAt(removeIndex));
    }
  }
  @Test
  public void testUncheckedListgetDouble_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertTodouble(i);
      Assertions.assertEquals(expectedVal,seq.getDouble(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
    //IF OfFloat
  @Test
  public void testUncheckedListremoveFloatAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(seq.size()-1),seq.removeFloatAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveFloatAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.removeFloatAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveFloatAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(((i&1)==0?i:0)+removeIndex),seq.removeFloatAt(removeIndex));
    }
  }
  @Test
  public void testUncheckedListgetFloat_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertTofloat(i);
      Assertions.assertEquals(expectedVal,seq.getFloat(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
      //IF OfLong
  @Test
  public void testUncheckedListremoveLongAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTolong(seq.size()-1),seq.removeLongAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveLongAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.removeLongAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveLongAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertTolong(((i&1)==0?i:0)+removeIndex),seq.removeLongAt(removeIndex));
    }
  }
  @Test
  public void testUncheckedListgetLong_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertTolong(i);
      Assertions.assertEquals(expectedVal,seq.getLong(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
  @Test
  public void testUncheckedListset_intint_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(int)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
//IF OfRef
  @Test
  public void testUncheckedListset_intInteger_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToInteger(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(Integer)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
//ENDIF
  @Test
  public void testCheckedListput_intint_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intint_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intint_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intint_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intint_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intint_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intint_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intint_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intint_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
//IF OfRef
  @Test
  public void testCheckedListput_intInteger_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intInteger_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intInteger_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intInteger_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intInteger_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intInteger_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  //IF OfBoolean
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertTointboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
    //IF OfByte,OfChar
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intByte_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
      //IF OfShort
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intchar_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intshort_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new IntArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intShort_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
//IF 
      var expectedVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new IntArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListremoveIntAt_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToint(seq.size()-1),seq.removeIntAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.removeIntAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToint(((i&1)==0?i:0)+removeIndex),seq.removeIntAt(removeIndex));
    }
  }
  @Test
  public void testCheckedListgetInt_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertToint(i);
      Assertions.assertEquals(expectedVal,seq.getInt(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
//IF OfRef
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListremove_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListremove_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(seq.size()-1),seq.remove(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(i),seq.remove(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(((i&1)==0?i:0)+removeIndex),seq.remove(removeIndex));
    }
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertToInteger(i);
      Assertions.assertEquals(expectedVal,seq.get(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  //IF OfDouble
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListremoveDoubleAt_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(seq.size()-1),seq.removeDoubleAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(i),seq.removeDoubleAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(((i&1)==0?i:0)+removeIndex),seq.removeDoubleAt(removeIndex));
    }
  }
  @Test
  public void testCheckedListgetDouble_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertTodouble(i);
      Assertions.assertEquals(expectedVal,seq.getDouble(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
    //IF OfFloat
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListremoveFloatAt_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(seq.size()-1),seq.removeFloatAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.removeFloatAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(((i&1)==0?i:0)+removeIndex),seq.removeFloatAt(removeIndex));
    }
  }
  @Test
  public void testCheckedListgetFloat_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertTofloat(i);
      Assertions.assertEquals(expectedVal,seq.getFloat(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
      //IF OfLong
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListremoveLongAt_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTolong(seq.size()-1),seq.removeLongAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.removeLongAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertTolong(((i&1)==0?i:0)+removeIndex),seq.removeLongAt(removeIndex));
    }
  }
  @Test
  public void testCheckedListgetLong_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertTolong(i);
      Assertions.assertEquals(expectedVal,seq.getLong(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
  @Test
  public void testCheckedListset_intint_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToint(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(int)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListset_intint_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intint_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intint_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intint_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
//IF OfRef
  @Test
  public void testCheckedListset_intInteger_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToInteger(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToInteger(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(Integer)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListset_intInteger_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intInteger_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intInteger_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intInteger_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new IntArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
//ENDIF
}
