package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
@SuppressWarnings({"rawtypes","unchecked"})
public class RefArrSeqListTest
{
  @Test
  public void testUncheckedListput_intObject_initialCapacityDEFAULT_NonThrowingIndex()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
      Assertions.assertSame(putVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacityDEFAULT_InsertBegin()
  {
    var seq=new RefArrSeq.UncheckedList();
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
    var seq=new RefArrSeq.UncheckedList();
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
    var seq=new RefArrSeq.UncheckedList();
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
    var seq=new RefArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
      Assertions.assertSame(putVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacityNULL_InsertBegin()
  {
    var seq=new RefArrSeq.UncheckedList(0,null);
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
    var seq=new RefArrSeq.UncheckedList(0,null);
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
    var seq=new RefArrSeq.UncheckedList(0,null);
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
    var seq=new RefArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
      Assertions.assertSame(putVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intObject_initialCapacity50_InsertBegin()
  {
    var seq=new RefArrSeq.UncheckedList(50);
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
    var seq=new RefArrSeq.UncheckedList(50);
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
    var seq=new RefArrSeq.UncheckedList(50);
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
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertSame(seq.arr[removeIndex],seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtBegin()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertSame(seq.arr[removeIndex],seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtMidPoint()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToObject(((i&1)==0?i:0)+removeIndex),seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
    }
  }
  @Test
  public void testUncheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(seq.arr[i],seq.get(i));
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListset_intObject_seqIsNotEmpty_NonThrowingIndex()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToObject(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.set(i,inputVal));
      Assertions.assertSame(inputVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_NonThrowingIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
      Assertions.assertSame(putVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_InsertBegin()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_InsertEnd()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_InsertMidPoint()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
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
  public void testCheckedListput_intObject_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList();
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
    var seq=new RefArrSeq.CheckedList();
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
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    var seq=new RefArrSeq.CheckedList();
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
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_NonThrowingIndex()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
      Assertions.assertSame(putVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_InsertBegin()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_InsertEnd()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_InsertMidPoint()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
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
  public void testCheckedListput_intObject_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
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
    var seq=new RefArrSeq.CheckedList(0,null);
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
    var seq=new RefArrSeq.CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
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
    var seq=new RefArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacity50_NonThrowingIndex()
  {
    var seq=new RefArrSeq.CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToObject(100-i-1);
      seq.put(i,putVal);
      Assertions.assertSame(putVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_InsertBegin()
  {
    var seq=new RefArrSeq.CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_InsertEnd()
  {
    var seq=new RefArrSeq.CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_InsertMidPoint()
  {
    var seq=new RefArrSeq.CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
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
  public void testCheckedListput_intObject_initialCapacity50_seqIsEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    var seq=new RefArrSeq.CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList(50);
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
    var seq=new RefArrSeq.CheckedList(50);
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
    var seq=new RefArrSeq.CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_seqIsEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    var seq=new RefArrSeq.CheckedList(50);
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
    var seq=new RefArrSeq.CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsEmpty_NegativeIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_NegativeIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList();
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
    var seq=new RefArrSeq.CheckedList();
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
    var seq=new RefArrSeq.CheckedList();
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
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_removeAtEnd()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertSame(seq.arr[removeIndex],seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_removeAtBegin()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertSame(seq.arr[removeIndex],seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_removeAtMidPoint()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(TypeConversionUtil.convertToObject(((i&1)==0?i:0)+removeIndex),seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
    }
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(seq.arr[i],seq.get(i));
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListset_intObject_seqIsNotEmpty_NonThrowingIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToObject(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.set(i,inputVal));
      Assertions.assertSame(inputVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListset_intObject_seqIsEmpty_negativeIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intObject_seqIsEmpty_hiIndex()
  {
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intObject_seqIsNotEmpty_negativeIndex()
  {
    var seq=new RefArrSeq.CheckedList();
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
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
}
