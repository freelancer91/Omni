package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//IF OfRef
@SuppressWarnings({"rawtypes","unchecked"})
//ENDIF
public class RefArrSeqListTest
{
  @Test
  public void testUncheckedListput_intObject_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
//IF OfRef
      Assertions.assertSame(putVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intObject_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
//IF OfRef
      Assertions.assertSame(putVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.UncheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListput_intObject_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new RefArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
//IF OfRef
      Assertions.assertSame(putVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new RefArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new RefArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new RefArrSeq.UncheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF OfRef
      Assertions.assertSame(seq.arr[removeIndex],seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF OfRef
      Assertions.assertSame(seq.arr[removeIndex],seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
//ENDIF
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToObject(((i&1)==0?i:0)+removeIndex),seq.remove(removeIndex));
//IF OfRef
      Assertions.assertNull(seq.arr[seq.size()]);
//ENDIF
    }
  }
  @Test
  public void testUncheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
//IF OfRef
      Assertions.assertSame(seq.arr[i],seq.get(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListset_intObject_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.UncheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToObject(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.set(i,inputVal));
//IF OfRef
      Assertions.assertSame(inputVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
//IF OfRef
      Assertions.assertSame(putVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_InsertBegin()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_InsertEnd()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_InsertMidPoint()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_NonThrowingIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
//IF OfRef
      Assertions.assertSame(putVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_InsertBegin()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_InsertEnd()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_InsertMidPoint()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==NULL
    var seq=new RefArrSeq.CheckedList(0,null);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
  @Test
  public void testCheckedListput_intObject_initialCapacity50_NonThrowingIndex()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
//IF OfRef
      Assertions.assertSame(putVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_InsertBegin()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_InsertEnd()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_InsertMidPoint()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
  //IF STRUCTNAME==CheckedList
    Assertions.assertEquals(100,seq.modCount);
  //ENDIF
    for(int i=0,val=1;i<50;++i,val+=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
    for(int i=50,val=98;i<100;++i,val-=2)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(val),seq.arr[i]);
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListput_intObject_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intObject_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_seqIsEmpty_negativeIndex()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_seqIsEmpty_hiIndex()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
//IF 
    var seq=new RefArrSeq.CheckedList(50);
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListremove_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_NegativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
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
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
//IF OfRef
      Assertions.assertSame(seq.arr[removeIndex],seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
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
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
//IF OfRef
      Assertions.assertSame(seq.arr[removeIndex],seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
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
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToObject(((i&1)==0?i:0)+removeIndex),seq.remove(removeIndex));
//IF OfRef
      Assertions.assertNull(seq.arr[seq.size()]);
//ENDIF
    }
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
//IF OfRef
      Assertions.assertSame(seq.arr[i],seq.get(i));
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
  @Test
  public void testCheckedListset_intObject_seqIsNotEmpty_NonThrowingIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToObject(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.set(i,inputVal));
//IF OfRef
      Assertions.assertSame(inputVal,seq.arr[i]);
//ENDIF
      Assertions.assertEquals(100,seq.size());
//IF STRUCTNAME==CheckedList
      Assertions.assertEquals(100,seq.modCount);
//ENDIF
    }
  }
//IF STRUCTNAME==CheckedList
  @Test
  public void testCheckedListset_intObject_seqIsEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intObject_seqIsEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intObject_seqIsNotEmpty_negativeIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intObject_seqIsNotEmpty_hiIndex()
  {
//IF INITIALCAPACITY==DEFAULT
    var seq=new RefArrSeq.CheckedList();
//ENDIF
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
//ENDIF
}
