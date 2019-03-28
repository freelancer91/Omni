package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class ShortArrSeqListTest
{
  @Test
  public void testUncheckedListput_intshort_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intshort_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intshort_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF OfRef
  @Test
  public void testUncheckedListput_intShort_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intShort_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intShort_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
  //IF OfBoolean
  @Test
  public void testUncheckedListput_intboolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intboolean_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intboolean_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
    //IF OfByte,OfChar
  @Test
  public void testUncheckedListput_intbyte_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intbyte_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intbyte_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intByte_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intByte_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intByte_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
    //ENDIF
  //ENDIF
//ENDIF
  @Test
  public void testUncheckedListremoveShortAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToshort(seq.size()-1),seq.removeShortAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveShortAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.removeShortAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveShortAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToshort(((i&1)==0?i:0)+removeIndex),seq.removeShortAt(removeIndex));
    }
  }
  @Test
  public void testUncheckedListgetShort_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertToshort(i);
      Assertions.assertEquals(expectedVal,seq.getShort(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
//IF OfRef
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToShort(seq.size()-1),seq.remove(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToShort(i),seq.remove(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToShort(((i&1)==0?i:0)+removeIndex),seq.remove(removeIndex));
    }
  }
  @Test
  public void testUncheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertToShort(i);
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
        //IF OfInt
  @Test
  public void testUncheckedListremoveIntAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
        //ENDIF
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
  @Test
  public void testUncheckedListset_intshort_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(short)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
//IF OfRef
  @Test
  public void testUncheckedListset_intShort_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToShort(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToShort(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(Short)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
//ENDIF
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
//IF OfRef
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
  //IF OfBoolean
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
    var seq=new ShortArrSeq.CheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshortboolean(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
    var seq=new ShortArrSeq.CheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
    var seq=new ShortArrSeq.CheckedList();
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList();
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(0,null);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertToshort(100-i-1);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshort(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new ShortArrSeq.CheckedList(50);
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
    var seq=new ShortArrSeq.CheckedList(50);
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
    //ENDIF
  //ENDIF
//ENDIF
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListremoveShortAt_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeShortAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveShortAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeShortAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetShort_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getShort(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetShort_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getShort(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveShortAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeShortAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveShortAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeShortAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetShort_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getShort(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetShort_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getShort(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListremoveShortAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToshort(seq.size()-1),seq.removeShortAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveShortAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.removeShortAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveShortAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToshort(((i&1)==0?i:0)+removeIndex),seq.removeShortAt(removeIndex));
    }
  }
  @Test
  public void testCheckedListgetShort_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertToshort(i);
      Assertions.assertEquals(expectedVal,seq.getShort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToShort(seq.size()-1),seq.remove(removeIndex));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToShort(i),seq.remove(removeIndex));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToShort(((i&1)==0?i:0)+removeIndex),seq.remove(removeIndex));
    }
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertToShort(i);
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
        //IF OfInt
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListremoveIntAt_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
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
        //ENDIF
      //ENDIF
    //ENDIF
  //ENDIF
//ENDIF
  @Test
  public void testCheckedListset_intshort_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToshort(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToshort(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(short)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListset_intshort_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intshort_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intshort_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intshort_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
//IF OfRef
  @Test
  public void testCheckedListset_intShort_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToShort(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToShort(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(Short)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListset_intShort_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intShort_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intShort_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intShort_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new ShortArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
//ENDIF
}
