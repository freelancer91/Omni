package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class CharArrSeqListTest
{
  @Test
  public void testUncheckedListput_intchar_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intchar_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intchar_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
//IF OfRef
  @Test
  public void testUncheckedListput_intCharacter_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intCharacter_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intCharacter_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
  //IF OfBoolean
  @Test
  public void testUncheckedListput_intboolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intboolean_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intboolean_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.UncheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new CharArrSeq.UncheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
  //ENDIF
//ENDIF
  @Test
  public void testUncheckedListremoveCharAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTochar(seq.size()-1),seq.removeCharAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveCharAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.removeCharAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveCharAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertTochar(((i&1)==0?i:0)+removeIndex),seq.removeCharAt(removeIndex));
    }
  }
  @Test
  public void testUncheckedListgetChar_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertTochar(i);
      Assertions.assertEquals(expectedVal,seq.getChar(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
//IF OfRef
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(seq.size()-1),seq.remove(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(i),seq.remove(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(((i&1)==0?i:0)+removeIndex),seq.remove(removeIndex));
    }
  }
  @Test
  public void testUncheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertToCharacter(i);
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
  public void testUncheckedListset_intchar_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertTochar(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(char)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
//IF OfRef
  @Test
  public void testUncheckedListset_intCharacter_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToCharacter(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(Character)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
//ENDIF
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
    var seq=new CharArrSeq.CheckedList();
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
    var seq=new CharArrSeq.CheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
    var seq=new CharArrSeq.CheckedList(50);
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
//IF OfRef
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
    var seq=new CharArrSeq.CheckedList();
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
    var seq=new CharArrSeq.CheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
    var seq=new CharArrSeq.CheckedList(50);
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
  //IF OfBoolean
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
    var seq=new CharArrSeq.CheckedList();
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
    var seq=new CharArrSeq.CheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
    var seq=new CharArrSeq.CheckedList(50);
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
    var seq=new CharArrSeq.CheckedList();
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
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
    var seq=new CharArrSeq.CheckedList();
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new CharArrSeq.CheckedList(0,null);
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
    var seq=new CharArrSeq.CheckedList(0,null);
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
    var seq=new CharArrSeq.CheckedList(50);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new CharArrSeq.CheckedList(50);
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
    var seq=new CharArrSeq.CheckedList(50);
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
  //ENDIF
//ENDIF
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListremoveCharAt_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeCharAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveCharAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeCharAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetChar_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getChar(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetChar_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getChar(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveCharAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeCharAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveCharAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeCharAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetChar_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getChar(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetChar_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getChar(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListremoveCharAt_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTochar(seq.size()-1),seq.removeCharAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveCharAt_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.removeCharAt(removeIndex));
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100+i+1,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListremoveCharAt_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertTochar(((i&1)==0?i:0)+removeIndex),seq.removeCharAt(removeIndex));
    }
  }
  @Test
  public void testCheckedListgetChar_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertTochar(i);
      Assertions.assertEquals(expectedVal,seq.getChar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(seq.size()-1),seq.remove(removeIndex));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF 
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(i),seq.remove(removeIndex));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(((i&1)==0?i:0)+removeIndex),seq.remove(removeIndex));
    }
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
//IF 
      var expectedVal=TypeConversionUtil.convertToCharacter(i);
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
  public void testCheckedListset_intchar_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertTochar(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(char)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListset_intchar_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intchar_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intchar_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intchar_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
//IF OfRef
  @Test
  public void testCheckedListset_intCharacter_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToCharacter(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(i),seq.set(i,inputVal));
//IF 
      Assertions.assertEquals(inputVal,(Character)seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListset_intCharacter_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intCharacter_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intCharacter_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intCharacter_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new CharArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
//ENDIF
}
