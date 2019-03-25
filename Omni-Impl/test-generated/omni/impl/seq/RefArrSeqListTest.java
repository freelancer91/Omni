package omni.impl.seq;
import java.util.ArrayList;
import omni.impl.seq.RefArrSeq.UncheckedList;
import omni.impl.seq.RefArrSeq.CheckedList;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqListTest
{
  @Test
  public void testUncheckedListput_intObject_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
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
    UncheckedList seq=new UncheckedList();
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
    UncheckedList seq=new UncheckedList();
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
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertToObject(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intObject_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intObject_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intObject_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
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
    UncheckedList seq=new UncheckedList(0,null);
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
    UncheckedList seq=new UncheckedList(0,null);
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
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertToObject(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intObject_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intObject_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intObject_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
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
    UncheckedList seq=new UncheckedList(50);
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
    UncheckedList seq=new UncheckedList(50);
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
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertToObject(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intObject_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intObject_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtEnd()
  {
    UncheckedList seq=new UncheckedList();
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
    UncheckedList seq=new UncheckedList();
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
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertSame(seq.arr[removeIndex],seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
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
    UncheckedList seq=new UncheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertToObject(i));
    }
    System.out.println("BEGIN testCheckedListadd_intObject_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intObject_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertToObject(i));
    }
    System.out.println("BEGIN testCheckedListadd_intObject_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intObject_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToObject(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertToObject(i));
    }
    System.out.println("BEGIN testCheckedListadd_intObject_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intObject_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intObject_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intObject_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intObject_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.remove(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListget_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.get(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremove_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertSame(seq.arr[removeIndex],seq.remove(removeIndex));
      Assertions.assertNull(seq.arr[seq.size()]);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intObject_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intObject_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToObject(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertToObject(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
}
